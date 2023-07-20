package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.model.ReSearchDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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

    @ApiOperation(value = "OPENAI 재 상품추천 결과")
    @PostMapping("/openai/create-recommend-product")
    public List<ProductDTO> reCommandList(@RequestBody ReSearchDTO reSearchDTO) {

        Long pickCd = reSearchDTO.getPickCd();
        //List<ProductDTO> productDTOList = reSearchDTO.getProductDTOList();
        System.out.println("pickCd : " + pickCd);

        PickDTO pickDTO = discoService.selectPick(pickCd);

        /*
        if(productDTOList != null && !productDTOList.isEmpty()) {
            List<String> productCdList = productDTOList.stream()
                    .map(ProductDTO::getProductCd) // 필드 이름을 원하는 필드로 대체하세요
                    .collect(Collectors.toList());
            pickDTO.setProductCdList(productCdList); // 기추천받은 상품목록 세팅
            pickDTO.setRecommendProductCnt(productCdList.size());
        }
        */

        pickDTO.setRecommendProductCnt(reSearchDTO.getRecommendProductCnt()); // 기추천받은 상품개수 세팅

        return openAIApiDelegate.createRecommend(pickDTO);

        /*
        return List.of(
                new ProductDTO() {{
                    setProductCd("2151567416");
                    setProductName("톰보이 TOMBOY  코튼 린넨 셔츠원피스 (9103241435)");
                    setDivision("상하의");
                    setPrice(String.valueOf(155400));
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                    setPickCd(1);
                    setUserId("USER1");
                    setSex("1");
                }},
                new ProductDTO() {{
                    setProductCd("2151565519");
                    setProductName("[TOMBOY] 트위스티드 코튼 원피스 9103241424 (1 color)");
                    setDivision("상하의");
                    setPrice(String.valueOf(143400));
                    setBrndCd("002331");
                    setBrndNm("톰보이(백화점)");
                    setPickCd(1);
                    setUserId("USER1");
                    setSex("1");
                }}
        );

        */
    }
}
