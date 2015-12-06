import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConfigPacketHandler implements Runnable{
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private SyncHashMap syncHashMap;
	private PNGList pngList;
	private DList dlist;
	private int numAddresses;
	
	public ConfigPacketHandler(int numAddresses) {
		this.numAddresses = numAddresses;
	}

	@Override
	public void run() {
		while(true){
			
		}
	}

	public void handlePacket(Packet p) {
		DataPacketThread d = new DataPacketThread(p);
		threadPool.execute(d);
	}
	
	
	private class DataPacketThread implements Runnable {
		private Packet p;
		
		private DataPacketThread(Packet p) {
			
		}
		
		@Override
		public void run() {
			
		}

	}

}
