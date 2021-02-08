package br.com.itau.kafka.handson.controllers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.kafka.handson.models.KafkaProducerRequestModel;
import br.com.itau.kafka.handson.services.KafkaProducerService;

@RestController
public class KafkaProducerController {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaProducerController.class);
	
	@Autowired
	public KafkaProducerService kafkaProducerService;	
	
	@PostMapping("/produzir")
	public void produzir(@RequestBody KafkaProducerRequestModel requisicao) throws Exception {

		try {
			
			ListenableFuture<SendResult<String, Record>> future = this.kafkaProducerService.produzir(requisicao.getTopico(), new Schema.Parser().parse(requisicao.getSchema().toString()), requisicao.getHeader(), requisicao.getPayload());
			
			SendResult<String, Record> result = future.get(10, TimeUnit.SECONDS);
			
			log.info("Evento produzido com sucesso.");
			
		} catch (ExecutionException e) {
	    	
	    } catch (TimeoutException | InterruptedException e) {	    	
	    
	    }
		
	}
	
}
