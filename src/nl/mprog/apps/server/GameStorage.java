package nl.mprog.apps.server;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {
	
	private Map<String, Game> games;

	public GameStorage() {
		games = new HashMap<String, Game>();
	}
	
	public void addGame(String key, Game game){
		games.put(key, game);
	}
	
	public int getEnemyConnection(String game_id, String player_id){
		Game game = (Game) games.get(game_id);
		
		if(game.getPlayerOne().getPlayerId() == player_id){
			return game.getPlayerOne().getConnectionId();
		} else {
			return game.getPlayerTwo().getConnectionId();
		}
	}
}
