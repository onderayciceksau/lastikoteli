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
        if (drList.size() > 0) {
            d.setDepo_raflari(new ArrayList<>(drList));
            drList.clear();
            dList.add(d);
            d = null;
        }

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

    public void OtelCikis(int id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement("update lastik_oteli set cikis_tarihi=now() where id=?");
        psmt.setInt(1, id);
        psmt.executeUpdate();
        psmt.close();
    }

    public List<LastikOtel> getLastikOtelListe(String musteri, String plaka, Depolar depo, DepoRaflari raf) throws SQLException {
        String sql = "select * from lastik_oteli as a "
                + "inner join depo_raflari as b on a.raf_id=b.id "
                + "inner join depolar as c on b.depo_id=c.id "
                + "inner join musteri as d on d.id=a.musteri_id "
                + "where a.cikis_tarihi is null ";
        List<Object> param = new ArrayList<>();
        if (musteri != null) {
            if (musteri.length() >= 3) {
                sql += "and concat(d.ad,' ',d.soyad) like '?%' ";
                param.add(musteri);
            }
        }

        if (plaka != null) {
            if (plaka.length() >= 3) {
                sql += "and a.plaka like '?%' ";
                param.add(plaka);
            }
        }

        if (depo != null) {
            sql += "and c.id=? ";
            param.add(depo.getId());
        }

        if (raf != null) {
            sql += "and b.id=? ";
            param.add(raf.getId());
        }

        List<LastikOtel> oList = new ArrayList<>();
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement psmt = con.prepareStatement(sql);
        for (int p = 1; p <= param.size(); p++) {
            psmt.setObject(p, param.get(p - 1));
        }
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            LastikOtel lo = new LastikOtel();
            lo.setId(rs.getInt("otel_id"));
            lo.setAdet(rs.getInt("adet"));
            lo.setArac_plakasi(rs.getString("plaka"));
            lo.setGiris_tarihi(rs.getTimestamp("giris_tarihi").getTime());
            lo.setCikis_tarihi(rs.getTimestamp("cikis_tarihi").getTime());
            lo.setLastik_cap(rs.getInt("lastik_cap"));
            lo.setLastik_taban(rs.getInt("lastik_taban"));
            lo.setLastik_yanak(rs.getInt("lastik_yanak"));
            lo.setLastik_tarihi(rs.getString("lastik_tarihi"));
            Musteri musteri_obj = new Musteri();
            musteri_obj.setId(rs.getInt("musteri_id"));
            musteri_obj.setAd(rs.getString("ad"));
            musteri_obj.setSoyad(rs.getString("soyad"));
            lo.setMusteri(musteri_obj);
            LastikMarka lastik_marka_obj = new LastikMarka();
            lastik_marka_obj.setId(rs.getInt("marka_id"));
            lastik_marka_obj.setMarka_adi(rs.getString("marka_adi"));
            lo.setLastik_marka(lastik_marka_obj);
            DepoRaflari raf_obj = new DepoRaflari();
            raf_obj.setId(rs.getInt("raf_id"));
            raf_obj.setRaf_adi(rs.getString("raf_adi"));
            raf_obj.setDepo(new Depolar());
            raf_obj.getDepo().setId(rs.getInt("depo_id"));
            raf_obj.getDepo().setDepo_adi(rs.getString("depo_adi"));
            lo.setRaf(raf_obj);
            oList.add(lo);
        }
        rs.close();
        psmt.close();
        return oList;
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
