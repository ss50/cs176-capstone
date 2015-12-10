import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("hiding")
public class ConcurrentQueue{
	public static final int MAX_QUEUE_SIZE = 256;
	static final ConcurrentLinkedQueue<PacketCallbackBundle> queue[] = new ConcurrentLinkedQueue[ParallelFirewall.NUM_CONCURRENT_QUEUES];
	private static int queueSize;
	public static void initialize(){
		queueSize = MAX_QUEUE_SIZE; // can be changed for testing
		for (int i = 0; i < ParallelFirewall.NUM_CONCURRENT_QUEUES; i++) {
			queue[i] = new ConcurrentLinkedQueue<PacketCallbackBundle>();
		}
	}
	

	public static boolean enqueue(int index, PacketCallbackBundle item) {
		// TODO Auto-generated method stub
		if (queue[index].size() > queueSize) {
			return false;
		}
		return queue[index].offer(item);
	}

	public static PacketCallbackBundle dequeue(int index) {
		// TODO Auto-generated method stub
		return queue[index].poll();
	}
	
	
	
	

}
