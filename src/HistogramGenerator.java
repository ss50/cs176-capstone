import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HistogramGenerator {
	private final static ConcurrentHashMap<Long, AtomicInteger> fingerprintToCount = new ConcurrentHashMap<>();

	public static void addFingerprintSighting(long fingerPrint) {
		if (fingerprintToCount.get(fingerPrint) == null) {
			fingerprintToCount.put(fingerPrint, new AtomicInteger(0));
		}
		fingerprintToCount.get(fingerPrint).addAndGet(1);
	}
}
