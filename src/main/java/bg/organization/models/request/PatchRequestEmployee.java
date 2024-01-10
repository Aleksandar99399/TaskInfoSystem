package bg.organization.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchRequestEmployee {
    private Optional<String> name;
    private Optional<String> lastName;
    private Optional<Integer> age;
    private Optional<@Length(min = 10, max = 10, message = "EGN must contains 10 characters") String> egn;
    private Optional<String> position;
    private Optional<Integer> department;
    private Optional<Integer> directorate;
}
