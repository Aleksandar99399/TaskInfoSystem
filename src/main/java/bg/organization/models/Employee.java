package bg.organization.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String egn;
    private int age;
    @Enumerated(value = EnumType.STRING)
    private Position position;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("employees")
    private Department department;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("director")
    private Directorate directorate;
}
