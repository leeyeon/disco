package com.startup.disco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "STYLE")
public class StyleDTO {

    @Id
    private String styleCd = ""; // 선호스타일
    private String style = ""; // 선호스타일


}
