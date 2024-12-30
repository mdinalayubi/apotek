/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mdina
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends javax.swing.JFrame {

    public LoginForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Login Pengguna");
        setLocationRelativeTo(null); // Menyentuhkan form di tengah layar
        setResizable(false);  // Menonaktifkan perubahan ukuran

        // Panel untuk menampung semua komponen dan setel warna latar belakang
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));

        // Gambar Latar Belakang
        JPanel background = new BackgroundPanel("src/icons/background.jpg");
        background.setLayout(new BorderLayout());
        panel.add(background, BorderLayout.CENTER);

        // Panel Form Login (semi-transparan agar latar belakang tetap terlihat)
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());

        // Label Username
        jLabel1.setText("Masukkan Username Anda:");
        jLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel1.setForeground(Color.BLACK); // Mengubah warna label menjadi hitam

        // Label Password
        jLabel2.setText("Masukkan Password Anda:");
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel2.setForeground(Color.BLACK); // Mengubah warna label menjadi hitam

        // Field Username dengan sudut membulat
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204), 2));
        usernameField.setPreferredSize(new Dimension(200, 30));

        // Field Password dengan sudut membulat
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204), 2));
        passwordField.setPreferredSize(new Dimension(200, 30));

        // Tombol Login dengan gaya
        loginButton.setText("Masuk");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.BLUE); // Mengubah warna teks tombol menjadi biru
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setPreferredSize(new Dimension(100, 40));

        // Penanganan aksi tombol login
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        // Layout untuk formPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(jLabel1, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(jLabel2, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        // Tambahkan form panel ke bagian tengah panel utama
        background.add(formPanel, BorderLayout.CENTER);

        // Tambahkan panel ke frame
        getContentPane().add(panel);
        pack();
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Cek login sederhana
        if (username.equals("admin") && password.equals("admin123")) {
            new DashboardForm().setVisible(true); // Lanjut ke Dashboard
            this.dispose(); // Tutup form login
        } else {
            JOptionPane.showMessageDialog(this, 
                "Username atau Password salah. Silakan coba lagi.", 
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    private javax.swing.JPanel panel; // Panel untuk menampung form
}
