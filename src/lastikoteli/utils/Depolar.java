/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lastikoteli.utils;

import java.util.List;

/**
 *
 * @author imac
 */
public class Depolar {
    private int id;
    private String adres,depo_adi;
    private List<DepoRaflari> depo_raflari;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getDepo_adi() {
        return depo_adi;
    }

    public void setDepo_adi(String depo_adi) {
        this.depo_adi = depo_adi;
    }

    public List<DepoRaflari> getDepo_raflari() {
        return depo_raflari;
    }

    public void setDepo_raflari(List<DepoRaflari> depo_raflari) {
        this.depo_raflari = depo_raflari;
    }
    
    
}
