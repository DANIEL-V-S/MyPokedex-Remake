package com.danrio.mypokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ViewPokemon extends AppCompatActivity {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private ConstraintLayout constraintLayout;
    private Pokemon thisPokemon;
    private ViewPager2 viewP;
    private String id;

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ViewPager2 getViewP() {
        return viewP;
    }

    public void setViewP(ViewPager2 viewP) {
        this.viewP = viewP;
    }

    public Pokemon getThisPokemon() {
        return thisPokemon;
    }

    public void setThisPokemon(Pokemon thisPokemon) {
        this.thisPokemon = thisPokemon;
    }

    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }

    public void setConstraintLayout(ConstraintLayout constraintLayout) {
        this.constraintLayout = constraintLayout;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_link, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.linkIcon) {
            getJm().linkPokemonDialog(this, getConstraintLayout(), getDataBase(), getId(), getThisPokemon().getName());
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pokemon);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setId(getIntent().getStringExtra("id"));
        setThisPokemon(getDataBase().getPokemon(this, "ID = ?", new String[]{getId()}));
        setViewP(findViewById(R.id.viewPager));
        setConstraintLayout(findViewById(R.id.constraintLayout));

        try {
            toolbar.setTitle("#" + thisPokemon.getDex() + " - " + thisPokemon.getName());
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getViewP().setAdapter(new ViewPokemonPagerAdapter(this, getId()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, getViewP(), (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText(R.string.activity_ViewPokemon_tab_0);
                }
                break;
                case 1: {
                    tab.setText(R.string.activity_ViewPokemon_tab_1);
                }
                break;
                case 2: {
                    tab.setText(R.string.activity_ViewPokemon_tab_2);
                }
                break;
                case 3: {
                    tab.setText(R.string.activity_ViewPokemon_tab_3);
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