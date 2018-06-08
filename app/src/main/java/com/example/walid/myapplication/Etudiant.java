package com.example.walid.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by walid on 18/02/2017.
 */

public class Etudiant implements Parcelable {
    private int id;
    private String option;
    private String nom;
    private String email;
    private int abs;
    private String avatar;

    public Etudiant(String nom, String email, String option, int abs, String avatar) {
        this.nom = nom;
        this.email = email;
        this.option = option;
        this.abs = abs;
        this.avatar = avatar;
    }

    public Etudiant(String nom, String email, String option, int abs) {
        this.nom = nom;
        this.email = email;
        this.option = option;
        this.abs = abs;
    }

    public Etudiant(int id, String nom, String email, String option, int abs) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.option = option;
        this.abs = abs;
    }

    protected Etudiant(Parcel in){
        id = in.readInt();
        nom = in.readString();
        email = in.readString();
        option = in.readString();
        abs = in.readInt();
    }

    public static final Parcelable.Creator<Etudiant> CREATOR = new Creator<Etudiant>() {
        @Override
        public Etudiant createFromParcel(Parcel source) {
            return new Etudiant(source);
        }

        @Override
        public Etudiant[] newArray(int size) {
            return new Etudiant[size];
        }
    };

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(email);
        dest.writeString(option);
        dest.writeInt(abs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(int abs) {
        this.email = email;
    }
    public int getAbs() {
        return abs;
    }

    public void setAbs(int abs) {
        this.abs = abs;
    }
    public void setAvatar(String imgUrl) {
        this.avatar = imgUrl;
    }

    public String getAvatar() {
        return avatar;
    }
}