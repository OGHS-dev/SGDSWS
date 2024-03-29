package com.oghs.sgdsws.controller;

import java.security.Principal;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        if (!Objects.isNull(error)) {
            model.addAttribute("error", "Error de acceso: Usuario y/o contraseña incorrectos");
        }
        if (!Objects.isNull(principal)) {
            redirectAttributes.addFlashAttribute("warning", "Atención: Sesión ya iniciada previamente");
            
            return "redirect:/index";
        }
        if (!Objects.isNull(logout)) {
            model.addAttribute("success", "Sesión finalizada con éxito");
        }

        return "login";
    }
}
