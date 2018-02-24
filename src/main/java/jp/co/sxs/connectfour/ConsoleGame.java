package jp.co.sxs.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleGame {

	protected PrintStream out = System.out;
	protected InputStream in = System.in;
	//protected Scanner sc;
	protected BufferedReader br;
	
	protected ConnectFour c4 = new ConnectFour();
	
	protected Player player1 = Player.Player1;
	protected Player player2 = Player.Player2;
	
	public void beginGame() {
		c4.reset();
		
		//sc = new Scanner(in);
		br = new BufferedReader(new InputStreamReader(in));
	}
	
	public void endGame() {
		
	}
	
	public void runGame() {
		
		renderBoard();
		
		while (true) {
			
			if (playerRound(player1)) {
				break;
			}
			
			if (playerRound(player2)) {
				break;
			}
		}
		
	}
	
	public void renderBoard() {
		Board board = c4.getBoard();
		final int nrow = board.getNumRow();
		final int ncol = board.getNumCol();
		
		for (int i=nrow-1; i>=0; i--) {
			for (int j=0; j<ncol; j++) {
				int pid = board.at(j, i);
				Player p = Player.lookupPlayer(pid);
				
				out.print('|');
				out.print(p.getColorAbbr());
				if (j == ncol-1) {
					out.print('|');
				}
			}
			out.println();
		}
		out.println();
	}
	
	protected int playerInput(Player player) throws NumberFormatException {
		int col = -1;
		try {
			col = Integer.parseInt(br.readLine()) - 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return col;
	}
	
	protected boolean playerRound(Player player) {
		
		final Board board = c4.getBoard();
		
		// 
		String prompt = String.format("%s [%s] - choose column (1-%d): ", 
				player.getName(), player.getColor(), board.getNumCol());
		
		while (true) {
			out.println(prompt);
			
			// 
			try {
				int col = playerInput(player);
				
				if (c4.insertDisk(player, col)) {
					// success
					break;
				} else {
					out.println("You cannot insert disk here!");
				}
			} catch (NumberFormatException e) {
				out.println("Please enter a number!");
			}
		}
		
		renderBoard();
		
		if (c4.checkLastWin()) {
			String msg = String.format("%s [%s] wins!", 
					player.getName(), player.getColor());
			out.println(msg);
			return true;
		} 
		
		if (c4.checkDraw()) {
			out.println("Draw!");
			return true;
		}
		
		return false;
	}
	
	
	public static void main(String[] args) {
		ConsoleGame game = new ConsoleGame();
		
		game.beginGame();
		
		game.runGame();
	
		game.endGame();
	}
}
