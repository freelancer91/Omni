//#TYPEDEF OfFloat
//#TYPEDEF OfDouble
//#TYPEDEF OfRef
package omni.impl.set;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import omni.impl.AbstractIntItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
public class IntOpenAddressHashSet
extends AbstractIntegralTypeOpenAddressHashSet
implements OmniSet.OfInt{
  private static  long wordRemoveIf(long word,
  int
  valOffset,IntPredicate filter){
    long marker=1L;
    for(;;){
        if((word & marker) != 0){
            if(filter.test((int)valOffset)){
                word&=~marker;
            }
        }
        if((marker<<=1) == 0){
            return word;
        }
        ++valOffset;
    }
  }
  transient int[] table;
  public IntOpenAddressHashSet(){
    super();
  }
  public IntOpenAddressHashSet(IntOpenAddressHashSet that){
    super(that);
    int tableSize;
    if((tableSize=that.tableSize)!=0){
      int[] table;
      this.table=table=new int[tableSizeFor(tableSize)];
      int[] thatTable;
      for(int i=(thatTable=that.table).length;;){
        int tableVal;
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
  public IntOpenAddressHashSet(int initialCapacity){
    super(initialCapacity);
  }
  public IntOpenAddressHashSet(float loadFactor){
    super(loadFactor);
  }
  public IntOpenAddressHashSet(int initialCapacity,float loadFactor){
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
  private void insert(int[] table,int hash,int val){
    int tableSize;
    if((tableSize=++this.tableSize)>=maxTableSize){
      maxTableSize=(int)((hash=table.length<<1)*loadFactor);
      int[] newTable;
      this.table=newTable=new int[hash];
      for(int i=0;;++i){
        int tableVal;
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
  private static void quickInsert(int[] table,int val){
    int tableLength;
    for(int hash=(val) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  private boolean addToTable(int val){
    int[] table;
    if((table=this.table)!=null){
      int tableLength;
      int hash;
      int insertHere=-1;
      insertInTable:for(final int initialHash=hash=(val)&(tableLength=table.length-1);;){
        int tableVal;
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
        if((hash=hash+1&tableLength)==initialHash){
          break insertInTable;
        }
      }
      insert(table,insertHere,val);
      return true;
    }
    int maxTableSize;
    this.table=table=new int[maxTableSize=this.maxTableSize];
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    this.tableSize=1;
    table[(val)&(maxTableSize-1)]=val;
    return true;
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
  boolean uncheckedRemoveIf(int size,IntPredicate filter){
    long word;
    int numRemoved=Long.bitCount((word=word0) ^ (word0=wordRemoveIf(word,-128,filter)))
      + Long.bitCount((word=word1) ^ (word1=wordRemoveIf(word,-64,filter)))
      + Long.bitCount((word=word2) ^ (word2=wordRemoveIf(word,0,filter)))
      + Long.bitCount((word=word3) ^ (word3=wordRemoveIf(word,64,filter)));
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      int[] table;
      int newTableSize=0;
      for(int i=(table=this.table).length;;){
        int tableVal;
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
  public boolean removeIf(IntPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Integer> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
  }  
  @Override public boolean add(Integer val){
    return add((int)val);
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public Object clone(){
    return new IntOpenAddressHashSet(this);
  }
  private boolean uncheckedRemoveFromTable(int val){
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      int[] table;
      int tableLength,initialHash;
      int tableVal;
      if((tableVal=(table=this.table)[initialHash=(val)&(tableLength=table.length-1)])!=0){
        int hash=initialHash;
        do{
          if(tableVal==val){
            table[hash]=1;
            this.tableSize=tableSize-1;
            return true;
          }
        }while((hash=(hash+1)&tableLength)!=initialHash && (tableVal=table[hash])!=0);
      }
    }
    return false;
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
            if(!uncheckedRemoveFromTable(val)){
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
              if(uncheckedRemoveFromTable(val)){
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          if((v=(int)val)==val){
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
                if(uncheckedRemoveFromTable(v)){
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
  @Override public boolean removeVal(float val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          if((double)(v=(int)val)==(double)val){
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
                if(uncheckedRemoveFromTable(v)){
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
  @Override public boolean removeVal(double val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          int v;
          if((v=(int)val)==val){
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
                if(uncheckedRemoveFromTable(v)){
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
  @Override public boolean remove(Object val){
    returnFalse:for(;;){
      int size;
      if((size=this.size)!=0){
        returnTrue:for(;;){
          int v;
          long word;
          checkTableInt:for(;;){
            checkInt:for(;;){
              checkByte:for(;;){
                checkChar:for(;;){
                  checkBoolean:for(;;){
                    if(val instanceof Integer || val instanceof Short){
                      v=((Number)val).intValue();
                    }else if(val instanceof Long){
                      long l;
                      if((l=(long)val)!=(v=(int)l)){
                        break returnFalse;
                      }
                    }else if(val instanceof Float){
                      float f;
                      if((double)(f=(float)val)!=(double)(v=(int)f)){
                        break returnFalse;
                      }
                    }else if(val instanceof Double){
                      double d;
                      if((d=(double)val)!=(v=(int)d)){
                        break returnFalse;
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
          if(!uncheckedRemoveFromTable(v)){
            break returnFalse;
          }
          break returnTrue;
        }
        this.size=size-1;
        return true;
      }
      break returnFalse;
    }
    return false;
  }
  @Override void clearTable(){
    int[] table;
    for(int i=(table=this.table).length;--i >= 0;){
        table[i]=0;
    }
  }
  @Override
  void updateMaxTableSize(float loadFactor){
      int[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
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
                          int[] table;
                          for(int i=(table=this.table).length;--i >= 0;){
                              int tableVal;
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
  @Override
  public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size != 0){
          out.writeLong(word0);
          out.writeLong(word1);
          out.writeLong(word2);
          out.writeLong(word3);
          out.writeInt(size=tableSize);
          if(size != 0){
              int[] table;
              for(int i=(table=this.table).length;--i >= 0;){
                  int tableVal;
                  if(((tableVal=table[i])&-2)!=0)
                  {
                      out.writeInt(tableVal);
                      if(--size == 0){
                          break;
                      }
                  }
              }
          }
      }
  }
  @Override
  public void readExternal(ObjectInput in) throws IOException{
      int size;
      this.size=size=in.readInt();
      this.loadFactor=0.75f;
      if(size != 0){
          word0=in.readLong();
          word1=in.readLong();
          word2=in.readLong();
          word3=in.readLong();
          tableSize=size=in.readInt();
          if(size != 0){
              int tableSize;
              maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
              int[] table;
              this.table=table=new int[tableSize];
              do{
                  quickInsert(table,in.readInt());
              }while(--size != 0);
          }else{
              maxTableSize=1;
          }
      }else{
        maxTableSize=1;
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
              if(--numLeft == 0){
                  break;
              }
              builder.uncheckedAppendCommaAndSpace();
          }
      }while(++valOffset != valBound);
      return numLeft;
  }
  @Override
  public String toString(){
      int size;
      if((size=this.size) != 0){
        if(size<=(OmniArray.MAX_ARR_SIZE/13)){
          return quickToString(size);
        }else{
          return massiveToString(size);
        }
      }
      return "[]";
  }
  private String quickToString(int size){
    byte[] buffer;
    (buffer=new byte[size*13])[0]='[';
    long magicWord;
    if((int)(magicWord=processWordToString(word0,-128,-64,buffer,size)) != 0){
      if((int)(magicWord=processWordToString(word1,-64,0,buffer,magicWord)) != 0){
        if((int)(magicWord=processWordToString(word2,0,64,buffer,magicWord)) != 0){
          if((size=(int)(magicWord=processWordToString(word3,64,128,buffer,magicWord))) != 0){
            int bufferOffset=(int)(magicWord >>> 32);
            final var table=this.table;
            for(int i=0;;++i){
              int tableVal;
              if(((tableVal=table[i])&-2)!=0)
              {
                bufferOffset=ToStringUtil.getStringInt(tableVal,buffer,++bufferOffset);
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
    buffer[size=(int)(magicWord >>> 32) + 1]=']';
    return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
  }
  private String massiveToString(int size){
    byte[] buffer;
    ToStringUtil.OmniStringBuilderByte builder;
    if((size=processWordToString(word0,-128,-64,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]),size)) != 0){
      if((size=processWordToString(word1,-64,0,builder,size)) != 0){
        if((size=processWordToString(word2,0,64,builder,size)) != 0){
          if((size=processWordToString(word3,64,128,builder,size)) != 0){
            final var table=this.table;
            for(int i=0;;++i){
              int tableVal;
              if(((tableVal=table[i])&-2)!=0)
              {
                builder.uncheckedAppendInt(tableVal);
                if(--size == 0){
                  break;
                }
                builder.uncheckedAppendCommaAndSpace();
              }
            }
          }
        }
      }
    }
    builder.uncheckedAppendChar((byte)']');
    (buffer=builder.buffer)[0]=(byte)'[';
    return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
  }
  @Override public boolean contains(boolean val){
    return (this.word2 & (val?2L:1L))!=0;
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
  @Override public boolean contains(byte val){
    return uncheckedContainsByte(val);
  }
  @Override public boolean contains(char val){
    return uncheckedContainsChar(val);
  }
  @Override public boolean contains(double val){
    int v;
    return size!=0 && (double)(v=(int)val)==val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(float val){
    int v;
    return size!=0 && (double)(v=(int)val)==(double)val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(long val){
    int v;
    return size!=0 && (v=(int)val)==val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(int val){
    return uncheckedContainsInt(val);
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      returnFalse:for(;;){
        int v;
        if(val instanceof Integer || val instanceof Short){
          v=((Number)val).intValue();
        }else if(val instanceof Long){
          long l;
          if((l=(long)val)!=(v=(int)l)){
            break returnFalse;
          }
        }else if(val instanceof Float){
          float f;
          if((double)(f=(float)val)!=(double)(v=(int)f)){
            break returnFalse;
          }
        }else if(val instanceof Double){
          double d;
          if((d=(double)val)!=(v=(int)d)){
            break returnFalse;
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
        return uncheckedContainsInt(v);
      }
    }
    return false;
  }
  private boolean tableContains(int val){
    if(tableSize != 0){
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(val) & (tableLength=table.length - 1)]) != 0){
            int hash=initialHash;
            do{
                if(tableVal == val){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
        }
    }
    return false;
  }
  private static  int processWordForEach(long word,int valOffset,int valBound,IntConsumer action,int numLeft){
    do{
      if((word & 1L << valOffset) != 0L){
        action.accept((int)valOffset);
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft;
  }
  private void forEachHelper(int size,IntConsumer action){
      if((size=processWordForEach(word0,-128,-64,action,size)) != 0){
          if((size=processWordForEach(word1,-64,0,action,size)) != 0){
              if((size=processWordForEach(word2,0,64,action,size)) != 0){
                  if((size=processWordForEach(word3,64,128,action,size)) != 0){
                      final var table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
  @Override public void forEach(IntConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action::accept);
    }
  }
  private static int wordCopy(long word,int valOffset,int valBound,Object[] dst,int dstOffset,int dstBound){
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
  private void uncheckedCopyIntoArray(int size,Object[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
  @Override public Integer[] toArray(){
      int size;
      if((size=this.size) != 0){
          Integer[] dst;
          uncheckedCopyIntoArray(size,dst=new Integer[size]);
          return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
  private static int wordCopy(long word,int valOffset,int valBound,Integer[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(Integer)(int)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Integer[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(Integer)(int)(tableVal);
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
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final int[] table=this.table;
                      for(int i=0;;++i){
                          int tableVal;
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
  @Override public long[] toLongArray(){
      int size;
      if((size=this.size) != 0){
          long[] dst;
          uncheckedCopyIntoArray(size,dst=new long[size]);
          return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
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
  @Override public OmniIterator.OfInt iterator(){
    return new Itr(this);
  }
  private static class Itr extends AbstractIntItr{
      private static  void forEachRemainingWordHelper(long word,int valOffset,IntConsumer action){
          for(long marker=1L << valOffset;;++valOffset){
              if((word & marker) != 0){
                  action.accept((int)valOffset);
              }
              if((marker<<=1) == 0){
                  break;
              }
          }
      }
      private final IntOpenAddressHashSet root;
      private int valOffset;
      Itr(IntOpenAddressHashSet root){
          this.root=root;
          if(root.size != 0){
              int i;
              if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                  valOffset=i;
              }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                  valOffset=i + 64;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  valOffset=i + 128;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  valOffset=i + 192;
              }else{
                  final int[] table=root.table;
                  for(i=0;;++i){
                      if(((table[i])&-2)!=0)
                      {
                          valOffset=256 + i;
                          return;
                      }
                  }
              }
          }else{
              valOffset=-1;
          }
      }
      @Override
      public void forEachRemaining(IntConsumer action){
          int valOffset;
          if((valOffset=this.valOffset) < 256){
              forEachRemainingFromWords(valOffset - 128,action);
          }else{
              forEachRemainingFromTable(valOffset - 256,action);
          }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action){
          int valOffset;
          if((valOffset=this.valOffset) < 256){
              forEachRemainingFromWords(valOffset - 128,action::accept);
          }else{
              forEachRemainingFromTable(valOffset - 256,action::accept);
          }
      }
      @Override
      public boolean hasNext(){
          return valOffset != -1;
      }
      @Override
      public int nextInt(){
          int valOffset;
          if((valOffset=this.valOffset) < 256){
              return getNextFromWords(valOffset - 128);
          }else{
              return getNextFromTable(valOffset - 256);
          }
      }
      @Override
      public void remove(){
          IntOpenAddressHashSet root;
          long word;
          int valOffset;
          --(root=this.root).size;
          switch((valOffset=this.valOffset) - 1 >> 6){
          default:
              int[] table;
              if((table=root.table) != null){
                  if(valOffset == -1){
                      valOffset=table.length;
                  }else{
                      valOffset-=256;
                  }
                  for(;;){
                      if(((table[--valOffset])&-2)!=0)
                      {
                          table[valOffset]=1;
                          --root.tableSize;
                          return;
                      }
                      if(valOffset == 0){
                          break;
                      }
                  }
              }else{
                  valOffset=0;
              }
          case 3:
              if((valOffset=Long.numberOfLeadingZeros((word=root.word3) << -valOffset)) != 64){
                  root.word3=word & ~(1L << -1 - valOffset);
                  return;
              }
              valOffset=0;
          case 2:
              if((valOffset=Long.numberOfLeadingZeros((word=root.word2) << -valOffset)) != 64){
                  root.word2=word & ~(1L << -1 - valOffset);
                  return;
              }
              valOffset=0;
          case 1:
              if((valOffset=Long.numberOfLeadingZeros((word=root.word1) << -valOffset)) != 64){
                  root.word1=word & ~(1L << -1 - valOffset);
                  return;
              }
              valOffset=0;
          case 0:
              root.word0=(word=root.word0) & ~(1L << -1 - Long.numberOfLeadingZeros(word << -valOffset));
          }
      }
      private void forEachRemainingFromTable(int valOffset,IntConsumer action){
          int[] table;
          for(final int tableLength=(table=root.table).length;;){
              int tableVal;
              if(((tableVal=table[valOffset])&-2)!=0)
              {
                  action.accept(tableVal);
              }
              if(++valOffset == tableLength){
                  this.valOffset=-1;
                  return;
              }
          }
      }
      private void forEachRemainingFromWords(int valOffset,IntConsumer action){
          final var root=this.root;
          switch(valOffset >> 6){
          case -2:
              forEachRemainingWordHelper(root.word0,valOffset,action);
              valOffset=-64;
          case -1:
              forEachRemainingWordHelper(root.word1,valOffset,action);
              valOffset=0;
          case 0:
              forEachRemainingWordHelper(root.word2,valOffset,action);
              valOffset=64;
          case 1:
              forEachRemainingWordHelper(root.word3,valOffset,action);
              int tableSize;
              if((tableSize=root.tableSize) != 0){
                  final var table=root.table;
                  for(valOffset=0;;++valOffset){
                      int tableVal;
                      if(((tableVal=table[valOffset])&-2)!=0)
                      {
                          action.accept(tableVal);
                          if(--tableSize == 0){
                              break;
                          }
                      }
                  }
              }
              this.valOffset=-1;
          default:
              return;
          }
      }
      private int getNextFromTable(int valOffset){
          int[] table;
          final var ret=(int)(table=root.table)[valOffset];
          for(final int tableLength=table.length;;){
              if(((table[valOffset])&-2)!=0)
              {
                  this.valOffset=valOffset + 256;
                  break;
              }
              if(++valOffset == tableLength){
                  this.valOffset=-1;
                  break;
              }
          }
          return ret;
      }
      private int getNextFromWords(int valOffset){
          final var ret=(int)valOffset;
            returnVal:for(;;){
                final var root=this.root;
                switch(++valOffset >> 6){
                case -2:
                    if((valOffset=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                        this.valOffset=valOffset;
                        break returnVal;
                    }
                    valOffset=0;
                case -1:
                    if((valOffset=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 64;
                        break returnVal;
                    }
                    valOffset=0;
                case 0:
                    if((valOffset=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 128;
                        break returnVal;
                    }
                    valOffset=0;
                case 1:
                    if((valOffset=Long.numberOfTrailingZeros(root.word3 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 192;
                        break returnVal;
                    }
                    valOffset=0;
                    break;
                default:
                    valOffset-=128;
                }
                if(root.tableSize != 0){
                    final var table=root.table;
                    for(;;++valOffset){
                        if(((table[valOffset])&-2)!=0)
                        {
                            this.valOffset=valOffset + 256;
                            break returnVal;
                        }
                    }
                }else{
                    this.valOffset=-1;
                }
                break returnVal;
            }
            return ret;
      }
  }
  public static class Checked extends IntOpenAddressHashSet{
    transient int modCount;
    Checked(){
      super();
    }
    Checked(IntOpenAddressHashSet that){
      super(that);
    }
    Checked(int initialCapacity){
      super(initialCapacity);
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
      if(size != 0){
          ++modCount;
          if(tableSize != 0){
              super.clearTable();
              tableSize=0;
          }
          word0=0;
          word1=0;
          word2=0;
          word3=0;
          size=0;
      }
    }
    @Override public Object clone(){
      return new Checked(this);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void forEach(IntConsumer action){
      int size;
      if((size=this.size) != 0){
        final int modCount=this.modCount;
        try{
          super.forEachHelper(size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Integer> action){
      int size;
      if((size=this.size) != 0){
        final int modCount=this.modCount;
        try{
          super.forEachHelper(size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new CheckedItr(this);
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
    @Override boolean uncheckedRemoveIf(int size,IntPredicate filter){
      long word0;
      long word1;
      long word2;
      long word3;
      int[] tableIndicesRemoved=null;
      int numRemovedFromTable=0;
      int tableSize;
      final int modCount=this.modCount;
      int numRemoved;
      int[] table=null;
      try{
          numRemoved=Long.bitCount((word0=this.word0) ^ (word0=wordRemoveIf(word0,-128,filter)))
                  + Long.bitCount((word1=this.word1) ^ (word1=wordRemoveIf(word1,-64,filter)))
                  + Long.bitCount((word2=this.word2) ^ (word2=wordRemoveIf(word2,0,filter)))
                  + Long.bitCount((word3=this.word3) ^ (word3=wordRemoveIf(word3,64,filter)));
          if((tableSize=this.tableSize) != 0){
              tableIndicesRemoved=new int[tableSize];
              for(int i=(table=this.table).length;;){
                  int tableVal;
                  if(((tableVal=table[--i])&-2)!=0)
                  {
                      if(filter.test(tableVal)){
                          tableIndicesRemoved[numRemovedFromTable++]=i;
                          ++numRemoved;
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
          if(numRemovedFromTable != 0){
              this.tableSize=tableSize - numRemovedFromTable;
              do{
                  table[tableIndicesRemoved[--numRemovedFromTable]]=1;
              }while(numRemovedFromTable != 0);
          }
      }
      return false;
    }
    private static class CheckedItr extends AbstractIntItr{
      private static int forEachRemainingWordHelper(long word,int valOffset,int lastRet,IntConsumer action){
          for(long marker=1L << valOffset;;++valOffset){
              if((word & marker) != 0){
                  action.accept((int)valOffset);
                  lastRet=valOffset;
              }
              if((marker<<=1) == 0){
                  return lastRet;
              }
          }
      }
      private final Checked root;
      private int valOffset;
      private int modCount;
      private int lastRet;
      CheckedItr(Checked root){
          modCount=root.modCount;
          this.root=root;
          if(root.size != 0){
              int i;
              if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                  valOffset=i;
              }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                  valOffset=i + 64;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  valOffset=i + 128;
              }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                  valOffset=i + 192;
              }else{
                  final var table=root.table;
                  for(i=0;;++i){
                      if(table[i] > 1){
                          valOffset=256 + i;
                          return;
                      }
                  }
              }
          }else{
              valOffset=-1;
          }
          lastRet=-1;
      }
      @Override
      public void forEachRemaining(IntConsumer action){
          int valOffset;
          if((valOffset=this.valOffset) != -1){
              if(valOffset < 256){
                  forEachRemainingFromWords(valOffset - 128,action);
              }else{
                  forEachRemainingFromTable(valOffset - 256,action);
              }
          }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action){
          int valOffset;
          if((valOffset=this.valOffset) != -1){
              if(valOffset < 256){
                  forEachRemainingFromWords(valOffset - 128,action::accept);
              }else{
                  forEachRemainingFromTable(valOffset - 256,action::accept);
              }
          }
      }
      @Override
      public boolean hasNext(){
          return valOffset != -1;
      }
      @Override
      public int nextInt(){
          Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          int valOffset;
          if((valOffset=this.valOffset) != -1){
              lastRet=valOffset;
              if(valOffset < 256){
                  return getNextFromWords(root,valOffset - 128);
              }else{
                  return getNextFromTable(root,valOffset - 256);
              }
          }
          throw new NoSuchElementException();
      }
      @Override
      public void remove(){
          int lastRet;
          if((lastRet=this.lastRet) != -1){
              int modCount;
              Checked root;
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
          }
      }
      private void forEachRemainingFromTable(int valOffset,IntConsumer action){
          final int modCount=this.modCount;
          final var root=this.root;
          int lastRet=this.lastRet;
          try{
              int[] table;
              for(final int tableLength=(table=root.table).length;;){
                  int tableVal;
                  if(((tableVal=table[valOffset])&-2)!=0)
                  {
                      action.accept(tableVal);
                      lastRet=valOffset + 256;
                  }
                  if(++valOffset == tableLength){
                      break;
                  }
              }
          }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.valOffset=-1;
          this.lastRet=lastRet;
      }
      private void forEachRemainingFromWords(int valOffset,IntConsumer action){
          final var root=this.root;
          final int modCount=this.modCount;
          int lastRet=this.lastRet;
          try{
              switch(valOffset >> 6){
              case -2:
                  lastRet=forEachRemainingWordHelper(root.word0,valOffset,lastRet,action);
                  valOffset=-64;
              case -1:
                  lastRet=forEachRemainingWordHelper(root.word1,valOffset,lastRet,action);
                  valOffset=0;
              case 0:
                  lastRet=forEachRemainingWordHelper(root.word2,valOffset,lastRet,action);
                  valOffset=64;
              default:
                  lastRet=forEachRemainingWordHelper(root.word3,valOffset,lastRet,action);
                  int tableSize;
                  if((tableSize=root.tableSize) != 0){
                      final var table=root.table;
                      for(valOffset=0;;++valOffset){
                          int tableVal;
                          if(((tableVal=table[valOffset])&-2)!=0)
                          {
                              action.accept(tableVal);
                              lastRet=valOffset + 256;
                              if(--tableSize == 0){
                                  break;
                              }
                          }
                      }
                  }
              }
          }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=lastRet;
          this.valOffset=-1;
      }
      private int getNextFromTable(Checked root,int valOffset){
          int[] table;
          final var ret=(int)(table=root.table)[valOffset];
          for(final int tableLength=table.length;;){
              if(((table[valOffset])&-2)!=0)
              {
                  this.valOffset=valOffset + 256;
                  break;
              }
              if(++valOffset == tableLength){
                  this.valOffset=-1;
                  break;
              }
          }
          return ret;
      }
      private int getNextFromWords(Checked root,int valOffset){
          final var ret=(int)valOffset;
          returnVal:for(;;){
              switch(++valOffset >> 6){
              case -2:
                  if((valOffset=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                      this.valOffset=valOffset;
                      break returnVal;
                  }
                  valOffset=0;
              case -1:
                  if((valOffset=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                      this.valOffset=valOffset + 64;
                      break returnVal;
                  }
                  valOffset=0;
              case 0:
                  if((valOffset=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                      this.valOffset=valOffset + 128;
                      break returnVal;
                  }
                  valOffset=0;
              case 1:
                  if((valOffset=Long.numberOfTrailingZeros(root.word3 >>> valOffset)) != 64){
                      this.valOffset=valOffset + 192;
                      break returnVal;
                  }
                  valOffset=0;
                  break;
              default:
                  valOffset-=128;
              }
              if(root.tableSize != 0){
                  final var table=root.table;
                  for(;;++valOffset){
                      if(((table[valOffset])&-2)!=0)
                      {
                          this.valOffset=valOffset + 256;
                          break returnVal;
                      }
                  }
              }else{
                  this.valOffset=-1;
              }
              break returnVal;
          }
          return ret;
      }
    }
  }
}
