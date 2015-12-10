
public interface Queue<T> {

	boolean enqueue(int index, T item);
	T dequeue(int index);
}
