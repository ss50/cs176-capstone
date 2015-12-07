import java.util.concurrent.ConcurrentHashMap;

public class PNGList {
	private ConcurrentHashMap<Integer, Boolean> hashMap;
	
	public PNGList() {
		
	}
	
	public void setAddress(int sourceAddress, boolean isPNG){
		hashMap.put(sourceAddress, isPNG);
	}
	
	public boolean isPNG(int sourceAddress){
		return hashMap.contains(sourceAddress) ? hashMap.get(sourceAddress) : false;
	}

}
