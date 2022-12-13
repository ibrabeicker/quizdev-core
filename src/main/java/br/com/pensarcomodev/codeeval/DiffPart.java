package br.com.pensarcomodev.codeeval;

import lombok.Data;

@Data
public class DiffPart {

    private String value;
    private DiffEquality diffEquality;
    private DiffPart reference;

    public DiffPart(String value, DiffEquality diffEquality) {
        this.value = value;
        this.diffEquality = diffEquality;
    }
}
