package com.integrador.assets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.service.AssetCreateService;

@SpringBootApplication
@ComponentScan(basePackages = "com.integrador.assets")
@EnableMongoRepositories
public class AssetsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AssetsApplication.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private AssetCreateService assetCreateService;
	
	@Autowired
	private AssetRepository assetRepository;
	
	@Override
	public void run(String... args) throws Exception {
		//System.out.println(mongoTemplate.findAll(DBObject.class, "asset").size());;
		assetCreateService.createAssets();
		//assetRepository.deleteAll();
	}
}