import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConfigPacketHandler implements Runnable{
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	private ReadWriteLock lockArray;
	private int numAddresses;
	private AccessControl accessControl;
	
	public ConfigPacketHandler(int numAddressesLog, AccessControl ac) {
		this.numAddresses = (int) Math.pow(2, numAddresses);
		this.accessControl = ac;
	}

	@Override
	public void run() {
		while(true){
			
		}
	}

	public void handlePacket(Packet p, CallbackFunction cf) {
		ConfigPacketThread d = new ConfigPacketThread(p,cf);
		threadPool.execute(d);
	}
	
	
	private class ConfigPacketThread implements Runnable {
		private Packet p;
		private int address;
		private int addressBegin;
		private int addressEnd;
		private boolean acceptingRange;
		private boolean personaNonGrata;
		private CallbackFunction cf;
		
		private ConfigPacketThread(Packet p, CallbackFunction cf) {
			Config config = p.config;
			address = config.address;
			addressBegin = config.addressBegin;
			addressEnd = config.addressEnd;
			acceptingRange = config.acceptingRange;
			personaNonGrata = config.personaNonGrata;
			this.cf = cf;
		}
		
		@Override
		public void run() {
			accessControl.setAddress(address, personaNonGrata);
			accessControl.setAcceptingSources(address, addressBegin, addressEnd, acceptingRange);
			cf.operation();
		}

	}

}
