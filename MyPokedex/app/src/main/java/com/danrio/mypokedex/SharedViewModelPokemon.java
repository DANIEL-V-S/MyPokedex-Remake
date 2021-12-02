package com.danrio.mypokedex;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SharedViewModelPokemon extends ViewModel {
    private MutableLiveData<Pokemon> pokemon = new MutableLiveData<>(null);
    private MutableLiveData<List<Integer>> tipos = new MutableLiveData<>(new ArrayList<>());

    public LiveData<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = new MutableLiveData<>(pokemon);
    }

    public Pokemon getPokemonClass() {
        return Objects.requireNonNull(getPokemon().getValue());
    }

    public void setDex(String dex) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setDex(Integer.parseInt(dex));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setName(String name) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setName(name);
        pokemon.setValue(pkmn);
    }

    public void setSpecies(String species) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setSpecies(species);
        pokemon.setValue(pkmn);
    }

    public void setCategory(String category) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setCategory(category);
        pokemon.setValue(pkmn);
    }

    public void setRegion(String region) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setRegion(region);
        pokemon.setValue(pkmn);
    }

    public void setHeight(String height) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setHeight(Double.parseDouble(height));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setWeight(String weight) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setWeight(Double.parseDouble(weight));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setGender(String gender) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setGender(gender);
        pokemon.setValue(pkmn);
    }

    public void setDescription(String description) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setDescription(description);
        pokemon.setValue(pkmn);
    }

    public void setBase_hp(String hp) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_hp(Integer.parseInt(hp));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBase_atk(String atk) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_atk(Integer.parseInt(atk));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBase_def(String def) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_def(Integer.parseInt(def));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBase_spatk(String spatk) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_spatk(Integer.parseInt(spatk));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBase_spdef(String spdef) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_spdef(Integer.parseInt(spdef));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setBase_speed(String speed) {
        try {
            Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
            pkmn.setBase_speed(Integer.parseInt(speed));
            pokemon.setValue(pkmn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRegular_photo(byte[] regular_photo) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setRegular_photo(regular_photo);
        pokemon.setValue(pkmn);
    }

    public void setShiny_photo(byte[] shiny_photo) {
        Pokemon pkmn = Objects.requireNonNull(getPokemon().getValue());
        pkmn.setShiny_photo(shiny_photo);
        pokemon.setValue(pkmn);
    }

    public LiveData<List<Integer>> getTipos() {
        if (tipos == null) {
            tipos = new MutableLiveData<>(new ArrayList<>());
        }
        return tipos;
    }

    public void setTipos(List<Integer> tipos) {
        this.tipos = new MutableLiveData<>(tipos);
    }

    public void addTipo(int idTipo) {
        List<Integer> select = Objects.requireNonNull(getTipos().getValue());
        if (!select.contains(idTipo)) {
            select.add(idTipo);
            tipos.setValue(select);
        }
    }

    public void removeTipo(int pos) {
        Objects.requireNonNull(getTipos().getValue()).remove(getTipos().getValue().indexOf(pos));
    }
}
