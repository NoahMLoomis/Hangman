package hangman;

import linked_data_structures.*;

public class SingleGame implements java.io.Serializable {
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

	public SingleGame(String theWord) {
		gameWon = false;
		wordLetters = new SinglyLinkedList<String>();
		guessedLetters = new SinglyLinkedList<String>();
		badGuessNum = 0;
		setWord(theWord);
		toString();
	}

	public String getWord() {
		return this.theWord;
	}

	/*
	 * set theWord populate wordLetters with theWord's letters
	 */

	public void setWord(String newWord) {
		this.theWord = newWord;
		for (int i = 0; i < theWord.length(); i++) {
			wordLetters.add(theWord.charAt(i) + "", i);
		}
	}

	public SinglyLinkedList getGuessedLetters() {
		return guessedLetters;
	}

	/*
	 * If the game is over, return false. Else, select a random index from the
	 * length of the wordLetters while the letter at that index has already been
	 * guessed, restart the loop and select another random index, until you get a
	 * new letter that is correct and hasn't been guessed call guessLetter() with
	 * the new correct letter, and inc badGuessNum return true so you know the hint
	 * worked
	 */
	public boolean hint() {
		if (!isGameOver()) {
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
			return true;
		}
		return false;
	}

	public int getGuessesLeft() {
		return 6 - badGuessNum;
	}

	/*
	 * If a wordLetter, add a *, else add what it is. if a guessedLetter is also a
	 * wordLetter, replace that * (and any others) with the correct letter
	 */

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

	/*
	 * If hiddenWord length is 0, return true Loop thought the hiddenWord, if a
	 * letter is a *, return true else if loop completes and no * have been found,
	 * return false;
	 */

	public boolean containsStars() {
		toString();
		if (hiddenWord.length() != 0) {
			for (int i = 0; i < hiddenWord.length(); i++) {
				if (hiddenWord.charAt(i) == '*') {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	/*
	 * If the badGuessNum is >=6, set gameWon to false, return true else if the word
	 * contains no more *'s, set gameWon and return true else, return false
	 */

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

	/*
	 * If the game is over, return false if the guess is not a letter, throw an
	 * IllegalArgumentException else, loop through guessedLetters, if it contains
	 * the guess, return false add the guess to guessLetters loop through
	 * wordLetters, if if contains the guess, return true incBadGuess, return false
	 */

	public boolean guessLetter(char guess) {
		toString();
		if (!isGameOver()) {
			if (!Character.isLetter(guess)) {
				throw new IllegalArgumentException();
			} else {

				for (int i = 0; i < guessedLetters.getLength(); i++) {
					if (Character.toUpperCase(guessedLetters.getElementAt(i).charAt(0)) == Character
							.toUpperCase(guess)) {
						return false;
					}
				}
				guessedLetters.add(guess + "");
				for (int i = 0; i < wordLetters.getLength(); i++) {
					if ((Character.toUpperCase(wordLetters.getElementAt(i).charAt(0)) == Character
							.toUpperCase(guess))) {
						return true;
					}
				}
				badGuessNum++;
			}
		}
		return false;
	}

}// SingleGame