package com.ipi.jva320.web;

import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonController {
    @Autowired
    private SalarieAideADomicileService salarieService;

    /**
     *
     * @param model
     */
    @ModelAttribute
    public void addCommonAttributes(final ModelMap model) {
        model.addAttribute("nombreSalaries", salarieService.countSalaries());
    }
}
