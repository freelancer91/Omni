package omni.impl.set;
import omni.api.OmniSet;
import java.io.Externalizable;
import java.io.Serializable;
import omni.impl.CheckedCollection;
import omni.function.BytePredicate;
import java.util.function.Predicate;
import omni.function.ByteConsumer;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.Set;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.util.Collection;
import omni.api.OmniCollection;
public class ByteSetImpl extends AbstractByteSet implements Externalizable,Cloneable{
  //TODO equals methods
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  private static final long serialVersionUID=1L;
  public ByteSetImpl(){
    super();
  }
  public ByteSetImpl(ByteSetImpl that){
    super();
    word0=that.word0;
    word1=that.word1;
    word2=that.word2;
    word3=that.word3;
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
  public ByteSetImpl(OmniCollection.ByteOutput<?> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  @Override public Object clone(){
    return new ByteSetImpl(this);
  }
  ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  @Override public int hashCode(){
    return ByteSetImpl.hashCodeForWord(word0,Byte.MIN_VALUE,ByteSetImpl.hashCodeForWord(word1,-64,ByteSetImpl.hashCodeForWord(word2,0,ByteSetImpl.hashCodeForWord(word2,64,0))));  
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
  @Override public void clear(){
    this.word0=0;
    this.word1=0;
    this.word2=0;
    this.word3=0; 
  }
  @Override public boolean isEmpty(){
    return word0==0 && word1==0 && word2==0 && word3==0;
  }
  @Override public int size(){
    return SetCommonImpl.size(word0,word1,word2,word3);
  }
  @Override public boolean add(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean add(byte val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean removeIf(BytePredicate filter){
    long word;
    return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
      | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
      | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
      | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    long word;
    return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
      | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
      | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
      | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
  }
  @Override public String toString(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public void forEach(ByteConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public void forEach(Consumer<? super Byte> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public OmniIterator.OfByte iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Byte[] toArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte[] toByteArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short[] toShortArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int[] toIntArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long[] toLongArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float[] toFloatArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double[] toDoubleArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public <T> T[] toArray(T[] dst){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public void writeExternal(ObjectOutput output) throws IOException{
    output.writeLong(word0);
    output.writeLong(word1);
    output.writeLong(word2);
    output.writeLong(word3);
  }
  @Override public void readExternal(ObjectInput input) throws IOException{
    word0=input.readLong();
    word1=input.readLong();
    word2=input.readLong();
    word3=input.readLong();
  }
  private static int hashCodeForWord(long word,int offset,int sum){
    for(;;){
      int tail0s;
      if((tail0s=Long.numberOfTrailingZeros(word))==64){
        return sum;
      }
      offset+=tail0s;
      sum+=offset;
      word>>>=(1+tail0s);
      ++offset;
    }
  }
  private int removeIfHelper(int inclusiveFrom,BytePredicate filter,int exclusiveTo){
    //TODO make this more concise
    //TODO write a checked version
    //TODO write head set and tail set versions
    long word;
    switch(inclusiveFrom>>6){
      case -2:
        switch(exclusiveTo>>6){
          case -2:
            return Long.bitCount(((word=this.word0)^(this.word0=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
          case -1:
            return Long.bitCount(((word=this.word0)&(this.word0=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount(((word=this.word1)&(this.word1=processSubSetWordRemoveIf(word,-64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
          case 0:
            return Long.bitCount(((word=this.word0)&(this.word0=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount((word=this.word1)&(this.word1=processWordRemoveIf(word,-64,filter)))
            + Long.bitCount(((word=this.word2)&(this.word2=processSubSetWordRemoveIf(word,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
          default:
            return Long.bitCount(((word=this.word0)&(this.word0=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount((word=this.word1)&(this.word1=processWordRemoveIf(word,-64,filter)))
            + Long.bitCount((word=this.word2)&(this.word2=processWordRemoveIf(word,0,filter)))
            + Long.bitCount(((word=this.word3)&(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
        }
      case -1:
        switch(exclusiveTo>>6){
          case -1:
            return Long.bitCount(((word=this.word1)^(this.word1=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
          case 0:
            return Long.bitCount(((word=this.word1)&(this.word1=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount(((word=this.word2)&(this.word2=processSubSetWordRemoveIf(word,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
          default:
            return Long.bitCount(((word=this.word1)&(this.word1=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount((word=this.word2)&(this.word2=processWordRemoveIf(word,0,filter)))
            + Long.bitCount(((word=this.word3)&(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
        }
      case 0:
        if(exclusiveTo>>6==0){
          return Long.bitCount(((word=this.word2)^(this.word2=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
        }else{
          return Long.bitCount(((word=this.word2)&(this.word2=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount(((word=this.word3)&(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
        }
      default:
        return Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
    }
  }
    private static long processWordRemoveIf(long word,int valOffset,BytePredicate filter){
      long marker=1L<<valOffset;
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
    private static long processSubSetWordRemoveIf(long word,int valOffset,int valBound,BytePredicate filter){
      long marker=1L<<valOffset;
      for(;;){
        if((word & marker) != 0){
          if(filter.test((byte)valOffset)){
            word&=~marker;
          }
        }
        if(++valOffset==valBound){
          return word;
        }
        marker<<=1;
      }
    }
    private static long processWordRemoveIf(long word,int valOffset,Predicate<? super Byte> filter){
      long marker=1L<<valOffset;
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
    private static long processSubSetWordRemoveIf(long word,int valOffset,int valBound,Predicate<? super Byte> filter){
      long marker=1L<<valOffset;
      for(;;){
        if((word & marker) != 0){
          if(filter.test((byte)valOffset)){
            word&=~marker;
          }
        }
        if(++valOffset==valBound){
          return word;
        }
        marker<<=1;
      }
    }
  public static class Checked extends ByteSetImpl{
    transient int modCountAndSize;
    private static final long serialVersionUID=1L;
    public Checked(){
      super();
    }
    public Checked(ByteSetImpl that){
      super(that);
      modCountAndSize=SetCommonImpl.size(word0,word1,word2,word3);
    }
    public Checked(Collection<? extends Byte> that){
      super(that);
    }
    public Checked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    public Checked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    public Checked(OmniCollection.OfByte that){
      super(that);
    }
    public Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    @Override public Object clone(){
      return new Checked(this);
    }
    public Checked(ByteSetImpl.Checked that){
      super(that);
      modCountAndSize=that.modCountAndSize&0x1ff;
    }
    Checked(int size,long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
      this.modCountAndSize=size;
    }
    @Override public int hashCode(){
      int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        size=ByteSetImpl.Checked.hashCodeForWord(word0,Byte.MIN_VALUE,size);
        if((size&0x1ff)!=0){
          size=ByteSetImpl.Checked.hashCodeForWord(word1,-64,size);
          if((size&0x1ff)!=0){
            size=ByteSetImpl.Checked.hashCodeForWord(word2,0,size);
            int numLeft;
            if((numLeft=(size&0x1ff))!=0){
              long word=word3;
              size>>=9;
              int offset=64;
              for(;;){
                int tail0s;
                size+=(offset+=(tail0s=Long.numberOfTrailingZeros(word)));
                if(--numLeft==0){
                  return size;
                }
                word>>>=(++tail0s);
                ++offset;
              }
            }
          }
        }
        return size>>9;
      }
      return 0;
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
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=(modCountAndSize+(1<<9))&0xfffffe00;
      }
    }
    @Override public boolean isEmpty(){
      return (this.modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      return (this.modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0) {
        long word,newWord0,newWord1,newWord2,newWord3;
        int numRemoved;
        try {
          numRemoved=Long.bitCount((word=this.word0)^(newWord0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)))
            +Long.bitCount((word=this.word1)^(newWord1=processWordRemoveIf(word,-64,filter)))
            +Long.bitCount((word=this.word2)^(newWord2=processWordRemoveIf(word,0,filter)))
            +Long.bitCount((word=this.word3)^(newWord3=processWordRemoveIf(word,64,filter)));
        }finally {
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(numRemoved!=0) {
          this.word0=newWord0;
          this.word1=newWord1;
          this.word2=newWord2;
          this.word3=newWord3;
          this.modCountAndSize=modCountAndSize-numRemoved+(1<<9);
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0) {
        long word,newWord0,newWord1,newWord2,newWord3;
        int numRemoved;
        try {
          numRemoved=Long.bitCount((word=this.word0)^(newWord0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)))
            +Long.bitCount((word=this.word1)^(newWord1=processWordRemoveIf(word,-64,filter)))
            +Long.bitCount((word=this.word2)^(newWord2=processWordRemoveIf(word,0,filter)))
            +Long.bitCount((word=this.word3)^(newWord3=processWordRemoveIf(word,64,filter)));
        }finally {
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(numRemoved!=0) {
          this.word0=newWord0;
          this.word1=newWord1;
          this.word2=newWord2;
          this.word3=newWord3;
          this.modCountAndSize=modCountAndSize-numRemoved+(1<<9);
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void writeExternal(ObjectOutput output) throws IOException{
      final int modCountAndSize=this.modCountAndSize;
      try{
        output.writeShort(modCountAndSize);
        int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          //only write as many words as we need to
          //start by writing the positive number since those will probably be more common
          long word;
          output.writeLong(word=this.word2);
          if((numLeft-=Long.bitCount(word))!=0){
            output.writeLong(word=this.word3);
            if((numLeft-=Long.bitCount(word))!=0){
              output.writeLong(word=this.word1);
              if((numLeft-=Long.bitCount(word))!=0){
                output.writeLong(this.word0);
              }
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
    }
    @Override public void readExternal(ObjectInput input) throws IOException{
      int numLeft;
      this.modCountAndSize=numLeft=input.readUnsignedShort();
      if((numLeft&=0x1ff)!=0){
        long word;
        word2=word=input.readLong();
        if((numLeft-=Long.bitCount(word))!=0){
          word3=word=input.readLong();
          if((numLeft-=Long.bitCount(word))!=0){
            word1=word=input.readLong();
            if((numLeft-=Long.bitCount(word))!=0){
              word0=input.readLong();
            }
          }
        }
      }
    }
    private static int hashCodeForWord(long word,int offset,int sumAndNumLeft){
      int tail0s;
      if((tail0s=Long.numberOfTrailingZeros(word))==64){
        return sumAndNumLeft;
      }
      int numLeft=sumAndNumLeft&0x1ff;
      sumAndNumLeft&=0xfffffe00;
      for(;;){
        sumAndNumLeft+=((offset+=tail0s)<<9);
        if(--numLeft==0){
          return sumAndNumLeft;
        }
        if((tail0s=Long.numberOfTrailingZeros(word>>>=(++tail0s)))==64){
          return sumAndNumLeft|numLeft;
        }
        ++offset;
      }
    }
    public static class Descending extends Checked{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
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
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      Descending(int size,long word0,long word1,long word2,long word3){
        super(size,word0,word1,word2,word3);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  public static class Descending extends ByteSetImpl{
    private static final long serialVersionUID=1L;
    public Descending(){
      super();
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
    public Descending(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    public Descending(OmniCollection.OfByte that){
      super(that);
    }
    public Descending(OmniCollection.OfBoolean that){
      super(that);
    }
    @Override public Object clone(){
      return new Descending(this);
    }
    Descending(long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedFullView extends AbstractByteSet implements Cloneable,Serializable{
    private static final long serialVersionUID=1L;
    transient final ByteSetImpl root;
    private UncheckedFullView(ByteSetImpl root){
      this.root=root;
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
    @Override public void clear(){
      final ByteSetImpl root;
      (root=this.root).word0=0;
      root.word1=0;
      root.word2=0;
      root.word3=0;  
    }
    @Override public boolean isEmpty(){
      final ByteSetImpl root;
      return (root=this.root).word0==0 && root.word1==0 && root.word2==0 && root.word3==0;
    }
    @Override public int size(){
      final ByteSetImpl root;
      return SetCommonImpl.size((root=this.root).word0,root.word1,root.word2,root.word3);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf(filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf(filter);
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private Object writeReplace(){
      return new ByteSetImpl(root);
    }
    private static class Descending extends UncheckedFullView{
      private Descending(ByteSetImpl root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Descending(root);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedFullView extends AbstractByteSet implements Cloneable,Serializable{
    private static final long serialVersionUID=1L;
    transient final ByteSetImpl.Checked root;
    private CheckedFullView(ByteSetImpl.Checked root){
      this.root=root;
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
    @Override public void clear(){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){  
        root.modCountAndSize=(modCountAndSize+(1<<9))&0xfffffe00;
      }
    }
    @Override public boolean isEmpty(){
      return (root.modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      return (root.modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf(filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf(filter);
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private Object writeReplace(){
      return new ByteSetImpl.Checked(root);
    }
    private static class Descending extends CheckedFullView{
      private Descending(final ByteSetImpl.Checked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Checked.Descending(root);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private abstract static class AbstractUncheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl root;
    transient final AbstractUncheckedSubSet parent;
    transient final int boundInfo;
    transient int size;
    private AbstractUncheckedSubSet(ByteSetImpl root,int boundInfo,int size){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    private AbstractUncheckedSubSet(AbstractUncheckedSubSet parent,int boundInfo,int size){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.size=size;
    }
  }
  private abstract static class AbstractCheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl.Checked root;
    transient final AbstractCheckedSubSet parent;
    transient final int boundInfo;
    transient int modCountAndSize;
    private AbstractCheckedSubSet(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    private AbstractCheckedSubSet(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
  }
  private static class UncheckedHeadView extends AbstractUncheckedSubSet{
    private UncheckedHeadView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedHeadView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        this.size=0;
        for(var parent=this.parent;parent!=null;parent=parent.parent){
          parent.size-=size;
        }
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                root.word0&=(-1L<<boundInfo);
                break goToEnd;
              case -1:
                root.word1&=(-1L<<boundInfo);
                break goToWord0;
              case 0:
                root.word2&=(-1L<<boundInfo);
                break goToWord1;
              default:
                root.word3&=(-1L<<boundInfo);
              }
              root.word2=0;
              break;
            }
            root.word1=0;
            break;
          }
          root.word0=0;
          break;
        }
      }
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        long word;
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                numRemoved+=Long.bitCount(((word=root.word0)^(root.word0=processSubSetWordRemoveIf(word,Byte.MIN_VALUE,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToEnd;
              case -1:
                numRemoved+=Long.bitCount(((word=root.word1)^(root.word1=processSubSetWordRemoveIf(word,-64,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToWord0;
              case 0:
                numRemoved+=Long.bitCount(((word=root.word2)^(root.word2=processSubSetWordRemoveIf(word,0,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToWord1;
              default:
                numRemoved+=Long.bitCount(((word=root.word3)^(root.word3=processSubSetWordRemoveIf(word,64,boundInfo,filter)))&(-1L>>>-boundInfo));
              }
              numRemoved+=Long.bitCount((word=root.word2)^(root.word2=processWordRemoveIf(word,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount((word=root.word1)^(root.word1=processWordRemoveIf(word,-64,filter)));
            break;
          }
          numRemoved+=Long.bitCount((word=root.word0)^(root.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)));
          break;
        }
        if(numRemoved!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        long word;
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                numRemoved+=Long.bitCount(((word=root.word0)^(root.word0=processSubSetWordRemoveIf(word,Byte.MIN_VALUE,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToEnd;
              case -1:
                numRemoved+=Long.bitCount(((word=root.word1)^(root.word1=processSubSetWordRemoveIf(word,-64,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToWord0;
              case 0:
                numRemoved+=Long.bitCount(((word=root.word2)^(root.word2=processSubSetWordRemoveIf(word,0,boundInfo,filter)))&(-1L>>>-boundInfo));
                break goToWord1;
              default:
                numRemoved+=Long.bitCount(((word=root.word3)^(root.word3=processSubSetWordRemoveIf(word,64,boundInfo,filter)))&(-1L>>>-boundInfo));
              }
              numRemoved+=Long.bitCount((word=root.word2)^(root.word2=processWordRemoveIf(word,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount((word=root.word1)^(root.word1=processWordRemoveIf(word,-64,filter)));
            break;
          }
          numRemoved+=Long.bitCount((word=root.word0)^(root.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)));
          break;
        }
        if(numRemoved!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedHeadView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedHeadView extends AbstractCheckedSubSet{
    private CheckedHeadView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedHeadView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&0xfffffe00,((root=this.root).modCountAndSize)&0xfffffe00);
      if(((modCountAndSize&=0x1ff))!=0){
        root.modCountAndSize+=(modCountAndSize=(1<<9)-modCountAndSize);
        AbstractCheckedSubSet curr=this;
        do{
          curr.modCountAndSize+=modCountAndSize;
        }while((curr=curr.parent)!=null);
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                root.word0&=(-1L<<boundInfo);
                break goToEnd;
              case -1:
                root.word1&=(-1L<<boundInfo);
                break goToWord0;
              case 0:
                root.word2&=(-1L<<boundInfo);
                break goToWord1;
              default:
                root.word3&=(-1L<<boundInfo);
              }
              root.word2=0;
              break;
            }
            root.word1=0;
            break;
          }
          root.word0=0;
          break;
        }
      }
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved=0;
        try{
          goToEnd:for(;;){
            goToWord0:for(;;){
              goToWord1:for(;;){
                final int boundInfo;
                switch((boundInfo=this.boundInfo)>>6){
                case -2:
                  numRemoved+=Long.bitCount(((newWord0)^(newWord0=processSubSetWordRemoveIf(newWord0,Byte.MIN_VALUE,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToEnd;
                case -1:
                  numRemoved+=Long.bitCount(((newWord1)^(newWord1=processSubSetWordRemoveIf(newWord1,-64,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToWord0;
                case 0:
                  numRemoved+=Long.bitCount(((newWord2)^(newWord2=processSubSetWordRemoveIf(newWord2,0,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToWord1;
                default:
                  numRemoved+=Long.bitCount(((newWord3)^(newWord3=processSubSetWordRemoveIf(newWord3,64,boundInfo,filter)))&(-1L>>>-boundInfo));
                }
                numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
                break;
              }
              numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord0^(newWord0=processWordRemoveIf(newWord0,Byte.MIN_VALUE,filter)));
            break;
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved=0;
        try{
          goToEnd:for(;;){
            goToWord0:for(;;){
              goToWord1:for(;;){
                final int boundInfo;
                switch((boundInfo=this.boundInfo)>>6){
                case -2:
                  numRemoved+=Long.bitCount(((newWord0)^(newWord0=processSubSetWordRemoveIf(newWord0,Byte.MIN_VALUE,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToEnd;
                case -1:
                  numRemoved+=Long.bitCount(((newWord1)^(newWord1=processSubSetWordRemoveIf(newWord1,-64,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToWord0;
                case 0:
                  numRemoved+=Long.bitCount(((newWord2)^(newWord2=processSubSetWordRemoveIf(newWord2,0,boundInfo,filter)))&(-1L>>>-boundInfo));
                  break goToWord1;
                default:
                  numRemoved+=Long.bitCount(((newWord3)^(newWord3=processSubSetWordRemoveIf(newWord3,64,boundInfo,filter)))&(-1L>>>-boundInfo));
                }
                numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
                break;
              }
              numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord0^(newWord0=processWordRemoveIf(newWord0,Byte.MIN_VALUE,filter)));
            break;
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedHeadView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class UncheckedTailView extends AbstractUncheckedSubSet{
    private UncheckedTailView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedTailView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        long word;
        goToEnd:for(;;){
          goToWord3:for(;;){
            goToWord2:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case 1:
                numRemoved+=Long.bitCount(((word=root.word3)^(root.word3=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToEnd;
              case 0:
                numRemoved+=Long.bitCount(((word=root.word2)^(root.word2=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToWord3;
              case -1:
                numRemoved+=Long.bitCount(((word=root.word1)^(root.word1=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToWord2;
              default:
                numRemoved+=Long.bitCount(((word=root.word0)^(root.word0=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
              }
              numRemoved+=Long.bitCount((word=root.word1)^(root.word1=processWordRemoveIf(word,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount((word=root.word2)^(root.word2=processWordRemoveIf(word,0,filter)));
            break;
          }
          numRemoved+=Long.bitCount((word=root.word3)^(root.word3=processWordRemoveIf(word,64,filter)));
          break;
        }
        if(numRemoved!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        long word;
        goToEnd:for(;;){
          goToWord3:for(;;){
            goToWord2:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case 1:
                numRemoved+=Long.bitCount(((word=root.word3)^(root.word3=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToEnd;
              case 0:
                numRemoved+=Long.bitCount(((word=root.word2)^(root.word2=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToWord3;
              case -1:
                numRemoved+=Long.bitCount(((word=root.word1)^(root.word1=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
                break goToWord2;
              default:
                numRemoved+=Long.bitCount(((word=root.word0)^(root.word0=processWordRemoveIf(word,boundInfo,filter)))&(-1L<<boundInfo));
              }
              numRemoved+=Long.bitCount((word=root.word1)^(root.word1=processWordRemoveIf(word,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount((word=root.word2)^(root.word2=processWordRemoveIf(word,0,filter)));
            break;
          }
          numRemoved+=Long.bitCount((word=root.word3)^(root.word3=processWordRemoveIf(word,64,filter)));
          break;
        }
        if(numRemoved!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedTailView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedTailView extends AbstractCheckedSubSet{
    private CheckedTailView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedTailView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved=0;
        try{
          goToEnd:for(;;){
            goToWord3:for(;;){
              goToWord2:for(;;){
                final int boundInfo;
                switch((boundInfo=this.boundInfo)>>6){
                case 1:
                  numRemoved+=Long.bitCount(((newWord3)^(newWord3=processWordRemoveIf(newWord3,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToEnd;
                case 0:
                  numRemoved+=Long.bitCount(((newWord2)^(newWord2=processWordRemoveIf(newWord2,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToWord3;
                case -1:
                  numRemoved+=Long.bitCount(((newWord1)^(newWord1=processWordRemoveIf(newWord1,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToWord2;
                default:
                  numRemoved+=Long.bitCount(((newWord0)^(newWord0=processWordRemoveIf(newWord0,boundInfo,filter)))&(-1L<<boundInfo));
                }
                numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
                break;
              }
              numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord3^(newWord3=processWordRemoveIf(newWord3,64,filter)));
            break;
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved=0;
        try{
          goToEnd:for(;;){
            goToWord3:for(;;){
              goToWord2:for(;;){
                final int boundInfo;
                switch((boundInfo=this.boundInfo)>>6){
                case 1:
                  numRemoved+=Long.bitCount(((newWord3)^(newWord3=processWordRemoveIf(newWord3,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToEnd;
                case 0:
                  numRemoved+=Long.bitCount(((newWord2)^(newWord2=processWordRemoveIf(newWord2,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToWord3;
                case -1:
                  numRemoved+=Long.bitCount(((newWord1)^(newWord1=processWordRemoveIf(newWord1,boundInfo,filter)))&(-1L<<boundInfo));
                  break goToWord2;
                default:
                  numRemoved+=Long.bitCount(((newWord0)^(newWord0=processWordRemoveIf(newWord0,boundInfo,filter)))&(-1L<<boundInfo));
                }
                numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
                break;
              }
              numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord3^(newWord3=processWordRemoveIf(newWord3,64,filter)));
            break;
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedTailView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class UncheckedBodyView extends AbstractUncheckedSubSet{
    private UncheckedBodyView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedBodyView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter,(numRemoved>>8)))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter::test,(numRemoved>>8)))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedBodyView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedBodyView extends AbstractCheckedSubSet{
    private CheckedBodyView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedBodyView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved;
        try{
          final int boundInfo;
          final int low;
          switch((low=(boundInfo=this.boundInfo)&0xff)>>6){
            case 2: //-128 < lowInclusiveBound < 64
              final int high;
              switch((high=boundInfo>>8)>>6){
                case -2: //-128 < highExclusiveBound < -64
                  numRemoved=Long.bitCount((newWord0^(newWord0=processSubSetWordRemoveIf(newWord0,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
                  break;
                case -1: //-64 <= highExclusiveBound < 0
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,-64,high,filter)))&(-1L>>>-high));
                  break;
                case 0:  //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)))
                    + Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,high,filter)))&(-1L>>>-high));
                  break;
                default: //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)))
                    + Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            case 3: //-64 <= lowInclusiveBound < 0
              switch((high=boundInfo>>8)>>6){
                case -1: //-64 <= highExclusiveBound < 0
                  numRemoved=Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
                  break;
                case 0:  //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord1^(newWord1=processWordRemoveIf(newWord1,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,high,filter)))&(-1L>>>-high));
                  break;
                default: //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord1^(newWord1=processWordRemoveIf(newWord1,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            case 0:  //0 <= lowInclusiveBound < 64
              if((high=boundInfo>>8)>>6==0){ //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
              }else{ //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord2^(newWord2=processWordRemoveIf(newWord2,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            default: //64 <= lowInclusiveBound < 128 ; 64 <= highExclusiveBound < 128
              numRemoved=Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,low,high=boundInfo>>8,filter)))&(-1L<<low) & (-1L>>>-high));
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        long newWord0=root.word0,newWord1=root.word1,newWord2=root.word2,newWord3=root.word3;
        int numRemoved;
        try{
          final int boundInfo;
          final int low;
          switch((low=(boundInfo=this.boundInfo)&0xff)>>6){
            case 2: //-128 < lowInclusiveBound < 64
              final int high;
              switch((high=boundInfo>>8)>>6){
                case -2: //-128 < highExclusiveBound < -64
                  numRemoved=Long.bitCount((newWord0^(newWord0=processSubSetWordRemoveIf(newWord0,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
                  break;
                case -1: //-64 <= highExclusiveBound < 0
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,-64,high,filter)))&(-1L>>>-high));
                  break;
                case 0:  //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)))
                    + Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,high,filter)))&(-1L>>>-high));
                  break;
                default: //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)))
                    + Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            case 3: //-64 <= lowInclusiveBound < 0
              switch((high=boundInfo>>8)>>6){
                case -1: //-64 <= highExclusiveBound < 0
                  numRemoved=Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
                  break;
                case 0:  //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord1^(newWord1=processWordRemoveIf(newWord1,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,high,filter)))&(-1L>>>-high));
                  break;
                default: //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord1^(newWord1=processWordRemoveIf(newWord1,low,filter)))&(-1L<<low))
                    + Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            case 0:  //0 <= lowInclusiveBound < 64
              if((high=boundInfo>>8)>>6==0){ //0 <= highExclusiveBound < 64
                  numRemoved=Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,low,high,filter)))&(-1L<<low) & (-1L>>>-high));
              }else{ //64 <= highExclusiveBound < 128
                  numRemoved=Long.bitCount((newWord2^(newWord2=processWordRemoveIf(newWord2,low,filter)))&(-1L<<low))
                    + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,high,filter)))&(-1L>>>-high));
              }
              break;
            default: //64 <= lowInclusiveBound < 128 ; 64 <= highExclusiveBound < 128
              numRemoved=Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,low,high=boundInfo>>8,filter)))&(-1L<<low) & (-1L>>>-high));
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
        }
        if(numRemoved!=0){
          root.word0=newWord0;
          root.word1=newWord1;
          root.word2=newWord2;
          root.word3=newWord3;
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedBodyView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
}
