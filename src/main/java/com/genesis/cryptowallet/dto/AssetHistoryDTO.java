package com.genesis.cryptowallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AssetHistoryDTO {

    @JsonProperty("priceUsd")
    private double priceUsd;

    @JsonProperty("time")
    private String time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("date")
    @JsonIgnore
    private LocalDateTime date;

}
