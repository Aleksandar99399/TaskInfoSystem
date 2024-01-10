package bg.organization.repositories;

import bg.organization.models.Directorate;
import bg.organization.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorateRepository extends JpaRepository<Directorate, Long> {

    Optional<Directorate> findByName(String name);

    Optional<Directorate> findByDirector(Employee employee);
}
