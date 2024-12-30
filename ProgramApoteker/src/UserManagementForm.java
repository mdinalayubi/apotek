/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mdina
 */
import config.DatabaseConnection;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class UserManagementForm extends javax.swing.JFrame {

    private javax.swing.JTable userTable;
    private javax.swing.table.DefaultTableModel tableModel;

    public UserManagementForm() {
        initComponents();
        loadUsers();  // Memuat data pengguna dari database saat form dibuka
    }

    private void loadUsers() {
        // Mengisi tabel dengan data pengguna dari database
        try (Connection conn = config.DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(query);

            // Menghapus semua data lama di tabel
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("role")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data pengguna: " + e.getMessage());
        }
    }

    private void initComponents() {

        JPanel backgroundPanel = new BackgroundPanel("src/icons/background.jpg"); // Path ke gambar background

        // Set layout utama
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manajemen Pengguna");

        javax.swing.JLabel jLabel = new javax.swing.JLabel("Form Manajemen Pengguna");
        javax.swing.JButton addButton = new javax.swing.JButton("Tambah Pengguna");
        javax.swing.JButton editButton = new javax.swing.JButton("Edit Pengguna");
        javax.swing.JButton deleteButton = new javax.swing.JButton("Hapus Pengguna");

        // Tabel untuk menampilkan data pengguna
        userTable = new javax.swing.JTable();
        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Username", "Role"}
        );
        userTable.setModel(tableModel);
        javax.swing.JScrollPane tableScrollPane = new javax.swing.JScrollPane(userTable);

        addButton.addActionListener(evt -> addUserActionPerformed());
        editButton.addActionListener(evt -> editUserActionPerformed());
        deleteButton.addActionListener(evt -> deleteUserActionPerformed());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel)
                    .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel)
                .addGap(18, 18, 18)
                .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(editButton)
                    .addComponent(deleteButton))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    private void addUserActionPerformed() {
        String username = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Username:");
        String password = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Password:");
        String role = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Role (Admin/User):");

        if (username != null && password != null && role != null) {
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.executeUpdate();

                loadUsers();  // Memuat ulang data pengguna
                JOptionPane.showMessageDialog(this, "Pengguna berhasil ditambahkan.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    private void editUserActionPerformed() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow != -1) {
            int id = (Integer) tableModel.getValueAt(selectedRow, 0);
            String username = javax.swing.JOptionPane.showInputDialog(this, "Edit Username:", tableModel.getValueAt(selectedRow, 1));
            String password = javax.swing.JOptionPane.showInputDialog(this, "Edit Password:", tableModel.getValueAt(selectedRow, 2));
            String role = javax.swing.JOptionPane.showInputDialog(this, "Edit Role:", tableModel.getValueAt(selectedRow, 3));

            if (username != null && password != null && role != null) {
                try (Connection conn = DatabaseConnection.connect()) {
                    String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE user_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setString(3, role);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();

                    loadUsers();  // Memuat ulang data pengguna
                    JOptionPane.showMessageDialog(this, "Pengguna berhasil diedit.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk diedit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUserActionPerformed() {
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow != -1) {
            int id = (Integer) tableModel.getValueAt(selectedRow, 0);
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
                stmt.executeUpdate();

                loadUsers();  // Memuat ulang data pengguna
                JOptionPane.showMessageDialog(this, "Pengguna berhasil dihapus.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        // Load gambar sebagai background
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserManagementForm().setVisible(true);
            }
        });
    }
}
