package com.startup.disco.service.lmpl;

import com.startup.disco.model.ProductDTO;
import com.startup.disco.repository.ProductRepository;
import com.startup.disco.service.discoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class discoServiceImpl implements discoService {

    private final ProductRepository productRepository;

    @Override
    public void insertProduct (ProductDTO productDTO) {
        productRepository.save(productDTO);
    }

    @Override
    public List<String> allSelectProduct () throws Exception {
        List<String> productNameList = new ArrayList<>();
        List<ProductDTO> productDTOList =  productRepository.findAll();
        if (productDTOList.isEmpty()) {
            throw new Exception("데이터 없음");
        }else {
            for (ProductDTO productDTO : productDTOList) {
                productNameList.add(productDTO.getProductName());
            }
            return productNameList;
        }
    }
}
