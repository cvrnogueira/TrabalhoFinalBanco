package bdLeilao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dados.LeilaoDAO;

public class Leilao {

	private String ownerUser; //cpf do usuario q � o owner
	private double minimunPrice;
	private String winnerUser; //usuario vencedor(est� sempre sendo atualizado p o q d� o maior lance)
	private String shortDescription;
	private String longDescription;
	private String category;
	private LeilaoDAO banco;
	private Lances lance;
	private HashMap<String, Double> Lances;
	
	public Leilao( String ownerUser,  double minimunPrice, String shortDescription, String longDescription, String category ) throws SQLException{
	//manda para o banco salvar o leilao criado
		banco.inserirNovoLeilao(ownerUser, minimunPrice, shortDescription, longDescription, category);
		this.ownerUser = ownerUser;
		this.minimunPrice = minimunPrice;
		this.shortDescription = shortDescription;
		this.longDescription =  longDescription;
		this.category = category;
	}
	
	public void endLeilao() throws SQLException {
		//seta o vencedor, da drop em todos dados da tabela para ter uma tabela nova pra os proximos e encerra a conex�o
		actualWinner();
		banco.sair();
	}
	public void saveNewLance( User user, Double value) throws SQLException {
		//salva no banco novos lances que a pessoa tenha feito]
		Boolean createLance = true;
		for (Entry<String, Double> pair : Lances.entrySet()) {
			if(pair.getValue() == value || value < minimunPrice) {
				createLance = false;
			}
		}
		if(createLance) {
			lance.createLance( user, value);	
		}
		else {
			System.out.println("Valor para lance deve ser maior do que lances j� existentes e maior do que o valor minimo");
		}
	}
	public void cancelLance(User user, Double value) {
		
	}
	public void showAllLances() throws SQLException {
		//mostra todos os lances j� feitos(pega do banco)
		this.Lances = banco.listarTodosLances();
		
	}
	public void actualWinner() {
		//diz quem � o vencedor no momento
		Double maior = 0.0;
		String winner = null;
		for (Entry<String, Double> pair : Lances.entrySet()) {
			if(pair.getValue() > maior) {
				maior = pair.getValue();
				winner = pair.getKey();
			}
		}
		
	}
}