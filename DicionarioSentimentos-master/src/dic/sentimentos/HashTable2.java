package dic.sentimentos;

import java.util.LinkedList;
/* Object of InvertedIndex.java*/
public class HashTable2 {


	private String key;
	private int key2;
	private int id;
	LinkedList<HashTable2> colisions;
	private LinkedList<Integer> idTweets;

	public HashTable2(){
		colisions = new LinkedList<>();
	}

	public HashTable2(String key, int id){
		this.key = key;
		this.id = id;
		idTweets= new LinkedList<>();
	}
	public String getKey() {
		return key;
	}
	public int getId() {
		return id;
	}
	public void add(HashTable2 hashTable2) {
		this.colisions.add(hashTable2);	
	}
	public LinkedList<Integer> returnTweetsList(){
		return this.idTweets;
	}
	public void addTweet(int tweetIndex){
		this.idTweets.add(tweetIndex);
	}
	public LinkedList<HashTable2> returnList(){
		return this.colisions;
	}
	@Override
	public String toString() {
		return (" === key é: " + this.getKey() + "\n ==== [" + "value é" +  "] "  + this.getId() + "\n");
	}


}
