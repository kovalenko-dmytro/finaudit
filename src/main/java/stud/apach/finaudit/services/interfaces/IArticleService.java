package stud.apach.finaudit.services.interfaces;

import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.validate.ArticleForm;

import java.util.List;

public interface IArticleService {

    Article selectByName(String name);
    Article addArticle(ArticleForm articleForm, Statement statement);
    Article selectById(long articleId);
    Article updateArticle(ArticleForm articleForm, long articleId);

    void deleteArticle(long articleId);
    List<Article> selectByStatement(Statement statement);
}
