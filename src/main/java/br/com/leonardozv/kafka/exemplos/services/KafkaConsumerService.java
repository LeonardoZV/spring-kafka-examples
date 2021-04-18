package br.com.leonardozv.kafka.exemplos.services;

import java.util.List;

import br.com.leonardozv.kafka.exemplos.mappers.CloudEventsMessageHeaderMapper;
import br.com.leonardozv.kafka.exemplos.models.CloudEventsMessageHeader;
import org.apache.avro.generic.GenericData.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.BatchListenerFailedException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

	@Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public String[] obterTopicos() {
		return new String[] { "processamento-ted" };
	}
	
	@KafkaListener(id = "kafka-handson", containerFactory = "kafkaListenerContainerFactory", topics = "#{kafkaConsumerService.obterTopicos()}", idIsGroup = false)
	private void consumir(List<Message<Record>> listaEventos) throws Exception {		
		
		int i = 0;
		
		for(Message<Record> evento : listaEventos) {
			
			try {
				
				CloudEventsMessageHeader header = CloudEventsMessageHeaderMapper.from(evento.getHeaders());
				
				log.info("Headers: " + objectMapper.writeValueAsString(header) + " | Payload: " + evento.getPayload().toString());
								
			} catch(Exception ex) {
				
				throw new BatchListenerFailedException("Falha no processamento do evento", ex, i);
				
			}
			
			i++;
			
		}

		log.info("Batch de eventos consumidos com sucesso.");
		
	}
	
    public void restart(String id) {
    	
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);

        listenerContainer.stop();
        
        listenerContainer.start();

    }
    
}
