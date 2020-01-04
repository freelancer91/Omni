package omni.impl;
import omni.api.OmniCollection;
import omni.util.ArrCopy;
import java.util.function.Predicate;
import omni.util.OmniArray;
import java.util.function.ToIntFunction;
abstract class RefUntetheredArrSeq<E> implements OmniCollection<E>
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
  @SuppressWarnings("unchecked")
  boolean nonfragmentedRemoveMatch(int head,int tail,@SuppressWarnings("rawtypes") final ToIntFunction comparator)
  {
    final Object[] arr;
    int mid;
    switch(comparator.applyAsInt((arr=this.arr)[mid=(head+tail)>>>1]))
    {
      default:
        //search the upper half of the structure (between mid and tail)
        if(++mid>tail || (mid=findIndex(arr,mid,tail,comparator))==-1)
        {
          return false;
        }
        //found the element, pull the tail down
        ArrCopy.semicheckedSelfCopy(arr,mid,mid+1,tail-mid);
        arr[tail]=null;
        this.tail=tail-1;
        return true;
      case 0:
        //the value was found on the first attempt. Remove it.
        if(head==tail)
        {
          //the element is the last element
          this.tail=-1;
          arr[head]=null;
          return true;
        }
        break;
      case 1:
        //search the lower half of the structure (between head and mid)
        if(--mid<head || (mid=findIndex(arr,head,mid,comparator))==-1)
        {
          return false;
        }
    }
    //found the element, pull the head up
    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,mid-head);
    this.head=tail;
    arr[head]=null;
    return true;
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedRemoveMatch(int head,int tail,@SuppressWarnings("rawtypes") final ToIntFunction comparator)
  {
    final Object[] arr;
    switch(comparator.applyAsInt((arr=this.arr)[0]))
    {
      case 0:
        //found the element at index 0, remove it
        if(tail==0)
        {
          this.tail=arr.length-1;
        }
        else
        {
          //pull the tail down
          ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
          this.tail=tail-1;
        }
        break;
      case 1:
        //search the lower half of the structure (between head and array.length)
        int index;
        if((index=findIndex(arr,head,tail=arr.length-1,comparator))==-1)
        {
          return false;
        }
        //found the element, pull the head up
        if(head==tail)
        {
          this.head=0;
        }
        else
        {
          ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,index-head);
          this.head=tail;
        }
        arr[head]=null;
        return true;
      default:
        //search the upper half of the structure (between 0 and tail)
        if(tail==0 || (index=findIndex(arr,1,tail,comparator))==-1)
        {
          return false;
        }
        //found the element, pull the tail down
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tail-index);
        this.tail=tail-1;
    }
    arr[tail]=null;
    return true;
  }
  @SuppressWarnings("unchecked")
  static int findIndex(final Object[] arr,int head,int tail,@SuppressWarnings("rawtypes") final ToIntFunction comparator)
  {
    do
    {
      final int mid;
      switch(comparator.applyAsInt(arr[mid=(head+tail)>>>1]))
      {
        case 0:
          return mid;
        case 1:
          tail=mid-1;
          break;
        default:
          head=mid+1;
      }
    }
    while(head<=tail);
    return -1;
  }
  @SuppressWarnings("unchecked")
  boolean uncheckedContainsMatch(int tail,@SuppressWarnings("rawtypes") final ToIntFunction comparator)
  {
    final var arr=this.arr;
    int head;
    if((head=this.head)>tail)
    {
      //fragmented
      switch(comparator.applyAsInt(arr[0]))
      {
        case 0:
          return true;
        case 1:
          //search in the head span
          tail=arr.length-1;
          break;
        default:
          //search in the tail span
          if(tail==0)
          {
            return false;
          }
          head=1;
      }
    }
    return findIndex(arr,head,tail,comparator)!=-1;
  }
}
