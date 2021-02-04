package br.com.itau.kafka.handson.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import br.com.itau.kafka.handson.services.KafkaConsumerService;

@Component
@Endpoint(id="restart-kafka-container")
public class RestartKafkaContainer {

	@Autowired
	public KafkaConsumerService kafkaConsumerService;
	
	@WriteOperation
    public void restart(@Selector String id) {
        this.kafkaConsumerService.restart(id);
    }
	
}