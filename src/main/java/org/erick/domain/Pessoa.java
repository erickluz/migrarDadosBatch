package org.erick.domain;

import java.util.Date;

import org.apache.logging.log4j.util.Strings;

import lombok.Data;

@Data
public class Pessoa {
	private int id;
	private String nome;
	private String email;
	private Date dataNascimento;
	private int idade;
	
	public boolean isValida() {
		return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
	}
}
