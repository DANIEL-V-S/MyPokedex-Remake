package com.danrio.mypokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Objects;

public class AddPokemon extends AppCompatActivity {
    private final JavaMethods jm = new JavaMethods();
    private final DataBase dataBase = new DataBase();
    private SharedViewModelPokemon viewModelPokemon;
    private List<String> nameList;
    private ViewPager2 viewP;
    private Pokemon pokemon;

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
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
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saveIcon) {
            getViewP().requestFocus();
            if (!getNameList().contains(getViewModelPokemon().getPokemonClass().getName())) {
                getJm().addPokemon(this, getViewP(), getDataBase(), getViewModelPokemon().getPokemonClass(), getViewModelPokemon().getTipos().getValue(), this);
            } else {
                Snackbar.make(getViewP(), R.string.activity_addPokemon_failed_2, Snackbar.LENGTH_LONG).show();
            }
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
        setContentView(R.layout.activity_add_pokemon);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.menu_save);
        setPokemon(getJm().blankPokemon(this));
        setViewModelPokemon(new ViewModelProvider(this).get(SharedViewModelPokemon.class));
        setNameList(getDataBase().getPokemonNameList(this, null, null));
        if (savedInstanceState == null || getViewModelPokemon().getPokemon() == null) {
            getViewModelPokemon().setPokemon(getPokemon());
        }

        setViewP(findViewById(R.id.viewPager));
        getViewP().setAdapter(new AddPokemonPagerAdapter(this));
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