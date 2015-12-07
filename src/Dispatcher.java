import java.util.concurrent.atomic.*;

public class Dispatcher implements Runnable {
	
	PaddedPrimitiveNonVolatile<Boolean> done;
	PaddedPrimitiveNonVolatile<Integer> inFlight;
	PaddedPrimitive<Boolean> memFence;
	private PacketGenerator pktGen;
	private int numAddresses;
	private ConfigPacketHandler configHandler;
	private DataPacketHandler dataHandler;
	private int numInFlight = 0;
	
	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done, PaddedPrimitiveNonVolatile<Integer> numInFlight, PaddedPrimitive<Boolean> memFence, int numAddresses, PacketGenerator gen) {
		this.done = done;
		this.inFlight = numInFlight;
		this.memFence = memFence;
		this.numAddresses = numAddresses;
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
				while (numInFlight > 256 && !done.value) {;}
				numInFlight++;
				inFlight.value++;
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
