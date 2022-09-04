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
	public RecordHeaders headers;

	@Nullable
	public JsonNode key;

	@Nullable
	public JsonNode key_schema;

	@Nullable
	public JsonNode value;

	@Nullable
	public JsonNode value_schema;
	
}
