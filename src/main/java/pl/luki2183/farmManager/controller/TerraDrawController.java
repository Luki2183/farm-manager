package pl.luki2183.farmManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TerraDrawController {
    @GetMapping("/terra")
    public String terra(Model model) {
        return "index_terra_draw";
    }
}
