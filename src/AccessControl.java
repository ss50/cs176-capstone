import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccessControl {
	
	private ConcurrentHashMap<Integer, Boolean> pngList;
	private IntervalTree dList;
	
	public AccessControl(boolean isConcurrent) {
		pngList = new ConcurrentHashMap<Integer, Boolean>();
		dList = isConcurrent ? IntervalTree.getParallelIntervalTree() : IntervalTree.getSerialIntervalTree();
	}
	
	public boolean isValidDataPacket(int src, int dest) {
		return isDestAcceptingSource(src,dest) && !isPNG(src);
	}
	
	public void setAcceptingSources(int dest, int addressBegin, int addressEnd, boolean acceptingRange) {
		if(acceptingRange){
			dList.addAddressRange(dest, addressBegin, addressEnd);
		}
	}
	
	public boolean isDestAcceptingSource(int src, int dest) {
		return dList.containsDestinationAddress(src, dest);
	}
	
	public void setPNG(int sourceAddress, boolean isPNG){
		pngList.put(sourceAddress, isPNG);
	}
		
	public boolean isPNG(int sourceAddress){
		return pngList.contains(sourceAddress) ? pngList.get(sourceAddress) : false;
	}
}
