/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mdina
 */

import config.DatabaseConnection;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CashierForm extends javax.swing.JFrame {

    private javax.swing.JTable productTable;
    private javax.swing.JTable cartTable;
    private javax.swing.table.DefaultTableModel productTableModel;
    private javax.swing.table.DefaultTableModel cartTableModel;
    private javax.swing.JLabel totalLabel;

    public CashierForm() {
        initComponents();
        loadProducts(); // Memuat produk dari database
    }

    private void initComponents() {

        JPanel backgroundPanel = new BackgroundPanel("src/icons/background.jpg"); // Path ke gambar background

        // Set layout utama
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kasir - Apotek Modern");
        getContentPane().setBackground(new Color(245, 245, 245));

        // Inisialisasi tabel produk
        productTable = new javax.swing.JTable();
        productTableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nama Produk", "Harga", "Stok"} // Kolom Stok ditambahkan
        );
        productTable.setModel(productTableModel);

        // Inisialisasi tabel keranjang
        cartTable = new javax.swing.JTable();
        cartTableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nama Produk", "Harga", "Jumlah", "Total"}
        );
        cartTable.setModel(cartTableModel);

        // Header
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Form Kasir");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Scroll pane untuk tabel produk
        javax.swing.JScrollPane productTableScrollPane = new javax.swing.JScrollPane(productTable);

        // Scroll pane untuk tabel keranjang
        javax.swing.JScrollPane cartTableScrollPane = new javax.swing.JScrollPane(cartTable);

        // Total harga
        totalLabel = new javax.swing.JLabel("Total: Rp 0");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Tambahkan tombol cetak struk
        javax.swing.JButton printReceiptButton = new javax.swing.JButton("Cetak Struk", new ImageIcon("icons/print.png"));
        printReceiptButton.addActionListener(evt -> printReceiptActionPerformed());

        // Tombol dengan ikon
        javax.swing.JButton addToCartButton = new javax.swing.JButton("Tambah", new ImageIcon("icons/add.png"));
        javax.swing.JButton completeTransactionButton = new javax.swing.JButton("Bayar", new ImageIcon("icons/pay.png"));
        javax.swing.JButton salesReportButton = new javax.swing.JButton("Laporan", new ImageIcon("icons/report.png"));

        addToCartButton.addActionListener(evt -> addToCartActionPerformed());
        completeTransactionButton.addActionListener(evt -> completeTransactionActionPerformed());
        salesReportButton.addActionListener(evt -> openSalesReportForm());

        // Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
    .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(productTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cartTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(totalLabel)
            .addGroup(layout.createSequentialGroup()
                .addComponent(addToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(completeTransactionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salesReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(printReceiptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20))
);

layout.setVerticalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20)
        .addComponent(productTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20)
        .addComponent(cartTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20)
        .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(addToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(completeTransactionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(salesReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20)
        .addComponent(printReceiptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(20, 20, 20))
);


        pack();
    }
    // Tambahkan metode untuk mencetak struk
private void printReceiptActionPerformed() {
    if (cartTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Keranjang belanja kosong, tidak ada struk untuk dicetak.");
        return;
    }

    StringBuilder receipt = new StringBuilder();
    receipt.append("======= STRUK BELANJA =======\n");
    receipt.append("Tanggal: ").append(new java.util.Date()).append("\n\n");
    receipt.append(String.format("%-5s %-20s %-10s %-10s %-10s\n", "ID", "Nama", "Harga", "Jumlah", "Total"));
    receipt.append("---------------------------------------------\n");

    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
        int productId = (Integer) cartTableModel.getValueAt(i, 0);
        String productName = (String) cartTableModel.getValueAt(i, 1);
        double price = (Double) cartTableModel.getValueAt(i, 2);
        int quantity = (Integer) cartTableModel.getValueAt(i, 3);
        double total = (Double) cartTableModel.getValueAt(i, 4);

        receipt.append(String.format("%-5d %-20s %-10.2f %-10d %-10.2f\n", productId, productName, price, quantity, total));
    }

    receipt.append("---------------------------------------------\n");
    receipt.append("Total Belanja: ").append(totalLabel.getText()).append("\n");
    receipt.append("Terima kasih atas kunjungan Anda!\n");

    JTextArea textArea = new JTextArea(receipt.toString());
    try {
        textArea.print();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencetak: " + e.getMessage());
    }
}

    private void loadProducts() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM medicines"; // Asumsi tabel memiliki kolom 'stock'
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int productId = rs.getInt("medicine_id");
                String productName = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock"); // Ambil stok dari database
                Object[] row = {productId, productName, price, stock};
                productTableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat produk: " + e.getMessage());
        }
    }

    private void addToCartActionPerformed() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow != -1) {
            int productId = (Integer) productTableModel.getValueAt(selectedRow, 0);
            String productName = (String) productTableModel.getValueAt(selectedRow, 1);
            double price = (Double) productTableModel.getValueAt(selectedRow, 2);
            int stock = (Integer) productTableModel.getValueAt(selectedRow, 3);

            String input = JOptionPane.showInputDialog(this, "Masukkan Jumlah Produk:");
            if (input != null && !input.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(input);
                    if (quantity <= 0) {
                        throw new NumberFormatException();
                    }
                    if (quantity > stock) {
                        JOptionPane.showMessageDialog(this, "Stok tidak mencukupi.");
                        return;
                    }

                    double total = price * quantity;

                    Object[] row = {productId, productName, price, quantity, total};
                    cartTableModel.addRow(row);

                    // Kurangi stok pada tabel produk
                    productTableModel.setValueAt(stock - quantity, selectedRow, 3);

                    updateTotal();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Jumlah tidak valid. Masukkan angka positif.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih produk untuk ditambahkan ke keranjang.");
        }
    }

    private void completeTransactionActionPerformed() {
        double totalAmount = 0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            totalAmount += (Double) cartTableModel.getValueAt(i, 4);
        }

        if (totalAmount > 0) {
            saveTransactionToDatabase();
            updateProductStockInDatabase();
            JOptionPane.showMessageDialog(this, "Transaksi berhasil diselesaikan.\nTotal: Rp " + totalAmount);
            cartTableModel.setRowCount(0);
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Keranjang belanja kosong.");
        }
    }
    
    private void updateProductStockInDatabase() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "UPDATE medicines SET stock = ? WHERE medicine_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
    
            for (int i = 0; i < productTableModel.getRowCount(); i++) {
                int productId = (Integer) productTableModel.getValueAt(i, 0);
                int stock = (Integer) productTableModel.getValueAt(i, 3);
    
                pstmt.setInt(1, stock);
                pstmt.setInt(2, productId);
    
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memperbarui stok: " + e.getMessage());
        }
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            total += (Double) cartTableModel.getValueAt(i, 4);
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        totalLabel.setText("Total: " + formatter.format(total));
    }

    
    private void saveTransactionToDatabase() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "INSERT INTO sales_report (sale_date, product_name, quantity, price, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String productName = (String) cartTableModel.getValueAt(i, 1);
                int quantity = (Integer) cartTableModel.getValueAt(i, 3);
                double price = (Double) cartTableModel.getValueAt(i, 2);
                double total = (Double) cartTableModel.getValueAt(i, 4);

                pstmt.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                pstmt.setString(2, productName);
                pstmt.setInt(3, quantity);
                pstmt.setDouble(4, price);
                pstmt.setDouble(5, total);

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            JOptionPane.showMessageDialog(this, "Data transaksi berhasil disimpan.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan transaksi: " + e.getMessage());
        }
    }

    private void openSalesReportForm() {
        new SalesReportForm().setVisible(true);
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
        java.awt.EventQueue.invokeLater(() -> {
            new CashierForm().setVisible(true);
        });
    }
}
