package com.danrio.mypokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {
    private final DataBase dataBase = new DataBase();

    public DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView txtHome = v.findViewById(R.id.txtWelcome);
        String pronoun = getDataBase().queryString(getContext(), false, "SETTINGS", new String[]{"PRONOUN"}, null, null, null, null, null, null).get(0).get(0);
        switch (pronoun) {
            case "1": {
                txtHome.setText(getString(R.string.home_welcome_1));
            }
            break;
            case "2": {
                txtHome.setText(getString(R.string.home_welcome_2));
            }
            break;
            default: {
                txtHome.setText(getString(R.string.home_welcome_0));
            }
        }
        return v;
    }
}