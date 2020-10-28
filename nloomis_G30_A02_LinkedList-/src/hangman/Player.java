package hangman;

public class Player implements java.io.Serializable{

	private String playerName;
	private int timesPlayed;
	private int wins;

	public Player() {
		playerName="";
		timesPlayed = 0;
		wins = 0;
	}

	public Player(String _playerName) {
		playerName = _playerName;
		timesPlayed = 0;
		wins = 0;
	}
	
	public void incWins(){
		this.timesPlayed++;
		this.wins++;
	}
	
	public void incLosses(){
		this.timesPlayed++;
	}


	public void setWins(int _wins) {
		wins = _wins;
	}

	public int getWins() {
		return wins;
	}

	public void setPlayerName(String _playerName) {
		playerName = _playerName;
	}

	public  String getPlayerName() {
		return playerName;
	}
	

	public void setTimesPlayed(int _timesPlayed) {
		timesPlayed = _timesPlayed;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}

	public int getLosses() {
		return timesPlayed - wins;
	}

}
