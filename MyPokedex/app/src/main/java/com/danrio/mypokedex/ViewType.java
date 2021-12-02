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

public class ViewType extends AppCompatActivity {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private RecyclerView rcvPokemon, rcvMoves;
    private ConstraintLayout constraintLayout;
    private TextView txtNome;
    private String id;

    public JavaMethods getJm() {
        return jm;
    }

    public DataBase getDataBase() {
        return dataBase;
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

    public RecyclerView getRcvPokemon() {
        return rcvPokemon;
    }

    public void setRcvPokemon(RecyclerView rcvPokemon) {
        this.rcvPokemon = rcvPokemon;
    }

    public RecyclerView getRcvMoves() {
        return rcvMoves;
    }

    public void setRcvMoves(RecyclerView rcvMoves) {
        this.rcvMoves = rcvMoves;
    }

    public TextView getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(TextView txtNome) {
        this.txtNome = txtNome;
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
            getJm().linkTypeDialog(this, getConstraintLayout(), getDataBase(), getId(), getTxtNome().getText().toString());
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
        setContentView(R.layout.activity_view_type);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setId(getIntent().getStringExtra("id"));
        List<String> thisType = getDataBase().getTypesName(this, "ID = ?", new String[]{getId()});
        setTxtNome(findViewById(R.id.txtNome));
        setRcvPokemon(findViewById(R.id.rcvPokemon));
        setRcvMoves(findViewById(R.id.rcvMoves));
        setConstraintLayout(findViewById(R.id.constraintLayout));

        try {
            toolbar.setTitle(thisType.get(0));
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getTxtNome().setText(thisType.get(0));
            getRcvPokemon().setHasFixedSize(false);
            getRcvPokemon().setAdapter(new RcvPokedexAdapter(this, getDataBase().getCursorValue(getDataBase().openDatabase(this), true, "POKEMON", new String[]{"ID", "NAME", "'#'||DEX"}, "ID IN (SELECT POKEMON_ID FROM POKEMON_TYPE WHERE TYPE_ID = ?)", new String[]{getId()}, null, null, "DEX ASC, NAME ASC", null)));
            getRcvPokemon().setLayoutManager(new LinearLayoutManager(this));
            getRcvMoves().setHasFixedSize(false);
            getRcvMoves().setAdapter(new RcvMovesAdapter(this, getDataBase().getCursorValue(getDataBase().openDatabase(this), true, "MOVE", new String[]{"ID", "NAME", "CATEGORY"}, "ID IN (SELECT MOVE_ID FROM MOVE_TYPE WHERE TYPE_ID = ?)", new String[]{getId()}, null, null, "NAME ASC, ID ASC", null)));
            getRcvMoves().setLayoutManager(new LinearLayoutManager(this));
            if (Objects.requireNonNull(getRcvPokemon().getAdapter()).getItemCount() == 0) {
                findViewById(R.id.textNoPokemon).setVisibility(View.VISIBLE);
            }
            if (Objects.requireNonNull(getRcvMoves().getAdapter()).getItemCount() == 0) {
                findViewById(R.id.textNoMove).setVisibility(View.VISIBLE);
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