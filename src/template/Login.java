package template;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbdemo";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        // Tạo JFrame chính (đăng nhập)
        JFrame frame = new JFrame("Login System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2));

        // Các thành phần giao diện
        JLabel lblRole = new JLabel("Role:");
        JComboBox<String> cbRole = new JComboBox<>(new String[]{"Admin", "Users"});

        JLabel lblUsername = new JLabel("Username:");
        JTextField txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Password:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Login");
        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);

        // Thêm các thành phần vào JFrame
        frame.add(lblRole);
        frame.add(cbRole);
        frame.add(lblUsername);
        frame.add(txtUsername);
        frame.add(lblPassword);
        frame.add(txtPassword);
        frame.add(new JLabel()); // khoảng trống
        frame.add(btnLogin);
        frame.add(lblMessage);

        // Xử lý sự kiện đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = cbRole.getSelectedItem().toString();
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (authenticateUser(role, username, password)) {
                    lblMessage.setText("Login successful!");
                    lblMessage.setForeground(Color.GREEN);

                    // Chuyển tiếp giao diện cho Customer
                    if (role.equals("Users")) {
                        Customers.openCustomerInterface(username); // Mở giao diện Customer
                    } else {
                        Admin.openAdminInterface(username);
                    }
                } else {
                    lblMessage.setText("Nhập sai tài khoản hoặc mật khẩu!");
                    lblMessage.setForeground(Color.RED);
                }
            }
        });

        frame.setVisible(true);
    }

    // Hàm xác thực người dùng
    private static boolean authenticateUser(String role, String username, String password) {
        String query;
        if (role.equals("Admin")) {
            query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        } else {
            query = "SELECT * FROM users WHERE username = ? AND password = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error!");
        }
        return false;
    }

}
