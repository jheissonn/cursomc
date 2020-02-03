package com.jhei.cursomc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LogClasse = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LogClasse.info("Simulando envio de email...");
		LogClasse.info(msg.toString());
		LogClasse.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LogClasse.info("Simulando envio de email html...");
		LogClasse.info(msg.toString());
		LogClasse.info("Email enviado email html");
		
	}

	
}
