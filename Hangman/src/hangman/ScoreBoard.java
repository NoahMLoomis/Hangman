package hangman;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import linked_data_structures.DLNode;
import linked_data_structures.DoublyLinkedList;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ScoreBoard extends JPanel implements java.io.Serializable {
	private DoublyLinkedList<Player> players;
	private JTable table;
	DefaultTableModel model;

	/**
	 * Create the panel.
	 */

	/*
	 * create a new list of players initialize the frame, sort the scoreboard
	 * populate the frame of the scoreboard with any existing players
	 */

	public ScoreBoard() {
		players = new DoublyLinkedList<>();
		initialize();
		sort();
		for (int i = 0; i < players.getLength(); i++) {
			model.addRow(new Object[] { players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(),
					players.getElementAt(i).getTimesPlayed() });
		}
	}

	private void sort() {
		for (int i = 0; i < players.getLength(); i++) {
			for (int j = 1; j < players.getLength(); j++) {

				if (players.getElementAt(i).getPlayerName().charAt(0) < players.getElementAt(j).getPlayerName()
						.charAt(0)) {
					Player temp = players.getElementAt(i);
					players.remove(i);
					players.add(temp, j);
				} else if (players.getElementAt(i).getPlayerName().charAt(0) > players.getElementAt(j).getPlayerName()
						.charAt(0)) {
					Player temp = players.getElementAt(j);
					players.remove(j);
					players.add(temp, i);
				}
			}
		}
	}
	
	/*
	 * If the game is won, find the player and incWins
	 * else find the player and incLosses
	 * Delete the current players from the frame
	 * add all the players to the frame 
	 */
	
	public void gamePlayed(String playerName, boolean winOrLose) {

		if (winOrLose) {
			findPlayer(playerName).incWins();

		} else {
			findPlayer(playerName).incLosses();
		}

		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		sort();
		for (int i = 0; i < players.getLength(); i++) {
			model.addRow(new Object[] { players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(),
					players.getElementAt(i).getTimesPlayed() });
		}

	}

	/*
	 * findPlayer(String playerName) searches through the players DoublyLinkedList
	 * if the player is not named or null, is given the default name of Anonymous if
	 * the player already exists, do nothing if the player does not exist, create a
	 * new player and add to players DoublyLinkedList return the player
	 */

	public Player findPlayer(String playerName) {
		Player currPlayer = null;
		if ((playerName == null) || (playerName.equalsIgnoreCase(""))) {
			playerName = "Anonymous";
		}

		boolean playerFound = false;
		for (int i = 0; i < players.getLength(); i++) {
			if (players.getElementAt(i).getPlayerName().equalsIgnoreCase(playerName)) {
				System.out.println("Player has been found " + playerName);
				playerFound = true;
				currPlayer = players.getElementAt(i);

			}
		}

		if (!playerFound) {
			currPlayer = new Player(playerName);
			players.add(currPlayer);
			sort();
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			for (int i = 0; i < players.getLength(); i++) {
				model.addRow(new Object[] { players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(),
						players.getElementAt(i).getTimesPlayed() });
			}
		}
		return currPlayer;
	}

	public int getNumPlayers() {
		return players.getLength();
	}

	public DoublyLinkedList<Player> getPlayers() {
		return players;
	}
	
	/*
	 * Initialize frame, add any players to the frame.
	 */

	private void initialize() {
		setLayout(null);

		setPreferredSize(new Dimension(500, 500));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 68, 350, 223);
		add(scrollPane);

		table = new JTable();

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Wins", "Total games played" }));

		scrollPane.setViewportView(table);
		model = (DefaultTableModel) table.getModel();
		sort();
		for (int i = 0; i < players.getLength(); i++) {
			model.addRow(new Object[] { players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(),
					players.getElementAt(i).getTimesPlayed() });
		}

		JLabel lblNewLabel = new JLabel("ScoreBoard");
		lblNewLabel.setBounds(202, 21, 151, 37);
		add(lblNewLabel);
	}
}
