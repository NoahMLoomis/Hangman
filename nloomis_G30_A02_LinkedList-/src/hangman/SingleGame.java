package hangman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import linked_data_structures.*;

public class SingleGame implements java.io.Serializable{
	public static HangmanGame game = new HangmanGame();
	private SinglyLinkedList<String> wordLetters;
	private SinglyLinkedList<String> guessedLetters;
	private String theWord;
	private int badGuessNum;
	private StringBuilder hiddenWord;
	private boolean gameWon;
	private String filename;
	
	
	public SingleGame() {
		System.out.println("NEW SINGLEGAME GETTING CREATED");

//		System.out.println(game.dic.allWords.getLength());
//		this.theWord = game.dic.getNextWord();
//		System.out.println(game.dic.allWords.getLength());

		gameWon = false;
		wordLetters = new SinglyLinkedList<String>();
		guessedLetters = new SinglyLinkedList<String>();
		badGuessNum = 0;
		this.theWord = game.sendNextWord();
		System.out.println(theWord);
		
		for (int i = 0; i < theWord.length(); i++) {
			wordLetters.add(theWord.charAt(i) + "", i);
		}
		toString();
	}
	
	public SinglyLinkedList getGuessedLetters() {
		return guessedLetters;
	}

	public void hint() {
		int randIndex = (int) ((Math.random() * (wordLetters.getLength())));
		int j = 0;
		while (j < guessedLetters.getLength()) {
			while ((wordLetters.getElementAt(randIndex).equalsIgnoreCase("-"))
					|| (guessedLetters.getElementAt(j).equalsIgnoreCase(wordLetters.getElementAt(randIndex)))) {
				j = 0;
				randIndex = (int) ((Math.random() * (wordLetters.getLength())));
			}
			j++;
		}
		this.guessLetter(Character.toUpperCase(wordLetters.getElementAt(randIndex).charAt(0)));
	}

	public int getGuessesLeft() {
		return 6 - badGuessNum;
	}

	public String toString() {
		hiddenWord = new StringBuilder();
		for (int i = 0; i < wordLetters.getLength(); i++) {
			if (!(wordLetters.getElementAt(i).equalsIgnoreCase("-"))) {
				hiddenWord.append("*");
			} else {
				hiddenWord.append("-");
			}

			for (int j = 0; j < guessedLetters.getLength(); j++) {
				if (wordLetters.getElementAt(i).equalsIgnoreCase((guessedLetters.getElementAt(j)))) {
					hiddenWord.replace(i, i + 1, (wordLetters.getElementAt(i)));
				}
			}
		}
		return hiddenWord.toString();
	}

	private boolean containsStars() {
		for (int i = 0; i < hiddenWord.length(); i++) {
			if (hiddenWord.charAt(i) == '*') {
				return true;
			}
		}
		return false;
	}

	public boolean isGameOver() {

		if ((badGuessNum >= 6)) {
			gameWon = false;
			return true;
		} else if (!(containsStars())) {
			gameWon = true;
			return true;
		}

		return false;
	}

	public boolean getWinLossStatus() {
		return gameWon;
	}

	public boolean guessLetter(char guess) {

		try {
			if (!Character.isLetter(guess)) {
				throw new IllegalArgumentException();
			} else {
				guessedLetters.add(guess + "");
				for (int i = 0; i < wordLetters.getLength(); i++) {
					if ((Character.toUpperCase(wordLetters.getElementAt(i).charAt(0)) == guess)) {
						return true;
					}
				}
				badGuessNum++;
				return false;
			}
		} catch (Exception e) {
			System.out.println("Caught exception " + e);
		}
		return false;

	}

	public void save(){
	
			filename = "./cereal/gameSerealized.txt"; 
		
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(this);
			file.close();
			System.out.println("Object has been serealized");
			
		}catch (Exception e) {
			System.out.println("error with serealization " + e);
		}
	}




}