package com.genesis.cryptowallet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesis.cryptowallet.dto.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CryptoWalletService implements Callable<AssetHistoryDTO> {


    public static final String API_COINCAP_V2 = "http://api.coincap.io/v2/";
    private static final long SLEEP_TIME = 10;
    private String symbol;

    public CryptoWalletService(String symbol){
        this.symbol = symbol;
    }


    /**
     * @param symbol most common symbol used to identify this asset on an exchange
     * @return Information about asset
     */
    public AssetHistoryDTO findAsset(String symbol) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        List<AssetDTO> responseDto = new ArrayList<AssetDTO>();

        try {
            response = client.execute(new HttpGet(API_COINCAP_V2 + String.format("assets?search=%s", symbol)));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                AssetDTO objResponse = new AssetDTO();
                objResponse = mapper.readerFor(AssetDTO.class).readValue(jsonArray.getJSONObject(i).toString());
                responseDto.add(objResponse);
            }

            AssetDTO responseAsset = responseDto.stream()
                    .filter(asset -> asset.getSymbol().equals(symbol))
                    .findAny()
                    .orElse(null);

            return findAssetHistory(responseAsset.getId());

        } catch (IOException e) {
            throw new InterruptedIOException("Find some problem: " + e.getMessage());
        }
    }

    /**
     * @param id unique identifier for asset
     * @return History price
     */
    public AssetHistoryDTO findAssetHistory(String id) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        AssetHistoryDTO assetHistoryDTO = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            response = client.execute(new HttpGet(API_COINCAP_V2 + String.format("assets/%s/history?interval=d1&start=1617753600000&end=161" +
                    "7753601000", id)));
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                assetHistoryDTO = mapper.readerFor(AssetHistoryDTO.class).readValue(jsonArray.getJSONObject(i).toString());
            }


        } catch (IOException e) {
            throw new InterruptedIOException("Find some problem call api coincap: " + e.getMessage());
        }

        return assetHistoryDTO;
    }


    @Override
    public AssetHistoryDTO call() throws Exception {
        System.out.println("Submitted request ASSET_"+ this.symbol + " at " + LocalDateTime.now());
        Thread.sleep(SLEEP_TIME);
        return findAsset(this.symbol);
    }
}
