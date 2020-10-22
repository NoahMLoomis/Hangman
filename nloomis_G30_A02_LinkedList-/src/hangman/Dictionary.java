package hangman;

import java.io.File;
import java.util.*;
import linked_data_structures.SinglyLinkedList;

public class Dictionary {
	private SinglyLinkedList<String> allWords = new SinglyLinkedList<>();

	
	public Dictionary() {
		System.out.println("NEW DICTIONARY GETTING CREATED");

		try {
			File dicFile = new File("dictionary.txt");
			Scanner fileReader = new Scanner(dicFile);
			
			while (fileReader.hasNext()) {
				allWords.add(fileReader.next());
			}
		}catch (Exception e) {
			System.out.println("Exeption " + e + "occured");
			e.printStackTrace();
		}
		
		
	}
	
	public String getNextWord() {
		int randIndex = (int) ((Math.random() * (allWords.getLength())));
		String temp = allWords.getElementAt(randIndex);
		allWords.remove(randIndex);

		return temp;
	}

}
