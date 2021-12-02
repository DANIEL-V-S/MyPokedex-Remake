package com.danrio.mypokedex;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FragmentMyPokedex extends Fragment {
    private final DataBase dataBase = new DataBase();
    private FloatingActionButton fabAdicionar;
    private RcvPokedexAdapter adapter;
    private RecyclerView rcvPokedex;
    private Cursor cursor;

    public RcvPokedexAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RcvPokedexAdapter adapter) {
        this.adapter = adapter;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public FloatingActionButton getFabAdicionar() {
        return fabAdicionar;
    }

    public void setFabAdicionar(FloatingActionButton fabAdicionar) {
        this.fabAdicionar = fabAdicionar;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public RecyclerView getRcvPokedex() {
        return rcvPokedex;
    }

    public void setRcvPokedex(RecyclerView rcvPokedex) {
        this.rcvPokedex = rcvPokedex;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_pokedex, container, false);
        setCursor(listarPokedex(requireContext()));
        setFabAdicionar(v.findViewById(R.id.fabAdicionar));
        setRcvPokedex(v.findViewById(R.id.rcvPokedex));
        getRcvPokedex().setHasFixedSize(true);
        setAdapter(new RcvPokedexAdapter(requireContext(), getCursor()));
        getRcvPokedex().setAdapter(getAdapter());
        getRcvPokedex().setLayoutManager(new LinearLayoutManager(getContext()));
        getFabAdicionar().setOnClickListener(v1 -> {
            Intent intent = new Intent(requireContext(), AddPokemon.class);
            requireContext().startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getCursor().getCount() == 0) {
            Snackbar.make(view, R.string.no_pokemon, Snackbar.LENGTH_LONG).show();
        }
    }

    private Cursor listarPokedex(Context context) {
        return getDataBase().getCursorValue(getDataBase().openDatabase(context), false, "POKEMON", new String[]{"ID", "NAME", "'#'||DEX"}, null, null, null, null, "DEX ASC, NAME ASC", null);
    }
}