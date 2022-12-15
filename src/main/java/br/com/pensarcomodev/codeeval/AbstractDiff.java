package br.com.pensarcomodev.codeeval;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDiff implements CommandVisitor<Character> {

    boolean leftDiff = false;
    boolean rightDiff = false;

    private final Accumulator leftAccumulator;
    private final Accumulator rightAccumulator;

    public AbstractDiff(String left, String right, Accumulator leftAccumulator, Accumulator rightAccumulator) {
        this.leftAccumulator = leftAccumulator;
        this.rightAccumulator = rightAccumulator;
        StringsComparator stringsComparator = new StringsComparator(left, right);
        EditScript<Character> script = stringsComparator.getScript();
        script.visit(this);
        end();
    }

    @Override
    public void visitKeepCommand(Character c) {
        if (leftDiff) {
            leftDiff = false;
            leftAccumulator.endDiff();
        }
        if (rightDiff) {
            rightDiff = false;
            rightAccumulator.endDiff();
        }
        leftAccumulator.accumulate(c);
        rightAccumulator.accumulate(c);
    }

    @Override
    public void visitInsertCommand(Character c) {
        if (!leftDiff) {
            leftDiff = true;
            leftAccumulator.startDiff();
        }
        leftAccumulator.accumulate(c);
    }

    @Override
    public void visitDeleteCommand(Character c) {
        if (!rightDiff) {
            rightDiff = true;
            rightAccumulator.startDiff();
        }
        rightAccumulator.accumulate(c);
    }

    public void end() {
        leftAccumulator.end();
        rightAccumulator.end();
    }

    public List<DiffPart> getLeftDiffs() {
        return leftAccumulator.getParts();
    }

    public List<DiffPart> getRightDiffs() {
        return rightAccumulator.getParts();
    }

    public String toString() {
        return String.format("leftDiff = '%s', rightDiff = '%s'", leftAccumulator, rightAccumulator);
    }

}
