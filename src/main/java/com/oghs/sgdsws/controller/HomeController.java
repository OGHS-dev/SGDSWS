package com.oghs.sgdsws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping({"/", "/index", "/home"})
public class HomeController {
    
    @GetMapping
    public String inicio() {
        return "home";
    }
    
}
