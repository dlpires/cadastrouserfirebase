package com.example.root.cadastrofirebase;

import com.google.firebase.database.Exclude;

/**
 * Created by root on 14/01/18.
 */

public class Disciplina {
    @Exclude
    private int codDisc;
    private String dscDisc;

    public Disciplina(String dscDisc) {
        this.setDscDisc(dscDisc);
    }

    @Exclude
    public int getCodDisc() {
        return codDisc;
    }

    @Exclude
    public void setCodDisc(int codDisc) {
        this.codDisc = codDisc;
    }


    public String getDscDisc() {
        return dscDisc;
    }

    public void setDscDisc(String dscDisc) {
        this.dscDisc = dscDisc;
    }
}
