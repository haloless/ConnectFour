package jp.co.sxs.connectfour;

/**
 * Represents a source of player input.
 * For the Connect-Four game, the only operation is to choose the column.
 *
 */
public interface IPlayerInput {

	/**
	 * Choose the column to insert disk
	 * @return
	 */
	int chooseColumn();
	
}
