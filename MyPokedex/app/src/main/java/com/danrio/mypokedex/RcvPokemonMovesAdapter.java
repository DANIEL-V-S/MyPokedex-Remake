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

public class RcvPokemonMovesAdapter extends RecyclerView.Adapter<RcvPokemonMovesAdapter.ViewHolder> {
    private final JavaMethods jm = new JavaMethods();
    private final DataBase dataBase = new DataBase();
    private List<List<String>> moves;
    private Context context;

    public RcvPokemonMovesAdapter(Context context, List<List<String>> moves) {
        setContext(context);
        setMoves(moves);
    }

    public JavaMethods getJm() {
        return jm;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public List<List<String>> getMoves() {
        return moves;
    }

    public void setMoves(List<List<String>> moves) {
        this.moves = moves;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RcvPokemonMovesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvPokemonMovesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_two_lines, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvPokemonMovesAdapter.ViewHolder holder, int position) {
        holder.getTxtCodigo().setText(getMoves().get(holder.getAdapterPosition()).get(0));
        holder.getTxtLinha1().setText(getMoves().get(holder.getAdapterPosition()).get(1));
        holder.getTxtLinha2().setText(getMoves().get(holder.getAdapterPosition()).get(2));
        holder.getConstraintLayout().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setTitle(R.string.dialog_what_to_do_title).setIcon(R.drawable.question_mark_icon);
            builder.setItems(new String[]{getContext().getString(R.string.dialog_what_to_do_view_details), getContext().getString(R.string.unlink_)}, (dialog, which) -> {
                String id = holder.getTxtCodigo().getText().toString(), move_id = id.split(";", 3)[1];
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(getContext(), ViewMove.class);
                        intent.putExtra("id", move_id);
                        getContext().startActivity(intent);
                    } break;
                    case 1: {
                        try {
                            List<String> move = getDataBase().getMovesIdName(getContext(), "ID = ?", new String[]{move_id}).get(0);
                            getJm().unlinkPokemonMoveDialog(getContext(), getDataBase(), id, move.get(1));
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
        return getMoves().size();
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
