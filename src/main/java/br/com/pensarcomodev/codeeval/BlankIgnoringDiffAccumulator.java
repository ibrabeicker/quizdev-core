package br.com.pensarcomodev.codeeval;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BlankIgnoringDiffAccumulator implements Accumulator {

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
        String string = stringBuilder.toString();
        reset();
        handleEndDiff(string);
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

    private void handleEndDiff(String input) {
        input = passPrecedingBlanksToPredecessor(input);
        String trimmed = input.trim();
        if (trimmed.length() > 0) {
            DiffPart diffPart = new DiffPart(trimmed, DiffEquality.DIFFERENT);
            parts.add(diffPart);
        } else {
            return;
        }
        passTrailingBlanksToSuccessor(input);
    }

    private String passPrecedingBlanksToPredecessor(String input) {
        StringBuilder blanks = new StringBuilder();
        int i = 0;
        for (; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\n' || c == ' ' || c == '\t') {
                blanks.append(c);
            } else {
                break;
            }
        }
        if (i > 0) { // Teve brancos
            assurePartsNotEmpty();
            appendToPredecessor(blanks.toString());
        }
        return input.substring(i);
    }

    private void passTrailingBlanksToSuccessor(String input) {
        int i = input.length() - 1;
        for (; i >= 0; i--) {
            char c = input.charAt(i);
            if (c == '\n' || c == ' ' || c == '\t') {
                stringBuilder.append(c);
            } else {
                break;
            }
        }
    }

    private void assurePartsNotEmpty() {
        if (parts.isEmpty()) {
            DiffPart diffPart = new DiffPart("", DiffEquality.SAME);
            parts.add(diffPart);
        }
    }

    private void appendToPredecessor(String value) {
        DiffPart lastPart = parts.get(parts.size() - 1);
        lastPart.setValue(lastPart.getValue() + value);
    }

    @Override
    public String toString() {
        return DiffPrinter.printDiff(parts);
    }
}
