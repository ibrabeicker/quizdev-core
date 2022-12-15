package br.com.pensarcomodev.codeeval;

import java.util.List;

public class DiffPrinter {

    private DiffPrinter() {}

    public static String printDiff(List<DiffPart> parts) {
        StringBuilder sb = new StringBuilder();
        for (DiffPart diffPart : parts) {
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
