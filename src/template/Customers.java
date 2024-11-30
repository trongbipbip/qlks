package template;

import DAO.BookingDAO;
import services.BookingService;
import DAO.RoomDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Customers {
    public static void openCustomerInterface(String username) {
        // Tạo JFrame chính (giao diện khách hàng)
        JFrame frame = new JFrame("Hotel Management - Customer");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tạo menu bên trái
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, 600));
        menuPanel.setBackground(new Color(220, 220, 220));

        // Tạo panel chính để hiển thị nội dung
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // Đặt nội dung mặc định cho mainContentPanel
        JLabel welcomeLabel = new JLabel("Chào mừng bạn đến với hệ thống quản lý khách sạn!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainContentPanel.add(welcomeLabel, BorderLayout.CENTER);

        String[] menuItems = {
                "Home", "Đặt Phòng", "Trả Phòng",
                "Tra Cứu Thông Tin Phòng", "Đăng xuất"
        };

        // Tạo các nút và thêm vào menuPanel
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setMaximumSize(new Dimension(150, 40));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(180, 40));
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách

            // Thêm xử lý sự kiện cho từng nút
            button.addActionListener(e -> {
                switch (item) {
                    case "Home":
                        System.out.println("Nút Home được nhấn!");
                        showHotelInfo(mainContentPanel); // Cập nhật nội dung
                        break;
                    case "Đặt Phòng":
                        System.out.println("Nút Đặt phòng được nhấn!");
                        showBookingForm(mainContentPanel);

                        break;
                    case "Trả Phòng":
                        System.out.println("Nút Trả phòng được nhấn!");
                        showCheckoutForm(mainContentPanel);

                        break;
                    case "Tra Cứu Thông Tin Phòng":
                        System.out.println("Nút Tra cứu thông tin được nhấn!");
                        showRoomSearchForm(mainContentPanel);

                        break;
                    case "Đăng xuất":
                        System.out.println("Nút Đăng xuất được nhấn!");
                        frame.dispose(); // Đóng giao diện
                        break;
                    default:
                        System.out.println("Nút không xác định!");
                        break;
                }
            });
        }

        // Thêm các panel vào frame
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainContentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Hàm hiển thị thông tin khách sạn
    private static void showHotelInfo(JPanel mainContentPanel) {
        mainContentPanel.removeAll(); // Xóa nội dung cũ

        JLabel title = new JLabel("Thông Tin Khách Sạn", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JTextArea hotelInfo = new JTextArea(
                "Khách sạn ABC\n" +
                        "Địa chỉ: 123 Đường XYZ, Thành phố HCM\n" +
                        "Số điện thoại: 0909 123 456\n" +
                        "Email: contact@hotelabc.com\n" +
                        "Dịch vụ: Wifi miễn phí, Bể bơi, Gym, Nhà hàng\n"
        );
        hotelInfo.setFont(new Font("Arial", Font.PLAIN, 30));
        hotelInfo.setEditable(false);

        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.add(title, BorderLayout.NORTH);
        mainContentPanel.add(new JScrollPane(hotelInfo), BorderLayout.CENTER);

        mainContentPanel.revalidate(); // Cập nhật giao diện
        mainContentPanel.repaint();
    }

    // Hàm hiển thị form đặt phòng (ví dụ)
    private static void showBookingForm(JPanel mainContentPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new GridLayout(9, 2, 10, 10));

        JLabel nameLabel = new JLabel("Tên khách hàng:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Số điện thoại:");
        JTextField phoneField = new JTextField();

        JLabel roomTypeLabel = new JLabel("Loại phòng:");
        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"Single Room", "Double Room", "Suite"});

        JLabel roomLabel = new JLabel("Số phòng:");
        JComboBox<Integer> roomNumberComboBox = new JComboBox<>();

        JLabel checkInLabel = new JLabel("Ngày nhận phòng (YYYY-MM-DD):");
        JTextField checkInField = new JTextField();
        JLabel checkOutLabel = new JLabel("Ngày trả phòng (YYYY-MM-DD):");
        JTextField checkOutField = new JTextField();

        JButton submitButton = new JButton("Đặt Phòng");

        // Thêm các thành phần vào mainContentPanel
        mainContentPanel.add(nameLabel);
        mainContentPanel.add(nameField);
        mainContentPanel.add(emailLabel);
        mainContentPanel.add(emailField);
        mainContentPanel.add(phoneLabel);
        mainContentPanel.add(phoneField);
        mainContentPanel.add(roomTypeLabel);
        mainContentPanel.add(roomTypeComboBox);
        mainContentPanel.add(roomLabel);
        mainContentPanel.add(roomNumberComboBox);
        mainContentPanel.add(checkInLabel);
        mainContentPanel.add(checkInField);
        mainContentPanel.add(checkOutLabel);
        mainContentPanel.add(checkOutField);
        mainContentPanel.add(new JLabel()); // khoảng trống
        mainContentPanel.add(submitButton);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Xử lý thay đổi loại phòng để cập nhật danh sách số phòng
        roomTypeComboBox.addActionListener(e -> {
            String selectedRoomType = (String) roomTypeComboBox.getSelectedItem();
            RoomDAO roomDAO = new RoomDAO();
            ArrayList<Integer> availableRooms = roomDAO.getRoomsByType(selectedRoomType);

            roomNumberComboBox.removeAllItems();
            for (Integer roomNumber : availableRooms) {
                roomNumberComboBox.addItem(roomNumber);
            }
        });

        // Xử lý khi nhấn nút Đặt Phòng
        submitButton.addActionListener(e -> {
            String customerName = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            Integer roomNumber = (Integer) roomNumberComboBox.getSelectedItem();
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String checkInDate = checkInField.getText();
            String checkOutDate = checkOutField.getText();

            if (roomNumber == null || customerName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(mainContentPanel, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            BookingService bookingService = new BookingService();

            // Tính toán chi phí
            int totalCost = bookingService.calculateTotalCost(roomType, checkInDate, checkOutDate);

            // Đặt phòng
            boolean success = bookingService.bookRoom(customerName, email, phone, roomNumber, roomType, checkInDate, checkOutDate, totalCost);

            if (success) {
                JOptionPane.showMessageDialog(mainContentPanel, "Đặt phòng thành công! Tổng chi phí: " + totalCost + " USD");
            } else {
                JOptionPane.showMessageDialog(mainContentPanel, "Đặt phòng thất bại! Vui lòng thử lại.");
            }
        });
    }

    private static void showCheckoutForm(JPanel mainContentPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel roomLabel = new JLabel("Số phòng cần trả:");
        JTextField roomField = new JTextField();

        JButton submitButton = new JButton("Trả Phòng");

        // Thêm các thành phần vào mainContentPanel
        mainContentPanel.add(roomLabel);
        mainContentPanel.add(roomField);
        mainContentPanel.add(new JLabel()); // khoảng trống
        mainContentPanel.add(submitButton);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Xử lý khi nhấn nút "Trả Phòng"
        submitButton.addActionListener(e -> {
            int roomNumber;
            try {
                roomNumber = Integer.parseInt(roomField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainContentPanel, "Số phòng không hợp lệ!");
                return;
            }

            BookingService bookingService = new BookingService();
            boolean success = bookingService.checkoutRoom(roomNumber);

            if (success) {
                JOptionPane.showMessageDialog(mainContentPanel, "Trả phòng thành công! Phòng " + roomNumber + " đã được xóa khỏi hệ thống.");
            } else {
                JOptionPane.showMessageDialog(mainContentPanel, "Trả phòng thất bại! Kiểm tra lại số phòng.");
            }
        });
    }
    private static void showRoomSearchForm(JPanel mainContentPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Nhập tên hoặc email:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm Kiếm");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JTable resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        mainContentPanel.add(searchPanel, BorderLayout.NORTH);
        mainContentPanel.add(tableScrollPane, BorderLayout.CENTER);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Xử lý khi nhấn nút Tìm Kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(mainContentPanel, "Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }

            // Tra cứu thông tin trong cơ sở dữ liệu
            BookingDAO bookingDAO = new BookingDAO();
            ArrayList<Object[]> searchResults = bookingDAO.searchBookings(keyword);

            // Hiển thị kết quả trong bảng
            String[] columnNames = {"Tên khách hàng", "Email", "Số điện thoại", "Số phòng", "Loại phòng", "Giá tiền", "Ngày nhận", "Ngày trả"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (Object[] row : searchResults) {
                tableModel.addRow(row);
            }

            resultTable.setModel(tableModel);
        });
    }
}
