package com.jhei.cursomc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	private static final Logger LogClasse = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LogClasse.info("Enviando email...");
		LogClasse.info(msg.toString());
		LogClasse.info("Email enviado");
		mailSender.send(msg);
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LogClasse.info("Enviando email...");		
		javaMailSender.send(msg);
		LogClasse.info("Email enviado");
		
	}

}
