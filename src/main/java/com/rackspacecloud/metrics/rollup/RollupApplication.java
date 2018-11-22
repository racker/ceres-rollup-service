package com.rackspacecloud.metrics.rollup;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RollupApplication {

	public static void main(String[] args) {
//		SpringApplication.run(RollupApplication.class, args);


		ConfigurableApplicationContext context =
				new SpringApplicationBuilder(RollupApplication.class)
						.web(WebApplicationType.NONE)
						.run(args);

		KafkaStreams kafkaStreams = context.getBean(KafkaStreams.class);

		kafkaStreams.cleanUp();

		kafkaStreams.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> {
			System.out.println("MRIT ERROR: [" + throwable.getMessage() + "]");
			// here you should examine the throwable/exception and perform an appropriate action!
		});

		kafkaStreams.start();
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
	}
}
