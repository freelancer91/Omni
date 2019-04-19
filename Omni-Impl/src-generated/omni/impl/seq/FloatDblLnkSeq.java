package omni.impl.seq;
import omni.api.OmniList;
import omni.impl.FloatDblLnkNode;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import java.util.function.UnaryOperator;
import omni.function.FloatUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.FloatComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class FloatDblLnkSeq extends AbstractSeq implements
   FloatSubListDefault
{
  private static final long serialVersionUID=1L;
  transient FloatDblLnkNode head;
  transient FloatDblLnkNode tail;
  private  FloatDblLnkSeq(){
  }
  private FloatDblLnkSeq(FloatDblLnkNode head,int size,FloatDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(float val){
    FloatDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new FloatDblLnkNode(val);
    }else{
      tail.next=tail=new FloatDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(float val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,float val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new FloatDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        FloatDblLnkNode before;
        (before=tail.prev).next=before=new FloatDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      FloatDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new FloatDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new FloatDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        FloatDblLnkNode after;
        (after=head.next).prev=after=new FloatDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private FloatDblLnkNode getNode(int index,int size){
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
  @Override public float set(int index,float val){
    FloatDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,float val){
    getNode(index,size).val=val;
  }
  @Override public float getFloat(int index){
    return getNode(index,size).val;
  }
  @Override public float removeFloatAt(int index){
    final float ret;
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
        ret=(tail=FloatDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        FloatDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=FloatDblLnkNode.uncheckedIterateAscending(head,index)).val;
        FloatDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(FloatConsumer action){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      FloatDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      FloatDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Float[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new Float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return FloatDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final float v;
          if(val==(v=(float)val)){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
              }
              return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(l));
              }
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val){
            return uncheckedremoveValBits(head,tail,TypeUtil.FLT_TRUE_BITS);
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val){
            return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final float v;
          if(val==(v=(float)val)){
            return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return uncheckedremoveValNaN(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(f));
              }
              return uncheckedremoveValNaN(head,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return uncheckedremoveValNaN(head,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(i));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(l));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(i));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(i));
              }
              return uncheckedremoveVal0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return uncheckedremoveValBits(head,tail,TypeUtil.FLT_TRUE_BITS);
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return uncheckedremoveValBits(head,tail,Float.floatToRawIntBits(val));
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return FloatDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final float v;
          if(val==(v=(float)val)){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
              }
              return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(l));
              }
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val==val){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final float v;
          if(val==(v=(float)val)){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
              }
              return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(l));
              }
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
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
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final FloatDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  boolean uncheckedremoveValBits(FloatDblLnkNode head,FloatDblLnkNode tail
  ,int bits
  ){
    if(bits==Float.floatToRawIntBits(head.val)){
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
      if(bits==Float.floatToRawIntBits((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          FloatDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveVal0(FloatDblLnkNode head,FloatDblLnkNode tail
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
          FloatDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveValNaN(FloatDblLnkNode head,FloatDblLnkNode tail
  ){
    if(Float.isNaN(head.val)){
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
      if(Float.isNaN((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          FloatDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public void replaceAll(FloatUnaryOperator operator){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Float> operator){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(FloatPredicate filter){
    final FloatDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final FloatDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(FloatDblLnkNode head,int size,FloatPredicate filter){
    //TODO
    return false;
  }
  @Override public void sort(FloatComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Float> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(FloatComparator sorter){
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
  public static class UncheckedList extends FloatDblLnkSeq implements OmniDeque.OfFloat{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(FloatDblLnkNode head,int size,FloatDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        FloatDblLnkNode head,newTail;
        final var newHead=newTail=new FloatDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new FloatDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfFloat iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public float getLastFloat(){
      return tail.val;
    }
    @Override public boolean offerFirst(float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(float val){
      addLast((float)val);
      return true;
    }
    @Override public void addFirst(float val){
      push((float)val);
    }
    @Override public float removeFirstFloat(){
      return popFloat();
    }
    @Override public void push(float val){
      FloatDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new FloatDblLnkNode(val);
      }else{
        head.prev=head=new FloatDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public float removeLastFloat(){
      FloatDblLnkNode tail;
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
    @Override public float popFloat(){
      FloatDblLnkNode head;
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
    @Override public float floatElement(){
      return head.val;
    }
    @Override public boolean offer(float val){
      addLast((float)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedsearch0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedsearch0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearchNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatDblLnkNode.uncheckedsearchNaN(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(f));
                }
                return FloatDblLnkNode.uncheckedsearchNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatDblLnkNode.uncheckedsearchNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(l));
                }
                return FloatDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatDblLnkNode.uncheckedsearchBits(head,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatDblLnkNode.uncheckedsearch0(head,tail);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return uncheckedremoveLastOccurrenceBits(head,tail,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveLastOccurrenceNaN(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveLastOccurrenceNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveLastOccurrenceNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveLastOccurrenceBits(head,tail,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(head,tail,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveLastOccurrence0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceBits(FloatDblLnkNode head,FloatDblLnkNode tail
    ,int bits
    ){
      if(bits==Float.floatToRawIntBits(tail.val)){
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
        if(bits==Float.floatToRawIntBits((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            FloatDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrence0(FloatDblLnkNode head,FloatDblLnkNode tail
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
            FloatDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNaN(FloatDblLnkNode head,FloatDblLnkNode tail
    ){
      if(Float.isNaN(tail.val)){
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
        if(Float.isNaN((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            FloatDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    @Override public Float peekFirst(){
      return peek();
    }
    @Override public Float pollFirst(){
      return poll();
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public Float remove(){
      return popFloat();
    }
    @Override public boolean offer(Float val){
      addLast((float)val);
      return true;
    }
    @Override public Float element(){
      return floatElement();
    }
    @Override public Float removeFirst(){
      return popFloat();
    }
    @Override public Float removeLast(){
      return removeLastFloat();
    }
    @Override public boolean offerFirst(Float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(Float val){
      addLast((float)val);
      return true;
    }
    @Override public void push(Float val){
      push((float)val);
    }
    @Override public void addFirst(Float val){
      push((float)val);
    }
    @Override public void addLast(Float val){
      addLast((float)val);
    }
    @Override public Float getFirst(){
      return floatElement();
    }
    @Override public Float getLast(){
      return getLastFloat();
    }
    @Override public float pollFloat(){
      FloatDblLnkNode head;
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
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      FloatDblLnkNode tail;
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
      return Float.NaN;
    }
    @Override public Float poll(){
      FloatDblLnkNode head;
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
    @Override public Float pollLast(){
      FloatDblLnkNode tail;
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
      FloatDblLnkNode head;
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
      FloatDblLnkNode tail;
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
    @Override public float peekFloat(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Float.NaN;
    }
    @Override public Float peek(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Float peekLast(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
  }
}
