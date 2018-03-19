/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lastikoteli.utils;

/**
 *
 * @author imac
 */
public class Musteri {
    
    private int id;
    private String ad,soyad,plaka,adres;
    private long ceptel, kayit_tarihi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public long getCeptel() {
        return ceptel;
    }

    public void setCeptel(long ceptel) {
        this.ceptel = ceptel;
    }

    public long getKayit_tarihi() {
        return kayit_tarihi;
    }

    public void setKayit_tarihi(long kayit_tarihi) {
        this.kayit_tarihi = kayit_tarihi;
    }
    
    
    
}
