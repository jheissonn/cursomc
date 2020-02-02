package com.jhei.cursomc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jhei.cursomc.cursomc.services.DbService;
import com.jhei.cursomc.cursomc.services.EmailService;
import com.jhei.cursomc.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DbService service;

	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		service.instantiateTestDataBase();
		
		return true;
		
	}
	
	/**
	 * @BEAN responsável por retornar uma instancia no perfil de teste quando chamado pelo pedido service
     * o spring procura pelo @bean e retorna a implementação parametrizada
	 * @return
	 * @throws ParseException
	 */
	@Bean
	public EmailService emailService() throws ParseException {
		return new MockEmailService();		
	}
}
