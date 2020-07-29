package com.test.cep.service.impl;

import org.springframework.stereotype.Service;

import com.test.cep.client.ViaCepClient;
import com.test.cep.client.exception.ViaCepClientException;
import com.test.cep.model.Address;
import com.test.cep.service.CepService;

@Service
public class CepServiceImpl implements CepService {
	
	private ViaCepClient viaCepClient;
	
	public CepServiceImpl(ViaCepClient viaCepClient) {
		this.viaCepClient = viaCepClient;
	}

	public Address getAddressByCep(String string) throws Exception{
		try {
			return viaCepClient.getAddressByCep(string);
		}catch (ViaCepClientException e) {
			throw new ViaCepClientException("Your search has been failed, please check the cep");
		}
	}

}
