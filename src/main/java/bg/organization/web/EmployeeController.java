package bg.organization.web;

import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestEmployee;
import bg.organization.models.request.PostRequestEmployee;
import bg.organization.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> loadEmployees(){
        List<Employee> employees = employeeService.getEmployees();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> loadEmployeeById(@PathVariable long id){
        Employee employees = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employees);
    }

    @PostMapping()
    public ResponseEntity<String> postEmployee(@Valid @RequestBody PostRequestEmployee employee){
        employeeService.createEmployee(employee);
        return ResponseEntity.ok("Successfully created employee");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchEmployee(@PathVariable long id,
                                                @Valid @RequestBody PatchRequestEmployee requestEmployee){
        employeeService.editEmployee(id, requestEmployee);
        return ResponseEntity.ok("Successfully edited");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
