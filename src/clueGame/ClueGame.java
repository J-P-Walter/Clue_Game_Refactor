package clueGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
* *************************************************************************************************************************************************
* Starts up the entire game. Creates the overall JFrame and puts in the control panel and card panel. Creates the board and initialzes it.
* The board can be swapped out by changing the csv file and the setup file. Should throw errors if there are any but don't bet on it. 
* *************************************************************************************************************************************************
*/
public class ClueGame extends JFrame{

	private static Board board;
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
	
	//Not really sure why this is here...
	public static void updateCardPanel() {
		CardPanel cardPanel = new CardPanel(board.getPlayers().get(0));
		frame.add(cardPanel, BorderLayout.EAST);
		cardPanel.repaint();
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}
}
