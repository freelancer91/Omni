package omni.impl.seq;

import java.io.Externalizable;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniCollection;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractOmniCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;

abstract class AbstractBooleanArrSeq extends AbstractOmniCollection<Boolean> implements OmniCollection.OfBoolean,Externalizable,RandomAccess{
    AbstractBooleanArrSeq(){
        super();
    }
    AbstractBooleanArrSeq(int size){
        super(size);
    }
    @Override
    public void clear(){
        size=0;
    }
    
    abstract int uncheckedsearch(int size,boolean val);
    
    public int search(boolean val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              return uncheckedsearch(size,val);
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int search(int val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedsearch(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int search(long val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              returnFalse:for(;;){
                final boolean v;
                if(val==0L){
                  v=false;
                }else if(val==1L){
                  v=true;
                }else{
                  break returnFalse;
                }
                return uncheckedsearch(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int search(float val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedsearch(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int search(double val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedsearch(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int search(Object val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              //TODO a pattern-matching switch statement would be great here
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
                return uncheckedsearch(size,b);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
    abstract boolean popBoolean();
    public Boolean pop(){
      return popBoolean();
    }
    abstract boolean uncheckedcontains(int bound,boolean val);
    abstract boolean uncheckedremoveVal(int size,boolean val);
    abstract void uncheckedCopyInto(Object[] dst,int length);
    abstract void uncheckedCopyInto(Boolean[] dst,int length);
    abstract void uncheckedCopyInto(double[] dst,int length);
    abstract void uncheckedCopyInto(float[] dst,int length);
    abstract void uncheckedCopyInto(long[] dst,int length);
    abstract void uncheckedCopyInto(int[] dst,int length);
    abstract void uncheckedCopyInto(short[] dst,int length);
    abstract void uncheckedCopyInto(char[] dst,int length);
    abstract void uncheckedCopyInto(byte[] dst,int length);
    abstract void uncheckedCopyInto(boolean[] dst,int length);
    abstract void uncheckedAppend(int size,boolean val);
    abstract void uncheckedInit(boolean val);
    abstract void uncheckedForEach(int size,BooleanConsumer action);
    abstract int uncheckedRemoveIfImpl(int size,BooleanPredicate filter);
    abstract int uncheckedToString(int size,byte[] buffer);
    abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder);
    //abstract int uncheckedHashCode(int size);

    @Override
    public boolean contains(Object val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                //TODO a pattern-matching switch statement would be great here
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
                  return uncheckedcontains(size-1,b);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean contains(boolean val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                return uncheckedcontains(size-1,val);
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean contains(int val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return uncheckedcontains(size-1,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean contains(long val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                returnFalse:for(;;){
                  final boolean v;
                  if(val==0L){
                    v=false;
                  }else if(val==1L){
                    v=true;
                  }else{
                    break returnFalse;
                  }
                  return uncheckedcontains(size-1,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean contains(float val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return uncheckedcontains(size-1,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean contains(double val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return uncheckedcontains(size-1,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    
    @Override
    public boolean remove(Object val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                //TODO a pattern-matching switch statement would be great here
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
                  return this.uncheckedremoveVal(size,b);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean removeVal(boolean val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                return this.uncheckedremoveVal(size,val);
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean removeVal(int val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return this.uncheckedremoveVal(size,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean removeVal(long val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
                returnFalse:for(;;){
                  final boolean v;
                  if(val==0L){
                    v=false;
                  }else if(val==1L){
                    v=true;
                  }else{
                    break returnFalse;
                  }
                  return this.uncheckedremoveVal(size,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean removeVal(float val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return this.uncheckedremoveVal(size,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    @Override
    public boolean removeVal(double val){
        {
            {
              final int size;
              if((size=this.size)!=0)
              {
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
                  return this.uncheckedremoveVal(size,v);
                }
              } //end size check
            } //end checked sublist try modcount
          }//end val check
          return false;
        }
    
    abstract int uncheckedindexOf(int size,boolean val);
    abstract int uncheckedlastIndexOf(int size,boolean val);
    
    public int indexOf(boolean val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              return uncheckedindexOf(size,val);
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int indexOf(int val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedindexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int indexOf(long val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              returnFalse:for(;;){
                final boolean v;
                if(val==0L){
                  v=false;
                }else if(val==1L){
                  v=true;
                }else{
                  break returnFalse;
                }
                return uncheckedindexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int indexOf(float val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedindexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int indexOf(double val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedindexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int indexOf(Object val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              //TODO a pattern-matching switch statement would be great here
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
                return uncheckedindexOf(size,b);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(boolean val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
                return uncheckedlastIndexOf(size,val);
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(int val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedlastIndexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(long val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              returnFalse:for(;;){
                final boolean v;
                if(val==0L){
                  v=false;
                }else if(val==1L){
                  v=true;
                }else{
                  break returnFalse;
                }
                return uncheckedlastIndexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(float val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedlastIndexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(double val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
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
                return uncheckedlastIndexOf(size,v);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
      public int lastIndexOf(Object val){
        {
          {
            final int size;
            if((size=this.size)!=0)
            {
              //TODO a pattern-matching switch statement would be great here
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
                return uncheckedlastIndexOf(size,b);
              }
            } //end size check
          } //end checked sublist try modcount
        }//end val check
        return -1;
      }
    
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int size;
        T[] dst=arrConstructor.apply(size=this.size);
        if(size!=0){
          uncheckedCopyInto(dst,size);
        }
        return dst;
      }
    @Override
    public <T> T[] toArray(T[] dst){
        final int size;
        if((size=this.size)!=0){
          uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),size);
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
   
    public void push(Boolean val) {
        push((boolean)val);
    }
    public void push(boolean val){
        final int size;
        if((size=this.size)!=0){
          uncheckedAppend(size,val);
        }else{
          uncheckedInit(val);
        }
      }
    @Override
    public boolean add(boolean val){
        push(val);
        return true;
      }
    @Override
    public boolean add(Boolean val){
        push(val);
        return true;
      }
    
    @Override
    public void forEach(BooleanConsumer action){
        final int size;
        if((size=this.size)!=0){
          uncheckedForEach(size,action);
        }
      }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        final int size;
        if((size=this.size)!=0){
          uncheckedForEach(size,action::accept);
        }
      }
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter) {
        if(size!=(size-=uncheckedRemoveIfImpl(size,filter))){
            this.size=size;
            return true;
          }
          return false;
        }
    @Override
    public boolean removeIf(BooleanPredicate filter){
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
      }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter){
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
      }
    
    @Override
    public Boolean[] toArray(){
        final int size;
        if((size=this.size)!=0){
          final Boolean[] dst;
          uncheckedCopyInto(dst=new Boolean[size],size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      }
    @Override
    public boolean[] toBooleanArray(){
        final int size;
        if((size=this.size)!=0){
          final boolean[] dst;
          uncheckedCopyInto(dst=new boolean[size],size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_ARR;
      }
    @Override
    public double[] toDoubleArray(){
        final int size;
        if((size=this.size)!=0){
          final double[] dst;
          uncheckedCopyInto(dst=new double[size],size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    @Override
    public float[] toFloatArray(){
        final int size;
        if((size=this.size)!=0){
          final float[] dst;
          uncheckedCopyInto(dst=new float[size],size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
    @Override
    public long[] toLongArray(){
        final int size;
        if((size=this.size)!=0){
          final long[] dst;
          uncheckedCopyInto(dst=new long[size],size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
    @Override
    public int[] toIntArray(){
        final int size;
        if((size=this.size)!=0){
          final int[] dst;
          uncheckedCopyInto(dst=new int[size],size);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
    @Override
    public short[] toShortArray(){
        final int size;
        if((size=this.size)!=0){
          final short[] dst;
          uncheckedCopyInto(dst=new short[size],size);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
    @Override
    public byte[] toByteArray(){
        final int size;
        if((size=this.size)!=0){
          final byte[] dst;
          uncheckedCopyInto(dst=new byte[size],size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
    @Override
    public char[] toCharArray(){
        final int size;
        if((size=this.size)!=0){
          final char[] dst;
          uncheckedCopyInto(dst=new char[size],size);
          return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }

    
    @Override
    public String toString(){
        int size;
        if((size=this.size)!=0){
          final byte[] buffer;
          if(size<=OmniArray.MAX_ARR_SIZE/7){(buffer=new byte[size*7])
            [size=uncheckedToString(size,buffer)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
          }
        }
        return "[]";
      }
   
    
  
    
}
