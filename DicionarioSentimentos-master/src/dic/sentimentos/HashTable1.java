package dic.sentimentos;


import java.util.LinkedList;

/* Object of HashTbale.java*/
public class HashTable1 {

	private int key;
	private String word;
	private int value;
	private int totalScore;
	private int numAppearances= 0;
	private LinkedList<HashTable1> colisions;
	private LinkedList<Integer> tweetList;

	public HashTable1(){
		colisions = new LinkedList<>();
	}

	public HashTable1(int key, int value, String word){
		this.key = key;
		this.value = value;
		this.word = word;
		this.totalScore = value;
		this.numAppearances = 1;
		tweetList =  new LinkedList<Integer>();
	}
	public int getKey() {
		return this.key;
	}
	public String getWord() {
		return this.word;
	}
	public void addTweet(int tweetIndex){
		this.tweetList.add(tweetIndex);

	}
	public LinkedList<Integer> returnTweetList(){
		return this.tweetList;
	}
	public LinkedList<HashTable1> returnList(){
		return this.colisions;
	}
	public void add(HashTable1 hashTable1) {
		this.colisions.add(hashTable1);	
	}
	public int getValue() {
		return value;
	} 
	public void incAppearances(){
		this.numAppearances +=1;
	}
	public String giveWordOfList(int j){
		if(this.colisions != null){
			return this.colisions.get(j).getWord();
		}
		else return null;
	}
	public void setValue(int value) {
		this.value = this.totalScore/this.numAppearances;
	}
	public int getNumAppearances(){
		return this.numAppearances;
	}
	public double getTotalScore(){
		return this.totalScore;
	}
	public void setTotalScore(int score){
		totalScore+=score;
	}
	public double getAcummulateScore(){

		return this.getTotalScore()/this.getNumAppearances();
	}

	@Override
	public String toString() {
		return (" === key é: " + this.getKey() + "\n ==== [" + "value é" +  "] "  + this.getValue() +  "\n ==== [" + "Total value é" +  "] "  + this.getTotalScore() + "\n ==== Word é " + this.getWord() 
		+ "\n ==== Apareceu "  + this.getNumAppearances() + " vezes \n");
	}


}
