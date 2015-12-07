import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
	
	private ReentrantReadWriteLock[] locks;
	
	public ReadWriteLock(int numLocks) {
		locks = new ReentrantReadWriteLock[numLocks];
		for (int i = 0; i < locks.length; i++) {
			locks[i] = new ReentrantReadWriteLock();
		}
	}
	
	public int addressToHashIndex(int address){
		return address % locks.length;
	}
	
	public void lockRead(int address) {
		locks[addressToHashIndex(address)].readLock().lock();
	}
	
	public void unlockRead(int address) {
		locks[addressToHashIndex(address)].readLock().unlock();
	}
	
	public void lockWrite(int address) {
		locks[addressToHashIndex(address)].writeLock().lock();
	}
	
	public void unlockWrite(int address) {
		locks[addressToHashIndex(address)].writeLock().unlock();
	}

}
