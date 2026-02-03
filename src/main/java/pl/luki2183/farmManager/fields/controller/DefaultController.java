package pl.luki2183.farmManager.fields.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String map(Model model) {
        return "map";
    }

    @GetMapping("/error")
    public String error(Model model) { return "error"; }
}
