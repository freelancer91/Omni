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
  int[] table;
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
              int[] table;
              this.table=table=new int[tableSize];
              do{
                  quickInsert(table,in.readInt());
              }while(--size != 0);
          }else{
              maxTableSize=1;
          }
      }
  }
  @Override
  public String toString(){
      int size;
      if((size=this.size) != 0){
        //TODO toString impl
      }
      return "[]";
  }
  @Override public boolean contains(boolean val){
    return (this.word2 & (val?2L:1L))!=0;
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
}
