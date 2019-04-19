package omni.impl.seq;
import omni.api.OmniList;
import omni.impl.LongDblLnkNode;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.UnaryOperator;
import java.util.function.LongUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.LongComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
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
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(long val){
    LongDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new LongDblLnkNode(val);
    }else{
      tail.next=tail=new LongDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(long val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,long val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new LongDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        LongDblLnkNode before;
        (before=tail.prev).next=before=new LongDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      LongDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new LongDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new LongDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        LongDblLnkNode after;
        (after=head.next).prev=after=new LongDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private LongDblLnkNode getNode(int index,int size){
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
  @Override public long set(int index,long val){
    LongDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,long val){
    getNode(index,size).val=val;
  }
  @Override public long getLong(int index){
    return getNode(index,size).val;
  }
  @Override public long removeLongAt(int index){
    final long ret;
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
        ret=(tail=LongDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        LongDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=LongDblLnkNode.uncheckedIterateAscending(head,index)).val;
        LongDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(LongConsumer action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
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
  @Override public boolean contains(boolean val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,TypeUtil.castToLong(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final LongDblLnkNode tail;
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final long v;
          if(TypeUtil.doubleEquals(val,v=(long)val))
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
            return uncheckedremoveVal(head,tail,l);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final LongDblLnkNode tail;
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
        final LongDblLnkNode tail;
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int indexOf(byte val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final LongDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
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
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int lastIndexOf(byte val){
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
  @Override public int lastIndexOf(char val){
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
  boolean uncheckedremoveVal(LongDblLnkNode head,LongDblLnkNode tail
  ,long val
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
          LongDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public void replaceAll(LongUnaryOperator operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Long> operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(LongPredicate filter){
    final LongDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Long> filter){
    final LongDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(LongDblLnkNode head,int size,LongPredicate filter){
    //TODO
    return false;
  }
  @Override public void sort(LongComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Long> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(LongComparator sorter){
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
  public static class UncheckedList extends LongDblLnkSeq implements OmniDeque.OfLong{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
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
    @Override public OmniIterator.OfLong descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfLong iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfLong listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      //TODO
      return null;
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
    @Override public void addFirst(long val){
      push((long)val);
    }
    @Override public long removeFirstLong(){
      return popLong();
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
    @Override public long removeLastLong(){
      LongDblLnkNode tail;
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
    @Override public long popLong(){
      LongDblLnkNode head;
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
              return LongDblLnkNode.uncheckedsearch(head,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,TypeUtil.castToLong(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
              return uncheckedremoveLastOccurrence(head,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final LongDblLnkNode tail;
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(LongDblLnkNode head,LongDblLnkNode tail
    ,long val
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
            LongDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
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
  }
}
