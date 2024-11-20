package Model;

public class Guest {
    private final String name;
    private final String email;
    private final String phoneNum;
    private long checkInDate;
    private long checkOutDate;

    public Guest(String name, String email, String phoneNum) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setCheckInDate(long checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(long checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public long getCheckInDate() {
        return checkInDate;
    }

    public long getCheckOutDate() {
        return checkOutDate;
    }
}
