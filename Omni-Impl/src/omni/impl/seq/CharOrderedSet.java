package omni.impl.seq;

import omni.util.ArrCopy;
import omni.util.OmniArray;

public class CharOrderedSet {

	char[] arr;
	int lo;
	int hi;
	
	private static boolean nonFragmentedContains(char[] arr,char key,int lo,int hi) {
		do{
			final int mid;
			switch(Integer.signum(key-(arr[mid=(lo+hi)>>>1]))){
			case 0:
				return true;
			case 1:
				lo=mid+1;
				break;
			default:
				hi=mid-1;
			}
		}while(lo<=hi);
		return false;
	}
	private static boolean nonFragmentedRemoveValPullDown(char[] arr,char key,int lo,int hi) {
		var tmpHi=hi;
		do {
			final int mid;
			switch(Integer.signum(arr[mid=(lo+tmpHi)>>>1]-key)) {
			case 0:
				ArrCopy.uncheckedSelfCopy(arr, mid, mid+1, hi-mid);
				return true;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				lo=mid+1;
			}
		}while(lo<=tmpHi);
		return false;
	}
	private static boolean nonFragmentedRemoveValPullUp(char[] arr, char key,int lo,int hi) {
		for(var tmpLo=lo;tmpLo<=hi;) {
			final int mid;
			switch(Integer.signum(arr[mid=(tmpLo+hi)>>>1]-key)) {
			case 0:
				ArrCopy.semicheckedCopy(arr, lo, arr, lo+1, mid-lo);
				return true;
			case 1:
				hi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}
		return false;
	}
	
	private boolean nonFragmentedRemoveVal(char key,int lo,int hi) {
		final char[] arr;
		final int mid;
		switch(Integer.signum(key-(arr=this.arr)[mid=(hi+lo)>>>1])) {
		case 0:
			ArrCopy.uncheckedSelfCopy(arr,mid,mid+1,hi-mid);
			this.hi=hi-1;
			return true;
		case 1:
			if(nonFragmentedRemoveValPullDown(arr,key,mid+1,hi)) {
				this.hi=hi-1;
				return true;
			}
			break;
		default:
			if(nonFragmentedRemoveValPullUp(arr,key,lo,mid-1)) {
				this.lo=lo+1;
				return true;
			}
		}
		return false;
	}
	private boolean fragmentedRemoveVal(char key,int lo,int hi) {
		final char[] arr;
		switch(Integer.signum(key-(arr=this.arr)[0])) {
		case 0:
			if(hi==0) {
				this.hi=arr.length-1;
			}else {
				ArrCopy.uncheckedSelfCopy(arr, 0, 1, hi);
				this.hi=hi-1;
			}
			return true;
		case 1:
			if(hi>0 && nonFragmentedRemoveValPullDown(arr,key,1,hi)) {
				this.hi=hi-1;
				return true;
			}
			break;
		default:
			if(nonFragmentedRemoveValPullUp(arr,key,lo,hi=arr.length-1)) {
				if(lo==hi) {
					this.lo=0;
				}else {
					this.lo=lo+1;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean removeVal(char key) {
		int hi;
		if((hi=this.hi)!=-1) {
			int lo;
			switch(Integer.signum(hi-(lo=this.lo))) {
			case 0:
				if(arr[hi]==key) {
					this.hi=-1;
					return true;
				}
				break;
			case 1:
				return nonFragmentedRemoveVal(key,lo,hi);
			default:
				return fragmentedRemoveVal(key,lo,hi);
			}
		}
		return false;
	}
	
	public boolean contains(char key) {
		int hi;
		if((hi=this.hi)!=-1) {
			final var arr=this.arr;
			final int lo;
			if(hi>=(lo=this.lo)){
				return nonFragmentedContains(arr,key,lo,hi);
			}else{
				switch(Integer.signum(key-arr[0])) {
				case 0:
					return true;
				case 1:
					if(1<=hi) {
						return nonFragmentedContains(arr,key,1,hi);
					}
					break;
				default:
					return nonFragmentedContains(arr,key,lo,arr.length-1);
				}
			}
		}
		return false;
	}

	private void initialize(char val) {
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	private boolean nonfragmentedAdd(char key,int lo,int hi) {
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	
	
	
	public boolean add(char key) {
		int hi;
		if((hi=this.hi)!=-1) {
			int lo;
			if(hi>=(lo=this.lo)) {
				return nonfragmentedAdd(key,lo,hi);
			}else {
				final var arr=this.arr;
				switch(Integer.signum(key-arr[0])) {
				case 0:
					return false;
				case 1:
					if(hi==0) {
						if(lo==1) {
							char[] tmp;
							this.arr=tmp=new char[OmniArray.growBy50Pct(lo=arr.length)];
							ArrCopy.uncheckedCopy(arr, 1, tmp, 0, hi=lo-1);
							tmp[hi]=arr[0];
							this.lo=0;
							arr=tmp;
						}
						arr[++hi]=key;
						this.hi=hi;
						break;
					}
					
					
					//TODO search in 0 -> hi span
					throw new omni.util.NotYetImplementedException();
				default:
					//TODO search in lo->arr.length-1 span
					throw new omni.util.NotYetImplementedException();
				}
			}
		}else {
			initialize(val);
		}
		return true;
	}
}
