package ar.dantezulli.diet_formulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root redirect to profiles page.
 * Redirección de la raíz a la página de perfiles.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/profiles";
    }
}
