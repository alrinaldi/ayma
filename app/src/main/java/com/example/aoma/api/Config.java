package com.example.aoma.api;

public class Config {

    public static final String LOGIN_URL = "http://192.168.2.2/aoma/login.php";
    public static final String UPDATE_ASSET_URL = "http://192.168.2.2/aoma/update_asset.php?nomerassetbaru=";
    public static final String UPDATE_ASSET = "http://192.168.2.2/aoma/edit_asset.php";
    public static final String UPLOAD_FOTO = "http://192.168.2.2/aoma/upload_foto.php";
    public static final String VIEW_RESULT_URL = "http://192.168.2.2/aoma/view_asset.php";
    public static final String VIEW_MASTER_URL ="http://192.168.2.2/aoma/view_master.php";
    public static final String UPDATE_PARTNER_URL = "http://192.168.2.2/aoma/update_partner.php";
    public static final String VIEW_PARTNER_AKRIF = "http://192.168.2.2/aoma/view_partner.php";

    //Keys for nrp and password as defined in our $_POST['key'] in login.php
    public static final String KEY_NRP = "nrp";
    public static final String KEY_PASSWORD = "password";
        //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    public static final String KEY_NOMERASSETBARU = "nomerassetbaru";
    public static final String KEY_DEKSRIPSI = "deskripsi";
    public static final String KEY_WCT = "wct";
    public static final String KEY_KONDISI = "kondisi";
    public static final String KEY_LABEL = "label";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_NOMERASSETBARU = "nomerassetbaru";
    public static final String TAG_DESKRIPSI = "deskripsi";
    public static final String TAG_WCT = "wct";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_KONDISI = "kondisi";
    public static final String TAG_LABEL = "label";
    public static final String TAG_GAMBAR = "fotobaru";
    public static final String  NOMERASSET = "noasset";



}
