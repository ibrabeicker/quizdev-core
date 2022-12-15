package br.com.pensarcomodev.codeeval;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DiffAccumulator implements Accumulator {

    @Getter
    private final List<DiffPart> parts = new ArrayList<>();

    private StringBuilder stringBuilder = new StringBuilder();

    @Getter
    private boolean diffState = false;

    @Override
    public void accumulate(Character c) {
        stringBuilder.append(c);
    }

    @Override
    public void startDiff() {
        if (stringBuilder.length() == 0) {
            return;
        }
        DiffPart diffPart = new DiffPart(stringBuilder.toString(), DiffEquality.SAME);
        parts.add(diffPart);
        reset();
        diffState = true;
    }

    @Override
    public void endDiff() {
        if (stringBuilder.length() == 0) {
            return;
        }
        DiffPart diffPart = new DiffPart(stringBuilder.toString(), DiffEquality.DIFFERENT);
        parts.add(diffPart);
        reset();
        diffState = false;
    }

    @Override
    public void end() {
        if (diffState) {
            endDiff();
        } else {
            startDiff();
        }
    }

    private void reset() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public String toString() {
        return DiffPrinter.printDiff(parts);
    }
}
