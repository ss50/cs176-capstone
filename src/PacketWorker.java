public interface PacketWorker extends Runnable {
  public void run();
}

class SerialPacketWorker implements PacketWorker {
  PaddedPrimitiveNonVolatile<Boolean> done;
  final PacketGenerator pkt;
  final Fingerprint residue = new Fingerprint();
  long fingerprint = 0;
  long totalPackets = 0;
  
  public SerialPacketWorker(
    PaddedPrimitiveNonVolatile<Boolean> done, 
    PacketGenerator pkt,
    int numSources) {
    this.done = done;
    this.pkt = pkt;
  }
  
  public void run() {
      while (!this.done.value) {
    	  Packet packet = pkt.getPacket();
    	  long checksum = residue.getFingerprint(packet.body.iterations, packet.body.seed);
    	  switch (packet.type) {
    	  		case ConfigPacket:
    	  			break;
    	  		case DataPacket:
    	  			break;
    	  		default:
    	  			System.out.println("Invalid packet type: " + packet.type.toString());
    		  
    	  }
      }
  }  
}

// class ParallelPacketWorker implements PacketWorker...