/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mdina
 */

import java.awt.*;
import javax.swing.*;

public class DashboardForm extends JFrame {

    public DashboardForm() {
        initComponents();
    }

    private void initComponents() {
        // Background Panel
        JPanel backgroundPanel = new BackgroundPanel("src/icons/background.jpg"); // Path ke gambar background

        // Set layout utama
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel();
        JButton manageMedicineButton = new JButton();
        JButton userManagementButton = new JButton();
        JButton salesReportButton = new JButton();
        JButton cashierButton = new JButton();
        JButton exitButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dashboard");

        // Title Label
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Dashboard");
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Panel with Grid Layout
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        centerPanel.setOpaque(false); // Membuat panel transparan
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Manage Medicine Button
        manageMedicineButton.setText("Manajemen Obat");
        manageMedicineButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        manageMedicineButton.setIcon(new ImageIcon("medicine_icon.png")); // Example icon
        manageMedicineButton.setToolTipText("Kelola data obat yang tersedia");
        manageMedicineButton.setFocusPainted(false); // Menghilangkan border focus
        manageMedicineButton.setBackground(new Color(63, 81, 181)); // Set background color
        manageMedicineButton.setForeground(Color.WHITE); // Set text color
        manageMedicineButton.setBorder(BorderFactory.createLineBorder(new Color(63, 81, 181), 2));
        manageMedicineButton.setOpaque(true);
        manageMedicineButton.setPreferredSize(new Dimension(200, 50));
        manageMedicineButton.addActionListener(evt -> new MedicineManagementForm().setVisible(true));
        manageMedicineButton.setRolloverEnabled(true); // Enable rollover effect
        manageMedicineButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageMedicineButton.setBackground(new Color(48, 63, 159)); // Rollover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageMedicineButton.setBackground(new Color(63, 81, 181)); // Default color
            }
        });
        centerPanel.add(manageMedicineButton);

        // User Management Button
        userManagementButton.setText("Manajemen Pengguna");
        userManagementButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userManagementButton.setIcon(new ImageIcon("user_icon.png")); // Example icon
        userManagementButton.setToolTipText("Kelola data pengguna");
        userManagementButton.setFocusPainted(false); // Menghilangkan border focus
        userManagementButton.setBackground(new Color(63, 81, 181)); // Set background color
        userManagementButton.setForeground(Color.WHITE); // Set text color
        userManagementButton.setBorder(BorderFactory.createLineBorder(new Color(63, 81, 181), 2));
        userManagementButton.setOpaque(true);
        userManagementButton.setPreferredSize(new Dimension(200, 50));
        userManagementButton.addActionListener(evt -> new UserManagementForm().setVisible(true));
        userManagementButton.setRolloverEnabled(true);
        userManagementButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userManagementButton.setBackground(new Color(48, 63, 159)); // Rollover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userManagementButton.setBackground(new Color(63, 81, 181)); // Default color
            }
        });
        centerPanel.add(userManagementButton);

        // Sales Report Button
        salesReportButton.setText("Laporan Penjualan");
        salesReportButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        salesReportButton.setIcon(new ImageIcon("report_icon.png")); // Example icon
        salesReportButton.setToolTipText("Lihat laporan penjualan");
        salesReportButton.setFocusPainted(false); // Menghilangkan border focus
        salesReportButton.setBackground(new Color(63, 81, 181)); // Set background color
        salesReportButton.setForeground(Color.WHITE); // Set text color
        salesReportButton.setBorder(BorderFactory.createLineBorder(new Color(63, 81, 181), 2));
        salesReportButton.setOpaque(true);
        salesReportButton.setPreferredSize(new Dimension(200, 50));
        salesReportButton.addActionListener(evt -> new SalesReportForm().setVisible(true));
        salesReportButton.setRolloverEnabled(true);
        salesReportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                salesReportButton.setBackground(new Color(48, 63, 159)); // Rollover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                salesReportButton.setBackground(new Color(63, 81, 181)); // Default color
            }
        });
        centerPanel.add(salesReportButton);

        // Cashier Button
        cashierButton.setText("Kasir");
        cashierButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cashierButton.setIcon(new ImageIcon("cashier_icon.png")); // Example icon
        cashierButton.setToolTipText("Akses fungsi kasir");
        cashierButton.setFocusPainted(false); // Menghilangkan border focus
        cashierButton.setBackground(new Color(63, 81, 181)); // Set background color
        cashierButton.setForeground(Color.WHITE); // Set text color
        cashierButton.setBorder(BorderFactory.createLineBorder(new Color(63, 81, 181), 2));
        cashierButton.setOpaque(true);
        cashierButton.setPreferredSize(new Dimension(200, 50));
        cashierButton.addActionListener(evt -> new CashierForm().setVisible(true));
        cashierButton.setRolloverEnabled(true);
        cashierButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cashierButton.setBackground(new Color(48, 63, 159)); // Rollover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cashierButton.setBackground(new Color(63, 81, 181)); // Default color
            }
        });
        centerPanel.add(cashierButton);

        // Exit Button
        exitButton.setText("Keluar");
        exitButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        exitButton.setIcon(new ImageIcon("exit_icon.png")); // Example icon
        exitButton.setToolTipText("Keluar dari aplikasi");
        exitButton.setFocusPainted(false); // Menghilangkan border focus
        exitButton.setBackground(new Color(63, 81, 181)); // Set background color
        exitButton.setForeground(Color.WHITE); // Set text color
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(63, 81, 181), 2));
        exitButton.setOpaque(true);
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.addActionListener(evt -> System.exit(0));
        exitButton.setRolloverEnabled(true);
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(48, 63, 159)); // Rollover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(63, 81, 181)); // Default color
            }
        });
        centerPanel.add(exitButton);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // Status Bar
        JLabel statusBar = new JLabel("Ready", JLabel.CENTER);
        statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        backgroundPanel.add(statusBar, BorderLayout.SOUTH);

        pack();
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardForm().setVisible(true));
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
