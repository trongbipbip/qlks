package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public ArrayList<Integer> getRoomsByType(String roomType) {
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        String sql = "SELECT room_number FROM rooms WHERE room_type = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                roomNumbers.add(rs.getInt("room_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNumbers;
    }
}

