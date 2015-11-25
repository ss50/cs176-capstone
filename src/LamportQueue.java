class EmptyException extends Exception {
	
	public EmptyException() {
		super();
	}
	

	public EmptyException(String message) {
		super(message);
	}
	
}

class FullException extends Exception {
	
	public FullException() {
		super();
	}
	
	public FullException(String message) {
		super(message);
	}
}

public class LamportQueue<T> {

	private T[] queue;
	private volatile int head;
	private volatile int tail;
	private volatile int size;
	
	@SuppressWarnings("unchecked")
	public LamportQueue(int capacity) {
		queue = (T[]) new Object[capacity]; 
		head = 0;
		tail = 0;
		size = 0;
	}
	
	public void enqueue(T item) throws FullException{
		if (size == queue.length) {
			throw new FullException("Queue is full");
		}
		queue[tail] = item;
		tail = (tail + 1) % queue.length;
		size++;
	}
	
	public T dequeue() throws EmptyException{
		if (this.isEmpty()) {
			throw new EmptyException("Queue is empty");
		}
		T item = queue[head];
		head = (head + 1) % queue.length;
		size--;
		return item;
	}
	
	public boolean isEmpty() {
		return (size==0);
	}
}
