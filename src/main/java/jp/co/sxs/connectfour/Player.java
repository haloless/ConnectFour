package jp.co.sxs.connectfour;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a game player.
 * A player should have a unique ID and a name.
 * The ID will be used as placeholder in a board game.
 * The player also has a color field.
 *
 */
public class Player {
	
	/*
	 * static fields
	 */
	
	/**
	 * Used to keep registered players.
	 */
	private static Map<Integer, Player> playerMap = new HashMap<>();
	
	/**
	 * Represents an empty position.
	 */
	public static final Player NoPlayer = new Player(0, "", " ");
	
	public static final Player Player1 = new Player(1, "Player 1", "RED");
	
	public static final Player Player2 = new Player(2, "Player 2", "GREEN");

	/**
	 * Lookup player by ID.
	 * @param id
	 * @return
	 */
	public static Player lookupPlayer(int id) {
		return playerMap.get(id);
	}
	

	
	/*
	 * fields
	 */
	
	private int id;
	private String name;
	private String color;
	
	/**
	 * Currently, we do not allow external creation of players...
	 * 
	 * @param id
	 * @param name
	 * @param color
	 */
	private Player(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
		
		// register in player map
		playerMap.put(id, this);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColorAbbr() {
		return this.color.substring(0, 1);
	}
	
}
