package com.genesis.cryptowallet.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Getter
@Setter
@ToString
public class ResponseDTO {

    private BigDecimal total;
    private String bestAsset;
    private BigDecimal bestPerformance;
    private String worstAsset;
    private BigDecimal worstPerformance;

    private static DecimalFormat df = new DecimalFormat("0.00");

    public BigDecimal getTotal() { return new BigDecimal(df.format(this.total)); }

    public BigDecimal getBestPerformance() { return new BigDecimal(df.format(this.bestPerformance)); }

    public BigDecimal getWorstPerformance() { return new BigDecimal(df.format(this.worstPerformance)); }
}
