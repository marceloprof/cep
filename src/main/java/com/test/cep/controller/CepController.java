package com.test.cep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.cep.client.exception.ViaCepClientException;
import com.test.cep.dto.AddressResult;
import com.test.cep.dto.AddressResult.AddressResultBuilder;
import com.test.cep.dto.ErrorDTO;
import com.test.cep.model.Address;
import com.test.cep.service.CepService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("cep")
@Slf4j
public class CepController {

	@Autowired
	private CepService cepService;
	
	@GetMapping(path = "/")
	public String root() {
		return "OK";
	}
	
	@GetMapping(path = "/{cep}/address", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AddressResult> getAddressByCep(@PathVariable("cep") String cep) {
		Address addressByCep;
		AddressResultBuilder addressBuilder = AddressResult.builder();
		try {
			addressByCep = cepService.getAddressByCep(cep);
			return new ResponseEntity<AddressResult>(addressBuilder.cep(addressByCep.getCep()).street(addressByCep.getStreet()).build(), HttpStatus.OK);
		} catch (ViaCepClientException e) {
			log.error(e.getMessage());
			return new ResponseEntity<AddressResult>(addressBuilder.error(new ErrorDTO(e.getMessage())).build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<AddressResult>(addressBuilder.error(new ErrorDTO("Unknown Error, please contact the call center")).build(), HttpStatus.OK);
		}
	}
	
}
