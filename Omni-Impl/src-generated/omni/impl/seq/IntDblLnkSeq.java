package omni.impl.seq;
import omni.api.OmniList;
import java.io.Serializable;
import omni.impl.IntDblLnkNode;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.UnaryOperator;
import java.util.function.IntUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class IntDblLnkSeq implements
   IntSubListDefault
  ,Cloneable,Serializable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient IntDblLnkNode head;
  transient IntDblLnkNode tail;
  private  IntDblLnkSeq(){
  }
  private IntDblLnkSeq(IntDblLnkNode head,int size,IntDblLnkNode tail){
    this.head=head;
    this.size=size;
    this.tail=tail;
  }
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(int val){
    IntDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new IntDblLnkNode(val);
    }else{
      tail.next=tail=new IntDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(int val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,int val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new IntDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        IntDblLnkNode before;
        (before=tail.prev).next=before=new IntDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      IntDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new IntDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new IntDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        IntDblLnkNode after;
        (after=head.next).prev=after=new IntDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private IntDblLnkNode getNode(int index,int size)
  {
    int tailDist;
    if((tailDist=size-index)<index){
      for(var tail=this.tail;;tail=tail.prev){
        if(--tailDist==0){
          return tail;
        }
      }
    }else{
      for(var head=this.head;;--index,head=head.next){
        if(index==0){
          return head;
        }
      }
    }
  }
  @Override public int set(int index,int val){
    IntDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,int val){
    getNode(index,size).val=val;
  }
  @Override public int getInt(int index){
    return getNode(index,size).val;
  }
  @Override public int removeIntAt(int index){
    final int ret;
    int tailDist;
    if((tailDist=--this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==0){
        ret=tail.val;
        if(index==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
      }else{
        ret=(tail=IntDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        IntDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=IntDblLnkNode.uncheckedIterateAscending(head,index)).val;
        IntDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(IntConsumer action)
  {
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action)
  {
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      IntDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      IntDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Integer[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new Integer[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedcontains(head,tail,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((v=(int)val)==val)
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return IntDblLnkNode.uncheckedcontains(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((v=(int)val)==val)
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return uncheckedremoveVal(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedindexOf(head,tail,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((v=(int)val)==val)
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return IntDblLnkNode.uncheckedindexOf(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(byte val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((v=(int)val)==val)
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final IntDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  boolean uncheckedremoveVal(IntDblLnkNode head,IntDblLnkNode tail
  ,int val
  ){
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
    while(head!=tail){
      if(val==((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          IntDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public abstract Object clone();
  @Override public void replaceAll(IntUnaryOperator operator){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Integer> operator){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(IntPredicate filter)
  {
    final IntDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter)
  {
    final IntDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(IntDblLnkNode head,int size,IntPredicate filter)
  {
    //TODO
    return false;
  }
  @Override public void sort(IntBinaryOperator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Integer> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(IntBinaryOperator sorter){
    //TODO
  }
  @Override public String toString(){
    //TODO
    return null;
  }
  @Override public int hashCode(){
    //TODO
    return 0;
  }
  //TODO serialization methods
  public static class UncheckedList extends IntDblLnkSeq implements OmniDeque.OfInt{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(IntDblLnkNode head,int size,IntDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0)
      {
        IntDblLnkNode head,newTail;
        final var newHead=newTail=new IntDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new IntDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfInt descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfInt iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfInt listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public int getLastInt(){
      return tail.val;
    }
    @Override public boolean offerFirst(int val){
      push((int)val);
      return true;
    }
    @Override public boolean offerLast(int val){
      addLast((int)val);
      return true;
    }
    @Override public void addFirst(int val){
      push((int)val);
    }
    @Override public int removeFirstInt(){
      return popInt();
    }
    @Override public void push(int val){
      IntDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new IntDblLnkNode(val);
      }else{
        head.prev=head=new IntDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public int removeLastInt(){
      IntDblLnkNode tail;
      final var ret=(tail=this.tail).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (tail=tail.prev).next=null;
        this.tail=tail;
      }
      return ret;
    }
    @Override public int popInt(){
      IntDblLnkNode head;
      final var ret=(head=this.head).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (head=head.next).prev=null;
        this.head=head;
      }
      return ret;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public int intElement(){
      return head.val;
    }
    @Override public boolean offer(int val){
      addLast((int)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((v=(int)val)==val)
            {
              return IntDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntDblLnkNode.uncheckedsearch(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((v=(int)val)==val)
            {
              return uncheckedremoveLastOccurrence(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return uncheckedremoveLastOccurrence(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return uncheckedremoveLastOccurrence(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(IntDblLnkNode head,IntDblLnkNode tail
    ,int val
    ){
      if(val==(tail.val)){
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
        return true;
      }
      while(head!=tail){
        if(val==((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            IntDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    @Override public Integer peekFirst(){
      return peek();
    }
    @Override public Integer pollFirst(){
      return poll();
    }
    @Override public Integer pop(){
      return popInt();
    }
    @Override public Integer remove(){
      return popInt();
    }
    @Override public boolean offer(Integer val){
      addLast((int)val);
      return true;
    }
    @Override public Integer element(){
      return intElement();
    }
    @Override public Integer removeFirst(){
      return popInt();
    }
    @Override public Integer removeLast(){
      return removeLastInt();
    }
    @Override public boolean offerFirst(Integer val){
      push((int)val);
      return true;
    }
    @Override public boolean offerLast(Integer val){
      addLast((int)val);
      return true;
    }
    @Override public void push(Integer val){
      push((int)val);
    }
    @Override public void addFirst(Integer val){
      push((int)val);
    }
    @Override public void addLast(Integer val){
      addLast((int)val);
    }
    @Override public Integer getFirst(){
      return intElement();
    }
    @Override public Integer getLast(){
      return getLastInt();
    }
    @Override public int pollInt(){
      IntDblLnkNode head;
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
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      IntDblLnkNode tail;
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
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      IntDblLnkNode head;
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
    @Override public Integer pollLast(){
      IntDblLnkNode tail;
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
      IntDblLnkNode head;
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
      IntDblLnkNode tail;
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
      IntDblLnkNode head;
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
      IntDblLnkNode tail;
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
      IntDblLnkNode head;
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
      IntDblLnkNode tail;
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
    @Override public int peekInt(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer peek(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Integer peekLast(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
  }
}
