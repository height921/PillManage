package com.example.pillmanage;

import com.example.pillmanage.config.chainmaker.ChainConfig;
import com.example.pillmanage.config.chainmaker.InitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


import static com.example.pillmanage.config.chainmaker.InitClient.inItChainClient;

@SpringBootApplication
public class PillManageApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(PillManageApplication.class, args);
        //初始化链客户端
        inItChainClient();
        //查询链配置
        ChainConfig.getChainConfig(InitClient.chainClient);
    }
    @Bean
    public TomcatServletWebServerFactory servletContainer(){
        return new TomcatServletWebServerFactory(8081) ;
    }
}
