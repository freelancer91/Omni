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
public abstract class FloatSnglLnkSeq implements OmniCollection.OfFloat,Cloneable{
  static class Node{
    transient float val;
    transient Node next;
    Node(float val){
      this.val=val;
    }
    Node(float val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+HashUtil.hashFloat(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+HashUtil.hashFloat(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendFloat(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(FloatConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Object[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(double)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private boolean uncheckedcontainsBits(int bits){
      for(var curr=this;bits!=Float.floatToRawIntBits(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearchBits(int bits){
      int index=1;
      for(var curr=this;bits!=Float.floatToRawIntBits(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private boolean uncheckedcontains0(){
      for(var curr=this;!Float.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearch0(){
      int index=1;
      for(var curr=this;!Float.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private boolean uncheckedcontainsNaN(){
      for(var curr=this;0!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearchNaN(){
      int index=1;
      for(var curr=this;0!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private int retainSurvivors(final FloatPredicate filter){
      int numSurvivors=1;
      Node prev,next;
      outer:for(next=(prev=this).next;next!=null;++numSurvivors,next=(prev=next).next){
        if(filter.test(next.val)){
          do{
            if((next=next.next)==null){
              prev.next=null;
              break outer;
            }
          }while(filter.test(next.val));
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int retainTrailingSurvivors(final FloatPredicate filter){
      int numSurvivors=0;
      Node prev,curr;
      outer:for(prev=this;;prev=curr){
        if((curr=prev.next)==null){
          prev.next=null;
          break;
        }
        if(!filter.test(curr.val)){
          prev.next=curr;
          do{
            ++numSurvivors;
            if((curr=(prev=curr).next)==null){
              break outer;
            }
          }
          while(!filter.test(curr.val));
        }
      }
      return numSurvivors;
    }
  }
  transient int size;
  transient Node head;
  private FloatSnglLnkSeq(){
  }
  private FloatSnglLnkSeq(int size,Node head){
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
    final Node head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=head.uncheckedToString(buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        head.uncheckedToString(builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(FloatConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      head.uncheckedCopyInto(arr);
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
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Float[] dst;
      head.uncheckedCopyInto(dst=new Float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      head.uncheckedCopyInto(dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return head.uncheckedcontainsBits(TypeUtil.FLT_TRUE_BITS);
            }
            return head.uncheckedcontains0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return head.uncheckedcontains0();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return head.uncheckedcontains0();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val==val)
            {
              return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedcontainsNaN();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val))
            {
              return head.uncheckedcontainsBits(Float.floatToRawIntBits(v));
            }
            else if(v!=v)
            {
              return head.uncheckedcontainsNaN();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return head.uncheckedcontainsBits(Float.floatToRawIntBits(f));
                }
                return head.uncheckedcontainsNaN();
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return head.uncheckedcontainsBits(Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return head.uncheckedcontainsNaN();
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return head.uncheckedcontainsBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return head.uncheckedcontainsBits(Float.floatToRawIntBits(l));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return head.uncheckedcontainsBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return head.uncheckedcontainsBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return head.uncheckedcontainsBits(TypeUtil.FLT_TRUE_BITS);
                }
                return head.uncheckedcontains0();
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedcontains0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedcontains0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return head.uncheckedcontainsBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedcontains0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
  abstract boolean uncheckedremoveValBits(Node head,int bits);
  abstract boolean uncheckedremoveVal0(Node head);
  abstract boolean uncheckedremoveValNaN(Node head);
  @Override public boolean removeIf(FloatPredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node head,FloatPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node head){
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
      Node head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        Node newHead;
        for(clone=new CheckedStack(this.size,newHead=new Node(head.val));(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public float popFloat(){
      Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          head.uncheckedForEach(action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          head.uncheckedForEach(action::accept);
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
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
      final int modCount=this.modCount;
      try
      {
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            if(!filter.test(head.val)){
              this.size=head.retainSurvivors(filter,new ModCountChecker(modCount));
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
          for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
            if(filter.test(head.val)){
              this.size=numSurvivors+head.retainTrailingSurvivors(filter,new ModCountChecker(modCount));
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
      final Node head;
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
      final Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,int bits
      ){
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveValNaN(Node head
      ){
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node next;
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
      private void uncheckedForEachRemaining(Node next,FloatConsumer action){
        final int modCount=this.modCount;
        Node prev,curr;
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
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final Node prev;
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
    private UncheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(float val){
      this.head=new Node(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      Node head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        Node newHead;
        for(clone=new UncheckedStack(this.size,newHead=new Node(head.val));(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public float popFloat(){
      Node head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=head.retainSurvivors(filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+head.retainTrailingSurvivors(filter);
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
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float peek(){
      final Node head;
      if((head=this.head)!=null){
        return (Float)(head.val);
      }
      return null;
    }
    @Override public Float poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final Node head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,int bits
      ){
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveValNaN(Node head
      ){
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return head.uncheckedsearchBits(TypeUtil.FLT_TRUE_BITS);
            }
            return head.uncheckedsearch0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return head.uncheckedsearchBits(Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return head.uncheckedsearch0();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              if(TypeUtil.checkCastToFloat(val))
              {
                return head.uncheckedsearchBits(Float.floatToRawIntBits(val));
              }
            }
            else
            {
              return head.uncheckedsearch0();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val==val)
            {
              return head.uncheckedsearchBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedsearchNaN();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val))
            {
              return head.uncheckedsearchBits(Float.floatToRawIntBits(v));
            }
            else if(v!=v)
            {
              return head.uncheckedsearchNaN();
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return head.uncheckedsearchBits(Float.floatToRawIntBits(f));
                }
                return head.uncheckedsearchNaN();
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return head.uncheckedsearchBits(Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return head.uncheckedsearchNaN();
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return head.uncheckedsearchBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return head.uncheckedsearchBits(Float.floatToRawIntBits(l));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return head.uncheckedsearchBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return head.uncheckedsearchBits(Float.floatToRawIntBits(i));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return head.uncheckedsearchBits(TypeUtil.FLT_TRUE_BITS);
                }
                return head.uncheckedsearch0();
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return head.uncheckedsearchBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedsearch0();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return head.uncheckedsearchBits(Float.floatToRawIntBits(val));
            }
            return head.uncheckedsearch0();
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
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(FloatSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public float nextFloat(){
        final Node next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node next,FloatConsumer action){
        Node prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final FloatSnglLnkSeq parent;
        --(parent=this.parent).size;
        final Node prev;
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
