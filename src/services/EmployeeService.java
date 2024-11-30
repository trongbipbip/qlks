package services;

import Model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    // Kết nối cơ sở dữ liệu
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dbdemo", "root", "");
    }

    // Lấy tất cả nhân viên từ CSDL
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getDouble("salary"),
                        rs.getString("email"),
                        rs.getString("position"),
                        rs.getDate("start_date")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Thêm một nhân viên mới
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO employees (name, phone, email, position, salary, start_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getPhone());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPosition());
            stmt.setDouble(5, employee.getSalary());
            stmt.setDate(6, new java.sql.Date(employee.getStartDate().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nhân viên theo ID
    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
