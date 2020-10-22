package hangman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import linked_data_structures.SinglyLinkedList;

public class HangmanGame {
	public static ScoreBoard board; 
	public static Dictionary dic;
	
	 public HangmanGame() {
		System.out.println("NEW HANGMANGAME GETTING CREATED");

		board = new ScoreBoard();
		dic= new Dictionary();
	}
	
	public String sendNextWord() {
		
		return dic.getNextWord();
	}
	
//	public void save(SingleGame obj) {
//		prevGame = obj;
//		String filename = "./cereal/gameSerealized.txt"; 
//	
//	try {
//		FileOutputStream file = new FileOutputStream(filename);
//		ObjectOutputStream out = new ObjectOutputStream(file);
//		
//		out.writeObject(obj);
//		file.close();
//		System.out.println("Object has been serealized");
//		
//	}catch (Exception e) {
//		System.out.println("error with serealization " + e);
//	}
//}

	
	
	public SingleGame resumeGame() {
		
		SingleGame prevGame = new SingleGame();
//		if (prevGame != null) {
		
	     try
	     {  
	    	String filename = "./cereal/gameSerealized.txt"; 
	         // Reading the object from a file 
	         FileInputStream file = new FileInputStream(filename); 
	         ObjectInputStream in = new ObjectInputStream(file); 
	           
	         prevGame = (SingleGame)in.readObject(); 
	           
	         in.close(); 
	         file.close(); 
	           
	         System.out.println("Object has been deserialized: " + prevGame.getGuessesLeft()); 
	         
	         

	     } 
	       
	     catch(Exception e) 
	     { 
	         System.out.println("IOException is caught " + e); 
	     } 
//		}
		return prevGame;
	}
	
	//TODO Serealize HangmanGame
	
}