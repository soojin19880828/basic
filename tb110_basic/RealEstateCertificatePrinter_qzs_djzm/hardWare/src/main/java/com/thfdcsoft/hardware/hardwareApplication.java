package com.thfdcsoft.hardware;

import com.thfdcsoft.hardware.factory.PropFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

@SpringBootApplication
@EnableAutoConfiguration
public class hardwareApplication implements EmbeddedServletContainerCustomizer {

	public static void main(String[] args) {
		// 读取配置文件
//		PropFactory.initProperties();
		SpringApplication.run(hardwareApplication.class, args);
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8808);
	}

}
