package nl.mprog.apps.server;

public class Player {
	
	private final String playerId;
	private final int connectionId;
	private int colorId;
	
	public Player(String playerId, int connectionId) {
		this.playerId = playerId;
		this.connectionId = connectionId;
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public int getConnectionId() {
		return connectionId;
	}
	
	public int getColorId() {
		return colorId;
	}
	
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	 @Override
	 public boolean equals(Object o){
		 if(o instanceof Player){
			 Player p = (Player) o;
			 return playerId == p.playerId && connectionId == p.connectionId;
	    }
	    return false;
	 }
}
