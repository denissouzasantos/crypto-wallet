package com.genesis.cryptowallet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ComparePositionDTO {

    private String symbol;
    private BigDecimal currentPosition;
    private BigDecimal priceCompared;
}
