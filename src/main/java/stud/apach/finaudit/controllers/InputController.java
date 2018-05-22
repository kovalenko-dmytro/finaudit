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
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.Input;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.services.ArticleService;
import stud.apach.finaudit.services.EnterpriseService;
import stud.apach.finaudit.services.InputService;
import stud.apach.finaudit.services.StatementService;
import stud.apach.finaudit.validate.InputForm;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InputController {

    @Autowired
    InputService inputService;

    @Autowired
    ArticleService articleService;

    @Autowired
    StatementService statementService;

    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}/create")
    public ModelAndView viewInputForm(@PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId) {

        ModelAndView modelAndView = new ModelAndView();

        List<Statement> statements = statementService.selectAll();

        InputForm inputForm = inputService.createInputForm(statementId);

        String statementName = statementService.selectById(statementId).getName();

        modelAndView.addObject("inputForm", inputForm);
        modelAndView.addObject("statements", statements);
        modelAndView.addObject("statementName", statementName);
        modelAndView.addObject("enterpriseId", enterpriseId);
        modelAndView.addObject("statementId", statementId);

        modelAndView.setViewName("/pages/inputs/create-input");

        return modelAndView;
    }

    @PostMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}/create")
    public ModelAndView createInputs(@Valid @ModelAttribute("inputForm") InputForm inputForm, @PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Enterprise enterprise = enterpriseService.selectById(enterpriseId);
        Statement statement = statementService.selectById(statementId);


        Input existingInput = null;
        System.out.println(inputForm.getInputs());
        for (Input input : inputForm.getInputs()) {
            existingInput = inputService.selectByArticleAndEnterpriseAndDate(input.getArticle(), enterprise, inputForm.getDate());
            if (existingInput != null) {break;}
        }

        if (existingInput != null) {
            bindingResult.rejectValue("date", "error.inputForm","This date is existing");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("inputForm", inputForm);
            modelAndView.setViewName("/pages/inputs/create-input");
        } else {
            inputService.addInputs(inputForm, enterprise, statement);
            modelAndView.setViewName("redirect:/finaudit/enterprises/" + enterpriseId + "");
        }

        return  modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}")
    public ModelAndView viewInputs(@PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId) {

        ModelAndView modelAndView = new ModelAndView();

        List<Statement> statements = statementService.selectAll();

        Statement statement = statementService.selectById(statementId);
        String statementName = statement.getName();
        List<Article> articles = articleService.selectByStatement(statement);
        Enterprise enterprise = enterpriseService.selectById(enterpriseId);

        List<Input> inputs;
        if (articles.size() != 0) {
            inputs = inputService.selectDistinctDateByArticleAndEnterpriseOrderByDateDesc(articles.get(0), enterprise);
        } else {inputs = null;}

        modelAndView.addObject("statements", statements);
        modelAndView.addObject("statementName", statementName);
        modelAndView.addObject("enterpriseId", enterpriseId);
        modelAndView.addObject("statementId", statementId);
        modelAndView.addObject("inputs", inputs);

        modelAndView.setViewName("/pages/inputs/view-inputs");

        return modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}/{date}/edit")
    public ModelAndView editInputs(@PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId, @PathVariable("date") Date date) {

        ModelAndView modelAndView = new ModelAndView();

        List<Statement> statements = statementService.selectAll();

        Statement statement = statementService.selectById(statementId);
        String statementName = statement.getName();
        List<Article> articles = articleService.selectByStatement(statement);
        Enterprise enterprise = enterpriseService.selectById(enterpriseId);

        List<Input> inputs = new ArrayList<>();
        Input input;

        for (Article article : articles) {
           input = inputService.selectByArticleAndEnterpriseAndDate(article, enterprise, date);
           inputs.add(input);
        }

        InputForm inputForm = new InputForm();
        inputForm.setDate(date);
        inputForm.setInputs(inputs);

        modelAndView.addObject("statements", statements);
        modelAndView.addObject("statementName", statementName);
        modelAndView.addObject("enterpriseId", enterpriseId);
        modelAndView.addObject("statementId", statementId);
        modelAndView.addObject("inputsDate", date);
        modelAndView.addObject("inputForm", inputForm);

        modelAndView.setViewName("/pages/inputs/edit-inputs");

        return modelAndView;
    }

    @PostMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}/{date}/edit")
    public ModelAndView editInputs(@Valid @ModelAttribute("inputForm") InputForm inputForm, @PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId, @PathVariable("date") Date date, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("inputForm", inputForm);
            modelAndView.setViewName("/pages/inputs/edit-inputs");
        } else {
            inputService.updateInputs(inputForm, enterpriseId, statementId);
            modelAndView.setViewName("redirect:/finaudit/enterprises/" + enterpriseId + "/statements/" + statementId + "");
        }

        return modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}/statements/{statementId}/{date}/delete")
    public ModelAndView deleteInputs(@PathVariable("enterpriseId") Long enterpriseId, @PathVariable("statementId") Long statementId, @PathVariable("date") Date date) {

        ModelAndView modelAndView = new ModelAndView();

        inputService.deleteInputs(enterpriseId, statementId, date);

        modelAndView.setViewName("redirect:/finaudit/enterprises/" + enterpriseId + "/statements/" + statementId + "");

        return modelAndView;
    }
}
