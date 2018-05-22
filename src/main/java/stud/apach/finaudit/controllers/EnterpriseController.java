package stud.apach.finaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.services.EnterpriseService;
import stud.apach.finaudit.services.StatementService;
import stud.apach.finaudit.services.UserService;
import stud.apach.finaudit.validate.EnterpriseForm;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EnterpriseController {

    @Autowired
    UserService userService;

    @Autowired
    EnterpriseService enterpriseService;

    @Autowired
    StatementService statementService;

    /********************************** CRUD for enterprises **********************************************/

    @GetMapping("finaudit/enterprises/create")
    public ModelAndView viewEnterpriseForm(EnterpriseForm enterpriseForm) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("enterpriseForm", enterpriseForm);
        modelAndView.setViewName("/pages/enterprises/create-enterprise");

        return modelAndView;
    }

    @PostMapping("finaudit/enterprises/create")
    public ModelAndView createEnterprise(@Valid @ModelAttribute("enterpriseForm") EnterpriseForm enterpriseForm, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        Enterprise existing = enterpriseService.selectByName(enterpriseForm.getName());

        if (existing != null) {
            bindingResult.rejectValue("name", "error.enterprise","This enterprise is existing");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("enterpriseForm", enterpriseForm);
            modelAndView.setViewName("/pages/enterprises/create-enterprise");
        } else {
            Enterprise newEnterprise = enterpriseService.addEnterprise(enterpriseForm);
            modelAndView.setViewName("redirect:/finaudit/enterprises");
        }

        return  modelAndView;
    }

    @GetMapping("finaudit/enterprises")
    public ModelAndView viewEnterprises() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("enterprises", enterpriseService.selectByUser(user));
        modelAndView.setViewName("/pages/enterprises/view-enterprises");

        return modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}")
    public ModelAndView getEnterprise(@PathVariable("enterpriseId") long enterpriseId) {
        ModelAndView modelAndView = new ModelAndView();
        Enterprise enterprise = enterpriseService.selectById(enterpriseId);
        List<Statement> statements = statementService.selectAll();

        modelAndView.addObject("enterprise", enterprise);
        modelAndView.addObject("statements", statements);

        modelAndView.setViewName("/pages/enterprises/view-enterprise");

        return modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}/edit")
    public ModelAndView editEnterprise(@PathVariable("enterpriseId") long enterpriseId) {
        ModelAndView modelAndView = new ModelAndView();
        Enterprise enterprise = enterpriseService.selectById(enterpriseId);

        EnterpriseForm enterpriseForm = enterpriseService.getEnterpriseForm(enterprise);

        modelAndView.addObject("enterpriseForm", enterpriseForm);
        modelAndView.setViewName("/pages/enterprises/edit-enterprise");

        return modelAndView;
    }

    @PostMapping("/finaudit/enterprises/{enterpriseId}/edit")
    public ModelAndView editEnterprise(@Valid @ModelAttribute("enterpriseForm") EnterpriseForm enterpriseForm, @PathVariable("enterpriseId") long enterpriseId, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("enterpriseForm", enterpriseForm);
            modelAndView.setViewName("/pages/enterprises/edit-enterprise");
        } else {
            Enterprise editEnterprise = enterpriseService.updateEnterprise(enterpriseForm, enterpriseId);
            modelAndView.setViewName("redirect:/finaudit/enterprises");
        }

        return  modelAndView;
    }

    @GetMapping("/finaudit/enterprises/{enterpriseId}/delete")
    public ModelAndView deleteEnterprise(@PathVariable("enterpriseId") long enterpriseId) {
        ModelAndView modelAndView = new ModelAndView();
        enterpriseService.deleteEnterprise(enterpriseId);
        modelAndView.setViewName("redirect:/finaudit/enterprises");

        return modelAndView;
    }

    /********************************** END CRUD for enterprises **********************************************/

    @PostMapping("finaudit/enterprises/select")
    public ModelAndView searchEnterprise(@ModelAttribute("enterpriseForm") EnterpriseForm enterpriseForm) {

        ModelAndView modelAndView = new ModelAndView();
        Enterprise enterprise = enterpriseService.selectByName(enterpriseForm.getName());
        modelAndView.addObject("enterprise", enterprise);
        modelAndView.setViewName("redirect:/finaudit/enterprises/" + enterprise.getEnterpriseId() + "");

        return  modelAndView;
    }
}
