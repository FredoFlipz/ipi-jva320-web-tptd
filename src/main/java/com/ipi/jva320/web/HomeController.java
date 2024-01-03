package com.ipi.jva320.web;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.MessageFormat;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarie;

    @GetMapping(value = "/")
    public String home(final ModelMap model){
        String nbSalarie = salarie.countSalaries().toString();
        model.put("nombreSalaries", salarie.countSalaries().toString());
        model.put("msgAccueil", "Bienvenue dans l'interface d'administration RH ! (" + nbSalarie + " salari\u00e9(s))");
        model.addAttribute("titrePage", "Aide à domicile RH - gestion des salariés");
        return "home";
    }
}