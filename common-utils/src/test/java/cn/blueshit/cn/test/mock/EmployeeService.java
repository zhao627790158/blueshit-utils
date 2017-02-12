package cn.blueshit.cn.test.mock;

public class EmployeeService {

    public int getEmployeeCount() {
        return Employee.count();
    }

    public void saveEmployee(Employee employee) {
        throw new UnsupportedOperationException();
    }
}