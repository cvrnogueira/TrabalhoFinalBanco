package dic.sentimentos;

public class InvertedIndex {

	private int score;
	private String key;
	private HashTable2[] escoresHMap;
	private static int SIZE = 1249; //prime number

	public InvertedIndex(int score){
		escoresHMap = new HashTable2[SIZE];
		for (int i = 0; i < SIZE; i++)
			escoresHMap[i] = new HashTable2(); //cada indice do array agora tem uma lista encadeada
	}
	public InvertedIndex(){
		escoresHMap = new HashTable2[SIZE];
		for (int i = 0; i < SIZE; i++)
			escoresHMap[i] = new HashTable2(); //cada indice do array agora tem uma lista encadeada
	}
	private int computeHash(String keyASerInserida){
		if(keyASerInserida.hashCode() % SIZE > 0){
			return keyASerInserida.hashCode() % SIZE;
		}
		else{
			return (keyASerInserida.hashCode() % SIZE * -1); // Calculate the rest of key's division by prime number 1249, returning it as hash index.
		}
	}
	public void put(String keyASerInserida, int id){
		int keyASerInseridaComoInt = computeHash(keyASerInserida);

		if(escoresHMap[keyASerInseridaComoInt].returnList().isEmpty()){ //add a new node in the linkedList
			escoresHMap[keyASerInseridaComoInt].add(new HashTable2(keyASerInserida, id));

		}
		else { //collision has happened, so values have to be reset
			if(!(checkIfCollision( keyASerInserida,  id,  keyASerInseridaComoInt))){
				escoresHMap[keyASerInseridaComoInt].add(new HashTable2(keyASerInserida, id)); //if there was no collision, it will add a new node at the end of the linked list
			}

		}
	}
	public boolean checkIfCollision(String keyASerInserida, int id, int keyASerInseridaComoInt){
		for(HashTable2 entry: escoresHMap[keyASerInseridaComoInt].returnList()){ //return the collision list
			if (entry.getKey().equals(keyASerInserida)){ //SE O VALOR JÁ TÁ LÁ, VOU ADICIONAR O ID NO ARRAYLIST
				entry.addTweet(id);
				return true;
			}
		}
		return false;
	}
	public HashTable2 getValueFromKey(String keyASerBuscada) {

		int keyASerBuscadaComoInt = computeHash(keyASerBuscada);

		if (escoresHMap[keyASerBuscadaComoInt] == null) //if the key does not exist is false
			return null;
		else {
			for(HashTable2 colisao: escoresHMap[keyASerBuscadaComoInt].returnList()){ //check if a collision have happend, so it will search in the linked list associated to the key generated my computeHas()
				if(colisao.getKey().equals(keyASerBuscada)){
					return colisao;
				}
			}
			return null; //if does the key is not in the liked list of keys it does not exist
		}

	}


}
