package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class FragmentEditPokemonGeneral extends Fragment {
    private TextInputEditText edtPokemonDex, edtPokemonName, edtPokemonSpecies, edtPokemonCategory, edtPokemonRegion, edtPokemonHeight, edtPokemonWeight, edtPokemonGender, edtPokemonDescription;
    private SharedViewModelPokemon viewModelPokemon;

    public FragmentEditPokemonGeneral() {
        // Required empty public constructor
    }

    public TextInputEditText getEdtPokemonDex() {
        return edtPokemonDex;
    }

    public void setEdtPokemonDex(TextInputEditText edtPokemonDex) {
        this.edtPokemonDex = edtPokemonDex;
    }

    public TextInputEditText getEdtPokemonName() {
        return edtPokemonName;
    }

    public void setEdtPokemonName(TextInputEditText edtPokemonName) {
        this.edtPokemonName = edtPokemonName;
    }

    public TextInputEditText getEdtPokemonSpecies() {
        return edtPokemonSpecies;
    }

    public void setEdtPokemonSpecies(TextInputEditText edtPokemonSpecies) {
        this.edtPokemonSpecies = edtPokemonSpecies;
    }

    public TextInputEditText getEdtPokemonCategory() {
        return edtPokemonCategory;
    }

    public void setEdtPokemonCategory(TextInputEditText edtPokemonCategory) {
        this.edtPokemonCategory = edtPokemonCategory;
    }

    public TextInputEditText getEdtPokemonRegion() {
        return edtPokemonRegion;
    }

    public void setEdtPokemonRegion(TextInputEditText edtPokemonRegion) {
        this.edtPokemonRegion = edtPokemonRegion;
    }

    public TextInputEditText getEdtPokemonHeight() {
        return edtPokemonHeight;
    }

    public void setEdtPokemonHeight(TextInputEditText edtPokemonHeight) {
        this.edtPokemonHeight = edtPokemonHeight;
    }

    public TextInputEditText getEdtPokemonWeight() {
        return edtPokemonWeight;
    }

    public void setEdtPokemonWeight(TextInputEditText edtPokemonWeight) {
        this.edtPokemonWeight = edtPokemonWeight;
    }

    public TextInputEditText getEdtPokemonGender() {
        return edtPokemonGender;
    }

    public void setEdtPokemonGender(TextInputEditText edtPokemonGender) {
        this.edtPokemonGender = edtPokemonGender;
    }

    public TextInputEditText getEdtPokemonDescription() {
        return edtPokemonDescription;
    }

    public void setEdtPokemonDescription(TextInputEditText edtPokemonDescription) {
        this.edtPokemonDescription = edtPokemonDescription;
    }

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    private void setFields(View v) {
        setEdtPokemonDex(v.findViewById(R.id.edtPokemonDex));
        setEdtPokemonName(v.findViewById(R.id.edtPokemonName));
        setEdtPokemonSpecies(v.findViewById(R.id.edtPokemonSpecies));
        setEdtPokemonCategory(v.findViewById(R.id.edtPokemonCategory));
        setEdtPokemonRegion(v.findViewById(R.id.edtPokemonRegion));
        setEdtPokemonHeight(v.findViewById(R.id.edtPokemonHeight));
        setEdtPokemonWeight(v.findViewById(R.id.edtPokemonWeight));
        setEdtPokemonGender(v.findViewById(R.id.edtPokemonGender));
        setEdtPokemonDescription(v.findViewById(R.id.edtPokemonDescription));
    }

    private void setValues() {
        getEdtPokemonDex().setText(String.valueOf(getViewModelPokemon().getPokemonClass().getDex()));
        getEdtPokemonName().setText(getViewModelPokemon().getPokemonClass().getName());
        getEdtPokemonSpecies().setText(getViewModelPokemon().getPokemonClass().getSpecies());
        getEdtPokemonCategory().setText(getViewModelPokemon().getPokemonClass().getCategory());
        getEdtPokemonRegion().setText(getViewModelPokemon().getPokemonClass().getRegion());
        getEdtPokemonHeight().setText(String.valueOf(getViewModelPokemon().getPokemonClass().getHeight()));
        getEdtPokemonWeight().setText(String.valueOf(getViewModelPokemon().getPokemonClass().getWeight()));
        getEdtPokemonGender().setText(getViewModelPokemon().getPokemonClass().getGender());
        getEdtPokemonDescription().setText(getViewModelPokemon().getPokemonClass().getDescription());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModelPokemon(new ViewModelProvider(requireActivity()).get(SharedViewModelPokemon.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pokemon_general, container, false);
        setFields(v);
        setValues();
        getEdtPokemonDex().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String general = Objects.requireNonNull(getEdtPokemonDex().getText()).toString();
                getViewModelPokemon().setDex(general.equals("") ? "0" : general);
            }
        });
        getEdtPokemonName().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setName(Objects.requireNonNull(getEdtPokemonName().getText()).toString());
            }
        });
        getEdtPokemonSpecies().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setSpecies(Objects.requireNonNull(getEdtPokemonSpecies().getText()).toString());
            }
        });
        getEdtPokemonCategory().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setCategory(Objects.requireNonNull(getEdtPokemonCategory().getText()).toString());
            }
        });
        getEdtPokemonRegion().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setRegion(Objects.requireNonNull(getEdtPokemonRegion().getText()).toString());
            }
        });
        getEdtPokemonHeight().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String general = Objects.requireNonNull(getEdtPokemonHeight().getText()).toString();
                getViewModelPokemon().setHeight(general.equals("") ? "0" : general);
            }
        });
        getEdtPokemonWeight().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                String general = Objects.requireNonNull(getEdtPokemonWeight().getText()).toString();
                getViewModelPokemon().setWeight(general.equals("") ? "0" : general);
            }
        });
        getEdtPokemonGender().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setGender(Objects.requireNonNull(getEdtPokemonGender().getText()).toString());
            }
        });
        getEdtPokemonDescription().setOnFocusChangeListener((v1, hasFocus) -> {
            if (!hasFocus) {
                getViewModelPokemon().setDescription(Objects.requireNonNull(getEdtPokemonDescription().getText()).toString());
            }
        });
        return v;
    }
}
