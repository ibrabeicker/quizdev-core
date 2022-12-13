package br.com.pensarcomodev.codeeval;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

public class CodeEval {

    public static String eval(String first, String second) {
        StringsComparator stringsComparator = new StringsComparator(first, second);
        EditScript<Character> script = stringsComparator.getScript();
        MyCommandsVisitor myCommandsVisitor = new MyCommandsVisitor();
        script.visit(myCommandsVisitor);
        myCommandsVisitor.end();
//        return "FINAL DIFF = " + myCommandsVisitor.left + " | " + myCommandsVisitor.right;
//        return myCommandsVisitor.print();
        return myCommandsVisitor.printFromParts();
    }
}
