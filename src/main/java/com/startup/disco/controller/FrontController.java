package com.startup.disco.controller;

import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class FrontController {

    @Autowired
    DiscoService discoService;

    @GetMapping("/index")
    public String index() {

        log.debug("this is index page");

        return "index";
    }

    @GetMapping("/main")
    public String main(Model model) {
        List<PickDTO> pickDTOList = discoService.allSelectPick("USER1");

        model.addAttribute("pickDTOList", pickDTOList);
        model.addAttribute("msg", "나오낭");

        return "main";
    }

    @ApiOperation(value = "pick 추가")
    @PostMapping("pick.do")
    public String insertPick(@RequestParam String pickNm) {
        //Long strPickCd = Long.valueOf("001");
        Long strPickCd = 001L;
        //PickDTO pickDTO = new PickDTO();

        //pickDTO.setPickNm(pickNm);
        System.out.println("!!!!!!!!!!!!!!@@!~~~ :"+pickNm);
        discoService.insertPick(pickNm);

        //model.addAttribute("pickDTOList", pickDTOList);
        //model.addAttribute("msg", "저장되낭");

        return "main";
    }

}
