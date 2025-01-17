package com.jhei.cursomc.cursomc.domain.enums;

import java.util.Objects;

public enum Perfil {

	/*
	  é adicionando o role pelo fato do security do spring exigir 
    */
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String desc) {
		this.cod = cod;
		this.descricao = desc;		
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		
		if(Objects.isNull(cod)) {
			return null;
		}
		
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Código inválido: " + cod);
	}
}
