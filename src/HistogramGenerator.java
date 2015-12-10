import java.util.Map;
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
	
	public static void printHistogram() {
		for (Map.Entry<Long, AtomicInteger> entry : fingerprintToCount.entrySet()) {
			System.out.println("Address: " + entry.getKey() + " count: " + entry.getValue().get());
		}
	}
	
}
