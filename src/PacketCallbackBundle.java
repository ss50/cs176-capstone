
public class PacketCallbackBundle {
	public final CallbackFunction cf;
	public final Packet packet;
	
	public PacketCallbackBundle(CallbackFunction callback, Packet packet) {
		this.cf = callback;
		this.packet = packet;
	}
}
