package com.danrio.mypokedex;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RcvLinkAdapter extends RecyclerView.Adapter<RcvLinkAdapter.ViewHolder> {
    private List<List<String>> nameList;
    private List<String> selected;
    private Context context;
    private Cursor cursor;

    public RcvLinkAdapter(Context context, List<List<String>> names, List<String> selected) {
        setSelected(selected);
        setContext(context);
        setNameList(names);
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }

    public List<List<String>> getNameList() {
        return nameList;
    }

    public void setNameList(List<List<String>> nameList) {
        this.nameList = nameList;
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
    public RcvLinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RcvLinkAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_line_checkbox, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RcvLinkAdapter.ViewHolder holder, int position) {
        holder.getTxtCodigo().setText(getNameList().get(holder.getAdapterPosition()).get(0));
        holder.getTxtLinha1().setText(getNameList().get(holder.getAdapterPosition()).get(1));
        String linkId = holder.getTxtCodigo().getText().toString();
        try {
            holder.getCheckBox().setChecked(getSelected().contains(linkId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        holder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                try {
                    getSelected().add(linkId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    getSelected().remove(linkId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
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
