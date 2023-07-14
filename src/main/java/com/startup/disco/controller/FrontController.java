package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class FrontController {

    @Autowired
    OpenAIApiDelegate openAIApiDelegate;

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
    @RequestMapping(value = "/insertPick", method = { RequestMethod.POST })
    public String insertPick(@RequestParam("pickNm") String pickNm,
                             @RequestParam("bottom") Integer bottomAmt,
                             @RequestParam("favBrand") String brnd,
                             @RequestParam("sex") String sex,
                             @RequestParam("style") String style,
                             @RequestParam("topWear") Integer topAmt) {
        //pickDTO.setPickNm(pickNm);

        System.out.println("!!!!!!!!!!!!!!@@!~~~ :"+pickNm);
        System.out.println("!!!!!!!!!!!!!!@@!~~~ :"+bottomAmt);
        System.out.println("!!!!!!!!!!!!!!@@!~~~ :"+style);
        String delFlag = "N";
        String userId = "USER1";

        discoService.insertPick(bottomAmt, delFlag, brnd, pickNm, sex, style, topAmt, userId);

        //model.addAttribute("pickDTOList", pickDTOList);
        //model.addAttribute("msg", "저장되낭");

        return "main";
    }

    @GetMapping("/myPickResult")
    public String myPickResult(Model model, @RequestParam(value = "pickCd", required = true) long pickCd) {
        PickDTO pickDTO = discoService.selectPick(pickCd);
        List<ProductDTO> productDTOList = openAIApiDelegate.createRecommend(pickDTO);

        model.addAttribute("productDTOList", productDTOList);

        return "myPickResult";
    }

}
