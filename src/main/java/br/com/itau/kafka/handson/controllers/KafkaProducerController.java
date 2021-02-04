package br.com.itau.kafka.handson.controllers;

import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.kafka.handson.models.KafkaProducerRequestModel;
import br.com.itau.kafka.handson.services.KafkaProducerService;

@RestController
public class KafkaProducerController {

	@Autowired
	public KafkaProducerService kafkaProducerService;	
	
	@PostMapping("/produzir")
	public void produzir(@RequestBody KafkaProducerRequestModel requisicao) throws Exception {
		
		this.kafkaProducerService.produzir(requisicao.getTopico(), new Schema.Parser().parse(requisicao.getSchema().toString()), requisicao.getHeader(), requisicao.getPayload());
		
	}
	
}
