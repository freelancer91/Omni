package omni.impl.seq;
import omni.api.OmniList;
import java.io.Serializable;
import omni.impl.DoubleDblLnkNode;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.UnaryOperator;
import java.util.function.DoubleUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.DoubleComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class DoubleDblLnkSeq implements
   DoubleSubListDefault
  ,Cloneable,Serializable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient DoubleDblLnkNode head;
  transient DoubleDblLnkNode tail;
  private  DoubleDblLnkSeq(){
  }
  private DoubleDblLnkSeq(DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
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
  public void addLast(double val){
    DoubleDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new DoubleDblLnkNode(val);
    }else{
      tail.next=tail=new DoubleDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(double val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,double val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new DoubleDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        DoubleDblLnkNode before;
        (before=tail.prev).next=before=new DoubleDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      DoubleDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new DoubleDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new DoubleDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        DoubleDblLnkNode after;
        (after=head.next).prev=after=new DoubleDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private DoubleDblLnkNode getNode(int index,int size)
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
  @Override public double set(int index,double val){
    DoubleDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,double val){
    getNode(index,size).val=val;
  }
  @Override public double getDouble(int index){
    return getNode(index,size).val;
  }
  @Override public double removeDoubleAt(int index){
    final double ret;
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
        ret=(tail=DoubleDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        DoubleDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=DoubleDblLnkNode.uncheckedIterateAscending(head,index)).val;
        DoubleDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(DoubleConsumer action)
  {
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action)
  {
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      DoubleDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      DoubleDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      DoubleDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public Double[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Double[] dst;
      DoubleDblLnkNode.uncheckedCopyInto(dst=new Double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.DBL_TRUE_BITS);
          }
          return DoubleDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            {
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(d));
              }
              return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(f));
              }
              return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(l));
              }
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.DBL_TRUE_BITS);
              }
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val)
          {
            return uncheckedremoveValBits(head,tail,TypeUtil.DBL_TRUE_BITS);
          }
          return uncheckedremoveVal0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            {
              return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return uncheckedremoveVal0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return uncheckedremoveVal0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return uncheckedremoveValNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return uncheckedremoveValNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(d));
              }
              return uncheckedremoveValNaN(head,tail);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(f));
              }
              return uncheckedremoveValNaN(head,tail);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(l));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return uncheckedremoveValBits(head,tail,TypeUtil.DBL_TRUE_BITS);
              }
              return uncheckedremoveVal0(head,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return uncheckedremoveVal0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return uncheckedremoveVal0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return uncheckedremoveVal0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.DBL_TRUE_BITS);
          }
          return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            {
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(d));
              }
              return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(f));
              }
              return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(l));
              }
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.DBL_TRUE_BITS);
              }
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(byte val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.DBL_TRUE_BITS);
          }
          return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            {
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(d));
              }
              return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(f));
              }
              return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(l));
              }
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(i));
              }
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.DBL_TRUE_BITS);
              }
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final DoubleDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0)
          {
            return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
          }
          return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  boolean uncheckedremoveValBits(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ,long bits
  ){
    if(bits==Double.doubleToRawLongBits(head.val)){
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
      if(bits==Double.doubleToRawLongBits((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          DoubleDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveVal0(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    if(0==(head.val)){
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
      if(0==((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          DoubleDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveValNaN(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    if(Double.isNaN(head.val)){
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
      if(Double.isNaN((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          DoubleDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public abstract Object clone();
  @Override public void replaceAll(DoubleUnaryOperator operator){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Double> operator){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(DoublePredicate filter)
  {
    final DoubleDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Double> filter)
  {
    final DoubleDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(DoubleDblLnkNode head,int size,DoublePredicate filter)
  {
    //TODO
    return false;
  }
  @Override public void sort(DoubleComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Double> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(DoubleComparator sorter){
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
  public static class UncheckedList extends DoubleDblLnkSeq implements OmniDeque.OfDouble{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0)
      {
        DoubleDblLnkNode head,newTail;
        final var newHead=newTail=new DoubleDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new DoubleDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfDouble iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public double getLastDouble(){
      return tail.val;
    }
    @Override public boolean offerFirst(double val){
      push((double)val);
      return true;
    }
    @Override public boolean offerLast(double val){
      addLast((double)val);
      return true;
    }
    @Override public void addFirst(double val){
      push((double)val);
    }
    @Override public double removeFirstDouble(){
      return popDouble();
    }
    @Override public void push(double val){
      DoubleDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new DoubleDblLnkNode(val);
      }else{
        head.prev=head=new DoubleDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public double removeLastDouble(){
      DoubleDblLnkNode tail;
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
    @Override public double popDouble(){
      DoubleDblLnkNode head;
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
    @Override public double doubleElement(){
      return head.val;
    }
    @Override public boolean offer(double val){
      addLast((double)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              {
                return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
              return DoubleDblLnkNode.uncheckedsearch0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToDouble(val))
              {
                return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
              return DoubleDblLnkNode.uncheckedsearch0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearchNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearchNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(d));
                }
                return DoubleDblLnkNode.uncheckedsearchNaN(head,tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(f));
                }
                return DoubleDblLnkNode.uncheckedsearchNaN(head,tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(l));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,tail,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleDblLnkNode.uncheckedsearch0(head,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return DoubleDblLnkNode.uncheckedsearchBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveLastOccurrence0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              {
                return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
              return uncheckedremoveLastOccurrence0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToDouble(val))
              {
                return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
              return uncheckedremoveLastOccurrence0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrenceNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrenceNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(d));
                }
                return uncheckedremoveLastOccurrenceNaN(head,tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(f));
                }
                return uncheckedremoveLastOccurrenceNaN(head,tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(l));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveLastOccurrenceBits(head,tail,TypeUtil.DBL_TRUE_BITS);
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveLastOccurrenceBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceBits(DoubleDblLnkNode head,DoubleDblLnkNode tail
    ,long bits
    ){
      if(bits==Double.doubleToRawLongBits(tail.val)){
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
        if(bits==Double.doubleToRawLongBits((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            DoubleDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrence0(DoubleDblLnkNode head,DoubleDblLnkNode tail
    ){
      if(0==(tail.val)){
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
        if(0==((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            DoubleDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNaN(DoubleDblLnkNode head,DoubleDblLnkNode tail
    ){
      if(Double.isNaN(tail.val)){
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
        if(Double.isNaN((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            DoubleDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    @Override public Double peekFirst(){
      return peek();
    }
    @Override public Double pollFirst(){
      return poll();
    }
    @Override public Double pop(){
      return popDouble();
    }
    @Override public Double remove(){
      return popDouble();
    }
    @Override public boolean offer(Double val){
      addLast((double)val);
      return true;
    }
    @Override public Double element(){
      return doubleElement();
    }
    @Override public Double removeFirst(){
      return popDouble();
    }
    @Override public Double removeLast(){
      return removeLastDouble();
    }
    @Override public boolean offerFirst(Double val){
      push((double)val);
      return true;
    }
    @Override public boolean offerLast(Double val){
      addLast((double)val);
      return true;
    }
    @Override public void push(Double val){
      push((double)val);
    }
    @Override public void addFirst(Double val){
      push((double)val);
    }
    @Override public void addLast(Double val){
      addLast((double)val);
    }
    @Override public Double getFirst(){
      return doubleElement();
    }
    @Override public Double getLast(){
      return getLastDouble();
    }
    @Override public double pollDouble(){
      DoubleDblLnkNode head;
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
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      DoubleDblLnkNode tail;
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
      return Double.NaN;
    }
    @Override public Double poll(){
      DoubleDblLnkNode head;
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
    @Override public Double pollLast(){
      DoubleDblLnkNode tail;
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
    @Override public double peekDouble(){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final DoubleDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Double.NaN;
    }
    @Override public Double peek(){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Double peekLast(){
      final DoubleDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
  }
}
