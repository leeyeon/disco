package com.startup.disco.controller;

import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class FrontController {

    @Autowired
    DiscoService discoService;

    @GetMapping("/index")
    public String main() {

        log.debug("this is index page");

        return "index";
    }

}
