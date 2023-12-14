package com.integrador.assets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.Generated;

@SpringBootApplication
@ComponentScan(basePackages = "com.integrador.assets")
@EnableMongoRepositories
@Generated
public class AssetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetsApplication.class, args);
	}

//	@Autowired
//	private MongoTemplate mongoTemplate;
//
//	@Autowired
//	private AssetUpdateService assetCreateService;
//
//	@Autowired
//	private AssetRepository assetRepository;
//
//	@Autowired
//	private PubSubTemplate pubSubTemplate;
//
//	@Autowired
//	private TopicAdminClient topicAdminClient;
//
//	@Autowired
//	private PubSubAdmin pubSubAdmin;
//
//	@Autowired
//	private ManuSisProducer manuSisProducer;

//	@Override
//	public void run(String... args) throws Exception {
		// System.out.println(mongoTemplate.findAll(DBObject.class, "asset").size());;
		// assetCreateService.createAssets();
		// assetRepository.deleteAll();
//		Query query = new Query();
//		query.fields().include("asset_type.description");
//		List<String> result = mongoTemplate.find(query, String.class, "asset");
//		System.out.println(result.stream().map(obj -> new JSONObject(obj)).collect(Collectors.toSet()));

//		pubSubTemplate.subscribe("manusis-subscription", basicAcknowledgeablePubsubMessage -> {
//			System.out.println("Mensagem recebida " + basicAcknowledgeablePubsubMessage.getPubsubMessage());
//			basicAcknowledgeablePubsubMessage.ack();
//			System.out.println("Recebeu!");
//		});
//
//		pubSubTemplate.subscribeAndConvert("manusis-subscription", convertedBasicAcknowledgeablePubsubMessage -> {
//			System.out.println("Mensagem recebida " + convertedBasicAcknowledgeablePubsubMessage.getPayload());
//			convertedBasicAcknowledgeablePubsubMessage.ack();
//			System.out.println("Recebeu!");
//		}, ManusisMessage.class);
//
//		pubSubTemplate.publish("manusis-topic", ManusisMessage.builder().id("dasdahskh8768daskljdlas")
//				.action(ManuSisMessageAction.INSERT).currentTimeStamp(LocalDateTime.now()).build());
//		System.out.println("Enviou!");
//		assetRepository.deleteAll();
//	}
}