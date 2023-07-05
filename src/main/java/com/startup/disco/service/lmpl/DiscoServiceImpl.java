package com.startup.disco.service.lmpl;

import com.startup.disco.global.BaseException;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.repository.PickRepository;
import com.startup.disco.repository.ProductRepository;
import com.startup.disco.service.DiscoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscoServiceImpl implements DiscoService {

    private final ProductRepository productRepository;

    private final PickRepository pickRepository;

    @Override
    public void insertProduct (ProductDTO productDTO) {
        productRepository.save(productDTO);
    }

    @Override
    public List<String> allSelectProduct () throws BaseException {
        List<String> productNameList = new ArrayList<>();
        List<ProductDTO> productDTOList =  productRepository.findAll();
        if (productDTOList.isEmpty()) {
            throw new BaseException("데이터 없음");
        }else {
            for (ProductDTO productDTO : productDTOList) {
                productNameList.add(productDTO.getProductName());
            }
            return productNameList;
        }
    }

    @Override
    public void deletePick(Long pickCd) {
        pickRepository.deleteByPickCd(pickCd);
    }

    @Override
    public List<PickDTO> allSelectPick(String userId) throws BaseException {
        List<PickDTO> pickDTOList = pickRepository.findByUserIdAndDelFlag(userId, "N");

        if (pickDTOList.isEmpty()) {
            throw new BaseException("데이터 없음");
        }else {
            return pickDTOList;
        }
    }
}
