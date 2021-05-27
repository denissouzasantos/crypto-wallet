import com.genesis.cryptowallet.bussiness.CrytoWalletPerformance;
import com.genesis.cryptowallet.dto.AssetDTO;
import com.genesis.cryptowallet.dto.ResponseDTO;
import com.genesis.cryptowallet.service.CryptoWalletService;
import com.genesis.cryptowallet.util.ReadCsvFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.mock;


public class CryptoWalletPerformanceTest {

    private static final String BESTASSET = "BTC";

    private static final String WORSTASSET = "ETH";

    private CrytoWalletPerformance crytoWalletPerformance;


    @Before
    public void setup(){
        crytoWalletPerformance = new CrytoWalletPerformance();
    }


    @Test
    public void fistCase() throws IOException, ExecutionException, InterruptedException {
        ResponseDTO thePerformance = crytoWalletPerformance.findThePerformance("teste.csv");
        Assert.assertEquals(thePerformance.getBestAsset(), BESTASSET);
        Assert.assertEquals(thePerformance.getWorstAsset(), WORSTASSET);
    }

    @Test
    public void secondCase() throws IOException, ExecutionException, InterruptedException {
        ResponseDTO thePerformance = crytoWalletPerformance.findThePerformance("teste.csv");
        Assert.assertNotEquals(thePerformance.getBestAsset(), WORSTASSET);
        Assert.assertNotEquals(thePerformance.getWorstAsset(), BESTASSET);
    }



}
