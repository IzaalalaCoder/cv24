package univ.rouen.cv24.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelpController {

    @GetMapping("/help")
    public ModelAndView getHelpPage() {
        return new ModelAndView("web/html/help.html");
    }
}
