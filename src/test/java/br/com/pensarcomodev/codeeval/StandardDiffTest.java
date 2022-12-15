package br.com.pensarcomodev.codeeval;

import org.junit.jupiter.api.Test;

public class StandardDiffTest {

    @Test
    public void blanks() {
        StandardDiff standardDiff = new StandardDiff("{ if ( abc ) { } ", "{if (abc) {}");
        System.out.println(standardDiff);
    }
}
