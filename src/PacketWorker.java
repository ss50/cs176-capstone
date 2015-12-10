public interface PacketWorker extends Runnable {
  public void run();
}

class SerialPacketWorker implements PacketWorker {
  PaddedPrimitiveNonVolatile<Boolean> done;
  final PacketGenerator pkt;
  final Fingerprint residue = new Fingerprint();
  AccessControl accessControl;
  long fingerprint = 0;
  long totalPackets = 0;
  
  public SerialPacketWorker(
    PaddedPrimitiveNonVolatile<Boolean> done, 
    PacketGenerator pkt,
    AccessControl ac) {
    this.done = done;
    this.pkt = pkt;
    accessControl = ac;
  }
  
  public void run() {
      while (!done.value) {
    	  Packet packet = pkt.getPacket();
    	  switch (packet.type) {
    	  		case ConfigPacket:
    	  			Config config = packet.config;
    	  			accessControl.setPNG(config.address, config.personaNonGrata);
    	  			accessControl.setAcceptingSources(config.address, config.addressBegin, config.addressEnd, config.acceptingRange);
    	  			break;
    	  		case DataPacket:
    	  			Body body = packet.body;
    	  			Header header = packet.header;
    	  			if (accessControl.isValidDataPacket(header.source, header.dest)) {
    	  				long checksum = residue.getFingerprint(body.iterations, body.seed);
        	  			HistogramGenerator.addFingerprintSighting(checksum);
    	  			}
    	  			
    	  			break;
    	  		default:
    	  			System.out.println("Invalid packet type: " + packet.type.toString());
    	  }
    	  totalPackets++;
      }
  }  
}

// class ParallelPacketWorker implements PacketWorker...