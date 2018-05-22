package stud.apach.finaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/finaudit/login")
    public ModelAndView showLoginForm() {

        return new ModelAndView("/pages/login");
    }

    @GetMapping("/finaudit/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/finaudit/login?logout";
    }

    @GetMapping("/finaudit/register")
    public ModelAndView showRegisterForm(User user) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/pages/register");

        return modelAndView;
    }

    @PostMapping("/finaudit/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        User existing = userService.findByEmail(user.getEmail());

        if (existing != null) {
            bindingResult.rejectValue("email", "error.user","The email is existing");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/pages/register");
        } else {
            User registerUser = userService.registerNewUser(user);
            modelAndView.setViewName("redirect:/finaudit");
        }

        return modelAndView;
    }
}
