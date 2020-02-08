package com.jhei.cursomc.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Cliente;
import com.jhei.cursomc.cursomc.repositories.ClienteRepository;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) { // se o email não existe
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword(); // gera uma nova senha
		cliente.setSenha(pe.encode(newPass)); // salva a senha criptografada
		clienteRepository.save(cliente); // salva nova senha
		emailService.sendNewPasswordEmail(cliente, newPass); // envia email
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i = 0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		if(opt == 1) { // gera uma letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera uma letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
