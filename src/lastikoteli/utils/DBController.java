/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lastikoteli.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author imac
 */
public class DBController {

    private DBController() {

    }

    private static class LazyHolder {

        static final DBController INSTANCE = new DBController();
    }

    public static DBController getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void depoKayit(Depolar d) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("insert into depolar(depo_adi,adres)values(?,?)");
        psmt.setString(1, d.getDepo_adi());
        psmt.setString(2, d.getAdres());
        psmt.executeUpdate();
        psmt.close();
    }

    public void depoRaflariKayit(DepoRaflari dr) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("insert into depo_raflari(raf_adi,depo_id,kapasite)values(?,?,?)");
        psmt.setString(1, dr.getRaf_adi());
        psmt.setInt(2, dr.getDepo().getId());
        psmt.setInt(3, dr.getKapasite());
        psmt.executeUpdate();
        psmt.close();
    }

    public List<Depolar> getDepolar() throws SQLException {
        List<Depolar> dList = new ArrayList<>();
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("select d.*,dr.id as rafid,dr.kapasite,dr.raf_adi from depolar d left join depo_raflari dr on d.id=dr.depo_id order by d.id");
        ResultSet rs = psmt.executeQuery();
        Depolar d = null;
        ArrayList<DepoRaflari> drList = new ArrayList<>();
        while (rs.next()) {
            if (d != null) {
                if (d.getId() != rs.getInt("id")) {
                    d.setDepo_raflari(new ArrayList<>(drList));
                    drList.clear();
                    dList.add(d);
                    d = null;
                }
            }
            if (d == null) {
                d = new Depolar();
                d.setId(rs.getInt("id"));
                d.setDepo_adi(rs.getString("depo_adi"));
                d.setAdres(rs.getString("adres"));

            }
            DepoRaflari dr = new DepoRaflari();
            dr.setId(rs.getInt("rafid"));
            dr.setRaf_adi(rs.getString("raf_adi"));
            dr.setKapasite(rs.getInt("kapasite"));
            dr.setDepo(d);
            drList.add(dr);

        }

        d.setDepo_raflari(new ArrayList<>(drList));
        drList.clear();
        dList.add(d);
        d = null;

        rs.close();
        psmt.close();
        return dList;
    }

    public void lastikOtelKayit(LastikOtel lo) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("insert into lastik_oteli(giris_tarihi,cikis_tarihi,kullanici_id,lastik_taban,lastik_yanak,lastik_cap,lastik_marka,lastik_tarihi,arac_plaka,adet,musteri_id,raf_id)"
                + "values(now(),null,?,?,?,?,?,?,?,?,?)");
        psmt.setInt(1, lo.getKullanici().getId());
        psmt.setInt(2, lo.getLastik_taban());
        psmt.setInt(3, lo.getLastik_yanak());
        psmt.setInt(4, lo.getLastik_cap());
        psmt.setInt(5, lo.getLastik_marka().getId());
        psmt.setString(6, lo.getLastik_tarihi());
        psmt.setString(7, lo.getArac_plakasi());
        psmt.setInt(8, lo.getAdet());
        psmt.setInt(9, lo.getMusteri().getId());
        psmt.setInt(10, lo.getRaf().getId());        
        psmt.executeUpdate();
        psmt.close();
    }

    public void lastikMarkaKayit(LastikMarka lm) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("insert into lastik_marka(marka_adi)values(?)");
        psmt.setString(1, lm.getMarka_adi());
        psmt.executeUpdate();
        psmt.close();
    }

    public void musteriKayit(Musteri m) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("insert into musteri(ad,soyad,ceptel,adres,kayit_tarihi,durum)values(?,?,?,?,now(),1)");
        psmt.setString(1, m.getAd());
        psmt.setString(2, m.getSoyad());
        psmt.setLong(3, m.getCeptel());
        psmt.setString(4, m.getAdres());
        psmt.executeUpdate();
        psmt.close();
    }
    
    public List<Musteri> getMusteriler() throws SQLException {
        List<Musteri> mList = new ArrayList<>();
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("select * from musteri");
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            Musteri m = new Musteri();
            m.setId(rs.getInt("id"));
            m.setAd(rs.getString("ad"));
            m.setSoyad(rs.getString("soyad"));
            m.setAdres(rs.getString("adres"));
            mList.add(m);
        }
        rs.close();
        psmt.close();
        return mList;
    }

    public List<LastikMarka> getMarkalar() throws SQLException {
        List<LastikMarka> mList = new ArrayList<>();
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("select * from lastik_marka");
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            LastikMarka m = new LastikMarka();
            m.setId(rs.getInt("id"));
            m.setMarka_adi(rs.getString("marka_adi"));
            mList.add(m);
        }
        rs.close();
        psmt.close();
        return mList;
    }

    public int getDepoDoluluk(int rafid) {
        int retObj = 0;
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement psmt = con.prepareStatement("select sum(adet) as sayi from lastik_oteli where raf_id=?");
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                retObj = rs.getInt("sayi");
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {

        }
        return retObj;
    }
}
