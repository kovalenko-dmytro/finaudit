package stud.apach.finaudit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OutputController {

    @GetMapping("/finaudit/enterprises/{enterpriseId}/output")
    public ModelAndView viewOutputs() {

        return new ModelAndView("pages/outputs/view-outputs");
    }
}
