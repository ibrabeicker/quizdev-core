package br.com.pensarcomodev.codeeval;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CodeComparator {

    public static void compare(String input, String expected) {
        List<String> inputLines = Arrays.asList(input.split("\n"));
        List<String> expectedLines = Arrays.asList(expected.split("\n"));
        Iterator<String> inputIterator = inputLines.iterator();
        Iterator<String> expectedIterator = expectedLines.iterator();

        MyCommandsVisitor visitor = new MyCommandsVisitor();

        while (inputIterator.hasNext() || expectedIterator.hasNext()) {
            String left = (inputIterator.hasNext() ? inputIterator.next() : "");
            String right = (expectedIterator.hasNext() ? expectedIterator.next() : "");

            StringsComparator stringsComparator = new StringsComparator(left, right);
            EditScript<Character> script = stringsComparator.getScript();

            if (script.getLCSLength() > (Integer.max(left.length(), right.length()) * 0.4)) {
                script.visit(visitor);
            } else {
                StringsComparator leftComparator = new StringsComparator(left, "\n");
                leftComparator.getScript().visit(visitor);
                StringsComparator rightComparator = new StringsComparator("\n", right);
                rightComparator.getScript().visit(visitor);
            }
        }
        System.out.println(visitor.printFromParts());
    }
}
