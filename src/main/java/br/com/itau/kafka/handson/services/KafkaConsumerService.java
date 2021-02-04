package br.com.itau.kafka.handson.services;

import java.util.List;

import org.apache.avro.generic.GenericData.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.itau.kafka.handson.mappers.CloudEventsMessageHeaderMapper;
import br.com.itau.kafka.handson.models.CloudEventsMessageHeader;

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
		
		for(Message<Record> evento : listaEventos) {	
			
			CloudEventsMessageHeader header = CloudEventsMessageHeaderMapper.from(evento.getHeaders());
			
			log.info("Headers: " + objectMapper.writeValueAsString(header) + " | Payload: " + evento.getPayload().toString());
			
		}

		log.info("Batch Eventos Consumidos");
		
	}
	
    public void restart(String id) {
    	
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);

        listenerContainer.stop();
        
        listenerContainer.start();

    }
}
