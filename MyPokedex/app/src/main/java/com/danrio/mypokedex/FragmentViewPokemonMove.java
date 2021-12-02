package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentViewPokemonMove extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private RecyclerView rcvMoves;
    private String idPokemon;

    public FragmentViewPokemonMove() {
        // Required empty public constructor
    }

    public static FragmentViewPokemonMove newInstance(String id) {
        FragmentViewPokemonMove fragment = new FragmentViewPokemonMove();
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

    public RecyclerView getRcvMoves() {
        return rcvMoves;
    }

    public void setRcvMoves(RecyclerView rcvMoves) {
        this.rcvMoves = rcvMoves;
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
        View v = inflater.inflate(R.layout.fragment_view_pokemon_move, container, false);
        setRcvMoves(v.findViewById(R.id.rcvMoves));
        getRcvMoves().setHasFixedSize(true);
        getRcvMoves().setAdapter(new RcvPokemonMovesAdapter(getContext(), getDataBase().queryString(getContext(), true, "POKEMON_MOVE AS PM INNER JOIN MOVE AS M ON (PM.MOVE_ID = M.ID)", new String[]{"PM.POKEMON_ID||\";\"||PM.MOVE_ID||\";\"||PM.METHOD", "M.NAME||\" - \"||M.CATEGORY", "PM.METHOD"}, "PM.POKEMON_ID = ?", new String[]{getIdPokemon()}, null, null, "M.NAME ASC", null)));
        getRcvMoves().setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}