package bg.organization.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchRequestDepartment {
    private Optional<String> name;
    private Optional<String> description;
    //add or remove employee from department
    private String operation;
    private String position;
    private List<Integer> employeesId;
}
