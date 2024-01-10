package bg.organization.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestEmployee {
    private String name;
    private String lastName;
    private int age;
    @Length(min = 10, max = 10, message = "EGN contains 10 characters")
    private String egn;
    private String position;
    private int department;
    private int directorate;
}
