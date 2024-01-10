package bg.organization.exceptions.directorate;

import bg.organization.exceptions.GlobalCustomException;

public class DirectorateNotFoundException  extends GlobalCustomException {
    public DirectorateNotFoundException(String message) {
        super(message);
    }
}
