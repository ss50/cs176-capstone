
class SerialPacket {
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

    @SuppressWarnings({"unchecked"})
    StopWatch timer = new StopWatch();
    PacketGenerator pktGen = new PacketGenerator(numAddressesLog, numTrainsLog, meanTrainSize, meanTrainsPerComm,
			meanWindow, meanCommsPerAddress, meanWork, configFraction, pngFraction, acceptingFraction);

    PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
    AccessControl ac = new AccessControl();
        
    SerialPacketWorker workerData = new SerialPacketWorker(done, pktGen, ac);
    Thread workerThread = new Thread(workerData);
    
    workerThread.start();
    timer.startTimer();
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    done.value = true;
    memFence.value = true;  // memFence is a 'volatile' forcing a memory fence
    try {                   // which means that done.value is visible to the workers
      workerThread.join();
    } catch (InterruptedException ignore) {;}      
    timer.stopTimer();
    final long totalCount = workerData.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}

class ParallelPacket {
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

    @SuppressWarnings({"unchecked"})
    //
    // Allocate and initialize your Lamport queues
    //
    StopWatch timer = new StopWatch();
    PacketGenerator pktGen = new PacketGenerator(numAddressesLog, numTrainsLog, meanTrainSize, meanTrainsPerComm,
			meanWindow, meanCommsPerAddress, meanWork, configFraction, pngFraction, acceptingFraction);
    
    
    // TODO: change this to ParallelPacketWorker once the class is implemented
    PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
    AccessControl ac = new AccessControl();
    SerialPacketWorker workerData = new SerialPacketWorker(done, pktGen, ac);
    // 
    // Allocate and initialize locks and any signals used to marshal threads (eg. done signals)
    // 
    // Allocate and initialize Dispatcher and Worker threads
    //
    // call .start() on your Workers
    //
    timer.startTimer();
    // 
    // call .start() on your Dispatcher
    // 
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    // 
    // assert signals to stop Dispatcher - remember, Dispatcher needs to deliver an 
    // equal number of packets from each source
    //
    // call .join() on Dispatcher
    //
    // assert signals to stop Workers - they are responsible for leaving the queues
    // empty - use whatever protocol you like, but one easy one is to have each
    // worker verify that it's corresponding queue is empty after it observes the
    // done signal set to true
    //
    // call .join() for each Worker
    timer.stopTimer();
    
    // TODO: get total packets from Dispatcher instead
    final long totalCount = workerData.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}