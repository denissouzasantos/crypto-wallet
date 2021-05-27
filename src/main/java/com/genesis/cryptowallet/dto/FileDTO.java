package com.genesis.cryptowallet.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDTO {

    private String symbol;
    private double quantity;
    private double price;
}
