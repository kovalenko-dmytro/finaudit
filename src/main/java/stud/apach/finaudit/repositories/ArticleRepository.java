package stud.apach.finaudit.repositories;

import org.springframework.data.repository.CrudRepository;
import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Statement;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findByName(String name);
    List<Article> findByStatement(Statement statement);
}
