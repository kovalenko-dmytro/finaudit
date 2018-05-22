package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(String role);
}
