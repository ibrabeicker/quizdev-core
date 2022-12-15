package br.com.pensarcomodev.codeeval;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import java.util.List;

@Slf4j
public class StandardDiff extends AbstractDiff {

    public StandardDiff(String left, String right) {
        super(left, right, new DiffAccumulator(), new DiffAccumulator());
    }
}
