package com.vintrace.codingchallenge.service.model;

import com.vintrace.codingchallenge.repository.entity.ComponentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Component {
    private Integer id;
    private String wineLotCode;
    private Integer year;
    private Double percentage;
    private String variety;
    private String region;

    public ComponentEntity toComponent(String wineLotCode) {
        return ComponentEntity.builder()
                .wineLotCode(wineLotCode)
                .year(year)
                .percentage(percentage)
                .variety(variety)
                .region(region)
                .build();
    }
}
