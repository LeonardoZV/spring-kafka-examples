package br.com.leonardozv.examples;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SpringKafkaConsumerApplicationIntegrationTest {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> SpringKafkaConsumerApplication.main(new String[]{}));
	}

}
