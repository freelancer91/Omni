package omni.impl.seq;

import omni.util.ArrCopy;
import omni.util.OmniArray;

public class CharOrderedSet {

	char[] arr;
	int lo;
	int hi;
	
	private static int getDefaultIndex(final char key) {
		final int index;
		if(key<=OmniArray.DEFAULT_ARR_SEQ_CAP/2)
		{
			return key;
		}
		else if((index=OmniArray.DEFAULT_ARR_SEQ_CAP-(Character.MAX_VALUE-key)-1)<OmniArray.DEFAULT_ARR_SEQ_CAP/2)
		{
			return OmniArray.DEFAULT_ARR_SEQ_CAP/2;
		}
		return index;
	}
	private static int getInitialIndex(final char[] arr,final char key) {
		int arrLength;
		final int halfArrLength;
		if(key<=(halfArrLength=(arrLength=arr.length)>>>1)) {
			return key;
		}else if(halfArrLength>(arrLength-=(Character.MAX_VALUE-key)-1)) {
			return halfArrLength;
		}
		return arrLength;
	}
	private void initialize1(final char loKey,final char hiKey)
	{
		this.arr=new char[] {loKey,hiKey};
		this.lo=0;
		this.hi=1;
	}
	
	private boolean addSize1(int occupiedIndex,final char key) {
		final char[] arr;
		final char arrKey;
		switch(Integer.signum((arrKey=(arr=this.arr)[occupiedIndex])-key))
		{
		case 0:
			//key already exists
			return false;
		case -1:
			//insert new key after arrKey
			if(++occupiedIndex==arr.length)
			{
				if(occupiedIndex==1){
					initialize1(arrKey,key);
				}else {
					arr[0]=key;
					this.hi=0;
				}
			}else {
				arr[occupiedIndex]=key;
				this.hi=occupiedIndex;
			}
			break;
		default:
			//insert new key before arrKey
			if(--occupiedIndex==-1 && (occupiedIndex=arr.length-1)==0)
			{
				initialize1(key,arrKey);
				break;
			}
			arr[occupiedIndex]=key;
			this.lo=occupiedIndex;
		}
		return true;
	}
	private boolean addFragmented(int lo,int hi,int hiLoDist,final char key) {
		//TODO
		throw new omni.util.NotYetImplementedException();
	}
	
	private boolean addNonfragmented(int lo,int hi,int hiLoDist,final char key) {
		final char[] arr;
		int mid;
		char midKey=(arr=this.arr)[mid=(lo+(hiLoDist>>>1))];
		boolean insertInLowerHalf;
		int tmpLo;
		int tmpHi;
		switch(Integer.signum(midKey-key))
		{
		case 0:
			return false;
		case -1:
			insertInLowerHalf=false;
			tmpLo=mid+1;
			tmpHi=hi;
			break;
		default:
			insertInLowerHalf=true;
			tmpLo=lo;
			tmpHi=mid-1;
		}
		while((hiLoDist=tmpHi-tmpLo)>=0)
		{
			mid=tmpLo+(hiLoDist>>>1);
			midKey=arr[mid];
			switch(Integer.signum(midKey-key))
			{
			case 0:
				return false;
			case -1:
				tmpLo=mid+1;
				break;
			default:
				tmpHi=mid-1;
			}
		}
		if(insertInLowerHalf)
		{
			if(lo==0)
			{
				if(hi==(lo=arr.length-1))
				{
					//grow array
					//TODO
					throw new omni.util.NotYetImplementedException();
				}else {
					if(tmpLo==0)
					{
						arr[lo]=key;
					}else {
						arr[lo]=arr[0];
						ArrCopy.semicheckedSelfCopy(arr, 0, 1, tmpLo-1);
						arr[tmpLo]=key;
						
					}
					this.lo=lo;
					
				}
			}else {
				ArrCopy.semicheckedSelfCopy(arr,hi=lo-1,lo,tmpLo-lo);
				arr[tmpLo]=key;
				this.lo=hi;
			}
		}
		else
		{
			if(hi==arr.length-1)
			{
				if(lo==0)
				{
					
					//grow array
					//TODO
					throw new omni.util.NotYetImplementedException();
				}else {
					if(tmpLo==hi+1)
					{
						arr[0]=key;
					}else {
						arr[0]=arr[hi];
						ArrCopy.semicheckedCopy(arr, tmpLo, arr, tmpLo+1, hi-tmpLo);
						arr[tmpLo]=key;
					}
					this.hi=0;
				}
			}else {
				ArrCopy.semicheckedCopy(arr, tmpLo, arr, tmpLo+1,(++hi)-tmpLo);
				this.hi=hi;
				arr[tmpLo]=key;
			}
		}
		return true;
	}
	
	public boolean add(final char key)
	{
		int hi;
		if((hi=this.hi)!=0) {
			final int lo,hiLoDist;
			switch(Integer.signum(hiLoDist=hi-(lo=this.lo)))
			{
			case -1:
				return addFragmented(lo,hi,hiLoDist,key);
			case 0:
				return addSize1(hi,key);
			default:
				return addNonfragmented(lo,hi,hiLoDist,key);
			}
		}else {
			initialize(key);
			return true;
		}
	}
	private void initialize(final char key) {
		int hi;
		char[] arr;
		if((arr=this.arr)==null){
			hi=0;
			this.arr=new char[] {key};
		}else if(arr==OmniArray.OfChar.DEFAULT_ARR) {
			this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
			arr[hi=getDefaultIndex(key)]=key;
		}else {
			arr[hi=getInitialIndex(arr,key)]=key;
		}
		this.hi=hi;
		this.lo=hi;
	}
}
