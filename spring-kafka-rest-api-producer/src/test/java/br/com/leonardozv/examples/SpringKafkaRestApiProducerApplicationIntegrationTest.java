package br.com.leonardozv.examples;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SpringKafkaRestApiProducerApplicationIntegrationTest {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> SpringKafkaRestApiProducerApplication.main(new String[]{}));
	}

}
