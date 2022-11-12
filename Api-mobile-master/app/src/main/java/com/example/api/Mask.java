package com.example.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int Id_zakaz;
    private String Users;
    private String Nazvanie;
    private String Zena;
    private String Image;

    public Mask(int Id_zakaz, String Users, String Nazvanie, String Zena, String Image) {
        this.Id_zakaz = Id_zakaz;
        this.Users = Users;
        this.Nazvanie = Nazvanie;
        this.Zena = Zena;
        this.Image = Image;
    }

    protected Mask(Parcel in) {
        Id_zakaz = in.readInt();
        Users = in.readString();
        Nazvanie = in.readString();
        Zena = in.readString();
        Image = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };


    public void setId_zakaz(int Id_zakaz) {
        this.Id_zakaz = Id_zakaz;
    }
    public void setUsers(String Users) {
        this.Users = Users;
    }
    public void setNazvanie(String Nazvanie) {
        this.Nazvanie = Nazvanie;
    }
    public void setZena(String Zena) {
        this.Zena = Zena;
    }
    public void setImage(String Image) {
        this.Image = Image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id_zakaz);
        parcel.writeString(Users);
        parcel.writeString(Nazvanie);
        parcel.writeString(Zena);
        parcel.writeString(Image);
    }

    public int getId_zakaz() {
        return Id_zakaz;
    }

    public String getUsers() {
        return Users;
    }

    public String getNazvanie() {
        return Nazvanie;
    }

    public String getZena() {
        return Zena;
    }

    public String getImage() {
        return Image;
    }
}
