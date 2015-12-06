import java.util.concurrent.atomic.*;

public class Dispatcher implements Runnable {
	
	PaddedPrimitiveNonVolatile<Boolean> done;
	PaddedPrimitive<Boolean> memFence;
	private PacketGenerator pktGen;
	private int numAddresses;
	private ConfigPacketHandler configHandler;
	private DataPacketHandler dataHandler;
	
	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done, PaddedPrimitive<Boolean> memFence, int numAddresses, PacketGenerator gen) {
		this.done = done;
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
