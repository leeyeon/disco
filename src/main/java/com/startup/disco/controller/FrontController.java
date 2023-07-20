package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.model.StyleDTO;
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

        List<StyleDTO> styleDTOList = discoService.allSelectStyle();

        model.addAttribute("styleDTOList", styleDTOList);

        return "main";
    }

    @ApiOperation(value = "pick 추가")
    @RequestMapping(value = "/insertPick", method = { RequestMethod.POST })
    public String insertPick(@RequestParam("pickNm") String pickNm,
                             @RequestParam("bottom") Integer bottomAmt,
                             @RequestParam("brand") String brnd,
                             @RequestParam("sex") String sex,
                             @RequestParam("style") String style,
                             @RequestParam("topWear") Integer topAmt) {

        String delFlag = "N";
        String userId = "USER1";

        discoService.insertPick(bottomAmt, delFlag, brnd, pickNm, sex, style, topAmt, userId);

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

        /*
        List<ProductDTO> productDTOList = List.of(
                new ProductDTO() {{
                    setProductCd("2152090539");
                    setProductName("[톰보이] 숏 리버시블 무스탕");
                    setDivision("상의");
                    setPrice(String.valueOf(119700));
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                    setPickCd(1);
                    setUserId("USER1");
                    setSex("1");
                }},
                new ProductDTO() {{
                    setProductCd("2152078631");
                    setProductName("톰보이 9173331971 백밴딩 원턱 와이드데님");
                    setDivision("하의");
                    setPrice(String.valueOf(129000));
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                    setPickCd(1);
                    setUserId("USER1");
                    setSex("1");
                }}
        );



        List<String> productStringList = new ArrayList<>();
        if(!productDTOList.isEmpty()) {
            productStringList = productDTOList.stream()
                    .map(ProductDTO::getProductCd)
                    .collect(Collectors.toList());
            pickDTO.setProductCdList(productStringList); // 기추천받은 상품목록 세팅
        }

        JSONObject gptResposeData = new JSONObject();
        gptResposeData.put("pickCd", pickCd);
        gptResposeData.put("productDTOList", new JSONArray(productDTOList));
        model.addAttribute("gptResposeData", gptResposeData.toString());


        */
        model.addAttribute("pickCd", pickCd);
        model.addAttribute("productDTOList", productDTOList);

        return "myPickResult";
    }

}
