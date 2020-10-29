package hangman;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import linked_data_structures.SinglyLinkedList;

public class HangmanGame implements java.io.Serializable {
	public ScoreBoard board;
	private Dictionary dic;
	private SingleGame newGame;
	private SinglyLinkedList<SingleGame> allGames;

	/*
	 * This constructor checks if there is a previous game that's been saved. If so,
	 * it loads that past game If not, it creates a new one
	 */
	public HangmanGame() {
		HangmanGame prevGame = resume();
		if (prevGame == null) {
			allGames = new SinglyLinkedList<>();
			board = new ScoreBoard();
			dic = new Dictionary();
			resetSingleGame();
		} else {
			allGames = prevGame.allGames;
			board = prevGame.board;
			dic = prevGame.dic;
			newGame = prevGame.newGame;
		}
	}

	public SingleGame getCurrGame() {
		return newGame;
	}

	public void resetSingleGame() {
		allGames.add(newGame);
		newGame = new SingleGame();
		newGame.setWord(dic.getNextWord());
	}

	public HangmanGame resume() {
		HangmanGame prevGame = null;
		try {
			String filename = "./cereal/gameSerealized.txt";
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			prevGame = (HangmanGame) in.readObject();
			in.close();
			file.close();
		} catch (Exception e) {
			System.out.println("File is empty  " + e);
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
			System.out.println("Game is cereal now");
		} catch (Exception e) {
			System.out.println("error with serealization " + e);
		}
	}

}