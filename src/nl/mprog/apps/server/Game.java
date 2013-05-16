package nl.mprog.apps.server;

public class Game {
	
	private final String gameId;
	private final Player playerOne;
	private final Player playerTwo;
	
	public Game(String gameId, Player playerOne, Player playerTwo) {
		this.gameId = gameId;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public Player getPlayerOne() {
		return playerOne;
	}
	
	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	public Player returnOpponent(String playerId){
		if(playerOne.getPlayerId() == playerId)
			return playerTwo;
		else
			return playerOne;
	}
	
}
