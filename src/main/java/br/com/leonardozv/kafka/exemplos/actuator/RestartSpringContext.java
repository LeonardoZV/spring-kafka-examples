package br.com.leonardozv.kafka.exemplos.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import br.com.leonardozv.kafka.exemplos.KafkaSpringJavaExemplosApplication;

@Component
@Endpoint(id="restart-spring-context")
public class RestartSpringContext {

	@WriteOperation
    public void restart() {
        KafkaSpringJavaExemplosApplication.restart();
    }
	
}
