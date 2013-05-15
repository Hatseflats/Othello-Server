package nl.mprog.apps.server;

import java.util.HashMap;

public class GameStorage {
	
	HashMap<String, Game> games;

	public GameStorage() {
		games = new HashMap<String, Game>();
	}
	
	public void addGame(String key, Game game){
		games.put(key, game);
	}
	
	public int getEnemyConnection(String game_id, String player_id){
		Game game = games.get(game_id);
		
		if(game.player_one.player_id == player_id){
			return game.player_one.connection_id;
		} else {
			return game.player_two.connection_id;
		}
	}
	
	public Game getGameById(String game_id) {
		return games.get(game_id);
	}
}
