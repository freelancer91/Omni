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
public abstract class CharSnglLnkSeq implements OmniCollection.OfChar,Cloneable{
  static class Node{
    transient char val;
    transient Node next;
    Node(char val){
      this.val=val;
    }
    Node(char val,Node next){
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
    private void uncheckedToString(char[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',++bufferOffset){
        buffer[bufferOffset]=curr.val;
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(CharConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(char[] dst){
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
    private void uncheckedCopyInto(Character[] dst){
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
    private void uncheckedCopyInto(int[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(int)(curr.val);
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
    private int retainSurvivors(final CharPredicate filter){
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
    private int retainTrailingSurvivors(final CharPredicate filter){
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
  private CharSnglLnkSeq(){
  }
  private CharSnglLnkSeq(int size,Node head){
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
      final char[] buffer;
      head.uncheckedToString(buffer=new char[size=this.size*3]);
      buffer[0]='[';
      buffer[size-1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  @Override public void forEach(CharConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
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
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public char[] toCharArray(){
    final Node head;
    if((head=this.head)!=null){
      final char[] dst;
      head.uncheckedCopyInto(dst=new char[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Character[] dst;
      head.uncheckedCopyInto(dst=new Character[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      head.uncheckedCopyInto(dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return head.uncheckedcontains(TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(char)val)
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
            final char v;
            if((v=(char)val)==val)
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
            final char v;
            if(val==(v=(char)val))
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
            final char v;
            if(val==(v=(char)val))
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
              return head.uncheckedcontains(i);
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
    @Override public boolean contains(short val){
      if(val>=0)
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
    @Override public boolean removeVal(short val){
      if(val>=0)
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
  @Override public boolean removeIf(CharPredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Character> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node head,CharPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node head){
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
      Node head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        Node newHead;
        for(clone=new CheckedStack(this.size,newHead=new Node(head.val));(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public char popChar(){
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
    @Override public void forEach(CharConsumer action){
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
    @Override public void forEach(Consumer<? super Character> action){
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
    @Override boolean uncheckedremoveIf(Node head,CharPredicate filter){
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
    @Override public char pollChar(){
      final Node head;
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
      final Node head;
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
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
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
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractCharItr
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
      @Override public char nextChar(){
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
      private void uncheckedForEachRemaining(Node next,CharConsumer action){
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
      @Override public void forEachRemaining(CharConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
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
  public static class UncheckedStack extends CharSnglLnkSeq implements OmniStack.OfChar{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(char val){
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
    @Override public char popChar(){
      Node head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node head,CharPredicate filter){
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
    @Override public Character pop(){
      return popChar();
    }
    @Override public char peekChar(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollChar(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character peek(){
      final Node head;
      if((head=this.head)!=null){
        return (Character)(head.val);
      }
      return null;
    }
    @Override public Character poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Character)(head.val);
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
    @Override public int peekInt(){
      final Node head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
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
            return head.uncheckedsearch(TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(char)val)
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
            final char v;
            if((v=(char)val)==val)
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
            final char v;
            if(val==(v=(char)val))
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
            final char v;
            if(val==(v=(char)val))
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
              return head.uncheckedsearch(i);
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
            return head.uncheckedsearch((val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      if(val>=0)
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
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CharSnglLnkSeq parent;
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(CharSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public char nextChar(){
        final Node next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node next,CharConsumer action){
        Node prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final CharSnglLnkSeq parent;
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
