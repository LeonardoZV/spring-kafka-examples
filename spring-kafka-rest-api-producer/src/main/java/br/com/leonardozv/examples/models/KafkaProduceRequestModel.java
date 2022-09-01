package br.com.leonardozv.examples.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KafkaProduceRequestModel {

	@NotNull
	public String topic;

	@Nullable
	public String key;

	@Nullable
	public RecordHeaders headers;

	@NotNull
	public JsonNode payload;

	@NotNull
	public JsonNode schema;
	
}
