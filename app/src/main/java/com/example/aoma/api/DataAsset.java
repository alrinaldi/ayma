package com.example.aoma.api;

public class DataAsset {
    private String nomerassetlama,nomerassetbaru,kategori,deskripsi,workcenter;


DataAsset(String nomerassetlama,String nomerassetbaru,String kategori,String deskripsi, String workcenter){
    this.setNomerassetlama(nomerassetlama);
    this.setNomerassetbaru(nomerassetbaru);
    this.setKategori(kategori);
    this.setDeskripsi(deskripsi);
    this.setWorkcenter(workcenter);
}


    public String getNomerassetlama() {
        return nomerassetlama;
    }

    public void setNomerassetlama(String nomerassetlama) {
        this.nomerassetlama = nomerassetlama;
    }

    public String getNomerassetbaru() {
        return nomerassetbaru;
    }

    public void setNomerassetbaru(String nomerassetbaru) {
        this.nomerassetbaru = nomerassetbaru;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getWorkcenter() {
        return workcenter;
    }

    public void setWorkcenter(String workcenter) {
        this.workcenter = workcenter;
    }
}
