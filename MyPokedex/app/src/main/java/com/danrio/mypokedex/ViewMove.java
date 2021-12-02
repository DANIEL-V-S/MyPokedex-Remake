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

public class ViewMove extends AppCompatActivity {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private TextView txtNome, txtTipos, txtDescription, txtPower, txtAccuracy, txtPP, txtPriority, txtCategory;
    private ConstraintLayout constraintLayout;
    private RecyclerView rcvPokemon;
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

    public RecyclerView getRcvPokemon() {
        return rcvPokemon;
    }

    public void setRcvPokemon(RecyclerView rcvPokemon) {
        this.rcvPokemon = rcvPokemon;
    }

    public TextView getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(TextView txtNome) {
        this.txtNome = txtNome;
    }

    public TextView getTxtTipos() {
        return txtTipos;
    }

    public void setTxtTipos(TextView txtTipos) {
        this.txtTipos = txtTipos;
    }

    public TextView getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }

    public TextView getTxtPower() {
        return txtPower;
    }

    public void setTxtPower(TextView txtPower) {
        this.txtPower = txtPower;
    }

    public TextView getTxtAccuracy() {
        return txtAccuracy;
    }

    public void setTxtAccuracy(TextView txtAccuracy) {
        this.txtAccuracy = txtAccuracy;
    }

    public TextView getTxtPP() {
        return txtPP;
    }

    public void setTxtPP(TextView txtPP) {
        this.txtPP = txtPP;
    }

    public TextView getTxtPriority() {
        return txtPriority;
    }

    public void setTxtPriority(TextView txtPriority) {
        this.txtPriority = txtPriority;
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }

    public void setTxtCategory(TextView txtCategory) {
        this.txtCategory = txtCategory;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_link_unlink, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.linkIcon) {
            getJm().linkMoveDialog(this, getConstraintLayout(), getDataBase(), getId(), getTxtNome().getText().toString());
            return true;
        }
        if (id == R.id.unlinkIcon) {
            getJm().unlinkMoveDialog(this, getConstraintLayout(), getDataBase(), getId(), getTxtNome().getText().toString());
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
        setContentView(R.layout.activity_view_move);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setId(getIntent().getStringExtra("id"));
        List<List<String>> moves = getDataBase().getMoves(this, "ID = ?", new String[]{getId()});
        List<String> tipos = getDataBase().getTypesName(this, "ID IN (SELECT TYPE_ID FROM MOVE_TYPE WHERE MOVE_ID = ?)", new String[]{getId()});
        setTxtNome(findViewById(R.id.txtNome));
        setTxtTipos(findViewById(R.id.txtTipos));
        setTxtDescription(findViewById(R.id.txtDescricao));
        setTxtPower(findViewById(R.id.txtValPower));
        setTxtAccuracy(findViewById(R.id.txtValAccuracy));
        setTxtPP(findViewById(R.id.txtValPP));
        setTxtPriority(findViewById(R.id.txtValPriority));
        setTxtCategory(findViewById(R.id.txtValCategory));
        setRcvPokemon(findViewById(R.id.rcvPokemon));
        setConstraintLayout(findViewById(R.id.constraintLayout));

        try {
            String tipo = " ";
            for (int i = 0; i < tipos.size(); i++) {
                if (i > 0) {
                    tipo = tipo.concat("/");
                }
                tipo = tipo.concat(tipos.get(i));
            }
            if (moves.size() == 1) {
                toolbar.setTitle(moves.get(0).get(1));
                setSupportActionBar(toolbar);
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                getTxtTipos().setText(getString(R.string.dialog_link_type).concat(tipo));
                getTxtNome().setText(moves.get(0).get(1));
                getTxtDescription().setText(moves.get(0).get(2));
                getTxtPower().setText(moves.get(0).get(3));
                getTxtAccuracy().setText(moves.get(0).get(4).concat("%"));
                getTxtPP().setText(moves.get(0).get(5));
                getTxtPriority().setText(moves.get(0).get(6));
                getTxtCategory().setText(moves.get(0).get(7));
                getRcvPokemon().setHasFixedSize(false);
                getRcvPokemon().setAdapter(new RcvPokedexAdapter(this, getDataBase().getCursorValue(getDataBase().openDatabase(this), true, "POKEMON AS P INNER JOIN POKEMON_MOVE AS PM ON (P.ID = PM.POKEMON_ID)", new String[]{"P.ID", "'#'||P.DEX||' - '||P.NAME", "PM.METHOD"}, "PM.MOVE_ID = ?", new String[]{getId()}, null, null, "P.DEX ASC, P.NAME ASC", null)));
                getRcvPokemon().setLayoutManager(new LinearLayoutManager(this));
                if (Objects.requireNonNull(getRcvPokemon().getAdapter()).getItemCount() == 0) {
                    findViewById(R.id.textNoPokemon).setVisibility(View.VISIBLE);
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