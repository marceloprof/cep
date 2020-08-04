package com.test.cep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.junit.jupiter.MockitoExtension;

import com.test.cep.client.ViaCepClient;
import com.test.cep.client.exception.ViaCepClientException;
import com.test.cep.model.Address;
import com.test.cep.service.impl.CepServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CepServiceImplTest {
	
	private static final String CEP = "30575-020";

	private CepServiceImpl cepServiceImpl;
	
	@Mock
	private ViaCepClient viaCepClient;
	
	@Test
	public void testGetCep() throws Exception {
		cepServiceImpl = new CepServiceImpl(viaCepClient);
		
		when(viaCepClient.getAddressByCep(anyString())).thenReturn(Address.builder().cep(CEP).street("Rua San Salvador").build());
		
		assertThat(cepServiceImpl).isNotNull();
		Address addressByCep = cepServiceImpl.getAddressByCep(CEP);
		assertThat(addressByCep).isNotNull();
		assertThat(addressByCep.getCep()).isEqualTo(CEP);
		assertThat(addressByCep.getStreet()).isEqualTo("Rua San Salvador");
	}
	
	@Test
	public void testGetCepError() throws Exception {
		cepServiceImpl = new CepServiceImpl(viaCepClient);
		
		when(viaCepClient.getAddressByCep(anyString())).thenThrow(new ViaCepClientException("Error message from client"));
		
		assertThat(cepServiceImpl).isNotNull();
		
		assertThatThrownBy(() -> cepServiceImpl.getAddressByCep(anyString())).isInstanceOf(ViaCepClientException.class).hasMessage("Your search has been failed, please check the cep");
	}

}
