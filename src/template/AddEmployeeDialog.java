package template;

import Model.Employee;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.EmployeeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AddEmployeeDialog extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField positionField;
    private JTextField salaryField;
    private JDatePickerImpl startDatePicker;

    public AddEmployeeDialog(JPanel parentFrame) {
        super(); // Modal dialog
        setLayout(new BorderLayout());

        // Tạo panel nhập liệu
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));

        // Tạo các label và trường nhập liệu
        formPanel.add(new JLabel("Tên:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Số Điện Thoại:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Chức Vụ:"));
        positionField = new JTextField();
        formPanel.add(positionField);

        formPanel.add(new JLabel("Lương:"));
        salaryField = new JTextField();
        formPanel.add(salaryField);

        formPanel.add(new JLabel("Ngày Vào Làm:"));
        startDatePicker = createDatePicker();
        formPanel.add(startDatePicker);

        // Tạo panel chứa nút
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm Nhân Viên");
        JButton cancelButton = new JButton("Hủy");

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        // Thêm các panel vào dialog
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Lắng nghe sự kiện của nút "Thêm Nhân Viên"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kiểm tra dữ liệu nhập vào
                if (validateInput()) {
                    String name = nameField.getText();
                    String phone = phoneField.getText();
                    String email = emailField.getText();
                    String position = positionField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    Date startDate = getDateFromPicker(startDatePicker);

                    // Tạo đối tượng Employee và gọi phương thức thêm nhân viên
                    Employee newEmployee = new Employee(0, name, phone, salary, email, position, startDate);
                    EmployeeService employeeService = new EmployeeService();
                    boolean success = employeeService.addEmployee(newEmployee);

                    if (success) {
                        JOptionPane.showMessageDialog(AddEmployeeDialog.this, "Nhân viên đã được thêm!");
                        dispose(); // Đóng dialog
                    } else {
                        JOptionPane.showMessageDialog(AddEmployeeDialog.this, "Lỗi khi thêm nhân viên. Vui lòng thử lại!");
                    }
                }
            }
        });

        // Lắng nghe sự kiện của nút "Hủy"
        cancelButton.addActionListener(e -> dispose());

        // Cấu hình dialog
        setSize(400, 400);
        setLocationRelativeTo(parentFrame); // Đặt dialog ở trung tâm của cửa sổ cha
    }

    // Kiểm tra dữ liệu nhập vào
    private boolean validateInput() {
        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                positionField.getText().isEmpty() || salaryField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }

        try {
            Double.parseDouble(salaryField.getText()); // Kiểm tra lương có phải là số không
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lương phải là một số hợp lệ!");
            return false;
        }

        return true;
    }

    // Tạo DatePicker cho ngày vào làm
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hôm nay");
        properties.put("text.month", "Tháng");
        properties.put("text.year", "Năm");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    // Lấy ngày từ JDatePicker
    private Date getDateFromPicker(JDatePickerImpl datePicker) {
        return (Date) datePicker.getModel().getValue();
    }

    // Lớp Formatter để hiển thị ngày tháng
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                return dateFormatter.format((Date) value);
            }
            return "";
        }
    }
}

