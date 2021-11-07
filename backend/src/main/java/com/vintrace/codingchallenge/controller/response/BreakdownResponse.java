package com.vintrace.codingchallenge.controller.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vintrace.codingchallenge.repository.projection.IBreakdown;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class BreakdownResponse {
    private String breakDownType;
    private List<BreakDown> breakdowns;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    public static class BreakDown {
        private double percentage;
        private String key;

        public BreakDown(IBreakdown ibd) {
            this.percentage = ibd.getPercentage();
            this.key = ibd.getKey();
        }
    }
}
