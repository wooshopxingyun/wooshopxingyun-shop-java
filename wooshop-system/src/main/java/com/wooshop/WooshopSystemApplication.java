package com.wooshop;

import com.binarywang.spring.starter.wxjava.miniapp.config.WxMaAutoConfiguration;
import com.wooshop.annotation.rest.AnonymousGetMapping;
import com.wooshop.utils.SpringContextHolder;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@EnableAsync //开启异步操作
@RestController
@Api(hidden = true)
@SpringBootApplication(exclude = {WxMaAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackages={"com.wooshop.*.mapper","com.wooshop.modules.*.mapper",
        "com.wooshop.modules.*.service.mapper","com.wooshop.config"})
public class WooshopSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WooshopSystemApplication.class, args);

        System.out.println(" _          __  _____   _____   _____   _   _   _____   _____\n" +
                "| |        / / /  _  \\ /  _  \\ /  ___/ | | | | /  _  \\ |  _  \\\n" +
                "| |  __   / /  | | | | | | | | | |___  | |_| | | | | | | |_| |\n" +
                "| | /  | / /   | | | | | | | | \\___  \\ |  _  | | | | | |  ___/\n" +
                "| |/   |/ /    | |_| | | |_| |  ___| | | | | | | |_| | | |\n" +
                "|___/|___/     \\_____/ \\_____/ /_____/ |_| |_| \\_____/ |_|\n"+
                "");
        System.out.println("wooshop商城管理后台运行成功，官网地址:https://wooshopxingyun.com");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @AnonymousGetMapping("/")
    public String index() {
        return "Backend service started successfully";
    }
}
