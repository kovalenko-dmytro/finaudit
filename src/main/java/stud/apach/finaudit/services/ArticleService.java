package stud.apach.finaudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.repositories.ArticleRepository;
import stud.apach.finaudit.services.interfaces.IArticleService;
import stud.apach.finaudit.validate.ArticleForm;

import java.util.List;

@Service
public class ArticleService implements IArticleService {

    @Autowired
    ArticleRepository articleRepo;


    @Override
    public Article selectByName(String name) {

        return articleRepo.findByName(name);
    }

    @Override
    public Article addArticle(ArticleForm articleForm, Statement statement) {

        Article article = new Article();
        article.setName(articleForm.getName());
        article.setCode(articleForm.getCode());
        article.setStatement(statement);

        return articleRepo.save(article);
    }

    @Override
    public Article selectById(long articleId) {

        return articleRepo.findOne(articleId);
    }

    @Override
    public Article updateArticle(ArticleForm articleForm, long articleId) {

        Article article = articleRepo.findOne(articleId);
        article.setName(articleForm.getName());
        article.setCode(articleForm.getCode());

        return articleRepo.save(article);
    }

    @Override
    public void deleteArticle(long articleId) {

        articleRepo.delete(articleId);
    }

    @Override
    public List<Article> selectByStatement(Statement statement) {

        return articleRepo.findByStatement(statement);
    }
}
