package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTypeDAO {
    public static int getRoomPrice(String roomType) {
        String sql = "SELECT price_per_night FROM room_types WHERE room_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("price_per_night");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Nếu không tìm thấy loại phòng
    }
}

