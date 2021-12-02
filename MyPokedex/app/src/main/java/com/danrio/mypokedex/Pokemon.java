package com.danrio.mypokedex;

import android.os.Parcel;
import android.os.Parcelable;

public class Pokemon implements Parcelable {
    public static final Creator<Pokemon> CREATOR = new Creator<>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    private String name, species, category, region, gender, description;
    private double height, weight;
    private int id, dex, base_hp, base_atk, base_def, base_spatk, base_spdef, base_speed;
    private byte[] regular_photo, shiny_photo;

    public Pokemon(int id, int dex, String name, String species, String category, String region, double height, double weight, String gender, String description, int base_hp, int base_atk, int base_def, int base_spatk, int base_spdef, int base_speed, byte[] regular_photo, byte[] shiny_photo) {
        //this.id = id;
        setId(id);
        setDex(dex);
        setName(name);
        setSpecies(species);
        setCategory(category);
        setRegion(region);
        setHeight(height);
        setWeight(weight);
        setGender(gender);
        setDescription(description);
        setBase_hp(base_hp);
        setBase_atk(base_atk);
        setBase_def(base_def);
        setBase_spatk(base_spatk);
        setBase_spdef(base_spdef);
        setBase_speed(base_speed);
        setRegular_photo(regular_photo);
        setShiny_photo(shiny_photo);
    }

    protected Pokemon(Parcel in) {
        //id = in.readInt();
        setId(in.readInt());
        setDex(in.readInt());
        setName(in.readString());
        setSpecies(in.readString());
        setCategory(in.readString());
        setRegion(in.readString());
        setHeight(in.readDouble());
        setWeight(in.readDouble());
        setGender(in.readString());
        setDescription(in.readString());
        setBase_hp(in.readInt());
        setBase_atk(in.readInt());
        setBase_def(in.readInt());
        setBase_spatk(in.readInt());
        setBase_spdef(in.readInt());
        setBase_speed(in.readInt());
        setRegular_photo(in.createByteArray());
        setShiny_photo(in.createByteArray());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(id);
        dest.writeInt(getId());
        dest.writeInt(getDex());
        dest.writeString(getName());
        dest.writeString(getSpecies());
        dest.writeString(getCategory());
        dest.writeString(getRegion());
        dest.writeDouble(getHeight());
        dest.writeDouble(getWeight());
        dest.writeString(getGender());
        dest.writeString(getDescription());
        dest.writeInt(getBase_hp());
        dest.writeInt(getBase_atk());
        dest.writeInt(getBase_def());
        dest.writeInt(getBase_spatk());
        dest.writeInt(getBase_spdef());
        dest.writeInt(getBase_speed());
        dest.writeByteArray(getRegular_photo());
        dest.writeByteArray(getShiny_photo());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getBase_hp() {
        return base_hp;
    }

    public void setBase_hp(int base_hp) {
        this.base_hp = base_hp;
    }

    public int getBase_atk() {
        return base_atk;
    }

    public void setBase_atk(int base_atk) {
        this.base_atk = base_atk;
    }

    public int getBase_def() {
        return base_def;
    }

    public void setBase_def(int base_def) {
        this.base_def = base_def;
    }

    public int getBase_spatk() {
        return base_spatk;
    }

    public void setBase_spatk(int base_spatk) {
        this.base_spatk = base_spatk;
    }

    public int getBase_spdef() {
        return base_spdef;
    }

    public void setBase_spdef(int base_spdef) {
        this.base_spdef = base_spdef;
    }

    public int getBase_speed() {
        return base_speed;
    }

    public void setBase_speed(int base_speed) {
        this.base_speed = base_speed;
    }

    public byte[] getRegular_photo() {
        return regular_photo;
    }

    public void setRegular_photo(byte[] regular_photo) {
        this.regular_photo = regular_photo;
    }

    public byte[] getShiny_photo() {
        return shiny_photo;
    }

    public void setShiny_photo(byte[] shiny_photo) {
        this.shiny_photo = shiny_photo;
    }
}
