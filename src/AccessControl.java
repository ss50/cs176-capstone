import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AccessControl {
	
	private ConcurrentHashMap<Integer, Boolean> pngList;
	private ConcurrentHashMap<Integer, Integer> dList;
	
	public AccessControl() {
		pngList = new ConcurrentHashMap<Integer, Boolean>();
		dList = new ConcurrentHashMap<Integer, Integer>();
	}
	
	public void setAcceptingSources(int dest, int addressBegin, int addressEnd, boolean acceptingRange) {
		
	}
	
	public List<Integer> getAcceptingSources(int dest, int addressBegin, int addressEnd, boolean acceptingRange) {
		return null;
	}
	
	public void setAddress(int sourceAddress, boolean isPNG){
		pngList.put(sourceAddress, isPNG);
	}
	
	public boolean getAddressPermission(int sourceAddress) {
		if (pngList.get(sourceAddress) == null) {
			return false;
		}
		return pngList.get(sourceAddress);
	}
	
	public boolean isPNG(int sourceAddress){
		return pngList.contains(sourceAddress) ? pngList.get(sourceAddress) : false;
	}
}
