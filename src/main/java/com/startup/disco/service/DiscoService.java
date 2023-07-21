package com.startup.disco.service;

import com.startup.disco.global.BaseException;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import com.startup.disco.model.StyleDTO;

import java.util.List;

public interface DiscoService {

    /* 픽 관련 서비스 */
    void deletePick(Long pickCd);
    List<PickDTO> allSelectPick(String userId) throws BaseException;
    PickDTO selectPick(long pickCd) throws BaseException;
    PickDTO insertPick(Integer bottomAmt, String delFlag, String brnd, String pickNm, String sex, String style, Integer topAmt, String userId);
    List<StyleDTO> allSelectStyle() throws BaseException;

}
