package com.danrio.mypokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class ViewAbility extends AppCompatActivity {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private RecyclerView rcvPokemon, rcvPokemonH;
    private ConstraintLayout constraintLayout;
    private TextView txtNome, txtDescricao;
    private String id;

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }

    public void setConstraintLayout(ConstraintLayout constraintLayout) {
        this.constraintLayout = constraintLayout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(TextView txtNome) {
        this.txtNome = txtNome;
    }

    public TextView getTxtDescricao() {
        return txtDescricao;
    }

    public void setTxtDescricao(TextView txtDescricao) {
        this.txtDescricao = txtDescricao;
    }

    public RecyclerView getRcvPokemon() {
        return rcvPokemon;
    }

    public void setRcvPokemon(RecyclerView rcvPokemon) {
        this.rcvPokemon = rcvPokemon;
    }

    public RecyclerView getRcvPokemonH() {
        return rcvPokemonH;
    }

    public void setRcvPokemonH(RecyclerView rcvPokemonH) {
        this.rcvPokemonH = rcvPokemonH;
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
            getJm().linkAbilityDialog(this, getConstraintLayout(), getDataBase(), getId(), getTxtNome().getText().toString());
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
        setContentView(R.layout.activity_view_ability);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setId(getIntent().getStringExtra("id"));
        List<List<String>> abilities = getDataBase().getAbilities(this, "ID = ?", new String[]{getId()});
        setTxtNome(findViewById(R.id.txtNome));
        setTxtDescricao(findViewById(R.id.txtDescricao));
        setRcvPokemon(findViewById(R.id.rcvPokemon));
        setRcvPokemonH(findViewById(R.id.rcvPokemonH));
        setConstraintLayout(findViewById(R.id.constraintLayout));

        try {
            if (abilities.size() == 1) {
                toolbar.setTitle(abilities.get(0).get(1));
                setSupportActionBar(toolbar);
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                getTxtNome().setText(abilities.get(0).get(1));
                getTxtDescricao().setText(abilities.get(0).get(2));
                getRcvPokemon().setHasFixedSize(false);
                getRcvPokemon().setAdapter(new RcvPokedexAdapter(this, getDataBase().getCursorValue(getDataBase().openDatabase(this), true, "POKEMON", new String[]{"ID", "NAME", "'#'||DEX"}, "ID IN (SELECT POKEMON_ID FROM POKEMON_ABILITY WHERE ABILITY_ID = ? AND HIDDEN = FALSE)", new String[]{getId()}, null, null, "DEX ASC, NAME ASC", null)));
                getRcvPokemon().setLayoutManager(new LinearLayoutManager(this));
                getRcvPokemonH().setHasFixedSize(false);
                getRcvPokemonH().setAdapter(new RcvPokedexAdapter(this, getDataBase().getCursorValue(getDataBase().openDatabase(this), true, "POKEMON", new String[]{"ID", "NAME", "'#'||DEX"}, "ID IN (SELECT POKEMON_ID FROM POKEMON_ABILITY WHERE ABILITY_ID = ? AND HIDDEN = TRUE)", new String[]{getId()}, null, null, "DEX ASC, NAME ASC", null)));
                getRcvPokemonH().setLayoutManager(new LinearLayoutManager(this));
                if (Objects.requireNonNull(getRcvPokemon().getAdapter()).getItemCount() == 0) {
                    findViewById(R.id.textNoPokemon).setVisibility(View.VISIBLE);
                }
                if (Objects.requireNonNull(getRcvPokemonH().getAdapter()).getItemCount() == 0) {
                    findViewById(R.id.textNoPokemonH).setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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