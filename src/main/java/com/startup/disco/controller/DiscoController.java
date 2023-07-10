package com.startup.disco.controller;

import com.startup.disco.delegate.OpenAIApiDelegate;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.DiscoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "상품 추가")
    @PostMapping("/insert/product")
    public ResponseEntity<String> insertProduct(ProductDTO productDTO) throws Exception {
        if (ObjectUtils.isEmpty(productDTO)) {
            throw new Exception("파라미터 오류");
        }else{
            discoService.insertProduct(productDTO);
            return ResponseEntity.ok("제품 추가 완료");
        }
    }

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

    @GetMapping("/openai/create-recommend-product")
    public ProductDTO test2(@RequestParam(value = "pickCd", required = true) long pickCd) {

        PickDTO pickDTO = discoService.selectPick(pickCd);

        return openAIApiDelegate.createRecommend(pickDTO);
    }


}
