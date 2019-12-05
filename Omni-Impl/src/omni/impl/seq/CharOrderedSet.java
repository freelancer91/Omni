package omni.impl.seq;

import omni.util.OmniArray;

public class CharOrderedSet {

	int lo;
	int hi;
	char[] arr;
	
	@Override public boolean add(char val) {
		char[] arr;
		if((arr=this.arr)!=null) {
			int arrLength;
			if((arrLength=arr.length)!=0) {
				int hi;
				if((hi=this.hi)!=-1) {
					
				}else {
					if(val<(arrLength>>>=1)) {
						arr[val]=val;
						this.lo=val;
						this.hi=val;
					}else {
						
					}
				}
			}else {
				this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
				if(val<OmniArray.DEFAULT_ARR_SEQ_CAP/2) {
					arr[val]=val;
					this.lo=val;
					this.hi=val;
				}else {
					if((arrLength=Character.MAX_VALUE-val)<OmniArray.DEFAULT_ARR_SEQ_CAP/2) {
						arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
						this.lo=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
						this.hi=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
					}else {
						arr[OmniArray.DEFAULT_ARR_SEQ_CAP/2]=val;
						this.lo=OmniArray.DEFAULT_ARR_SEQ_CAP/2;
						this.hi=OmniArray.DEFAULT_ARR_SEQ_CAP/2;
					}
				}
			}
		}else {
			this.arr=new char[] {val};
			this.lo=0;
			this.hi=0;
		}
		return true;
	}
}
