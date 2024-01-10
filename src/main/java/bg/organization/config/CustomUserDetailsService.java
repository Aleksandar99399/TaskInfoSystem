//package bg.organization.config;
//
//import bg.organization.models.Employee;
//import bg.organization.models.Position;
//import bg.organization.services.EmployeeService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final EmployeeService employeeService;
//
//    public CustomUserDetailsService(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
//        Employee employeeById = employeeService.getEmployeeById(Long.parseLong(empId));
//
//        if (Position.DIRECTOR.toString().equalsIgnoreCase(employeeById.getPosition().toString())) {
//            return User.withUsername("admin")
//                    .password("admin")
//                    .roles(Position.DIRECTOR.toString())
//                    .build();
//        } else if (Position.MANAGER.toString().equalsIgnoreCase(employeeById.getPosition().toString())) {
//            return User.withUsername("user")
//                    .password("user")
//                    .roles(Position.MANAGER.toString())
//                    .build();
//        } else {
//            throw new UsernameNotFoundException("User not found");
//        }
//    }
//}
