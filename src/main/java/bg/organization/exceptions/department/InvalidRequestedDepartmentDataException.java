package bg.organization.exceptions.department;

import bg.organization.exceptions.GlobalCustomException;

public class InvalidRequestedDepartmentDataException extends GlobalCustomException {
    public InvalidRequestedDepartmentDataException(String message) {
        super(message);
    }
}
