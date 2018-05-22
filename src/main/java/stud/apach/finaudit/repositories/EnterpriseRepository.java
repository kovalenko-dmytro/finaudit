package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.User;

public interface EnterpriseRepository extends CrudRepository<Enterprise, Long> {

    Iterable<Enterprise> findByUser(User user);
    Enterprise findByName(String name);
}
