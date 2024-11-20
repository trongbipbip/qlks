package Model;

public  class DoubleRoom extends Room {
    private Guest occupiedBy;

    public DoubleRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }

    @Override
    public void book() {
        setAvailable(false);
    }

    @Override
    public void checkout() {
        setAvailable(true);
    }

    @Override
    public boolean isOccupiedBy(Guest guest) {
        return occupiedBy != null && occupiedBy.getName().equals(guest.getName());
    }

    public void occupy(Guest guest) {
        this.occupiedBy = guest;
        book();
    }

    public void vacate() {
        this.occupiedBy = null;
        checkout();
    }
}
