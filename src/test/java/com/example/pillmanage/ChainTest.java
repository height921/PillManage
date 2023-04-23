package com.example.pillmanage;

import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;


public class ChainTest {
    @Test
    public void deployNFT721() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://152.136.182.202:12301/"));

        BigInteger chainId = web3j.ethChainId().send().getChainId();
        System.out.println(chainId);
    }
}
