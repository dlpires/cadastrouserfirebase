package com.example.root.cadastrofirebase;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class VincDisciplinaActivity extends ListActivity {

    private String [] lista;
    private ListView listView;
    public static ArrayList<Disciplina> listaSelecionada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vinc_disciplina);

        lista = new String[]{"SIF043 - Gerência de Projetos",
                "SIF038 - Redes de Computadores I",
                "SIF011 - Linguagem de Programação III",
                "SIF030 - Sistemas Operacionais II",
                "SIF022 - Estrutura de Dados II"};

        listaSelecionada = new ArrayList<Disciplina>();

        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, lista));

        listView = getListView();

        // Ativando a seleção de mais de um item na lista
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        //Pegar o item clicado
        Disciplina d = new Disciplina(this.getListAdapter().getItem(position).toString());
        listaSelecionada.add(d);
    }

}
