package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "PICK")
public class PickDTO {
    @Id @GeneratedValue
    private long pickCd; // 픽ID

    private String userId; // 사용자id

    private String pickNm; // 제목

    private int topAmt; // 상의 예산 금액
    private int bottomAmt; // 하의 예산 금액
    private int shoesAmt; // 신발 예산 금액

    @Column(length = 2)
    private String sex; // 성별 0:남성, 1:여성, 2:기타
    private String brnd; // 선호브랜드
    private String style; // 선호스타일

    @Column(length = 2)
    private String delFlag;

    public String getSexNm() {
        if(sex.equals("0")) {
            return "남성";
        } else if(sex.equals("1")) {
            return "여성";
        } else {
            return "기타";
        }
    }

    public int getTotalAmt() {
        return topAmt + bottomAmt + shoesAmt;
    }
}
