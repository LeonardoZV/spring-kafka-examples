package br.com.itau.kafka.handson.services;

import java.util.concurrent.CompletableFuture;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class KafkaProducerService {
	
	@Autowired
	private KafkaTemplate<String, Record> kafkaTemplate;
	
	private DecoderFactory decoderFactory = new DecoderFactory();
		
	public CompletableFuture<SendResult<String, Record>> produzir(String topico, Schema schema, JsonNode headerJson, JsonNode payload) throws Exception {

		Decoder decoder = decoderFactory.jsonDecoder(schema, payload.toString());		
		DatumReader<Record> reader = new GenericDatumReader<Record>(schema);		
		Record genericRecord = reader.read(null, decoder);
		ProducerRecord<String, Record> record = new ProducerRecord<String, Record>(topico, genericRecord);
		
		if (headerJson != null) {
			
			headerJson.fields().forEachRemaining(h -> {
				record.headers().add(h.getKey(), h.getValue().asText().getBytes());
			});
			
		}

		return this.kafkaTemplate.send(record).completable();
		
	}

}
