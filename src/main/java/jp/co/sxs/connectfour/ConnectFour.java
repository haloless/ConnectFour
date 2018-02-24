package jp.co.sxs.connectfour;

import java.util.LinkedList;
import java.util.List;

public class ConnectFour {

	private static final int nrow = 6;
	private static final int ncol = 7;
	
	private static class Record {
		Player player;
		int col;
		int row;
		
		Record(Player player, int col, int row) {
			this.player = player;
			this.col = col;
			this.row = row;
		}
		
		
	}
	
	/**
	 * 
	 */
	private static final int nwin = 4;
	
	private Board board;
	
	private LinkedList<Record> records = new LinkedList<>();
	
	public ConnectFour() {
		
		this.board = new Board(nrow, ncol);
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	
	public void reset() {
		
		board.reset();
		
		records.clear();
	}
	
	/**
	 * 
	 * @param player
	 * @param col
	 * @return
	 */
	public boolean insertDisk(Player player, int col) {
		boolean ok = board.insertDisk(col, player);
		if (ok) {
			// record
			int row = board.getDepth(col)-1;
			
			Record record = new Record(player, col, row);
			records.add(record);			
		}
		return ok;
	}
	
	public boolean checkLastWin() {
		if (!records.isEmpty()) {
			Record last = records.getLast();
			return board.checkWinner(last.player, nwin, last.col, last.row);
		} else {
			return false;
		}
	}
	
	public boolean checkDraw() {
		return board.isBoardFull();
	}
	
}
