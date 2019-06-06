package omni.impl.set;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniSet;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.impl.AbstractByteItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
public class ByteSetImpl implements OmniSet.OfByte,Cloneable,Externalizable{
  private static final long serialVersionUID=1L;
    transient long word0;
    transient long word1;
    transient long word2;
    transient long word3;
    ByteSetImpl(){
        super();
    }
    ByteSetImpl(long word0,long word1,long word2,long word3){
        this.word0=word0;
        this.word1=word1;
        this.word2=word2;
        this.word3=word3;
    }
    ByteSetImpl(ByteSetImpl that){
      this.word0=that.word0;
      this.word1=that.word1;
      this.word2=that.word2;
      this.word3=that.word3;
    }
    @Override
    public Object clone(){
        return new ByteSetImpl(this);
    }
    private static int size(long word0,long word1,long word2,long word3){
        return Long.bitCount(word0) + Long.bitCount(word1) + Long.bitCount(word2) + Long.bitCount(word3);
    }
    private static long processWordToString(long word,int valOffset,int valBound,byte[] buffer,long magicWord){
        int bufferOffset=(int)(magicWord >>> 32);
        int numLeft=(int)magicWord;
        do{
            if((word & 1L << valOffset) != 0L){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--numLeft == 0){
                    break;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
            }
        }while(++valOffset != valBound);
        return numLeft | (long)bufferOffset << 32;
    }
    private static long processWordHashCode(long word,int valOffset,int valBound,long magicWord){
        int hash=(int)(magicWord >>> 32);
        int numLeft=(int)magicWord;
        do{
            if((word & 1L << valOffset) != 0L){
                hash+=valOffset;
                if(--numLeft == 0){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return numLeft | (long)hash << 32;
    }
    
    private static String toStringHelper(long word0,long word1,long word2,long word3,int size) {
      final byte[] buffer;
      (buffer=new byte[size * 6])[0]='[';
      long magicWord;
      if((int)(magicWord=processWordToString(word0,Byte.MIN_VALUE,-64,buffer,size)) != 0){
          if((int)(magicWord=processWordToString(word1,-64,0,buffer,magicWord)) != 0){
              if((int)(magicWord=processWordToString(word2,0,64,buffer,magicWord)) != 0){
                  magicWord=processWordToString(word3,64,128,buffer,magicWord);
              }
          }
      }
      buffer[size=(int)(magicWord >>> 32)]=']';
      return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
    }
    private static int hashCodeHelper(long word0,long word1,long word2,long word3,int size) {
      long magicWord;
      if((int)(magicWord=processWordHashCode(word0,Byte.MIN_VALUE,-64,size)) != 0){
          if((int)(magicWord=processWordHashCode(word1,-64,0,magicWord)) != 0){
              if((int)(magicWord=processWordHashCode(word2,0,64,magicWord)) != 0){
                  magicWord=processWordHashCode(word3,64,128,magicWord);
              }
          }
      }
      return (int)(magicWord >>> 32);
    }
    @Override
    public String toString(){
        int size;
        long word0,word1,word2,word3;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
           return toStringHelper(word0,word1,word2,word3,size);
        }
        return "[]";
    }
    @Override
    public int hashCode(){
        int size;
        long word0,word1,word2,word3;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            return hashCodeHelper(word0,word1,word2,word3,size);
        }
        return 0;
    }
    @Override
    public boolean equals(Object val){
        // TODO
        return false;
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
    @Override
    public void clear(){
        word0=0;
        word1=0;
        word2=0;
        word3=0;
    }
    private boolean uncheckedContainsByte(int val){
        long mask=1L << val;
        switch(val >> 6){
        case -2:
            return (this.word0 & mask) != 0;
        case -1:
            return (this.word1 & mask) != 0;
        case 0:
            return (this.word2 & mask) != 0;
        default:
            return (this.word3 & mask) != 0;
        }
    }
    private boolean uncheckedRemoveByte(int val){
        long word,mask=~(1L << val);
        switch(val >> 6){
        case -2:
            return (word=this.word0) != (this.word0=word & mask);
        case -1:
            return (word=this.word1) != (this.word1=word & mask);
        case 0:
            return (word=this.word2) != (this.word2=word & mask);
        default:
            return (word=this.word3) != (this.word3=word & mask);
        }
    }
    @Override
    public boolean contains(Object val){
        if(val instanceof Byte){
            return uncheckedContainsByte((byte)val);
        }else if(val instanceof Integer || val instanceof Short){
            return contains(((Number)val).intValue());
        }else if(val instanceof Long){
            return contains((long)val);
        }else if(val instanceof Float){
            return contains((float)val);
        }else if(val instanceof Double){
            return contains((double)val);
        }else if(val instanceof Character){
            return contains((char)val);
        }else if(val instanceof Boolean){
            return contains((boolean)val);
        }
        return false;
    }
    @Override
    public boolean contains(boolean val){
        return (this.word2 & (val?2L:1L)) != 0;
    }
    @Override
    public boolean contains(byte val){
        return uncheckedContainsByte(val);
    }
    @Override
    public boolean contains(char val){
        switch(val){
        case 0:
            return (this.word2 & 1L << val) != 0;
        case 1:
            return (this.word3 & 1L << val) != 0;
        default:
            return false;
        }
    }
    @Override
    public boolean contains(int val){
        switch(val >> 6){
        case -2:
            return (this.word0 & 1L << val) != 0;
        case -1:
            return (this.word1 & 1L << val) != 0;
        case 0:
            return (this.word2 & 1L << val) != 0;
        case 1:
            return (this.word3 & 1L << val) != 0;
        default:
            return false;
        }
    }
    @Override
    public boolean contains(long val){
        int v;
        return (v=(int)val) == val && contains(v);
    }
    @Override
    public boolean contains(float val){
        int v;
        return (v=(int)val) == val && contains(v);
    }
    @Override
    public boolean contains(double val){
        int v;
        return (v=(int)val) == val && contains(v);
    }
    @Override
    public boolean remove(Object val){
        if(val instanceof Byte){
            return uncheckedRemoveByte((byte)val);
        }else if(val instanceof Integer || val instanceof Short){
            return removeVal(((Number)val).intValue());
        }else if(val instanceof Long){
            return removeVal((long)val);
        }else if(val instanceof Float){
            return removeVal((float)val);
        }else if(val instanceof Double){
            return removeVal((double)val);
        }else if(val instanceof Character){
            return removeVal((char)val);
        }else if(val instanceof Boolean){
            return removeVal((boolean)val);
        }
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        long word;
        return (word=this.word2) != (this.word2=word & (val?~2L:~1L));
    }
    @Override
    public boolean removeVal(byte val){
        return uncheckedRemoveByte(val);
    }
    @Override
    public boolean removeVal(char val){
        long word;
        switch(val){
        case 0:
            return (word=this.word2) != (this.word2=word & ~(1L << val));
        case 1:
            return (word=this.word3) != (this.word3=word & ~(1L << val));
        default:
            return false;
        }
    }
    @Override
    public boolean removeVal(int val){
        long word;
        switch(val >> 6){
        case -2:
            return (word=this.word0) != (this.word0=word & ~(1L << val));
        case -1:
            return (word=this.word1) != (this.word1=word & ~(1L << val));
        case 0:
            return (word=this.word2) != (this.word2=word & ~(1L << val));
        case 1:
            return (word=this.word3) != (this.word3=word & ~(1L << val));
        default:
            return false;
        }
    }
    @Override
    public boolean removeVal(long val){
        int v;
        return (v=(int)val) == val && removeVal(v);
    }
    @Override
    public boolean removeVal(float val){
        int v;
        return (v=(int)val) == val && removeVal(v);
    }
    @Override
    public boolean removeVal(double val){
        int v;
        return (v=(int)val) == val && removeVal(v);
    }
    @Override
    public boolean isEmpty(){
        return word0 == 0 && word1 == 0 && word2 == 0 && word3 == 0;
    }
    @Override
    public int size(){
        return size(word0,word1,word2,word3);
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        long word0,word1,word2,word3;
        int size;
        T[] arr=arrConstructor.apply(size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
        if(size != 0){
            if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                arr[--size]=(T)(Byte)(byte)valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
        }
        return arr;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,Object[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=(byte)valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,Byte[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=(byte)valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,byte[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=(byte)valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,short[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=(short)valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,int[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,long[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,float[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,double[] dst,int dstOffset){
        do{
            if((word & 1L << --valBound) != 0L){
                dst[--dstOffset]=valBound;
                if(dstOffset == 0){
                    break;
                }
            }
        }while(valBound != valOffset);
        return dstOffset;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] dst){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            if((size=processWordCopyToArray(word3,64,128,dst=OmniArray.uncheckedArrResize(size,dst),size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=(T)(Byte)(byte)valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
        }else if(dst.length != 0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public boolean add(boolean val){
        long word;
        return (word=this.word2) != (this.word2=word | (val?2L:1L));
    }
    @Override
    public boolean add(Byte val){
        return add((byte)val);
    }
    private static int processWordForEach(long word,int valOffset,int valBound,ByteConsumer action,int numLeft){
        do{
            if((word & 1L << valOffset) != 0L){
                action.accept((byte)valOffset);
                if(--numLeft == 0){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return numLeft;
    }
    private static int processWordForEach(long word,int valOffset,int valBound,Consumer<? super Byte> action,
            int numLeft){
        do{
            if((word & 1L << valOffset) != 0L){
                action.accept((byte)valOffset);
                if(--numLeft == 0){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return numLeft;
    }
    @Override
    public void forEach(ByteConsumer action){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            if((size=processWordForEach(word0,Byte.MIN_VALUE,-64,action,size)) != 0){
                if((size=processWordForEach(word1,-64,0,action,size)) != 0){
                    if((size=processWordForEach(word2,0,64,action,size)) != 0){
                        int valOffset=64;
                        do{
                            if((word3 & 1L << valOffset) != 0L){
                                action.accept((byte)valOffset);
                                if(--size == 0){
                                    break;
                                }
                            }
                        }while(++valOffset != 128);
                    }
                }
            }
        }
    }
    @Override
    public void forEach(Consumer<? super Byte> action){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            if((size=processWordForEach(word0,Byte.MIN_VALUE,-64,action,size)) != 0){
                if((size=processWordForEach(word1,-64,0,action,size)) != 0){
                    if((size=processWordForEach(word2,0,64,action,size)) != 0){
                        int valOffset=64;
                        do{
                            if((word3 & 1L << valOffset) != 0L){
                                action.accept((byte)valOffset);
                                if(--size == 0){
                                    break;
                                }
                            }
                        }while(++valOffset != 128);
                    }
                }
            }
        }
    }
    private static long processWordRemoveIf(long word,int valOffset,BytePredicate filter){
        long marker=1L;
        for(;;){
            if((word & marker) != 0){
                if(filter.test((byte)valOffset)){
                    word&=~marker;
                }
            }
            if((marker<<=1) == 0){
                return word;
            }
            ++valOffset;
        }
    }
    private static long processWordRemoveIf(long word,int valOffset,Predicate<? super Byte> filter){
        long marker=1L;
        for(;;){
            if((word & marker) != 0){
                if(filter.test((byte)valOffset)){
                    word&=~marker;
                }
            }
            if((marker<<=1) == 0){
                return word;
            }
            ++valOffset;
        }
    }
    @Override
    public boolean removeIf(BytePredicate filter){
        long word;
        return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
                | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
                | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
                | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
    }
    @Override
    public boolean removeIf(Predicate<? super Byte> filter){
        long word;
        return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
                | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
                | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
                | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
    }
    private static class Itr extends AbstractByteItr{
        final ByteSetImpl root;
        int valOffset;
        Itr(ByteSetImpl root){
            this.root=root;
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(root.word0)) != 64){
                this.valOffset=tail0s - 128;
            }else if((tail0s=Long.numberOfTrailingZeros(root.word1)) != 64){
                this.valOffset=tail0s - 64;
            }else if((tail0s=Long.numberOfTrailingZeros(root.word2)) != 64){
                this.valOffset=tail0s;
            }else{
                this.valOffset=Long.numberOfTrailingZeros(root.word3) + 64;
            }
        }
        @Override
        public boolean hasNext(){
            return this.valOffset != 128;
        }
        @Override
        public byte nextByte(){
            int valOffset;
            var ret=(byte)(valOffset=this.valOffset);
            var root=this.root;
            switch(++valOffset >> 6){
            case -2:
                if((valOffset=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                    this.valOffset=valOffset - 128;
                    break;
                }
                valOffset=0;
            case -1:
                if((valOffset=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                    this.valOffset=valOffset - 64;
                    break;
                }
                valOffset=0;
            case 0:
                if((valOffset=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                    this.valOffset=valOffset;
                    break;
                }
                valOffset=0;
            case 1:
                this.valOffset=Long.numberOfTrailingZeros(root.word3 >>> valOffset) + 64;
                break;
            default:
                this.valOffset=128;
            }
            return ret;
        }
        private static void forEachRemainingHelper(long word,int valOffset,ByteConsumer action){
            for(long marker=1L << valOffset;;++valOffset){
                if((word & marker) != 0){
                    action.accept((byte)valOffset);
                }
                if((marker<<=1) == 0){
                    break;
                }
            }
        }
        private static void forEachRemainingHelper(long word,int valOffset,Consumer<? super Byte> action){
            for(long marker=1L << valOffset;;++valOffset){
                if((word & marker) != 0){
                    action.accept((byte)valOffset);
                }
                if((marker<<=1) == 0){
                    break;
                }
            }
        }
        @Override
        public void forEachRemaining(ByteConsumer action){
            var root=this.root;
            int valOffset;
            switch((valOffset=this.valOffset) >> 6){
            case -2:
                forEachRemainingHelper(root.word0,valOffset,action);
                valOffset=-64;
            case -1:
                forEachRemainingHelper(root.word1,valOffset,action);
                valOffset=0;
            case 0:
                forEachRemainingHelper(root.word2,valOffset,action);
                valOffset=64;
            case 1:
                forEachRemainingHelper(root.word3,valOffset,action);
                this.valOffset=128;
            default:
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Byte> action){
            var root=this.root;
            int valOffset;
            switch((valOffset=this.valOffset) >> 6){
            case -2:
                forEachRemainingHelper(root.word0,valOffset,action);
                valOffset=-64;
            case -1:
                forEachRemainingHelper(root.word1,valOffset,action);
                valOffset=0;
            case 0:
                forEachRemainingHelper(root.word2,valOffset,action);
                valOffset=64;
            case 1:
                forEachRemainingHelper(root.word3,valOffset,action);
                this.valOffset=128;
            default:
            }
        }
        @Override
        public void remove(){
          var root=this.root;
          long word;
          int valOffset;
          switch(((valOffset=this.valOffset)-1)>>6) {
          case 1:
            if((valOffset=Long.numberOfLeadingZeros((word=root.word3)<<-valOffset))!=64) {
              root.word3=(word&~(1L<<(-1-valOffset)));
              return;
            }
            valOffset=0;
          case 0:
            if((valOffset=Long.numberOfLeadingZeros((word=root.word2)<<-valOffset))!=64) {
              root.word2=(word&~(1L<<(-1-valOffset)));
              return;
            }
            valOffset=0;
          case -1:
            if((valOffset=Long.numberOfLeadingZeros((word=root.word1)<<-valOffset))!=64) {
              root.word1=(word&~(1L<<(-1-valOffset)));
              return;
            }
            valOffset=0;
          default:
            root.word0=((word=root.word0)&~(1L<<(-1-(Long.numberOfLeadingZeros(word<<-valOffset)))));
          }
        }
    }
    @Override
    public omni.api.OmniIterator.OfByte iterator(){
        return new Itr(this);
    }
    @Override
    public Byte[] toArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            Byte[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new Byte[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=(byte)valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override
    public byte[] toByteArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            byte[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new byte[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=(byte)valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public double[] toDoubleArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            double[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new double[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            float[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new float[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            long[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new long[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            int[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new int[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public short[] toShortArray(){
        long word0,word1,word2,word3;
        int size;
        if((size=size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
            short[] dst;
            if((size=processWordCopyToArray(word3,64,128,dst=new short[size],size)) != 0){
                if((size=processWordCopyToArray(word2,0,64,dst,size)) != 0){
                    if((size=processWordCopyToArray(word1,-64,0,dst,size)) != 0){
                        int valBound=-64;
                        do{
                            if((word0 & 1L << --valBound) != 0L){
                                dst[--size]=(short)valBound;
                                if(size == 0){
                                    break;
                                }
                            }
                        }while(valBound != Byte.MIN_VALUE);
                    }
                }
            }
            return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public boolean add(byte val){
        long word,mask=1L << val;
        switch(val >> 6){
        case -2:
            return (word=this.word0) != (this.word0=word | mask);
        case -1:
            return (word=this.word1) != (this.word1=word | mask);
        case 0:
            return (word=this.word2) != (this.word2=word | mask);
        default:
            return (word=this.word3) != (this.word3=word | mask);
        }
    }
    private static class Checked extends ByteSetImpl{
      int modCount;
      int size;
      Checked(){
        super();
      }
      Checked(long word0,long word1,long word2,long word3){
        super(word0,word1,word2,word3);
        this.size=size(word0,word1,word2,word3);
      }
      Checked(long word0,long word1,long word2,long word3,int size){
        super(word0,word1,word2,word3);
        this.size=size;
      }
      Checked(Checked that){
        super(that.word0,that.word1,that.word2,that.word3);
        this.size=that.size;
      }
      @Override public Object clone() {
        return new Checked(this);
      }
      @Override public String toString() {
        int size;
        if((size=this.size)!=0) {
          return toStringHelper(word0,word1,word2,word3,size);
        }
        return "[]";
      }
      @Override public int hashCode() {
        int size;
        if((size=this.size)!=0) {
          return hashCodeHelper(word0,word1,word2,word3,size);
        }
        return 0;
      }
      @Override public boolean equals(Object val){
        // TODO Auto-generated method stub
        return super.equals(val);
      }
      @Override public void writeExternal(ObjectOutput out) throws IOException{
        int modCount=this.modCount;
        try {
          super.writeExternal(out);
        }finally {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      @Override public void readExternal(ObjectInput in) throws IOException{
          this.size=size(word0=in.readLong(),word1=in.readLong(),word2=in.readLong(),word3=in.readLong());
      }
      @Override public void clear(){
        if(this.size!=0) {
          this.word0=0;
          this.word0=2;
          this.word0=0;
          this.word0=0;
        }
      }
      @Override public boolean contains(Object val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(boolean val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(byte val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(char val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(int val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(long val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(float val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean contains(double val){
        // TODO Auto-generated method stub
        return super.contains(val);
      }
      @Override public boolean remove(Object val){
        // TODO Auto-generated method stub
        return super.remove(val);
      }
      @Override public boolean removeVal(boolean val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(byte val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(char val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(int val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(long val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(float val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean removeVal(double val){
        // TODO Auto-generated method stub
        return super.removeVal(val);
      }
      @Override public boolean isEmpty(){
        // TODO Auto-generated method stub
        return super.isEmpty();
      }
      @Override public int size(){
        // TODO Auto-generated method stub
        return super.size();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        // TODO Auto-generated method stub
        return super.toArray(arrConstructor);
      }
      @Override public <T> T[] toArray(T[] dst){
        // TODO Auto-generated method stub
        return super.toArray(dst);
      }
      @Override public boolean add(boolean val){
        // TODO Auto-generated method stub
        return super.add(val);
      }
      @Override public boolean add(Byte val){
        // TODO Auto-generated method stub
        return super.add(val);
      }
      @Override public void forEach(ByteConsumer action){
        // TODO Auto-generated method stub
        super.forEach(action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        // TODO Auto-generated method stub
        super.forEach(action);
      }
      @Override public boolean removeIf(BytePredicate filter){
        // TODO Auto-generated method stub
        return super.removeIf(filter);
      }
      @Override public boolean removeIf(Predicate<? super Byte> filter){
        // TODO Auto-generated method stub
        return super.removeIf(filter);
      }
      @Override public omni.api.OmniIterator.OfByte iterator(){
        // TODO Auto-generated method stub
        return super.iterator();
      }
      @Override public Byte[] toArray(){
        // TODO Auto-generated method stub
        return super.toArray();
      }
      @Override public byte[] toByteArray(){
        // TODO Auto-generated method stub
        return super.toByteArray();
      }
      @Override public double[] toDoubleArray(){
        // TODO Auto-generated method stub
        return super.toDoubleArray();
      }
      @Override public float[] toFloatArray(){
        // TODO Auto-generated method stub
        return super.toFloatArray();
      }
      @Override public long[] toLongArray(){
        // TODO Auto-generated method stub
        return super.toLongArray();
      }
      @Override public int[] toIntArray(){
        // TODO Auto-generated method stub
        return super.toIntArray();
      }
      @Override public short[] toShortArray(){
        // TODO Auto-generated method stub
        return super.toShortArray();
      }
      @Override public boolean add(byte val){
        // TODO Auto-generated method stub
        return super.add(val);
      }
      
    }
    
}
