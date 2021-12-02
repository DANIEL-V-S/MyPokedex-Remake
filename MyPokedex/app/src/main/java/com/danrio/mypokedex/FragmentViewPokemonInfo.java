package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentViewPokemonInfo extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private TextView txtDex, txtName, txtSpecies, txtCategory, txtRegion, txtTipos, txtHabilidades, txtHabilidadesHidden, txtHeight, txtWeight, txtGender, txtDescricao;
    private TextView txtHP, txtValHP, txtAtk, txtValAtk, txtDef, txtValDef, txtSpAtk, txtValSpAtk, txtSpDef, txtValSpDef, txtSpeed, txtValSpeed;
    private ConstraintLayout constraintLayout;
    private FloatingActionButton fabSwap;
    private ImageView imgPhoto;
    private String idPokemon;
    private boolean isShiny;

    public FragmentViewPokemonInfo() {
        // Required empty public constructor
    }

    public static FragmentViewPokemonInfo newInstance(String id) {
        FragmentViewPokemonInfo fragment = new FragmentViewPokemonInfo();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }

    public void setConstraintLayout(ConstraintLayout constraintLayout) {
        this.constraintLayout = constraintLayout;
    }

    public TextView getTxtDex() {
        return txtDex;
    }

    public void setTxtDex(TextView txtDex) {
        this.txtDex = txtDex;
    }

    public TextView getTxtName() {
        return txtName;
    }

    public void setTxtName(TextView txtName) {
        this.txtName = txtName;
    }

    public TextView getTxtSpecies() {
        return txtSpecies;
    }

    public void setTxtSpecies(TextView txtSpecies) {
        this.txtSpecies = txtSpecies;
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(TextView txtCategory) {
        this.txtCategory = txtCategory;
    }

    public TextView getTxtRegion() {
        return txtRegion;
    }

    public void setTxtRegion(TextView txtRegion) {
        this.txtRegion = txtRegion;
    }

    public FloatingActionButton getFabSwap() {
        return fabSwap;
    }

    public void setFabSwap(FloatingActionButton fabSwap) {
        this.fabSwap = fabSwap;
    }

    public ImageView getImgPhoto() {
        return imgPhoto;
    }

    public void setImgPhoto(ImageView imgPhoto) {
        this.imgPhoto = imgPhoto;
    }

    public TextView getTxtHeight() {
        return txtHeight;
    }

    public void setTxtHeight(TextView txtHeight) {
        this.txtHeight = txtHeight;
    }

    public TextView getTxtWeight() {
        return txtWeight;
    }

    public void setTxtWeight(TextView txtWeight) {
        this.txtWeight = txtWeight;
    }

    public TextView getTxtTipos() {
        return txtTipos;
    }

    public void setTxtTipos(TextView txtTipos) {
        this.txtTipos = txtTipos;
    }

    public TextView getTxtHabilidades() {
        return txtHabilidades;
    }

    public void setTxtHabilidades(TextView txtHabilidades) {
        this.txtHabilidades = txtHabilidades;
    }

    public TextView getTxtHabilidadesHidden() {
        return txtHabilidadesHidden;
    }

    public void setTxtHabilidadesHidden(TextView txtHabilidadesHidden) {
        this.txtHabilidadesHidden = txtHabilidadesHidden;
    }

    public TextView getTxtGender() {
        return txtGender;
    }

    public void setTxtGender(TextView txtGender) {
        this.txtGender = txtGender;
    }

    public TextView getTxtDescricao() {
        return txtDescricao;
    }

    public void setTxtDescricao(TextView txtDescricao) {
        this.txtDescricao = txtDescricao;
    }

    public TextView getTxtHP() {
        return txtHP;
    }

    public void setTxtHP(TextView txtHP) {
        this.txtHP = txtHP;
    }

    public TextView getTxtValHP() {
        return txtValHP;
    }

    public void setTxtValHP(TextView txtValHP) {
        this.txtValHP = txtValHP;
    }

    public TextView getTxtAtk() {
        return txtAtk;
    }

    public void setTxtAtk(TextView txtAtk) {
        this.txtAtk = txtAtk;
    }

    public TextView getTxtValAtk() {
        return txtValAtk;
    }

    public void setTxtValAtk(TextView txtValAtk) {
        this.txtValAtk = txtValAtk;
    }

    public TextView getTxtDef() {
        return txtDef;
    }

    public void setTxtDef(TextView txtDef) {
        this.txtDef = txtDef;
    }

    public TextView getTxtValDef() {
        return txtValDef;
    }

    public void setTxtValDef(TextView txtValDef) {
        this.txtValDef = txtValDef;
    }

    public TextView getTxtSpAtk() {
        return txtSpAtk;
    }

    public void setTxtSpAtk(TextView txtSpAtk) {
        this.txtSpAtk = txtSpAtk;
    }

    public TextView getTxtValSpAtk() {
        return txtValSpAtk;
    }

    public void setTxtValSpAtk(TextView txtValSpAtk) {
        this.txtValSpAtk = txtValSpAtk;
    }

    public TextView getTxtSpDef() {
        return txtSpDef;
    }

    public void setTxtSpDef(TextView txtSpDef) {
        this.txtSpDef = txtSpDef;
    }

    public TextView getTxtValSpDef() {
        return txtValSpDef;
    }

    public void setTxtValSpDef(TextView txtValSpDef) {
        this.txtValSpDef = txtValSpDef;
    }

    public TextView getTxtSpeed() {
        return txtSpeed;
    }

    public void setTxtSpeed(TextView txtSpeed) {
        this.txtSpeed = txtSpeed;
    }

    public TextView getTxtValSpeed() {
        return txtValSpeed;
    }

    public void setTxtValSpeed(TextView txtValSpeed) {
        this.txtValSpeed = txtValSpeed;
    }

    public boolean isShiny() {
        return isShiny;
    }

    public void setShiny(boolean shiny) {
        isShiny = shiny;
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

    private void setPhoto(Pokemon thisPokemon) {
        try {
            byte[] photo = isShiny() ? thisPokemon.getShiny_photo() : thisPokemon.getRegular_photo();
            if (photo != null) {
                getImgPhoto().setImageBitmap(getJm().getBitmapFromByteArray(photo));
            } else {
                getImgPhoto().setImageBitmap(null);
                getImgPhoto().setImageResource(R.drawable.photo_icon);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void swapPhoto(Pokemon thisPokemon) {
        try {
            byte[] photo = isShiny() ? thisPokemon.getRegular_photo() : thisPokemon.getShiny_photo();
            setShiny(!isShiny());
            if (photo != null) {
                getImgPhoto().setImageBitmap(getJm().getBitmapFromByteArray(photo));
            } else {
                getImgPhoto().setImageBitmap(null);
                getImgPhoto().setImageResource(R.drawable.photo_icon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        View v = inflater.inflate(R.layout.fragment_view_pokemon_info, container, false);
        setConstraintLayout(v.findViewById(R.id.constraintLayout));
        setImgPhoto(v.findViewById(R.id.imgPhoto));
        setFabSwap(v.findViewById(R.id.fabSwap));
        setTxtDex(v.findViewById(R.id.txtDex));
        setTxtName(v.findViewById(R.id.txtName));
        setTxtSpecies(v.findViewById(R.id.txtSpecies));
        setTxtCategory(v.findViewById(R.id.txtCategory));
        setTxtRegion(v.findViewById(R.id.txtRegion));
        setTxtHeight(v.findViewById(R.id.txtValHeight));
        setTxtWeight(v.findViewById(R.id.txtValWeight));
        setTxtTipos(v.findViewById(R.id.txtTipos));
        setTxtGender(v.findViewById(R.id.txtGender));
        setTxtDescricao(v.findViewById(R.id.txtDescricao));
        setTxtHabilidades(v.findViewById(R.id.txtHabilidades));
        setTxtHabilidadesHidden(v.findViewById(R.id.txtHabilidadesHidden));
        setTxtHP(v.findViewById(R.id.txtHP));
        setTxtValHP(v.findViewById(R.id.txtValHP));
        setTxtAtk(v.findViewById(R.id.txtAtk));
        setTxtValAtk(v.findViewById(R.id.txtValAtk));
        setTxtDef(v.findViewById(R.id.txtDef));
        setTxtValDef(v.findViewById(R.id.txtValDef));
        setTxtSpAtk(v.findViewById(R.id.txtSpAtk));
        setTxtValSpAtk(v.findViewById(R.id.txtValSpAtk));
        setTxtSpDef(v.findViewById(R.id.txtSpDef));
        setTxtValSpDef(v.findViewById(R.id.txtValSpDef));
        setTxtSpeed(v.findViewById(R.id.txtSpeed));
        setTxtValSpeed(v.findViewById(R.id.txtValSpeed));
        try {
            setShiny(false);
            Pokemon thisPokemon = getDataBase().getPokemon(getContext(), "ID = ?", new String[]{getIdPokemon()});
            List<String> tipos = getDataBase().getTypesName(getContext(), "ID IN (SELECT TYPE_ID FROM POKEMON_TYPE WHERE POKEMON_ID = ?)", new String[]{getIdPokemon()});
            List<String> habilidades = getDataBase().getAbilitiesName(getContext(), "ID IN (SELECT ABILITY_ID FROM POKEMON_ABILITY WHERE POKEMON_ID = ? AND HIDDEN = FALSE)", new String[]{getIdPokemon()});
            List<String> habilidadesHidden = getDataBase().getAbilitiesName(getContext(), "ID IN (SELECT ABILITY_ID FROM POKEMON_ABILITY WHERE POKEMON_ID = ? AND HIDDEN = TRUE)", new String[]{getIdPokemon()});
            String tiposString = "", habilidadesString = "", habilidadesHiddenString = "";
            for (int i = 0; i < tipos.size(); i++) {
                tiposString = i == 0 ? tiposString.concat(tipos.get(i)) : tiposString.concat("/" + tipos.get(i));
            }
            for (int i = 0; i < habilidades.size(); i++) {
                habilidadesString = i == 0 ? habilidadesString.concat(habilidades.get(i)) : habilidadesString.concat("/" + habilidades.get(i));
            }
            for (int i = 0; i < habilidadesHidden.size(); i++) {
                habilidadesHiddenString = i == 0 ? habilidadesHiddenString.concat(habilidadesHidden.get(i)) : habilidadesHiddenString.concat("/" + habilidadesHidden.get(i));
            }
            tiposString = tipos.size() == 1 ? getString(R.string.activity_ViewPokemon_txtTypes).concat(" " + tiposString) : getString(R.string.activity_ViewPokemon_txtTypes_Plural).concat(" " + tiposString);
            habilidadesString = habilidades.size() == 1 ? getString(R.string.activity_ViewPokemon_txtAbilities).concat(" " + habilidadesString) : getString(R.string.activity_ViewPokemon_txtAbilities_Plural).concat(" " + habilidadesString);
            habilidadesHiddenString = habilidadesHidden.size() == 1 ? getString(R.string.activity_ViewPokemon_txtHiddenAbilities).concat(" " + habilidadesHiddenString) : getString(R.string.activity_ViewPokemon_txtHiddenAbilities_Plural).concat(" " + habilidadesHiddenString);
            getTxtDex().setText("#".concat(String.valueOf(thisPokemon.getDex())));
            getTxtName().setText(thisPokemon.getName());
            getTxtSpecies().setText(getString(R.string.activity_ViewPokemon_species).concat(" " + thisPokemon.getSpecies()));
            getTxtCategory().setText(getString(R.string.activity_ViewPokemon_category).concat(" " + thisPokemon.getCategory()));
            getTxtRegion().setText(getString(R.string.activity_ViewPokemon_region).concat(" " + thisPokemon.getRegion()));
            getFabSwap().setOnClickListener(view -> swapPhoto(thisPokemon));
            setPhoto(thisPokemon);
            getTxtHeight().setText(String.valueOf(thisPokemon.getHeight()).concat(" m"));
            getTxtWeight().setText(String.valueOf(thisPokemon.getWeight()).concat(" kg"));
            getTxtTipos().setText(tiposString.equals(getString(R.string.activity_ViewPokemon_txtTypes_Plural).concat(" ")) ? getString(R.string.activity_ViewPokemon_txtTypes_No_Type) : tiposString);
            getTxtHabilidades().setText(habilidadesString.equals(getString(R.string.activity_ViewPokemon_txtAbilities_Plural).concat(" ")) ? getString(R.string.activity_ViewPokemon_txtAbilities_No_Type) : habilidadesString);
            getTxtHabilidadesHidden().setText(habilidadesHiddenString.equals(getString(R.string.activity_ViewPokemon_txtHiddenAbilities_Plural).concat(" ")) ? getString(R.string.activity_ViewPokemon_txtHiddenAbilities_No_Type) : habilidadesHiddenString);
            getTxtGender().setText(thisPokemon.getGender());
            getTxtDescricao().setText(thisPokemon.getDescription());
            String[] stats = getResources().getStringArray(R.array.activity_ViewPokemon_txtStats);
            getTxtHP().setText(stats[0]);
            getTxtAtk().setText(stats[1]);
            getTxtDef().setText(stats[2]);
            getTxtSpAtk().setText(stats[3]);
            getTxtSpDef().setText(stats[4]);
            getTxtSpeed().setText(stats[5]);
            getTxtValHP().setText(String.valueOf(thisPokemon.getBase_hp()));
            getTxtValAtk().setText(String.valueOf(thisPokemon.getBase_atk()));
            getTxtValDef().setText(String.valueOf(thisPokemon.getBase_def()));
            getTxtValSpAtk().setText(String.valueOf(thisPokemon.getBase_spatk()));
            getTxtValSpDef().setText(String.valueOf(thisPokemon.getBase_spdef()));
            getTxtValSpeed().setText(String.valueOf(thisPokemon.getBase_speed()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return v;
    }
}