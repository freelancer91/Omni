package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.function.CharComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class CharNavigableSetImpl
  extends CharUntetheredArrSeq implements OmniNavigableSet.OfChar
{
  CharNavigableSetImpl(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharNavigableSetImpl(){
    super();
  }
  @Override public char firstChar(){
    return (char)arr[head];
  }
  @Override public char lastChar(){
    return (char)arr[tail];
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      switch(key){
        case 0:
          return this.uncheckedAdd0(tail);
        case 1:
          return this.uncheckedAdd1(tail);
        default:
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Character key){
    return this.add((char)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return this.uncheckedAdd0(tail);
      }else{
        return this.uncheckedAdd1(tail);
      }
    }else{
      super.insertAtMiddle(TypeUtil.castToChar(key));
      return true;
    }
  }
  abstract int insertionCompare(char key1,char key2);
  abstract boolean uncheckedAdd0(int tail);
  abstract boolean uncheckedAdd1(int tail);
  abstract boolean uncheckedcontains(int tail,int key);
  abstract boolean uncheckedremoveVal(int tail,int key);
  abstract boolean uncheckedcontains(int tail,boolean key);
  abstract boolean uncheckedremoveVal(int tail,boolean key);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer){
        if((k=(int)key)!=(char)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(char)d)){
          return false;
        }
      }else if(key instanceof Byte || key instanceof Short){
        if((k=((Number)key).shortValue())<0){
          return false;
        }
      }else if(key instanceof Boolean){
        return uncheckedcontains(tail,(boolean)key);
      }else{
        return false;
      }
      return uncheckedcontains(tail,k);
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer){
        if((k=(int)key)!=(char)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(char)d)){
          return false;
        }
      }else if(key instanceof Byte || key instanceof Short){
        if((k=((Number)key).shortValue())<0){
          return false;
        }
      }else if(key instanceof Boolean){
        return uncheckedremoveVal(tail,(boolean)key);
      }else{
        return false;
      }
      return uncheckedremoveVal(tail,k);
    }
    return false;
  }
  public static class Ascending extends CharNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Character.MIN_VALUE;
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character ceiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Character floor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character higher(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character lower(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(char toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(char fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(char fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(char fromElement,char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(char fromElement, boolean fromInclusive, char toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(Character fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(Character fromElement,Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final char[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new char[size],0,size);
          cloneTail=size-1;
        }else{
          final char[] arr;
          dst=new char[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
    @Override public CharComparator comparator(){
      return Character::compare;
    }
    @Override boolean uncheckedAdd0(int tail){
      final char[] arr;
      final int head;
      if(((arr=this.arr)[head=this.head])!=0){
        super.insertAtHead(arr,(char)0,head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAdd1(int tail){
      int head;
      char[] arr;
      final char bottomVal;
      if((bottomVal=(arr=this.arr)[head=this.head])!=1){
        if(bottomVal==0){
          //add just after head
          int newHead;
          switch(Integer.signum(tail-(newHead=head-1))){
            case 0:
              //fragmented must grow
              final char[] tmp;
              int arrLength;
              ArrCopy.uncheckedCopy(arr,0,tmp=new char[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
              ArrCopy.semicheckedCopy(arr,head,tmp,head=tail-(arrLength-=(head+1)),arrLength);
              --head;
              newHead=head-1;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newHead==-1){
                if(tail==(newHead=(tail=arr.length)-1)){
                  //must grow
                  this.tail=(head=OmniArray.growBy50Pct(tail))-1;
                  ArrCopy.semicheckedCopy(arr,0,arr=new char[head],head-=newHead,newHead);
                  --head;
                  this.arr=arr;
                }
                newHead=head-1;
              }
            case -1:
              //fragmented
          }
          arr[head]=1;
          arr[newHead]=0;
          this.head=newHead;
        }else{
          super.insertAtHead(arr,(char)1,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedcontains(int tail,boolean key){
      final var arr=this.arr;
      int head=this.head;
      if(key){
        switch(arr[head]){
          case 0:
            return head!=tail && arr[(++head==arr.length)?0:head]==1;
          case 1:
            return true;
          default:
        }
      }
      return arr[head]==0;
    }
    @Override boolean uncheckedremoveVal(int tail,boolean key){
      final var arr=this.arr;
      int head=this.head;
      if(key){
        switch(arr[head]){
          case 0:
            if(head!=tail && arr[(++head==arr.length)?(head=0):head]==1){
              arr[head]=0;
              this.head=head;
              return true;
            }
          default:
            return false;
          case 1:
        }
      }else if(arr[head]!=0){
        return false;
      }
      if(head==tail){
        this.tail=-1;
      }else{
        if(++head==arr.length){
          head=0;
        }
        this.head=head;
      }
      return true;
    }
    private static IntUnaryOperator getQueryComparator(int key){
      return (k)->Integer.signum(key-k);
    }
    @Override boolean uncheckedcontains(int tail,int key){
      switch(key){
        default:
          return super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
        case 1:
          {
            final char[] arr;
            int head;
            switch((arr=this.arr)[head=this.head]){
              case 0:
                return head!=tail && arr[(++head==arr.length)?0:head]==1;
              case 1:
                return true;
              default:
            }
            return false;
          }
        case 0:
          return arr[head]==0;
      }
    }
    @Override boolean uncheckedremoveVal(int tail,int key){
      final char[] arr;
      int head;
      switch(key){
        default:
          return super.uncheckedRemoveMatch(tail,getQueryComparator(key));
        case 1:
          switch((arr=this.arr)[head=this.head]){
            case 0:
              if(head!=tail && arr[(++head==arr.length)?(head=0):head]==1){
                arr[head]=0;
                this.head=head;
                return true;
              }
            default:
              return false;
            case 1:
          }
          break;
        case 0:
          if((arr=this.arr)[head=this.head]!=0){
            return false;
          }
      }
      if(head==tail){
        this.tail=-1;
      }else{
        if(++head==arr.length){
          head=0;
        }
        this.head=head;
      }
      return true;
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key1-key2);
    }
  }
  public static class Descending extends CharNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Character.MIN_VALUE;
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character ceiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Character floor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character higher(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Character lower(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(char toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(char fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(char fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(char fromElement,char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(char fromElement, boolean fromInclusive, char toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar headSet(Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar tailSet(Character fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfChar subSet(Character fromElement,Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final char[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new char[size],0,size);
          cloneTail=size-1;
        }else{
          final char[] arr;
          dst=new char[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
    @Override public CharComparator comparator(){
      return CharComparator::descendingCompare;
    }
    @Override boolean uncheckedAdd0(int tail){
      final char[] arr;
      if(((arr=this.arr)[tail])!=0){
        super.insertAtTail(arr,(char)0,this.head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAdd1(int tail){
      char[] arr;
      final char topVal;
      if((topVal=(arr=this.arr)[tail])!=1){
        int head=this.head;
        if(topVal==0){
          //add it before the tail
          int newTail;
          switch(Integer.signum((newTail=tail+1)-head)){
            case 0:
              //fragmented must grow
              final char[] tmp;
              int arrLength;
              ArrCopy.semicheckedCopy(arr,0,tmp=new char[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
              ArrCopy.uncheckedCopy(arr,newTail,tmp,head-=(arrLength-=newTail),arrLength);
              this.head=head;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newTail==arr.length){
                if(head==0){
                  //must grow
                  ArrCopy.semicheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(newTail)],0,tail);
                  this.arr=arr;
                }else{
                  newTail=0;
                }
              }
            case -1:
              //fragmented
          }
          arr[tail]=1;
          arr[newTail]=0;
          this.tail=newTail;
        }else{
          super.insertAtTail(arr,(char)1,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedcontains(int tail,boolean key){
      final var arr=this.arr;
      if(key){
        switch(arr[tail]){
          case 0:
            return this.head!=tail && arr[(tail==0)?arr.length-1:tail-1]==1;
          case 1:
            return true;
          default:
        }
        return false;
      }
      return arr[tail]==0;
    }
    @Override boolean uncheckedremoveVal(int tail,boolean key){
      final var arr=this.arr;
      if(key){
        switch(arr[tail]){
          case 0:
            if(head!=tail && arr[(--tail==-1)?(tail=arr.length-1):tail]==1){
              arr[tail]=0;
              this.tail=tail;
              return true;
            }
          default:
            return false;
          case 1:
        }
      }else if(arr[tail]!=0){
        return false;
      }
      if(this.head==tail){
        tail=-1;
      }else{
        if(--tail==-1){
          tail=arr.length-1;
        }
      }
      this.tail=tail;
      return true;
    }
    static IntUnaryOperator getQueryComparator(int key){
      return (k)->Integer.signum(k-key);
    }
    @Override boolean uncheckedcontains(int tail,int key){
      switch(key){
        default:
          return super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
        case 1:
          {
            final char[] arr;
            switch((arr=this.arr)[tail]){
              case 0:
                return this.head!=tail && arr[(tail==0)?arr.length-1:tail-1]==1;
              case 1:
                return true;
              default:
            }
            return false;
          }
        case 0:
          return arr[tail]==0;
      }
    }
    @Override boolean uncheckedremoveVal(int tail,int key){
      final char[] arr;
      switch(key){
        default:
          return super.uncheckedRemoveMatch(tail,getQueryComparator(key));
        case 1:
          switch((arr=this.arr)[tail]){
            case 0:
              if(this.head!=tail && arr[(--tail==-1)?(tail=arr.length-1):tail]==1){
                arr[tail]=0;
                this.tail=tail;
                return true;
              }
            default:
              return false;
            case 1:
          }
          break;
        case 0:
          if((arr=this.arr)[tail]!=0){
            return false;
          }
      }
      if(this.head==tail){
        tail=-1;
      }else{
        if(--tail==-1){
          tail=arr.length-1;
        }
      }
      this.tail=tail;
      return true;
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key2-key1);
    }
  }
}
