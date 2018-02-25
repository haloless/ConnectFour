package jp.co.sxs.connectfour;

/**
 * This is a very stupid AI implementation based on a greedy strategy.
 * <li> Find rival critical position and block
 * <li> Find self max gain position
 */
public class StupidAI extends GameAI {

	public StupidAI(ConnectFour c4, Player self, Player rival) {
		super(c4, self, rival);
	}

	/**
	 * Solve based on single-step, greedy mode
	 */
	@Override
	protected int solve() {
		final int nwin = c4.getNumToWin();
		final Board board = c4.getBoard();
		final int ncol = board.getNumCol();
		
		// block all rival wins
		for (int col=0; col<ncol; col++) {
			if (!board.isColumnFull(col)) {
				int depth = board.getDepth(col);
				
				boolean critical = board.checkWinner(rival, nwin, col, depth);
				if (critical) {
					return col;
				}
			}
		}
		
		// find self best choice
		double nummax = 0;
		int colmax = 0;
		for (int col=0; col<ncol; col++) {
			if (!board.isColumnFull(col)) {
				int depth = board.getDepth(col);
				
				// compute the max gain by placing in the current column
				double n = 0;
				n += board.countPlayerRow(self, nwin, col, depth);
				n += board.countPlayerCol(self, nwin, col, depth);
				n += board.countPlayerLeftCross(self, nwin, col, depth);
				n += board.countPlayerRightCross(self, nwin, col, depth);
				
				// choose the column with the max gain
				if (n > nummax) {
					nummax = n;
					colmax = col;
				}
			}
		}
		
		return colmax;
	}
}
