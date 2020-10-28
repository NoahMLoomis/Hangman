package hangman;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import linked_data_structures.DoublyLinkedList;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ScoreBoard extends JPanel implements java.io.Serializable {
	private DoublyLinkedList<Player> players = new DoublyLinkedList();
	private JTable table;
	DefaultTableModel model;
	/**
	 * Create the panel.
	 */
	public ScoreBoard() {
		
		System.out.println("NEW SCOREBOARD GETTING CREATED");
		
		for (int i=0; i<players.getLength(); i++) {
			model.addRow(new Object [] {players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(), players.getElementAt(i).getTimesPlayed()});
		}
		
		initialize();
		
	}
	

	
	private Player getNextPlayer(int i) {
		Player currPlayer=null;
		try {
			currPlayer=players.getElementAt(i+1);
		}catch (Exception e) {
			System.out.println("Exception caught "+e );
		}
		return currPlayer;
	}
	

	private void sort() {
//		 TODO sort the players list
	}
	
	
	public Player gamePlayed(String playerName, boolean winOrLose) {
		
		if (winOrLose) {
			findPlayer(playerName).incWins();
			
		} else {
			findPlayer(playerName).incLosses();
		}
		
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
		    model.removeRow(i);
		}
		
		for (int i=0; i<players.getLength(); i++) {
			model.addRow(new Object [] {players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(), players.getElementAt(i).getTimesPlayed()});
		}
		
		return findPlayer(playerName);
	}
	
	/*findPlayer(String playerName) searches through the players DoublyLinkedList
	  		if the player is not named or null, is given the default name of Anonymous
	  		if the player already exists, do nothing
	  		if the player does not exist, create a new player and add to players DoublyLinkedList
			return the player
	 */
	
	public Player findPlayer(String playerName) {
		Player currPlayer=null;
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
			System.out.println("creating new player with the name " + playerName);
			currPlayer= new Player(playerName);
			players.add(currPlayer);
			model.addRow(new Object[] {currPlayer.getPlayerName(), currPlayer.getWins(), currPlayer.getLosses()});
		}
		
		return currPlayer;
		
	}
	public int getNumPlayers() {
		return players.getLength();
	}

	public DoublyLinkedList<Player> getPlayers(){
		return players;
	}
	
	public void initialize() {
		setLayout(null);
		
		setPreferredSize(new Dimension(500, 500));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 68, 350, 223);
		add(scrollPane);
		
		table = new JTable();
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Wins", "Total games played"
			}
		));
		
		scrollPane.setViewportView(table);
		model = (DefaultTableModel)table.getModel();
		
		players.add(new Player("Tristan"));
		for (int i=0; i<players.getLength(); i++) {
			model.addRow(new Object [] {players.getElementAt(i).getPlayerName(), players.getElementAt(i).getWins(), players.getElementAt(i).getTimesPlayed()});
		}
		
		JLabel lblNewLabel = new JLabel("ScoreBoard");
		lblNewLabel.setBounds(202, 21, 151, 37);
		add(lblNewLabel);
	}
}
