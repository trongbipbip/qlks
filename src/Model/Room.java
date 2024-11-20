package Model;

public abstract class Room {
    private final int roomNumber;
    private boolean isAvailable;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract void book();
    public abstract void checkout();
    public abstract boolean isOccupiedBy(Guest guest); // Phương thức kiểm tra ai đang chiếm phòng
}
