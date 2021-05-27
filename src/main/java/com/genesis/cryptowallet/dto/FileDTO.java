package com.genesis.cryptowallet.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class FileDTO {

    private String symbol;
    private BigDecimal quantity;
    private BigDecimal price;
}
