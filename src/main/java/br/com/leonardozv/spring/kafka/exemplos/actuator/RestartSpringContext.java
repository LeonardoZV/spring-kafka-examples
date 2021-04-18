package br.com.leonardozv.spring.kafka.exemplos.actuator;

import br.com.leonardozv.spring.kafka.exemplos.SpringKafkaExemplosApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="restart-spring-context")
public class RestartSpringContext {

	@WriteOperation
    public void restart() {
        SpringKafkaExemplosApplication.restart();
    }
	
}
