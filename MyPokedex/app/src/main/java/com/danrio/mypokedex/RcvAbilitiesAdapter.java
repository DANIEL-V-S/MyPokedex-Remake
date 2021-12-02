package com.danrio.mypokedex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RcvAbilitiesAdapter extends RecyclerView.Adapter<RcvAbilitiesAdapter.ViewHolder> {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private final List<List<String>> abilities = new ArrayList<>();
    private Context context;
    private Cursor cursor;

    public RcvAbilitiesAdapter(Context context, Cursor cursor) {
        setContext(context);
        setCursor(cursor);
        getCursor().moveToFirst();
        for (int i = 0; i < getCursor().getCount(); i++) {
            getAbilities().add(new ArrayList<>(Arrays.asList(getCursor().getString(0), getCursor().getString(1))));
            getCursor().moveToNext();
        }
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public List<List<String>> getAbilities() {
        return abilities;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RcvAbilitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvAbilitiesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvAbilitiesAdapter.ViewHolder holder, int position) {
        holder.getTxtCodigo().setText(getAbilities().get(holder.getAdapterPosition()).get(0));
        holder.getTxtLinha1().setText(getAbilities().get(holder.getAdapterPosition()).get(1));
        holder.getConstraintLayout().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setTitle(R.string.dialog_what_to_do_title).setIcon(R.drawable.question_mark_icon);
            builder.setItems(new String[]{context.getString(R.string.dialog_what_to_do_view_details), context.getString(R.string.dialog_what_to_do_update), context.getString(R.string.dialog_what_to_do_delete)}, (dialog, which) -> {
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(context, ViewAbility.class);
                        intent.putExtra("id", holder.getTxtCodigo().getText().toString());
                        getContext().startActivity(intent);
                    }
                    break;
                    case 1: {
                        getJm().updateAbilityDialog(getContext(), holder.getConstraintLayout(), getDataBase(), new String[]{holder.getTxtCodigo().getText().toString()}, this, holder.getAdapterPosition());
                    }
                    break;
                    case 2: {
                        getJm().deleteAbilityDialog(getContext(), holder.getConstraintLayout(), getDataBase(), new String[]{holder.getTxtCodigo().getText().toString()}, this, holder.getAdapterPosition());
                    }
                    break;
                    default: {
                    }
                    break;
                }
            }).create().show();
        });
    }

    @Override
    public int getItemCount() {
        return getAbilities().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLinha1, txtCodigo;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            setTxtLinha1(itemView.findViewById(R.id.txtLinha1));
            setTxtCodigo(itemView.findViewById(R.id.txtCodigo));
            setConstraintLayout(itemView.findViewById(R.id.constraintLayout));
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

        public ConstraintLayout getConstraintLayout() {
            return constraintLayout;
        }

        public void setConstraintLayout(ConstraintLayout constraintLayout) {
            this.constraintLayout = constraintLayout;
        }
    }
}
