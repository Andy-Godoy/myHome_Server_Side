package com.myhome.servicioinmobiliaria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/agencies")
public class InmobiliariaController {

    @GetMapping(value = "")
    public String getAgencies(){
        return "Esta todo OK!";
    }
}
