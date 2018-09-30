package de.rubenmaurer.lang;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.List;

import static de.rubenmaurer.lang.CerealParser.*;

class ExtractCerealListener extends de.rubenmaurer.lang.CerealBaseListener {

    private HashMap<String, String> vis = new HashMap<>();

    private HashMap<String, String> buildIns = new HashMap<>();

    private boolean important = false;

    private int exprLevel = 0;

    private String contextName;

    ExtractCerealListener() {
        vis.put("common", "public");
        vis.put("delicious", "private");

        buildIns.put("print", "System.out.println");
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        int channel = ctx.getStop().getChannel();

        //f*ck this s**t
        if (channel == 1) {
            System.out.println(ctx.getText());
        }
    }

    @Override
    public void enterBrand(BrandContext ctx) {
        Template.get("brand").single("brand", ctx.BRAND().getText()).println();
    }

    @Override
    public void enterBuy(BuyContext ctx) {
        Template.get("buy").single("brand", ctx.BRAND().getText()).println();
    }

    @Override
    public void enterBowl(BowlContext ctx) {
        String vis = ctx.visibility() != null ? this.vis.getOrDefault(ctx.visibility().getText(), "") : "";
        String name = ctx.ID().getText();

        Template.get("bowl_enter").single("visibility", vis).single("name", name).println();
        contextName = ctx.ID().getText();
    }

    @Override
    public void exitBowl(BowlContext ctx) {
        Template.get("bowl_exit").println();
    }

    @Override
    public void enterVarDec(VarDecContext ctx) {
        Template.get("varDec").single("static", important)
                                .single("type", ctx.type().ID().getText())
                                .single("name", ctx.ID().getText()).print();
    }

    @Override
    public void enterMethod(MethodContext ctx) {
        String vis = ctx.visibility() != null ? this.vis.getOrDefault(ctx.visibility().getText(), "") : "";
        String typ = ctx.type() != null ? ctx.type().ID().getText() : "void";
        String para;

        StringBuilder params = new StringBuilder();
        if (ctx.params() != null) {
            List<ParamContext> parameter = ctx.params().param();
            if (!parameter.isEmpty()) {
                parameter.forEach(p -> {
                    String type = p.type().BRACKETS() == null ? p.type().ID().getText()
                                                                : String.format("%s[]", p.type().ID().getText()) ;

                    params.append(String.format("%s %s,", type, p.ID().getText()));
                });
            }
        }

        para = params.toString();
        if (para.length() != 0) para = para.substring(0, para.length() - 1);

        Template.get("method_enter").single("visibility", vis)
                                    .single("static", important)
                                    .single("return", typ)
                                    .single("name", ctx.ID().getText())
                                    .single("params", para)
                                    .single("construct", ctx.ID().getText().equalsIgnoreCase(contextName)).println();
    }

    @Override
    public void exitMethod(MethodContext ctx) {
        Template.get("method_exit").println();
    }

    @Override
    public void enterRet(RetContext ctx) {
        Template.get("return_enter").print();
    }

    @Override
    public void enterMethodCall(MethodCallContext ctx) {
        exprLevel++;

        String method = buildIns.getOrDefault(ctx.ID().getText(), ctx.ID().getText());
        Template.get("method_call_enter").single("expr", method).print();
    }

    @Override
    public void exitMethodCall(MethodCallContext ctx) {
        Template.get("method_call_exit").print();

        checkExprLevel();
    }

    @Override
    public void enterString(StringContext ctx) {
        exprLevel++;
        Template.get("expr").single("expr", ctx.STRING().getText()).print();
    }

    @Override
    public void exitString(StringContext ctx) {
        checkExprLevel();
    }

    @Override
    public void enterAllocValue(AllocValueContext ctx) {
        Template.get("allocValue").print();
    }

    @Override
    public void enterCreateNewObj(CreateNewObjContext ctx) {
        exprLevel++;

        Template.get("createNew_enter").single("name", ctx.ID().getText()).print();
    }

    @Override
    public void exitCreateNewObj(CreateNewObjContext ctx) {
        Template.get("createNew_exit").print();

        checkExprLevel();
    }

    @Override
    public void enterImp(ImpContext ctx) {
        important = true;
    }

    @Override
    public void exitImp(ImpContext ctx) {
        important = false;
    }

    private void checkExprLevel() {
        if (--exprLevel == 0) {
            Template.get("colon").println();
        }
    }
}
