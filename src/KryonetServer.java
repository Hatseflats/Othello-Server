import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class KryonetServer {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
		server.bind(54555, 54777);	
		
		Kryo kryo = server.getKryo();
		kryo.register(Request.class);
		kryo.register(Response.class);
		
		server.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof Request) {
			         Request request = (Request)object;
			         System.out.println(request.text);

			         Response response = new Response();
			         response.text = "Thanks!";
			         connection.sendTCP(response);
			      }
			   }
			});
	}

}
