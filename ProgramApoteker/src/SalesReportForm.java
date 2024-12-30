/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Laporan Penjualan
 * 
 * @author mdina
 */
public class SalesReportForm extends javax.swing.JFrame {
    private javax.swing.JTable reportTable;
    private javax.swing.table.DefaultTableModel reportTableModel;
    private javax.swing.JLabel totalSalesLabel;

    public SalesReportForm() {
        initComponents();
        loadSalesReport();
    }

    private void initComponents() {

        JPanel backgroundPanel = new BackgroundPanel("src/icons/background.jpg"); // Path ke gambar background

        // Set layout utama
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);
   
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Laporan Penjualan");

        // Header Panel
        javax.swing.JPanel headerPanel = new javax.swing.JPanel();
        headerPanel.setBackground(new java.awt.Color(51, 153, 255));
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Laporan Penjualan");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        titleLabel.setForeground(java.awt.Color.WHITE);
        headerPanel.add(titleLabel);

        // Tabel Laporan
        reportTable = new javax.swing.JTable();
        reportTableModel = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Tanggal", "Nama Produk", "Jumlah", "Harga", "Total"}
        );
        reportTable.setModel(reportTableModel);
        javax.swing.JScrollPane reportTableScrollPane = new javax.swing.JScrollPane(reportTable);

        // Filter Panel
        javax.swing.JPanel filterPanel = new javax.swing.JPanel();
        javax.swing.JLabel filterLabel = new javax.swing.JLabel("Filter Tanggal:");
        javax.swing.JTextField filterField = new javax.swing.JTextField(10);
        javax.swing.JButton filterButton = new javax.swing.JButton("Terapkan");
        filterButton.addActionListener(evt -> applyFilter(filterField.getText()));

        javax.swing.JButton exportButton = new javax.swing.JButton("Ekspor CSV");
        exportButton.addActionListener(evt -> exportToCSV());

        // Tambahkan tombol Hapus Laporan
        javax.swing.JButton deleteButton = new javax.swing.JButton("Hapus Laporan");
        deleteButton.addActionListener(evt -> deleteReport());

        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(exportButton);
        filterPanel.add(deleteButton);  // Menambahkan tombol Hapus

        // Footer Panel
        javax.swing.JPanel footerPanel = new javax.swing.JPanel();
        totalSalesLabel = new javax.swing.JLabel("Total Penjualan: Rp 0");
        totalSalesLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        footerPanel.add(totalSalesLabel);

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(filterPanel)
                    .addComponent(reportTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(footerPanel))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(filterPanel)
            .addGap(10, 10, 10)
            .addComponent(reportTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(footerPanel)
        );

        pack();
    }

    private void loadSalesReport() {
        try (Connection conn = config.DatabaseConnection.connect()) {
            String query = "SELECT * FROM sales_report";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                double totalSales = 0;
                reportTableModel.setRowCount(0); // Hapus data sebelumnya

                while (rs.next()) {
                    int saleId = rs.getInt("sale_id");
                    java.sql.Timestamp saleDate = rs.getTimestamp("sale_date");
                    String productName = rs.getString("product_name");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    double total = rs.getDouble("total");

                    totalSales += total;

                    Object[] row = {saleId, saleDate, productName, quantity, price, total};
                    reportTableModel.addRow(row);
                }
                totalSalesLabel.setText("Total Penjualan: Rp " + totalSales);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat laporan penjualan: " + e.getMessage());
        }
    }

    private void applyFilter(String date) {
        try (Connection conn = config.DatabaseConnection.connect()) {
            String query = "SELECT * FROM sales_report WHERE sale_date LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + date + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    reportTableModel.setRowCount(0); // Hapus data sebelumnya
                    double totalSales = 0;

                    while (rs.next()) {
                        int saleId = rs.getInt("sale_id");
                        java.sql.Timestamp saleDate = rs.getTimestamp("sale_date");
                        String productName = rs.getString("product_name");
                        int quantity = rs.getInt("quantity");
                        double price = rs.getDouble("price");
                        double total = rs.getDouble("total");

                        totalSales += total;

                        Object[] row = {saleId, saleDate, productName, quantity, price, total};
                        reportTableModel.addRow(row);
                    }
                    totalSalesLabel.setText("Total Penjualan: Rp " + totalSales);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menerapkan filter: " + e.getMessage());
        }
    }

    private void exportToCSV() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan sebagai CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            // Tambahkan ekstensi .csv jika belum ada
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".csv");
            }

            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
                // Tulis header
                for (int i = 0; i < reportTableModel.getColumnCount(); i++) {
                    fileWriter.write(reportTableModel.getColumnName(i));
                    if (i < reportTableModel.getColumnCount() - 1) {
                        fileWriter.write(",");
                    }
                }
                fileWriter.write("\n");

                // Tulis data
                for (int row = 0; row < reportTableModel.getRowCount(); row++) {
                    for (int col = 0; col < reportTableModel.getColumnCount(); col++) {
                        fileWriter.write(reportTableModel.getValueAt(row, col).toString());
                        if (col < reportTableModel.getColumnCount() - 1) {
                            fileWriter.write(",");
                        }
                    }
                    fileWriter.write("\n");
                }

                JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan sebagai " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan file: " + e.getMessage());
            }
        }
    }

    private void deleteReport() {
        int selectedRow = reportTable.getSelectedRow();

        if (selectedRow != -1) {
            // Mendapatkan ID laporan yang dipilih
            int saleId = (int) reportTableModel.getValueAt(selectedRow, 0);

            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin menghapus laporan dengan ID: " + saleId + "?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                try (Connection conn = config.DatabaseConnection.connect()) {
                    String query = "DELETE FROM sales_report WHERE sale_id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, saleId);
                        int rowsAffected = stmt.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Laporan berhasil dihapus.");
                            loadSalesReport(); // Muat ulang laporan setelah penghapusan
                        } else {
                            JOptionPane.showMessageDialog(this, "Laporan gagal dihapus.");
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus laporan: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih laporan yang ingin dihapus.");
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
        java.awt.EventQueue.invokeLater(() -> new SalesReportForm().setVisible(true));
    }
}
