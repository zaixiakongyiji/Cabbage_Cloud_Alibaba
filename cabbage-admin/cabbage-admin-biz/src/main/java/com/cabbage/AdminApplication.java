/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cabbage;

import com.cabbage.annotation.EnableSpringDoc;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lengleng
 * @date 2018年06月21日 用户统一管理系统
 */
@EnableDubbo
@EnableSpringDoc
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class AdminApplication {

	public static void main( String[] args )throws UnknownHostException
	{
		ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);
		Environment env = application.getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"{} 启动成功!:\n\t" +
						"本地地址: \thttp://localhost:{}\n\t" +
						"外部地址: \thttp://{}:{}\n\t" +
						"文档地址: \thttp://{}:{}/doc.html#/home\n" +
						"----------------------------------------------------------",
				env.getProperty("dubbo.application.name"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

}
