package com.ngnmsn.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {
    @GetMapping(value="/")
    String list(){
        return "sample/list";
    }
}
