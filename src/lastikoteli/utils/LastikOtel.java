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
public class LastikOtel {
    private int id,lastik_taban,lastik_yanak,lastik_cap,adet;
    private String lastik_tarihi;
    private long giris_tarihi,cikis_tarihi;    
    private Musteri musteri;
    private DepoRaflari raf;
    private LastikMarka lastik_marka;
    private Kullanici kullanici;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastik_taban() {
        return lastik_taban;
    }

    public void setLastik_taban(int lastik_taban) {
        this.lastik_taban = lastik_taban;
    }

    public int getLastik_yanak() {
        return lastik_yanak;
    }

    public void setLastik_yanak(int lastik_yanak) {
        this.lastik_yanak = lastik_yanak;
    }

    public int getLastik_cap() {
        return lastik_cap;
    }

    public void setLastik_cap(int lastik_cap) {
        this.lastik_cap = lastik_cap;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public String getLastik_tarihi() {
        return lastik_tarihi;
    }

    public void setLastik_tarihi(String lastik_tarihi) {
        this.lastik_tarihi = lastik_tarihi;
    }

    public long getGiris_tarihi() {
        return giris_tarihi;
    }

    public void setGiris_tarihi(long giris_tarihi) {
        this.giris_tarihi = giris_tarihi;
    }

    public long getCikis_tarihi() {
        return cikis_tarihi;
    }

    public void setCikis_tarihi(long cikis_tarihi) {
        this.cikis_tarihi = cikis_tarihi;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public DepoRaflari getRaf() {
        return raf;
    }

    public void setRaf(DepoRaflari raf) {
        this.raf = raf;
    }

    public LastikMarka getLastik_marka() {
        return lastik_marka;
    }

    public void setLastik_marka(LastikMarka lastik_marka) {
        this.lastik_marka = lastik_marka;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }
    
    
}
