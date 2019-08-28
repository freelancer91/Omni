package omni.impl.seq;

import java.io.Externalizable;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniDeque;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.TypeUtil;

abstract class AbstractBooleanArrDeq implements OmniDeque.OfBoolean,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
  transient int head;
  transient int tail;
  public AbstractBooleanArrDeq(){
    super();
    this.tail=-1;
  }
  AbstractBooleanArrDeq(int head,int tail){
    super();
    this.head=head;
    this.tail=tail;
  }
  @Override public boolean isEmpty(){
    return this.tail==-1;
  }
  @Override public void forEach(BooleanConsumer action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action::accept);
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if(tail<(head=this.head)){
        return fragmentedRemoveIf(head,tail,filter);
      }
      return nonfragmentedRemoveIf(head,tail,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if(tail<(head=this.head)){
        return fragmentedRemoveIf(head,tail,filter::test);
      }
      return nonfragmentedRemoveIf(head,tail,filter::test);
    }
    return false;
  }
  @Override public void clear(){
    this.tail=-1;
  }
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  @Override public void addFirst(boolean val){
    push(val);
  }
  @Override public boolean offerFirst(boolean val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean offer(boolean val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,val);
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if(((bits=Double.doubleToRawLongBits(val))&Long.MAX_VALUE)==0){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean contains(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if(((bits=Double.doubleToRawLongBits((double)val))&Long.MAX_VALUE)==0){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedcontains(tail,b);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,val);
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if(((bits=Double.doubleToRawLongBits(val))&Long.MAX_VALUE)==0){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean remove(Object val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if(((bits=Double.doubleToRawLongBits((double)val))&Long.MAX_VALUE)==0){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedremoveVal(tail,b);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,val);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if(((bits=Double.doubleToRawLongBits(val))&Long.MAX_VALUE)==0){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,v);
        }
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val)
  {
    {
      final int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if(((bits=Double.doubleToRawLongBits((double)val))&Long.MAX_VALUE)==0){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedremoveLastOccurrence(tail,b);
        }
      }
    }
    return false;
  }
  @Override public int search(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,val);
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(val){
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          if(val==0L){
            v=false;
          }else if(val==1L){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          switch(Float.floatToRawIntBits(val)){
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        returnFalse:for(;;){
          final boolean v;
          long bits;
          if(((bits=Double.doubleToRawLongBits(val))&Long.MAX_VALUE)==0){
            v=false;
          }else if(bits==TypeUtil.DBL_TRUE_BITS){
            v=true;
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,v);
        }
      }
    }
    return -1;
  }
  @Override public int search(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        //todo: a pattern-matching switch statement would be great here
        returnFalse:for(;;){
          final boolean b;
          if(val instanceof Boolean){
            b=(boolean)val;
          }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
            switch(((Number)val).intValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                b=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                b=true;
            }
          }else if(val instanceof Double){
            final long bits;
            if(((bits=Double.doubleToRawLongBits((double)val))&Long.MAX_VALUE)==0){
              b=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              b=true;
            }else{
              break returnFalse;
            }
          }else if(val instanceof Long){
            final long v;
            if((v=(long)val)==0L){
              b=false;
            }else if(v==1L){
              b=true;
            }else{
             break returnFalse;
            }
          }else if(val instanceof Character){
            switch(((Character)val).charValue()){
              default:
                break returnFalse;
              case 0:
                b=false;
                break;
              case 1:
                b=true;
            }
          }else{
            break returnFalse;
          }
          return uncheckedsearch(tail,b);
        }
      }
    }
    return -1;
  }
  @Override public Boolean getFirst(){
    return booleanElement();
  }
  @Override public Boolean peekFirst(){
    return peek();
  }
  @Override public Boolean pollFirst(){
    return poll();
  }
  @Override public Boolean removeFirst(){
    return popBoolean();
  }
  @Override public Boolean remove(){
    return popBoolean();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  @Override public Boolean pop(){
    return popBoolean();
  }
  @Override public Boolean removeLast() {
    return removeLastBoolean();
  }
  @Override public void push(Boolean val){
    push((boolean)val);
  }
  @Override public boolean offer(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public Boolean element(){
    return booleanElement();
  }
  @Override public Boolean getLast(){
    return getLastBoolean();
  }
  @Override public void addFirst(Boolean val){
    push((boolean)val);
  }
  @Override public void addLast(Boolean val){
    addLast((boolean)val);
  }
  @Override public boolean add(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public boolean offerFirst(Boolean val){
    push((boolean)val);
    return true;
  }
  @Override public boolean offerLast(Boolean val){
    addLast((boolean)val);
    return true;
  }
  @Override public String toString(){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedToString(tail);
    }
    return "[]";
  }
  @Override public abstract Object clone();
  abstract void uncheckedForEach(int tail,BooleanConsumer action);
  abstract boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter);
  abstract boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter);
  abstract boolean uncheckedcontains(int tail,boolean val);
  abstract boolean uncheckedremoveVal(int tail,boolean val);
  abstract boolean uncheckedremoveLastOccurrence(int tail,boolean val);
  abstract int uncheckedsearch(int tail,boolean val);
  abstract String uncheckedToString(int tail);

  static abstract class AbstractDeqItr
  extends AbstractBooleanItr
{
  transient int cursor;
  AbstractDeqItr(AbstractDeqItr itr){
    this.cursor=itr.cursor;
  }
  AbstractDeqItr(int cursor){
    this.cursor=cursor;
  }
  @Override public boolean hasNext(){
    return this.cursor!=-1;
  }
  abstract void uncheckedForEachRemaining(int cursor,BooleanConsumer action);
  @Override public void forEachRemaining(BooleanConsumer action){
    int cursor;
    if((cursor=this.cursor)!=-1){
      uncheckedForEachRemaining(cursor,action);
    }
  }
  @Override public void forEachRemaining(Consumer<? super Boolean> action){
    int cursor;
    if((cursor=this.cursor)!=-1){
      uncheckedForEachRemaining(cursor,action::accept);
    }
  }
}
}
