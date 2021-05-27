package com.genesis.cryptowallet.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class AssetDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("rank")
    private String rank;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("supply")
    private String supply;

    @JsonProperty("maxSupply")
    private String maxSupply;

    @JsonProperty("marketCapUsd")
    private String marketCapUsd;

    @JsonProperty("volumeUsd24Hr")
    private String volumeUsd24Hr;

    @JsonProperty("priceUsd")
    private String priceUsd;

    @JsonProperty("changePercent24Hr")
    private String changePercent24Hr;

    @JsonProperty("vwap24Hr")
    private String vwap24Hr;

    @JsonProperty("explorer")
    private String explorer;

}
