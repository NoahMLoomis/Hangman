package hangman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class SingleGameTest {

	@Test
	void testHint() {
		//Testing displaying special-characters
		SingleGame game1 = new SingleGame("I'll-be-testing");
		assertEquals("*'**-**-*******", game1.toString(), "Test Case 1: toString() with no letters guessed");
		
		//Testing guessing a letter after the game is over
		SingleGame game10 = new SingleGame("Test");
		assertTrue(game10.hint());
		assertTrue(game10.hint());
		assertTrue(game10.hint());
		assertFalse(game10.hint());
		
	
	}
	
	@Test
	void testGuessLetter() {
		SingleGame game2 = new SingleGame("Test");
		assertEquals(6, game2.getGuessesLeft(), "Test Case 2: getGuessesLeft() with no guesses made yet ");
		assertFalse(game2.guessLetter('C'), "Test Case 2: guessLetter(char) with a bad letter");
		assertEquals(5, game2.getGuessesLeft(), "Test Case 2: getGuessesLeft() with one bad guess made ");
		assertTrue(game2.guessLetter('E'), "Test Case 2: guessLetter(char) with a correct letter");
		assertEquals(5, game2.getGuessesLeft(), "Test Case 2: getGuessesLeft() with one bad guess made, and one correct guess ");
		
		//Guessing a letter that has already been guessed
		assertFalse(game2.guessLetter('E'), "Test Case 2: guessLetter(char) with a letter that has already been guessed");
		
		try {
			game2.guessLetter('9');
			fail("Test Case 2: IllegalArgumentException not thrown");
		}catch (IllegalArgumentException e) {
			assertTrue(true, "Test Case 2: Correctly catch IllegalArgumentException with a number");
		}
		
		try {
			game2.guessLetter('*');
			fail("Test Case 2: IllegalArgumentException not thrown");
		}catch (IllegalArgumentException e) {
			assertTrue(true, "Test Case 2: Correctly catch IllegalArgumentException with a special character");
		}
		
		// guessing a letter when the game is over
		assertTrue(game2.guessLetter('s'));
		assertTrue(game2.guessLetter('t'));
		assertFalse(game2.guessLetter('Z'));
		
		
		
		

	}
	
	@Test
	void testToString() {
		SingleGame game3 = new SingleGame("Test");
		assertEquals("****", game3.toString(), "Test Case 3: toString() with no letters guessed");
		assertTrue(game3.guessLetter('t'));
		assertEquals("T**t", game3.toString(), "Test Case 3: toString with two different letters guessed");
		assertTrue(game3.guessLetter('e'));
		assertEquals("Te*t", game3.toString());
		game3.toString();
		assertTrue(game3.guessLetter('s'));
		assertEquals("Test", game3.toString(), "Test Case 3: toString() all letters of the word guessed");
		
	}
	
	
	@Test
	void testContainsStars() {
		SingleGame game4 = new SingleGame("Test");
		assertTrue(game4.containsStars());
		assertTrue(game4.guessLetter('t'));
		assertTrue(game4.containsStars());
		assertTrue(game4.guessLetter('s'));
		assertTrue(game4.containsStars());
		assertTrue(game4.guessLetter('e'));
		assertFalse(game4.containsStars());
	}
	
	
	@Test
	void testIsGameOver() {
		//Checking when a game is over when the user runs out of guesses
		SingleGame game5 = new SingleGame("Test");
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('A'));
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('B'));
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('C'));
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('D'));
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('Z'));
		assertFalse(game5.isGameOver());
		assertFalse(game5.guessLetter('V'));
		assertTrue(game5.isGameOver());
		assertFalse(game5.getWinLossStatus());
		
		//Checking when a game is over when the user guesses all the correct words
		SingleGame game50 = new SingleGame("Test");
		assertTrue(game50.guessLetter('T'));
		assertFalse(game50.isGameOver());
		assertTrue(game50.guessLetter('e'));
		assertFalse(game50.isGameOver());
		assertTrue(game50.guessLetter('s'));
		assertTrue(game50.isGameOver());
		assertTrue(game50.getWinLossStatus());
		
	}

}
