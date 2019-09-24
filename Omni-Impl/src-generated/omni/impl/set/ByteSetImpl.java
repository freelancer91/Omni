package omni.impl.set;
import omni.impl.AbstractByteItr;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.impl.CheckedCollection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import java.util.NoSuchElementException;
public abstract class ByteSetImpl extends AbstractByteSet{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  //TODO toString
  //TODO equals
  //TODO hashCode
  //TODO read/write methods
  @Override public boolean add(byte val){
    switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word|=(1L<<val))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word|=(1L<<val))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word|=(1L<<val))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word|=(1L<<val))){
          this.word3=word;
          return true;
        }
    }
    return false;
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(word|=(val?0b10:0b01))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean contains(boolean val){
    return (this.word2&(val?0b10:0b01))!=0;
  }
  @Override public boolean contains(byte val){
    switch(val>>6){
    case -2:
      return (this.word0&(1L<<val))!=0;
    case -1:
      return (this.word1&(1L<<val))!=0;
    case 0:
      return (this.word2&(1L<<val))!=0;
    default:
      return (this.word3&(1L<<val))!=0;
    }
  }
  @Override public boolean contains(char val){
    switch(val>>6){
    case 0:
      return (this.word2&(1L<<val))!=0;
    case 1:
      return (this.word3&(1L<<val))!=0;
    default:
      return false;
    }
  }
  @Override public boolean contains(double val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(float val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(long val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(int val){
    switch(val>>6){
    case -2:
      return (this.word0&(1L<<val))!=0;
    case -1:
      return (this.word1&(1L<<val))!=0;
    case 0:
      return (this.word2&(1L<<val))!=0;
    case 1:
      return (this.word3&(1L<<val))!=0;
    default:
      return false;
    }
  }
  @Override public boolean removeVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?(~0b10):(~0b01)))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(byte val){
    switch(val>>6){
    case -2:
      long word;
      if((word=this.word0)!=(word&=(1L<<val))){
        this.word0=word;
        return true;
      }
      break;
    case -1:
      if((word=this.word1)!=(word&=(1L<<val))){
        this.word1=word;
        return true;
      }
      break;
    case 0:
      if((word=this.word2)!=(word&=(1L<<val))){
        this.word2=word;
        return true;
      }
      break;
    default:
      if((word=this.word3)!=(word&=(1L<<val))){
        this.word3=word;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(char val){
    switch(val>>6){
    case 0:
      long word;
      if((word=this.word2)!=(word&=(1L<<val))){
        this.word2=word;
        return true;
      }
      break;
    case 1:
      if((word=this.word3)!=(word&=(1L<<val))){
        this.word3=word;
        return true;
      }
    default:
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    switch(val>>6){
    case -2:
      long word;
      if((word=this.word0)!=(word&=(1L<<val))){
        this.word0=word;
        return true;
      }
      break;
    case -1:
      if((word=this.word1)!=(word&=(1L<<val))){
        this.word1=word;
        return true;
      }
      break;
    case 0:
      if((word=this.word2)!=(word&=(1L<<val))){
        this.word2=word;
        return true;
      }
      break;
    case 1:
      if((word=this.word3)!=(word&=(1L<<val))){
        this.word3=word;
        return true;
      }
    default:
    }
    return false;
  }
  public static class Checked extends ByteSetImpl{
    transient int modCountAndSize;
    @Override public boolean add(byte val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public boolean add(boolean val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public OmniIterator.OfByte iterator(){
      return new CheckedAscendingItr(this);
    }
    private boolean uncheckedremoveIf(int modCountAndSize,BytePredicate filter){
      int offset=Byte.MAX_VALUE;
      int numRemoved=0;
      long word3=this.word3;
      for(long mask=Long.MIN_VALUE;;--offset){
        if((word3&mask)!=0){
          if(filter.test((byte)offset)){
            word3&=(~mask);
            ++numRemoved;
          }
          if(((--modCountAndSize)&0x1ff)==0){
            CheckedCollection.checkModCount(modCountAndSize>>>9,(modCountAndSize=this.modCountAndSize)>>>9);
            if(numRemoved==0){
              return false;
            }
            break;
          }
        }
        if((mask>>>=1)==0){
          long word2=this.word2;
          for(mask=Long.MIN_VALUE;;--offset){
            if((word2&mask)!=0){
              if(filter.test((byte)offset)){
                word2&=(~mask);
                ++numRemoved;
              }
              if(((--modCountAndSize)&0x1ff)==0){
                CheckedCollection.checkModCount(modCountAndSize>>>9,(modCountAndSize=this.modCountAndSize)>>>9);
                if(numRemoved==0){
                  return false;
                }
                break;
              }
            }
            if((mask>>>=1)==0){
              long word1=this.word1;
              for(mask=Long.MIN_VALUE;;--offset){
                if((word1&mask)!=0){
                  if(filter.test((byte)offset)){
                    word1&=(~mask);
                    ++numRemoved;
                  }
                  if(((--modCountAndSize)&0x1ff)==0){
                    CheckedCollection.checkModCount(modCountAndSize>>>9,(modCountAndSize=this.modCountAndSize)>>>9);
                    if(numRemoved==0){
                      return false;
                    }
                    break;
                  }
                }
                if((mask>>>=1)==0){
                  long word0=this.word0;
                  for(mask=Long.MIN_VALUE;;--offset,mask>>>=1){
                    if((word0&mask)!=0){
                      if(filter.test((byte)offset)){
                        word0&=(~mask);
                        ++numRemoved;
                      }
                      if(((--modCountAndSize)&0x1ff)==0){
                        CheckedCollection.checkModCount(modCountAndSize>>>9,(modCountAndSize=this.modCountAndSize)>>>9);
                        if(numRemoved==0){
                          return false;
                        }
                        break;
                      }
                    }
                  }
                  this.word0=word0;
                  break;
                }
              }
              this.word1=word1;
              break;
            }
          }
          this.word2=word2;
          break;
        }
      }
      this.word3=word3;
      this.modCountAndSize=modCountAndSize+(1<<9)-numRemoved;
      return true;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      return ((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedremoveIf(modCountAndSize,filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      return ((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedremoveIf(modCountAndSize,filter::test);
    }
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=(modCountAndSize&(~0x1ff))+(1<<9);
        this.word0=0;
        this.word1=0;
        this.word2=0;
        this.word3=0;
      }
    }
    @Override public boolean isEmpty(){
      return (this.modCountAndSize&0x1ff)==0;
    }
    @Override public int size(){
      return this.modCountAndSize&0x1ff;
    }
    @Override public boolean contains(Object val){
      if((this.modCountAndSize&0x1ff)!=0){
        if(val instanceof Byte){
          return super.contains((byte)val);
        }else if(val instanceof Integer || val instanceof Short){
          return super.contains(((Number)val).intValue());
        }else if(val instanceof Long){
          return super.contains((long)val);
        }else if(val instanceof Float){
          return super.contains((float)val);
        }else if(val instanceof Double){
          return super.contains((double)val);
        }else if(val instanceof Character){
          return super.contains((char)val);
        }else if(val instanceof Boolean){
          return super.contains((boolean)val);
        }
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        for(;;){
          if(val instanceof Byte){
            if(!super.removeVal((byte)val)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!super.removeVal(((Number)val).intValue())){
              break;
            }
          }else if(val instanceof Long){
            if(!super.removeVal((long)val)){
              break;
            }
          }else if(val instanceof Float){
            if(!super.removeVal((float)val)){
              break;
            }
          }else if(val instanceof Double){
            if(!super.removeVal((double)val)){
              break;
            }
          }else if(val instanceof Character){
            if(!super.removeVal((char)val)){
              break;
            }
          }else if(val instanceof Boolean){
            if(!super.removeVal((boolean)val)){
              break;
            }
          }else{
            break;
          }
          this.modCountAndSize=modCountAndSize+((1<<9)-1);
          return true;
        }
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        dst=OmniArray.uncheckedArrResize(numLeft,dst);
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(T)(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(T)(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(T)(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(T)(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int numLeft;
      final T[] dst;
      final int modCountAndSize=this.modCountAndSize;
      try{
        dst=arrConstructor.apply((numLeft=modCountAndSize&0x1ff));
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
      if(numLeft!=0){
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(T)(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(T)(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(T)(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(T)(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      return dst;
    }
    @Override public Byte[] toArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final Byte[] dst=new Byte[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(Byte)(byte)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final byte[] dst=new byte[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(byte)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final short[] dst=new short[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(short)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(short)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(short)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(short)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final int[] dst=new int[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final long[] dst=new long[numLeft];
      long offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final float[] dst=new float[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(float)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(float)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(float)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(float)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        final double[] dst=new double[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          dst[--numLeft]=(double)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(double)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(double)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(double)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public boolean removeVal(boolean val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    private void uncheckedForEachAscending(int numLeft,ByteConsumer action){
      int offset=-128;
      for(long word=this.word0;;){
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
          if(--numLeft==0){
            return;
          }
        }
        if(++offset==-64){
          for(word=this.word1;;){
            if((word&(1L<<offset))!=0){
              action.accept((byte)offset);
              if(--numLeft==0){
                return;
              }
            }
            if(++offset==0){
              for(word=this.word2;;){
                if((word&(1L<<offset))!=0){
                  action.accept((byte)offset);
                  if(--numLeft==0){
                    return;
                  }
                }
                if(++offset==64){
                  for(word=this.word3;;++offset){
                    if((word&(1L<<offset))!=0){
                      action.accept((byte)offset);
                      if(--numLeft==0){
                        return;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      int numLeft;
      if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          uncheckedForEachAscending(numLeft,action);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize;
      int numLeft;
      if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          uncheckedForEachAscending(numLeft,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    /*
    public static class Descending extends Checked{
    }
    */
  }
  private void uncheckedForEachAscending(int offset,ByteConsumer action){
    switch(offset>>6){
      case -2:
        Unchecked.uncheckedAscendingWordForEach(this.word0,offset,offset=-64,action);
      case -1:
        Unchecked.uncheckedAscendingWordForEach(this.word1,offset,offset=0,action);
      case 0:
        Unchecked.uncheckedAscendingWordForEach(this.word2,offset,offset=64,action);
      default:
        Unchecked.uncheckedAscendingWordForEach(this.word3,offset,128,action);
    }
  }
  public static class Unchecked extends ByteSetImpl{
    private static void uncheckedAscendingWordForEach(long word,int offset,int bound,ByteConsumer action){
      do{
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
        } 
      }while(++offset!=bound);
    }
    @Override public void forEach(ByteConsumer action){
      uncheckedAscendingWordForEach(word0,-128,-64,action);
      uncheckedAscendingWordForEach(word1,-64,0,action);
      uncheckedAscendingWordForEach(word2,0,64,action);
      uncheckedAscendingWordForEach(word3,64,128,action);
    }
    @Override public void forEach(Consumer<? super Byte> action){
      forEach((ByteConsumer)action::accept);
    }
    @Override public OmniIterator.OfByte iterator(){
      return new UncheckedAscendingItr(this);
    }
    @Override public boolean removeIf(BytePredicate filter){
      boolean changed=false;
      int offset=Byte.MIN_VALUE;
      for(long word=this.word0,mask=1L;;++offset){
        if((word&mask)!=0 && filter.test((byte)offset)){
          changed=true;
          word&=(~mask);
        }
        if((mask<<=1)==0L){
          for(this.word0=word,word=this.word1,mask=1L;;++offset){
            if((word&mask)!=0 && filter.test((byte)offset)){
              changed=true;
              word&=(~mask);
            }
            if((mask<<=1)==0L){
              for(this.word1=word,word=this.word2,mask=1L;;++offset){
                if((word&mask)!=0 && filter.test((byte)offset)){
                  changed=true;
                  word&=(~mask);
                }
                if((mask<<=1)==0L){
                  for(this.word2=word,word=this.word3,mask=1L;;++offset){
                    if((word&mask)!=0 && filter.test((byte)offset)){
                      changed=true;
                      word&=(~mask);
                    }
                    if((mask<<=1)==0L){
                      this.word3=word;
                      return changed;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return this.removeIf((BytePredicate)filter::test);
    }
    @Override public void clear(){
      this.word0=0;
      this.word1=0;
      this.word2=0;
      this.word3=0;
    }
    @Override public boolean isEmpty(){
      return this.word0==0 && this.word1==0 && this.word2==0 && this.word3==0;
    }
    @Override public int size(){
      return SetCommonImpl.size(word0,word1,word2,word3);
    }
    @Override public boolean contains(Object val){
      if(val instanceof Byte){
        return super.contains((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        return super.contains(((Number)val).intValue());
      }else if(val instanceof Long){
        return super.contains((long)val);
      }else if(val instanceof Float){
        return super.contains((float)val);
      }else if(val instanceof Double){
        return super.contains((double)val);
      }else if(val instanceof Character){
        return super.contains((char)val);
      }else if(val instanceof Boolean){
        return super.contains((boolean)val);
      }
      return false;
    }
    @Override public boolean remove(Object val){
      if(val instanceof Byte){
        return super.removeVal((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        return super.removeVal(((Number)val).intValue());
      }else if(val instanceof Long){
        return super.removeVal((long)val);
      }else if(val instanceof Float){
        return super.removeVal((float)val);
      }else if(val instanceof Double){
        return super.removeVal((double)val);
      }else if(val instanceof Character){
        return super.removeVal((char)val);
      }else if(val instanceof Boolean){
        return super.removeVal((boolean)val);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        dst=OmniArray.uncheckedArrResize(numLeft,dst);
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(T)(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(T)(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(T)(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(T)(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int numLeft;
      final long word0,word1,word2,word3;
      final T[] dst=arrConstructor.apply(numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
      if(numLeft!=0){
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(T)(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(T)(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(T)(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(T)(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      return dst;
    }
    @Override public Byte[] toArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final Byte[] dst=new Byte[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(Byte)(byte)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final byte[] dst=new byte[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(byte)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final short[] dst=new short[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(short)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(short)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(short)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(short)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final int[] dst=new int[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final long[] dst=new long[numLeft];
      long offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final float[] dst=new float[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(float)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(float)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(float)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(float)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int numLeft;
      final long word0,word1,word2,word3;
      if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
        final double[] dst=new double[numLeft];
      int offset=Byte.MAX_VALUE;
      outer:for(;;){
        if((word3&(1L<<offset))!=0){
          dst[--numLeft]=(double)(offset);
          if(numLeft==0){
            break outer;
          }
        }
        if(--offset==63){
          for(;;){
            if((word2&(1L<<offset))!=0){
              dst[--numLeft]=(double)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==-1){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(double)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-65){
                  for(;;--offset){
                    if((word0&(1L<<offset))!=0){
                      dst[--numLeft]=(double)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    /*
    public static class Descending extends Unchecked{
    }
    */
  }
  private static class UncheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl root;
    transient int currIndex;
    private UncheckedAscendingItr(ByteSetImpl root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfTrailingZeros(root.word0))==64){
        if((currIndex+=Long.numberOfTrailingZeros(root.word1))==128){
          if((currIndex+=Long.numberOfTrailingZeros(root.word2))==192){
            currIndex+=Long.numberOfTrailingZeros(root.word3);
          }
        }
      }
      this.currIndex=currIndex-128;
    }
    private UncheckedAscendingItr(UncheckedAscendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
    }
    @Override public Object clone(){
      return new UncheckedAscendingItr(this);
    }
    @Override public void remove(){
      final var root=this.root;
      int currIndex;
      switch(((currIndex=this.currIndex)-1)>>6){
        case 1:
          int lead0s;
          long word;
          if((lead0s=Long.numberOfLeadingZeros((word=root.word3)<<(-currIndex)))!=64){
            root.word3=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word2)<<(-currIndex)))!=64){
            root.word2=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word1)<<(-currIndex)))!=64){
            root.word1=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        default:
          root.word0=(word=root.word0)&(~(1L<<(currIndex-Long.numberOfLeadingZeros(word<<(-currIndex)))));
      }
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        root.uncheckedForEachAscending(currIndex,action);
        this.currIndex=128;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        root.uncheckedForEachAscending(currIndex,action::accept);
        this.currIndex=128;
      }
    }
    @Override public boolean hasNext(){
      return this.currIndex!=128;
    }
    @Override public byte nextByte(){
      final int ret;
      int currIndex;
      final var root=this.root;
      switch((currIndex=ret=this.currIndex)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(root.word0>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=-64;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(root.word1>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(root.word2>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=64;
        default:
          if((tail0s=Long.numberOfTrailingZeros(root.word3>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          this.currIndex=128;
      }
      return (byte)ret;
    }
  }
  private static class CheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int currIndex;
    transient int modCountAndLastRet;
    private CheckedAscendingItr(ByteSetImpl.Checked root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfTrailingZeros(root.word0))==64){
        if((currIndex+=Long.numberOfTrailingZeros(root.word1))==128){
          if((currIndex+=Long.numberOfTrailingZeros(root.word2))==192){
            currIndex+=Long.numberOfTrailingZeros(root.word3);
          }
        }
      }
      this.currIndex=currIndex-128;
      this.modCountAndLastRet=root.modCountAndSize|0x1ff;
    }
    private CheckedAscendingItr(CheckedAscendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
      this.modCountAndLastRet=that.modCountAndLastRet;
    }
    @Override public Object clone(){
      return new CheckedAscendingItr(this);
    }
    @Override public void remove(){
      int lastRet;
      int modCountAndLastRet;
      if((lastRet=(modCountAndLastRet=this.modCountAndLastRet)&0x1ff)==0x1ff){
        throw new IllegalStateException();
      }
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount(modCountAndLastRet&(~0x1ff),(modCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      switch((lastRet=(byte)lastRet)>>6){
        case -2:
          root.word0 &=(~(1L<<lastRet));
          break;
        case -1:
          root.word1 &=(~(1L<<lastRet));
          break;
        case 0:
          root.word2 &=(~(1L<<lastRet));
          break;
        default:
          root.word3 &=(~(1L<<lastRet));
      }
      root.modCountAndSize=modCountAndSize+((1<<9)-1);
      this.modCountAndLastRet=(modCountAndLastRet+(1<<9))|0x1ff;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean hasNext(){
      return this.currIndex!=128;
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndLastRet;
      CheckedCollection.checkModCount((modCountAndLastRet=this.modCountAndLastRet)&(-0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
      final int ret;
      int currIndex;
      switch((currIndex=ret=this.currIndex)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(root.word0>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=-64;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(root.word1>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(root.word2>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=64;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(root.word3>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          this.currIndex=128;
          break;
        default:
          throw new NoSuchElementException();
      }
      this.modCountAndLastRet=(modCountAndLastRet&(~0x1ff))+(ret&0xff);
      return (byte)ret;
    }
  }
}
