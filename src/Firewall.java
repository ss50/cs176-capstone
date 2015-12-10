import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class SerialFirewall {
	public static void main(String[] args) {
		System.out.println("Serial firewall");
		final int numMilliseconds = Integer.parseInt(args[0]);
		final int numAddressesLog = Integer.parseInt(args[1]);
		final int numTrainsLog = Integer.parseInt(args[2]);
		final double meanTrainSize = Double.parseDouble(args[3]);
		final double meanTrainsPerComm = Double.parseDouble(args[4]);
		final int meanWindow = Integer.parseInt(args[5]);
		final int meanCommsPerAddress = Integer.parseInt(args[6]);
		final int meanWork = Integer.parseInt(args[7]);
		final double configFraction = Double.parseDouble(args[8]);
		final double pngFraction = Double.parseDouble(args[9]);
		final double acceptingFraction = Double.parseDouble(args[10]);
		long fingerprint = 0;
		StopWatch timer = new StopWatch();
		PacketGenerator pktGen = new PacketGenerator(numAddressesLog,
				numTrainsLog, meanTrainSize, meanTrainsPerComm, meanWindow,
				meanCommsPerAddress, meanWork, configFraction, pngFraction,
				acceptingFraction);
		Fingerprint residue = new Fingerprint();

		int initializedTotalPackets = (int) Math.pow(
				Math.pow(2, numAddressesLog), 1.5);

		PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(
				false);
		PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
		AccessControl accessControl = new AccessControl(false);

		for (int i = 0; i < initializedTotalPackets; i++) {
			Packet configPacket = pktGen.getConfigPacket();
			Config config = configPacket.config;
			accessControl.setPNG(config.address, config.personaNonGrata);
			accessControl.setAcceptingSources(config.address,
					config.addressBegin, config.addressEnd,
					config.acceptingRange);
		}
		
		
		SerialPacketWorker serialWorker = new SerialPacketWorker(done, pktGen,
				accessControl);
		Thread workerThread = new Thread(serialWorker);

		workerThread.start();
		timer.startTimer();

		try {
			Thread.sleep(numMilliseconds);
		} catch (InterruptedException ignore) {
			;
		}

		done.value = true;
		memFence.value = true;
		try {
			workerThread.join();
		} catch (InterruptedException ignore) {
			;
		}
		timer.stopTimer();
		final long totalCount = serialWorker.totalPackets;
		
//		HistogramGenerator.printHistogram();
		System.out.println("count: " + totalCount);
		System.out.println("time: " + timer.getElapsedTime());
		System.out.println(totalCount / timer.getElapsedTime() + " pkts / ms");
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
		PacketGenerator pktGen = new PacketGenerator(numAddressesLog,
				numTrainsLog, meanTrainSize, meanTrainsPerComm, meanWindow,
				meanCommsPerAddress, meanWork, configFraction, pngFraction,
				acceptingFraction);

		Fingerprint residue = new Fingerprint();
		// ...
		// allocate and initialize bank of numSources Lamport queues
		// each with depth queueDepth
		// they should throw FullException and EmptyException upon those
		// conditions
		// ...
		int totalPackets = (int) Math.pow(2, numAddressesLog);
		AtomicQueue packetQueues[] = new AtomicQueue[numSources];

		for (int i = 0; i < numSources; i++) {
			packetQueues[i] = new AtomicQueue<Packet>();
		}

		// TODO: implement logic for dispatching config and data packets here
	}
}

class FirewallTest {
	public static void main(String[] args) {
//		String[] myArgs = { "3000", "11", "12", "5", "1", "3", "3", "3822", ".24", ".04", ".96" };
//		String[] myArgs = { "3000", "12", "10", "1", "3", "3", "1", "2644", ".11", ".09", ".92" };
//		String[] myArgs = { "3000", "12", "10", "4", "3", "6", "2", "1304", ".10", ".03", ".90" };
//		String[] myArgs = { "3000", "14", "10", "5", "5", "6", "2", "315", ".08", ".05", ".90" };
//		String[] myArgs = { "3000", "15", "14", "9", "16", "7", "10", "4007", ".02", ".10", ".84" };
		String[] myArgs = { "3000", "15", "15", "9", "10", "9", "9", "7125", ".01", ".2", ".77" };
//		String[] myArgs = { "3000", "15", "15", "10", "13", "8", "10", "5328", ".04", ".18", ".8" };
//		String[] myArgs = { "3000", "16", "14", "15", "12", "9", "5", "8840", ".04", ".19", ".76" };
//		boolean serial = false;
//		if (serial) {
//			SerialFirewall.main(myArgs);
//		} else {
//			ParallelFirewall.main(myArgs);
//		}
		
		SerialFirewall.main(myArgs);
		System.out.println();
		ParallelFirewall.main(myArgs);

	}
}

class ParallelFirewall {
	public static int NUM_DISPATCH_THREADS = 6;
	public static int NUM_CONCURRENT_QUEUES = 4;
	public static int NUM_HANDLER_THREADS = 15;

	public static void main(String[] args) {
		System.out.println("ParallelFirewall");
		final int numMilliseconds = Integer.parseInt(args[0]);
		final int numAddressesLog = Integer.parseInt(args[1]);
		final int numTrainsLog = Integer.parseInt(args[2]);
		final double meanTrainSize = Double.parseDouble(args[3]);
		final double meanTrainsPerComm = Double.parseDouble(args[4]);
		final int meanWindow = Integer.parseInt(args[5]);
		final int meanCommsPerAddress = Integer.parseInt(args[6]);
		final int meanWork = Integer.parseInt(args[7]);
		final double configFraction = Double.parseDouble(args[8]);
		final double pngFraction = Double.parseDouble(args[9]);
		final double acceptingFraction = Double.parseDouble(args[10]);
		StopWatch timer = new StopWatch();

		PacketGenerator pktGen = new PacketGenerator(numAddressesLog,
				numTrainsLog, meanTrainSize, meanTrainsPerComm, meanWindow,
				meanCommsPerAddress, meanWork, configFraction, pngFraction,
				acceptingFraction);
//		PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(
//				false);
		AtomicBoolean done = new AtomicBoolean();
		PaddedPrimitiveNonVolatile<Integer> numInFlight = new PaddedPrimitiveNonVolatile<Integer>(
				0);
		PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
		
		// ...
		// Allocate and initialize bank of Lamport queues, as in
		// SerialQueueFirewall
		// ...

		// Allocate and initialize a Dispatcher class implementing Runnable
		// and a corresponding Dispatcher Thread
		// ...
		ConcurrentQueue.initialize();
		Fingerprint residue = new Fingerprint();
		AccessControl accessControl = new AccessControl(true);
		int totalPackets = (int) Math.pow(Math.pow(2, numAddressesLog), 1.5);

		for (int i = 0; i < totalPackets; i++) {
			Packet configPacket = pktGen.getConfigPacket();
			Config config = configPacket.config;
			accessControl.setPNG(config.address, config.personaNonGrata);
			accessControl.setAcceptingSources(config.address,
					config.addressBegin, config.addressEnd,
					config.acceptingRange);
		}
		
		// AtomicQueue packetQueues[] = new AtomicQueue[numSources];

		/*
		 * for (int i = 0; i < numSources; i++) { packetQueues[i] = new
		 * AtomicQueue<Packet>(); }
		 */

		AtomicInteger numPacketsDistributed = new AtomicInteger(0);

		CallbackFunction callbackFunc = () -> {
			return numPacketsDistributed.incrementAndGet();
		};
		
		Thread[] dispatcherThreads = new Thread[NUM_DISPATCH_THREADS];
		for (int i = 0; i < NUM_DISPATCH_THREADS; i++) {
			Dispatcher dispatcher = new Dispatcher(done, numInFlight, memFence,
					accessControl, numAddressesLog, pktGen, callbackFunc, i);
			dispatcherThreads[i] = new Thread(dispatcher);
		}

		// Allocate and initialize an array of Worker classes, implementing
		// Runnable
		// and the corresponding Worker Threads
		// ...

		// Call start() for each worker
		// ...
		timer.startTimer();
		// ...
		for (int i = 0; i < NUM_DISPATCH_THREADS; i++) {
			dispatcherThreads[i].start();
		}
		// Call start() for the Dispatcher thread
		// ...
		// Call join() for Dispatcher thread
		// ...
		// Call join() for each Worker thread
		// ...
		try {
			Thread.sleep(numMilliseconds);
		} catch (InterruptedException ignore) {
			;
		}
		done.set(true);
		memFence.value = true;
		try {
			for(Thread t: dispatcherThreads){
				t.join();
			}
		} catch (InterruptedException e) {
			;
		}
		timer.stopTimer();
		// System.out.println("elapsed time: " + timer.getElapsedTime());
		// System.out.println("numPacketsDistributed: " +
		// numPacketsDistributed.get());
		//HistogramGenerator.printHistogram();
		System.out.println("count: " + numPacketsDistributed);
		System.out.println("time: " + timer.getElapsedTime());
		System.out.println((float) numPacketsDistributed.get()
				/ timer.getElapsedTime() + " pkts / ms");
	}
}
