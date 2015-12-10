import java.util.concurrent.atomic.*;

public class Dispatcher implements Runnable {

	PaddedPrimitiveNonVolatile<Boolean> done;
	PaddedPrimitiveNonVolatile<Integer> inFlight;
	PaddedPrimitive<Boolean> memFence;
	private PacketGenerator pktGen;
	private int numAddresses;
	private PacketHandler packetHandler;
	private AccessControl accessControl;
	private AtomicInteger numInFlight = new AtomicInteger(0);
	private CallbackFunction cf;
	private int queueIndex;

	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done,
			PaddedPrimitiveNonVolatile<Integer> numInFlight,
			PaddedPrimitive<Boolean> memFence, AccessControl accessControl,
			int numAddressesLog, PacketGenerator gen, CallbackFunction cf,
			int queueIndex) {
		this.done = done;
		this.memFence = memFence;
		this.numAddresses = (int) Math.pow(2, numAddressesLog);
		this.pktGen = gen;
		this.accessControl = accessControl;
		packetHandler = new PacketHandler(this.numAddresses,
				this.accessControl, ParallelFirewall.NUM_HANDLER_THREADS, done);
		this.cf = cf;
		this.queueIndex = queueIndex;
	}

	@Override
	public void run() {
		while (!done.value) {
			// no need for numAddresses loop since multiple threads are running
			// for (int i = 0; i < numAddresses; i++) {
			Packet packet = pktGen.getPacket();
			// only 256 packets can be in flight at once
//			while (numInFlight.get() > 256 && !done.value) {
//			}
			numInFlight.addAndGet(1);
			PacketCallbackBundle p = new PacketCallbackBundle(this.cf, packet);
			while (!ConcurrentQueue.enqueue(this.queueIndex, p)) {
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
