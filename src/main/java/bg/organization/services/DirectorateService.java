package bg.organization.services;

import bg.organization.models.Department;
import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestDirectorate;
import bg.organization.models.request.PostRequestDirectorate;

import java.util.List;


public interface DirectorateService {
    List<Directorate> getDirectorates();

    void createDirectorate(PostRequestDirectorate directorate);

    Directorate editDirectorate(long id, PatchRequestDirectorate patchRequestDirectorate);

    void deleteDirectorate(long id);

    Directorate getDirectorateById(long id);

    Directorate addEmployeeToDirectorate(long id, Employee director);

    void removeDepartmentFromDirectorate(long directorateId, Department department);
}
