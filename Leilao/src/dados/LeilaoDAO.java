package dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LeilaoDAO {
	private Connection conexao;
	public LeilaoDAO() throws SQLException{
		conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
}
	  
	   
	
	///=====certos===//		
			 public void inserirNovaPessoa(String cpf, String email) throws SQLException{
			        Scanner in = new Scanner(System.in);      
			        Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD); 
			        String comandoSQL = "INSERT INTO USERSLEILAO(cpf, email) VALUES (?, ?)";
			        PreparedStatement stmt = conexao.prepareStatement(comandoSQL); //se perapar para mandar o comando comandoSQL no banco
			        
			        stmt.setString(1,cpf);
			        stmt.setString(2,email);
			        stmt.execute();
			        stmt.close();
			    }
			 public void inserirNovoLance(String cpf, Double value) throws SQLException{
			        Scanner in = new Scanner(System.in);      
			        Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD); 
			        String comandoSQL = "INSERT INTO LANCESLEILAO(cpf, value) VALUES (?, ?)";
			        PreparedStatement stmt = conexao.prepareStatement(comandoSQL); //se perapar para mandar o comando comandoSQL no banco
			        
			        stmt.setString(1,cpf);
			        stmt.setDouble(2,value);
			        stmt.execute();
			        stmt.close();
			    }
			 

			 public void inserirNovoLeilao(String ownerUser,  double minimunPrice, String shortDescription, String longDescription, String category ) throws SQLException{
			        Scanner in = new Scanner(System.in);      
			        Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD); 
			        String comandoSQL = "INSERT INTO DETALHESLEILAO( ownerUser,   minimunPrice,  shortDescription,  longDescription,  category ) VALUES (?, ?, ?, ?, ?)";
			        PreparedStatement stmt = conexao.prepareStatement(comandoSQL); //se perapar para mandar o comando comandoSQL no banco
			       
			        stmt.setString(1,ownerUser);
			        stmt.setDouble(2,minimunPrice);
			        stmt.setString(3,shortDescription);
			        stmt.setString(4,longDescription);
			        stmt.setString(5,category);
			        stmt.execute();
			        stmt.close();
			    }
			 public HashMap<String, Double> listarTodosLances() throws SQLException{
					HashMap<String, Double> lances = new HashMap<String, Double>();
			        String comandoSQL = "SELECT cpf, value FROM LANCESLEILAO ORDER BY value";
			        PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL); 
			        //mando executar a consulta
			        ResultSet registros = consultaSQL.executeQuery(); 

			        while(registros.next()){
			            String cpf = registros.getString("cpf");
			            Double value = registros.getDouble("value");
			            lances.put(cpf, value);
			        }
			        //fecha tudo!
			        registros.close();
			        consultaSQL.close();
			        return lances;
			    }
				
				public void sair() throws SQLException{
					//TODO: DROP NOS VALORES DE TODAS TABELAS
					conexao.close();
				}
}