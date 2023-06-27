package com.startup.disco.controller;

import com.startup.disco.model.ProductDTO;
import com.startup.disco.service.discoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class discoController {

    @Autowired
    discoService discoService;
    

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
    @GetMapping("/all/select/product")
    public ResponseEntity<List<String>> allSelectProduct () throws Exception {
        return ResponseEntity.ok(discoService.allSelectProduct());
    }
    

}
