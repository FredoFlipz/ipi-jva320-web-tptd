package com.ipi.jva320.web;
import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/salaries")
public class SalarieController {

    private final String GESTION_SALARIES = "Gestion des salariés";

    @Autowired
    private SalarieAideADomicileService salarieService;

    @GetMapping(value = "/salaries/{id}")
    public String detailSalarie(@PathVariable Long id, final ModelMap model) {
        SalarieAideADomicile salarie = salarieService.getSalarie(id);
        if (salarie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le salarié avec l'id" + id + " n'existe pas");
        }
        model.addAttribute("titrePage", GESTION_SALARIES);
        model.addAttribute("visible", true);
        model.put("message", "Détails du salarié");
        model.put("salarie", salarie);
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String formulaireCreationSalarie(final ModelMap model) {
        model.addAttribute("titrePage", GESTION_SALARIES);
        model.addAttribute("visible", false);
        model.put("message", "Création d'un salarié");
        model.put("vide", "");
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries")
    public String salariesList(@RequestParam(name = "matricule", required = false) String matricule, @RequestParam(name = "sortDirection", required = false) String sortDirection,final ModelMap model){
        List<SalarieAideADomicile> salaries;
        if(matricule == null){
            salaries = salarieService.getSalaries();
        }else{
            salaries = salarieService.getSalaries().stream()
                    .filter(s -> s.getNom().toLowerCase().contains(matricule.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (sortDirection != null && sortDirection.equals("DESC")) {
            Collections.sort(salaries);
        }

        if (salaries.isEmpty()) {
            return "redirect:/404";
        }

        model.addAttribute("salaries", salaries);
        model.addAttribute("titrePage", GESTION_SALARIES);
        return "list";
    }

    @PostMapping("/salaries/aide/create")
    public String CreationSalarie(RedirectAttributes redirectAttributes, SalarieAideADomicile salarieForm) throws SalarieException {

        salarieService.creerSalarieAideADomicile(salarieForm);
        redirectAttributes.addFlashAttribute("creationReussi", true);
        System.out.println("ID du salarié créé : " + salarieForm.getId());
        Long nb = salarieForm.getId();
        return "redirect:/salaries/" + nb;
    }

    @PostMapping("/salaries/aide/update")
    public String modificationSalarie(RedirectAttributes redirectAttributes, final ModelMap model, SalarieAideADomicile salarieForm) throws SalarieException {
        salarieService.updateSalarieAideADomicile(salarieForm);
        redirectAttributes.addFlashAttribute("modificationReussi", true);
        return "redirect:/salaries/" + salarieForm.getId();
    }

    @GetMapping("/salaries/{id}/delete")
    public String suppressionSalarie(@PathVariable Long id) throws SalarieException {
        salarieService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }
}
