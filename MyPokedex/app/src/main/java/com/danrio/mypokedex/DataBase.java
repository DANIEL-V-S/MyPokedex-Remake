package com.danrio.mypokedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private final JavaMethods jm = new JavaMethods();
    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public void setDatabase(SQLiteDatabase db) {
        this.db = db;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public SQLiteDatabase openDatabase(@NonNull Context context) {
        return context.openOrCreateDatabase("mypokedex.db", Context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {
        if (getCursor() != null && !getCursor().isClosed()) {
            getCursor().close();
        }
        if (getDatabase() != null && getDatabase().isOpen()) {
            getDatabase().close();
        }
    }

    public long insert(Context context, String tabela, String colunas, ContentValues dados) {
        setDatabase(openDatabase(context));
        long rows = getDatabase().insert(tabela, colunas, dados);
        closeDatabase();
        return rows;
    }

    public long insertDB(@NonNull SQLiteDatabase database, String tabela, String colunas, ContentValues dados) {
        return database.insert(tabela, colunas, dados);
    }

    public int insertAll(Context context, String tabela, String colunas, @NonNull List<ContentValues> dados) {
        setDatabase(openDatabase(context));
        int rows = 0;
        for (ContentValues values : dados) {
            try {
                getDatabase().insert(tabela, colunas, values);
                rows++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        closeDatabase();
        return rows;
    }

    public int insertAllDB(@NonNull SQLiteDatabase database, String tabela, String colunas, @NonNull List<ContentValues> dados) {
        int rows = 0;
        for (ContentValues values : dados) {
            try {
                database.insert(tabela, colunas, values);
                rows++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return rows;
    }

    public long replace(Context context, String tabela, String colunas, ContentValues dados) {
        setDatabase(openDatabase(context));
        long row = getDatabase().replace(tabela, colunas, dados);
        closeDatabase();
        return row;
    }

    public long replaceDB(@NonNull SQLiteDatabase database, String tabela, String colunas, ContentValues dados) {
        return database.replace(tabela, colunas, dados);
    }

    public int replaceAll(Context context, String tabela, String colunas, @NonNull List<ContentValues> dados) {
        setDatabase(openDatabase(context));
        int rows = 0;
        for (ContentValues values : dados) {
            try {
                getDatabase().replace(tabela, colunas, values);
                rows++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        closeDatabase();
        return rows;
    }

    public int replaceAllDB(@NonNull SQLiteDatabase database, String tabela, String colunas, @NonNull List<ContentValues> dados) {
        int rows = 0;
        for (ContentValues values : dados) {
            try {
                database.replace(tabela, colunas, values);
                rows++;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return rows;
    }

    public int update(Context context, String tabela, ContentValues dados, String where, String[] dadosWhere) {
        setDatabase(openDatabase(context));
        int rows = getDatabase().update(tabela, dados, where, dadosWhere);
        closeDatabase();
        return rows;
    }

    public int updateDB(@NonNull SQLiteDatabase database, String tabela, ContentValues dados, String where, String[] dadosWhere) {
        return database.update(tabela, dados, where, dadosWhere);
    }

    public int delete(Context context, String tabela, String where, String[] dadosWhere) {
        setDatabase(openDatabase(context));
        int rows = getDatabase().delete(tabela, where, dadosWhere);
        closeDatabase();
        return rows;
    }

    public int deleteDB(@NonNull SQLiteDatabase database, String tabela, String where, String[] dadosWhere) {
        return database.delete(tabela, where, dadosWhere);
    }

    public Cursor getCursorValue(@NonNull SQLiteDatabase database, boolean distinct, String tabela, String[] colunas, String selecao, String[] dadosSelecao, String groupBy, String having, String orderBy, String limit) {
        return database.query(distinct, tabela, colunas, selecao, dadosSelecao, groupBy, having, orderBy, limit);
    }

    public List<List<String>> queryString(Context context, boolean distinct, String tabela, String[] colunas, String where, String[] dadosWhere, String groupBy, String having, String orderBy, String limit) {
        List<List<String>> select = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), distinct, tabela, colunas, where, dadosWhere, groupBy, having, orderBy, limit));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    select.add(new ArrayList<>());
                    for (int i = 0; i < getCursor().getColumnCount(); i++) {
                        if (getCursor().getString(i) != null) {
                            select.get(getCursor().getPosition()).add(getCursor().getString(i));
                        } else {
                            select.get(getCursor().getPosition()).add("");
                        }
                    }
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return select;
    }

    public void create(Context context) {
        SQLiteStatement stmt;
        setDatabase(openDatabase(context));
        List<String> queryCreate = new ArrayList<>();
        queryCreate.add("CREATE TABLE IF NOT EXISTS SETTINGS (NAME VARCHAR(25) DEFAULT '', PRONOUN INTEGER DEFAULT 0);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON (ID INTEGER PRIMARY KEY, DEX INTEGER NOT NULL, NAME VARCHAR(50) UNIQUE NOT NULL, REGULAR_PHOTO BLOB, SHINY_PHOTO BLOB, SPECIES VARCHAR(50), CATEGORY VARCHAR(50), REGION VARCHAR(50), HEIGHT FLOAT, WEIGHT FLOAT, GENDER VARCHAR(50), DESCRIPTION VARCHAR(512), BASE_HP INTEGER, BASE_ATK INTEGER, BASE_DEF INTEGER, BASE_SPATK INTEGER, BASE_SPDEF INTEGER, BASE_SPEED INTEGER);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS TYPE (ID INTEGER PRIMARY KEY, NAME VARCHAR(50) UNIQUE NOT NULL);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS MOVE (ID INTEGER PRIMARY KEY, NAME VARCHAR(50) UNIQUE NOT NULL, DESCRIPTION VARCHAR(512), POWER INTEGER DEFAULT 0, ACCURACY INTEGER, PP INTEGER DEFAULT 0, PRIORITY INTEGER DEFAULT 0, CATEGORY VARCHAR(50));");
        queryCreate.add("CREATE TABLE IF NOT EXISTS ABILITY (ID INTEGER PRIMARY KEY, NAME VARCHAR(50) UNIQUE NOT NULL, DESCRIPTION VARCHAR(512));");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON_POKEMON (POKEMON1_ID INTEGER, POKEMON2_ID INTEGER, METHOD VARCHAR(100), PRIMARY KEY (POKEMON1_ID, POKEMON2_ID), FOREIGN KEY (POKEMON1_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE, FOREIGN KEY (POKEMON2_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON_FORM (ORIGIN_ID INTEGER, FORM_ID INTEGER, KIND VARCHAR(50), METHOD VARCHAR(100), PRIMARY KEY (ORIGIN_ID, FORM_ID), FOREIGN KEY (ORIGIN_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE, FOREIGN KEY (FORM_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON_MOVE (POKEMON_ID INTEGER, MOVE_ID INTEGER, METHOD VARCHAR(50), PRIMARY KEY (POKEMON_ID, MOVE_ID, METHOD), FOREIGN KEY (POKEMON_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE, FOREIGN KEY (MOVE_ID) REFERENCES MOVE (ID) ON DELETE CASCADE);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON_ABILITY (POKEMON_ID INTEGER, ABILITY_ID INTEGER, HIDDEN BOOLEAN, PRIMARY KEY (POKEMON_ID, ABILITY_ID), FOREIGN KEY (POKEMON_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE, FOREIGN KEY (ABILITY_ID) REFERENCES ABILITY (ID) ON DELETE CASCADE);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS POKEMON_TYPE (POKEMON_ID INTEGER, TYPE_ID INTEGER, PRIMARY KEY (POKEMON_ID, TYPE_ID), FOREIGN KEY (POKEMON_ID) REFERENCES POKEMON (ID) ON DELETE CASCADE, FOREIGN KEY (TYPE_ID) REFERENCES TYPE (ID) ON DELETE CASCADE);");
        queryCreate.add("CREATE TABLE IF NOT EXISTS MOVE_TYPE (MOVE_ID INTEGER, TYPE_ID INTEGER, PRIMARY KEY (MOVE_ID, TYPE_ID), FOREIGN KEY (MOVE_ID) REFERENCES MOVE (ID) ON DELETE CASCADE, FOREIGN KEY (TYPE_ID) REFERENCES TYPE (ID) ON DELETE CASCADE);");
        for (String str : queryCreate) {
            stmt = getDatabase().compileStatement(str);
            stmt.execute();
        }
        closeDatabase();
        setSettings(context);
    }

    public void deleteDatabase(Context context) {
        closeDatabase();
        context.deleteDatabase("mypokedex.db");
    }

    private void setSettings(Context context) {
        List<List<String>> select = queryString(context, false, "SETTINGS", new String[]{"NAME"}, null, null, null, null, null, null);
        if (select.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", context.getString(R.string.nav_header_subtitle_0));
            contentValues.put("PRONOUN", 0);
            insert(context, "SETTINGS", "NAME, PRONOUN", contentValues);
        }
    }

    public List<String> getSettings(Context context) {
        return queryString(context, false, "SETTINGS", new String[]{"NAME", "PRONOUN"}, null, null, null, null, null, null).get(0);
    }

    public int getNewId(Context context, String tabela) {
        return Integer.parseInt(queryString(context, false, tabela, new String[]{"COALESCE(MAX(ID)+1, 1)"}, null, null, null, null, null, null).get(0).get(0));
    }

    public List<List<String>> getTypes(Context context, String where, String[] dadosWhere) {
        return queryString(context, false, "TYPE", new String[]{"ID", "NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
    }

    public List<String> getTypesName(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, false, "TYPE", new String[]{"NAME"}, where, dadosWhere, null, null, null, null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<List<String>> getAbilities(Context context, String where, String[] dadosWhere) {
        return queryString(context, false, "ABILITY", new String[]{"ID", "NAME", "DESCRIPTION"}, where, dadosWhere, null, null, null, null);
    }

    public List<List<String>> getAbilitiesIdName(Context context, String where, String[] dadosWhere) {
        return queryString(context, false, "ABILITY", new String[]{"ID", "NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
    }

    public List<String> getAbilitiesName(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, false, "ABILITY", new String[]{"NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<String> getAbilityPokemon(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "POKEMON_ABILITY", new String[]{"POKEMON_ID", "ABILITY_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(0));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<String> getPokemonAbility(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "POKEMON_ABILITY", new String[]{"POKEMON_ID", "ABILITY_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(1));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<List<String>> getMoves(Context context, String where, String[] dadosWhere) {
        return queryString(context, false, "MOVE", new String[]{"ID", "NAME", "DESCRIPTION", "POWER", "ACCURACY", "PP", "PRIORITY", "CATEGORY"}, where, dadosWhere, null, null, null, null);
    }

    public List<List<String>> getMovesIdName(Context context, String where, String[] dadosWhere) {
        return queryString(context, false, "MOVE", new String[]{"ID", "NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
    }

    public List<String> getMovesName(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, false, "MOVE", new String[]{"NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public Pokemon getPokemon(Context context, String where, String[] dadosWhere) {
        Pokemon pokemon = getJm().blankPokemon(context);
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), true, "POKEMON", new String[]{"ID", "DEX", "NAME", "REGULAR_PHOTO", "SHINY_PHOTO", "SPECIES", "CATEGORY", "REGION", "HEIGHT", "WEIGHT", "GENDER", "DESCRIPTION", "BASE_HP", "BASE_ATK", "BASE_DEF", "BASE_SPATK", "BASE_SPDEF", "BASE_SPEED"}, where, dadosWhere, null, null, null, "1"));
        try {
            if (getCursor().moveToFirst()) {
                pokemon.setId(getCursor().getInt(0));
                pokemon.setDex(getCursor().getInt(1));
                pokemon.setName(getCursor().getString(2));
                pokemon.setRegular_photo(getCursor().getBlob(3));
                pokemon.setShiny_photo(getCursor().getBlob(4));
                pokemon.setSpecies(getCursor().getString(5));
                pokemon.setCategory(getCursor().getString(6));
                pokemon.setRegion(getCursor().getString(7));
                pokemon.setHeight(getCursor().getDouble(8));
                pokemon.setWeight(getCursor().getDouble(9));
                pokemon.setGender(getCursor().getString(10));
                pokemon.setDescription(getCursor().getString(11));
                pokemon.setBase_hp(getCursor().getInt(12));
                pokemon.setBase_atk(getCursor().getInt(13));
                pokemon.setBase_def(getCursor().getInt(14));
                pokemon.setBase_spatk(getCursor().getInt(15));
                pokemon.setBase_spdef(getCursor().getInt(16));
                pokemon.setBase_speed(getCursor().getInt(17));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return pokemon;
    }

    public List<String> getPokemonTypes(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "POKEMON_TYPE", new String[]{"POKEMON_ID", "TYPE_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(1));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<Integer> getIntPokemonTypes(Context context, String where, String[] dadosWhere) {
        List<Integer> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "POKEMON_TYPE", new String[]{"POKEMON_ID", "TYPE_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getInt(1));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<String> getTypePokemon(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "POKEMON_TYPE", new String[]{"POKEMON_ID", "TYPE_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(0));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<String> getTypeMoves(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "MOVE_TYPE", new String[]{"MOVE_ID", "TYPE_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(0));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<String> getMoveTypes(Context context, String where, String[] dadosWhere) {
        List<String> list = new ArrayList<>();
        setDatabase(openDatabase(context));
        setCursor(getCursorValue(getDatabase(), false, "MOVE_TYPE", new String[]{"MOVE_ID", "TYPE_ID"}, where, dadosWhere, null, null, null, null));
        try {
            if (getCursor().moveToFirst()) {
                do {
                    list.add(getCursor().getString(1));
                } while (getCursor().moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeDatabase();
        }
        return list;
    }

    public List<String> getPokemonDexList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"DEX"}, where, dadosWhere, null, null, "DEX ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<List<String>> getPokemonIdNameList(Context context, String where, String[] dadosWhere) {
        return queryString(context, true, "POKEMON", new String[]{"ID", "NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
    }

    public List<String> getPokemonNameList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"NAME"}, where, dadosWhere, null, null, "NAME ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<String> getPokemonSpeciesList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"SPECIES"}, where, dadosWhere, null, null, "SPECIES ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<String> getPokemonCategoryList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"CATEGORY"}, where, dadosWhere, null, null, "CATEGORY ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<String> getPokemonRegionList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"REGION"}, where, dadosWhere, null, null, "REGION ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public List<String> getPokemonGenderList(Context context, String where, String[] dadosWhere) {
        List<List<String>> select = queryString(context, true, "POKEMON", new String[]{"GENDER"}, where, dadosWhere, null, null, "GENDER ASC", null);
        List<String> list = new ArrayList<>();
        for (List<String> stringList : select) {
            list.add(stringList.get(0));
        }
        return list;
    }

    public void importTypes(Context context, View v, List<String> types) {
        try {
            List<ContentValues> contents = new ArrayList<>();
            for (String s : types) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("NAME", s);
                contents.add(contentValues);
            }
            int rows = insertAll(context, "TYPE", "NAME", contents);
            if (rows == types.size()) {
                Snackbar.make(v, context.getString(R.string.settings_btnImportOriginal_results_0), Snackbar.LENGTH_LONG).show();
            } else if (rows > 0) {
                Snackbar.make(v, context.getString(R.string.settings_btnImportOriginal_results_1), Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(v, context.getString(R.string.settings_btnImportOriginal_results_2), Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
