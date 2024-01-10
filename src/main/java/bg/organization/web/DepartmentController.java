package bg.organization.web;

import bg.organization.models.Department;
import bg.organization.models.request.PatchRequestDepartment;
import bg.organization.models.request.PostRequestDepartment;
import bg.organization.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> loadDepartments(){
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> loadDepartments(@PathVariable long id){
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<String> postDepartment(@RequestBody PostRequestDepartment requestDepartment){
        departmentService.createDepartment(requestDepartment);
        return ResponseEntity.ok("Successfully created department");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> patchDepartment(@PathVariable long id,
                                                      @RequestBody PatchRequestDepartment requestDepartment){
        Department department = departmentService.editDepartment(id, requestDepartment);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
