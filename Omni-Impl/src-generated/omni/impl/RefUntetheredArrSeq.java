package omni.impl;
import omni.api.OmniCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import java.util.Comparator;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
abstract class RefUntetheredArrSeq<E> implements OmniCollection.OfRef<E>,Externalizable
{
  Object[] arr;
  int head;
  int tail;
  RefUntetheredArrSeq(int head,Object[] arr,int tail){
    super();
    this.arr=arr;
    this.head=head;
    this.tail=tail;
  }
  RefUntetheredArrSeq(){
    super();
    this.tail=-1;
  }
  @Override public int size(){
    int tail;
    if((tail=this.tail+1)>0 && (tail-=this.head)<=0){
      tail+=arr.length;
    }
    return tail;
  }
  @Override public boolean isEmpty(){
    return this.tail==-1;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr<E>(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr<E>(this,-1,0);
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  public void forEach(Consumer<? super E> action){
    final int tail;
    if((tail=this.tail)!=-1){
      ascendingForEach(tail,action);
    }
  }
  @Override public Object[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Object[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Object[size],0,size);
      }else{
        final Object[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Object[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int tail;
    if((tail=this.tail)!=-1){
      final T[] dst;
      final int head;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=arrConstructor.apply(size),0,size);
      }else{
        final Object[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=arrConstructor.apply(size+=arr.length),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return arrConstructor.apply(0);
  }
  @Override public <T> T[] toArray(T[] dst){
    int tail;
    if((tail=this.tail)!=-1){
      final int head;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }else{
        final Object[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public void clear(){
    final int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      int head;
      if((head=this.head)>tail){
        OmniArray.OfRef.nullifyRange(arr,arr.length-1,head);
        head=0;
      }
      OmniArray.OfRef.nullifyRange(arr,tail,head);
    }
  }
  public void addLast(E val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      this.insertAtTail(arr,val,this.head,tail);
    }else{
      if(arr==null){
        this.arr=new Object[]{val};
      }else{
        if(arr==OmniArray.OfRef.DEFAULT_ARR){
          this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }
        arr[0]=val;
      }
      this.head=0;
      this.tail=0;
    }
  }
  public void push(E val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      this.insertAtHead(arr,val,this.head,tail);
    }else{
      if(arr==null){
        this.arr=new Object[]{val};
        this.head=0;
        this.tail=0;
      }else if(arr==OmniArray.OfRef.DEFAULT_ARR){
        this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
      }else{
        arr[tail=arr.length-1]=val;
        this.tail=tail;
        this.head=tail;
      }
    }
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException
  {
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      int size;
      final int head;
      if((size=(tail+1)-(head=this.head))<=0){
        out.writeInt((size+(size=arr.length-1)));
        OmniArray.OfRef.writeArray(arr,head,size,out);
        OmniArray.OfRef.writeArray(arr,0,tail,out);
      }else{
        out.writeInt(size-1);
        OmniArray.OfRef.writeArray(arr,head,tail,out);
      }
    }else{
      out.writeInt(-1);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
    ,ClassNotFoundException
  {
    int tail;
    this.tail=tail=in.readInt();
    if(tail!=-1){
      this.head=0;
      final Object[] arr;
      OmniArray.OfRef.readArray(arr=new Object[tail+1],0,tail,in);
      this.arr=arr;
    }
  }
  boolean uncheckedRemoveLastMatch(int tail,@SuppressWarnings("rawtypes") final Predicate queryParam)
  {
    final int head;
    if((head=this.head)<=tail){
      return nonfragmentedRemoveLastMatch(head,tail,queryParam);
    }
    return fragmentedRemoveLastMatch(head,tail,queryParam);
  }
  boolean uncheckedRemoveFirstMatch(int tail,@SuppressWarnings("rawtypes") final Predicate queryParam)
  {
    final int head;
    if((head=this.head)<=tail){
      return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
    }
    return fragmentedRemoveFirstMatch(head,tail,queryParam);
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedRemoveLastMatch(int head,int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final var arr=this.arr;
    //search the upper half of the structure
    int index;
    for(int mid=(head+(index=tail))>>>1;;)
    {
      if(tester.test(arr[index]))
      {
        //found the element;
        if(tail==head)
        {
          //remove the last element
          this.tail=-1;
        }
        else
        {
          //pull the tail down
          ArrCopy.semicheckedSelfCopy(arr,index,index+1,tail-index);
          this.tail=tail-1;
        }
        arr[tail]=null;
        return true;
      }
      if(--index<mid){
        break;
      }
    }
    //search the lower half of the structure
    for(int headLength;(headLength=index-head)>=0;--index){
      if(tester.test(arr[index]))
      {
        //found the element, pull the head up
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        this.head=tail;
        arr[head]=null;
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedRemoveLastMatch(int head,int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final Object[] arr;
    final int bound=(arr=this.arr).length-1;
    for(int index=tail;;){
      if(tester.test(arr[index]))
      {
        final int tailLength,headLength;
        if((tailLength=tail-index)<(headLength=bound-head)+index){
          if(tail==0){
            this.tail=bound;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,index);
          arr[0]=arr[bound];
          if(headLength==0){
            this.head=0;
          }else{
            ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
            this.head=tail;
          }
          arr[head]=null;
        }
        return true;
      }
      if(--index==-1){
        break;
      }
    }
    for(int index=bound;;){
      if(tester.test(arr[index]))
      {
        final int tailLength,headLength;
        if((headLength=index-head)<(tailLength=bound-index)+tail)
        {
          if(head==bound){
            this.head=0;
          }else{
            ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
            this.head=tail;
          }
          arr[head]=null;
        }
        else
        {
          ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
          arr[bound]=arr[0];
          if(tail==0){
            this.tail=bound;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }
        return true;
      }
      if(--index<head){
        break;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedRemoveFirstMatch(int head,int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final var arr=this.arr;
    int index;
    //search the lower half of the structure
    for(int mid=((index=head)+tail)>>>1;;){
      if(tester.test(arr[index]))
      {
        //found the element
        if(tail==head)
        {
          //remove the last element
          this.tail=-1;
        }
        else
        {
          //pull the head up
          ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,index-head);
          this.head=tail;
        }
        arr[head]=null;
        return true;
      }
      if(++index>mid)
      {
        break;
      }
    }
    //search the upper half of the structure
    for(int tailLength;(tailLength=tail-index)>=0;++index){
      if(tester.test(arr[index]))
      {
        //found the element, pull the tail down
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        arr[tail]=null;
        this.tail=tail-1;
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedRemoveFirstMatch(int head,int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final Object[] arr;
    final int bound=(arr=this.arr).length-1;
    for(int index=head;;){
      if(tester.test(arr[index]))
      {
        final int headLength,tailLength;
        if((headLength=index-head)<tail+(tailLength=bound-index))
        {
          if(head==bound){
            this.head=0;
          }else{
            ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
            this.head=tail;
          }
          arr[head]=null;
        }
        else
        {
          ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
          arr[bound]=arr[0];
          if(tail==0)
          {
            this.tail=bound;
          }
          else
          {
            ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }
        return true;
      }
      if(++index>bound)
      {
        break;
      }
    }
    for(int index=0;;){
      if(tester.test(arr[index]))
      {
        final int headLength,tailLength;
        if((tailLength=tail-index)<(headLength=bound-head)+index)
        {
          if(tail==0){
            this.tail=bound;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
            this.tail=tail-1;
          }
          arr[tail]=null;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,0,arr,1,index);
          arr[0]=arr[bound];
          if(headLength==0){
            this.head=0;
          }else{
            ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
            this.head=tail;
          }
          arr[head]=null;
        }
        return true;
      }
      if(++index>tail){
        break;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  int uncheckedSearch(int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final var arr=this.arr;
    int count=1;
    int head;
    if((head=this.head)>tail){
      for(final int bound=arr.length;;)
      {
        if(tester.test(arr[head]))
        {
          return count;
        }
        ++count;
        if(++head==bound)
        {
          head=0;
          break;
        }
      }
    }
    for(;;)
    {
      if(tester.test(arr[head]))
      {
        return count;
      }
      if(head==tail)
      {
        return -1;
      }
      ++count;
      ++head;
    }
  }
  @SuppressWarnings("unchecked")
  boolean uncheckedContainsMatch(int tail,@SuppressWarnings("rawtypes") final Predicate tester)
  {
    final var arr=this.arr;
    int head;
    if((head=this.head)>tail)
    {
      for(;;)
      {
        if(tester.test(arr[tail]))
        {
          return true;
        }
        if(tail==0)
        {
          tail=arr.length-1;
          break;
        }
        --tail;
      }
    }
    for(;;)
    {
      if(tester.test(arr[head]))
      {
        return true;
      }
      if(head==tail)
      {
        return false;
      }
      ++head;
    }
  }
  public E poll(){
    return pollFirst();
  }
  @SuppressWarnings("unchecked")
  public E pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (E)(uncheckedRemoveFirst(tail));
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  public E pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (E)(uncheckedRemoveLast(tail));
    }
    return null;
  }
  void insertAtTail(Object[] arr,E key,int head,int tail){
    switch(Integer.signum((++tail)-head)){
      case 0:
        //fragmented must grow
        final Object[] tmp;
        int arrLength;
        ArrCopy.uncheckedCopy(arr,0,tmp=new Object[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
        ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
        this.head=head;
        this.arr=arr=tmp;
        break;
      default:
        //nonfragmented
        if(tail==arr.length){
          if(head==0){
            //must grow
            ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(tail)],0,tail);
            this.arr=arr;
          }else{
            tail=0;
          }
        }
      case -1:
        //fragmented
    }
    arr[tail]=key;
    this.tail=tail;
  }
  void insertAtHead(Object[] arr,E key,int head,int tail){
    int newHead;
    switch(Integer.signum(tail-(newHead=head-1))){
      case 0:
        //fragmented must grow
        final Object[] tmp;
        int arrLength;
        ArrCopy.uncheckedCopy(arr,0,tmp=new Object[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
        ArrCopy.uncheckedCopy(arr,head,tmp,newHead=tail-(arrLength-=head),arrLength);
        --newHead;
        this.arr=arr=tmp;
        break;
      default:
        //nonfragmented
        if(newHead==-1 && tail==(newHead=arr.length-1)){
          //must grow
          this.tail=(newHead=OmniArray.growBy50Pct(++tail))-1;
          ArrCopy.uncheckedCopy(arr,0,arr=new Object[newHead],newHead-=(tail),tail);
          --newHead;
          this.arr=arr;
        }
      case -1:
        //fragmented
    }
    arr[newHead]=key;
    this.head=newHead;
  }
  void insertAtMiddle(E key){
    Object[] arr;
    if((arr=this.arr)==null){
      this.arr=new Object[]{key};
      this.head=0;
      this.tail=0;
    }else if(arr==OmniArray.OfRef.DEFAULT_ARR){
      this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
      arr[OmniArray.DEFAULT_ARR_SEQ_CAP/2]=key;
      this.head=OmniArray.DEFAULT_ARR_SEQ_CAP/2;
      this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP/2;
    }else{
      final int index;
      arr[index=(arr.length>>1)]=key;
      this.tail=index;
      this.head=index;
    }
  }
  @SuppressWarnings("unchecked")
  boolean uncheckedAdd(int tail,E key,Comparator<E> sorter)
  {
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head))
    {
      switch(sorter.compare(key,(E)arr[0]))
      {
        case 0:
          return false;
        case -1:
          return fragmentedInsertLo(arr,head,tail,key,sorter);
        default:
          return fragmentedInsertHi(arr,head,tail,key,sorter);
      }
    }
    else
    {
      final int mid;
      switch(sorter.compare(key,(E)arr[mid=(head+tail)>>>1]))
      {
        case 0:
          return false;
        case -1:
          return nonfragmentedInsertLo(arr,head,mid-1,key,sorter);
        default:
          return nonfragmentedInsertHi(arr,mid+1,tail,key,sorter);
      }
    }
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedInsertHi(Object[] arr,int head,int tail,E key,Comparator<E> sorter){
    int lo=1;
    int hi=tail;
    while(lo<=hi)
    {
      final int mid;
      switch(sorter.compare(key,(E)arr[ mid=(hi+lo)>>>1]))
      {
        case 0:
          return false;
        case -1:
          hi=mid-1;
          break;
        default:
          lo=mid+1;
      }
    }
    final int arrLength;
    final int headDist=(arrLength=arr.length)-head;
    if(++tail==head)
    {
      //must grow
      final Object[] tmp;
      //allocate a new array and assign the key
      (tmp=new Object[hi=OmniArray.growBy50Pct(arrLength)])[lo]=key;
      if(lo==0)
      {
        //inserting at index 0, copy the span from [0->tail] to the right one
        ArrCopy.uncheckedCopy(arr,0,tmp,1,tail);
      }
      else
      {
        //inserting at 0<index<=tail
        //copy [0->index]
        ArrCopy.uncheckedCopy(arr,0,tmp,0,lo);
        //copy [index->tail] but move it right one
        ArrCopy.semicheckedCopy(arr,lo,tmp,lo+1,tail-lo);
      }
      //copy the head span [head->arrBound]
      ArrCopy.uncheckedCopy(arr,head,tmp,(hi-=headDist),headDist);
      this.head=hi;
      this.tail=tail;
      this.arr=tmp;
    }
    else
    {
      int tailDist;
      if((tailDist=tail-lo)<=headDist+lo)
      {
        //shift the elements AFTER the insertion point right
        ArrCopy.semicheckedCopy(arr,lo,arr,lo+1,tailDist);
        this.tail=tail;
      }
      else
      {
        //shift the elements BEFORE the insertion point left (wrap around the break)
        ArrCopy.uncheckedSelfCopy(arr,tail=head-1,head,headDist);
        arr[arrLength-1]=arr[0];
        ArrCopy.semicheckedSelfCopy(arr,0,1,lo-1);
        this.head=tail;
      }
      arr[lo]=key;
    }
    return true;
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedInsertLo(Object[] arr,int head,int tail,E key,Comparator<E> sorter){
    int arrBound;
    int hi=(arrBound=arr.length)-1;
    int lo=head;
    do
    {
      final int mid;
      switch(sorter.compare(key,(E)arr[mid=(hi+lo)>>>1]))
      {
        case 0:
          return false;
        case -1:
          hi=mid-1;
          break;
        default:
          lo=mid+1;
      }
    }while(lo<=hi);
    final int headDist=lo-head;
    if(++tail==head)
    {
      //must grow
      final Object[] tmp;
      //Copy the span from [0->tail]
      ArrCopy.uncheckedCopy(arr,0,tmp=new Object[hi=OmniArray.growBy50Pct(arrBound)],0,tail);
      //copy the span from [index->arrBound]
      ArrCopy.semicheckedCopy(arr,lo,tmp,hi-=(arrBound-=lo),arrBound);
      //insert the new key
      tmp[--hi]=key;
      //copy the span from [head->index]
      ArrCopy.semicheckedCopy(arr,head,tmp,hi-=headDist,headDist);
      this.head=hi;
      this.arr=tmp;
    }
    else
    {
      if(headDist<=((hi=(--arrBound)-lo)+(tail)))
      {
        //move the elements BEFORE the insertion point left
        ArrCopy.semicheckedSelfCopy(arr,tail=head-1,head,headDist);
        this.head=tail; 
      }
      else
      {
        //move the points AFTER the insertion point right (wrap around the break)
        ArrCopy.uncheckedCopy(arr,0,arr,1,tail);
        arr[0]=arr[arrBound];
        ArrCopy.semicheckedCopy(arr,lo,arr,lo+1,hi);
        this.tail=tail;
      }
      arr[lo]=key;
    }
    return true;
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedInsertLo(Object[] arr,int head,int hi,E key,Comparator<E> sorter){
    int lo=head;
    while(lo<=hi)
    {
      final int mid;
      switch(sorter.compare(key,(E)arr[mid=(lo+hi)>>>1]))
      {
        case 0:
          return false;
        case -1:
          hi=mid-1;
          break;
        default:
          lo=mid+1;
      }
    }
    int headDist;
    if((headDist=lo-head)==0){
      //inserting at index==head
      if(--head==-1 && this.tail==(head=arr.length-1)){
        //must grow
        ArrCopy.uncheckedCopy(arr,0,arr=new Object[headDist=OmniArray.growBy50Pct(++head)],lo=headDist-head,head);
        this.tail=headDist-1;
        this.head=--lo;
        arr[lo]=key;
        this.arr=arr;
      }else{
        arr[head]=key;
        this.head=head;
      }
    }else{
      if(head==0){
        if(this.tail==(head=arr.length-1)){
          //must grow
          final var tmp=new Object[headDist=OmniArray.growBy50Pct(++head)];
          this.tail=headDist-1;
          ArrCopy.uncheckedCopy(arr,lo,tmp,head=headDist-(headDist=head-lo),headDist);
          tmp[--head]=key;
          ArrCopy.uncheckedCopy(arr,0,tmp,head-=lo,lo);
          this.arr=tmp;
        }else{
          arr[head]=arr[0];
          ArrCopy.semicheckedSelfCopy(arr,0,1,headDist-1);
          arr[lo]=key;
        }
        this.head=head;
      }else{
        ArrCopy.uncheckedSelfCopy(arr,hi=head-1,head,headDist);
        arr[lo]=key;
        this.head=hi;
      }
    }
    return true;
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedInsertHi(Object[] arr,int lo,int tail,E key,Comparator<E> sorter){
    int hi=tail;
    while(lo<=hi)
    {
      final int mid;
      switch(sorter.compare(key,(E)arr[mid=(lo+hi)>>>1]))
      {
        case 0:
          return false;
        case -1:
          hi=mid-1;
          break;
        default:
          lo=mid+1;
      }
    }
    int tailDist;
    if((tailDist=(++tail-lo))==0){
      //inserting at index==tail+1
      if(tail==arr.length && head==0){
        //must grow
        ArrCopy.uncheckedCopy(arr,0,arr=new Object[head=OmniArray.growBy50Pct(tail)],0,tail);
        this.tail=tail;
        arr[lo]=key;
        this.arr=arr;
      }else{
        arr[0]=key;
        this.tail=0;
      }
    }else{
      if(tail==arr.length){
        if(this.head==0){
          //must grow
          final Object[] tmp;
          ArrCopy.uncheckedCopy(arr,0,tmp=new Object[hi=OmniArray.growBy50Pct(tail)],0,lo);
          tmp[lo]=key;
          ArrCopy.uncheckedCopy(arr,lo,tmp,lo+1,tailDist);
          this.tail=tail;
          this.arr=tmp;
        }else{
          arr[0]=arr[tail-1];
          ArrCopy.semicheckedCopy(arr,lo,arr,lo+1,tailDist-1);
          arr[lo]=key;
          this.tail=0;
        }
      }else{
        ArrCopy.uncheckedCopy(arr,lo,arr,lo+1,tailDist);
        arr[lo]=key;
        this.tail=tail;
      }
    }
    return true;
  }
  Object uncheckedRemoveLast(int tail){
    final Object[] arr;
    final var ret=(arr=this.arr)[tail];
    arr[tail]=null;
    switch(Integer.signum(tail-this.head))
    {
      case 0:
        this.tail=-1;
        return ret;
      case -1:
        //fragmented
        if(--tail==-1){
          tail=arr.length-1;
        }
        break;
      default:
        --tail;
    }
    this.tail=tail;
    return ret;
  }
  Object uncheckedRemoveFirst(int tail){
    int head;
    final Object[] arr;
    final var ret=(arr=this.arr)[head=this.head];
    arr[head]=null;
    switch(Integer.signum(tail-head))
    {
      case 0:
        this.tail=-1;
        return ret;
      case -1:
        //fragmented
        if(++head==arr.length){
          head=0;
        }
        break;
      default:
        ++head;
    }
    this.head=head;
    return ret;
  }
  @SuppressWarnings("unchecked")
  void ascendingForEach(int tail,Consumer<? super E> action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      for(int bound=arr.length;;){
        action.accept((E)arr[head]);
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;){
      action.accept((E)arr[head]);
      if(head==tail){
        break;
      }
      ++head;
    }
  }
  @SuppressWarnings("unchecked")
  void descendingForEach(int tail,Consumer<? super E> action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      for(;;){
        action.accept((E)arr[tail]);
        if(tail==0){
          tail=arr.length-1;
          break;
        }
        --tail;
      }
    }
    for(;;){
      action.accept((E)arr[tail]);
      if(tail==head){
        return;
      }
      --tail;
    }
  }
  @SuppressWarnings("unchecked")
  private static <E> int nonfragmentedPullDown(final Object[] arr,int dst,int src,int bound,final Predicate<? super E> filter){
    for(;src<=bound;++src){
      final Object tmp;
      if(!filter.test((E)(tmp=arr[src]))){
        arr[dst++]=tmp;
      }
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    final Object[] arr;
    if(filter.test((E)(arr=this.arr)[head])){
      for(int src=head+1;head<=tail;++src){
        if(!filter.test((E)arr[src])){
          OmniArray.OfRef.nullifyRange(arr,src-1,head);
          this.head=src;
          while(++src<=tail){
            if(filter.test((E)arr[src])){
              this.tail=(src=nonfragmentedPullDown(arr,src,src+1,tail,filter))-1;
              OmniArray.OfRef.nullifyRange(arr,tail,src);
              break;
            }
          }
          return true;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,tail,head);
      this.tail=-1;
      return true;
    }else{
      while(++head<=tail){
        if(filter.test((E)arr[head])){
          this.tail=(head=nonfragmentedPullDown(arr,head,head+1,tail,filter))-1;
          OmniArray.OfRef.nullifyRange(arr,tail,head);
          return true;
        }
      }
      return false;
    }
  }
  @SuppressWarnings("unchecked")
  private static <E> int fragmentedPullDown(final Object[] arr,int src,int arrBound,int tail,final Predicate<? super E> filter){
    int dst=nonfragmentedPullDown(arr,src,src+1,arrBound,filter);
    for(src=0;;++src){
      final Object tmp;
      if(!filter.test((E)(tmp=arr[src]))){
        arr[dst]=tmp;
        if(dst==arrBound){
          OmniArray.OfRef.nullifyRange(arr,tail,src=nonfragmentedPullDown(arr,0,src+1,tail,filter));
          return src-1;
        }
        ++dst;
      }
      if(src==tail){
        OmniArray.OfRef.nullifyRange(arr,tail,0);
        OmniArray.OfRef.nullifyRange(arr,arrBound,dst);
        return dst-1;
      }
    }
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    final Object[] arr;
    if(filter.test((E)(arr=this.arr)[head])){
      for(int bound=arr.length-1;;){
        arr[head]=null;
        if(head==bound){
          break;
        }
        if(!filter.test((E)arr[++head])){
          this.head=head;
          while(head!=bound){
            if(filter.test((E)arr[++head])){
              this.tail=fragmentedPullDown(arr,head,bound,tail,filter);
              return true;
            }
          }
          for(head=0;!filter.test((E)arr[head]);++head){
            if(head==tail){
              return true;
            }
          }
          this.tail=(head=nonfragmentedPullDown(arr,head,head+1,tail,filter))-1;
          OmniArray.OfRef.nullifyRange(arr,tail,head);
          return true;
        }
      }
      for(head=0;filter.test((E)arr[head]);++head){
        arr[head]=null;
        if(head==tail){
          this.tail=-1;
          return true;
        }
      }
      this.head=head;
      while(++head<=tail){
        if(filter.test((E)arr[head])){
          this.tail=(head=nonfragmentedPullDown(arr,head,head+1,tail,filter))-1;
          OmniArray.OfRef.nullifyRange(arr,tail,head);
          break;
        }
      }
      return true;
    }else{
      for(int bound=arr.length-1;++head<=bound;){
        if(filter.test((E)arr[head])){
          this.tail=fragmentedPullDown(arr,head,bound,tail,filter);
          return true;
        }
      }
      for(head=0;!filter.test((E)arr[head]);++head){
        if(head==tail){
          return false;
        }
      }
      this.tail=(head=nonfragmentedPullDown(arr,head,head+1,tail,filter))-1;
      OmniArray.OfRef.nullifyRange(arr,tail,head+1);
      return true;
    }
  }
  @SuppressWarnings("unchecked")
  boolean uncheckedRemoveIf(int tail,Predicate<? super E> filter){
    int head;
    switch(Integer.signum(tail-(head=this.head))){
      case -1:
      {
        return fragmentedRemoveIf(head,tail,filter);
      }
      case 0:
      {
        final Object[] arr;
        if(filter.test((E)(arr=this.arr)[head])){
          arr[head]=null;
          this.tail=-1;
          return true;
        }
        return false;
      }
      default:
      {
        return nonfragmentedRemoveIf(head,tail,filter);
      }
    }
  }
  static abstract class AbstractUntetheredArrSeqItr<E> implements OmniIterator.OfRef<E>{
    transient final RefUntetheredArrSeq<E> root;
    transient int index;
    transient int numLeft;
    AbstractUntetheredArrSeqItr(RefUntetheredArrSeq<E> root,int index,int numLeft){
      this.root=root;
      this.index=index;
      this.numLeft=numLeft;
    }
    public abstract Object clone();
    public boolean hasNext(){
      return this.numLeft>0;
    }
    @SuppressWarnings("unchecked")
    public E next(){
      --numLeft;
      final Object[] arr;
      final int index;
      iterateIndex(index=this.index,arr=this.root.arr);
      return (E)arr[index];
    }
    public void remove(){
      final RefUntetheredArrSeq<E> root;
      final int head;
      int tail;
      switch(Integer.signum((tail=(root=this.root).tail)-(head=root.head))){
        case -1:
          fragmentedRemove(root,head,tail);
          break;
        case 0:
          root.arr[tail]=null;
          root.tail=-1;
          break;
        default:
          nonfragmentedRemove(root,head,tail);
      }
    }
    public void forEachRemaining(Consumer<? super E> action){
      if(numLeft!=0){
        uncheckedForEachRemaining(action);
      }
    }
    abstract void iterateIndex(int index,final Object[] arr);
    abstract void fragmentedRemove(final RefUntetheredArrSeq<E> root,int head,int tail);
    abstract void nonfragmentedRemove(final RefUntetheredArrSeq<E> root,int head,int tail);
    abstract void uncheckedForEachRemaining(final Consumer<? super E> action);
  }
  static class AscendingUntetheredArrSeqItr<E> extends AbstractUntetheredArrSeqItr<E>{
    AscendingUntetheredArrSeqItr(RefUntetheredArrSeq<E> root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new AscendingUntetheredArrSeqItr<E>(root,index,numLeft);
    }
    @Override void iterateIndex(int index,final Object[] arr){
      if(++index==arr.length){
        index=0;
      }
      this.index=index;
    }
    @Override void nonfragmentedRemove(final RefUntetheredArrSeq<E> root,final int head,int tail){
      final var arr=root.arr;
      final int index,headLength,tailLength;
      if((headLength=(index=this.index-1)-head)<=(tailLength=tail-index)){
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        arr[head]=null;
        root.head=tail;
      }else{
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        arr[tail]=null;
        root.tail=tail-1;
        this.index=index;
      }
    }
    @Override void fragmentedRemove(final RefUntetheredArrSeq<E> root,final int head,int tail){
      final var arr=root.arr;
      final int headLength;
      int index;
      if((index=this.index-1)==-1){
        if((headLength=(index=arr.length-1)-head)<=1+tail){
          if(headLength==0){
            root.head=0;
          }else{
            ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
            root.head=tail;
          }
          arr[head]=null;
        }else{
          arr[index]=arr[0];
          if(tail==0){
            root.tail=index;
          }else{
            ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
            root.tail=tail-1;
          }
          arr[tail]=null;
          this.index=index;
        }
      }else{
        int tailLength;
        if(index<head){
          if((headLength=arr.length-head-1)+index<(tailLength=tail-index)){
            ArrCopy.semicheckedCopy(arr,0,arr,1,index);
            arr[0]=arr[headLength+head];
            if(headLength==0){
              root.head=0;
            }else{
              ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
              root.head=tail;
            }
            arr[head]=null;
          }else{
            if(tail==0){
              root.tail=arr.length-1;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
              root.tail=tail-1;
            }
            arr[tail]=null;
            this.index=index;
          }
        }else{
          if((headLength=index-head)<=(tailLength=arr.length-index)+tail){
            if(headLength+tailLength==1){
              root.head=0;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
              root.head=tail;
            }
            arr[head]=null;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,--tailLength);
            arr[tailLength+=index]=arr[0];
            if(tail==0){
              root.tail=tailLength;
            }else{
              ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
              root.tail=tail-1;
            }
            arr[tail]=null;
            this.index=index;
          }
        }
      }
    }
    @SuppressWarnings("unchecked")
    @Override void uncheckedForEachRemaining(final Consumer<? super E> action){
      final RefUntetheredArrSeq<E> root;
      final int tail;
      int index;
      final var arr=(root=this.root).arr;
      if((index=this.index)>(tail=root.tail)){
        for(int bound=arr.length;;){
          action.accept((E)arr[index]);
          if(++index==bound){
            index=0;
            break;
          }
        }
      }
      do{
        action.accept((E)arr[index]);
      }while(++index<=tail);
      this.index=index;
    }
  }
  static class DescendingUntetheredArrSeqItr<E> extends AbstractUntetheredArrSeqItr<E>{
    DescendingUntetheredArrSeqItr(RefUntetheredArrSeq<E> root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new AscendingUntetheredArrSeqItr<E>(root,index,numLeft);
    }
    @Override void iterateIndex(int index,final Object[] arr){
      if(--index==-1){
        index=arr.length-1;
      }
      this.index=index;
    }
    @Override void nonfragmentedRemove(final RefUntetheredArrSeq<E> root,final int head,int tail){
      final var arr=root.arr;
      final int index,headLength,tailLength;
      if((headLength=(index=this.index+1)-head)<=(tailLength=tail-index)){
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        arr[head]=null;
        root.head=tail;
        this.index=index;
      }else{
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        arr[tail]=null;
        root.tail=tail-1;
      }
    }
    @Override void fragmentedRemove(final RefUntetheredArrSeq<E> root,final int head,int tail){
      final Object[] arr;
      final int headLength;
      int index;
      if((index=this.index+1)==(arr=root.arr).length){
        if((headLength=index-head-1)<tail){
          arr[0]=arr[index-1];
          if(headLength==0){
            root.head=0;
          }else{
            ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
            root.head=tail;
          }
          arr[head]=null;
          this.index=0;
        }else{
          if(tail==0){
            root.tail=index-1;
          }else{
            ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
            root.tail=tail-1;
          }
          arr[tail]=null;
        }
      }else{
        int tailLength;
        if(index<head){
          if((headLength=arr.length-head-1)+index<(tailLength=tail-index)){
            ArrCopy.semicheckedCopy(arr,0,arr,1,index);
            arr[0]=arr[headLength+head];
            if(headLength==0){
              root.head=0;
            }else{
              ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
              root.head=tail;
            }
            arr[head]=null;
            this.index=index;
          }else{
            if(tail==0){
              root.tail=arr.length-1;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
              root.tail=tail-1;
            }
            arr[tail]=null;
          }
        }else{
          if((headLength=index-head)<=(tailLength=arr.length-index)+tail){
            if(headLength+tailLength==1){
              root.head=0;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
              root.head=tail;
            }
            arr[head]=null;
            this.index=index;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,--tailLength);
            arr[tailLength+=index]=arr[0];
            if(tail==0){
              root.tail=tailLength;
            }else{
              ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
              root.tail=tail-1;
            }
            arr[tail]=null;
          }
        }
      }
    }
    @SuppressWarnings("unchecked")
    @Override void uncheckedForEachRemaining(final Consumer<? super E> action){
      final RefUntetheredArrSeq<E> root;
      final int head;
      int index;
      final var arr=(root=this.root).arr;
      if((index=this.index)<(head=root.head)){
        for(;;){
          action.accept((E)arr[index]);
          if(--index==-1){
            index=arr.length-1;
            break;
          }
        }
      }
      do{
        action.accept((E)arr[index]);
      }while(--index>=head);
      this.index=index;
    }
  }
}
