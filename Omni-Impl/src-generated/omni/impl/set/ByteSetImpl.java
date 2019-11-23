package omni.impl.set;
import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ToStringUtil;
import omni.impl.AbstractByteItr;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.function.ByteComparator;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public abstract class ByteSetImpl extends AbstractByteSet.ComparatorlessImpl implements Externalizable{
  private static final long serialVersionUID=1L;
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  private ByteSetImpl(){
    super();
  }
  private ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  private ByteSetImpl(ByteSetImpl that){
    this(that.word0,that.word1,that.word2,that.word3);
  }
  private ByteSetImpl(Collection<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfRef<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.ByteOutput<?> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(word|=(val?1L<<1:1L<<0))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean add(byte val){
    long word;
    switch(val>>6){
      case -2:
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
  private static boolean wordContains(long word,long mask){
    return (word&mask)!=0;
  }
  private static boolean contains(ByteSetImpl set,boolean val){
    return wordContains(set.word2,val?2L:1L);
  }
  private static boolean contains(ByteSetImpl set,int val){
    final long word;
    switch(val>>6){
      default:
        return false;
      case -2:
        word=set.word0;
        break;
      case -1:
        word=set.word1;
        break;
      case 0:
        word=set.word2;
        break;
      case 1:
        word=set.word3;
    }
    return wordContains(word,1L<<val);
  }
  private static boolean contains(ByteSetImpl set,long val){
    final int v;
    return (v=(int)val)==val && contains(set,v);
  }
  private static boolean contains(ByteSetImpl set,float val){
    final int v;
    return (v=(int)val)==val && contains(set,v);
  }
  private static boolean contains(ByteSetImpl set,double val){
    final int v;
    return (v=(int)val)==val && contains(set,v);
  }
  private static boolean contains(ByteSetImpl set,Object val){
    final int v;
    if(val instanceof Byte){
      v=(byte)val;
    }else if(val instanceof Integer || val instanceof Short){
      v=((Number)val).intValue();
    }else if(val instanceof Long){
      return contains(set,(long)val);
    }else if(val instanceof Float){
      return contains(set,(float)val);
    }else if(val instanceof Double){
      return contains(set,(double)val);
    }else if(val instanceof Character){
      v=(char)val;
    }else if(val instanceof Boolean){
      return contains(set,(boolean)val);
    }else{
      return false;
    }
    return contains(set,v);
  }
  private static boolean removeVal(ByteSetImpl set,boolean val){
    long word;
    if((word=set.word2)!=(word&=(val?~2L:~1L))){
      set.word2=word;
      return true;
    }
    return false;
  }
  private static boolean removeVal(ByteSetImpl set,int val){
    returnTrue:for(;;){
      switch(val>>6){
        case -2:
          long word;
          if((word=set.word0)!=(word&=~(1L<<val))){
            set.word0=word;
            break returnTrue;
          }
          break;
        case -1:
          if((word=set.word1)!=(word&=~(1L<<val))){
            set.word1=word;
            break returnTrue;
          }
          break;
        case 0:
          if((word=set.word2)!=(word&=~(1L<<val))){
            set.word2=word;
            break returnTrue;
          }
          break;
        case 1:
          if((word=set.word3)!=(word&=~(1L<<val))){
            set.word3=word;
            break returnTrue;
          }
        default:
      }
      return false;
    }
    return true;
  }
  private static boolean removeVal(ByteSetImpl set,long val){
    final int v;
    return (v=(int)val)==val && removeVal(set,v);
  }
  private static boolean removeVal(ByteSetImpl set,float val){
    final int v;
    return (v=(int)val)==val && removeVal(set,v);
  }
  private static boolean removeVal(ByteSetImpl set,double val){
    final int v;
    return (v=(int)val)==val && removeVal(set,v);
  }
  private static boolean removeVal(ByteSetImpl set,Object val){
    final int v;
    if(val instanceof Byte){
      v=(byte)val;
    }else if(val instanceof Integer || val instanceof Short){
      v=((Number)val).intValue();
    }else if(val instanceof Long){
      return removeVal(set,(long)val);
    }else if(val instanceof Float){
      return removeVal(set,(float)val);
    }else if(val instanceof Double){
      return removeVal(set,(double)val);
    }else if(val instanceof Character){
      v=(char)val;
    }else if(val instanceof Boolean){
      return removeVal(set,(boolean)val);
    }else{
      return false;
    }
    return removeVal(set,v);
  }
  @Override public boolean contains(boolean val){
    return contains(this,val);
  }
  @Override public boolean contains(byte val){
    return contains(this,val);
  }
  @Override public boolean contains(char val){
    return contains(this,val);
  }
  @Override public boolean contains(int val){
    return contains(this,val);
  }
  @Override public boolean contains(long val){
    return contains(this,val);
  }
  @Override public boolean contains(float val){
    return contains(this,val);
  }
  @Override public boolean contains(double val){
    return contains(this,val);
  }
  @Override public boolean contains(Object val){
    return contains(this,val);
  }
  @Override public boolean removeVal(boolean val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(byte val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(char val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(int val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(long val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(float val){
    return removeVal(this,val);
  }
  @Override public boolean removeVal(double val){
    return removeVal(this,val);
  }
  @Override public boolean remove(Object val){
    return removeVal(this,val);
  }
  private static void clearHeadSet(ByteSetImpl set,int inclHi){
    switch(inclHi>>6){
      case 1:
        set.word3&=(-1L>>>(-inclHi-1));
        inclHi=-1;
      case 0:
        set.word2&=(-1L>>>(-inclHi-1));
        inclHi=-1;
      case -1:
        set.word1&=(-1L>>>(-inclHi-1));
        inclHi=-1;
      default:
        set.word0&=(-1L>>>(-inclHi-1));
    }
  }
  private static void clearTailSet(ByteSetImpl set,int inclLo){
    switch(inclLo>>6){
      case -2:
        set.word0&=(-1L<<inclLo);
        inclLo=0;
      case -1:
        set.word1&=(-1L<<inclLo);
        inclLo=0;
      case 0:
        set.word2&=(-1L<<inclLo);
        inclLo=0;
      default:
        set.word3&=(-1L<<inclLo);
    }
  }
  private static void clearBodySet(ByteSetImpl set,int boundInfo){
    int inclLo=boundInfo>>8;
    final int inclHi;
    switch((inclHi=(byte)(boundInfo&0xff))>>6){
      case 1:
        switch(inclLo>>6){
          case -2:
            set.word0&=(-1L<<inclLo);
            inclLo=0;
          case -1:
            set.word1&=(-1L<<inclLo);
            inclLo=0;
          case 0:
            set.word2&=(-1L<<inclLo);
            inclLo=0;
          default:
        }
        set.word3&=((-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        break;
      case 0:
        switch(inclLo>>6){
          case -2:
            set.word0&=(-1L<<inclLo);
            inclLo=0;
          case -1:
            set.word1&=(-1L<<inclLo);
            inclLo=0;
          default:
        }
        set.word2&=((-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        break;
      case -1:
        if(inclLo>>6==-2){
          set.word0&=(-1L<<inclLo);
          inclLo=0;
        }
        set.word1&=((-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        break;
      default:
        set.word0&=((-1L<<inclLo)&(-1L>>>(-inclHi-1)));
    }
  }
  //TODO equals
  private static void copyToArrayAscending(ByteSetImpl set,int size,byte[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,byte[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,byte[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,byte[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static byte[] toByteArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new byte[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  private static byte[] toByteArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new byte[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,short[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,short[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,short[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,short[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static short[] toShortArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new short[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  private static short[] toShortArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new short[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,int[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,int[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,int[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,int[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static int[] toIntArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new int[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  private static int[] toIntArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new int[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,long[] dst){
    done:for(long offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,long[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,long[] dst){
    done:for(long offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,long[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static long[] toLongArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new long[size];
      done:for(long offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  private static long[] toLongArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new long[size];
      done:for(long offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,float[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,float[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,float[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,float[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static float[] toFloatArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new float[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  private static float[] toFloatArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new float[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,double[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,double[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,double[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,double[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static double[] toDoubleArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new double[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  private static double[] toDoubleArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new double[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,Object[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,Object[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,Object[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,Object[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int size,Byte[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayAscending(ByteSetImpl set,int offset,int size,Byte[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int size,Byte[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  private static void copyToArrayDescending(ByteSetImpl set,int offset,int size,Byte[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  private static Byte[] toArrayAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new Byte[size];
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  private static Byte[] toArrayDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final var dst=new Byte[size];
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @SuppressWarnings("unchecked")
  private static <T> T[] toArrayAscending(ByteSetImpl set,IntFunction<T[]> arrConstructor){
    int size;
    final long word0,word1,word2,word3;
    final var dst=arrConstructor.apply(size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3));
    if(size!=0){
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  private static <T> T[] toArrayDescending(ByteSetImpl set,IntFunction<T[]> arrConstructor){
    int size;
    final long word0,word1,word2,word3;
    final var dst=arrConstructor.apply(size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3));
    if(size!=0){
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  private static <T> T[] toArrayAscending(ByteSetImpl set,T[] dst){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      dst=OmniArray.uncheckedArrResize(size,dst);
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
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
  private static <T> T[] toArrayDescending(ByteSetImpl set,T[] dst){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      dst=OmniArray.uncheckedArrResize(size,dst);
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            dst[--size]=(T)(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  private static int removeIfImplStartWord0Descending(ByteSetImpl.Checked set,int offset,int size,BytePredicate filter,int expectedModCount){
    int numRemoved=0;
    for(var word0=set.word0;;--offset){
      final long mask;
      if(wordContains(word0,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word0-=mask;
        }
        if(--size==0){
          CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
          if(numRemoved!=0){
            set.word0=word0;
          }
          break;
        }
      }
    }
    return numRemoved;
  }
  private static int removeIfImplStartWord1Descending(ByteSetImpl.Checked set,int offset,int size,BytePredicate filter,int expectedModCount){
    int numRemoved=0;
    outer:for(;;){
      var word1=set.word1;
      for(;;){
        long mask;
        if(wordContains(word1,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word1-=mask;
          }
          if(--size==0){
            CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==-65){
          var word0=set.word0;
          for(;;--offset){
            if(wordContains(word0,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word0-=mask;
              }
              if(--size==0){
                CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
          }
          set.word0=word0;
          break;
        }
      }
      set.word1=word1;
      break;
    }
    return numRemoved;
  }
  private static int removeIfImplStartWord2Descending(ByteSetImpl.Checked set,int offset,int size,BytePredicate filter,int expectedModCount){
    int numRemoved=0;
    outer:for(;;){
      var word2=set.word2;
      for(;;){
        long mask;
        if(wordContains(word2,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word2-=mask;
          }
          if(--size==0){
            CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==-1){
          var word1=set.word1;
          for(;;){
            if(wordContains(word1,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word1-=mask;
              }
              if(--size==0){
                CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(--offset==-65){
              var word0=set.word0;
              for(;;--offset){
                if(wordContains(word0,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word0-=mask;
                  }
                  if(--size==0){
                    CheckedCollection.checkModCount(set.modCountAndSize>>9,expectedModCount>>9);
                    if(numRemoved==0){
                      break outer;
                    }
                    break;
                  }
                }
              }
              set.word0=word0;
              break;
            }
          }
          set.word1=word1;
          break;
        }
      }
      set.word2=word2;
      break;
    }
    return numRemoved;
  }
  private static int word0RemoveIfAscending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word0;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word0=word;
          }
          return numRemoved;
        }
      }
      if(++offset==-64){
        if(numRemoved!=0){
          set.word0=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int word1RemoveIfAscending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word1;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word1=word;
          }
          return numRemoved;
        }
      }
      if(++offset==0){
        if(numRemoved!=0){
          set.word1=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int word2RemoveIfAscending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word2;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word2=word;
          }
          return numRemoved;
        }
      }
      if(++offset==64){
        if(numRemoved!=0){
          set.word2=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int word3RemoveIfAscending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word3;;++offset){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word3=word;
          }
          return numRemoved;
        }
      }
    }
  }
  private static int word0RemoveIfDescending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word0;;--offset){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word0=word;
          }
          return numRemoved;
        }
      }
    }
  }
  private static int word1RemoveIfDescending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word1;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word1=word;
          }
          return numRemoved;
        }
      }
      if(--offset==-65){
        if(numRemoved!=0){
          set.word1=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int word2RemoveIfDescending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word2;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word2=word;
          }
          return numRemoved;
        }
      }
      if(--offset==-1){
        if(numRemoved!=0){
          set.word2=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int word3RemoveIfDescending(ByteSetImpl set,int offset,int size,BytePredicate filter){
    int numRemoved=0;
    for(var word=set.word3;;){
      final long mask;
      if(wordContains(word,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word-=mask;
        }
        if(--size==0){
          if(numRemoved!=0){
            set.word3=word;
          }
          return numRemoved;
        }
      }
      if(--offset==63){
        if(numRemoved!=0){
          set.word3=word;
        }
        return (size<<9)+numRemoved;
      }
    }
  }
  private static int removeIfImplStartWord3Descending(ByteSetImpl.Checked set,int offset,int size,BytePredicate filter,int expectedModCount){
    int numRemoved=0;
    outer:for(;;){
      var word3=set.word3;
      for(;;){
        long mask;
        if(wordContains(word3,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word3-=mask;
          }
          if(--size==0){
            CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==63){
          var word2=set.word2;
          for(;;){
            if(wordContains(word2,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word2-=mask;
              }
              if(--size==0){
                CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(--offset==-1){
              var word1=set.word1;
              for(;;){
                if(wordContains(word1,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word1-=mask;
                  }
                  if(--size==0){
                    CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                    if(numRemoved==0){
                      break outer;
                    }
                    break;
                  }
                }
                if(--offset==-65){
                  var word0=set.word0;
                  for(;;--offset){
                    if(wordContains(word0,mask=1L<<offset)){
                      if(filter.test((byte)offset)){
                        ++numRemoved;
                        word0-=mask;
                      }
                      if(--size==0){
                        CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                        if(numRemoved==0){
                          break outer;
                        }
                        break;
                      }
                    }
                  }
                  set.word0=word0;
                  break;
                }
              }
              set.word1=word1;
              break;
            }
          }
          set.word2=word2;
          break;
        }
      }
      set.word3=word3;
      break;
    }
    return numRemoved;
  }
  private static int removeIfImplStartWord0Ascending(ByteSetImpl.Checked set,int offset,int size,BytePredicate filter,int expectedModCount){
    int numRemoved=0;
    outer:for(;;){
      var word0=set.word0;
      for(;;){
        long mask;
        if(wordContains(word0,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word0-=mask;
          }
          if(--size==0){
            CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(++offset==-64){
          var word1=set.word1;
          for(;;){
            if(wordContains(word1,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word1-=mask;
              }
              if(--size==0){
                CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(++offset==0){
              var word2=set.word2;
              for(;;){
                if(wordContains(word2,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word2-=mask;
                  }
                  if(--size==0){
                    CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                    if(numRemoved==0){
                      break outer;
                    }
                    break;
                  }
                }
                if(++offset==64){
                  var word3=set.word3;
                  for(;;++offset){
                    if(wordContains(word3,mask=1L<<offset)){
                      if(filter.test((byte)offset)){
                        ++numRemoved;
                        word3-=mask;
                      }
                      if(--size==0){
                        CheckedCollection.checkModCount(set.modCountAndSize>>8,expectedModCount>>9);
                        if(numRemoved==0){
                          break outer;
                        }
                        break;
                      }
                    }
                  }
                  set.word3=word3;
                  break;
                }
              }
              set.word2=word2;
              break;
            }
          }
          set.word1=word1;
          break;
        }
      }
      set.word0=word0;
      break;
    }
    return numRemoved;
  }
  private static int toStringAscending(ByteSetImpl set,int size,byte[] buffer){
    int bufferOffset=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
      }
    }
    return bufferOffset;
  }
  private static int toStringAscending(ByteSetImpl set,int offset,int size,byte[] buffer){
    int bufferOffset=0;
    done:switch(offset>>6){
      case -2:
        for(final var word=set.word0;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(final var word=set.word3;;++offset){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
        }
    }
    return bufferOffset;
  }
  private static int toStringDescending(ByteSetImpl set,int size,byte[] buffer){
    int bufferOffset=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--size==0){
            break done;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
      }
    }
    return bufferOffset;
  }
  private static int toStringDescending(ByteSetImpl set,int offset,int size,byte[] buffer){
    int bufferOffset=0;
    done:switch(offset>>6){
      case 1:
        for(final var word=set.word3;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(final var word=set.word2;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(final var word=set.word1;;){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(final var word=set.word0;;--offset){
          if(wordContains(word,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
        }
    }
    return bufferOffset;
  }
  private static String toStringAscending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final byte[] buffer;
      int bufferOffset;
      (buffer=new byte[size*6])[bufferOffset=0]='[';
      done:for(int offset=Byte.MIN_VALUE;;){
        for(;;){
          if(wordContains(word0,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==-64){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==0){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(++offset==64){
            break;
          }
        }
        for(;;++offset){
          if(wordContains(word3,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
        }
      }
      buffer[bufferOffset]=']';
      return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
    }
    return "[]";
  }
  private static String toStringDescending(ByteSetImpl set){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=set.word0,word1=set.word1,word2=set.word2,word3=set.word3))!=0){
      final byte[] buffer;
      int bufferOffset;
      (buffer=new byte[size*6])[bufferOffset=0]='[';
      done:for(int offset=Byte.MAX_VALUE;;){
        for(;;){
          if(wordContains(word3,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==63){
            break;
          }
        }
        for(;;){
          if(wordContains(word2,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==-1){
            break;
          }
        }
        for(;;){
          if(wordContains(word1,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
          if(--offset==-65){
            break;
          }
        }
        for(;;--offset){
          if(wordContains(word0,1L<<offset)){
            bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
        }
      }
      buffer[bufferOffset]=']';
      return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
    }
    return "[]";
  }
  private static void forEachAscending(ByteSetImpl set,int size,ByteConsumer action){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  private static void forEachAscending(ByteSetImpl set,int offset,int size,ByteConsumer action){
    done:switch(offset>>6){
    case -2:
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
    case -1:
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
    case 0:
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
    default:
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  private static void forEachDescending(ByteSetImpl set,int size,ByteConsumer action){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  private static void forEachDescending(ByteSetImpl set,int offset,int size,ByteConsumer action){
    done:switch(offset>>6){
    case 1:
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
    case 0:
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
    case -1:
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
    default:
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  private static void forEachAscending(ByteSetImpl set,ByteConsumer action){
    wordForEachAscending(set.word0,Byte.MIN_VALUE,action);
    wordForEachAscending(set.word1,-64,action);
    wordForEachAscending(set.word2,0,action);
    wordForEachAscending(set.word3,64,action);
  }
  private static void forEachDescending(ByteSetImpl set,ByteConsumer action){
    wordForEachDescending(set.word3,Byte.MAX_VALUE,action);
    wordForEachDescending(set.word2,63,action);
    wordForEachDescending(set.word1,-1,action);
    wordForEachDescending(set.word0,-65,action);
  }
  private static void wordForEachDescending(long word,int inclHi,ByteConsumer action){
    for(;;--inclHi){
      final long mask;
      if(wordContains(word,mask=1L<<inclHi)){
        action.accept((byte)inclHi);
      }
      if(mask==1L){
        return;
      }
    }
  }
  private static void wordForEachAscending(long word,int inclLo,ByteConsumer action){
    for(;;++inclLo){
      final long mask;
      if(wordContains(word,mask=1L<<inclLo)){
        action.accept((byte)inclLo);
      }
      if(mask==Long.MIN_VALUE){
        return;
      }
    }
  }
  private static int countElementsAscending(ByteSetImpl set,int inclHiBound){
    switch(inclHiBound>>6){
      case -2:
        return Long.bitCount(set.word0&(-1L>>>(-inclHiBound-1)));
      case -1:
        return Long.bitCount(set.word0)
          +Long.bitCount(set.word1&(-1L>>>(-inclHiBound-1)));
      case 0:
        return Long.bitCount(set.word0)
          +Long.bitCount(set.word1)
          +Long.bitCount(set.word2&(-1L>>>(-inclHiBound-1)));
      default:
        return Long.bitCount(set.word0)
          +Long.bitCount(set.word1)
          +Long.bitCount(set.word2)
          +Long.bitCount(set.word3&(-1L>>>(-inclHiBound-1)));
    }
  }
  private static int countElementsDescending(ByteSetImpl set,int inclLoBound){
    switch(inclLoBound>>6){
      case 1:
        return Long.bitCount(set.word3&(-1L<<inclLoBound));
      case 0:
        return Long.bitCount(set.word3)
          +Long.bitCount(set.word2&(-1L<<inclLoBound));
      case -1:
        return Long.bitCount(set.word3)
          +Long.bitCount(set.word2)
          +Long.bitCount(set.word1&(-1L<<inclLoBound));
      default:
        return Long.bitCount(set.word3)
          +Long.bitCount(set.word2)
          +Long.bitCount(set.word1)
          +Long.bitCount(set.word0&(-1L<<inclLoBound));
    }
  }
  private static int countElements(ByteSetImpl set,int inclLo,int inclHi){
    switch(inclHi>>6){
      case 1:
        int count=0;
        switch(inclLo>>6){
          case -2:
            count+=Long.bitCount(set.word0&(-1L<<inclLo));
            inclLo=0;
          case -1:
            count+=Long.bitCount(set.word1&(-1L<<inclLo));
            inclLo=0;
          case 0:
            count+=Long.bitCount(set.word2&(-1L<<inclLo));
            inclLo=0;
          default:
            return count+Long.bitCount(set.word3&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        }
      case 0:
        count=0;
        switch(inclLo>>6){
          case -2:
            count+=Long.bitCount(set.word0&(-1L<<inclLo));
            inclLo=0;
          case -1:
            count+=Long.bitCount(set.word1&(-1L<<inclLo));
            inclLo=0;
          default:
            return count+Long.bitCount(set.word2&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        }
      case -1:
        count=0;
        if(inclLo<-64){
          count+=Long.bitCount(set.word0&(-1L<<inclLo));
          inclLo=0;
        }
        return count+Long.bitCount(set.word1&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
      default:
        return Long.bitCount(set.word0&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
    }
  }
  private static int hashCodeAscending(ByteSetImpl set,int size){
    int hash=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word0;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(++offset==-64){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(++offset==0){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(++offset==64){
          break;
        }
      }
      for(final var word=set.word3;;++offset){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
      }
    }
    return hash;
  }
  private static int hashCodeDescending(ByteSetImpl set,int size){
    int hash=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
      }
    }
    return hash;
  }
  private static int hashCodeDescending(ByteSetImpl set,int offset,int size){
    int hash=0;
    done:switch(offset>>6){
    case 1:
      for(final var word=set.word3;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==63){
          break;
        }
      }
    case 0:
      for(final var word=set.word2;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==-1){
          break;
        }
      }
    case -1:
      for(final var word=set.word1;;){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
        if(--offset==-65){
          break;
        }
      }
    default:
      for(final var word=set.word0;;--offset){
        if(wordContains(word,1L<<offset)){
          hash+=offset;
          if(--size==0){
            break done;
          }
        }
      }
    }
    return hash;
  }
  private static int getThisOrHigher(ByteSetImpl set){
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(set.word0))==64){
      if((tail0s+=Long.numberOfTrailingZeros(set.word1))==128){
        if((tail0s+=Long.numberOfTrailingZeros(set.word2))==192){
          tail0s+=Long.numberOfTrailingZeros(set.word3);
        }
      }
    }
    return tail0s+Byte.MIN_VALUE;
  }
  private static int getThisOrHigher(ByteSetImpl set,int val){
    switch(val>>6){
      case -2:
        if((val=Long.numberOfTrailingZeros(set.word0&(-1L<<val)))!=64){
          return val-128;
        }
        val=0;
      case -1:
        if((val=Long.numberOfTrailingZeros(set.word1&(-1L<<val)))!=64){
          return val-64;
        }
        val=0;
      case 0:
        if((val=Long.numberOfTrailingZeros(set.word2&(-1L<<val)))!=64){
          return val;
        }
        val=0;
      default:
        return Long.numberOfTrailingZeros(set.word3&(-1L<<val))+64;
    }
  }
  private static int getThisOrHigher(ByteSetImpl set,int inclHiBound,int val){
    switch(inclHiBound>>6){
      case 1:
        switch(val>>6){
          case -2:
            if((val+=Long.numberOfTrailingZeros(set.word0>>>val))<-64){
              return val;
            }
            val=-64;
          case -1:
            if((val+=Long.numberOfTrailingZeros(set.word1>>>val))<0){
              return val;
            }
            val=0;
          case 0:
            if((val+=Long.numberOfTrailingZeros(set.word2>>>val))<64){
              return val;
            }
            val=64;
          default:
            if((val+=Long.numberOfTrailingZeros(set.word3>>>val))<=inclHiBound){
              return val;
            }
            return 128;
        }
      case 0:
        switch(val>>6){
          case -2:
            if((val+=Long.numberOfTrailingZeros(set.word0>>>val))<-64){
              return val;
            }
            val=-64;
          case -1:
            if((val+=Long.numberOfTrailingZeros(set.word1>>>val))<0){
              return val;
            }
            val=0;
          default:
            if((val+=Long.numberOfTrailingZeros(set.word2>>>val))<=inclHiBound){
              return val;
            }
            return 128;
        }
      case -1:
        if(val<-64){
          if((val+=Long.numberOfTrailingZeros(set.word0>>>val))<-64){
            return val;
          }
          val=-64;
        }
        if((val+=Long.numberOfTrailingZeros(set.word1>>>val))<=inclHiBound){
          return val;
        }
        return 128;
      default:
        if((val+=Long.numberOfTrailingZeros(set.word0>>>val))<=inclHiBound){
          return val;
        }
        return 128;
    }
  }
  private static int getThisOrLower(ByteSetImpl set){
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(set.word3))==64){
      if((lead0s+=Long.numberOfLeadingZeros(set.word2))==128){
        if((lead0s+=Long.numberOfLeadingZeros(set.word1))==192){
          lead0s+=Long.numberOfLeadingZeros(set.word0);
        }
      }
    }
    return Byte.MAX_VALUE-lead0s;
  }
  private static int getThisOrLower(ByteSetImpl set,int val){
    switch(val>>6){
      case 1:
        if((val=Long.numberOfLeadingZeros(set.word3&(-1L>>>(-val-1))))!=64){
          return 127-val;
        }
        val=-1;
      case 0:
        if((val=Long.numberOfLeadingZeros(set.word2&(-1L>>>(-val-1))))!=64){
          return 63-val;
        }
        val=-1;
      case -1:
        if((val=Long.numberOfLeadingZeros(set.word1&(-1L>>>(-val-1))))!=64){
          return -1-val;
        }
        val=-1;
      default:
        return -65-Long.numberOfLeadingZeros(set.word0&(-1L>>>(-val-1)));
    }
  }
  private static int getThisOrLower(ByteSetImpl set,int inclLoBound,int val){
    switch(inclLoBound>>6){
      case -2:
        switch(val>>6){
          case 1:
            if((val-=Long.numberOfLeadingZeros(set.word3<<(-val-1)))>63){
              return val;
            }
            val=63;
          case 0:
            if((val-=Long.numberOfLeadingZeros(set.word2<<(-val-1)))>-1){
              return val;
            }
            val=-1;
          case -1:
            if((val-=Long.numberOfLeadingZeros(set.word1<<(-val-1)))>-65){
              return val;
            }
            val=-65;
          default:
            if((val-=Long.numberOfLeadingZeros(set.word0<<(-val-1)))>=inclLoBound){
              return val;
            }
            return -129;
        }
      case -1:
        switch(val>>6){
          case 1:
            if((val-=Long.numberOfLeadingZeros(set.word3<<(-val-1)))>63){
              return val;
            }
            val=63;
          case 0:
            if((val-=Long.numberOfLeadingZeros(set.word2<<(-val-1)))>-1){
              return val;
            }
            val=-1;
          default:
            if((val-=Long.numberOfLeadingZeros(set.word1<<(-val-1)))>=inclLoBound){
              return val;
            }
            return -129;
        }
      case 0:
        if(val>63){
          if((val-=Long.numberOfLeadingZeros(set.word3<<(-val-1)))>63){
            return val;
          }
          val=63;
        }
        if((val-=Long.numberOfLeadingZeros(set.word2<<(-val-1)))>=inclLoBound){
          return val;
        }
        return -129;
      default:
        if((val-=Long.numberOfLeadingZeros(set.word3<<(-val-1)))>=inclLoBound){
          return val;
        }
        return -129;
    }
  }
  @Override public void clear(){
    word0=0;
    word1=0;
    word2=0;
    word3=0;
  }
  private static long wordRemoveIf(long word,int inclHi,BytePredicate filter){
    for(;;--inclHi){
      final long mask;
      if(wordContains(word,mask=1L<<inclHi)){
        if(filter.test((byte)inclHi)){
          word-=mask;
        }
      }
      if(mask==1L){
        return word;
      }
    }
  }
  private static OmniNavigableSet.OfByte headSetAscending(ByteSetImpl.Checked set,int inclusiveTo){
    if(inclusiveTo!=Byte.MAX_VALUE){
      return new CheckedSubSet.HeadSet.Ascending(set,(set.modCountAndSize&(~0x1ff))|countElementsAscending(set,inclusiveTo),inclusiveTo);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte tailSetAscending(ByteSetImpl.Checked set,int inclusiveFrom){
    if(inclusiveFrom!=Byte.MIN_VALUE){
      return new CheckedSubSet.TailSet.Ascending(set,(set.modCountAndSize&(~0x1ff))|countElementsDescending(set,inclusiveFrom),inclusiveFrom);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte subSetAscending(ByteSetImpl.Checked set,int inclusiveFrom,int inclusiveTo){
    switch(Integer.signum(inclusiveTo+1-inclusiveFrom)){
      case 1:
        if(inclusiveFrom!=Byte.MIN_VALUE){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new CheckedSubSet.BodySet.Ascending(set,(set.modCountAndSize&(~0x1ff))|countElements(set,inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
          }else{
            return new CheckedSubSet.TailSet.Ascending(set,(set.modCountAndSize&(~0x1ff))|countElementsDescending(set,inclusiveFrom),inclusiveFrom);
          }
        }else{
          return headSetAscending(set,inclusiveTo);
        }
      case 0:
        return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
      default:
        throw new IllegalArgumentException("out of bounds");
    }
  }
  private static OmniNavigableSet.OfByte headSetDescending(ByteSetImpl.Checked set,int inclusiveTo){
    if(inclusiveTo!=Byte.MIN_VALUE){
      return new CheckedSubSet.TailSet.Descending(set,(set.modCountAndSize&(~0x1ff))|countElementsDescending(set,inclusiveTo),inclusiveTo);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte tailSetDescending(ByteSetImpl.Checked set,int inclusiveFrom){
    if(inclusiveFrom!=Byte.MAX_VALUE){
      return new CheckedSubSet.HeadSet.Descending(set,(set.modCountAndSize&(~0x1ff))|countElementsAscending(set,inclusiveFrom),inclusiveFrom);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte subSetDescending(ByteSetImpl.Checked set,int inclusiveFrom,int inclusiveTo){
    switch(Integer.signum(inclusiveFrom+1-inclusiveTo)){
      case 1:
        if(inclusiveFrom!=Byte.MAX_VALUE){
          if(inclusiveTo!=Byte.MIN_VALUE){
            return new CheckedSubSet.BodySet.Descending(set,(set.modCountAndSize&(~0x1ff))|countElements(set,inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
          }else{
            return new CheckedSubSet.HeadSet.Descending(set,(set.modCountAndSize&(~0x1ff))|countElementsAscending(set,inclusiveFrom),inclusiveFrom);
          }
        }else{
          return headSetDescending(set,inclusiveTo);
        }
      case 0:
        return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
      default:
        throw new IllegalArgumentException("out of bounds");
    }
  }
  private static OmniNavigableSet.OfByte headSetAscending(ByteSetImpl.Unchecked set,int inclusiveTo){
    if(inclusiveTo!=Byte.MAX_VALUE){
      return new UncheckedSubSet.HeadSet.Ascending(set,countElementsAscending(set,inclusiveTo),inclusiveTo);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte tailSetAscending(ByteSetImpl.Unchecked set,int inclusiveFrom){
    if(inclusiveFrom!=Byte.MIN_VALUE){
      return new UncheckedSubSet.TailSet.Ascending(set,countElementsDescending(set,inclusiveFrom),inclusiveFrom);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte subSetAscending(ByteSetImpl.Unchecked set,int inclusiveFrom,int inclusiveTo){
    if(inclusiveFrom<=inclusiveTo){
      if(inclusiveFrom!=Byte.MIN_VALUE){
        if(inclusiveTo!=Byte.MAX_VALUE){
          return new UncheckedSubSet.BodySet.Ascending(set,countElements(set,inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
        }else{
          return new UncheckedSubSet.TailSet.Ascending(set,countElementsDescending(set,inclusiveFrom),inclusiveFrom);
        }
      }else{
        return headSetAscending(set,inclusiveTo);
      }
    }else{
      return AbstractByteSet.EmptyView.ASCENDING;
    }
  }
  private static OmniNavigableSet.OfByte headSetDescending(ByteSetImpl.Unchecked set,int inclusiveTo){
    if(inclusiveTo!=Byte.MIN_VALUE){
      return new UncheckedSubSet.TailSet.Descending(set,countElementsDescending(set,inclusiveTo),inclusiveTo);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte tailSetDescending(ByteSetImpl.Unchecked set,int inclusiveFrom){
    if(inclusiveFrom!=Byte.MAX_VALUE){
      return new UncheckedSubSet.HeadSet.Descending(set,countElementsAscending(set,inclusiveFrom),inclusiveFrom);
    }else{
      return set;
    }
  }
  private static OmniNavigableSet.OfByte subSetDescending(ByteSetImpl.Unchecked set,int inclusiveFrom,int inclusiveTo){
    if(inclusiveFrom!=Byte.MAX_VALUE){
      if(inclusiveTo!=Byte.MIN_VALUE){
        return new UncheckedSubSet.BodySet.Descending(set,countElements(set,inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff)); 
      }else{
        return new UncheckedSubSet.HeadSet.Descending(set,countElementsAscending(set,inclusiveFrom),inclusiveFrom);
      }
    }else{
      return headSetDescending(set,inclusiveTo);
    }
  }
  public static abstract class Unchecked extends ByteSetImpl{
    private static final long serialVersionUID=1L;
    private Unchecked(){
      super();
    }
    private Unchecked(long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
    }
    private Unchecked(ByteSetImpl that){
      super(that);
    }
    private Unchecked(Collection<? extends Byte> that){
      super(that);
    }
    private Unchecked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    private Unchecked(OmniCollection.OfBoolean that){
      super(that);
    }
    private Unchecked(OmniCollection.OfByte that){
      super(that);
    }
    private Unchecked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    @Override public int size(){
      return SetCommonImpl.size(word0,word1,word2,word3);
    }
    @Override public boolean isEmpty(){
      return SetCommonImpl.size(word0,word1,word2,word3)==0;
    }
    @Override public Byte ceiling(byte val){
      {
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte floor(byte val){
      {
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte higher(byte val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=getThisOrHigher(this,1+(int)(val)))!=129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte lower(byte val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=getThisOrLower(this,-1+(int)(val)))!=-129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public byte byteCeiling(byte val){
      {
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      {
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=getThisOrHigher(this,1+(int)(val)))!=129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte lowerByte(byte val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=getThisOrLower(this,-1+(int)(val)))!=-129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      {
        if(val<=Byte.MAX_VALUE)
        {
          if((val=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          if((val=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      {
        if(val<Byte.MAX_VALUE)
        {
          if((val=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int lowerInt(int val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          if((val=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long lowerLong(long val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public float floatCeiling(float val){
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.intCeiling(val)):getThisOrHigher(this))!=128){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.intFloor(val)):getThisOrLower(this)))!=-129){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.higherInt(val)):getThisOrHigher(this))!=128){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float lowerFloat(float val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.lowerInt(val)):getThisOrLower(this)))!=-129){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public double doubleCeiling(double val){
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.intCeiling(val)):getThisOrHigher(this))!=128){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.intFloor(val)):getThisOrLower(this)))!=-129){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.higherInt(val)):getThisOrHigher(this))!=128){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double lowerDouble(double val){
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.lowerInt(val)):getThisOrLower(this)))!=-129){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    private static int hashCodeForWord(long word,int inclLo,int exclHi){
      for(int hash=0;;){
        if(wordContains(word,1L<<inclLo)){
          hash+=inclLo;
        }
        if(++inclLo==exclHi){
          return hash;
        }
      }
    }
    @Override public boolean removeIf(BytePredicate filter){
      long word;
      return (word=this.word0)!=(this.word0=wordRemoveIf(word,-65,filter))
        |(word=this.word1)!=(this.word1=wordRemoveIf(word,-1,filter))
        |(word=this.word2)!=(this.word2=wordRemoveIf(word,63,filter))
        |(word=this.word3)!=(this.word3=wordRemoveIf(word,Byte.MAX_VALUE,filter));
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return removeIf((BytePredicate)filter::test);
    }
    @Override public int hashCode(){
      return hashCodeForWord(word0,-128,-64)
           + hashCodeForWord(word1,-64,0)
           + hashCodeForWord(word2,0,64)
           + hashCodeForWord(word3,64,128);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      out.writeLong(word0);
      out.writeLong(word1);
      out.writeLong(word2);
      out.writeLong(word3);
    }
    @Override public void readExternal(ObjectInput in) throws IOException{
      word0=in.readLong();
      word1=in.readLong();
      word2=in.readLong();
      word3=in.readLong();
    }
    @Override public int pollFirstInt(){
      {
        returnNotFound:for(;;)
        {
          int tail0s;
          for(;;){
            long word;
            if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
              this.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
              break;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
              this.word1=word&(~(1L<<(tail0s-=64)));
              break;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
              this.word2=word&(~(1L<<(tail0s)));
              break;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
              this.word2=word&(~(1L<<(tail0s+=64)));
              break;
            }
            break returnNotFound;
          }
          return tail0s;
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      {
        returnNotFound:for(;;)
        {
          int lead0s;
          for(;;){
            long word;
            if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
              this.word3=word&(~(1L<<(lead0s=Byte.MAX_VALUE-lead0s)));
              break;
            }
            if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
              this.word2=word&(~(1L<<(lead0s=63-lead0s)));
              break;
            }
            if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
              this.word1=word&(~(1L<<(lead0s=-1-lead0s)));
              break;
            }
            if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
              this.word0=word&(~(1L<<(lead0s=-65-lead0s)));
              break;
            }
            break returnNotFound;
          }
          return lead0s;
        }
      }
      return Integer.MIN_VALUE;
    }
    public static class Ascending extends Unchecked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Ascending(){
        super();
      }
      Ascending(long word0,long word1,long word2,long word3){
        super(word0,word1,word2,word3);
      }
      public Ascending(ByteSetImpl that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Ascending(OmniCollection.OfByte that){
        super(that);
      }
      public Ascending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override public String toString(){
        return toStringAscending(this);
      }
      @Override public int firstInt(){
        return getThisOrHigher(this);
      }
      @Override public int lastInt(){
        return getThisOrLower(this);
      }
      @Override public void forEach(ByteConsumer action){
        forEachAscending(this,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        forEachAscending(this,action::accept);
      }
      @Override public byte[] toByteArray(){
        return toByteArrayAscending(this);
      }
      @Override public short[] toShortArray(){
        return toShortArrayAscending(this);
      }
      @Override public int[] toIntArray(){
        return toIntArrayAscending(this);
      }
      @Override public long[] toLongArray(){
        return toLongArrayAscending(this);
      }
      @Override public float[] toFloatArray(){
        return toFloatArrayAscending(this);
      }
      @Override public double[] toDoubleArray(){
        return toDoubleArrayAscending(this);
      }
      @Override public Byte[] toArray(){
        return toArrayAscending(this);
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return toArrayAscending(this,arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return toArrayAscending(this,dst);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetAscending(this,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return headSetAscending(this,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetAscending(this,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return tailSetAscending(this,inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetAscending(this,(int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          ++inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          --inclusiveTo;
        }
        return subSetAscending(this,inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new UncheckedFullView.Descending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
    public static class Descending extends Unchecked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
      }
      Descending(long word0,long word1,long word2,long word3){
        super(word0,word1,word2,word3);
      }
      public Descending(ByteSetImpl that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override public String toString(){
        return toStringDescending(this);
      }
      @Override public int firstInt(){
        return getThisOrLower(this);
      }
      @Override public int lastInt(){
        return getThisOrHigher(this);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public void forEach(ByteConsumer action){
        forEachDescending(this,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        forEachDescending(this,action::accept);
      }
      @Override public byte[] toByteArray(){
        return toByteArrayDescending(this);
      }
      @Override public short[] toShortArray(){
        return toShortArrayDescending(this);
      }
      @Override public int[] toIntArray(){
        return toIntArrayDescending(this);
      }
      @Override public long[] toLongArray(){
        return toLongArrayDescending(this);
      }
      @Override public float[] toFloatArray(){
        return toFloatArrayDescending(this);
      }
      @Override public double[] toDoubleArray(){
        return toDoubleArrayDescending(this);
      }
      @Override public Byte[] toArray(){
        return toArrayDescending(this);
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return toArrayDescending(this,arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return toArrayDescending(this,dst);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return headSetDescending(this,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          --inclusiveFrom;
        }
        if(inclusiveFrom!=-129){
          return tailSetDescending(this,inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          --inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          ++inclusiveTo;
        }
        if(inclusiveFrom>=inclusiveTo){
          return subSetDescending(this,inclusiveFrom,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetDescending(this,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetDescending(this,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetDescending(this,(int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new UncheckedFullView.Ascending(this);
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  public static abstract class Checked extends ByteSetImpl{
    private static final long serialVersionUID=1L;
    transient int modCountAndSize;
    private Checked(){
      super();
    }
    private Checked(int modCountAndSize,long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
      this.modCountAndSize=modCountAndSize;
    }
    private Checked(ByteSetImpl.Unchecked that){
      super(that);
      this.modCountAndSize=SetCommonImpl.size(word0,word1,word2,word3);
    }
    private Checked(ByteSetImpl.Checked that){
      super(that);
      this.modCountAndSize=that.modCountAndSize;
    }
    private Checked(Collection<? extends Byte> that){
      super(that);
    }
    private Checked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    private Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    private Checked(OmniCollection.OfByte that){
      super(that);
    }
    private Checked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    @Override public int size(){
      return this.modCountAndSize&0x1ff;
    }
    @Override public boolean isEmpty(){
      return (this.modCountAndSize&0x1ff)==0;
    }
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=(modCountAndSize+(1<<9))&(~0x1ff);
        super.clear();
      }
    }
    @Override public boolean contains(long val){
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(this,val);
    }
    @Override public boolean contains(float val){
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(this,val);
    }
    @Override public boolean contains(double val){
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(this,val);
    }
    @Override public boolean contains(Object val){
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(this,val);
    }
    @Override public boolean removeVal(boolean val){
      if(ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      if(ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      if(ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(this,val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0  && (size=removeIfImplStartWord3Descending(this,Byte.MAX_VALUE,size,filter,modCountAndSize))!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-size);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0  && (size=removeIfImplStartWord3Descending(this,Byte.MAX_VALUE,size,filter::test,modCountAndSize))!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-size);
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
    @Override public boolean add(byte val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        return hashCodeDescending(this,size);
      }
      return 0;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCountAndSize=this.modCountAndSize;
      try{
        int size;
        out.writeShort(size=modCountAndSize&0x1ff);
        if(size!=0){
          long word;
          out.writeLong(word=this.word2);
          if((size-=Long.bitCount(word))!=0){
            out.writeLong(word=this.word3);
            if((size-=Long.bitCount(word))!=0){
              out.writeLong(word=this.word1);
              if((size-Long.bitCount(word))!=0){
                out.writeLong(word0);
              }
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException{
      int size;
      this.modCountAndSize=size=in.readUnsignedShort();
      if(size!=0){
        long word;
        this.word2=word=in.readLong();
        if((size-=Long.bitCount(word))!=0){
          this.word3=word=in.readLong();
          if((size-=Long.bitCount(word))!=0){
            this.word1=word=in.readLong();
            if((size-Long.bitCount(word))!=0){
              this.word0=in.readLong();
            }
          }
        }
      }
    }
    abstract void forEachImpl(int size,ByteConsumer action);
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      final int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          forEachImpl(size,action);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize;
      final int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          forEachImpl(size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    abstract int toStringImpl(int size,byte[] buffer);
    @Override public String toString(){
      int modCountAndSize;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        final byte[] buffer;
        (buffer=new byte[modCountAndSize*6])[0]='[';
        buffer[modCountAndSize=toStringImpl(modCountAndSize,buffer)]=']';
        return new String(buffer,0,modCountAndSize+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    @Override public int firstInt(){
      if((modCountAndSize&0x1ff)!=0){
        return getThisOrHigher(this);
      }
      throw new NoSuchElementException();
    }
    @Override public int lastInt(){
      if((modCountAndSize&0x1ff)!=0){
        return getThisOrLower(this);
      }
      throw new NoSuchElementException();
    }
    @Override public Byte ceiling(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte floor(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte higher(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=getThisOrHigher(this,1+(int)(val)))!=129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public Byte lower(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=getThisOrLower(this,-1+(int)(val)))!=-129){
            return (byte)v;
          }
        }
      }
      return null;
    }
    @Override public byte byteCeiling(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=getThisOrHigher(this,1+(int)(val)))!=129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte lowerByte(byte val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=getThisOrLower(this,-1+(int)(val)))!=-129){
            return (byte)v;
          }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (short)v;
          }
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<=Byte.MAX_VALUE)
        {
          if((val=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          if((val=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          if((val=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int lowerInt(int val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          if((val=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (int)val;
          }
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,(int)(val)):getThisOrHigher(this))!=128){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,(int)(val)):getThisOrLower(this)))!=-129){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,1+(int)(val)):getThisOrHigher(this))!=128){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long lowerLong(long val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,-1+(int)(val)):getThisOrLower(this)))!=-129){
            return (long)v;
          }
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public float floatCeiling(float val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.intCeiling(val)):getThisOrHigher(this))!=128){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.intFloor(val)):getThisOrLower(this)))!=-129){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.higherInt(val)):getThisOrHigher(this))!=128){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public float lowerFloat(float val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.lowerInt(val)):getThisOrLower(this)))!=-129){
            return (float)v;
          }
        }
      }
      return Float.NaN;
    }
    @Override public double doubleCeiling(double val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.intCeiling(val)):getThisOrHigher(this))!=128){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.intFloor(val)):getThisOrLower(this)))!=-129){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(val<Byte.MAX_VALUE)
        {
          final int v;
          if((v=val>=Byte.MIN_VALUE?getThisOrHigher(this,TypeUtil.higherInt(val)):getThisOrHigher(this))!=128){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public double lowerDouble(double val){
      if((modCountAndSize&0x1ff)!=0)
      {
        if(!(val<=(Byte.MIN_VALUE)))
        {
          final int v;
          if((v=(val<=Byte.MAX_VALUE?getThisOrLower(this,TypeUtil.lowerInt(val)):getThisOrLower(this)))!=-129){
            return (double)v;
          }
        }
      }
      return Double.NaN;
    }
    @Override public int pollFirstInt(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0)  
      {
        {
          int tail0s;
          for(;;){
            long word;
            if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
              this.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
              break;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
              this.word1=word&(~(1L<<(tail0s-=64)));
              break;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
              this.word2=word&(~(1L<<(tail0s)));
              break;
            }
            this.word3=(word=this.word3)&(~(1L<<(tail0s=Long.numberOfTrailingZeros(word)+64)));
            break;
          }
          this.modCountAndSize=modCountAndSize+((1<<9)-1);
          return tail0s;
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0)  
      {
        {
          int lead0s;
          for(;;){
            long word;
            if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
              this.word3=word&(~(1L<<(lead0s=Byte.MAX_VALUE-lead0s)));
              break;
            }
            if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
              this.word2=word&(~(1L<<(lead0s=63-lead0s)));
              break;
            }
            if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
              this.word1=word&(~(1L<<(lead0s=-1-lead0s)));
              break;
            }
            this.word0=(word=this.word0)&(~(1L<<(lead0s=-65-Long.numberOfLeadingZeros(word))));
            break;
          }
          this.modCountAndSize=modCountAndSize+((1<<9)-1);
          return lead0s;
        }
      }
      return Integer.MIN_VALUE;
    }
    public static class Ascending extends Checked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Ascending(){
        super();
      }
      Ascending(int modCountAndSize,long word0,long word1,long word2,long word3){
        super(modCountAndSize,word0,word1,word2,word3);
      }
      public Ascending(ByteSetImpl.Unchecked that){
        super(that);
      }
      public Ascending(ByteSetImpl.Checked that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Ascending(OmniCollection.OfByte that){
        super(that);
      }
      public Ascending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override void forEachImpl(int size,ByteConsumer action){
        forEachAscending(this,size,action);
      }
      @Override public byte[] toByteArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          copyToArrayAscending(this,size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          copyToArrayAscending(this,size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          copyToArrayAscending(this,size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          copyToArrayAscending(this,size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          copyToArrayAscending(this,size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          copyToArrayAscending(this,size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          copyToArrayAscending(this,size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int modCountAndSize=this.modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(size!=0){
          copyToArrayAscending(this,size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          copyToArrayAscending(this,size,dst);
        }
        return dst;
      }
      @Override int toStringImpl(int size,byte[] buffer){
        return toStringAscending(this,size,buffer);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return headSetAscending(this,inclusiveTo);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return tailSetAscending(this,inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetAscending(this,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetAscending(this,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          ++inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          --inclusiveTo;
        }
        return subSetAscending(this,inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetAscending(this,(int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new CheckedFullView.Descending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
    public static class Descending extends Checked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
      }
      Descending(int modCountAndSize,long word0,long word1,long word2,long word3){
        super(modCountAndSize,word0,word1,word2,word3);
      }
      public Descending(ByteSetImpl.Unchecked that){
        super(that);
      }
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override void forEachImpl(int size,ByteConsumer action){
        forEachDescending(this,size,action);
      }
      @Override public byte[] toByteArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          copyToArrayDescending(this,size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          copyToArrayDescending(this,size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          copyToArrayDescending(this,size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          copyToArrayDescending(this,size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          copyToArrayDescending(this,size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          copyToArrayDescending(this,size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          copyToArrayDescending(this,size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int modCountAndSize=this.modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(size!=0){
          copyToArrayDescending(this,size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          copyToArrayDescending(this,size,dst);
        }
        return dst;
      }
      @Override int toStringImpl(int size,byte[] buffer){
        return toStringDescending(this,size,buffer);
      }
      @Override public int firstInt(){
        return super.lastInt();
      }
      @Override public int lastInt(){
        return super.firstInt();
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return headSetDescending(this,inclusiveTo);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveTo);
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          --inclusiveFrom;
        }
        if(inclusiveFrom!=-129){
          return tailSetDescending(this,inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetDescending(this,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetDescending(this,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          --inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          ++inclusiveTo;
        }
        return subSetDescending(this,inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetDescending(this,(int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new CheckedFullView.Ascending(this);
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static abstract class UncheckedFullView extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Unchecked root;
    private UncheckedFullView(ByteSetImpl.Unchecked root){
      this.root=root;
    }
    @Override public int firstInt(){
      return root.lastInt();
    }
    @Override public int lastInt(){
      return root.firstInt();
    }
    @Override public Byte ceiling(byte val){
      return root.floor(val);
    }
    @Override public Byte floor(byte val){
      return root.ceiling(val);
    }
    @Override public Byte higher(byte val){
      return root.lower(val);
    }
    @Override public Byte lower(byte val){
      return root.higher(val);
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public int size(){
      final ByteSetImpl.Unchecked root;
      return SetCommonImpl.size((root=this.root).word0,root.word1,root.word2,root.word3);
    }
    @Override public boolean isEmpty(){
      final ByteSetImpl.Unchecked root;
      return SetCommonImpl.size((root=this.root).word0,root.word1,root.word2,root.word3)==0;
    }
    @Override public void clear(){
      root.clear();
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public boolean contains(boolean val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(byte val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(char val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(int val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(long val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(float val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(double val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(Object val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean removeVal(boolean val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(byte val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(char val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(int val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(long val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(float val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean removeVal(double val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public boolean remove(Object val){
      return ByteSetImpl.removeVal(root,val);
    }
    @Override public int pollFirstInt(){
      return root.pollFirstInt();
    }
    @Override public int pollLastInt(){
      return root.pollLastInt();
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf(filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf((BytePredicate)filter::test);
    }
    private static class Ascending extends UncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Unchecked.Descending root){
        super(root);
      }
      @Override public String toString(){
        return toStringAscending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Unchecked.Ascending(this.root);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetAscending(root,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return headSetAscending(root,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetAscending(root,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return tailSetAscending(root,inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetAscending(root,(int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          ++inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          --inclusiveTo;
        }
        return subSetAscending(root,inclusiveFrom,inclusiveTo);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Ascending(this.root);
      }
      @Override public byte[] toByteArray(){
        return toByteArrayAscending(root);
      }
      @Override public short[] toShortArray(){
        return toShortArrayAscending(root);
      }
      @Override public int[] toIntArray(){
        return toIntArrayAscending(root);
      }
      @Override public long[] toLongArray(){
        return toLongArrayAscending(root);
      }
      @Override public float[] toFloatArray(){
        return toFloatArrayAscending(root);
      }
      @Override public double[] toDoubleArray(){
        return toDoubleArrayAscending(root);
      }
      @Override public Byte[] toArray(){
        return toArrayAscending(root);
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return toArrayAscending(root,arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return toArrayAscending(root,dst);
      }
      @Override public void forEach(ByteConsumer action){
        forEachAscending(root,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        forEachAscending(root,action::accept);
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
    private static class Descending extends UncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Unchecked.Ascending root){
        super(root);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public String toString(){
        return toStringDescending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Unchecked.Descending(this.root);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return headSetDescending(root,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          --inclusiveFrom;
        }
        if(inclusiveFrom!=-129){
          return tailSetDescending(root,inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          --inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          ++inclusiveTo;
        }
        if(inclusiveFrom>=inclusiveTo){
          return subSetDescending(root,inclusiveFrom,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetDescending(root,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetDescending(root,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetDescending(root,(int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Descending(this.root);
      }
      @Override public byte[] toByteArray(){
        return toByteArrayDescending(root);
      }
      @Override public short[] toShortArray(){
        return toShortArrayDescending(root);
      }
      @Override public int[] toIntArray(){
        return toIntArrayDescending(root);
      }
      @Override public long[] toLongArray(){
        return toLongArrayDescending(root);
      }
      @Override public float[] toFloatArray(){
        return toFloatArrayDescending(root);
      }
      @Override public double[] toDoubleArray(){
        return toDoubleArrayDescending(root);
      }
      @Override public Byte[] toArray(){
        return toArrayDescending(root);
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return toArrayDescending(root,arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return toArrayDescending(root,dst);
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public void forEach(ByteConsumer action){
        forEachDescending(root,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        forEachDescending(root,action::accept);
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static abstract class CheckedFullView extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Checked root;
    private CheckedFullView(ByteSetImpl.Checked root){
      this.root=root;
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public int size(){
      return root.modCountAndSize&0x1ff;
    }
    @Override public boolean isEmpty(){
      return (root.modCountAndSize&0x1ff)==0;
    }
    @Override public void clear(){
      root.clear();
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public boolean contains(boolean val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(byte val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(char val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(int val){
      return ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(long val){
      final ByteSetImpl.Checked root;
      return ((root=this.root).modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(float val){
      final ByteSetImpl.Checked root;
      return ((root=this.root).modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(double val){
      final ByteSetImpl.Checked root;
      return ((root=this.root).modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(Object val){
      final ByteSetImpl.Checked root;
      return ((root=this.root).modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,val);
    }
    @Override public boolean removeVal(boolean val){
      final ByteSetImpl.Checked root;
      if(ByteSetImpl.removeVal(root=this.root,val)){
        root.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final ByteSetImpl.Checked root;
      if(ByteSetImpl.removeVal(root=this.root,val)){
        root.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final ByteSetImpl.Checked root;
      if(ByteSetImpl.removeVal(root=this.root,val)){
        root.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final ByteSetImpl.Checked root;
      if(ByteSetImpl.removeVal(root=this.root,val)){
        root.modCountAndSize+=((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0 && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      final ByteSetImpl.Checked root;
      if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0  && (size=removeIfImplStartWord3Descending(root,Byte.MAX_VALUE,size,filter,modCountAndSize))!=0){
        root.modCountAndSize=modCountAndSize+((1<<9)-size);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      int size;
      final ByteSetImpl.Checked root;
      if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0  && (size=removeIfImplStartWord3Descending(root,Byte.MAX_VALUE,size,filter::test,modCountAndSize))!=0){
        root.modCountAndSize=modCountAndSize+((1<<9)-size);
        return true;
      }
      return false;
    }
    private static class SerializationIntermediateBase implements Serializable{
      private static final long serialVersionUID=1L;
      transient final ByteSetImpl.Checked root;
      private SerializationIntermediateBase(ByteSetImpl.Checked root){
        this.root=root;
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        root.writeExternal(oos);
      }
      private void readObject(ObjectInputStream ois) throws IOException{
        root.readExternal(ois);
      }
    }
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public Byte ceiling(byte val){
      return root.floor(val);
    }
    @Override public Byte floor(byte val){
      return root.ceiling(val);
    }
    @Override public Byte higher(byte val){
      return root.lower(val);
    }
    @Override public Byte lower(byte val){
      return root.higher(val);
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public int pollFirstInt(){
      return root.pollFirstInt();
    }
    @Override public int pollLastInt(){
      return root.pollLastInt();
    }
    private static class Ascending extends CheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Checked.Descending root){
        super(root);
      }
      @Override public String toString(){
        final ByteSetImpl.Checked root;
        int modCountAndSize;
        if((modCountAndSize=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          (buffer=new byte[modCountAndSize*6])[0]='[';
          buffer[modCountAndSize=toStringAscending(root,modCountAndSize,buffer)]=']';
          return new String(buffer,0,modCountAndSize+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            forEachAscending(root,size,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            forEachAscending(root,size,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public Object clone(){
        return new ByteSetImpl.Checked.Ascending(this.root);
      }
      @Override public int firstInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return getThisOrHigher(root);
        }
        throw new NoSuchElementException();
      }
      @Override public int lastInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return getThisOrLower(root);
        }
        throw new NoSuchElementException();
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return headSetAscending(root,inclusiveTo);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return tailSetAscending(root,inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetAscending(root,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetAscending(root,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          ++inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          --inclusiveTo;
        }
        return subSetAscending(root,inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetAscending(root,(int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new SerializationIntermediate(this.root);
      }
      @Override public byte[] toByteArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          copyToArrayAscending(root,size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final short[] dst;
          copyToArrayAscending(root,size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final int[] dst;
          copyToArrayAscending(root,size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final long[] dst;
          copyToArrayAscending(root,size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final float[] dst;
          copyToArrayAscending(root,size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final double[] dst;
          copyToArrayAscending(root,size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          copyToArrayAscending(root,size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
        if(size!=0){
          copyToArrayAscending(root,size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          copyToArrayAscending(root,size,dst);
        }
        return dst;
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      private static class SerializationIntermediate extends SerializationIntermediateBase{
        private static final long serialVersionUID=1L;
        private SerializationIntermediate(ByteSetImpl.Checked root){
          super(root);
        }
        private Object readResolve(){
          return new ByteSetImpl.Checked.Ascending(this.root);
        }
      } 
    }
    private static class Descending extends CheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Checked.Ascending root){
        super(root);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public String toString(){
        final ByteSetImpl.Checked root;
        int modCountAndSize;
        if((modCountAndSize=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          (buffer=new byte[modCountAndSize*6])[0]='[';
          buffer[modCountAndSize=toStringDescending(root,modCountAndSize,buffer)]=']';
          return new String(buffer,0,modCountAndSize+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            forEachDescending(root,size,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            forEachDescending(root,size,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public Object clone(){
        return new ByteSetImpl.Checked.Descending(this.root);
      }
      @Override public int firstInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return getThisOrLower(root);
        }
        throw new NoSuchElementException();
      }
      @Override public int lastInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return getThisOrHigher(root);
        }
        throw new NoSuchElementException();
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return headSetDescending(root,inclusiveTo);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveTo);
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          --inclusiveFrom;
        }
        if(inclusiveFrom!=-129){
          return tailSetDescending(root,inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return headSetDescending(root,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return tailSetDescending(root,(int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        int inclusiveFrom=fromElement;
        if(!fromInclusive){
          --inclusiveFrom;
        }
        int inclusiveTo=toElement;
        if(!toInclusive){
          ++inclusiveTo;
        }
        return subSetDescending(root,inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return subSetDescending(root,(int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new SerializationIntermediate(this.root);
      }
      @Override public byte[] toByteArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          copyToArrayDescending(root,size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final short[] dst;
          copyToArrayDescending(root,size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final int[] dst;
          copyToArrayDescending(root,size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final long[] dst;
          copyToArrayDescending(root,size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final float[] dst;
          copyToArrayDescending(root,size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final double[] dst;
          copyToArrayDescending(root,size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          copyToArrayDescending(root,size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
        if(size!=0){
          copyToArrayDescending(root,size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          copyToArrayDescending(root,size,dst);
        }
        return dst;
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      private static class SerializationIntermediate extends SerializationIntermediateBase{
        private static final long serialVersionUID=1L;
        private SerializationIntermediate(ByteSetImpl.Checked root){
          super(root);
        }
        private Object readResolve(){
          return new ByteSetImpl.Checked.Descending(this.root);
        }
      } 
    }
  }
  private static abstract class UncheckedSubSet extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Unchecked root;
    transient final UncheckedSubSet parent;
    transient int size;
    private UncheckedSubSet(ByteSetImpl.Unchecked root,int size){
      super();
      this.root=root;
      this.parent=null;
      this.size=size;
    }
    private UncheckedSubSet(UncheckedSubSet parent,int size){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.size=size;
    }
    abstract void clearImpl();
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        clearImpl();
        this.size=0;
        bubbleUpModifySize(-size);
      }
    }
    private void bubbleUpDecrementSize(){
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        --parent.size;
      }
    }
    private void bubbleUpModifySize(int delta){
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size+=delta;
      }
    }
    private void bubbleUpIncrementSize(){
      var curr=this;
      do{
        ++curr.size;
      }while((curr=curr.parent)!=null);
    }
    @Override public boolean add(boolean val){
      if(root.add(val)){
        bubbleUpIncrementSize();
        return true;
      }
      return false;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public boolean add(byte val){
      if(root.add(val)){
        bubbleUpIncrementSize();
        return true;
      }
      return false;
    }
    abstract void copyToArray(int size,byte[] dst);
    @Override public byte[] toByteArray(){
      final int size;
      if((size=this.size)!=0){
        final byte[] dst;
        copyToArray(size,dst=new byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,short[] dst);
    @Override public short[] toShortArray(){
      final int size;
      if((size=this.size)!=0){
        final short[] dst;
        copyToArray(size,dst=new short[size]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,int[] dst);
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        copyToArray(size,dst=new int[size]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,long[] dst);
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        copyToArray(size,dst=new long[size]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,float[] dst);
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        copyToArray(size,dst=new float[size]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,double[] dst);
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        copyToArray(size,dst=new double[size]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    abstract void copyToArray(int size,Byte[] dst);
    @Override public Byte[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Byte[] dst;
        copyToArray(size,dst=new Byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    abstract void copyToArray(int size,Object[] dst);
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      final int size;
      dst=arrConstructor.apply(size=this.size);
      if(size!=0){
        copyToArray(size,dst);
      }
      return dst;
    }
    @Override public <T> T[] toArray(T[] dst){
      final int size;
      if((size=this.size)!=0){
        copyToArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    abstract int toStringImpl(int size,byte[] buffer);
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        buffer[size=toStringImpl(size,buffer)]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    abstract void forEachImpl(int size,ByteConsumer action);
    @Override public void forEach(ByteConsumer action){
      final int size;
      if((size=this.size)!=0){
        forEachImpl(size,action);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int size;
      if((size=this.size)!=0){
        forEachImpl(size,action::accept);
      }
    }
    abstract long isInRange(boolean val);
    abstract boolean isInRange(int val);
    abstract int isInRange(long val);
    abstract int isInRange(float val);
    abstract int isInRange(double val);
    @Override public boolean contains(boolean val){
      return this.size!=0 && wordContains(root.word2,isInRange(val));
    }
    @Override public boolean contains(byte val){
      return this.size!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(char val){
      return this.size!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(int val){
      return this.size!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(long val){
      return this.size!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(float val){
      return this.size!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(double val){
      return this.size!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(Object val){
      if(this.size!=0){
        for(;;){
          final int v;
          if(val instanceof Byte){
            v=(byte)val;
          }else if(val instanceof Integer || val instanceof Short){
            v=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(int)l)){
              break;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(int)f)){
              break;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(int)d)){
              break;
            }
          }else if(val instanceof Character){
            v=(char)val;
          }else if(val instanceof Boolean){
            return wordContains(root.word2,isInRange((boolean)val));
          }else{
            break;
          }
          return isInRange(v) && ByteSetImpl.contains(root,v);
        }
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      final int size;
      final ByteSetImpl.Unchecked root;
      long word;
      if((size=this.size)!=0 && (word=(root=this.root).word2)!=(word&=~isInRange(val))){
        root.word2=word;
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int size;
      if((size=this.size)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int size;
      if((size=this.size)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int size;
      if((size=this.size)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        this.size=size-1;
        bubbleUpDecrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int size;
      if((size=this.size)!=0){
        returnFalse:for(;;){
          returnTrue:for(;;){
            final int v;
            if(val instanceof Byte){
              v=(byte)val;
            }else if(val instanceof Integer || val instanceof Short){
              v=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(v=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(v=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(v=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              v=(char)val;
            }else if(val instanceof Boolean){
              long word;
              final ByteSetImpl.Unchecked root;
              if((word=(root=this.root).word2)!=(word&=~isInRange((boolean)val))){
                root.word2=word;
                break returnTrue;
              }
              break returnFalse;
            }else{
              break returnFalse;
            }
            if(isInRange(v) && ByteSetImpl.removeVal(root,v)){
              break returnTrue;
            }
            break returnFalse;
          }
          this.size=size-1;
          bubbleUpDecrementSize();
          return true;
        }
      }
      return false;
    }
    abstract int removeIfImpl(int size,BytePredicate filter);
    @Override public boolean removeIf(BytePredicate filter){
      final int size,numRemoved;
      if((size=this.size)!=0 && (numRemoved=removeIfImpl(size,filter))!=0){
        this.size=size-numRemoved;
        bubbleUpModifySize(-numRemoved);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size,numRemoved;
      if((size=this.size)!=0 && (numRemoved=removeIfImpl(size,filter::test))!=0){
        this.size=size-numRemoved;
        bubbleUpModifySize(-numRemoved);
        return true;
      }
      return false;
    }
    private static abstract class TailSet extends UncheckedSubSet{
      transient final int inclusiveLo;
      private TailSet(ByteSetImpl.Unchecked root,int size,int inclusiveLo){
        super(root,size);
        this.inclusiveLo=inclusiveLo;
      }
      private TailSet(TailSet parent,int size,int inclusiveLo){
        super(parent,size);
        this.inclusiveLo=inclusiveLo;
      }
      @Override void clearImpl(){
        clearTailSet(root,inclusiveLo);
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        final ByteSetImpl root;
        int numRemoved=(size=word3RemoveIfDescending(root=this.root,Byte.MAX_VALUE,size,filter))&0x1ff;
        if(size>=1<<9){
          numRemoved+=((size=word2RemoveIfDescending(root,63,size>>9,filter))&0x1ff);
          if(size>=1<<9){
            numRemoved+=((size=word1RemoveIfDescending(root,-1,size>>9,filter))&0x1ff);
            if(size>=1<<9){
              numRemoved+=word0RemoveIfDescending(root,-65,size>>9,filter);
            }
          }
        }
        return numRemoved;
      }
      @Override long isInRange(boolean val){
        if(val){
          if(1>=inclusiveLo){
            return 2L;
          }
        }else{
          if(0>=inclusiveLo){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        return val>=inclusiveLo;
      }
      @Override int isInRange(long val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return hashCodeDescending(root,size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        final int inclusiveFrom;
        if((inclusiveFrom=this.inclusiveLo)<=inclusiveTo){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          if(this.inclusiveLo!=inclusiveFrom){
            return new UncheckedSubSet.TailSet.Ascending(this,countElementsDescending(root,inclusiveFrom),inclusiveFrom);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        final int inclusiveTo;
        if((inclusiveTo=this.inclusiveLo)<=inclusiveFrom){
          if(inclusiveFrom!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          if(inclusiveFrom!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          if(this.inclusiveLo!=inclusiveTo){
            return new UncheckedSubSet.TailSet.Descending(this,countElementsDescending(root,inclusiveTo),inclusiveTo);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        if(inclusiveTo!=this.inclusiveLo){
          return new UncheckedSubSet.TailSet.Descending(this,countElementsDescending(root,inclusiveTo),inclusiveTo);
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        if(inclusiveFrom!=this.inclusiveLo){
          return new UncheckedSubSet.TailSet.Ascending(this,countElementsDescending(root,inclusiveFrom),inclusiveFrom);
        }
        return this;
      }
      @Override public Byte ceiling(byte val){
        if(this.size!=0)
        {
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=getThisOrLower(root,inclusiveLo,-1+(int)(val)))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        if(this.size!=0)
        {
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=getThisOrLower(root,inclusiveLo,-1+(int)(val)))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        if(this.size!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        if(this.size!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            if((val=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
             if((val=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            if((val=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
             if((val=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        if(this.size!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        if(this.size!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?TypeUtil.intCeiling(val):inclusiveLo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.intFloor(val)):getThisOrLower(root)))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?TypeUtil.higherInt(val):inclusiveLo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.lowerInt(val)):getThisOrLower(root)))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        if(this.size!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?TypeUtil.intCeiling(val):inclusiveLo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.intFloor(val)):getThisOrLower(root)))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        if(this.size!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?TypeUtil.higherInt(val):inclusiveLo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.lowerInt(val)):getThisOrLower(root)))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int tail0s;
            switch((tail0s=this.inclusiveLo)>>6){  
              case -2:
                long word;
                if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<tail0s)))!=64){
                  root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                  break;
                }
              case -1:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<tail0s)))!=64){
                  root.word1=word&(~(1L<<(tail0s-=64)));
                  break;
                }
              case 0:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<tail0s)))!=64){
                  root.word2=word&(~(1L<<(tail0s)));
                  break; 
                }
              default:
                root.word3=(word=root.word3)&(~(1L<<(tail0s=64+Long.numberOfTrailingZeros(word&(-1L<<tail0s)))));
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int lead0s;
            for(;;){
              long word;
              if((lead0s=Long.numberOfLeadingZeros(word=root.word3))!=64){
                root.word3=word&(~(1L<<(lead0s=Byte.MAX_VALUE-lead0s)));
                break;
              }
              if((lead0s=Long.numberOfLeadingZeros(word=root.word2))!=64){
                root.word2=word&(~(1L<<(lead0s=63-lead0s)));
                break;
              }
              if((lead0s=Long.numberOfLeadingZeros(word=root.word1))!=64){
                root.word1=word&(~(1L<<(lead0s=-1-lead0s)));
                break;
              }
              root.word0=(word=root.word0)&(~(1L<<(lead0s=-65-Long.numberOfLeadingZeros(word))));
              break;
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int inclusiveLo){
          super(root,size,inclusiveLo);
        }
        private Ascending(TailSet parent,int size,int inclusiveLo){
          super(parent,size,inclusiveLo);
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachAscending(root,inclusiveLo,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringAscending(root,inclusiveLo,size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrHigher(root,this.inclusiveLo);
        }
        @Override public int lastInt(){
          return getThisOrLower(root);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          return super.headSetAscending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          if(inclusiveFrom!=Byte.MAX_VALUE+1){
            return super.tailSetAscending(inclusiveFrom);
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetAscending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetAscending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.TailSet.Descending(this,size,inclusiveLo);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Unchecked root,int size,int inclusiveLo){
          super(root,size,inclusiveLo);
        }
        private Descending(TailSet parent,int size,int inclusiveLo){
          super(parent,size,inclusiveLo);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachDescending(root,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringDescending(root,size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrLower(root);
        }
        @Override public int lastInt(){
          return getThisOrHigher(root,this.inclusiveLo);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          if(inclusiveTo!=Byte.MAX_VALUE+1){
            return super.headSetDescending(inclusiveTo);
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          return super.tailSetDescending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetDescending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetDescending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.TailSet.Ascending(this,size,inclusiveLo);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }
    private static abstract class HeadSet extends UncheckedSubSet{
      transient final int inclusiveHi;
      private HeadSet(ByteSetImpl.Unchecked root,int size,int inclusiveHi){
        super(root,size);
        this.inclusiveHi=inclusiveHi;
      }
      private HeadSet(HeadSet parent,int size,int inclusiveHi){
        super(parent,size);
        this.inclusiveHi=inclusiveHi;
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        final ByteSetImpl root;
        int numRemoved=(size=word0RemoveIfAscending(root=this.root,Byte.MIN_VALUE,size,filter))&0x1ff;
        if(size>=1<<9){
          numRemoved+=((size=word1RemoveIfAscending(root,-64,size>>9,filter))&0x1ff);
          if(size>=1<<9){
            numRemoved+=((size=word2RemoveIfAscending(root,0,size>>9,filter))&0x1ff);
            if(size>=1<<9){
              numRemoved+=word3RemoveIfAscending(root,64,size>>9,filter);
            }
          }
        }
        return numRemoved;
      }
      @Override void clearImpl(){
        clearHeadSet(root,inclusiveHi);
      }
      @Override long isInRange(boolean val){
        if(val){
          if(1<=inclusiveHi){
            return 2L;
          }
        }else{
          if(0<=inclusiveHi){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        return val<=inclusiveHi;
      }
      @Override int isInRange(long val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return hashCodeAscending(root,size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        final int inclusiveTo;
        if((inclusiveTo=this.inclusiveHi)>=inclusiveFrom){
          if(inclusiveFrom!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          if(inclusiveFrom!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          if(this.inclusiveHi!=inclusiveTo){
            return new UncheckedSubSet.HeadSet.Ascending(this,countElementsAscending(root,inclusiveTo),inclusiveTo);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        final int inclusiveFrom;
        if((inclusiveFrom=this.inclusiveHi)>=inclusiveTo){
          if(inclusiveTo!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          if(inclusiveTo!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          if(this.inclusiveHi!=inclusiveFrom){
            return new UncheckedSubSet.HeadSet.Descending(this,countElementsAscending(root,inclusiveFrom),inclusiveFrom);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        if(inclusiveTo!=this.inclusiveHi){
          return new UncheckedSubSet.HeadSet.Ascending(this,countElementsAscending(root,inclusiveTo),inclusiveTo);
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        if(inclusiveFrom!=this.inclusiveHi){
          return new UncheckedSubSet.HeadSet.Descending(this,countElementsAscending(root,inclusiveFrom),inclusiveFrom);
        }
        return this;
      }
      @Override public Byte ceiling(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        if(this.size!=0)
        {
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,1+(int)(val)))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        if(this.size!=0)
        {
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,1+(int)(val)))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        if(this.size!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            if((val=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        if(this.size!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            if((val=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            if((val=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            if((val=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        if(this.size!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.intCeiling(val)):getThisOrHigher(root)))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        if(this.size!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?TypeUtil.intFloor(val):inclusiveHi))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.higherInt(val)):getThisOrHigher(root)))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?TypeUtil.lowerInt(val):inclusiveHi))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.intCeiling(val)):getThisOrHigher(root)))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        if(this.size!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?TypeUtil.intFloor(val):inclusiveHi))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.higherInt(val)):getThisOrHigher(root)))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        if(this.size!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?TypeUtil.lowerInt(val):inclusiveHi))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int tail0s;
            for(;;){
              long word;
              if((tail0s=Long.numberOfTrailingZeros(word=root.word0))!=64){
                root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                break;
              }
              if((tail0s=Long.numberOfTrailingZeros(word=root.word1))!=64){
                root.word1=word&(~(1L<<(tail0s-=64)));
                break;
              }
              if((tail0s=Long.numberOfTrailingZeros(word=root.word2))!=64){
                root.word2=word&(~(1L<<(tail0s)));
                break;
              }
              root.word3=(word=root.word3)&(~(1L<<(tail0s=Long.numberOfTrailingZeros(word)+64)));
              break;
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int lead0s;
            switch((lead0s=this.inclusiveHi)>>6){
              case 1:
                long word;
                if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word3=word&(~(1L<<(lead0s=128-lead0s)));
                  break; 
                }
              case -1:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word2=word&(~(1L<<(lead0s=64-lead0s)));
                  break; 
                }
              case 0:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word1=word&(~(1L<<(lead0s=-lead0s)));
                  break; 
                }
              default:
                root.word0=(word=root.word0)&(~(1L<<(lead0s=-64-Long.numberOfLeadingZeros(word&(-1L>>>(-lead0s-1))))));
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int inclusiveHi){
          super(root,size,inclusiveHi);
        }
        private Ascending(HeadSet parent,int size,int inclusiveHi){
          super(parent,size,inclusiveHi);
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachAscending(root,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringAscending(root,size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrHigher(root);
        }
        @Override public int lastInt(){
          return getThisOrLower(root,this.inclusiveHi);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          if(inclusiveTo!=Byte.MIN_VALUE-1){
            return super.headSetAscending(inclusiveTo);
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          return super.tailSetAscending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetAscending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetAscending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.HeadSet.Descending(this,size,inclusiveHi);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Unchecked root,int size,int inclusiveHi){
          super(root,size,inclusiveHi);
        }
        private Descending(HeadSet parent,int size,int inclusiveHi){
          super(parent,size,inclusiveHi);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachDescending(root,inclusiveHi,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringDescending(root,inclusiveHi,size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrLower(root,this.inclusiveHi);
        }
        @Override public int lastInt(){
          return getThisOrHigher(root);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          return super.headSetDescending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          if(inclusiveFrom!=Byte.MIN_VALUE-1){
            return super.tailSetDescending(inclusiveFrom);
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetDescending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetDescending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.HeadSet.Ascending(this,size,inclusiveHi);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }
    private static abstract class BodySet extends UncheckedSubSet{
      transient final int boundInfo;
      private BodySet(ByteSetImpl.Unchecked root,int size,int boundInfo){
        super(root,size);
        this.boundInfo=boundInfo;
      }
      private BodySet(UncheckedSubSet parent,int size,int boundInfo){
        super(parent,size);
        this.boundInfo=boundInfo;
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        int offset;
        switch((offset=this.boundInfo>>8)>>6){
          case -2:
            numRemoved=(size=word0RemoveIfAscending(root,offset,size,filter))&0x1ff;
            if((size>>=9)==0){
              break;
            }
            offset=-64;
          case -1:
            numRemoved+=(size=word1RemoveIfAscending(root,offset,size,filter))&0x1ff;
            if((size>>=9)==0){
              break;
            }
            offset=0;
          case 0:
            numRemoved+=(size=word2RemoveIfAscending(root,offset,size,filter))&0x1ff;
            if((size>>=9)==0){
              break;
            }
            offset=64;
          default:
            numRemoved+=word3RemoveIfAscending(root,offset,size,filter);
        }
        return numRemoved;
      }
      @Override void clearImpl(){
        clearBodySet(root,boundInfo);
      }
      @Override long isInRange(boolean val){
        final int boundInfo=this.boundInfo;
        if(val){
          if(1<=(boundInfo>>8) && 1>=((byte)(boundInfo&0xff))){
            return 2L;
          }
        }else{
          if(0<=(boundInfo>>8) && 0>=((byte)(boundInfo&0xff))){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        final int boundInfo;
        return val>=(boundInfo=this.boundInfo)>>8 && val<=((byte)(boundInfo&0xff));
      }
      @Override int isInRange(long val){
        final int boundInfo;
        if(val>=(boundInfo=this.boundInfo)>>8 && val<=((byte)(boundInfo&0xff))){
          return (int)val;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int boundInfo,v;
        if((v=(int)val)==val && v>=(boundInfo=this.boundInfo)>>8 && v<=((byte)(boundInfo&0xff))){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int boundInfo,v;
        if((v=(int)val)==val && v>=(boundInfo=this.boundInfo)>>8 && v<=((byte)(boundInfo&0xff))){
          return v;
        }
        return 128;
      }
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return hashCodeDescending(root,(byte)(this.boundInfo&0xff),size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        int boundInfo;
        if(((byte)((boundInfo=this.boundInfo)&0xff))!=inclusiveTo){
          if(inclusiveTo>=(boundInfo>>=8)){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,boundInfo,inclusiveTo),(boundInfo<<8)|(inclusiveTo&0xff));
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        int boundInfo;
        if(inclusiveTo!=(boundInfo=this.boundInfo)>>8){
          if(((byte)(boundInfo&=0xff))>=inclusiveTo){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,boundInfo),(inclusiveTo<<8)|boundInfo);
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        int boundInfo;
        if(inclusiveFrom!=(boundInfo=this.boundInfo)>>8){
          if(((byte)(boundInfo&=0xff))+1!=inclusiveFrom){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,boundInfo),(inclusiveFrom<<8)|boundInfo);
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        int boundInfo;
        if(((byte)((boundInfo=this.boundInfo)&0xff))!=inclusiveFrom){
          if(inclusiveFrom>=(boundInfo>>=8)){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,boundInfo,inclusiveFrom),(boundInfo<<8)|(inclusiveFrom&0xff));
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          final int boundInfo;
          if(inclusiveFrom!=(boundInfo=this.boundInfo)>>8 || inclusiveTo!=(byte)(boundInfo&0xff)){
            return new UncheckedSubSet.BodySet.Ascending(this,countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          final int boundInfo;
          if(inclusiveTo!=(boundInfo=this.boundInfo)>>8 || inclusiveFrom!=(byte)(boundInfo&0xff)){
            return new UncheckedSubSet.BodySet.Descending(this,countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      @Override public Byte ceiling(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            if((val=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            if((val=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            if((val=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            if((val=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?TypeUtil.intCeiling(val):boundInfo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.intFloor(val):boundInfo))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?TypeUtil.higherInt(val):boundInfo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.lowerInt(val):boundInfo))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?TypeUtil.intCeiling(val):boundInfo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.intFloor(val):boundInfo))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        if(this.size!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?TypeUtil.higherInt(val):boundInfo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        if(this.size!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.lowerInt(val):boundInfo))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int tail0s;
            switch((tail0s=this.boundInfo>>8)>>6){  
              case -2:
                long word;
                if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<tail0s)))!=64){
                  root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                  break;
                }
              case -1:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<tail0s)))!=64){
                  root.word1=word&(~(1L<<(tail0s-=64)));
                  break;
                }
              case 0:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<tail0s)))!=64){
                  root.word2=word&(~(1L<<(tail0s)));
                  break; 
                }
              default:
                root.word3=(word=root.word3)&(~(1L<<(tail0s=64+Long.numberOfTrailingZeros(word&(-1L<<tail0s)))));
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final int size;
          if((size=this.size)!=0){
            final var root=this.root;
            int lead0s;
            switch((lead0s=(byte)(this.boundInfo&0xff))>>6){  
              case 1:
                long word;
                if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word3=word&(~(1L<<(lead0s=128-lead0s)));
                  break; 
                }
              case -1:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word2=word&(~(1L<<(lead0s=64-lead0s)));
                  break; 
                }
              case 0:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word1=word&(~(1L<<(lead0s=-lead0s)));
                  break; 
                }
              default:
                root.word0=(word=root.word0)&(~(1L<<(lead0s=-64-Long.numberOfLeadingZeros(word&(-1L>>>(-lead0s-1))))));
            }
            this.size=size-1;
            super.bubbleUpDecrementSize();
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int boundInfo){
          super(root,size,boundInfo);
        }
        private Ascending(UncheckedSubSet parent,int size,int boundInfo){
          super(parent,size,boundInfo);
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachAscending(root,boundInfo>>8,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringAscending(root,boundInfo>>8,size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrHigher(root,this.boundInfo>>8);
        }
        @Override public int lastInt(){
          return getThisOrLower(root,(byte)(this.boundInfo&0xff));
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          return super.headSetAscending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          return super.tailSetAscending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetAscending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetAscending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.BodySet.Descending(this,size,boundInfo);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Unchecked root,int size,int boundInfo){
          super(root,size,boundInfo);
        }
        private Descending(UncheckedSubSet parent,int size,int boundInfo){
          super(parent,size,boundInfo);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(int size,byte[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          forEachDescending(root,(byte)(boundInfo&0xff),size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return toStringDescending(root,(byte)(boundInfo&0xff),size,buffer);
        }
        @Override public int firstInt(){
          return getThisOrLower(root,(byte)(this.boundInfo&0xff));
        }
        @Override public int lastInt(){
          return getThisOrHigher(root,this.boundInfo>>8);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          return super.headSetDescending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          return super.tailSetDescending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetDescending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetDescending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          return new UncheckedSubSet.BodySet.Ascending(this,size,boundInfo);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }    
  }
  private static abstract class CheckedSubSet extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Checked root;
    transient final CheckedSubSet parent;
    transient int modCountAndSize;
    private CheckedSubSet(ByteSetImpl.Checked root,int modCountAndSize){
      super();
      this.root=root;
      this.parent=null;
      this.modCountAndSize=modCountAndSize;
    }
    private CheckedSubSet(CheckedSubSet parent,int modCountAndSize){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.modCountAndSize=modCountAndSize;
    }
    abstract void clearImpl(ByteSetImpl.Checked root);
    @Override public void clear(){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      int size;
      if((size=modCountAndSize&0x1ff)!=0){
        clearImpl(root);
        root.modCountAndSize=rootModCountAndSize+(size=(1<<9)-size);
        this.modCountAndSize=modCountAndSize+size;
        bubbleUpModify(size);
      }
    }
    private void incrementModCountAndSize(){
      var curr=this;
      do{
        curr.modCountAndSize+=((1<<9)+1);
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpModify(int modCountDiff){
      for(var curr=parent;curr!=null;curr=curr.parent){
        curr.modCountAndSize+=modCountDiff;
      }
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,root.modCountAndSize>>9);
      return modCountAndSize&0x1ff;
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,root.modCountAndSize>>9);
      return (modCountAndSize&0x1ff)==0;
    }
    abstract boolean isInRange(int val);
    abstract int isInRange(long val);
    abstract int isInRange(float val);
    abstract int isInRange(double val);
    abstract long isInRange(boolean val);
    @Override public boolean contains(boolean val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && wordContains(root.word2,isInRange(val));
    }
    @Override public boolean contains(byte val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(char val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(int val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.contains(root,val);
    }
    @Override public boolean contains(long val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(float val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(double val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      return (modCountAndSize&0x1ff)!=0 && ByteSetImpl.contains(root,isInRange(val));
    }
    @Override public boolean contains(Object val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((modCountAndSize&0x1ff)!=0){
        for(;;){
          final int v;
          if(val instanceof Byte){
            v=(byte)val;
          }else if(val instanceof Integer || val instanceof Short){
            v=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(int)l)){
              break;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(int)f)){
              break;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(int)d)){
              break;
            }
          }else if(val instanceof Character){
            v=(char)val;
          }else if(val instanceof Boolean){
            return wordContains(root.word2,isInRange((boolean)val));
          }else{
            break;
          }
          return isInRange(v) && ByteSetImpl.contains(root,v);
        }
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      long word;
      if((modCountAndSize&0x1ff)!=0 && (word=root.word2)!=(word&=~isInRange(val))){
        root.word2=word;
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ByteSetImpl.removeVal(root,val)){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0 && ByteSetImpl.removeVal(root,isInRange(val))){
        root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        bubbleUpModify((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
      if((modCountAndSize&0x1ff)!=0){
        returnFalse:for(;;){
          returnTrue:for(;;){
            final int v;
            if(val instanceof Byte){
              v=(byte)val;
            }else if(val instanceof Integer || val instanceof Short){
              v=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(v=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(v=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(v=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              v=(char)val;
            }else if(val instanceof Boolean){
              long word;
              if((word=root.word2)!=(word&=~isInRange((boolean)val))){
                root.word2=word;
                break returnTrue;
              }
              break returnFalse;
            }else{
              break returnFalse;
            }
            if(isInRange(v) && ByteSetImpl.removeVal(root,v)){
              break returnTrue;
            }
            break returnFalse;
          }
          root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
          this.modCountAndSize=modCountAndSize+((1<<9)-1);
          bubbleUpModify((1<<9)-1);
          return true;
        }
      }
      return false;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst);
    @Override public byte[] toByteArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final byte[] dst;
        copyToArray(root,size,dst=new byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,short[] dst);
    @Override public short[] toShortArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final short[] dst;
        copyToArray(root,size,dst=new short[size]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,int[] dst);
    @Override public int[] toIntArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final int[] dst;
        copyToArray(root,size,dst=new int[size]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,long[] dst);
    @Override public long[] toLongArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final long[] dst;
        copyToArray(root,size,dst=new long[size]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,float[] dst);
    @Override public float[] toFloatArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final float[] dst;
        copyToArray(root,size,dst=new float[size]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,double[] dst);
    @Override public double[] toDoubleArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final double[] dst;
        copyToArray(root,size,dst=new double[size]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst);
    @Override public Byte[] toArray(){
      final ByteSetImpl.Checked root;
      int size;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        final Byte[] dst;
        copyToArray(root,size,dst=new Byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    abstract void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst);
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      final int size;
      final int modCountAndSize=this.modCountAndSize;
      final ByteSetImpl.Checked root;
      try{
        dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
      }finally{
        CheckedCollection.checkModCount(modCountAndSize>>9,(root=this.root).modCountAndSize>>9);
      }
      if(size!=0){
        copyToArray(root,size,dst);
      }
      return dst;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount((size=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
      if((size&=0x1ff)!=0){
        copyToArray(root,size,dst=OmniArray.uncheckedArrResize(size,dst));
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    abstract int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,int expectedModCount);
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        final ByteSetImpl.Checked root;
        if((size=removeIfImpl(root=this.root,size,filter,modCountAndSize))!=0){
          root.modCountAndSize+=(size=(1<<9)-size);
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpModify(size);
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCountAndSize>>9,root.modCountAndSize>>9);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        final ByteSetImpl.Checked root;
        if((size=removeIfImpl(root=this.root,size,filter::test,modCountAndSize))!=0){
          root.modCountAndSize+=(size=(1<<9)-size);
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpModify(size);
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCountAndSize>>9,root.modCountAndSize>>9);
      }
      return false;
    }
    abstract void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action);
    @Override public void forEach(ByteConsumer action){
      final var root=this.root;
      final int modCountAndSize=this.modCountAndSize;
      try{
        final int size;
        if((size=modCountAndSize&0x1ff)!=0){
          forEachImpl(root,size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize>>9,root.modCountAndSize>>9);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final var root=this.root;
      final int modCountAndSize=this.modCountAndSize;
      try{
        final int size;
        if((size=modCountAndSize&0x1ff)!=0){
          forEachImpl(root,size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize>>9,root.modCountAndSize>>9);
      }
    }
    abstract int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer);
    @Override public String toString(){
      int modCountAndSize;
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final byte[] buffer;
        (buffer=new byte[modCountAndSize*6])[0]='[';
        buffer[modCountAndSize=toStringImpl(root,modCountAndSize,buffer)]=']';
        return new String(buffer,0,modCountAndSize+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    abstract int firstIntImpl(ByteSetImpl.Checked root);
    abstract int lastIntImpl(ByteSetImpl.Checked root);
    @Override public int firstInt(){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&0x1ff)!=0){
        return firstIntImpl(root);
      }
      throw new NoSuchElementException();
    }
    @Override public int lastInt(){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&0x1ff)!=0){
        return lastIntImpl(root);
      }
      throw new NoSuchElementException();
    }
    private void checkAddBounds(byte val){
      if(!isInRange(val)){
        throw new IllegalArgumentException("cannot add "+val+" : out of bounds");
      }
    }
    @Override public boolean add(boolean val){
      final long mask;
      if(val){
        checkAddBounds((byte)1);
        mask=2L;
      }else{
        checkAddBounds((byte)1);
        mask=1L;
      }
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      long word;
      if((word=root.word2)!=(word|=mask)){
        root.word2=word;
        root.modCountAndSize=rootModCountAndSize+((1<<9)+1);
        this.modCountAndSize=modCountAndSize+((1<<9)+1);
        final CheckedSubSet parent;
        if((parent=this.parent)!=null){
          parent.incrementModCountAndSize();
        }
        return true;
      }
      return false;
    }
    @Override public boolean add(byte val){
      checkAddBounds(val);
      final ByteSetImpl.Checked root;
      final int modCountAndSize,rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      incrementSize:for(;;){
        long word;
        switch(val>>6){
          case -2:
            if((word=root.word0)!=(word|=(1L<<val))){
              root.word0=word;
              break incrementSize;
            }
            break;
          case -1:
            if((word=root.word1)!=(word|=(1L<<val))){
              root.word1=word;
              break incrementSize;
            }
            break;
          case 0:
            if((word=root.word2)!=(word|=(1L<<val))){
              root.word2=word;
              break incrementSize;
            }
            break;
          default:
            if((word=root.word3)!=(word|=(1L<<val))){
              root.word3=word;
              break incrementSize;
            }
        }
        return false;
      }
      root.modCountAndSize=rootModCountAndSize+((1<<9)+1);
      this.modCountAndSize=modCountAndSize+((1<<9)+1);
      final CheckedSubSet parent;
      if((parent=this.parent)!=null){
        parent.incrementModCountAndSize();
      }
      return true;
    }
    private static abstract class TailSet extends CheckedSubSet{
      transient final int inclusiveLo;
      private TailSet(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveLo){
        super(root,modCountAndSize);
        this.inclusiveLo=inclusiveLo;
      }
      private TailSet(TailSet parent,int modCountAndSize,int inclusiveLo){
        super(parent,modCountAndSize);
        this.inclusiveLo=inclusiveLo;
      }
      @Override void clearImpl(ByteSetImpl.Checked root){
        clearTailSet(root,inclusiveLo);
      }
      @Override long isInRange(boolean val){
        if(val){
          if(1>=inclusiveLo){
            return 2L;
          }
        }else{
          if(0>=inclusiveLo){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        return val>=inclusiveLo;
      }
      @Override int isInRange(long val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int v;
        if((v=(int)val)==val && v>=inclusiveLo){
          return v;
        }
        return 128;
      }
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,int expectedModCount){
        return removeIfImplStartWord3Descending(root,Byte.MAX_VALUE,size,filter,expectedModCount);
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return hashCodeDescending(root,modCountAndSize);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int inclusiveFrom;
        switch(Integer.signum(inclusiveTo+1-(inclusiveFrom=this.inclusiveLo))){
          case 1:
            if(inclusiveTo!=Byte.MAX_VALUE){
              return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
            }
            return this;
          case 0:
            return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        throwIAE:switch(Integer.signum(inclusiveTo+1-inclusiveFrom)){
          case 1:
            switch(Integer.signum(inclusiveFrom-this.inclusiveLo)){
              case 1:
                if(inclusiveTo!=Byte.MAX_VALUE){
                  break;
                }
                return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|countElementsDescending(root,inclusiveFrom),inclusiveFrom);
              case 0:
                if(inclusiveTo!=Byte.MAX_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          case 0:
            if(this.inclusiveLo<=inclusiveFrom){
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int inclusiveTo;
        switch(Integer.signum(inclusiveFrom+1-(inclusiveTo=this.inclusiveLo))){
          case 1:
            if(inclusiveFrom!=Byte.MAX_VALUE){
              return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
            }
            return this;
          case 0:
            return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        throwIAE:switch(Integer.signum(inclusiveFrom+1-inclusiveTo)){
          case 1:
            switch(Integer.signum(inclusiveTo-this.inclusiveLo)){
              case 1:
                if(inclusiveFrom!=Byte.MAX_VALUE){
                  break;
                }
                return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|countElementsDescending(root,inclusiveTo),inclusiveTo);
              case 0:
                if(inclusiveFrom!=Byte.MAX_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          case 0:
            if(this.inclusiveLo<=inclusiveTo){
              return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public Byte ceiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=getThisOrLower(root,inclusiveLo,-1+(int)(val)))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=getThisOrLower(root,inclusiveLo,-1+(int)(val)))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            if((val=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
             if((val=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            if((val=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
             if((val=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?(int)(val):inclusiveLo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,(int)(val)):getThisOrLower(root)))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?1+(int)(val):inclusiveLo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,-1+(int)(val)):getThisOrLower(root)))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?TypeUtil.intCeiling(val):inclusiveLo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.intFloor(val)):getThisOrLower(root)))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?TypeUtil.higherInt(val):inclusiveLo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.lowerInt(val)):getThisOrLower(root)))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<=Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>(inclusiveLo=this.inclusiveLo)?TypeUtil.intCeiling(val):inclusiveLo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.intFloor(val)):getThisOrLower(root)))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(val<Byte.MAX_VALUE)
          {
            final int inclusiveLo;
            final int v;
            if((v=getThisOrHigher(root,val>=(inclusiveLo=this.inclusiveLo)?TypeUtil.higherInt(val):inclusiveLo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          if(!(val<=(inclusiveLo=this.inclusiveLo))){
            final int v;
             if((v=(val<=Byte.MAX_VALUE?getThisOrLower(root,inclusiveLo,TypeUtil.lowerInt(val)):getThisOrLower(root)))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int tail0s;
            switch((tail0s=this.inclusiveLo)>>6){  
              case -2:
                long word;
                if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<tail0s)))!=64){
                  root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                  break;
                }
              case -1:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<tail0s)))!=64){
                  root.word1=word&(~(1L<<(tail0s-=64)));
                  break;
                }
              case 0:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<tail0s)))!=64){
                  root.word2=word&(~(1L<<(tail0s)));
                  break; 
                }
              default:
                root.word3=(word=root.word3)&(~(1L<<(tail0s=64+Long.numberOfTrailingZeros(word&(-1L<<tail0s)))));
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int lead0s;
            for(;;){
              long word;
              if((lead0s=Long.numberOfLeadingZeros(word=root.word3))!=64){
                root.word3=word&(~(1L<<(lead0s=Byte.MAX_VALUE-lead0s)));
                break;
              }
              if((lead0s=Long.numberOfLeadingZeros(word=root.word2))!=64){
                root.word2=word&(~(1L<<(lead0s=63-lead0s)));
                break;
              }
              if((lead0s=Long.numberOfLeadingZeros(word=root.word1))!=64){
                root.word1=word&(~(1L<<(lead0s=-1-lead0s)));
                break;
              }
              root.word0=(word=root.word0)&(~(1L<<(lead0s=-65-Long.numberOfLeadingZeros(word))));
              break;
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveLo){
          super(root,modCountAndSize,inclusiveLo);
        }
        private Ascending(TailSet parent,int modCountAndSize,int inclusiveLo){
          super(parent,modCountAndSize,inclusiveLo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayAscending(root,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachAscending(root,inclusiveLo,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringAscending(root,inclusiveLo,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root,inclusiveLo);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          return super.headSetAscending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(inclusiveFrom-this.inclusiveLo)){
            case 1:
              if(inclusiveFrom!=Byte.MAX_VALUE+1){
                return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|countElementsDescending(root,inclusiveFrom),inclusiveFrom);
              }
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetAscending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(fromElement-this.inclusiveLo)){
            case 1:
              return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|countElementsDescending(root,fromElement),fromElement);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending((int)inclusiveFrom,(int)inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.TailSet.Descending(this,modCountAndSize,this.inclusiveLo);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveLo){
          super(root,modCountAndSize,inclusiveLo);
        }
        private Descending(TailSet parent,int modCountAndSize,int inclusiveLo){
          super(parent,modCountAndSize,inclusiveLo);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayDescending(root,
          this.inclusiveLo,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachDescending(root,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringDescending(root,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root,inclusiveLo);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(inclusiveTo-this.inclusiveLo)){
            case 1:
              if(inclusiveTo!=Byte.MAX_VALUE+1){
                return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|countElementsDescending(root,inclusiveTo),inclusiveTo);
              }
              return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          return super.tailSetDescending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(toElement-this.inclusiveLo)){
            case 1:
              return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|countElementsDescending(root,toElement),toElement);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetDescending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize,this.inclusiveLo);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }
    private static abstract class HeadSet extends CheckedSubSet{
      transient final int inclusiveHi;
      private HeadSet(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveHi){
        super(root,modCountAndSize);
        this.inclusiveHi=inclusiveHi;
      }
      private HeadSet(HeadSet parent,int modCountAndSize,int inclusiveHi){
        super(parent,modCountAndSize);
        this.inclusiveHi=inclusiveHi;
      }
      @Override void clearImpl(ByteSetImpl.Checked root){
        clearHeadSet(root,inclusiveHi);
      }
      @Override long isInRange(boolean val){
        if(val){
          if(1<=inclusiveHi){
            return 2L;
          }
        }else{
          if(0<=inclusiveHi){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        return val<=inclusiveHi;
      }
      @Override int isInRange(long val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int v;
        if((v=(int)val)==val && v<=inclusiveHi){
          return v;
        }
        return 128;
      }
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,int expectedModCount){
        return removeIfImplStartWord0Ascending(root,Byte.MIN_VALUE,size,filter,expectedModCount);
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return hashCodeAscending(root,modCountAndSize);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int inclusiveTo;
        switch(Integer.signum((inclusiveTo=this.inclusiveHi)+1-inclusiveFrom)){
        case 1:
          if(inclusiveFrom!=Byte.MIN_VALUE){
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        throwIAE:switch(Integer.signum(inclusiveTo-inclusiveFrom+1)){
          case 1:
            switch(Integer.signum(this.inclusiveHi-inclusiveTo)){
              case 1:
                if(inclusiveFrom!=Byte.MIN_VALUE){
                  break; 
                }
                return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|countElementsAscending(root,inclusiveTo),inclusiveTo);
              case 0:
                if(inclusiveFrom!=Byte.MIN_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          case 0:
            if(inclusiveTo<=this.inclusiveHi){
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int inclusiveFrom;
        switch(Integer.signum((inclusiveFrom=this.inclusiveHi)+1-inclusiveTo)){
          case 1:
            if(inclusiveTo!=Byte.MIN_VALUE){
              return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
            }
            return this;
          case 0:
            return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        throwIAE:switch(Integer.signum(inclusiveFrom+1-inclusiveTo)){
          case 1:
            switch(Integer.signum(this.inclusiveHi-inclusiveFrom)){
              case 1:
                if(inclusiveTo!=Byte.MIN_VALUE){
                  break;
                }
                return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|countElementsAscending(root,inclusiveFrom),inclusiveFrom);
              case 0:
                if(inclusiveTo!=Byte.MIN_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
          case 0:
            if(inclusiveFrom<=this.inclusiveHi){
              return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public Byte ceiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,1+(int)(val)))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,1+(int)(val)))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            if((val=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            if((val=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            if((val=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            if((val=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,(int)(val)):getThisOrHigher(root)))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?(int)(val):inclusiveHi))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,1+(int)(val)):getThisOrHigher(root)))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?-1+(int)(val):inclusiveHi))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.intCeiling(val)):getThisOrHigher(root)))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?TypeUtil.intFloor(val):inclusiveHi))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.higherInt(val)):getThisOrHigher(root)))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?TypeUtil.lowerInt(val):inclusiveHi))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<=(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.intCeiling(val)):getThisOrHigher(root)))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<(inclusiveHi=this.inclusiveHi)?TypeUtil.intFloor(val):inclusiveHi))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          if(val<(inclusiveHi=this.inclusiveHi)){
            final int v;
            if((v=(val>=Byte.MIN_VALUE?getThisOrHigher(root,inclusiveHi,TypeUtil.higherInt(val)):getThisOrHigher(root)))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          if(!(val<=Byte.MIN_VALUE))
          {
            final int inclusiveHi;
            final int v;
            if((v=getThisOrLower(root,val<=(inclusiveHi=this.inclusiveHi)?TypeUtil.lowerInt(val):inclusiveHi))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int tail0s;
            for(;;){
              long word;
              if((tail0s=Long.numberOfTrailingZeros(word=root.word0))!=64){
                root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                break;
              }
              if((tail0s=Long.numberOfTrailingZeros(word=root.word1))!=64){
                root.word1=word&(~(1L<<(tail0s-=64)));
                break;
              }
              if((tail0s=Long.numberOfTrailingZeros(word=root.word2))!=64){
                root.word2=word&(~(1L<<(tail0s)));
                break;
              }
              root.word3=(word=root.word3)&(~(1L<<(tail0s=Long.numberOfTrailingZeros(word)+64)));
              break;
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int lead0s;
            switch((lead0s=this.inclusiveHi)>>6){
              case 1:
                long word;
                if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word3=word&(~(1L<<(lead0s=128-lead0s)));
                  break; 
                }
              case -1:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word2=word&(~(1L<<(lead0s=64-lead0s)));
                  break; 
                }
              case 0:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word1=word&(~(1L<<(lead0s=-lead0s)));
                  break; 
                }
              default:
                root.word0=(word=root.word0)&(~(1L<<(lead0s=-64-Long.numberOfLeadingZeros(word&(-1L>>>(-lead0s-1))))));
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveHi){
          super(root,modCountAndSize,inclusiveHi);
        }
        private Ascending(HeadSet parent,int modCountAndSize,int inclusiveHi){
          super(parent,modCountAndSize,inclusiveHi);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayAscending(root,
          this.inclusiveHi,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachAscending(root,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringAscending(root,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root,inclusiveHi);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(this.inclusiveHi-inclusiveTo)){
            case 1:
              if(inclusiveTo!=Byte.MIN_VALUE-1){
                return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|countElementsAscending(root,inclusiveTo),inclusiveTo);
              }
              return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          return super.tailSetAscending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(this.inclusiveHi-toElement)){
            case 1:
              return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|countElementsAscending(root,toElement),toElement);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetAscending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending((int)inclusiveFrom,(int)inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize,this.inclusiveHi);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveHi){
          super(root,modCountAndSize,inclusiveHi);
        }
        private Descending(HeadSet parent,int modCountAndSize,int inclusiveHi){
          super(parent,modCountAndSize,inclusiveHi);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayDescending(root,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachDescending(root,inclusiveHi,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringDescending(root,inclusiveHi,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root,inclusiveHi);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          return super.headSetDescending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(this.inclusiveHi-inclusiveFrom)){
            case 1:
              if(inclusiveFrom!=Byte.MIN_VALUE-1){
                return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|countElementsAscending(root,inclusiveFrom),inclusiveFrom);
              }
              return new AbstractByteSet.EmptyView.Checked.Descending(Byte.MIN_VALUE);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetDescending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          final int modCountAndSize;
          final ByteSetImpl.Checked root;
          CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
          switch(Integer.signum(this.inclusiveHi-fromElement)){
            case 1:
              return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|countElementsAscending(root,fromElement),fromElement);
            case 0:
              return this;
            default:
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize,this.inclusiveHi);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }
    private static abstract class BodySet extends CheckedSubSet{
      transient final int boundInfo;
      private BodySet(ByteSetImpl.Checked root,int modCountAndSize,int boundInfo){
        super(root,modCountAndSize);
        this.boundInfo=boundInfo;
      }
      private BodySet(CheckedSubSet parent,int modCountAndSize,int boundInfo){
        super(parent,modCountAndSize);
        this.boundInfo=boundInfo;
      }
      @Override void clearImpl(ByteSetImpl.Checked root){
        clearBodySet(root,boundInfo);
      }
      @Override long isInRange(boolean val){
        final int boundInfo=this.boundInfo;
        if(val){
          if(1<=(boundInfo>>8) && 1>=((byte)(boundInfo&0xff))){
            return 2L;
          }
        }else{
          if(0<=(boundInfo>>8) && 0>=((byte)(boundInfo&0xff))){
            return 1L;
          }
        }
        return 0L;
      }
      @Override boolean isInRange(int val){
        final int boundInfo;
        return val>=(boundInfo=this.boundInfo)>>8 && val<=((byte)(boundInfo&0xff));
      }
      @Override int isInRange(long val){
        final int boundInfo;
        if(val>=(boundInfo=this.boundInfo)>>8 && val<=((byte)(boundInfo&0xff))){
          return (int)val;
        }
        return 128;
      }
      @Override int isInRange(float val){
        final int boundInfo,v;
        if((v=(int)val)==val && v>=(boundInfo=this.boundInfo)>>8 && v<=((byte)(boundInfo&0xff))){
          return v;
        }
        return 128;
      }
      @Override int isInRange(double val){
        final int boundInfo,v;
        if((v=(int)val)==val && v>=(boundInfo=this.boundInfo)>>8 && v<=((byte)(boundInfo&0xff))){
          return v;
        }
        return 128;
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return hashCodeDescending(root,(byte)(this.boundInfo&0xff),modCountAndSize);
        }
        return 0;
      }
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,int expectedModCount){
        final int inclHi;
        switch((inclHi=(byte)(this.boundInfo&0xff))>>6){
          case 1:
            return removeIfImplStartWord3Descending(root,inclHi,size,filter,expectedModCount);
          case 0:
            return removeIfImplStartWord2Descending(root,inclHi,size,filter,expectedModCount);
          case -1:
            return removeIfImplStartWord1Descending(root,inclHi,size,filter,expectedModCount);
          default:
            return removeIfImplStartWord0Descending(root,inclHi,size,filter,expectedModCount);
        }
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(Integer.signum(((byte)((boundInfo=this.boundInfo)&0xff))-inclusiveTo)){
          case 1:
            final int inclusiveFrom;
            switch(Integer.signum(inclusiveTo+1-(inclusiveFrom=boundInfo>>8))){
              case 1:
                return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),(boundInfo&(~0xff))|(inclusiveTo&0xff));
              case 0:
                return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
              default:
                break throwIAE;
            }
          case 0:
            return this;
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(Integer.signum(inclusiveTo-(((boundInfo=this.boundInfo)>>8)))){
          case 1:
            final int inclusiveFrom;
            switch(Integer.signum(((byte)(inclusiveFrom=boundInfo&0xff))+1-inclusiveTo)){
              case 1:
                return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|inclusiveFrom);
              case 0:
                return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
              default:
                break throwIAE;
            }
          case 0:
            return this;
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(Integer.signum(inclusiveFrom-(((boundInfo=this.boundInfo)>>8)))){
          case 1:
            final int inclusiveTo;
            switch(Integer.signum(((byte)(inclusiveTo=boundInfo&0xff))+1-inclusiveFrom)){
              case 1:
                return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|inclusiveTo);
              case 0:
                return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
              default:
                break throwIAE;
            }
          case 0:
            return this;
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(Integer.signum(((byte)((boundInfo=this.boundInfo)&0xff))-inclusiveFrom)){
          case 1:
            final int inclusiveTo;
            switch(Integer.signum(inclusiveFrom+1-(inclusiveTo=boundInfo>>8))){
              case 1:
                return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),(boundInfo&(~0xff))|(inclusiveFrom&0xff));
              case 0:
                return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
              default:
                break throwIAE;
            }
          case 0:
            return this;
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(inclusiveTo-inclusiveFrom+1){
          case 1:
            switch(Integer.signum(inclusiveFrom-((boundInfo=this.boundInfo)>>8))){
              case 1:
                if(((byte)(boundInfo&0xff))<inclusiveTo){
                  break throwIAE;
                }
                break;
              default:
                break throwIAE;
              case 0:
                switch(Integer.signum(((byte)(boundInfo&0xff))-inclusiveTo)){
                  default:
                    break throwIAE;
                  case 0:
                    return this;
                  case 1:
                }
            }
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|countElements(root,inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
          case 0:
            if(inclusiveFrom>=(boundInfo=this.boundInfo)>>8 && inclusiveTo<=(byte)(boundInfo&0xff)){
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
        final int boundInfo;
        throwIAE:switch(inclusiveFrom-inclusiveTo+1){
          case 1:
            switch(Integer.signum(inclusiveTo-((boundInfo=this.boundInfo)>>8))){
              case 1:
                if(((byte)(boundInfo&0xff))<inclusiveFrom){
                  break throwIAE;
                }
                break;
              default:
                break throwIAE;
              case 0:
                switch(Integer.signum(((byte)(boundInfo&0xff))-inclusiveFrom)){
                  default:
                    break throwIAE;
                  case 0:
                    return this;
                  case 1:
                }
            }
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|countElements(root,inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
          case 0:
            if(inclusiveTo>=(boundInfo=this.boundInfo)>>8 && inclusiveFrom<=(byte)(boundInfo&0xff)){
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public Byte ceiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte floor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte higher(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public Byte lower(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return null;
      }
      @Override public byte byteCeiling(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte higherByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public byte lowerByte(byte val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (byte)v;
            }
          }
        }
        return Byte.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short higherShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public short lowerShort(short val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (short)v;
            }
          }
        }
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            if((val=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            if((val=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int higherInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            if((val=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public int lowerInt(int val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            if((val=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (int)val;
            }
          }
        }
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?(int)(val):boundInfo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?(int)(val):boundInfo))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long higherLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?1+(int)(val):boundInfo))!=128){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public long lowerLong(long val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?-1+(int)(val):boundInfo))!=-129){
              return (long)v;
            }
          }
        }
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?TypeUtil.intCeiling(val):boundInfo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.intFloor(val):boundInfo))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float higherFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?TypeUtil.higherInt(val):boundInfo))!=128){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public float lowerFloat(float val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.lowerInt(val):boundInfo))!=-129){
              return (float)v;
            }
          }
        }
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<=(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>(boundInfo>>=8)?TypeUtil.intCeiling(val):boundInfo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.intFloor(val):boundInfo))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double higherDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveHi;
          int boundInfo;
          if(val<(inclusiveHi=(byte)((boundInfo=this.boundInfo)&0xff))){
            final int v;
            if((v=getThisOrHigher(root,inclusiveHi,val>=(boundInfo>>=8)?TypeUtil.higherInt(val):boundInfo))!=128){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public double lowerDouble(double val){
        final int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0)
        {
          final int inclusiveLo;
          int boundInfo;
          if(!(val<=(inclusiveLo=((boundInfo=this.boundInfo)>>8)))){
            final int v;
            if((v=getThisOrLower(root,inclusiveLo,val<=(boundInfo=(byte)(boundInfo&0xff))?TypeUtil.lowerInt(val):boundInfo))!=-129){
              return (double)v;
            }
          }
        }
        return Double.NaN;
      }
      @Override public int pollFirstInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int tail0s;
            switch((tail0s=this.boundInfo>>8)>>6){  
              case -2:
                long word;
                if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<tail0s)))!=64){
                  root.word0=word&(~(1L<<(tail0s+=Byte.MIN_VALUE)));
                  break;
                }
              case -1:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<tail0s)))!=64){
                  root.word1=word&(~(1L<<(tail0s-=64)));
                  break;
                }
              case 0:
                if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<tail0s)))!=64){
                  root.word2=word&(~(1L<<(tail0s)));
                  break; 
                }
              default:
                root.word3=(word=root.word3)&(~(1L<<(tail0s=64+Long.numberOfTrailingZeros(word&(-1L<<tail0s)))));
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return tail0s;
          }
          return Integer.MIN_VALUE;
      }
      @Override public int pollLastInt(){
          final ByteSetImpl.Checked root;
          final int rootModCountAndSize,modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
          if((modCountAndSize&0x1ff)!=0){
            int lead0s;
            switch((lead0s=(byte)(this.boundInfo&0xff))>>6){  
              case 1:
                long word;
                if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word3=word&(~(1L<<(lead0s=128-lead0s)));
                  break; 
                }
              case -1:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word2=word&(~(1L<<(lead0s=64-lead0s)));
                  break; 
                }
              case 0:
                if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-lead0s-1)))-1)!=63){
                  root.word1=word&(~(1L<<(lead0s=-lead0s)));
                  break; 
                }
              default:
                root.word0=(word=root.word0)&(~(1L<<(lead0s=-64-Long.numberOfLeadingZeros(word&(-1L>>>(-lead0s-1))))));
            }
            root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
            this.modCountAndSize=modCountAndSize+((1<<9)-1);
            super.bubbleUpModify((1<<9)-1);
            return lead0s;
          }
          return Integer.MIN_VALUE;
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int boundInfo){
          super(root,modCountAndSize,boundInfo);
        }
        private Ascending(CheckedSubSet parent,int modCountAndSize,int boundInfo){
          super(parent,modCountAndSize,boundInfo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayAscending(root,
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachAscending(root,this.boundInfo>>8,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringAscending(root,this.boundInfo>>8,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root,boundInfo>>8);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root,(byte)(boundInfo&0xff));
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            --inclusiveTo;
          }
          return super.headSetAscending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            ++inclusiveFrom;
          }
          return super.tailSetAscending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetAscending(toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetAscending(fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            ++inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            --inclusiveTo;
          }
          return super.subSetAscending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetAscending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.BodySet.Descending(this,modCountAndSize,this.boundInfo);
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int boundInfo){
          super(root,modCountAndSize,boundInfo);
        }
        private Descending(CheckedSubSet parent,int modCountAndSize,int boundInfo){
          super(parent,modCountAndSize,boundInfo);
        }
        @Override public Byte ceiling(byte val){
          return super.floor(val);
        }
        @Override public Byte floor(byte val){
          return super.ceiling(val);
        }
        @Override public Byte higher(byte val){
          return super.lower(val);
        }
        @Override public Byte lower(byte val){
          return super.higher(val);
        }
        @Override public byte byteCeiling(byte val){
          return super.byteFloor(val);
        }
        @Override public byte byteFloor(byte val){
          return super.byteCeiling(val);
        }
        @Override public byte higherByte(byte val){
          return super.lowerByte(val);
        }
        @Override public byte lowerByte(byte val){
          return super.higherByte(val);
        }
        @Override public short shortCeiling(short val){
          return super.shortFloor(val);
        }
        @Override public short shortFloor(short val){
          return super.shortCeiling(val);
        }
        @Override public short higherShort(short val){
          return super.lowerShort(val);
        }
        @Override public short lowerShort(short val){
          return super.higherShort(val);
        }
        @Override public int intCeiling(int val){
          return super.intFloor(val);
        }
        @Override public int intFloor(int val){
          return super.intCeiling(val);
        }
        @Override public int higherInt(int val){
          return super.lowerInt(val);
        }
        @Override public int lowerInt(int val){
          return super.higherInt(val);
        }
        @Override public long longCeiling(long val){
          return super.longFloor(val);
        }
        @Override public long longFloor(long val){
          return super.longCeiling(val);
        }
        @Override public long higherLong(long val){
          return super.lowerLong(val);
        }
        @Override public long lowerLong(long val){
          return super.higherLong(val);
        }
        @Override public float floatCeiling(float val){
          return super.floatFloor(val);
        }
        @Override public float floatFloor(float val){
          return super.floatCeiling(val);
        }
        @Override public float higherFloat(float val){
          return super.lowerFloat(val);
        }
        @Override public float lowerFloat(float val){
          return super.higherFloat(val);
        }
        @Override public double doubleCeiling(double val){
          return super.doubleFloor(val);
        }
        @Override public double doubleFloor(double val){
          return super.doubleCeiling(val);
        }
        @Override public double higherDouble(double val){
          return super.lowerDouble(val);
        }
        @Override public double lowerDouble(double val){
          return super.higherDouble(val);
        }
        @Override public ByteComparator comparator(){
          return ByteComparator::descendingCompare;
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          copyToArrayDescending(root,
          this.boundInfo>>8,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          forEachDescending(root,(byte)(this.boundInfo&0xff),size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return toStringDescending(root,(byte)(this.boundInfo&0xff),size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return getThisOrLower(root,(byte)(boundInfo&0xff));
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return getThisOrHigher(root,boundInfo>>8);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
          int inclusiveTo=toElement;
          if(!inclusive){
            ++inclusiveTo;
          }
          return super.headSetDescending(inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
          int inclusiveFrom=fromElement;
          if(!inclusive){
            --inclusiveFrom;
          }
          return super.tailSetDescending(inclusiveFrom);
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
          return super.headSetDescending((int)toElement);
        }
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
          return super.tailSetDescending((int)fromElement);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
          int inclusiveFrom=fromElement;
          if(!fromInclusive){
            --inclusiveFrom;
          }
          int inclusiveTo=toElement;
          if(!toInclusive){
            ++inclusiveTo;
          }
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
          return super.subSetDescending((int)fromElement,(int)toElement);
        }
        @Override public OmniNavigableSet.OfByte descendingSet(){
          final int modCountAndSize;
          CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),root.modCountAndSize&(~0x1ff));
          return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize,this.boundInfo);
        }
        @Override public int pollFirstInt(){
          return super.pollLastInt();
        }
        @Override public int pollLastInt(){
          return super.pollFirstInt();
        }
        @Override public OmniIterator.OfByte iterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
        @Override public OmniIterator.OfByte descendingIterator(){
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
    }
  }
  private static abstract class UncheckedFullItr extends AbstractByteItr{
    final ByteSetImpl root;
    int offset;
    private UncheckedFullItr(ByteSetImpl root,int offset){
      this.root=root;
      this.offset=offset;
    }
    private void forEachRemainingAscending(int offset,ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private void forEachRemainingDescending(int offset,ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Ascending extends UncheckedFullItr{
      private Ascending(ByteSetImpl root,int offset){
        super(root,offset);
      }
      @Override public boolean hasNext(){
        return this.offset!=128;
      }
      @Override public byte nextByte(){
        final int ret;
        if((ret=this.offset)!=Byte.MAX_VALUE){
          this.offset=getThisOrHigher(root,ret+1);
        }else{
          this.offset=128;
        }
        return (byte)ret;
      }
      @Override public void remove(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Object clone(){
        return new Ascending(this.root,this.offset);
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int offset;
        if((offset=this.offset)!=128){
          super.forEachRemainingAscending(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int offset;
        if((offset=this.offset)!=128){
          super.forEachRemainingAscending(offset,action::accept);
        }
      }
    }
    private static class Descending extends UncheckedFullItr{
      private Descending(ByteSetImpl root,int offset){
        super(root,offset);
      }
      @Override public boolean hasNext(){
        return this.offset!=-129;
      }
      @Override public byte nextByte(){
        final int ret;
        if((ret=this.offset)!=Byte.MIN_VALUE){
          this.offset=getThisOrLower(root,ret-1);
        }else{
          this.offset=-129;
        }
        return (byte)ret;
      }
      @Override public void remove(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Object clone(){
        return new Descending(this.root,this.offset);
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int offset;
        if((offset=this.offset)!=129){
          super.forEachRemainingDescending(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int offset;
        if((offset=this.offset)!=-129){
          super.forEachRemainingDescending(offset,action::accept);
        }
      }
    }
  }
  private static abstract class CheckedFullItr extends AbstractByteItr{
    final ByteSetImpl.Checked root;
    int modCountAndSize;
    int offsetAndLastRet;
    private CheckedFullItr(ByteSetImpl.Checked root,int modCountAndSize,int offsetAndLastRet){
      this.root=root;
      this.modCountAndSize=modCountAndSize;
      this.offsetAndLastRet=offsetAndLastRet;
    }
    abstract byte nextImpl(ByteSetImpl.Checked root);
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount(modCountAndSize,(root=this.root).modCountAndSize);
      return nextImpl(root);
    }
    private void forEachRemainingAscending(int offset,ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private void forEachRemainingDescending(int offset,ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void remove(){
      final int offsetAndLastRet;
      final int lastRet;
      if((lastRet=(offsetAndLastRet=this.offsetAndLastRet)&0x1ff)!=0x1ff){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize=this.modCountAndSize,(root=this.root).modCountAndSize);
        root.modCountAndSize=(modCountAndSize+=((1<<9)-1));
        this.modCountAndSize=modCountAndSize;
        final byte b;
        switch((b=(byte)lastRet)>>6){
          case -2:
            root.word0-=(1L<<b);
            break;
          case -1:
            root.word1-=(1L<<b);
            break;
          case 0:
            root.word2-=(1L<<b);
            break;
          default:
            root.word3-=(1L<<b);
        }
        this.offsetAndLastRet=offsetAndLastRet|0x1ff;
        return;
      }
      throw new IllegalStateException();
    }
    private static class Ascending extends CheckedFullItr{
      private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int offsetAndLastRet){
        super(root,modCountAndSize,offsetAndLastRet);
      }
      @Override public boolean hasNext(){
        return (this.offsetAndLastRet>>9)!=128;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int offset;
        if((offset=this.offsetAndLastRet>>9)!=128){
          super.forEachRemainingAscending(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int offset;
        if((offset=this.offsetAndLastRet>>9)!=128){
          super.forEachRemainingAscending(offset,action::accept);
        }
      }
      @Override public Object clone(){
        return new Ascending(this.root,this.modCountAndSize,this.offsetAndLastRet);
      }
      @Override byte nextImpl(ByteSetImpl.Checked root){
        final int offset;
        switch(offset=(this.offsetAndLastRet)>>9){
          case 128:
            throw new NoSuchElementException();
          case Byte.MAX_VALUE:
            this.offsetAndLastRet=0x1007f;//(128<<9)|(Byte.MAX_VALUE)
            return Byte.MAX_VALUE;
          default:
            final byte ret;
            this.offsetAndLastRet=(getThisOrHigher(root,offset+1)<<9)|((ret=(byte)offset)&0x1ff);
            return ret;
        }
      }
    }
    private static class Descending extends CheckedFullItr{
      private Descending(ByteSetImpl.Checked root,int modCountAndSize,int offsetAndLastRet){
        super(root,modCountAndSize,offsetAndLastRet);
      }
      @Override public boolean hasNext(){
        return (this.offsetAndLastRet>>9)!=-129;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final int offset;
        if((offset=this.offsetAndLastRet>>9)!=-129){
          super.forEachRemainingDescending(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final int offset;
        if((offset=this.offsetAndLastRet>>9)!=-129){
          super.forEachRemainingDescending(offset,action::accept);
        }
      }
      @Override public Object clone(){
        return new Descending(this.root,this.modCountAndSize,this.offsetAndLastRet);
      }
      @Override byte nextImpl(ByteSetImpl.Checked root){
        final int offset;
        switch(offset=(this.offsetAndLastRet)>>9){
          case -129:
            throw new NoSuchElementException();
          case Byte.MIN_VALUE:
            this.offsetAndLastRet=0xfffeff80;//(-129<<9)|(0x1ff&((byte)Byte.MIN_VALUE))
            return Byte.MIN_VALUE;
          default:
            final byte ret;
            this.offsetAndLastRet=(getThisOrLower(root,offset-1)<<9)|((ret=(byte)offset)&0x1ff);
            return ret;
        }
      }
    }
  }
}
