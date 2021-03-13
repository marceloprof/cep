package com.test.cep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.cep.client.exception.ViaCepClientException;
import com.test.cep.dto.AddressResult;
import com.test.cep.model.Address;
import com.test.cep.service.CepService;

@WebMvcTest(CepController.class)
public class CepControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CepService cepService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testRoot() throws Exception{
		this.mockMvc.perform(get("/cep/")).andExpect(status().isOk());
	}
	
	@Test
	public void getAddressByCep() throws Exception {

		when(cepService.getAddressByCep(anyString())).thenReturn(Address.builder().cep("30575-020").street("Rua San Salvador").build());
		
		MvcResult result = this.mockMvc.perform(get("/cep/30575020/address")).andExpect(status().isOk()).andReturn();
		AddressResult addressResult = objectMapper.readValue(result.getResponse().getContentAsString(), AddressResult.class);
		
		assertThat(addressResult).isNotNull();
		assertThat(addressResult.getCep()).isEqualTo("30575-020");
		assertThat(addressResult.getStreet()).isEqualTo("Rua San Salvador");
	}
	
	@Test
	public void getAddressByCepError() throws Exception {
		
		when(cepService.getAddressByCep(anyString())).thenThrow(new ViaCepClientException("Invalid cep"));
		
		MvcResult result = this.mockMvc.perform(get("/cep/30575/address")).andExpect(status().isOk()).andReturn();
		AddressResult addressResult = objectMapper.readValue(result.getResponse().getContentAsString(), AddressResult.class);
		
		assertThat(addressResult).isNotNull();
		assertThat(addressResult.getCep()).isNull();;
		assertThat(addressResult.getStreet()).isNull();
		assertThat(addressResult.getError()).isNotNull();
		assertThat(addressResult.getError().getMessage()).isEqualTo("Invalid cep");
	}
	
	@Test
	public void getAddressByCepUnknownError() throws Exception {
		
		when(cepService.getAddressByCep(anyString())).thenThrow(new Exception("Invalid cep"));
		
		MvcResult result = this.mockMvc.perform(get("/cep/30575/address")).andExpect(status().isOk()).andReturn();
		AddressResult addressResult = objectMapper.readValue(result.getResponse().getContentAsString(), AddressResult.class);
		
		assertThat(addressResult).isNotNull();
		assertThat(addressResult.getStreet()).isNull();
		assertThat(addressResult.getCep()).isNull();;
		assertThat(addressResult.getError()).isNotNull();
		assertThat(addressResult.getError().getMessage()).isEqualTo("Unknown Error, please contact the call center");
	}
	
}
