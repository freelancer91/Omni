package omni.impl;
import omni.api.OmniCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
abstract class BooleanUntetheredArrSeq implements OmniCollection.OfBoolean,Externalizable
{
  boolean[] arr;
  int head;
  int tail;
  BooleanUntetheredArrSeq(int head,boolean[] arr,int tail){
    super();
    this.arr=arr;
    this.head=head;
    this.tail=tail;
  }
  BooleanUntetheredArrSeq(){
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
  @Override public OmniIterator.OfBoolean iterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-(tail=this.head))<=0){
        size+=arr.length;
      }
      return new AscendingUntetheredArrSeqItr(this,tail,size);
    }
    return new AscendingUntetheredArrSeqItr(this,-1,0);
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter);
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedRemoveIf(tail,filter::test);
  }
  public void forEach(BooleanConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      ascendingForEach(this.head,tail,action);
    }
  }
  public void forEach(Consumer<? super Boolean> action){
    final int tail;
    if((tail=this.tail)!=-1){
      ascendingForEach(this.head,tail,action::accept);
    }
  }
  @Override public boolean[] toBooleanArray(){
    int tail;
    if((tail=this.tail)!=-1){
      boolean[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new boolean[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new boolean[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      Boolean[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new Boolean[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new Boolean[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int tail;
    if((tail=this.tail)!=-1){
      double[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new double[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new double[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int tail;
    if((tail=this.tail)!=-1){
      float[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new float[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new float[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int tail;
    if((tail=this.tail)!=-1){
      long[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new long[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new long[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new int[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new int[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int tail;
    if((tail=this.tail)!=-1){
      short[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new short[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new short[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int tail;
    if((tail=this.tail)!=-1){
      char[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new char[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new char[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int tail;
    if((tail=this.tail)!=-1){
      byte[] dst;
      final int head;
        int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,dst=new byte[size],0,size);
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=new byte[size+=arr.length],0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
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
        final boolean[] arr;
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
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,head,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),0,size-=tail);
        ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public void clear(){
    this.tail=-1;
  }
  public void addLast(boolean val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      this.insertAtTail(arr,val,this.head,tail);
    }else{
      if(arr==null){
        this.arr=new boolean[]{val};
      }else{
        if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
          this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }
        arr[0]=val;
      }
      this.head=0;
      this.tail=0;
    }
  }
  public void push(boolean val){
    var arr=this.arr;
    int tail;
    if((tail=this.tail)!=-1){
      this.insertAtHead(arr,val,this.head,tail);
    }else{
      if(arr==null){
        this.arr=new boolean[]{val};
        this.head=0;
        this.tail=0;
      }else if(arr==OmniArray.OfBoolean.DEFAULT_ARR){
        this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
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
      final boolean[] arr;
      int head;
      int word=TypeUtil.castToByte((arr=this.arr)[head=this.head]);
      int mask=1;
      int size;
      if((size=(tail+1)-head)<=0){
        out.writeInt((size+(size=arr.length))-1);
        for(;;){
          if(++head==size){
            head=0;
            break;
          }
          if((mask<<=1)==(1<<8)){
            out.writeByte(word);
            word=0;
            mask=1;
          }
          if(arr[head]){
            word|=mask;
          }
        }
      }else{
        out.writeInt(size-1);
      }
      for(;;){
        if(head==tail){
          out.writeByte(word);
          return;
        }
        if((mask<<=1)==(1<<8)){
          out.writeByte(word);
          word=0;
          mask=1;
        }
        if(arr[++head]){
          word|=mask;
        }
      }
    }else{
      out.writeInt(-1);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
  {
    int tail;
    this.tail=tail=in.readInt();
    if(tail!=-1){
      this.head=0;
      final boolean[] arr;
      OmniArray.OfBoolean.readArray(arr=new boolean[tail+1],0,tail,in);
      this.arr=arr;
    }
  }
  boolean uncheckedRemoveLastMatch(int tail,final boolean queryParam)
  {
    final int head;
    if((head=this.head)<=tail){
      return nonfragmentedRemoveLastMatch(head,tail,queryParam);
    }
    return fragmentedRemoveLastMatch(head,tail,queryParam);
  }
  boolean uncheckedRemoveFirstMatch(int tail,final boolean queryParam)
  {
    final int head;
    if((head=this.head)<=tail){
      return nonfragmentedRemoveFirstMatch(head,tail,queryParam);
    }
    return fragmentedRemoveFirstMatch(head,tail,queryParam);
  }
  boolean nonfragmentedRemoveLastMatch(int head,int tail,final boolean searchVal)
  {
    final var arr=this.arr;
    //search the upper half of the structure
    int index;
    for(int mid=(head+(index=tail))>>>1;;)
    {
      if(arr[index]==searchVal)
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
        return true;
      }
      if(--index<mid){
        break;
      }
    }
    //search the lower half of the structure
    for(int headLength;(headLength=index-head)>=0;--index){
      if(arr[index]==searchVal)
      {
        //found the element, pull the head up
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        this.head=tail;
        return true;
      }
    }
    return false;
  }
  boolean fragmentedRemoveLastMatch(int head,int tail,final boolean searchVal)
  {
    final boolean[] arr;
    final int bound=(arr=this.arr).length-1;
    for(int index=tail;;){
      if(arr[index]==searchVal)
      {
        final int tailLength,headLength;
        if((tailLength=tail-index)<(headLength=bound-head)+index){
          if(tail==0){
            this.tail=bound;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
            this.tail=tail-1;
          }
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,index);
          arr[0]=arr[bound];
          if(headLength==0){
            this.head=0;
          }else{
            ArrCopy.uncheckedCopy(arr,head,arr,tail=head+1,headLength);
            this.head=tail;
          }
        }
        return true;
      }
      if(--index==-1){
        break;
      }
    }
    for(int index=bound;;){
      if(arr[index]==searchVal)
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
        }
        return true;
      }
      if(--index<head){
        break;
      }
    }
    return false;
  }
  boolean nonfragmentedRemoveFirstMatch(int head,int tail,final boolean searchVal)
  {
    final var arr=this.arr;
    int index;
    //search the lower half of the structure
    for(int mid=((index=head)+tail)>>>1;;){
      if(arr[index]==searchVal)
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
        return true;
      }
      if(++index>mid)
      {
        break;
      }
    }
    //search the upper half of the structure
    for(int tailLength;(tailLength=tail-index)>=0;++index){
      if(arr[index]==searchVal)
      {
        //found the element, pull the tail down
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        this.tail=tail-1;
        return true;
      }
    }
    return false;
  }
  boolean fragmentedRemoveFirstMatch(int head,int tail,final boolean searchVal)
  {
    final boolean[] arr;
    final int bound=(arr=this.arr).length-1;
    for(int index=head;;){
      if(arr[index]==searchVal)
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
        }
        return true;
      }
      if(++index>bound)
      {
        break;
      }
    }
    for(int index=0;;){
      if(arr[index]==searchVal)
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
        }
        return true;
      }
      if(++index>tail){
        break;
      }
    }
    return false;
  }
  int uncheckedSearch(int tail,final boolean searchVal)
  {
    final var arr=this.arr;
    int count=1;
    int head;
    if((head=this.head)>tail){
      for(final int bound=arr.length;;)
      {
        if(arr[head]==searchVal)
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
      if(arr[head]==searchVal)
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
  boolean uncheckedContainsMatch(int tail,final boolean searchVal)
  {
    final var arr=this.arr;
    int head;
    if((head=this.head)>tail)
    {
      for(;;)
      {
        if(arr[tail]==searchVal)
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
      if(arr[head]==searchVal)
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
  public Boolean poll(){
    return pollFirst();
  }
  public boolean pollFirstBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)(uncheckedRemoveFirst(tail));
    }
    return false;
  }
  public boolean pollLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)(uncheckedRemoveLast(tail));
    }
    return false;
  }
  public Boolean pollFirst(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)(uncheckedRemoveFirst(tail));
    }
    return null;
  }
  public Boolean pollLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)(uncheckedRemoveLast(tail));
    }
    return null;
  }
  public double pollFirstDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(uncheckedRemoveFirst(tail));
    }
    return Double.NaN;
  }
  public double pollLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(uncheckedRemoveLast(tail));
    }
    return Double.NaN;
  }
  public float pollFirstFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(uncheckedRemoveFirst(tail));
    }
    return Float.NaN;
  }
  public float pollLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(uncheckedRemoveLast(tail));
    }
    return Float.NaN;
  }
  public long pollFirstLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(uncheckedRemoveFirst(tail));
    }
    return Long.MIN_VALUE;
  }
  public long pollLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(uncheckedRemoveLast(tail));
    }
    return Long.MIN_VALUE;
  }
  public int pollFirstInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Integer.MIN_VALUE;
  }
  public int pollLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Integer.MIN_VALUE;
  }
  public short pollFirstShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Short.MIN_VALUE;
  }
  public short pollLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Short.MIN_VALUE;
  }
  public char pollFirstChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(uncheckedRemoveFirst(tail));
    }
    return Character.MIN_VALUE;
  }
  public char pollLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(uncheckedRemoveLast(tail));
    }
    return Character.MIN_VALUE;
  }
  public byte pollFirstByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(uncheckedRemoveFirst(tail));
    }
    return Byte.MIN_VALUE;
  }
  public byte pollLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(uncheckedRemoveLast(tail));
    }
    return Byte.MIN_VALUE;
  }
  void insertAtTail(boolean[] arr,boolean key,int head,int tail){
    switch(Integer.signum((++tail)-head)){
      case 0:
        //fragmented must grow
        final boolean[] tmp;
        int arrLength;
        ArrCopy.uncheckedCopy(arr,0,tmp=new boolean[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
        ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
        this.head=head;
        this.arr=arr=tmp;
        break;
      default:
        //nonfragmented
        if(tail==arr.length){
          if(head==0){
            //must grow
            ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(tail)],0,tail);
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
  void insertAtHead(boolean[] arr,boolean key,int head,int tail){
    int newHead;
    switch(Integer.signum(tail-(newHead=head-1))){
      case 0:
        //fragmented must grow
        final boolean[] tmp;
        int arrLength;
        ArrCopy.uncheckedCopy(arr,0,tmp=new boolean[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
        ArrCopy.uncheckedCopy(arr,head,tmp,newHead=tail-(arrLength-=head),arrLength);
        --newHead;
        this.arr=arr=tmp;
        break;
      default:
        //nonfragmented
        if(newHead==-1 && tail==(newHead=arr.length-1)){
          //must grow
          this.tail=(newHead=OmniArray.growBy50Pct(++tail))-1;
          ArrCopy.uncheckedCopy(arr,0,arr=new boolean[newHead],newHead-=(tail),tail);
          --newHead;
          this.arr=arr;
        }
      case -1:
        //fragmented
    }
    arr[newHead]=key;
    this.head=newHead;
  }
  boolean uncheckedRemoveLast(int tail){
    final boolean[] arr;
    final var ret=(arr=this.arr)[tail];
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
  boolean uncheckedRemoveFirst(int tail){
    int head;
    final boolean[] arr;
    final var ret=(arr=this.arr)[head=this.head];
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
  void ascendingForEach(int head,int tail,BooleanConsumer action){
    final var arr=this.arr;
    if(tail<head){
      for(int bound=arr.length;;){
        action.accept((boolean)arr[head]);
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;){
      action.accept((boolean)arr[head]);
      if(head==tail){
        break;
      }
      ++head;
    }
  }
  void descendingForEach(int head,int tail,BooleanConsumer action){
    final var arr=this.arr;
    if(tail<head){
      for(;;){
        action.accept((boolean)arr[tail]);
        if(tail==0){
          tail=arr.length-1;
          break;
        }
        --tail;
      }
    }
    for(;;){
      action.accept((boolean)arr[tail]);
      if(tail==head){
        return;
      }
      --tail;
    }
  }
  void fragmentedRemoveIfHelper(boolean[] arr,int head,int tail,boolean retainThis){
    int count=1;
    for(;;){
      if(--tail==-1){
        tail=arr.length-1;
        if(arr[tail=arr.length-1]==retainThis){
          ++count;
        }
        nonfragmentedRemoveIfHelper(count,arr,head,tail,retainThis);
        break;
      }
      if(arr[tail]==retainThis){
        ++count;
      }
    }
  }
  void nonfragmentedRemoveIfHelper(int count,boolean[] arr,int head,int tail,boolean retainThis){
    for(;;){
      if(tail==head){
        this.head=0;
        this.tail=count-1;
        OmniArray.OfBoolean.fill(arr,0,count,retainThis);
        break;
      }
      if(arr[--tail]==retainThis){
        ++count;
      }
    }
  }
  boolean uncheckedRemoveIf(int tail,BooleanPredicate filter){
    final boolean[] arr;
    boolean firstVal;
    var head=this.head;
    if(filter.test(firstVal=(arr=this.arr)[tail])){
      switch(Integer.signum(tail-head)){
        case -1:
          while(--tail!=-1){
            if(arr[tail]^firstVal){
              if(filter.test(!firstVal)){
                this.tail=-1;
              }else{
                fragmentedRemoveIfHelper(arr,head,tail,!firstVal);
              }
              return true;
            }
          }
          tail=arr.length;
        default:
          do{
            if(arr[--tail]^firstVal){
              if(filter.test(!firstVal)){
                break;
              }
              nonfragmentedRemoveIfHelper(1,arr,head,tail,!firstVal);
              return true;
            }
          }while(tail!=head);
        case 0:
          this.tail=-1;
          break;
      }
      return true;
    }else{
      switch(Integer.signum(tail-head)){
        case -1:
          while(--tail!=-1){
            if(arr[tail]^firstVal){
              if(filter.test(!firstVal)){
                fragmentedRemoveIfHelper(arr,head,tail,firstVal);
                return true;
              }else{
                return false;
              }
            }
          }
          tail=arr.length;
        default:
          do{
            if(arr[--tail]^firstVal){
              if(filter.test(!firstVal)){
                nonfragmentedRemoveIfHelper(1,arr,head,tail,firstVal);
                return true;
              }
              break;
            }
          }while(tail!=head);
        case 0:
          return false;
      }
    }
  }
  static abstract class AbstractUntetheredArrSeqItr extends AbstractBooleanItr{
    transient final BooleanUntetheredArrSeq root;
    transient int index;
    transient int numLeft;
    AbstractUntetheredArrSeqItr(BooleanUntetheredArrSeq root,int index,int numLeft){
      this.root=root;
      this.index=index;
      this.numLeft=numLeft;
    }
    public boolean hasNext(){
      return this.numLeft>0;
    }
    public boolean nextBoolean(){
      --numLeft;
      final boolean[] arr;
      final int index;
      iterateIndex(index=this.index,arr=this.root.arr);
      return (boolean)arr[index];
    }
    public void remove(){
      final BooleanUntetheredArrSeq root;
      final int head;
      int tail;
      switch(Integer.signum((tail=(root=this.root).tail)-(head=root.head))){
        case -1:
          fragmentedRemove(root,head,tail);
          break;
        case 0:
          root.tail=-1;
          break;
        default:
          nonfragmentedRemove(root,head,tail);
      }
    }
    public void forEachRemaining(BooleanConsumer action){
      if(numLeft!=0){
        uncheckedForEachRemaining(action);
      }
    }
    public void forEachRemaining(Consumer<? super Boolean> action){
      if(numLeft!=0){
        uncheckedForEachRemaining(action::accept);
      }
    }
    abstract void iterateIndex(int index,final boolean[] arr);
    abstract void fragmentedRemove(final BooleanUntetheredArrSeq root,int head,int tail);
    abstract void nonfragmentedRemove(final BooleanUntetheredArrSeq root,int head,int tail);
    abstract void uncheckedForEachRemaining(final BooleanConsumer action);
  }
  static class AscendingUntetheredArrSeqItr extends AbstractUntetheredArrSeqItr{
    AscendingUntetheredArrSeqItr(BooleanUntetheredArrSeq root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new AscendingUntetheredArrSeqItr(root,index,numLeft);
    }
    @Override void iterateIndex(int index,final boolean[] arr){
      if(++index==arr.length){
        index=0;
      }
      this.index=index;
    }
    @Override void nonfragmentedRemove(final BooleanUntetheredArrSeq root,final int head,int tail){
      final var arr=root.arr;
      final int index,headLength,tailLength;
      if((headLength=(index=this.index-1)-head)<=(tailLength=tail-index)){
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        root.head=tail;
      }else{
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        root.tail=tail-1;
        this.index=index;
      }
    }
    @Override void fragmentedRemove(final BooleanUntetheredArrSeq root,final int head,int tail){
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
        }else{
          arr[index]=arr[0];
          if(tail==0){
            root.tail=index;
          }else{
            ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
            root.tail=tail-1;
          }
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
          }else{
            if(tail==0){
              root.tail=arr.length-1;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
              root.tail=tail-1;
            }
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
          }else{
            ArrCopy.semicheckedSelfCopy(arr,index,index+1,--tailLength);
            arr[tailLength+=index]=arr[0];
            if(tail==0){
              root.tail=tailLength;
            }else{
              ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
              root.tail=tail-1;
            }
            this.index=index;
          }
        }
      }
    }
    @Override void uncheckedForEachRemaining(final BooleanConsumer action){
      final BooleanUntetheredArrSeq root;
      final int tail;
      int index;
      final var arr=(root=this.root).arr;
      if((index=this.index)>(tail=root.tail)){
        for(int bound=arr.length;;){
          action.accept((boolean)arr[index]);
          if(++index==bound){
            index=0;
            break;
          }
        }
      }
      do{
        action.accept((boolean)arr[index]);
      }while(++index<=tail);
      this.index=index;
    }
  }
  static class DescendingUntetheredArrSeqItr extends AbstractUntetheredArrSeqItr{
    DescendingUntetheredArrSeqItr(BooleanUntetheredArrSeq root,int index,int numLeft){
      super(root,index,numLeft);
    }
    @Override public Object clone(){
      return new AscendingUntetheredArrSeqItr(root,index,numLeft);
    }
    @Override void iterateIndex(int index,final boolean[] arr){
      if(--index==-1){
        index=arr.length-1;
      }
      this.index=index;
    }
    @Override void nonfragmentedRemove(final BooleanUntetheredArrSeq root,final int head,int tail){
      final var arr=root.arr;
      final int index,headLength,tailLength;
      if((headLength=(index=this.index+1)-head)<=(tailLength=tail-index)){
        ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
        root.head=tail;
        this.index=index;
      }else{
        ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
        root.tail=tail-1;
      }
    }
    @Override void fragmentedRemove(final BooleanUntetheredArrSeq root,final int head,int tail){
      final boolean[] arr;
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
          this.index=0;
        }else{
          if(tail==0){
            root.tail=index-1;
          }else{
            ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
            root.tail=tail-1;
          }
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
            this.index=index;
          }else{
            if(tail==0){
              root.tail=arr.length-1;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailLength);
              root.tail=tail-1;
            }
          }
        }else{
          if((headLength=index-head)<=(tailLength=arr.length-index)+tail){
            if(headLength+tailLength==1){
              root.head=0;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headLength);
              root.head=tail;
            }
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
          }
        }
      }
    }
    @Override void uncheckedForEachRemaining(final BooleanConsumer action){
      final BooleanUntetheredArrSeq root;
      final int head;
      int index;
      final var arr=(root=this.root).arr;
      if((index=this.index)<(head=root.head)){
        for(;;){
          action.accept((boolean)arr[index]);
          if(--index==-1){
            index=arr.length-1;
            break;
          }
        }
      }
      do{
        action.accept((boolean)arr[index]);
      }while(--index>=head);
      this.index=index;
    }
  }
}
