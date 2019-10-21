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
import omni.util.ToStringUtil;
import omni.impl.AbstractByteItr;
public class ByteSetImpl extends AbstractByteSet.ComparatorlessImpl implements Externalizable,Cloneable{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  private static final long serialVersionUID=1L;
  public ByteSetImpl(){
    super();
  }
  ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  public ByteSetImpl(ByteSetImpl that){
    this(that.word0,that.word1,that.word2,that.word3);
  }
  public ByteSetImpl(Collection<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfRef<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.ByteOutput<?> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  @Override public Object clone(){
    return new ByteSetImpl(word0,word1,word2,word3);
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
  //TODO serialization methods
  //TODO toString
  //TODO equals
  //TODO hashCode
  @Override public void clear(){
    word0=0;
    word1=0;
    word2=0;
    word3=0;
  }
  @Override public int size(){
    return SetCommonImpl.size(word0,word1,word2,word3);
  }
  @Override public boolean isEmpty(){
    return word0==0 && word1==0 && word2==0 && word3==0;
  }
  @Override public boolean add(byte val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
	    case -2:
	      long word;
       if((word=this.word0)!=(word|=(1L<<(val)))){
         this.word0=word;
         break returnTrue;
       }
       break returnFalse;
	    case -1:
       if((word=this.word1)!=(word|=(1L<<(val)))){
         this.word1=word;
         break returnTrue;
       }
       break returnFalse;
	    case 0:
       if((word=this.word2)!=(word|=(1L<<(val)))){
         this.word2=word;
         break returnTrue;
       }
       break returnFalse;
	    default:
       if((word=this.word3)!=(word|=(1L<<(val)))){
         this.word3=word;
         break returnTrue;
       }
       break returnFalse;
	  }
	  return false;
    }
    return true;
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(word|=(val?0b10:0b01))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean contains(byte val){
    switch(val>>6){
      case -2:
        return
          ((word0)&(1L<<(val)))!=0
          ;
      case -1:
        return
          ((word1)&(1L<<(val)))!=0
          ;
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      default:
        return
          ((word3)&(1L<<(val)))!=0
          ;
    }
  }
  @Override public boolean contains(boolean val){
    return (word2&(val?0b10:0b01))!=0;
  }
  @Override public boolean contains(int val){
    switch(val>>6){
      case -2:
        return
          ((word0)&(1L<<(val)))!=0
          ;
      case -1:
        return
          ((word1)&(1L<<(val)))!=0
          ;
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      case 1:
        return
          ((word3)&(1L<<(val)))!=0
          ;
      default:
        return false; 
    }
  }
  @Override public boolean contains(char val){
    switch(val>>6){
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      case 1:
        return
          ((word3)&(1L<<(val)))!=0
          ;
      default:
        return false;
    }
  }
  @Override public boolean contains(Object val){
    int v;
    if(val instanceof Byte){
      return contains((byte)val);
    }else if(val instanceof Integer || val instanceof Short){
      v=((Number)val).intValue();
    }else if(val instanceof Long){
      long l;
      if((l=(long)val)!=(v=(int)l)){
        return false;
      }
    }else if(val instanceof Float){
      float f;
      if((f=(float)val)!=(v=(int)f)){
        return false;
      }
    }else if(val instanceof Double){
      double d;
      if((d=(double)val)!=(v=(int)d)){
        return false;
      }
    }else if(val instanceof Character){
      return contains((char)val);
    }else if(val instanceof Boolean){
      return contains((boolean)val);
    }else{
      return false;
    }
    return contains(v);
  }
  @Override public boolean contains(long val){
    int v;
    return (v=(int)val)==val && contains(v);
  }
  @Override public boolean contains(float val){
    int v;
    return (v=(int)val)==val && contains(v);
  }
  @Override public boolean contains(double val){
    int v;
    return (v=(int)val)==val && contains(v);
  }
  @Override public boolean removeVal(long val){
    int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean removeVal(float val){
    int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean removeVal(double val){
    int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean removeVal(byte val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
	    case -2:
	      long word;
       if((word=this.word0)!=(word&=(~(1L<<(val))))){
         this.word0=word;
         break returnTrue;
       }
       break returnFalse;
	    case -1:
       if((word=this.word1)!=(word&=(~(1L<<(val))))){
         this.word1=word;
         break returnTrue;
       }
       break returnFalse;
	    case 0:
       if((word=this.word2)!=(word&=(~(1L<<(val))))){
         this.word2=word;
         break returnTrue;
       }
       break returnFalse;
	    default:
       if((word=this.word3)!=(word&=(~(1L<<(val))))){
         this.word3=word;
         break returnTrue;
       }
       break returnFalse;
	  }
	  return false;
    }
    return true;
  }
  @Override public boolean removeVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?0b01:0b10))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
	    case -2:
	      long word;
       if((word=this.word0)!=(word&=(~(1L<<(val))))){
         this.word0=word;
         break returnTrue;
       }
       break returnFalse;
	    case -1:
       if((word=this.word1)!=(word&=(~(1L<<(val))))){
         this.word1=word;
         break returnTrue;
       }
       break returnFalse;
	    case 0:
       if((word=this.word2)!=(word&=(~(1L<<(val))))){
         this.word2=word;
         break returnTrue;
       }
       break returnFalse;
	    case 1:
       if((word=this.word3)!=(word&=(~(1L<<(val))))){
         this.word3=word;
         break returnTrue;
       }
       break returnFalse;
	    default:
	  }
	  return false;
    }
    return true;
  }
  @Override public boolean removeVal(char val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
	    case 0:
	      long word;
       if((word=this.word2)!=(word&=(~(1L<<(val))))){
         this.word2=word;
         break returnTrue;
       }
       break returnFalse;
	    case 1:
       if((word=this.word3)!=(word&=(~(1L<<(val))))){
         this.word3=word;
         break returnTrue;
       }
       break returnFalse;
	    default:
	  }
	  return false;
    }
    return true;
  }
  @Override public boolean remove(Object val){
    int v;
    if(val instanceof Byte){
      return removeVal((byte)val);
    }else if(val instanceof Integer || val instanceof Short){
      v=((Number)val).intValue();
    }else if(val instanceof Long){
      long l;
      if((l=(long)val)!=(v=(int)l)){
        return false;
      }
    }else if(val instanceof Float){
      float f;
      if((f=(float)val)!=(v=(int)f)){
        return false;
      }
    }else if(val instanceof Double){
      double d;
      if((d=(double)val)!=(v=(int)d)){
        return false;
      }
    }else if(val instanceof Character){
      return removeVal((char)val);
    }else if(val instanceof Boolean){
      return removeVal((boolean)val);
    }else{
      return false;
    }
    return removeVal(v);
  }
  private static void consumeWordAscending(long word,int inclLo,int exclHi,ByteConsumer action){
    do{
      if((word&(1L<<inclLo))!=0){
        action.accept((byte)inclLo);
      }
    }while(++inclLo!=exclHi);
  }
  private void forEachAscendingHelper(int numLeft,int offset,ByteConsumer action){
    switch(offset>>6){
      case -2:
        for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(long word=this.word1;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(long word=this.word2;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(long word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
        }
    }
  }
  private void forEachDescendingHelper(int numLeft,int offset,ByteConsumer action){
    switch(offset>>6){
      case 1:
        for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(long word=this.word2;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(long word=this.word1;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(long word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
        }
    }
  }
  private static long removeIfWord(long word,int inclLo,int exclHi,BytePredicate filter){
    do{
      long mask;
      if((word&(mask=1L<<inclLo))!=0){
        if(filter.test((byte)inclLo)){
          word&=(~mask);
        }
      }
    }while(++inclLo!=exclHi);
    return word;
  }
  @Override public void forEach(ByteConsumer action){
    consumeWordAscending(word0,-128,-64,action);
    consumeWordAscending(word1,-64,0,action);
    consumeWordAscending(word2,0,64,action);
    consumeWordAscending(word3,64,128,action);
  }
  @Override public void forEach(Consumer<? super Byte> action){
    forEach((ByteConsumer)action::accept);
  }
  @Override public boolean removeIf(BytePredicate filter){
    long word;
    return (word=this.word0)!=(this.word0=removeIfWord(word,-128,-64,filter)) |
           (word=this.word1)!=(this.word1=removeIfWord(word,-64,0,filter)) |
           (word=this.word2)!=(this.word2=removeIfWord(word,0,64,filter)) |
           (word=this.word3)!=(this.word3=removeIfWord(word,64,128,filter));
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    return removeIf((BytePredicate)filter::test);
  }
  @Override public int hashCode(){
    return wordHashCode(word0,-128,-64) +
           wordHashCode(word1,-64,0) +
           wordHashCode(word2,0,64) +
           wordHashCode(word3,64,128);
  }
  @Override public OmniIterator.OfByte iterator(){
    int offset;
    final int numLeft;
    goToNonEmptyItr:for(;;){
      long word;
	  if((offset=Long.numberOfTrailingZeros(word=word0))!=64){
	      numLeft=SetCommonImpl.size(word,word1,word2,word3);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfTrailingZeros(word=word1))!=128){
	      numLeft=Long.bitCount(word)+Long.bitCount(word2)+Long.bitCount(word3);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfTrailingZeros(word=word2))!=192){
	      numLeft=Long.bitCount(word)+Long.bitCount(word3);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfTrailingZeros(word=word3))!=256){
	      numLeft=Long.bitCount(word);
	      break goToNonEmptyItr;
	  }
	  return EmptyView.EMPTY_ITR;
    }
    return new UncheckedAscendingItr(this,offset-128,numLeft);
  }
  @Override public OmniIterator.OfByte descendingIterator(){
    int offset;
    final int numLeft;
    goToNonEmptyItr:for(;;){
      long word;
	  if((offset=Long.numberOfLeadingZeros(word=word3))!=64){
	      numLeft=SetCommonImpl.size(word0,word1,word2,word);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfLeadingZeros(word=word2))!=128){
	      numLeft=Long.bitCount(word0)+Long.bitCount(word1)+Long.bitCount(word);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfLeadingZeros(word=word1))!=192){
	      numLeft=Long.bitCount(word0)+Long.bitCount(word);
	      break goToNonEmptyItr;
	  }
	  if((offset+=Long.numberOfLeadingZeros(word=word0))!=256){
	      numLeft=Long.bitCount(word);
	      break goToNonEmptyItr;
	  }
	  return EmptyView.EMPTY_ITR;
    }
    return new UncheckedDescendingItr(this,128-offset,numLeft);
  }
  private static int wordHashCode(long word,int inclLo,int inclHi){
    int hash=0;
    do{
      if((word&(1L<<inclLo))!=0){
        hash+=inclLo;
      }
    }while(++inclLo!=inclHi);
    return hash;
  }
  @Override public String toString(){
    final long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
      final byte[] buffer;
      int bufferOffset;
      (buffer=new byte[size*6])[bufferOffset=0]='[';
      done:for(int valOffset=Byte.MIN_VALUE;;){
        do{
          if((word0&(1L<<valOffset))!=0){
            bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
      	}while(++valOffset!=-64);
      	do{
          if((word1&(1L<<valOffset))!=0){
            bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
      	}while(++valOffset!=0);
      	do{
          if((word2&(1L<<valOffset))!=0){
            bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
            if(--size==0){
              break done;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
          }
      	}while(++valOffset!=64);
      	for(;;++valOffset){
          if((word3&(1L<<valOffset))!=0){
            bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
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
  private int uncheckedGetThisOrHigher(int offset){
    switch(offset>>6){
      case -2:
        int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(word0>>>offset))!=64){
          return offset+tail0s;
        }
        offset=-64;
      case -1:
        if((tail0s=Long.numberOfTrailingZeros(word1>>>offset))!=64){
          return offset+tail0s;
        }
        offset=0;
      case 0:
        if((tail0s=Long.numberOfTrailingZeros(word2>>>offset))!=64){
          return offset+tail0s;
        }
        offset=64;
      default:
        return offset+Long.numberOfTrailingZeros(word3>>>offset);
    }
  }
  private int uncheckedGetThisOrLower(int offset){
    switch(offset>>6){
      case 1:
        int lead0s;
        if((lead0s=Long.numberOfLeadingZeros(word3<<(-offset-1)))!=64){
          return offset-lead0s;
        }
        offset=63;
      case 0:
        if((lead0s=Long.numberOfLeadingZeros(word2<<(-offset-1)))!=64){
          return offset-lead0s;
        }
        offset=-1;
      case -1:
        if((lead0s=Long.numberOfLeadingZeros(word1<<(-offset-1)))!=64){
          return offset-lead0s;
        }
        offset=-65;
      default:
        return offset-Long.numberOfLeadingZeros(word0<<(-offset-1));
    }
  }
  private static class UncheckedDescendingItr extends UncheckedAscendingItr{
    private UncheckedDescendingItr(ByteSetImpl root,int offset,int numLeft){
      super(root,offset,numLeft);
    }
    @Override public Object clone(){
      return new UncheckedDescendingItr(root,offset,numLeft);
    }
    @Override public byte nextByte(){
      final var ret=this.offset;
      if(--numLeft==0){
        this.offset=-129;
      }else{
        this.offset=root.uncheckedGetThisOrLower(ret-1);
      }
      return (byte)ret;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachDescendingHelper(numLeft,this.offset,action);
        this.numLeft=0;
        this.offset=-129;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachDescendingHelper(numLeft,this.offset,action::accept);
        this.numLeft=0;
        this.offset=-129;
      }
    }
    @Override public void remove(){
      int offset;
      final var root=this.root;
      switch(((offset=this.offset+1))>>6){
        case -2:
          int tail0s;
          long word;
          if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<(offset))))!=64){
            root.word0=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<(offset))))!=64){
            root.word1=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<(offset))))!=64){
            root.word2=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        default:
          root.word3=(word=root.word3)&(~(1L<<(Long.numberOfTrailingZeros(word&(-1L<<(offset))))));
      }
    }
  }
  private static class UncheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl root;
    transient int offset;
    transient int numLeft;
    private UncheckedAscendingItr(ByteSetImpl root,int offset,int numLeft){
      this.root=root;
      this.offset=offset;
      this.numLeft=numLeft;
    }
    @Override public Object clone(){
      return new UncheckedAscendingItr(root,offset,numLeft);
    }
    @Override public boolean hasNext(){
      return this.numLeft!=0;
    }
    @Override public byte nextByte(){
      final var ret=this.offset;
      if(--numLeft==0){
        this.offset=128;
      }else{
        this.offset=root.uncheckedGetThisOrHigher(ret+1);
      }
      return (byte)ret;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachAscendingHelper(numLeft,this.offset,action);
        this.numLeft=0;
        this.offset=128;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachAscendingHelper(numLeft,this.offset,action::accept);
        this.numLeft=0;
        this.offset=128;
      }
    }
    @Override public void remove(){
      int offset;
      final var root=this.root;
      switch(((offset=this.offset)-1)>>6){
        case 1:
          int lead0s;
          long word;
          if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-offset))))!=64){
            root.word3=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-offset))))!=64){
            root.word2=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-offset))))!=64){
            root.word1=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        default:
          root.word0=(word=root.word0)&(~(Long.MIN_VALUE>>>(Long.numberOfLeadingZeros(word&(-1L>>>(-offset))))));
      }
    }
  }
}
