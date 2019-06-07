package omni.impl.seq;
import omni.util.BooleanSortUtil;
import omni.api.OmniList;
import omni.impl.BooleanDblLnkNode;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractBooleanItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.BooleanComparator;
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
  abstract void addLast(boolean val);
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,BooleanDblLnkNode after,BooleanDblLnkNode newNode){
    newNode.next=after=BooleanDblLnkNode.iterateDescending(after,dist-2);
    final BooleanDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,BooleanDblLnkNode before,BooleanDblLnkNode newNode){
    newNode.prev=before=BooleanDblLnkNode.iterateAscending(before,dist-1);
    final BooleanDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,BooleanDblLnkNode newNode){
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
        BooleanDblLnkNode head;
        (head=this.head).prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,this.head,newNode);
      }
    }
  }
  private BooleanDblLnkNode getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return BooleanDblLnkNode.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return BooleanDblLnkNode.iterateAscending(head,index);
    }
  }
  private BooleanDblLnkNode getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return BooleanDblLnkNode.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return BooleanDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public boolean set(int index,boolean val){
    BooleanDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,boolean val){
    getNode(index,size).val=val;
  }
  @Override public boolean getBoolean(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(BooleanConsumer action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
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
  @Override public void replaceAll(BooleanPredicate operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Boolean> operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(BooleanComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      if(sorter==null){
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Boolean> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      if(sorter==null){
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=BooleanDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        BooleanDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      return BooleanDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
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
  @Override public int indexOf(boolean val){
    {
      {
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
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
        final BooleanDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
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
          //todo: a pattern-matching switch statement would be great here
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
  private static  int collapseBodyHelper(BooleanDblLnkNode newHead,BooleanDblLnkNode newTail,boolean removeThis){
    int numRemoved=0;
    outer:for(BooleanDblLnkNode prev;(newHead=(prev=newHead).next)!=newTail;){
      if(removeThis==(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next)==newTail){
            newHead.prev=prev;
            prev.next=newHead;
            break outer;
          }
        }while(removeThis==(newHead.val));
        newHead.prev=prev;
        prev.next=newHead;
      }
    }
    return numRemoved;
  }
  private static class UncheckedSubList extends BooleanDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
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
    private void bubbleUpAppend(BooleanDblLnkNode oldTail,BooleanDblLnkNode newTail){
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
    private void bubbleUpAppend(BooleanDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(BooleanDblLnkNode oldHead,BooleanDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(BooleanDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(BooleanDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,BooleanDblLnkNode newNode){
      BooleanDblLnkNode after,before;   
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
          before=(after=BooleanDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=BooleanDblLnkNode.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(BooleanDblLnkNode newNode){
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
      ((BooleanDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,boolean val){
      final UncheckedList root;
      final var newNode=new BooleanDblLnkNode(val);
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
    @Override void addLast(boolean val){
      final UncheckedList root;
      if(++(root=this.root).size!=1){
        if(++this.size!=1){
          BooleanDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new BooleanDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new BooleanDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new BooleanDblLnkNode(val));
        }
      }else{
        BooleanDblLnkNode newNode;
        bubbleUpRootInit(newNode=new BooleanDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private void bubbleUpPeelHead(BooleanDblLnkNode newHead,BooleanDblLnkNode oldHead){
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
    private void bubbleUpPeelHead(BooleanDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
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
    private void bubbleUpPeelTail(BooleanDblLnkNode newTail){
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
    private void peelTail(BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
      this.tail=newTail;
      BooleanDblLnkNode after;
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
    private void peelTail(BooleanDblLnkNode tail){
      BooleanDblLnkNode after,before;
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
    private void removeLastNode(BooleanDblLnkNode lastNode){
      BooleanDblLnkNode after,before=lastNode.prev;
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
    private void peelHead(BooleanDblLnkNode head){
      BooleanDblLnkNode after,before;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          BooleanDblLnkNode before;
          ret=(before=( tail=BooleanDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          BooleanDblLnkNode after;
          ret=(after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int collapsehead(BooleanDblLnkNode oldhead,BooleanDblLnkNode tail,boolean retainThis){
      int numRemoved=1;
      BooleanDblLnkNode newhead;
      outer:for(newhead=oldhead.next;newhead!=tail;++numRemoved,newhead=newhead.next){
        if(newhead.val==retainThis){
          BooleanDblLnkNode prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      this.head=newhead;
      BooleanDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
      return numRemoved;
    }
    private int collapsetail(BooleanDblLnkNode oldtail,BooleanDblLnkNode head,boolean retainThis){
      int numRemoved=1;
      BooleanDblLnkNode newtail;
      outer:for(newtail=oldtail.prev;newtail!=head;++numRemoved,newtail=newtail.prev){
        if(newtail.val==retainThis){
          BooleanDblLnkNode next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      this.tail=newtail;
      BooleanDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
      return numRemoved;
    }
    private void bubbleUpCollapseHeadAndTail(BooleanDblLnkNode oldHead,BooleanDblLnkNode newHead,int numRemoved,BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final BooleanDblLnkNode after,before=oldHead.prev;
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
    private boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      BooleanDblLnkNode tail;
      {
        boolean firstVal;
        if(filter.test(firstVal=(tail=this.tail).val)){
          if(tail==head){
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(head.val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                final UncheckedList root;
                int size;
                (root=this.root).size-=(size=this.size);
                //all nodes should be removed from the list
                clearAllHelper(size,head,tail,root);
              }else{
                final int numRemoved;
                root.size-=(numRemoved=collapsetail(tail,head,firstVal));
                this.size=size-numRemoved;
              }
            }else{
              collapseHeadAndTail(head,tail,firstVal,filter
              );
            }
          }
          return true;
        }else{
          if(tail!=head){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                final int numRemoved;
                root.size-=(numRemoved=collapsehead(head,tail,firstVal));
                this.size=size-numRemoved;
                return true;
              }
            }else{
              return collapseBody(head,tail,firstVal,filter
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
    private void clearAllHelper(int size,BooleanDblLnkNode head,BooleanDblLnkNode tail,UncheckedList root){
      BooleanDblLnkNode before,after=tail.next;
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
    private void bubbleUpClearBody(BooleanDblLnkNode before,BooleanDblLnkNode head,int numRemoved,BooleanDblLnkNode tail,BooleanDblLnkNode after){
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
    private void bubbleUpClearHead(BooleanDblLnkNode tail, BooleanDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(BooleanDblLnkNode head, BooleanDblLnkNode before,int numRemoved){
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
    private void collapseHeadAndTail(BooleanDblLnkNode head,BooleanDblLnkNode tail,boolean removeThis,BooleanPredicate filter
    ){
      {
        int numRemoved=2;
        for(var newHead=head.next;;++numRemoved,newHead=newHead.next){
          if(newHead==tail){
            break;
          }
          if(newHead.val^removeThis){
            if(filter.test(!removeThis)){
              break;   
            }
            BooleanDblLnkNode newTail;
            for(newTail=tail.prev;newTail!=newHead;++numRemoved,newTail=newTail.prev){
              if(newTail.val^removeThis){
                numRemoved+=collapseBodyHelper(newHead,newTail,removeThis);
                break;
              }
            }
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            this.size=size-numRemoved;
            root.size-=numRemoved;
            return;
          }
        }
      }
      final UncheckedList root;
      final int size;
      (root=this.root).size-=(size=this.size);
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(BooleanDblLnkNode head,BooleanDblLnkNode tail,boolean retainThis,BooleanPredicate filter
    ){
      for(BooleanDblLnkNode prev;(head=(prev=head).next)!=tail;){
        if(head.val^retainThis){
          if(filter.test(!retainThis)){
            int numRemoved=1;
            while((head=head.next)!=tail){
              if(head.val==retainThis){
                numRemoved+=collapseBodyHelper(head,tail,!retainThis);
                break;
              }
              ++numRemoved;
            }
            prev.next=head;
            head.prev=prev;
            for(var parent=this.parent;parent!=null;
              parent.size-=numRemoved,parent=parent.parent){}
            root.size-=numRemoved;
            this.size=size-numRemoved;
            return true;
          }
          break;
        }
      }
      return false;
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
    private static class AscendingItr
      extends AbstractBooleanItr
    {
      transient final UncheckedSubList parent;
      transient BooleanDblLnkNode curr;
      private AscendingItr(UncheckedSubList parent,BooleanDblLnkNode curr){
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
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          BooleanDblLnkNode curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            BooleanDblLnkNode lastRet;
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfBoolean{
      private transient int currIndex;
      private transient BooleanDblLnkNode lastRet;
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,BooleanDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public boolean previousBoolean(){
        BooleanDblLnkNode curr;
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
      @Override public void set(boolean val){
        lastRet.val=val;
      }
      @Override public void add(boolean val){
        final UncheckedSubList currList;
        final UncheckedList root;
        this.lastRet=null;
        ++currIndex;
        if(++(root=(currList=this.parent).root).size!=1){
          if(++currList.size!=1){
            BooleanDblLnkNode after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new BooleanDblLnkNode(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new BooleanDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              BooleanDblLnkNode newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new BooleanDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new BooleanDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new BooleanDblLnkNode(val));
          }
        }else{
          BooleanDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new BooleanDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet,curr;
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final BooleanDblLnkNode lastRet;
          BooleanDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final BooleanDblLnkNode lastRet;
          BooleanDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        int tailDist;
        final BooleanDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?BooleanDblLnkNode.iterateDescending(subListTail,subListSize-1):BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?BooleanDblLnkNode.iterateAscending(subListHead,subListSize-1):BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
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
          BooleanDblLnkNode prev;
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
  private static class CheckedSubList extends BooleanDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
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
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
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
          BooleanDblLnkNode prev;
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
    @Override public OmniIterator.OfBoolean iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractBooleanItr
      implements OmniListIterator.OfBoolean{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient BooleanDblLnkNode curr;
      private transient BooleanDblLnkNode lastRet;
      private transient int currIndex;
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,BooleanDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean nextBoolean(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          BooleanDblLnkNode curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean previousBoolean(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          BooleanDblLnkNode curr;
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
      @Override public void set(boolean val){
        final BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          BooleanDblLnkNode curr;
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
      @Override public void add(boolean val){
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
            BooleanDblLnkNode after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new BooleanDblLnkNode(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new BooleanDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              BooleanDblLnkNode newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new BooleanDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new BooleanDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new BooleanDblLnkNode(val));
          }
        }else{
          BooleanDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new BooleanDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final BooleanDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?BooleanDblLnkNode.iterateDescending(subListTail,subListSize-1):BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?BooleanDblLnkNode.iterateAscending(subListHead,subListSize-1):BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        BooleanDblLnkNode head,newTail;
        final var newHead=newTail=new BooleanDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new BooleanDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
              return uncheckedremoveVal(head,b);
            }
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient BooleanDblLnkNode head;
      private transient BooleanDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail,CheckedList.ModCountChecker modCountChecker){
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
          BooleanDblLnkNode curr;
          int word,marker;
          for(this.head=curr=new BooleanDblLnkNode(((marker=1)&(word=ois.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new BooleanDblLnkNode(curr,(word&marker)!=0)){
            if((marker<<=1)==(1<<8)){
              word=ois.readUnsignedByte();
              marker=1;
            }
          }
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
              if(--size==0){
                oos.writeByte(word);
                return;
              }else if((marker<<=1)==(1<<8)){
                oos.writeByte(word);
                word=0;
                marker=1;
              }
              if((curr=curr.next).val){
                word|=marker;
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
    private void bubbleUpPeelHead(BooleanDblLnkNode newHead,BooleanDblLnkNode oldHead){
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
    private void bubbleUpPeelHead(BooleanDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
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
    private void bubbleUpPeelTail(BooleanDblLnkNode newTail){
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
    private void peelTail(BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
      this.tail=newTail;
      BooleanDblLnkNode after;
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
    private void peelTail(BooleanDblLnkNode tail){
      BooleanDblLnkNode after,before;
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
    private void removeLastNode(BooleanDblLnkNode lastNode){
      BooleanDblLnkNode after,before=lastNode.prev;
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
    private void peelHead(BooleanDblLnkNode head){
      BooleanDblLnkNode after,before;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          BooleanDblLnkNode before;
          ret=(before=( tail=BooleanDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          BooleanDblLnkNode after;
          ret=(after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private int collapsehead(BooleanDblLnkNode oldhead,BooleanDblLnkNode tail,boolean retainThis){
      int numRemoved=1;
      BooleanDblLnkNode newhead;
      outer:for(newhead=oldhead.next;newhead!=tail;++numRemoved,newhead=newhead.next){
        if(newhead.val==retainThis){
          BooleanDblLnkNode prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      this.head=newhead;
      BooleanDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            ++parent.modCount;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
      return numRemoved;
    }
    private int collapsetail(BooleanDblLnkNode oldtail,BooleanDblLnkNode head,boolean retainThis){
      int numRemoved=1;
      BooleanDblLnkNode newtail;
      outer:for(newtail=oldtail.prev;newtail!=head;++numRemoved,newtail=newtail.prev){
        if(newtail.val==retainThis){
          BooleanDblLnkNode next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      this.tail=newtail;
      BooleanDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            ++parent.modCount;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
      return numRemoved;
    }
    private void bubbleUpCollapseHeadAndTail(BooleanDblLnkNode oldHead,BooleanDblLnkNode newHead,int numRemoved,BooleanDblLnkNode newTail,BooleanDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final BooleanDblLnkNode after,before=oldHead.prev;
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
    private boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      BooleanDblLnkNode tail;
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        boolean firstVal;
        if(filter.test(firstVal=(tail=this.tail).val)){
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
            if(head.val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                root.size-=size;
                //all nodes should be removed from the list
                clearAllHelper(size,head,tail,root);
              }else{
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                final int numRemoved;
                root.size-=(numRemoved=collapsetail(tail,head,firstVal));
                this.size=size-numRemoved;
              }
            }else{
              collapseHeadAndTail(head,tail,firstVal,filter
                ,size,modCount
              );
            }
          }
          return true;
        }else{
          if(size!=1){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                final int numRemoved;
                root.size-=(numRemoved=collapsehead(head,tail,firstVal));
                this.size=size-numRemoved;
                return true;
              }
            }else{
              return collapseBody(head,tail,firstVal,filter
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
    private void clearAllHelper(int size,BooleanDblLnkNode head,BooleanDblLnkNode tail,CheckedList root){
      BooleanDblLnkNode before,after=tail.next;
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
    private void bubbleUpClearBody(BooleanDblLnkNode before,BooleanDblLnkNode head,int numRemoved,BooleanDblLnkNode tail,BooleanDblLnkNode after){
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
    private void bubbleUpClearHead(BooleanDblLnkNode tail, BooleanDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(BooleanDblLnkNode head, BooleanDblLnkNode before,int numRemoved){
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
    private void collapseHeadAndTail(BooleanDblLnkNode head,BooleanDblLnkNode tail,boolean removeThis,BooleanPredicate filter
      ,int size,int modCount
    ){
      int numLeft;
      if((numLeft=size-2)!=0)
      {
        int numRemoved=2;
        for(var newHead=head.next;;++numRemoved,newHead=newHead.next){
          if(newHead.val^removeThis){
            if(filter.test(!removeThis)){
              break;   
            }
            CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            BooleanDblLnkNode newTail;
            for(newTail=tail.prev;newTail!=newHead;++numRemoved,newTail=newTail.prev){
              if(newTail.val^removeThis){
                numRemoved+=collapseBodyHelper(newHead,newTail,removeThis);
                break;
              }
            }
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            this.size=size-numRemoved;
            root.size-=numRemoved;
            return;
          }
          if(--numLeft==0){
            break;
          }
        }
      }
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(BooleanDblLnkNode head,BooleanDblLnkNode tail,boolean retainThis,BooleanPredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        BooleanDblLnkNode prev;
        if((head=(prev=head).next).val^retainThis){
          if(filter.test(!retainThis)){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            int numRemoved=1;
            while((head=head.next)!=tail){
              if(head.val==retainThis){
                numRemoved+=collapseBodyHelper(head,tail,!retainThis);
                break;
              }
              ++numRemoved;
            }
            prev.next=head;
            head.prev=prev;
            for(var parent=this.parent;parent!=null;
              parent.modCount=modCount,
              parent.size-=numRemoved,parent=parent.parent){}
            root.size-=numRemoved;
            this.size=size-numRemoved;
            return true;
          }
          break;
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
    private void bubbleUpAppend(BooleanDblLnkNode oldTail,BooleanDblLnkNode newTail){
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
    private void bubbleUpAppend(BooleanDblLnkNode newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(BooleanDblLnkNode oldHead,BooleanDblLnkNode newHead){
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
    private void bubbleUpPrepend(BooleanDblLnkNode newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(BooleanDblLnkNode newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,BooleanDblLnkNode newNode){
      BooleanDblLnkNode after,before;   
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
          before=(after=BooleanDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=BooleanDblLnkNode.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(BooleanDblLnkNode newNode){
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
      ((BooleanDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,boolean val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new BooleanDblLnkNode(val);
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
    @Override void addLast(boolean val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size!=1){
        if(++this.size!=1){
          BooleanDblLnkNode currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new BooleanDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new BooleanDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new BooleanDblLnkNode(val));
        }
      }else{
        BooleanDblLnkNode newNode;
        bubbleUpRootInit(newNode=new BooleanDblLnkNode(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final BooleanDblLnkNode node;
      final var ret=(node=((BooleanDblLnkSeq)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((BooleanDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((BooleanDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final BooleanDblLnkNode head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(BooleanComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final BooleanDblLnkNode head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final int modCount=this.modCount;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    @Override public boolean[] toBooleanArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toBooleanArray();
    }
    @Override public Boolean[] toArray(){
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
    @Override public byte[] toByteArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toByteArray();
    }
    @Override public char[] toCharArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toCharArray();
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
    CheckedList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
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
    @Override public boolean removeLastBoolean(){
      BooleanDblLnkNode tail;
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
    @Override public boolean popBoolean(){
      BooleanDblLnkNode head;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          BooleanDblLnkNode before;
          ret=(before=(tail=BooleanDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          BooleanDblLnkNode after;
          ret=(after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,boolean val){
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
          tail.next=tail=new BooleanDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          BooleanDblLnkNode before;
          (before=(tail=BooleanDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new BooleanDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        BooleanDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new BooleanDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new BooleanDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          BooleanDblLnkNode after;
          (after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new BooleanDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(boolean val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      BooleanDblLnkNode tmp;
      final var ret=(tmp=((BooleanDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((BooleanDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((BooleanDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public boolean getLastBoolean(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean booleanElement(){
      final BooleanDblLnkNode head;
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
    @Override public void forEach(BooleanConsumer action){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(BooleanComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        if(sorter==null){
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        this.modCount=modCount+1;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        this.modCount=modCount+1;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        if(sorter==null){
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private int removeIfHelper(BooleanDblLnkNode prev,int numLeft,boolean retainThis){
      int numSurvivors=1;
      outer:for(BooleanDblLnkNode next;--numLeft!=0;prev=next,++numSurvivors){
        if((next=prev.next).val^retainThis){
          do{
            if(--numLeft==0){
              prev.next=null;
              this.tail=prev;
              break outer;
            }
          }while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode head,int numLeft,boolean retainThis){
      int numSurvivors=0;
      outer:for(;;){
        if(--numLeft==0){
          prev.next=null;
          this.tail=prev;
          break;
        }
        if((head=head.next).val==retainThis){
          prev.next=head;
          head.prev=prev;
          do{
            ++numSurvivors;
            if(--numLeft==0){
              break outer;
            }
          }
          while((head=(prev=head).next).val==retainThis);
        }
      }
      return numSurvivors;
    }
    @Override boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        boolean firstVal;
        if(filter.test(firstVal=head.val)){
          while(--numLeft!=0){
            if((head=head.next).val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                break;
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              head.prev=null;
              this.head=head;
              this.size=removeIfHelper(head,numLeft,firstVal);
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
          for(int numSurvivors=1;--numLeft!=0;++numSurvivors){
            BooleanDblLnkNode prev;
            if((head=(prev=head).next).val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+removeIfHelper(prev,head,numLeft,firstVal);
                return true;
              }
              break;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
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
          for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
            if(--size==0){
              out.writeByte(word);
              return;
            }else if((marker<<=1)==(1<<8)){
              out.writeByte(word);
              word=0;
              marker=1;
            }
            if((curr=curr.next).val){
              word|=marker;
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
        BooleanDblLnkNode head,newTail;
        final var newHead=newTail=new BooleanDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new BooleanDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final BooleanDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?BooleanDblLnkNode.iterateDescending(subListTail,subListSize-1):BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?BooleanDblLnkNode.iterateAscending(subListHead,subListSize-1):BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    } 
    boolean uncheckedremoveLastOccurrence(BooleanDblLnkNode tail
    ,boolean val
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
        for(BooleanDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
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
        for(BooleanDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public boolean pollBoolean(){
      BooleanDblLnkNode head;
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
      return false;
    }
    @Override public boolean pollLastBoolean(){
      BooleanDblLnkNode tail;
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
      return false;
    }
    @Override public Boolean poll(){
      BooleanDblLnkNode head;
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
    @Override public Boolean pollLast(){
      BooleanDblLnkNode tail;
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
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
        ++this.modCount;
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
    private static class DescendingItr
      extends AbstractBooleanItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient BooleanDblLnkNode curr;
      transient BooleanDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,BooleanDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
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
              BooleanDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,BooleanConsumer action){
        final int modCount=this.modCount;
        final CheckedList parent;
        try{
          BooleanDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfBoolean{
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,BooleanDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean previousBoolean(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          BooleanDblLnkNode curr;
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
      @Override public void set(boolean val){
        final BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(boolean val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        BooleanDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new BooleanDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new BooleanDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new BooleanDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final BooleanDblLnkNode tmp;
            (newNode=curr).prev=newNode=new BooleanDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          BooleanDblLnkNode curr;
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
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
  public static class UncheckedList extends BooleanDblLnkSeq implements OmniDeque.OfBoolean,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public boolean removeLastBoolean(){
      BooleanDblLnkNode tail;
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
    @Override public boolean popBoolean(){
      BooleanDblLnkNode head;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          BooleanDblLnkNode before;
          ret=(before=(tail=BooleanDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          BooleanDblLnkNode after;
          ret=(after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,boolean val){
      int size;
      if((size=++this.size-index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new BooleanDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          BooleanDblLnkNode before;
          (before=(tail=BooleanDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new BooleanDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        BooleanDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new BooleanDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new BooleanDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          BooleanDblLnkNode after;
          (after=(head=BooleanDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new BooleanDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(boolean val){
      BooleanDblLnkNode tail;
      if((tail=this.tail)==null){
        this.head=tail=new BooleanDblLnkNode(val);
      }else{
        tail.next=tail=new BooleanDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(boolean val){
      BooleanDblLnkNode head;
      if((head=this.head)==null){
        tail=head=new BooleanDblLnkNode(val);
      }else{
        head.prev=head=new BooleanDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
          if((curr=curr.next)==null){
            out.writeByte(word);
            return;
          }else if((marker<<=1)==(1<<8)){
            out.writeByte(word);
            word=0;
            marker=1;
          }
          if(curr.val){
            word|=marker;
          }
        }
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        BooleanDblLnkNode curr;
        int word,marker;
        for(this.head=curr=new BooleanDblLnkNode(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new BooleanDblLnkNode(curr,(word&marker)!=0)){
          if((marker<<=1)==(1<<8)){
            word=in.readUnsignedByte();
            marker=1;
          }
        }
        this.tail=curr;
      }
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
    @Override public boolean removeVal(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
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
        for(BooleanDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        final int tailDist;
        final BooleanDblLnkNode subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?BooleanDblLnkNode.iterateDescending(subListTail,subListSize-1):BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=BooleanDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?BooleanDblLnkNode.iterateAscending(subListHead,subListSize-1):BooleanDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
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
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
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
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
              return BooleanDblLnkNode.uncheckedsearch(head,b);
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
            return uncheckedremoveLastOccurrence(tail,(val));
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
              return uncheckedremoveLastOccurrence(tail,v);
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
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
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
              return uncheckedremoveLastOccurrence(tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(BooleanDblLnkNode tail
    ,boolean val
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
        for(BooleanDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(BooleanDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if((next=prev.next).val^retainThis){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode curr,BooleanDblLnkNode tail,boolean retainThis){
      int numSurvivors=0;
      while(curr!=tail) {
        if((curr=curr.next).val==retainThis){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while((curr=(prev=curr).next).val==retainThis);
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        for(var tail=this.tail;head!=tail;){
          if((head=head.next).val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            head.prev=null;
            this.head=head;
            this.size=removeIfHelper(head,tail,firstVal);
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
          final BooleanDblLnkNode prev;
          if((head=(prev=head).next).val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
              return true;
            }
            break;
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
          BooleanDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            BooleanDblLnkNode lastRet;
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
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(BooleanDblLnkNode curr,BooleanConsumer action){
        BooleanDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractBooleanItr
    {
      transient final UncheckedList parent;
      transient BooleanDblLnkNode curr;
      private AscendingItr(UncheckedList parent,BooleanDblLnkNode curr){
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
          BooleanDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            BooleanDblLnkNode lastRet;
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
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(BooleanDblLnkNode curr,BooleanConsumer action){
        BooleanDblLnkNode.uncheckedForEachAscending(curr,action);
        this.curr=null;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfBoolean{
      private transient int currIndex;
      private transient BooleanDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,BooleanDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public boolean previousBoolean(){
        BooleanDblLnkNode curr;
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
      @Override public void add(boolean val){
        final UncheckedList parent;
        BooleanDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new BooleanDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new BooleanDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new BooleanDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final BooleanDblLnkNode tmp;
            (newNode=curr).prev=newNode=new BooleanDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(boolean val){
        lastRet.val=val;
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet,curr;
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
      @Override void uncheckedForEachRemaining(BooleanDblLnkNode curr,BooleanConsumer action){
        BooleanDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
