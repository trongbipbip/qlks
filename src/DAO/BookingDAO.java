package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class BookingDAO {
    public boolean createBooking(String customerName, String email, String phone, int roomNumber, String roomType, String checkInDate, String checkOutDate, int totalcost) {
        String sql = "INSERT INTO bookings (customer_name, email, phone, room_number, room_type, check_in_date, check_out_date, status, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerName);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setInt(4, roomNumber);
            pstmt.setString(5, roomType);
            pstmt.setString(6, checkInDate);
            pstmt.setString(7, checkOutDate);
            pstmt.setString(8,"Confirmed");
            pstmt.setInt(9,totalcost);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isRoomAvailable(int roomNumber, String checkInDate, String checkOutDate) {
        String sql = "SELECT * FROM bookings WHERE room_number = ? AND " +
                "(check_in_date < ? AND check_out_date > ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomNumber);
            pstmt.setString(2, checkOutDate);
            pstmt.setString(3, checkInDate);

            ResultSet rs = pstmt.executeQuery();
            return !rs.next(); // Trả về true nếu không có xung đột đặt phòng
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteBooking(int roomNumber) {
        String sql = "DELETE FROM bookings WHERE room_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomNumber);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // Trả về true nếu có dòng nào bị xóa

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Object[]> searchBookings(String keyword) {
        String sql = "SELECT customer_name, email, phone, room_number, room_type, " +
                "CASE room_type " +
                "    WHEN 'Single Room' THEN 100 " +
                "    WHEN 'Double Room' THEN 200 " +
                "    WHEN 'Suite' THEN 300 " +
                "END AS price, check_in_date, check_out_date " +
                "FROM bookings " +
                "WHERE customer_name LIKE ? OR email LIKE ?";
        ArrayList<Object[]> results = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getString("customer_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("room_number"),
                        rs.getString("room_type"),
                        rs.getInt("price"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date")
                };
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }




}

