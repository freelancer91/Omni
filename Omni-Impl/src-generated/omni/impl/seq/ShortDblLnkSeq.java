package omni.impl.seq;
import omni.api.OmniList;
import omni.impl.ShortDblLnkNode;
import java.util.function.Consumer;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import java.util.function.UnaryOperator;
import omni.function.ShortUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.ShortComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
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
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(short val){
    ShortDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new ShortDblLnkNode(val);
    }else{
      tail.next=tail=new ShortDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(short val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,short val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new ShortDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        ShortDblLnkNode before;
        (before=tail.prev).next=before=new ShortDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      ShortDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new ShortDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new ShortDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        ShortDblLnkNode after;
        (after=head.next).prev=after=new ShortDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private ShortDblLnkNode getNode(int index,int size){
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
  @Override public short set(int index,short val){
    ShortDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,short val){
    getNode(index,size).val=val;
  }
  @Override public short getShort(int index){
    return getNode(index,size).val;
  }
  @Override public short removeShortAt(int index){
    final short ret;
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
        ret=(tail=ShortDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        ShortDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=ShortDblLnkNode.uncheckedIterateAscending(head,index)).val;
        ShortDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(ShortConsumer action){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Short> action){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
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
  @Override public boolean contains(boolean val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(short)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    if(val==(short)val)
    {
      {
        final ShortDblLnkNode tail;
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if((v=(short)val)==val){
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if(val==(v=(short)val))
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final short v;
          if(val==(v=(short)val))
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    if(val<=Short.MAX_VALUE)
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    {
      {
        final ShortDblLnkNode tail;
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int indexOf(byte val){
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return ShortDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    if(val<=Short.MAX_VALUE)
    {
      {
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final ShortDblLnkNode tail;
        if((tail=this.tail)!=null)
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
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int lastIndexOf(byte val){
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
  boolean uncheckedremoveVal(ShortDblLnkNode head,ShortDblLnkNode tail
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
          ShortDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public void replaceAll(ShortUnaryOperator operator){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Short> operator){
    final ShortDblLnkNode head;
    if((head=this.head)!=null){
      ShortDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(ShortPredicate filter){
    final ShortDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Short> filter){
    final ShortDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(ShortDblLnkNode head,int size,ShortPredicate filter){
    //TODO
    return false;
  }
  @Override public void sort(ShortComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Short> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(ShortComparator sorter){
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
  public static class UncheckedList extends ShortDblLnkSeq implements OmniDeque.OfShort{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(ShortDblLnkNode head,int size,ShortDblLnkNode tail){
      super(head,size,tail);
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
    @Override public OmniIterator.OfShort descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfShort iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfShort listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfShort listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
      //TODO
      return null;
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
    @Override public void addFirst(short val){
      push((short)val);
    }
    @Override public short removeFirstShort(){
      return popShort();
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
    @Override public short removeLastShort(){
      ShortDblLnkNode tail;
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
    @Override public short popShort(){
      ShortDblLnkNode head;
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,tail,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(short)val)
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if((v=(short)val)==val){
              return ShortDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
              return ShortDblLnkNode.uncheckedsearch(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return ShortDblLnkNode.uncheckedsearch(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(short)TypeUtil.castToByte(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
          final ShortDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(ShortDblLnkNode head,ShortDblLnkNode tail
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
            ShortDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
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
  }
}
