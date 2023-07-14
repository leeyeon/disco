package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 검색결과
     * @param model
     * @param pickCd
     * @return
     */
    @GetMapping("/myPickResult")
    public String myPickResult(Model model, @RequestParam(value = "pickCd", required = true) long pickCd) {
        PickDTO pickDTO = discoService.selectPick(pickCd);

        List<ProductDTO> productDTOList = openAIApiDelegate.createRecommend(pickDTO);

        List<String> productStringList = new ArrayList<>();
        if(!productDTOList.isEmpty()) {
            productStringList = productDTOList.stream()
                    .map(ProductDTO::getProductCd) // 필드 이름을 원하는 필드로 대체하세요
                    .collect(Collectors.toList());
            pickDTO.setProductList(productStringList); // 기추천받은 상품목록 세팅
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        response.put("pickCd", pickCd);
        response.put("productDTOList", new JSONArray(productDTOList));
        model.addAttribute("jsonRequest", response.toString());
        model.addAttribute("productDTOList", productDTOList);

        return "myPickResult";
    }

}
