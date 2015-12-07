import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class DList {
	
	private ConcurrentHashMap<Integer, Integer> skipListMap;
	
	public DList() {
//		skipListMap = new ConcurrentSkipListMap<Integer, List<Integer>>();
		IntervalTree aoeu = new IntervalTree();
	}
	
	public void setAcceptingSources(int dest, int addressBegin, int addressEnd, boolean acceptingRange) {
		
	}
	
	public List<Integer> getAcceptingSources(int dest, int addressBegin, int addressEnd, boolean acceptingRange) {
		return null;
	}
	
}
