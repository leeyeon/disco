package com.startup.disco.service.lmpl;

import com.startup.disco.global.BaseException;
import com.startup.disco.model.PickDTO;
import com.startup.disco.model.StyleDTO;
import com.startup.disco.repository.PickRepository;
import com.startup.disco.repository.StyleRepository;
import com.startup.disco.service.DiscoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscoServiceImpl implements DiscoService {

    private final PickRepository pickRepository;
    private final StyleRepository styleRepository;

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

    @Override
    public PickDTO selectPick(long pickCd) throws BaseException {
        return pickRepository.findByPickCd(pickCd);
    }

    public void insertPick (Integer bottomAmt, String delFlag, String brnd, String pickNm, String sex, String style, Integer topAmt, String userId){

        PickDTO pickDto = new PickDTO();
        pickDto.setPickNm(pickNm);
        pickDto.setBottomAmt(bottomAmt);
        pickDto.setDelFlag(delFlag);
        pickDto.setBrnd(brnd);
        pickDto.setSex(sex);
        pickDto.setStyle(style);
        pickDto.setTopAmt(topAmt);
        pickDto.setUserId(userId);

        pickRepository.save(pickDto);
    }
    public List<StyleDTO> allSelectStyle() throws BaseException {
        List<StyleDTO> styleList = styleRepository.findAll();

        if (styleList.isEmpty()) {
            throw new BaseException("데이터 없음");
        }else {
            return styleList;
        }
    }

}
