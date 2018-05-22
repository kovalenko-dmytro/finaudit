package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.Input;

import java.sql.Date;
import java.util.List;

public interface InputRepository extends CrudRepository<Input, Long> {
    Input findByArticleAndEnterpriseAndDate(Article article, Enterprise enterprise, Date date);

    List<Input> findDistinctDateByArticleAndEnterpriseOrderByDateDesc(Article article, Enterprise enterprise);
}
