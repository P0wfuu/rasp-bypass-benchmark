package com.example.raspbypass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.instrument.Instrumentation;

@Controller
@RequestMapping("bypass4")
public class Bypass4 {

    @GetMapping("/")
    @ResponseBody
    public String bypass4(){
        return "To do~";
    }

}
