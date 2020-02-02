package com.jhei.cursomc.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.jhei.cursomc.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
		
	
}
