package com.example.pillmanage;

import com.alibaba.fastjson.JSONObject;
import org.chainmaker.pb.common.ResultOuterClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ReflectTest
 * @Package com.example.pillmanage
 * @Author Hang Zhao
 * @Description TODO
 * @Date 2022/12/19 22:06
 */
public class ReflectTest {

//    @Test
//    public void testReflect() throws IllegalAccessException {
//        Producer producer = new Producer();
//        producer.setProducerId("123");
//        producer.setPassword("123456");
//        producer.setAccount("111");
//        Map<String,byte[]> params=new HashMap<>();
//        ResultOuterClass.TxResponse response=null;
//        Field[] fields = producer.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if(field.get(producer)!=null){
//                params.put(field.getName(), field.get(producer).toString().getBytes());
//            }else{
//                params.put(field.getName(),"".getBytes());
//            }
//
//        }
//        System.out.println(params);
//    }

    @Test
    public void testJsonTransfer(){
        String data="{\n" +
                "        \"traceno\":      \"111\",\n" +
                "        \"name\": \"浜戝崡鐧借嵂\",\n" +
                "        \"is_freezed\":   false,\n" +
                "        \"produce_info\": {\n" +
                "                \"p_number\":     \"123546\",\n" +
                "                \"p_ingredient\": \"123546\",\n" +
                "                \"p_package_size\":       \"123546\",\n" +
                "                \"p_time\":       \"123546\",\n" +
                "                \"p_validity_time\":      \"123546\",\n" +
                "                \"p_batch_number\":       \"123546\",\n" +
                "                \"p_approval_number\":    \"123546\",\n" +
                "                \"p_name\":       \"123546\",\n" +
                "                \"p_address\":    \"123546\",\n" +
                "                \"p_tel\":        \"123546\",\n" +
                "                \"p_remark\":     \"123546\",\n" +
                "                \"p_remark\":     \"123546\",\n" +
                "                \"producer_id\":  \"123546\",\n" +
                "                \"p_count\":      648\n" +
                "        },\n" +
                "        \"sell_info\":    {\n" +
                "                \"s_number\":     \"\",\n" +
                "                \"s_shop_name\":  \"\",\n" +
                "                \"s_name\":       \"\",\n" +
                "                \"s_tel\":        \"\",\n" +
                "                \"s_price\":      \"\",\n" +
                "                \"s_remark\":     \"\",\n" +
                "                \"seller_id\":    \"\"\n" +
                "        }\n" +
                "}";
        System.out.println(data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        System.out.println(jsonObject.getString("traceno"));
    }

}
