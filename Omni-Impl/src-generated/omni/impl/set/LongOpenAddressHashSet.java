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
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import omni.impl.AbstractLongItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.util.ToStringUtil;
public class LongOpenAddressHashSet
extends AbstractIntegralTypeOpenAddressHashSet<Long>
implements OmniSet.OfLong{
  //TODO long hash codes for negative values are different from int hashcodes of the same value. This is a problem
  private  static long processWordHashCode(long word,int valOffset,int valBound,long magicWord){
    int hash=(int)(magicWord >>> 32);
    int numLeft=(int)magicWord;
    do{
        if((word & 1L << valOffset) != 0L){
            long v;
            hash+=(int)((v=valOffset)^(v>>>32));
            if(--numLeft == 0){
                break;
            }
        }
    }while(++valOffset != valBound);
    return numLeft | (long)hash << 32;
  }
  private static void quickInsert(long[] table,long val){
    int tableLength;
    int hash;
    for(hash=((int)(val^(val>>>32))) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  private static  long wordRemoveIf(long word,
  long
  valOffset,LongPredicate filter){
    long newWord=0L;
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(word))!=64;++valOffset,word>>>=1){
      word>>>=tail0s;
      if(!filter.test((long)(valOffset+=tail0s))){
        newWord|=(1L<<valOffset);
      }
    }
    return newWord;
  }
  transient long[] table;
  public LongOpenAddressHashSet(){
    super();
  }
  public LongOpenAddressHashSet(LongOpenAddressHashSet that){
    super(that);
    int tableSize;
    if((tableSize=that.tableSize)!=0){
      long[] table;
      int tableLength;
      this.table=table=new long[tableLength=tableSizeFor(tableSize)];
      this.maxTableSize=(int)(tableLength*loadFactor);
      long[] thatTable;
      for(tableLength=(thatTable=that.table).length;;){
        long tableVal;
        if(((tableVal=thatTable[--tableLength])&-2)!=0)
        {
          quickInsert(table,tableVal);
          if(--tableSize==0){
            break;
          }
        }
      }
    }
  }
  public LongOpenAddressHashSet(int initialCapacity){
    super(initialCapacity);
  }
  public LongOpenAddressHashSet(float loadFactor){
    super(loadFactor);
  }
  public LongOpenAddressHashSet(int initialCapacity,float loadFactor){
    super(initialCapacity,loadFactor);
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(this.word2=word|(val?2L:1L))){
      ++this.size;
      return true;
    }
    return false;
  }
  @Override public boolean add(byte val){
    returnFalse:for(;;){
      long word,mask=1L<<val;
      switch(val>>6){
      case -2:
        if((word=this.word0) == (this.word0=word | (mask))){
          break returnFalse;
        }
        break;
      case -1:
        if((word=this.word1) == (this.word1=word | (mask))){
          break returnFalse;
        }
        break;
      case 0:
        if((word=this.word2) == (this.word2=word | (mask))){
          break returnFalse;
        }
        break;
      default:
        if((word=this.word3) == (this.word3=word | (mask))){
          break returnFalse;
        }
        break;
      }
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
        if((word=this.word2) == (this.word2=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 1:
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
  @Override public boolean add(int val){
    returnFalse:for(;;){
      long word;
      switch(val >> 6){
      case -2:
        if((word=this.word0) == (this.word0=word | (1L << val))){
          break returnFalse;
        }
        break;
      case -1:
        if((word=this.word1) == (this.word1=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 0:
        if((word=this.word2) == (this.word2=word | (1L << val))){
          break returnFalse;
        }
        break;
      case 1:
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
  @Override public boolean add(long val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        addToTable:for(;;){
          int v;
          if((v=(int)val)==val){
            long word;
            switch(v>>6){
              case -2:
                if((word=this.word0) == (this.word0=word | (1L<<v))){
                  break returnFalse;
                }
                break;
              case -1:
                if((word=this.word1) == (this.word1=word | (1L<<v))){
                  break returnFalse;
                }
                break;
              case 0:
                if((word=this.word2) == (this.word2=word | (1L<<v))){
                  break returnFalse;
                }
                break;
              case 1:
                if((word=this.word3) == (this.word3=word | (1L<<v))){
                  break returnFalse;
                }
                break;
              default:
                break addToTable;
            }
            break returnTrue;
          }
          break addToTable;
        }
        if(!addToTable(val)){
          break returnFalse;
        }
        break returnTrue;
      }
      ++this.size;
      return true;
    }
    return false;
  }
  @Override public boolean add(Long val){
    return add((long)val);
  }
  @Override public Object clone(){
    return new LongOpenAddressHashSet(this);
  }
  @Override public boolean contains(boolean val){
    return (this.word2 & (val?2L:1L))!=0;
  }
  @Override public boolean contains(byte val){
    return uncheckedContainsByte(val);
  }
  @Override public boolean contains(char val){
    return uncheckedContainsChar(val);
  }
  @Override public boolean contains(int val){
    return uncheckedContainsInt(val);
  }
  @Override public boolean contains(long val){
    if(size==0){
      return false;
    }
    int v;
    if((v=(int)val)==val){
      return uncheckedContainsInt(v);
    }
    return tableContains(val);
  }
  @Override public boolean contains(float val){
    long l;
    if(size==0 || !TypeUtil.floatEquals(val,l=(long)val)){
      return false;
    }
    int v;
    if((v=(int)l)!=l){
      return tableContains(l);
    }
    return uncheckedContainsInt(v);
  }
  @Override public boolean contains(double val){
    long l;
    if(size==0 || !TypeUtil.doubleEquals(val,l=(long)val)){
      return false;
    }
    int v;
    if((v=(int)l)!=l){
      return tableContains(l);
    }
    return uncheckedContainsInt(v);
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      returnFalse:for(;;){
        int v;
        containsInt:for(;;){
          long l;
          if(val instanceof Long){
            if((l=(long)val)==(v=(int)l)){
              break containsInt;
            }
          }else if(val instanceof Integer || val instanceof Short){
            v=((Number)val).intValue();
            break containsInt;
          }else if(val instanceof Float){
            float f;
            if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
              break returnFalse;
            }
            if((v=(int)l)==l){
              break containsInt;
            }
          }else if(val instanceof Double){
            double d;
            if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
              break returnFalse;
            }
            if((v=(int)l)==l){
              break containsInt;
            }
          }else if(val instanceof Byte){
            return uncheckedContainsByte((byte)val);
          }else if(val instanceof Character){
            return uncheckedContainsChar((char)val);
          }else if(val instanceof Boolean){
            return (this.word2 & (((boolean)val)?2L:1L))!=0;
          }else{
            break returnFalse;
          }
          return tableContains(l);
        }
        return uncheckedContainsInt(v);
      }
    }
    return false;
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public void forEach(LongConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
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
      if((int)(magicWord=processWordHashCode(word0,-128,-64,size)) != 0){
        if((int)(magicWord=processWordHashCode(word1,-64,0,magicWord)) != 0){
          if((int)(magicWord=processWordHashCode(word2,0,64,magicWord)) != 0){
            if((size=(int)(magicWord=processWordHashCode(word3,64,128,magicWord))) != 0){
              int hash=(int)(magicWord >>> 32);
              long[] table;
              for(int i=(table=this.table).length;;){
                long tableVal;
                if(((tableVal=table[--i])&-2)!=0)
                {
                  hash+=((int)(tableVal^(tableVal>>>32)));
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
  @Override public OmniIterator.OfLong iterator(){
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
              long[] table;
              this.table=table=new long[tableSize];
              do{
                  quickInsert(table,in.readLong());
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
            checkInt:for(;;){
              checkByte:for(;;){
                checkChar:for(;;){
                  checkBoolean:for(;;){
                    long l;
                    checkTableLong:for(;;){
                      if(val instanceof Long){
                        if((l=(long)val)!=(v=(int)l)){
                          break checkTableLong;
                        }
                      }else if(val instanceof Integer || val instanceof Short){
                        v=((Number)val).intValue();
                      }else if(val instanceof Float){
                        float f;
                        if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                          break returnFalse;
                        }
                        if((v=(int)l)!=l){
                          break checkTableLong;
                        }
                      }else if(val instanceof Double){
                        double d;
                        if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                          break returnFalse;
                        }
                        if((v=(int)l)!=l){
                          break checkTableLong;
                        }
                      }else if(val instanceof Byte){
                        break checkByte;
                      }else if(val instanceof Character){
                        break checkChar;
                      }else if(val instanceof Boolean){
                        break checkBoolean;
                      }else{
                        break returnFalse;
                      }
                      break checkInt;
                    }//checkTableLong
                    if(!removeFromTable(l)){
                      break returnFalse;
                    }
                    break returnTrue;
                  }//checkBoolean
                  if((word=this.word2)==(this.word2=(word&(((boolean)val)?~2L:~1L)))){
                    break returnFalse;
                  }
                  break returnTrue;
                }//checkChar
                switch((v=(char)val)>>6){
                  case 0:
                    if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                      break returnFalse;
                    }
                    break returnTrue;
                  case 1:
                    if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                      break returnFalse;
                    }
                    break returnTrue;
                  default:
                    break checkTableInt;
                }
              }//checkByte
              long mask=~(1L<<(v=(byte)val));
              switch(v>>6){
                case -2:
                  if((word=this.word0)==(this.word0=(word&(mask)))){
                    break returnFalse;
                  }
                  break returnTrue;
                case -1:
                  if((word=this.word1)==(this.word1=(word&(mask)))){
                    break returnFalse;
                  }
                  break returnTrue;
                case 0:
                  if((word=this.word2)==(this.word2=(word&(mask)))){
                    break returnFalse;
                  }
                  break returnTrue;
                default:
                  if((word=this.word3)==(this.word3=(word&(mask)))){
                    break returnFalse;
                  }
                  break returnTrue;
              }
            }//checkInt
            switch(v>>6){
              case -2:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case -1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 0:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:
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
  public boolean removeIf(LongPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Long> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
  }  
  @Override public boolean removeVal(boolean val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        long word;
        if((word=this.word2)==(this.word2=(word&(((boolean)val)?~2L:~1L)))){
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
        long mask=~(1L<<val);
        switch(val>>6){
          case -2:
            if((word=this.word0)==(this.word0=(word&(mask)))){
              break returnFalse;
            }
            break returnTrue;
          case -1:
            if((word=this.word1)==(this.word1=(word&(mask)))){
              break returnFalse;
            }
            break returnTrue;
          case 0:
            if((word=this.word2)==(this.word2=(word&(mask)))){
              break returnFalse;
            }
            break returnTrue;
          default:
            if((word=this.word3)==(this.word3=(word&(mask)))){
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
  @Override public boolean removeVal(char val){
    returnFalse:for(;;){
      returnTrue:for(;;){
        long word;
        switch(val>>6){
          case 0:
            if((word=this.word2)==(this.word2=(word&(~(1L<<val))))){
              break returnFalse;
            }
            break returnTrue;
          case 1:
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
  @Override public boolean removeVal(int val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          long word;
          switch(val>>6){
            case -2:
              if((word=this.word0)==(this.word0=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case -1:
              if((word=this.word1)==(this.word1=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 0:
              if((word=this.word2)==(this.word2=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            case 1:
              if((word=this.word3)==(this.word3=(word&(~(1L<<val))))){
                break returnFalse;
              }
              break returnTrue;
            default:
              if(removeFromTable(val)){
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
          if((v=(int)val)==val)
          {
            long word;
            switch(v>>6)
            {
              case -2:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case -1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 0:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:             
                if((word=this.word3)==(this.word3=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              default:
            }
          }
          if(removeFromTable(val)){
            break returnTrue;
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
          long l;
          if(TypeUtil.floatEquals(val,l=(long)val)){
            int v;
            if((v=(int)l)!=l){
              if(removeFromTable(l)){
                break returnTrue;
              }
              break returnFalse;
            }
            long word;
            switch(v>>6){
              case -2:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case -1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 0:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:              
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
          long l;
          if(TypeUtil.doubleEquals(val,l=(long)val)){
            int v;
            if((v=(int)l)!=l){
              if(!removeFromTable(l)){
                break returnFalse;
              }
              break returnTrue;
            }
            long word;
            switch(v>>6){
              case -2:
                if((word=this.word0)==(this.word0=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case -1:
                if((word=this.word1)==(this.word1=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 0:
                if((word=this.word2)==(this.word2=(word&(~(1L<<v))))){
                  break returnFalse;
                }
                break returnTrue;
              case 1:              
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
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size) != 0){
      long[] dst;
      uncheckedCopyIntoArray(size,dst=new long[size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    int size;
    if((size=this.size) != 0){
      Long[] dst;
      uncheckedCopyIntoArray(size,dst=new Long[size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
  private static int wordCopy(long word,int valOffset,int valBound,long[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(long)(valOffset);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,long[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final long[] table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
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
  private static int wordCopy(long word,int valOffset,int valBound,Object[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(long)(valOffset);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Object[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final long[] table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
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
  private static int wordCopy(long word,int valOffset,int valBound,Long[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(Long)(long)(valOffset);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Long[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final long[] table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(Long)(long)(tableVal);
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
            dst[dstOffset]=(double)(valOffset);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,double[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final long[] table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
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
            dst[dstOffset]=(float)(valOffset);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,float[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final long[] table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
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
        if(size<=(OmniArray.MAX_ARR_SIZE/22)){
          return quickToString(size);
        }else{
          return massiveToString(size);
        }
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
          long[] table;
          for(int i=(table=this.table).length;;){
            long tableVal;
            if(((tableVal=table[--i])&-2)!=0)
            {
              out.writeLong(tableVal);
              if(--size == 0){
                break;
              }
            }
          }
        }
      }
  }
  private boolean addToTable(long val){
    long[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      int hash;
      insertInTable:for(final int initialHash=hash=((int)(val^(val>>>32)))&(tableLength=table.length-1);;){
        long tableVal;
        if((tableVal=table[hash])==0L){
          if(insertHere==-1){
            insertHere=hash;
          }
          break insertInTable;
        }else if(tableVal==1L){
          insertHere=hash;
        }else if(tableVal==val){
          //already contains
          return false;
        }
        if((hash=hash + 1 & tableLength) == initialHash){
          break insertInTable;
        }
      }
      insert(table,insertHere,val);
      return true;
    }
    int maxTableSize;
    this.table=table=new long[maxTableSize=this.maxTableSize];
    this.tableSize=1;
    table[((int)(val^(val>>>32)))&(maxTableSize-1)]=val;
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  private boolean removeFromTable(long val){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((table=this.table)!=null && (tableVal=table[initialHash=((int)(val^(val>>>32)))&(tableLength=table.length-1)])!=0){
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
  private boolean tableContains(long val){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((table=this.table)!=null && (tableVal=table[initialHash=((int)(val^(val>>>32)))&(tableLength=table.length-1)])!=0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  void forEachHelper(int size,LongConsumer action){
      if((size=processWordForEach(word0,-128,-64,action,size)) != 0){
          if((size=processWordForEach(word1,-64,0,action,size)) != 0){
              if((size=processWordForEach(word2,0,64,action,size)) != 0){
                  if((size=processWordForEach(word3,64,128,action,size)) != 0){
                      final var table=this.table;
                      for(int i=0;;++i){
                          long tableVal;
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
  boolean uncheckedRemoveIf(int size,LongPredicate filter){
    long word;
    int numRemoved=Long.bitCount((word=word0) ^ (word0=wordRemoveIf(word,-128,filter)))
      + Long.bitCount((word=word1) ^ (word1=wordRemoveIf(word,-64,filter)))
      + Long.bitCount((word=word2) ^ (word2=wordRemoveIf(word,0,filter)))
      + Long.bitCount((word=word3) ^ (word3=wordRemoveIf(word,64,filter)));
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      long[] table;
      int newTableSize=0;
      for(int i=(table=this.table).length;;){
        long tableVal;
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
      long[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
      }
  }
  private void insert(long[] table,int hash,long val){
    int tableSize;
    if((tableSize=++this.tableSize)>=maxTableSize){
      maxTableSize=(int)((hash=table.length<<1)*loadFactor);
      long[] newTable;
      this.table=newTable=new long[hash];
      if(tableSize!=1){
        for(int i=0;;++i){
          long tableVal;
          if(((tableVal=table[i])&-2)!=0)
          {
            quickInsert(newTable,tableVal);
            if(--tableSize==1){
              break;
            }
          }
        }
      }
      quickInsert(newTable,val);
    }else{
      table[hash]=val;
    }
  }
  private String massiveToString(int size){
    byte[] buffer;
    ToStringUtil.OmniStringBuilderByte builder;
    size=processWordToString(word3,64,128,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]),processWordToString(word2,0,64,builder,processWordToString(word1,-64,0,builder,processWordToString(word0,-128,-64,builder,size))));
    final var table=this.table;
    for(int i=0;;++i){
      long tableVal;
      if(((tableVal=table[i])&-2)!=0)
      {
        builder.uncheckedAppendLong(tableVal);
        if(--size == 0){
          break;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    builder.uncheckedAppendChar((byte)']');
    (buffer=builder.buffer)[0]=(byte)'[';
    return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
  }
  private String quickToString(int size){
    byte[] buffer;
    (buffer=new byte[size*22])[0]='[';
    long magicWord;
    if((int)(magicWord=processWordToString(word0,-128,-64,buffer,size)) != 0){
      if((int)(magicWord=processWordToString(word1,-64,0,buffer,magicWord)) != 0){
        if((int)(magicWord=processWordToString(word2,0,64,buffer,magicWord)) != 0){
          if((size=(int)(magicWord=processWordToString(word3,64,128,buffer,magicWord))) != 0){
            int bufferOffset=(int)(magicWord >>> 32);
            final var table=this.table;
            for(int i=0;;++i){
              long tableVal;
              if(((tableVal=table[i])&-2)!=0)
              {
                bufferOffset=ToStringUtil.getStringLong(tableVal,buffer,++bufferOffset);
                if(--size == 0){
                  break;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }
            buffer[bufferOffset]=']';
            return new String(buffer,0,bufferOffset + 1,ToStringUtil.IOS8859CharSet);
          }
        }
      }
    }
    buffer[size=(int)(magicWord >>> 32)]=']';
    return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
  }
  @Override void clearTable(){
    long[] table;
    for(int i=(table=this.table).length;--i >= 0;){
        table[i]=0;
    }
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
  private static int processWordToString(long word,int valOffset,int valBound,ToStringUtil.OmniStringBuilderByte builder,int numLeft){
      do{
          if((word & 1L << valOffset) != 0L){
              builder.uncheckedAppendShort(valOffset);
              --numLeft;
              builder.uncheckedAppendCommaAndSpace();
          }
      }while(++valOffset != valBound);
      return numLeft;
  }
  private boolean uncheckedContainsByte(int val){
    long mask=1L<<val;
    switch(val>>6){
      case -2:
        return (this.word0 & (mask))!=0;
      case -1:
        return (this.word1 & (mask))!=0;
      case 0:
        return (this.word2 & (mask))!=0;
      default:
        return (this.word3 & (mask))!=0;
    }
  }
  private boolean uncheckedContainsChar(int val){
    switch(val>>6){
      case 0:
        return (this.word2 & (1L<<val))!=0;
      case 1:
        return (this.word3 & (1L<<val))!=0;
      default:
        return tableContains(val);
    }
  }
  private boolean uncheckedContainsInt(int val){
    switch(val>>6){
      case -2:
        return (this.word0 & (1L<<val))!=0;
      case -1:
        return (this.word1 & (1L<<val))!=0;
      case 0:
        return (this.word2 & (1L<<val))!=0;
      case 1:
        return (this.word3 & (1L<<val))!=0;
      default:
        return tableContains(val);
    }
  }
  private static  int processWordForEach(long word,int valOffset,int valBound,LongConsumer action,int numLeft){
    do{
      if((word & 1L << valOffset) != 0L){
        action.accept((long)valOffset);
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft;
  }
  private static class Itr
  extends AbstractLongItr{
      private final LongOpenAddressHashSet root;
      private int offset;
      Itr(Itr itr){
        this.root=itr.root;
        this.offset=itr.offset;
      }
      Itr(LongOpenAddressHashSet root){
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
                  final long[] table=root.table;
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
      @Override public void forEachRemaining(LongConsumer action){
        int offset;
        if((offset=this.offset)<256){
          forEachRemainingFromWords(offset,action);
        }else{
          forEachRemainingFromTable(offset-256,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        int offset;
        if((offset=this.offset)<256){
          forEachRemainingFromWords(offset,action::accept);
        }else{
          forEachRemainingFromTable(offset-256,action::accept);
        }
      }
      @Override
      public long nextLong(){
          int offset;
          if((offset=this.offset) < 256){
              return getNextFromWords(offset);
          }else{
              return getNextFromTable(offset - 256);
          }
      }
      @Override
      public void remove(){
          LongOpenAddressHashSet root;
          --(root=this.root).size;
          long word;
          int offset;
          switch((offset=this.offset) - 1 >> 6){
          default:
              long[] table;
              if((table=root.table) != null){
                  int tableOffset;
                  if(offset == -1){
                      tableOffset=table.length;
                  }else{
                      tableOffset=offset-256;
                  }
                  for(;;){
                      if(((table[--tableOffset])&-2)!=0)
                      {
                          table[tableOffset]=1;
                          --root.tableSize;
                          return;
                      }
                      if(tableOffset == 0){
                          break;
                      }
                  }
              }
              offset=0;
          case 3:
              if((offset=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>-offset)))!=64){
                root.word3=word&~(Long.MIN_VALUE>>>offset);
                return;
              }
              offset=0;
          case 2:
              if((offset=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>-offset)))!=64){
                root.word2=word&~(Long.MIN_VALUE>>>offset);
                return;
              }
              offset=0;
          case 1:
              if((offset=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>-offset)))!=64){
                root.word1=word&~(Long.MIN_VALUE>>>offset);
                return;
              }
              offset=0;
          case 0:
              root.word0=(word=root.word0)&~(Long.MIN_VALUE>>>Long.numberOfLeadingZeros(word&(-1L>>>-offset)));
          }
      }
      private static  void forEachRemainingWordHelper(long word,int offset,LongConsumer action){
          for(long marker=1L << offset;;++offset){
              if((word & marker) != 0){
                  action.accept((long)(offset-128));
              }
              if((marker<<=1) == 0){
                  break;
              }
          }
      }
      private void forEachRemainingFromTable(int offset,LongConsumer action){
          long[] table;
          final int tableLength=(table=root.table).length;
          action.accept(table[offset]);
          while(++offset!=tableLength){
              long tableVal;
              if(((tableVal=table[offset])&-2)!=0)
              {
                  action.accept(tableVal);
              }
          }
          this.offset=-1;
      }
      private void forEachRemainingFromWords(int offset,LongConsumer action){
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
                      long tableVal;
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
      private long getNextFromTable(int offset){
          long[] table;
          final var ret=(long)(table=root.table)[offset];
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
      private long getNextFromWords(int offset){
          final var ret=(long)(offset-128);
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
  public static class Checked extends LongOpenAddressHashSet{
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(LongOpenAddressHashSet that){
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
    @Override public boolean add(byte val){
      if(super.add(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean add(long val){
      if(super.add(val)){
        ++modCount;
        return true;
      }
      return false;
    }
    @Override public boolean add(int val){
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
    @Override public OmniIterator.OfLong iterator(){
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
    @Override void forEachHelper(int size,LongConsumer action){
        int modCount=this.modCount;
        try{
            super.forEachHelper(size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override boolean uncheckedRemoveIf(int size,LongPredicate filter){
      int numRemovedFromTable=0;
      final int modCount=this.modCount;
      int[] tableIndicesRemoved=null;
      long word0;
      long word1;
      long word2;
      long word3;      
      int tableSize;
      int numRemoved;
      long[] table=null;
      try{
          numRemoved=Long.bitCount((word0=this.word0) ^ (word0=wordRemoveIf(word0,-128,filter)))
                  + Long.bitCount((word1=this.word1) ^ (word1=wordRemoveIf(word1,-64,filter)))
                  + Long.bitCount((word2=this.word2) ^ (word2=wordRemoveIf(word2,0,filter)))
                  + Long.bitCount((word3=this.word3) ^ (word3=wordRemoveIf(word3,64,filter)));
          if((tableSize=this.tableSize) != 0){
            for(int i=(table=this.table).length,numLeftInTable=tableSize;;){
              long tableVal;
              if(((tableVal=table[--i])&-2)!=0)
              {
                  if(filter.test(tableVal)){
                      (tableIndicesRemoved=new int[numLeftInTable])[0]=i;
                      ++numRemoved;
                      outer:for(;;){
                        if(--numLeftInTable==0){
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
                  if(--numLeftInTable == 0){
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
          return true;
      }
      return false;
    }
    private static class Itr
    extends AbstractLongItr{
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
      @Override public void forEachRemaining(LongConsumer action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          if(offset<256){
            forEachRemainingFromWords(expectedOffset,action);
          }else{
            forEachRemainingFromTable(expectedOffset,action);
          }
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
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
      public long nextLong(){
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
      private static int forEachRemainingWordHelper(long word,int offset,int lastRet,LongConsumer action){
          for(long marker=1L << offset;;++offset){
              if((word & marker) != 0){
                  action.accept((long)(offset-128));
                  lastRet=offset;
              }
              if((marker<<=1) == 0){
                  return lastRet;
              }
          }
      }
      private void forEachRemainingFromTable(final int expectedOffset,LongConsumer action){
          final int modCount=this.modCount;
          final var root=this.root;
          int lastRet;
          int offset;
          try{
              long[] table;
              final int tableLength=(table=root.table).length;
              action.accept(table[lastRet=offset=expectedOffset-256]);
              while(++offset!=tableLength){
                  long tableVal;
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
      private void forEachRemainingFromWords(final int expectedOffset,LongConsumer action){
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
                          long tableVal;
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
      private long getNextFromTable(Checked root,int offset){
          long[] table;
          final var ret=(long)(table=root.table)[offset];
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
      private long getNextFromWords(Checked root,int offset){
          final var ret=(long)(offset-128);
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
