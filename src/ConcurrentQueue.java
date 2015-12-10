import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("hiding")
public class ConcurrentQueue{
	static final ConcurrentLinkedQueue<PacketCallbackBundle> queue[] = new ConcurrentLinkedQueue[ParallelFirewall.NUM_DISPATCH_THREADS];
	private static int queueSize;
	
	public static void initialize(){
		queueSize = 256; // can be changed for testing
		for (int i = 0; i < queue.length; i++) {
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
