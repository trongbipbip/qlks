package services;

import DAO.BookingDAO;
import DAO.RoomTypeDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public boolean bookRoom(String customerName, String email, String phone, int roomNumber, String roomType, String checkInDate, String checkOutDate, int totalcost ) {
        BookingDAO bookingDAO = new BookingDAO();

        // Kiểm tra xem phòng có sẵn không
        if (!bookingDAO.isRoomAvailable(roomNumber, checkInDate, checkOutDate)) {
            System.out.println("Phòng " + roomNumber + " không khả dụng trong khoảng thời gian này!");
            return false;
        }

        // Đặt phòng nếu phòng khả dụng
        return bookingDAO.createBooking(customerName, email, phone, roomNumber, roomType, checkInDate, checkOutDate, totalcost);
    }

    public int calculateTotalCost(String roomType, String checkInDate, String checkOutDate) {
        // Lấy giá phòng từ cơ sở dữ liệu

        int roomPrice = RoomTypeDAO.getRoomPrice(roomType);

        // Tính số ngày lưu trú
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);
        long daysStayed = ChronoUnit.DAYS.between(checkIn, checkOut);

        // Tính tổng chi phí
        return (int) (roomPrice * daysStayed);
    }
    public boolean checkoutRoom(int roomNumber) {
        // Kiểm tra số phòng hợp lệ
        if (roomNumber <= 0) {
            System.out.println("Số phòng không hợp lệ!");
            return false;
        }

        // Xóa thông tin đặt phòng trong cơ sở dữ liệu
        boolean isDeleted = bookingDAO.deleteBooking(roomNumber);

        if (isDeleted) {
            System.out.println("Phòng " + roomNumber + " đã được trả!");
        } else {
            System.out.println("Không thể trả phòng " + roomNumber + ". Phòng này có thể không tồn tại!");
        }
        return isDeleted;
    }

}

