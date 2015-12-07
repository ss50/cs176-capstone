import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataPacketHandler implements Runnable {
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private int numAddresses;
	private ReadWriteLock lockArray;
	private AccessControl accessControl;

	public DataPacketHandler(int numAddresses, AccessControl ac) {
		this.numAddresses = numAddresses;
		this.accessControl = ac;
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
		private Fingerprint residue;

		public DataPacketThread(Packet p) {
			residue = new Fingerprint();
			this.p = p;
		}

		@Override
		public void run() {
			Header header = p.header;
			Body body = p.body;
			if (!accessControl.getAddressPermission(header.source) /* and it is in the list of source address */) {
				long checksum = residue.getFingerprint(body.iterations, body.seed);
				HistogramGenerator.addFingerprintSighting(checksum);
			}
					
		}

	}

}
