package omni.impl.seq;

import omni.util.ArrCopy;
import omni.util.OmniArray;

public class CharOrderedSetPrototype {

	char[] arr;
	int lo;
	int hi;
	
	
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
	
	private void initialize(char key) {
		char[] arr;
		if((arr=this.arr)!=null) {
			int arrLength;
			if((arrLength=arr.length)==0) {
				this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
				if(key<=OmniArray.DEFAULT_ARR_SEQ_CAP/2) {
					arrLength=key;
				}else if((arrLength=OmniArray.DEFAULT_ARR_SEQ_CAP-1-(Character.MAX_VALUE-key))<OmniArray.DEFAULT_ARR_SEQ_CAP/2) {
					arrLength=OmniArray.DEFAULT_ARR_SEQ_CAP/2;
					
				}
			}else {
				final int halfArrLength;
				if(key<=(halfArrLength=arrLength>>>1)) {
					arrLength=key;
				}else if((arrLength-=(1+(Character.MAX_VALUE-key)))<halfArrLength) {
					arrLength=halfArrLength;
				}
			}
			arr[arrLength]=key;
			this.lo=arrLength;
			this.hi=arrLength;
		}else {
			this.arr=new char[]{key};
			this.lo=0;
			this.hi=0;
		}
	}
	
	private boolean nonfragmentedAddPushDown(char key,char[] arr,int lo,int hi) {
		int tmpLo=lo;
		int mid;
		while((mid=(tmpLo+hi)>>>1)>=tmpLo) {
			switch(Integer.signum(arr[mid]-key)) {
			case 0:
				return false;
			case 1:
				hi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}
		//insert
		if(lo==0) {
			if((lo+=((hi=arr.length)-1))==this.hi) {
				final var tmpArr=new char[lo=OmniArray.growBy50Pct(hi)];
				this.hi=(lo-=((lo-hi)>>>1))-1;
				ArrCopy.uncheckedCopy(arr, tmpLo, tmpArr, lo-=(hi-=tmpLo), hi);
				tmpArr[--lo]=key;
				ArrCopy.semicheckedCopy(arr, 0, tmpArr, lo-=tmpLo, tmpLo);
				this.arr=tmpArr;
			}else {
				//wrap around
				arr[lo]=arr[0];
				ArrCopy.semicheckedSelfCopy(arr, 0, 1, tmpLo-1);
				arr[tmpLo]=key;
			}
			this.lo=lo;
		}else {
			//simple down-shift
			ArrCopy.semicheckedSelfCopy(arr, hi=lo-1, lo, tmpLo-lo);
			arr[tmpLo]=key;
			this.lo=hi;
		}
		return true;
	}
	private boolean nonfragmentedAddPushUp(char key,char[] arr,int lo,int hi) {
		int tmpHi=hi;
		int mid;
		while((mid=(lo+tmpHi)>>>1)>=lo) {
			switch(Integer.signum(arr[mid]-key)) {
			case 0:
				return false;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				lo=mid+1;
			}
		}
		//insert
		if(hi==(tmpHi=arr.length)-1) {
			if(this.lo==0) {
				//grow the array
				final char[] tmpArr=new char[hi=OmniArray.growBy50Pct(tmpHi)];
				this.lo=(hi=(hi-tmpHi)>>>1);
				ArrCopy.uncheckedCopy(arr, 0, tmpArr, hi, lo);
				tmpArr[hi+=lo]=key;
				ArrCopy.semicheckedCopy(arr, lo, tmpArr, hi+1, tmpHi-=lo);
				this.hi=hi+tmpHi;
				this.arr=tmpArr;
			}else {
				//wrap around
				arr[0]=arr[hi];
				ArrCopy.semicheckedCopy(arr, lo, arr, lo+1, hi-lo);
				arr[lo]=key;
				this.hi=0;
			}
		}else {
			//simple up-shift
			ArrCopy.semicheckedCopy(arr,lo,arr,lo+1,hi+1-lo);
			arr[lo]=key;
			this.hi=hi+1;
		}
		return true;
	}
	
	
	private boolean fragmentedSearchHiAndExpand(char key,char[] arr,int lo) {
		int tmpLo=lo;
		int arrLength;
		int tmpHi=(arrLength=arr.length)-1;
		do {
			final int mid;
			switch(Integer.signum(arr[mid=(tmpHi+tmpLo)>>>1]-key)) {
			case 0:
				return false;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}while(tmpHi>=tmpLo);
		//grow array and insert
		final var tmpArr=new char[tmpHi=OmniArray.growBy50Pct(arrLength)];
		this.hi=(tmpHi-=((tmpHi-arrLength)>>>1))-1;
		ArrCopy.uncheckedCopy(arr, 0, tmpArr, tmpHi-=lo, lo);
		ArrCopy.semicheckedCopy(arr, tmpLo, tmpArr, tmpHi-=(arrLength-=tmpLo), arrLength);
		tmpArr[--tmpHi]=key;
		ArrCopy.semicheckedCopy(arr, lo, tmpArr, tmpHi-=(tmpLo-=lo), tmpLo);
		this.arr=tmpArr;
		this.lo=tmpHi;
		return true;
	}

	private boolean fragmentedAddPushDown(char key,char[] arr,int lo) {
		int tmpHi=arr.length-1;
		int tmpLo=lo;
		do {
			final int mid;
			switch(Integer.signum(arr[mid=(tmpHi+tmpLo)>>>1]-key)) {
			case 0:
				return false;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}while(tmpHi>=tmpLo);
		//insert
		ArrCopy.semicheckedSelfCopy(arr,tmpHi=lo-1,lo,tmpLo-lo);
		arr[tmpLo]=key;
		this.lo=tmpHi;
		return true;
	}
	private boolean fragmentedSearchLoAndExpand(char key,char[] arr,int hi) {
		int tmpHi=hi;
		int tmpLo=1;
		while(tmpLo<=tmpHi) {
			final int mid;
			switch(Integer.signum(arr[mid=(tmpHi+tmpLo)>>>1]-key)) {
			case 0:
				return false;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}
		//grow array and insert
		int newLo;
		final var tmpArr=new char[newLo=OmniArray.growBy100Pct(tmpHi=arr.length)];
		this.lo=newLo=(newLo-tmpHi)>>>1;
		ArrCopy.uncheckedCopy(arr, ++hi, tmpArr, newLo, tmpHi-=hi);
		ArrCopy.semicheckedCopy(arr, 0, tmpArr, newLo+=tmpHi, tmpLo);
		tmpArr[newLo]=key;
		ArrCopy.semicheckedCopy(arr, tmpLo, tmpArr, newLo+=tmpLo, hi-=tmpLo);
		this.hi=hi-1;
		this.arr=tmpArr;
		return true;
	}
	private boolean fragmentedAddPushUp(char key,char[] arr,int hi) {
		int tmpHi=hi;
		int tmpLo=1;
		while(tmpLo<=tmpHi) {
			final int mid;
			switch(Integer.signum(arr[mid=(tmpHi+tmpLo)>>>1]-key)) {
			case 0:
				return false;
			case 1:
				tmpHi=mid-1;
				break;
			default:
				tmpLo=mid+1;
			}
		}
		//insert
		ArrCopy.semicheckedCopy(arr, tmpLo, arr, tmpLo+1, (++hi)-tmpLo);
		arr[tmpLo]=key;
		this.hi=hi;
		return true;
	}
	
	public boolean add(char key) {
		int hi;
		if((hi=this.hi)!=-1) {
			int lo,mid;
			if((mid=((lo=this.lo)+hi)>>>1)>=lo) {
				final char[] arr;
				switch(Integer.signum((arr=this.arr)[mid]-key)) {
				case 1:
					return nonfragmentedAddPushDown(key,arr,lo,mid-1);
				default:
					return nonfragmentedAddPushUp(key,arr,mid+1,hi);
				case 0:
				}
			}else {
				final char[] arr;
				switch(Integer.signum((arr=this.arr)[0]-key)) {
				case 1:
					if(hi+1==lo) {
						return fragmentedSearchHiAndExpand(key,arr,lo);
					}else {
						return fragmentedAddPushDown(key,arr,lo);
					}
					
				default:
					if(hi+1==lo) {
						return fragmentedSearchLoAndExpand(key,arr,hi);
					}else {
						return fragmentedAddPushUp(key,arr,hi);
					}
				case 0:
				}
			}
			return false;
		}else {
			initialize(key);
			return true;
		}
	}
}
