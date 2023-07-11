package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class DiscoController {
    @Autowired
    OpenAIApiDelegate openAIApiDelegate;

    @Autowired
    DiscoService discoService;

    @ApiOperation(value = "상품 조회")
    @GetMapping("/select/product/{productCd}")
    public ResponseEntity<List<String>> selectProduct (@PathVariable String productCd) throws Exception {
        if(StringUtils.isEmpty(productCd)){
            throw new Exception("GPT productCd 없음");
        }
        return ResponseEntity.ok(discoService.selectByProductCd(productCd));
    }

    @ApiOperation(value = "Pick 삭제")
    @PostMapping("/delete/pick")
    public ResponseEntity<String> deletePick(Long pickCd) throws Exception {
        if (ObjectUtils.isEmpty(pickCd)) {
            throw new Exception("파라미터 오류");
        }else{
            discoService.deletePick(pickCd);
            return ResponseEntity.ok("PICK이 삭제되었습니다.");
        }
    }

    @GetMapping("/openai/create-completion")
    public String test(@RequestParam(value = "message", required = true) String message) {
        return openAIApiDelegate.createCompletions(message)
                .getChoices()
                .stream()
                .map(choice ->
                    choice.getMessage().getContent()
                ).collect(Collectors.joining("\n"));
    }

    @ApiOperation(value = "OPENAI 상품추천 결과")
    @GetMapping("/openai/create-recommend-product")
    public ProductDTO createRecommendProduct(@RequestParam(value = "pickCd", required = true) long pickCd) {
        PickDTO pickDTO = discoService.selectPick(pickCd);
        return openAIApiDelegate.createRecommend(pickDTO);
    }



    @ApiOperation(value = "gpt 임시 api")
    @PostMapping("/recommandGPT/pick")
    public String recommandGPT(String pickCd, Model model) {

        List<ProductDTO> productDTOList = List.of(
                new ProductDTO() {{
                    setProductCd("2152090539");
                    setProductName("[톰보이] 숏 리버시블 무스탕");
                    setDivision("상의");
                    setPrice("119700");
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                }},
                new ProductDTO() {{
                    setProductCd("2152078631");
                    setProductName("톰보이 9173331971 백밴딩 원턱 와이드데님");
                    setDivision("하의");
                    setPrice("129000");
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                }}
        );

        discoService.insertProduct(productDTOList);

        return "jsonView";

    }

}
