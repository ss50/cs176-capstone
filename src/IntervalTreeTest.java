



public class IntervalTreeTest{
	public static void main(String[] args){
		IntervalTree mt = IntervalTree.getSerialIntervalTree();
		mt.addAddressRange(0, 1, 2);
		mt.addAddressRange(0, 11, 22);
		mt.addAddressRange(0, 1, 5);
		mt.printRangesForAddress(0);
	}
}
