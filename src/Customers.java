import javax.swing.*;
import java.awt.*;

public class Customers {
    public static void openCustomerInterface(String username) {
        JFrame customerFrame = new JFrame("Customer Interface");
        customerFrame.setSize(400, 300);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thêm giao diện cơ bản cho khách hàng
        JLabel lblWelcome = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        JButton btnLogout = new JButton("Logout");

        customerFrame.setLayout(new BorderLayout());
        customerFrame.add(lblWelcome, BorderLayout.CENTER);
        customerFrame.add(btnLogout, BorderLayout.SOUTH);

        // Xử lý sự kiện Logout
        btnLogout.addActionListener(e -> {
            customerFrame.dispose(); // Đóng giao diện Customer
             // Quay lại giao diện đăng nhập
        });

        customerFrame.setVisible(true);
    }
}
