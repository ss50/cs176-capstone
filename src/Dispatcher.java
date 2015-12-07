import java.util.concurrent.atomic.*;

public class Dispatcher implements Runnable {

	PaddedPrimitiveNonVolatile<Boolean> done;
	PaddedPrimitiveNonVolatile<Integer> inFlight;
	PaddedPrimitive<Boolean> memFence;
	private PacketGenerator pktGen;
	private int numAddresses;
	private ConfigPacketHandler configHandler;
	private DataPacketHandler dataHandler;
	private AtomicInteger numInFlight = new AtomicInteger(0);

	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done,
			PaddedPrimitiveNonVolatile<Integer> numInFlight,
			PaddedPrimitive<Boolean> memFence, int numAddressesLog,
			PacketGenerator gen) {
		this.done = done;
//		this.inFlight = numInFlight;
		this.memFence = memFence;
		this.numAddresses = (int) Math.pow(2, numAddressesLog);
		this.pktGen = gen;
		configHandler = new ConfigPacketHandler(numAddresses);
		dataHandler = new DataPacketHandler(numAddresses);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!done.value) {
			for (int i = 0; i < numAddresses; i++) {
				Packet packet = pktGen.getPacket();
				// only 256 packets can be in flight at once
				while (numInFlight.get() > 256 && !done.value) {}
				numInFlight.addAndGet(1);
//				inFlight.value++;
				switch (packet.type) {
				case ConfigPacket:
					// send to config thread pool
					configHandler.handlePacket(packet);
					break;
				case DataPacket:
					// send to data thread pool
					dataHandler.handlePacket(packet);
					break;
				}
			}
		}

	}

}
