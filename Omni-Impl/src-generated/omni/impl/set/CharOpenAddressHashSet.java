package omni.impl.set;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.impl.AbstractCharItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
public class CharOpenAddressHashSet
extends AbstractIntegralTypeOpenAddressHashSet<Character>
implements OmniSet.OfChar{
  private static void quickInsert(char[] table,char val){
    int tableLength;
    int hash;
    for(hash=(val) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  private static  long wordRemoveIf(long word,
  int
  valOffset,CharPredicate filter){
    long marker=1L;
    for(;;){
        if((word & marker) != 0){
            if(filter.test((char)valOffset)){
                word&=~marker;
            }
        }
        if((marker<<=1) == 0){
            return word;
        }
        ++valOffset;
    }
  }
  transient char[] table;
  public CharOpenAddressHashSet(){
    super();
  }
  public CharOpenAddressHashSet(CharOpenAddressHashSet that){
    super(that);
    int tableSize;
    if((tableSize=that.tableSize)!=0){
      char[] table;
      this.table=table=new char[tableSizeFor(tableSize)];
      char[] thatTable;
      for(int i=(thatTable=that.table).length;;){
        char tableVal;
        if(((tableVal=thatTable[--i])&-2)!=0)
        {
          quickInsert(table,tableVal);
          if(--tableSize==0){
            break;
          }
        }
      }
    }
  }
  public CharOpenAddressHashSet(int initialCapacity){
    super(initialCapacity);
  }
  public CharOpenAddressHashSet(float loadFactor){
    super(loadFactor);
  }
  public CharOpenAddressHashSet(int initialCapacity,float loadFactor){
    super(initialCapacity,loadFactor);
  }
  CharOpenAddressHashSet(int initialCapacity,float loadFactor,long word0,long word1,long word2,long word3){
    //for testing purposes
    super(initialCapacity,loadFactor,word0,word1,word2,word3);
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word0)!=(this.word0=word|(val?2L:1L))){
      ++this.size;
      return true;
    }
    return false;
  }
  @Override public boolean add(char val){
    returnFalse:for(;;){
      long word;
      switch(val >> 6){
      case 0:
        if((word=this.word0) == (this.word0=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 1:
        if((word=this.word1) == (this.word1=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 2:
        if((word=this.word2) == (this.word2=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 3:
        if((word=this.word3) == (this.word3=word | (1L << val))){
          break returnFalse;
        }
        break;
      default:
        if(!addToTable(val)){
          break returnFalse;
        }
      }
      ++size;
      return true;
    }
    return false; 
  }
  @Override public boolean add(Character val){
    return add((char)val);
  }
  @Override public Object clone(){
    return new CharOpenAddressHashSet(this);
  }
  @Override public boolean contains(boolean val){
    return (this.word0 & (val?2L:1L))!=0;
  }
  @Override public boolean contains(byte val){
    return uncheckedContainsByte(val);
  }
  @Override public boolean contains(char val){
    return uncheckedContainsChar(val);
  }
  @Override public boolean contains(short val){
    return val>=0 && uncheckedContainsChar(val);
  }
  @Override public boolean contains(int val){
    return val==(char)val && uncheckedContainsChar(val);
  }
  @Override public boolean contains(long val){
    int v;
    return size!=0 && (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(float val){
    int v;
    return size!=0 && (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(double val){
    int v;
    return size!=0 && (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      returnFalse:for(;;){
        int v;
        if(val instanceof Character){
          v=(char)val;
        }else if(val instanceof Integer){
          if((v=(int)val)!=(char)v){
            break returnFalse;
          }
        }else if(val instanceof Long){
          long l;
          if((l=(long)val)!=(v=(char)l)){
            break returnFalse;
          }
        }else if(val instanceof Float){
          float f;
          if((f=(float)val)!=(v=(char)f)){
            break returnFalse;
          }
        }else if(val instanceof Double){
          double d;
          if((d=(double)val)!=(v=(char)d)){
            break returnFalse;
          }
        }else if(val instanceof Byte){
          return uncheckedContainsByte((byte)val);
        }else if(val instanceof Short){
          if((v=(short)val)<0){
            break returnFalse;
          }
        }else if(val instanceof Boolean){
          return (this.word0 & (((boolean)val)?2L:1L))!=0;
        }else{
          break returnFalse;
        }
        return uncheckedContainsChar(v);
      }
    }
    return false;
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public void forEach(CharConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action::accept);
    }
  }
  @Override
  public int hashCode(){
    int size;
    if((size=this.size) != 0){
      long magicWord;
      if((int)(magicWord=processWordHashCode(word0,0,64,size)) != 0){
        if((int)(magicWord=processWordHashCode(word1,64,128,magicWord)) != 0){
          if((int)(magicWord=processWordHashCode(word2,128,192,magicWord)) != 0){
            if((size=(int)(magicWord=processWordHashCode(word3,192,256,magicWord))) != 0){
              int hash=(int)(magicWord >>> 32);
              char[] table;
              for(int i=(table=this.table).length;--i >= 0;){
                char tableVal;
                if(((tableVal=table[i])&-2)!=0)
                {
                  hash+=tableVal;
                  if(--size == 0){
                    return hash;
                  }
                }
              }
            }
          }
        }
      }
      return (int)(magicWord >>> 32);
    }
    return 0;
  }
  @Override public OmniIterator.OfChar iterator(){
    return new Itr(this);
  }
  @Override
  public void readExternal(ObjectInput in) throws IOException
  {
      int size;
      this.size=size=in.readInt();
      float loadFactor;
      this.loadFactor=loadFactor=in.readFloat();
      if(size != 0){
          word0=in.readLong();
          word1=in.readLong();
          word2=in.readLong();
          word3=in.readLong();
          tableSize=size=in.readInt();
          if(size != 0){
              int tableSize;
              maxTableSize=(int)((tableSize=tableSizeFor(size)) * loadFactor);
              char[] table;
              this.table=table=new char[tableSize];
              do{
                  quickInsert(table,in.readChar());
              }while(--size != 0);
          }else{
              maxTableSize=1;
          }
      }else{
        maxTableSize=1;
      }
  }
  @Override public boolean remove(Object val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          long word;
          checkTableInt:for(;;){
            if(val instanceof Character){
              v=(char)val;
            }else if(val instanceof Integer){
              if((v=(int)val)!=(char)v){
                break returnFalse;
              }
            }else if(val instanceof Long){
              long l;
              if((l=(long)val)!=(v=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              float f;
              if((f=(float)val)!=(v=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              double d;
              if((d=(double)val)!=(v=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Byte){
              switch((v=(byte)val)>>6){
                case 0:
                  if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                    break returnFalse;
                  }
                  break returnTrue;
                case 1:
                  if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                    break returnFalse;
                  }
                  break returnTrue;
                default:
                  break returnFalse;
              }
            }else if(val instanceof Short){
              if((v=(short)val)<0){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              if((word=this.word0)==(this.word0=(word&(((boolean)val)?~2L:~1L)))){
                break returnFalse;
              }
              break returnTrue;
            }else{
              break returnFalse;
            }
            switch(v>>6){
              case 0:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 2:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 3:
                if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              default:
                break checkTableInt;
            }
          }//checkTableInt
          if(removeFromTable(v)){
            break returnTrue;
          }
          break returnFalse;
        }
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override
  public boolean removeIf(CharPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Character> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
  }  
  @Override public boolean removeVal(boolean val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        long word;
        if((word=this.word0)==(this.word0=(word&(((boolean)val)?~2L:~1L)))){
          break returnFalse;
        }
        break returnTrue;
      }
      --this.size;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(byte val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        long word;
        switch(val>>6){
          case 0:
            if((word=this.word0)==(this.word0=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          case 1:
            if((word=this.word1)==(this.word1=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          default:
            break returnFalse;
        }
      }
      --this.size;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(char val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        long word;
        switch(val>>6){
          case 0:
            if((word=this.word0)==(this.word0=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          case 1:
            if((word=this.word1)==(this.word1=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          case 2:
            if((word=this.word2)==(this.word2=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          case 3:
            if((word=this.word3)==(this.word3=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          default:
            if(!removeFromTable(val)){
              break returnFalse;
            }
            break returnTrue;
        }
      }
      --this.size;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(short val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          long word;
          switch(val>>6){
            case 0:
              if((word=this.word0)==(this.word0=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 1:
              if((word=this.word1)==(this.word1=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 2:
              if((word=this.word2)==(this.word2=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 3:
              if((word=this.word3)==(this.word3=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            default:
              if(val>=0 && removeFromTable(val)){
                break returnTrue;
              }
              break returnFalse;
          }
        }
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          long word;
          switch(val>>6){
            case 0:
              if((word=this.word0)==(this.word0=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 1:
              if((word=this.word1)==(this.word1=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 2:
              if((word=this.word2)==(this.word2=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 3:
              if((word=this.word3)==(this.word3=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            default:
              if(val>=0 && removeFromTable(val)){    
                break returnTrue;
              }
              break returnFalse;
          }
        }
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        returnTrue:for(;;)
        {
          int v;
          if((v=(char)val)==val)
          {
            long word;
            switch(v>>6)
            {
              case 0:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 2:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 3:
                if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              default:
                if(removeFromTable(v)){
                  break returnTrue;
                }
            }
          }
          break returnFalse;
        }
        //returnTrue
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          if((v=(char)val)==val){
            long word;
            switch(v>>6){
              case 0:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 2:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 3:
                if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              default:
            }
            if(removeFromTable(v)){
              break returnTrue;
            }
          }
          break returnFalse;
        }
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          if((v=(char)val)==val){
            long word;
            switch(v>>6){
              case 0:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 2:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 3:
                if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              default:
                if(removeFromTable(v)){
                  break returnTrue;
                }
            }
          }
          break returnFalse;
        }
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size) != 0){
      char[] dst;
      uncheckedCopyIntoArray(size,dst=new char[size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    int size;
    if((size=this.size) != 0){
      Character[] dst;
      uncheckedCopyIntoArray(size,dst=new Character[size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size) != 0){
      double[] dst;
      uncheckedCopyIntoArray(size,dst=new double[size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size) != 0){
      float[] dst;
      uncheckedCopyIntoArray(size,dst=new float[size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size) != 0){
      long[] dst;
      uncheckedCopyIntoArray(size,dst=new long[size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size) != 0){
      int[] dst;
      uncheckedCopyIntoArray(size,dst=new int[size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  private static int wordCopy(long word,int valOffset,int valBound,char[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(char)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,char[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(char)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,Object[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(char)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Object[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(char)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,Character[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(Character)(char)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Character[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(Character)(char)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,double[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(double)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,double[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(double)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,float[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(float)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,float[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(float)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,long[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(long)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,long[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(long)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  private static int wordCopy(long word,int valOffset,int valBound,int[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(int)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,int[] dst){
      int offset;
      if((offset=wordCopy(word0,0,64,dst,0,size)) != size){
          if((offset=wordCopy(word1,64,128,dst,offset,size)) != size){
              if((offset=wordCopy(word2,128,192,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,192,256,dst,offset,size)) != size){
                      final char[] table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(int)(tableVal);
                              if(++offset == size){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int size;
    final T[] arr=arrConstructor.apply(size=this.size);
    if(size != 0){
        uncheckedCopyIntoArray(size,arr);
    }
    return arr;
  }
  @Override public <T> T[] toArray(T[] dst){
    int size;
    if((size=this.size) != 0){
        uncheckedCopyIntoArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
    }else if(dst.length != 0){
        dst[0]=null;
    }
    return dst;
  }
  @Override
  public String toString(){
      int size;
      if((size=this.size) != 0){
          char[] buffer;
          (buffer=new char[size * 3])[0]='[';
          long magicWord;
          if((int)(magicWord=processWordToString(word0,0,64,buffer,size)) != 0){
              if((int)(magicWord=processWordToString(word1,64,128,buffer,magicWord)) != 0){
                  if((int)(magicWord=processWordToString(word2,128,192,buffer,magicWord)) != 0){
                      if((size=(int)(magicWord=processWordToString(word3,192,256,buffer,magicWord))) != 0){
                          int bufferOffset=(int)(magicWord >>> 32);
                          final var table=this.table;
                          for(int i=0;;++i){
                              char tableVal;
                              if(((tableVal=table[i])&-2)!=0)
                              {
                                  buffer[++bufferOffset]=tableVal;
                                  if(--size == 0){
                                      break;
                                  }
                                  buffer[++bufferOffset]=',';
                                  buffer[++bufferOffset]=' ';
                              }
                          }
                          buffer[++bufferOffset]=']';
                          return new String(buffer,0,bufferOffset + 1);
                      }
                  }
              }
          }
          buffer[size=(int)(magicWord >>> 32) + 1]=']';
          return new String(buffer,0,size + 1);
      }
      return "[]";
  }
  @Override
  public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      out.writeFloat(this.loadFactor);
      if(size != 0){
        out.writeLong(word0);
        out.writeLong(word1);
        out.writeLong(word2);
        out.writeLong(word3);
        out.writeInt(size=tableSize);
        if(size != 0){
          char[] table;
          for(int i=(table=this.table).length;--i >= 0;){
            char tableVal;
            if(((tableVal=table[i])&-2)!=0)
            {
              out.writeChar(tableVal);
              if(--size == 0){
                break;
              }
            }
          }
        }
      }
  }
  private boolean addToTable(char val){
    char[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      int hash;
      insertInTable:for(final int initialHash=hash=val&(tableLength=table.length-1);;){
        char tableVal;
        switch(tableVal=table[hash]){
          case 0:
            if(insertHere==-1){
              insertHere=hash;
            }
            break insertInTable;
          case 1:
            insertHere=hash;
            break;
          default:
            if(tableVal==val){
              //already contains
              return false;
            }
        }
        if((hash=hash + 1 & tableLength) == initialHash){
          break insertInTable;
        }
      }
      insert(table,insertHere,val);
      return true;
    }
    int maxTableSize;
    this.table=table=new char[maxTableSize=this.maxTableSize];
    this.tableSize=1;
    table[val&(maxTableSize-1)]=val;  
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  private boolean removeFromTable(int val){
    char[] table;
    char tableVal;
    int tableLength,initialHash;
    if((table=this.table)!=null && (tableVal=table[initialHash=val&(tableLength=table.length-1)])!=0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          this.tableSize=tableSize-1;
          table[hash]=1;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContains(int val){
    char[] table;
    char tableVal;
    int tableLength,initialHash;
    if((table=this.table)!=null && (tableVal=table[initialHash=val&(tableLength=table.length-1)])!=0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  void forEachHelper(int size,CharConsumer action){
      if((size=processWordForEach(word0,0,64,action,size)) != 0){
          if((size=processWordForEach(word1,64,128,action,size)) != 0){
              if((size=processWordForEach(word2,128,192,action,size)) != 0){
                  if((size=processWordForEach(word3,192,256,action,size)) != 0){
                      final var table=this.table;
                      for(int i=0;;++i){
                          char tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              action.accept(tableVal);
                              if(--size == 0){
                                  break;
                              }
                          }
                      }
                  }
              }
          }
      }
  }
  boolean uncheckedRemoveIf(int size,CharPredicate filter){
    long word;
    int numRemoved=Long.bitCount((word=word0) ^ (word0=wordRemoveIf(word,0,filter)))
      + Long.bitCount((word=word1) ^ (word1=wordRemoveIf(word,64,filter)))
      + Long.bitCount((word=word2) ^ (word2=wordRemoveIf(word,128,filter)))
      + Long.bitCount((word=word3) ^ (word3=wordRemoveIf(word,192,filter)));
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      char[] table;
      int newTableSize=0;
      for(int i=(table=this.table).length;;){
        char tableVal;
        if(((tableVal=table[--i])&-2)!=0)
        {
          if(filter.test(tableVal)){
            table[i]=1;
            ++numRemoved;
          }else{
            ++newTableSize;
          }
          if(--tableSize==0){
            break;
          }
        }
      }
      this.tableSize=newTableSize;
    }
    if(numRemoved!=0){
      this.size=size-numRemoved;
      return true;
    }
    return false;
  }
  @Override
  void updateMaxTableSize(float loadFactor){
      char[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
      }
  }
  private void insert(char[] table,int hash,char val){
    int tableSize;
    if((tableSize=++this.tableSize)>=maxTableSize){
      maxTableSize=(int)((hash=table.length<<1)*loadFactor);
      char[] newTable;
      this.table=newTable=new char[hash];
      for(int i=0;;++i){
        char tableVal;
        if(((tableVal=table[i])&-2)!=0)
        {
          quickInsert(newTable,tableVal);
          if(--tableSize==1){
            quickInsert(newTable,val);
            return;
          }
        }
      }
    }else{
      table[hash]=val;
    }
  }
  @Override void clearTable(){
    char[] table;
    for(int i=(table=this.table).length;--i >= 0;){
        table[i]=0;
    }
  }
  private static long processWordToString(long word,int valOffset,int valBound,char[] buffer,long magicWord){
      int bufferOffset=(int)(magicWord >>> 32);
      int numLeft=(int)magicWord;
      do{
          if((word & 1L << valOffset) != 0L){
              buffer[++bufferOffset]=(char)valOffset;
              if(--numLeft == 0){
                  break;
              }
              buffer[++bufferOffset]=',';
              buffer[++bufferOffset]=' ';
          }
      }while(++valOffset != valBound);
      return numLeft | (long)bufferOffset << 32;
  }
  private boolean uncheckedContainsByte(int val){
    switch(val>>6){
      case 0:
        return (this.word0 & (1L<<val))!=0;
      case 1:
        return (this.word1 & (1L<<val))!=0;
      default:
        return false;
    }
  }
  private boolean uncheckedContainsChar(int val){
    switch(val>>6){
      case 0:
        return (this.word0 & (1L<<val))!=0;
      case 1:
        return (this.word1 & (1L<<val))!=0;
      case 2:
        return (this.word2 & (1L<<val))!=0;
      case 3:
        return (this.word3 & (1L<<val))!=0;
      default:
        return tableContains(val);
    }
  }
  private static  int processWordForEach(long word,int valOffset,int valBound,CharConsumer action,int numLeft){
    do{
      if((word & 1L << valOffset) != 0L){
        action.accept((char)valOffset);
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft;
  }
  private static class Itr
  extends AbstractCharItr{
      private final CharOpenAddressHashSet root;
      private int offset;
      Itr(Itr itr){
        this.root=itr.root;
        this.offset=itr.offset;
      }
      Itr(CharOpenAddressHashSet root){
          this.root=root;
          if(root.size != 0){
              int i;
              if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                  offset=i;
              }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                  offset=i + 64;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  offset=i + 128;
              }else if((i=Long.numberOfTrailingZeros(root.word3)) != 64){
                  offset=i + 192;
              }else{
                  final char[] table=root.table;
                  for(i=0;;++i){
                      if(((table[i])&-2)!=0)
                      {
                          offset=256 + i;
                          return;
                      }
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override
      public Object clone(){
        return new Itr(this);
      }
      @Override
      public boolean hasNext(){
          return offset != -1;
      }
      @Override public void forEachRemaining(CharConsumer action){
        int offset;
        if((offset=this.offset)<256){
          forEachRemainingFromWords(offset,action);
        }else{
          forEachRemainingFromTable(offset-256,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        int offset;
        if((offset=this.offset)<256){
          forEachRemainingFromWords(offset,action::accept);
        }else{
          forEachRemainingFromTable(offset-256,action::accept);
        }
      }
      @Override
      public char nextChar(){
          int offset;
          if((offset=this.offset) < 256){
              return getNextFromWords(offset);
          }else{
              return getNextFromTable(offset - 256);
          }
      }
      @Override
      public void remove(){
          CharOpenAddressHashSet root;
          --(root=this.root).size;
          long word;
          int offset;
          switch((offset=this.offset) - 1 >> 6){
          default:
              char[] table;
              if((table=root.table) != null){
                  if(offset == -1){
                      offset=table.length;
                  }else{
                      offset-=256;
                  }
                  for(;;){
                      if(((table[--offset])&-2)!=0)
                      {
                          table[offset]=1;
                          --root.tableSize;
                          return;
                      }
                      if(offset == 0){
                          break;
                      }
                  }
              }else{
                  offset=0;
              }
          case 3:
              if((offset=Long.numberOfLeadingZeros((word=root.word3) << -offset)) != 64){
                  root.word3=word & ~(1L << -1 - offset);
                  return;
              }
              offset=0;
          case 2:
              if((offset=Long.numberOfLeadingZeros((word=root.word2) << -offset)) != 64){
                  root.word2=word & ~(1L << -1 - offset);
                  return;
              }
              offset=0;
          case 1:
              if((offset=Long.numberOfLeadingZeros((word=root.word1) << -offset)) != 64){
                  root.word1=word & ~(1L << -1 - offset);
                  return;
              }
              offset=0;
          case 0:
              root.word0=(word=root.word0) & ~(1L << -1 - Long.numberOfLeadingZeros(word << -offset));
          }
      }
      private static  void forEachRemainingWordHelper(long word,int offset,CharConsumer action){
          for(long marker=1L << offset;;++offset){
              if((word & marker) != 0){
                  action.accept((char)offset);
              }
              if((marker<<=1) == 0){
                  break;
              }
          }
      }
      private void forEachRemainingFromTable(int offset,CharConsumer action){
          char[] table;
          final int tableLength=(table=root.table).length;
          action.accept(table[offset]);
          while(++offset!=tableLength){
              char tableVal;
              if(((tableVal=table[offset])&-2)!=0)
              {
                  action.accept(tableVal);
              }
          }
          this.offset=-1;
      }
      private void forEachRemainingFromWords(int offset,CharConsumer action){
          final var root=this.root;
          switch(offset >> 6){
          case 0:
              forEachRemainingWordHelper(root.word0,offset,action);
              offset=64;
          case 1:
              forEachRemainingWordHelper(root.word1,offset,action);
              offset=128;
          case 2:
              forEachRemainingWordHelper(root.word2,offset,action);
              offset=192;
          case 3:
              forEachRemainingWordHelper(root.word3,offset,action);
              int tableSize;
              if((tableSize=root.tableSize) != 0){
                  final var table=root.table;
                  for(offset=0;;++offset){
                      char tableVal;
                      if(((tableVal=table[offset])&-2)!=0)
                      {
                          action.accept(tableVal);
                          if(--tableSize == 0){
                              break;
                          }
                      }
                  }
              }
              this.offset=-1;
          default:
              return;
          }
      }
      private char getNextFromTable(int offset){
          char[] table;
          final var ret=(char)(table=root.table)[offset];
          for(final int tableLength=table.length;;){
              if(++offset==tableLength){
                this.offset=-1;
                break;
              }
              if(((table[offset])&-2)!=0)
              {
                this.offset=offset + 256;
                break;
              }
          }
          return ret;
      }
      private char getNextFromWords(int offset){
          final var ret=(char)offset;
            returnVal:for(;;){
                final var root=this.root;
                switch(++offset >> 6){
                case 0:
                    int tail0s;
                    if((tail0s=Long.numberOfTrailingZeros(root.word0 >>> offset)) != 64){
                        this.offset=offset+tail0s;
                        break returnVal;
                    }
                    offset=64;
                case 1:
                    if((tail0s=Long.numberOfTrailingZeros(root.word1 >>> offset)) != 64){
                        this.offset=offset + tail0s;
                        break returnVal;
                    }
                    offset=128;
                case 2:
                    if((tail0s=Long.numberOfTrailingZeros(root.word2 >>> offset)) != 64){
                        this.offset=offset + tail0s;
                        break returnVal;
                    }
                    offset=192;
                case 3:
                    if((tail0s=Long.numberOfTrailingZeros(root.word3 >>> offset)) != 64){
                        this.offset=offset + tail0s;
                        break returnVal;
                    }
                    offset=0;
                    break;
                default:
                    offset-=256;
                }
                if(root.tableSize != 0){
                    final var table=root.table;
                    for(;;++offset){
                        if(((table[offset])&-2)!=0)
                        {
                            this.offset=offset + 256;
                            break returnVal;
                        }
                    }
                }else{
                    this.offset=-1;
                }
                break returnVal;
            }
            return ret;
      }
  }
  public static class Checked extends CharOpenAddressHashSet{
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(CharOpenAddressHashSet that){
      super(that);
    }
    public Checked(int initialCapacity){
      super(validateInitialCapacity(initialCapacity));
    }
    public Checked(float loadFactor){
        super(validateLoadFactor(loadFactor));
    }
    public Checked(int initialCapacity,float loadFactor){
        super(validateInitialCapacity(initialCapacity),validateLoadFactor(loadFactor));
    }
    Checked(int initialCapacity,float loadFactor,long word0,long word1,long word2,long word3){
      //for testing purposes
      super(initialCapacity,loadFactor,word0,word1,word2,word3);
    }
    @Override void updateMaxTableSize(float loadFactor){
      super.updateMaxTableSize(validateLoadFactor(loadFactor));
    }
    @Override public boolean add(boolean val){
      if(super.add(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean add(char val){
      if(super.add(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public void clear(){
      if(this.size != 0){
          ++this.modCount;
          this.size=0;
          if(this.tableSize != 0){
              super.clearTable();
              this.tableSize=0;
          }
          word0=0;
          word1=0;
          word2=0;
          word3=0;
      }
    }
    @Override public Object clone(){
      return new Checked(this);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    @Override public boolean remove(Object val){
      if(super.remove(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(short val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      if(super.removeVal(val)){
        ++modCount;
        return true;
      }
      return false;
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
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override void forEachHelper(int size,CharConsumer action){
        int modCount=this.modCount;
        try{
            super.forEachHelper(size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override boolean uncheckedRemoveIf(int size,CharPredicate filter){
      int[] tableIndicesRemoved=null;
      int numRemovedFromTable=0;
      final int modCount=this.modCount;
      long word0;
      long word1;
      long word2;
      long word3;      
      int tableSize;
      int numRemoved;
      char[] table=null;
      try{
          numRemoved=Long.bitCount((word0=this.word0) ^ (word0=wordRemoveIf(word0,0,filter)))
                  + Long.bitCount((word1=this.word1) ^ (word1=wordRemoveIf(word1,64,filter)))
                  + Long.bitCount((word2=this.word2) ^ (word2=wordRemoveIf(word2,128,filter)))
                  + Long.bitCount((word3=this.word3) ^ (word3=wordRemoveIf(word3,192,filter)));
          if((tableSize=this.tableSize) != 0){
            for(int i=(table=this.table).length;;){
              char tableVal;
              if(((tableVal=table[--i])&-2)!=0)
              {
                  if(filter.test(tableVal)){
                      (tableIndicesRemoved=new int[tableSize])[0]=i;
                      ++numRemoved;
                      outer:for(;;){
                        if(--tableSize==0){
                          break;
                        }
                        for(;;){
                           if(((tableVal=table[--i])&-2)!=0)
                           {
                             if(filter.test(tableVal)){
                               tableIndicesRemoved[++numRemovedFromTable]=i;
                               ++numRemoved;
                             }
                             continue outer;
                           }
                        }
                      }
                      break;
                  }
                  if(--tableSize == 0){
                      break;
                  }
               }
            }
          }
      }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
      }
      if(numRemoved != 0){
          this.modCount=modCount + 1;
          this.size=size - numRemoved;
          this.word0=word0;
          this.word1=word1;
          this.word2=word2;
          this.word3=word3;
          if(tableIndicesRemoved != null){
              this.tableSize=tableSize - numRemovedFromTable-1;
              do{
                  table[tableIndicesRemoved[numRemovedFromTable]]=1;
              }while(--numRemovedFromTable !=-1);
          }
      }
      return false;
    }
    private static class Itr
    extends AbstractCharItr{
      private final Checked root;
      private int offset;
      private int modCount;
      private int lastRet;
      Itr(Itr itr){
        this.root=itr.root;
        this.modCount=itr.modCount;
        this.offset=itr.offset;
        this.lastRet=itr.lastRet;
      }
      Itr(Checked root){
          this.root=root;
          this.modCount=root.modCount;
          this.lastRet=-1;
          if(root.size != 0){
              int i;
              if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                  offset=i;
              }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                  offset=i + 64;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  offset=i + 128;
              }else if((i=Long.numberOfTrailingZeros(root.word3)) != 64){
                  offset=i + 192;
              }else{
                  final var table=root.table;
                  for(i=0;;++i){
                      if(table[i] > 1){
                          offset=256 + i;
                          return;
                      }
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          if(offset<256){
            forEachRemainingFromWords(expectedOffset,action);
          }else{
            forEachRemainingFromTable(expectedOffset,action);
          }
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          if(offset<256){
            forEachRemainingFromWords(expectedOffset,action::accept);
          }else{
            forEachRemainingFromTable(expectedOffset,action::accept);
          }
        }
      }
      @Override
      public boolean hasNext(){
          return this.offset != -1;
      }
      @Override
      public char nextChar(){
          Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          int offset;
          if((offset=this.offset) != -1){
              this.lastRet=offset;
              if(offset < 256){
                  return getNextFromWords(root,offset);
              }else{
                  return getNextFromTable(root,offset - 256);
              }
          }
          throw new NoSuchElementException();
      }
      @Override
      public void remove(){
          int lastRet;
          if((lastRet=this.lastRet) != -1){
              int modCount;
              final Checked root;
              CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              --root.size;
              switch(lastRet >> 6){
              case 0:
                  root.word0&=~(1L << lastRet);
                  break;
              case 1:
                  root.word1&=~(1L << lastRet);
                  break;
              case 2:
                  root.word2&=~(1L << lastRet);
                  break;
              case 3:
                  root.word3&=~(1L << lastRet);
                  break;
              default:
                  root.table[lastRet - 256]=1;
                  --root.tableSize;
              }
              this.lastRet=-1;
              return;
          }
          throw new IllegalStateException();
      }
      private static int forEachRemainingWordHelper(long word,int offset,int lastRet,CharConsumer action){
          for(long marker=1L << offset;;++offset){
              if((word & marker) != 0){
                  action.accept((char)offset);
                  lastRet=offset;
              }
              if((marker<<=1) == 0){
                  return lastRet;
              }
          }
      }
      private void forEachRemainingFromTable(final int expectedOffset,CharConsumer action){
          final int modCount=this.modCount;
          final var root=this.root;
          int lastRet;
          int offset;
          try{
              char[] table;
              final int tableLength=(table=root.table).length;
              action.accept(table[lastRet=offset=expectedOffset-256]);
              while(++offset!=tableLength){
                  char tableVal;
                  if(((tableVal=table[offset])&-2)!=0)
                  {
                      action.accept(tableVal);
                      lastRet=offset;
                  }
              }
          }finally{
              if(modCount!=root.modCount || expectedOffset!=this.offset){
                throw new ConcurrentModificationException("modCount{expected="+modCount+",actual="+root.modCount+"},offset{expected="+expectedOffset+";actual="+this.offset+"}");
              }
          }
          this.offset=-1;
          this.lastRet=lastRet+256;
      }
      private void forEachRemainingFromWords(final int expectedOffset,CharConsumer action){
          final var root=this.root;
          final int modCount=this.modCount;
          int lastRet=this.lastRet;
          int offset=expectedOffset;
          try{
              switch(offset >> 6){
              case 0:
                  lastRet=forEachRemainingWordHelper(root.word0,offset,lastRet,action);
                  offset=64;
              case 1:
                  lastRet=forEachRemainingWordHelper(root.word1,offset,lastRet,action);
                  offset=128;
              case 2:
                  lastRet=forEachRemainingWordHelper(root.word2,offset,lastRet,action);
                  offset=192;
              default:
                  lastRet=forEachRemainingWordHelper(root.word3,offset,lastRet,action);
                  int tableSize;
                  if((tableSize=root.tableSize) != 0){
                      final var table=root.table;
                      for(offset=0;;++offset){
                          char tableVal;
                          if(((tableVal=table[offset])&-2)!=0)
                          {
                              action.accept(tableVal);
                              lastRet=offset;
                              if(--tableSize == 0){
                                  break;
                              }
                          }
                      }
                      lastRet+=256;
                  }
              }
          }finally{
              if(modCount!=root.modCount || expectedOffset!=this.offset){
                throw new ConcurrentModificationException("modCount{expected="+modCount+",actual="+root.modCount+"},offset{expected="+expectedOffset+";actual="+this.offset+"}");
              }
          }
          this.lastRet=lastRet;
          this.offset=-1;
      }
      private char getNextFromTable(Checked root,int offset){
          char[] table;
          final var ret=(char)(table=root.table)[offset];
          for(final int tableLength=table.length;;){
              if(++offset==tableLength){
                this.offset=-1;
                break;
              }
              if(((table[offset])&-2)!=0)
              {
                  this.offset=offset + 256;
                  break;
              }
          }
          return ret;
      }
      private char getNextFromWords(Checked root,int offset){
          final var ret=(char)offset;
          returnVal:for(;;){
              switch(++offset >> 6){
              case 0:
                  int tail0s;
                  if((tail0s=Long.numberOfTrailingZeros(root.word0 >>> offset)) != 64){
                      this.offset=offset+tail0s;
                      break returnVal;
                  }
                  offset=64;
              case 1:
                  if((tail0s=Long.numberOfTrailingZeros(root.word1 >>> offset)) != 64){
                      this.offset=offset+tail0s;
                      break returnVal;
                  }
                  offset=128;
              case 2:
                  if((tail0s=Long.numberOfTrailingZeros(root.word2 >>> offset)) != 64){
                      this.offset=offset+tail0s;
                      break returnVal;
                  }
                  offset=192;
              case 3:
                  if((tail0s=Long.numberOfTrailingZeros(root.word3 >>> offset)) != 64){
                      this.offset=offset+tail0s;
                      break returnVal;
                  }
                  offset=0;
                  break;
              default:
                  offset-=256;
              }
              if(root.tableSize != 0){
                  final var table=root.table;
                  for(;;++offset){
                      if(((table[offset])&-2)!=0)
                      {
                          this.offset=offset + 256;
                          break returnVal;
                      }
                  }
              }else{
                  this.offset=-1;
              }
              break returnVal;
          }
          return ret;
      }
    }
  }
}
