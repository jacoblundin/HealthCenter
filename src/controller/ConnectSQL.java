package controller;

import java.sql.*;

public class ConnectSQL {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;integratedSecurity=true;databaseName=Clinic");
            System.out.println("Connected\n**********");
            new MenuController(conn);

        }
        catch (SQLException e) {
            System.out.println("Failed to Connect");
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                System.out.println("Failed to close connection");
            }
            System.out.println("**********\nConnection closed");
        }
    }
}
