import java.util.concurrent.atomic.*;

public class Dispatcher implements Runnable {
	
	PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
	PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
	private PacketGenerator pktGen;
	private int numSources;
	
	public Dispatcher(PaddedPrimitiveNonVolatile<Boolean> done, PaddedPrimitive<Boolean> memFence,
			int numSources, PacketGenerator gen) {
		this.done = done;
		this.memFence = memFence;
		this.numSources = numSources;
		this.pktGen = gen;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!done.value) {
			for (int i = 0; i < numSources; i++) {
				Packet packet = pktGen.getPacket();
				switch (packet.type) {
					case ConfigPacket:
						// send to config thread pool
						break;
					case DataPacket:
						// send to data thread pool
						break;
				}
			}
		}
		
	}

}
