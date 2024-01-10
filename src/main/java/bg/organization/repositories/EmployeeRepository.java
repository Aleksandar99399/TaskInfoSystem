package bg.organization.repositories;

import bg.organization.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEgn(String egn);

    @Query(nativeQuery = true, value = "DELETE FROM employees WHERE id = :empId")
//    @Query("DELETE FROM departments_employees WHERE departments_employees.employee_id = ?")
    void deleteEmployeeFromDepartment(@Param("empId") long employeeId);
//    @Query("select a from employees as e where ")
//    void myQuery();
}
