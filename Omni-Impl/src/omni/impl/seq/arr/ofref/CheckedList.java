package omni.impl.seq.arr.ofref;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
public class CheckedList<E>extends AbstractSeq.Checked<E> implements OmniList.OfRef<E>{
  CheckedList(){
    super();
  }
  CheckedList(int capacity){
    super(capacity);
  }
  CheckedList(int size,Object[] arr){
    super(size,arr);
  }
  @Override public Object clone(){
    final Object[] arr;
    final int size;
    if((size=this.size)!=0){
      ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
    }else{
      arr=null;
    }
    return new CheckedList<>(size,arr);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0){
      final var arr=this.arr;
      if(val!=null){
        final int expectedModCount=modCount;
        try{
          return uncheckedIndexOfMatch(arr,size,val::equals);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,modCount);
        }
      }
      return uncheckedIndexOfMatch(arr,size,Objects::isNull);
    }
    return -1;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new CheckedBidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new CheckedBidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    CheckedCollection.checkLo(index);
    CheckedCollection.checkWriteHi(index,size);
    return new CheckedBidirectionalItr<>(this,index);
  }
  @Override public E remove(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    ++modCount;
    final Object[] arr;
    @SuppressWarnings("unchecked") final var removed=(E)(arr=this.arr)[index];
    eraseIndexHelper(arr,index,--size);
    this.size=size;
    return removed;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    final int size;
    if((size=this.size)!=0){
      final int expectedModCount=modCount;
      try{
        uncheckedReplaceAll(arr,0,size,operator);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
  }
  @Override public void reverseSort(){
    final int size;
    if((size=this.size)>1){
      final int expectedModCount=modCount;
      try{
        uncheckedSort(arr,0,size-1,Comparator.reverseOrder());
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
  }
  @Override public void sort(){
    final int size;
    if((size=this.size)>1){
      final int expectedModCount=modCount;
      try{
        uncheckedSort(arr,0,size-1,Comparator.naturalOrder());
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1){
      final int expectedModCount=modCount;
      try{
        uncheckedSort(arr,0,size-1,sorter);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size);
    return new CheckedSubList<>(this,fromIndex,toIndex-fromIndex);
  }
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      final int modCount=this.modCount;
      try{
        forwardToString(arr,0,size,builder=new StringBuilder("["));
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override void uncheckedCopyInto(Object[] dst,int length){
    ArrCopy.uncheckedCopy(arr,0,dst,0,length);
  }
  @Override void uncheckedForEach(int size,Consumer<? super E> action){
    final int modCount=this.modCount;
    try{
      uncheckedForwardForEachInRange(arr,0,size,action);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override int uncheckedHashCode(int size){
    final int modCount=this.modCount;
    try{
      return forwardHashCode(arr,0,size);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override boolean uncheckedRemoveFirstMatch(int size,Predicate<Object> pred){
    final var arr=this.arr;
    int index=0;
    do{
      if(pred.test(arr[index])){
        ++modCount;
        eraseIndexHelper(arr,index,--size);
        this.size=size;
        return true;
      }
    }while(++index!=size);
    return false;
  }
  @Override boolean uncheckedRemoveFirstNonNull(int size,Object nonNull){
    final int expectedModCount=modCount;
    try{
      final var arr=this.arr;
      int index=0;
      do{
        if(nonNull.equals(arr[index])){
          CheckedCollection.checkModCount(expectedModCount,modCount);
          modCount=expectedModCount+1;
          eraseIndexHelper(arr,index,--size);
          this.size=size;
          return true;
        }
      }while(++index!=size);
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
    }
    CheckedCollection.checkModCount(expectedModCount,modCount);
    return false;
  }
  @Override void uncheckedToString(int size,StringBuilder builder){
    final int modCount=this.modCount;
    try{
      forwardToString(arr,0,size,builder);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
}