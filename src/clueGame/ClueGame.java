package clueGame;

//TODO: remove unused imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame{

	private static Board board;
	private ClueGame game;
	private static JFrame frame;
	
	public ClueGame() {
		frame = new JFrame("Clue Game");	
		frame.setExtendedState(MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JOptionPane.showMessageDialog(null, "You are Miss Scarlett. Can you find the solution before"
				+ " the computer players?");
		
		board = Board.getInstance();
			
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
		frame.addMouseListener(board);
		frame.add(board, BorderLayout.CENTER);
		
		//Right pannel, shows player's hand and seen cards
		CardPanel cardPanel = new CardPanel(board.getPlayers().get(0));
		frame.add(cardPanel, BorderLayout.EAST);

		//Bottom pannel, controls to move game along
		GameControlPanel gcp = new GameControlPanel(board, cardPanel);
		frame.add(gcp, BorderLayout.SOUTH);
		
		//Setup for first turn by player
		int roll = gcp.roll();
		gcp.setTurn(board.getCurrentPlayer(), roll);
		gcp.setGuess("");
		gcp.setGuessResult("");	
		BoardCell currentCell = board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn());
		board.calcTargets(currentCell, roll);
		board.flagTargets();
		frame.setVisible(true);
		board.repaint();
		JOptionPane.showMessageDialog(null, "You rolled a " + roll);
	}
	
	public static void updateCardPanel() {
		CardPanel cardPanel = new CardPanel(board.getPlayers().get(0));
		frame.add(cardPanel, BorderLayout.EAST);
		cardPanel.repaint();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
	//TODO FIX ENDING, REPLAY GAME?
	//TODO Color coded cards from players
	//TODO better color choices, looks brash right now
	//TODO fix board background

	//TODO Impliment AI?
	
}
