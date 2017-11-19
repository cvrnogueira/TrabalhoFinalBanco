package bdLeilao;

import java.sql.SQLException;

import dados.LeilaoDAO;

public class User {
	private String cpf;
	private String email;
	private LeilaoDAO banco;
	
	public User(String cpf, String email) throws SQLException {
		//cria uma pessoa no banco
		banco.inserirNovaPessoa(cpf, email);
		this.cpf = cpf;
		this.email = email;
	}
	public String getCPF() {
		return cpf;
	}
	
}
