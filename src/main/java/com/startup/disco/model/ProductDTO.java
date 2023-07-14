package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "PRODUCT")
public class ProductDTO {
    @Id
    private String productCd = ""; // 상품코드
    private String productName = ""; // 상품명
    private String brndCd = ""; // 브랜드코드
    private String brndNm = ""; // 브랜드명
    private String price = ""; // 가격
    private String division = ""; // 분류
    private long pickCd = 0; // 픽ID
    private String pickNm = ""; // 제목
    private String userId = ""; // 사용자id
    private String sex = ""; // 성별 0:남성, 1:여성, 2:기타


}
