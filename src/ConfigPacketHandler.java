import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConfigPacketHandler implements Runnable{
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private ReadWriteLock lockArray;
	private PNGList pngList;
	private DList dlist;
	private int numAddresses;
	
	public ConfigPacketHandler(int numAddressesLog) {
		this.numAddresses = (int) Math.pow(2, numAddresses);
	}

	@Override
	public void run() {
		while(true){
			
		}
	}

	public void handlePacket(Packet p) {
		ConfigPacketThread d = new ConfigPacketThread(p);
		threadPool.execute(d);
	}
	
	
	private class ConfigPacketThread implements Runnable {
		private Packet p;
		private int address;
		private int addressBegin;
		private int addressEnd;
		private boolean acceptingRange;
		private boolean personaNonGrata;
		
		private ConfigPacketThread(Packet p) {
			Config config = p.config;
			address = config.address;
			addressBegin = config.addressBegin;
			addressEnd = config.addressEnd;
			acceptingRange = config.acceptingRange;
			personaNonGrata = config.personaNonGrata;
		}
		
		@Override
		public void run() {
			pngList.setAddress(address, personaNonGrata);
			dlist.setAcceptingSources(address, addressBegin, addressEnd, acceptingRange);
		}

	}

}
