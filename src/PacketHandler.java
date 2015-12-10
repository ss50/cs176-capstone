import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class PacketHandler /** implements Runnable */
{

	private final ExecutorService threadPool;
	private ReadWriteLock lockArray;
	private int numAddresses;
	private AccessControl accessControl;
	private Fingerprint residue;
	private int numThreads;
	private PacketThread[] packetThreads;
	private AtomicBoolean done;

	public PacketHandler(int numAddresses, AccessControl ac, int numThreads,
			AtomicBoolean done) {
		this.numThreads = numThreads;
		this.numAddresses = numAddresses;
		this.accessControl = ac;
		lockArray = new ReadWriteLock(this.numAddresses);
		residue = new Fingerprint();
		this.packetThreads = new PacketThread[numThreads];
		this.done = done;
		this.threadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < packetThreads.length; i++) {
			packetThreads[i] = new PacketThread(i);
			threadPool.execute(packetThreads[i]);
		}

	}

	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// while (done.value) {
	//
	// }
	// }

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
			while (!done.get()) {
				PacketCallbackBundle bundle = ConcurrentQueue
						.dequeue(this.queueIndex);
				if (bundle == null) {
					continue;
				}
				Packet p = bundle.packet;
				CallbackFunction cf = bundle.cf;
				switch (p.type) {
				case ConfigPacket:
					Config config = p.config;
					boolean locked = lockArray.tryLockWrite(config.address);
					if (locked) {
						accessControl.setPNG(config.address,
								config.personaNonGrata);
						accessControl.setAcceptingSources(config.address,
								config.addressBegin, config.addressEnd,
								config.acceptingRange);
						lockArray.unlockWrite(config.address);
					} else {
						returnToQueue(bundle);
					}

					break;
				case DataPacket:
					Header header = p.header;
					Body body = p.body;
					boolean isLocked = lockArray.tryLockRead(header.dest);
					if (isLocked) {
						// System.out.println(header.source);
						if (accessControl.isValidDataPacket(header.source,
								header.dest)) {
							long checksum = residue.getFingerprint(
									body.iterations, body.seed);
							HistogramGenerator.addFingerprintSighting(checksum);
						}
						lockArray.unlockRead(header.dest);
					} else{
						returnToQueue(bundle);
					}

					break;
				}
				cf.operation();
			}
		}

		private void returnToQueue(PacketCallbackBundle p) {
			while (!done.get() && !ConcurrentQueue.enqueue(this.queueIndex, p)) {
			}
		}

	}

}
