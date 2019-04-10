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
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractIntItr;
import omni.api.OmniStack;
public abstract class IntSnglLnkSeq implements OmniCollection.OfInt,Cloneable{
  static class Node{
    transient int val;
    transient Node next;
    Node(int val){
      this.val=val;
    }
    Node(int val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringInt(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendInt(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(IntConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(int[] dst){
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
    private void uncheckedCopyInto(Integer[] dst){
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
    private void uncheckedCopyInto(float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(float)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(long[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(long)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private boolean uncheckedcontains(int val){
      for(var curr=this;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearch(int val){
      int index=1;
      for(var curr=this;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private int retainSurvivors(final IntPredicate filter){
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
    private int retainTrailingSurvivors(final IntPredicate filter){
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
  private IntSnglLnkSeq(){
  }
  private IntSnglLnkSeq(int size,Node head){
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
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
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
  @Override public void forEach(IntConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
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
  abstract void push(int val);
  @Override public boolean add(int val)
  {
    push((val));
    return true;
  }
  public void push(Integer val){
    push((int)val);
  }
  @Override public boolean add(Integer val)
  {
    push((int)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((int)(int)TypeUtil.castToByte(val));
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
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      head.uncheckedCopyInto(dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Integer[] dst;
      head.uncheckedCopyInto(dst=new Integer[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      head.uncheckedCopyInto(dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return head.uncheckedcontains((int)TypeUtil.castToByte(val));
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
            return head.uncheckedcontains((val));
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
            final int v;
            if((v=(int)val)==val)
            {
              return head.uncheckedcontains(v);
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
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return head.uncheckedcontains(v);
            }
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
            final int v;
            if(val==(v=(int)val))
            {
              return head.uncheckedcontains(v);
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
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return head.uncheckedcontains(i);
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
            return head.uncheckedcontains((val));
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
            return head.uncheckedcontains((val));
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
            return uncheckedremoveVal(head,(int)TypeUtil.castToByte(val));
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
            return uncheckedremoveVal(head,(val));
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
            final int v;
            if((v=(int)val)==val)
            {
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
          final Node head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
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
          final Node head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
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
          final Node head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
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
            return uncheckedremoveVal(head,(val));
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
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(Node head,int val);
  @Override public boolean removeIf(IntPredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node head,IntPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(int val){
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
    @Override public int popInt(){
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
    @Override public void forEach(IntConsumer action){
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
    @Override public void forEach(Consumer<? super Integer> action){
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
    @Override boolean uncheckedremoveIf(Node head,IntPredicate filter){
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
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Integer)(head.val);
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
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(Node head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(val!=(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractIntItr
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
      @Override public int nextInt(){
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
      private void uncheckedForEachRemaining(Node next,IntConsumer action){
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
      @Override public void forEachRemaining(IntConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
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
  public static class UncheckedStack extends IntSnglLnkSeq implements OmniStack.OfInt{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(int val){
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
    @Override public int popInt(){
      Node head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node head,IntPredicate filter){
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
    @Override public Integer pop(){
      return popInt();
    }
    @Override public int peekInt(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer peek(){
      final Node head;
      if((head=this.head)!=null){
        return (Integer)(head.val);
      }
      return null;
    }
    @Override public Integer poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Integer)(head.val);
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
    @Override public float peekFloat(){
      final Node head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final Node head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(Node head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(val!=(head.val));
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
            return head.uncheckedsearch((int)TypeUtil.castToByte(val));
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
            return head.uncheckedsearch((val));
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
            final int v;
            if((v=(int)val)==val)
            {
              return head.uncheckedsearch(v);
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
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return head.uncheckedsearch(v);
            }
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
            final int v;
            if(val==(v=(int)val))
            {
              return head.uncheckedsearch(v);
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
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return head.uncheckedsearch(i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final IntSnglLnkSeq parent;
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(IntSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public int nextInt(){
        final Node next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node next,IntConsumer action){
        Node prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(IntConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final IntSnglLnkSeq parent;
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
