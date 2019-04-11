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
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.impl.AbstractCharItr;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.util.CharSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class CharSnglLnkSeq implements OmniCollection.OfChar,Cloneable,Externalizable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient CharSnglLnkNode head;
  private CharSnglLnkSeq(){
  }
  private CharSnglLnkSeq(CharSnglLnkNode head,int size){
    this.size=size;
    this.head=head;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException
  {
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      var curr=this.head;
      do
      {
        out.writeChar(curr.val);
      }
      while((curr=curr.next)!=null);
    }
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
  public char peekChar(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (char)(head.val);
    }
    return Character.MIN_VALUE;
  }
  public Character peek(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (Character)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public long peekLong(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (long)(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt(){
    final CharSnglLnkNode head;
    if((head=this.head)!=null){
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  abstract boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(CharSnglLnkNode head,int size){
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
        for(clone=new CheckedStack(newHead=new CharSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new CharSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public char popChar(){
      CharSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
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
    private int removeIfHelper(CharSnglLnkNode prev,CharPredicate filter,int numLeft,int modCount)
    {
      if(numLeft!=0)
      {
        int numSurvivors;
        if(numLeft>64)
        {
          long[] survivorSet;
          numSurvivors=CharSnglLnkNode.markSurvivors(prev.next,filter,survivorSet=BitSetUtil.getBitSet(numLeft));
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0)
          {
            CharSnglLnkNode.pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }
        else
        {
          long survivorWord=CharSnglLnkNode.markSurvivors(prev.next,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0)
          {
            CharSnglLnkNode.pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter){
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
    )
    {
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
    private static class Itr extends AbstractItr{
      final CheckedStack parent;
      transient int modCount;
      Itr(CheckedStack parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(CharSnglLnkNode next,CharConsumer action){
        final int modCount=this.modCount;
        CharSnglLnkNode prev,curr;
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
        final CharSnglLnkNode prev;
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
  public static class UncheckedStack extends CharSnglLnkSeq implements OmniStack.OfChar{
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
    }
    UncheckedStack(CharSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0)
      {
        CharSnglLnkNode curr;
        for(this.head=curr=new CharSnglLnkNode((char)in.readChar());--size!=0;curr=curr.next=new CharSnglLnkNode((char)in.readChar())){}
      }
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
        for(clone=new UncheckedStack(newHead=new CharSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new CharSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public char popChar(){
      CharSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
    @Override public Character pop(){
      return popChar();
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
    @Override boolean uncheckedremoveVal(CharSnglLnkNode head
      ,int val
    )
    {
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
    @Override public OmniIterator.OfChar iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr
    {
      Itr(){
        super(UncheckedStack.this.head);
      }
      @Override public void remove(){
        final UncheckedStack parent;
        --(parent=UncheckedStack.this).size;
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
  public static class CheckedQueue extends UncheckedQueue
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(CharSnglLnkNode head,int size,CharSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
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
    @Override public void clear(){
      if(size!=0)
      {
        ++this.modCount;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public Object clone(){
      CharSnglLnkNode head;
      if((head=this.head)!=null){
        CharSnglLnkNode newHead=new CharSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new CharSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(char val){
      ++this.modCount;
      super.push(val);
    }
    @Override public char removeChar(){
      final CharSnglLnkNode head;
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
    @Override public char pollChar(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
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
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(head==this.tail)
        {
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
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    private void pullSurvivorsDown(CharSnglLnkNode prev,CharPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved)
    {
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=null;
              if(curr==tail)
              {
                this.tail=prev;
              }
              return;
            }
            if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
        }
        if(--numSurvivors==0){
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(CharSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=null;
              if(curr==tail)
              {
                this.tail=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
        }
        if(--numSurvivors==0){
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(CharSnglLnkNode prev,CharPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64)
        {
          long[] survivorSet;
          numSurvivors=CharSnglLnkNode.markSurvivors(prev.next,filter,survivorSet=BitSetUtil.getBitSet(numLeft));
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0)
          {
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }
        else
        {
          long survivorWord=CharSnglLnkNode.markSurvivors(prev.next,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0)
          {
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter){
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
    @Override boolean uncheckedremoveVal(CharSnglLnkNode head
    ,int val
    ){
      if(val==(head.val))
      {
        if(head==tail)
        {
          this.tail=null;
        }
        this.head=head.next;
      }else{
        CharSnglLnkNode prev;
        {
          do{
            if(head==tail)
            {
              return false;
            }
            //if((head=(prev=head).next)==null){
            //  return false;
            //}
          }while(val!=((head=(prev=head).next).val));
        }
        if(head==tail)
        {
          this.tail=prev;
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
    private static class Itr extends AbstractItr
    {
      transient final CheckedQueue parent;
      transient int modCount;
      Itr(CheckedQueue parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(CharSnglLnkNode next,CharConsumer action){
        final int modCount=this.modCount;
        CharSnglLnkNode prev,curr;
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
        final CharSnglLnkNode prev;
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
          if(curr==parent.tail)
          {
            parent.tail=prev;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedQueue extends CharSnglLnkSeq implements OmniQueue.OfChar{
    private static final long serialVersionUID=1L;
    transient CharSnglLnkNode tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(CharSnglLnkNode head,int size,CharSnglLnkNode tail){
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
      if(size!=0)
      {
        CharSnglLnkNode curr;
        for(this.head=curr=new CharSnglLnkNode((char)in.readChar());--size!=0;curr=curr.next=new CharSnglLnkNode((char)in.readChar())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      CharSnglLnkNode head;
      if((head=this.head)!=null){
        CharSnglLnkNode newHead=new CharSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new CharSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(char val){
      CharSnglLnkNode newNode;
      this.tail.next=(newNode=new CharSnglLnkNode(val));
      this.tail=newNode;
      ++this.size;
    }
    @Override public char charElement(){
      return head.val;
    }
    @Override public boolean offer(char val){
      push(val);
      return true;
    }
    @Override public char removeChar(){
      final CharSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public char pollChar(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character remove(){
      return removeChar();
    }
    @Override public Character element(){
      return charElement();
    }
    @Override public boolean offer(Character val){
      push((char)val);
      return true;
    }
    @Override public Character poll(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Character)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final CharSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    private int removeIfHelper(CharSnglLnkNode prev,CharSnglLnkNode tail,CharPredicate filter)
    {
      int numSurvivors=1;
      outer:for(CharSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(CharSnglLnkNode prev,CharSnglLnkNode curr,CharSnglLnkNode tail,CharPredicate filter)
    {
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
    @Override boolean uncheckedremoveIf(CharSnglLnkNode head,CharPredicate filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;)
        {
          if(!filter.test((head=head.next).val))
          {
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
        for(final var tail=this.tail;head!=tail;++numSurvivors)
        {
          final CharSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val))
          {
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(CharSnglLnkNode head
    ,int val
    ){
      if(val==(head.val))
      {
        if(head==tail)
        {
          this.tail=null;
        }
        this.head=head.next;
      }else{
        CharSnglLnkNode prev;
        {
          do{
            if(head==tail)
            {
              return false;
            }
            //if((head=(prev=head).next)==null){
            //  return false;
            //}
          }while(val!=((head=(prev=head).next).val));
        }
        if(head==tail)
        {
          this.tail=prev;
        }
        prev.next=head.next;
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr
    {
      Itr(){
        super(UncheckedQueue.this.head);
      }
      @Override public void remove(){
        final UncheckedQueue parent;
        --(parent=UncheckedQueue.this).size;
        final CharSnglLnkNode prev;
        if((prev=this.prev)==null)
        {
          parent.head=next;
        }else{
          prev.next=null;
        }
        if(this.curr==parent.tail)
        {
          parent.tail=prev;
        }
        this.curr=prev;
      }
    }
  }
  private static class AbstractItr
      extends AbstractCharItr
  {
    transient CharSnglLnkNode prev;
    transient CharSnglLnkNode curr;
    transient CharSnglLnkNode next;
    AbstractItr(CharSnglLnkNode next)
    {
      this.next=next; 
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
    void uncheckedForEachRemaining(CharSnglLnkNode next,CharConsumer action){
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
  }
}
