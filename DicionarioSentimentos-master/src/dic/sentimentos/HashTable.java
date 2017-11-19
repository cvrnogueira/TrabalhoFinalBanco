package dic.sentimentos;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class HashTable
{

	private HashTable1[] hashTable;
	private static int SIZE = 1249; //prime number

	public HashTable(){
		hashTable = new HashTable1[SIZE];
		for (int i = 0; i < SIZE; i++)
			hashTable[i] = new HashTable1(); //every index now have a linked List
	}
	/* computeHash
	 *  return an integer based on the input string
	 *  used for index into the array in hash table
	 */
	private int computeHash(String keyASerInserida){
		if(keyASerInserida.hashCode() % SIZE > 0){
			return keyASerInserida.hashCode() % SIZE;
		}
		else{
			return (keyASerInserida.hashCode() % SIZE * -1); // Calculate the rest of key's division by prime number 1249, returning it as hash index.
		}
	}
	private HashTable1 returnHashTable(int a){
		return hashTable[a];
	}

	public void setSize(int size){
		SIZE = size;
	}

	/* put
	 *  input: string word and int score to be inserted
	 *  First, look to see if word already exists in hash table
	 *   if so, solveCollision with the score to the WordEntry
	 *   if not, create a new Entry and push it on the list at the
	 *   appropriate array index
	 */

	public void put(String keyASerInserida, int score){

		int keyASerInseridaComoInt = computeHash(keyASerInserida);

		if(hashTable[keyASerInseridaComoInt].returnList().isEmpty()){ //add a new node in the linkedList
			hashTable[keyASerInseridaComoInt].add(new HashTable1(keyASerInseridaComoInt, score, keyASerInserida));


		}
		else { //collision has happened, so values have to be reset
			if(!(checkIfCollision( keyASerInserida,  score,  keyASerInseridaComoInt))){
				hashTable[keyASerInseridaComoInt].add(new HashTable1(keyASerInseridaComoInt, score, keyASerInserida)); //if there was no collision, it will add a new node at the end of the linked list

			}

		}
	}


	public boolean checkIfCollision(String keyASerInserida, int score, int keyASerInseridaComoInt){
		for(HashTable1 entry: hashTable[keyASerInseridaComoInt].returnList()){
			if (entry.getWord().equals(keyASerInserida)){
				entry.setValue(score);
				entry.incAppearances();
				entry.setTotalScore(score);
				return true;
			}
		}
		return false;
	}

	public HashTable1 getValueFromKey(String keyASerBuscada) {

		int keyASerInseridaComoInt = computeHash(keyASerBuscada);

		if (hashTable[keyASerInseridaComoInt] == null) //if the key does not exist is false
			return null;
		else {
			for(HashTable1 colisao: hashTable[keyASerInseridaComoInt].returnList()){ //check if a collision have happend, so it will search in the linked list associated to the key generated my computeHas()

				if(colisao.getWord().equals(keyASerBuscada)){
					return colisao;
				}
			}
			return null; //if does the key is not in the liked list of keys it does not exist
		}

	}
	public String giveWordOfList(int i){
		System.out.println(hashTable[i]);
		return hashTable[i].giveWordOfList(i);
	}
	public LinkedList<HashTable1> getValueFromInt(int num){
		return hashTable[num].returnList();
	}



	public boolean checkIfCollision2(String keyASerInserida, int score, int keyASerInseridaComoInt){
		for(HashTable1 entry: hashTable[keyASerInseridaComoInt].returnList()){
			if (entry.getWord().equals(keyASerInserida)){
				entry.setValue(score);
				entry.incAppearances();
				entry.setTotalScore(score);
				return true;
			}
		}
		return false;
	}

	/*Fuction used to teste hash eficiency and to make the tables that are in the report*/
	public void testaTamanho(){
		HashTable hashTable2 = this;

		int maior = 0; //lista com maior tamanho
		int temp = 0;
		int o =0;
		int total = 0;

		for(int i =0; i< SIZE; i++){
			temp = 0;
			if(hashTable2.returnHashTable(i).returnList().isEmpty()){
				o++;
			}

			for(HashTable1 entry: hashTable2.returnHashTable(i).returnList()){
				temp++;
				total++;
			}

			if(temp > maior){
				maior = temp;
			}
		}
	}

	public String salvaEmArquivo(String inputFileName){

		HashTable hashTable2 = this;
		Path path2 = Paths.get(inputFileName);
		if(Files.exists(path2)) {
			try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path2, Charset.forName("utf8")))) {
				for(int i =0; i< SIZE; i++){
					for(HashTable1 entry: hashTable2.returnHashTable(i).returnList()){
						writer.format("Chave= %s; Valor= %s; Escore Total= %s; Número de vezes em que aparece= %s%n",entry.getWord(), entry.getValue(), entry.getTotalScore(), entry.getNumAppearances());
					}
				}
				return ("Salvo no arquivo " + inputFileName); 
			}
			catch (IOException x) {
				return ("Erro ao escrever no arquivo"); 
			}
		}
		else{
			return ("Esse arquivo nao existe!"); 
		}
	}

}

