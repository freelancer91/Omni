package omni.impl.seq;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniStack;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.ToStringUtil.OmniStringBuilderByte;
public abstract class PackedBooleanArrSeq extends AbstractBooleanArrSeq implements OmniCollection.OfBoolean{
  public static class CheckedList extends UncheckedList{
    private static class Itr extends AbstractBooleanItr{
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedList parent){
        this.parent=parent;
        cursor=0;
        modCount=parent.modCount;
        lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        modCount=parent.modCount;
        lastRet=-1;
      }
      private Itr(Itr itr){
        parent=itr.parent;
        cursor=itr.cursor;
        lastRet=itr.lastRet;
        modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            // TODO
            // OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          // TODO
          // this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor) < (bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            // TODO
            // OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          // TODO
          // this.lastRet=lastRet;
        }
      }
      @Override public boolean hasNext(){
        return cursor < parent.size;
      }
      @Override public boolean nextBoolean(){
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=parent).modCount);
        final int cursor;
        if((cursor=this.cursor) < root.size){
          lastRet=cursor;
          this.cursor=cursor + 1;
          // TODO
          return false;
          // return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet) != -1){
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          // TODO
          // OmniArray.OfBoolean.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class ListItr extends Itr implements OmniListIterator.OfBoolean{
      private ListItr(CheckedList parent){
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor){
        super(parent,cursor);
      }
      private ListItr(ListItr itr){
        super(itr);
      }
      @Override public void add(boolean val){
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        lastRet=-1;
        final int rootSize;
        if((rootSize=root.size) != 0){
          // TODO
          // ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++cursor;
        }
      }
      @Override public Object clone(){
        return new ListItr(this);
      }
      @Override public boolean hasPrevious(){
        return cursor > 0;
      }
      @Override public int nextIndex(){
        return cursor;
      }
      @Override public boolean previousBoolean(){
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=parent).modCount);
        if((cursor=this.cursor) > 0){
          lastRet=--cursor;
          this.cursor=cursor;
          // TODO
          return false;
          // return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public int previousIndex(){
        return cursor - 1;
      }
      @Override public void set(boolean val){
        final int lastRet;
        if((lastRet=this.lastRet) != -1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=parent).modCount);
          // TODO
          // root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return modCount;
      }
    }
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedList(){
      super();
    }
    public CheckedList(int initialCapacity){
      super(initialCapacity);
    }
    CheckedList(int size,long[] words){
      super(size,words);
    }
    @Override public void add(int index,boolean val){
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      if(size != 0){
        // TODO
        // ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override public void clear(){
      if(size != 0){
        ++modCount;
        size=0;
      }
    }
    @Override public Object clone(){
      // final boolean[] copy;
      // final int size;
      // if((size=this.size)!=0){
      // ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      // }else{
      // copy=OmniArray.OfBoolean.DEFAULT_ARR;
      // }
      // return new CheckedList(size,copy);
      // TODO
      return null;
    }
    @Override public boolean equals(Object val){
      // TODO implements equals method for CheckedList
      return false;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      // TODO
      return false;
      // return (boolean)this.arr[index];
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size);
      return new ListItr(this,index);
    }
    @Override public void push(boolean val){
      ++modCount;
      super.push(val);
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      // TODO
      // this.arr[index]=val;
    }
    @Override public boolean removeBooleanAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final boolean[] arr;
      ++modCount;
      // TODO
      return false;
      // final var ret=(boolean)(arr=this.arr)[index];
      // OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
      // this.size=size;
      // return ret;
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final int size;
      if((size=this.size) != 0){
        final int modCount=this.modCount;
        try{
          // TODO
          // OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount + 1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final int size;
      if((size=this.size) != 0){
        final int modCount=this.modCount;
        try{
          // TODO
          // OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount + 1;
        }
      }
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      final boolean[] arr;
      // TODO
      return false;
      // final var ret=(boolean)(arr=this.arr)[index];
      // arr[index]=val;
      // return ret;
    }
    @Override public void sort(BooleanComparator sorter){
      final int size;
      if((size=this.size) > 1){
        if(sorter == null){
          // TODO
          // BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++modCount;
        }else{
          final int modCount=this.modCount;
          try{
            // TODO
            // BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount + 1;
          }
        }
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      final int size;
      if((size=this.size) > 1){
        if(sorter == null){
          // TODO
          // BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++modCount;
        }else{
          final int modCount=this.modCount;
          try{
            // TODO
            // BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter::compare);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount + 1;
          }
        }
      }
    }
    @Override public void stableAscendingSort(){
      final int size;
      if((size=this.size) > 1){
        // TODO
        // BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        modCount=modCount + 1;
      }
    }
    @Override public void stableDescendingSort(){
      final int size;
      if((size=this.size) > 1){
        // TODO
        // BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
        modCount=modCount + 1;
      }
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      // TODO
      return null;
      // return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override void uncheckedForEach(int size,BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        // TODO
        // OmniArray.OfBoolean.ascendingForEach(this.arr,0,size-1,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        // TODO
        // if(size!=(size-=uncheckedRemoveIfImpl(this.arr
        // ,0,size,filter,new ModCountChecker(modCount)))
        // ){
        // this.modCount=modCount+1;
        // this.size=size;
        // return true;
        // }
      }catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override boolean uncheckedremoveVal(int size,boolean val){
      // final var arr=this.arr;
      // for(int index=0;;){
      // if(
      // val==arr[index]
      // )
      // {
      // ++this.modCount;
      // OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
      // this.size=size;
      // return true;
      // }else if(++index==size){
      // return false;
      // }
      // }
      // TODO
      return false;
    }
  }
  public static class CheckedStack extends UncheckedStack{
    private static class Itr extends AbstractBooleanItr{
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedStack parent){
        this.parent=parent;
        cursor=parent.size;
        modCount=parent.modCount;
        lastRet=-1;
      }
      private Itr(Itr itr){
        parent=itr.parent;
        cursor=itr.cursor;
        lastRet=itr.lastRet;
        modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int cursor;
        if((cursor=this.cursor) > 0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          // TODO
          // try{
          // OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor-1,action);
          // }finally{
          // CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          // }
          this.cursor=0;
          lastRet=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int cursor;
        if((cursor=this.cursor) > 0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          // TODO
          // try{
          // OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor-1,action::accept);
          // }finally{
          // CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          // }
          this.cursor=0;
          lastRet=0;
        }
      }
      @Override public boolean hasNext(){
        return cursor > 0;
      }
      @Override public boolean nextBoolean(){
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=parent).modCount);
        int cursor;
        if((cursor=this.cursor) > 0){
          lastRet=--cursor;
          this.cursor=cursor;
          // TODO
          return false;
          // return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet) != -1){
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          // TODO
          // OmniArray.OfBoolean.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return modCount;
      }
    }
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
      super();
    }
    public CheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    CheckedStack(int size,long[] words){
      super(size,words);
    }
    @Override public void clear(){
      if(size != 0){
        ++modCount;
        size=0;
      }
    }
    @Override public Object clone(){
      // final boolean[] copy;
      // final int size;
      // if((size=this.size)!=0){
      // ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      // }else{
      // copy=OmniArray.OfBoolean.DEFAULT_ARR;
      // }
      // return new CheckedStack(size,copy);
      // TODO
      return null;
    }
    @Override public boolean equals(Object val){
      // TODO implements equals method for CheckedStack
      return false;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
    }
    @Override public Boolean poll(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // TODO
        return null;
        // final var ret=(Boolean)(arr[--size]);
        // this.size=size;
        // return ret;
      }
      return null;
    }
    @Override public boolean pollBoolean(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // TODO
        return false;
        // final var ret=(boolean)(arr[--size]);
        // this.size=size;
        // return ret;
      }
      return false;
    }
    @Override public byte pollByte(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=TypeUtil.castToByte(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=TypeUtil.castToChar(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=TypeUtil.castToDouble(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=TypeUtil.castToFloat(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Float.NaN;
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=(int)TypeUtil.castToByte(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long pollLong(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=TypeUtil.castToLong(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Long.MIN_VALUE;
    }
    @Override public short pollShort(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // final var ret=(short)TypeUtil.castToByte(arr[--size]);
        // this.size=size;
        // return ret;
        // TODO
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public boolean popBoolean(){
      int size;
      if((size=this.size) != 0){
        ++modCount;
        // TODO
        return false;
        // final var ret=(boolean)arr[--size];
        // this.size=size;
        // return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void push(boolean val){
      ++modCount;
      super.push(val);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override void uncheckedForEach(int size,BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        // TODO
        // OmniArray.OfBoolean.descendingForEach(this.arr,0,size-1,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        // if(size!=(size-=uncheckedRemoveIfImpl(this.arr
        // ,0,size,filter,new ModCountChecker(modCount)))
        // ){
        // this.modCount=modCount+1;
        // this.size=size;
        // return true;
        // }
        // TODO
      }catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override boolean uncheckedremoveVal(int size,boolean val){
      // final var arr=this.arr;
      // for(int index=--size;;--index){
      // if(
      // val==arr[index]
      // )
      // {
      // ++this.modCount;
      // OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,size);
      // this.size=size;
      // return true;
      // }else if(index==0){
      // return false;
      // }
      // }
      // TODO
      return false;
    }
  }
  public static class UncheckedList extends PackedBooleanArrSeq implements BooleanListDefault,Cloneable,RandomAccess{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,long[] words){
      super(size,words);
    }
    @Override public void add(int index,boolean val){
      // TODO Auto-generated method stub
    }
    @Override public Object clone(){
      int size;
      long[] words;
      if((size=this.size) != 0){
        final int arrLength;
        ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
      }else{
        words=null;
      }
      return new UncheckedList(size,words);
    }
    @Override public boolean equals(Object val){
      // TODO implement equals method
      return false;
    }
    @Override public boolean getBoolean(int index){
      return (words[index >> 6] & 1L << index) != 0;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      // TODO Auto-generated method stub
      return null;
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      // TODO Auto-generated method stub
      return null;
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      // TODO Auto-generated method stub
      return null;
    }
    @Override public void put(int index,boolean val){
      if(val){
        words[index >> 6]|=1L << index;
      }else{
        words[index >> 6]&=~(1L << index);
      }
    }
    @Override public boolean removeBooleanAt(int index){
      return (super.uncheckedRemoveIndex(index) & 1L << index) != 0;
    }
    private void uncheckedReplaceAll(int offset,int size,BooleanPredicate operator) {
        final long[] words;
        int wordOffset;
        for(var word=(words=this.words)[wordOffset=offset>>6];;) {
          final long mask;
          if(operator.test((word&(mask=1L<<offset))!=0)) {
            word|=mask;
          }else {
            word&=~mask;
          }
          if(++offset==size) {
            words[offset>>6]=word;
            return;
          }
          if((offset&63)==0) {
            words[wordOffset]=word;
            word=words[++wordOffset];
          }
        }
    }
    @Override public void replaceAll(BooleanPredicate operator){
      int size;
      if((size=this.size)!=0) {
        uncheckedReplaceAll(0,size,operator);
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      int size;
      if((size=this.size)!=0) {
        uncheckedReplaceAll(0,size,operator::apply);
      }
    }
    @Override public boolean set(int index,boolean val){
      final long mask;
      final long word;
      final long [] words;
      final int wordIndex;
      if(((word=(words=this.words)[wordIndex=index>>6])&(mask=1L<<index))!=0) {
        words[wordIndex]=word|mask;
        return true;
      }else {
        words[wordIndex]=word&~mask;
        return false;
      }
    }
    @Override public void sort(BooleanComparator sorter){
      // TODO Auto-generated method stub
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      // TODO Auto-generated method stub
    }
    @Override public void stableAscendingSort(){
      // TODO Auto-generated method stub
    }
    @Override public void stableDescendingSort(){
      // TODO Auto-generated method stub
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      // TODO Auto-generated method stub
      return null;
    }
    @Override void uncheckedCopyInto(boolean[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(word >> i & 1) != 0;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(Boolean[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(word >> i & 1) != 0;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(byte[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(byte)(word >> i & 1);
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(char[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(char)(word >> i & 1);
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=word >> i & 1;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=word >> i & 1;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(int)(word >> i & 1);
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=word >> i & 1;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(word >> i & 1) != 0;
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(short[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[i]=(short)(word >> i & 1);
          if(++i == length){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedForEach(int size,BooleanConsumer action){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          action.accept((word >> i & 1) != 0);
          if(++i == size){ return; }
        }while((i & 63) != 0);
      }
    }
    @Override int uncheckedHashCode(int size){
      int i;
      final long[] words;
      long word;
      int hash=((word=(words=this.words)[i=0]) & 1) != 0?1231:1237;
      while(++i != size){
        if((i & 63) == 0){
          word=words[i >> 6];
        }
        hash=hash * 31 + ((word >> i & 1) != 0?1231:1237);
      }
      return hash;
    }
    @Override boolean uncheckedremoveVal(int size,boolean val){
      final var words=this.words;
      final int bound;
      final var wordBound=(bound=size-1)>>6;
      long word;
      int tail0s,wordOffset=0;
      goToShiftDown:for(;;) {
        if(val) {
          for(;wordOffset<wordBound;++wordOffset) {
            if((tail0s=Long.numberOfTrailingZeros(word=words[wordOffset]))!=64) {
              break goToShiftDown;
            }
          }
          if((tail0s=Long.numberOfTrailingZeros((word=words[wordBound])&((1L<<size)-1)))!=64) {
            words[wordBound]=partialWordShiftDown(word,tail0s);
            this.size=bound;
            return true;
          }
          return false;
        }else {
          for(;wordOffset<wordBound;++wordOffset) {
            if((tail0s=Long.numberOfTrailingZeros(~(word=words[wordOffset])))!=64) {
              break goToShiftDown;
            }
          }
          if((tail0s=Long.numberOfTrailingZeros(~((word=words[wordBound])&((1L<<size)-1))))!=64) {
            words[wordBound]=partialWordShiftDown(word,tail0s);
            this.size=bound;
            return true;
          }
          return false;
        }
      }
      words[wordOffset]=partialWordShiftDown(word,tail0s) | (word=words[++wordOffset]) << 63;
      while(wordOffset != wordBound){
        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
      }
      words[wordBound]=word >>> 1;
      this.size=bound;
      return true;
      
//      final var words=this.words;
//      final int bound;
//      final var wordBound=(bound=size - 1) >> 6;
//      int lead0s;
//      long word;
//      int wordOffset;
//      goToShiftDown:for(;;){
//        if(val){
//          if((lead0s=Long.numberOfLeadingZeros((word=words[wordBound]) << -size)) != 64){
//            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
//            this.size=bound;
//            return true;
//          }
//          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
//            if((lead0s=Long.numberOfLeadingZeros(word=words[wordOffset])) != 64){
//              break goToShiftDown;
//            }
//          }
//        }else{
//          if((lead0s=Long.numberOfLeadingZeros(~((word=words[wordBound]) << -size))) != 64){
//            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
//            this.size=bound;
//            return true;
//          }
//          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
//            if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset]))) != 64){
//              break goToShiftDown;
//            }
//          }
//        }
//        return false;
//      }
//      words[wordOffset]=partialWordShiftDown(word,-lead0s - 1) | (word=words[++wordOffset]) << 63;
//      while(wordOffset != wordBound){
//        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
//      }
//      words[wordBound]=word >>> 1;
//      this.size=bound;
//      return true;
    }
    @Override int uncheckedToString(int size,byte[] buffer){
      final var words=this.words;
      for(int i=0,bufferOffset=1;;){
        final var word=words[i >> 6];
        do{
          bufferOffset=ToStringUtil.getStringBoolean((word >> i & 1) != 0,buffer,bufferOffset);
          if(++i == size){ return bufferOffset; }
          buffer[bufferOffset]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          ++bufferOffset;
        }while((i & 63) != 0);
      }
    }
    @Override void uncheckedToString(int size,OmniStringBuilderByte builder){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          builder.uncheckedAppendBoolean((word >> i & 1) != 0);
          if(++i == size){ return; }
          builder.uncheckedAppendCommaAndSpace();
        }while((i & 63) != 0);
      }
    }
  }
  public static class UncheckedStack extends PackedBooleanArrSeq implements OmniStack.OfBoolean{
    private static class Itr extends AbstractBooleanItr{
      transient final UncheckedStack root;
      transient int cursor;
      private Itr(Itr itr){
        root=itr.root;
        cursor=itr.cursor;
      }
      private Itr(UncheckedStack root){
        this.root=root;
        cursor=root.size;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        int cursor;
        if((cursor=this.cursor) != 0){
          root.uncheckedForEach(cursor,action);
          this.cursor=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        int cursor;
        if((cursor=this.cursor) != 0){
          root.uncheckedForEach(cursor,action::accept);
          this.cursor=0;
        }
      }
      @Override public boolean hasNext(){
        return cursor != 0;
      }
      @Override public boolean nextBoolean(){
        final int cursor;
        return (root.words[(cursor=--this.cursor) >> 6] & 1L << cursor) != 0;
      }
      @Override public void remove(){
        ((PackedBooleanArrSeq)root).uncheckedRemoveIndexNoRet(cursor);
      }
    }
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,long[] words){
      super(size,words);
    }
    @Override public Object clone(){
      int size;
      long[] words;
      if((size=this.size) != 0){
        final int arrLength;
        ArrCopy.uncheckedCopy(this.words,0,words=new long[arrLength=(size - 1 >> 6) + 1],0,arrLength);
      }else{
        words=null;
      }
      return new UncheckedStack(size,words);
    }
    @Override public boolean equals(Object val){
      // TODO implements equals method
      return false;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
    }
    @Override public Boolean peek(){
      int size;
      if((size=this.size) != 0){ return (words[--size >> 6] & 1L << size) != 0; }
      return null;
    }
    @Override public boolean peekBoolean(){
      int size;
      if((size=this.size) != 0){ return (words[--size >> 6] & 1L << size) != 0; }
      return false;
    }
    @Override public byte peekByte(){
      int size;
      if((size=this.size) != 0){ return (byte)(words[--size >> 6] >> size & 1); }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      int size;
      if((size=this.size) != 0){ return (char)(words[--size >> 6] >> size & 1); }
      return Character.MIN_VALUE;
    }
    @Override public double peekDouble(){
      int size;
      if((size=this.size) != 0){ return words[--size >> 6] >> size & 1L; }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      int size;
      if((size=this.size) != 0){ return words[--size >> 6] >> size & 1L; }
      return Float.NaN;
    }
    @Override public int peekInt(){
      int size;
      if((size=this.size) != 0){ return (int)(words[--size >> 6] >> size & 1); }
      return Integer.MIN_VALUE;
    }
    @Override public long peekLong(){
      int size;
      if((size=this.size) != 0){ return words[--size >> 6] >> size & 1L; }
      return Long.MIN_VALUE;
    }
    @Override public short peekShort(){
      int size;
      if((size=this.size) != 0){ return (short)(words[--size >> 6] >> size & 1); }
      return Short.MIN_VALUE;
    }
    @Override public Boolean poll(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (words[size >> 6] & 1L << size) != 0;
      }
      return null;
    }
    @Override public boolean pollBoolean(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (words[size >> 6] & 1L << size) != 0;
      }
      return false;
    }
    @Override public byte pollByte(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (byte)(words[size >> 6] >> size & 1);
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (char)(words[size >> 6] >> size & 1);
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return words[size >> 6] >> size & 1;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return words[size >> 6] >> size & 1;
      }
      return Float.NaN;
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (int)(words[size >> 6] >> size & 1);
      }
      return Integer.MIN_VALUE;
    }
    @Override public long pollLong(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return words[size >> 6] >> size & 1;
      }
      return Long.MIN_VALUE;
    }
    @Override public short pollShort(){
      int size;
      if((size=this.size) != 0){
        this.size=--size;
        return (short)(words[size >> 6] >> size & 1);
      }
      return Short.MIN_VALUE;
    }
    @Override void uncheckedCopyInto(boolean[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(word >> i & 1) != 0;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(Boolean[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(word >> i & 1) != 0;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(byte[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(byte)(word >> i & 1);
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(char[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(char)(word >> i & 1);
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=word >> i & 1;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=word >> i & 1;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(int)(word >> i & 1);
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=word >> i & 1;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(word >> i & 1) != 0;
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedCopyInto(short[] dst,int length){
      final var words=this.words;
      for(int i=0;;){
        final var word=words[i >> 6];
        do{
          dst[--length]=(short)(word >> i & 1);
          if(length == 0){ return; }
        }while((++i & 63) != 0);
      }
    }
    @Override void uncheckedForEach(int size,BooleanConsumer action){
      final var words=this.words;
      for(;;){
        final var word=words[--size >> 6];
        for(;;){
          action.accept((word >> size & 1) != 0);
          if(size == 0){ return; }
          if((size & 63) == 0){
            break;
          }
          --size;
        }
      }
    }
    @Override int uncheckedHashCode(int size){
      final long[] words;
      long word;
      int hash=((word=(words=this.words)[--size]) & 1) != 0?1231:1237;
      while(size != 0){
        if((--size & 63) == 63){
          word=words[size >> 6];
        }
        hash=hash * 31 + ((word >> size & 1) != 0?1231:1237);
      }
      return hash;
    }
    @Override boolean uncheckedremoveVal(int size,boolean val){
      final var words=this.words;
      final int bound;
      final var wordBound=(bound=size - 1) >> 6;
      int lead0s;
      long word;
      int wordOffset;
      goToShiftDown:for(;;){
        if(val){
          if((lead0s=Long.numberOfLeadingZeros((word=words[wordBound]) << -size)) != 64){
            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
            this.size=bound;
            return true;
          }
          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
            if((lead0s=Long.numberOfLeadingZeros(word=words[wordOffset])) != 64){
              break goToShiftDown;
            }
          }
        }else{
          if((lead0s=Long.numberOfLeadingZeros(~((word=words[wordBound]) << -size))) != 64){
            words[wordBound]=partialWordShiftDown(word,-lead0s - 1);
            this.size=bound;
            return true;
          }
          for(wordOffset=wordBound - 1;wordOffset >= 0;--wordOffset){
            if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset]))) != 64){
              break goToShiftDown;
            }
          }
        }
        return false;
      }
      words[wordOffset]=partialWordShiftDown(word,-lead0s - 1) | (word=words[++wordOffset]) << 63;
      while(wordOffset != wordBound){
        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
      }
      words[wordBound]=word >>> 1;
      this.size=bound;
      return true;
    }
    @Override int uncheckedToString(int size,byte[] buffer){
      final var words=this.words;
      for(int bufferOffset=1;;){
        final var word=words[--size >> 6];
        for(;;){
          bufferOffset=ToStringUtil.getStringBoolean((word >> size & 1) != 0,buffer,bufferOffset);
          if(size == 0){ return bufferOffset; }
          buffer[bufferOffset]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          ++bufferOffset;
          if((size & 63) == 0){
            break;
          }
          --size;
        }
      }
    }
    @Override void uncheckedToString(int size,OmniStringBuilderByte builder){
      final var words=this.words;
      for(;;){
        final var word=words[--size >> 6];
        for(;;){
          builder.uncheckedAppendBoolean((word >> size & 1) != 0);
          if(size == 0){ return; }
          builder.uncheckedAppendCommaAndSpace();
          if((size & 63) == 0){
            break;
          }
          --size;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  private static int getRemainingBitCountAndSet(final long[] words,int wordOffset,final int wordBound,
      final long valToSet){
    int bitCount=0;
    for(;;){
      words[wordOffset]=valToSet;
      if(++wordOffset == wordBound){ return bitCount; }
      bitCount+=Long.bitCount(words[wordOffset]);
    }
  }
  private static long partialWordShiftDown(long word,int shift){
    long mask;
    return word & (mask=(1L << shift) - 1) | word >>> 1 & ~mask;
  }
  transient long[] words;
  PackedBooleanArrSeq(){
    super();
  }
  PackedBooleanArrSeq(int initialCapacity){
    super();
    if(initialCapacity > 64){
      words=new long[(initialCapacity - 1 >> 6) + 1];
    }
  }
  PackedBooleanArrSeq(int size,long[] words){
    super(size);
    this.words=words;
  }
  @Override public boolean popBoolean(){
    final int size;
    return (words[(size=--this.size) >> 6] & 1L << size) != 0;
  }
  @Override public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException{
    int size;
    this.size=size=in.readInt();
    if(size != 0){
      long[] words;
      OmniArray.OfLong.readArray(words=new long[(size=size - 1 >> 6) + 1],0,size,in);
      this.words=words;
    }else{
      words=OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size != 0){
      OmniArray.OfLong.writeArray(words,0,size - 1 >> 6,out);
    }
  }
  private long uncheckedRemoveIndex(final int index){
    final long[] words;
    int wordOffset;
    final int wordBound,bound;
    final long retWord;
    var word=(words=this.words)[wordOffset=index >> 6]=partialWordShiftDown(retWord=words[wordOffset],index);
    if(wordOffset == (wordBound=(bound=size - 1) >> 6)){
      words[wordOffset]=word;
    }else{
      words[wordOffset]=word | (word=words[++wordOffset]) << 63;
      while(wordOffset != wordBound){
        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
      }
      words[wordBound]=word >>> 1;
    }
    size=bound;
    return retWord;
  }
  private void uncheckedRemoveIndexNoRet(final int index){
    final long[] words;
    int wordOffset;
    final int wordBound,bound;
    var word=(words=this.words)[wordOffset=index >> 6]=partialWordShiftDown(words[wordOffset],index);
    if(wordOffset == (wordBound=(bound=size - 1) >> 6)){
      words[wordOffset]=word;
    }else{
      words[wordOffset]=word | (word=words[++wordOffset]) << 63;
      while(wordOffset != wordBound){
        words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
      }
      words[wordBound]=word >>> 1;
    }
    size=bound;
  }
  @Override void uncheckedAppend(int size,boolean val){
    long[] words;
    int wordBound=size >> 6;
    if((words=this.words).length == (wordBound=size >> 6)){
      ArrCopy.uncheckedCopy(words,0,words=new long[OmniArray.growBy50Pct(wordBound)],0,wordBound);
      this.words=words;
    }
    if(val){
      words[wordBound]|=1L << size;
    }else{
      words[wordBound]&=~(1L << size);
    }
    this.size=size + 1;
  }
  @Override boolean uncheckedcontains(final int bound,final boolean val){
    final var words=this.words;
    final var wordBound=bound >> 6;
    final long finalWord;
    if(val){
      for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
        if(words[wordOffset] != 0){ return true; }
      }
      finalWord=words[wordBound];
    }else{
      for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
        if(words[wordOffset] != -1){ return true; }
      }
      finalWord=~words[wordBound];
    }
    return finalWord << -bound - 1 != 0;
  }
  @Override int uncheckedindexOf(int size,boolean val){
    final var words=this.words;
    final var wordBound=--size >> 6;
    final long finalWord;
    if(val){
      for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
        final int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(words[wordOffset])) != 64){ return tail0s + (wordOffset << 6); }
      }
      finalWord=words[wordBound];
    }else{
      for(var wordOffset=0;wordOffset < wordBound;++wordOffset){
        final int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(~words[wordOffset])) != 64){ return tail0s + (wordOffset << 6); }
      }
      finalWord=~words[wordBound];
    }
    final int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(finalWord)) > (size & 63)){ return -1; }
    return tail0s + (wordBound << 6);
  }
  @Override void uncheckedInit(boolean val){
    long[] words;
    if((words=this.words) == null){
      this.words=new long[]{val?1L:0L};
    }else{
      words[0]=val?1L:0L;
    }
  }
  @Override int uncheckedlastIndexOf(final int size,final boolean val){
    final var words=this.words;
    final int bound;
    var wordBound=(bound=size - 1) >> 6;
    if(val){
      int lead0s;
      if((lead0s=Long.numberOfLeadingZeros(words[wordBound] << -size)) != 64){ return bound - lead0s; }
      while(--wordBound >= 0){
        if((lead0s=Long.numberOfLeadingZeros(words[wordBound])) != 64){ return (wordBound + 1 << 6) - lead0s; }
      }
    }else{
      int lead0s;
      if((lead0s=Long.numberOfLeadingZeros(~(words[wordBound] << -size))) != 64){ return bound - lead0s; }
      while(--wordBound >= 0){
        if((lead0s=Long.numberOfLeadingZeros(~words[wordBound])) != 64){ return (wordBound + 1 << 6) - lead0s; }
      }
    }
    return -1;
  }
  @Override int uncheckedRemoveIfImpl(final int size,final BooleanPredicate filter){
    final var words=this.words;
    final int wordBound;
    int bitCount;
    if((bitCount=Long.bitCount(words[wordBound=size - 1 >> 6] << -size)) == 0){
      final var removeFalse=filter.test(false);
      for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
        if((bitCount+=Long.bitCount(words[wordOffset])) != 0){
          if(filter.test(true)){
            if(removeFalse){
              // remove all
              return size;
            }
            // remove only true
            return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
          }else{
            if(removeFalse){
              // remove only false
              return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
            }
            // remove none
            return 0;
          }
        }
      }
      // only false was encountered
      return removeFalse?size:0;
    }else if(bitCount == 64 - (-size & 63)){
      final var removeTrue=filter.test(true);
      for(int wordOffset=0;wordOffset < wordBound;++wordOffset){
        final int numTrue;
        bitCount+=numTrue=Long.bitCount(words[wordOffset]);
        if(numTrue != 64){
          if(filter.test(false)){
            if(removeTrue){
              // remove all
              return size;
            }
            /// remove only false
            return size - (bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,-1L));
          }else{
            if(removeTrue){
              // remove only true
              return bitCount + getRemainingBitCountAndSet(words,wordOffset,wordBound,0L);
            }
            // remove none
            return 0;
          }
        }
      }
      // only true was encountered
      return removeTrue?bitCount - size:0;
    }else{
      if(filter.test(true)){
        if(filter.test(false)){
          // remove all
          return size;
        }else{
          // remove only true
          return bitCount + getRemainingBitCountAndSet(words,0,wordBound,0L);
        }
      }else{
        if(filter.test(false)){
          // remove only false
          return size - (bitCount + getRemainingBitCountAndSet(words,0,wordBound,-1L));
        }else{
          // remove none
          return 0;
        }
      }
    }
  }
  @Override int uncheckedsearch(final int size,final boolean val){
    final var words=this.words;
    var wordBound=size - 1 >> 6;
    if(val){
      int lead0s;
      if((lead0s=Long.numberOfLeadingZeros(words[wordBound] << -size)) != 64){ return lead0s + 1; }
      while(--wordBound >= 0){
        if((lead0s=Long.numberOfLeadingZeros(words[wordBound])) != 64){ return size - ((wordBound + 1 << 6) - lead0s); }
      }
    }else{
      int lead0s;
      if((lead0s=Long.numberOfLeadingZeros(~(words[wordBound] << -size))) != 64){ return lead0s + 1; }
      while(--wordBound >= 0){
        if((lead0s=Long.numberOfLeadingZeros(~words[wordBound])) != 64){
          return size - ((wordBound + 1 << 6) - lead0s);
        }
      }
    }
    return -1;
  }
}
