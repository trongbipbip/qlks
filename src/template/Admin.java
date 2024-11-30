package template;

import Model.Employee;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.EmployeeService;
import services.ReportService;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import static DAO.ReportDAO.generateCustomRevenueReport;
import static DAO.employeeDAO.deleteEmployee;
import static DAO.employeeDAO.loadEmployeeData;
import static services.ReportService.createDatePicker;
import static services.ReportService.getDateFromPicker;

public class Admin {

    public static void openAdminInterface(String username) {
        // Tạo JFrame chính (giao diện admin)
        JFrame frame = new JFrame("Hotel Management - Admin");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Menu bên trái
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, 600));
        menuPanel.setBackground(new Color(220, 220, 220));

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // Các nút trong menu
        String[] adminMenuItems = {"Xem Báo Cáo Doanh Thu", "Nhân Viên", "Quản Lý Đặt Phòng", "Đăng Xuất"};
        for (String item : adminMenuItems) {
            JButton button = new JButton(item);
            button.setMaximumSize(new Dimension(150, 40));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setPreferredSize(new Dimension(180, 40));
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách

            // Xử lý sự kiện khi nhấn nút
            button.addActionListener(e -> {
                switch (item) {
                    case "Xem Báo Cáo Doanh Thu":
                        showReportPanel(mainContentPanel);
                        break;
                    case "Nhân Viên":
                        showEmployeeManagementPanel(mainContentPanel);
                        break;
                    case "Quản Lý Đặt Phòng":

                        break;
                    case "Đăng Xuất":
                        frame.dispose(); // Đóng giao diện admin
                        JOptionPane.showMessageDialog(null, "Bạn đã đăng xuất!");
                        // Quay lại giao diện đăng nhập (hoặc hành động khác)
                        break;
                    default:
                        break;
                }
            });
        }


        // Thêm các panel vào frame
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainContentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Phương thức hiển thị báo cáo doanh thu


    private static void showReportPanel(JPanel mainContentPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setPreferredSize(new Dimension(800, 100));
        JLabel startDateLabel = new JLabel("Ngày Bắt Đầu:");
        JDatePickerImpl startDatePicker = createDatePicker();
        JLabel endDateLabel = new JLabel("Ngày Kết Thúc:");
        JDatePickerImpl endDatePicker = createDatePicker();
        JButton viewReportButton = new JButton("Xem Báo Cáo");

        filterPanel.add(startDateLabel);
        filterPanel.add(startDatePicker);
        filterPanel.add(endDateLabel);
        filterPanel.add(endDatePicker);
        filterPanel.add(viewReportButton);

        // Panel chứa báo cáo
        JTable reportPanel = new JTable();
        mainContentPanel.add(filterPanel, BorderLayout.NORTH);
        mainContentPanel.add(reportPanel, BorderLayout.CENTER);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Xử lý khi nhấn nút "Xem Báo Cáo"
        viewReportButton.addActionListener(e -> {
            String startDate = getDateFromPicker(startDatePicker);
            String endDate = getDateFromPicker(endDatePicker);

            if (startDate == null || endDate == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc hợp lệ!");
                return;
            }

            generateCustomRevenueReport(startDate, endDate, reportPanel);
        });
    }


    private static void showEmployeeManagementPanel(JPanel mainContentPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());

        // Panel chứa bảng nhân viên
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BorderLayout());

        // Tạo bảng để hiển thị thông tin nhân viên
        String[] columns = {"ID", "Tên", "Số Điện Thoại", "Lương", "Email", "Chức Vụ"};
        JTable employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        employeePanel.add(scrollPane, BorderLayout.CENTER);

        // Nút "Thêm Nhân Viên"
        JButton addEmployeeButton = new JButton("Thêm Nhân Viên");
        addEmployeeButton.addActionListener(e -> {
            new AddEmployeeDialog(mainContentPanel).setVisible(true);
        });

        // Nút "Xóa Nhân Viên"
        JButton deleteEmployeeButton = new JButton("Xóa Nhân Viên");
        deleteEmployeeButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                int employeeId = Integer.parseInt(employeeTable.getValueAt(selectedRow, 0).toString());
                EmployeeService employeeService = new EmployeeService();
                if (employeeService.deleteEmployee(employeeId)) {
                    JOptionPane.showMessageDialog(null, "Nhân viên đã được xóa.");
                    loadEmployeeData(employeeTable); // Refresh lại bảng
                } else {
                    JOptionPane.showMessageDialog(null, "Không thể xóa nhân viên.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);

        mainContentPanel.add(employeePanel, BorderLayout.CENTER);
        mainContentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        // Load dữ liệu nhân viên từ cơ sở dữ liệu
        loadEmployeeData(employeeTable);

    }
    private static void loadEmployeeData(JTable employeeTable) {
        EmployeeService employeeService = new EmployeeService();
        ArrayList<Employee> employees = employeeService.getAllEmployees();
        String[][] data = new String[employees.size()][6];
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            data[i][0] = String.valueOf(employee.getId());
            data[i][1] = employee.getName();
            data[i][2] = employee.getPhone();
            data[i][3] = String.valueOf(employee.getSalary());
            data[i][4] = employee.getEmail();
            data[i][5] = employee.getPosition();
        }
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(data, new String[]{"ID", "Tên", "Số Điện Thoại", "Lương", "Email", "Chức Vụ"}));
    }



}

