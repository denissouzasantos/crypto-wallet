package com.genesis.cryptowallet;

import com.genesis.cryptowallet.bussiness.CrytoWalletPerformance;
import com.genesis.cryptowallet.dto.ResponseDTO;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The main class that contains the initial method
 */
public class Application {


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        CrytoWalletPerformance bussiness = new CrytoWalletPerformance();
        ResponseDTO thePerformance = bussiness.findThePerformance("teste.csv");
        System.out.println(thePerformance);

    }
}
