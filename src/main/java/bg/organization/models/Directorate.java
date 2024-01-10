package bg.organization.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "directorates")
public class Directorate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("directorate")
    private Employee director;
    @OneToMany
    @Builder.Default
    @JsonIgnoreProperties("employee")
    private Set<Department> departments = new HashSet<>();
}
