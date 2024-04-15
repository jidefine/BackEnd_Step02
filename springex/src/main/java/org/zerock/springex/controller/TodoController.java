package org.zerock.springex.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/todo")
@Log4j2
public class TodoController {
    // /todo/list
    @RequestMapping("/list")
    public void list(){
        log.info("todo list........");
    }

    // /todo/register
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public void register(){
//        log.info("todo register......");
//    }
    @GetMapping("/register")
    public void registerGet(){
        log.info("GET todo register.....");
    }
    @PostMapping("/regiser")
    public void registerPost(){
        log.info("POST todo register......");
    }
}