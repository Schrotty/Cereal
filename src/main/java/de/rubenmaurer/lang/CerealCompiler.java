package de.rubenmaurer.lang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class CerealCompiler {

    public static void main(String[] args) throws FileNotFoundException {
        String output = String.format("%s.java", args[0].split("\\.")[0]);

        CharStream stream = CharStreams.fromString(readInput(args[0]));
        System.setOut(new PrintStream(new FileOutputStream(new File(output))));

        de.rubenmaurer.lang.CerealLexer lexer = new de.rubenmaurer.lang.CerealLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        de.rubenmaurer.lang.CerealParser parser = new de.rubenmaurer.lang.CerealParser(tokens);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExtractCerealListener extractor = new ExtractCerealListener(parser);

        walker.walk(extractor, tree);
    }

    private static String readInput(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        FileReader fr = null;

        try {
            fr = new FileReader(path);
            int i;
            while ((i=fr.read()) != -1) {
                stringBuilder.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
