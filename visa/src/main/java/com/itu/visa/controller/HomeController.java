package com.itu.visa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Affiche la page d'accueil du formulaire de demande de visa
     * 
     * @return La vue index
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Route alternative pour accéder au formulaire
     * 
     * @return La vue index
     */
    @GetMapping("/visa")
    public String visaForm() {
        return "index";
    }
}
