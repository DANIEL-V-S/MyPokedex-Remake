package com.danrio.mypokedex;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RcvPokemonTypesAdapter extends RecyclerView.Adapter<RcvPokemonTypesAdapter.ViewHolder> {
    private final List<List<String>> types = new ArrayList<>();
    private SharedViewModelPokemon viewModelPokemon;
    private Cursor cursor;

    public RcvPokemonTypesAdapter(Cursor cursor, SharedViewModelPokemon viewModelPokemon) {
        setViewModelPokemon(viewModelPokemon);
        setCursor(cursor);
        getCursor().moveToFirst();
        for (int i = 0; i < getCursor().getCount(); i++) {
            getTypes().add(new ArrayList<>(Arrays.asList(getCursor().getString(0), getCursor().getString(1))));
            getCursor().moveToNext();
        }
    }

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    public List<List<String>> getTypes() {
        return types;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RcvPokemonTypesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvPokemonTypesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_line_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvPokemonTypesAdapter.ViewHolder holder, int position) {
        holder.getTxtCodigo().setText(getTypes().get(holder.getAdapterPosition()).get(0));
        holder.getTxtLinha1().setText(getTypes().get(holder.getAdapterPosition()).get(1));
        String typeId = holder.getTxtCodigo().getText().toString();
        try {
            holder.getCheckBox().setChecked(Objects.requireNonNull(getViewModelPokemon().getTipos().getValue()).contains(Integer.parseInt(typeId)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        holder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                try {
                    getViewModelPokemon().addTipo(Integer.parseInt(typeId));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    getViewModelPokemon().removeTipo(Integer.parseInt(typeId));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLinha1, txtCodigo;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            setTxtLinha1(itemView.findViewById(R.id.txtLinha1));
            setTxtCodigo(itemView.findViewById(R.id.txtCodigo));
            setCheckBox(itemView.findViewById(R.id.checkBox));
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTxtLinha1() {
            return txtLinha1;
        }

        public void setTxtLinha1(TextView txtLinha1) {
            this.txtLinha1 = txtLinha1;
        }

        public TextView getTxtCodigo() {
            return txtCodigo;
        }

        public void setTxtCodigo(TextView txtCodigo) {
            this.txtCodigo = txtCodigo;
        }
    }
}
