package com.genesis.cryptowallet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Setter
@ToString
public class ResponseDTO {

    private double total;
    private String bestAsset;
    private double bestPerformance;
    private String worstAsset;
    private double worstPerformance;

    private static DecimalFormat df = new DecimalFormat("0.00");

    public double getTotal() {
        return Double.valueOf(df.format(total));
    }

    public double getBestPerformance() {
        return Double.valueOf(df.format(bestPerformance));
    }

    public double getWorstPerformance() {
        return Double.valueOf(df.format(worstPerformance));
    }
}
