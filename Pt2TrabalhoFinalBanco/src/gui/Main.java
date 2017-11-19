package gui;

import java.util.Scanner;

import negocio.comandaOperacoes;

public class Main {
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		comandaOperacoes comandaOperacao = new comandaOperacoes();
		String nome = null;
		String descricao = null;
		int option;
		do {
			System.out.println("====Digite o N�mero da op��o====");
			System.out.println("1) Listar funcionarios em ordem alfab�tica");
			System.out.println("2) Buscar funcion�rio por nome");
			System.out.println("3) Buscar equipamento por descri��o");
			System.out.println("4) Fazer uma nova reserva");
			System.out.println("5) Relatorio de reservas futuras");
			System.out.println("6) Visualizar a quantidade de reservas de um equipamento e o total do custo correspondente");
			System.out.println("7) Listar n�mero de reserva e custo total de uso de equipamentos por funcin�rio");
			System.out.println("8) TODO: [Uma consulta � escolha do grupo (deve envolver pelo menos uma subconsulta)]");
			System.out.println("0) Sair");
			option = in.nextInt();
			if(option == 2) {
				System.out.println("Digite o nome");
				nome = in.nextLine();
				comandaOperacao.realizaOperacoesByName(option, nome);
			}
			if( option == 3) {
				System.out.println("Digite a descricao");
				descricao = in.nextLine();
				comandaOperacao.realizaOperacoes(option, descricao);
			}
			if(option ==4) {
				//fazernova reserva
			}
		}while(option !=0);
	}
}
