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
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.impl.AbstractShortItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
public class ShortOpenAddressHashSet
extends AbstractIntegralTypeOpenAddressHashSet
implements OmniSet.OfShort{
  private static  long wordRemoveIf(long word,
  int
  valOffset,ShortPredicate filter){
    long marker=1L;
    for(;;){
        if((word & marker) != 0){
            if(filter.test((short)valOffset)){
                word&=~marker;
            }
        }
        if((marker<<=1) == 0){
            return word;
        }
        ++valOffset;
    }
  }
  short[] table;
  public ShortOpenAddressHashSet(){
    super();
  }
  public ShortOpenAddressHashSet(ShortOpenAddressHashSet that){
    super(that);
    int tableSize;
    if((tableSize=that.tableSize)!=0){
      short[] table;
      this.table=table=new short[tableSizeFor(tableSize)];
      short[] thatTable;
      for(int i=(thatTable=that.table).length;;){
        short tableVal;
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
  public ShortOpenAddressHashSet(int initialCapacity){
    super(initialCapacity);
  }
  public ShortOpenAddressHashSet(float loadFactor){
    super(loadFactor);
  }
  public ShortOpenAddressHashSet(int initialCapacity,float loadFactor){
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
  private void insert(short[] table,int hash,short val){
    int tableSize;
    if((tableSize=++this.tableSize)>=maxTableSize){
      maxTableSize=(int)((hash=table.length<<1)*loadFactor);
      short[] newTable;
      this.table=newTable=new short[hash];
      for(int i=0;;++i){
        short tableVal;
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
  private static void quickInsert(short[] table,short val){
    int tableLength;
    for(int hash=(val) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  private boolean addToTable(short val){
    short[] table;
    if((table=this.table)!=null){
      int tableLength;
      int hash;
      int insertHere=-1;
      insertInTable:for(final int initialHash=hash=(val)&(tableLength=table.length-1);;){
        short tableVal;
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
    this.table=table=new short[maxTableSize=this.maxTableSize];
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
  @Override public boolean add(short val){
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
  boolean uncheckedRemoveIf(int size,ShortPredicate filter){
    long word;
    int numRemoved=Long.bitCount((word=word0) ^ (word0=wordRemoveIf(word,-128,filter)))
      + Long.bitCount((word=word1) ^ (word1=wordRemoveIf(word,-64,filter)))
      + Long.bitCount((word=word2) ^ (word2=wordRemoveIf(word,0,filter)))
      + Long.bitCount((word=word3) ^ (word3=wordRemoveIf(word,64,filter)));
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      short[] table;
      int newTableSize=0;
      for(int i=(table=this.table).length;;){
        short tableVal;
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
  public boolean removeIf(ShortPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Short> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
  }  
  @Override public boolean add(Short val){
    return add((short)val);
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public Object clone(){
    return new ShortOpenAddressHashSet(this);
  }
  private boolean uncheckedRemoveFromTable(int val){
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      short[] table;
      int tableLength,initialHash;
      short tableVal;
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
            if(val>Short.MAX_VALUE || !uncheckedRemoveFromTable(val)){
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
              if(val==(short)val && uncheckedRemoveFromTable(val)){
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
          if((v=(short)val)==val){
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
          if((v=(short)val)==val){
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
          if((v=(short)val)==val){
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
  @Override public boolean removeVal(short val){
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
                    if(val instanceof Short){
                      v=(short)val;
                    }else if(val instanceof Integer){
                      if((v=(int)val)!=(short)v){
                        break returnFalse;
                      }
                    }else if(val instanceof Long){
                      long l;
                      if((l=(long)val)!=(v=(short)l)){
                        break returnFalse;
                      }
                    }else if(val instanceof Float){
                      float f;
                      if((f=(float)val)!=(v=(short)f)){
                        break returnFalse;
                      }
                    }else if(val instanceof Double){
                      double d;
                      if((d=(double)val)!=(v=(short)d)){
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
                    if(v>Short.MAX_VALUE){
                      break returnFalse;
                    }
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
    short[] table;
    for(int i=(table=this.table).length;--i >= 0;){
        table[i]=0;
    }
  }
  @Override
  void updateMaxTableSize(float loadFactor){
      short[] table;
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
                          short[] table;
                          for(int i=(table=this.table).length;--i >= 0;){
                              short tableVal;
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
              short[] table;
              for(int i=(table=this.table).length;--i >= 0;){
                  short tableVal;
                  if(((tableVal=table[i])&-2)!=0)
                  {
                      out.writeShort(tableVal);
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
              short[] table;
              this.table=table=new short[tableSize];
              do{
                  quickInsert(table,in.readShort());
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
  @Override
  public String toString(){
      int size;
      if((size=this.size) != 0){
        byte[] buffer;
        (buffer=new byte[size<<3])[0]='[';
        long magicWord;
        if((int)(magicWord=processWordToString(word0,-128,-64,buffer,size)) != 0){
              if((int)(magicWord=processWordToString(word1,-64,0,buffer,magicWord)) != 0){
                  if((int)(magicWord=processWordToString(word2,0,64,buffer,magicWord)) != 0){
                      if((size=(int)(magicWord=processWordToString(word3,64,128,buffer,magicWord))) != 0){
                          int bufferOffset=(int)(magicWord >>> 32);
                          final var table=this.table;
                          for(int i=0;;++i){
                              short tableVal;
                              if(((tableVal=table[i])&-2)!=0)
                              {
                                  bufferOffset=ToStringUtil.getStringShort(tableVal,buffer,++bufferOffset);
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
      return "[]";
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
        return val<Short.MAX_VALUE && tableContains(val);
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
    return size!=0 && (v=(short)val)==val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(float val){
    int v;
    return size!=0 && (v=(short)val)==val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(long val){
    int v;
    return size!=0 && (v=(short)val)==val && uncheckedContainsInt(v);
  }
  @Override public boolean contains(int val){
    return val==(short)val && uncheckedContainsInt(val);
  }
  @Override public boolean contains(short val){
    return uncheckedContainsInt(val);
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      returnFalse:for(;;){
        int v;
        if(val instanceof Short){
          v=(short)val;
        }else if(val instanceof Integer){
          if((v=(int)val)!=(short)v){
            break returnFalse;
          }
        }else if(val instanceof Long){
          long l;
          if((l=(long)val)!=(v=(short)l)){
            break returnFalse;
          }
        }else if(val instanceof Float){
          float f;
          if((f=(float)val)!=(v=(short)f)){
            break returnFalse;
          }
        }else if(val instanceof Double){
          double d;
          if((d=(double)val)!=(v=(short)d)){
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
        short[] table;
        int tableLength,initialHash;
        short tableVal;
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
  private static  int processWordForEach(long word,int valOffset,int valBound,ShortConsumer action,int numLeft){
    do{
      if((word & 1L << valOffset) != 0L){
        action.accept((short)valOffset);
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft;
  }
  private void forEachHelper(int size,ShortConsumer action){
      if((size=processWordForEach(word0,-128,-64,action,size)) != 0){
          if((size=processWordForEach(word1,-64,0,action,size)) != 0){
              if((size=processWordForEach(word2,0,64,action,size)) != 0){
                  if((size=processWordForEach(word3,64,128,action,size)) != 0){
                      final var table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
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
  @Override public void forEach(ShortConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Short> action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action::accept);
    }
  }
  private static int wordCopy(long word,int valOffset,int valBound,Object[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(short)(valBound);
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
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(short)(tableVal);
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
  @Override public Short[] toArray(){
      int size;
      if((size=this.size) != 0){
          Short[] dst;
          uncheckedCopyIntoArray(size,dst=new Short[size]);
          return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
  }
  @Override public short[] toShortArray(){
      int size;
      if((size=this.size) != 0){
          short[] dst;
          uncheckedCopyIntoArray(size,dst=new short[size]);
          return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
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
  private static int wordCopy(long word,int valOffset,int valBound,short[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(short)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,short[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(short)(tableVal);
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
  private static int wordCopy(long word,int valOffset,int valBound,Short[] dst,int dstOffset,int dstBound){
    do{
        if((word & 1L << valOffset) != 0L){
            dst[dstOffset]=(Short)(short)(valBound);
            if(++dstOffset == dstBound){
                break;
            }
        }
    }while(++valOffset != valBound);
    return dstOffset;  
  }
  private void uncheckedCopyIntoArray(int size,Short[] dst){
      int offset;
      if((offset=wordCopy(word0,-128,-64,dst,0,size)) != size){
          if((offset=wordCopy(word1,-64,0,dst,offset,size)) != size){
              if((offset=wordCopy(word2,0,64,dst,offset,size)) != size){
                  if((offset=wordCopy(word3,64,128,dst,offset,size)) != size){
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
                          if(((tableVal=table[i])&-2)!=0)
                          {
                              dst[offset]=(Short)(short)(tableVal);
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
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
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
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
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
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
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
                      final short[] table=this.table;
                      for(int i=0;;++i){
                          short tableVal;
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
  @Override public int[] toIntArray(){
      int size;
      if((size=this.size) != 0){
          int[] dst;
          uncheckedCopyIntoArray(size,dst=new int[size]);
          return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
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
  @Override public OmniIterator.OfShort iterator(){
    return new Itr(this);
  }
  private static class Itr extends AbstractShortItr{
      private static  void forEachRemainingWordHelper(long word,int valOffset,ShortConsumer action){
          for(long marker=1L << valOffset;;++valOffset){
              if((word & marker) != 0){
                  action.accept((short)valOffset);
              }
              if((marker<<=1) == 0){
                  break;
              }
          }
      }
      private final ShortOpenAddressHashSet root;
      private int valOffset;
      Itr(ShortOpenAddressHashSet root){
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
                  final short[] table=root.table;
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
      public void forEachRemaining(ShortConsumer action){
          int valOffset;
          if((valOffset=this.valOffset) < 256){
              forEachRemainingFromWords(valOffset - 128,action);
          }else{
              forEachRemainingFromTable(valOffset - 256,action);
          }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action){
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
      public short nextShort(){
          int valOffset;
          if((valOffset=this.valOffset) < 256){
              return getNextFromWords(valOffset - 128);
          }else{
              return getNextFromTable(valOffset - 256);
          }
      }
      @Override
      public void remove(){
          ShortOpenAddressHashSet root;
          long word;
          int valOffset;
          --(root=this.root).size;
          switch((valOffset=this.valOffset) - 1 >> 6){
          default:
              short[] table;
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
      private void forEachRemainingFromTable(int valOffset,ShortConsumer action){
          short[] table;
          for(final int tableLength=(table=root.table).length;;){
              short tableVal;
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
      private void forEachRemainingFromWords(int valOffset,ShortConsumer action){
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
                      short tableVal;
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
      private short getNextFromTable(int valOffset){
          short[] table;
          final var ret=(short)(table=root.table)[valOffset];
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
      private short getNextFromWords(int valOffset){
          final var ret=(short)valOffset;
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
  public static class Checked extends ShortOpenAddressHashSet{
    transient int modCount;
    Checked(){
      super();
    }
    Checked(ShortOpenAddressHashSet that){
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
    @Override public boolean add(short val){
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
    @Override public void forEach(ShortConsumer action){
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
    @Override public void forEach(Consumer<? super Short> action){
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
    @Override public OmniIterator.OfShort iterator(){
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
    @Override boolean uncheckedRemoveIf(int size,ShortPredicate filter){
      long word0;
      long word1;
      long word2;
      long word3;
      int[] tableIndicesRemoved=null;
      int numRemovedFromTable=0;
      int tableSize;
      final int modCount=this.modCount;
      int numRemoved;
      short[] table=null;
      try{
          numRemoved=Long.bitCount((word0=this.word0) ^ (word0=wordRemoveIf(word0,-128,filter)))
                  + Long.bitCount((word1=this.word1) ^ (word1=wordRemoveIf(word1,-64,filter)))
                  + Long.bitCount((word2=this.word2) ^ (word2=wordRemoveIf(word2,0,filter)))
                  + Long.bitCount((word3=this.word3) ^ (word3=wordRemoveIf(word3,64,filter)));
          if((tableSize=this.tableSize) != 0){
              tableIndicesRemoved=new int[tableSize];
              for(int i=(table=this.table).length;;){
                  short tableVal;
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
    private static class CheckedItr extends AbstractShortItr{
      private static int forEachRemainingWordHelper(long word,int valOffset,int lastRet,ShortConsumer action){
          for(long marker=1L << valOffset;;++valOffset){
              if((word & marker) != 0){
                  action.accept((short)valOffset);
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
      public void forEachRemaining(ShortConsumer action){
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
      public void forEachRemaining(Consumer<? super Short> action){
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
      public short nextShort(){
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
      private void forEachRemainingFromTable(int valOffset,ShortConsumer action){
          final int modCount=this.modCount;
          final var root=this.root;
          int lastRet=this.lastRet;
          try{
              short[] table;
              for(final int tableLength=(table=root.table).length;;){
                  short tableVal;
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
      private void forEachRemainingFromWords(int valOffset,ShortConsumer action){
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
                          short tableVal;
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
      private short getNextFromTable(Checked root,int valOffset){
          short[] table;
          final var ret=(short)(table=root.table)[valOffset];
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
      private short getNextFromWords(Checked root,int valOffset){
          final var ret=(short)valOffset;
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
