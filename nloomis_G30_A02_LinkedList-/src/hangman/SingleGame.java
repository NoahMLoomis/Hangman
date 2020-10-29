package hangman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import linked_data_structures.*;

public class SingleGame implements java.io.Serializable{
	private SinglyLinkedList<String> wordLetters;
	private SinglyLinkedList<String> guessedLetters;
	private String theWord;
	private int badGuessNum;
	private StringBuilder hiddenWord;
	private boolean gameWon;
	
	
	public SingleGame() {
		gameWon = false;
		wordLetters = new SinglyLinkedList<String>();
		guessedLetters = new SinglyLinkedList<String>();
		badGuessNum = 0;
		
		toString();
	}
	
	public String getWord() {
		return this.theWord;
	}
	
	public void setWord(String newWord) {
		this.theWord = newWord;
		for (int i = 0; i < theWord.length(); i++) {
			wordLetters.add(theWord.charAt(i) + "", i);
		}
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
		this.badGuessNum++;
		this.guessLetter(Character.toUpperCase(wordLetters.getElementAt(randIndex).charAt(0)));
	}

	public int getGuessesLeft() {
		return 6 - badGuessNum;
	}

	
	public String toString() {
		hiddenWord = new StringBuilder();
		for (int i = 0; i < wordLetters.getLength(); i++) {
			if ((Character.isLetter(wordLetters.getElementAt(i).charAt(0)))) {
				hiddenWord.append("*");
			} else {
				hiddenWord.append(wordLetters.getElementAt(i));
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

}