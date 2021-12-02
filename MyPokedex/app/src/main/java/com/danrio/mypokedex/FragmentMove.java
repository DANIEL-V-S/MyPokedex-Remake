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

public class FragmentMove extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private FloatingActionButton fabAdicionar;
    private RcvMovesAdapter adapter;
    private RecyclerView rcvMoves;
    private Cursor cursor;

    public RcvMovesAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RcvMovesAdapter adapter) {
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

    public RecyclerView getRcvMoves() {
        return rcvMoves;
    }

    public void setRcvMoves(RecyclerView rcvMoves) {
        this.rcvMoves = rcvMoves;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_move, container, false);
        setCursor(listarMoves(requireContext()));
        setFabAdicionar(v.findViewById(R.id.fabAdicionar));
        setRcvMoves(v.findViewById(R.id.rcvMoves));
        getRcvMoves().setHasFixedSize(true);
        setAdapter(new RcvMovesAdapter(requireContext(), getCursor()));
        getRcvMoves().setAdapter(getAdapter());
        getRcvMoves().setLayoutManager(new LinearLayoutManager(getContext()));
        getFabAdicionar().setOnClickListener(v1 -> getJm().addMoveDialog(requireContext(), v, getDataBase(), getAdapter()));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getCursor().getCount() == 0) {
            Snackbar.make(view, R.string.no_move, Snackbar.LENGTH_LONG).show();
        }
    }

    private Cursor listarMoves(Context context) {
        return getDataBase().getCursorValue(getDataBase().openDatabase(context), false, "MOVE", new String[]{"ID", "NAME", "CATEGORY"}, null, null, null, null, "NAME ASC", null);
    }
}