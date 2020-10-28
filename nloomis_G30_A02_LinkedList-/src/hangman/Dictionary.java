package hangman;

import java.io.File;
import java.util.*;
import linked_data_structures.SinglyLinkedList;

public class Dictionary implements java.io.Serializable {
	private SinglyLinkedList<String> allWords;

	// Git test
	public Dictionary() {
		System.out.println("NEW DICTIONARY GETTING CREATED");
		allWords = new SinglyLinkedList<>();
		loadWords();
	}

	private void loadWords() {
		try {
			File dicFile = new File("dictionary.txt");
			Scanner fileReader = new Scanner(dicFile);

			while (fileReader.hasNext()) {
				allWords.add(fileReader.next());
			}
		} catch (Exception e) {
			System.out.println("Exeption " + e + "occured");
			e.printStackTrace();
		}
	}

	public String getNextWord() {
		if (allWords.getLength() <=0) {
			loadWords();
		}
		int randIndex = (int) ((Math.random() * (allWords.getLength())));
		String temp = allWords.getElementAt(randIndex);
		allWords.remove(randIndex);
		System.out.println("Number of words left is " + allWords.getLength());
		return temp;
	}

}
