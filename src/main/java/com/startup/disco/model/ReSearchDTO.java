package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReSearchDTO {
    private long pickCd = 0; // 픽ID
    private int recommendProductCnt = 0; // 기 추천한 상품개수
    private List<ProductDTO> productDTOList;

}
