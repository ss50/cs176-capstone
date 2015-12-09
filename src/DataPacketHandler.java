import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataPacketHandler implements Runnable {
	private final ExecutorService threadPool = Executors.newSingleThreadExecutor();
//	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	private int numAddresses;
	private ReadWriteLock lockArray;
	private AccessControl accessControl;

	public DataPacketHandler(int numAddresses, AccessControl ac) {
		this.numAddresses = numAddresses;
		this.accessControl = ac;
		lockArray = new ReadWriteLock(this.numAddresses);
	}

	@Override
	public void run() {
		while (true) {
		}
	}

	public void handlePacket(Packet p, CallbackFunction cf) {
		DataPacketThread d = new DataPacketThread(p,cf);
		threadPool.execute(d);
	}

	private class DataPacketThread implements Runnable {
		private Packet p;
		private Fingerprint residue;
		private CallbackFunction cf;

		public DataPacketThread(Packet p, CallbackFunction cf) {
			residue = new Fingerprint();
			this.p = p;
			this.cf = cf;
		}

		@Override
		public void run() {
			Header header = p.header;
			Body body = p.body;
			lockArray.lockRead(header.dest);
			if (accessControl.isValidDataPacket(header.source, header.dest)) {
				long checksum = residue.getFingerprint(body.iterations, body.seed);
				HistogramGenerator.addFingerprintSighting(checksum);
			}
			lockArray.unlockRead(header.dest);
			cf.operation();	
					
		}

	}

}
