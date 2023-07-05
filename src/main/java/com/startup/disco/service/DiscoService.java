package com.startup.disco.service;

import com.startup.disco.global.BaseException;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;

import java.util.List;

public interface DiscoService {

    /* 상품 관련 서비스 */
    void insertProduct(ProductDTO productDTO);

    List<String> allSelectProduct() throws BaseException;

    /* 픽 관련 서비스 */
    void deletePick(Long pickCd);
    List<PickDTO> allSelectPick(String userId) throws BaseException;

}
