
public interface Queue<T> {

	void enqueue(int index, T item);
	void dequeue(int index);
}
