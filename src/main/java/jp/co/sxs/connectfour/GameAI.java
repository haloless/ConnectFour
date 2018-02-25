package jp.co.sxs.connectfour;

/**
 * AI base class for a two-player self/rival game.
 *
 */
public abstract class GameAI implements IPlayerInput {

	protected ConnectFour c4;
	
	protected Player self;
	protected Player rival;
	
	public GameAI(ConnectFour c4, Player self, Player rival) {
		this.c4 = c4;
		this.self = self;
		this.rival = rival;
	}

	/**
	 * Implement this to find solution.
	 * @return
	 */
	protected abstract int solve();

	@Override
	public int chooseColumn() {
		return solve();
	}
	
	
}
