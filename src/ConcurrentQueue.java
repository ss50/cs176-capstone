import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("hiding")
public class ConcurrentQueue{

	static ConcurrentLinkedQueue<Packet> queue[];
	
	public ConcurrentQueue(int size) {
		for (int i = 0; i < size; i++) {
			queue[i] = new ConcurrentLinkedQueue<Packet>();
		}
	}
	

	public static boolean enqueue(int index, Packet item) {
		// TODO Auto-generated method stub
		return queue[index].offer(item);
	}

	public static Packet dequeue(int index) {
		// TODO Auto-generated method stub
		return queue[index].poll();
	}
	
	
	
	

}
