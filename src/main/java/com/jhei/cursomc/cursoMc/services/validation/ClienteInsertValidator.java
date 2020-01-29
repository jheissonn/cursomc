package com.jhei.cursomc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jhei.cursomc.cursomc.domain.enums.TipoCliente;
import com.jhei.cursomc.cursomc.dto.ClienteNewDTO;
import com.jhei.cursomc.cursomc.repositories.ClienteRepository;
import com.jhei.cursomc.cursomc.resources.exceptions.FieldMessage;
import com.jhei.cursomc.cursomc.services.validation.utils.BR;
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCpf(objDto.getCpfoucnpj())) {
			list.add(new FieldMessage("cpfoucnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURITICA.getCod()) && !BR.isValidCnpj(objDto.getCpfoucnpj())) {
			list.add(new FieldMessage("cpfoucnpj", "CNPJ inválido"));
		}
		
		
		if(repo.findByEmail(objDto.getEmail()) != null) {
			list.add(new FieldMessage("email", "Email já existente."));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}