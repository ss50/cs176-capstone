public interface PacketWorker extends Runnable {
  public void run();
}

class SerialPacketWorker implements PacketWorker {
  PaddedPrimitiveNonVolatile<Boolean> done;
  final PacketGenerator pkt;
  final Fingerprint residue = new Fingerprint();
  long fingerprint = 0;
  long totalPackets = 0;
  final int numSources;
  public SerialPacketWorker(
    PaddedPrimitiveNonVolatile<Boolean> done, 
    PacketGenerator pkt,
    int numSources) {
    this.done = done;
    this.pkt = pkt;
    this.numSources = numSources;
  }
  
  public void run() {
    
  }  
}

// class ParallelPacketWorker implements PacketWorker...