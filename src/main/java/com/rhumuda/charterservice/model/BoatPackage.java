package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@Entity
@DiscriminatorValue("BOAT")
@EqualsAndHashCode(callSuper = true)
public class BoatPackage extends Package {
    private Double adultPrice;
    private Double kidPrice;
    private Double privateBoatPrice;
} 