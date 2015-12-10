
public class PacketCallbackBundle {
	public final CallbackFunction cf;
	public final Packet p;
	
	public PacketCallbackBundle(CallbackFunction callback, Packet packet) {
		this.cf = callback;
		this.p = packet;
	}
}
