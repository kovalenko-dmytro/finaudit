package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.Statement;

public interface StatementRepository extends CrudRepository<Statement, Long> {
    Statement findByName(String name);
}
