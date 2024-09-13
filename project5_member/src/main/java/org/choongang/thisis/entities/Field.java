package org.choongang.thisis.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    private String id; //학문별 분류 코드
    private String name; //대 분류명
    private String subfield; //중 분류명
}
