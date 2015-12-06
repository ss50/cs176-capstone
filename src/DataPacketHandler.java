import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataPacketHandler implements Runnable {
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private SyncHashMap syncHashMap;
	private PNGList pngList;
	private DList dlist;
	private int numAddresses;

	public DataPacketHandler(int numAddresses) {
		this.numAddresses = numAddresses;
	}

	@Override
	public void run() {
		while (true) {
		}
	}

	public void handlePacket(Packet p) {

	}

	private class DataPacketThread implements Runnable {
		private Packet p;

		public DataPacketThread(Packet p) {

		}

		@Override
		public void run() {

		}

	}

}
