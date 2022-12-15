package br.com.pensarcomodev.codeeval;

import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Diff;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlankIgnoringDiffTest {

    @Test
    public void sameString() {
        assertSame("abc def", "abc def");
        assertSame(" abc def ", "abc def");
        assertSame("abc     def", "abc def");
        assertSame("abc\n\ndef", "abc def");
        assertDiff("abcdef", "abc def");
    }

    @Test
    public void sameStringTrailingLeadingBlanks() {

    }

    @Test
    public void sameStringInterBlanks() {

    }

    private void assertSame(String left, String right) {
        BlankIgnoringDiff blankIgnoringDiff = new BlankIgnoringDiff(left, right);
        List<DiffPart> leftDiffs = blankIgnoringDiff.getLeftDiffs();
        assertNoDiffs(leftDiffs);
        List<DiffPart> rightDiffs = blankIgnoringDiff.getRightDiffs();
        assertNoDiffs(rightDiffs);
    }

    private void assertDiff(String left, String right) {
        BlankIgnoringDiff blankIgnoringDiff = new BlankIgnoringDiff(left, right);
        System.out.println(blankIgnoringDiff);
        List<DiffPart> leftDiffs = blankIgnoringDiff.getLeftDiffs();
        boolean leftHasDiff = leftDiffs.stream().anyMatch(i -> i.getDiffEquality() == DiffEquality.DIFFERENT);
        List<DiffPart> rightDiffs = blankIgnoringDiff.getRightDiffs();
        boolean rightHasDiff = rightDiffs.stream().anyMatch(i -> i.getDiffEquality() == DiffEquality.DIFFERENT);
        assertTrue(leftHasDiff || rightHasDiff);
    }

    private void assertNoDiffs(List<DiffPart> parts) {
        Assertions.assertThat(parts).allMatch(i -> i.getDiffEquality() == DiffEquality.SAME);
    }
}
