package omni.impl.seq;
import omni.util.CharSortUtil;
import omni.api.OmniList;
import omni.impl.CharDblLnkNode;
import java.util.function.Consumer;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import java.util.function.UnaryOperator;
import omni.function.CharUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractCharItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.CharComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollection;
public abstract class CharDblLnkSeq extends AbstractSeq implements
   CharSubListDefault
{
  private static final long serialVersionUID=1L;
  transient CharDblLnkNode head;
  transient CharDblLnkNode tail;
  private  CharDblLnkSeq(){
  }
  private CharDblLnkSeq(CharDblLnkNode head,int size,CharDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(char val);
  @Override public boolean add(char val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,CharDblLnkNode after,CharDblLnkNode newNode){
    newNode.next=after=CharDblLnkNode.iterateDescending(after,dist-2);
    final CharDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,CharDblLnkNode before,CharDblLnkNode newNode){
    newNode.prev=before=CharDblLnkNode.iterateAscending(before,dist-1);
    final CharDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,CharDblLnkNode newNode){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      //the insertion point is closer to the tail
      var tail=this.tail;
      if(tailDist==1){
        //the insertion point IS the tail
        newNode.prev=tail;
        tail.next=newNode;
        this.tail=newNode;
      }else{
        //iterate from the root's tail
        iterateDescendingAndInsert(tailDist,tail,newNode);
      }
    }else{
      //the insertion point is closer to the head
      CharDblLnkNode head;
      if((head=this.head)==null){
        //the root was empty, so initialize it
        this.head=newNode;
        this.tail=newNode;
      }else if(index==0){
        //the insertion point IS the head
        head.prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,head,newNode);
      }
    }
  }
  private static  int markSurvivors(CharDblLnkNode curr,int numLeft,CharPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if(--numLeft==0)
        {
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
        curr=curr.next;
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static  long markSurvivors(CharDblLnkNode curr,int numLeft,CharPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1,curr=curr.next){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if(--numLeft==0)
      {
        return word;
      }
    }
  }
  private CharDblLnkNode getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return CharDblLnkNode.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return CharDblLnkNode.iterateAscending(head,index);
    }
  }
  private CharDblLnkNode getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return CharDblLnkNode.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return CharDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public char set(int index,char val){
    CharDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,char val){
    getNode(index,size).val=val;
  }
  @Override public char getChar(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(CharConsumer action){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      CharDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      CharDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size)!=0){
      final char[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new char[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Character[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new Character[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public void replaceAll(CharUnaryOperator operator){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Character> operator){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(CharComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      {
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Character> sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      {
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      {
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      {
          CharSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(CharComparator sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      {
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          {
            CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final char[] buffer;
      CharDblLnkNode.uncheckedToString(head,tail,buffer=new char[size=this.size*3]);
      buffer[0]='[';
      buffer[size-1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  @Override public int hashCode(){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      return CharDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    if(val==(char)val)
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if((v=(char)val)==val){
            return CharDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val)!=(char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte||val instanceof Short){
              if((i=((Number)val).shortValue())<0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedcontains(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    if(val>=0)
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    if(val>=0)
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    if(val==(char)val)
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if((v=(char)val)==val){
            return CharDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val)!=(char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte||val instanceof Short){
              if((i=((Number)val).shortValue())<0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedindexOf(head,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    if(val>=0)
    {
      {
        final CharDblLnkNode head;
        if((head=this.head)!=null)
        {
          return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    if(val==(char)val)
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if((v=(char)val)==val){
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val)!=(char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte||val instanceof Short){
              if((i=((Number)val).shortValue())<0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    if(val>=0)
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static  int collapseBodyHelper(CharDblLnkNode newHead,CharDblLnkNode newTail,CharPredicate filter){
    int numRemoved=0;
    outer:for(CharDblLnkNode prev;(newHead=(prev=newHead).next)!=newTail;){
      if(filter.test(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next)==newTail){
            newHead.prev=prev;
            prev.next=newHead;
            break outer;
          }
        }while(filter.test(newHead.val));
        newHead.prev=prev;
        prev.next=newHead;
      }
    }
    return numRemoved;
  }
  private static class UncheckedSubList extends CharDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private Object writeReplace(){
      return new UncheckedList(this.head,this.size,this.tail);
    }
    private void bubbleUpAppend(CharDblLnkNode newNode){
      for(var curr=this;;){
        curr.tail=newNode;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpAppend(CharDblLnkNode newNode,CharDblLnkNode oldTail){
      for(var curr=this;;){
        curr.tail=newNode;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.tail!=oldTail){
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newNode){
      for(var curr=this;;){
        curr.head=newNode;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newNode,CharDblLnkNode oldHead){
      for(var curr=this;;){
        curr.head=newNode;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.head!=oldHead){
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;
      ++curr.size,curr=curr.parent){}
    }
    @Override public void add(int index,char val){
      int size;
      UncheckedSubList curr;
      final var newNode=new CharDblLnkNode(val);
      if((size=++(curr=this).size)==1){
        //initialize this list
        UncheckedSubList parent;
        do{
          curr.head=newNode;
          curr.tail=newNode;
          if((parent=curr.parent)==null){
            //all parents were empty, insert in the root
            ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
            return;
          }
        }
        while((size=++(curr=parent).size)==1);
      }
      final UncheckedList root;
      ++(root=this.root).size;
      CharDblLnkNode before,after;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        if(size==1){
          //the insertion point IS the tail
          if((after=(before=curr.tail).next)==null){
            //there are no nodes after this list
            curr.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            curr.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          //iterate from the tail and insert
          before=(after=CharDblLnkNode.iterateDescending(curr.tail,size-1)).prev;
          after.prev=newNode;
          curr.bubbleUpIncrementSize();
        }
        before.next=newNode;
      }else{
        //the insertion point is closer to the head
        if(index==0){
          //the insertion point IS the tail
          if((before=(after=curr.head).prev)==null){
            //there are no nodes before this list
            curr.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            //there are nodes before this list
            curr.bubbleUpPrepend(newNode,after);
            before.next=newNode;
          }
        }else{
          //iterate from the head and insert
          after=(before=CharDblLnkNode.iterateAscending(curr.head,index-1)).next;
          before.next=newNode;
          curr.bubbleUpIncrementSize();
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    @Override void addLast(char val){
      final UncheckedList root=this.root;
      var newNode=new CharDblLnkNode(val);
      UncheckedSubList parent,curr=this;
      for(;++curr.size==1;curr=parent){
        curr.head=newNode;
        curr.tail=newNode;
        if((parent=curr.parent)==null){
          //all parents were empty, insert in the root
          ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
          return;
        }
      }
      CharDblLnkNode oldTail,after;
      if((after=(oldTail=curr.tail).next)==null){
        curr.bubbleUpAppend(newNode);
        root.tail=newNode;
      }else{
        curr.bubbleUpAppend(newNode,oldTail);
        after.prev=newNode;
      }
      ++root.size;
      newNode.next=after;
      oldTail.next=newNode;
      newNode.prev=oldTail;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead,CharDblLnkNode oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       UncheckedSubList parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(CharDblLnkNode tail){
      CharDblLnkNode after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(CharDblLnkNode lastNode){
      CharDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(CharDblLnkNode head){
      CharDblLnkNode after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public char removeCharAt(int index){
      final char ret;
      int size;
      if((size=(--this.size)-index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          CharDblLnkNode before;
          ret=(before=( tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          CharDblLnkNode after;
          ret=(after=( head=CharDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(CharPredicate filter){
      final CharDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private void collapsehead(CharDblLnkNode oldhead,CharDblLnkNode tail,CharPredicate filter
    ){
      int numRemoved=1;
      CharDblLnkNode newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          break;
        }
        if(!filter.test(newhead.val)){
          CharDblLnkNode prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      UncheckedList root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      CharDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(CharDblLnkNode oldtail,CharDblLnkNode head,CharPredicate filter
    ){
      int numRemoved=1;
      CharDblLnkNode newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          break;
        }
        if(!filter.test(newtail.val)){
          CharDblLnkNode next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      UncheckedList root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      CharDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(CharDblLnkNode oldHead,CharDblLnkNode newHead,int numRemoved,CharDblLnkNode newTail,CharDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final CharDblLnkNode after,before=oldHead.prev;
      if((after=oldHead.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          UncheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      CharDblLnkNode tail;
      {
        if(filter.test((tail=this.tail).val)){
          if(tail==head){
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
              );
            }else{
              collapsetail(tail,head,filter
              );
            }
          }
          return true;
        }else{
          if(tail!=head){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
              );
            }
          }
        }
      }
      return false;
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        final UncheckedList root;
        (root=this.root).size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,CharDblLnkNode head,CharDblLnkNode tail,UncheckedList root){
      CharDblLnkNode before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      var curr=this;
      do{
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(CharDblLnkNode before,CharDblLnkNode head,int numRemoved,CharDblLnkNode tail,CharDblLnkNode after){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(CharDblLnkNode tail, CharDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(CharDblLnkNode head, CharDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void collapseHeadAndTail(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter
    ){
      CharDblLnkNode newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            CharDblLnkNode prev;
            outer: for(var curr=(prev=newHead).next;curr!=tail;curr=(prev=curr).next){
              if(filter.test(curr.val)){
                do{
                  ++numRemoved;
                  if((curr=curr.next)==tail){
                    break outer;
                  }
                }while(filter.test(curr.val));
                prev.next=curr;
                curr.prev=prev;
              }
            }
            this.size-=numRemoved;
            root.size-=numRemoved; 
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,prev,tail);
            return;
          }else if((newHead=newHead.next)==tail){
            break;
          }
        }
      }
      UncheckedList root;
      int size;
      (root=this.root).size-=(size=this.size);
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter
    ){
      for(CharDblLnkNode prev;(head=(prev=head).next)!=tail;){
        if(filter.test(head.val)){
          int numRemoved=1;
          for(;(head=head.next)!=tail;++numRemoved){
            if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,filter);
              break;
            }
          }
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static class AscendingItr
      extends AbstractCharItr
    {
      transient final UncheckedSubList parent;
      transient CharDblLnkNode curr;
      private AscendingItr(UncheckedSubList parent,CharDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedSubList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          CharDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          CharDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar{
      private transient int currIndex;
      private transient CharDblLnkNode lastRet;
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public char previousChar(){
        CharDblLnkNode curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(char val){
        lastRet.val=val;
      }
      @Override public void add(char val){
        int size;
        UncheckedSubList currList;
        final var newNode=new CharDblLnkNode(val);
        this.lastRet=null;
        if((size=++(currList=this.parent).size)==1){
          ++currIndex;
          //initialize the list
          UncheckedSubList parent;
          do{
            currList.head=newNode;
            currList.tail=newNode;
            if((parent=currList.parent)==null){
              //all parents were empty, insert in the root
              ((CharDblLnkSeq)currList.root).insertNode(currList.parentOffset,newNode);
              return;
            }
          }while((size=++(currList=parent).size)==1);
        }
        final UncheckedList root;
        ++(root=currList.root).size;
        CharDblLnkNode after,before;
        int currIndex;
        if((currIndex=++this.currIndex)==size){
          //the insertion point IS the tail
          if((after=(before=currList.tail).next)==null){
            //there are no nodes after this list
            currList.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            currList.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          if(currIndex==1){
            //the insertion point IS the head
            if((before=(after=currList.head).prev)==null){
              //there are no nodes before this list
              currList.bubbleUpPrepend(newNode);
              root.tail=newNode;
            }else{
              //there are nodes before this list
              currList.bubbleUpPrepend(newNode,after);
              before.next=newNode;
            }
          }else{
            newNode.next=after=curr;
            newNode.prev=before=after.prev;
            after.prev=newNode;
            before.next=newNode;
            currList.bubbleUpIncrementSize();
          }
        }
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
        }
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          if(lastRet==parent.tail){
            parent.peelTail(lastRet);
          }else{
            if(lastRet==parent.head){
              parent.peelHead(lastRet);
            }else{
              CharDblLnkNode.eraseNode(lastRet);
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final CharDblLnkNode lastRet;
          CharDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final CharDblLnkNode lastRet;
          CharDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      final int tailDist,subListSize=toIndex-fromIndex;
      final CharDblLnkNode subListHead,subListTail;
      if((tailDist=this.size-toIndex)<=fromIndex){
        subListTail=CharDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize):CharDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=CharDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize):CharDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private boolean uncheckedremoveVal(CharDblLnkNode head
    ,int val
    ){
      if(val==(head.val)){
        --root.size;
        if(--this.size==0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail!=head;){
          if(val==((head=head.next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(head);
            }else{
              CharDblLnkNode before,after;
              (before=head.prev).next=(after=head.next);
              after.prev=before;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  private static class CheckedSubList extends CharDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private boolean uncheckedremoveVal(CharDblLnkNode head
    ,int val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(val==(head.val)){
          root.modCount=++modCount;
          --root.size;
          this.modCount=modCount;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }else{
          for(final var tail=this.tail;tail!=head;){
            if(val==((head=head.next).val)){
              root.modCount=++modCount;
              --root.size;
              this.modCount=modCount;
              this.size=size-1;
              if(head==tail){
                peelTail(head);
              }else{
                CharDblLnkNode before,after;
                (before=head.prev).next=(after=head.next);
                after.prev=before;
                bubbleUpDecrementSize();
              }
              return true;
            }
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfChar iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractCharItr
      implements OmniListIterator.OfChar{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient CharDblLnkNode curr;
      private transient CharDblLnkNode lastRet;
      private transient int currIndex;
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,CharDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public char nextChar(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          CharDblLnkNode curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public char previousChar(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          CharDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(char val){
        final CharDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(CharConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int size,numLeft;
        final CheckedSubList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
          }
          if(--(parent=this.parent).size==0){
            parent.removeLastNode(parent.tail);
          }else{
            if(lastRet==parent.tail){
              parent.peelTail(lastRet);
            }else{
              if(lastRet==parent.head){
                parent.peelHead(lastRet);
              }else{
                CharDblLnkNode.eraseNode(lastRet);
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(char val){
        CheckedSubList currList;
        CheckedList root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=this.parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        int size;
        final var newNode=new CharDblLnkNode(val);
        this.lastRet=null;
        if((size=++currList.size)==1){
          ++currIndex;
          //initialize the list
          CheckedSubList parent;
          do{
            currList.head=newNode;
            currList.tail=newNode;
            if((parent=currList.parent)==null){
              //all parents were empty, insert in the root
              ((CharDblLnkSeq)currList.root).insertNode(currList.parentOffset,newNode);
              return;
            }
          }while((size=++(currList=parent).size)==1);
        }
        ++root.size;
        CharDblLnkNode after,before;
        int currIndex;
        if((currIndex=++this.currIndex)==size){
          //the insertion point IS the tail
          if((after=(before=currList.tail).next)==null){
            //there are no nodes after this list
            currList.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            currList.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          if(currIndex==1){
            //the insertion point IS the head
            if((before=(after=currList.head).prev)==null){
              //there are no nodes before this list
              currList.bubbleUpPrepend(newNode);
              root.tail=newNode;
            }else{
              //there are nodes before this list
              currList.bubbleUpPrepend(newNode,after);
              before.next=newNode;
            }
          }else{
            newNode.next=after=curr;
            newNode.prev=before=after.prev;
            after.prev=newNode;
            before.next=newNode;
            currList.bubbleUpIncrementSize();
          }
        }
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size);
      final CharDblLnkNode subListHead,subListTail;
      if((tailDist-=toIndex)<=fromIndex){
        subListTail=CharDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize):CharDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=CharDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize):CharDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(char)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return CharDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedindexOf(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      if(val>=0)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(char)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedlastIndexOf(size,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      if(val>=0)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(char)val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return CharDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedcontains(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      if(val>=0)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      if(val>=0)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient CharDblLnkNode head;
      private transient CharDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(CharDblLnkNode head,int size,CharDblLnkNode tail,CheckedList.ModCountChecker modCountChecker){
        this.head=head;
        this.tail=tail;
        this.size=size;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList(head,size,tail);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          CharDblLnkNode curr;
          for(this.head=curr=new CharDblLnkNode((char)ois.readChar());--size!=0;curr=curr.next=new CharDblLnkNode(curr,(char)ois.readChar())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            do{
              oos.writeChar(curr.val);
            }while((curr=curr.next)!=null);
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    private static  void pullSurvivorsDown(CharDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static  void pullSurvivorsDown(CharDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return;
        }
        prev=curr;
      }
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead,CharDblLnkNode oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        ++curr.modCount;
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       CheckedSubList parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(CharDblLnkNode tail){
      CharDblLnkNode after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(CharDblLnkNode lastNode){
      CharDblLnkNode after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                ++curr.modCount;
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            ++curr.modCount;
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(CharDblLnkNode head){
      CharDblLnkNode after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public char removeCharAt(int index){
      final char ret;
      int size;
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      this.size=--size;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          CharDblLnkNode before;
          ret=(before=( tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          CharDblLnkNode after;
          ret=(after=( head=CharDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(CharPredicate filter){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(CharDblLnkNode oldhead,CharDblLnkNode tail,CharPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      CharDblLnkNode newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      CharDblLnkNode tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(CharDblLnkNode oldtail,CharDblLnkNode head,CharPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      CharDblLnkNode newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      CharDblLnkNode tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(CharDblLnkNode oldHead,CharDblLnkNode newHead,int numRemoved,CharDblLnkNode newTail,CharDblLnkNode oldTail){
      this.head=newHead;
      this.tail=newTail;
      final CharDblLnkNode after,before=oldHead.prev;
      if((after=oldHead.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          CheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      CharDblLnkNode tail;
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        if(filter.test((tail=this.tail).val)){
          if(size==1){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
                ,size,modCount
              );
            }else{
              collapsetail(tail,head,filter
                ,size,modCount
              );
            }
          }
          return true;
        }else{
          if(size!=1){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
                ,size,modCount
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
                ,size,modCount
              );
            }
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void clear(){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        root.size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,CharDblLnkNode head,CharDblLnkNode tail,CheckedList root){
      CharDblLnkNode before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      var curr=this;
      do{
        ++curr.modCount;
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(CharDblLnkNode before,CharDblLnkNode head,int numRemoved,CharDblLnkNode tail,CharDblLnkNode after){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(CharDblLnkNode tail, CharDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(CharDblLnkNode head, CharDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private static  int collapseBodyHelper(CharDblLnkNode newHead,int numLeft,CharPredicate filter,CheckedList.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft);
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private void collapseHeadAndTail(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        var newHead=head.next;
        do{
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList root=this.root;
            for(--size;numRemoved!=size;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
                break;
              }
            }
            root.modCount=++modCount;
            this.modCount=modCount;
            root.size-=numRemoved;
            this.size=size+1-numRemoved;
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            return;
          }
        }
        while(++numRemoved!=size);
      }
      CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        CharDblLnkNode prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test((head=head.next).val)){
              numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private void bubbleUpAppend(CharDblLnkNode newNode){
      for(var curr=this;;){
        curr.tail=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpAppend(CharDblLnkNode newNode,CharDblLnkNode oldTail){
      for(var curr=this;;){
        curr.tail=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.tail!=oldTail){
          ++curr.modCount;
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newNode){
      for(var curr=this;;){
        curr.head=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          break;
        }
        ++curr.size;
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newNode,CharDblLnkNode oldHead){
      for(var curr=this;;){
        curr.head=newNode;
        ++curr.modCount;
        if((curr=curr.parent)==null){
          return;
        }
        ++curr.size;
        if(curr.head!=oldHead){
          ++curr.modCount;
          curr.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      ++curr.size,curr=curr.parent){}
    }
    @Override public void add(int index,char val){
      int size;
      CheckedSubList curr;
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      root.modCount=++modCount;
      (curr=this).size=++size;
      final var newNode=new CharDblLnkNode(val);
      if(size==1){
        //initialize this list
        CheckedSubList parent;
        do{
          curr.head=newNode;
          curr.tail=newNode;
          curr.modCount=modCount;
          if((parent=curr.parent)==null){
            //all parents were empty, insert in the root
            ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
            return;
          }
        }
        while((size=++(curr=parent).size)==1);
      }
      ++root.size;
      CharDblLnkNode before,after;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        if(size==1){
          //the insertion point IS the tail
          if((after=(before=curr.tail).next)==null){
            //there are no nodes after this list
            curr.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            //there are nodes after this list
            curr.bubbleUpAppend(newNode,before);
            after.prev=newNode;
          }
        }else{
          //iterate from the tail and insert
          before=(after=CharDblLnkNode.iterateDescending(curr.tail,size-1)).prev;
          after.prev=newNode;
          curr.modCount=modCount;
          curr.bubbleUpIncrementSize();
        }
        before.next=newNode;
      }else{
        //the insertion point is closer to the head
        if(index==0){
          //the insertion point IS the tail
          if((before=(after=curr.head).prev)==null){
            //there are no nodes before this list
            curr.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            //there are nodes before this list
            curr.bubbleUpPrepend(newNode,after);
            before.next=newNode;
          }
        }else{
          //iterate from the head and insert
          after=(before=CharDblLnkNode.iterateAscending(curr.head,index-1)).next;
          before.next=newNode;
          curr.modCount=modCount;
          curr.bubbleUpIncrementSize();
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    @Override void addLast(char val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      var newNode=new CharDblLnkNode(val);
      CheckedSubList parent,curr=this;
      for(;++curr.size==1;curr=parent){
        curr.head=newNode;
        curr.tail=newNode;
        curr.modCount=modCount;
        if((parent=curr.parent)==null){
          //all parents were empty, insert in the root
          ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
          return;
        }
      }
      CharDblLnkNode oldTail,after;
      if((after=(oldTail=curr.tail).next)==null){
        curr.bubbleUpAppend(newNode);
        root.tail=newNode;
      }else{
        curr.bubbleUpAppend(newNode,oldTail);
        after.prev=newNode;
      }
      ++root.size;
      newNode.next=after;
      oldTail.next=newNode;
      newNode.prev=oldTail;
    }
    @Override public char set(int index,char val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final CharDblLnkNode node;
      final var ret=(node=((CharDblLnkSeq)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,char val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((CharDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public char getChar(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((CharDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final CharDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        CharDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(CharConsumer action){
      final int modCount=this.modCount;
      try{
        final CharDblLnkNode head;
        if((head=this.head)!=null){
          CharDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(CharComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            CharSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally{
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final CharDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        CharDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Character> action){
      final int modCount=this.modCount;
      try{
        final CharDblLnkNode head;
        if((head=this.head)!=null){
          CharDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Character> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(CharComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally{
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public <T> T[] toArray(T[] dst){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray(dst);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->
      {
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      });
    }
    @Override public char[] toCharArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toCharArray();
    }
    @Override public Character[] toArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray();
    }
    @Override public double[] toDoubleArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toDoubleArray();
    }
    @Override public float[] toFloatArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toFloatArray();
    }
    @Override public long[] toLongArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toLongArray();
    }
    @Override public int[] toIntArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toIntArray();
    }
    @Override public String toString(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toString();
    }
    @Override public int hashCode(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.hashCode();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    } 
  }
  public static class CheckedList extends UncheckedList{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedList.this.modCount;
      }
    }
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.size=0;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public char removeLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=tail.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public char popChar(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=head.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public char removeCharAt(int index){
      final char ret;
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++this.modCount;
      this.size=--size;
      if((size-=index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,char val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=++size;
      if((size-=index)<index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new CharDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          CharDblLnkNode before;
          (before=(tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).next=before=new CharDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        CharDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new CharDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new CharDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          CharDblLnkNode after;
          (after=(head=CharDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new CharDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(char val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(char val){
      ++this.modCount;
      super.push(val);
    }
    @Override public char set(int index,char val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      CharDblLnkNode tmp;
      final var ret=(tmp=((CharDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,char val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((CharDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public char getChar(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((CharDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public char getLastChar(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public char charElement(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void forEach(CharConsumer action){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(CharComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
            CharSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Character> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(CharComparator sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        {
          if(sorter==null){
            ++this.modCount;
            CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          }else{
            int modCount=this.modCount;
            try
            {
              CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally{
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(CharDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(CharDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(CharDblLnkNode prev,CharPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          final long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(prev.next,numLeft,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
              head.prev=null;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.tail=null;
          this.size=0;
          return true;
        }else{
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfChar iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size);
      final CharDblLnkNode subListHead,subListTail;
      if((tailDist-=toIndex)<=fromIndex){
        subListTail=CharDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize):CharDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=CharDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize):CharDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    } 
    boolean uncheckedremoveLastOccurrence(CharDblLnkNode tail
    ,int val
    ){
      {
        if(val==(tail.val)){
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(CharDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            this.modCount=modCount+1;
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(CharDblLnkNode head
    ,int val
    ){
      {
        if(val==(head.val)){
          this.modCount=modCount+1;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(CharDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            this.modCount=modCount+1;
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public char pollChar(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character poll(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Character pollLast(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(double)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(double)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(float)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(float)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(long)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(long)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(int)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(int)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    private static class DescendingItr
      extends AbstractCharItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient CharDblLnkNode curr;
      transient CharDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,CharDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public char nextChar(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              CharDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,CharConsumer action){
        final int modCount=this.modCount;
        final CheckedList parent;
        try{
          CharDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfChar{
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public char nextChar(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public char previousChar(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          CharDblLnkNode curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasPrevious(){
        return this.currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(char val){
        final CharDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(char val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        CharDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new CharDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new CharDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new CharDblLnkNode(val,newNode);
          }else{
            final CharDblLnkNode tmp;
            (newNode=curr).prev=newNode=new CharDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
          }
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              CharDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
  }
  public static class UncheckedList extends CharDblLnkSeq implements OmniDeque.OfChar,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public char removeLastChar(){
      CharDblLnkNode tail;
      final var ret=(tail=this.tail).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
    }
    @Override public char popChar(){
      CharDblLnkNode head;
      final var ret=(head=this.head).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
    }
    @Override public char removeCharAt(int index){
      final char ret;
      int size;
      if((size=--this.size-index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,char val){
      int size;
      if((size=++this.size-index)<index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new CharDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          CharDblLnkNode before;
          (before=(tail=CharDblLnkNode.iterateDescending(tail,size-1)).prev).next=before=new CharDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        CharDblLnkNode head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new CharDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new CharDblLnkNode(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          CharDblLnkNode after;
          (after=(head=CharDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new CharDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(char val){
      CharDblLnkNode tail;
      if((tail=this.tail)==null){
        this.head=tail=new CharDblLnkNode(val);
      }else{
        tail.next=tail=new CharDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(char val){
      CharDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new CharDblLnkNode(val);
      }else{
        head.prev=head=new CharDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeChar(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        CharDblLnkNode curr;
        for(this.head=curr=new CharDblLnkNode((char)in.readChar());--size!=0;curr=curr.next=new CharDblLnkNode(curr,(char)in.readChar())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(CharDblLnkNode head
    ,int val
    ){
      {
        if(val==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(CharDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfChar iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      final int tailDist,subListSize=toIndex-fromIndex;
      final CharDblLnkNode subListHead,subListTail;
      if((tailDist=this.size-toIndex)<=fromIndex){
        subListTail=CharDblLnkNode.iterateDescending(this.tail,tailDist);
        subListHead=subListSize<=fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize):CharDblLnkNode.iterateAscending(this.head,fromIndex);
      }else{
        subListHead=CharDblLnkNode.iterateAscending(this.head,fromIndex);
        subListTail=subListSize<=tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize):CharDblLnkNode.iterateDescending(this.tail,tailDist);
      }
      return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
    }
    @Override public char getLastChar(){
      return tail.val;
    }
    @Override public boolean offerFirst(char val){
      push((char)val);
      return true;
    }
    @Override public boolean offerLast(char val){
      addLast((char)val);
      return true;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public char charElement(){
      return head.val;
    }
    @Override public boolean offer(char val){
      addLast((char)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return CharDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode head;
          if((head=this.head)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if((v=(char)val)==val){
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(CharDblLnkNode tail
    ,int val
    ){
      {
        if(val==(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(CharDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public Character peekFirst(){
      return peek();
    }
    @Override public Character pollFirst(){
      return poll();
    }
    @Override public Character pop(){
      return popChar();
    }
    @Override public Character remove(){
      return popChar();
    }
    @Override public boolean offer(Character val){
      addLast((char)val);
      return true;
    }
    @Override public Character element(){
      return charElement();
    }
    @Override public Character removeFirst(){
      return popChar();
    }
    @Override public Character removeLast(){
      return removeLastChar();
    }
    @Override public boolean offerFirst(Character val){
      push((char)val);
      return true;
    }
    @Override public boolean offerLast(Character val){
      addLast((char)val);
      return true;
    }
    @Override public void push(Character val){
      push((char)val);
    }
    @Override public void addFirst(Character val){
      push((char)val);
    }
    @Override public void addLast(Character val){
      addLast((char)val);
    }
    @Override public Character getFirst(){
      return charElement();
    }
    @Override public Character getLast(){
      return getLastChar();
    }
    @Override public char pollChar(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character poll(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Character pollLast(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(double)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(float)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(long)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      CharDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(int)(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public char peekChar(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char peekLastChar(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public Character peek(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Character peekLast(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final CharDblLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final CharDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (int)(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public boolean removeIf(CharPredicate filter){
      final CharDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(CharDblLnkNode prev,CharDblLnkNode tail,CharPredicate filter){
      int numSurvivors=1;
      outer:for(CharDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while(filter.test((next=next.next).val));
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(CharDblLnkNode prev,CharDblLnkNode curr,CharDblLnkNode tail,CharPredicate filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
            head.prev=null;
            this.head=head;
            return true;  
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head!=tail;++numSurvivors){
          final CharDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(UncheckedList parent){
        super(parent,parent.tail);
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.next)==parent.tail){
              parent.tail=curr;
              curr.next=null;
            }else{
              curr.next=lastRet=lastRet.next;
              lastRet.prev=curr;
            }
          }
        }
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractCharItr
    {
      transient final UncheckedList parent;
      transient CharDblLnkNode curr;
      private AscendingItr(UncheckedList parent,CharDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
        }
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachAscending(curr,action);
        this.curr=null;
      }
      @Override public void forEachRemaining(CharConsumer action){
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharDblLnkNode curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar{
      private transient int currIndex;
      private transient CharDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public char previousChar(){
        CharDblLnkNode curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return curr.prev!=null;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(char val){
        final UncheckedList parent;
        CharDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new CharDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new CharDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new CharDblLnkNode(val,newNode);
          }else{
            final CharDblLnkNode tmp;
            (newNode=curr).prev=newNode=new CharDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(char val){
        lastRet.val=val;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
        }
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          if(lastRet==parent.tail){
            parent.tail=lastRet=lastRet.prev;
            lastRet.next=null;
          }else if(lastRet==parent.head){
            parent.head=lastRet=lastRet.next;
            lastRet.prev=null;
          }else{
            CharDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
