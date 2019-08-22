package omni.impl.seq;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.IntFunction;
import java.util.function.LongUnaryOperator;
import omni.api.OmniIterator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.BitSetUtil;
import omni.util.OmniArray;
import omni.util.ToStringUtil;

//TODO move some of the common operations to BitSetUtil
public class PackedBooleanArrDeq extends AbstractBooleanArrDeq{
  private static final long serialVersionUID=1L;
  transient long[] words;
  public PackedBooleanArrDeq(){
    super();
  }
  public PackedBooleanArrDeq(int initialCapacity){
    super();
    if(initialCapacity>=64) {
      this.words=new long[(initialCapacity-1>>6)+1];
    }
  }
  PackedBooleanArrDeq(int head,long[] words,int tail){
    super(head,tail);
    this.words=words;
  }
  @Override public int size(){
    int tail;
    if((tail=this.tail+1)!=0){
      if((tail-=this.head)<=0){
        tail+=words.length<<6;
      }
    }
    return tail;
  }
  private void removeIndex0PullDown(long[] words,int tail,int cursor,int tailOffset) {
    if(tail==0){
      this.tail=cursor;
    }else{
      this.tail=tail-1;
      words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,words[0],0,tailOffset),tail);
    }
  }
  private void removeIndex0PullUp(long[] words,int head,int cursor,int headOffset,int arrBound) {
    long word;
    words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(words[0]&-2L,word=words[arrBound]);
    if(head==cursor){
        this.head=0;
    }else{
        this.head=head+1;
        words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,arrBound),head);
    }
  }
  @Override
  void uncheckedForEach(final int tail,BooleanConsumer action){
    final var words=this.words;
    int head;
    int wordOffset=(head=this.head)>>6;
    if(tail<(head=this.head)) {
      int wordsBound=words.length;
      for(long word=words[wordOffset];;) {
        action.accept((word>>>head&1)!=0);
        if((++head&63)==0) {
          if(++wordOffset==wordsBound) {
            head=0;
            wordOffset=0;
            break;
          }
          word=words[wordOffset];
        }
      }
    }else {
      wordOffset=head>>6;
    }
    for(long word=words[wordOffset];;) {
      action.accept((word>>>head&1)!=0);
      if(head==tail) {
        return;
      }
      if((++head&63)==0) {
        word=words[++wordOffset];
      }
    }
  }
  @Override public boolean booleanElement(){
    int head;
    return (words[(head=this.head)>>6]>>head&1)!=0;
  }
  @Override public boolean getLastBoolean(){
    int tail;
    return (words[(tail=this.tail)>>6]>>tail&1)!=0;
  }
  @Override public boolean equals(Object obj){
    //TODO
    return false;
  }
  @Override public void push(boolean val){
    final long[] words;
    if((words=this.words)!=null) {
      int tail;
      if((tail=this.tail)==-1) {
        words[(tail=words.length)-1]=val?-1L:0L;
        this.head=tail=(tail<<6)-1;
        this.tail=tail;
      }else {
        int head;
        final long[] newWords;
        int newTailWordBound;
        int oldCap,newCap;
        if(tail==(head=this.head-1)) {
          newCap=OmniArray.growBy50Pct(oldCap=words.length);
          if((tail&63)==0) {
            (newWords=new long[newCap])[(head=newCap-oldCap)-1]=val?-1L:0L;
            this.head=(head<<6)-1;
            this.tail=(newCap<<6)-1;
            ArrCopy.semicheckedCopy(words,tail=(tail>>6)+1,newWords,head,oldCap-=tail);
            ArrCopy.uncheckedCopy(words,0,newWords,head+oldCap,tail);
          }else {
            ArrCopy.uncheckedCopy(words,0,newWords=new long[newCap],0,(newTailWordBound=tail>>6)+1);
            this.head=head=(newCap<<6)-((oldCap<<6)-(tail+1))-1;
            ArrCopy.semicheckedCopy(words,newTailWordBound+1,newWords,oldCap=(tail=head>>6)+1,newCap-oldCap);
            if(val) {
              newWords[tail]=words[newTailWordBound]|1L<<head;
            }else {
              newWords[tail]=words[newTailWordBound]&~(1L<<head);
            }
          }
          this.words=newWords;
          return;
        }else if(head==-1 && tail==(head=((oldCap=words.length)<<6)-1)) {
          this.tail=((newCap=OmniArray.growBy50Pct(oldCap))<<6)-1;
          ArrCopy.uncheckedCopy(words,0,newWords=new long[newCap],head=newCap-oldCap,oldCap);
          this.head=(--head<<6)+63;
          if(val) {
              newWords[head]=-1L;
          }
          this.words=newWords;
          return;
        }else {
          newWords=words;
          this.head=head;
        }
        if(val) {
          newWords[head>>6]|=1L<<head;
        }else {
          newWords[head>>6]&=~(1L<<head);
        }
      }
    }else {
      this.head=63;
      this.tail=63;
      this.words=new long[] {val?-1L:0L};
    }
  }
  @Override public void addLast(boolean val){
    final long[] words;
    if((words=this.words)!=null) {
      int tail;
      if((tail=this.tail)==-1) {
        words[0]=val?1L:0L;
        this.head=0;
        this.tail=0;
      }else {
        int head;
        final long[] newWords;
        int newTailWordBound;
        if(++tail==(head=this.head)) {
          if((tail&63)==0) {
            this.head=0;
            (newWords=new long[OmniArray.growBy50Pct(tail=words.length)])[tail]=val?1L:0L;
            this.tail=tail<<6;
            ArrCopy.uncheckedCopy(words,head>>=6,newWords,0,tail-=head);
            ArrCopy.uncheckedCopy(words,0,newWords,tail,head);
          }else {
            this.tail=tail;
            int oldCap,newCap;
            ArrCopy.semicheckedCopy(words,0,newWords=new long[newCap=OmniArray.growBy50Pct(oldCap=words.length)],0,newTailWordBound=tail>>6);
            if(val) {
              newWords[newTailWordBound]=words[newTailWordBound]|1L<<tail;
            }else {
              newWords[newTailWordBound]=words[newTailWordBound]&~(1L<<tail);
            }
            this.head=head=(newCap<<6)-((oldCap<<6)-tail);
            ArrCopy.uncheckedCopy(words,newTailWordBound,newWords,head>>=6,newCap-head);
          }
          this.words=newWords;
        }else {
          if((newTailWordBound=tail>>6)==words.length) {
            if(head==0) {
              ArrCopy.uncheckedCopy(words,0,newWords=new long[OmniArray.growBy50Pct(newTailWordBound)],0,newTailWordBound);
              this.words=newWords;
              if(val) {
                  newWords[newTailWordBound]=1L;
              }
              this.tail=tail;
            }else {
              if(val) {
                words[0]|=1L;
              }else {
                words[0]&=-2L;
              }
              this.tail=0;
            }
          }else {
            if(val) {
              words[newTailWordBound]|=1L<<tail;
            }else {
              words[newTailWordBound]&=~(1L<<tail);
            }
            this.tail=tail;
          }
        }
      }
    }else {
      this.head=0;
      this.tail=0;
      this.words=new long[] {val?1L:0L};
    }
  }
  @Override public boolean removeLastBoolean(){
    final long[] words;
    final int tail;
    final var ret=(words=this.words)[(tail=this.tail)>>6];
    if(this.head==tail) {
      this.tail=-1;
    }else if(tail==0) {
      this.tail=(words.length<<6)-1;
    }else {
      this.tail=tail-1;
    }
    return (ret>>>tail&1)!=0;
  }
  @Override public Boolean peekLast(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (this.words[tail>>6]>>>tail&1)!=0;
    }
    return null;
  }
  @Override public Boolean pollLast(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (ret>>>tail&1)!=0;
    }
    return null;
  }
  @Override public boolean peekLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (this.words[tail>>6]>>>tail&1)!=0;
    }
    return false;
  }
  @Override public boolean pollLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (ret>>>tail&1)!=0;
    }
    return false;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return this.words[tail>>6]>>>tail&1;
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return ret>>>tail&1;
    }
    return Double.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return this.words[tail>>6]>>>tail&1;
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return ret>>>tail&1;
    }
    return Float.NaN;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return this.words[tail>>6]>>>tail&1;
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return ret>>>tail&1;
    }
    return Long.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (int)(this.words[tail>>6]>>>tail&1);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (int)(ret>>>tail&1);
    }
    return Integer.MIN_VALUE;
  }
  @Override public short peekLastShort(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (short)(this.words[tail>>6]>>>tail&1);
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (short)(ret>>>tail&1);
    }
    return Short.MIN_VALUE;
  }
  @Override public byte peekLastByte(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (byte)(this.words[tail>>6]>>>tail&1);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (byte)(ret>>>tail&1);
    }
    return Byte.MIN_VALUE;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1) {
      return (char)(this.words[tail>>6]>>>tail&1);
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final var ret=(words=this.words)[tail>>6];
      if(this.head==tail) {
        this.tail=-1;
      }else if(tail==0) {
        this.tail=(words.length<<6)-1;
      }else {
        this.tail=tail-1;
      }
      return (char)(ret>>>tail&1);
    }
    return Character.MIN_VALUE;
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    return new DescendingItr(this);
  }
  @SuppressWarnings("unchecked") @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final T[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=arrConstructor.apply(size+((wordsBound=words.length)<<6));
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(T)(Boolean)((word>>>head&1)!=0);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=arrConstructor.apply(size);
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(T)(Boolean)((word>>>head&1)!=0);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return arrConstructor.apply(0);
  }
  @SuppressWarnings("unchecked") @Override public <T> T[] toArray(T[] dst){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=OmniArray.uncheckedArrResize(size+((wordsBound=words.length)<<6),dst);
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(T)(Boolean)((word>>>head&1)!=0);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=OmniArray.uncheckedArrResize(size,dst);
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(T)(Boolean)((word>>>head&1)!=0);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }

  @Override public OmniIterator.OfBoolean iterator(){
    return new AscendingItr(this);
  }
  @Override public Boolean[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final Boolean[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new Boolean[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(word>>>head&1)!=0;
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new Boolean[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(word>>>head&1)!=0;
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public boolean[] toBooleanArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final boolean[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new boolean[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(word>>>head&1)!=0;
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new boolean[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(word>>>head&1)!=0;
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public double[] toDoubleArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final double[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new double[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=word>>>head&1;
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new double[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=word>>>head&1;
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final float[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new float[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=word>>>head&1;
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new float[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=word>>>head&1;
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final long[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new long[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=word>>>head&1;
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new long[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=word>>>head&1;
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final int[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new int[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(int)(word>>>head&1);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new int[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(int)(word>>>head&1);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final short[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new short[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(short)(word>>>head&1);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new short[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(short)(word>>>head&1);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final byte[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new byte[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(byte)(word>>>head&1);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new byte[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(byte)(word>>>head&1);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int tail;
    if((tail=this.tail)!=-1){
      int dstOffset=0;
      final var words=this.words;
      final char[] dst;
      int size,head;
      int wordOffset=(head=this.head)>>6;
      if((size=++tail-head)<=0){
        int wordsBound;
        dst=new char[size+((wordsBound=words.length)<<6)];
        for(long word=words[wordOffset];;) {
          dst[dstOffset++]=(char)(word>>>head&1);
          if((++head&63)==0) {
            if(++wordOffset==wordsBound) {
              head=0;
              wordOffset=0;
              break;
            }
            word=words[wordOffset];
          }
        }
      }else{
        dst=new char[size];
      }
      for(long word=words[wordOffset];;++dstOffset) {
        dst[dstOffset]=(char)(word>>>head&1);
        if(++head==tail) {
         return dst;
        }
        if((head&63)==0) {
          word=words[++wordOffset];
        }
      }
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public boolean peekBoolean(){
    if(this.tail!=-1) {
      final int head;
      return (this.words[(head=this.head)>>6]>>>head&1)!=0;
    }
    return false;
  }
  @Override public boolean pollBoolean(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (ret>>>head&1)!=0;
    }
    return false;
  }
  @Override public byte peekByte(){
    if(this.tail!=-1) {
      final int head;
      return (byte)(this.words[(head=this.head)>>6]>>>head&1);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollByte(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (byte)(ret>>>head&1);
    }
    return Byte.MIN_VALUE;
  }
  @Override public short peekShort(){
    if(this.tail!=-1) {
      final int head;
      return (short)(this.words[(head=this.head)>>6]>>>head&1);
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollShort(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (short)(ret>>>head&1);
    }
    return Short.MIN_VALUE;
  }
  @Override public int peekInt(){
    if(this.tail!=-1) {
      final int head;
      return (int)(this.words[(head=this.head)>>6]>>>head&1);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollInt(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (int)(ret>>>head&1);
    }
    return Integer.MIN_VALUE;
  }
  @Override public long peekLong(){
    if(this.tail!=-1) {
      final int head;
      return this.words[(head=this.head)>>6]>>>head&1;
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLong(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return ret>>>head&1;
    }
    return Long.MIN_VALUE;
  }
  @Override public float peekFloat(){
    if(this.tail!=-1) {
      final int head;
      return this.words[(head=this.head)>>6]>>>head&1;
    }
    return Float.NaN;
  }
  @Override public float pollFloat(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return ret>>>head&1;
    }
    return Float.NaN;
  }
  @Override public double peekDouble(){
    if(this.tail!=-1) {
      final int head;
      return this.words[(head=this.head)>>6]>>>head&1;
    }
    return Double.NaN;
  }
  @Override public double pollDouble(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return ret>>>head&1;
    }
    return Double.NaN;
  }
  @Override public Boolean peek(){
    if(this.tail!=-1) {
      final int head;
      return (this.words[(head=this.head)>>6]>>>head&1)!=0;
    }
    return null;
  }
  @Override public Boolean poll(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (ret>>>head&1)!=0;
    }
    return null;
  }
  @Override public char peekChar(){
    if(this.tail!=-1) {
      final int head;
      return (char)(this.words[(head=this.head)>>6]>>>head&1);
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollChar(){
    final int tail;
    if((tail=this.tail)!=-1) {
      final long[] words;
      final int head;
      final var ret=(words=this.words)[(head=this.head)>>6];
      if(head==tail) {
        this.tail=-1;
      }else if(head==(words.length<<6)-1) {
        this.head=0;
      }else {
        this.head=head+1;
      }
      return (char)(ret>>>head&1);
    }
    return Character.MIN_VALUE;
  }
  @Override public boolean popBoolean(){
    final long[] words;
    final int head;
    final var ret=(words=this.words)[(head=this.head)>>6];
    if(head==this.tail) {
      this.tail=-1;
    }else if(head==(words.length<<6)-1) {
      this.head=0;
    }else {
      this.head=head+1;
    }
    return (ret>>>head&1)!=0;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int tail;
    if((tail=this.tail)==-1) {
      out.writeInt(-1);
    }else{
      final var words=this.words;
      int size,head;
      if((size=tail-(head=this.head))<0) {
        int wordLength;
        out.writeInt(size+=(wordLength=words.length)<<6);
        if((head&63)==0) {
          OmniArray.OfLong.writeArray(words,head>>6,wordLength-1,out);
          BitSetUtil.writeWordsSrcAligned(words,0,tail,out);
        }else {
          int headOffset;
          var word=words[headOffset=head>>6];
          int numLeftInHead;
          for(numLeftInHead=size-tail;numLeftInHead>=64;numLeftInHead-=64) {
              out.writeLong(word>>>head|(word=words[++headOffset])<<-head);
          }
          for(size=tail+numLeftInHead,headOffset=-1;size>=64;size-=64) {
              out.writeLong(word>>>head|(word=words[++headOffset])<<-head);
          }
          if(headOffset==tail>>6) {
              if(headOffset==wordLength-1) {
                  BitSetUtil.writeFinalWord(word>>>head|word<<-head,size,out);
              }else {
                  BitSetUtil.writeFinalWord(word>>>head,size,out);
              }
          }else {
              BitSetUtil.writeFinalWord(word>>>head|words[headOffset+1]<<-head,size,out);
          }
        }
      }else {
        out.writeInt(size);
        if((head&63)==0) {
          BitSetUtil.writeWordsSrcAligned(words,head,tail,out);
        }else {
          int headOffset;
          var word=words[headOffset=head>>6];
          for(;size>=64;size-=64) {
              out.writeLong(word>>>head|(word=words[++headOffset])<<-head);
          }
          if(headOffset==tail>>6) {
              BitSetUtil.writeFinalWord(word>>>head,size,out);
          }else {
              BitSetUtil.writeFinalWord(word>>>head|words[headOffset+1]<<-head,size,out);
          }
        }
      }
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException{
    int tail;
    this.tail=tail=in.readInt();
    if(tail!=-1) {
      final long[] words;
      BitSetUtil.readWords(words=new long[(tail>>6)+1],tail,in);
      this.words=words;
      this.head=0;
    }
  }
  @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1) {
          final var words=this.words;
          final long[] dst;
          int size;
          int head;
          final PackedBooleanArrDeq clone;
          if((size=tail-(head=this.head))<0) {
              int oldCap;
              if((size+=oldCap=words.length<<6)<64) {
                  long mask;
                  (dst=new long[1])[0]=words[0]&~(mask=-1L<<tail+1)|words[oldCap-1>>6]&mask;
                  clone=new PackedBooleanArrDeq(head&63,dst,tail);
              }else {
                  int cloneHead;
                  clone=new PackedBooleanArrDeq(cloneHead=(size=size+64&-64)-(oldCap-=head),dst=new long[size>>6],tail);
                  if((size=tail>>6)==(cloneHead>>=6)) {
                      if(size==0) {
                          dst[0]=words[0]&-1L>>>-tail-1|words[tail=head>>6]&-1L<<head;
                          ArrCopy.uncheckedCopy(words,tail+1,dst,cloneHead+1,oldCap>>6);
                      }else {
                          ArrCopy.uncheckedCopy(words,0,dst,0,size);
                          long mask;
                          dst[size]=words[size]&(mask=-1L>>>-tail-1)|words[tail=head>>6]&~mask;
                          ArrCopy.semicheckedCopy(words,tail+1,dst,cloneHead+1,oldCap>>6);
                      }
                  }else {
                      ArrCopy.uncheckedCopy(words,0,dst,0,size+1);
                      ArrCopy.uncheckedCopy(words,head>>6,dst,cloneHead,(oldCap-1>>6)+1);
                  }
              }
          }else {
              final int cloneCap;
              clone=new PackedBooleanArrDeq(0,dst=new long[cloneCap=(size>>6)+1],size);
              if((head&63)==0) {
                  ArrCopy.uncheckedCopy(words,head>>6,dst,0,cloneCap);
              }else {
                long srcWord;
                int srcOffset;
                for(srcWord=words[srcOffset=head>>6]>>>head,tail>>=6,size=0;srcOffset<tail;++size,srcWord>>>=head) {
                    dst[size]=srcWord | (srcWord=words[++srcOffset])<<-head;
                }
                if(size<cloneCap) {
                    dst[size]=srcWord;
                }
              }
          }
          return clone;
      }
      return new PackedBooleanArrDeq();
  }
  @Override boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
    //TODO
    throw new UnsupportedOperationException();
  }
  @Override boolean uncheckedcontains(int tail,boolean val){
    final var wordFlipper=BitSetUtil.getWordFlipper(val);
    final var words=this.words;
    int head;
    final int headWordOffset,tailWordOffset=tail>>6;
    if(tail<(head=this.head)) {
      for(int i=tailWordOffset;--i>=0;) {
        if(wordFlipper.applyAsLong(words[i])!=0) {
          return true;
        }
      }
      headWordOffset=head>>6;
      for(int i=words.length;--i>headWordOffset;) {
        if(wordFlipper.applyAsLong(words[i])!=0) {
          return true;
        }
      }
      if(headWordOffset==tailWordOffset) {
        return (wordFlipper.applyAsLong(words[headWordOffset])&((1L<<tail+1)-1|-1L<<head))!=0;
      }
      
    }else {
      headWordOffset=head>>6;
      for(int i=tailWordOffset;--i>headWordOffset;) {
        if(wordFlipper.applyAsLong(words[i])!=0) {
          return true;
        }
      }
      if(headWordOffset==tailWordOffset) {
        return (wordFlipper.applyAsLong(words[headWordOffset]) & -1L>>>-(tail-head+1)<<head)!=0;
      }
    }
    return wordFlipper.applyAsLong(words[tailWordOffset])<<-tail-1!=0 || wordFlipper.applyAsLong(words[headWordOffset]>>>head)!=0;
  }
  @Override boolean uncheckedremoveVal(int tail,boolean val){
    int head;
    if(tail<(head=this.head)) {
      return fragmentedremoveVal(head,tail,val);
    }
    return nonfragmentedremoveVal(tail,val,head);
  }
  private boolean nonfragmentedremoveVal(int tail,boolean val,int head){
    int headWordOffset;
    int tailWordOffset;
    int tail0s;
    long word;
    final var words=this.words;
    if((headWordOffset=head>>6)==(tailWordOffset=tail>>6)){
      if(val) {
        if((tail0s=Long.numberOfTrailingZeros((word=words[tailWordOffset])>>>head))<=tail-head) {
          if(head==tail) {
            this.tail=-1;
          }else {
            this.head=head+1;
            words[tailWordOffset]=word-(1L<<tail0s+head);
          }
          return true;
        }
      }else {
        if((tail0s=Long.numberOfTrailingZeros(~(word=words[tailWordOffset])>>>head))<=tail-head) {
          if(head==tail) {
            this.tail=-1;
          }else {
            this.head=head+1;
            words[tailWordOffset]=word+(1L<<tail0s+head);
          }
          return true;
        }
      }
      
    }else {
      if(val) {
        if((tail0s=Long.numberOfTrailingZeros((word=words[headWordOffset])>>>head))!=64) {
          this.head=head+1;
          words[headWordOffset]=word-(1L<<head+tail0s);
          return true;
        }
        for(int i=headWordOffset+1;i<tailWordOffset;++i) {
          if((tail0s=Long.numberOfTrailingZeros(word=words[i]))!=64) {
            this.head=head+1;
            words[i]=word-(1L<<tail0s);
            return true;
          }
        }
        if((tail0s=Long.numberOfTrailingZeros(word=words[tailWordOffset]))<(tail&63)){
          words[tailWordOffset]=word-(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }else {
        if((tail0s=Long.numberOfTrailingZeros(~(word=words[headWordOffset])>>>head))!=64) {
          this.head=head+1;
          words[headWordOffset]=word+(1L<<head+tail0s);
          return true;
        }
        for(int i=headWordOffset+1;i<tailWordOffset;++i) {
          if((tail0s=Long.numberOfTrailingZeros(~(word=words[i])))!=64) {
            this.head=head+1;
            words[i]=word+(1L<<tail0s);
            return true;
          }
        }
        if((tail0s=Long.numberOfTrailingZeros(~(word=words[tailWordOffset])))<(tail&63)){
          words[tailWordOffset]=word+(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }
     
    }
    return false;
  }
  private boolean fragmentedremoveVal(int head,int tail,boolean val){
    int tail0s;
    final var words=this.words;
    long word;
    int wordBound=words.length;
    int wordOffset=head>>6;
    if(val) {
      if((tail0s=Long.numberOfTrailingZeros((word=words[wordOffset])>>>head))!=64) {
        if(head==(wordBound<<6)-1) {
          this.head=0;
        }else {
          words[wordOffset]=word-(1L<<head+tail0s);
          this.head=head+1;
        }
        return true;
      }
      for(int i=wordOffset+1;i<wordBound;++i) {
        if((tail0s=Long.numberOfTrailingZeros(word=words[i]))!=64) {
          words[i]=word-(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }
      wordOffset=tail>>6;
      for(int i=0;i<wordOffset;++i) {
        if((tail0s=Long.numberOfTrailingZeros(word=words[i]))!=64) {
          words[i]=word-(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }
      if((tail0s=Long.numberOfTrailingZeros(word=words[wordOffset]))<(tail&63)){
        words[wordOffset]=word-(1L<<tail0s);
        this.head=head+1;
        return true;
      }
    }else {
      if((tail0s=Long.numberOfTrailingZeros(~(word=words[wordOffset])>>>head))!=64) {
        if(head==(wordBound<<6)-1) {
          this.head=0;
        }else {
          words[wordOffset]=word+(1L<<head+tail0s);
          this.head=head+1;
        }
        return true;
      }
      for(int i=wordOffset+1;i<wordBound;++i) {
        if((tail0s=Long.numberOfTrailingZeros(~(word=words[i])))!=64) {
          words[i]=word+(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }
      wordOffset=tail>>6;
      for(int i=0;i<wordOffset;++i) {
        if((tail0s=Long.numberOfTrailingZeros(~(word=words[i])))!=64) {
          words[i]=word+(1L<<tail0s);
          this.head=head+1;
          return true;
        }
      }
      if((tail0s=Long.numberOfTrailingZeros(~(word=words[wordOffset])))<(tail&63)){
        words[wordOffset]=word+(1L<<tail0s);
        this.head=head+1;
        return true;
      }
    }
    return false;
  }
  @Override boolean uncheckedremoveLastOccurrence(int tail,boolean val){
    int head;
    if(tail<(head=this.head)) {
      return fragmentedremoveLastOccurrence(head,tail,val);
    }
    return nonfragmentedremoveLastOccurrence(head,tail,val);
  }
  private boolean fragmentedremoveLastOccurrence(int head,int tail,boolean val) {
    int lead0s;
    final var words=this.words;
    long word;
    int wordBound=words.length;
    int wordOffset=tail>>6;
    if(val) {
      if((lead0s=Long.numberOfLeadingZeros((word=words[wordOffset])<<-tail))!=64) {
        if(tail==0) {
          this.tail=(wordBound<<6)-1;
        }else {
          words[wordOffset]=word-(Long.MIN_VALUE>>>lead0s-tail);
          this.tail=tail-1;
        }
        return true;
      }
      for(int i=wordOffset-1;i>=0;--i) {
        if((lead0s=Long.numberOfLeadingZeros(word=words[i]))!=64) {
          words[i]=word-(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }
      wordOffset=head>>6;
      for(int i=wordBound-1;i>wordOffset;--i) {
        if((lead0s=Long.numberOfLeadingZeros(word=words[i]))!=64) {
          words[i]=word-(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }
      if((lead0s=Long.numberOfLeadingZeros(word=words[wordOffset]))<(-head&63)){
        words[wordOffset]=word-(Long.MIN_VALUE>>>lead0s);
        this.tail=tail-1;
        return true;
      }
    }else {
      if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset])<<-tail))!=64) {
        if(tail==0) {
          this.tail=(wordBound<<6)-1;
        }else {
          words[wordOffset]=word+(Long.MIN_VALUE>>>lead0s-tail);
          this.tail=tail-1;
        }
        return true;
      }
      for(int i=wordOffset-1;i>=0;--i) {
        if((lead0s=Long.numberOfLeadingZeros(~(word=words[i])))!=64) {
          words[i]=word+(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }
      wordOffset=head>>6;
      for(int i=wordBound-1;i>wordOffset;--i) {
        if((lead0s=Long.numberOfLeadingZeros(~(word=words[i])))!=64) {
          words[i]=word+(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }
      if((lead0s=Long.numberOfLeadingZeros(~(word=words[wordOffset])))<(-head&63)){
        words[wordOffset]=word+(Long.MIN_VALUE>>>lead0s);
        this.tail=tail-1;
        return true;
      }
    }
    return false;
  }
  private boolean nonfragmentedremoveLastOccurrence(int head,int tail,boolean val) {
    int headWordOffset;
    int tailWordOffset;
    int lead0s;
    long word;
    final var words=this.words;
    if((headWordOffset=head>>6)==(tailWordOffset=tail>>6)){
      if(val) {
        if((lead0s=Long.numberOfLeadingZeros((word=words[tailWordOffset])<<-tail))<=tail-head) {
          if(head==tail) {
            this.tail=-1;
          }else {
            this.tail=tail-1;
            words[tailWordOffset]=word-(Long.MIN_VALUE>>>lead0s-tail);
          }
          return true;
        }
      }else {
        if((lead0s=Long.numberOfLeadingZeros(~(word=words[tailWordOffset])<<-tail))<=tail-head) {
          if(head==tail) {
            this.tail=-1;
          }else {
            this.tail=tail-1;
            words[tailWordOffset]=word+(Long.MIN_VALUE>>>lead0s-tail);
          }
          return true;
        }
      }
    }else {
      if(val) {
        if((lead0s=Long.numberOfLeadingZeros((word=words[tailWordOffset])<<-tail))!=64) {
          this.tail=tail-1;
          words[tailWordOffset]=word-(Long.MIN_VALUE>>>lead0s-tail);
          return true;
        }
        for(int i=tailWordOffset-1;i>headWordOffset;--tail) {
          if((lead0s=Long.numberOfLeadingZeros(word=words[i]))!=64) {
            this.tail=tail-1;
            words[i]=word-(Long.MIN_VALUE>>>lead0s);
            return true;
          }
        }
        if((lead0s=Long.numberOfLeadingZeros(word=words[headWordOffset]))<(-head&63)){
          words[headWordOffset]=word-(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }else {
        if((lead0s=Long.numberOfLeadingZeros(~(word=words[tailWordOffset])<<-tail))!=64) {
          this.tail=tail-1;
          words[tailWordOffset]=word+(Long.MIN_VALUE>>>lead0s-tail);
          return true;
        }
        for(int i=tailWordOffset-1;i>headWordOffset;--tail) {
          if((lead0s=Long.numberOfLeadingZeros(~(word=words[i])))!=64) {
            this.tail=tail-1;
            words[i]=word+(Long.MIN_VALUE>>>lead0s);
            return true;
          }
        }
        if((lead0s=Long.numberOfLeadingZeros(~(word=words[headWordOffset])))<(-head&63)){
          words[headWordOffset]=word+(Long.MIN_VALUE>>>lead0s);
          this.tail=tail-1;
          return true;
        }
      }
     
    }
    return false;
  }
  @Override int uncheckedsearch(int tail,boolean val){
    final long[] words;
    final int head;
    int headWordOffset,tail0s;
    final LongUnaryOperator wordFlipper;
    if((tail0s=Long.numberOfTrailingZeros((wordFlipper=BitSetUtil.getWordFlipper(val)).applyAsLong((words=this.words)[headWordOffset=(head=this.head)>>6])>>>head))!=64) {
      if(tail<head ||tail0s<tail-head+1) {
        return tail0s+1;
      }
      return -1;
    }
    final int tailWordOffset;
    if(tail<head) {
      final var wordBound=words.length;
      for(int i=++headWordOffset;i<wordBound;++i) {
        if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[i])))!=64) {
          return 64-(head&63)+(i-headWordOffset<<6)+tail0s+1;
        }
      }
      tailWordOffset=tail>>6;
      for(int i=0;i<tailWordOffset;++i) {
        if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[i])))!=64) {
          return 64-(head&63)+(wordBound-headWordOffset+i<<6)+tail0s+1;
        }
      }
      if((tail0s=Long.numberOfTrailingZeros(wordFlipper.applyAsLong(words[tailWordOffset])))<=(tail&63)) {
        return 64-(head&63)+(wordBound-headWordOffset+tailWordOffset-1<<6)+tail0s+1;
      }
    }else if(headWordOffset!=(tailWordOffset=tail>>6)) {
      for(int i=++headWordOffset;i<tailWordOffset;++i) {
        if((tail0s=Long.numberOfTrailingZeros(words[i]))!=64) {
          return 64-(head&63)+(i-headWordOffset<<6)+tail0s+1;
        }
      }
      if((tail0s=Long.numberOfTrailingZeros(words[tailWordOffset]))<=(tail&63)) {
        return 64-(head&63)+(tailWordOffset-1-headWordOffset<<6)+tail0s+1;
      }
    }
    return -1;
  }
  @Override String uncheckedToString(int tail){
    final byte[] buffer;
    int size,head,bufferOffset=1;
    if((size=++tail-(head=this.head))<=0) {
      final long[] words;
      int wordBound,wordOffset=head>>6;
      if((size+=(wordBound=(words=this.words).length)<<6)<=OmniArray.MAX_ARR_SIZE/7){
        (buffer=new byte[size*7])[0]=(byte)'[';
        for(long word=words[wordOffset];;++bufferOffset){
          buffer[bufferOffset=ToStringUtil.getStringBoolean((word>>>head&1)!=0,buffer,bufferOffset)]=(byte)',';
          buffer[++bufferOffset]=(byte)' ';
          if((++head&63)==0){
            if(++wordOffset==wordBound) {
              for(word=words[head=wordOffset=0];;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' '){
                bufferOffset=ToStringUtil.getStringBoolean((word>>>head&1)!=0,buffer,++bufferOffset);
                if(++head==tail){
                  buffer[bufferOffset]=(byte)']';
                  return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
                }
                if((head&63)==0) {
                  word=words[++wordOffset];
                }
              }
            }
            word=words[wordOffset];
          }
        }
      }else {
        final ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]);
        for(long word=words[wordOffset];;){
          builder.uncheckedAppendBoolean((word>>>head&1)!=0);
          builder.uncheckedAppendCommaAndSpace();
          if((++head&63)==0){
            if(++wordOffset==wordBound) {
              for(word=words[head=wordOffset=0];;builder.uncheckedAppendCommaAndSpace()){
                builder.uncheckedAppendBoolean((word>>>head&1)!=0);
                if(++head==tail){
                  builder.uncheckedAppendChar((byte)']');
                  (buffer=builder.buffer)[0]=(byte)'[';
                  return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
                }
                if((head&63)==0) {
                  word=words[++wordOffset];
                }
              }
            }
            word=words[wordOffset];
          }
        }
      }
    }else if(size<=OmniArray.MAX_ARR_SIZE/7) {
      (buffer=new byte[size*7])[size=PackedBooleanArrSeq.UncheckedList.uncheckedToString(this.words,head,tail,buffer)]=(byte)']';
      buffer[0]=(byte)'[';
      return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
    }else {
      final ToStringUtil.OmniStringBuilderByte builder;
      PackedBooleanArrSeq.UncheckedList.uncheckedToString(words,head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
      builder.uncheckedAppendChar((byte)']');
      (buffer=builder.buffer)[0]=(byte)'[';
      return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
    }
  }
  @Override int uncheckedHashCode(int tail){
    final long[] words;
    long word;
    int head;
    int wordOffset;
    int hash=((word=(words=this.words)[wordOffset=(head=this.head) >> 6]) >> head & 1) != 0?31 + 1231:31 + 1237;
    if(tail<head) {
      for(int wordBound=words.length;;) {
        if((++head&63)==0) {
          if(++wordOffset==wordBound) {
            hash=hash * 31 + (((word=words[head=wordOffset=0]) & 1) != 0?1231:1237);
            break;
          }
          word=words[wordOffset];
        }
        hash=hash * 31 + ((word >> head & 1) != 0?1231:1237);
      }
    }
    for(;;) {
      if(head==tail) {
        return hash;
      }
      if((++head&63)==0) {
        word=words[++wordOffset];
      }
      hash=hash * 31 + ((word >> head & 1) != 0?1231:1237);
    }
  }
  private void eraseHead(){
      int head;
      switch(Integer.signum(this.tail-(head=this.head))){
        case -1:
          this.head=head==(words.length<<6)-1?0:head+1;
          return;
        case 0:
          this.tail=-1;
          break;
        default:
          this.head=head+1;
      }
    }
    private void eraseTail() {
        int tail;
        switch(Integer.signum((tail=this.tail)-this.head)){
          case -1:
            this.tail=tail==0?(words.length<<6)-1:tail-1;
            return;
          case 0:
            this.tail=-1;
            break;
          default:
            this.tail=tail-1;
        }
    }


    private static class DescendingItr extends AscendingItr{
      private DescendingItr(DescendingItr itr){
        super(itr);
      }
      private DescendingItr(PackedBooleanArrDeq root){
        super(root,root.tail);
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override void uncheckedForEachRemaining(int cursor,BooleanConsumer action){
          final PackedBooleanArrDeq root;
          final int head;
          final long[] words;
          long word=(words=(root=this.root).words)[cursor>>6];
          if(cursor<(head=root.head)){
              for(;;) {
                  action.accept((word>>>cursor&1L)!=0);
                  if((--cursor&63)==63) {
                      if(cursor==-1) {
                          word=words[(cursor=words.length)-1];
                          cursor=(cursor<<6)-1;
                          break;
                      }
                      word=words[cursor>>6];
                  }
              }
          }
          for(;;) {
              action.accept((word>>>cursor&1L)!=0);
              if(cursor==head) {
                  break;
              }
              if((--cursor&63)==63) {
                  word=words[cursor>>6];
              }
          }
          this.cursor=-1;
        }
      @Override public boolean nextBoolean(){
        int cursor;
        final PackedBooleanArrDeq root;
        final var words=(root=this.root).words;
        this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?(words.length<<6)-1:cursor-1;
        return (words[cursor>>6]>>>cursor&1)!=0;
      }
      private void fragmentedDescendingRemove(int head,int cursor,int tail,PackedBooleanArrDeq root){
          //TODO optimize and clean up
        final long[] words;
        final int arrBound;
       
        int tailDist;
        int capacityBound;
        if((tailDist=(capacityBound=((arrBound=(words=root.words).length-1)<<6)+63)-cursor)==0) {
            //remove index 0
            final int headOffset,tailOffset;
            if((tailOffset=tail>>6)<=arrBound-(headOffset=head>>6)+1) {
                //pull the tail down
                root.removeIndex0PullDown(words,tail,cursor,tailOffset);
            }else {
                //pull the head up
                this.cursor=0;
                root.removeIndex0PullUp(words,head,cursor,headOffset,arrBound);
            }
        }else {
            final int headOffset=head>>6,tailOffset=tail>>6;
            int lastRetOffset;
            long word=words[lastRetOffset=++cursor>>6];
            if(cursor-head>0)
            {
              //removing from head run
                final int headWordDist,tailWordDist;
                if((headWordDist=lastRetOffset-(headOffset))<=(tailWordDist=arrBound-lastRetOffset)+(tailOffset)+1) {
                    //pull the head up
                    this.cursor=cursor;
                    root.head=head+1;
                    if(headWordDist==0) {
                      words[lastRetOffset]=BitSetUtil.shiftUpMiddleBits(word,head,cursor);
                    }else {
                      words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.shiftUpTrailingBits(word,cursor),word=words[--lastRetOffset]);
                      words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,lastRetOffset),head);
                    }
                }else {
                    //pull the tail down
                    root.tail=tail==0?(arrBound<<6)+63:tail-1;
                    word=BitSetUtil.shiftDownLeadingBits(word,cursor);
                    if(tailWordDist==0) {
                        words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[0]);
                    }else {
                        words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                        words[arrBound]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.pullDownLoop(words,word,lastRetOffset,arrBound)>>>1,word=words[0]);
                    }
                    words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,0,tailOffset),tail);
                }
            }
            else
            {
                //removing from tail run
                final int tailWordDist;
                if((tailWordDist=(tailOffset)-lastRetOffset)<=arrBound-(headOffset)+lastRetOffset+1) {
                    //pull the tail down
                    root.tail=tail-1;
                    if(tailWordDist==0) {
                        words[lastRetOffset]=BitSetUtil.shiftDownMiddleBits(word,cursor,tail);
                    }else {
                        words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.shiftDownLeadingBits(word,cursor),word=words[++lastRetOffset]);
                        words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,lastRetOffset,tailOffset),tail);
                    }
                }else {
                    //pull the head up
                    this.cursor=cursor;
                    root.head=head==(arrBound<<6)+63?0:head+1;
                    word=BitSetUtil.shiftUpTrailingBits(word,cursor);
                    if(lastRetOffset==0) {
                        words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[arrBound]);
                    }else {
                        words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                        words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.pullUpLoop(words,word,0,lastRetOffset)<<1,word=words[arrBound]);
                    }
                    words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,arrBound),head);
                }
            }
        }
      }
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,PackedBooleanArrDeq root){
          //TODO optimize and clean up
          final long[] words;
          final int headOffset,tailOffset;
          int lastRetOffset;
          long word=(words=root.words)[lastRetOffset=lastRet>>6];
          if(lastRetOffset-(headOffset=head>>6)<=(tailOffset=tail>>6)-lastRetOffset) {
              this.cursor=lastRet;
              root.head=head+1;
              word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
              for(;lastRetOffset!=headOffset;word<<=1) {
                  words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
              }
          }else {
              root.tail=tail-1;
              word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
              for(;lastRetOffset!=tailOffset;word>>>=1) {
                  words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
              }
          }
          words[lastRetOffset]=word;
        }
      @Override public void remove(){
        int cursor;
        if((cursor=this.cursor)==-1){
          root.eraseHead();
        }else{
          final PackedBooleanArrDeq root;
          int head,tail;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedDescendingRemove(head,cursor,tail,root);
          }else{
            nonfragmentedDescendingRemove(head,cursor+1,tail,root);
          }
        }
      }
    } 
    private static class AscendingItr extends AbstractDeqItr
    {
      transient final PackedBooleanArrDeq root;
      private AscendingItr(AscendingItr itr){
        super(itr);
        this.root=itr.root;
      }
      private AscendingItr(PackedBooleanArrDeq root){
        super(root.tail!=-1?root.head:-1);
        this.root=root;
      }
      private AscendingItr(PackedBooleanArrDeq root,int cursor){
        super(cursor);
        this.root=root;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public boolean nextBoolean(){
        final long[] words;
        int cursor;
        final PackedBooleanArrDeq root;
        final var ret=((words=(root=this.root).words)[(cursor=this.cursor)>>6]>>>cursor&1)!=0;
        if(cursor==root.tail){
          cursor=-1;
        }else if(++cursor==words.length<<6){
          cursor=0;
        }
        this.cursor=cursor;
        return ret;
      }
      @Override void uncheckedForEachRemaining(int cursor,BooleanConsumer action){
          final PackedBooleanArrDeq root;
          final int tail;
          final long[] words;
          var word=(words=(root=this.root).words)[cursor>>6];
          if(cursor>(tail=root.tail)) {
              for(int wordBound=words.length<<6;;) {
                  action.accept((word>>>cursor&1L)!=0);
                  if((++cursor&63)==0) {
                      if(cursor==wordBound) {
                          word=words[cursor=0];
                          break;
                      }
                      word=words[cursor>>6];
                  }
              }
          }
          for(;;) {
              action.accept((word>>>cursor&1L)!=0);
              if(cursor==tail) {
                  break;
              }
              if((++cursor&63)==0) {
                  word=words[cursor>>6];
              }
          }
          this.cursor=-1;
        }
      @Override public void remove(){
          int cursor;
          switch(cursor=this.cursor) {
          case -1:
              root.eraseTail();
              break;
          case 0:
              eraseAtSplit();
              break;
          default:
              final int head,tail;
              final PackedBooleanArrDeq root;
              if((tail=(root=this.root).tail)<(head=root.head)){
                  fragmentedAscendingRemove(head,cursor-1,tail,root);
              }else {
                  nonfragmentedAscendingRemove(head,cursor-1,tail,root);
              }
          }
      }
      private void eraseAtSplit(){
          final PackedBooleanArrDeq root;
          final long[] words;
          final int head,tail,tailOffset,headOffset;
          int arrBound,headDist;
          long word;
          if((tailOffset=(tail=(root=this.root).tail)>>6)<(headDist=(arrBound=(words=root.words).length-1)-(headOffset=(head=root.head)>>6))) {
              //it's more efficient to pull the tail down
              this.cursor=headDist=(arrBound<<6)+63;
              root.tail=tail==0?headDist:tail-1;
              words[arrBound]=BitSetUtil.combineWordWithTrailingBitOfNext(words[arrBound]&Long.MAX_VALUE,word=words[0]);
              words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,0,tailOffset),tail);
          }else {
              //it's more efficient to pull the head up
              word=words[arrBound];
              if(headDist==0) {
                  root.head=head==(arrBound<<6)+63?0:head+1;
              }else {
                  root.head=head+1;   
                  do {
                      words[arrBound]=BitSetUtil.combineWordWithLeadingBitOfPrev(word<<1,word=words[--arrBound]);
                  }while(arrBound!=headOffset);
              }
              words[arrBound]=BitSetUtil.shiftUpLeadingBits(word,head);
          }
        }
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,PackedBooleanArrDeq root) {
          //TODO optimize and clean up
          final long[] words;
          final int headOffset=head>>6,tailOffset=tail>>6,arrBound=(words=root.words).length-1;
          int lastRetOffset;
          long word=words[lastRetOffset=lastRet>>6];
          switch(Integer.signum(lastRet-head))
          {
          case -1:
          {
              //the index to remove is in the tail run
              final int tailWordDist;
              if((tailWordDist=tailOffset-lastRetOffset)<=arrBound-headOffset+lastRetOffset) {
                //pull down the tail
                 root.tail=tail-1;
                 this.cursor=lastRet;
                 if(tailWordDist==0) {
                     words[lastRetOffset]=BitSetUtil.shiftDownMiddleBits(word,lastRet,tail);
                 }else {
                     words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.shiftDownLeadingBits(word,lastRet),word=words[++lastRetOffset]);
                     words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,lastRetOffset,tailOffset),tail);
                 }
              }else {
                  //pull up the head
                  root.head=head==(arrBound<<6)+63?0:head+1;
                  word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
                  if(lastRetOffset==0) {
                      words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[arrBound]);
                  }else {
                      words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                      words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.pullUpLoop(words,word,0,lastRetOffset)<<1,word=words[arrBound]);
                  }
                  words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,arrBound),head);
              }
              break;
          }
          case 0:
          {
              //removing the head
              root.head=head+1;
              break;
          }
          default:
          {
              //removing from the head run
              final int headWordDist,tailWordDist;
              if((headWordDist=lastRetOffset-headOffset)<=(tailWordDist=arrBound-lastRetOffset)+tailOffset){
                  //pull up the head
                  root.head=head+1;
                  if(headWordDist==0) {
                      words[lastRetOffset]=BitSetUtil.shiftUpMiddleBits(word,head,lastRet);
                  }else {
                      words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.shiftUpTrailingBits(word,lastRet),word=words[--lastRetOffset]);
                      words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,lastRetOffset),head);
                  }
              }else {
                  //pull the tail down
                  this.cursor=lastRet;
                  root.tail=tail==0?(arrBound<<6)+63:tail-1;
                  word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
                  if(tailWordDist==0) {
                      words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[0]);
                  }else {
                      words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                      words[arrBound]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.pullDownLoop(words,word,lastRetOffset,arrBound)>>>1,word=words[0]);
                  }
                  words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,0,tailOffset),tail);
              }
          }
          }
      }
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,PackedBooleanArrDeq root){
          //TODO optimize and clean up
        final long[] words;
        final int headOffset,tailOffset;
        int lastRetOffset;
        long word=(words=root.words)[lastRetOffset=lastRet>>6];
        if(lastRetOffset-(headOffset=head>>6)<=(tailOffset=tail>>6)-lastRetOffset){
          root.head=head+1;
          word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
          while(lastRetOffset!=headOffset) {
            words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
            word<<=1;
          }
        }else{
          this.cursor=lastRet;
          root.tail=tail-1;
          word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
          while(lastRetOffset!=tailOffset){
            words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
            word>>>=1;
          }
        }
        words[lastRetOffset]=word;
      }
    }
    public static class Checked extends PackedBooleanArrDeq{
      private static class AscendingItr extends AbstractDeqItr{
          transient int modCount;
          transient int lastRet;
          transient final Checked root;
          private AscendingItr(AscendingItr itr){
            super(itr);
            this.modCount=itr.modCount;
            this.lastRet=itr.lastRet;
            this.root=itr.root;
          }
          private AscendingItr(Checked root){
            super(root.tail==-1?-1:root.head);
            this.root=root;
            this.modCount=root.modCount;
            this.lastRet=-1;
          }
          private AscendingItr(Checked root,int cursor){
            super(cursor);
            this.root=root;
            this.modCount=root.modCount;
            this.lastRet=-1;
          }
          @Override public Object clone(){
            return new AscendingItr(this);
          }
          @Override public boolean nextBoolean(){
            final Checked root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int cursor;
            if((cursor=this.cursor)!=-1){
              final long[] words;
              final var ret=((words=root.words)[cursor>>6]>>>cursor&1)!=0;
              this.lastRet=cursor;
              if(cursor==root.tail){
                cursor=-1;
              }else if(++cursor==words.length<<6){
                cursor=0;
              }
              this.cursor=cursor;
              return ret;
            }
            throw new NoSuchElementException();
          }
          private void fragmentedAscendingRemove(int head,int lastRet,int tail,Checked root) {
              //TODO optimize and clean up
              final long[] words;
              final int headOffset=head>>6,tailOffset=tail>>6,arrBound=(words=root.words).length-1;
              int lastRetOffset;
              long word=words[lastRetOffset=lastRet>>6];
              switch(Integer.signum(lastRet-head))
              {
              case -1:
              {
                //removing from tail run
                  final int tailWordDist;
                  if((tailWordDist=tailOffset-lastRetOffset)<=arrBound-headOffset+lastRetOffset)
                  {
                     //pull down the tail
                      if(tail==0)
                      {
                          root.tail=(arrBound<<6)+63;
                      }
                      else
                      {
                          root.tail=tail-1;
                          this.cursor=lastRet;
                          if(tailWordDist==0) {
                              words[lastRetOffset]=BitSetUtil.shiftDownMiddleBits(word,lastRet,tail);
                          }else {
                              words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.shiftDownLeadingBits(word,lastRet),word=words[++lastRetOffset]);
                              words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,lastRetOffset,tailOffset),tail);
                          }
                      }
                   }
                  else
                  {
                      //pull up the head
                      root.head=head==(arrBound<<6)+63?0:head+1;
                      word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
                      if(lastRetOffset==0) {
                          words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[arrBound]);
                      }else {
                          words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                          words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.pullUpLoop(words,word,0,lastRetOffset)<<1,word=words[arrBound]);
                      }
                      words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,arrBound),head);
                  }
                  break;
              }
              case 0:
              {
                  //removing from the head
                  root.head=head==(arrBound<<6)+63?0:head+1;
              }
                  break;
              default:
                  //removing from head run
                  final int headWordDist,tailWordDist;
                  if((headWordDist=lastRetOffset-headOffset)<=(tailWordDist=arrBound-lastRetOffset)+tailOffset)
                  {
                      //pull up the head
                      root.head=head+1;
                      if(headWordDist==0) {
                          words[lastRetOffset]=BitSetUtil.shiftUpMiddleBits(word,head,lastRet);
                      }else {
                          words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.shiftUpTrailingBits(word,lastRet),word=words[--lastRetOffset]);
                          words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,lastRetOffset),head);
                      }
                  }
                  else
                  {
                      //pull the tail down
                      this.cursor=lastRet;
                      root.tail=tail==0?(arrBound<<6)+63:tail-1;
                      word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
                      if(tailWordDist==0)
                      {
                          words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[0]);
                      }
                      else
                      {
                          words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                          words[arrBound]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.pullDownLoop(words,word,lastRetOffset,arrBound)>>>1,word=words[0]);
                      }
                      words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,0,tailOffset),tail);
                  }
              }
              
             
              
          }
          private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,Checked root) {
              //TODO optimize and clean up
              final long[] words;
              final int headOffset, tailOffset;
              int lastRetOffset;
              long word=(words=root.words)[lastRetOffset=lastRet>>6];
              if(lastRetOffset-(headOffset=head>>6)<=(tailOffset=tail>>6)-lastRetOffset){
                root.head=head+1;
                word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
                while(lastRetOffset!=headOffset) {
                  words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                  word<<=1;
                }
              }else{
                this.cursor=lastRet;
                root.tail=tail-1;
                word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
                while(lastRetOffset!=tailOffset){
                  words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                  word>>>=1;
                }
              }
              words[lastRetOffset]=word;
          }
          @Override public void remove(){
            int lastRet;
            if((lastRet=this.lastRet)!=-1){
              int modCount;
              final Checked root;
              CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              final int head,tail;
              switch(Integer.signum((tail=root.tail)-(head=root.head))){
                case -1:
                  fragmentedAscendingRemove(head,lastRet,tail,root);
                  break;
                case 0:
                  root.tail=-1;
                  break;
                default:
                  nonfragmentedAscendingRemove(head,lastRet,tail,root);
              }
              this.lastRet=-1;
              return;
            }
            throw new IllegalStateException();
          }
          @Override void uncheckedForEachRemaining(final int expectedCursor,BooleanConsumer action){
              final int modCount=this.modCount;
              final Checked root;
              final int tail=(root=this.root).tail;
              try{
                  final long[] words;
                  int cursor;
                  var word=(words=root.words)[(cursor=expectedCursor)>>6];
                  if(cursor>tail) {
                      for(int wordBound=words.length<<6;;) {
                          action.accept((word>>>cursor&1L)!=0);
                          if((++cursor&63)==0) {
                              if(cursor==wordBound) {
                                  word=words[cursor=0];
                                  break;
                              }
                              word=words[cursor>>6];
                          }
                      }
                  }
                  for(;;) {
                      action.accept((word>>>cursor&1L)!=0);
                      if(cursor==tail) {
                          break;
                      }
                      if((++cursor&63)==0) {
                          word=words[cursor>>6];
                      }
                  }
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount,expectedCursor,this.cursor);
              }
              this.lastRet=tail;
              this.cursor=-1;
            }
        }
        private static class DescendingItr extends AscendingItr{
          private void fragmentedDescendingRemove(int head,int lastRet,int tail,Checked root){
              //TODO optimize and clean up
              final long[] words;
              final int headOffset=head>>6,tailOffset=tail>>6,arrBound=(words=root.words).length-1;
              int lastRetOffset;
              long word=words[lastRetOffset=lastRet>>6];
              switch(Integer.signum(lastRet-head))
              {
              case -1:
              {
                //removing from tail run
                  final int tailWordDist;
                  if((tailWordDist=tailOffset-lastRetOffset)<=arrBound-headOffset+lastRetOffset)
                  {
                     //pull down the tail
                      if(tail==0)
                      {
                          root.tail=(arrBound<<6)+63;
                      }
                      else
                      {
                          root.tail=tail-1;
                          if(tailWordDist==0) {
                              words[lastRetOffset]=BitSetUtil.shiftDownMiddleBits(word,lastRet,tail);
                          }else {
                              words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.shiftDownLeadingBits(word,lastRet),word=words[++lastRetOffset]);
                              words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,lastRetOffset,tailOffset),tail);
                          }
                      }
                   }
                  else
                  {
                      //pull up the head
                      this.cursor=lastRet;
                      root.head=head==(arrBound<<6)+63?0:head+1;
                      word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
                      if(lastRetOffset==0) {
                          words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[arrBound]);
                      }else {
                          words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                          words[0]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.pullUpLoop(words,word,0,lastRetOffset)<<1,word=words[arrBound]);
                      }
                      words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,arrBound),head);
                  }
                  break;
              }
              case 0:
              {
                  //removing from the head
                  root.head=head==(arrBound<<6)+63?0:head+1;
              }
                  break;
              default:
                  //removing from head run
                  final int headWordDist,tailWordDist;
                  if((headWordDist=lastRetOffset-headOffset)<=(tailWordDist=arrBound-lastRetOffset)+tailOffset)
                  {
                      this.cursor=lastRet;
                      //pull up the head
                      root.head=head+1;
                      if(headWordDist==0) {
                          words[lastRetOffset]=BitSetUtil.shiftUpMiddleBits(word,head,lastRet);
                      }else {
                          words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(BitSetUtil.shiftUpTrailingBits(word,lastRet),word=words[--lastRetOffset]);
                          words[headOffset]=BitSetUtil.shiftUpLeadingBits(BitSetUtil.pullUpLoop(words,word,headOffset,lastRetOffset),head);
                      }
                  }
                  else
                  {
                      //pull the tail down
                      root.tail=tail==0?(arrBound<<6)+63:tail-1;
                      word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
                      if(tailWordDist==0)
                      {
                          words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[0]);
                      }
                      else
                      {
                          words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                          words[arrBound]=BitSetUtil.combineWordWithTrailingBitOfNext(BitSetUtil.pullDownLoop(words,word,lastRetOffset,arrBound)>>>1,word=words[0]);
                      }
                      words[tailOffset]=BitSetUtil.shiftDownTrailingBits(BitSetUtil.pullDownLoop(words,word,0,tailOffset),tail);
                  }
              }
          }
          private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,Checked root){
              //TODO optimize and clean up
              final long[] words;
              final int headOffset,tailOffset;
              int lastRetOffset;
              long word=(words=root.words)[lastRetOffset=lastRet>>6];
              if(lastRetOffset-(headOffset=head>>6)<=(tailOffset=tail>>6)-lastRetOffset) {
                  this.cursor=lastRet;
                  root.head=head+1;
                  word=BitSetUtil.shiftUpTrailingBits(word,lastRet);
                  for(;lastRetOffset!=headOffset;word<<=1) {
                      words[lastRetOffset]=BitSetUtil.combineWordWithLeadingBitOfPrev(word,word=words[--lastRetOffset]);
                  }
              }else {
                  
                  root.tail=tail-1;
                  word=BitSetUtil.shiftDownLeadingBits(word,lastRet);
                  for(;lastRetOffset!=tailOffset;word>>>=1) {
                      words[lastRetOffset]=BitSetUtil.combineWordWithTrailingBitOfNext(word,word=words[++lastRetOffset]);
                  }
              }
              words[lastRetOffset]=word;
            }
          @Override public void remove(){
              int lastRet;
              if((lastRet=this.lastRet)!=-1){
                int modCount;
                final Checked root;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                final int head,tail;
                switch(Integer.signum((tail=root.tail)-(head=root.head))){
                  case -1:
                    fragmentedDescendingRemove(head,lastRet,tail,root);
                    break;
                  case 0:
                    root.tail=-1;
                    break;
                  default:
                    nonfragmentedDescendingRemove(head,lastRet,tail,root);
                }
                this.lastRet=-1;
                return;
              }
              throw new IllegalStateException();
            }
          private DescendingItr(DescendingItr itr){
            super(itr);
          }
          private DescendingItr(Checked root){
            super(root,root.tail);
          }
          @Override public Object clone(){
            return new DescendingItr(this);
          }
          @Override public boolean nextBoolean(){
            final Checked root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int cursor;
            if((cursor=this.cursor)!=-1){
              final long[] words;
              final var ret=((words=root.words)[cursor>>6]>>>cursor&1)!=0;
              this.lastRet=cursor;
              if(cursor==root.head){
                cursor=-1;
              }else if(--cursor==-1){
                cursor=(words.length<<6)-1;
              }
              this.cursor=cursor;
              return ret;
            }
            throw new NoSuchElementException();
          } 
          @Override void uncheckedForEachRemaining(final int expectedCursor,BooleanConsumer action){
              int modCount=this.modCount;
              final Checked root;
              final int head=(root=this.root).head;
              try{
                  final long[] words;
                  int cursor;
                  long word=(words=root.words)[(cursor=expectedCursor)>>6];
                  if(cursor<head){
                      for(;;) {
                          action.accept((word>>>cursor&1L)!=0);
                          if((--cursor&63)==63) {
                              if(cursor==-1) {
                                  word=words[(cursor=words.length)-1];
                                  cursor=(cursor<<6)-1;
                                  break;
                              }
                              word=words[cursor>>6];
                          }
                      }
                  }
                  for(;;) {
                      action.accept((word>>>cursor&1L)!=0);
                      if(cursor==head) {
                          break;
                      }
                      if((--cursor&63)==63) {
                          word=words[cursor>>6];
                      }
                  }
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount,expectedCursor,this.cursor);
              }
              this.lastRet=head;
              this.cursor=-1;
            }
        }
    private static final long serialVersionUID=1L;
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,long[] words,int tail){
      super(head,words,tail);
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
      //TODO
      throw new UnsupportedOperationException();
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,BooleanPredicate filter){
      //TODO
      throw new UnsupportedOperationException();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    
    @Override public Object clone(){
        int tail;
        if((tail=this.tail)!=-1) {
            final var words=this.words;
            final long[] dst;
            int size;
            int head;
            final Checked clone;
            if((size=tail-(head=this.head))<0) {
                int oldCap;
                if((size+=oldCap=words.length<<6)<64) {
                    long mask;
                    (dst=new long[1])[0]=words[0]&~(mask=-1L<<tail+1)|words[oldCap-1>>6]&mask;
                    clone=new Checked(head&63,dst,tail);
                }else {
                    int cloneHead;
                    clone=new Checked(cloneHead=(size=size+64&-64)-(oldCap-=head),dst=new long[size>>6],tail);
                    if((size=tail>>6)==(cloneHead>>=6)) {
                        if(size==0) {
                            dst[0]=words[0]&-1L>>>-tail-1|words[tail=head>>6]&-1L<<head;
                            ArrCopy.uncheckedCopy(words,tail+1,dst,cloneHead+1,oldCap>>6);
                        }else {
                            ArrCopy.uncheckedCopy(words,0,dst,0,size);
                            long mask;
                            dst[size]=words[size]&(mask=-1L>>>-tail-1)|words[tail=head>>6]&~mask;
                            ArrCopy.semicheckedCopy(words,tail+1,dst,cloneHead+1,oldCap>>6);
                        }
                    }else {
                        ArrCopy.uncheckedCopy(words,0,dst,0,size+1);
                        ArrCopy.uncheckedCopy(words,head>>6,dst,cloneHead,(oldCap-1>>6)+1);
                    }
                }
            }else {
                final int cloneCap;
                clone=new Checked(0,dst=new long[cloneCap=(size>>6)+1],size);
                if((head&63)==0) {
                    ArrCopy.uncheckedCopy(words,head>>6,dst,0,cloneCap);
                }else {
                  long srcWord;
                  int srcOffset;
                  for(srcWord=words[srcOffset=head>>6]>>>head,tail>>=6,size=0;srcOffset<tail;++size,srcWord>>>=head) {
                      dst[size]=srcWord | (srcWord=words[++srcOffset])<<-head;
                  }
                  if(size<cloneCap) {
                      dst[size]=srcWord;
                  }
                }
            }
            return clone;
        }
        return new Checked();
    }
    @Override public boolean removeLastBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (ret>>>tail&1)!=0;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean popBoolean(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (ret>>>head&1)!=0;
      }
      throw new NoSuchElementException();
    }
    @Override public void writeExternal(ObjectOutput output) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(output);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void clear(){
      if(this.tail!=-1){
        ++this.modCount;
        this.tail=-1;
      }
    }
    @Override public void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(boolean val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override void uncheckedForEach(final int tail,BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public boolean booleanElement(){
      if(tail!=-1){
        int head;
        return (words[(head=this.head)>>6]>>head&1)!=0;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean getLastBoolean(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (words[tail>>6]>>tail&1)!=0;
      }
      throw new NoSuchElementException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public boolean pollBoolean(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (ret>>>head&1)!=0;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (ret>>>tail&1)!=0;
      }
      return false;
    }
    @Override public Boolean poll(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (ret>>>head&1)!=0;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (ret>>>tail&1)!=0;
      }
      return null;
    }
    @Override public double pollDouble(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return ret>>>head&1;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return ret>>>tail&1;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return ret>>>head&1;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return ret>>>tail&1;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final int tail;
      if((tail=this.tail)!=-1) {
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return ret>>>head&1;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return ret>>>tail&1;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (int)(ret>>>head&1);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (int)(ret>>>tail&1);
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (short)(ret>>>head&1);
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (short)(ret>>>tail&1);
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (byte)(ret>>>head&1);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (byte)(ret>>>tail&1);
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final int head;
        final var ret=(words=this.words)[(head=this.head)>>6];
        if(head==tail) {
          this.tail=-1;
        }else if(head==(words.length<<6)-1) {
          this.head=0;
        }else {
          this.head=head+1;
        }
        return (char)(ret>>>head&1);
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final long[] words;
        final var ret=(words=this.words)[tail>>6];
        if(this.head==tail) {
          this.tail=-1;
        }else if(tail==0) {
          this.tail=(words.length<<6)-1;
        }else {
          this.tail=tail-1;
        }
        return (char)(ret>>>tail&1);
      }
      return Character.MIN_VALUE;
    }
    @Override
    boolean uncheckedremoveVal(final int tail
    ,final boolean val
    ){
      final int head;
      if(tail<(head=this.head)) {
        if(!super.fragmentedremoveVal(head,tail,val)) {
          return false;
        }
      }else if(!super.nonfragmentedremoveVal(tail,val,head)) {
        return false;
      }
      ++this.modCount;
      return true;
    }
    @Override
    boolean uncheckedremoveLastOccurrence(final int tail
    ,final boolean val
    ){
      final int head;
      if(tail<(head=this.head)) {
        if(!super.fragmentedremoveLastOccurrence(head,tail,val)) {
          return false;
        }
      }else if(!super.nonfragmentedremoveLastOccurrence(head,tail,val)) {
        return false;
      }
      ++this.modCount;
      return true;
    }
  }
}
