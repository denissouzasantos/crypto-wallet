package com.genesis.cryptowallet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComparePositionDTO {

    private String symbol;
    private double currentPosition;
    private double priceCompared;
}
