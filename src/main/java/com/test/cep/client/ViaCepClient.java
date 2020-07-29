package com.test.cep.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.cep.client.exception.ViaCepClientException;
import com.test.cep.model.Address;

@FeignClient(value = "viacep", url = "https://viacep.com.br/", fallback = ViaCepClientFallback.class)
public interface ViaCepClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/ws/{cep}/json/")
	Address getAddressByCep(@PathVariable("cep") String cep) throws ViaCepClientException;
}

class ViaCepClientFallback implements ViaCepClient {
	
	@Override
	public Address getAddressByCep(String cep) throws ViaCepClientException {
		throw new ViaCepClientException("Error calling the ViaCep api");
	}
	
}
