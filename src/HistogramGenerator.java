import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HistogramGenerator {
	private final static ConcurrentHashMap<Integer, AtomicInteger> fingerprintToCount = new ConcurrentHashMap<>();

	public static void addFingerprintSighting(long fingerPrint) {
		fingerprintToCount.get(fingerPrint).addAndGet(1);
	}
}
