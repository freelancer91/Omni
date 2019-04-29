package omni.impl.seq;
import omni.util.LongSortUtil;
import omni.api.OmniList;
import omni.impl.LongDblLnkNode;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.UnaryOperator;
import java.util.function.LongUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractLongItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.LongComparator;
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
public abstract class LongDblLnkSeq extends AbstractSeq implements
   LongSubListDefault
{
  private static final long serialVersionUID=1L;
  transient LongDblLnkNode head;
  transient LongDblLnkNode tail;
  private  LongDblLnkSeq(){
  }
  private LongDblLnkSeq(LongDblLnkNode head,int size,LongDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(long val);
  @Override public boolean add(long val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,LongDblLnkNode after,LongDblLnkNode newNode){
    newNode.next=after=LongDblLnkNode.iterateDescending(after,dist-2);
    final LongDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,LongDblLnkNode before,LongDblLnkNode newNode){
    newNode.prev=before=LongDblLnkNode.iterateAscending(before,dist-1);
    final LongDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,LongDblLnkNode newNode){
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
      LongDblLnkNode head;
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
  private static  int markSurvivors(LongDblLnkNode curr,int numLeft,LongPredicate filter,long[] survivorSet){
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
  private static  long markSurvivors(LongDblLnkNode curr,int numLeft,LongPredicate filter){
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
  private LongDblLnkNode getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return LongDblLnkNode.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return LongDblLnkNode.iterateAscending(head,index);
    }
  }
  private LongDblLnkNode getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return LongDblLnkNode.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return LongDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public long set(int index,long val){
    LongDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,long val){
    getNode(index,size).val=val;
  }
  @Override public long getLong(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(LongConsumer action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      LongDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      LongDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Long[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new Long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public void replaceAll(LongUnaryOperator operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Long> operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(LongComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null){
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Long> sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null){
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
          LongSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(LongComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null){
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=LongDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        LongDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      return LongDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToLong(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          final long v;
          if(TypeUtil.doubleEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final long l;
            if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
              l=((Number)val).longValue();
            }else if(val instanceof Float){
              final float f;
              if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              l=(char)val;
            }else if(val instanceof Boolean){
              l=TypeUtil.castToLong((boolean)val);
            }else{
              break returnFalse;
            }
            return LongDblLnkNode.uncheckedcontains(head,tail,l);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToLong(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          final long v;
          if(TypeUtil.doubleEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final LongDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final long l;
            if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
              l=((Number)val).longValue();
            }else if(val instanceof Float){
              final float f;
              if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              l=(char)val;
            }else if(val instanceof Boolean){
              l=TypeUtil.castToLong((boolean)val);
            }else{
              break returnFalse;
            }
            return LongDblLnkNode.uncheckedindexOf(head,tail,l);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToLong(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final long v;
          if(TypeUtil.doubleEquals(val,v=(long)val))
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final long l;
            if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
              l=((Number)val).longValue();
            }else if(val instanceof Float){
              final float f;
              if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              l=(char)val;
            }else if(val instanceof Boolean){
              l=TypeUtil.castToLong((boolean)val);
            }else{
              break returnFalse;
            }
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,l);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static  int collapseBodyHelper(LongDblLnkNode newHead,LongDblLnkNode newTail,LongPredicate filter){
    int numRemoved=0;
    outer:for(LongDblLnkNode prev;(newHead=(prev=newHead).next)!=newTail;){
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
  private static class UncheckedSubList extends LongDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    private void bubbleUpAppend(LongDblLnkNode oldTail,LongDblLnkNode newTail){
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
    private void bubbleUpAppend(LongDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(LongDblLnkNode oldHead,LongDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(LongDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(LongDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,LongDblLnkNode newNode){
      LongDblLnkNode after,before;   
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
          before=(after=LongDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=LongDblLnkNode.uncheckedIterateAscending(after,index)).next;
          before.prev=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(LongDblLnkNode newNode){
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
      ((LongDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,long val){
      final UncheckedList root;
      final var newNode=new LongDblLnkNode(val);
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
    @Override void addLast(long val){
      final UncheckedList root;
      if(++(root=this.root).size!=1){
        if(++this.size!=1){
          LongDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new LongDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new LongDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new LongDblLnkNode(val));
        }
      }else{
        LongDblLnkNode newNode;
        bubbleUpRootInit(newNode=new LongDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private void bubbleUpPeelHead(LongDblLnkNode newHead,LongDblLnkNode oldHead){
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
    private void bubbleUpPeelHead(LongDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(LongDblLnkNode newTail,LongDblLnkNode oldTail){
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
    private void bubbleUpPeelTail(LongDblLnkNode newTail){
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
    private void peelTail(LongDblLnkNode tail){
      LongDblLnkNode after,before;
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
    private void removeLastNode(LongDblLnkNode lastNode){
      LongDblLnkNode after,before=lastNode.prev;
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
    private void peelHead(LongDblLnkNode head){
      LongDblLnkNode after,before;
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
    @Override public long removeLongAt(int index){
      final long ret;
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
          LongDblLnkNode before;
          ret=(before=( tail=LongDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          LongDblLnkNode after;
          ret=(after=( head=LongDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(LongPredicate filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Long> filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private void collapsehead(LongDblLnkNode oldhead,LongDblLnkNode tail,LongPredicate filter
    ){
      int numRemoved=1;
      LongDblLnkNode newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          break;
        }
        if(!filter.test(newhead.val)){
          LongDblLnkNode prev,curr;
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
      LongDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(LongDblLnkNode oldtail,LongDblLnkNode head,LongPredicate filter
    ){
      int numRemoved=1;
      LongDblLnkNode newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          break;
        }
        if(!filter.test(newtail.val)){
          LongDblLnkNode next,curr;
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
      LongDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(LongDblLnkNode oldHead,LongDblLnkNode newHead,int numRemoved,LongDblLnkNode newTail,LongDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final LongDblLnkNode after,before=oldHead.prev;
      if((after=oldHead.next)==null){
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
    private boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
      LongDblLnkNode tail;
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
    private void clearAllHelper(int size,LongDblLnkNode head,LongDblLnkNode tail,UncheckedList root){
      LongDblLnkNode before,after=tail.next;
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
      var curr=this;
      do{
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(LongDblLnkNode before,LongDblLnkNode head,int numRemoved,LongDblLnkNode tail,LongDblLnkNode after){
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
    private void bubbleUpClearHead(LongDblLnkNode tail, LongDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(LongDblLnkNode head, LongDblLnkNode before,int numRemoved){
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
    private void collapseHeadAndTail(LongDblLnkNode head,LongDblLnkNode tail,LongPredicate filter
    ){
      LongDblLnkNode newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            LongDblLnkNode prev;
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
    private boolean collapseBody(LongDblLnkNode head,LongDblLnkNode tail,LongPredicate filter
    ){
      for(LongDblLnkNode prev;(head=(prev=head).next)!=tail;){
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
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static class AscendingItr
      extends AbstractLongItr
    {
      transient final UncheckedSubList parent;
      transient LongDblLnkNode curr;
      private AscendingItr(UncheckedSubList parent,LongDblLnkNode curr){
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
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          LongDblLnkNode curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            LongDblLnkNode lastRet;
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
      @Override public void forEachRemaining(LongConsumer action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfLong{
      private transient int currIndex;
      private transient LongDblLnkNode lastRet;
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,LongDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public long previousLong(){
        LongDblLnkNode curr;
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
      @Override public void set(long val){
        lastRet.val=val;
      }
      @Override public void add(long val){
        final UncheckedSubList currList;
        final UncheckedList root;
        this.lastRet=null;
        ++currIndex;
        if(++(root=(currList=this.parent).root).size!=1){
          if(++currList.size!=1){
            LongDblLnkNode after,before,newNode;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                currList.bubbleUpIncrementSize();
                before.next=newNode=new LongDblLnkNode(before,val,after);
              }else{
                currList.bubbleUpPrepend(newNode=new LongDblLnkNode(val,after));
                root.head=newNode;
              }
              after.prev=newNode;
            }else{
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new LongDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new LongDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new LongDblLnkNode(val));
          }
        }else{
          LongDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new LongDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
              LongDblLnkNode.eraseNode(lastRet);
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final LongDblLnkNode lastRet;
          LongDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final LongDblLnkNode lastRet;
          LongDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfLong iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        int tailDist;
        final LongDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=LongDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?LongDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):LongDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=LongDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?LongDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):LongDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
              LongDblLnkNode before,after;
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
  private static class CheckedSubList extends LongDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
        }
        for(LongDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            this.modCount=modCount+1;
            if((head=head.next)==null){
              this.head=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfLong iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractLongItr
      implements OmniListIterator.OfLong{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient LongDblLnkNode curr;
      private transient LongDblLnkNode lastRet;
      private transient int currIndex;
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,LongDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public long nextLong(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          LongDblLnkNode curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public long previousLong(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          LongDblLnkNode curr;
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
      @Override public void set(long val){
        final LongDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(LongConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
                LongDblLnkNode.eraseNode(lastRet);
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(long val){
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
            LongDblLnkNode after,before,newNode;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                currList.bubbleUpIncrementSize();
                before.next=newNode=new LongDblLnkNode(before,val,after);
              }else{
                currList.bubbleUpPrepend(newNode=new LongDblLnkNode(val,after));
                root.head=newNode;
              }
              after.prev=newNode;
            }else{
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new LongDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new LongDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new LongDblLnkNode(val));
          }
        }else{
          LongDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new LongDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final LongDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=LongDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?LongDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):LongDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=LongDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?LongDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):LongDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,l);
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
          final LongDblLnkNode head;
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
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedindexOf(head,tail,v);
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedindexOf(head,tail,v);
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedindexOf(head,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedcontains(head,tail,v);
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedcontains(head,tail,v);
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedcontains(head,tail,l);
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient LongDblLnkNode head;
      private transient LongDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(LongDblLnkNode head,int size,LongDblLnkNode tail,CheckedList.ModCountChecker modCountChecker){
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
          LongDblLnkNode curr;
          for(this.head=curr=new LongDblLnkNode((long)ois.readLong());--size!=0;curr=curr.next=new LongDblLnkNode(curr,(long)ois.readLong())){}
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
              oos.writeLong(curr.val);
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
    private static  void pullSurvivorsDown(LongDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
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
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static  void pullSurvivorsDown(LongDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return;
        }
        prev=curr;
      }
    }
    private void bubbleUpPeelHead(LongDblLnkNode newHead,LongDblLnkNode oldHead){
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
    private void bubbleUpPeelHead(LongDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(LongDblLnkNode newTail,LongDblLnkNode oldTail){
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
    private void bubbleUpPeelTail(LongDblLnkNode newTail){
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
    private void peelTail(LongDblLnkNode tail){
      LongDblLnkNode after,before;
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
    private void removeLastNode(LongDblLnkNode lastNode){
      LongDblLnkNode after,before=lastNode.prev;
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
    private void peelHead(LongDblLnkNode head){
      LongDblLnkNode after,before;
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
    @Override public long removeLongAt(int index){
      final long ret;
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
          LongDblLnkNode before;
          ret=(before=( tail=LongDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          LongDblLnkNode after;
          ret=(after=( head=LongDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(LongPredicate filter){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Long> filter){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(LongDblLnkNode oldhead,LongDblLnkNode tail,LongPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      LongDblLnkNode newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      LongDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(LongDblLnkNode oldtail,LongDblLnkNode head,LongPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      LongDblLnkNode newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      LongDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(LongDblLnkNode oldHead,LongDblLnkNode newHead,int numRemoved,LongDblLnkNode newTail,LongDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final LongDblLnkNode after,before=oldHead.prev;
      if((after=oldHead.next)==null){
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
    private boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
      LongDblLnkNode tail;
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
    private void clearAllHelper(int size,LongDblLnkNode head,LongDblLnkNode tail,CheckedList root){
      LongDblLnkNode before,after=tail.next;
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
      var curr=this;
      do{
        ++curr.modCount;
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(LongDblLnkNode before,LongDblLnkNode head,int numRemoved,LongDblLnkNode tail,LongDblLnkNode after){
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
    private void bubbleUpClearHead(LongDblLnkNode tail, LongDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(LongDblLnkNode head, LongDblLnkNode before,int numRemoved){
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
    private static  int collapseBodyHelper(LongDblLnkNode newHead,int numLeft,LongPredicate filter,CheckedList.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft);
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private void collapseHeadAndTail(LongDblLnkNode head,LongDblLnkNode tail,LongPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        var newHead=head.next;
        do{
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList root=this.root;
            for(--size;numRemoved!=size;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
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
        }
        while(++numRemoved!=size);
      }
      CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(LongDblLnkNode head,LongDblLnkNode tail,LongPredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        LongDblLnkNode prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test((head=head.next).val)){
              numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
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
    private void bubbleUpAppend(LongDblLnkNode oldTail,LongDblLnkNode newTail){
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
    private void bubbleUpAppend(LongDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(LongDblLnkNode oldHead,LongDblLnkNode newHead){
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
    private void bubbleUpPrepend(LongDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(LongDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,LongDblLnkNode newNode){
      LongDblLnkNode after,before;   
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
          before=(after=LongDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=LongDblLnkNode.uncheckedIterateAscending(after,index)).next;
          before.prev=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(LongDblLnkNode newNode){
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
      ((LongDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,long val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new LongDblLnkNode(val);
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
    @Override void addLast(long val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size!=1){
        if(++this.size!=1){
          LongDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new LongDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new LongDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new LongDblLnkNode(val));
        }
      }else{
        LongDblLnkNode newNode;
        bubbleUpRootInit(newNode=new LongDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public long set(int index,long val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final LongDblLnkNode node;
      final var ret=(node=((LongDblLnkSeq)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,long val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((LongDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public long getLong(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((LongDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(LongUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final LongDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(LongConsumer action){
      final int modCount=this.modCount;
      try{
        final LongDblLnkNode head;
        if((head=this.head)!=null){
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(LongComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            LongSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Long> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final LongDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Long> action){
      final int modCount=this.modCount;
      try{
        final LongDblLnkNode head;
        if((head=this.head)!=null){
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Long> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(LongComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    @Override public long[] toLongArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toLongArray();
    }
    @Override public Long[] toArray(){
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
    CheckedList(LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    @Override public long removeLastLong(){
      LongDblLnkNode tail;
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
    @Override public long popLong(){
      LongDblLnkNode head;
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
    @Override public long removeLongAt(int index){
      final long ret;
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
          LongDblLnkNode before;
          ret=(before=(tail=LongDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          LongDblLnkNode after;
          ret=(after=(head=LongDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,long val){
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
          tail.next=tail=new LongDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          LongDblLnkNode before;
          (before=(tail=LongDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new LongDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        LongDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new LongDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new LongDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          LongDblLnkNode after;
          (after=(head=LongDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new LongDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(long val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(long val){
      ++this.modCount;
      super.push(val);
    }
    @Override public long set(int index,long val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      LongDblLnkNode tmp;
      final var ret=(tmp=((LongDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,long val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((LongDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public long getLong(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((LongDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public long getLastLong(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public long longElement(){
      final LongDblLnkNode head;
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
    @Override public void forEach(LongConsumer action){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(LongUnaryOperator operator){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(LongComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
            LongSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Long> action){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Long> operator){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Long> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(LongComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(LongDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(LongDblLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(LongDblLnkNode prev,LongPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
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
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfLong descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfLong iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final LongDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=LongDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?LongDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):LongDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=LongDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?LongDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):LongDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    } 
    boolean uncheckedremoveLastOccurrence(LongDblLnkNode tail
    ,long val
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
        for(LongDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
        for(LongDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public long pollLong(){
      LongDblLnkNode head;
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
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      LongDblLnkNode tail;
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
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      LongDblLnkNode head;
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
    @Override public Long pollLast(){
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
    private static class DescendingItr
      extends AbstractLongItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient LongDblLnkNode curr;
      transient LongDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,LongDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
              LongDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,LongConsumer action){
        final int modCount=this.modCount;
        final CheckedList parent;
        try{
          LongDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfLong{
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,LongDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public long previousLong(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          LongDblLnkNode curr;
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
      @Override public void set(long val){
        final LongDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(long val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        LongDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new LongDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new LongDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new LongDblLnkNode(val,newNode);
          }else{
            final LongDblLnkNode tmp;
            (newNode=curr).prev=newNode=new LongDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
              LongDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(LongConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
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
  public static class UncheckedList extends LongDblLnkSeq implements OmniDeque.OfLong,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public long removeLastLong(){
      LongDblLnkNode tail;
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
    @Override public long popLong(){
      LongDblLnkNode head;
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
    @Override public long removeLongAt(int index){
      final long ret;
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
          LongDblLnkNode before;
          ret=(before=(tail=LongDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          LongDblLnkNode after;
          ret=(after=(head=LongDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,long val){
      int size;
      if((size=++this.size-index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new LongDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          LongDblLnkNode before;
          (before=(tail=LongDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new LongDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        LongDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new LongDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new LongDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          LongDblLnkNode after;
          (after=(head=LongDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new LongDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(long val){
      LongDblLnkNode tail;
      if((tail=this.tail)==null){
        this.head=tail=new LongDblLnkNode(val);
      }else{
        tail.next=tail=new LongDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(long val){
      LongDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new LongDblLnkNode(val);
      }else{
        head.prev=head=new LongDblLnkNode(val,head);
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
          out.writeLong(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        LongDblLnkNode curr;
        for(this.head=curr=new LongDblLnkNode((long)in.readLong());--size!=0;curr=curr.next=new LongDblLnkNode(curr,(long)in.readLong())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
        for(LongDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfLong descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfLong iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        final int tailDist;
        final LongDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=LongDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?LongDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):LongDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=LongDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?LongDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):LongDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public long getLastLong(){
      return tail.val;
    }
    @Override public boolean offerFirst(long val){
      push((long)val);
      return true;
    }
    @Override public boolean offerLast(long val){
      addLast((long)val);
      return true;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public long longElement(){
      return head.val;
    }
    @Override public boolean offer(long val){
      addLast((long)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedsearch(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final LongDblLnkNode tail;
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(LongDblLnkNode tail
    ,long val
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
        for(LongDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    @Override public Long peekFirst(){
      return peek();
    }
    @Override public Long pollFirst(){
      return poll();
    }
    @Override public Long pop(){
      return popLong();
    }
    @Override public Long remove(){
      return popLong();
    }
    @Override public boolean offer(Long val){
      addLast((long)val);
      return true;
    }
    @Override public Long element(){
      return longElement();
    }
    @Override public Long removeFirst(){
      return popLong();
    }
    @Override public Long removeLast(){
      return removeLastLong();
    }
    @Override public boolean offerFirst(Long val){
      push((long)val);
      return true;
    }
    @Override public boolean offerLast(Long val){
      addLast((long)val);
      return true;
    }
    @Override public void push(Long val){
      push((long)val);
    }
    @Override public void addFirst(Long val){
      push((long)val);
    }
    @Override public void addLast(Long val){
      addLast((long)val);
    }
    @Override public Long getFirst(){
      return longElement();
    }
    @Override public Long getLast(){
      return getLastLong();
    }
    @Override public long pollLong(){
      LongDblLnkNode head;
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
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      LongDblLnkNode tail;
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
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      LongDblLnkNode head;
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
    @Override public Long pollLast(){
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
    @Override public long peekLong(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public Long peek(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Long peekLast(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public boolean removeIf(LongPredicate filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Long> filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(LongDblLnkNode prev,LongDblLnkNode tail,LongPredicate filter){
      int numSurvivors=1;
      outer:for(LongDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(LongDblLnkNode prev,LongDblLnkNode curr,LongDblLnkNode tail,LongPredicate filter){
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
    boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
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
          final LongDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
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
          LongDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            LongDblLnkNode lastRet;
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
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(LongDblLnkNode curr,LongConsumer action){
        LongDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractLongItr
    {
      transient final UncheckedList parent;
      transient LongDblLnkNode curr;
      private AscendingItr(UncheckedList parent,LongDblLnkNode curr){
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
          LongDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            LongDblLnkNode lastRet;
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
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(LongDblLnkNode curr,LongConsumer action){
        LongDblLnkNode.uncheckedForEachAscending(curr,action);
        this.curr=null;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfLong{
      private transient int currIndex;
      private transient LongDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,LongDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public long previousLong(){
        LongDblLnkNode curr;
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
      @Override public void add(long val){
        final UncheckedList parent;
        LongDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new LongDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new LongDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new LongDblLnkNode(val,newNode);
          }else{
            final LongDblLnkNode tmp;
            (newNode=curr).prev=newNode=new LongDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(long val){
        lastRet.val=val;
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
            LongDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(LongDblLnkNode curr,LongConsumer action){
        LongDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
