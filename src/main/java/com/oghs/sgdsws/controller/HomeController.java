package com.oghs.sgdsws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author oghs
 */
@Controller
public class HomeController {
    
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "home";
    }
}
