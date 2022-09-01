package br.com.leonardozv.examples.controllers;

import br.com.leonardozv.examples.models.KafkaProduceRequestModel;
import br.com.leonardozv.examples.services.GenericKafkaProducerService;
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

		CompletableFuture<SendResult<String, Record>> future = this.genericKafkaProducerService.produce(request.getTopic(), new Schema.Parser().parse(request.getSchema().toString()), request.getKey(), request.getHeaders(), request.getPayload().toString());

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
