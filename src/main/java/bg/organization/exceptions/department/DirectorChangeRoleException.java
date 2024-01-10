package bg.organization.exceptions.department;

import bg.organization.exceptions.GlobalCustomException;

public class DirectorChangeRoleException extends GlobalCustomException {
    public DirectorChangeRoleException(String message) {
        super(message);
    }
}
