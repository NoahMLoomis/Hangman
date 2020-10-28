package hangman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HangmanFrame implements ActionListener, WindowListener {

	private JFrame frame = new JFrame();
	private ArrayList<JButton> letterButtons = new ArrayList<>();
	private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private HangmanGame game = new HangmanGame();

	private JPanel letterpanel = new JPanel();

	private JMenuItem rulesMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem scoreboardMenuItem;
	private JMenuItem quitMenuItem;
	private JMenuItem hintMenuItem;
	private JMenuItem newgameMenuItem;

	private JLabel lblOr = new JLabel();
	private JButton btnPlay = new JButton("Play");
	private JTextField namefld = new JTextField();
	private JComboBox cmbxPrevPlayers = new JComboBox();

	private JLabel lblTitle = new JLabel();

//	private ImageIcon[] imgArr = new ImageIcon[7];

	private ImageIcon standImg = new ImageIcon("images/stand.png");

	private ImageIcon headImg = new ImageIcon("images/head.png");
	private ImageIcon bodyImg = new ImageIcon("images/body.png");
	private ImageIcon arm1Img = new ImageIcon("images/arm1.png");
	private ImageIcon arm2Img = new ImageIcon("images/arm2.png");
	private ImageIcon leg1Img = new ImageIcon("images/leg1.png");
	private ImageIcon leg2Img = new ImageIcon("images/leg2.png");

	private ImageIcon[] imgArr = { leg2Img, leg1Img, arm2Img, arm1Img, bodyImg, headImg, standImg };

	private JLabel lblNumGuesses = new JLabel();
//	private SingleGame newGame;
	private JTextField fldHidden = new JTextField();
	private JLabel stand = new JLabel();
	private JLabel lblName = new JLabel();
	private String name = "";
	JButton btnResume = new JButton("Resume last game");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					HangmanFrame window = new HangmanFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * TODO: 
	 * 	-Do adding to scoreboard when a player starts a game
	 *  -Do adding to scoreboard when a player ends a game
	 *  -Do "Previous player" stuff
	 *  -save() and Resume(), real ugly, there's gotta be a better way
	 *  -Able to resume game from a specific player, if none specified, resume last saved game
	 *  -Garbage Collection
	 *  -Final review
	 */
	public HangmanFrame() {
		for (char i : alphabet) {
			JButton btni = new JButton(i + "");
			btni.addActionListener(this);
			letterButtons.add(btni);
			letterpanel.add(btni);
		}
		initialize();
	}

	private boolean valName() {
		if (namefld.getText().equalsIgnoreCase("") && (cmbxPrevPlayers.getSelectedIndex() == 0)) {
			return false;
		}

		if (cmbxPrevPlayers.getSelectedIndex() != 0) {
			name = cmbxPrevPlayers.getSelectedItem().toString();
		} else {
			name = namefld.getText();
		}
		return true;

	}

	private void startGame() {
//		game.board.addToScoreboard(name);
		
		game.board.findPlayer(name);
//		fileMenu.setEnabled(true);

		for (JButton btn : letterButtons) {
			btn.setEnabled(true);
			btn.setBackground(new Color(69, 153, 64)); // Reseting all the buttons to be enabled and the right color
			for (int i = 0; i < game.getCurrGame().getGuessedLetters().getLength(); i++) {
				if (btn.getText().equalsIgnoreCase(game.getCurrGame().getGuessedLetters().getElementAt(i) + "")) {
					btn.setBackground(new Color(255, 255, 255));
					btn.setEnabled(false);
				}
			}
		}

		fldHidden.setText(game.getCurrGame().toString());
		lblNumGuesses.setText(game.getCurrGame().getGuessesLeft() + "");
		stand.setIcon(imgArr[game.getCurrGame().getGuessesLeft()]);

		btnResume.setEnabled(false);
		btnPlay.setEnabled(false);
		namefld.setEnabled(false);
		cmbxPrevPlayers.setEnabled(false);
		fldHidden.setText(game.getCurrGame().toString());

	}

	private void initialize() {

		frame.getContentPane().setBackground(new Color(149, 64, 153));
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 837, 660);
		// Bounds on reset
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Hangman");

		letterpanel.setBackground(new Color(149, 64, 153));
		letterpanel.setBounds(366, 168, 300, 291);
		frame.getContentPane().add(letterpanel);
		letterpanel.setLayout(new GridLayout(0, 2, 0, 0));

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		rulesMenuItem = new JMenuItem("Rules");
		saveMenuItem = new JMenuItem("Save game");
		scoreboardMenuItem = new JMenuItem("Scoreboard");
		quitMenuItem = new JMenuItem("Quit Game");
		hintMenuItem = new JMenuItem("Hint");
		newgameMenuItem = new JMenuItem("New Game");

		frame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.setEnabled(true);
		fileMenu.add(saveMenuItem);
		fileMenu.add(scoreboardMenuItem);
		fileMenu.add(quitMenuItem);
		fileMenu.add(hintMenuItem);
		fileMenu.add(newgameMenuItem);
		fileMenu.add(rulesMenuItem);

		saveMenuItem.addActionListener(this);
		scoreboardMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);
		hintMenuItem.addActionListener(this);
		newgameMenuItem.addActionListener(this);
		rulesMenuItem.addActionListener(this);

		for (JButton btn : letterButtons) {
			btn.setEnabled(false);
			btn.setForeground(new Color(109, 232, 2));
			btn.setBackground(new Color(69, 153, 64));
			btn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}

//		namefld = new JTextField();
		namefld.setEnabled(true);
		namefld.setText("");
		namefld.setBounds(172, 63, 201, 19);
		frame.getContentPane().add(namefld);
		namefld.setColumns(10);

//		comboBox = new JComboBox();
		cmbxPrevPlayers.setEnabled(true);
		cmbxPrevPlayers.setModel(new DefaultComboBoxModel(new String[] { "Previous Players" }));

		for (int i=0; i< game.board.getNumPlayers(); i++) {
			cmbxPrevPlayers.addItem(game.board.getPlayers().getElementAt(i).getPlayerName());
		}

		cmbxPrevPlayers.setBounds(494, 62, 147, 21);
		frame.getContentPane().add(cmbxPrevPlayers);
		lblOr.setText("OR");

//		lblNewLabel = new JLabel("OR");
		lblOr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOr.setForeground(new Color(255, 255, 255));
		lblOr.setBounds(421, 66, 28, 13);
		frame.getContentPane().add(lblOr);

		btnPlay.setForeground(new Color(102, 51, 102));
		btnPlay.setBackground(new Color(51, 153, 255));
		btnPlay.setEnabled(true);
		btnPlay.addActionListener(this);
		btnPlay.setBounds(337, 112, 85, 21);
		frame.getContentPane().add(btnPlay);

//		hiddenfld = new JTextField();
		fldHidden.setHorizontalAlignment(SwingConstants.CENTER);
		fldHidden.setText("");
		fldHidden.setEditable(false);
		fldHidden.setFont(new Font("Tahoma", Font.PLAIN, 18));
		fldHidden.setBounds(104, 481, 201, 59);
		frame.getContentPane().add(fldHidden);
		fldHidden.setColumns(10);

		JLabel lblGuessesLeft = new JLabel("Guesses Left:");
		lblGuessesLeft.setForeground(new Color(255, 255, 255));
		lblGuessesLeft.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGuessesLeft.setBounds(376, 478, 108, 31);
		frame.getContentPane().add(lblGuessesLeft);

//		stand = new JLabel("");
		stand.setIcon(standImg);
		stand.setBounds(84, 168, 272, 291);
		frame.getContentPane().add(stand);

//		lblNumGuesses = new JLabel("6");
		lblNumGuesses.setBounds(494, 484, 53, 19);
		frame.getContentPane().add(lblNumGuesses);
		lblNumGuesses.setForeground(new Color(255, 255, 255));
		lblNumGuesses.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNumGuesses.setText("6");
//		lblTitle = new JLabel("Hangman");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(366, 10, 118, 37);
		lblTitle.setForeground(new Color(255, 255, 255));
		frame.getContentPane().add(lblTitle);
		lblName.setText("Enter Name:");

//		lblName = new JLabel("Enter name:");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(54, 66, 108, 13);
		frame.getContentPane().add(lblName);

//		JButton btnResume = new JButton("Resume last game");
		btnResume.addActionListener(this);
		btnResume.setForeground(new Color(102, 51, 102));
		btnResume.setEnabled(true);
		btnResume.setBackground(new Color(51, 153, 255));
		btnResume.setBounds(432, 112, 160, 21);
		frame.getContentPane().add(btnResume);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO VErify that game isn't over

		if (e.getSource() == btnPlay) {
			if (valName()) {
				game.resetSingleGame();
				startGame();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter a name or select one from the dropdown");
			}
		} else if (e.getSource() == btnResume) {
			resume_ActionPerformed();
		} else if (e.getSource() == scoreboardMenuItem) {
			scoreboard_ActionPerformed();
		} else if (e.getSource() == hintMenuItem) {
			hint_ActionPerformed();
		} else if (e.getSource() == rulesMenuItem) {
			rules_ActionPerformed();
		} else if (e.getSource() == saveMenuItem) {
			save_ActionPerformed();
		} else if (e.getSource() == quitMenuItem) {
			quit_ActionPerformed();
		} else if (e.getSource() == newgameMenuItem) {
			newgame_ActionPerformed();
		}

		for (JButton btn : letterButtons) {
			if (e.getSource() == btn) {
				btn.setBackground(new Color(255, 255, 255));
				btn.setEnabled(false);
				if (game.getCurrGame().guessLetter(btn.getText().charAt(0))) {
					fldHidden.setText(game.getCurrGame().toString());
				} else {
					lblNumGuesses.setText(game.getCurrGame().getGuessesLeft() + "");
					stand.setIcon(imgArr[game.getCurrGame().getGuessesLeft()]);

				}
			}
		}
		
		
		if (game.getCurrGame().isGameOver()) {
			if (game.getCurrGame().getWinLossStatus()) {
				JOptionPane.showMessageDialog(null, "You won!!\n" + game.getCurrGame().getWord());
				game.board.gamePlayed(name, true);
			} else {
				JOptionPane.showMessageDialog(null, "You lost!!\n" + game.getCurrGame().getWord());
				game.board.gamePlayed(name, false);
			}

			game.resetSingleGame();
			startGame();

		}
	}

	private void newgame_ActionPerformed() {
		save_ActionPerformed();
		frame.dispose();
		HangmanFrame newFrame = new HangmanFrame();
		newFrame.frame.setVisible(true);

	}

	private void resume_ActionPerformed() {
		game =  game.resume();
		if (game == null) {
			game.resetSingleGame();
		}
		startGame();
	}

	private void quit_ActionPerformed() {
		save_ActionPerformed();
		System.exit(1);
	}

	private void save_ActionPerformed() {
		game.saveGame();
//		game.getCurrGame().save();

	}

	private void rules_ActionPerformed() {
		JOptionPane.showMessageDialog(null, "insert RULES!");

	}

	private void hint_ActionPerformed() {
		game.getCurrGame().hint();
		// This seems massively inefficient,but i'm not sure how else to do this without
		// returning something in the hint()
		// or guessLetter()
		for (JButton btn : letterButtons) {
			for (int i = 0; i < game.getCurrGame().getGuessedLetters().getLength(); i++) {
				if (btn.getText().equalsIgnoreCase(game.getCurrGame().getGuessedLetters().getElementAt(i) + "")) {
					btn.setBackground(new Color(255, 255, 255));
					btn.setEnabled(false);
				}
			}
		}
		fldHidden.setText(game.getCurrGame().toString());

	}

	private void scoreboard_ActionPerformed() {
		JOptionPane.showMessageDialog(null, game.board, "Scoreboard", JOptionPane.PLAIN_MESSAGE);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
