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
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.impl.AbstractCharItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
public class CharOpenAddressHashSet
extends AbstractIntegralTypeOpenAddressHashSet
implements OmniSet.OfChar{
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
  char[] table;
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
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word0)!=(this.word0=word|(val?2L:1L))){
      ++this.size;
      return true;
    }
    return false;
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
  private static void quickInsert(char[] table,char val){
    int tableLength;
    for(int hash=(val) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  private boolean addToTable(char val){
    char[] table;
    if((table=this.table)!=null){
      int tableLength;
      int hash;
      int insertHere=-1;
      insertInTable:for(final int initialHash=hash=(val)&(tableLength=table.length-1);;){
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
        if((hash=hash+1&tableLength)==initialHash){
          break insertInTable;
        }
      }
      insert(table,insertHere,val);
      return true;
    }
    int maxTableSize;
    this.table=table=new char[maxTableSize=this.maxTableSize];
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    this.tableSize=1;
    table[(val)&(maxTableSize-1)]=val;
    return true;
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
  public boolean removeIf(CharPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Character> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
  }  
  @Override public boolean add(Character val){
    return add((char)val);
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public Object clone(){
    return new CharOpenAddressHashSet(this);
  }
  private boolean uncheckedRemoveFromTable(int val){
    int tableSize;
    if((tableSize=this.tableSize)!=0){
      char[] table;
      int tableLength,initialHash;
      char tableVal;
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
  @Override public boolean remove(Object val){
    returnFalse:for(;;){
      int size;
      if((size=this.size)!=0){
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
    char[] table;
    for(int i=(table=this.table).length;--i >= 0;){
        table[i]=0;
    }
  }
  @Override
  void updateMaxTableSize(float loadFactor){
      char[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
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
  @Override
  public void readExternal(ObjectInput in) throws IOException{
      int size;
      this.size=size=in.readInt();
      if(size != 0){
          word0=in.readLong();
          word1=in.readLong();
          word2=in.readLong();
          word3=in.readLong();
          tableSize=size=in.readInt();
          this.loadFactor=0.75f;
          if(size != 0){
              int tableSize;
              maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
              char[] table;
              this.table=table=new char[tableSize];
              do{
                  quickInsert(table,in.readChar());
              }while(--size != 0);
          }else{
              maxTableSize=1;
          }
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
                              if((tableVal=table[i]) > 1){
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
  @Override public boolean contains(boolean val){
    return (this.word0 & (val?2L:1L))!=0;
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
  @Override public boolean contains(byte val){
    return uncheckedContainsByte(val);
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
  @Override public boolean contains(char val){
    return uncheckedContainsChar(val);
  }
  @Override public boolean contains(double val){
    int v;
    return (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(float val){
    int v;
    return (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(long val){
    int v;
    return (v=(char)val)==val && uncheckedContainsChar(v);
  }
  @Override public boolean contains(short val){
    return val>=0 && uncheckedContainsChar(val);
  }
  @Override public boolean contains(int val){
    return val==(char)val && uncheckedContainsChar(val);
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
  private boolean tableContains(int val){
    if(tableSize != 0){
        char[] table;
        int tableLength,initialHash;
        char tableVal;
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
}
