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
  boolean uncheckedContains(boolean val){
    return wordContains(this.word2,val?2L:1L);
  }
  boolean uncheckedContains(byte val){
    final long word;
    switch(val>>6){
      case -2:
        word=word0;
        break;
      case -1:
        word=word1;
        break;
      case 0:
        word=word2;
        break;
      default:
        word=word3;
    }
    return wordContains(word,1L<<val);
  }
  boolean uncheckedContains(char val){
    final long word;
    switch(val>>6){
      default:
        return false;
      case 0:
        word=word2;
        break;
      case 1:
        word=word3;
    }
    return wordContains(word,1L<<val);
  }
  boolean uncheckedContains(int val){
    final long word;
    switch(val>>6){
      default:
        return false;
      case -2:
        word=word0;
        break;
      case -1:
        word=word1;
        break;
      case 0:
        word=word2;
        break;
      case 1:
        word=word3;
    }
    return wordContains(word,1L<<val);
  }
  boolean uncheckedRemoveVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?~2L:~1L))){
      this.word2=word;
      return true;
    }
    return false;
  }
  boolean uncheckedRemoveVal(byte val){
    returnTrue:for(;;){
      switch(val>>6){
        case -2:
          long word;
          if((word=this.word0)!=(word&=(~(1L<<val)))){
            this.word0=word;
            break returnTrue;
          }
          break;
        case -1:
          if((word=this.word1)!=(word&=(~(1L<<val)))){
            this.word1=word;
            break returnTrue;
          }
          break;
        case 0:
          if((word=this.word2)!=(word&=(~(1L<<val)))){
            this.word2=word;
            break returnTrue;
          }
          break;
        default:
          if((word=this.word3)!=(word&=(~(1L<<val)))){
            this.word3=word;
            break returnTrue;
          }
      }
      return false;
    }
    return true;
  }
  boolean uncheckedRemoveVal(char val){
    returnTrue:for(;;){
      switch(val>>6){
        case 0:
          if((word=this.word2)!=(word&=(~(1L<<val)))){
            this.word2=word;
            break returnTrue;
          }
          break;
        case 1:
          if((word=this.word3)!=(word&=(~(1L<<val)))){
            this.word3=word;
            break returnTrue;
          }
        default:
      }
      return false;
    }
    return true;
  }
  boolean uncheckedRemoveVal(int val){
    returnTrue:for(;;){
      switch(val>>6){
        case -2:
          long word;
          if((word=this.word0)!=(word&=(~(1L<<val)))){
            this.word0=word;
            break returnTrue;
          }
          break;
        case -1:
          if((word=this.word1)!=(word&=(~(1L<<val)))){
            this.word1=word;
            break returnTrue;
          }
          break;
        case 0:
          if((word=this.word2)!=(word&=(~(1L<<val)))){
            this.word2=word;
            break returnTrue;
          }
          break;
        case 1:
          if((word=this.word3)!=(word&=(~(1L<<val)))){
            this.word3=word;
            break returnTrue;
          }
        default:
      }
      return false;
    }
    return true;
  }
  //TODO equals
  void copyToArrayAscending(int size,byte[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,byte[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,byte[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,byte[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  byte[] toByteArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  byte[] toByteArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,short[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,short[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,short[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(short)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,short[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(short)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  short[] toShortArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  short[] toShortArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,int[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,int[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,int[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,int[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  int[] toIntArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  int[] toIntArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,long[] dst){
    done:for(long offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,long[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,long[] dst){
    done:for(long offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,long[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  long[] toLongArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  long[] toLongArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,float[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,float[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,float[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,float[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  float[] toFloatArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  float[] toFloatArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,double[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,double[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,double[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,double[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  double[] toDoubleArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  double[] toDoubleArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void copyToArrayAscending(int size,Object[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,Object[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,Object[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,Object[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayAscending(int size,Byte[] dst){
    done:for(int offset=Byte.MAX_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayAscending(int offset,int size,Byte[] dst){
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  void copyToArrayDescending(int size,Byte[] dst){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          dst[--size]=(Byte)(byte)(offset);
          if(size==0){
            break done;
          }
        }
      }
    }
  }
  void copyToArrayDescending(int offset,int size,Byte[] dst){
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
          if(wordContains(word,1L<<offset)){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              break done;
            }
          }
        }
    }
  }
  Byte[] toArrayAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  Byte[] toArrayDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  <T> T[] toArrayAscending(IntFunction<T[]> arrConstructor){
    int size;
    final long word0,word1,word2,word3;
    final var dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
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
  <T> T[] toArrayDescending(IntFunction<T[]> arrConstructor){
    int size;
    final long word0,word1,word2,word3;
    final var dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
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
  <T> T[] toArrayAscending(T[] dst){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  <T> T[] toArrayDescending(T[] dst){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  int removeIfImplStartWord0Descending(int offset,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
    int numRemoved=0;
    for(var word0=this.word0;;--offset){
      final long mask;
      if(wordContains(word0,mask=1L<<offset)){
        if(filter.test((byte)offset)){
          ++numRemoved;
          word0-=mask;
        }
        if(--size==0){
          modCountChecker.checkModCount();
          if(numRemoved!=0){
            this.word0=word0;
          }
          break;
        }
      }
    }
    return numRemoved;
  }
  int removeIfImplStartWord1Descending(int offset,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
    int numRemoved=0;
    outer:for(;;){
      var word1=this.word1;
      for(;;){
        long mask;
        if(wordContains(word1,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word1-=mask;
          }
          if(--size==0){
            modCountChecker.checkModCount();
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==-65){
          var word0=this.word0;
          for(;;--offset){
            if(wordContains(word0,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word0-=mask;
              }
              if(--size==0){
                modCountChecker.checkModCount();
                if(numRemoved==0){
                  break outer;
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
    return numRemoved;
  }
  int removeIfImplStartWord2Descending(int offset,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
    int numRemoved=0;
    outer:for(;;){
      var word2=this.word2;
      for(;;){
        long mask;
        if(wordContains(word2,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word2-=mask;
          }
          if(--size==0){
            modCountChecker.checkModCount();
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==-1){
          var word1=this.word1;
          for(;;){
            if(wordContains(word1,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word1-=mask;
              }
              if(--size==0){
                modCountChecker.checkModCount();
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(--offset==-65){
              var word0=this.word0;
              for(;;--offset){
                if(wordContains(word0,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word0-=mask;
                  }
                  if(--size==0){
                    modCountChecker.checkModCount();
                    if(numRemoved==0){
                      break outer;
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
    return numRemoved;
  }
  int removeIfImplStartWord3Descending(int offset,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker  modCountChecker){
    int numRemoved=0;
    outer:for(;;){
      var word3=this.word3;
      for(;;){
        long mask;
        if(wordContains(word3,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word3-=mask;
          }
          if(--size==0){
            modCountChecker.checkModCount();
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(--offset==63){
          var word2=this.word2;
          for(;;){
            if(wordContains(word2,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word2-=mask;
              }
              if(--size==0){
                modCountChecker.checkModCount();
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(--offset==-1){
              var word1=this.word1;
              for(;;){
                if(wordContains(word1,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word1-=mask;
                  }
                  if(--size==0){
                    modCountChecker.checkModCount();
                    if(numRemoved==0){
                      break outer;
                    }
                    break;
                  }
                }
                if(--offset==-65){
                  var word0=this.word0;
                  for(;;--offset){
                    if(wordContains(word0,mask=1L<<offset)){
                      if(filter.test((byte)offset)){
                        ++numRemoved;
                        word0-=mask;
                      }
                      if(--size==0){
                        modCountChecker.checkModCount();
                        if(numRemoved==0){
                          break outer;
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
      break;
    }
    return numRemoved;
  }
  int removeIfImplStartWord0Ascending(int offset,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
    int numRemoved=0;
    outer:for(;;){
      var word0=this.word0;
      for(;;){
        long mask;
        if(wordContains(word0,mask=1L<<offset)){
          if(filter.test((byte)offset)){
            ++numRemoved;
            word0-=mask;
          }
          if(--size==0){
            modCountChecker.checkModCount();
            if(numRemoved==0){
              break outer;
            }
            break;
          }
        }
        if(++offset==-64){
          var word1=this.word1;
          for(;;){
            if(wordContains(word1,mask=1L<<offset)){
              if(filter.test((byte)offset)){
                ++numRemoved;
                word1-=mask;
              }
              if(--size==0){
                modCountChecker.checkModCount();
                if(numRemoved==0){
                  break outer;
                }
                break;
              }
            }
            if(++offset==0){
              var word2=this.word2;
              for(;;){
                if(wordContains(word2,mask=1L<<offset)){
                  if(filter.test((byte)offset)){
                    ++numRemoved;
                    word2-=mask;
                  }
                  if(--size==0){
                    modCountChecker.checkModCount();
                    if(numRemoved==0){
                      break outer;
                    }
                    break;
                  }
                }
                if(++offset==64){
                  var word3=this.word3;
                  for(;;++offset){
                    if(wordContains(word3,mask=1L<<offset)){
                      if(filter.test((byte)offset)){
                        ++numRemoved;
                        word3-=mask;
                      }
                      if(--size==0){
                        modCountChecker.checkModCount();
                        if(numRemoved==0){
                          break outer;
                        }
                        break;
                      }
                    }
                  }
                  this.word3=word3;
                  break;
                }
              }
              this.word2=word2;
              break;
            }
          }
          this.word1=word1;
          break;
        }
      }
      this.word0=word0;
      break;
    }
    return numRemoved;
  }
  int toStringAscending(int size,byte[] buffer){
    int bufferOffset=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
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
  int toStringAscending(int offset,int size,byte[] buffer){
    int bufferOffset=0;
    done:switch(offset>>6){
      case -2:
        for(final var word=word0;;){
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
        for(final var word=word1;;){
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
        for(final var word=word2;;){
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
        for(final var word=word3;;++offset){
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
  int toStringDescending(int size,byte[] buffer){
    int bufferOffset=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
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
  int toStringDescending(int offset,int size,byte[] buffer){
    int bufferOffset=0;
    done:switch(offset>>6){
      case 1:
        for(final var word=word3;;){
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
        for(final var word=word2;;){
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
        for(final var word=word1;;){
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
        for(final var word=word0;;--offset){
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
  String toStringAscending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  String toStringDescending(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
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
  void forEachAscending(int size,ByteConsumer action){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  void forEachAscending(int offset,int size,ByteConsumer action){
    done:switch(offset>>6){
    case -2:
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  void forEachDescending(int size,ByteConsumer action){
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  void forEachDescending(int offset,int size,ByteConsumer action){
    done:switch(offset>>6){
    case 1:
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
        if(wordContains(word,1L<<offset)){
          action.accept((byte)offset);
          if(--size==0){
            break done;
          }
        }
      }
    }
  }
  void forEachAscending(ByteConsumer action){
    wordForEachAscending(word0,Byte.MIN_VALUE,action);
    wordForEachAscending(word1,-64,action);
    wordForEachAscending(word2,0,action);
    wordForEachAscending(word3,64,action);
  }
  void forEachDescending(ByteConsumer action){
    wordForEachDescending(word3,Byte.MAX_VALUE,action);
    wordForEachDescending(word2,63,action);
    wordForEachDescending(word1,-1,action);
    wordForEachDescending(word0,-65,action);
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
  int countElementsAscending(int inclHiBound){
    switch(inclHiBound>>6){
      case -2:
        return Long.bitCount(word0&(-1L>>>(-inclHiBound-1)));
      case -1:
        return Long.bitCount(word0)
          +Long.bitCount(word1&(-1L>>>(-inclHiBound-1)));
      case 0:
        return Long.bitCount(word0)
          +Long.bitCount(word1)
          +Long.bitCount(word2&(-1L>>>(-inclHiBound-1)));
      default:
        return Long.bitCount(word0)
          +Long.bitCount(word1)
          +Long.bitCount(word2)
          +Long.bitCount(word3&(-1L>>>(-inclHiBound-1)));
    }
  }
  int countElementsDescending(int inclLoBound){
    switch(inclLoBound>>6){
      case 1:
        return Long.bitCount(word3&(-1L<<inclLoBound));
      case 0:
        return Long.bitCount(word3)
          +Long.bitCount(word2&(-1L<<inclLoBound));
      case -1:
        return Long.bitCount(word3)
          +Long.bitCount(word2)
          +Long.bitCount(word1&(-1L<<inclLoBound));
      default:
        return Long.bitCount(word3)
          +Long.bitCount(word2)
          +Long.bitCount(word1)
          +Long.bitCount(word0&(-1L<<inclLoBound));
    }
  }
  int countElements(int inclLo,int inclHi){
    switch(inclHi>>6){
      case 1:
        int count=0;
        switch(inclLo>>6){
          case -2:
            count+=Long.bitCount(word0&(-1L<<inclLo));
            inclLo=0;
          case -1:
            count+=Long.bitCount(word1&(-1L<<inclLo));
            inclLo=0;
          case 0:
            count+=Long.bitCount(word2&(-1L<<inclLo));
            inclLo=0;
          default:
            return count+Long.bitCount(word3&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        }
      case 0:
        count=0;
        switch(inclLo>>6){
          case -2:
            count+=Long.bitCount(word0&(-1L<<inclLo));
            inclLo=0;
          case -1:
            count+=Long.bitCount(word1&(-1L<<inclLo));
            inclLo=0;
          default:
            return count+Long.bitCount(word2&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
        }
      case -1:
        count=0;
        if(inclLo<-64){
          count+=Long.bitCount(word0&(-1L<<inclLo));
          inclLo=0;
        }
        return count+Long.bitCount(word1&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
      default:
        return Long.bitCount(word0&(-1L<<inclLo)&(-1L>>>(-inclHi-1)));
    }
  }
  int hashCodeAscending(int size){
    int hash=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word0;;){
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
      for(final var word=word1;;){
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
      for(final var word=word2;;){
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
      for(final var word=word3;;++offset){
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
  int hashCodeDescending(int size){
    int hash=0;
    done:for(int offset=Byte.MIN_VALUE;;){
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
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
  int hashCodeDescending(int offset,int size){
    int hash=0;
    done:switch(offset>>6){
    case 1:
      for(final var word=word3;;){
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
      for(final var word=word2;;){
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
      for(final var word=word1;;){
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
      for(final var word=word0;;--offset){
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
  int getThisOrHigher(){
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word0))==64){
      if((tail0s+=Long.numberOfTrailingZeros(word1))==128){
        if((tail0s+=Long.numberOfTrailingZeros(word2))==192){
          tail0s+=Long.numberOfTrailingZeros(word3);
        }
      }
    }
    return tail0s+Byte.MIN_VALUE;
  }
  int getThisOrHigher(int val){
    switch(val>>6){
      case -2:
        if((val=Long.numberOfTrailingZeros(word0&(-1L<<val)))!=64){
          return val-128;
        }
        val=0;
      case -1:
        if((val=Long.numberOfTrailingZeros(word1&(-1L<<val)))!=64){
          return val-64;
        }
        val=0;
      case 0:
        if((val=Long.numberOfTrailingZeros(word2&(-1L<<val)))!=64){
          return val;
        }
        val=0;
      default:
        return Long.numberOfTrailingZeros(word2&(-1L<<val))+64;
    }
  }
  int getThisOrHigher(int bound,int val){
    switch(bound>>6){
      case 1:
        switch(val>>6){
          case -2:
            if((val+=Long.numberOfTrailingZeros(word0>>>val))<-64){
              return val;
            }
            val=-64;
          case -1:
            if((val+=Long.numberOfTrailingZeros(word1>>>val))<0){
              return val;
            }
            val=0;
          case 0:
            if((val+=Long.numberOfTrailingZeros(word2>>>val))<64){
              return val;
            }
            val=64;
          default:
            if((val+=Long.numberOfTrailingZeros(word3>>>val))<=bound){
              return val;
            }
            return 128;
        }
      case 0:
        switch(val>>6){
          case -2:
            if((val+=Long.numberOfTrailingZeros(word0>>>val))<-64){
              return val;
            }
            val=-64;
          case -1:
            if((val+=Long.numberOfTrailingZeros(word1>>>val))<0){
              return val;
            }
            val=0;
          default:
            if((val+=Long.numberOfTrailingZeros(word2>>>val))<=bound){
              return val;
            }
            return 128;
        }
      case -1:
        if(val<-64){
          if((val+=Long.numberOfTrailingZeros(word0>>>val))<-64){
            return val;
          }
          val=-64;
        }
        if((val+=Long.numberOfTrailingZeros(word1>>>val))<=bound){
          return val;
        }
        return 128;
      default:
        if((val+=Long.numberOfTrailingZeros(word0>>>val))<=bound){
          return val;
        }
        return 128;
    }
  }
  int getThisOrLower(){
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word3))==64){
      if((lead0s+=Long.numberOfLeadingZeros(word2))==128){
        if((lead0s+=Long.numberOfLeadingZeros(word1))==192){
          lead0s+=Long.numberOfLeadingZeros(word0);
        }
      }
    }
    return Byte.MAX_VALUE-lead0s;
  }
  int getThisOrLower(int val){
    switch(val>>6){
      case 1:
        if((val=Long.numberOfLeadingZeros(word3&(-1L>>>(-val-1))))!=64){
          return 127-val;
        }
        val=-1;
      case 0:
        if((val=Long.numberOfLeadingZeros(word2&(-1L>>>(-val-1))))!=64){
          return 63-val;
        }
        val=-1;
      case -1:
        if((val=Long.numberOfLeadingZeros(word1&(-1L>>>(-val-1))))!=64){
          return -1-val;
        }
        val=-1;
      default:
        return -65-Long.numberOfLeadingZeros(word0&(-1L>>>(-val-1)));
    }
  }
  int getThisOrLower(int bound,int val){
    switch(bound>>6){
      case -2:
        switch(val>>6){
          case 1:
            if((val-=Long.numberOfLeadingZeros(word3<<(-val-1)))>63){
              return val;
            }
            val=63;
          case 0:
            if((val-=Long.numberOfLeadingZeros(word2<<(-val-1)))>-1){
              return val;
            }
            val=-1;
          case -1:
            if((val-=Long.numberOfLeadingZeros(word1<<(-val-1)))>-65){
              return val;
            }
            val=-65;
          default:
            if((val-=Long.numberOfLeadingZeros(word0<<(-val-1)))>=bound){
              return val;
            }
            return -129;
        }
      case -1:
        switch(val>>6){
          case 1:
            if((val-=Long.numberOfLeadingZeros(word3<<(-val-1)))>63){
              return val;
            }
            val=63;
          case 0:
            if((val-=Long.numberOfLeadingZeros(word2<<(-val-1)))>-1){
              return val;
            }
            val=-1;
          default:
            if((val-=Long.numberOfLeadingZeros(word1<<(-val-1)))>=bound){
              return val;
            }
            return -129;
        }
      case 0:
        if(val>63){
          if((val-=Long.numberOfLeadingZeros(word3<<(-val-1)))>63){
            return val;
          }
          val=63;
        }
        if((val-=Long.numberOfLeadingZeros(word2<<(-val-1)))>=bound){
          return val;
        }
        return -129;
      default:
        if((val-=Long.numberOfLeadingZeros(word3<<(-val-1)))>=bound){
          return val;
        }
        return -129;
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
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
      if(inclusiveTo!=Byte.MAX_VALUE){
        return new UncheckedSubSet.HeadSet.Ascending(this,super.countElementsAscending(inclusiveTo),inclusiveTo);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
      if(inclusiveFrom!=Byte.MIN_VALUE){
        return new UncheckedSubSet.TailSet.Ascending(this,super.countElementsDescending(inclusiveFrom),inclusiveFrom);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
      if(inclusiveFrom<=inclusiveTo){
        if(inclusiveFrom!=Byte.MIN_VALUE){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,super.countElements(inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
          }else{
            return new UncheckedSubSet.TailSet.Ascending(this,super.countElementsDescending(inclusiveFrom),inclusiveFrom);
          }
        }else{
          return headSetAscending(inclusiveTo);
        }
      }else{
        return AbstractByteSet.EmptyView.ASCENDING;
      }
    }
    private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
      if(inclusiveTo!=Byte.MIN_VALUE){
        return new UncheckedSubSet.TailSet.Descending(this,super.countElementsDescending(inclusiveTo),inclusiveTo);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
      if(inclusiveFrom!=Byte.MAX_VALUE){
        return new UncheckedSubSet.HeadSet.Descending(this,super.countElementsAscending(inclusiveFrom),inclusiveFrom);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
      if(inclusiveFrom!=Byte.MAX_VALUE){
        if(inclusiveTo!=Byte.MIN_VALUE){
          return new UncheckedSubSet.BodySet.Descending(this,super.countElements(inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff)); 
        }else{
          return new UncheckedSubSet.HeadSet.Descending(this,super.countElementsAscending(inclusiveFrom),inclusiveFrom);
        }
      }else{
        return headSetDescending(inclusiveTo);
      }
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
        return super.toStringAscending();
      }
      @Override public int firstInt(){
        return super.getThisOrHigher();
      }
      @Override public int lastInt(){
        return super.getThisOrLower();
      }
      @Override public void forEach(ByteConsumer action){
        super.forEachAscending(action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        super.forEachAscending(action::accept);
      }
      @Override public byte[] toByteArray(){
        return super.toByteArrayAscending();
      }
      @Override public short[] toShortArray(){
        return super.toShortArrayAscending();
      }
      @Override public int[] toIntArray(){
        return super.toIntArrayAscending();
      }
      @Override public long[] toLongArray(){
        return super.toLongArrayAscending();
      }
      @Override public float[] toFloatArray(){
        return super.toFloatArrayAscending();
      }
      @Override public double[] toDoubleArray(){
        return super.toDoubleArrayAscending();
      }
      @Override public Byte[] toArray(){
        return super.toArrayAscending();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return super.toArrayAscending(arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return super.toArrayAscending(dst);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return super.headSetAscending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return super.headSetAscending(inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return super.tailSetAscending((int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return super.tailSetAscending(inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return super.subSetAscending((int)fromElement,(int)toElement);
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
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new UncheckedFullView.Descending(this);
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
        return super.toStringDescending();
      }
      @Override public int firstInt(){
        return super.getThisOrLower();
      }
      @Override public int lastInt(){
        return super.getThisOrHigher();
      }
      @Override public void forEach(ByteConsumer action){
        super.forEachDescending(action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        super.forEachDescending(action::accept);
      }
      @Override public byte[] toByteArray(){
        return super.toByteArrayDescending();
      }
      @Override public short[] toShortArray(){
        return super.toShortArrayDescending();
      }
      @Override public int[] toIntArray(){
        return super.toIntArrayDescending();
      }
      @Override public long[] toLongArray(){
        return super.toLongArrayDescending();
      }
      @Override public float[] toFloatArray(){
        return super.toFloatArrayDescending();
      }
      @Override public double[] toDoubleArray(){
        return super.toDoubleArrayDescending();
      }
      @Override public Byte[] toArray(){
        return super.toArrayDescending();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return super.toArrayDescending(arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return super.toArrayDescending(dst);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return super.headSetDescending(inclusiveTo);
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
          return super.tailSetDescending(inclusiveFrom);
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
          return super.subSetDescending(inclusiveFrom,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return super.headSetDescending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return super.tailSetDescending((int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return super.subSetDescending((int)fromElement,(int)toElement);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new UncheckedFullView.Ascending(this);
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
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCountAndSize){
        super(modCountAndSize>>9);
      }
      @Override protected int getActualModCount(){
        return Checked.this.modCountAndSize>>9;
      }
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0  && (size=super.removeIfImplStartWord3Descending(Byte.MAX_VALUE,size,filter,new ModCountChecker(modCountAndSize)))!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-size);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0  && (size=super.removeIfImplStartWord3Descending(Byte.MAX_VALUE,size,filter::test,new ModCountChecker(modCountAndSize)))!=0){
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
        return super.hashCodeDescending(size);
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
    private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
      if(inclusiveTo!=Byte.MAX_VALUE){
        return new CheckedSubSet.HeadSet.Ascending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsAscending(inclusiveTo),inclusiveTo);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
      if(inclusiveFrom!=Byte.MIN_VALUE){
        return new CheckedSubSet.TailSet.Ascending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsDescending(inclusiveFrom),inclusiveFrom);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
      switch(Integer.signum(inclusiveTo+1-inclusiveFrom)){
        case 1:
          if(inclusiveFrom!=Byte.MIN_VALUE){
            if(inclusiveTo!=Byte.MAX_VALUE){
              return new CheckedSubSet.BodySet.Ascending(this,(this.modCountAndSize&(~0x1ff))|super.countElements(inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
            }else{
              return new CheckedSubSet.TailSet.Ascending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsDescending(inclusiveFrom),inclusiveFrom);
            }
          }else{
            return headSetAscending(inclusiveTo);
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        default:
          throw new IllegalArgumentException("out of bounds");
      }
    }
    private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
      if(inclusiveTo!=Byte.MIN_VALUE){
        return new CheckedSubSet.TailSet.Descending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsDescending(inclusiveTo),inclusiveTo);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
      if(inclusiveFrom!=Byte.MAX_VALUE){
        return new CheckedSubSet.HeadSet.Descending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsAscending(inclusiveFrom),inclusiveFrom);
      }else{
        return this;
      }
    }
    private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
      switch(Integer.signum(inclusiveFrom+1-inclusiveTo)){
        case 1:
          if(inclusiveFrom!=Byte.MAX_VALUE){
            if(inclusiveTo!=Byte.MIN_VALUE){
              return new CheckedSubSet.BodySet.Descending(this,(this.modCountAndSize&(~0x1ff))|super.countElements(inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
            }else{
              return new CheckedSubSet.HeadSet.Descending(this,(this.modCountAndSize&(~0x1ff))|super.countElementsAscending(inclusiveFrom),inclusiveFrom);
            }
          }else{
            return headSetDescending(inclusiveTo);
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
        default:
          throw new IllegalArgumentException("out of bounds");
      }
    }
    abstract void forEachImpl(int size,ByteConsumer action);
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      final int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          forEachImpl(size,action);
          super.forEachAscending(size,action);
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
        return super.getThisOrHigher();
      }
      throw new NoSuchElementException();
    }
    @Override public int lastInt(){
      if((modCountAndSize&0x1ff)!=0){
        return super.getThisOrLower();
      }
      throw new NoSuchElementException();
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
        super.forEachAscending(size,action);
      }
      @Override public byte[] toByteArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          super.copyToArrayAscending(size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          super.copyToArrayAscending(size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          super.copyToArrayAscending(size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          super.copyToArrayAscending(size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          super.copyToArrayAscending(size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          super.copyToArrayAscending(size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          super.copyToArrayAscending(size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int modCountAndSize=super.modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,super.modCountAndSize);
        }
        if(size!=0){
          super.copyToArrayAscending(size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        if((size=super.modCountAndSize&0x1ff)!=0){
          super.copyToArrayAscending(size,dst);
        }
        return dst;
      }
      @Override int toStringImpl(int size,byte[] buffer){
        return super.toStringAscending(size,buffer);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return super.headSetAscending(inclusiveTo);
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
          return super.tailSetAscending(inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        }
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
        return new CheckedFullView.Descending(this);
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
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override void forEachImpl(int size,ByteConsumer action){
        super.forEachDescending(size,action);
      }
      @Override public byte[] toByteArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          super.copyToArrayDescending(size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          super.copyToArrayDescending(size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          super.copyToArrayDescending(size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          super.copyToArrayDescending(size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          super.copyToArrayDescending(size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          super.copyToArrayDescending(size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        if((size=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          super.copyToArrayDescending(size,dst=new Byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final int modCountAndSize=super.modCountAndSize,size;
        final T[] dst;
        try{
          dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,super.modCountAndSize);
        }
        if(size!=0){
          super.copyToArrayDescending(size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        if((size=super.modCountAndSize&0x1ff)!=0){
          super.copyToArrayDescending(size,dst);
        }
        return dst;
      }
      @Override int toStringImpl(int size,byte[] buffer){
        return super.toStringDescending(size,buffer);
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
          return super.headSetDescending(inclusiveTo);
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
          return super.tailSetDescending(inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
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
        return new CheckedFullView.Ascending(this);
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
    @Override public void forEach(Consumer<? super Byte> action){
      forEach((ByteConsumer)action::accept);
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Ascending extends UncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Unchecked.Descending root){
        super(root);
      }
      @Override public String toString(){
        return root.toStringAscending();
      }
      @Override public Object clone(){
        return new ByteSetImpl.Unchecked.Ascending(this.root);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return root.headSetAscending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return root.headSetAscending(inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return root.tailSetAscending((int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        int inclusiveFrom=fromElement;
        if(!inclusive){
          ++inclusiveFrom;
        }
        if(inclusiveFrom!=128){
          return root.tailSetAscending(inclusiveFrom);
        }else{
          return AbstractByteSet.EmptyView.ASCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return root.subSetAscending((int)fromElement,(int)toElement);
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
        return root.subSetAscending(inclusiveFrom,inclusiveTo);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Ascending(this.root);
      }
      @Override public byte[] toByteArray(){
        return root.toByteArrayAscending();
      }
      @Override public short[] toShortArray(){
        return root.toShortArrayAscending();
      }
      @Override public int[] toIntArray(){
        return root.toIntArrayAscending();
      }
      @Override public long[] toLongArray(){
        return root.toLongArrayAscending();
      }
      @Override public float[] toFloatArray(){
        return root.toFloatArrayAscending();
      }
      @Override public double[] toDoubleArray(){
        return root.toDoubleArrayAscending();
      }
      @Override public Byte[] toArray(){
        return root.toArrayAscending();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return root.toArrayAscending(arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return root.toArrayAscending(dst);
      }
    }
    private static class Descending extends UncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Unchecked.Ascending root){
        super(root);
      }
      @Override public String toString(){
        return root.toStringDescending();
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
          return root.headSetDescending(inclusiveTo);
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
          return root.tailSetDescending(inclusiveFrom);
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
          return root.subSetDescending(inclusiveFrom,inclusiveTo);
        }else{
          return AbstractByteSet.EmptyView.DESCENDING;
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return root.headSetDescending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return root.tailSetDescending((int)fromElement);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return root.subSetDescending((int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Descending(this.root);
      }
      @Override public byte[] toByteArray(){
        return root.toByteArrayDescending();
      }
      @Override public short[] toShortArray(){
        return root.toShortArrayDescending();
      }
      @Override public int[] toIntArray(){
        return root.toIntArrayDescending();
      }
      @Override public long[] toLongArray(){
        return root.toLongArrayDescending();
      }
      @Override public float[] toFloatArray(){
        return root.toFloatArrayDescending();
      }
      @Override public double[] toDoubleArray(){
        return root.toDoubleArrayDescending();
      }
      @Override public Byte[] toArray(){
        return root.toArrayDescending();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return root.toArrayDescending(arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        return root.toArrayDescending(dst);
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
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      final ByteSetImpl.Checked root;
      if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0  && (size=root.removeIfImplStartWord3Descending(Byte.MAX_VALUE,size,filter,root.new ModCountChecker(modCountAndSize)))!=0){
        root.modCountAndSize=modCountAndSize+((1<<9)-size);
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      int size;
      final ByteSetImpl.Checked root;
      if((size=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0  && (size=root.removeIfImplStartWord3Descending(Byte.MAX_VALUE,size,filter::test,root.new ModCountChecker(modCountAndSize)))!=0){
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
          buffer[modCountAndSize=root.toStringAscending(modCountAndSize,buffer)]=']';
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
            root.forEachAscending(size,action);
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
            root.forEachAscending(size,action::accept);
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
          return root.getThisOrHigher();
        }
        throw new NoSuchElementException();
      }
      @Override public int lastInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return root.getThisOrLower();
        }
        throw new NoSuchElementException();
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          --inclusiveTo;
        }
        if(inclusiveTo!=-129){
          return root.headSetAscending(inclusiveTo);
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
          return root.tailSetAscending(inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveFrom);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return root.headSetAscending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return root.tailSetAscending((int)fromElement);
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
        return root.subSetAscending(inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return root.subSetAscending((int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new SerializationIntermediate(this.root);
      }
      @Override public byte[] toByteArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          root.copyToArrayAscending(size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final short[] dst;
          root.copyToArrayAscending(size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final int[] dst;
          root.copyToArrayAscending(size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final long[] dst;
          root.copyToArrayAscending(size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final float[] dst;
          root.copyToArrayAscending(size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final double[] dst;
          root.copyToArrayAscending(size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          root.copyToArrayAscending(size,dst=new Byte[size]);
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
          root.copyToArrayAscending(size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          root.copyToArrayAscending(size,dst);
        }
        return dst;
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
      @Override public String toString(){
        final ByteSetImpl.Checked root;
        int modCountAndSize;
        if((modCountAndSize=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          (buffer=new byte[modCountAndSize*6])[0]='[';
          buffer[modCountAndSize=root.toStringDescending(modCountAndSize,buffer)]=']';
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
            root.forEachDescending(size,action);
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
            root.forEachDescending(size,action::accept);
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
          return root.getThisOrLower();
        }
        throw new NoSuchElementException();
      }
      @Override public int lastInt(){
        final ByteSetImpl.Checked root;
        if(((root=this.root).modCountAndSize&0x1ff)!=0){
          return root.getThisOrHigher();
        }
        throw new NoSuchElementException();
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        int inclusiveTo=toElement;
        if(!inclusive){
          ++inclusiveTo;
        }
        if(inclusiveTo!=128){
          return root.headSetDescending(inclusiveTo);
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
          return root.tailSetDescending(inclusiveFrom);
        }else{
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        return root.headSetDescending((int)toElement);
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        return root.tailSetDescending((int)fromElement);
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
        return root.subSetDescending(inclusiveFrom,inclusiveTo);
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        return root.subSetDescending((int)fromElement,(int)toElement);
      }
      private Object writeReplace(){
        return new SerializationIntermediate(this.root);
      }
      @Override public byte[] toByteArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          root.copyToArrayDescending(size,dst=new byte[size]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final short[] dst;
          root.copyToArrayDescending(size,dst=new short[size]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final int[] dst;
          root.copyToArrayDescending(size,dst=new int[size]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final long[] dst;
          root.copyToArrayDescending(size,dst=new long[size]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final float[] dst;
          root.copyToArrayDescending(size,dst=new float[size]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final double[] dst;
          root.copyToArrayDescending(size,dst=new double[size]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public Byte[] toArray(){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          root.copyToArrayDescending(size,dst=new Byte[size]);
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
          root.copyToArrayDescending(size,dst);
        }
        return dst;
      }
      @Override public <T> T[] toArray(T[] dst){
        final int size;
        final ByteSetImpl.Checked root;
        if((size=(root=this.root).modCountAndSize&0x1ff)!=0){
          root.copyToArrayDescending(size,dst);
        }
        return dst;
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
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return root.hashCodeDescending(size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        final int inclusiveFrom;
        if((inclusiveFrom=this.inclusiveLo)<=inclusiveTo){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          if(inclusiveTo!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          if(this.inclusiveLo!=inclusiveFrom){
            return new UncheckedSubSet.TailSet.Ascending(this,root.countElementsDescending(inclusiveFrom),inclusiveFrom);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        final int inclusiveTo;
        if((inclusiveTo=this.inclusiveLo)<=inclusiveFrom){
          if(inclusiveFrom!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          if(inclusiveFrom!=Byte.MAX_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          if(this.inclusiveLo!=inclusiveTo){
            return new UncheckedSubSet.TailSet.Descending(this,root.countElementsDescending(inclusiveTo),inclusiveTo);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        if(inclusiveTo!=this.inclusiveLo){
          return new UncheckedSubSet.TailSet.Descending(this,root.countElementsDescending(inclusiveTo),inclusiveTo);
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        if(inclusiveFrom!=this.inclusiveLo){
          return new UncheckedSubSet.TailSet.Ascending(this,root.countElementsDescending(inclusiveFrom),inclusiveFrom);
        }
        return this;
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int inclusiveLo){
          super(root,size,inclusiveLo);
        }
        private Ascending(TailSet parent,int size,int inclusiveLo){
          super(parent,size,inclusiveLo);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachAscending(inclusiveLo,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringAscending(inclusiveLo,size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrHigher(this.inclusiveLo);
        }
        @Override public int lastInt(){
          return root.getThisOrLower();
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
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Unchecked root,int size,int inclusiveLo){
          super(root,size,inclusiveLo);
        }
        private Descending(TailSet parent,int size,int inclusiveLo){
          super(parent,size,inclusiveLo);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachDescending(size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringDescending(size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrLower();
        }
        @Override public int lastInt(){
          return root.getThisOrHigher(this.inclusiveLo);
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
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return root.hashCodeAscending(size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        final int inclusiveTo;
        if((inclusiveTo=this.inclusiveHi)>=inclusiveFrom){
          if(inclusiveFrom!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          if(inclusiveFrom!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          if(this.inclusiveHi!=inclusiveTo){
            return new UncheckedSubSet.HeadSet.Ascending(this,root.countElementsAscending(inclusiveTo),inclusiveTo);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        final int inclusiveFrom;
        if((inclusiveFrom=this.inclusiveHi)>=inclusiveTo){
          if(inclusiveTo!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          if(inclusiveTo!=Byte.MIN_VALUE){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          if(this.inclusiveHi!=inclusiveFrom){
            return new UncheckedSubSet.HeadSet.Descending(this,root.countElementsAscending(inclusiveFrom),inclusiveFrom);
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        if(inclusiveTo!=this.inclusiveHi){
          return new UncheckedSubSet.HeadSet.Ascending(this,root.countElementsAscending(inclusiveTo),inclusiveTo);
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        if(inclusiveFrom!=this.inclusiveHi){
          return new UncheckedSubSet.HeadSet.Descending(this,root.countElementsAscending(inclusiveFrom),inclusiveFrom);
        }
        return this;
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int inclusiveHi){
          super(root,size,inclusiveHi);
        }
        private Ascending(HeadSet parent,int size,int inclusiveHi){
          super(parent,size,inclusiveHi);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachAscending(size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringAscending(size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrHigher();
        }
        @Override public int lastInt(){
          return root.getThisOrLower(this.inclusiveHi);
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
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Unchecked root,int size,int inclusiveHi){
          super(root,size,inclusiveHi);
        }
        private Descending(HeadSet parent,int size,int inclusiveHi){
          super(parent,size,inclusiveHi);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachDescending(inclusiveHi,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringDescending(inclusiveHi,size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrLower(this.inclusiveHi);
        }
        @Override public int lastInt(){
          return root.getThisOrHigher();
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
      @Override public int hashCode(){
        final int size;
        if((size=this.size)!=0){
          return root.hashCodeDescending((byte)(this.boundInfo&0xff),size);
        }
        return 0;
      }
      private OmniNavigableSet.OfByte headSetAscending(int inclusiveTo){
        int boundInfo;
        if(((byte)((boundInfo=this.boundInfo)&0xff))!=inclusiveTo){
          if(inclusiveTo>=(boundInfo>>=8)){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(boundInfo,inclusiveTo),(boundInfo<<8)|(inclusiveTo&0xff));
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte headSetDescending(int inclusiveTo){
        int boundInfo;
        if(inclusiveTo!=(boundInfo=this.boundInfo)>>8){
          if(((byte)(boundInfo&=0xff))>=inclusiveTo){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,boundInfo),(inclusiveTo<<8)|boundInfo);
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetAscending(int inclusiveFrom){
        int boundInfo;
        if(inclusiveFrom!=(boundInfo=this.boundInfo)>>8){
          if(((byte)(boundInfo&=0xff))+1!=inclusiveFrom){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,boundInfo),(inclusiveFrom<<8)|boundInfo);
          }
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte tailSetDescending(int inclusiveFrom){
        int boundInfo;
        if(((byte)((boundInfo=this.boundInfo)&0xff))!=inclusiveFrom){
          if(inclusiveFrom>=(boundInfo>>=8)){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(boundInfo,inclusiveFrom),(boundInfo<<8)|(inclusiveFrom&0xff));
          }
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        return this;
      }
      private OmniNavigableSet.OfByte subSetAscending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveTo>=inclusiveFrom){
          final int boundInfo;
          if(inclusiveFrom!=(boundInfo=this.boundInfo)>>8 || inclusiveTo!=(byte)(boundInfo&0xff)){
            return new UncheckedSubSet.BodySet.Ascending(this,root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      private OmniNavigableSet.OfByte subSetDescending(int inclusiveFrom,int inclusiveTo){
        if(inclusiveFrom>=inclusiveTo){
          final int boundInfo;
          if(inclusiveTo!=(boundInfo=this.boundInfo)>>8 || inclusiveFrom!=(byte)(boundInfo&0xff)){
            return new UncheckedSubSet.BodySet.Descending(this,root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          }
          return this;
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Unchecked root,int size,int boundInfo){
          super(root,size,boundInfo);
        }
        private Ascending(UncheckedSubSet parent,int size,int boundInfo){
          super(parent,size,boundInfo);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachAscending(boundInfo>>8,size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringAscending(boundInfo>>8,size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrHigher(this.boundInfo>>8);
        }
        @Override public int lastInt(){
          return root.getThisOrLower((byte)(this.boundInfo&0xff));
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
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Unchecked root,int size,int boundInfo){
          super(root,size,boundInfo);
        }
        private Descending(UncheckedSubSet parent,int size,int boundInfo){
          super(parent,size,boundInfo);
        }
        @Override void copyToArray(int size,byte[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,short[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,int[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,long[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,float[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,double[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,Byte[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(int size,Object[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void forEachImpl(int size,ByteConsumer action){
          root.forEachDescending((byte)(boundInfo&0xff),size,action);
        }
        @Override int toStringImpl(int size,byte[] buffer){
          return root.toStringDescending((byte)(boundInfo&0xff),size,buffer);
        }
        @Override public int firstInt(){
          return root.getThisOrLower((byte)(this.boundInfo&0xff));
        }
        @Override public int lastInt(){
          return root.getThisOrHigher(this.boundInfo>>8);
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
    private void incrementModCountAndSize(){
      var curr=this;
      do{
        curr.modCountAndSize+=((1<<9)+1);
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpRemove(int modCountDiff){
      for(var curr=parent;curr!=null;curr=curr.parent){
        curr.modCountAndSize+=modCountDiff;
      }
    }
    abstract boolean isInBounds(byte val);
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    abstract int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker);
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        final ByteSetImpl.Checked root;
        if((size=removeIfImpl(root=this.root,size,filter,root.new ModCountChecker(modCountAndSize)))!=0){
          root.modCountAndSize+=(size=(1<<9)-size);
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpRemove(size);
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
        if((size=removeIfImpl(root=this.root,size,filter::test,root.new ModCountChecker(modCountAndSize)))!=0){
          root.modCountAndSize+=(size=(1<<9)-size);
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpRemove(size);
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
      if(!isInBounds(val)){
        throw new IllegalArgumentException("cannot add "+val+" : out of bounds");
      }
    }
    @Override public boolean add(boolean val){
      final long mask;
      if(val){
        checkAddBounds((byte)1);
        mask=1L<<1;
      }else{
        checkAddBounds((byte)1);
        mask=1L<<0;
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
      @Override public boolean contains(boolean val){
        final ByteSetImpl.Checked root;
        final int modCountAndSize;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(root=this.root).modCountAndSize>>9);
        if((modCountAndSize&0x1ff)!=0){
          for(;;){
            final long mask;
            if(val){
              if(1<inclusiveLo){
                break;
              }
              mask=2L;
            }else{
              if(0<inclusiveLo){
                break;
              }
              mask=1L;
            }
            return wordContains(root.word2,mask);
          }
        }
        return false;
      }
      @Override public boolean removeVal(boolean val){
        final ByteSetImpl.Checked root;
        final int modCountAndSize;
        final int rootModCountAndSize;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>9);
        if((modCountAndSize&0x1ff)!=0){
          for(;;){
            final long mask;
            if(val){
              if(1<inclusiveLo){
                break;
              }
              mask=~2L;
            }else{
              if(0<inclusiveLo){
                break;
              }
              mask=~1L;
            }
            long word;
            if((word=root.word2)!=(word&=mask)){
              root.word2=word;
              root.modCountAndSize=rootModCountAndSize+((1<<9)-1);
              this.modCountAndSize=modCountAndSize+((1<<9)-1);
              super.bubbleUpRemove((1<<9)-1);
              return true;
            }
            break;
          }
        }
        return false;
      }
      @Override boolean isInBounds(byte val){
        return val>=inclusiveLo;
      }
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
        return root.removeIfImplStartWord3Descending(Byte.MAX_VALUE,size,filter,modCountChecker);
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return root.hashCodeDescending(modCountAndSize);
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
              return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
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
                return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|root.countElementsDescending(inclusiveFrom),inclusiveFrom);
              case 0:
                if(inclusiveTo!=Byte.MAX_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
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
              return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
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
                return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|root.countElementsDescending(inclusiveTo),inclusiveTo);
              case 0:
                if(inclusiveFrom!=Byte.MAX_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
          case 0:
            if(this.inclusiveLo<=inclusiveTo){
              return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveLo){
          super(root,modCountAndSize,inclusiveLo);
        }
        private Ascending(TailSet parent,int modCountAndSize,int inclusiveLo){
          super(parent,modCountAndSize,inclusiveLo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayAscending(
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachAscending(inclusiveLo,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringAscending(inclusiveLo,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher(inclusiveLo);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower();
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
                return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|root.countElementsDescending(inclusiveFrom),inclusiveFrom);
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
              return new CheckedSubSet.TailSet.Ascending(this,modCountAndSize|root.countElementsDescending(fromElement),fromElement);
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
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveLo){
          super(root,modCountAndSize,inclusiveLo);
        }
        private Descending(TailSet parent,int modCountAndSize,int inclusiveLo){
          super(parent,modCountAndSize,inclusiveLo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayDescending(
          this.inclusiveLo,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachDescending(size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringDescending(size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower();
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher(inclusiveLo);
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
                return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|root.countElementsDescending(inclusiveTo),inclusiveTo);
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
              return new CheckedSubSet.TailSet.Descending(this,modCountAndSize|root.countElementsDescending(toElement),toElement);
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
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
        return root.removeIfImplStartWord0Ascending(Byte.MIN_VALUE,size,filter,modCountChecker);
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return root.hashCodeAscending(modCountAndSize);
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
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
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
                return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|root.countElementsAscending(inclusiveTo),inclusiveTo);
              case 0:
                if(inclusiveFrom!=Byte.MIN_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),inclusiveFrom<<8|(inclusiveTo&0xff));
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
              return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),inclusiveTo<<8|(inclusiveFrom&0xff));
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
                return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|root.countElementsAscending(inclusiveFrom),inclusiveFrom);
              case 0:
                if(inclusiveTo!=Byte.MIN_VALUE){
                  break;
                }
                return this;
              default:
                break throwIAE;
            }
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
          case 0:
            if(inclusiveFrom<=this.inclusiveHi){
              return new AbstractByteSet.EmptyView.Checked.Descending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override boolean isInBounds(byte val){
        return val<=inclusiveHi;
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveHi){
          super(root,modCountAndSize,inclusiveHi);
        }
        private Ascending(HeadSet parent,int modCountAndSize,int inclusiveHi){
          super(parent,modCountAndSize,inclusiveHi);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayAscending(
          this.inclusiveHi,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachAscending(size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringAscending(size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher();
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower(inclusiveHi);
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
                return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|root.countElementsAscending(inclusiveTo),inclusiveTo);
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
              return new CheckedSubSet.HeadSet.Ascending(this,modCountAndSize|root.countElementsAscending(toElement),toElement);
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
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int inclusiveHi){
          super(root,modCountAndSize,inclusiveHi);
        }
        private Descending(HeadSet parent,int modCountAndSize,int inclusiveHi){
          super(parent,modCountAndSize,inclusiveHi);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayDescending(
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachDescending(inclusiveHi,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringDescending(inclusiveHi,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower(inclusiveHi);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher();
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
                return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|root.countElementsAscending(inclusiveFrom),inclusiveFrom);
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
              return new CheckedSubSet.HeadSet.Descending(this,modCountAndSize|root.countElementsAscending(fromElement),fromElement);
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
      @Override boolean isInBounds(byte val){
        final int boundInfo;
        return val>=(boundInfo=this.boundInfo)>>8 && val<=((byte)(boundInfo&0xff));
      }
      @Override public int hashCode(){
        int modCountAndSize;
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
        if((modCountAndSize&=0x1ff)!=0){
          return root.hashCodeDescending((byte)(this.boundInfo&0xff),modCountAndSize);
        }
        return 0;
      }
      @Override int removeIfImpl(ByteSetImpl.Checked root,int size,BytePredicate filter,ByteSetImpl.Checked.ModCountChecker modCountChecker){
        final int inclHi;
        switch((inclHi=(byte)(this.boundInfo&0xff))>>6){
          case 1:
            return root.removeIfImplStartWord3Descending(inclHi,size,filter,modCountChecker);
          case 0:
            return root.removeIfImplStartWord2Descending(inclHi,size,filter,modCountChecker);
          case -1:
            return root.removeIfImplStartWord1Descending(inclHi,size,filter,modCountChecker);
          default:
            return root.removeIfImplStartWord0Descending(inclHi,size,filter,modCountChecker);
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
                return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),(boundInfo&(~0xff))|(inclusiveTo&0xff));
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
                return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|inclusiveFrom);
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
                return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|inclusiveTo);
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
                return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),(boundInfo&(~0xff))|(inclusiveFrom&0xff));
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
            return new CheckedSubSet.BodySet.Ascending(this,modCountAndSize|root.countElements(inclusiveFrom,inclusiveTo),(inclusiveFrom<<8)|(inclusiveTo&0xff));
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
            return new CheckedSubSet.BodySet.Descending(this,modCountAndSize|root.countElements(inclusiveTo,inclusiveFrom),(inclusiveTo<<8)|(inclusiveFrom&0xff));
          case 0:
            if(inclusiveTo>=(boundInfo=this.boundInfo)>>8 && inclusiveFrom<=(byte)(boundInfo&0xff)){
              return new AbstractByteSet.EmptyView.Checked.Ascending(inclusiveTo);
            }
          default:
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Checked root,int modCountAndSize,int boundInfo){
          super(root,modCountAndSize,boundInfo);
        }
        private Ascending(CheckedSubSet parent,int modCountAndSize,int boundInfo){
          super(parent,modCountAndSize,boundInfo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayAscending(
          (byte)(this.boundInfo&0xff),
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachAscending(this.boundInfo>>8,size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringAscending(this.boundInfo>>8,size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher(boundInfo>>8);
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower((byte)(boundInfo&0xff));
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
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Checked root,int modCountAndSize,int boundInfo){
          super(root,modCountAndSize,boundInfo);
        }
        private Descending(CheckedSubSet parent,int modCountAndSize,int boundInfo){
          super(parent,modCountAndSize,boundInfo);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,byte[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,short[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,int[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,long[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,float[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,double[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Byte[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void copyToArray(ByteSetImpl.Checked root,int size,Object[] dst){
          root.copyToArrayDescending(
          this.boundInfo>>8,
          size,dst);
        }
        @Override void forEachImpl(ByteSetImpl.Checked root,int size,ByteConsumer action){
          root.forEachDescending((byte)(this.boundInfo&0xff),size,action);
        }
        @Override int toStringImpl(ByteSetImpl.Checked root,int size,byte[] buffer){
          return root.toStringDescending((byte)(this.boundInfo&0xff),size,buffer);
        }
        @Override int firstIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrLower((byte)(boundInfo&0xff));
        }
        @Override int lastIntImpl(ByteSetImpl.Checked root){
          return root.getThisOrHigher(boundInfo>>8);
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
      }
    }
  }
}
