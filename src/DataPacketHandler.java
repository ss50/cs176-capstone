import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataPacketHandler implements Runnable {
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private SyncHashMap syncHashMap;
	private PNGList pngList;
	private DList dlist;

	public DataPacketHandler() {
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
