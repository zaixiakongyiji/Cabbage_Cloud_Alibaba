package com.cabbage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class GateAwayApp {
    public static void main( String[] args )throws UnknownHostException
    {
        ConfigurableApplicationContext application = SpringApplication.run(GateAwayApp.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "{} 启动成功!:\n\t" +
                        "本地地址: \thttp://localhost:{}\n\t" +
                        "外部地址: \thttp://{}:{}\n\t" +
//                        "文档地址: \thttp://{}:{}/doc.html#/home\n" +
                        "----------------------------------------------------------",
                env.getProperty("application.name"),
                env.getProperty("server.port"),
//                InetAddress.getLocalHost().getHostAddress(),
//                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
