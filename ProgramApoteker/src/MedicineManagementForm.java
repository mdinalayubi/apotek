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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Form Manajemen Obat
 * 
 * @author mdina
 */
public class MedicineManagementForm extends javax.swing.JFrame {

    private javax.swing.JTable medicineTable;
    private javax.swing.table.DefaultTableModel tableModel;
    private javax.swing.JTextField searchField;

    /**
     * Konstruktor untuk membuat form Manajemen Obat.
     */
    public MedicineManagementForm() {
        initComponents();
        loadMedicines("");  // Memuat data obat dari database saat form dibuka
    }

    private void loadMedicines(String keyword) {
        // Mengisi tabel dengan data dari database
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT * FROM medicines WHERE name LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + keyword + "%");
                ResultSet rs = stmt.executeQuery();

                // Menghapus semua data lama di tabel
                tableModel.setRowCount(0);

                while (rs.next()) {
                    Object[] row = {
                        rs.getInt("medicine_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                    };
                    tableModel.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat obat: " + e.getMessage());
        }
    }

    /**
     * Inisialisasi komponen GUI.
     */
    private void initComponents() {

        JPanel backgroundPanel = new BackgroundPanel("src/icons/background.jpg"); // Path ke gambar background

        // Set layout utama
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);
        
        setTitle("Manajemen Obat");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Judul Form
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Manajemen Obat");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18));

        // Field pencarian
        javax.swing.JLabel searchLabel = new javax.swing.JLabel("Cari Obat:");
        searchField = new javax.swing.JTextField(20);
        javax.swing.JButton searchButton = new javax.swing.JButton("Cari");
        searchButton.addActionListener(evt -> loadMedicines(searchField.getText()));

        // Tombol Refresh
        javax.swing.JButton refreshButton = new javax.swing.JButton("Refresh");
        refreshButton.addActionListener(evt -> loadMedicines(""));

        // Tabel untuk menampilkan data obat
        medicineTable = new javax.swing.JTable();
        tableModel = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nama Obat", "Harga", "Stok"}
        );
        medicineTable.setModel(tableModel);
        javax.swing.JScrollPane tableScrollPane = new javax.swing.JScrollPane(medicineTable);

        // Tombol untuk fitur
        javax.swing.JButton addButton = new javax.swing.JButton("Tambah Obat");
        addButton.addActionListener(evt -> addMedicineActionPerformed());

        javax.swing.JButton editButton = new javax.swing.JButton("Edit Obat");
        editButton.addActionListener(evt -> editMedicineActionPerformed());

        javax.swing.JButton deleteButton = new javax.swing.JButton("Hapus Obat");
        deleteButton.addActionListener(evt -> deleteMedicineActionPerformed());

        // Panel tombol dan pencarian
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.add(searchLabel);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Layout GUI
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(titleLabel)
                    .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPanel))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addGap(20, 20, 20)
                .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(buttonPanel)
                .addGap(20, 20, 20))
        );

        pack();
    }

    /**
     * Tambah data obat baru.
     */
    private void addMedicineActionPerformed() {
        String name = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Nama Obat:");
        String price = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Harga Obat:");
        String stock = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Stok Obat:");

        if (name != null && price != null && stock != null) {
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "INSERT INTO medicines (name, price, stock_quantity) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setDouble(2, Double.parseDouble(price));
                    stmt.setInt(3, Integer.parseInt(stock));
                    stmt.executeUpdate();

                    // Setelah menambah data, muat ulang data dari database
                    loadMedicines("");
                    JOptionPane.showMessageDialog(this, "Obat berhasil ditambahkan.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            }
        }
    }

    /**
     * Edit data obat yang dipilih.
     */
    private void editMedicineActionPerformed() {
        int selectedRow = medicineTable.getSelectedRow();

        if (selectedRow != -1) {
            int id = (Integer) tableModel.getValueAt(selectedRow, 0);
            String name = javax.swing.JOptionPane.showInputDialog(this, "Edit Nama Obat:", tableModel.getValueAt(selectedRow, 1));
            String price = javax.swing.JOptionPane.showInputDialog(this, "Edit Harga Obat:", tableModel.getValueAt(selectedRow, 2));
            String stock = javax.swing.JOptionPane.showInputDialog(this, "Edit Stok Obat:", tableModel.getValueAt(selectedRow, 3));

            if (name != null && price != null && stock != null) {
                try (Connection conn = DatabaseConnection.connect()) {
                    String query = "UPDATE medicines SET name = ?, price = ?, stock_quantity = ? WHERE medicine_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, name);
                        stmt.setDouble(2, Double.parseDouble(price));
                        stmt.setInt(3, Integer.parseInt(stock));
                        stmt.setInt(4, id);
                        stmt.executeUpdate();

                        // Setelah mengedit data, muat ulang data dari database
                        loadMedicines("");
                        JOptionPane.showMessageDialog(this, "Obat berhasil diedit.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris untuk diedit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Hapus data obat yang dipilih.
     */
    private void deleteMedicineActionPerformed() {
        int selectedRow = medicineTable.getSelectedRow();

        if (selectedRow != -1) {
            int id = (Integer) tableModel.getValueAt(selectedRow, 0);
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "DELETE FROM medicines WHERE medicine_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();

                    // Setelah menghapus data, muat ulang data dari database
                    loadMedicines("");
                    JOptionPane.showMessageDialog(this, "Obat berhasil dihapus.");
                }
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


    /**
     * Jalankan form.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MedicineManagementForm().setVisible(true));
    }
}
