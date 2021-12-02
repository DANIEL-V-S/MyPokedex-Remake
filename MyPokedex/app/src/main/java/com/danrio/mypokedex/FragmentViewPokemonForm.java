package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentViewPokemonForm extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private RecyclerView rcvForms;
    private String idPokemon;

    public FragmentViewPokemonForm() {
        // Required empty public constructor
    }

    public static FragmentViewPokemonForm newInstance(String id) {
        FragmentViewPokemonForm fragment = new FragmentViewPokemonForm();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public String getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(String idPokemon) {
        this.idPokemon = idPokemon;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public RecyclerView getRcvForms() {
        return rcvForms;
    }

    public void setRcvForms(RecyclerView rcvForms) {
        this.rcvForms = rcvForms;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setIdPokemon(getArguments().getString("id"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pokemon_form, container, false);
        setRcvForms(v.findViewById(R.id.rcvForms));
        getRcvForms().setHasFixedSize(true);
        getRcvForms().setAdapter(new RcvPokemonFormsAdapter(getContext(), getDataBase().queryString(getContext(), true, "POKEMON_FORM AS PF INNER JOIN POKEMON AS P1 ON (PF.ORIGIN_ID = P1.ID) INNER JOIN POKEMON AS P2 ON (PF.FORM_ID = P2.ID)", new String[]{"PF.ORIGIN_ID||\";\"||PF.FORM_ID", "P2.NAME||\" - \"||PF.KIND", "PF.METHOD"}, "PF.ORIGIN_ID = ?", new String[]{getIdPokemon()}, null, null, "P2.NAME ASC", null)));
        getRcvForms().setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}