package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentViewPokemonEvolution extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private RecyclerView rcvEvolutions;
    private String idPokemon;

    public FragmentViewPokemonEvolution() {
        // Required empty public constructor
    }

    public static FragmentViewPokemonEvolution newInstance(String id) {
        FragmentViewPokemonEvolution fragment = new FragmentViewPokemonEvolution();
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

    public RecyclerView getRcvEvolutions() {
        return rcvEvolutions;
    }

    public void setRcvEvolutions(RecyclerView rcvEvolutions) {
        this.rcvEvolutions = rcvEvolutions;
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
        View v = inflater.inflate(R.layout.fragment_view_pokemon_evolution, container, false);
        setRcvEvolutions(v.findViewById(R.id.rcvEvolutions));
        getRcvEvolutions().setHasFixedSize(true);
        getRcvEvolutions().setAdapter(new RcvPokemonEvolutionsAdapter(getContext(), getDataBase().queryString(getContext(), true, "POKEMON_POKEMON AS PP INNER JOIN POKEMON AS P1 ON (PP.POKEMON1_ID = P1.ID) INNER JOIN POKEMON AS P2 ON (PP.POKEMON2_ID = P2.ID)", new String[]{"PP.POKEMON1_ID||\";\"||PP.POKEMON2_ID", "\"#\"||P2.DEX||\" - \"||P2.NAME", "PP.METHOD"}, "PP.POKEMON1_ID = ?", new String[]{getIdPokemon()}, null, null, "P2.NAME ASC", null)));
        getRcvEvolutions().setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}