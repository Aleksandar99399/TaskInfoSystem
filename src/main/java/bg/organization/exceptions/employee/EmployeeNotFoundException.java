package bg.organization.exceptions.employee;

import bg.organization.exceptions.GlobalCustomException;

public class EmployeeNotFoundException extends GlobalCustomException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

