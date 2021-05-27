package com.genesis.cryptowallet.bussiness;

import com.genesis.cryptowallet.dto.*;
import com.genesis.cryptowallet.service.CryptoWalletService;
import com.genesis.cryptowallet.util.ReadCsvFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrytoWalletPerformance {

    private static final int ZERO = 0;
    private static final BigDecimal DEFAULT_BD = new BigDecimal(0);
    private static final int ONE_HUNDRED = 100;

    /**
     *
     * @param comparePosition
     * @return
     */
    public ResponseDTO compareCurrentPosition(List<ComparePositionDTO> comparePosition) {
        ResponseDTO response = new ResponseDTO();
        String bestAsset = "";
        BigDecimal bestPerformance = new BigDecimal(ZERO);
        String worstAsset = "";
        BigDecimal worstPerformance = new BigDecimal(ZERO);
        BigDecimal total = new BigDecimal(ZERO);

        for (ComparePositionDTO compared : comparePosition) {
            total = total.add(compared.getCurrentPosition());
            if (bestPerformance.compareTo(DEFAULT_BD) == ZERO || compared.getPriceCompared().compareTo(bestPerformance) > ZERO ) {
                bestPerformance = compared.getPriceCompared();
                bestAsset = compared.getSymbol();
            }
            if (worstPerformance.compareTo(DEFAULT_BD) == ZERO || compared.getPriceCompared().compareTo(worstPerformance) < ZERO) {
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
            assetCompared.setCurrentPosition(line.getQuantity().multiply(line.getPrice()) );
            assetCompared.setPriceCompared(((assetHistory.getPriceUsd().divide(line.getPrice(), MathContext.DECIMAL128))
                    .multiply(new BigDecimal(ONE_HUNDRED))).divide(new BigDecimal(ONE_HUNDRED), MathContext.DECIMAL128));

            comparePosition.add(assetCompared);
        }
        threadPool.shutdown();
        return compareCurrentPosition(comparePosition);

    }
}
