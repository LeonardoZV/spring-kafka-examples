package br.com.leonardozv.kafka.handson.models;

import com.fasterxml.jackson.databind.JsonNode;

public class KafkaProducerRequestModel {

	public String topico;
	public JsonNode header;
	public JsonNode payload;
	public JsonNode schema;
	
	public String getTopico() {
		return topico;
	}
	public void setTopico(String topico) {
		this.topico = topico;
	}
	public JsonNode getHeader() {
		return header;
	}
	public void setHeader(JsonNode header) {
		this.header = header;
	}
	public JsonNode getPayload() {
		return payload;
	}
	public void setPayload(JsonNode payload) {
		this.payload = payload;
	}
	public JsonNode getSchema() {
		return schema;
	}
	public void setSchema(JsonNode schema) {
		this.schema = schema;
	}
	
}
