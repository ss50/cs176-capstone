



public class IntervalTreeTest{
	public static void main(String[] args){
		IntervalTree mt = IntervalTree.getSerialIntervalTree();
		AccessControl ac = new AccessControl(false);
		mt.addAddressRange(0, 2, 4);
		mt.addAddressRange(0, 5, 7);
		mt.removeAddressRange(0, 4, 6);
		mt.printRangesForAddress(0);
	}
}
