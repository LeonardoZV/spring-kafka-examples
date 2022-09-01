package br.com.leonardozv.examples.services;

import br.com.leonardozv.examples.models.CompleteMessageHeader;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.BatchListenerFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerService {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	public KafkaConsumerService() {
		this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@KafkaListener(topics = "accounting-journal-entry-created", groupId = "spring-kafka-consumer", batch = "true")
	private void consume(List<Message<Record>> messages) {

		int i = 0;
		
		for(Message<Record> message : messages) {
			
			try {

				CompleteMessageHeader header = this.objectMapper.convertValue(message.getHeaders(), CompleteMessageHeader.class);

				if (log.isInfoEnabled()) {
					log.info("Headers: {} | Payload: {}", this.objectMapper.writeValueAsString(header), message.getPayload());
				}
								
			} catch(Exception ex) {
				throw new BatchListenerFailedException("Error in message consumption", ex, i);
			}
			
			i++;
			
		}
		
	}
    
}
