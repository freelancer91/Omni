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
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractLongItr;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.LongSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class LongSnglLnkSeq extends AbstractSeq implements OmniCollection.OfLong,Externalizable{
  private static final long serialVersionUID=1L;
  transient LongSnglLnkNode head;
  private LongSnglLnkSeq(){
  }
  private LongSnglLnkSeq(LongSnglLnkNode head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(LongSnglLnkNode prev,LongPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
    int wordOffset;
    for(long word=survivorSet[wordOffset=0],marker=1L;;){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=curr.next;
            return;
          }else if((marker<<=1)==0){
            word=survivorSet[++wordOffset];
            marker=1L;
          }
          curr=curr.next;
        }while((marker&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        curr.next=null;
        return;
      }else if((marker<<=1)==0){
         word=survivorSet[++wordOffset];
         marker=1L;
      }
      prev=curr;
    }
  }
  private static  int markSurvivors(LongSnglLnkNode curr,LongPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static  void pullSurvivorsDown(LongSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
    for(long marker=1L;;marker<<=1){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=curr.next;
            return;
          }
          curr=curr.next;
        }while(((marker<<=1)&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        curr.next=null;
        return;
      }
      prev=curr;
    }
  }
  private static  long markSurvivors(LongSnglLnkNode curr,LongPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(LongSnglLnkNode prev, final LongPredicate filter){
    int numSurvivors=1;
    outer:for(LongSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(LongSnglLnkNode prev,LongSnglLnkNode curr,final LongPredicate filter){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }else if(!filter.test(curr.val)){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }while(!filter.test(curr.val));
      }
    }
    return numSurvivors;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0){
      var curr=this.head;
      do{
        out.writeLong(curr.val);
      }
      while((curr=curr.next)!=null);
    }
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return LongSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=LongSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        LongSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(LongConsumer action){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      LongSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(long val);
  @Override public boolean add(long val){
    push((val));
    return true;
  }
  @Override public boolean add(char val){
    push((long)(val));
    return true;
  }
  @Override public boolean add(byte val){
    push((long)(val));
    return true;
  }
  public void push(Long val){
    push((long)val);
  }
  @Override public boolean add(Long val){
    push((long)(val));
    return true;
  }
  @Override public boolean add(boolean val){
    push((long)TypeUtil.castToLong(val));
    return true;
  }
  @Override public boolean add(int val){
    push((long)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public long[] toLongArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final Long[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new Long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
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
              return LongSnglLnkNode.uncheckedcontains(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
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
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
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
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
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
              return uncheckedremoveVal(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(LongSnglLnkNode head,long val);
  @Override public boolean removeIf(LongPredicate filter){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Long> filter){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  public long peekLong(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return (long)(head.val);
    }
    return Long.MIN_VALUE;
  }
  public Long peek(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return (Long)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  abstract boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(LongSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void push(long val){
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
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        LongSnglLnkNode newHead;
        for(clone=new CheckedStack(newHead=new LongSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new LongSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public long popLong(){
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(LongConsumer action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Long> action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,action::accept);
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
    private int removeIfHelper(LongSnglLnkNode prev,LongPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
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
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
      ,long val
    ){
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(val!=(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr(this);
    }
    private static class Itr extends AbstractItr{
      final CheckedStack parent;
      transient int modCount;
      Itr(CheckedStack parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(LongSnglLnkNode next,LongConsumer action){
        final int modCount=this.modCount;
        LongSnglLnkNode prev,curr;
        try{
          curr=this.curr;
          do{
            action.accept(next.val);
            prev=curr;
          }while((next=(curr=next).next)!=null);
        }finally{
          CheckedCollection.checkModCount(modCount,this.parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final LongSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          --parent.size;
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
  public static class UncheckedStack extends LongSnglLnkSeq implements OmniStack.OfLong{
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
    }
    UncheckedStack(LongSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        LongSnglLnkNode curr;
        for(this.head=curr=new LongSnglLnkNode((long)in.readLong());--size!=0;curr=curr.next=new LongSnglLnkNode((long)in.readLong())){}
      }
    }
    @Override public void push(long val){
      this.head=new LongSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        LongSnglLnkNode newHead;
        for(clone=new UncheckedStack(newHead=new LongSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new LongSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public long popLong(){
      LongSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
    }
    @Override public int search(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
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
              return LongSnglLnkNode.uncheckedsearch(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long pop(){
      return popLong();
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        LongSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
      ,long val
    ){
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(val!=(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr{
      Itr(){
        super(UncheckedStack.this.head);
      }
      @Override public void remove(){
        final UncheckedStack parent;
        --(parent=UncheckedStack.this).size;
        final LongSnglLnkNode prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        this.curr=prev;
      }
    }
  }
  public static class CheckedQueue extends UncheckedQueue{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(LongSnglLnkNode head,int size,LongSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public long longElement(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        int size;
        final var tail=this.tail;
        out.writeInt(size=this.size);
        if(size!=0){
          LongSnglLnkNode curr;
          for(curr=this.head;;curr=curr.next){
            out.writeLong(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(LongConsumer action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Long> action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,tail,action::accept);
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
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.head=null;
        this.tail=null;
        this.size=0;
      }
    }
    @Override public Object clone(){
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        LongSnglLnkNode newHead=new LongSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new LongSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(long val){
      ++this.modCount;
      super.push(val);
    }
    @Override public long removeLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        if(head==tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    private void pullSurvivorsDown(LongSnglLnkNode prev,LongPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr.next;
              if(curr==tail){
                this.tail=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(LongSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr.next;
              if(curr==tail){
                this.tail=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(LongSnglLnkNode prev,LongPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
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
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
    ,long val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(val!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr(this);
    }
    private static class Itr extends AbstractItr{
      transient final CheckedQueue parent;
      transient int modCount;
      Itr(CheckedQueue parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(LongSnglLnkNode next,LongConsumer action){
        final int modCount=this.modCount;
        LongSnglLnkNode prev,curr;
        final CheckedQueue parent;
        final var tail=(parent=this.parent).tail;
        try{
          for(curr=this.curr;;next=curr.next){
            action.accept(next.val);
            prev=curr;
            if((curr=next)==tail){
              break;
            }
          }
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final LongSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedQueue parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          --parent.size;
          if(prev==null){
            parent.head=next;
          }else{
            prev.next=next;
          }
          if(curr==parent.tail){
            parent.tail=prev;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedQueue extends LongSnglLnkSeq implements OmniQueue.OfLong{
    private static final long serialVersionUID=1L;
    transient LongSnglLnkNode tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(LongSnglLnkNode head,int size,LongSnglLnkNode tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        LongSnglLnkNode curr;
        for(this.head=curr=new LongSnglLnkNode((long)in.readLong());--size!=0;curr=curr.next=new LongSnglLnkNode((long)in.readLong())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        LongSnglLnkNode newHead=new LongSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new LongSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(long val){
      final var newNode=new LongSnglLnkNode(val);
      final LongSnglLnkNode tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public long longElement(){
      return head.val;
    }
    @Override public boolean offer(long val){
      push(val);
      return true;
    }
    @Override public long removeLong(){
      final LongSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long remove(){
      return removeLong();
    }
    @Override public Long element(){
      return longElement();
    }
    @Override public boolean offer(Long val){
      push((long)val);
      return true;
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    private int removeIfHelper(LongSnglLnkNode prev,LongSnglLnkNode tail,LongPredicate filter){
      int numSurvivors=1;
      outer:for(LongSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while(filter.test((next=next.next).val));
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(LongSnglLnkNode prev,LongSnglLnkNode curr,LongSnglLnkNode tail,LongPredicate filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
            this.head=head;
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
          final LongSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
    ,long val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(val!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr{
      Itr(){
        super(UncheckedQueue.this.head);
      }
      @Override public void remove(){
        final UncheckedQueue parent;
        --(parent=UncheckedQueue.this).size;
        final LongSnglLnkNode prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        if(this.curr==parent.tail){
          parent.tail=prev;
        }
        this.curr=prev;
      }
    }
  }
  private static class AbstractItr
      extends AbstractLongItr
  {
    transient LongSnglLnkNode prev;
    transient LongSnglLnkNode curr;
    transient LongSnglLnkNode next;
    AbstractItr(LongSnglLnkNode next){
      this.next=next; 
    }
    @Override public long nextLong(){
      final LongSnglLnkNode next;
      this.next=(next=this.next).next;
      this.prev=this.curr;
      this.curr=next;
      return next.val;
    }
    @Override public boolean hasNext(){
      return next!=null;
    }
    void uncheckedForEachRemaining(LongSnglLnkNode next,LongConsumer action){
      LongSnglLnkNode prev,curr=this.curr;
      do{
        action.accept(next.val);
        prev=curr;
      }while((next=(curr=next).next)!=null);
      this.prev=prev;
      this.curr=curr;
      this.next=null;
    }
    @Override public void forEachRemaining(LongConsumer action){
      final LongSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Long> action){
      final LongSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
}
