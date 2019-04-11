package omni.impl.seq;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniCollection;
import omni.impl.CheckedCollection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.util.HashUtil;
import omni.util.TypeUtil;
import omni.impl.AbstractFloatItr;
import omni.api.OmniStack;
import omni.util.FloatSnglLnkNode;
public abstract class FloatSnglLnkSeq implements OmniCollection.OfFloat,Cloneable{
  transient int size;
  transient FloatSnglLnkNode head;
  private FloatSnglLnkSeq(){
  }
  private FloatSnglLnkSeq(int size,FloatSnglLnkNode head){
    this.size=size;
    this.head=head;
  }
  @Override public abstract Object clone();
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return FloatSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=FloatSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        FloatSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(FloatConsumer action){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      FloatSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(float val);
  @Override public boolean add(float val)
  {
    push((val));
    return true;
  }
  public void push(Float val){
    push((float)val);
  }
  @Override public boolean add(Float val)
  {
    push((float)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(int val)
  {
    push((float)(val));
    return true;
  }
  @Override public boolean add(char val)
  {
    push((float)(val));
    return true;
  }
  @Override public boolean add(short val)
  {
    push((float)(val));
    return true;
  }
  @Override public boolean add(long val)
  {
    push((float)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public float[] toFloatArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final Float[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new Float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return FloatSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return FloatSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val)
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontainsNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val))
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(v));
            }
            else if(v!=v)
            {
              return FloatSnglLnkNode.uncheckedcontainsNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }
                return FloatSnglLnkNode.uncheckedcontainsNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatSnglLnkNode.uncheckedcontainsNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(l));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
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
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val)
            {
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val))
            {
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }
            else if(v!=v)
            {
              return uncheckedremoveValNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
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
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveValBits(FloatSnglLnkNode head,int bits);
  abstract boolean uncheckedremoveVal0(FloatSnglLnkNode head);
  abstract boolean uncheckedremoveValNaN(FloatSnglLnkNode head);
  @Override public boolean removeIf(FloatPredicate filter){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,FloatSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void clear(){
     if(size!=0){
       ++this.modCount;
       this.size=0;
       this.head=null;
     }
    }
    @Override public Object clone(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        FloatSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new FloatSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new FloatSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public float popFloat(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        var ret=head.val;
        this.head=head.next;
        --this.size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedStack.this.modCount;
      }
    }
    @Override public void forEach(FloatConsumer action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray((arrSize)->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=FloatSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
              this.modCount=modCount+1;
              this.head=head;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.size=0;
          return true;
        }else{
          FloatSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+FloatSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
              this.modCount=modCount+1;
              return true;
            }
          }
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
      ,int bits
      ){
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Float.floatToRawIntBits(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(0!=(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
      ){
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Float.isNaN(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractFloatItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient FloatSnglLnkNode prev;
      transient FloatSnglLnkNode curr;
      transient FloatSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(FloatSnglLnkNode next,FloatConsumer action){
        final int modCount=this.modCount;
        FloatSnglLnkNode prev,curr;
        try{
          curr=this.curr;
          do{
            action.accept(next.val);
            prev=curr;
          }while((next=(curr=next).next)!=null);
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final FloatSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(prev==null){
            parent.head=next;
          }else{
            prev.next=next;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedStack extends FloatSnglLnkSeq implements OmniStack.OfFloat{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,FloatSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(float val){
      this.head=new FloatSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        FloatSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new FloatSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new FloatSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public float popFloat(){
      FloatSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=FloatSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        FloatSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+FloatSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public float peekFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float peek(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        return (Float)(head.val);
      }
      return null;
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
      ,int bits
      ){
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Float.floatToRawIntBits(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(0!=(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
      ){
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Float.isNaN(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override public int search(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return FloatSnglLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return FloatSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return FloatSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val)
            {
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val))
            {
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(v));
            }
            else if(v!=v)
            {
              return FloatSnglLnkNode.uncheckedsearchNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }
                return FloatSnglLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatSnglLnkNode.uncheckedsearchNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(l));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractFloatItr
    {
      transient final FloatSnglLnkSeq parent;
      transient FloatSnglLnkNode prev;
      transient FloatSnglLnkNode curr;
      transient FloatSnglLnkNode next;
      Itr(FloatSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public float nextFloat(){
        final FloatSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(FloatSnglLnkNode next,FloatConsumer action){
        FloatSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final FloatSnglLnkSeq parent;
        --(parent=this.parent).size;
        final FloatSnglLnkNode prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        this.curr=prev;
      }
    }
  } 
}
