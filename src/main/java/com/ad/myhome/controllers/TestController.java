package com.ad.myhome.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class TestController {

    @GetMapping(value = "/test")
    public String test(){
        return "Esta ok!";
    }

}
