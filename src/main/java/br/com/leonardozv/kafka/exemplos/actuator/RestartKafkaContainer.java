package br.com.leonardozv.kafka.exemplos.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import br.com.leonardozv.kafka.exemplos.services.KafkaConsumerService;

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
