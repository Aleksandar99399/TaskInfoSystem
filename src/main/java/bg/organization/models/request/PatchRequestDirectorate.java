package bg.organization.models.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchRequestDirectorate extends PostRequestDirectorate{
    private String operation;
    private long directorId;
}
