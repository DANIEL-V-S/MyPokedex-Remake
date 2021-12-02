package com.danrio.mypokedex;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class FragmentAddPokemonType extends Fragment {
    private final DataBase dataBase = new DataBase();
    private SharedViewModelPokemon viewModelPokemon;
    private RcvPokemonTypesAdapter adapter;
    private RecyclerView rcvPokemonTypes;
    private Cursor cursor;

    public FragmentAddPokemonType() {
        // Required empty public constructor
    }

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public RcvPokemonTypesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RcvPokemonTypesAdapter adapter) {
        this.adapter = adapter;
    }

    public RecyclerView getRcvPokemonTypes() {
        return rcvPokemonTypes;
    }

    public void setRcvPokemonTypes(RecyclerView rcvPokemonType) {
        this.rcvPokemonTypes = rcvPokemonType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModelPokemon(new ViewModelProvider(requireActivity()).get(SharedViewModelPokemon.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pokemon_type, container, false);
        setCursor(listarTypes(requireContext()));
        setRcvPokemonTypes(v.findViewById(R.id.rcvPokemonTypes));
        getRcvPokemonTypes().setHasFixedSize(true);
        setAdapter(new RcvPokemonTypesAdapter(getCursor(), getViewModelPokemon()));
        getRcvPokemonTypes().setAdapter(getAdapter());
        getRcvPokemonTypes().setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getCursor().getCount() == 0) {
            try {
                Snackbar.make(this.requireView(), R.string.no_type, Snackbar.LENGTH_LONG).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Cursor listarTypes(Context context) {
        return getDataBase().getCursorValue(getDataBase().openDatabase(context), false, "TYPE", new String[]{"ID", "NAME"}, null, null, null, null, "NAME ASC", null);
    }
}