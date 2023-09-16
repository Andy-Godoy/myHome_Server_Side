package com.ad.myhome.controller;

import com.ad.myhome.MyhomeApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class TestController {

    private final String CLASS_NAME = MyhomeApplication.class.getSimpleName();

    @GetMapping(value = "/test")
    public String test(){
        String msg = CLASS_NAME.concat(" esta funcionando ok!");
        return msg;
    }

}
