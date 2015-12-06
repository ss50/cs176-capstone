import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
	
	private ReentrantReadWriteLock[] locks;
	
	public ReadWriteLock(int numLocks) {
		locks = new ReentrantReadWriteLock[numLocks];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ReentrantReadWriteLock();
		}
	}
	
	public void lockRead(int address) {
		locks[address].readLock().lock();
	}
	
	public void unlockRead(int address) {
		locks[address].readLock().unlock();
	}
	
	public void lockWrite(int address) {
		locks[address].writeLock().lock();
	}
	
	public void unlockWrite(int address) {
		locks[address].writeLock().unlock();
	}

}
