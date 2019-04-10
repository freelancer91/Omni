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
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import omni.util.HashUtil;
import omni.util.TypeUtil;
import omni.impl.AbstractDoubleItr;
import omni.api.OmniStack;
public abstract class DoubleSnglLnkSeq implements OmniCollection.OfDouble,Cloneable{
  static class Node{
    transient double val;
    transient Node next;
    Node(double val){
      this.val=val;
    }
    Node(double val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+HashUtil.hashDouble(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+HashUtil.hashDouble(curr.val);
      }
      return hash;
    }
    private void uncheckedToString(StringBuilder builder){
      for(var curr=this;;builder.append(',').append(' ')){
        builder.append(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(DoubleConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
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
    private void uncheckedCopyInto(Double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private boolean uncheckedcontainsBits(long bits){
      for(var curr=this;bits!=Double.doubleToRawLongBits(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearchBits(long bits){
      int index=1;
      for(var curr=this;bits!=Double.doubleToRawLongBits(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private boolean uncheckedcontains0(){
      for(var curr=this;!Double.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearch0(){
      int index=1;
      for(var curr=this;!Double.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
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
    private int retainSurvivors(final DoublePredicate filter){
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
    private int retainTrailingSurvivors(final DoublePredicate filter){
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
  private DoubleSnglLnkSeq(){
  }
  private DoubleSnglLnkSeq(int size,Node head){
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
      final StringBuilder builder=new StringBuilder("[");
      head.uncheckedToString(builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(DoubleConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
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
  abstract void push(double val);
  @Override public boolean add(double val)
  {
    push((val));
    return true;
  }
  public void push(Double val){
    push((double)val);
  }
  @Override public boolean add(Double val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(char val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(short val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(long val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(float val)
  {
    push((double)(val));
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
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      head.uncheckedCopyInto(dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public Double[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Double[] dst;
      head.uncheckedCopyInto(dst=new Double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val)
            {
              return head.uncheckedcontainsBits(TypeUtil.DBL_TRUE_BITS);
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
              {
                return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
              if(TypeUtil.checkCastToDouble(val))
              {
                return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
              return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
            if(val==val)
            {
              return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
            }
            return head.uncheckedcontainsNaN();
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
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return head.uncheckedcontainsBits(Double.doubleToRawLongBits(d));
                }
                return head.uncheckedcontainsNaN();
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return head.uncheckedcontainsBits(Double.doubleToRawLongBits(f));
                }
                return head.uncheckedcontainsNaN();
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return head.uncheckedcontainsBits(Double.doubleToRawLongBits(i));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return head.uncheckedcontainsBits(Double.doubleToRawLongBits(l));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return head.uncheckedcontainsBits(Double.doubleToRawLongBits(i));
                }
                return head.uncheckedcontains0();
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return head.uncheckedcontainsBits(TypeUtil.DBL_TRUE_BITS);
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
              return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
              return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
              return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
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
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
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
              if(TypeUtil.checkCastToDouble(val))
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
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
            if(val==val)
            {
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveValNaN(head);
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
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(d));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
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
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveValBits(Node head,long bits);
  abstract boolean uncheckedremoveVal0(Node head);
  abstract boolean uncheckedremoveValNaN(Node head);
  @Override public boolean removeIf(DoublePredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Double> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node head,DoublePredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(double val){
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
    @Override public double popDouble(){
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
    @Override public void forEach(DoubleConsumer action){
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
    @Override public void forEach(Consumer<? super Double> action){
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
    @Override boolean uncheckedremoveIf(Node head,DoublePredicate filter){
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
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,long bits
      ){
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Double.doubleToRawLongBits(head.val));
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
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Double.isNaN(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractDoubleItr
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
      @Override public double nextDouble(){
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
      private void uncheckedForEachRemaining(Node next,DoubleConsumer action){
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
      @Override public void forEachRemaining(DoubleConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
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
  public static class UncheckedStack extends DoubleSnglLnkSeq implements OmniStack.OfDouble{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(double val){
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
    @Override public double popDouble(){
      Node head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node head,DoublePredicate filter){
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
    @Override public Double pop(){
      return popDouble();
    }
    @Override public double peekDouble(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double peek(){
      final Node head;
      if((head=this.head)!=null){
        return (Double)(head.val);
      }
      return null;
    }
    @Override public Double poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,long bits
      ){
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Double.doubleToRawLongBits(head.val));
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
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Double.isNaN(head.val));
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
              return head.uncheckedsearchBits(TypeUtil.DBL_TRUE_BITS);
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
              {
                return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
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
              if(TypeUtil.checkCastToDouble(val))
              {
                return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
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
              return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
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
            if(val==val)
            {
              return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
            }
            return head.uncheckedsearchNaN();
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
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return head.uncheckedsearchBits(Double.doubleToRawLongBits(d));
                }
                return head.uncheckedsearchNaN();
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return head.uncheckedsearchBits(Double.doubleToRawLongBits(f));
                }
                return head.uncheckedsearchNaN();
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return head.uncheckedsearchBits(Double.doubleToRawLongBits(i));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return head.uncheckedsearchBits(Double.doubleToRawLongBits(l));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return head.uncheckedsearchBits(Double.doubleToRawLongBits(i));
                }
                return head.uncheckedsearch0();
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return head.uncheckedsearchBits(TypeUtil.DBL_TRUE_BITS);
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
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final DoubleSnglLnkSeq parent;
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(DoubleSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public double nextDouble(){
        final Node next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node next,DoubleConsumer action){
        Node prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final DoubleSnglLnkSeq parent;
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
