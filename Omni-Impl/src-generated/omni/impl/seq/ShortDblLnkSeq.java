package omni.impl.seq;
import omni.util.ShortSortUtil;
import omni.api.OmniList;
import omni.impl.ShortDblLnkNode;
import java.util.function.Consumer;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import java.util.function.UnaryOperator;
import omni.function.ShortUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractShortItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.ShortComparator;
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
public abstract class ShortDblLnkSeq extends AbstractSeq implements
   ShortSubListDefault
{
  private static final long serialVersionUID=1L;
  transient ShortDblLnkNode head;
  transient ShortDblLnkNode tail;
  private  ShortDblLnkSeq(){
  }
  private ShortDblLnkSeq(ShortDblLnkNode head,int size,ShortDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(short val);
  @Override public boolean add(short val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,ShortDblLnkNode after,ShortDblLnkNode newNode){
    newNode.next=after=ShortDblLnkNode.iterateDescending(after,dist-2);
    final ShortDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,ShortDblLnkNode before,ShortDblLnkNode newNode){
    newNode.prev=before=ShortDblLnkNode.iterateAscending(before,dist-1);
    final ShortDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,ShortDblLnkNode newNode){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
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
      ShortDblLnkNode head;
      if((head=this.head)==null){
        //the root was empty, so initialize it
        this.head=newNode;
        this.tail=newNode;
      }else if(index==0){
        //the insertion point IS the head
        head.prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,head,newNode);
      }
    }
  }
  private static  int markSurvivors(ShortDblLnkNode curr,ShortPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static  long markSurvivors(ShortDblLnkNode curr,ShortPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private ShortDblLnkNode getNode(int index,int size){
    int tailDist;
    if((tailDist=size-index)<index){
      //the node is closer to the tail
      return ShortDblLnkNode.iterateDescending(tail,tailDist-1);
    }else{
      //the node is closer to the head
      return ShortDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public short set(int index,short val){
    ShortDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,short val){
    getNode(index,size).val=val;
  }
  @Override public short getShort(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(ShortConsumer action){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Short> action){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      ShortDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      ShortDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      final short[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public Short[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Short[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new Short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      ShortDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public void replaceAll(ShortUnaryOperator operator){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Short> operator){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(ShortComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final short[] tmp;
      final ShortDblLnkNode tail;
      ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
      {
        if(sorter==null){
          ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Short> sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final short[] tmp;
      final ShortDblLnkNode tail;
      ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
      {
        if(sorter==null){
          ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final short[] tmp;
      final ShortDblLnkNode tail;
      ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
      {
          ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final short[] tmp;
      final ShortDblLnkNode tail;
      ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
      {
          ShortSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(ShortComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final short[] tmp;
      final ShortDblLnkNode tail;
      ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
      {
        if(sorter==null){
          ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            ShortSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE>>3)){(buffer=new byte[size<<3])
        [size=ShortDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        ShortDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      return ShortDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(short)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    if(val==(short)val)
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if((v=(short)val)==val){
            return ShortDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Short||val instanceof Byte){
              i=((Number)val).shortValue();
            }else if(val instanceof Integer){
              if((i=(int)val)!=(short)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(short)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(short)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(short)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Short.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ShortDblLnkNode.uncheckedcontains(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    if(val<=Short.MAX_VALUE)
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedindexOf(head,tail,(short)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    if(val==(short)val)
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if((v=(short)val)==val){
            return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Short||val instanceof Byte){
              i=((Number)val).shortValue();
            }else if(val instanceof Integer){
              if((i=(int)val)!=(short)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(short)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(short)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(short)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Short.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ShortDblLnkNode.uncheckedindexOf(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    if(val<=Short.MAX_VALUE)
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final ShortDblLnkNode head;
        if((head=this.head)!=null)
        {
          return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(short)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    if(val==(short)val)
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if((v=(short)val)==val){
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if(val==(v=(short)val))
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Short||val instanceof Byte){
              i=((Number)val).shortValue();
            }else if(val instanceof Integer){
              if((i=(int)val)!=(short)i){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(short)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(short)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(short)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              if((i=(char)val)>Short.MAX_VALUE){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    if(val<=Short.MAX_VALUE)
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static class UncheckedSubList extends ShortDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private Object writeReplace(){
      return new UncheckedList(this.head,this.size,this.tail);
    }
    private void bubbleUpAppend(ShortDblLnkNode newNode){
      for(var curr=this;;){
        curr.tail=newNode;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpAppend(ShortDblLnkNode newNode,ShortDblLnkNode oldTail){
      for(var curr=this;;){
        curr.tail=newNode;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.tail!=oldTail){
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(ShortDblLnkNode newNode){
      for(var curr=this;;){
        curr.head=newNode;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpPrepend(ShortDblLnkNode newNode,ShortDblLnkNode oldHead){
      for(var curr=this;;){
        curr.head=newNode;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.head!=oldHead){
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;
      ++curr.size,curr=curr.parent){}
    }
    @Override public void add(int index,short val){
      int size;
      UncheckedSubList curr;
      final var newNode=new ShortDblLnkNode(val);
      if((size=++(curr=this).size)==1){
        //initialize this list
        UncheckedSubList parent;
        do{
          curr.head=newNode;
          curr.tail=newNode;
          if((parent=curr.parent)==null){
            //all parents were empty, insert in the root
            ((ShortDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
            return;
          }
        }
        while((size=++(curr=parent).size)==1);
      }
      final UncheckedList root;
      ++(root=this.root).size;
      ShortDblLnkNode before,after;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        if(size==1){
          //the insertion point IS the tail
          if((after=(before=curr.tail).next)==null){
            //there are no nodes after this list
            curr.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            curr.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          //iterate from the tail and insert
          before=(after=ShortDblLnkNode.iterateDescending(curr.tail,size-1)).prev;
          after.prev=newNode;
          curr.bubbleUpIncrementSize();
        }
        before.next=newNode;
      }else{
        //the insertion point is closer to the head
        if(index==0){
          //the insertion point IS the tail
          if((before=(after=curr.head).prev)==null){
            //there are no nodes before this list
            curr.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            //there are nodes before this list
            curr.bubbleUpPrepend(newNode,after);
            before.next=newNode;
          }
        }else{
          //iterate from the head and insert
          after=(before=ShortDblLnkNode.iterateAscending(curr.head,index-1)).next;
          before.next=newNode;
          curr.bubbleUpIncrementSize();
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    @Override void addLast(short val){
      final UncheckedList root=this.root;
      var newNode=new ShortDblLnkNode(val);
      UncheckedSubList parent,curr=this;
      for(;++curr.size==1;curr=parent){
        curr.head=newNode;
        curr.tail=newNode;
        if((parent=curr.parent)==null){
          //all parents were empty, insert in the root
          ((ShortDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
          return;
        }
      }
      ShortDblLnkNode oldTail,after;
      if((after=(oldTail=curr.tail).next)==null){
        curr.bubbleUpAppend(newNode);
        root.tail=newNode;
      }else{
        curr.bubbleUpAppend(newNode,oldTail);
        after.prev=newNode;
      }
      ++root.size;
      newNode.next=after;
      oldTail.next=newNode;
      newNode.prev=oldTail;
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        final UncheckedList root;
        (root=this.root).size-=size;
        clearAllHelper(size,root);
      }
    }
    private void clearAllHelper(int size,UncheckedList root)
    {
      ShortDblLnkNode before,head,tail,after=(tail=this.tail).next;
      if((before=(head=this.head).prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.head=null;
          root.tail=null;
        }else{
          after.prev=null;
          bubbleUpClearHead(tail,after,size);
          root.head=after;
        }
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          after.prev=before;
          bubbleUpClearBody(before,head,size,tail,after);
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
      var curr=this;
      do{
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(ShortDblLnkNode before,ShortDblLnkNode head,int numRemoved,ShortDblLnkNode tail,ShortDblLnkNode after){
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
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(ShortDblLnkNode tail, ShortDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(ShortDblLnkNode head, ShortDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }
          while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean removeIf(ShortPredicate filter){
      final ShortDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Short> filter){
      final ShortDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private void collapseHeadHelper(ShortDblLnkNode oldHead,ShortDblLnkNode tail,ShortPredicate filter)
    {
      //TODO
    }
    private void collapseTailHelper(ShortDblLnkNode head,ShortDblLnkNode oldTail,ShortPredicate filter)
    {
      //TODO
    }
    private void collapseHeadAndTailHelper(ShortDblLnkNode oldHead,ShortDblLnkNode oldTail,ShortPredicate filter)
    {
      //TODO
    }
    private boolean collapseBodyHelper(ShortDblLnkNode head,ShortDblLnkNode tail,ShortPredicate filter)
    {
      //TODO
      return false;
    }
    private boolean uncheckedRemoveIf(ShortDblLnkNode head,ShortPredicate filter){
      var tail=this.tail;
      if(filter.test(head.val))
      {
        if(tail==head)
        {
          this.size=0;
          removeLastNode(head);
          --root.size;
        }
        else
        {
          if(filter.test(tail.val))
          {
            collapseHeadAndTailHelper(head,tail,filter);
          }
          else
          {
            collapseHeadHelper(head,tail,filter);
          }
        }
        return true;
      }
      else
      {
        if(tail!=head)
        {
          if(filter.test(tail.val))
          {
            collapseTailHelper(head,tail,filter);
            return true;
          }
          return collapseBodyHelper(head,tail,filter);
        }
      }
      return false;
    }
    private void bubbleUpPeelHead(ShortDblLnkNode newHead,ShortDblLnkNode oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(ShortDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(ShortDblLnkNode newTail,ShortDblLnkNode oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(ShortDblLnkNode newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
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
    private void peelTail(ShortDblLnkNode tail){
      ShortDblLnkNode after,before;
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
    private void removeLastNode(ShortDblLnkNode lastNode){
      ShortDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
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
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(ShortDblLnkNode head){
      ShortDblLnkNode after,before;
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
    @Override public short removeShortAt(int index){
      final short ret;
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
          ShortDblLnkNode before;
          ret=(before=( tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          ShortDblLnkNode after;
          ret=(after=( head=ShortDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ShortDblLnkNode head,newTail;
        final var newHead=newTail=new ShortDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ShortDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static class AscendingItr
      extends AbstractShortItr
    {
      transient final UncheckedSubList parent;
      transient ShortDblLnkNode curr;
      private AscendingItr(UncheckedSubList parent,ShortDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedSubList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public short nextShort(){
        final ShortDblLnkNode curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          ShortDblLnkNode curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            ShortDblLnkNode lastRet;
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
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfShort{
      transient int currIndex;
      transient ShortDblLnkNode lastRet;
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,ShortDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public short nextShort(){
        final ShortDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public short previousShort(){
        ShortDblLnkNode curr;
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
      @Override public void set(short val){
        lastRet.val=val;
      }
      @Override public void add(short val){
        int size;
        UncheckedSubList currList;
        final var newNode=new ShortDblLnkNode(val);
        this.lastRet=null;
        if((size=++(currList=this.parent).size)==1){
          ++currIndex;
          //initialize the list
          UncheckedSubList parent;
          do{
            currList.head=newNode;
            currList.tail=newNode;
            if((parent=currList.parent)==null){
              //all parents were empty, insert in the root
              ((ShortDblLnkSeq)currList.root).insertNode(currList.parentOffset,newNode);
              this.curr=newNode.next;
              return;
            }
          }while((size=++(currList=parent).size)==1);
        }
        final UncheckedList root;
        ++(root=currList.root).size;
        ShortDblLnkNode after,before;
        int currIndex;
        if((currIndex=++this.currIndex)==size){
          //the insertion point IS the tail
          if((after=(before=currList.tail).next)==null){
            //there are no nodes after this list
            currList.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            currList.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          if(currIndex==1){
            //the insertion point IS the head
            if((before=(after=currList.head).prev)==null){
              //there are no nodes before this list
              currList.bubbleUpPrepend(newNode);
              root.tail=newNode;
            }else{
              //there are nodes before this list
              currList.bubbleUpPrepend(newNode,after);
              before.next=newNode;
            }
          }else{
            newNode.next=after=curr;
            newNode.prev=before=after.prev;
            after.prev=newNode;
            before.next=newNode;
            currList.bubbleUpIncrementSize();
          }
        }
      }
      @Override public void remove(){
        ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
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
              ShortDblLnkNode.eraseNode(lastRet);
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final ShortDblLnkNode lastRet;
          ShortDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=lastRet.next;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final ShortDblLnkNode lastRet;
          ShortDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=lastRet.next;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfShort iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      return new BidirectionalItr(this,((ShortDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      final int tailDist,subListSize=toIndex-fromIndex;
      final ShortDblLnkNode subListHead,subListTail;
      if((tailDist=this.size-toIndex)<=fromIndex){
        subListTail=ShortDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?ShortDblLnkNode.iterateDescending(subListTail,subListSize):ShortDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=ShortDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?ShortDblLnkNode.iterateAscending(subListHead,subListSize):ShortDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode head;
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private boolean uncheckedremoveVal(ShortDblLnkNode head
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
          if(val==((head=head.next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(head);
            }else{
              ShortDblLnkNode before,after;
              (before=head.prev).next=(after=head.next);
              after.prev=before;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  private static class CheckedSubList extends ShortDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private boolean uncheckedremoveVal(ShortDblLnkNode head
    ,int val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(val==(head.val)){
          root.modCount=++modCount;
          --root.size;
          this.modCount=modCount;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }else{
          for(final var tail=this.tail;tail!=head;){
            if(val==((head=head.next).val)){
              root.modCount=++modCount;
              --root.size;
              this.modCount=modCount;
              this.size=size-1;
              if(head==tail){
                peelTail(head);
              }else{
                ShortDblLnkNode before,after;
                (before=head.prev).next=(after=head.next);
                after.prev=before;
                bubbleUpDecrementSize();
              }
              return true;
            }
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfShort iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((ShortDblLnkSeq)this).getNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractShortItr
      implements OmniListIterator.OfShort
    {
      transient final CheckedSubList parent;
      transient int modCount;
      transient ShortDblLnkNode curr;
      transient ShortDblLnkNode lastRet;
      transient int currIndex;
      BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      BidirectionalItr(CheckedSubList parent,ShortDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public short nextShort(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          ShortDblLnkNode curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public short previousShort(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          ShortDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
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
      @Override public void set(short val){
        final ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null)
        {
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(ShortConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            ShortDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          ShortDblLnkNode lastRet;
          this.lastRet=lastRet=parent.tail;
          this.curr=lastRet.next;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            ShortDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          ShortDblLnkNode lastRet;
          this.lastRet=lastRet=parent.tail;
          this.curr=lastRet.next;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
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
                ShortDblLnkNode.eraseNode(lastRet);
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(short val){
        CheckedSubList currList;
        CheckedList root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=this.parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        int size;
        final var newNode=new ShortDblLnkNode(val);
        this.lastRet=null;
        if((size=++currList.size)==1){
          ++currIndex;
          //initialize the list
          CheckedSubList parent;
          do{
            currList.head=newNode;
            currList.tail=newNode;
            if((parent=currList.parent)==null){
              //all parents were empty, insert in the root
              ((ShortDblLnkSeq)currList.root).insertNode(currList.parentOffset,newNode);
              this.curr=newNode.next;
              return;
            }
          }while((size=++(currList=parent).size)==1);
        }
        ++root.size;
        ShortDblLnkNode after,before;
        int currIndex;
        if((currIndex=++this.currIndex)==size){
          //the insertion point IS the tail
          if((after=(before=currList.tail).next)==null){
            //there are no nodes after this list
            currList.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            currList.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          if(currIndex==1){
            //the insertion point IS the head
            if((before=(after=currList.head).prev)==null){
              //there are no nodes before this list
              currList.bubbleUpPrepend(newNode);
              root.tail=newNode;
            }else{
              //there are nodes before this list
              currList.bubbleUpPrepend(newNode,after);
              before.next=newNode;
            }
          }else{
            newNode.next=after=curr;
            newNode.prev=before=after.prev;
            after.prev=newNode;
            before.next=newNode;
            currList.bubbleUpIncrementSize();
          }
        }
      }
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size);
      final ShortDblLnkNode subListHead,subListTail;
      if((tailDist-=toIndex)<=fromIndex){
        subListTail=ShortDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?ShortDblLnkNode.iterateDescending(subListTail,subListSize):ShortDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=ShortDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?ShortDblLnkNode.iterateAscending(subListHead,subListSize):ShortDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        ShortDblLnkNode head,newTail;
        final var newHead=newTail=new ShortDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ShortDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode head;
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
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
          final ShortDblLnkNode head;
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
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final ShortDblLnkNode head;
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(short)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
              return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedindexOf(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortDblLnkNode.uncheckedindexOf(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      if(val<=Short.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(short)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if((v=(short)val)==val){
              return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,v);
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      if(val<=Short.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(short)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
              return ShortDblLnkNode.uncheckedcontains(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedcontains(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedcontains(head,tail,v);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortDblLnkNode.uncheckedcontains(head,tail,i);
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      if(val<=Short.MAX_VALUE)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient ShortDblLnkNode head;
      private transient ShortDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(ShortDblLnkNode head,int size,ShortDblLnkNode tail,CheckedList.ModCountChecker modCountChecker){
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
          ShortDblLnkNode curr;
          for(this.head=curr=new ShortDblLnkNode((short)ois.readShort());--size!=0;curr=curr.next=new ShortDblLnkNode(curr,(short)ois.readShort())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            do{
              oos.writeShort(curr.val);
            }while((curr=curr.next)!=null);
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    @Override public boolean removeIf(ShortPredicate filter){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Short> filter){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private boolean uncheckedRemoveIf(ShortDblLnkNode head,ShortPredicate filter){
      //TODO
      return false;
    }
    private void bubbleUpPeelHead(ShortDblLnkNode newHead,ShortDblLnkNode oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(ShortDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(ShortDblLnkNode newTail,ShortDblLnkNode oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(ShortDblLnkNode newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
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
    private void peelTail(ShortDblLnkNode tail){
      ShortDblLnkNode after,before;
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
    private void removeLastNode(ShortDblLnkNode lastNode){
      ShortDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
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
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(ShortDblLnkNode head){
      ShortDblLnkNode after,before;
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
    @Override public short removeShortAt(int index){
      final short ret;
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
          ShortDblLnkNode before;
          ret=(before=( tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          ShortDblLnkNode after;
          ret=(after=( head=ShortDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    private void bubbleUpAppend(ShortDblLnkNode newNode){
      for(var curr=this;;){
        curr.tail=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpAppend(ShortDblLnkNode newNode,ShortDblLnkNode oldTail){
      for(var curr=this;;){
        curr.tail=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.tail!=oldTail){
          ++curr.modCount;
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(ShortDblLnkNode newNode){
      for(var curr=this;;){
        curr.head=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpPrepend(ShortDblLnkNode newNode,ShortDblLnkNode oldHead){
      for(var curr=this;;){
        curr.head=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.head!=oldHead){
          ++curr.modCount;
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      ++curr.size,curr=curr.parent){}
    }
    @Override public void add(int index,short val){
      int size;
      CheckedSubList curr;
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      root.modCount=++modCount;
      (curr=this).size=++size;
      final var newNode=new ShortDblLnkNode(val);
      if(size==1){
        //initialize this list
        CheckedSubList parent;
        do{
          curr.head=newNode;
          curr.tail=newNode;
          curr.modCount=modCount;
          if((parent=curr.parent)==null){
            //all parents were empty, insert in the root
            ((ShortDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
            return;
          }
        }
        while((size=++(curr=parent).size)==1);
      }
      ++root.size;
      ShortDblLnkNode before,after;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        if(size==1){
          //the insertion point IS the tail
          if((after=(before=curr.tail).next)==null){
            //there are no nodes after this list
            curr.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            curr.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          //iterate from the tail and insert
          before=(after=ShortDblLnkNode.iterateDescending(curr.tail,size-1)).prev;
          after.prev=newNode;
          curr.modCount=modCount;
          curr.bubbleUpIncrementSize();
        }
        before.next=newNode;
      }else{
        //the insertion point is closer to the head
        if(index==0){
          //the insertion point IS the tail
          if((before=(after=curr.head).prev)==null){
            //there are no nodes before this list
            curr.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            //there are nodes before this list
            curr.bubbleUpPrepend(newNode,after);
            before.next=newNode;
          }
        }else{
          //iterate from the head and insert
          after=(before=ShortDblLnkNode.iterateAscending(curr.head,index-1)).next;
          before.next=newNode;
          curr.modCount=modCount;
          curr.bubbleUpIncrementSize();
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    @Override void addLast(short val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      var newNode=new ShortDblLnkNode(val);
      CheckedSubList parent,curr=this;
      for(;++curr.size==1;curr=parent){
        curr.head=newNode;
        curr.tail=newNode;
        curr.modCount=modCount;
        if((parent=curr.parent)==null){
          //all parents were empty, insert in the root
          ((ShortDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
          return;
        }
      }
      ShortDblLnkNode oldTail,after;
      if((after=(oldTail=curr.tail).next)==null){
        curr.bubbleUpAppend(newNode);
        root.tail=newNode;
      }else{
        curr.bubbleUpAppend(newNode,oldTail);
        after.prev=newNode;
      }
      ++root.size;
      newNode.next=after;
      oldTail.next=newNode;
      newNode.prev=oldTail;
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
        clearAllHelper(size,root);
      }
    }
    private void clearAllHelper(int size,CheckedList root)
    {
      ShortDblLnkNode before,head,tail,after=(tail=this.tail).next;
      if((before=(head=this.head).prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.head=null;
          root.tail=null;
        }else{
          after.prev=null;
          bubbleUpClearHead(tail,after,size);
          root.head=after;
        }
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          after.prev=before;
          bubbleUpClearBody(before,head,size,tail,after);
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
      var curr=this;
      do{
        ++curr.modCount;
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(ShortDblLnkNode before,ShortDblLnkNode head,int numRemoved,ShortDblLnkNode tail,ShortDblLnkNode after){
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
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(ShortDblLnkNode tail, ShortDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(ShortDblLnkNode head, ShortDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }
          while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    @Override public short set(int index,short val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final ShortDblLnkNode node;
      final var ret=(node=((ShortDblLnkSeq)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,short val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((ShortDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public short getShort(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((ShortDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(ShortUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final ShortDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        ShortDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(ShortConsumer action){
      final int modCount=this.modCount;
      try{
        final ShortDblLnkNode head;
        if((head=this.head)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(ShortComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            ShortSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Short> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final ShortDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        ShortDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Short> action){
      final int modCount=this.modCount;
      try{
        final ShortDblLnkNode head;
        if((head=this.head)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Short> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(ShortComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
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
    @Override public short[] toShortArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toShortArray();
    }
    @Override public Short[] toArray(){
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
    @Override public String toString(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toString();
    }
    @Override public int hashCode(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.hashCode();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    } 
  }
  public static class CheckedList extends UncheckedList{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(ShortDblLnkNode head,int size,ShortDblLnkNode tail){
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
    @Override public short removeLastShort(){
      ShortDblLnkNode tail;
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
    @Override public short popShort(){
      ShortDblLnkNode head;
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
    @Override public short removeShortAt(int index){
      final short ret;
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
          ShortDblLnkNode before;
          ret=(before=(tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          ShortDblLnkNode after;
          ret=(after=(head=ShortDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,short val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=++size;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new ShortDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          ShortDblLnkNode before;
          (before=(tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).next=before=new ShortDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        ShortDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new ShortDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new ShortDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          ShortDblLnkNode after;
          (after=(head=ShortDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new ShortDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(short val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(short val){
      ++this.modCount;
      super.push(val);
    }
    @Override public short set(int index,short val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ShortDblLnkNode tmp;
      final var ret=(tmp=((ShortDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,short val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((ShortDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public short getShort(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((ShortDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public short getLastShort(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public short shortElement(){
      final ShortDblLnkNode head;
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
    @Override public void forEach(ShortConsumer action){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(ShortUnaryOperator operator){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(ShortComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
            ShortSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Short> action){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Short> operator){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Short> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(ShortComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final short[] tmp;
        final ShortDblLnkNode tail;
        ShortDblLnkNode.uncheckedCopyInto(tmp=new short[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            ShortSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              ShortSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        ShortDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(ShortDblLnkNode prev,ShortPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(ShortDblLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(ShortDblLnkNode prev,ShortPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
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
    @Override boolean uncheckedRemoveIf(ShortDblLnkNode head,ShortPredicate filter){
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
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ShortDblLnkNode head,newTail;
        final var newHead=newTail=new ShortDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ShortDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfShort descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfShort iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((ShortDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size);
      final ShortDblLnkNode subListHead,subListTail;
      if((tailDist-=toIndex)<=fromIndex){
        subListTail=ShortDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?ShortDblLnkNode.iterateDescending(subListTail,subListSize):ShortDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=ShortDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?ShortDblLnkNode.iterateAscending(subListHead,subListSize):ShortDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    } 
    boolean uncheckedremoveLastOccurrence(ShortDblLnkNode tail
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
        for(ShortDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(ShortDblLnkNode head
    ,int val
    ){
      {
        if(val==(head.val)){
          this.modCount=modCount+1;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(ShortDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            this.modCount=modCount+1;
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
    @Override public short pollShort(){
      ShortDblLnkNode head;
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
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      ShortDblLnkNode tail;
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
      return Short.MIN_VALUE;
    }
    @Override public Short poll(){
      ShortDblLnkNode head;
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
    @Override public Short pollLast(){
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
    private static class DescendingItr
      extends AbstractShortItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient ShortDblLnkNode curr;
      transient ShortDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,ShortDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public short nextShort(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        ShortDblLnkNode lastRet;
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
              ShortDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(ShortConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            ShortDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            ShortDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfShort{
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,ShortDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public short nextShort(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public short previousShort(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          ShortDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
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
      @Override public void set(short val){
        final ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(short val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        ShortDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new ShortDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new ShortDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new ShortDblLnkNode(val,newNode);
          }else{
            final ShortDblLnkNode tmp;
            (newNode=curr).prev=newNode=new ShortDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
          }
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
              ShortDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            ShortDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            ShortDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
  }
  public static class UncheckedList extends ShortDblLnkSeq implements OmniDeque.OfShort,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public short removeLastShort(){
      ShortDblLnkNode tail;
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
    @Override public short popShort(){
      ShortDblLnkNode head;
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
    @Override public short removeShortAt(int index){
      final short ret;
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
          ShortDblLnkNode before;
          ret=(before=(tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          ShortDblLnkNode after;
          ret=(after=(head=ShortDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,short val){
      int size;
      if((size=++this.size-index)<index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new ShortDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          ShortDblLnkNode before;
          (before=(tail=ShortDblLnkNode.iterateDescending(tail,size-1)).prev).next=before=new ShortDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        ShortDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new ShortDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new ShortDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          ShortDblLnkNode after;
          (after=(head=ShortDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new ShortDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(short val){
      ShortDblLnkNode tail;
      if((tail=this.tail)==null){
        this.head=tail=new ShortDblLnkNode(val);
      }else{
        tail.next=tail=new ShortDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(short val){
      ShortDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new ShortDblLnkNode(val);
      }else{
        head.prev=head=new ShortDblLnkNode(val,head);
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
          out.writeShort(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        ShortDblLnkNode curr;
        for(this.head=curr=new ShortDblLnkNode((short)in.readShort());--size!=0;curr=curr.next=new ShortDblLnkNode(curr,(short)in.readShort())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        ShortDblLnkNode head,newTail;
        final var newHead=newTail=new ShortDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ShortDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode head;
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
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
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(ShortDblLnkNode head
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
        for(ShortDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfShort descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfShort iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      return new BidirectionalItr(this,((ShortDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      final int tailDist,subListSize=toIndex-fromIndex;
      final ShortDblLnkNode subListHead,subListTail;
      if((tailDist=this.size-toIndex)<=fromIndex){
        subListTail=ShortDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?ShortDblLnkNode.iterateDescending(subListTail,subListSize):ShortDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=ShortDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?ShortDblLnkNode.iterateAscending(subListHead,subListSize):ShortDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public short getLastShort(){
      return tail.val;
    }
    @Override public boolean offerFirst(short val){
      push((short)val);
      return true;
    }
    @Override public boolean offerLast(short val){
      addLast((short)val);
      return true;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public short shortElement(){
      return head.val;
    }
    @Override public boolean offer(short val){
      addLast((short)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val){
              return ShortDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortDblLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final ShortDblLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode tail;
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if((v=(short)val)==val){
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
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
    @Override public boolean removeLastOccurrence(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(ShortDblLnkNode tail
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
        for(ShortDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    @Override public Short peekFirst(){
      return peek();
    }
    @Override public Short pollFirst(){
      return poll();
    }
    @Override public Short pop(){
      return popShort();
    }
    @Override public Short remove(){
      return popShort();
    }
    @Override public boolean offer(Short val){
      addLast((short)val);
      return true;
    }
    @Override public Short element(){
      return shortElement();
    }
    @Override public Short removeFirst(){
      return popShort();
    }
    @Override public Short removeLast(){
      return removeLastShort();
    }
    @Override public boolean offerFirst(Short val){
      push((short)val);
      return true;
    }
    @Override public boolean offerLast(Short val){
      addLast((short)val);
      return true;
    }
    @Override public void push(Short val){
      push((short)val);
    }
    @Override public void addFirst(Short val){
      push((short)val);
    }
    @Override public void addLast(Short val){
      addLast((short)val);
    }
    @Override public Short getFirst(){
      return shortElement();
    }
    @Override public Short getLast(){
      return getLastShort();
    }
    @Override public short pollShort(){
      ShortDblLnkNode head;
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
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      ShortDblLnkNode tail;
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
      return Short.MIN_VALUE;
    }
    @Override public Short poll(){
      ShortDblLnkNode head;
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
    @Override public Short pollLast(){
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
      ShortDblLnkNode head;
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
      ShortDblLnkNode tail;
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
    @Override public short peekShort(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short peekLastShort(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public Short peek(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Short peekLast(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final ShortDblLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final ShortDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (int)(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public boolean removeIf(ShortPredicate filter){
      final ShortDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Short> filter){
      final ShortDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(ShortDblLnkNode prev,ShortDblLnkNode tail,ShortPredicate filter){
      int numSurvivors=1;
      outer:for(ShortDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(ShortDblLnkNode prev,ShortDblLnkNode curr,ShortDblLnkNode tail,ShortPredicate filter){
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
    boolean uncheckedRemoveIf(ShortDblLnkNode head,ShortPredicate filter){
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
          final ShortDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    private static class AscendingItr
      extends AbstractShortItr
    {
      transient final UncheckedList parent;
      transient ShortDblLnkNode curr;
      private AscendingItr(UncheckedList parent,ShortDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        this.curr=parent.head;
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
          ShortDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            ShortDblLnkNode lastRet;
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
      @Override public short nextShort(){
        final ShortDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(UncheckedList parent){
        super(parent,parent.tail);
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          ShortDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            ShortDblLnkNode lastRet;
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
      @Override public short nextShort(){
        final ShortDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfShort{
      transient int currIndex;
      transient ShortDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,ShortDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public short nextShort(){
        final ShortDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public short previousShort(){
        ShortDblLnkNode curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return curr.prev!=null;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(short val){
        final UncheckedList parent;
        ShortDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new ShortDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new ShortDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new ShortDblLnkNode(val,newNode);
          }else{
            final ShortDblLnkNode tmp;
            (newNode=curr).prev=newNode=new ShortDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(short val){
        lastRet.val=val;
      }
      @Override public void remove(){
        ShortDblLnkNode lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
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
            parent.head=lastRet=lastRet.next;
            lastRet.prev=null;
          }else{
            ShortDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortDblLnkNode curr;
        if((curr=this.curr)!=null){
          ShortDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
  }
}
