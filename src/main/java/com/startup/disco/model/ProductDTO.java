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
    private String productCd; // 품번
    private String productName; // 상품명
    private String amt; // 상품금액
    private String brndCd; // 브랜드코드
}
