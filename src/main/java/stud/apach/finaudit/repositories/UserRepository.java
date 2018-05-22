package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
