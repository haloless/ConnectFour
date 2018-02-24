package jp.co.sxs.connectfour;

import java.util.HashMap;
import java.util.Map;

public class Player {

	private static Map<Integer, Player> playerMap = new HashMap<>();
	
	public static final Player NoPlayer = new Player(0, "", " ");
	
	public static final Player Player1 = new Player(1, "Player 1", "RED");
	
	public static final Player Player2 = new Player(2, "Player 2", "GREEN");

	
	private int id;
	private String name;
	private String color;
	
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
	
	public String getColor() {
		return this.color;
	}
	
	public String getColorAbbr() {
		return this.color.substring(0, 1);
	}
	
	public static Player lookupPlayer(int id) {
		return playerMap.get(id);
	}
	
}
