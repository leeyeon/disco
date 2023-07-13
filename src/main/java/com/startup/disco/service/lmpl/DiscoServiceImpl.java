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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscoServiceImpl implements DiscoService {

    private final ProductRepository productRepository;

    private final PickRepository pickRepository;

    @Override
    public void insertProduct (List<ProductDTO> productDTOList) {
        for (ProductDTO gptRecm : productDTOList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductCd(gptRecm.getProductCd());
            productDTO.setBrndCd(gptRecm.getBrndCd());
            productDTO.setBrndNm(gptRecm.getBrndNm());
            productDTO.setDivision(gptRecm.getDivision());
            productDTO.setPrice(gptRecm.getPrice());
            productDTO.setProductName(gptRecm.getProductName());
            productDTO.setPickCd(gptRecm.getPickCd());
            productDTO.setPickNm(gptRecm.getPickNm());
            productDTO.setUserId(gptRecm.getUserId());
            productDTO.setSex(gptRecm.getSex());

            productRepository.save(productDTO);

        }
    }

    @Override
    public List<String> selectByProductCd(String productCd) throws BaseException {
        List<String> resultList = new ArrayList<>();
        List<ProductDTO> productList = Optional.ofNullable(productRepository.findByProductCd(productCd)).orElse(new ArrayList<>());
        if (productList.isEmpty()) {
            throw new BaseException("해당 코드로 된 상품 없음");
        }
        for (ProductDTO productDTO : productList) {
            resultList.add(productDTO.getProductName());
            resultList.add(productDTO.getPrice());
        }
        return resultList;
    }

    @Override
    public PickDTO selectPick(Long pickCd) {
        return pickRepository.findByPickCd(pickCd);
    }

    @Override
    @Transactional
    public void deletePick(Long pickCd) {
        PickDTO pick = pickRepository.findByPickCd(pickCd);
        pick.setDelFlag("Y");
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

    public void insertPick (String pickNm){
        System.out.println("~~~~ServiceImpl 들어옴 ! "+pickNm);
        PickDTO pickDto = new PickDTO();
        pickDto.setPickNm(pickNm);
        PickDTO save = pickRepository.save(pickDto);
    }

    @Override
    public List<ProductDTO> allProductList() throws BaseException {
        List<ProductDTO> productDTOList = productRepository.findAll();

        if (productDTOList.isEmpty()) {
            throw new BaseException("데이터 없음");
        }
        return productDTOList;
    }

}
