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
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.services.EnterpriseService;
import stud.apach.finaudit.services.UserService;
import stud.apach.finaudit.validate.EnterpriseForm;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    EnterpriseService enterpriseService;

    @GetMapping("/finaudit")
    public ModelAndView index() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        modelAndView.addObject("enterprises", enterpriseService.selectByUser(user));
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/finaudit/admin")
    public ModelAndView admin() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pages/admin/index");

        return modelAndView;
    }


}
