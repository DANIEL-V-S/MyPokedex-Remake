package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class FragmentAddPokemonStats extends Fragment {
    private TextInputEditText edtHp, edtAtk, edtDef, edtSpAtk, edtSpDef, edtSpeed;
    private SharedViewModelPokemon viewModelPokemon;

    public FragmentAddPokemonStats() {
        // Required empty public constructor
    }

    public TextInputEditText getEdtHp() {
        return edtHp;
    }

    public void setEdtHp(TextInputEditText edtHp) {
        this.edtHp = edtHp;
    }

    public TextInputEditText getEdtAtk() {
        return edtAtk;
    }

    public void setEdtAtk(TextInputEditText edtAtk) {
        this.edtAtk = edtAtk;
    }

    public TextInputEditText getEdtDef() {
        return edtDef;
    }

    public void setEdtDef(TextInputEditText edtDef) {
        this.edtDef = edtDef;
    }

    public TextInputEditText getEdtSpAtk() {
        return edtSpAtk;
    }

    public void setEdtSpAtk(TextInputEditText edtSpAtk) {
        this.edtSpAtk = edtSpAtk;
    }

    public TextInputEditText getEdtSpDef() {
        return edtSpDef;
    }

    public void setEdtSpDef(TextInputEditText edtSpDef) {
        this.edtSpDef = edtSpDef;
    }

    public TextInputEditText getEdtSpeed() {
        return edtSpeed;
    }

    public void setEdtSpeed(TextInputEditText edtSpeed) {
        this.edtSpeed = edtSpeed;
    }

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModelPokemon(new ViewModelProvider(requireActivity()).get(SharedViewModelPokemon.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pokemon_stats, container, false);
        setEdtHp(v.findViewById(R.id.edtPokemonBaseHP));
        setEdtAtk(v.findViewById(R.id.edtPokemonBaseATK));
        setEdtDef(v.findViewById(R.id.edtPokemonBaseDEF));
        setEdtSpAtk(v.findViewById(R.id.edtPokemonBaseSPATK));
        setEdtSpDef(v.findViewById(R.id.edtPokemonBaseSPDEF));
        setEdtSpeed(v.findViewById(R.id.edtPokemonBaseSPEED));
        getEdtHp().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtHp().getText()).toString();
                getViewModelPokemon().setBase_hp(stat.equals("") ? "0" : stat);
            }
        });
        getEdtAtk().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtAtk().getText()).toString();
                getViewModelPokemon().setBase_atk(stat.equals("") ? "0" : stat);
            }
        });
        getEdtDef().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtDef().getText()).toString();
                getViewModelPokemon().setBase_def(stat.equals("") ? "0" : stat);
            }
        });
        getEdtSpAtk().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtSpAtk().getText()).toString();
                getViewModelPokemon().setBase_spatk(stat.equals("") ? "0" : stat);
            }
        });
        getEdtSpDef().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtSpDef().getText()).toString();
                getViewModelPokemon().setBase_spdef(stat.equals("") ? "0" : stat);
            }
        });
        getEdtSpeed().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String stat = Objects.requireNonNull(getEdtSpeed().getText()).toString();
                getViewModelPokemon().setBase_speed(stat.equals("") ? "0" : stat);
            }
        });
        return v;
    }
}