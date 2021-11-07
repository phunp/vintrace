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
@Table(name = "wine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lot_code")
    private String lotCode;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "description")
    private String description;

    @Column(name = "tank_code")
    private String tankCode;

    @Column(name = "product_state")
    private String productState;

    @Column(name = "owner_name")
    private String ownerName;
}
