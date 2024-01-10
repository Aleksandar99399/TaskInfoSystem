package bg.organization.exceptions.employee;

import bg.organization.exceptions.GlobalCustomException;

public class InvalidPositionException extends GlobalCustomException {
    public InvalidPositionException(String message) {
        super(message);
    }
}
