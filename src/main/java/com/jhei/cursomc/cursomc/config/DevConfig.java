package com.jhei.cursomc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jhei.cursomc.cursomc.services.DbService;
import com.jhei.cursomc.cursomc.services.EmailService;
import com.jhei.cursomc.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DbService service;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if("create".equals(strategy)) {
			service.instantiateTestDataBase();
		}
		
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
		return new SmtpEmailService();		
	}
}
