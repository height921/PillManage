package com.example.pillmanage.config.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.pillmanage.config.chainmaker.Contract;
import com.example.pillmanage.config.chainmaker.InitClient;
import com.example.pillmanage.config.entity.*;
import org.chainmaker.pb.common.ResultOuterClass;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {


    @PostMapping("/producer/get_produce_by_id")
    @ResponseBody
    public Result getProduceById(String id) throws InterruptedException {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("id", id.getBytes());
        System.out.println("id is " + id);
        response = Contract.invokeContract(InitClient.chainClient, "userTrace", "findUserById", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());

        System.out.println(jsonObject);

        System.out.println("线程结束");

        return new Result("getAllMedicine successfully", 200,jsonObject );
    }

    @GetMapping("/getAllMedicineById")
    @ResponseBody
    public Result getAllMedicine(String id) throws InterruptedException {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("id", id.getBytes());
        System.out.println("id is " + id);
        response = Contract.invokeContract(InitClient.chainClient, "userTrace", "findUserById", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());

        System.out.println(jsonObject);

        List<String> medicineList = JSONArray.parseArray(jsonObject.getJSONArray("medicine_vec").toJSONString(), String.class);

        System.out.println(medicineList);

        CountDownLatch cdl = new CountDownLatch(medicineList.size());
        System.out.println("线程开始");

        JSONArray resultArray = new JSONArray();
        //新建线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String traceno : medicineList) {
            // 开启线程
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        //get medicine info according to the medicine_vec in user info
                        System.out.println(traceno);
                        Map<String, byte[]> params_temp = new HashMap<>();
                        params_temp.put("traceno", traceno.getBytes());
                        ResultOuterClass.TxResponse response = Contract.invokeContract(InitClient.chainClient, "trace", "search", params_temp);
                        resultArray.add(JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 闭锁-1
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        //关闭线程池
        executor.shutdown();
        System.out.println("线程结束");
        System.out.println(resultArray);
        return new Result("getAllMedicine successfully", 200, resultArray);
    }

    @GetMapping("/producer/produce")
    @ResponseBody
    public Result produce(ProduceInfo produceInfo) throws IllegalAccessException {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("traceno", UUID.randomUUID().toString().replace("-", "").getBytes());
        Field[] fields = produceInfo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(produceInfo) != null) {
                params.put(field.getName(), field.get(produceInfo).toString().getBytes());
            } else {
                params.put(field.getName(), "".getBytes());
            }
        }
        response = Contract.invokeContract(InitClient.chainClient, "trace", "produce", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());

        Map<String, byte[]> params1 = new HashMap<>();
        ResultOuterClass.TxResponse response1 = null;
        params1.put("id", produceInfo.getProducer_id().getBytes());
        params1.put("traceno", jsonObject.getObject("traceno", String.class).getBytes());
        response1 = Contract.invokeContract(InitClient.chainClient, "userTrace", "putMedicine", params1);
        JSONObject jsonObject1 = JSONObject.parseObject(response1.getContractResult().getResult().toStringUtf8());
        System.out.println(jsonObject1.toJSONString());

        return new Result("produce successfully", 200, jsonObject);
    }


    @GetMapping("/seller/sell")
    @ResponseBody
    public Result sell(SellInfo sellInfo) throws IllegalAccessException {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        Field[] fields = sellInfo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(sellInfo) != null) {
                params.put(field.getName(), field.get(sellInfo).toString().getBytes());
            } else {
                params.put(field.getName(), "".getBytes());
            }
        }
        response = Contract.invokeContract(InitClient.chainClient, "trace", "sell", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());

        Map<String, byte[]> params1 = new HashMap<>();
        ResultOuterClass.TxResponse response1 = null;
        params1.put("id", sellInfo.getSeller_id().getBytes());
        params1.put("traceno", sellInfo.getTraceno().getBytes());
        response1 = Contract.invokeContract(InitClient.chainClient, "userTrace", "putMedicine", params1);
        JSONObject jsonObject1 = JSONObject.parseObject(response1.getContractResult().getResult().toStringUtf8());

        return new Result("sell successfully", 200, jsonObject);
    }

    @GetMapping("/admin/freeze")
    @ResponseBody
    public Result freeze(String traceno) {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("traceno", traceno.getBytes());
        response = Contract.invokeContract(InitClient.chainClient, "trace", "freeze", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());
        return new Result("freeze or unfreeze successfully", 200, jsonObject);
    }

    @GetMapping("/search")
    @ResponseBody
    public Result search(String traceno) {

        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("traceno", traceno.getBytes());
        response = Contract.queryContract(InitClient.chainClient, "trace", "search", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());
        return new Result("search successfully", 200, jsonObject);
    }

    @GetMapping("/login")
    @ResponseBody
    public Result login(String username, String password) {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        params.put("id", username.getBytes());
        response = Contract.queryContract(InitClient.chainClient, "userTrace", "findUserById", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());
        if (jsonObject.get("password").toString().equals(password)) {
            return new Result("login successfully", 200, jsonObject);
        } else {
            return new Result("login error", 220, null);
        }
    }

    @GetMapping("/register")
    @ResponseBody
    public Result register(int user_type, String password, String name) throws InterruptedException {
        Map<String, byte[]> params = new HashMap<>();
        ResultOuterClass.TxResponse response = null;
        String userType;
        switch (user_type) {
            case 0:
                userType = "producer";
                break;
            case 1:
                userType = "seller";
                break;
            case 2:
                userType = "admin";
                break;
            default:
                return new Result("user_type error", 230, null);
        }
//        String id= UUID.randomUUID().toString().replace("-","");
        ResultOuterClass.TxResponse response1 = Contract.invokeContract(InitClient.chainClient, "counter", "increase", null);
        params.put("id", response1.getContractResult().getResult().toStringUtf8().getBytes());
        params.put("user_type", userType.getBytes());
        params.put("name", name.getBytes());
        params.put("password", password.getBytes());
        TimeUnit.MILLISECONDS.sleep(500);
        response = Contract.invokeContract(InitClient.chainClient, "userTrace", "registerUser", params);
        JSONObject jsonObject = JSONObject.parseObject(response.getContractResult().getResult().toStringUtf8());
        return new Result("registerUser successfully", 200, jsonObject);
    }
}
