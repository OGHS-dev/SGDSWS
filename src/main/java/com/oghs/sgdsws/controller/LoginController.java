package com.oghs.sgdsws.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author oghs
 */
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (error != null) {
            model.addAttribute("error", "Error de acceso: Usuario y/o contraseña incorrectos");
        }
        if (principal != null) {
            redirectAttributes.addFlashAttribute("warning", "Atención: Sesión ya iniciada previamente");
            return "redirect:/index";
        }
        if (logout != null) {
            model.addAttribute("success", "Sesión finalizada con éxito");
        }

        return "login";
    }
}
