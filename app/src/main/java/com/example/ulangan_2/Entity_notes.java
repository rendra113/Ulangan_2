package com.example.ulangan_2;

public class Entity_notes {

    String id;
    String date;
    String judul;
    String deskripsi;

    public Entity_notes( String id,String date, String judul, String deskripsi) {
        this.id = id;
        this.date = date;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public Entity_notes(String idNotes, String penjudul, String dess){}

    public Entity_notes(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
