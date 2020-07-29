package com.test.cep.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResult {

	private String cep;
	private String street;
	private ErrorDTO error;

}
