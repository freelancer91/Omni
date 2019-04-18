package omni.impl.seq;
import omni.api.OmniList;
import java.io.Serializable;
import omni.impl.ByteDblLnkNode;
import java.util.function.Consumer;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import java.util.function.UnaryOperator;
import omni.function.ByteUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.ByteComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class ByteDblLnkSeq implements
   ByteSubListDefault
  ,Cloneable,Serializable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient ByteDblLnkNode head;
  transient ByteDblLnkNode tail;
  private  ByteDblLnkSeq(){
  }
  private ByteDblLnkSeq(ByteDblLnkNode head,int size,ByteDblLnkNode tail){
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
  public void addLast(byte val){
    ByteDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new ByteDblLnkNode(val);
    }else{
      tail.next=tail=new ByteDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(byte val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,byte val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new ByteDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        ByteDblLnkNode before;
        (before=tail.prev).next=before=new ByteDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      ByteDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new ByteDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new ByteDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        ByteDblLnkNode after;
        (after=head.next).prev=after=new ByteDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private ByteDblLnkNode getNode(int index,int size)
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
  @Override public byte set(int index,byte val){
    ByteDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,byte val){
    getNode(index,size).val=val;
  }
  @Override public byte getByte(int index){
    return getNode(index,size).val;
  }
  @Override public byte removeByteAt(int index){
    final byte ret;
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
        ret=(tail=ByteDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        ByteDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=ByteDblLnkNode.uncheckedIterateAscending(head,index)).val;
        ByteDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(ByteConsumer action)
  {
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action)
  {
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
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
  @Override public boolean contains(boolean val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if((v=(byte)val)==val)
          {
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ByteDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    if(val==(byte)val)
    {
      {
        final ByteDblLnkNode tail;
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if((v=(byte)val)==val)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if(val==(v=(byte)val))
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    if(val<=Byte.MAX_VALUE)
    {
      {
        final ByteDblLnkNode tail;
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final byte v;
          if((v=(byte)val)==val)
          {
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ByteDblLnkNode tail;
        if((tail=this.tail)!=null)
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
          if((v=(byte)val)==val)
          {
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
          //TODO a pattern-matching switch statement would be great here
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
  boolean uncheckedremoveVal(ByteDblLnkNode head,ByteDblLnkNode tail
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
          ByteDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public abstract Object clone();
  @Override public void replaceAll(ByteUnaryOperator operator){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Byte> operator){
    final ByteDblLnkNode head;
    if((head=this.head)!=null){
      ByteDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(BytePredicate filter)
  {
    final ByteDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter)
  {
    final ByteDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(ByteDblLnkNode head,int size,BytePredicate filter)
  {
    //TODO
    return false;
  }
  @Override public void sort(ByteComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Byte> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(ByteComparator sorter){
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
  public static class UncheckedList extends ByteDblLnkSeq implements OmniDeque.OfByte{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(ByteDblLnkNode head,int size,ByteDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0)
      {
        ByteDblLnkNode head,newTail;
        final var newHead=newTail=new ByteDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new ByteDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfByte descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfByte listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfByte listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
      //TODO
      return null;
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
    @Override public void addFirst(byte val){
      push((byte)val);
    }
    @Override public byte removeFirstByte(){
      return popByte();
    }
    @Override public void push(byte val){
      ByteDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new ByteDblLnkNode(val);
      }else{
        head.prev=head=new ByteDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public byte removeLastByte(){
      ByteDblLnkNode tail;
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
    @Override public byte popByte(){
      ByteDblLnkNode head;
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
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,tail,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(byte)val)
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if((v=(byte)val)==val)
            {
              return ByteDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
              return ByteDblLnkNode.uncheckedsearch(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ByteDblLnkNode.uncheckedsearch(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,TypeUtil.castToByte(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            if((v=(byte)val)==val)
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
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
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
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
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
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
          final ByteDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(ByteDblLnkNode head,ByteDblLnkNode tail
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
            ByteDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
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
  }
}
