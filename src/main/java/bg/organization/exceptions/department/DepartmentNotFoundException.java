package bg.organization.exceptions.department;

import bg.organization.exceptions.GlobalCustomException;

public class DepartmentNotFoundException extends GlobalCustomException {
    public DepartmentNotFoundException() {
        super("Department not found");
    }
}
