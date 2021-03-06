import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

public class IntervalTree {
	private Map<Integer, RangeSet<Integer>> map;

	private IntervalTree(Map<Integer, RangeSet<Integer>> map) {
		this.map = map;
	}

	public static IntervalTree getSerialIntervalTree() {
		return new IntervalTree(new HashMap<>());
	}				

	public static IntervalTree getParallelIntervalTree() {
		return new IntervalTree(new ConcurrentHashMap<>());
	}

	public void addAddressRange(int address, int rangeBottom, int rangeTop) {
		Range<Integer> toAdd = Range.closed(rangeBottom, rangeTop);
		if (map.containsKey(address)) {
			RangeSet<Integer> toUpdate = map.get(address);
			synchronized (toUpdate) {
				toUpdate.add(toAdd);
			}
		} else {
			TreeRangeSet<Integer> t = TreeRangeSet.<Integer> create();
			t.add(toAdd);
			map.put(address, t);
		}
	}

	public void removeAddressRange(int address, int rangeBottom, int rangeTop) {
		Range<Integer> toRemove = Range.closed(rangeBottom, rangeTop);
		if (map.containsKey(address)) {
			RangeSet<Integer> toUpdate = map.get(address);
			synchronized (toUpdate) {
				toUpdate.remove(toRemove);
			}

		}
	}

	public boolean containsDestinationAddress(int address, int destination) {
		if(!map.containsKey(destination)){
			return false;
		}
		
		return map.get(destination).contains(address);
	}

	public void printRangesForAddress(int address) {
		RangeSet<Integer> r = map.get(address);
		for (Range<Integer> ran : r.asRanges()) {
			System.out.println(ran.toString());
		}
	}
	
	public void printAllRanges() {
		for (Map.Entry<Integer, RangeSet<Integer>> entry : map.entrySet()) {
			System.out.print(entry.getKey() + ": ");
			RangeSet<Integer> r = entry.getValue();
			for (Range<Integer> ran : r.asRanges()) {
				System.out.print(ran.toString() + ", ");
			}
			System.out.println();
		}
	}
	
	public int getSize() {
		return map.entrySet().size();
	}

}