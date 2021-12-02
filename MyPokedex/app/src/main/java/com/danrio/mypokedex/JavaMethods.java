package com.danrio.mypokedex;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JavaMethods {
    private Button btnPos = null;

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public Button getBtnPos() {
        return btnPos;
    }

    public void setBtnPos(Button btnPos) {
        this.btnPos = btnPos;
    }

    private ContentValues putPokemonInContent(Pokemon pokemon) {
        ContentValues content = new ContentValues();
        content.put("ID", pokemon.getId());
        content.put("DEX", pokemon.getDex());
        content.put("NAME", pokemon.getName());
        content.put("REGULAR_PHOTO", pokemon.getRegular_photo());
        content.put("SHINY_PHOTO", pokemon.getShiny_photo());
        content.put("SPECIES", pokemon.getSpecies());
        content.put("CATEGORY", pokemon.getCategory());
        content.put("REGION", pokemon.getRegion());
        content.put("HEIGHT", pokemon.getHeight());
        content.put("WEIGHT", pokemon.getWeight());
        content.put("GENDER", pokemon.getGender());
        content.put("DESCRIPTION", pokemon.getDescription());
        content.put("BASE_HP", pokemon.getBase_hp());
        content.put("BASE_ATK", pokemon.getBase_atk());
        content.put("BASE_DEF", pokemon.getBase_def());
        content.put("BASE_SPATK", pokemon.getBase_spatk());
        content.put("BASE_SPDEF", pokemon.getBase_spdef());
        content.put("BASE_SPEED", pokemon.getBase_speed());
        return content;
    }

    public void underDevelopment(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setTitle(R.string.under_development_title).setIcon(R.drawable.info_icon);
        builder.setMessage(R.string.under_development_message);
        builder.setPositiveButton(R.string.ok, null).create().show();
    }

    public Pokemon blankPokemon(Context context) {
        DataBase dataBase = new DataBase();
        return new Pokemon(dataBase.getNewId(context, "Pokemon"), -1, "", "", "", "", 0.0, 0.0, "", "", 0, 0, 0, 0, 0, 0, null, null);
    }

    public void addPokemon(Context context, View v, DataBase dataBase, Pokemon pokemon, List<Integer> tipos, Activity activity) {
        if (pokemon.getDex() >= 0 && !pokemon.getName().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setIcon(R.drawable.add_icon).setTitle(context.getString(R.string.activity_addPokemon_title));
            builder.setMessage(context.getString(R.string.activity_addPokemon_dialog_message) + "\n#" + pokemon.getDex() + " - " + pokemon.getName());
            builder.setNeutralButton(context.getString(R.string.cancel), null);
            builder.setPositiveButton(context.getString(R.string.add), (dialog, which) -> {
                ContentValues contentP = putPokemonInContent(pokemon);
                if (dataBase.insert(context, "POKEMON", "ID, DEX, NAME, REGULAR_PHOTO, SHINY_PHOTO, SPECIES, CATEGORY, REGION, HEIGHT, WEIGHT, GENDER, DESCRIPTION, BASE_HP, BASE_ATK, BASE_DEF, BASE_SPATK, BASE_SPDEF, BASE_SPEED", contentP) != -1) {
                    for (int tipo : tipos) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("POKEMON_ID", pokemon.getId());
                        contentValues.put("TYPE_ID", tipo);
                        if (dataBase.insert(context, "POKEMON_TYPE", "POKEMON_ID, TYPE_ID", contentValues) != -1) {
                            System.out.println("Registrou tipo " + tipo);
                        } else {
                            System.out.println("Não registrou tipo " + tipo);
                        }
                    }
                    Toast.makeText(context, R.string.activity_addPokemon_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Snackbar.make(v, context.getString(R.string.activity_addPokemon_failed), Snackbar.LENGTH_LONG).show();
                }
            }).create().show();
        } else {
            Snackbar.make(v, context.getString(R.string.activity_addPokemon_failed_1), Snackbar.LENGTH_LONG).show();
        }
    }

    public void editPokemon(Context context, View v, DataBase dataBase, Pokemon pokemon, List<Integer> tipos, Activity activity) {
        if (pokemon.getDex() >= 0 && !pokemon.getName().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setIcon(R.drawable.edit_icon).setTitle(context.getString(R.string.activity_updatePokemon_title));
            builder.setMessage(context.getString(R.string.activity_updatePokemon_dialog_message) + "\n#" + pokemon.getDex() + " - " + pokemon.getName());
            builder.setNeutralButton(context.getString(R.string.cancel), null);
            builder.setPositiveButton(context.getString(R.string.update), (dialog, which) -> {
                ContentValues contentP = putPokemonInContent(pokemon);
                if (dataBase.update(context, "POKEMON", contentP, "ID = ?", new String[]{String.valueOf(pokemon.getId())}) == 1) {
                    dataBase.delete(context, "POKEMON_TYPE", "POKEMON_ID = ?", new String[]{String.valueOf(pokemon.getId())});
                    for (int tipo : tipos) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("POKEMON_ID", pokemon.getId());
                        contentValues.put("TYPE_ID", tipo);
                        if (dataBase.insert(context, "POKEMON_TYPE", "POKEMON_ID, TYPE_ID", contentValues) != -1) {
                            System.out.println("Registrou tipo " + tipo);
                        } else {
                            System.out.println("Não registrou tipo " + tipo);
                        }
                    }
                    Toast.makeText(context, R.string.activity_updatePokemon_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Snackbar.make(v, context.getString(R.string.activity_updatePokemon_failed), Snackbar.LENGTH_LONG).show();
                }
            }).create().show();
        } else {
            Snackbar.make(v, context.getString(R.string.activity_updatePokemon_failed_1), Snackbar.LENGTH_LONG).show();
        }
    }

    public void addTypeDialog(Context context, View v, DataBase dataBase, RcvTypesAdapter adapter) {
        List<String> list = dataBase.getTypesName(context, null, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_icon).setTitle(context.getString(R.string.dialog_addType_title));
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_type, (ViewGroup) v, false);
        TextInputEditText edtName = dialogV.findViewById(R.id.edtType);
        builder.setView(dialogV);
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.add), (dialog, which) -> {
            ContentValues content = new ContentValues();
            int id = dataBase.getNewId(context, "TYPE");
            String name = Objects.requireNonNull(edtName.getText()).toString();
            content.put("ID", id);
            content.put("NAME", name);
            if (dataBase.insert(context, "TYPE", "ID, NAME", content) != -1) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_addType_success), Snackbar.LENGTH_LONG).show();
                    adapter.getTypes().add(Arrays.asList(String.valueOf(id), name));
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_addType_failed), Snackbar.LENGTH_LONG).show();
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBtnPos() != null) {
                    getBtnPos().setEnabled(!s.toString().equals("") && !list.contains(s.toString()));
                }
            }
        });
        builder.create();
        setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
        if (getBtnPos() != null) {
            getBtnPos().setEnabled(false);
        }
    }

    public void addAbilityDialog(Context context, View v, DataBase dataBase, RcvAbilitiesAdapter adapter) {
        List<String> list = dataBase.getAbilitiesName(context, null, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_icon);
        builder.setTitle(context.getString(R.string.dialog_addAbility_title));
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_ability, (ViewGroup) v, false);
        TextInputEditText edtName = dialogV.findViewById(R.id.edtAbilityName), edtDescription = dialogV.findViewById(R.id.edtAbilityDescription);
        builder.setView(dialogV);
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.add), (dialog, which) -> {
            ContentValues content = new ContentValues();
            int id = dataBase.getNewId(context, "ABILITY");
            String name = Objects.requireNonNull(edtName.getText()).toString();
            content.put("ID", id);
            content.put("NAME", name);
            content.put("DESCRIPTION", Objects.requireNonNull(edtDescription.getText()).toString());
            if (dataBase.insert(context, "ABILITY", "ID, NAME, DESCRIPTION", content) != -1) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_addAbility_success), Snackbar.LENGTH_LONG).show();
                    adapter.getAbilities().add(Arrays.asList(String.valueOf(id), name));
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_addAbility_failed), Snackbar.LENGTH_LONG).show();
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBtnPos() != null) {
                    getBtnPos().setEnabled(!s.toString().equals("") && !list.contains(s.toString()));
                }
            }
        });
        builder.create();
        setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
        if (getBtnPos() != null) {
            getBtnPos().setEnabled(false);
        }
    }

    public void addMoveDialog(Context context, View v, DataBase dataBase, RcvMovesAdapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_icon);
        builder.setTitle(context.getString(R.string.dialog_addMove_title));
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_move, (ViewGroup) v, false);
        List<String> list = dataBase.getMovesName(context, null, null);
        TextInputEditText edtName = dialogV.findViewById(R.id.edtMoveName), edtDescription = dialogV.findViewById(R.id.edtMoveDescription), edtPower = dialogV.findViewById(R.id.edtMovePower), edtAccuracy = dialogV.findViewById(R.id.edtMoveAccuracy), edtPP = dialogV.findViewById(R.id.edtMovePP), edtPriority = dialogV.findViewById(R.id.edtMovePriority), edtCategory = dialogV.findViewById(R.id.edtMoveCategory);
        builder.setView(dialogV);
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.add), (dialog, which) -> {
            String power = Objects.requireNonNull(edtPower.getText()).toString(), accuracy = Objects.requireNonNull(edtAccuracy.getText()).toString(), pp = Objects.requireNonNull(edtPP.getText()).toString(), priority = Objects.requireNonNull(edtPriority.getText()).toString();
            int id = dataBase.getNewId(context, "MOVE");
            String name = Objects.requireNonNull(edtName.getText()).toString(), category = Objects.requireNonNull(edtCategory.getText()).toString();
            ContentValues content = new ContentValues();
            content.put("ID", id);
            content.put("NAME", name);
            content.put("DESCRIPTION", Objects.requireNonNull(edtDescription.getText()).toString());
            content.put("POWER", power.equals("") ? 0 : Integer.parseInt(power));
            content.put("ACCURACY", accuracy);
            content.put("PP", pp.equals("") ? 0 : Integer.parseInt(pp));
            content.put("PRIORITY", priority.equals("") ? 0 : Integer.parseInt(priority));
            content.put("CATEGORY", category);
            if (dataBase.insert(context, "MOVE", "ID, NAME, DESCRIPTION, POWER, ACCURACY, PP, PRIORITY, CATEGORY", content) != -1) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_addMove_success), Snackbar.LENGTH_LONG).show();
                    adapter.getMoves().add(Arrays.asList(String.valueOf(id), name, category));
                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_addMove_failed), Snackbar.LENGTH_LONG).show();
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBtnPos() != null) {
                    getBtnPos().setEnabled(!s.toString().equals("") && !list.contains(s.toString()));
                }
            }
        });
        builder.create();
        setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
        if (getBtnPos() != null) {
            getBtnPos().setEnabled(false);
        }
    }

    public void updateTypeDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvTypesAdapter adapter, int position) {
        List<String> list = dataBase.getTypesName(context, null, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.edit_icon);
        builder.setTitle(context.getString(R.string.dialog_updateType_title));
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_type, (ViewGroup) v, false);
        TextInputEditText edtName = dialogV.findViewById(R.id.edtType);
        edtName.setText(adapter.getTypes().get(position).get(1));
        builder.setView(dialogV);
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.update), (dialog, which) -> {
            ContentValues content = new ContentValues();
            String name = Objects.requireNonNull(edtName.getText()).toString();
            content.put("NAME", name);
            int linhas = dataBase.update(context, "TYPE", content, "ID = ?", dadosWhere);
            if (linhas == 1) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_updateType_success), Snackbar.LENGTH_LONG).show();
                    adapter.getTypes().get(position).set(1, name);
                    adapter.notifyItemChanged(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                if (linhas == 0) {
                    Snackbar.make(v, context.getString(R.string.no_update), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(v, context.getString(R.string.dialog_updateType_failed), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getBtnPos() != null) {
                    getBtnPos().setEnabled(!s.toString().equals("") && (!list.contains(s.toString()) || adapter.getTypes().get(position).get(1).equals(s.toString())));
                }
            }
        });
        builder.create();
        setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
    }

    public void updateAbilityDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvAbilitiesAdapter adapter, int position) {
        List<String> list = dataBase.getAbilitiesName(context, null, null);
        try {
            List<String> thisAbility = dataBase.getAbilities(context, "ID = ?", new String[]{adapter.getAbilities().get(position).get(0)}).get(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setIcon(R.drawable.edit_icon);
            builder.setTitle(context.getString(R.string.dialog_updateAbility_title));
            View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_ability, (ViewGroup) v, false);
            TextInputEditText edtName = dialogV.findViewById(R.id.edtAbilityName);
            edtName.setText(thisAbility.get(1));
            TextInputEditText edtDescription = dialogV.findViewById(R.id.edtAbilityDescription);
            edtDescription.setText(thisAbility.get(2));
            builder.setView(dialogV);
            builder.setNeutralButton(context.getString(R.string.cancel), null);
            builder.setPositiveButton(context.getString(R.string.update), (dialog, which) -> {
                ContentValues content = new ContentValues();
                String name = Objects.requireNonNull(edtName.getText()).toString();
                content.put("NAME", name);
                content.put("DESCRIPTION", Objects.requireNonNull(edtDescription.getText()).toString());
                int linhas = dataBase.update(context, "ABILITY", content, "ID = ?", dadosWhere);
                if (linhas == 1) {
                    try {
                        Snackbar.make(v, context.getString(R.string.dialog_updateAbility_success), Snackbar.LENGTH_LONG).show();
                        adapter.getAbilities().get(position).set(1, name);
                        adapter.notifyItemChanged(position);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    if (linhas == 0) {
                        Snackbar.make(v, context.getString(R.string.no_update), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(v, context.getString(R.string.dialog_updateAbility_failed), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            edtName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (getBtnPos() != null) {
                        getBtnPos().setEnabled(!s.toString().equals("") && (!list.contains(s.toString()) || adapter.getAbilities().get(position).get(1).equals(s.toString())));
                    }
                }
            });
            builder.create();
            setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
        } catch (Exception ex) {
            ex.printStackTrace();
            Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
        }
    }

    public void updateMoveDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvMovesAdapter adapter, int position) {
        List<String> list = dataBase.getMovesName(context, null, null);
        try {
            List<String> thisMove = dataBase.getMoves(context, "ID = ?", new String[]{adapter.getMoves().get(position).get(0)}).get(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            builder.setIcon(R.drawable.edit_icon);
            builder.setTitle(context.getString(R.string.dialog_updateMove_title));
            View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_move, (ViewGroup) v, false);
            TextInputEditText edtName = dialogV.findViewById(R.id.edtMoveName);
            edtName.setText(thisMove.get(1));
            TextInputEditText edtDescription = dialogV.findViewById(R.id.edtMoveDescription);
            edtDescription.setText(thisMove.get(2));
            TextInputEditText edtPower = dialogV.findViewById(R.id.edtMovePower);
            edtPower.setText(thisMove.get(3));
            TextInputEditText edtAccuracy = dialogV.findViewById(R.id.edtMoveAccuracy);
            edtAccuracy.setText(thisMove.get(4));
            TextInputEditText edtPP = dialogV.findViewById(R.id.edtMovePP);
            edtPP.setText(thisMove.get(5));
            TextInputEditText edtPriority = dialogV.findViewById(R.id.edtMovePriority);
            edtPriority.setText(thisMove.get(6));
            TextInputEditText edtCategory = dialogV.findViewById(R.id.edtMoveCategory);
            edtCategory.setText(thisMove.get(7));
            builder.setView(dialogV);
            builder.setNeutralButton(context.getString(R.string.cancel), null);
            builder.setPositiveButton(context.getString(R.string.update), (dialog, which) -> {
                ContentValues content = new ContentValues();
                String name = Objects.requireNonNull(edtName.getText()).toString();
                content.put("NAME", name);
                content.put("DESCRIPTION", Objects.requireNonNull(edtDescription.getText()).toString());
                content.put("POWER", Objects.requireNonNull(edtPower.getText()).toString());
                content.put("ACCURACY", Objects.requireNonNull(edtAccuracy.getText()).toString());
                content.put("PP", Objects.requireNonNull(edtPP.getText()).toString());
                content.put("PRIORITY", Objects.requireNonNull(edtPriority.getText()).toString());
                content.put("CATEGORY", Objects.requireNonNull(edtCategory.getText()).toString());
                int linhas = dataBase.update(context, "MOVE", content, "ID = ?", dadosWhere);
                if (linhas == 1) {
                    try {
                        Snackbar.make(v, context.getString(R.string.dialog_updateMove_success), Snackbar.LENGTH_LONG).show();
                        adapter.getMoves().get(position).set(1, name);
                        adapter.notifyItemChanged(position);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    if (linhas == 0) {
                        Snackbar.make(v, context.getString(R.string.no_update), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(v, context.getString(R.string.dialog_updateMove_failed), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            edtName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (getBtnPos() != null) {
                        getBtnPos().setEnabled(!s.toString().equals("") && (!list.contains(s.toString()) || adapter.getMoves().get(position).get(1).equals(s.toString())));
                    }
                }
            });
            builder.create();
            setBtnPos(builder.show().getButton(AlertDialog.BUTTON_POSITIVE));
        } catch (Exception ex) {
            ex.printStackTrace();
            Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
        }
    }

    public void deleteTypeDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvTypesAdapter adapter, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.delete_icon);
        builder.setTitle(context.getString(R.string.dialog_deleteType_title));
        builder.setMessage(context.getString(R.string.dialog_deleteType_message) + "\n\n" + adapter.getTypes().get(position).get(1));
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
            if (dataBase.delete(context, "TYPE", "ID = ?", dadosWhere) > 0) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_deleteType_success), Snackbar.LENGTH_LONG).show();
                    adapter.getTypes().remove(position);
                    adapter.notifyItemRemoved(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_deleteType_failed), Snackbar.LENGTH_LONG).show();
            }
        }).create().show();
    }

    public void deleteAbilityDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvAbilitiesAdapter adapter, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.delete_icon);
        builder.setTitle(context.getString(R.string.dialog_deleteAbility_title));
        builder.setMessage(context.getString(R.string.dialog_deleteAbility_message) + "\n\n" + adapter.getAbilities().get(position).get(1));
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
            if (dataBase.delete(context, "ABILITY", "ID = ?", dadosWhere) > 0) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_deleteAbility_success), Snackbar.LENGTH_LONG).show();
                    adapter.getAbilities().remove(position);
                    adapter.notifyItemRemoved(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_deleteAbility_failed), Snackbar.LENGTH_LONG).show();
            }
        }).create().show();
    }

    public void deleteMoveDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvMovesAdapter adapter, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.delete_icon);
        builder.setTitle(context.getString(R.string.dialog_deleteMove_title));
        builder.setMessage(context.getString(R.string.dialog_deleteMove_message) + "\n\n" + adapter.getMoves().get(position).get(1));
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
            if (dataBase.delete(context, "MOVE", "ID = ?", dadosWhere) > 0) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_deleteMove_success), Snackbar.LENGTH_LONG).show();
                    adapter.getMoves().remove(position);
                    adapter.notifyItemRemoved(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_deleteMove_failed), Snackbar.LENGTH_LONG).show();
            }
        }).create().show();
    }

    public void deletePokemonDialog(Context context, View v, DataBase dataBase, String[] dadosWhere, RcvPokedexAdapter adapter, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.delete_icon);
        builder.setTitle(context.getString(R.string.dialog_deletePokemon_title));
        builder.setMessage(context.getString(R.string.dialog_deletePokemon_message) + "\n\n" + adapter.getPokemon().get(position).get(1));
        builder.setNeutralButton(context.getString(R.string.cancel), null);
        builder.setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
            if (dataBase.delete(context, "POKEMON", "ID = ?", dadosWhere) > 0) {
                try {
                    Snackbar.make(v, context.getString(R.string.dialog_deletePokemon_success), Snackbar.LENGTH_LONG).show();
                    adapter.getPokemon().remove(position);
                    adapter.notifyItemRemoved(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Snackbar.make(v, context.getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(v, context.getString(R.string.dialog_deletePokemon_failed), Snackbar.LENGTH_LONG).show();
            }
        }).create().show();
    }

    public void changeRegularPhoto(Context context, View v, ActivityResultLauncher<String> request, ActivityResultLauncher<String> pick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.edit_icon);
        builder.setTitle(context.getString(R.string.activity_Pokemon_btnChangeRegularPhoto));
        builder.setItems(new String[]{context.getString(R.string.activity_Pokemon_takeRegularPhoto), context.getString(R.string.activity_Pokemon_pickRegularPhoto), context.getString(R.string.activity_Pokemon_removeRegularPhoto)}, (dialog, which) -> {
            switch (which) {
                case 0: {
                    request.launch(Manifest.permission.CAMERA);
                }
                break;
                case 1: {
                    try {
                        pick.launch("image/*");
                    } catch (Exception ex) {
                        Snackbar.make(v, R.string.error_occurred, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                }
                break;
                case 2: {
                    Snackbar.make(v, R.string.activity_Pokemon_removePhoto, Snackbar.LENGTH_LONG).show();
                }
                default: {

                }
                break;
            }
        }).create().show();
    }

    public void changeShinyPhoto(Context context, View v, ActivityResultLauncher<String> request, ActivityResultLauncher<String> pick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.edit_icon);
        builder.setTitle(context.getString(R.string.activity_Pokemon_btnChangeShinyPhoto));
        builder.setItems(new String[]{context.getString(R.string.activity_Pokemon_takeShinyPhoto), context.getString(R.string.activity_Pokemon_pickShinyPhoto), context.getString(R.string.activity_Pokemon_removeShinyPhoto)}, (dialog, which) -> {
            switch (which) {
                case 0: {
                    request.launch(Manifest.permission.CAMERA);
                }
                break;
                case 1: {
                    try {
                        pick.launch("image/*");
                    } catch (Exception ex) {
                        Snackbar.make(v, R.string.error_occurred, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                }
                break;
                case 2: {
                    Snackbar.make(v, R.string.activity_Pokemon_removePhoto, Snackbar.LENGTH_LONG).show();
                }
                default: {

                }
                break;
            }
        }).create().show();
    }

    public void linkTypeDialog(Context context, View v, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_link_icon);
        builder.setTitle(context.getString(R.string.link_));
        builder.setItems(context.getResources().getStringArray(R.array.array_link_type_to), (dialog, which) -> {
            AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            build.setIcon(R.drawable.add_link_icon);
            build.setTitle(context.getResources().getStringArray(R.array.link_type_title)[which]);
            RecyclerView rcv;
            switch (which) {
                case 0: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_type_to_pokemon, (ViewGroup) v, false);
                    TextView txtTipo = dialogV.findViewById(R.id.txtTipo);
                    rcv = dialogV.findViewById(R.id.rcvPokemon);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getPokemonIdNameList(context, null, null), dataBase.getTypePokemon(context, "TYPE_ID = ?", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtTipo.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_TYPE", "TYPE_ID = ?", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", item);
                                contentValues.put("TYPE_ID", id);
                                dataBase.insert(context, "POKEMON_TYPE", "POKEMON_ID, TYPE_ID", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewType.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 1: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_type_to_move, (ViewGroup) v, false);
                    TextView txtTipo = dialogV.findViewById(R.id.txtTipo);
                    rcv = dialogV.findViewById(R.id.rcvMove);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getMovesIdName(context, null, null), dataBase.getTypeMoves(context, "TYPE_ID = ?", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtTipo.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "MOVE_TYPE", "TYPE_ID = ?", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("MOVE_ID", item);
                                contentValues.put("TYPE_ID", id);
                                dataBase.insert(context, "MOVE_TYPE", "MOVE_ID, TYPE_ID", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewType.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
            }
        }).create().show();
    }

    public void linkAbilityDialog(Context context, View v, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_link_icon);
        builder.setTitle(context.getString(R.string.link_));
        builder.setItems(context.getResources().getStringArray(R.array.array_link_ability_to), (dialog, which) -> {
            AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            build.setIcon(R.drawable.add_link_icon);
            build.setTitle(context.getResources().getStringArray(R.array.link_ability_title)[which]);
            RecyclerView rcv;
            switch (which) {
                case 0: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_ability_to_pokemon, (ViewGroup) v, false);
                    TextView txtAbility = dialogV.findViewById(R.id.txtAbility);
                    rcv = dialogV.findViewById(R.id.rcvPokemon);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getPokemonIdNameList(context, "ID NOT IN (SELECT POKEMON_ID FROM POKEMON_ABILITY WHERE ABILITY_ID = ? AND HIDDEN = TRUE)", new String[]{id}), dataBase.getAbilityPokemon(context, "ABILITY_ID = ? AND HIDDEN = FALSE", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtAbility.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_ABILITY", "ABILITY_ID = ? AND HIDDEN = FALSE", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", item);
                                contentValues.put("ABILITY_ID", id);
                                contentValues.put("HIDDEN", false);
                                dataBase.insert(context, "POKEMON_ABILITY", "POKEMON_ID, ABILITY_ID, HIDDEN", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewAbility.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 1: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_ability_to_pokemon, (ViewGroup) v, false);
                    TextView txtAbility = dialogV.findViewById(R.id.txtAbility);
                    rcv = dialogV.findViewById(R.id.rcvPokemon);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getPokemonIdNameList(context, "ID NOT IN (SELECT POKEMON_ID FROM POKEMON_ABILITY WHERE ABILITY_ID = ? AND HIDDEN = FALSE)", new String[]{id}), dataBase.getAbilityPokemon(context, "ABILITY_ID = ? AND HIDDEN = TRUE", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtAbility.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_ABILITY", "ABILITY_ID = ? AND HIDDEN = TRUE", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", item);
                                contentValues.put("ABILITY_ID", id);
                                contentValues.put("HIDDEN", true);
                                dataBase.insert(context, "POKEMON_ABILITY", "POKEMON_ID, ABILITY_ID, HIDDEN", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewAbility.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
            }
        }).create().show();
    }

    public void linkMoveDialog(Context context, View v, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_link_icon);
        builder.setTitle(context.getString(R.string.link_));
        builder.setItems(context.getResources().getStringArray(R.array.array_link_move_to), (dialog, which) -> {
            AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
            build.setIcon(R.drawable.add_link_icon);
            build.setTitle(context.getResources().getStringArray(R.array.link_move_title)[which]);
            RecyclerView rcv;
            switch (which) {
                case 0: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_move_to_pokemon, (ViewGroup) v, false);
                    TextView txtMovimento = dialogV.findViewById(R.id.txtMovimento);
                    TextInputLayout tilMethod = dialogV.findViewById(R.id.tilMethod);
                    Spinner spnPokemon = dialogV.findViewById(R.id.spnPokemon);
                    List<String> nameList = new ArrayList<>();
                    nameList.add(context.getString(R.string.dialog_link_move_spinner_default));
                    nameList.addAll(dataBase.getPokemonNameList(context, null, null));
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameList) {
                        @Override
                        public boolean isEnabled(int position) {
                            return !spnPokemon.getItemAtPosition(position).toString().contains(context.getString(R.string.dialog_link_move_spinner_default));
                        }
                    };
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnPokemon.setAdapter(adapterSpinner);
                    txtMovimento.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        try {
                            String metodo = tilMethod.getEditText() == null ? "" : tilMethod.getEditText().getText().toString();
                            List<String> pokemon = dataBase.getPokemonIdNameList(context, "NAME = ?", new String[]{spnPokemon.getSelectedItem().toString()}).get(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("POKEMON_ID", pokemon.get(0));
                            contentValues.put("MOVE_ID", id);
                            contentValues.put("METHOD", metodo);
                            if (dataBase.insert(context, "POKEMON_MOVE", "POKEMON_ID, MOVE_ID, METHOD", contentValues) == -1) {
                                Toast.makeText(context, pokemon.get(1) + " " + context.getString(R.string.dialog_link_move_failed) + " " + metodo, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.link_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, ViewMove.class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 1: {
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_move_to_type, (ViewGroup) v, false);
                    TextView txtMovimento = dialogV.findViewById(R.id.txtMovimento);
                    rcv = dialogV.findViewById(R.id.rcvType);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getTypes(context, null, null), dataBase.getMoveTypes(context, "MOVE_ID = ?", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtMovimento.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "MOVE_TYPE", "MOVE_ID = ?", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("MOVE_ID", id);
                                contentValues.put("TYPE_ID", item);
                                dataBase.insert(context, "MOVE_TYPE", "MOVE_ID, TYPE_ID", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewMove.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
            }
        }).create().show();
    }

    public void unlinkMoveDialog(Context context, View v, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        build.setIcon(R.drawable.unlink_icon);
        build.setTitle(context.getString(R.string.unlink_move_title));
        RecyclerView rcv;
        View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_unlink_move_to_pokemon, (ViewGroup) v, false);
        TextView txtMovimento = dialogV.findViewById(R.id.txtMovimento);
        rcv = dialogV.findViewById(R.id.rcvPokemon);
        rcv.setHasFixedSize(true);
        RcvTwoLinesLinkAdapter rcvTwoLinesLinkAdapter = new RcvTwoLinesLinkAdapter(context, dataBase.queryString(context, false, "POKEMON_MOVE AS PM INNER JOIN POKEMON AS P ON (P.ID = PM.POKEMON_ID) INNER JOIN MOVE AS M ON (M.ID = PM.MOVE_ID)", new String[]{"PM.POKEMON_ID||\";\"||PM.MOVE_ID||\";\"||METHOD", "P.NAME", "PM.METHOD"}, "MOVE_ID = ?", new String[]{id}, null, null, "P.NAME ASC", null), new ArrayList<>());
        rcv.setAdapter(rcvTwoLinesLinkAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(context));
        txtMovimento.setText(nome);
        build.setView(dialogV);
        build.setPositiveButton(R.string.unlink, (dialog1, which1) -> {
            List<String> list = rcvTwoLinesLinkAdapter.getSelected();
            for (String s : list) {
                try {
                    String[] array = s.split(";", 3);
                    dataBase.delete(context, "POKEMON_MOVE", "POKEMON_ID = ? AND MOVE_ID = ? AND METHOD = ?", new String[]{array[0], array[1], array[2]});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Intent intent = new Intent(context, ViewMove.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }).setNeutralButton(R.string.cancel, null).create().show();
    }

    public void linkPokemonDialog(Context context, View v, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        builder.setIcon(R.drawable.add_link_icon).setTitle(context.getString(R.string.link_));
        builder.setItems(context.getResources().getStringArray(R.array.array_link_pokemon_to), (dialog, which) -> {
            switch (which) {
                case 0: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_type, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    RecyclerView rcv = dialogV.findViewById(R.id.rcvTipos);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getTypes(context, null, null), dataBase.getPokemonTypes(context, "POKEMON_ID = ?", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_TYPE", "POKEMON_ID = ?", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", id);
                                contentValues.put("TYPE_ID", item);
                                dataBase.insert(context, "POKEMON_TYPE", "POKEMON_ID, TYPE_ID", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewPokemon.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 1: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_ability, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    RecyclerView rcv = dialogV.findViewById(R.id.rcvAbilities);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getAbilitiesIdName(context, "ID NOT IN (SELECT ABILITY_ID FROM POKEMON_ABILITY WHERE POKEMON_ID = ? AND HIDDEN = TRUE)", new String[]{id}), dataBase.getPokemonAbility(context, "POKEMON_ID = ? AND HIDDEN = FALSE", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_ABILITY", "POKEMON_ID = ? AND HIDDEN = FALSE", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", id);
                                contentValues.put("ABILITY_ID", item);
                                contentValues.put("HIDDEN", false);
                                dataBase.insert(context, "POKEMON_ABILITY", "POKEMON_ID, ABILITY_ID, HIDDEN", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewPokemon.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 2: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_ability, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    RecyclerView rcv = dialogV.findViewById(R.id.rcvAbilities);
                    rcv.setHasFixedSize(true);
                    RcvLinkAdapter rcvLinkAdapter = new RcvLinkAdapter(context, dataBase.getAbilitiesIdName(context, "ID NOT IN (SELECT ABILITY_ID FROM POKEMON_ABILITY WHERE POKEMON_ID = ? AND HIDDEN = FALSE)", new String[]{id}), dataBase.getPokemonAbility(context, "POKEMON_ID = ? AND HIDDEN = TRUE", new String[]{id}));
                    rcv.setAdapter(rcvLinkAdapter);
                    rcv.setLayoutManager(new LinearLayoutManager(context));
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        dataBase.delete(context, "POKEMON_ABILITY", "POKEMON_ID = ? AND HIDDEN = TRUE", new String[]{id});
                        List<String> list = rcvLinkAdapter.getSelected();
                        for (String item : list) {
                            try {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("POKEMON_ID", id);
                                contentValues.put("ABILITY_ID", item);
                                contentValues.put("HIDDEN", true);
                                dataBase.insert(context, "POKEMON_ABILITY", "POKEMON_ID, ABILITY_ID, HIDDEN", contentValues);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(context, ViewPokemon.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 3: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_move, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    TextInputLayout tilMethod = dialogV.findViewById(R.id.tilMethod);
                    Spinner spnMoves = dialogV.findViewById(R.id.spnMoves);
                    List<String> nameList = new ArrayList<>();
                    nameList.add(context.getString(R.string.dialog_link_pokemon_to_move_spinner_default));
                    nameList.addAll(dataBase.getMovesName(context, null, null));
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameList) {
                        @Override
                        public boolean isEnabled(int position) {
                            return !spnMoves.getItemAtPosition(position).toString().contains(context.getString(R.string.dialog_link_pokemon_to_move_spinner_default));
                        }
                    };
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnMoves.setAdapter(adapterSpinner);
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        try {
                            String metodo = tilMethod.getEditText() == null ? "" : tilMethod.getEditText().getText().toString();
                            List<String> move = dataBase.getMovesIdName(context, "NAME = ?", new String[]{spnMoves.getSelectedItem().toString()}).get(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("POKEMON_ID", id);
                            contentValues.put("MOVE_ID", move.get(0));
                            contentValues.put("METHOD", metodo);
                            if (dataBase.insert(context, "POKEMON_MOVE", "POKEMON_ID, MOVE_ID, METHOD", contentValues) == -1) {
                                Toast.makeText(context, nome + " " + context.getString(R.string.dialog_link_pokemon_to_move_failed) + " " + metodo, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.link_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, ViewPokemon.class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 4: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_evolution, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    TextInputLayout tilMethod = dialogV.findViewById(R.id.tilMethod);
                    Spinner spnPokemon = dialogV.findViewById(R.id.spnPokemon);
                    List<String> nameList = new ArrayList<>();
                    nameList.add(context.getString(R.string.dialog_link_pokemon_to_evolution_spinner_default));
                    nameList.addAll(dataBase.getPokemonNameList(context, "ID <> ? AND ID NOT IN (SELECT POKEMON2_ID FROM POKEMON_POKEMON WHERE POKEMON1_ID = ?)", new String[]{id, id}));
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameList) {
                        @Override
                        public boolean isEnabled(int position) {
                            return !spnPokemon.getItemAtPosition(position).toString().contains(context.getString(R.string.dialog_link_pokemon_to_evolution_spinner_default));
                        }
                    };
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnPokemon.setAdapter(adapterSpinner);
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        try {
                            String metodo = tilMethod.getEditText() == null ? "" : tilMethod.getEditText().getText().toString();
                            List<String> pokemon = dataBase.getPokemonIdNameList(context, "NAME = ?", new String[]{spnPokemon.getSelectedItem().toString()}).get(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("POKEMON1_ID", id);
                            contentValues.put("POKEMON2_ID", pokemon.get(0));
                            contentValues.put("METHOD", metodo);
                            if (dataBase.insert(context, "POKEMON_POKEMON", "POKEMON1_ID, POKEMON2_ID, METHOD", contentValues) == -1) {
                                Toast.makeText(context, context.getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.link_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, ViewPokemon.class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
                case 5: {
                    AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
                    build.setIcon(R.drawable.add_link_icon).setTitle(context.getResources().getStringArray(R.array.link_pokemon_title)[which]);
                    View dialogV = LayoutInflater.from(context).inflate(R.layout.dialog_link_pokemon_to_form, (ViewGroup) v, false);
                    TextView txtPokemon = dialogV.findViewById(R.id.txtPokemon);
                    TextInputLayout tilKind = dialogV.findViewById(R.id.tilKind);
                    TextInputLayout tilMethod = dialogV.findViewById(R.id.tilMethod);
                    Spinner spnPokemon = dialogV.findViewById(R.id.spnPokemon);
                    List<String> nameList = new ArrayList<>();
                    nameList.add(context.getString(R.string.dialog_link_pokemon_to_form_spinner_default));
                    nameList.addAll(dataBase.getPokemonNameList(context, "ID <> ? AND ID NOT IN (SELECT FORM_ID FROM POKEMON_FORM WHERE ORIGIN_ID = ?)", new String[]{id, id}));
                    ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameList) {
                        @Override
                        public boolean isEnabled(int position) {
                            return !spnPokemon.getItemAtPosition(position).toString().contains(context.getString(R.string.dialog_link_pokemon_to_form_spinner_default));
                        }
                    };
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnPokemon.setAdapter(adapterSpinner);
                    txtPokemon.setText(nome);
                    build.setView(dialogV);
                    build.setPositiveButton(R.string.link, (dialog1, which1) -> {
                        try {
                            String metodo = tilMethod.getEditText() == null ? "" : tilMethod.getEditText().getText().toString(), tipo = tilKind.getEditText() == null ? "" : tilKind.getEditText().getText().toString();
                            List<String> pokemon = dataBase.getPokemonIdNameList(context, "NAME = ?", new String[]{spnPokemon.getSelectedItem().toString()}).get(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("ORIGIN_ID", id);
                            contentValues.put("FORM_ID", pokemon.get(0));
                            contentValues.put("KIND", tipo);
                            contentValues.put("METHOD", metodo);
                            if (dataBase.insert(context, "POKEMON_FORM", "ORIGIN_ID, FORM_ID, KIND, METHOD", contentValues) == -1) {
                                Toast.makeText(context, context.getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.link_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, ViewPokemon.class);
                                intent.putExtra("id", id);
                                context.startActivity(intent);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }).setNeutralButton(R.string.cancel, null).create().show();
                }
                break;
            }
        }).create().show();
    }

    public void unlinkPokemonMoveDialog(Context context, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        build.setIcon(R.drawable.unlink_icon).setTitle(context.getString(R.string.dialog_unlink_pokemon_move_title));
        build.setMessage(context.getString(R.string.dialog_unlink_pokemon_move_message).concat("\n" + nome)).setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
            String[] keys = id.split(";", 3);
            dataBase.delete(context, "POKEMON_MOVE", "POKEMON_ID = ? AND MOVE_ID = ? AND METHOD = ?", new String[]{keys[0], keys[1], keys[2]});
            Intent intent = new Intent(context, ViewPokemon.class);
            intent.putExtra("id", keys[0]);
            context.startActivity(intent);
        }).setNeutralButton(context.getString(R.string.no), null).create().show();
    }

    public void unlinkPokemonEvolutionDialog(Context context, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        build.setIcon(R.drawable.unlink_icon).setTitle(context.getString(R.string.dialog_unlink_pokemon_evolution_title));
        build.setMessage(context.getString(R.string.dialog_unlink_pokemon_evolution_message).concat("\n" + nome)).setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
            String[] keys = id.split(";", 2);
            dataBase.delete(context, "POKEMON_POKEMON", "POKEMON1_ID = ? AND POKEMON2_ID = ?", new String[]{keys[0], keys[1]});
            Intent intent = new Intent(context, ViewPokemon.class);
            intent.putExtra("id", keys[0]);
            context.startActivity(intent);
        }).setNeutralButton(context.getString(R.string.no), null).create().show();
    }

    public void unlinkPokemonFormDialog(Context context, DataBase dataBase, String id, String nome) {
        AlertDialog.Builder build = new AlertDialog.Builder(context, R.style.EditAlertDialog);
        build.setIcon(R.drawable.unlink_icon).setTitle(context.getString(R.string.dialog_unlink_pokemon_form_title));
        build.setMessage(context.getString(R.string.dialog_unlink_pokemon_form_message).concat("\n" + nome)).setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
            String[] keys = id.split(";", 2);
            dataBase.delete(context, "POKEMON_FORM", "ORIGIN_ID = ? AND FORM_ID = ?", new String[]{keys[0], keys[1]});
            Intent intent = new Intent(context, ViewPokemon.class);
            intent.putExtra("id", keys[0]);
            context.startActivity(intent);
        }).setNeutralButton(context.getString(R.string.no), null).create().show();
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap getBitmapFromByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
