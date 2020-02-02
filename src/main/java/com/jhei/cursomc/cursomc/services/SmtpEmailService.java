package com.jhei.cursomc.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	@Value("${spring.mail.password}")
	private String senha;
	
	private static final Logger LogClasse = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LogClasse.info("Enviando email...");
		LogClasse.info(msg.toString());
		LogClasse.info("Email enviado");
		mailSender.send(msg);
	}

}
