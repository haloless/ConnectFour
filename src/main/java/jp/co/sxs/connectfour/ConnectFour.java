package jp.co.sxs.connectfour;

import java.util.LinkedList;

/**
 * The Connect-Four game core.
 * It wraps {@link Board} class by providing standard configurations. 
 */
public class ConnectFour {

	/*
	 * static fields
	 */
	
	/**
	 * Used to record game steps.
	 *
	 */
	public static class Record {
		Player player;
		int col;
		int row;
		
		private Record(Player player, int col, int row) {
			this.player = player;
			this.col = col;
			this.row = row;
		}
	}
	
	// the game settings
	// TODO make them configurable?
	private static final int nrow = 6;
	private static final int ncol = 7;
	
	private static final int nwin = 4;
	
	
	/*
	 * fields
	 */
	
	protected Board board;
	
	protected LinkedList<Record> history;
	
	/**
	 * Create a default game.
	 */
	public ConnectFour() {
		
		this.board = new Board(nrow, ncol);
		
		this.history = new LinkedList<>();
	}
	
	public int getNumToWin() {
		return nwin;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public int getNumStep() {
		return history.size();
	}
	
	public Record getLastStep() {
		if (history.isEmpty()) {
			return null;
		} else {
			return history.getLast();
		}
	}
	
	/**
	 * Reset the game state. 
	 */
	public void reset() {
		
		board.reset();
		
		history.clear();
	}
	
	/**
	 * Insert a disk.
	 * The exact operation is delegated to the board.
	 * In addition, this class keeps a history if success. 
	 * 
	 * @param player
	 * @param col
	 * @return
	 */
	public boolean insertDisk(Player player, int col) {
		boolean ok = board.insertDisk(col, player);
		if (ok) {
			// record this step
			int row = board.getDepth(col)-1;
			
			Record record = new Record(player, col, row);
			history.add(record);			
		}
		return ok;
	}
	
	/**
	 * Check if the player win by the last step. 
	 * @return
	 */
	public boolean checkLastWin() {
		if (!history.isEmpty()) {
			Record last = history.getLast();
			return board.checkWinner(last.player, nwin, last.col, last.row);
		} else {
			return false;
		}
	}
	
	/**
	 * Check if the game is a draw. 
	 * @return
	 */
	public boolean checkDraw() {
		return board.isBoardFull();
	}
		
}
