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
public class DepoRaflari {
    private int id,kapasite;
    private String raf_adi;
    private Depolar depo;

    public int getKapasite() {
        return kapasite;
    }

    public void setKapasite(int kapasite) {
        this.kapasite = kapasite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaf_adi() {
        return raf_adi;
    }

    public void setRaf_adi(String raf_adi) {
        this.raf_adi = raf_adi;
    }

    public Depolar getDepo() {
        return depo;
    }

    public void setDepo(Depolar depo) {
        this.depo = depo;
    }
    
    
}
