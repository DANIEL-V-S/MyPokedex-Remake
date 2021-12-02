package com.danrio.mypokedex;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FragmentAbility extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private FloatingActionButton fabAdicionar;
    private RcvAbilitiesAdapter adapter;
    private RecyclerView rcvAbilities;
    private Cursor cursor;

    public RcvAbilitiesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RcvAbilitiesAdapter adapter) {
        this.adapter = adapter;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public FloatingActionButton getFabAdicionar() {
        return fabAdicionar;
    }

    public void setFabAdicionar(FloatingActionButton fabAdicionar) {
        this.fabAdicionar = fabAdicionar;
    }

    public RecyclerView getRcvAbilities() {
        return rcvAbilities;
    }

    public void setRcvAbilities(RecyclerView rcvAbilities) {
        this.rcvAbilities = rcvAbilities;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ability, container, false);
        setCursor(listarAbilities(requireContext()));
        setFabAdicionar(v.findViewById(R.id.fabAdicionar));
        setRcvAbilities(v.findViewById(R.id.rcvAbilities));
        getRcvAbilities().setHasFixedSize(true);
        setAdapter(new RcvAbilitiesAdapter(requireContext(), getCursor()));
        getRcvAbilities().setAdapter(getAdapter());
        getRcvAbilities().setLayoutManager(new LinearLayoutManager(getContext()));
        getFabAdicionar().setOnClickListener(v1 -> getJm().addAbilityDialog(requireContext(), v, getDataBase(), getAdapter()));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getCursor().getCount() == 0) {
            Snackbar.make(view, R.string.no_ability, Snackbar.LENGTH_LONG).show();
        }
    }

    private Cursor listarAbilities(Context context) {
        return getDataBase().getCursorValue(getDataBase().openDatabase(context), false, "ABILITY", new String[]{"ID", "NAME"}, null, null, null, null, "NAME ASC", null);
    }
}