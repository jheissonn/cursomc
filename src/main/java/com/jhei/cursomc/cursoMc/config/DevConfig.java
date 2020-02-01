package com.jhei.cursomc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jhei.cursomc.cursomc.services.DbService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DbService service;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if(!"create".equals(strategy)) {
			service.instantiateTestDataBase();
		}
		
		return true;
		
	}
}
