package stud.apach.finaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.services.ArticleService;
import stud.apach.finaudit.services.StatementService;
import stud.apach.finaudit.validate.ArticleForm;

import javax.validation.Valid;

@Controller
public class ArticleController {

    @Autowired
    StatementService statementService;

    @Autowired
    ArticleService articleService;

    @GetMapping("finaudit/admin/statements/{statementId}/create")
    public ModelAndView viewArticleForm(@PathVariable("statementId") long statementId, ArticleForm articleForm) {

        ModelAndView modelAndView = new ModelAndView();
        Statement statement = statementService.selectById(statementId);

        modelAndView.addObject("statement", statement);
        modelAndView.addObject("articleForm", articleForm);

        modelAndView.setViewName("/pages/admin/articles/create-article");

        return modelAndView;
    }

    @PostMapping("finaudit/admin/statements/{statementId}/create")
    public ModelAndView createArticle(@PathVariable("statementId") long statementId, @Valid @ModelAttribute("articleForm") ArticleForm articleForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Article existing = articleService.selectByName(articleForm.getName());

        if (existing != null) {
            bindingResult.rejectValue("name", "error.article","This article is existing");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleForm", articleForm);
            modelAndView.setViewName("/pages/admin/articles/create-article");
        } else {
            articleService.addArticle(articleForm, statementService.selectById(statementId));
            modelAndView.setViewName("redirect:/finaudit/admin/statements/" + statementId + "");
        }

        return  modelAndView;
    }

    @GetMapping("/finaudit/admin/statements/{statementId}/{articleId}/edit")
    public ModelAndView editArticle(@PathVariable("statementId") long statementId, @PathVariable("articleId") long articleId) {

        ModelAndView modelAndView = new ModelAndView();
        Statement statement = statementService.selectById(statementId);
        Article article = articleService.selectById(articleId);

        ArticleForm articleForm = new ArticleForm();
        articleForm.setName(article.getName());
        articleForm.setCode(article.getCode());

        modelAndView.addObject("statement", statement);
        modelAndView.addObject("article", article);
        modelAndView.addObject("articleForm", articleForm);
        modelAndView.setViewName("/pages/admin/articles/edit-article");

        return modelAndView;
    }

    @PostMapping("/finaudit/admin/statements/{statementId}/{articleId}/edit")
    public ModelAndView editArticle(@Valid @ModelAttribute("articleForm") ArticleForm articleForm, @PathVariable("statementId") long statementId, @PathVariable("articleId") long articleId, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleForm", articleForm);
            modelAndView.setViewName("/pages/admin/articles/edit-article");
        } else {
            articleService.updateArticle(articleForm, articleId);
            modelAndView.setViewName("redirect:/finaudit/admin/statements/" + statementId + "");
        }

        return  modelAndView;
    }

    @GetMapping("/finaudit/admin/statements/{statementId}/{articleId}/delete")
    public ModelAndView deleteArticle(@PathVariable("statementId") long statementId, @PathVariable("articleId") long articleId) {

        ModelAndView modelAndView = new ModelAndView();
        articleService.deleteArticle(articleId);
        modelAndView.setViewName("redirect:/finaudit/admin/statements/" + statementId + "");

        return modelAndView;
    }
}
