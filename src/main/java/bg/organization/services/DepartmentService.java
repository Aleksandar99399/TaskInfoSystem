package bg.organization.services;

import bg.organization.models.Department;
import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestDepartment;
import bg.organization.models.request.PostRequestDepartment;

import java.util.List;

public interface DepartmentService {
    List<Department> getDepartments();

    Department getDepartmentById(long id);

    void createDepartment(PostRequestDepartment requestDepartment);

    Department editDepartment(long id, PatchRequestDepartment requestDepartment);

    void deleteDepartment(long id);

    Department addEmployeeToDepartment(long id, Employee employee);
}
