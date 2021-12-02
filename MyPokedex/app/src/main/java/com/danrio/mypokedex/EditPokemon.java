package com.danrio.mypokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

public class EditPokemon extends AppCompatActivity {
    private final JavaMethods jm = new JavaMethods();
    private final DataBase dataBase = new DataBase();
    private SharedViewModelPokemon viewModelPokemon;
    private List<String> nameList;
    private List<Integer> tipos;
    private ViewPager2 viewP;
    private Pokemon pokemon;
    private int id;

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    public List<Integer> getTipos() {
        return tipos;
    }

    public void setTipos(List<Integer> tipos) {
        this.tipos = tipos;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public ViewPager2 getViewP() {
        return viewP;
    }

    public void setViewP(ViewPager2 viewP) {
        this.viewP = viewP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_and_reset, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saveIcon) {
            getViewP().requestFocus();
            if (!getNameList().contains(getViewModelPokemon().getPokemonClass().getName())) {
                getJm().editPokemon(this, getViewP(), getDataBase(), getViewModelPokemon().getPokemonClass(), getViewModelPokemon().getTipos().getValue(), this);
            } else {
                Snackbar.make(getViewP(), R.string.activity_addPokemon_failed_2, Snackbar.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.resetIcon) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.EditAlertDialog);
            builder.setTitle(R.string.activity_updatePokemon_reset_title).setIcon(R.drawable.reset_icon).setMessage(R.string.activity_updatePokemon_reset_message);
            builder.setNeutralButton(R.string.no, null).setPositiveButton(R.string.yes, (dialog, which) -> {
                Intent intent = new Intent(this, EditPokemon.class);
                intent.putExtra("id", String.valueOf(getId()));
                startActivity(intent);
            }).create().show();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);

        try {
            setId(Integer.parseInt(getIntent().getStringExtra("id")));
        } catch (Exception ex) {
            ex.printStackTrace();
            setId(-1);
        }
        if (getId() < 1) {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.menu_save);
        setPokemon(getDataBase().getPokemon(this, "ID = ?", new String[]{String.valueOf(getId())}));
        setTipos(getDataBase().getIntPokemonTypes(this, "POKEMON_ID = ?", new String[]{String.valueOf(getId())}));
        setViewModelPokemon(new ViewModelProvider(this).get(SharedViewModelPokemon.class));
        setNameList(getDataBase().getPokemonNameList(this, "NAME <> ?", new String[]{getPokemon().getName()}));
        if (savedInstanceState == null || getViewModelPokemon().getPokemon() == null) {
            getViewModelPokemon().setPokemon(getPokemon());
            getViewModelPokemon().setTipos(getTipos());
        }

        setViewP(findViewById(R.id.viewPager));
        getViewP().setAdapter(new EditPokemonPagerAdapter(this));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, getViewP(), (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText(R.string.activity_addPokemon_tab_0);
                }
                break;
                case 1: {
                    tab.setText(R.string.activity_addPokemon_tab_1);
                }
                break;
                case 2: {
                    tab.setText(R.string.activity_addPokemon_tab_2);
                }
                break;
                case 3: {
                    tab.setText(R.string.activity_addPokemon_tab_3);
                }
                break;
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this, R.style.EditAlertDialog)
                .setIcon(R.drawable.info_icon).setTitle(R.string.go_back_warning_title)
                .setMessage(R.string.go_back_warning_message)
                .setNeutralButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }).create().show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}