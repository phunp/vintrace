package com.vintrace.codingchallenge.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "component")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComponentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "wine_lot_code")
    private String wineLotCode;

    @Column(name = "year")
    private Integer year;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "variety")
    private String variety;

    @Column(name = "region")
    private String region;
}
