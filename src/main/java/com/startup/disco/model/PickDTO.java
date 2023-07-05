package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "PICK")
public class PickDTO {
    @Id @GeneratedValue
    private String pickCd; // 픽ID
    private String topAmt; // 상의 예산 금액
    private String bottomAmt; // 하의 예산 금액
    private String shoesAmt; // 신발 예산 금액
    private String sex; // 성별
    private String brand; // 선호브랜드
    private String style; // 선호스타일
}
