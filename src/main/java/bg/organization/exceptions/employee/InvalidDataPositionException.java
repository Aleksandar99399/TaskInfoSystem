package bg.organization.exceptions.employee;

import bg.organization.exceptions.GlobalCustomException;

public class InvalidDataPositionException extends GlobalCustomException {
    public InvalidDataPositionException(String message) {
        super(message);
    }
}

