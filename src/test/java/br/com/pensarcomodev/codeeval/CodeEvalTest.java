package br.com.pensarcomodev.codeeval;

import org.junit.jupiter.api.Test;

public class CodeEvalTest {

    @Test
    public void testSameString() {
        String foo = "foo\nbar\nbaz";
        String eval = CodeEval.eval(foo, foo);
        System.out.println(eval);
    }

    @Test
    public void testAddedLineString() {
        String s1 = "foo bar";
        String s2 = "foo tau bar";
        String eval = CodeEval.eval(s1, s2);
        System.out.println(eval);
    }

    @Test
    public void testAddedLineEndString() {
        String s1 = "foo\nbar\nbaz";
        String s2 = "foo\nbar\nbaz\nzoo";
        String eval = CodeEval.eval(s1, s2);
        System.out.println(eval);
    }

    @Test
    public void testIdPlaceholder() {
        String s1 = "int foo = 300";
        String s2 = "int <id1> = 400";
        String eval = CodeEval.eval(s1, s2);
        System.out.println(eval);
    }

    @Test
    public void testCodeExtraLine() {
        String s1 = "public void foo() {\n" +
                "    int a = 0;\n" +
                "    if (1 == 2) {\n" +
                "        foo();\n" +
                "    }\n" +
                "}";
        String s2 = "public void <id>() {\n" +
                "    if (1 == 2) {\n" +
                "        foo();\n" +
                "    }\n" +
                "}";
        CodeComparator.compare(s1, s2);
        System.out.println(CodeEval.eval(s1, s2));
    }

    @Test
    public void testDonaAranha() {
        String s1 = "A dona aranha subiu pela parede\nVeio a chuva forte e a derrubou";
        String s2 = "A dona aranha subiu pela parede\nVeio a chuva forte e a derrubou\nJá passou a chuva o sol já vai surgindo";
        String eval = CodeEval.eval(s1, s2);
        System.out.println(eval);
    }
}
