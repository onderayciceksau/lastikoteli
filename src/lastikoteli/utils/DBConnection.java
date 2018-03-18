/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lastikoteli.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author imac
 */
public class DBConnection {

    private Connection con;

    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/lastikoteli", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class LazyHolder {

        static final DBConnection INSTANCE = new DBConnection();
    }

    public static DBConnection getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Connection getConnection() {
        return con;
    }

}
