package bg.organization.services;

import bg.organization.models.Department;
import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestEmployee;
import bg.organization.models.request.PostRequestEmployee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();

    Employee getEmployeeById(long id);

    void createEmployee(PostRequestEmployee employee);

    void editEmployee(long id, PatchRequestEmployee requestEmployee);

    void deleteEmployee(long id);

    Employee addDirector(Directorate directorate, Employee employee);

    Employee addEmployeeToDepartment(Department department, long employeeId, String position);

    Employee removeEmployeeFromDepartment(long employeeId);

    void removeDirector(Directorate directorate, long directorId);

}
