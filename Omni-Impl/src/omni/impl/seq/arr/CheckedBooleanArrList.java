package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class CheckedBooleanArrList extends AbstractBooleanArrSeq.Checked implements OmniList.OfBoolean{
  CheckedBooleanArrList(){
    super();
  }
  CheckedBooleanArrList(int capacity){
    super(capacity);
  }
  private CheckedBooleanArrList(int size,boolean[] arr){
    super(size,arr);
  }
  @Override public void add(int index,Boolean val){
    super.add(index,val);
  }
  @Override public Object clone(){
    final boolean[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new boolean[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedBooleanArrList(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean get(int index){
    return super.getBoolean(index);
  }
  @Override public OmniIterator.OfBoolean iterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfBoolean listIterator(){
    return new BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfBoolean listIterator(int index){
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new BidirectionalItr(this,index);
  }
  @Override public Boolean remove(int index){
    return super.removeBooleanAt(index);
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      uncheckedReverseSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public Boolean set(int index,Boolean val){
    return super.set(index,val);
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(arr,0,size-1);
      ++modCount;
    }
  }
  @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new SubList(this,fromIndex,toIndex-fromIndex);
  }
  @Override void uncheckedCopyInto(boolean[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Boolean[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(byte[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(char[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(double[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(float[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(int[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(long[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedCopyInto(short[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,BooleanConsumer action){
    final int modCount=this.modCount;
    try{
      uncheckedForwardForEachInRange(arr,0,size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override int uncheckedHashCode(int size){
    return forwardHashCode(arr,0,size);
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,boolean val){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==val){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    forwardToString(arr,0,size,builder);
  }
  private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfBoolean{
    private BidirectionalItr(Checked root){
      super(root);
    }
    private BidirectionalItr(Checked root,int cursor){
      super(root,cursor);
    }
    @Override public boolean hasPrevious(){
      return cursor!=0;
    }
    @Override public int nextIndex(){
      return cursor;
    }
    @Override public boolean previousBoolean(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int cursor;
      if((cursor=this.cursor)!=0){
        lastRet=--cursor;
        this.cursor=cursor;
        return root.arr[cursor];
      }
      throw new NoSuchElementException();
    }
    @Override public int previousIndex(){
      return cursor-1;
    }
    @Override public void set(boolean val){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.arr[lastRet]=val;
        return;
      }
      throw new IllegalStateException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      final Checked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        final int expectedModCount=modCount;
        try{
          uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,root.modCount);
        }
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      final Checked root;
      final int cursor,bound;
      if((cursor=this.cursor)!=(bound=(root=this.root).size)){
        final int expectedModCount=modCount;
        try{
          uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,root.modCount);
        }
        this.cursor=bound;
        lastRet=bound-1;
      }
    }
    @Override public boolean hasNext(){
      return cursor!=root.size;
    }
    @Override public boolean nextBoolean(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int cursor;
      if((cursor=this.cursor)!=root.size){
        lastRet=cursor;
        this.cursor=cursor+1;
        return root.arr[cursor];
      }
      throw new NoSuchElementException();
    }
    @Override public void remove(){
      final int lastRet;
      if((lastRet=this.lastRet)!=-1){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        eraseIndexHelper(root.arr,lastRet,--root.size);
        root.modCount=++modCount;
        this.modCount=modCount;
        cursor=lastRet;
        this.lastRet=-1;
        return;
      }
      throw new IllegalStateException();
    }
  }
  private static class SubList extends AbstractSubList implements OmniList.OfBoolean{
    private static void bubbleUpIncrementModCount(AbstractSubList parent){
      while(parent!=null){
        ++parent.modCount;
        parent=parent.parent;
      }
    }
    private SubList(Checked root,int rootOffset,int size){
      super(root,rootOffset,size);
    }
    private SubList(Checked root,SubList parent,int rootOffset,int size,int modCount){
      super(root,parent,rootOffset,size,modCount);
    }
    @Override public boolean add(Boolean val){
      return super.add(val);
    }
    @Override public void add(int index,Boolean val){
      super.add(index,val);
    }
    @Override public Object clone(){
      final var root=checkModCountAndGetRoot();
      final boolean[] arr;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new boolean[size],0,size);
      }else{
        arr=null;
      }
      return new CheckedBooleanArrList(size,arr);
    }
    @Override public boolean contains(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
    }
    @Override public boolean contains(double val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
          v=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          v=true;
        }else{
          return false;
        }
        final int rootOffset;
        return uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return false;
    }
    @Override public boolean contains(float val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
        }
        final int rootOffset;
        return uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return false;
    }
    @Override public boolean contains(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(val){
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
        }
        final int rootOffset;
        return uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return false;
    }
    @Override public boolean contains(long val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        if(val==0){
          v=false;
        }else if(val==1){
          v=true;
        }else{
          return false;
        }
        final int rootOffset;
        return uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return false;
    }
    @Override public boolean contains(Object val){
      final var root=checkModCountAndGetRoot();
      final int size,rootOffset;
      return (size=this.size)!=0&&val instanceof Boolean
          &&uncheckedContains(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
    }
    @Override public boolean equals(Object val){
      // TODO Auto-generated method stub
      return false;
    }
    @Override public void forEach(BooleanConsumer action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final int size;
      if((size=this.size)!=0){
        uncheckedForEach(size,action::accept);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public Boolean get(int index){
      return super.getBoolean(index);
    }
    @Override public int indexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int indexOf(double val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
          v=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          v=true;
        }else{
          return -1;
        }
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(float val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
        }
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(val){
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
        }
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(long val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        if(val==0){
          v=false;
        }else if(val==1){
          v=true;
        }else{
          return -1;
        }
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int indexOf(Object val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0&&val instanceof Boolean){
        final int rootOffset;
        return uncheckedIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
      }
      return -1;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public int lastIndexOf(boolean val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      return -1;
    }
    @Override public int lastIndexOf(double val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
          v=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          v=true;
        }else{
          return -1;
        }
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(float val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
        }
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(int val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(val){
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
        }
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(long val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        if(val==0){
          v=false;
        }else if(val==1){
          v=true;
        }else{
          return -1;
        }
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,v);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0&&val instanceof Boolean){
        final int rootOffset;
        return uncheckedLastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,(boolean)val);
      }
      return -1;
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      return new BidirectionalItr(this,modCount);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size);
      return new BidirectionalItr(this,modCount,index+rootOffset);
    }
    @Override public void put(int index,boolean val){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      root.arr[index+rootOffset]=val;
    }
    @Override public Boolean remove(int index){
      return super.removeBooleanAt(index);
    }
    @Override public boolean remove(Object val){
      final int size;
      if((size=this.size)!=0&&val instanceof Boolean){ return uncheckedRemoveFirstMatch(size,(boolean)val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(boolean val){
      final int size;
      if((size=this.size)!=0){ return uncheckedRemoveFirstMatch(size,val); }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
          v=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          v=true;
        }else{
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        }
        return uncheckedRemoveFirstMatch(size,v);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(Float.floatToRawIntBits(val)){
        default:
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
        }
        return uncheckedRemoveFirstMatch(size,v);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        switch(val){
        default:
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
        }
        return uncheckedRemoveFirstMatch(size,v);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      final int size;
      if((size=this.size)!=0){
        final boolean v;
        if(val==0){
          v=false;
        }else if(val==1){
          v=true;
        }else{
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        }
        return uncheckedRemoveFirstMatch(size,v);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private boolean uncheckedRemoveFirstMatch(int size,boolean val){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int offset;
      final int bound=(offset=rootOffset)+--size;
      final var arr=root.arr;
      do{
        if(arr[offset]==val){
          root.modCount=++modCount;
          this.modCount=modCount;
          eraseIndexHelper(arr,offset,--root.size);
          bubbleUpDecrementSize(parent);
          this.size=size;
          return true;
        }
      }while(++offset!=bound);
      return false;
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final int size;
      if((size=this.size)!=0){
        uncheckedReplaceAll(size,operator);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final int size;
      if((size=this.size)!=0){
        uncheckedReplaceAll(size,operator::apply);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void reverseSort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        uncheckedReverseSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementModCount(parent);
      }
    }
    @Override public Boolean set(int index,Boolean val){
      return super.set(index,val);
    }
    @Override public void sort(){
      int modCount;
      final Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        AbstractBooleanArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,size+rootOffset-1);
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementModCount(parent);
      }
    }
    @Override public void sort(BooleanComparator sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      final int size;
      if((size=this.size)>1){
        uncheckedSort(size,sorter::compare);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      final Checked root;
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
      return new SubList(root,this,rootOffset+fromIndex,toIndex-fromIndex,modCount);
    }
    @Override public Boolean[] toArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final Boolean[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Boolean[size],0,size);
        return dst;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final var root=this.root;
      final T[] dst;
      final int size,modCount=this.modCount;
      try{
        dst=arrConstructor.apply(size=this.size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      if(size!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override public <T> T[] toArray(T[] dst){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public boolean[] toBooleanArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final boolean[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new boolean[size],0,size);
        return dst;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final byte[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0){
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    private Checked checkModCountAndGetRoot(){
      final Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      return root;
    }
    private void uncheckedForEach(int size,BooleanConsumer action){
      final int modCount=this.modCount;
      final var root=this.root;
      try{
        int rootOffset;
        uncheckedForwardForEachInRange(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private void uncheckedReplaceAll(int size,BooleanPredicate operator){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractBooleanArrSeq.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private void uncheckedSort(int size,BooleanComparator sorter){
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        AbstractBooleanArrSeq.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      bubbleUpIncrementModCount(parent);
    }
    private static class BidirectionalItr extends AbstractBidirectionalItr implements OmniListIterator.OfBoolean{
      private BidirectionalItr(SubList parent,int modCount){
        super(parent,modCount);
      }
      private BidirectionalItr(SubList parent,int modCount,int cursor){
        super(parent,modCount,cursor);
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final Checked root;
          final AbstractSubList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
          root.modCount=++modCount;
          parent.modCount=modCount;
          this.modCount=modCount;
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          final var root=parent.root;
          final int expectedModCount=modCount;
          try{
            uncheckedForwardForEachInRange(root.arr,cursor,bound,action);
          }finally{
            CheckedCollection.checkModCount(expectedModCount,root.modCount);
          }
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int cursor,bound;
        final AbstractSubList parent;
        if((cursor=this.cursor)!=(bound=(parent=this.parent).getBound())){
          final var root=parent.root;
          final int expectedModCount=modCount;
          try{
            uncheckedForwardForEachInRange(root.arr,cursor,bound,action::accept);
          }finally{
            CheckedCollection.checkModCount(expectedModCount,root.modCount);
          }
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override public boolean hasNext(){
        return cursor!=parent.getBound();
      }
      @Override public boolean hasPrevious(){
        return cursor!=parent.rootOffset;
      }
      @Override public boolean nextBoolean(){
        final Checked root;
        final AbstractSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=parent.getBound()){
          lastRet=cursor;
          this.cursor=cursor+1;
          return root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public int nextIndex(){
        return cursor-parent.rootOffset;
      }
      @Override public boolean previousBoolean(){
        final Checked root;
        final AbstractSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        int cursor;
        if((cursor=this.cursor)!=parent.rootOffset){
          lastRet=--cursor;
          this.cursor=cursor;
          return root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public int previousIndex(){
        return cursor-parent.rootOffset-1;
      }
      @Override public void set(boolean val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
}