package com.test.cep.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

	private String cep;
	
	@JsonProperty("logradouro")
	private String street;
	
}
