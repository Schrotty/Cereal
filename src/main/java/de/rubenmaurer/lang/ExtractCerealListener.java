package de.rubenmaurer.lang;

import java.util.List;

class ExtractCerealListener extends de.rubenmaurer.lang.CerealBaseListener {
    private de.rubenmaurer.lang.CerealParser parser;

    ExtractCerealListener(de.rubenmaurer.lang.CerealParser parser) {
        this.parser = parser;
    }

    @Override
    public void enterBowl(de.rubenmaurer.lang.CerealParser.BowlContext ctx) {
        System.out.print(String.format("public class %s {", ctx.ID().getText()));
    }

    @Override
    public void exitBowl(de.rubenmaurer.lang.CerealParser.BowlContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterMethod(de.rubenmaurer.lang.CerealParser.MethodContext ctx) {
        String fixed = "";
        String type = "";

        boolean isFixed = ctx.fixed() != null;
        boolean isTyped = ctx.type() != null;

        if (isFixed) fixed = "static";
        if (isTyped) type = ctx.type().getText().substring(1);

        if (type.equals("string")) type = "String";

        // parameter list
        String param = "";
        if (ctx.params() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            List<de.rubenmaurer.lang.CerealParser.ParamContext> parameter = ctx.params().param();
            if (!parameter.isEmpty()) {
                parameter.forEach(s -> {
                    String[] strings = s.getText().split(":");
                    String id = strings[0];
                    String typ = strings[1].substring(1);

                    if (typ.equalsIgnoreCase("string") || typ.equalsIgnoreCase("string[]")) {
                        stringBuilder.append(String.format("%s%s %s,", typ.substring(0, 1).toUpperCase(), typ.substring(1), id));
                        return;
                    }

                    stringBuilder.append(String.format("%s %s,", typ, id));
                });

                param = stringBuilder.toString();
                if (param.endsWith(",")) param = param.substring(0, param.length() - 1);
            }
        }

        System.out.println(String.format("public %s %s %s(%s) {", fixed, type, ctx.ID().getText(), param));
    }

    @Override
    public void exitMethod(de.rubenmaurer.lang.CerealParser.MethodContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterExpr(de.rubenmaurer.lang.CerealParser.ExprContext ctx) {
        String id = "";
        if (ctx.ID() != null) {
            id = ctx.ID().getText();
        }

        if (id.equals("print")) {
            String param;
            StringBuilder params = new StringBuilder();
            ctx.exprList().forEach(s -> params.append(String.format("%s,", s.getText())));

            param = params.toString();
            if (param.endsWith(",")) param = param.substring(0, param.length() - 1);
            System.out.println(String.format("System.out.println(%s);", param));
        }
    }

    @Override
    public void enterStat(de.rubenmaurer.lang.CerealParser.StatContext ctx) {
        if (ctx.getText().contains("return")) {
            if (!ctx.expr().isEmpty()) {
                System.out.println(String.format("return %s;", ctx.expr().get(0).getText()));
            }
        }
    }
}
