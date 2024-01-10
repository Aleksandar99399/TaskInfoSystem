package bg.organization.services.impl;

import bg.organization.exceptions.department.DepartmentNotFoundException;
import bg.organization.exceptions.department.InvalidRequestedDepartmentDataException;
import bg.organization.models.Department;
import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestDepartment;
import bg.organization.models.request.PostRequestDepartment;
import bg.organization.repositories.DepartmentRepository;
import bg.organization.services.DepartmentService;
import bg.organization.services.DirectorateService;
import bg.organization.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeService employeeService;
    private final DirectorateService directorateService;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, @Lazy EmployeeService employeeService, @Lazy DirectorateService directorateService, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
        this.directorateService = directorateService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department doesn't exist"));
    }

    @Override
    public void createDepartment(PostRequestDepartment requestDepartment) {
        if (doesDepartmentExistByName(requestDepartment.getName())) {
            throw new DepartmentNotFoundException();
        }
        Department department = modelMapper.map(requestDepartment, Department.class);
        departmentRepository.save(department);
    }

    private boolean doesDepartmentExistByName(String departmentName) {
        return departmentRepository.findByName(departmentName).isPresent();
    }

    @Override
    public Department editDepartment(long id, PatchRequestDepartment requestDepartment) {
        Department department = getDepartmentById(id);
        requestDepartment.getName().ifPresent(department::setName);
        requestDepartment.getDescription().ifPresent(department::setDescription);
        if (!requestDepartment.getOperation().isEmpty()) {
            changeDepartmentDataForEmployee(requestDepartment.getOperation(), department, requestDepartment);
        }
        return departmentRepository.save(department);
    }

    private void changeDepartmentDataForEmployee(String operation, Department department,
                                                 PatchRequestDepartment requestDepartment) {
        if (!requestDepartment.getEmployeesId().isEmpty()) {
            for (Integer empId : requestDepartment.getEmployeesId()) {
                switch (operation) {
                    case "add" -> {
                        Employee employee =
                                employeeService.addEmployeeToDepartment(department, empId, requestDepartment.getPosition());
                        department.getEmployees().add(employee);
                    }
                    case "remove" -> {
                        Employee employee = employeeService.removeEmployeeFromDepartment(empId);
                        department.getEmployees().remove(employee);
                    }
                    case "change" -> {
                        employeeService.removeEmployeeFromDepartment(empId);
                        Employee employee =
                                employeeService.addEmployeeToDepartment(department, empId, requestDepartment.getPosition());
                        department.getEmployees().add(employee);
                    }
                }
            }
        } else {
            throw new InvalidRequestedDepartmentDataException("Not requested employee");
        }
    }
    @Override
    public void deleteDepartment(long id) {
        Department department = getDepartmentById(id);
        long directorateId = department.getDirectorate().getId();
        directorateService.removeDepartmentFromDirectorate(directorateId, department);
        departmentRepository.deleteById(id);
    }

    @Override
    public Department addEmployeeToDepartment(long id, Employee employee) {
        Department departmentById = getDepartmentById(id);
        departmentById.getEmployees().add(employee);
        return departmentById;
    }
}
