package negocio;

import java.sql.SQLException;

import dados.EmprestimosBD;

public class comandaOperacoes {
	private EmprestimosBD banco;
	
	public comandaOperacoes(){
		try {
			banco = new EmprestimosBD();
		} catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
		}
	}
	public void realizaOperacoes(int opcao) {
		switch(opcao) {
		case 0: banco.sair(); break;
		case 01: banco.getAllFuncionarios();break;
		case 05: banco.relatorioReservasFuturas();break;
		case 07: banco.qtdReservasEcustoPorFunc();break;
		//case 08: banco. TODO: [Uma consulta à escolha do grupo (deve envolver pelo menos uma subconsulta)]"
		}
		
	}
	public Funcionario realizaOperacoesByName(int opcao, String nome) {
		banco.getFuncionarioPorNome(nome);
		return null;
		 
	}
	public void realizaOperacoes(int opcao, String descricao) {
		banco.getEquipamentoPorDescricao(descricao);		
	}
	public void realizaOperacoes(int opcao, Reserva reserva) {
		banco.realizaReserva( reserva);	
	}
	public void realizaOperacoes(int opcao, Equipamentos equip) {
		banco.qtdReservasEcustoDeEquip( equip);	
	}
}
