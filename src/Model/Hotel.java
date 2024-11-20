package Model;


import java.util.ArrayList;

public class Hotel {
    private String name;
    private String location;
    private ArrayList<Room> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<Food> foods;

    public Hotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public void checkIn(Guest guest, Room room) {
        if (room.isAvailable()) {
            guest.setCheckInDate(System.currentTimeMillis()); // Giả định thời gian check-in
            guests.add(guest);
            room.setAvailable(false);
            System.out.println("Khách " + guest.getName() + " đã check-in vào phòng " + room.getRoomNumber());
        } else {
            System.out.println("Phòng " + room.getRoomNumber() + " đã được đặt.");
        }
    }

    public void checkOut(Guest guest) {
        for (Room room : rooms) {
            if (!room.isAvailable() && room.isOccupiedBy(guest)) {
                room.setAvailable(true);
                guests.remove(guest);
                System.out.println("Khách " + guest.getName() + " đã check-out.");
                return;
            }
        }
        System.out.println("Khách không có trong danh sách hoặc không có phòng nào đang chiếm.");
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }
}
