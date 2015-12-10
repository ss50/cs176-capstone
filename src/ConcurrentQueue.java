import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("hiding")
public class ConcurrentQueue{
	public static final int MAX_QUEUE_SIZE = 50;
	static final ConcurrentLinkedQueue<PacketCallbackBundle> queue[] = new ConcurrentLinkedQueue[ParallelFirewall.NUM_DISPATCH_THREADS];
	static final AtomicInteger[] sizes = new AtomicInteger[ParallelFirewall.NUM_DISPATCH_THREADS];
	private static int queueSize;
	public static void initialize(){
		queueSize = MAX_QUEUE_SIZE; // can be changed for testing
//		for (int i = 0; i < ParallelFirewall.NUM_CONCURRENT_QUEUES; i++) {
		for (int i = 0; i < ParallelFirewall.NUM_DISPATCH_THREADS; i++) {
			queue[i] = new ConcurrentLinkedQueue<PacketCallbackBundle>();
			sizes[i] = new AtomicInteger(0);
		}
	}
	

	public static boolean enqueue(int index, PacketCallbackBundle item) {
		// TODO Auto-generated method stub
		if (sizes[index % queue.length].get() > queueSize) {
			System.out.println("Queue is full. retrying");
			return false;
		}
		System.out.println("enqueue " + sizes[index].get());

		sizes[index % queue.length].incrementAndGet();
		return queue[index % queue.length].offer(item);
	}

	public static PacketCallbackBundle dequeue(int index) {
		System.out.println("dequeue " + sizes[index].get());
		PacketCallbackBundle toReturn = queue[index % queue.length].poll();
		sizes[index % queue.length].addAndGet(toReturn == null ? 0 : -1);
		return toReturn;
	}
	
	
	
	

}
