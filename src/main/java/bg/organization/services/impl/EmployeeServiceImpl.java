package bg.organization.services.impl;

import bg.organization.exceptions.department.DirectorChangeRoleException;
import bg.organization.exceptions.employee.InvalidPositionException;
import bg.organization.util.StringToEnumConverter;
import bg.organization.exceptions.directorate.DirectorateAlreadyExistsException;
import bg.organization.exceptions.employee.EmployeeAlreadyExistException;
import bg.organization.exceptions.employee.EmployeeNotFoundException;
import bg.organization.exceptions.employee.InvalidDataPositionException;
import bg.organization.models.Department;
import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import bg.organization.models.Position;
import bg.organization.models.request.PatchRequestEmployee;
import bg.organization.models.request.PostRequestEmployee;
import bg.organization.repositories.EmployeeRepository;
import bg.organization.services.DepartmentService;
import bg.organization.services.DirectorateService;
import bg.organization.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DirectorateService directorateService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper,
                               DirectorateService directorateService, DepartmentService departmentService) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.directorateService = directorateService;
        this.departmentService = departmentService;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public void createEmployee(PostRequestEmployee postRequestEmployee) {
        if (doesEmployeeExistById(postRequestEmployee.getEgn())) {
            throw new EmployeeAlreadyExistException("Employee already exists");
        }
        Employee employee = mapEmployee(postRequestEmployee);

        if (postRequestEmployee.getDepartment() != 0 && postRequestEmployee.getDirectorate() != 0) {
            throw new InvalidDataPositionException("Cannot add employee to department and directorate simultaneously. Choose one preference");
        }

        if (postRequestEmployee.getPosition().equalsIgnoreCase(Position.DIRECTOR.toString())) {
            if (postRequestEmployee.getDirectorate() != 0) {
                Directorate directorate = directorateService.getDirectorateById(postRequestEmployee.getDirectorate());
                if (directorate.getDirector() != null) {
                    throw new DirectorateAlreadyExistsException();
                }
                directorate.setDirector(employee);
                employee.setDirectorate(directorate);
            } else {
                throw new InvalidDataPositionException("Invalid filled data for directorate");
            }
        } else if (postRequestEmployee.getPosition().equalsIgnoreCase(Position.MANAGER.toString())
                || postRequestEmployee.getPosition().equalsIgnoreCase(Position.EMPLOYEE.toString())) {

            if (postRequestEmployee.getDepartment() != 0) {
                Department department =
                        departmentService.addEmployeeToDepartment(postRequestEmployee.getDepartment(), employee);
                department.getEmployees().add(employee);
                employee.setDepartment(department);
            } else {
                throw new InvalidDataPositionException("Invalid filled data for department");
            }
        }
        employeeRepository.save(employee);
    }

    private Employee mapEmployee(PostRequestEmployee postRequestEmployee) {
        modelMapper.addConverter(new StringToEnumConverter());
        return modelMapper.map(postRequestEmployee, Employee.class);
    }

    @Override
    public void editEmployee(long id, PatchRequestEmployee requestEmployee) {
        Employee employee = getEmployeeById(id);
        requestEmployee.getName().ifPresent(employee::setName);
        requestEmployee.getLastName().ifPresent(employee::setLastName);
        requestEmployee.getEgn().ifPresent(employee::setEgn);
        requestEmployee.getAge().ifPresent(employee::setAge);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee = getEmployeeById(id);
        if (Position.DIRECTOR.toString().equalsIgnoreCase(employee.getPosition().toString())) {
            employee.getDirectorate().setDirector(null);
        } else {
            employee.getDepartment().getEmployees().remove(employee);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee addDirector(Directorate directorate, Employee employee) {
        if (employee.getDepartment() != null) {
            employee.setDepartment(null);
            employee.setPosition(Position.DIRECTOR);
            employee.setDirectorate(directorate);
            directorate.setDirector(employee);
        } else {
            directorate.setDirector(employee);
            employee.setDirectorate(directorate);
        }
        return employeeRepository.save(employee);
    }


    @Override
    public Employee addEmployeeToDepartment(Department department, long employeeId, String position) {
        Employee employee = getEmployeeById(employeeId);
        if (employee.getDirectorate() != null) {
            throw new DirectorChangeRoleException("Cannot change director role to be employee or manager: " + employeeId);
        } else {
            employee.setPosition(getValidPosition(position));
            employee.setDepartment(department);
        }
        return employee;
    }

    @Override
    public Employee removeEmployeeFromDepartment(long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (employee.getDirectorate() != null) {
            throw new DirectorChangeRoleException("Cannot change director role to be employee or manager: " + employeeId);
        } else {
            employee.setPosition(null);
            employee.setDepartment(null);
        }
        return employee;
    }

    @Override
    public void removeDirector(Directorate directorate, long directorId) {
        Employee employee = getEmployeeById(directorId);
        if (employee.getDirectorate() != null) {
            directorate.setDirector(null);
            employee.setDirectorate(null);
            employeeRepository.save(employee);
        }
    }

    private Position getValidPosition(String position) {
        try {
            return Position.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new InvalidPositionException("Requested invalid position");
        }
    }

    private boolean doesEmployeeExistById(String egn) {
        return employeeRepository.existsByEgn(egn);
    }
}
