package bg.organization.web;

import bg.organization.models.Directorate;
import bg.organization.models.request.PatchRequestDirectorate;
import bg.organization.models.request.PostRequestDirectorate;
import bg.organization.services.DirectorateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directorate")
public class DirectorateController {

    private final DirectorateService directorateService;

    @Autowired
    public DirectorateController(DirectorateService directorateService) {
        this.directorateService = directorateService;
    }

    @GetMapping
    public ResponseEntity<List<Directorate>> loadDirectorates(){
        List<Directorate> directorates = directorateService.getDirectorates();
        return ResponseEntity.ok(directorates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Directorate> loadDirectorateById(@PathVariable long id){
        Directorate directorates = directorateService.getDirectorateById(id);
        return ResponseEntity.ok(directorates);
    }

    @PostMapping
    public ResponseEntity<String> postDirectorate(@RequestBody PostRequestDirectorate directorate){
        directorateService.createDirectorate(directorate);
        return ResponseEntity.ok("Successfully created directorate");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Directorate> patchDirectorate(@PathVariable long id, @RequestBody PatchRequestDirectorate patchRequestDirectorate){
        Directorate directorate = directorateService.editDirectorate(id, patchRequestDirectorate);
        return ResponseEntity.ok(directorate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDirectorate(@PathVariable long id){
        directorateService.deleteDirectorate(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
