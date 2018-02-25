package jp.co.sxs.connectfour;

import java.util.Arrays;

/**
 * The game board class.
 * The board uses players' ID to represent current state.
 * It provides functions to query and insert disks for a player in some column.
 * It can also count contiguous disks owned by a player.
 *  
 * Note that this class is somewhat parameterized:
 * the numbers of rows/columns, players, and disks to win are not fixed.
 * This is mainly for flexible usage in {@link ConnectFour}. 
 * 
 * <br>
 * TODO The data structure could be more optimized by directly caching rows/columns/crosses.
 * Some logics may be better moved to the game controller class?
 */
public class Board {

	/**
	 * number of rows
	 */
	private final int nrow;
	/**
	 * number of columns
	 */
	private final int ncol;
	
	/**
	 * the board indexed by (column,row)
	 */
	private int[][] board;
	/**
	 * height of each column
	 */
	private int[] depth;
	
	/**
	 * Create a board with rows and columns.
	 * @param row
	 * @param col
	 */
	public Board(int row, int col) {
		
		this.nrow = row;
		this.ncol = col;
		
		this.board = new int[ncol][nrow];
		this.depth = new int[ncol];
	}
	
	public int getNumRow() {
		return nrow;
	}
	
	public int getNumCol() {
		return ncol;
	}
	
	public int getDepth(int col) {
		return depth[col];
	}
	
	/**
	 * Get owner at (column,row).
	 * @param col
	 * @param row
	 * @return
	 */
	public int at(int col, int row) {
		return board[col][row];
	}
	
	public int getColumnTop(int col) {
		int row = depth[col]-1;
		return board[col][row];
	}
	
	public boolean isValidPosition(int col, int row) {
		return 0<=row && row<nrow && 0<=col && col<ncol;
	}
	
	public boolean isColumnEmpty(int col) {
		return depth[col] == 0;
	}
	
	/**
	 * Query if the column is all filled.
	 * @param col
	 * @return
	 */
	public boolean isColumnFull(int col) {
		return depth[col] >= nrow;
	}
	
	/**
	 * Query if the board is all filled.
	 * @return
	 */
	public boolean isBoardFull() {
		for (int col=0; col<ncol; col++) {
			if (!isColumnFull(col)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Reset the board.
	 * This is made by filling the board with {@link Player#NoPlayer}.
	 */
	public void reset() {
		final int nop = Player.NoPlayer.getId();
		for (int col=0; col<ncol; col++) {
			Arrays.fill(board[col], nop);
		}
		
		Arrays.fill(depth, 0);
	}
	
	/**
	 * Introduce a disk into column by player.
	 * @param col
	 * @param player
	 * @return True if succeed.
	 */
	public boolean insertDisk(int col, Player player) {
		// check out of range
		if (col<0 || col>=ncol) return false;
		
		if (isColumnFull(col)) {
			return false;
		} else {
			int d = depth[col];
			board[col][d] = player.getId();
			depth[col]++;
			return true;
		}
	}
	
	/**
	 * Check if players win the game by taking the position (column,row).
	 * Check the row, column and left/right diagonals. 
	 * 
	 * @param player
	 * @param nwin The number of contiguous disks to win.
	 * @param cpos The current column position.
	 * @param rpos The current row position.
	 * @return
	 */
	public boolean checkWinner(
			Player player, int nwin, int cpos, int rpos) 
	{
		if (countPlayerRow(player, nwin, cpos, rpos) >= nwin) {
			return true;
		}
		if (countPlayerCol(player, nwin, cpos, rpos) >= nwin) {
			return true;
		}
		if (countPlayerRightCross(player, nwin, cpos, rpos) >= nwin) {
			return true;
		}
		if (countPlayerLeftCross(player, nwin, cpos, rpos) >= nwin) {
			return true;
		}
		
		return false;
	}
	
	public int countPlayerRow(Player player, int nwin, int col, int row) {
		int colIncr = 1;
		int rowIncr = 0;
		return countPlayer(player, nwin, col, row, colIncr, rowIncr);
	}
	
	public int countPlayerCol(Player player, int nwin, int col, int row) {
		int colIncr = 0;
		int rowIncr = 1;
		return countPlayer(player, nwin, col, row, colIncr, rowIncr);
	}
	
	public int countPlayerLeftCross(Player player, int nwin, int col, int row) {
		int colIncr = -1;
		int rowIncr = 1;
		return countPlayer(player, nwin, col, row, colIncr, rowIncr);
	}
	
	public int countPlayerRightCross(Player player, int nwin, int col, int row) {
		int colIncr = 1;
		int rowIncr = 1;
		return countPlayer(player, nwin, col, row, colIncr, rowIncr);
	}
	
	/**
	 * The workhorse method to count contiguous disks.
	 * 
	 * Notice this returns at least one 
	 * because the current position is assumed to be owned by the player.
	 * 
	 * @param player
	 * @param nmax Max distance to count.
	 * @param cpos Center column position.
	 * @param rpos Center row position.
	 * @param cdir The scan increment along column.
	 * @param rdir The scan increment along row.
	 * @return
	 */
	protected int countPlayer(Player player, int nmax, 
			int cpos, int rpos, int cdir, int rdir) 
	{
		final int pid = player.getId();
		int n = 1;
		
		// count in positive direction
		for (int i=1; i<nmax; i++) {
			int col = cpos + i*cdir;
			int row = rpos + i*rdir;
			if (!isValidPosition(col, row)) break;
			
			if (at(col, row) == pid) {
				n += 1;
			} else {
				break;
			}
		}
		
		// count in negative direction
		for (int i=1; i<nmax; i++) {
			int col = cpos - i*cdir;
			int row = rpos - i*rdir;
			if (!isValidPosition(col, row)) break;

			if (at(col, row) == pid) {
				n += 1;
			} else {
				break;
			}
		}
		
		return n;
	}
}
