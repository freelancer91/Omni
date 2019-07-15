package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.CharComparator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.CharUnaryOperator;
import omni.impl.AbstractCharItr;
import omni.impl.CharDblLnkNode;
import omni.impl.CheckedCollection;
import omni.util.CharSortUtil;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public abstract class CharDblLnkSeq extends AbstractSeq<Character> implements CharSubListDefault{
  public static class CheckedList extends UncheckedList{
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfChar{
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
      }
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public void add(char val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        CharDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex) == ++parent.size){
          if(currIndex == 1){
            parent.head=newNode=new CharDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new CharDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex == 1){
            (newNode=parent.head).prev=newNode=new CharDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final CharDblLnkNode tmp;
            (newNode=curr).prev=newNode=new CharDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        lastRet=null;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size) - (currIndex=this.currIndex)) != 0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          curr=null;
          lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size) - (currIndex=this.currIndex)) != 0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          curr=null;
          lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public boolean hasPrevious(){
        return currIndex != 0;
      }
      @Override public char nextChar(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public char previousChar(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex) != 0){
          CharDblLnkNode curr;
          lastRet=curr=(curr=this.curr) == null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex - 1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public int previousIndex(){
        return currIndex - 1;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet) != null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          CharDblLnkNode curr;
          if((curr=lastRet.next) == this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--parent.size == 0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet == parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet == parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void set(char val){
        final CharDblLnkNode lastRet;
        if((lastRet=this.lastRet) != null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class DescendingItr extends AbstractCharItr{
      transient final CheckedList parent;
      transient int modCount;
      transient CharDblLnkNode curr;
      transient CharDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        modCount=parent.modCount;
        currIndex=parent.size;
        curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,CharDblLnkNode curr,int currIndex){
        this.parent=parent;
        modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      private DescendingItr(DescendingItr itr){
        parent=itr.parent;
        modCount=itr.modCount;
        curr=itr.curr;
        lastRet=itr.lastRet;
        currIndex=itr.currIndex;
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex) > 0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int currIndex;
        if((currIndex=this.currIndex) > 0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
      @Override public boolean hasNext(){
        return curr != null;
      }
      @Override public char nextChar(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet) != null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(--parent.size == 0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet == parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet == parent.head){
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
          CharDblLnkNode.uncheckedForEachDescending(curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount,currIndex,this.currIndex);
        }
        curr=null;
        lastRet=parent.head;
        this.currIndex=0;
      }
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return modCount;
      }
    }
    transient int modCount;
    public CheckedList(){}
    CheckedList(CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void add(int index,char val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      this.size=++size;
      if((size-=index) <= index){
        // the insertion point is closer to the tail
        var tail=this.tail;
        if(size == 1){
          // the insertion point IS the tail
          tail.next=tail=new CharDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          // iterate from the tail and insert
          CharDblLnkNode before;
          (before=(tail=CharDblLnkNode.iterateDescending(tail,size - 2)).prev).next
              =before=new CharDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        // the insertion point is closer to the head
        CharDblLnkNode head;
        if((head=this.head) == null){
          // initialize the list
          this.head=head=new CharDblLnkNode(val);
          tail=head;
        }else if(index == 0){
          // the insertion point IS the head
          head.prev=head=new CharDblLnkNode(val,head);
          this.head=head;
        }else{
          // iterate from the head and insert
          CharDblLnkNode after;
          (after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).prev
              =after=new CharDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(char val){
      ++modCount;
      super.addLast(val);
    }
    @Override public char charElement(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return head.val; }
      throw new NoSuchElementException();
    }
    @Override public void clear(){
      if(size != 0){
        ++modCount;
        size=0;
        head=null;
        tail=null;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size) != 0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i != size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public boolean equals(Object val){
      // TODO
      return false;
    }
    @Override public void forEach(CharConsumer action){
      final CharDblLnkNode head;
      if((head=this.head) != null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedForEachAscending(head,size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      final CharDblLnkNode head;
      if((head=this.head) != null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public char getChar(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((CharDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public char getLastChar(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return tail.val; }
      throw new NoSuchElementException();
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
    @Override public Character poll(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public char pollChar(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollDouble(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=(double)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=(float)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public int pollInt(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=(int)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Character pollLast(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public char pollLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollLastDouble(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=(double)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollLastFloat(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=(float)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public int pollLastInt(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=(int)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=(long)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=(long)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public char popChar(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        ++modCount;
        final var ret=head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void push(char val){
      ++modCount;
      super.push(val);
    }
    @Override public void put(int index,char val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((CharDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public char removeCharAt(int index){
      final char ret;
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      this.size=--size;
      if((size-=index) <= index){
        // the node to remove is closer to the tail
        var tail=this.tail;
        if(size == 0){
          // the node to the remove IS the tail
          ret=tail.val;
          if(index == 0){
            // the node is the last node
            head=null;
            this.tail=null;
          }else{
            // peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          // iterate from the tail
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size - 1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        // the node to remove is close to the head
        var head=this.head;
        if(index == 0){
          // peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          // iterate from the head
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public char removeLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        ++modCount;
        final var ret=tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      final CharDblLnkNode head;
      if((head=this.head) != null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedReplaceAll(head,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount + 1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final CharDblLnkNode head;
      if((head=this.head) != null){
        final int modCount=this.modCount;
        try{
          CharDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount + 1;
        }
      }
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
    @Override public void sort(CharComparator sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        if(sorter == null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount + 1;
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Character> sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        if(sorter == null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount + 1;
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort(){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        modCount=modCount + 1;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort(){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        CharSortUtil.uncheckedDescendingSort(tmp,0,size);
        modCount=modCount + 1;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=size)) != 0){
        final CharDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex) <= fromIndex){
          subListTail=CharDblLnkNode.iterateDescending(tail,tailDist);
          subListHead=subListSize <= fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize - 1)
              :CharDblLnkNode.iterateAscending(head,fromIndex);
        }else{
          subListHead=CharDblLnkNode.iterateAscending(head,fromIndex);
          subListTail=subListSize <= tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize - 1)
              :CharDblLnkNode.iterateDescending(tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
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
    @Override public void unstableSort(CharComparator sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        if(sorter == null){
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount + 1;
        }
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        int size;
        out.writeInt(size=this.size);
        if(size != 0){
          var curr=head;
          for(;;curr=curr.next){
            out.writeChar(curr.val);
            if(--size == 0){
              break;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private void pullSurvivorsDown(CharDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker & word) == 0){
          do{
            if(--numRemoved == 0){
              if(curr == tail){
                prev.next=null;
                tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1) & word) == 0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors == 0){
          tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(CharDblLnkNode prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker & word) == 0){
          do{
            if(--numRemoved == 0){
              if(curr == tail){
                prev.next=null;
                tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }else if((marker<<=1) == 0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker & word) == 0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors == 0){
          tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1) == 0){
          word=survivorSet[++wordOffset];
          marker=1L;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(CharDblLnkNode prev,CharPredicate filter,int numLeft,int modCount){
      if(numLeft != 0){
        int numSurvivors;
        if(numLeft > 64){
          final long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,numLeft,filter,survivorSet=new long[(numLeft - 1 >> 6) + 1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors) != 0){
            pullSurvivorsDown(prev,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(prev.next,numLeft,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors=Long.bitCount(survivorWord)) != 0){
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
        int numLeft=size;
        if(filter.test(head.val)){
          while(--numLeft != 0){
            if(!filter.test((head=head.next).val)){
              size=1 + removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount + 1;
              this.head=head;
              head.prev=null;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount + 1;
          this.head=null;
          tail=null;
          size=0;
          return true;
        }else{
          int numSurvivors;
          if(--numLeft != (numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount + 1;
            size=1 + numSurvivors;
            return true;
          }
        }
      }catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override boolean uncheckedremoveLastOccurrence(CharDblLnkNode tail,int val){
      {
        if(val == tail.val){
          modCount=modCount + 1;
          if((tail=tail.prev) == null){
            head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --size;
          return true;
        }
        for(CharDblLnkNode next;(tail=(next=tail).prev) != null;){
          if(val == tail.val){
            modCount=modCount + 1;
            if((tail=tail.prev) == null){
              head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override boolean uncheckedremoveVal(CharDblLnkNode head,int val){
      {
        if(val == head.val){
          ++modCount;
          if(--size == 0){
            this.head=null;
            tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(CharDblLnkNode prev;(head=(prev=head).next) != null;){
          if(val == head.val){
            ++modCount;
            if((head=head.next) == null){
              tail=prev;
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
  }
  public static class UncheckedList extends CharDblLnkSeq implements OmniDeque.OfChar,Externalizable{
    private static class AscendingItr extends AbstractCharItr{
      transient final UncheckedList parent;
      transient CharDblLnkNode curr;
      private AscendingItr(AscendingItr itr){
        parent=itr.parent;
        curr=itr.curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        curr=parent.head;
      }
      private AscendingItr(UncheckedList parent,CharDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
      @Override public boolean hasNext(){
        return curr != null;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size == 0){
          parent.head=null;
          parent.tail=null;
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr) == null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.prev) == parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
        }
      }
      void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachAscending(curr,action);
        this.curr=null;
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar{
      private transient int currIndex;
      private transient CharDblLnkNode lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        currIndex=itr.currIndex;
        lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public void add(char val){
        final UncheckedList parent;
        CharDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex) == ++(parent=this.parent).size){
          if(currIndex == 1){
            parent.head=newNode=new CharDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new CharDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex == 1){
            (newNode=parent.head).prev=newNode=new CharDblLnkNode(val,newNode);
            parent.head=newNode;
          }else{
            final CharDblLnkNode tmp;
            (newNode=curr).prev=newNode=new CharDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        lastRet=null;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public boolean hasPrevious(){
        return currIndex > 0;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        lastRet=curr=this.curr;
        this.curr=curr.next;
        ++currIndex;
        return curr.val;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public char previousChar(){
        CharDblLnkNode curr;
        lastRet=curr=(curr=this.curr) == null?parent.tail:curr.prev;
        this.curr=curr;
        --currIndex;
        return curr.val;
      }
      @Override public int previousIndex(){
        return currIndex - 1;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet,curr;
        if((curr=(lastRet=this.lastRet).next) == this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        final UncheckedList parent;
        if(--(parent=this.parent).size == 0){
          parent.head=null;
          parent.tail=null;
        }else{
          if(lastRet == parent.tail){
            parent.tail=lastRet=lastRet.prev;
            lastRet.next=null;
          }else if(lastRet == parent.head){
            parent.head=curr;
            curr.prev=null;
          }else{
            curr.prev=lastRet=lastRet.prev;
            lastRet.next=curr;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(char val){
        lastRet.val=val;
      }
      @Override void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        lastRet=(parent=this.parent).tail;
        currIndex=parent.size;
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(DescendingItr itr){
        super(itr);
      }
      private DescendingItr(UncheckedList parent){
        super(parent,parent.tail);
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size == 0){
          parent.head=null;
          parent.tail=null;
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr) == null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.next) == parent.tail){
              parent.tail=curr;
              curr.next=null;
            }else{
              curr.next=lastRet=lastRet.next;
              lastRet.prev=curr;
            }
          }
        }
      }
      @Override void uncheckedForEachRemaining(CharDblLnkNode curr,CharConsumer action){
        CharDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static final long serialVersionUID=1L;
    public UncheckedList(){}
    UncheckedList(CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void add(int index,char val){
      int size;
      if((size=++this.size - index) <= index){
        // the insertion point is closer to the tail
        var tail=this.tail;
        if(size == 1){
          // the insertion point IS the tail
          tail.next=tail=new CharDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          // iterate from the tail and insert
          CharDblLnkNode before;
          (before=(tail=CharDblLnkNode.iterateDescending(tail,size - 2)).prev).next
              =before=new CharDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        // the insertion point is closer to the head
        CharDblLnkNode head;
        if((head=this.head) == null){
          // initialize the list
          this.head=head=new CharDblLnkNode(val);
          tail=head;
        }else if(index == 0){
          // the insertion point IS the head
          head.prev=head=new CharDblLnkNode(val,head);
          this.head=head;
        }else{
          // iterate from the head and insert
          CharDblLnkNode after;
          (after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).prev
              =after=new CharDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addFirst(Character val){
      push((char)val);
    }
    @Override public void addLast(char val){
      CharDblLnkNode tail;
      if((tail=this.tail) == null){
        head=tail=new CharDblLnkNode(val);
      }else{
        tail.next=tail=new CharDblLnkNode(tail,val);
      }
      this.tail=tail;
      ++size;
    }
    @Override public void addLast(Character val){
      addLast((char)val);
    }
    @Override public char charElement(){
      return head.val;
    }
    @Override public void clear(){
      head=null;
      size=0;
      tail=null;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size) != 0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i != size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public OmniIterator.OfChar descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public Character element(){
      return charElement();
    }
    @Override public boolean equals(Object val){
      // TODO
      return false;
    }
    @Override public Character getFirst(){
      return charElement();
    }
    @Override public Character getLast(){
      return getLastChar();
    }
    @Override public char getLastChar(){
      return tail.val;
    }
    @Override public OmniIterator.OfChar iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public boolean offer(char val){
      addLast((char)val);
      return true;
    }
    @Override public boolean offer(Character val){
      addLast((char)val);
      return true;
    }
    @Override public boolean offerFirst(char val){
      push((char)val);
      return true;
    }
    @Override public boolean offerFirst(Character val){
      push((char)val);
      return true;
    }
    @Override public boolean offerLast(char val){
      addLast((char)val);
      return true;
    }
    @Override public boolean offerLast(Character val){
      addLast((char)val);
      return true;
    }
    @Override public Character peek(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return head.val; }
      return null;
    }
    @Override public char peekChar(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return head.val; }
      return Character.MIN_VALUE;
    }
    @Override public double peekDouble(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return (double)head.val; }
      return Double.NaN;
    }
    @Override public Character peekFirst(){
      return peek();
    }
    @Override public float peekFloat(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return (float)head.val; }
      return Float.NaN;
    }
    @Override public int peekInt(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return (int)head.val; }
      return Integer.MIN_VALUE;
    }
    @Override public Character peekLast(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return tail.val; }
      return null;
    }
    @Override public char peekLastChar(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return tail.val; }
      return Character.MIN_VALUE;
    }
    @Override public double peekLastDouble(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return (double)tail.val; }
      return Double.NaN;
    }
    @Override public float peekLastFloat(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return (float)tail.val; }
      return Float.NaN;
    }
    @Override public int peekLastInt(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return (int)tail.val; }
      return Integer.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final CharDblLnkNode tail;
      if((tail=this.tail) != null){ return (long)tail.val; }
      return Long.MIN_VALUE;
    }
    @Override public long peekLong(){
      final CharDblLnkNode head;
      if((head=this.head) != null){ return (long)head.val; }
      return Long.MIN_VALUE;
    }
    @Override public Character poll(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public char pollChar(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollDouble(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=(double)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public Character pollFirst(){
      return poll();
    }
    @Override public float pollFloat(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=(float)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public int pollInt(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=(int)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Character pollLast(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public char pollLastChar(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public double pollLastDouble(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=(double)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollLastFloat(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=(float)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public int pollLastInt(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=(int)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      CharDblLnkNode tail;
      if((tail=this.tail) != null){
        final var ret=(long)tail.val;
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      CharDblLnkNode head;
      if((head=this.head) != null){
        final var ret=(long)head.val;
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Character pop(){
      return popChar();
    }
    @Override public char popChar(){
      CharDblLnkNode head;
      final var ret=(head=this.head).val;
      {
        if(--size == 0){
          this.head=null;
          tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
    }
    @Override public void push(char val){
      CharDblLnkNode head;
      if((head=this.head) == null){
        tail=head=new CharDblLnkNode(val);
      }else{
        head.prev=head=new CharDblLnkNode(val,head);
      }
      this.head=head;
      ++size;
    }
    @Override public void push(Character val){
      push((char)val);
    }
    @Override public void readExternal(ObjectInput in) throws IOException{
      int size;
      this.size=size=in.readInt();
      if(size != 0){
        CharDblLnkNode curr;
        for(head=curr=new CharDblLnkNode((char)in.readChar());--size != 0;
            curr=curr.next=new CharDblLnkNode(curr,(char)in.readChar())){}
        tail=curr;
      }
    }
    @Override public Character remove(){
      return popChar();
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public char removeCharAt(int index){
      final char ret;
      int size;
      if((size=--this.size - index) <= index){
        // the node to remove is closer to the tail
        var tail=this.tail;
        if(size == 0){
          // the node to the remove IS the tail
          ret=tail.val;
          if(index == 0){
            // the node is the last node
            head=null;
            this.tail=null;
          }else{
            // peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          // iterate from the tail
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size - 1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        // the node to remove is close to the head
        var head=this.head;
        if(index == 0){
          // peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          // iterate from the head
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public Character removeFirst(){
      return popChar();
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public boolean removeIf(CharPredicate filter){
      final CharDblLnkNode head;
      return (head=this.head) != null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      return (head=this.head) != null && uncheckedRemoveIf(head,filter::test);
    }
    @Override public Character removeLast(){
      return removeLastChar();
    }
    @Override public char removeLastChar(){
      CharDblLnkNode tail;
      final var ret=(tail=this.tail).val;
      {
        if(--size == 0){
          head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return uncheckedremoveLastOccurrence(tail,TypeUtil.castToChar(val)); } // end
                                                                                                               // size
                                                                                                               // check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return uncheckedremoveLastOccurrence(tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveLastOccurrence(tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveLastOccurrence(tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      if(val == (char)val){
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return uncheckedremoveLastOccurrence(tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if((v=(char)val) == val){ return uncheckedremoveLastOccurrence(tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      if(val >= 0){
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return uncheckedremoveLastOccurrence(tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,TypeUtil.castToChar(val)); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val == (char)val){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public int search(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedsearch(head,TypeUtil.castToChar(val)); } // end
                                                                                                                // size
                                                                                                                // check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedsearch(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedsearch(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedsearch(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(int val){
      if(val == (char)val){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedsearch(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return CharDblLnkNode.uncheckedsearch(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedsearch(head,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int search(short val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedsearch(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex - fromIndex) != 0){
        final int tailDist;
        final CharDblLnkNode subListHead,subListTail;
        if((tailDist=size - toIndex) <= fromIndex){
          subListTail=CharDblLnkNode.iterateDescending(tail,tailDist);
          subListHead=subListSize <= fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize - 1)
              :CharDblLnkNode.iterateAscending(head,fromIndex);
        }else{
          subListHead=CharDblLnkNode.iterateAscending(head,fromIndex);
          subListTail=subListSize <= tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize - 1)
              :CharDblLnkNode.iterateDescending(tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size != 0){
        var curr=head;
        do{
          out.writeChar(curr.val);
        }while((curr=curr.next) != null);
      }
    }
    private int removeIfHelper(CharDblLnkNode prev,CharDblLnkNode curr,CharDblLnkNode tail,CharPredicate filter){
      int numSurvivors=0;
      while(curr != tail){
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr == tail){ return numSurvivors; }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    private int removeIfHelper(CharDblLnkNode prev,CharDblLnkNode tail,CharPredicate filter){
      int numSurvivors=1;
      outer:for(CharDblLnkNode next;prev != tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next == tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }while(filter.test((next=next.next).val));
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      if(filter.test(head.val)){
        for(final var tail=this.tail;head != tail;){
          if(!filter.test((head=head.next).val)){
            size=removeIfHelper(head,tail,filter);
            head.prev=null;
            this.head=head;
            return true;
          }
        }
        this.head=null;
        tail=null;
        size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head != tail;++numSurvivors){
          final CharDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            size=numSurvivors + removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    boolean uncheckedremoveLastOccurrence(CharDblLnkNode tail,int val){
      {
        if(val == tail.val){
          if((tail=tail.prev) == null){
            head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --size;
          return true;
        }
        for(CharDblLnkNode next;(tail=(next=tail).prev) != null;){
          if(val == tail.val){
            if((tail=tail.prev) == null){
              head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(CharDblLnkNode head,int val){
      {
        if(val == head.val){
          if(--size == 0){
            this.head=null;
            tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(CharDblLnkNode prev;(head=(prev=head).next) != null;){
          if(val == head.val){
            if((head=head.next) == null){
              tail=prev;
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
  }
  private static class CheckedSubList extends CharDblLnkSeq{
    private static class BidirectionalItr extends AbstractCharItr implements OmniListIterator.OfChar{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient CharDblLnkNode curr;
      private transient CharDblLnkNode lastRet;
      private transient int currIndex;
      private BidirectionalItr(BidirectionalItr itr){
        parent=itr.parent;
        modCount=itr.modCount;
        curr=itr.curr;
        lastRet=itr.lastRet;
        currIndex=itr.currIndex;
      }
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        modCount=parent.modCount;
        curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,CharDblLnkNode curr,int currIndex){
        this.parent=parent;
        modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public void add(char val){
        final CheckedSubList currList;
        final CheckedList root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        lastRet=null;
        ++currIndex;
        if(++root.size != 1){
          if(++currList.size != 1){
            CharDblLnkNode after,before;
            if((after=curr) != null){
              if((before=after.prev) != null){
                before.next=before=new CharDblLnkNode(before,val,after);
                if(after == currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new CharDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              CharDblLnkNode newNode;
              if((after=(before=currList.tail).next) != null){
                currList.bubbleUpAppend(before,newNode=new CharDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new CharDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new CharDblLnkNode(val));
          }
        }else{
          CharDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new CharDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size) - (currIndex=this.currIndex)) > 0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          lastRet=parent.tail;
          curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size) - (currIndex=this.currIndex)) > 0){
          final int modCount=this.modCount;
          try{
            CharDblLnkNode.uncheckedForEachAscending(curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          lastRet=parent.tail;
          curr=null;
          this.currIndex=size;
        }
      }
      @Override public boolean hasNext(){
        return currIndex < parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex != 0;
      }
      @Override public char nextChar(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex) < parent.size){
          CharDblLnkNode curr;
          lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex + 1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public char previousChar(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex) != 0){
          CharDblLnkNode curr;
          lastRet=curr=(curr=this.curr) == null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex - 1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public int previousIndex(){
        return currIndex - 1;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet;
        if((lastRet=this.lastRet) != null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          CharDblLnkNode curr;
          if((curr=lastRet.next) == this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--(parent=this.parent).size == 0){
            parent.removeLastNode(parent.tail);
          }else{
            if(lastRet == parent.tail){
              parent.peelTail(lastRet);
            }else{
              if(lastRet == parent.head){
                parent.peelHead(lastRet);
              }else{
                curr.prev=lastRet=lastRet.prev;
                lastRet.next=curr;
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void set(char val){
        final CharDblLnkNode lastRet;
        if((lastRet=this.lastRet) != null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient CharDblLnkNode head;
      private transient CharDblLnkNode tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(CharDblLnkNode head,int size,CharDblLnkNode tail,
          CheckedList.ModCountChecker modCountChecker){
        this.head=head;
        this.tail=tail;
        this.size=size;
        this.modCountChecker=modCountChecker;
      }
      private void readObject(ObjectInputStream ois) throws IOException{
        int size;
        this.size=size=ois.readInt();
        if(size != 0){
          CharDblLnkNode curr;
          for(head=curr=new CharDblLnkNode((char)ois.readChar());--size != 0;
              curr=curr.next=new CharDblLnkNode(curr,(char)ois.readChar())){}
          tail=curr;
        }
      }
      private Object readResolve(){
        return new CheckedList(head,size,tail);
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size != 0){
            var curr=head;
            for(;;curr=curr.next){
              oos.writeChar(curr.val);
              if(--size == 0){
                break;
              }
            }
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private static final long serialVersionUID=1L;
    private static int collapseBodyHelper(CharDblLnkNode newHead,CharDblLnkNode newTail,int numLeft,
        CharPredicate filter,CheckedList.ModCountChecker modCountChecker){
      if(numLeft != 0){
        int numSurvivors;
        if(numLeft > 64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft - 1 >> 6) + 1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors) != 0){
            if((newHead=pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft)) != null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors=Long.bitCount(survivorWord)) != 0){
            if((newHead=pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft)) != null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private static CharDblLnkNode pullSurvivorsDown(CharDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker & word) == 0){
          do{
            if(--numRemoved == 0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }
            curr=curr.next;
          }while(((marker<<=1) & word) == 0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors == 0){ return curr; }
        prev=curr;
      }
    }
    private static CharDblLnkNode pullSurvivorsDown(CharDblLnkNode prev,long[] survivorSet,int numSurvivors,
        int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker & word) == 0){
          do{
            if(--numRemoved == 0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }else if((marker<<=1) == 0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker & word) == 0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors == 0){ return curr; }
        if((marker<<=1) == 0){
          word=survivorSet[++wordOffset];
          marker=1L;
        }
        prev=curr;
      }
    }
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset){
      super();
      this.root=root;
      parent=null;
      parentOffset=rootOffset;
      modCount=root.modCount;
    }
    private CheckedSubList(CheckedList root,int rootOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      parent=null;
      parentOffset=rootOffset;
      modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset){
      super();
      root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      modCount=parent.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      modCount=parent.modCount;
    }
    @Override public void add(int index,char val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new CharDblLnkNode(val);
      if(++root.size != 1){
        size=++currSize;
        if(currSize != 1){
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public void clear(){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size) != 0){
        root.modCount=++modCount;
        this.modCount=modCount;
        root.size-=size;
        clearAllHelper(size,head,tail,root);
      }
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size) != 0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i != size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToChar(val)); } // end
                                                                                                                       // size
                                                                                                                       // check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(byte val){
      if(val >= 0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val == (char)val){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedcontains(head,tail,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean contains(short val){
      if(val >= 0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean equals(Object val){
      // TODO
      return false;
    }
    @Override public void forEach(CharConsumer action){
      final int modCount=this.modCount;
      try{
        final CharDblLnkNode head;
        if((head=this.head) != null){
          CharDblLnkNode.uncheckedForEachAscending(head,size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      final int modCount=this.modCount;
      try{
        final CharDblLnkNode head;
        if((head=this.head) != null){
          CharDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public char getChar(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((CharDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int hashCode(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.hashCode();
    }
    @Override public int indexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToChar(val)); } // end
                                                                                                                      // size
                                                                                                                      // check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val == (char)val){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedindexOf(head,tail,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int indexOf(short val){
      if(val >= 0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return size == 0;
    }
    @Override public OmniIterator.OfChar iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToChar(val));
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val == (char)val){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            final char v;
            if((v=(char)val) == val){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return CharDblLnkNode.uncheckedlastIndexOf(size,tail,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      if(val >= 0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final CharDblLnkNode tail;
          if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return -1;
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
    @Override public void put(int index,char val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((CharDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
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
      if((size-=index) <= index){
        var tail=this.tail;
        if(size == 0){
          ret=tail.val;
          if(index == 0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size - 1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index == 0){
          ret=head.val;
          peelHead(head);
        }else{
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).val;
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
      if((head=this.head) != null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      if((head=this.head) != null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,TypeUtil.castToChar(val)); } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val == (char)val){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      final CharDblLnkNode head;
      if((head=this.head) == null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        CharDblLnkNode.uncheckedReplaceAll(head,size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent) != null);
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final CharDblLnkNode head;
      if((head=this.head) == null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        CharDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent) != null);
      }
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
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return size;
    }
    @Override public void sort(CharComparator sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        int modCount=this.modCount;
        final CheckedList root;
        final char[] tmp;
        final CharDblLnkNode tail;
        if(sorter == null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          try{
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr != null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Character> sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        int modCount=this.modCount;
        final CheckedList root;
        final char[] tmp;
        final CharDblLnkNode tail;
        if(sorter == null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          try{
            CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr != null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort(){
      // todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr != null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort(){
      // todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size) > 1){
        final char[] tmp;
        final CharDblLnkNode tail;
        CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr != null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        CharSortUtil.uncheckedDescendingSort(tmp,0,size);
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=size)) != 0){
        final CharDblLnkNode subListHead,subListTail;
        if((tailDist-=toIndex) <= fromIndex){
          subListTail=CharDblLnkNode.iterateDescending(tail,tailDist);
          subListHead=subListSize <= fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize - 1)
              :CharDblLnkNode.iterateAscending(head,fromIndex);
        }else{
          subListHead=CharDblLnkNode.iterateAscending(head,fromIndex);
          subListTail=subListSize <= tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize - 1)
              :CharDblLnkNode.iterateDescending(tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    }
    @Override public Character[] toArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      });
    }
    @Override public <T> T[] toArray(T[] dst){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray(dst);
    }
    @Override public char[] toCharArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toCharArray();
    }
    @Override public double[] toDoubleArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toDoubleArray();
    }
    @Override public float[] toFloatArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toFloatArray();
    }
    @Override public int[] toIntArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toIntArray();
    }
    @Override public long[] toLongArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toLongArray();
    }
    @Override public String toString(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toString();
    }
    @Override public void unstableSort(CharComparator sorter){
      // todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size) > 1){
        int modCount=this.modCount;
        final CheckedList root;
        final char[] tmp;
        final CharDblLnkNode tail;
        if(sorter == null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          CharSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
          try{
            CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(final ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr != null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private void bubbleUpAppend(CharDblLnkNode newTail){
      tail=newTail;
      for(var currList=parent;currList != null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpAppend(CharDblLnkNode oldTail,CharDblLnkNode newTail){
      oldTail.next=newTail;
      tail=newTail;
      for(var currList=parent;currList != null;currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.tail != oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr != null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpClearBody(CharDblLnkNode before,CharDblLnkNode head,int numRemoved,CharDblLnkNode tail,
        CharDblLnkNode after){
      for(var curr=parent;curr != null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head != head){
          while(curr.tail == tail){
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent) == null){ return; }
          }
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail != tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent) == null){ return; }
          }while(curr.head == head);
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(CharDblLnkNode tail,CharDblLnkNode after,int numRemoved){
      for(var curr=parent;curr != null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail != tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent) != null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(CharDblLnkNode head,CharDblLnkNode before,int numRemoved){
      for(var curr=parent;curr != null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head != head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent) != null);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(CharDblLnkNode oldHead,CharDblLnkNode newHead,int numRemoved,
        CharDblLnkNode newTail,CharDblLnkNode oldTail){
      head=newHead;
      tail=newTail;
      final CharDblLnkNode after,before=oldHead.prev;
      if((after=oldTail.next) == null){
        if(before == null){
          for(var parent=this.parent;parent != null;
              ++parent.modCount,parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          CheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent != null;
              ++parent.modCount,parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head != oldHead){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent) != null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before == null){
          for(var parent=this.parent;parent != null;
              ++parent.modCount,parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail != oldTail){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent) != null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent != null;
              ++parent.modCount,parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head != oldHead){
              do{
                if(parent.tail != oldTail){
                  ++parent.modCount;
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent) != null);
              break;
            }
            if(parent.tail != oldTail){
              for(;;){
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent) == null){
                  break;
                }
                if(parent.head != oldHead){
                  ++parent.modCount;
                  parent.size-=numRemoved;
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
    private void bubbleUpDecrementSize(){
      CheckedSubList parent;
      if((parent=this.parent) != null){
        parent.uncheckedBubbleUpDecrementSize();
      }
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr != null;curr.size-=numRemoved,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr != null;++curr.size,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpInit(CharDblLnkNode newNode){
      head=newNode;
      tail=newNode;
      CheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent != null;currParent=(curr=currParent).parent){
        ++currParent.modCount;
        int parentSize;
        if((parentSize=++currParent.size) != 1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    private void bubbleUpInitHelper(int index,int size,CharDblLnkNode newNode){
      CharDblLnkNode after,before;
      if((size-=index) <= index){
        before=tail;
        if(size == 1){
          if((after=before.next) == null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          bubbleUpIncrementSize();
          before=(after=CharDblLnkNode.iterateDescending(before,size - 2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;
      }else{
        after=head;
        if(index == 0){
          if((before=after.prev) == null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          bubbleUpIncrementSize();
          after=(before=CharDblLnkNode.iterateAscending(after,index - 1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead,CharDblLnkNode oldHead){
      newHead.prev=null;
      for(var curr=parent;curr != null;curr=curr.parent){
        if(curr.tail != oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      newTail.next=null;
      for(var curr=parent;curr != null;curr=curr.parent){
        if(curr.head != oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newHead){
      head=newHead;
      for(var currList=parent;currList != null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode oldHead,CharDblLnkNode newHead){
      head=newHead;
      for(var currList=parent;currList != null;currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.head != oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpRootInit(CharDblLnkNode newNode){
      head=newNode;
      tail=newNode;
      for(var parent=this.parent;parent != null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void clearAllHelper(int size,CharDblLnkNode head,CharDblLnkNode tail,CheckedList root){
      CharDblLnkNode before;
      final CharDblLnkNode after=tail.next;
      if((before=head.prev) == null){
        // this sublist is not preceded by nodes
        if(after == null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after == null){
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
    private boolean collapseBody(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter,int size,int modCount){
      for(int numLeft=size - 2;numLeft != 0;--numLeft){
        CharDblLnkNode prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          final var root=this.root;
          for(;;++numRemoved){
            head=head.next;
            if(--numLeft == 0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          this.size=size - numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private void collapsehead(CharDblLnkNode oldhead,CharDblLnkNode tail,CharPredicate filter,int size,int modCount){
      int numRemoved;
      int numLeft=size - (numRemoved=1) - 1;
      final CheckedList root=this.root;
      CharDblLnkNode newhead;
      for(newhead=oldhead.next;;--numLeft,++numRemoved,newhead=newhead.next){
        if(numLeft == 0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,tail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      head=newhead;
      CharDblLnkNode tmp;
      if((tmp=oldhead.prev) == null){
        for(var parent=this.parent;parent != null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          ++parent.modCount;
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent != null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head != oldhead){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapseHeadAndTail(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter,int size,
        int modCount){
      int numRemoved;
      if((numRemoved=2) != size){
        for(var newHead=head.next;;newHead=newHead.next){
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList root=this.root;
            for(--size;;++numRemoved,newTail=newTail.prev){
              if(numRemoved == size){
                CheckedCollection.checkModCount(modCount,root.modCount);
                break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,newTail,size - 1 - numRemoved,filter,
                    root.new ModCountChecker(modCount));
                break;
              }
            }
            root.modCount=++modCount;
            this.modCount=modCount;
            root.size-=numRemoved;
            this.size=size + 1 - numRemoved;
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            return;
          }
          if(++numRemoved == size){
            break;
          }
        }
      }
      CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private void collapsetail(CharDblLnkNode oldtail,CharDblLnkNode head,CharPredicate filter,int size,int modCount){
      int numRemoved;
      int numLeft=size - (numRemoved=1) - 1;
      final CheckedList root=this.root;
      CharDblLnkNode newtail;
      for(newtail=oldtail.prev;;--numLeft,++numRemoved,newtail=newtail.prev){
        if(numLeft == 0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,newtail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      tail=newtail;
      CharDblLnkNode tmp;
      if((tmp=oldtail.next) == null){
        for(var parent=this.parent;parent != null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          ++parent.modCount;
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent != null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail != oldtail){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void peelHead(CharDblLnkNode head){
      CharDblLnkNode after,before;
      (after=head.next).prev=before=head.prev;
      this.head=after;
      if(before == null){
        for(var curr=parent;curr != null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.head != head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
      }
    }
    private void peelTail(CharDblLnkNode tail){
      CharDblLnkNode after,before;
      (before=tail.prev).next=after=tail.next;
      this.tail=before;
      if(after == null){
        for(var curr=parent;curr != null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.tail != tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void peelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      tail=newTail;
      CharDblLnkNode after;
      if((after=oldTail.next) == null){
        final CheckedSubList parent;
        if((parent=this.parent) != null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.tail != oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void removeLastNode(CharDblLnkNode lastNode){
      CharDblLnkNode after;
      final CharDblLnkNode before=lastNode.prev;
      if((after=lastNode.next) == null){
        CheckedList root;
        (root=this.root).tail=before;
        if(before == null){
          for(var curr=parent;curr != null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before == null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr != null;curr=curr.parent){
            if(curr.head != lastNode){
              do{
                if(curr.tail != lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent) != null);
              break;
            }
            if(curr.tail != lastNode){
              for(;;){
                ++curr.modCount;
                --curr.size;
                curr.head=after;
                if((curr=curr.parent) == null){
                  break;
                }
                if(curr.head != lastNode){
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
        }
      }
      head=null;
      tail=null;
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        ++curr.modCount;
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      CharDblLnkNode tail;
      int modCount=this.modCount;
      final int size=this.size;
      try{
        if(filter.test((tail=this.tail).val)){
          if(size == 1){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            this.size=size - 1;
            // only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter,size,modCount);
            }else{
              collapsetail(tail,head,filter,size,modCount);
            }
          }
          return true;
        }else{
          if(size != 1){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter,size,modCount);
              return true;
            }else{
              return collapseBody(head,tail,filter,size,modCount);
            }
          }
        }
      }catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private Object writeReplace(){
      return new SerializableSubList(head,size,tail,root.new ModCountChecker(modCount));
    }
    @Override void addLast(char val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size != 1){
        if(++size != 1){
          CharDblLnkNode currTail,after;
          if((after=(currTail=tail).next) == null){
            currTail.next=currTail=new CharDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new CharDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new CharDblLnkNode(val));
        }
      }else{
        CharDblLnkNode newNode;
        bubbleUpRootInit(newNode=new CharDblLnkNode(val));
        size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    boolean uncheckedremoveVal(CharDblLnkNode head,int val){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(val == head.val){
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--size == 0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(final var tail=this.tail;head != tail;){
          CharDblLnkNode prev;
          if(val == (head=(prev=head).next).val){
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            --size;
            if(head == tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  private static class UncheckedSubList extends CharDblLnkSeq{
    private static class AscendingItr extends AbstractCharItr{
      transient final UncheckedSubList parent;
      transient CharDblLnkNode curr;
      private AscendingItr(AscendingItr itr){
        parent=itr.parent;
        curr=itr.curr;
      }
      private AscendingItr(UncheckedSubList parent){
        this.parent=parent;
        curr=parent.head;
      }
      private AscendingItr(UncheckedSubList parent,CharDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          CharDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final CharDblLnkNode curr;
        if((curr=this.curr) != null){
          CharDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
      @Override public boolean hasNext(){
        return curr != null;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        this.curr=(curr=this.curr) == parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size == 0){
          parent.removeLastNode(parent.tail);
        }else{
          CharDblLnkNode curr;
          if((curr=this.curr) == null){
            parent.peelTail(parent.tail);
          }else{
            CharDblLnkNode lastRet;
            if((lastRet=curr.prev) == parent.head){
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
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar{
      private transient int currIndex;
      private transient CharDblLnkNode lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        currIndex=itr.currIndex;
        lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,CharDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public void add(char val){
        final UncheckedSubList currList;
        final UncheckedList root;
        lastRet=null;
        ++currIndex;
        if(++(root=(currList=parent).root).size != 1){
          if(++currList.size != 1){
            CharDblLnkNode after,before;
            if((after=curr) != null){
              if((before=after.prev) != null){
                before.next=before=new CharDblLnkNode(before,val,after);
                if(after == currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new CharDblLnkNode(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              CharDblLnkNode newNode;
              if((after=(before=currList.tail).next) != null){
                currList.bubbleUpAppend(before,newNode=new CharDblLnkNode(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new CharDblLnkNode(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new CharDblLnkNode(val));
          }
        }else{
          CharDblLnkNode newNode;
          currList.bubbleUpRootInit(newNode=new CharDblLnkNode(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(currIndex < (bound=(parent=this.parent).size)){
          final CharDblLnkNode lastRet;
          CharDblLnkNode.uncheckedForEachAscending(curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          curr=null;
          currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int bound;
        final UncheckedSubList parent;
        if(currIndex < (bound=(parent=this.parent).size)){
          final CharDblLnkNode lastRet;
          CharDblLnkNode.uncheckedForEachAscending(curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          curr=null;
          currIndex=bound;
        }
      }
      @Override public boolean hasNext(){
        return currIndex < parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex > 0;
      }
      @Override public char nextChar(){
        final CharDblLnkNode curr;
        lastRet=curr=this.curr;
        this.curr=curr.next;
        ++currIndex;
        return curr.val;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public char previousChar(){
        CharDblLnkNode curr;
        lastRet=curr=(curr=this.curr) == null?parent.tail:curr.prev;
        this.curr=curr;
        --currIndex;
        return curr.val;
      }
      @Override public int previousIndex(){
        return currIndex - 1;
      }
      @Override public void remove(){
        CharDblLnkNode lastRet,curr;
        if((curr=(lastRet=this.lastRet).next) == this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        UncheckedSubList parent;
        if(--(parent=this.parent).size == 0){
          parent.removeLastNode(parent.tail);
        }else{
          if(lastRet == parent.tail){
            parent.peelTail(lastRet);
          }else{
            if(lastRet == parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        this.lastRet=null;
        --parent.root.size;
      }
      @Override public void set(char val){
        lastRet.val=val;
      }
    }
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset){
      super();
      this.root=root;
      parent=null;
      parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedList root,int rootOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      parent=null;
      parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset){
      super();
      root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
      root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    @Override public void add(int index,char val){
      final UncheckedList root;
      final var newNode=new CharDblLnkNode(val);
      if(++(root=this.root).size != 1){
        final int currSize;
        if((currSize=++size) != 1){
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public void clear(){
      int size;
      if((size=this.size) != 0){
        final UncheckedList root;
        (root=this.root).size-=size;
        clearAllHelper(size,head,tail,root);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size) != 0){
        CharDblLnkNode head,newTail;
        final var newHead=newTail=new CharDblLnkNode((head=this.head).val);
        for(int i=1;i != size;newTail=newTail.next=new CharDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      // TODO
      return false;
    }
    @Override public OmniIterator.OfChar iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new BidirectionalItr(this,((CharDblLnkSeq)this).getItrNode(index,size),index);
    }
    @Override public boolean remove(Object val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            // todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val) != (char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte || val instanceof Short){
                if((i=((Number)val).shortValue()) < 0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val) != (i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val) != (i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val) != (i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public char removeCharAt(int index){
      final char ret;
      int size;
      if((size=(--this.size) - index) <= index){
        var tail=this.tail;
        if(size == 0){
          ret=tail.val;
          if(index == 0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          CharDblLnkNode before;
          ret=(before=(tail=CharDblLnkNode.iterateDescending(tail,size - 1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index == 0){
          ret=head.val;
          peelHead(head);
        }else{
          CharDblLnkNode after;
          ret=(after=(head=CharDblLnkNode.iterateAscending(head,index - 1)).next).val;
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
      return (head=this.head) != null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final CharDblLnkNode head;
      return (head=this.head) != null && uncheckedRemoveIf(head,filter::test);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,TypeUtil.castToChar(val)); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if(val == (v=(char)val)){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val == (char)val){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){
            final char v;
            if((v=(char)val) == val){ return uncheckedremoveVal(head,v); }
          } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val >= 0){
        {
          final CharDblLnkNode head;
          if((head=this.head) != null){ return uncheckedremoveVal(head,val); } // end size check
        } // end checked sublist try modcount
      }// end val check
      return false;
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex - fromIndex) != 0){
        int tailDist;
        final CharDblLnkNode subListHead,subListTail;
        if((tailDist=size - toIndex) <= fromIndex){
          subListTail=CharDblLnkNode.iterateDescending(tail,tailDist);
          subListHead=subListSize <= fromIndex?CharDblLnkNode.iterateDescending(subListTail,subListSize - 1)
              :CharDblLnkNode.iterateAscending(head,fromIndex);
        }else{
          subListHead=CharDblLnkNode.iterateAscending(head,fromIndex);
          subListTail=subListSize <= tailDist?CharDblLnkNode.iterateAscending(subListHead,subListSize - 1)
              :CharDblLnkNode.iterateDescending(tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    private void bubbleUpAppend(CharDblLnkNode newTail){
      tail=newTail;
      for(var currList=parent;currList != null;++currList.size,currList.tail=newTail,currList=currList.parent){}
    }
    private void bubbleUpAppend(CharDblLnkNode oldTail,CharDblLnkNode newTail){
      oldTail.next=newTail;
      tail=newTail;
      for(var currList=parent;currList != null;currList.tail=newTail,currList=currList.parent){
        ++currList.size;
        if(currList.tail != oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr != null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpClearBody(CharDblLnkNode before,CharDblLnkNode head,int numRemoved,CharDblLnkNode tail,
        CharDblLnkNode after){
      for(var curr=parent;curr != null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head != head){
          while(curr.tail == tail){
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent) == null){ return; }
          }
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail != tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent) == null){ return; }
          }while(curr.head == head);
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(CharDblLnkNode tail,CharDblLnkNode after,int numRemoved){
      for(var curr=parent;curr != null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail != tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent) != null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(CharDblLnkNode head,CharDblLnkNode before,int numRemoved){
      for(var curr=parent;curr != null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head != head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent) != null);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(CharDblLnkNode oldHead,CharDblLnkNode newHead,int numRemoved,
        CharDblLnkNode newTail,CharDblLnkNode oldTail){
      head=newHead;
      tail=newTail;
      final CharDblLnkNode after,before=oldHead.prev;
      if((after=oldTail.next) == null){
        if(before == null){
          for(var parent=this.parent;parent != null;
              parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          UncheckedList root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent != null;
              parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head != oldHead){
              do{
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent) != null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before == null){
          for(var parent=this.parent;parent != null;
              parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail != oldTail){
              do{
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent) != null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent != null;
              parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head != oldHead){
              do{
                if(parent.tail != oldTail){
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent) != null);
              break;
            }
            if(parent.tail != oldTail){
              for(;;){
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent) == null){
                  break;
                }
                if(parent.head != oldHead){
                  parent.size-=numRemoved;
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
    private void bubbleUpDecrementSize(){
      UncheckedSubList parent;
      if((parent=this.parent) != null){
        parent.uncheckedBubbleUpDecrementSize();
      }
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr != null;curr.size-=numRemoved,curr=curr.parent){}
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr != null;++curr.size,curr=curr.parent){}
    }
    private void bubbleUpInit(CharDblLnkNode newNode){
      head=newNode;
      tail=newNode;
      UncheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent != null;currParent=(curr=currParent).parent){
        int parentSize;
        if((parentSize=++currParent.size) != 1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((CharDblLnkSeq)root).insertNode(curr.parentOffset,newNode);
    }
    private void bubbleUpInitHelper(int index,int size,CharDblLnkNode newNode){
      CharDblLnkNode after,before;
      if((size-=index) <= index){
        before=tail;
        if(size == 1){
          if((after=before.next) == null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          bubbleUpIncrementSize();
          before=(after=CharDblLnkNode.iterateDescending(before,size - 2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;
      }else{
        after=head;
        if(index == 0){
          if((before=after.prev) == null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          bubbleUpIncrementSize();
          after=(before=CharDblLnkNode.iterateAscending(after,index - 1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private void bubbleUpPeelHead(CharDblLnkNode newHead,CharDblLnkNode oldHead){
      newHead.prev=null;
      for(var curr=parent;curr != null;curr=curr.parent){
        if(curr.tail != oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private void bubbleUpPeelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      newTail.next=null;
      for(var curr=parent;curr != null;curr=curr.parent){
        if(curr.head != oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPrepend(CharDblLnkNode newHead){
      head=newHead;
      for(var currList=parent;currList != null;++currList.size,currList.head=newHead,currList=currList.parent){}
    }
    private void bubbleUpPrepend(CharDblLnkNode oldHead,CharDblLnkNode newHead){
      head=newHead;
      for(var currList=parent;currList != null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head != oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpRootInit(CharDblLnkNode newNode){
      head=newNode;
      tail=newNode;
      for(var parent=this.parent;parent != null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void clearAllHelper(int size,CharDblLnkNode head,CharDblLnkNode tail,UncheckedList root){
      CharDblLnkNode before;
      final CharDblLnkNode after=tail.next;
      if((before=head.prev) == null){
        // this sublist is not preceded by nodes
        if(after == null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after == null){
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
    private boolean collapseBody(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter){
      for(CharDblLnkNode prev;(head=(prev=head).next) != tail;){
        if(filter.test(head.val)){
          int numRemoved=1;
          for(;(head=head.next) != tail;++numRemoved){
            if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,filter);
              break;
            }
          }
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          size=size - numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    private void collapsehead(CharDblLnkNode oldhead,CharDblLnkNode tail,CharPredicate filter){
      int numRemoved=1;
      CharDblLnkNode newhead;
      outer:for(newhead=oldhead.next;;++numRemoved,newhead=newhead.next){
        if(newhead == tail){
          break;
        }
        if(!filter.test(newhead.val)){
          CharDblLnkNode prev,curr;
          for(curr=(prev=newhead).next;curr != tail;curr=(prev=curr).next){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.next) == tail){
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
      size-=numRemoved;
      head=newhead;
      CharDblLnkNode tmp;
      if((tmp=oldhead.prev) == null){
        for(var parent=this.parent;parent != null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent != null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head != oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapseHeadAndTail(CharDblLnkNode head,CharDblLnkNode tail,CharPredicate filter){
      CharDblLnkNode newHead;
      if((newHead=head.next) != tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            CharDblLnkNode prev;
            outer:for(var curr=(prev=newHead).next;curr != tail;curr=(prev=curr).next){
              if(filter.test(curr.val)){
                do{
                  ++numRemoved;
                  if((curr=curr.next) == tail){
                    break outer;
                  }
                }while(filter.test(curr.val));
                prev.next=curr;
                curr.prev=prev;
              }
            }
            size-=numRemoved;
            root.size-=numRemoved;
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,prev,tail);
            return;
          }else if((newHead=newHead.next) == tail){
            break;
          }
        }
      }
      UncheckedList root;
      int size;
      (root=this.root).size-=size=this.size;
      clearAllHelper(size,head,tail,root);
    }
    private void collapsetail(CharDblLnkNode oldtail,CharDblLnkNode head,CharPredicate filter){
      int numRemoved=1;
      CharDblLnkNode newtail;
      outer:for(newtail=oldtail.prev;;++numRemoved,newtail=newtail.prev){
        if(newtail == head){
          break;
        }
        if(!filter.test(newtail.val)){
          CharDblLnkNode next,curr;
          for(curr=(next=newtail).prev;curr != head;curr=(next=curr).prev){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.prev) == head){
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
      size-=numRemoved;
      tail=newtail;
      CharDblLnkNode tmp;
      if((tmp=oldtail.next) == null){
        for(var parent=this.parent;parent != null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent != null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail != oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void peelHead(CharDblLnkNode head){
      CharDblLnkNode after,before;
      (after=head.next).prev=before=head.prev;
      this.head=after;
      if(before == null){
        for(var curr=parent;curr != null;curr=curr.parent){
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.head != head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.head=after;
        }
      }
    }
    private void peelTail(CharDblLnkNode tail){
      CharDblLnkNode after,before;
      (before=tail.prev).next=after=tail.next;
      this.tail=before;
      if(after == null){
        for(var curr=parent;curr != null;curr=curr.parent){
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.tail != tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void peelTail(CharDblLnkNode newTail,CharDblLnkNode oldTail){
      tail=newTail;
      CharDblLnkNode after;
      if((after=oldTail.next) == null){
        final UncheckedSubList parent;
        if((parent=this.parent) != null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr != null;curr=curr.parent){
          if(curr.tail != oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void removeLastNode(CharDblLnkNode lastNode){
      CharDblLnkNode after;
      final CharDblLnkNode before=lastNode.prev;
      if((after=lastNode.next) == null){
        UncheckedList root;
        (root=this.root).tail=before;
        if(before == null){
          for(var curr=parent;curr != null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before == null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr != null;curr=curr.parent){
            if(curr.head != lastNode){
              do{
                if(curr.tail != lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent) != null);
              break;
            }
            if(curr.tail != lastNode){
              for(;;){
                --curr.size;
                curr.head=after;
                if((curr=curr.parent) == null){
                  break;
                }
                if(curr.head != lastNode){
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
        }
      }
      head=null;
      tail=null;
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        --curr.size;
      }while((curr=curr.parent) != null);
    }
    private boolean uncheckedRemoveIf(CharDblLnkNode head,CharPredicate filter){
      CharDblLnkNode tail;
      {
        if(filter.test((tail=this.tail).val)){
          if(tail == head){
            --root.size;
            size=size - 1;
            // only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter);
            }else{
              collapsetail(tail,head,filter);
            }
          }
          return true;
        }else{
          if(tail != head){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter);
              return true;
            }else{
              return collapseBody(head,tail,filter);
            }
          }
        }
      }
      return false;
    }
    private Object writeReplace(){
      return new UncheckedList(head,size,tail);
    }
    @Override void addLast(char val){
      final UncheckedList root;
      if(++(root=this.root).size != 1){
        if(++size != 1){
          CharDblLnkNode currTail,after;
          if((after=(currTail=tail).next) == null){
            currTail.next=currTail=new CharDblLnkNode(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new CharDblLnkNode(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new CharDblLnkNode(val));
        }
      }else{
        CharDblLnkNode newNode;
        bubbleUpRootInit(newNode=new CharDblLnkNode(val));
        size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    boolean uncheckedremoveVal(CharDblLnkNode head,int val){
      if(val == head.val){
        --root.size;
        if(--size == 0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail != head;){
          CharDblLnkNode prev;
          if(val == (head=(prev=head).next).val){
            --root.size;
            --size;
            if(head == tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  private static final long serialVersionUID=1L;
  private static int collapseBodyHelper(CharDblLnkNode newHead,CharDblLnkNode newTail,CharPredicate filter){
    int numRemoved=0;
    outer:for(CharDblLnkNode prev;(newHead=(prev=newHead).next) != newTail;){
      if(filter.test(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next) == newTail){
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
  private static long markSurvivors(CharDblLnkNode curr,int numLeft,CharPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1,curr=curr.next){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if(--numLeft == 0){ return word; }
    }
  }
  private static int markSurvivors(CharDblLnkNode curr,int numLeft,CharPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if(--numLeft == 0){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
        curr=curr.next;
      }while((marker<<=1) != 0L);
      survivorSet[wordOffset++]=word;
    }
  }
  transient CharDblLnkNode head;
  transient CharDblLnkNode tail;
  private CharDblLnkSeq(){}
  private CharDblLnkSeq(CharDblLnkNode head,int size,CharDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public boolean add(char val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToChar(val)); } // end
                                                                                                                     // size
                                                                                                                     // check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(byte val){
    if(val >= 0){
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(int val){
    if(val == (char)val){
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if((v=(char)val) == val){ return CharDblLnkNode.uncheckedcontains(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          // todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val) != (char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte || val instanceof Short){
              if((i=((Number)val).shortValue()) < 0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val) != (i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val) != (i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val) != (i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedcontains(head,tail,i);
          }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public boolean contains(short val){
    if(val >= 0){
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedcontains(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return false;
  }
  @Override public void forEach(CharConsumer action){
    final CharDblLnkNode head;
    if((head=this.head) != null){
      CharDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    final CharDblLnkNode head;
    if((head=this.head) != null){
      CharDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public char getChar(int index){
    return getNode(index,size).val;
  }
  @Override public int hashCode(){
    final CharDblLnkNode head;
    if((head=this.head) != null){ return CharDblLnkNode.uncheckedHashCode(head,tail); }
    return 1;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToChar(val)); } // end
                                                                                                                    // size
                                                                                                                    // check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(int val){
    if(val == (char)val){
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          final char v;
          if((v=(char)val) == val){ return CharDblLnkNode.uncheckedindexOf(head,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){
          // todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val) != (char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte || val instanceof Short){
              if((i=((Number)val).shortValue()) < 0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val) != (i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val) != (i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val) != (i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedindexOf(head,tail,i);
          }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int indexOf(short val){
    if(val >= 0){
      {
        final CharDblLnkNode head;
        if((head=this.head) != null){ return CharDblLnkNode.uncheckedindexOf(head,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToChar(val)); } // end
                                                                                                                        // size
                                                                                                                        // check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){
          final char v;
          if(val == (v=(char)val)){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    if(val == (char)val){
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){
          final char v;
          if((v=(char)val) == val){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,v); }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){
          // todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val) != (char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte || val instanceof Short){
              if((i=((Number)val).shortValue()) < 0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val) != (i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val) != (i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val) != (i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return CharDblLnkNode.uncheckedlastIndexOf(size,tail,i);
          }
        } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    if(val >= 0){
      {
        final CharDblLnkNode tail;
        if((tail=this.tail) != null){ return CharDblLnkNode.uncheckedlastIndexOf(size,tail,val); } // end size check
      } // end checked sublist try modcount
    }// end val check
    return -1;
  }
  @Override public void put(int index,char val){
    getNode(index,size).val=val;
  }
  @Override public void replaceAll(CharUnaryOperator operator){
    final CharDblLnkNode head;
    if((head=this.head) != null){
      CharDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Character> operator){
    final CharDblLnkNode head;
    if((head=this.head) != null){
      CharDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public char set(int index,char val){
    CharDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void sort(CharComparator sorter){
    // todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size) > 1){
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      if(sorter == null){
        CharSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        CharSortUtil.uncheckedStableSort(tmp,0,size,sorter);
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Character> sorter){
    // todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size) > 1){
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      if(sorter == null){
        CharSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        CharSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort(){
    // todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size) > 1){
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      CharSortUtil.uncheckedAscendingSort(tmp,0,size);
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort(){
    // todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size) > 1){
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      CharSortUtil.uncheckedDescendingSort(tmp,0,size);
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public Character[] toArray(){
    int size;
    if((size=this.size) != 0){
      final Character[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new Character[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size != 0){
      CharDblLnkNode.uncheckedCopyInto(dst,tail,size);
    }
    return dst;
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size) != 0){
      CharDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),tail,size);
    }else if(dst.length != 0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size) != 0){
      final char[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new char[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size) != 0){
      final double[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size) != 0){
      final float[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size) != 0){
      final int[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size) != 0){
      final long[] dst;
      CharDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public String toString(){
    final CharDblLnkNode head;
    if((head=this.head) != null){
      int size;
      final char[] buffer;
      CharDblLnkNode.uncheckedToString(head,tail,buffer=new char[size=this.size * 3]);
      buffer[0]='[';
      buffer[size - 1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  @Override public void unstableSort(CharComparator sorter){
    // todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size) > 1){
      final char[] tmp;
      final CharDblLnkNode tail;
      CharDblLnkNode.uncheckedCopyInto(tmp=new char[size],tail=this.tail,size);
      if(sorter == null){
        CharSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        CharSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
      }
      CharDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  private CharDblLnkNode getItrNode(int index,int size){
    if((size-=index) <= index){
      // the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return CharDblLnkNode.uncheckedIterateDescending(tail,size - 1);
      }
    }else{
      // the node is closer to the head
      return CharDblLnkNode.iterateAscending(head,index);
    }
  }
  private CharDblLnkNode getNode(int index,int size){
    if((size-=index) <= index){
      // the node is closer to the tail
      return CharDblLnkNode.iterateDescending(tail,size - 1);
    }else{
      // the node is closer to the head
      return CharDblLnkNode.iterateAscending(head,index);
    }
  }
  private void insertNode(int index,CharDblLnkNode newNode){
    int tailDist;
    if((tailDist=size - index) <= index){
      // the insertion point is closer to the tail
      final var tail=this.tail;
      if(tailDist == 1){
        // the insertion point IS the tail
        newNode.prev=tail;
        tail.next=newNode;
        this.tail=newNode;
      }else{
        // iterate from the root's tail
        iterateDescendingAndInsert(tailDist,tail,newNode);
      }
    }else{
      // the insertion point is closer to the head
      if(index == 0){
        // the insertion point IS the head
        CharDblLnkNode head;
        (head=this.head).prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        // iterate from the root's head
        iterateAscendingAndInsert(index,head,newNode);
      }
    }
  }
  private void iterateAscendingAndInsert(int dist,CharDblLnkNode before,CharDblLnkNode newNode){
    newNode.prev=before=CharDblLnkNode.iterateAscending(before,dist - 1);
    final CharDblLnkNode after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateDescendingAndInsert(int dist,CharDblLnkNode after,CharDblLnkNode newNode){
    newNode.next=after=CharDblLnkNode.iterateDescending(after,dist - 2);
    final CharDblLnkNode before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  abstract void addLast(char val);
}
