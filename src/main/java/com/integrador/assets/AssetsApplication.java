package com.integrador.assets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.integrador.assets.service.SyncLogic;

@SpringBootApplication
@ComponentScan(basePackages = "com.integrador.assets")

public class AssetsApplication {

	public static void main(String[] args) {

		SpringApplication.run(AssetsApplication.class, args);
			SyncLogic.sync();
	}
}