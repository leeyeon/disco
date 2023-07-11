package com.startup.disco.service;

import com.startup.disco.global.BaseException;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;

import java.util.List;

public interface DiscoService {

    /* 상품 관련 서비스 */
    void insertProduct(List<ProductDTO> productDTO);

    List<String> selectByProductCd(String productCd) throws BaseException;

    /* 픽 관련 서비스 */
    PickDTO selectPick(Long pickCd);
    void deletePick(Long pickCd);
    List<PickDTO> allSelectPick(String userId) throws BaseException;
    void insertPick(String pickNm);

    List<ProductDTO> allProductList() throws BaseException;
}
