package com.example.root.cadastrofirebase;

import com.example.root.cadastrofirebase.Disciplina;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 14/01/18.
 */

public class Professor {
    @Exclude
    private int codProf;
    private String nomeProf;
    private String emailProf;
    private String senhaProf;
    private String rgProf;
    private String dataNascProf;
    private String url;
    private ArrayList <Disciplina> disciplinas;

    @Exclude
    public int getCodProf() {
        return codProf;
    }

    @Exclude
    public void setCodProf(int codProf) {
        this.codProf = codProf;
    }

    public String getNomeProf() {
        return nomeProf;
    }

    public void setNomeProf(String nomeProf) {
        this.nomeProf = nomeProf;
    }

    public String getEmailProf() {
        return emailProf;
    }

    public void setEmailProf(String emailProf) {
        this.emailProf = emailProf;
    }

    public String getSenhaProf() {
        return senhaProf;
    }

    public void setSenhaProf(String senhaProf) {
        this.senhaProf = senhaProf;
    }

    public String getRgProf() {
        return rgProf;
    }

    public void setRgProf(String rgProf) {
        this.rgProf = rgProf;
    }

    public String getDataNascProf() {
        return dataNascProf;
    }

    public void setDataNascProf(String dataNascProf) {
        this.dataNascProf = dataNascProf;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
