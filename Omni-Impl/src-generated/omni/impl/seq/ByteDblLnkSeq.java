package omni.impl.seq;
import java.util.ListIterator;
import java.util.List;
import omni.util.ByteSortUtil;
import omni.api.OmniList;
import omni.impl.ByteDblLnkNode;
import java.util.function.Consumer;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import java.util.function.UnaryOperator;
import omni.function.ByteUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractByteItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.ByteComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollection;
import omni.util.ToStringUtil;
public abstract class ByteDblLnkSeq extends 
AbstractSeq<Byte>
 implements
   ByteSubListDefault
{
  private static final long serialVersionUID=1L;
  transient ByteDblLnkNode head;
  transient ByteDblLnkNode tail;
  private  ByteDblLnkSeq(){
  }
  private ByteDblLnkSeq(ByteDblLnkNode head,int size,ByteDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(byte val);
  @Override public boolean add(Byte val){
    addLast((byte)val);
    return true;
  }  
  @Override public boolean add(byte val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,ByteDblLnkNode after,ByteDblLnkNode newNode){
    newNode.next=after=ByteDblLnkNode.iterateDescending(after,dist-2);
    final ByteDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,ByteDblLnkNode before,ByteDblLnkNode newNode){
    newNode.prev=before=ByteDblLnkNode.iterateAscending(before,dist-1);
    final ByteDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,ByteDblLnkNode newNode){
    int tailDist;
    if((tailDist=this.size-index)<=index){
      //the insertion point is closer to the tail
      var tail=this.tail;
      if(tailDist==1){
        //the insertion point IS the tail
        newNode.prev=tail;
        tail.next=newNode;
        this.tail=newNode;
      }else{
        //iterate from the root's tail
        iterateDescendingAndInsert(tailDist,tail,newNode);
      }
    }else{
      //the insertion point is closer to the head
      if(index==0){
        //the insertion point IS the head
        ByteDblLnkNode head;
        (head=this.head).prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,this.head,newNode);
      }
    }
  }
  private static  int markSurvivors(ByteDblLnkNode curr,int numLeft,BytePredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if(--numLeft==0)
        {
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
        curr=curr.next;
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static  long markSurvivors(ByteDblLnkNode curr,int numLeft,BytePredicate filter){
    for(long word=0L,marker=1L;;marker<<=1,curr=curr.next){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if(--numLeft==0)
      {
        return word;
      }
    }
  }
  private ByteDblLnkNode getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return ByteDblLnkNode.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return ByteDblLnkNode.iterateAscending(head,index);
    }
  }
  private ByteDblLnkNode getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return ByteDblLnkNode.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return ByteDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public byte set(int index,byte val){
    ByteDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,byte val){
    getNode(index,size).val=val;
  }
  @Override public byte getByte(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(ByteConsumer action){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      ByteDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      ByteDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public byte[] toByteArray(){
    int size;
    if((size=this.size)!=0){
      final byte[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new byte[size],tail,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public Byte[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Byte[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new Byte[size],tail,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      final short[] dst;
      ByteDblLnkNode.uncheckedCopyInto(dst=new short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public void replaceAll(ByteUnaryOperator operator){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Byte> operator){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(ByteComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final byte[] tmp;
      final ByteDblLnkNode tail;
      ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
      if(sorter==null){
        ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter);
      }
      ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Byte> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final byte[] tmp;
      final ByteDblLnkNode tail;
      ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
      if(sorter==null){
        ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
      }
      ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final byte[] tmp;
      final ByteDblLnkNode tail;
      ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
      ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
      ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final byte[] tmp;
      final ByteDblLnkNode tail;
      ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
      ByteSortUtil.uncheckedDescendingSort(tmp,0,size);
      ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(ByteComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final byte[] tmp;
      final ByteDblLnkNode tail;
      ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
      if(sorter==null){
        ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        ByteSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
      }
      ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/6)){(buffer=new byte[size*6])
        [size=ByteDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        ByteDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      return ByteDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    if(val==(byte)val)
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if((v=(byte)val)==val){
            return ByteDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Byte){
              i=(byte)val;
            }else if(val instanceof Integer||val instanceof Short){
              if((i=((Number)val).intValue())!=(byte)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(byte)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(byte)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(byte)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Byte.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ByteDblLnkNode.uncheckedcontains(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    if(val<=Byte.MAX_VALUE)
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    if(val==(byte)val)
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if((v=(byte)val)==val){
            return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Byte){
              i=(byte)val;
            }else if(val instanceof Integer||val instanceof Short){
              if((i=((Number)val).intValue())!=(byte)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(byte)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(byte)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(byte)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Byte.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ByteDblLnkNode.uncheckedindexOf(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(byte val){
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    if(val<=Byte.MAX_VALUE)
    {
      {
        final ByteDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    if(val==(byte)val)
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if((v=(byte)val)==val){
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Byte){
              i=(byte)val;
            }else if(val instanceof Integer||val instanceof Short){
              if((i=((Number)val).intValue())!=(byte)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(byte)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(byte)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(byte)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Byte.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    if(val<=Byte.MAX_VALUE)
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static  int collapseBodyHelper(ByteDblLnkNode newHead,ByteDblLnkNode newTail,BytePredicate filter){
    int numRemoved=0;
    outer:for(ByteDblLnkNode prev;(newHead=(prev=newHead).next)!=newTail;){
      if(filter.test(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next)==newTail){
            newHead.prev=prev;
            prev.next=newHead;
            break outer;
          }
        }while(filter.test(newHead.val));
        newHead.prev=prev;
        prev.next=newHead;
      }
    }
    return numRemoved;
  }
  static class UncheckedSubList extends ByteDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private UncheckedSubList(UncheckedList root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private Object writeReplace(){
      return new UncheckedList(this.head,this.size,this.tail);
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
      }
    }
    private void bubbleUpAppend(ByteDblLnkNode oldTail,ByteDblLnkNode newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(ByteDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(ByteDblLnkNode oldHead,ByteDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(ByteDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(ByteDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,ByteDblLnkNode newNode){
      ByteDblLnkNode after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=ByteDblLnkNode.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=ByteDblLnkNode.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(ByteDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      UncheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((ByteDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,byte val){
      final UncheckedList root;
      final var newNode=new ByteDblLnkNode(val);
      if(++(root=this.root).size!=1){
        final int currSize;
        if((currSize=++this.size)!=1){
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(byte val){
      final UncheckedList root;
      if(++(root=this.root).size!=1){
        if(++this.size!=1){
          ByteDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new ByteDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new ByteDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new ByteDblLnkNode(val));
        }
      }else{
        ByteDblLnkNode newNode;
        bubbleUpRootInit(newNode=new ByteDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractSeq){
          //TODO optimize
          return SequenceEqualityUtil.isEqualTo(list,this);
        }else{
          return size==list.size() && root.isEqualTo(list.listIterator(),this.head);
        }
      }
      return false;
    }
    private void bubbleUpPeelHead(ByteDblLnkNode newHead,ByteDblLnkNode oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(ByteDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(ByteDblLnkNode newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       UncheckedSubList parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      this.tail=newTail;
      ByteDblLnkNode after;
      if((after=oldTail.next)==null){
        final UncheckedSubList parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void peelTail(ByteDblLnkNode tail){
      ByteDblLnkNode after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(ByteDblLnkNode lastNode){
      ByteDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(ByteDblLnkNode head){
      ByteDblLnkNode after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public byte removeByteAt(int index){
      final byte ret;
      int size;
      if((size=(--this.size)-index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          ByteDblLnkNode before;
          ret=(before=( tail=ByteDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          ByteDblLnkNode after;
          ret=(after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final ByteDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final ByteDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private void collapsehead(ByteDblLnkNode oldhead,ByteDblLnkNode tail,BytePredicate filter
    ){
      int numRemoved=1;
      ByteDblLnkNode newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){ 
        if(newhead==tail){
          break;
        }
        if(!filter.test(newhead.val)){
          ByteDblLnkNode prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      UncheckedList root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      ByteDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(ByteDblLnkNode oldtail,ByteDblLnkNode head,BytePredicate filter
    ){
      int numRemoved=1;
      ByteDblLnkNode newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.prev){ 
        if(newtail==head){
          break;
        }
        if(!filter.test(newtail.val)){
          ByteDblLnkNode next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      UncheckedList root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      ByteDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(ByteDblLnkNode oldHead,ByteDblLnkNode newHead,int numRemoved,ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final ByteDblLnkNode after,before=oldHead.prev;
      if((after=oldTail.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          UncheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(ByteDblLnkNode head,BytePredicate filter){
      ByteDblLnkNode tail;
      {
        if(filter.test((tail=this.tail).val)){
          if(tail==head){
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
              );
            }else{
              collapsetail(tail,head,filter
              );
            }
          }
          return true;
        }else{
          if(tail!=head){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
              );
            }
          }
        }
      }
      return false;
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        final UncheckedList root;
        (root=this.root).size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,ByteDblLnkNode head,ByteDblLnkNode tail,UncheckedList root){
      ByteDblLnkNode before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
      }
    }
    private void bubbleUpClearBody(ByteDblLnkNode before,ByteDblLnkNode head,int numRemoved,ByteDblLnkNode tail,ByteDblLnkNode after){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(ByteDblLnkNode tail, ByteDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(ByteDblLnkNode head, ByteDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void collapseHeadAndTail(ByteDblLnkNode head,ByteDblLnkNode tail,BytePredicate filter
    ){
      ByteDblLnkNode newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            ByteDblLnkNode prev;
            outer: for(var curr=(prev=newHead).next;curr!=tail;curr=(prev=curr).next){
              if(filter.test(curr.val)){
                do{
                  ++numRemoved;
                  if((curr=curr.next)==tail){
                    break outer;
                  }
                }while(filter.test(curr.val));
                prev.next=curr;
                curr.prev=prev;
              }
            }
            this.size-=numRemoved;
            root.size-=numRemoved; 
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,prev,tail);
            return;
          }else if((newHead=newHead.next)==tail){
            break;
          }
        }
      }
      UncheckedList root;
      int size;
      (root=this.root).size-=(size=this.size);
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(ByteDblLnkNode head,ByteDblLnkNode tail,BytePredicate filter
    ){
      for(ByteDblLnkNode prev;(head=(prev=head).next)!=tail;){
        if(filter.test(head.val)){
          int numRemoved=1;
          for(;(head=head.next)!=tail;++numRemoved){
            if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,filter);
              break;
            }
          }
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ByteDblLnkNode head,newTail;
        final var newHead=newTail=new ByteDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ByteDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static class AscendingItr
      extends AbstractByteItr
    {
      transient final UncheckedSubList parent;
      transient ByteDblLnkNode curr;
      private AscendingItr(UncheckedSubList parent,ByteDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(AscendingItr itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedSubList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public byte nextByte(){
        final ByteDblLnkNode curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          ByteDblLnkNode curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            ByteDblLnkNode lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          ByteDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          ByteDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfByte{
      private transient int currIndex;
      private transient ByteDblLnkNode lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,ByteDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public byte nextByte(){
        final ByteDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public byte previousByte(){
        ByteDblLnkNode curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(byte val){
        lastRet.val=val;
      }
      @Override public void add(byte val){
        final UncheckedSubList currList;
        final UncheckedList root;
        this.lastRet=null;
        ++currIndex;
        if(++(root=(currList=this.parent).root).size!=1){
          if(++currList.size!=1){
            ByteDblLnkNode after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new ByteDblLnkNode(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new ByteDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              ByteDblLnkNode newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new ByteDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new ByteDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new ByteDblLnkNode(val));
          }
        }else{
          ByteDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new ByteDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        ByteDblLnkNode lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          if(lastRet==parent.tail){
            parent.peelTail(lastRet);
          }else{
            if(lastRet==parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        this.lastRet=null;
        --parent.root.size;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final ByteDblLnkNode lastRet;
          ByteDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final ByteDblLnkNode lastRet;
          ByteDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(int index){
      return new BidirectionalItr(this,((ByteDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        int tailDist;
        final ByteDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=ByteDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?ByteDblLnkNode.iterateDescending(subListTail,subListSize-1):ByteDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=ByteDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?ByteDblLnkNode.iterateAscending(subListHead,subListSize-1):ByteDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(ByteDblLnkNode head
    ,int val
    ){
      if(val==(head.val)){
        --root.size;
        if(--this.size==0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail!=head;){
          ByteDblLnkNode prev;
          if(val==((head=(prev=head).next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  static class CheckedSubList extends ByteDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private CheckedSubList(CheckedList root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    boolean uncheckedremoveVal(ByteDblLnkNode head
    ,int val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(val==(head.val)){
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(final var tail=this.tail;head!=tail;){
          ByteDblLnkNode prev;
          if(val==((head=(prev=head).next).val)){
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfByte iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((ByteDblLnkSeq)this).getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractByteItr
      implements OmniListIterator.OfByte{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient ByteDblLnkNode curr;
      private transient ByteDblLnkNode lastRet;
      private transient int currIndex;
      private BidirectionalItr(BidirectionalItr itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,ByteDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public byte nextByte(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          ByteDblLnkNode curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public byte previousByte(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          ByteDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(byte val){
        final ByteDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(ByteConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))>0){
          final int modCount=this.modCount;
          try{
            ByteDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))>0){
          final int modCount=this.modCount;
          try{
            ByteDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        ByteDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          ByteDblLnkNode curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--(parent=this.parent).size==0){
            parent.removeLastNode(parent.tail);
          }else{
            if(lastRet==parent.tail){
              parent.peelTail(lastRet);
            }else{
              if(lastRet==parent.head){
                parent.peelHead(lastRet);
              }else{
                curr.prev=lastRet=lastRet.prev;
                lastRet.next=curr;
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(byte val){
        final CheckedSubList currList;
        final CheckedList root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=this.parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        this.lastRet=null;
        ++currIndex;
        if(++root.size!=1){
          if(++currList.size!=1){
            ByteDblLnkNode after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new ByteDblLnkNode(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new ByteDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              ByteDblLnkNode newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new ByteDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new ByteDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new ByteDblLnkNode(val));
          }
        }else{
          ByteDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new ByteDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
    }
    @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final ByteDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=ByteDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?ByteDblLnkNode.iterateDescending(subListTail,subListSize-1):ByteDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=ByteDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?ByteDblLnkNode.iterateAscending(subListHead,subListSize-1):ByteDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        ByteDblLnkNode head,newTail;
        final var newHead=newTail=new ByteDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ByteDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(byte)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteDblLnkNode.uncheckedindexOf(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      if(val<=Byte.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(byte)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      if(val<=Byte.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(byte)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return ByteDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteDblLnkNode.uncheckedcontains(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      if(val<=Byte.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient ByteDblLnkNode head;
      private transient ByteDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(ByteDblLnkNode head,int size,ByteDblLnkNode tail,CheckedList.ModCountChecker modCountChecker){
        this.head=head;
        this.tail=tail;
        this.size=size;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList(head,size,tail);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          ByteDblLnkNode curr;
          for(this.head=curr=new ByteDblLnkNode((byte)ois.readByte());--size!=0;curr=curr.next=new ByteDblLnkNode(curr,(byte)ois.readByte())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            for(;;curr=curr.next){
              oos.writeByte(curr.val);
              if(--size==0){
                break;
              }
            }
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    private static  ByteDblLnkNode pullSurvivorsDown(ByteDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return curr;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static  ByteDblLnkNode pullSurvivorsDown(ByteDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return curr;
        }
        prev=curr;
      }
    }
    private void bubbleUpPeelHead(ByteDblLnkNode newHead,ByteDblLnkNode oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(ByteDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(ByteDblLnkNode newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        ++curr.modCount;
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       CheckedSubList parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      this.tail=newTail;
      ByteDblLnkNode after;
      if((after=oldTail.next)==null){
        final CheckedSubList parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void peelTail(ByteDblLnkNode tail){
      ByteDblLnkNode after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(ByteDblLnkNode lastNode){
      ByteDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                ++curr.modCount;
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            ++curr.modCount;
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(ByteDblLnkNode head){
      ByteDblLnkNode after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public byte removeByteAt(int index){
      final byte ret;
      int size;
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      this.size=--size;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          ByteDblLnkNode before;
          ret=(before=( tail=ByteDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          ByteDblLnkNode after;
          ret=(after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(ByteDblLnkNode oldhead,ByteDblLnkNode tail,BytePredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      ByteDblLnkNode newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){ 
        if(numLeft==0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,tail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      ByteDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(ByteDblLnkNode oldtail,ByteDblLnkNode head,BytePredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      ByteDblLnkNode newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.prev){ 
        if(numLeft==0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,newtail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      ByteDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(ByteDblLnkNode oldHead,ByteDblLnkNode newHead,int numRemoved,ByteDblLnkNode newTail,ByteDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final ByteDblLnkNode after,before=oldHead.prev;
      if((after=oldTail.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          CheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  ++parent.modCount;
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  ++parent.modCount;
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(ByteDblLnkNode head,BytePredicate filter){
      ByteDblLnkNode tail;
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        if(filter.test((tail=this.tail).val)){
          if(size==1){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
                ,size,modCount
              );
            }else{
              collapsetail(tail,head,filter
                ,size,modCount
              );
            }
          }
          return true;
        }else{
          if(size!=1){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
                ,size,modCount
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
                ,size,modCount
              );
            }
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void clear(){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        root.size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,ByteDblLnkNode head,ByteDblLnkNode tail,CheckedList root){
      ByteDblLnkNode before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpClearBody(ByteDblLnkNode before,ByteDblLnkNode head,int numRemoved,ByteDblLnkNode tail,ByteDblLnkNode after){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(ByteDblLnkNode tail, ByteDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(ByteDblLnkNode head, ByteDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private static  int collapseBodyHelper(ByteDblLnkNode newHead,ByteDblLnkNode newTail,int numLeft,BytePredicate filter,CheckedList.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft))!=null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft))!=null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private void collapseHeadAndTail(ByteDblLnkNode head,ByteDblLnkNode tail,BytePredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        for(var newHead=head.next;;newHead=newHead.next){
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList root=this.root;
            for(--size;;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,newTail,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
                break;
              }
            }
            root.modCount=++modCount;
            this.modCount=modCount;
            root.size-=numRemoved;
            this.size=size+1-numRemoved;
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            return;
          }
          if(++numRemoved==size){
            break;
          }
        }
      }
      CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(ByteDblLnkNode head,ByteDblLnkNode tail,BytePredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        ByteDblLnkNode prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            head=head.next;
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpAppend(ByteDblLnkNode oldTail,ByteDblLnkNode newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(ByteDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(ByteDblLnkNode oldHead,ByteDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(ByteDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(ByteDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,ByteDblLnkNode newNode){
      ByteDblLnkNode after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=ByteDblLnkNode.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=ByteDblLnkNode.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(ByteDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      CheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        ++currParent.modCount;
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((ByteDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,byte val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new ByteDblLnkNode(val);
      if(++root.size!=1){
        this.size=++currSize;
        if(currSize!=1){    
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(byte val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size!=1){
        if(++this.size!=1){
          ByteDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new ByteDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new ByteDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new ByteDblLnkNode(val));
        }
      }else{
        ByteDblLnkNode newNode;
        bubbleUpRootInit(newNode=new ByteDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public byte set(int index,byte val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final ByteDblLnkNode node;
      final var ret=(node=((ByteDblLnkSeq)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,byte val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((ByteDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public byte getByte(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((ByteDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(ByteUnaryOperator operator){
      final ByteDblLnkNode head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        ByteDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(ByteConsumer action){
      final int modCount=this.modCount;
      try{
        final ByteDblLnkNode head;
        if((head=this.head)!=null){
          ByteDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(ByteComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final byte[] tmp;
        final ByteDblLnkNode tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          try{
            ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        ByteSortUtil.uncheckedDescendingSort(tmp,0,size);
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void replaceAll(UnaryOperator<Byte> operator){
      final ByteDblLnkNode head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        ByteDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCount=this.modCount;
      try{
        final ByteDblLnkNode head;
        if((head=this.head)!=null){
          ByteDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Byte> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final byte[] tmp;
        final ByteDblLnkNode tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          try{
            ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(ByteComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final byte[] tmp;
        final ByteDblLnkNode tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
          try{
            ByteSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public <T> T[] toArray(T[] dst){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray(dst);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->
      {
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      });
    }
    @Override public byte[] toByteArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toByteArray();
    }
    @Override public Byte[] toArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray();
    }
    @Override public double[] toDoubleArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toDoubleArray();
    }
    @Override public float[] toFloatArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toFloatArray();
    }
    @Override public long[] toLongArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toLongArray();
    }
    @Override public int[] toIntArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toIntArray();
    }
    @Override public short[] toShortArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toShortArray();
    }
    @Override public String toString(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toString();
    }
    @Override public int hashCode(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.hashCode();
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractSeq){
            //TODO optimize
            return SequenceEqualityUtil.isEqualTo(list,this);
          }else{
            return size==list.size() && ((UncheckedList)root).isEqualTo(list.listIterator(),this.head);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    } 
  }
  public static class CheckedList extends UncheckedList{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedList.this.modCount;
      }
    }
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.size=0;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public byte removeLastByte(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=tail.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public byte popByte(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=head.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public byte removeByteAt(int index){
      final byte ret;
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++this.modCount;
      this.size=--size;
      if((size-=index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          ByteDblLnkNode before;
          ret=(before=(tail=ByteDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          ByteDblLnkNode after;
          ret=(after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,byte val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=++size;
      if((size-=index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new ByteDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          ByteDblLnkNode before;
          (before=(tail=ByteDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new ByteDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        ByteDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new ByteDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new ByteDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          ByteDblLnkNode after;
          (after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new ByteDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(byte val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(byte val){
      ++this.modCount;
      super.push(val);
    }
    @Override public byte set(int index,byte val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ByteDblLnkNode tmp;
      final var ret=(tmp=((ByteDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,byte val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((ByteDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public byte getByte(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((ByteDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public byte getLastByte(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public byte byteElement(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
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
    @Override public void forEach(ByteConsumer action){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(ByteUnaryOperator operator){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(ByteComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        if(sorter==null){
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
        this.modCount=modCount+1;
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        ByteSortUtil.uncheckedDescendingSort(tmp,0,size);
        this.modCount=modCount+1;
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Byte> operator){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Byte> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        if(sorter==null){
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            ByteSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(ByteComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final byte[] tmp;
        final ByteDblLnkNode tail;
        ByteDblLnkNode.uncheckedCopyInto(tmp=new byte[size],tail=this.tail,size);
        if(sorter==null){
          ByteSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            ByteSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        ByteDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(ByteDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(ByteDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(ByteDblLnkNode prev,BytePredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          final long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(prev.next,numLeft,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedRemoveIf(ByteDblLnkNode head,BytePredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
              head.prev=null;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.tail=null;
          this.size=0;
          return true;
        }else{
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        int size;
        out.writeInt(size=this.size);
        if(size!=0){
          var curr=this.head;
          for(;;curr=curr.next){
            out.writeByte(curr.val);
            if(--size==0){
              break;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ByteDblLnkNode head,newTail;
        final var newHead=newTail=new ByteDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ByteDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractSeq){
          //TODO optimize
          return SequenceEqualityUtil.isEqualTo((List<?>)val,this);
        }else{
          final int modCount=this.modCount;
          try{
            return size==list.size() && super.isEqualTo(list.listIterator(),this.head);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfByte descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfByte iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((ByteDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final ByteDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=ByteDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?ByteDblLnkNode.iterateDescending(subListTail,subListSize-1):ByteDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=ByteDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?ByteDblLnkNode.iterateAscending(subListHead,subListSize-1):ByteDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    } 
    boolean uncheckedremoveLastOccurrence(ByteDblLnkNode tail
    ,int val
    ){
      {
        if(val==(tail.val)){
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(ByteDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            this.modCount=modCount+1;
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(ByteDblLnkNode head
    ,int val
    ){
      {
        if(val==(head.val)){
          ++this.modCount;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(ByteDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            ++this.modCount;
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public byte pollByte(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte poll(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Byte pollLast(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(double)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(double)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(float)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(float)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(long)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(long)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(int)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(int)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(short)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(short)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    private static class DescendingItr
      extends AbstractByteItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient ByteDblLnkNode curr;
      transient ByteDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(DescendingItr itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,ByteDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public byte nextByte(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        ByteDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              ByteDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,ByteConsumer action){
        final int modCount=this.modCount;
        final CheckedList parent;
        try{
          ByteDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount,currIndex,this.currIndex);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfByte{
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
      }
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,ByteDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public byte nextByte(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public byte previousByte(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          ByteDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasPrevious(){
        return this.currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(byte val){
        final ByteDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(byte val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        ByteDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new ByteDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new ByteDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new ByteDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final ByteDblLnkNode tmp;
            (newNode=curr).prev=newNode=new ByteDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        ByteDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          ByteDblLnkNode curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))!=0){
          final int modCount=this.modCount;
          try{
            ByteDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))!=0){
          final int modCount=this.modCount;
          try{
            ByteDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
  }
  public static class UncheckedList extends ByteDblLnkSeq implements OmniDeque.OfByte,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public byte removeLastByte(){
      ByteDblLnkNode tail;
      final var ret=(tail=this.tail).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
    }
    @Override public byte popByte(){
      ByteDblLnkNode head;
      final var ret=(head=this.head).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
    }
    @Override public byte removeByteAt(int index){
      final byte ret;
      int size;
      if((size=--this.size-index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          ByteDblLnkNode before;
          ret=(before=(tail=ByteDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          ByteDblLnkNode after;
          ret=(after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,byte val){
      int size;
      if((size=++this.size-index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new ByteDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          ByteDblLnkNode before;
          (before=(tail=ByteDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new ByteDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        ByteDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new ByteDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new ByteDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          ByteDblLnkNode after;
          (after=(head=ByteDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new ByteDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(byte val){
      ByteDblLnkNode tail;
      if((tail=this.tail)==null){
        this.head=tail=new ByteDblLnkNode(val);
      }else{
        tail.next=tail=new ByteDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(byte val){
      ByteDblLnkNode head;
      if((head=this.head)==null){
        tail=head=new ByteDblLnkNode(val);
      }else{
        head.prev=head=new ByteDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeByte(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        ByteDblLnkNode curr;
        for(this.head=curr=new ByteDblLnkNode((byte)in.readByte());--size!=0;curr=curr.next=new ByteDblLnkNode(curr,(byte)in.readByte())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ByteDblLnkNode head,newTail;
        final var newHead=newTail=new ByteDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ByteDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static boolean isEqualToHelper(ByteDblLnkNode thisHead,ByteDblLnkNode thatHead,int size){
      for(;thisHead.val==thatHead.val;thisHead=thisHead.next,thatHead=thatHead.next){
        if(--size==0){
          return true;
        }
      }
      return false;
    }
    private boolean isEqualTo(int size,AbstractSeq<?> list){
      //TODO
      throw omni.util.NotYetImplementedException.getNYI();
    }
    private boolean isEqualTo(ByteDblLnkNode thisHead,int size,AbstractSeq<?> list){
      //TODO
      throw omni.util.NotYetImplementedException.getNYI();
    }
    private boolean isEqualTo(ListIterator<?> itr,ByteDblLnkNode head){
      if(itr.hasNext()){
        while(TypeUtil.refEquals(itr.next(),head.val)){
          if(!itr.hasNext()){
            return true;
          }
          head=head.next;
        }
      }
      return false;
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractSeq){
          final AbstractSeq<?> abstractSeq;
          if((abstractSeq=(AbstractSeq<?>)list).size==size){
            return this.isEqualTo(size,abstractSeq);
          }
        }else{
          return size==list.size() && this.isEqualTo(list.listIterator(),this.head);
        }
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(ByteDblLnkNode head
    ,int val
    ){
      {
        if(val==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(ByteDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfByte descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfByte iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfByte listIterator(int index){
      return new BidirectionalItr(this,((ByteDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        final int tailDist;
        final ByteDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=ByteDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?ByteDblLnkNode.iterateDescending(subListTail,subListSize-1):ByteDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=ByteDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?ByteDblLnkNode.iterateAscending(subListHead,subListSize-1):ByteDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public byte getLastByte(){
      return tail.val;
    }
    @Override public boolean offerFirst(byte val){
      push((byte)val);
      return true;
    }
    @Override public boolean offerLast(byte val){
      addLast((byte)val);
      return true;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public byte byteElement(){
      return head.val;
    }
    @Override public boolean offer(byte val){
      addLast((byte)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return ByteDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteDblLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if((v=(byte)val)==val){
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(ByteDblLnkNode tail
    ,int val
    ){
      {
        if(val==(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(ByteDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public Byte peekFirst(){
      return peek();
    }
    @Override public Byte pollFirst(){
      return poll();
    }
    @Override public Byte pop(){
      return popByte();
    }
    @Override public Byte remove(){
      return popByte();
    }
    @Override public boolean offer(Byte val){
      addLast((byte)val);
      return true;
    }
    @Override public Byte element(){
      return byteElement();
    }
    @Override public Byte removeFirst(){
      return popByte();
    }
    @Override public Byte removeLast(){
      return removeLastByte();
    }
    @Override public boolean offerFirst(Byte val){
      push((byte)val);
      return true;
    }
    @Override public boolean offerLast(Byte val){
      addLast((byte)val);
      return true;
    }
    @Override public void push(Byte val){
      push((byte)val);
    }
    @Override public void addFirst(Byte val){
      push((byte)val);
    }
    @Override public void addLast(Byte val){
      addLast((byte)val);
    }
    @Override public Byte getFirst(){
      return byteElement();
    }
    @Override public Byte getLast(){
      return getLastByte();
    }
    @Override public byte pollByte(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte poll(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Byte pollLast(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(double)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(float)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(long)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(int)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      ByteDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(short)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte peekLastByte(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte peek(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Byte peekLast(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (int)(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final ByteDblLnkNode head;
      if((head=this.head)!=null){
        return (short)(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short peekLastShort(){
      final ByteDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (short)(tail.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final ByteDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final ByteDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(ByteDblLnkNode prev,ByteDblLnkNode tail,BytePredicate filter){
      int numSurvivors=1;
      outer:for(ByteDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while(filter.test((next=next.next).val));
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(ByteDblLnkNode prev,ByteDblLnkNode curr,ByteDblLnkNode tail,BytePredicate filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(ByteDblLnkNode head,BytePredicate filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
            head.prev=null;
            this.head=head;
            return true;  
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head!=tail;++numSurvivors){
          final ByteDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(DescendingItr itr){
        super(itr);
      }
      private DescendingItr(UncheckedList parent){
        super(parent,parent.tail);
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          ByteDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            ByteDblLnkNode lastRet;
            if((lastRet=curr.next)==parent.tail){
              parent.tail=curr;
              curr.next=null;
            }else{
              curr.next=lastRet=lastRet.next;
              lastRet.prev=curr;
            }
          }
        }
      }
      @Override public byte nextByte(){
        final ByteDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(ByteDblLnkNode curr,ByteConsumer action){
        ByteDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractByteItr
    {
      transient final UncheckedList parent;
      transient ByteDblLnkNode curr;
      private AscendingItr(AscendingItr itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedList parent,ByteDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          ByteDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            ByteDblLnkNode lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
        }
      }
      @Override public byte nextByte(){
        final ByteDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(ByteDblLnkNode curr,ByteConsumer action){
        ByteDblLnkNode.uncheckedForEachAscending(curr,action);
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final ByteDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfByte{
      private transient int currIndex;
      private transient ByteDblLnkNode lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,ByteDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public byte nextByte(){
        final ByteDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public byte previousByte(){
        ByteDblLnkNode curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(byte val){
        final UncheckedList parent;
        ByteDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new ByteDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new ByteDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new ByteDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final ByteDblLnkNode tmp;
            (newNode=curr).prev=newNode=new ByteDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(byte val){
        lastRet.val=val;
      }
      @Override public void remove(){
        ByteDblLnkNode lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          if(lastRet==parent.tail){
            parent.tail=lastRet=lastRet.prev;
            lastRet.next=null;
          }else if(lastRet==parent.head){
            parent.head=curr;
            curr.prev=null;
          }else{
            curr.prev=lastRet=lastRet.prev;
            lastRet.next=curr;
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(ByteDblLnkNode curr,ByteConsumer action){
        ByteDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
