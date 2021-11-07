package com.vintrace.codingchallenge.service.model;

import com.vintrace.codingchallenge.repository.entity.ComponentEntity;
import com.vintrace.codingchallenge.repository.entity.WineEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Wine {
    private String lotCode;
    private Double volume;
    private String description;
    private String tankCode;
    private String productState;
    private String ownerName;
    private List<Component> components;

    public WineEntity getWineEntity() {
        return WineEntity.builder()
                .lotCode(lotCode)
                .volume(volume)
                .description(description)
                .tankCode(tankCode)
                .productState(productState)
                .ownerName(ownerName)
                .build();
    }

    public List<ComponentEntity> getComponentEntities() {
        if (components == null) return new ArrayList<>();
        return components.stream().map(e -> e.toComponent(lotCode)).collect(Collectors.toList());
    }
}
