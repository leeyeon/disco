package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@SequenceGenerator(
        name = "pickCd",
        sequenceName = "pickCd_sequence",
        initialValue = 3,
        allocationSize = 1
)
@Table(name = "PICK")
public class PickDTO {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pickCd")
    private long pickCd; // 픽ID

    private String userId = ""; // 사용자id

    private String pickNm = ""; // 제목

    private int topAmt = 0; // 상의 예산 금액
    private int bottomAmt = 0; // 하의 예산 금액
    private int shoesAmt = 0; // 신발 예산 금액

    @Column(length = 2)
    private String sex = ""; // 성별 0:남성, 1:여성, 2:기타
    private String brnd = ""; // 선호브랜드
    private String style = ""; // 선호스타일

    @Column(length = 2)
    private String delFlag = "";

    @Transient
    private List<String> productCdList;

    @Transient
    private int recommendCnt = 2; // 상품 픽 개수 (디폴트 2)

    @Transient
    private int recommendProductCnt; // 기추천한 상품개수

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
