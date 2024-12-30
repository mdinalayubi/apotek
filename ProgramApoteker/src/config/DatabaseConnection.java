package config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.Statement;
 import java.sql.ResultSet;
 import javax.swing.JOptionPane;
 
 public class DatabaseConnection {
     
     private static final String URL = "jdbc:mysql://localhost:3306/pharmacy"; // Ubah dengan nama database kamu
     private static final String USER = "root"; // Ganti dengan username database
     private static final String PASSWORD = ""; // Ganti dengan password database kamu
 
     public static Connection connect() throws Exception {
         try {
             // Load JDBC driver
             Class.forName("com.mysql.cj.jdbc.Driver");
             // Establish connection
             return DriverManager.getConnection(URL, USER, PASSWORD);
         } catch (Exception e) {
             throw new Exception("Koneksi Gagal: " + e.getMessage());
         }
     }
 
     public static void main(String[] args) {
         try (Connection conn = connect()) {
             JOptionPane.showMessageDialog(null, "Koneksi berhasil!");
             Statement stmt = conn.createStatement();
             String query = "SELECT * FROM medicines"; // Contoh query untuk mengambil data obat
             ResultSet rs = stmt.executeQuery(query);
 
             while (rs.next()) {
                 System.out.println("ID Obat: " + rs.getInt("medicine_id"));
                 System.out.println("Nama Obat: " + rs.getString("name"));
                 System.out.println("Harga: " + rs.getDouble("price"));
                 System.out.println("Stok: " + rs.getInt("stock_quantity"));
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
         }
     }
 }
 
