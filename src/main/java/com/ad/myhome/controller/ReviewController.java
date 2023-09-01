package com.ad.myhome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    @GetMapping(value = "/test")
    public String test(){
        String msg = CLASS_NAME.concat(" esta funcionando ok!");
        return msg;
    }

}
