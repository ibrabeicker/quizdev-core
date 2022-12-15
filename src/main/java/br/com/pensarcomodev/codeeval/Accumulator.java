package br.com.pensarcomodev.codeeval;

import java.util.List;

public interface Accumulator {
    void accumulate(Character c);

    void startDiff();

    void endDiff();

    void end();

    List<DiffPart> getParts();
}
