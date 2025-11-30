package pl.luki2183.farmManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Map3DController {
    @GetMapping("/3d")
    public String map3d(Model model){
        return "map3d";
    }
}
