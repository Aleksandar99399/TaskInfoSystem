package bg.organization.services.impl;

import bg.organization.exceptions.directorate.DirectorateAlreadyExistsException;
import bg.organization.exceptions.directorate.DirectorateNotFoundException;
import bg.organization.models.Department;
import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import bg.organization.models.request.PatchRequestDirectorate;
import bg.organization.models.request.PostRequestDirectorate;
import bg.organization.repositories.DirectorateRepository;
import bg.organization.services.DepartmentService;
import bg.organization.services.DirectorateService;
import bg.organization.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorateServiceImpl implements DirectorateService {
    private final DirectorateRepository directorateRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public DirectorateServiceImpl(DirectorateRepository directorateRepository, @Lazy EmployeeService employeeService, ModelMapper modelMapper) {
        this.directorateRepository = directorateRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }
    @Override
    public Directorate getDirectorateById(long id) {
        return directorateRepository.findById(id)
                .orElseThrow(() -> new DirectorateNotFoundException("Directorate doesn't exist"));
    }

    @Override
    public List<Directorate> getDirectorates() {
        return directorateRepository.findAll();
    }

    @Override
    public void createDirectorate(PostRequestDirectorate postRequestDirectorate) {
        doesDirectorateExistByName(postRequestDirectorate.getName());
        Directorate directorate = modelMapper.map(postRequestDirectorate, Directorate.class);
        directorateRepository.save(directorate);
    }

    private void doesDirectorateExistByName(String name) {
        if (directorateRepository.findByName(name).isPresent()){
            throw new DirectorateAlreadyExistsException();
        }
    }

    @Override
    public Directorate editDirectorate(long id, PatchRequestDirectorate requestDirectorate) {
        Directorate directorate = getDirectorateById(id);
       if (requestDirectorate.getDirectorId() != 0 && !requestDirectorate.getOperation().isEmpty()) {
           if (requestDirectorate.getOperation().equalsIgnoreCase("add")) {
               Employee director = employeeService.getEmployeeById(requestDirectorate.getDirectorId());
               if (directorateRepository.findByDirector(director).isPresent()) {
                   Directorate exDirectorate = directorateRepository.findByDirector(director).get();
                   employeeService.removeDirector(exDirectorate, director.getId());
               }
               employeeService.addDirector(directorate, director);
           } else if (requestDirectorate.getOperation().equalsIgnoreCase("remove")) {
               employeeService.removeDirector(directorate, requestDirectorate.getDirectorId());
           }
       }
        return directorateRepository.save(directorate);
    }

    @Override
    public void deleteDirectorate(long id) {
        Directorate directorate = getDirectorateById(id);
        for (Department department : directorate.getDepartments()) {
            department.setDirectorate(null);
        }
        directorate.getDirector().setDirectorate(null);
        directorateRepository.deleteById(id);
    }

    @Override
    public Directorate addEmployeeToDirectorate(long directorateId, Employee director) {
        Directorate directorate = getDirectorateById(directorateId);
        return saveDirectorateToDB(directorate);
    }

    @Override
    public void removeDepartmentFromDirectorate(long directorateId, Department department) {
        Directorate directorate = getDirectorateById(directorateId);
        directorate.getDepartments().remove(department);
    }

    private Directorate saveDirectorateToDB(Directorate directorate){
        return directorateRepository.save(directorate);
    }

}
