package com.example.pillmanage.config.chainmaker;

import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;

import java.util.Map;


public class Contract {

    public static ResultOuterClass.TxResponse invokeContract(ChainClient chainClient,String contractName, String methodName, Map<String,byte[]> params) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            responseInfo = chainClient.invokeContract(contractName, methodName,
                    null, params,10000, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("执行合约结果：");
//        System.out.println(responseInfo);
        return responseInfo;
    }

    public static ResultOuterClass.TxResponse queryContract(ChainClient chainClient,String contractName,String methodName, Map<String,byte[]> params) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            responseInfo = chainClient.queryContract(contractName, methodName,
                    null,  params,10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("查询合约结果：");
//        System.out.println(responseInfo);
        return responseInfo;
    }
}
