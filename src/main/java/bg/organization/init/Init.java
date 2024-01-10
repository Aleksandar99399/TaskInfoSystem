package bg.organization.init;

import bg.organization.models.Department;
import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import bg.organization.models.Position;
import bg.organization.repositories.DepartmentRepository;
import bg.organization.repositories.DirectorateRepository;
import bg.organization.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Init implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DirectorateRepository directorateRepository;

    @Autowired
    public Init(EmployeeRepository employeeRepository,
                DepartmentRepository departmentRepository,
                DirectorateRepository directorateRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.directorateRepository = directorateRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (directorateRepository.count() == 0 && departmentRepository.count() == 0 && employeeRepository.count() == 0) {
            Department department = saveDepartment();
            Directorate directorate = saveDirectorate();
            saveEmployees(department, directorate);
        }
    }

    private Directorate saveDirectorate() {
        Directorate directorate = Directorate.builder()
                .name("NAP")
                .description("Collect information")
                .build();
//        directorateRepository.save(directorate);
        return directorate;
    }

    private Department saveDepartment() {
        Department department = Department.builder()
                .name("IT")
                .description("Creating and support software products")
                .build();

//        departmentRepository.save(department);
        return department;
    }

    private void saveEmployees(Department department, Directorate directorate) {
        Department depart2 =  Department.builder().name("Dep222").description("desc DEP222").build();

        Employee pesho = Employee.builder()
                .name("Pesho")
                .lastName("Pesho")
                .age(23)
                .egn("1238888736")
                .position(Position.DIRECTOR)
                .directorate(directorate)
                .build();
        Employee gosho = Employee.builder()
                .name("Gosho")
                .lastName("Gosho")
                .age(23)
                .egn("2347888736")
                .position(Position.MANAGER)
                .department(department)
                .build();
        Employee ivan = Employee.builder()
                .name("Ivan")
                .lastName("Ivan")
                .age(23)
                .egn("3458888736")
                .position(Position.EMPLOYEE)
                .department(depart2)
                .build();
        department.setEmployees(Set.of(gosho));
        department.setDirectorate(directorate);
        depart2.setEmployees(Set.of(ivan));
        directorate.setDirector(pesho);
        directorate.getDepartments().add(department);
        employeeRepository.saveAll(Set.of(gosho, ivan, pesho));
    }
}
