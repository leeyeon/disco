package com.startup.disco.repository;

import com.startup.disco.model.PickDTO;
import com.startup.disco.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickRepository extends JpaRepository<PickDTO, String> {
    List<PickDTO> findByPickCd(long pickCd);

    void deleteByPickCd(long pickCd);
    List<PickDTO> findByUserIdAndDelFlag(String userId, String delFlag);
}