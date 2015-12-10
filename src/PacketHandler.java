import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketHandler implements Runnable {

	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private ReadWriteLock lockArray;
	private int numAddresses;
	private AccessControl accessControl;
	private Fingerprint residue;
	private int numThreads;

	public PacketHandler(int numAddresses, AccessControl ac, int numThreads) {
		this.numThreads = numThreads;
		this.numAddresses = numAddresses;
		this.accessControl = ac;
		lockArray = new ReadWriteLock(this.numAddresses);
		residue = new Fingerprint();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

		}
	}

	// public void handlePacket(Packet p, CallbackFunction cf) {
	// PacketThread d = new PacketThread(p, cf);
	// threadPool.execute(d);
	// }

	private class PacketThread implements Runnable {
		private int queueIndex;
		
		private PacketThread(int queueIndex) {
			this.queueIndex = queueIndex;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				PacketCallbackBundle bundle = ConcurrentQueue.dequeue(this.queueIndex);
				Packet p = bundle.packet;
				CallbackFunction cf = bundle.cf;
				switch (p.type) {
				case ConfigPacket:
					Config config = p.config;
					lockArray.lockWrite(config.address);
					accessControl
							.setPNG(config.address, config.personaNonGrata);
					accessControl.setAcceptingSources(config.address,
							config.addressBegin, config.addressEnd,
							config.acceptingRange);
					lockArray.unlockWrite(config.address);
					break;
				case DataPacket:
					Header header = p.header;
					Body body = p.body;
					lockArray.lockRead(header.dest);
					// System.out.println(header.source);
					if (accessControl.isValidDataPacket(header.source,
							header.dest)) {
						long checksum = residue.getFingerprint(body.iterations,
								body.seed);
						HistogramGenerator.addFingerprintSighting(checksum);
					}
					lockArray.unlockRead(header.dest);
					break;
				}
				cf.operation();
			}
		}

	}

}
