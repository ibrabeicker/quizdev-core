package br.com.pensarcomodev.codeeval;

import org.apache.commons.text.diff.CommandVisitor;

import java.util.ArrayList;
import java.util.List;

public class MyCommandsVisitor implements CommandVisitor<Character> {

    String left = "";
    String right = "";

    int position = 0;
    List<String> leftDiff = new ArrayList<>();
    List<String> rightDiff = new ArrayList<>();

    private List<DiffPart> leftParts = new ArrayList<>();
    private List<DiffPart> rightParts = new ArrayList<>();

    StringBuilder leftSb = new StringBuilder();
    StringBuilder rightSb = new StringBuilder();

    boolean inserting = false;
    boolean deleting = false;

    @Override
    public void visitKeepCommand(Character c) {
        // Character is present in both files.
        DiffPart reference = null;
        if (deleting) {
            reference = new DiffPart(leftSb.toString(), DiffEquality.DIFFERENT);
            leftParts.add(reference);
            leftSb = new StringBuilder();
            left = left + "]";
            deleting = false;

        }
        if (inserting) {
            DiffPart diffPart = new DiffPart(rightSb.toString(), DiffEquality.DIFFERENT);
            diffPart.setReference(reference);
            rightParts.add(diffPart);
            rightSb = new StringBuilder();
            right = right + "]";
            inserting = false;
        }
        rightSb.append(c);
        leftSb.append(c);
        left = left + c;
        right = right + c;
    }

    @Override
    public void visitInsertCommand(Character c) {
        /*
         * Character is present in right file but not in left. Method name
         * 'InsertCommand' means, c need to insert it into left to match right.
         */
        if (!inserting) {
            if (c == '\n') {
                visitKeepCommand(c);
                return;
            }
            right = right + "[";
            rightParts.add(new DiffPart(rightSb.toString(), DiffEquality.SAME));
            rightSb = new StringBuilder();
        }
        inserting = true;
        right = right + c;
        rightSb.append(c);
    }

    @Override
    public void visitDeleteCommand(Character c) {
        /*
         * Character is present in left file but not right. Method name 'DeleteCommand'
         * means, c need to be deleted from left to match right.
         */
        if (!deleting) {
            if (c == '\n') {
                visitKeepCommand(c);
                return;
            }
            left = left + "[";
            leftParts.add(new DiffPart(leftSb.toString(), DiffEquality.SAME));
            leftSb = new StringBuilder();
        }
        deleting = true;
        left = left + c;
        leftSb.append(c);
    }

    public void end() {
        rightParts.add(new DiffPart(rightSb.toString(), inserting ? DiffEquality.DIFFERENT : DiffEquality.SAME));
        leftParts.add(new DiffPart(leftSb.toString(), deleting ? DiffEquality.DIFFERENT : DiffEquality.SAME));
    }

    public String print() {
        return String.format("leftDiff = %s, rightDiff = %s", leftDiff, rightDiff);
    }

    public String printFromParts() {
        return String.format("leftDiff = %s, rightDiff = %s", getFrom(leftParts), getFrom(rightParts));
    }

    private String getFrom(List<DiffPart> diffParts) {
        StringBuilder sb = new StringBuilder();
        for (DiffPart diffPart : diffParts) {
            if (diffPart.getDiffEquality() == DiffEquality.SAME) {
                sb.append(diffPart.getValue());
            } else {
                sb.append('[').append(diffPart.getValue());
                if (diffPart.getReference() != null) {
                    sb.append('>').append(diffPart.getReference().getValue());
                }
                sb.append(']');
            }
        }
        return sb.toString();
    }


}
