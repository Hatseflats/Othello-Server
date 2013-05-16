package nl.mprog.apps.server;

import java.util.PriorityQueue;
import java.util.Queue;

public class PlayerQueue {

	Queue<Player> playerQueue;

	public PlayerQueue() {
		playerQueue = new PriorityQueue<Player>();
	}
	
	public Game attemptGameCreate(Player player){
		if(playerQueue.size() != 0){

			Player playerOne = playerQueue.poll();
			Player playerTwo = player;
			
			playerOne.setColorId(1);
			playerTwo.setColorId(2);
			
			String gameId = playerOne.getPlayerId() + playerTwo.getPlayerId();
			return new Game(gameId, playerOne, playerTwo);
		} else {
			playerQueue.add(player);
			return null;
		}
	}
	
	public void removePlayerFromQueue(String playerId, int connectionId){
		playerQueue.remove(new Player(playerId, connectionId));
	}
	
	

}
