class SerialFirewall {
  public static void main(String[] args) {
    final int numAddressesLog = Integer.parseInt(args[0]);    
    final int numTrainsLog = Integer.parseInt(args[1]);
    final double meanTrainSize = Double.parseDouble(args[2]);
    final double meanTrainsPerComm = Double.parseDouble(args[3]);
    final int meanWindow = Integer.parseInt(args[4]);
    final int meanCommsPerAddress = Integer.parseInt(args[5]);
    final int meanWork = Integer.parseInt(args[6]);
    final double configFraction = Double.parseDouble(args[7]);
    final double pngFraction = Double.parseDouble(args[8]);
    final double acceptingFraction = Double.parseDouble(args[9]);
    long fingerprint = 0;
    StopWatch timer = new StopWatch();
    PacketGenerator pktGen = new PacketGenerator(numAddressesLog, numTrainsLog, meanTrainSize, meanTrainsPerComm,
    												meanWindow, meanCommsPerAddress, meanWork, configFraction, pngFraction, acceptingFraction);
    Fingerprint residue = new Fingerprint();
    
    // TODO: implement logic for dispatching config and data packets
  }
}


class SerialQueueFirewall {
  public static void main(String[] args) {  
	final int numMilliseconds = Integer.parseInt(args[0]);
    final int numSources = Integer.parseInt(args[1]);

    final int numAddressesLog = Integer.parseInt(args[2]);    
    final int numTrainsLog = Integer.parseInt(args[3]);
    final double meanTrainSize = Double.parseDouble(args[4]);
    final double meanTrainsPerComm = Double.parseDouble(args[5]);
    final int meanWindow = Integer.parseInt(args[6]);
    final int meanCommsPerAddress = Integer.parseInt(args[7]);
    final int meanWork = Integer.parseInt(args[8]);
    final double configFraction = Double.parseDouble(args[9]);
    final double pngFraction = Double.parseDouble(args[10]);
    final double acceptingFraction = Double.parseDouble(args[11]);
    
    StopWatch timer = new StopWatch();
    PacketGenerator pktGen = new PacketGenerator(numAddressesLog, numTrainsLog, meanTrainSize, meanTrainsPerComm,
			meanWindow, meanCommsPerAddress, meanWork, configFraction, pngFraction, acceptingFraction);

    Fingerprint residue = new Fingerprint();
    // ...
    // allocate and initialize bank of numSources Lamport queues
    // each with depth queueDepth
    // they should throw FullException and EmptyException upon those conditions
    // ...
    AtomicQueue packetQueues[] = new AtomicQueue[numSources];
    
    for (int i = 0; i < numSources; i++) {
    	packetQueues[i] = new AtomicQueue<Packet>();
    }
        
    // TODO: implement logic for dispatching config and data packets here
  }
}


class ParallelFirewall {
  public static void main(String[] args) {
	final int numMilliseconds = Integer.parseInt(args[0]);
    final int numSources = Integer.parseInt(args[1]);

    final int numAddressesLog = Integer.parseInt(args[2]);    
    final int numTrainsLog = Integer.parseInt(args[3]);
    final double meanTrainSize = Double.parseDouble(args[4]);
    final double meanTrainsPerComm = Double.parseDouble(args[5]);
    final int meanWindow = Integer.parseInt(args[6]);
    final int meanCommsPerAddress = Integer.parseInt(args[7]);
    final int meanWork = Integer.parseInt(args[8]);
    final double configFraction = Double.parseDouble(args[9]);
    final double pngFraction = Double.parseDouble(args[10]);
    final double acceptingFraction = Double.parseDouble(args[11]);
    StopWatch timer = new StopWatch();

    PacketGenerator pktGen = new PacketGenerator(numAddressesLog, numTrainsLog, meanTrainSize, meanTrainsPerComm,
			meanWindow, meanCommsPerAddress, meanWork, configFraction, pngFraction, acceptingFraction);
    // ...
    // Allocate and initialize bank of Lamport queues, as in SerialQueueFirewall
    // ...
    AtomicQueue packetQueues[] = new AtomicQueue[numSources];
    
    for (int i = 0; i < numSources; i++) {
    	packetQueues[i] = new AtomicQueue<Packet>();
    }
    // Allocate and initialize a Dispatcher class implementing Runnable
    // and a corresponding Dispatcher Thread
    // ...
    // Allocate and initialize an array of Worker classes, implementing Runnable
    // and the corresponding Worker Threads
    // ...
    // Call start() for each worker
    // ...
    timer.startTimer();
    // ...
    // Call start() for the Dispatcher thread
    // ...
    // Call join() for Dispatcher thread
    // ...
    // Call join() for each Worker thread
    // ...
    timer.stopTimer();
    System.out.println(timer.getElapsedTime());
  }
}