package br.com.leonardozv.examples.controllers;

import br.com.leonardozv.examples.models.KafkaProduceRequestModel;
import br.com.leonardozv.examples.services.GenericKafkaProducerService;
import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.SendResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class KafkaProducerController {

	private static final Logger log = LoggerFactory.getLogger(KafkaProducerController.class);

	public GenericKafkaProducerService genericKafkaProducerService;

	public KafkaProducerController(GenericKafkaProducerService genericKafkaProducerService) {
		this.genericKafkaProducerService = genericKafkaProducerService;
	}

	@PostMapping("/produce")
	public void produce(@Valid @RequestBody KafkaProduceRequestModel request) throws Exception {

		String key;

		if (request.getKey() == null || request.getKey().isEmpty()) {
			key = null;
		} else {
			key = request.getKey().toString();
		}

		Schema keySchema;

		if (request.getKey_schema() == null || request.getKey_schema().isEmpty()) {
			keySchema = null;
		} else {
			keySchema = new Schema.Parser().parse(request.getKey_schema().toString());
		}

		String value;

		if (request.getValue() == null || request.getValue().isEmpty()) {
			value = null;
		} else {
			value = request.getValue().toString();
		}

		Schema valueSchema;

		if (request.getValue_schema() == null || request.getValue_schema().isEmpty()) {
			valueSchema = null;
		} else {
			valueSchema = new Schema.Parser().parse(request.getValue_schema().toString());
		}

		CompletableFuture<SendResult<Record, Record>> future =
				this.genericKafkaProducerService.produce(request.getTopic(), request.getHeaders(), key,	keySchema, value, valueSchema);

		future.get(10, TimeUnit.SECONDS);
		
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

		log.info(ex.getMessage(), ex);

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();

			errors.put(fieldName, errorMessage);

		});

		return errors;

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
	}
	
}
