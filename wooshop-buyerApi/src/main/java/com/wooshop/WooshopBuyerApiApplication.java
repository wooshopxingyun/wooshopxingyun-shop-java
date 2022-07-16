package com.wooshop;

import com.binarywang.spring.starter.wxjava.miniapp.config.WxMaAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {WxMaAutoConfiguration.class})
@EnableCaching
@EnableTransactionManagement
@EnableAsync //开启异步操作
@MapperScan(basePackages={"com.wooshop.*.mapper","com.wooshop.modules.*.mapper",
        "com.wooshop.modules.*.service.mapper","com.wooshop.config"})
public class WooshopBuyerApiApplication {


    @Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WooshopBuyerApiApplication.class, args);
        System.out.println(" _          __  _____   _____   _____   _   _   _____   _____\n" +
                "| |        / / /  _  \\ /  _  \\ /  ___/ | | | | /  _  \\ |  _  \\\n" +
                "| |  __   / /  | | | | | | | | | |___  | |_| | | | | | | |_| |\n" +
                "| | /  | / /   | | | | | | | | \\___  \\ |  _  | | | | | |  ___/\n" +
                "| |/   |/ /    | |_| | | |_| |  ___| | | | | | | |_| | | |\n" +
                "|___/|___/     \\_____/ \\_____/ /_____/ |_| |_| \\_____/ |_|\n"+
                "");
        System.out.println("wooshop商城客户端运行成功，官网地址:https://wooshopxingyun.com");
    }

}
