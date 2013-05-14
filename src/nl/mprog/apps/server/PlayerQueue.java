package nl.mprog.apps.server;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerQueue {

	Queue<Player> playerQueue;

	public PlayerQueue() {
		playerQueue = new LinkedList<Player>();
	}
	
	public Game attemptGameCreate(Player player){
		if(playerQueue.size() != 0){
			Game game = new Game();
			game.player_one = playerQueue.poll();
			game.player_two = player;
			game.game_id = game.player_one.player_id + game.player_two.player_id;
			
			return game;
		} else {
			playerQueue.add(player);
			
			return null;
		}
	}
	
	

}
