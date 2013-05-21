package nl.mprog.apps.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryonet.Listener;

import nl.mprog.apps.server.Network.Connect;
import nl.mprog.apps.server.Network.Error;
import nl.mprog.apps.server.Network.GameData;
import nl.mprog.apps.server.Network.Move;

import java.io.IOException;

public class OthelloServer {
	
	Server server;
	PlayerQueue playerQueue;
	GameStorage games;
	
	public OthelloServer() throws IOException {
		
		Log.set(Log.LEVEL_TRACE);
		
		playerQueue = new PlayerQueue();
		games = new GameStorage();
		
		// Use our own connection implementation so we can store the unique android device id as player id.
		server = new Server(){
			protected Connection newConnection () {
				return new OthelloConnection();
			}
		};
		
		server.start();
		server.bind(Network.PORT);

		Network.register(server);
		
		server.addListener(new Listener() {
			
			   public void received (Connection c, Object object) {

		          OthelloConnection connection = (OthelloConnection) c;
				   
				  if (object instanceof Connect) {
					  
					  Connect data = (Connect) object;

                      System.out.println("WHERE THE FUCK DID THE ID GO");

                      if(data.id == null) return; // invalid ID
					  
					  connection.playerId = data.id; // store the players id in the connection
					  
					  Player player = new Player(data.id, connection.getID());

					  System.out.println("A player with android device ID " + data.id + " and IP " + connection.getRemoteAddressTCP() + " connected.");

					  Game game = playerQueue.attemptGameCreate(player);
					  
					  if(game == null) return;
					  
					  System.out.println("Created game with game_id: " + game.getGameId());
					  	  
					  games.addGame(game.getGameId(), game);
					  
					  GameData p1 = new GameData();
					  GameData p2 = new GameData();
					  
					  p1.gameId = game.getGameId();
					  p2.gameId = game.getGameId();
					  
					  p1.playerColor = 1;
					  p2.playerColor = 2;
					  
					  server.sendToTCP(game.getPlayerOne().getConnectionId(), p1);
					  server.sendToTCP(game.getPlayerTwo().getConnectionId(), p2);

					  return;
				  }
			      if (object instanceof Move) {
			    	  Move move = (Move) object;
			    	  			    	  
			    	  Game game = games.getGameById(move.gameId);
			    	  			    	  
			    	  int connectionId;
			    	  
			    	  if(game.getPlayerOne().getColorId() == move.colorId){
			    		  connectionId = game.getPlayerTwo().getConnectionId();
			    	  } else {
			    		  connectionId = game.getPlayerOne().getConnectionId();
			    	  }
			    	  
			    	  server.sendToTCP(connectionId, move);
			    	  
			    	  System.out.println("Recieved move for game with id: " + game.getGameId() + " from player " + move.colorId);
			      }
			   }
			   public void disconnected(Connection c) {
			          OthelloConnection connection = (OthelloConnection) c;
			          
				      System.out.println("Player with id " + connection.playerId + " Has disconnected");
				      
				      playerQueue.removePlayerFromQueue(connection.playerId, connection.getID()); // attempt to remove player from queue
				
				      Game game = games.getGameByPlayerId(connection.playerId); // attempt to find games in progress by this player
				      
				      if(game == null) return; // no game found!
				      
				      // if theres a game in progress, send the disconnected players opponent a message that the game is over.
				      Error opponentDisconnected = new Error();
				      opponentDisconnected.message = "Your opponent has left the game.";
				      opponentDisconnected.error = 0;
				      
				      server.sendToTCP(game.returnOpponent(connection.playerId).getConnectionId(), opponentDisconnected);
			   }
		});
	}

	public static void main(String[] args) throws IOException {
		new OthelloServer();
	}
	
	static class OthelloConnection extends Connection {
		public String playerId;
	}

}
