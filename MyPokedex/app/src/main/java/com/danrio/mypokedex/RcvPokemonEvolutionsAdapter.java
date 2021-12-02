package com.danrio.mypokedex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RcvPokemonEvolutionsAdapter extends RecyclerView.Adapter<RcvPokemonEvolutionsAdapter.ViewHolder> {
    private final JavaMethods jm = new JavaMethods();
    private final DataBase dataBase = new DataBase();
    private List<List<String>> evolutions;
    private Context context;

    public RcvPokemonEvolutionsAdapter(Context context, List<List<String>> evolutions) {
        setContext(context);
        setEvolutions(evolutions);
    }

    public JavaMethods getJm() {
        return jm;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public List<List<String>> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<List<String>> evolutions) {
        this.evolutions = evolutions;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RcvPokemonEvolutionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvPokemonEvolutionsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_two_lines, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvPokemonEvolutionsAdapter.ViewHolder holder, int position) {
        holder.getTxtCodigo().setText(getEvolutions().get(holder.getAdapterPosition()).get(0));
        holder.getTxtLinha1().setText(getEvolutions().get(holder.getAdapterPosition()).get(1));
        holder.getTxtLinha2().setText(getEvolutions().get(holder.getAdapterPosition()).get(2));
        holder.getConstraintLayout().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setTitle(R.string.dialog_what_to_do_title).setIcon(R.drawable.question_mark_icon);
            builder.setItems(new String[]{getContext().getString(R.string.dialog_what_to_do_view_details), getContext().getString(R.string.unlink_)}, (dialog, which) -> {
                String id = holder.getTxtCodigo().getText().toString(), evol_id = id.split(";")[1];
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(getContext(), ViewPokemon.class);
                        intent.putExtra("id", evol_id);
                        getContext().startActivity(intent);
                    } break;
                    case 1: {
                        try {
                            List<String> pokemon = getDataBase().getPokemonIdNameList(getContext(), "ID = ?", new String[]{evol_id}).get(0);
                            getJm().unlinkPokemonEvolutionDialog(getContext(), getDataBase(), id, pokemon.get(1));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } break;
                }
            }).create().show();
        });
    }

    @Override
    public int getItemCount() {
        return getEvolutions().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLinha1, txtLinha2, txtCodigo;
        ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            setTxtLinha1(itemView.findViewById(R.id.txtLinha1));
            setTxtLinha2(itemView.findViewById(R.id.txtLinha2));
            setTxtCodigo(itemView.findViewById(R.id.txtCodigo));
            setConstraintLayout(itemView.findViewById(R.id.constraintLayout));
        }

        public TextView getTxtLinha1() {
            return txtLinha1;
        }

        public void setTxtLinha1(TextView txtLinha1) {
            this.txtLinha1 = txtLinha1;
        }

        public TextView getTxtLinha2() {
            return txtLinha2;
        }

        public void setTxtLinha2(TextView txtLinha2) {
            this.txtLinha2 = txtLinha2;
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
