package univ.rouen.cv24.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/help")
public class HelpController {

    public ModelAndView getHelpPage() {
        return new ModelAndView("help");
    }
}
