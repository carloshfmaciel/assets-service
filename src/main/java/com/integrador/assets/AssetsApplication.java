package com.integrador.assets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.spring.pubsub.PubSubAdmin;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.TopicName;
import com.integrador.assets.mongo.repository.AssetRepository;
import com.integrador.assets.service.AssetUpdateService;

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
	private AssetUpdateService assetCreateService;
	
	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
	private PubSubTemplate pubSubTemplate;
	
	@Autowired
	private TopicAdminClient topicAdminClient;
		
	@Autowired
	private PubSubAdmin pubSubAdmin;
	
	@Override
	public void run(String... args) throws Exception {
		//System.out.println(mongoTemplate.findAll(DBObject.class, "asset").size());;
		//assetCreateService.createAssets();
		//assetRepository.deleteAll();
//		Query query = new Query();
//		query.fields().include("asset_type.description");
//		List<String> result = mongoTemplate.find(query, String.class, "asset");
//		System.out.println(result.stream().map(obj -> new JSONObject(obj)).collect(Collectors.toSet()));
		
//		pubSubTemplate.subscribe("manusis-subscription", basicAcknowledgeablePubsubMessage -> {
//			System.out.println("Mensagem recebida " + basicAcknowledgeablePubsubMessage.getPubsubMessage().getData().toString());
//			basicAcknowledgeablePubsubMessage.ack();
//			System.out.println("Recebeu!");
//		});
//		
//		pubSubTemplate.publish("manusis-topic", "Testandoooo");
//		System.out.println("Enviou!");
	}
}