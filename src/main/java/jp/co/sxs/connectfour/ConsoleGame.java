package jp.co.sxs.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Console version of Connect-Four.
 * Implements the console loop control. 
 */
public class ConsoleGame {

	/*
	 * static field
	 */
	
	/**
	 * Wrap human input from console
	 */
	private class ConsolePlayerInput implements IPlayerInput {
		@Override
		public int chooseColumn() {
			
			BufferedReader br = ConsoleGame.this.br;
			
			int col = -1;
			try {
				col = Integer.parseInt(br.readLine()) - 1;
			} catch (IOException e) {
				System.exit(1); // TODO error handle
			}
			return col;
		}
	}
	
	/**
	 * Wrap robot-like input (decoration pattern)
	 * Try to echo to the console as well.
	 */
	private class ConsoleRobotInput implements IPlayerInput {
		
		IPlayerInput input;
		
		private ConsoleRobotInput(IPlayerInput input) {
			this.input = input;
		}
		
		@Override
		public int chooseColumn() {
			int col = input.chooseColumn();
			// echo to console
			out.println(col+1);
			return col;
		}
		
	}
	
	/*
	 * class field
	 */
	
	private PrintStream out = System.out;
	private InputStream in = System.in;
	private BufferedReader br;
	
	private ConnectFour c4;
	
	private Player player1 = Player.Player1;
	private Player player2 = Player.Player2;
	
	private IPlayerInput input1;
	private IPlayerInput input2;
	
	public ConsoleGame() {
		// create game core
		c4 = new ConnectFour();
		
		// default inputs from console
		input1 = new ConsolePlayerInput();
		input2 = new ConsolePlayerInput();
	}
	
	public ConnectFour getGameCore() {
		return this.c4;
	}
	
	/**
	 * Prepare before entering main loop
	 */
	public void init() {
		// bind input stream
		br = new BufferedReader(new InputStreamReader(in));		
	}
	
	/**
	 * The main loop
	 */
	public void loop() {
		while (true) {
			beginGame();
			
			runGame();
			
			endGame();
			
			if (!restartGame()) break;
		}
	}
	
	protected void beginGame() {
		c4.reset();
	}
	
	protected void endGame() {
		// nothing at present
	}

	/**
	 * a single game pass
	 */
	protected void runGame() {
		
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
	
	/**
	 * If restart a new game
	 * @return
	 */
	protected boolean restartGame() {
		out.println("Restart? (q to quit)");
		try {
			if (br.readLine().equalsIgnoreCase("q")) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Render the game board on the console.
	 */
	protected void renderBoard() {
		final Board board = c4.getBoard();
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
		if (player.getId() == player1.getId()) {
			return input1.chooseColumn();
		} else {
			return input2.chooseColumn();
		}
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
	
	public void setPlayer1UseAI() {
		this.input1 = new ConsoleRobotInput(new StupidAI(c4, player1, player2));
	}
	
	public void setPlayer2UseAI() {
		this.input2 = new ConsoleRobotInput(new StupidAI(c4, player2, player1));
	}
	
	/**
	 * For test only
	 * @param out
	 */
	void setOutput(PrintStream out) {
		this.out = out;
	}

	void setInput(InputStream in) {
		this.in = in;
	}
	
	
	/**
	 * Console program entry point.
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConsoleGame game = new ConsoleGame();
		
		for (int i=0; i<args.length; i++) {
			if (args[i].equals("-p1=ai")) {
				game.setPlayer1UseAI();
			} else if (args[i].equals("-p2=ai")) {
				game.setPlayer2UseAI();
			}
		}
		
		game.init();
		game.loop();
		
	}
}
