package nl.mprog.apps.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameStorage {
	
	private Map<String, Game> games;

	public GameStorage() {
		games = new HashMap<String, Game>();
	}
	
	public void addGame(String key, Game game){
		games.put(key, game);
	}
	
	public Game getGameById(String gameId) {
		return games.get(gameId);
	}
	
	public Game getGameByPlayerId(String playerId){
		Set<String> keySet = games.keySet();
		
		for (String gameId : keySet) {
			if(gameId.contains(playerId)){ // found a game with this player
				return getGameById(gameId);
			}
		}
		
		return null;
	}
	
	public void removeGame(String gameId){
		games.remove(gameId);
	}
}
