package nl.mprog.apps.server;

import java.io.IOException;

import nl.mprog.apps.server.Network.Connect;
import nl.mprog.apps.server.Network.GameData;
import nl.mprog.apps.server.Network.Move;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class OthelloServer {
	
	Server server;
	PlayerQueue playerQueue;
	GameStorage games;
	
	public OthelloServer() throws IOException{
		
		playerQueue = new PlayerQueue();
		games = new GameStorage();
		server = new Server();
		server.start();
		server.bind(54555);
		
		System.out.println("Started server on TCP port 54555");
		
		Network.register(server);
		
		server.addListener(new Listener() {
			
			   public void received (Connection connection, Object object) {
				   
				  if (object instanceof Connect) {
					  
					  Connect data = (Connect) object;
					  
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
					  server.sendToTCP(game.getPlayerOne().getConnectionId(), p2);

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
			    	  
			    	  System.out.println("Send move for game with id: " + game.getGameId());
			      }
			      
			   }
		});
	}

	public static void main(String[] args) throws IOException {
		new OthelloServer();
	}

}
