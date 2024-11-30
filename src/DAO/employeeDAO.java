package DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class employeeDAO {
    public static void loadEmployeeData(JTable employeeTable) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Lấy số lượng cột và chuẩn bị mảng để chứa dữ liệu
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0); // Xóa tất cả các hàng cũ

            // Đọc dữ liệu từ cơ sở dữ liệu và thêm vào bảng
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu nhân viên.");
        }
    }
    public static void deleteEmployee(JTable employeeTable) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int employeeId = (Integer) employeeTable.getValueAt(selectedRow, 0); // Giả sử ID là cột đầu tiên
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM employees WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, employeeId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Nhân viên đã được xóa.");
                loadEmployeeData(employeeTable); // Tải lại dữ liệu bảng
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xóa nhân viên.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa.");
        }
    }



}
