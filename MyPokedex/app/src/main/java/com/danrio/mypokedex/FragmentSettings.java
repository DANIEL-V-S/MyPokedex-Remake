package com.danrio.mypokedex;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class FragmentSettings extends Fragment {
    private final DataBase dataBase = new DataBase();
    private final JavaMethods jm = new JavaMethods();
    private final JsonMethods jsonM = new JsonMethods();
    private TextInputEditText txtName;
    private Button btnSalvar, btnZerar, btnImportOriginal;
    private Spinner spnPronoun;
    private String name = "";
    private int pronoun = 0;

    public Button getBtnImportOriginal() {
        return btnImportOriginal;
    }

    public void setBtnImportOriginal(Button btnImportOriginal) {
        this.btnImportOriginal = btnImportOriginal;
    }

    public JsonMethods getJsonM() {
        return jsonM;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public int getPronoun() {
        return pronoun;
    }

    public void setPronoun(int pronoun) {
        this.pronoun = pronoun;
    }

    public Spinner getSpnPronoun() {
        return spnPronoun;
    }

    public void setSpnPronoun(Spinner spnPronoun) {
        this.spnPronoun = spnPronoun;
    }

    public Button getBtnZerar() {
        return btnZerar;
    }

    public void setBtnZerar(Button btnZerar) {
        this.btnZerar = btnZerar;
    }

    public Button getBtnSalvar() {
        return btnSalvar;
    }

    public void setBtnSalvar(Button btnSalvar) {
        this.btnSalvar = btnSalvar;
    }

    public TextInputEditText getTxtName() {
        return txtName;
    }

    public void setTxtName(TextInputEditText tilName) {
        this.txtName = tilName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        setBtnImportOriginal(v.findViewById(R.id.btnImportOriginal));
        setSpnPronoun(v.findViewById(R.id.spnPronoun));
        setBtnSalvar(v.findViewById(R.id.btnSalvar));
        setBtnZerar(v.findViewById(R.id.btnZerar));
        setTxtName(v.findViewById(R.id.edtName));
        if (savedInstanceState != null) {
            setName(savedInstanceState.getString("name"));
            setPronoun(savedInstanceState.getInt("pronoun", 0));
        } else {
            setName(getSettingsName());
            setPronoun(getSettingsPronoun());
        }
        getTxtName().setText(getName());
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{getString(R.string.settings_pronoun), getString(R.string.settings_pronoun_0), getString(R.string.settings_pronoun_1), getString(R.string.settings_pronoun_2)}) {
            @Override
            public boolean isEnabled(int position) {
                return !getSpnPronoun().getItemAtPosition(position).toString().contains(getString(R.string.settings_pronoun));
            }
        };
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpnPronoun().setAdapter(adapterSpinner);
        getSpnPronoun().setSelection(getPronoun());
        getBtnSalvar().setOnClickListener(v1 -> {
            if (Objects.requireNonNull(getTxtName().getText()).toString().isEmpty()) {
                Snackbar.make(v, getString(R.string.settings_name_empty), Snackbar.LENGTH_LONG).show();
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", Objects.requireNonNull(getTxtName().getText()).toString());
                contentValues.put("PRONOUN", (getSpnPronoun().getSelectedItemPosition() - 1));
                if (getDataBase().update(requireContext(), "SETTINGS", contentValues, null, null) == 1) {
                    Snackbar.make(v, getString(R.string.settings_updated), Snackbar.LENGTH_LONG).show();
                    NavigationView nav = requireActivity().findViewById(R.id.navigation);
                    TextView txtSubtitle = nav.getHeaderView(0).findViewById(R.id.navSubtitle);
                    txtSubtitle.setText(Objects.requireNonNull(getTxtName().getText()).toString());
                }
            }
        });
        getBtnZerar().setOnClickListener(v2 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
            builder.setTitle(R.string.settings_btnZerar_confirmation_title).setIcon(R.drawable.delete_icon);
            builder.setMessage(R.string.settings_btnZerar_confirmation_message);
            builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                getDataBase().deleteDatabase(requireContext());
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                requireContext().startActivity(intent);
            }).setNeutralButton(R.string.no, null).create().show();
        });
        getBtnImportOriginal().setOnClickListener(v3 -> {
            String[] importOriginal = getResources().getStringArray(R.array.settings_btnImportOriginal);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
            builder.setTitle(importOriginal[0]).setIcon(R.drawable.info_icon);
            builder.setItems(new String[]{importOriginal[1], importOriginal[2], importOriginal[3], importOriginal[4], importOriginal[5]}, (dialog, which) -> {
                String[] importOriginalLanguages = getResources().getStringArray(R.array.settings_btnImportOriginal_languages);
                switch (which) {
                    case 0: {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
                        alert.setTitle(importOriginalLanguages[0]).setIcon(R.drawable.type_icon);
                        alert.setItems(new String[]{importOriginalLanguages[1], importOriginalLanguages[2]}, (dialog1, which1) -> {
                            switch (which1) {
                                case 0: {
                                    List<String> types = getJsonM().importTypesEn(getContext());
                                    getDataBase().importTypes(getContext(), v3, types);
                                }
                                break;
                                case 1: {
                                    List<String> types = getJsonM().importTypesPt(getContext());
                                    getDataBase().importTypes(getContext(), v3, types);
                                }
                                break;
                            }
                        }).create().show();
                    }
                    break;
                    case 1: {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
                        alert.setTitle(importOriginalLanguages[0]).setIcon(R.drawable.move_icon);
                        alert.setItems(new String[]{importOriginalLanguages[1], importOriginalLanguages[2]}, (dialog1, which1) -> {
                            switch (which1) {
                                case 0: {
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                                case 1: {
                                    System.out.println("Movimento");
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                            }
                        }).create().show();
                    }
                    break;
                    case 2: {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
                        alert.setTitle(importOriginalLanguages[0]).setIcon(R.drawable.ability_icon);
                        alert.setItems(new String[]{importOriginalLanguages[1], importOriginalLanguages[2]}, (dialog1, which1) -> {
                            switch (which1) {
                                case 0: {
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                                case 1: {
                                    System.out.println("Habilidade");
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                            }
                        }).create().show();
                    }
                    break;
                    case 3: {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
                        alert.setTitle(importOriginalLanguages[0]).setIcon(R.drawable.pokeball_icon);
                        alert.setItems(new String[]{importOriginalLanguages[1], importOriginalLanguages[2]}, (dialog1, which1) -> {
                            switch (which1) {
                                case 0: {
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                                case 1: {
                                    System.out.println("Pokémon");
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                            }
                        }).create().show();
                    }
                    break;
                    case 4: {
                        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
                        alert.setTitle(importOriginalLanguages[0]).setIcon(R.drawable.mypokedex_icon);
                        alert.setItems(new String[]{importOriginalLanguages[1], importOriginalLanguages[2]}, (dialog1, which1) -> {
                            switch (which1) {
                                case 0: {
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                                case 1: {
                                    System.out.println("Pokédex");
                                    getJm().underDevelopment(getContext());
                                }
                                break;
                            }
                        }).create().show();
                    }
                    break;
                    default: {

                    }
                    break;
                }
            }).create().show();
        });
        return v;
    }

    private String getSettingsName() {
        List<List<String>> select = getDataBase().queryString(getContext(), false, "SETTINGS", new String[]{"NAME"}, null, null, null, null, null, null);
        if (select.isEmpty()) {
            return getString(R.string.nav_header_subtitle_0);
        } else {
            return select.get(0).get(0);
        }
    }

    private int getSettingsPronoun() {
        List<List<String>> select = getDataBase().queryString(getContext(), false, "SETTINGS", new String[]{"PRONOUN"}, null, null, null, null, null, null);
        if (select.isEmpty()) {
            return 0;
        } else {
            return (Integer.parseInt(select.get(0).get(0)) + 1);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setName(Objects.requireNonNull(getTxtName().getText()).toString());
        setPronoun(getSpnPronoun().getSelectedItemPosition());
        outState.putString("name", getName());
        outState.putInt("pronoun", getPronoun());
    }
}