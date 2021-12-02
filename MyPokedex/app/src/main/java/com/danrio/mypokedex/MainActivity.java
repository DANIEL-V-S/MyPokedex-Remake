package com.danrio.mypokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final DataBase dataBase = new DataBase();
    private DrawerLayout drawer = null;
    private String title = "";

    public DataBase getDataBase() {
        return dataBase;
    }

    public String getStringTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.navHome));
        setDrawer(findViewById(R.id.drawer_layout));
        NavigationView navView = findViewById(R.id.navigation);
        TextView txtSubtitle = navView.getHeaderView(0).findViewById(R.id.navSubtitle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, getDrawer(), toolbar, R.string.open_drawer, R.string.close_drawer);
        getDrawer().addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(v -> getDrawer().openDrawer(GravityCompat.START));
        navView.setNavigationItemSelectedListener(item -> {
            itemSelectDrawer(toolbar, item);
            return true;
        });

        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getString("toolbarTitle"));
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
            getDataBase().create(this);
        }
        toolbar.setTitle(getStringTitle());
        List<String> settings = getDataBase().getSettings(this);
        txtSubtitle.setText(settings.get(0));
    }

    private void itemSelectDrawer(Toolbar toolbar, MenuItem item) {
        item.setChecked(true);
        if (item.getItemId() == R.id.navHome) {
            setTitle(getString(R.string.navHome));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
        } else if (item.getItemId() == R.id.navMyPokedex) {
            setTitle(getString(R.string.navMyPokedex));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMyPokedex()).commit();
        } else if (item.getItemId() == R.id.navMoves) {
            setTitle(getString(R.string.navMoves));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMove()).commit();
        } else if (item.getItemId() == R.id.navAbility) {
            setTitle(getString(R.string.navAbilities));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbility()).commit();
        } else if (item.getItemId() == R.id.navType) {
            setTitle(getString(R.string.navTypes));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentType()).commit();
        } else if (item.getItemId() == R.id.navSettings) {
            setTitle(getString(R.string.navSettings));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSettings()).commit();
        } else if (item.getItemId() == R.id.navAbout) {
            setTitle(getString(R.string.navAbout));
            toolbar.setTitle(getStringTitle());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbout()).commit();
        }
        getDrawer().closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (getDrawer() != null && getDrawer().isDrawerOpen(GravityCompat.START)) {
            getDrawer().closeDrawers();
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this, R.style.EditAlertDialog)
                    .setIcon(R.drawable.info_icon).setTitle(R.string.exit_app_warning_title)
                    .setMessage(R.string.exit_app_warning_message)
                    .setNeutralButton(R.string.no, null)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }).create().show();
        }
    }

    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("toolbarTitle", getStringTitle());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}