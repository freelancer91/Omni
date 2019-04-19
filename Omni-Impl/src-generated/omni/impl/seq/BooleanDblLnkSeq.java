package omni.impl.seq;
import omni.api.OmniList;
import omni.impl.BooleanDblLnkNode;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.BooleanComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class BooleanDblLnkSeq extends AbstractSeq implements
   BooleanSubListDefault
{
  private static final long serialVersionUID=1L;
  transient BooleanDblLnkNode head;
  transient BooleanDblLnkNode tail;
  private  BooleanDblLnkSeq(){
  }
  private BooleanDblLnkSeq(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(boolean val){
    BooleanDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new BooleanDblLnkNode(val);
    }else{
      tail.next=tail=new BooleanDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,boolean val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new BooleanDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        BooleanDblLnkNode before;
        (before=tail.prev).next=before=new BooleanDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      BooleanDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new BooleanDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new BooleanDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        BooleanDblLnkNode after;
        (after=head.next).prev=after=new BooleanDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private BooleanDblLnkNode getNode(int index,int size){
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
  @Override public boolean set(int index,boolean val){
    BooleanDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,boolean val){
    getNode(index,size).val=val;
  }
  @Override public boolean getBoolean(int index){
    return getNode(index,size).val;
  }
  @Override public boolean removeBooleanAt(int index){
    final boolean ret;
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
        ret=(tail=BooleanDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        BooleanDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=BooleanDblLnkNode.uncheckedIterateAscending(head,index)).val;
        BooleanDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(BooleanConsumer action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      BooleanDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      BooleanDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public boolean[] toBooleanArray(){
    int size;
    if((size=this.size)!=0){
      final boolean[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Boolean[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new Boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      final short[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int size;
    if((size=this.size)!=0){
      final byte[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new byte[size],tail,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size)!=0){
      final char[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new char[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return BooleanDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedcontains(head,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
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
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
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
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
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
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return uncheckedremoveVal(head,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return BooleanDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final BooleanDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  boolean uncheckedremoveVal(BooleanDblLnkNode head,BooleanDblLnkNode tail
  ,boolean val
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
          BooleanDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public void replaceAll(BooleanPredicate operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Boolean> operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    final BooleanDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final BooleanDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(BooleanDblLnkNode head,int size,BooleanPredicate filter){
    //TODO
    return false;
  }
  @Override public void sort(BooleanComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Boolean> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
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
  public static class UncheckedList extends BooleanDblLnkSeq implements OmniDeque.OfBoolean{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        BooleanDblLnkNode head,newTail;
        final var newHead=newTail=new BooleanDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new BooleanDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public boolean getLastBoolean(){
      return tail.val;
    }
    @Override public boolean offerFirst(boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void addFirst(boolean val){
      push((boolean)val);
    }
    @Override public boolean removeFirstBoolean(){
      return popBoolean();
    }
    @Override public void push(boolean val){
      BooleanDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new BooleanDblLnkNode(val);
      }else{
        head.prev=head=new BooleanDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public boolean removeLastBoolean(){
      BooleanDblLnkNode tail;
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
    @Override public boolean popBoolean(){
      BooleanDblLnkNode head;
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
    @Override public boolean booleanElement(){
      return head.val;
    }
    @Override public boolean offer(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return BooleanDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveLastOccurrence(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
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
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
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
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(BooleanDblLnkNode head,BooleanDblLnkNode tail
    ,boolean val
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
            BooleanDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    @Override public Boolean peekFirst(){
      return peek();
    }
    @Override public Boolean pollFirst(){
      return poll();
    }
    @Override public Boolean pop(){
      return popBoolean();
    }
    @Override public Boolean remove(){
      return popBoolean();
    }
    @Override public boolean offer(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public Boolean element(){
      return booleanElement();
    }
    @Override public Boolean removeFirst(){
      return popBoolean();
    }
    @Override public Boolean removeLast(){
      return removeLastBoolean();
    }
    @Override public boolean offerFirst(Boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void push(Boolean val){
      push((boolean)val);
    }
    @Override public void addFirst(Boolean val){
      push((boolean)val);
    }
    @Override public void addLast(Boolean val){
      addLast((boolean)val);
    }
    @Override public Boolean getFirst(){
      return booleanElement();
    }
    @Override public Boolean getLast(){
      return getLastBoolean();
    }
    @Override public boolean pollBoolean(){
      BooleanDblLnkNode head;
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
      return false;
    }
    @Override public boolean pollLastBoolean(){
      BooleanDblLnkNode tail;
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
      return false;
    }
    @Override public Boolean poll(){
      BooleanDblLnkNode head;
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
    @Override public Boolean pollLast(){
      BooleanDblLnkNode tail;
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToDouble(tail.val);
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToFloat(tail.val);
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToLong(tail.val);
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(int)TypeUtil.castToByte(tail.val);
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(short)TypeUtil.castToByte(tail.val);
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
    @Override public byte pollByte(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
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
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToByte(tail.val);
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
    @Override public char pollChar(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToChar(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public boolean peekBoolean(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return false;
    }
    @Override public boolean peekLastBoolean(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return false;
    }
    @Override public Boolean peek(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Boolean peekLast(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToDouble(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToDouble(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToFloat(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToFloat(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToLong(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToLong(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (int)TypeUtil.castToByte(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (int)TypeUtil.castToByte(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (short)TypeUtil.castToByte(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short peekLastShort(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (short)TypeUtil.castToByte(tail.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToByte(head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte peekLastByte(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToByte(tail.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToChar(head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char peekLastChar(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToChar(tail.val);
      }
      return Character.MIN_VALUE;
    }
  }
}
