package br.com.leonardozv.kafka.handson;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaHandsonApplication {

	public static ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
		context = SpringApplication.run(KafkaHandsonApplication.class, args);
	}
	
	public static void restart() {
		
		ApplicationArguments args = context.getBean(ApplicationArguments.class);
		
		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(KafkaHandsonApplication.class, args.getSourceArgs());			
		});
		
		thread.setDaemon(false);
		thread.start();
		
	}

}
