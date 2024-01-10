package bg.organization.exceptions.directorate;

import bg.organization.exceptions.GlobalCustomException;

public class DirectorateAlreadyExistsException extends GlobalCustomException {
    public DirectorateAlreadyExistsException() {
        super("Directorate already exists for that directorate");
    }
}

