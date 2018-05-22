package stud.apach.finaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.services.StatementService;
import stud.apach.finaudit.validate.StatementForm;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StatementController {

    @Autowired
    StatementService statementService;

    @GetMapping("finaudit/admin/create")
    public ModelAndView viewStatementForm(StatementForm statementForm) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("statementForm", statementForm);
        modelAndView.setViewName("/pages/admin/statements/create-statement");

        return modelAndView;
    }

    @PostMapping("finaudit/admin/create")
    public ModelAndView createStatement(@Valid @ModelAttribute("statementForm") StatementForm statementForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Statement existing = statementService.selectByName(statementForm.getName());

        if (existing != null) {
            bindingResult.rejectValue("name", "error.statement","This statement is existing");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("statementForm", statementForm);
            modelAndView.setViewName("/pages/admin/statements/create-statement");
        } else {
            statementService.addStatement(statementForm);
            modelAndView.setViewName("redirect:/finaudit/admin");
        }

        return  modelAndView;
    }

    @GetMapping("finaudit/admin/statements")
    public ModelAndView viewStatements() {

        ModelAndView modelAndView = new ModelAndView();

        List<Statement> statements = statementService.selectAll();
        modelAndView.addObject("statements", statements);
        modelAndView.setViewName("/pages/admin/statements/view-statements");

        return modelAndView;
    }

    @GetMapping("/finaudit/admin/statements/{statementId}")
    public ModelAndView getStatement(@PathVariable("statementId") long statementId) {

        ModelAndView modelAndView = new ModelAndView();
        Statement statement = statementService.selectById(statementId);
        modelAndView.addObject("statement", statement);
        modelAndView.setViewName("/pages/admin/statements/view-statement");

        return modelAndView;
    }

    @GetMapping("/finaudit/admin/statements/{statementId}/edit")
    public ModelAndView editStatement(@PathVariable("statementId") long statementId) {

        ModelAndView modelAndView = new ModelAndView();
        Statement statement = statementService.selectById(statementId);

        StatementForm statementForm = new StatementForm();
        statementForm.setName(statement.getName());

        modelAndView.addObject("statementForm", statementForm);
        modelAndView.setViewName("/pages/admin/statements/edit-statement");

        return modelAndView;
    }

    @PostMapping("/finaudit/admin/statements/{statementId}/edit")
    public ModelAndView editStatement(@Valid @ModelAttribute("statementForm") StatementForm statementForm, @PathVariable("statementId") long statementId, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("statementForm", statementForm);
            modelAndView.setViewName("/pages/admin/statements/edit-statement");
        } else {
            statementService.updateStatement(statementForm, statementId);
            modelAndView.setViewName("redirect:/finaudit/admin/statements");
        }

        return  modelAndView;
    }

    @GetMapping("/finaudit/admin/statements/{statementId}/delete")
    public ModelAndView deleteEnterprise(@PathVariable("statementId") long statementId) {

        ModelAndView modelAndView = new ModelAndView();
        statementService.deleteStatement(statementId);
        modelAndView.setViewName("redirect:/finaudit/admin/statements");

        return modelAndView;
    }
}
