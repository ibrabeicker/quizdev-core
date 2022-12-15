package br.com.pensarcomodev.codeeval;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlankIgnoringDiff extends AbstractDiff {

    public BlankIgnoringDiff(String left, String right) {
        super(left, right, new BlankIgnoringDiffAccumulator(), new BlankIgnoringDiffAccumulator());
    }
}
