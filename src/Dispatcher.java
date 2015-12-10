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

	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done,
			PaddedPrimitiveNonVolatile<Integer> numInFlight,
			PaddedPrimitive<Boolean> memFence, AccessControl accessControl,
			int numAddressesLog, PacketGenerator gen, CallbackFunction cf) {
		this.done = done;
		// this.inFlight = numInFlight;
		this.memFence = memFence;
		this.numAddresses = (int) Math.pow(2, numAddressesLog);
		this.pktGen = gen;
		this.accessControl = accessControl;
		packetHandler = new PacketHandler(this.numAddresses, this.accessControl);
		this.cf = cf;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!done.value) {
			for (int i = 0; i < numAddresses; i++) {
				Packet packet = pktGen.getPacket();
				// only 256 packets can be in flight at once
				while (numInFlight.get() > 256 && !done.value) {
				}
				numInFlight.addAndGet(1);
				// decrements the number of packets in flight
				// CallbackFunction callbackFunc = () ->
				// {this.numInFlight.decrementAndGet(); return
				// numPacketsDistributed.incrementAndGet();};
				// inFlight.value++;
				packetHandler.handlePacket(packet, this.cf);
			}
		}
		// System.out.println("Number of packets dispatched: " +
		// numPacketsDistributed.get());
	}

}
