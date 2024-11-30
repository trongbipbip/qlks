package Model;

import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private double salary;
    private String email;
    private String position;
    private Date startDate;

    // Constructor
    public Employee(int id, String name, String phone, double salary, String email, String position, Date startDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.salary = salary;
        this.email = email;
        this.position = position;
        this.startDate = startDate;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getSalary() { return salary; }
    public String getEmail() { return email; }
    public String getPosition() { return position; }
    public Date getStartDate() { return startDate; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setEmail(String email) { this.email = email; }
    public void setPosition(String position) { this.position = position; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
}

