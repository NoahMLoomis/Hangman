package hangman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import linked_data_structures.SinglyLinkedList;

public class HangmanGame implements java.io.Serializable{
	public static ScoreBoard board; 
	public static Dictionary dic;
	
	 public HangmanGame() {
//		System.out.println("NEW HANGMANGAME GETTING CREATED");
		 
		board = new ScoreBoard();
		dic= new Dictionary();
	}
	
	public String sendNextWord() {
		return dic.getNextWord();
	}
	
	public SingleGame resumeSingleGame() {
		
		SingleGame prevGame=null;
//		if (prevGame != null) {
		
	     try
	     {  
	    	String filename = "./cereal/singleGameSerealized.txt"; 
	         // Reading the object from a file 
	         FileInputStream file = new FileInputStream(filename); 
	         ObjectInputStream in = new ObjectInputStream(file); 
	           
	         prevGame = (SingleGame)in.readObject(); 
	           
	         in.close(); 
	         file.close(); 
	     } 
	       
	     catch(Exception e) 
	     { 
	         System.out.println("IOException is caught " + e); 
	     } 
		return prevGame;
	}
	
	public void saveGame() {
		String filename = "./cereal/gameSerealized.txt"; 
		
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(this);
			file.close();
			System.out.println("HangmanGame has been serealized");
			
		}catch (Exception e) {
			System.out.println("error with serealization " + e);
		}
	}
	
	//TODO Serealize HangmanGame
	
}