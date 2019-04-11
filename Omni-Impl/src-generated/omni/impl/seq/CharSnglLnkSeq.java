package omni.impl.seq;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniCollection;
import omni.impl.CheckedCollection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractCharItr;
import omni.api.OmniStack;
import omni.util.CharSnglLnkNode;
public abstract class CharSnglLnkSeq implements OmniCollection.OfChar,Cloneable{
  transient int size;
  transient CharSnglLnkNode head;
  private CharSnglLnkSeq(){
  }
  private CharSnglLnkSeq(int size,CharSnglLnkNode head){
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
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return CharSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final char[] buffer;
      CharSnglLnkNode.uncheckedToString(head,buffer=new char[size=this.size*3]);
      buffer[0]='[';
      buffer[size-1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  @Override public void forEach(CharConsumer action){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      CharSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      CharSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      CharSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(char val);
  @Override public boolean add(char val)
  {
    push((val));
    return true;
  }
  public void push(Character val){
    push((char)val);
  }
  @Override public boolean add(Character val)
  {
    push((char)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((char)TypeUtil.castToChar(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      CharSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public char[] toCharArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final char[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new char[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final Character[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new Character[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      final int[] dst;
      CharSnglLnkNode.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedcontains(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(char)val)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val)
            {
              return CharSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharSnglLnkNode.uncheckedcontains(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      if(val>=0)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      if(val>=0)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final CharSnglLnkNode head;
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val)
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
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
      if(val>=0)
      {
        {
          final CharSnglLnkNode head;
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(CharSnglLnkNode head,int val);
  @Override public boolean removeIf(CharPredicate filter){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Character> filter){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,CharSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(char val){
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
      CharSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        CharSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new CharSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new CharSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public char popChar(){
      CharSnglLnkNode head;
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
    @Override public void forEach(CharConsumer action){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=CharSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          CharSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+CharSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
    @Override public char pollChar(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character poll(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Character)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final CharSnglLnkNode head;
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
      final CharSnglLnkNode head;
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
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(CharSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          CharSnglLnkNode prev;
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
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient CharSnglLnkNode prev;
      transient CharSnglLnkNode curr;
      transient CharSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public char nextChar(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final CharSnglLnkNode next;
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
      private void uncheckedForEachRemaining(CharSnglLnkNode next,CharConsumer action){
        final int modCount=this.modCount;
        CharSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(CharConsumer action){
        final CharSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final CharSnglLnkNode prev;
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
  public static class UncheckedStack extends CharSnglLnkSeq implements OmniStack.OfChar{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,CharSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(char val){
      this.head=new CharSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      CharSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        CharSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new CharSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new CharSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public char popChar(){
      CharSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=CharSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        CharSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+CharSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Character pop(){
      return popChar();
    }
    @Override public char peekChar(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollChar(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character peek(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (Character)(head.val);
      }
      return null;
    }
    @Override public Character poll(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Character)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(CharSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          CharSnglLnkNode prev;
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
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedsearch(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(char)val)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val)
            {
              return CharSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharSnglLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      if(val>=0)
      {
        {
          final CharSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return CharSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CharSnglLnkSeq parent;
      transient CharSnglLnkNode prev;
      transient CharSnglLnkNode curr;
      transient CharSnglLnkNode next;
      Itr(CharSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public char nextChar(){
        final CharSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(CharSnglLnkNode next,CharConsumer action){
        CharSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final CharSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final CharSnglLnkSeq parent;
        --(parent=this.parent).size;
        final CharSnglLnkNode prev;
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
