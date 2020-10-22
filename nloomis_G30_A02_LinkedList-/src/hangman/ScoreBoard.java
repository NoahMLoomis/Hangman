package hangman;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import linked_data_structures.DoublyLinkedList;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ScoreBoard extends JPanel {
	private JTable table;
	private DoublyLinkedList<Player> players = new DoublyLinkedList();
	/**
	 * Create the panel.
	 */
	public ScoreBoard() {
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
		
//		System.out.println(Player when game finished);
//		System.out.println(Name  + findPlayer(playerName).getPlayerName());
//		System.out.println(Times Played  + findPlayer(playerName).getTimesPlayed());
//		System.out.println(Times Won  + findPlayer(playerName).getWins());
//		System.out.println(Times Won  + findPlayer(playerName).getLosses());

		return findPlayer(playerName);
	}
	
	
	
//	  findPlayer(String playerName) searches through the players DoublyLinkedList
//	  		if the player is not named or null, is given the default name of Anonymous
//	  		if the player already exists, do nothing
//	  		if the player does not exist, create a new player and add to players DoublyLinkedList
//	  return the player
	 

	public Player findPlayer(String playerName) {
		Player currPlayer=null;
		if ((playerName == null) || (playerName.equalsIgnoreCase(""))) {
			playerName = "Anonymous";
		}
		
		boolean playerFound = false;
		for (int i = 0; i < players.getLength(); i++) {
			if (players.getElementAt(i).getPlayerName().equalsIgnoreCase(playerName)) {
				playerFound = true;
				currPlayer = players.getElementAt(i);

			}
		}
		
		if (!playerFound) {
			currPlayer= new Player(playerName);
			players.add(currPlayer);
		}
		
		return currPlayer;
		
	}
	
	public int getNumPlayers() {
		return players.getLength();
	}

	public DoublyLinkedList<Player> getPlayers(){
		System.out.println("Printing all players");
		for (int i=0; i< players.getLength(); i++) {
			System.out.println(players.getElementAt(i).getPlayerName());
		}
		return players;
	}
	
	private void initialize() {
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
				"Name", "Wins", "Times Played"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("ScoreBoard");
		lblNewLabel.setBounds(202, 21, 151, 37);
		add(lblNewLabel);
	}
}
