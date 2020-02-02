package com.jhei.cursomc.cursomc.domain.enums;

import java.util.Objects;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Fisica"),
	PESSOAJURITICA(2, "Pessoa Juridica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String desc) {
		this.cod = cod;
		this.descricao = desc;		
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	

	public static TipoCliente toEnum(Integer cod) {
		
		if(Objects.isNull(cod)) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Código inválido: " + cod);
	}
}
