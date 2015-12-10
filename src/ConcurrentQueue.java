import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("hiding")
public class ConcurrentQueue{
	static final ConcurrentLinkedQueue<PacketCallbackBundle> queue[] = new ConcurrentLinkedQueue[ParallelFirewall.NUM_CONCURRENT_QUEUES];
	
	public static void initialize(){
		for (int i = 0; i < ParallelFirewall.NUM_CONCURRENT_QUEUES; i++) {
			queue[i] = new ConcurrentLinkedQueue<PacketCallbackBundle>();
		}
	}
	

	public static boolean enqueue(int index, PacketCallbackBundle item) {
		// TODO Auto-generated method stub
		return queue[index].offer(item);
	}

	public static PacketCallbackBundle dequeue(int index) {
		// TODO Auto-generated method stub
		return queue[index].poll();
	}
	
	
	
	

}
