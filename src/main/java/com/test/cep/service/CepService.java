package com.test.cep.service;

import com.test.cep.model.Address;

public interface CepService {
	
	public Address getAddressByCep(String string) throws Exception;

}
