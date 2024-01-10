package bg.organization.exceptions.employee;

import bg.organization.exceptions.GlobalCustomException;

public class EmployeeAlreadyExistException extends GlobalCustomException {
    public EmployeeAlreadyExistException(String message) {
        super(message);
    }
}