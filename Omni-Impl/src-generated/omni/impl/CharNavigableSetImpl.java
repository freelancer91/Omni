package omni.impl;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import omni.function.CharComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class CharNavigableSetImpl
  extends CharUntetheredArrSeq implements OmniSortedSet.OfChar
{
  CharNavigableSetImpl(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharNavigableSetImpl(){
    super();
  }
  private static int privateCompare(int key1,int key2){
    return Integer.signum(key1-key2);
  }
  private boolean uncheckedAdd0(int tail){
    char[] arr;
    int head;
    if((arr=this.arr)[head=this.head]!=0){
      int newHead;
      switch(Integer.signum(tail-(newHead=head-1))){
        case 0:
          //fragmented must grow
          final char[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new char[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
          ArrCopy.uncheckedCopy(arr,head,tmp,newHead=tail-(arrLength-=head),arrLength);
          this.arr=tmp;
          this.head=newHead-1;
          return true;
        default:
          //nonfragmented
          if(newHead==-1 && tail==(newHead=arr.length-1)){
            //must grow
            this.tail=(newHead=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new char[newHead],newHead-=(tail),tail);
            this.arr=arr;
            this.head=newHead-1;
            return true;
          }
        case -1:
          //fragmented
      }
      arr[newHead]=0;
      this.head=newHead;
      return true;
    }
    return false;
  }
  private static IntUnaryOperator getSearchFunction(int key){
    return (k)->privateCompare(k,key);
  }
  @Override public boolean add(char key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key!=0){
        return super.uncheckedAdd(tail,key,CharNavigableSetImpl::privateCompare);
      }
      return uncheckedAdd0(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Character key){
    return add((char)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return super.uncheckedAdd(tail,(char)1,CharNavigableSetImpl::privateCompare);
      }
      return uncheckedAdd0(tail);
    }else{
      super.insertAtMiddle(TypeUtil.castToChar(key));
      return true;
    }
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int i;
      if(key instanceof Character){
        i=(char)key;
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        if((i=((Number)key).intValue())!=(char)i){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(i=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(char)d)){
          return false;
        }
      }else if(key instanceof Boolean){
        i=TypeUtil.castToChar((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int i;
      if(key instanceof Character){
        i=(char)key;
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        if((i=((Number)key).intValue())!=(char)i){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(i=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(char)d)){
          return false;
        }
      }else if(key instanceof Boolean){
        i=TypeUtil.castToChar((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getSearchFunction(i));
    }
    return false;
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(TypeUtil.castToChar(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(TypeUtil.castToChar(key)));
  }
  @Override public boolean contains(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(char)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  private static class AscendingFullView extends CharUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfChar,Cloneable,Serializable
  {
    AscendingFullView(CharUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Character> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfChar iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char[] toCharArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        char[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new char[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new char[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public Character[] toArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Character[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Character[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Character[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new double[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new long[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new long[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new int[size],0,size);
        }else{
          final char[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new int[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  private static class DescendingFullView extends CharUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfChar,Cloneable,Serializable
  {
    DescendingFullView(CharUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Character> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfChar iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char[] toCharArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        char[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new char[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new char[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public Character[] toArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Character[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Character[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Character[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int tail;
      final CharUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new int[size+=arr.length],tail,size-tail);
        }else{
          final char[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new int[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  public static class Ascending extends CharNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public CharComparator comparator(){
      return Character::compare;
    }
    @Override public char firstChar(){
      return (char)arr[head];
    }
    @Override public char lastChar(){
      return (char)arr[tail];
    }
    @Override public OmniSortedSet.OfChar headSet(char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar headSet(Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar tailSet(char fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar tailSet(Character fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar subSet(char fromElement,char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar subSet(Character fromElement,Character toElement){
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
  }
  public static class Descending extends CharNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public CharComparator comparator(){
      return CharComparator::descendingCompare;
    }
    @Override public char firstChar(){
      return (char)arr[tail];
    }
    @Override public char lastChar(){
      return (char)arr[head];
    }
    @Override public OmniSortedSet.OfChar headSet(char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar headSet(Character toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar tailSet(char fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar tailSet(Character fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar subSet(char fromElement,char toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfChar subSet(Character fromElement,Character toElement){
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
      @Override public char[] toCharArray(){
        int tail;
        if((tail=this.tail)!=-1){
          char[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new char[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new char[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override public Character[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Character[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Character[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Character[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfChar.DEFAULT_BOXED_ARR;
      }
      @Override public double[] toDoubleArray(){
        int tail;
        if((tail=this.tail)!=-1){
          double[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int tail;
        if((tail=this.tail)!=-1){
          float[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int tail;
        if((tail=this.tail)!=-1){
          long[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int tail;
        if((tail=this.tail)!=-1){
          int[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new int[size+=arr.length],tail,size-tail);
          }else{
            final char[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new int[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      /*
      @Override public Character ceiling(char val){
        return super.floor(val);
      }
      @Override public Character floor(char val){
        return super.ceiling(val);
      }
      @Override public Character higher(char val){
        return super.lower(val);
      }
      @Override public Character lower(char val){
        return super.higher(val);
      }
      */
      /*
      @Override public char charCeiling(char val){
        return super.charFloor(val);
      }
      @Override public char charFloor(char val){
        return super.charCeiling(val);
      }
      @Override public char higherChar(char val){
        return super.lowerChar(val);
      }
      @Override public char lowerChar(char val){
        return super.higherChar(val);
      }
      */
      /*
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      */
      /*
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      */
      /*
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      */
      /*
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      */
  }
}
