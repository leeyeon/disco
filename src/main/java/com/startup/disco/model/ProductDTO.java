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
    private String productCd; // 상품코드
    private String productName; // 상품명
    private String amt; // 상품금액
    private String brndCd; // 브랜드코드
    private String brndNm; // 브랜드명
    private String price; // 가격
}
