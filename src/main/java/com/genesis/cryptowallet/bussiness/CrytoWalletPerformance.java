package com.genesis.cryptowallet.bussiness;

import com.genesis.cryptowallet.dto.*;
import com.genesis.cryptowallet.service.CryptoWalletService;
import com.genesis.cryptowallet.util.ReadCsvFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrytoWalletPerformance {

    /**
     *
     * @param comparePosition
     * @return
     */
    public ResponseDTO compareCurrentPosition(List<ComparePositionDTO> comparePosition) {
        ResponseDTO response = new ResponseDTO();
        String bestAsset = "";
        double bestPerformance = 0.f;
        String worstAsset = "";
        double worstPerformance = 0.f;
        double total = 0.f;
        for (ComparePositionDTO compared : comparePosition) {
            total += compared.getCurrentPosition();
            if (bestPerformance == 0 || compared.getPriceCompared() > bestPerformance) {
                bestPerformance = compared.getPriceCompared();
                bestAsset = compared.getSymbol();
            }
            if (worstPerformance == 0 || compared.getPriceCompared() < worstPerformance) {
                worstPerformance = compared.getPriceCompared();
                worstAsset = compared.getSymbol();
            }
        }
        response.setTotal(total);
        response.setBestAsset(bestAsset);
        response.setBestPerformance(bestPerformance);
        response.setWorstAsset(worstAsset);
        response.setWorstPerformance(worstPerformance);

        return response;
    }

    /**
     *
     * @param fileName the file name
     * @return response with the information crypto wallet performance
     * @throws IOException find some problem with read process
     */
    public ResponseDTO findThePerformance(String fileName) throws IOException, InterruptedException, ExecutionException {

        ReadCsvFile file = new ReadCsvFile();

        List<FileDTO> rows = file.readFile(fileName);
        List<ComparePositionDTO> comparePosition = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(rows.size());
        ExecutorCompletionService<AssetHistoryDTO> completionService = new ExecutorCompletionService<>(threadPool);

        for (FileDTO line : rows) {
            completionService.submit(new CryptoWalletService(line.getSymbol()));
            AssetHistoryDTO assetHistory = completionService.take().get();
            ComparePositionDTO assetCompared = new ComparePositionDTO();
            assetCompared.setSymbol(line.getSymbol());
            assetCompared.setCurrentPosition(line.getQuantity() * line.getPrice());
            assetCompared.setPriceCompared(((assetHistory.getPriceUsd() / line.getPrice()) * 100) / 100);

            comparePosition.add(assetCompared);
        }
        threadPool.shutdown();
        return compareCurrentPosition(comparePosition);

    }
}
