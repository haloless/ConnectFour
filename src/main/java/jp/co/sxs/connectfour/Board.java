package jp.co.sxs.connectfour;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * 
 * @author homu
 *
 */
public class Board {

	private final int nrow;
	private final int ncol;
	
	private int[][] board;
	private int[] depth;
	
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
	
	public int at(int col, int row) {
		return board[col][row];
	}
	
	public boolean isColumnFull(int col) {
		return depth[col] >= nrow;
	}
	
	public boolean isBoardFull() {
		for (int col=0; col<ncol; col++) {
			if (!isColumnFull(col)) {
				return false;
			}
		}
		return true;
//		return Arrays.stream(depth).allMatch((d)->{
//			return d >= nrow;
//		});
	}
	
	/**
	 * Reset the board.
	 */
	public void reset() {
		final int nop = Player.NoPlayer.getId();
		for (int col=0; col<ncol; col++) {
			Arrays.fill(board[col], nop);
		}
		
		Arrays.fill(depth, 0);
	}
	
	/**
	 * 
	 * @param col
	 * @param player
	 * @return
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
	
	public boolean checkWinner(
			Player player, int nwin, int cpos, int rpos) 
	{
		if (countPlayer(player, nwin, cpos, rpos, 1, 0) >= nwin) {
			return true;
		}
		if (countPlayer(player, nwin, cpos, rpos, 0, 1) >= nwin) {
			return true;
		}
		if (countPlayer(player, nwin, cpos, rpos, 1, 1) >= nwin) {
			return true;
		}
		if (countPlayer(player, nwin, cpos, rpos, -1, 1) >= nwin) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @param nwin
	 * @param cpos
	 * @param rpos
	 * @return
	 */
	public boolean checkWinner1(
			Player player, int nwin, int cpos, int rpos) 
	{
		// TODO be smarter!!!
		
		final String swin = String.join("", Collections.nCopies(nwin, "P"));
		
		final int pid = player.getId();
		
		// check current row
		{
			final int row = rpos;
			StringBuilder sb = new StringBuilder(ncol);
			for (int col=0; col<ncol; col++) {
				sb.append(at(col,row)==pid ? 'P' : ' ');
			}
			if (sb.toString().contains(swin)) return true;
		}
		
		// check current column
		{
			final int col = cpos;
			StringBuilder sb = new StringBuilder(nrow);
			for (int row=0; row<nrow; row++) {
				sb.append(at(col, row)==pid ? 'P' : ' ');
			}
			if (sb.toString().contains(swin)) return true;
		}
		
		// check cross
		{
			String s = "";
			for (int i=0; i<nwin; i++) {
				int row = rpos + i;
				int col = cpos + i;
				if (0<=row && row<nrow && 0<=col && col<ncol) {
					s += at(col, row)==pid ? 'P' : ' ';
				}
			}
			for (int i=1; i<nwin; i++) {
				int row = rpos - i;
				int col = cpos - i;
				if (0<=row && row<nrow && 0<=col && col<ncol) {
					s = (at(col, row)==pid ? 'P' : ' ') + s;
				}
			}
			if (s.contains(swin)) return true;
		}
		{
			String s = "";
			for (int i=0; i<nwin; i++) {
				int row = rpos + i;
				int col = cpos - i;
				if (0<=row && row<nrow && 0<=col && col<ncol) {
					s += at(col, row)==pid ? 'P' : ' ';
				}
			}
			for (int i=1; i<nwin; i++) {
				int row = rpos - i;
				int col = cpos + i;
				if (0<=row && row<nrow && 0<=col && col<ncol) {
					s = (at(col, row)==pid ? 'P' : ' ') + s;
				}
			}
			if (s.contains(swin)) return true;
		}
		
		return false;
	}
	
	public int countPlayer(Player player, int nmax, 
			int cpos, int rpos, int cdir, int rdir) 
	{
		final int pid = player.getId();
		int n = 1;
		
		for (int i=1; i<nmax; i++) {
			int col = cpos + i*cdir;
			int row = rpos + i*rdir;
			if (0<=row && row<nrow && 0<=col && col<ncol) {
				if (at(col, row) == pid) {
					n += 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		
		for (int i=1; i<nmax; i++) {
			int col = cpos - i*cdir;
			int row = rpos - i*rdir;
			if (0<=row && row<nrow && 0<=col && col<ncol) {
				if (at(col, row) == pid) {
					n += 1;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		
		return n;
	}
	
	public int[] retrieveRow(int row) {
		int[] buf = new int[ncol];
		for (int col=0; col<ncol; col++) {
			buf[col] = board[col][row];
		}
		return buf;
	}
	
	public int[] retrieveCol(int col) {
		int[] buf = new int[nrow];
		System.arraycopy(board[col], 0, buf, 0, nrow);
		return buf;
	}
	
	
	
	
	
	public static class CTest {
	
		@Test
		public void test1() {
			
		}
	}
}
