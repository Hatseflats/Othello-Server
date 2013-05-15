package nl.mprog.apps.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Move.class);
        kryo.register(Connect.class);
        kryo.register(Error.class);
        kryo.register(GameData.class);
    }

    static public class Move {
        public int x;
        public int y;

        public String gameId;
        public int colorId;

    }
    
    static public class Connect {
    	public String id;
    }
    
    static public class GameData {
    	public String gameId;
    	public int playerColor;
    }
    
    static public class Error {
    	public int error;
    	public String message;
    }
}
