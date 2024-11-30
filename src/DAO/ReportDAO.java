package DAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    // Doanh thu theo tháng
    public static void generateCustomRevenueReport(String startDate, String endDate, JTable reportTable) {
        String sql = "SELECT room_type, COUNT(*) AS bookings_count, SUM(total_cost) AS total_revenue " +
                "FROM bookings WHERE check_in_date BETWEEN ? AND ? GROUP BY room_type";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(startDate));
            pstmt.setDate(2, java.sql.Date.valueOf(endDate));

            ResultSet rs = pstmt.executeQuery();

            // Cập nhật bảng hiển thị báo cáo
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Loại Phòng");
            tableModel.addColumn("Số Lượng Đặt");
            tableModel.addColumn("Doanh Thu (VNĐ)");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("room_type"),
                        rs.getInt("bookings_count"),
                        rs.getDouble("total_revenue")
                });
            }

            reportTable.setModel(tableModel);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu báo cáo!");
        }
    }

}

