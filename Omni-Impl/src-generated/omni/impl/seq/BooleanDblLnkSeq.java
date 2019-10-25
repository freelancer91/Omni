package omni.impl.seq;
import java.util.Collection;
import omni.api.OmniCollection;
import java.util.ListIterator;
import java.util.List;
import omni.util.BooleanSortUtil;
import omni.api.OmniList;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractBooleanItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.BooleanComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
import omni.impl.AbstractOmniCollection;
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
import omni.util.ToStringUtil;
public abstract class BooleanDblLnkSeq extends 
AbstractOmniCollection<Boolean>
 implements
   BooleanSubListDefault
{
  static final class Node{
    transient Node prev;
    transient boolean val;
    transient Node next;
    Node(boolean val){
      this.val=val;
    }
    Node(Node prev,boolean val){
      this.prev=prev;
      this.val=val;
    }
    Node(boolean val,Node next){
      this.val=val;
      this.next=next;
    }
    Node(Node prev,boolean val,Node next){
      this.prev=prev;
      this.val=val;
      this.next=next;
    }
    static  void uncheckedCopyFrom(boolean[] src,int length,Node dst){
      for(;;dst=dst.prev)
      {
        dst.val=(boolean)src[--length];
        if(length==0)
        {
          return;
        }
      }
    }
    static  int uncheckedToString(Node curr,Node tail,byte[] buffer){
      int bufferOffset=1;
      for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringBoolean(curr.val,buffer,bufferOffset);
        if(curr==tail){
          return bufferOffset;
        }
      }
    }
    static  void uncheckedToString(Node curr,Node tail,ToStringUtil.OmniStringBuilderByte builder){
      for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendBoolean(curr.val);
        if(curr==tail){
          return;
        }
      }
    }
    static  int uncheckedHashCode(Node curr,Node tail){
      int hash=31+Boolean.hashCode(curr.val);
      while(curr!=tail){
        hash=(hash*31)+Boolean.hashCode((curr=curr.next).val);
      }
      return hash;
    }
    static  void uncheckedForEachAscending(Node node,int size,BooleanConsumer action){
      for(;;node=node.next){
        action.accept(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static  void uncheckedReplaceAll(Node node,int size,BooleanPredicate operator){
      for(;;node=node.next){
        node.val=operator.test(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static  void uncheckedForEachAscending(Node node,BooleanConsumer action){
      for(;;){
        action.accept(node.val);
        if((node=node.next)==null){
          return;
        }
      }
    }
    static  void uncheckedForEachAscending(Node node,Node tail,BooleanConsumer action){
      for(;;node=node.next){
        action.accept(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static  void uncheckedReplaceAll(Node node,Node tail,BooleanPredicate operator){
      for(;;node=node.next){
        node.val=operator.test(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static  void uncheckedForEachDescending(Node node,BooleanConsumer action){
      for(;;){
        action.accept(node.val);
        if((node=node.prev)==null){
          return;
        }
      }
    }
    static  void uncheckedForEachDescending(Node node,int size,BooleanConsumer action){
      for(;;node=node.prev){
        action.accept(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static  void eraseNode(Node node){
      Node next,prev;
      (next=node.next).prev=(prev=node.prev);
      prev.next=next;
    }
    static  Node iterateAscending(Node node,int length){
      if(length!=0){
        do{
          node=node.next;
        }while(--length!=0);
      }
      return node;
    }
    static  Node iterateDescending(Node node,int length){
      if(length!=0){
        do{
          node=node.prev;
        }while(--length!=0);
      }
      return node;
    }
    static  Node uncheckedIterateDescending(Node node,int length){
      do{
        node=node.prev;
      }while(--length!=0);
      return node;
    }
    static  boolean uncheckedcontains (Node head,Node tail
    ,boolean val
    ){
      for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static  int uncheckedsearch (Node head
    ,boolean val
    ){
      int index=1;
      for(;val!=(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static  int uncheckedindexOf (Node head,Node tail
    ,boolean val
    ){
      int index=0;
      for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static  int uncheckedlastIndexOf (int length,Node tail
    ,boolean val
    ){
      for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static  void uncheckedCopyInto(boolean[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(Boolean[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(Object[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(double[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=TypeUtil.castToDouble(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(float[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=TypeUtil.castToFloat(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(long[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=TypeUtil.castToLong(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(int[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(int)TypeUtil.castToByte(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(short[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(short)TypeUtil.castToByte(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(byte[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=TypeUtil.castToByte(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(char[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=TypeUtil.castToChar(curr.val);
        if(length==0){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node head;
  transient Node tail;
  private BooleanDblLnkSeq(Collection<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private BooleanDblLnkSeq(OmniCollection.OfRef<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private BooleanDblLnkSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private  BooleanDblLnkSeq(){
  }
  private BooleanDblLnkSeq(Node head,int size,Node tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(boolean val);
  @Override public boolean add(Boolean val){
    addLast((boolean)val);
    return true;
  }  
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,Node after,Node newNode){
    newNode.next=after=Node.iterateDescending(after,dist-2);
    final Node before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,Node before,Node newNode){
    newNode.prev=before=Node.iterateAscending(before,dist-1);
    final Node after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  void insertNode(int index,Node newNode){
    int tailDist;
    if((tailDist=this.size-index)<=index){
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
      if(index==0){
        //the insertion point IS the head
        Node head;
        (head=this.head).prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,this.head,newNode);
      }
    }
  }
  Node getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return Node.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return Node.iterateAscending(head,index);
    }
  }
  Node getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return Node.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return Node.iterateAscending(head,index);
    }
  }
  @Override public boolean set(int index,boolean val){
    Node tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,boolean val){
    getNode(index,size).val=val;
  }
  @Override public boolean getBoolean(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(BooleanConsumer action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      Node.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      Node.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public boolean[] toBooleanArray(){
    int size;
    if((size=this.size)!=0){
      final boolean[] dst;
      Node.uncheckedCopyInto(dst=new boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Boolean[] dst;
      Node.uncheckedCopyInto(dst=new Boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      Node.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      Node.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      Node.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      Node.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      final short[] dst;
      Node.uncheckedCopyInto(dst=new short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int size;
    if((size=this.size)!=0){
      final byte[] dst;
      Node.uncheckedCopyInto(dst=new byte[size],tail,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size)!=0){
      final char[] dst;
      Node.uncheckedCopyInto(dst=new char[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public void replaceAll(BooleanPredicate operator){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Boolean> operator){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(BooleanComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      if(sorter==null){
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Boolean> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      if(sorter==null){
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final boolean[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=Node.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        Node.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final Node head;
    if((head=this.head)!=null){
      return Node.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return Node.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return Node.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedcontains(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return Node.uncheckedcontains(head,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return Node.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return Node.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedindexOf(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return Node.uncheckedindexOf(head,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(val){
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return Node.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            if(val==0L){
              v=false;
            }else if(val==1L){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            switch(Float.floatToRawIntBits(val)){
              default:
                break returnFalse;
              case 0:
              case Integer.MIN_VALUE:
                v=false;
                break;
              case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return Node.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          returnFalse:for(;;){
            final boolean v;
            long bits;
            if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
              v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
              v=true;
            }else{
              break returnFalse;
            }
            return Node.uncheckedlastIndexOf(size,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          //todo: a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final boolean b;
            if(val instanceof Boolean){
              b=(boolean)val;
            }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              switch(((Number)val).intValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else if(val instanceof Float){
              switch(Float.floatToRawIntBits((float)val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  b=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  b=true;
              }
            }else if(val instanceof Double){
              final long bits;
              if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                b=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                b=true;
              }else{
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long v;
              if((v=(long)val)==0L){
                b=false;
              }else if(v==1L){
                b=true;
              }else{
               break returnFalse;
              }
            }else if(val instanceof Character){
              switch(((Character)val).charValue()){
                default:
                  break returnFalse;
                case 0:
                  b=false;
                  break;
                case 1:
                  b=true;
              }
            }else{
              break returnFalse;
            }
            return Node.uncheckedlastIndexOf(size,tail,b);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static  int collapseBodyHelper(Node newHead,Node newTail,boolean removeThis){
    int numRemoved=0;
    outer:for(Node prev;(newHead=(prev=newHead).next)!=newTail;){
      if(removeThis==(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next)==newTail){
            newHead.prev=prev;
            prev.next=newHead;
            break outer;
          }
        }while(removeThis==(newHead.val));
        newHead.prev=prev;
        prev.next=newHead;
      }
    }
    return numRemoved;
  }
  static class UncheckedSubList extends BooleanDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,Node head,int size,Node tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset,Node head,int size,Node tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private UncheckedSubList(UncheckedList root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private Object writeReplace(){
      return new UncheckedList(this.head,this.size,this.tail);
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
      }
    }
    private void bubbleUpAppend(Node oldTail,Node newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(Node newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(Node oldHead,Node newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(Node newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(Node newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,Node newNode){
      Node after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=Node.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=Node.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(Node newNode){
      this.head=newNode;
      this.tail=newNode;
      UncheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      root.insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,boolean val){
      final UncheckedList root;
      final var newNode=new Node(val);
      if(++(root=this.root).size!=1){
        final int currSize;
        if((currSize=++this.size)!=1){
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(boolean val){
      final UncheckedList root;
      if(++(root=this.root).size!=1){
        if(++this.size!=1){
          Node currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new Node(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new Node(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new Node(val));
        }
      }else{
        Node newNode;
        bubbleUpRootInit(newNode=new Node(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfBoolean){
            return root.isEqualTo(this.head,size,(OmniList.OfBoolean)list);
          }else if(list instanceof OmniList.OfRef){
            return root.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
          }
        }else{
          return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
        }
      }
      return false;
    }
    private void bubbleUpPeelHead(Node newHead,Node oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(Node newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(Node newTail,Node oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(Node newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
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
    private void peelTail(Node newTail,Node oldTail){
      this.tail=newTail;
      Node after;
      if((after=oldTail.next)==null){
        final UncheckedSubList parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void peelTail(Node tail){
      Node after,before;
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
    private void removeLastNode(Node lastNode){
      Node after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
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
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(Node head){
      Node after,before;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          Node before;
          ret=(before=( tail=Node.iterateDescending(tail,size-1)).prev).val;
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
          Node after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int collapsehead(Node oldhead,Node tail,boolean retainThis){
      int numRemoved=1;
      Node newhead;
      outer:for(newhead=oldhead.next;newhead!=tail;++numRemoved,newhead=newhead.next){
        if(newhead.val==retainThis){
          Node prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      this.head=newhead;
      Node tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
      return numRemoved;
    }
    private int collapsetail(Node oldtail,Node head,boolean retainThis){
      int numRemoved=1;
      Node newtail;
      outer:for(newtail=oldtail.prev;newtail!=head;++numRemoved,newtail=newtail.prev){
        if(newtail.val==retainThis){
          Node next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      this.tail=newtail;
      Node tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
      return numRemoved;
    }
    private void bubbleUpCollapseHeadAndTail(Node oldHead,Node newHead,int numRemoved,Node newTail,Node oldTail){
      this.head=newHead;
      this.tail=newTail;
      final Node after,before=oldHead.prev;
      if((after=oldTail.next)==null){
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
                  parent.size-=numRemoved;
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
    private boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
      Node tail;
      {
        boolean firstVal;
        if(filter.test(firstVal=(tail=this.tail).val)){
          if(tail==head){
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(head.val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                final UncheckedList root;
                int size;
                (root=this.root).size-=(size=this.size);
                //all nodes should be removed from the list
                clearAllHelper(size,head,tail,root);
              }else{
                final int numRemoved;
                root.size-=(numRemoved=collapsetail(tail,head,firstVal));
                this.size=size-numRemoved;
              }
            }else{
              collapseHeadAndTail(head,tail,firstVal,filter
              );
            }
          }
          return true;
        }else{
          if(tail!=head){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                final int numRemoved;
                root.size-=(numRemoved=collapsehead(head,tail,firstVal));
                this.size=size-numRemoved;
                return true;
              }
            }else{
              return collapseBody(head,tail,firstVal,filter
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
    private void clearAllHelper(int size,Node head,Node tail,UncheckedList root){
      Node before,after=tail.next;
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
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
      }
    }
    private void bubbleUpClearBody(Node before,Node head,int numRemoved,Node tail,Node after){
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
          curr.size-=numRemoved;
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
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(Node tail, Node after,int numRemoved){
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
    private void bubbleUpClearTail(Node head, Node before,int numRemoved){
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
    private void collapseHeadAndTail(Node head,Node tail,boolean removeThis,BooleanPredicate filter
    ){
      {
        int numRemoved=2;
        for(var newHead=head.next;;++numRemoved,newHead=newHead.next){
          if(newHead==tail){
            break;
          }
          if(newHead.val^removeThis){
            if(filter.test(!removeThis)){
              break;   
            }
            Node newTail;
            for(newTail=tail.prev;newTail!=newHead;++numRemoved,newTail=newTail.prev){
              if(newTail.val^removeThis){
                numRemoved+=collapseBodyHelper(newHead,newTail,removeThis);
                break;
              }
            }
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            this.size=size-numRemoved;
            root.size-=numRemoved;
            return;
          }
        }
      }
      final UncheckedList root;
      final int size;
      (root=this.root).size-=(size=this.size);
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(Node head,Node tail,boolean retainThis,BooleanPredicate filter
    ){
      for(Node prev;(head=(prev=head).next)!=tail;){
        if(head.val^retainThis){
          if(filter.test(!retainThis)){
            int numRemoved=1;
            while((head=head.next)!=tail){
              if(head.val==retainThis){
                numRemoved+=collapseBodyHelper(head,tail,!retainThis);
                break;
              }
              ++numRemoved;
            }
            prev.next=head;
            head.prev=prev;
            for(var parent=this.parent;parent!=null;
              parent.size-=numRemoved,parent=parent.parent){}
            root.size-=numRemoved;
            this.size=size-numRemoved;
            return true;
          }
          break;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node head,newTail;
        final var newHead=newTail=new Node((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static class AscendingItr
      extends AbstractBooleanItr
    {
      transient final UncheckedSubList parent;
      transient Node curr;
      private AscendingItr(UncheckedSubList parent,Node curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(AscendingItr itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedSubList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public boolean nextBoolean(){
        final Node curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          Node curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            Node lastRet;
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final Node curr;
        if((curr=this.curr)!=null){
          Node.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final Node curr;
        if((curr=this.curr)!=null){
          Node.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfBoolean{
      private transient int currIndex;
      private transient Node lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedSubList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList parent,Node curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public boolean nextBoolean(){
        final Node curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public boolean previousBoolean(){
        Node curr;
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
      @Override public void set(boolean val){
        lastRet.val=val;
      }
      @Override public void add(boolean val){
        final UncheckedSubList currList;
        final UncheckedList root;
        this.lastRet=null;
        ++currIndex;
        if(++(root=(currList=this.parent).root).size!=1){
          if(++currList.size!=1){
            Node after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new Node(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new Node(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              Node newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new Node(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new Node(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new Node(val));
          }
        }else{
          Node newNode;
          currList.bubbleUpRootInit(newNode=new Node(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        Node lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
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
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        this.lastRet=null;
        --parent.root.size;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final Node lastRet;
          Node.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int bound;
        final UncheckedSubList parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final Node lastRet;
          Node.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action::accept);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      return new BidirectionalItr(this,super.getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        int tailDist;
        final Node subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(Node head
    ,boolean val
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
          Node prev;
          if(val==((head=(prev=head).next).val)){
            --root.size;
            --this.size;
            if(head==tail){
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
  static class CheckedSubList extends BooleanDblLnkSeq{
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,Node head,int size,Node tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,Node head,int size,Node tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private CheckedSubList(CheckedList root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    boolean uncheckedremoveVal(Node head
    ,boolean val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(val==(head.val)){
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(final var tail=this.tail;head!=tail;){
          Node prev;
          if(val==((head=(prev=head).next).val)){
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            --this.size;
            if(head==tail){
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
    @Override public OmniIterator.OfBoolean iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,super.getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractBooleanItr
      implements OmniListIterator.OfBoolean{
      private transient final CheckedSubList parent;
      private transient int modCount;
      private transient Node curr;
      private transient Node lastRet;
      private transient int currIndex;
      private BidirectionalItr(BidirectionalItr itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private BidirectionalItr(CheckedSubList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList parent,Node curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public boolean nextBoolean(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          Node curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean previousBoolean(){
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          Node curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(boolean val){
        final Node lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))>0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int size,numLeft;
        final CheckedSubList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))>0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        Node lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList parent;
          CheckedList root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          Node curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
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
      @Override public void add(boolean val){
        final CheckedSubList currList;
        final CheckedList root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=this.parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        this.lastRet=null;
        ++currIndex;
        if(++root.size!=1){
          if(++currList.size!=1){
            Node after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new Node(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new Node(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              Node newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new Node(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new Node(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new Node(val));
          }
        }else{
          Node newNode;
          currList.bubbleUpRootInit(newNode=new Node(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final Node subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        Node head,newTail;
        final var newHead=newTail=new Node((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,b);
            }
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
          final Node head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedindexOf(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedindexOf(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedindexOf(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return Node.uncheckedindexOf(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedlastIndexOf(size,tail,v);
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedlastIndexOf(size,tail,v);
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedlastIndexOf(size,tail,v);
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return Node.uncheckedlastIndexOf(size,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedcontains(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedcontains(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedcontains(head,tail,v);
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
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return Node.uncheckedcontains(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient Node head;
      private transient Node tail;
      private transient int size;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(Node head,int size,Node tail,CheckedList.ModCountChecker modCountChecker){
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
          Node curr;
          int word,marker;
          for(this.head=curr=new Node(((marker=1)&(word=ois.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new Node(curr,(word&marker)!=0)){
            if((marker<<=1)==(1<<8)){
              word=ois.readUnsignedByte();
              marker=1;
            }
          }
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
              if(--size==0){
                oos.writeByte(word);
                return;
              }else if((marker<<=1)==(1<<8)){
                oos.writeByte(word);
                word=0;
                marker=1;
              }
              if((curr=curr.next).val){
                word|=marker;
              }
            }
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    private void bubbleUpPeelHead(Node newHead,Node oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(Node newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(Node newTail,Node oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(Node newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
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
    private void peelTail(Node newTail,Node oldTail){
      this.tail=newTail;
      Node after;
      if((after=oldTail.next)==null){
        final CheckedSubList parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
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
    private void peelTail(Node tail){
      Node after,before;
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
    private void removeLastNode(Node lastNode){
      Node after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
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
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(Node head){
      Node after,before;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          Node before;
          ret=(before=( tail=Node.iterateDescending(tail,size-1)).prev).val;
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
          Node after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final Node head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final Node head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private int collapsehead(Node oldhead,Node tail,boolean retainThis){
      int numRemoved=1;
      Node newhead;
      outer:for(newhead=oldhead.next;newhead!=tail;++numRemoved,newhead=newhead.next){
        if(newhead.val==retainThis){
          Node prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      this.head=newhead;
      Node tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            ++parent.modCount;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
      return numRemoved;
    }
    private int collapsetail(Node oldtail,Node head,boolean retainThis){
      int numRemoved=1;
      Node newtail;
      outer:for(newtail=oldtail.prev;newtail!=head;++numRemoved,newtail=newtail.prev){
        if(newtail.val==retainThis){
          Node next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(curr.val^retainThis){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(curr.val^retainThis);
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      this.tail=newtail;
      Node tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            ++parent.modCount;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
      return numRemoved;
    }
    private void bubbleUpCollapseHeadAndTail(Node oldHead,Node newHead,int numRemoved,Node newTail,Node oldTail){
      this.head=newHead;
      this.tail=newTail;
      final Node after,before=oldHead.prev;
      if((after=oldTail.next)==null){
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
                  ++parent.modCount;
                  parent.size-=numRemoved;
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
    private boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
      Node tail;
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        boolean firstVal;
        if(filter.test(firstVal=(tail=this.tail).val)){
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
            if(head.val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                root.size-=size;
                //all nodes should be removed from the list
                clearAllHelper(size,head,tail,root);
              }else{
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                final int numRemoved;
                root.size-=(numRemoved=collapsetail(tail,head,firstVal));
                this.size=size-numRemoved;
              }
            }else{
              collapseHeadAndTail(head,tail,firstVal,filter
                ,size,modCount
              );
            }
          }
          return true;
        }else{
          if(size!=1){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                final CheckedList root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                final int numRemoved;
                root.size-=(numRemoved=collapsehead(head,tail,firstVal));
                this.size=size-numRemoved;
                return true;
              }
            }else{
              return collapseBody(head,tail,firstVal,filter
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
    private void clearAllHelper(int size,Node head,Node tail,CheckedList root){
      Node before,after=tail.next;
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
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpClearBody(Node before,Node head,int numRemoved,Node tail,Node after){
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
          ++curr.modCount;
          curr.size-=numRemoved;
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
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(Node tail, Node after,int numRemoved){
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
    private void bubbleUpClearTail(Node head, Node before,int numRemoved){
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
    private void collapseHeadAndTail(Node head,Node tail,boolean removeThis,BooleanPredicate filter
      ,int size,int modCount
    ){
      int numLeft;
      if((numLeft=size-2)!=0)
      {
        int numRemoved=2;
        for(var newHead=head.next;;++numRemoved,newHead=newHead.next){
          if(newHead.val^removeThis){
            if(filter.test(!removeThis)){
              break;   
            }
            CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            Node newTail;
            for(newTail=tail.prev;newTail!=newHead;++numRemoved,newTail=newTail.prev){
              if(newTail.val^removeThis){
                numRemoved+=collapseBodyHelper(newHead,newTail,removeThis);
                break;
              }
            }
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            this.size=size-numRemoved;
            root.size-=numRemoved;
            return;
          }
          if(--numLeft==0){
            break;
          }
        }
      }
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(Node head,Node tail,boolean retainThis,BooleanPredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        Node prev;
        if((head=(prev=head).next).val^retainThis){
          if(filter.test(!retainThis)){
            final CheckedList root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            int numRemoved=1;
            while((head=head.next)!=tail){
              if(head.val==retainThis){
                numRemoved+=collapseBodyHelper(head,tail,!retainThis);
                break;
              }
              ++numRemoved;
            }
            prev.next=head;
            head.prev=prev;
            for(var parent=this.parent;parent!=null;
              parent.modCount=modCount,
              parent.size-=numRemoved,parent=parent.parent){}
            root.size-=numRemoved;
            this.size=size-numRemoved;
            return true;
          }
          break;
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpAppend(Node oldTail,Node newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(Node newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(Node oldHead,Node newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(Node newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(Node newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,Node newNode){
      Node after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=Node.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=Node.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(Node newNode){
      this.head=newNode;
      this.tail=newNode;
      CheckedSubList curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        ++currParent.modCount;
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      root.insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,boolean val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new Node(val);
      if(++root.size!=1){
        this.size=++currSize;
        if(currSize!=1){    
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(boolean val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size!=1){
        if(++this.size!=1){
          Node currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new Node(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new Node(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new Node(val));
        }
      }else{
        Node newNode;
        bubbleUpRootInit(newNode=new Node(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Node node;
      final var ret=(node=super.getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.getNode(index,size).val=val;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return super.getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final Node head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        Node.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        final Node head;
        if((head=this.head)!=null){
          Node.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(BooleanComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final boolean[] tmp;
        final Node tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final Node head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList root;
      int modCount=this.modCount;
      try{
        Node.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final int modCount=this.modCount;
      try{
        final Node head;
        if((head=this.head)!=null){
          Node.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final boolean[] tmp;
        final Node tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
          }finally{
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          }
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
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
    @Override public boolean[] toBooleanArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toBooleanArray();
    }
    @Override public Boolean[] toArray(){
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
    @Override public short[] toShortArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toShortArray();
    }
    @Override public byte[] toByteArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toByteArray();
    }
    @Override public char[] toCharArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toCharArray();
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
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfBoolean){
              return root.isEqualTo(this.head,size,(OmniList.OfBoolean)list);
            }else if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
            }
          }else{
            return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    } 
  }
  public static class CheckedList extends UncheckedList{
    transient int modCount;
    public CheckedList(Collection<? extends Boolean> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedList(){
    }
    CheckedList(Node head,int size,Node tail){
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
    @Override public boolean removeLastBoolean(){
      Node tail;
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
    @Override public boolean popBoolean(){
      Node head;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          Node before;
          ret=(before=(tail=Node.iterateDescending(tail,size-1)).prev).val;
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
          Node after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,boolean val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=++size;
      if((size-=index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new Node(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          Node before;
          (before=(tail=Node.iterateDescending(tail,size-2)).prev).next=before=new Node(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        Node head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new Node(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new Node(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          Node after;
          (after=(head=Node.iterateAscending(head,index-1)).next).prev=after=new Node(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(boolean val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      Node tmp;
      final var ret=(tmp=super.getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.getNode(index,size).val=val;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return super.getNode(index,size).val;
    }
    @Override public boolean getLastBoolean(){
      final Node tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean booleanElement(){
      final Node head;
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
    @Override public void forEach(BooleanConsumer action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(BooleanComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        if(sorter==null){
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final boolean[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        if(sorter==null){
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private int removeIfHelper(Node prev,int numLeft,boolean retainThis){
      int numSurvivors=1;
      outer:for(Node next;--numLeft!=0;prev=next,++numSurvivors){
        if((next=prev.next).val^retainThis){
          do{
            if(--numLeft==0){
              prev.next=null;
              this.tail=prev;
              break outer;
            }
          }while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(Node prev,Node head,int numLeft,boolean retainThis){
      int numSurvivors=0;
      outer:for(;;){
        if(--numLeft==0){
          prev.next=null;
          this.tail=prev;
          break;
        }
        if((head=head.next).val==retainThis){
          prev.next=head;
          head.prev=prev;
          do{
            ++numSurvivors;
            if(--numLeft==0){
              break outer;
            }
          }
          while((head=(prev=head).next).val==retainThis);
        }
      }
      return numSurvivors;
    }
    @Override boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        boolean firstVal;
        if(filter.test(firstVal=head.val)){
          while(--numLeft!=0){
            if((head=head.next).val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                break;
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              head.prev=null;
              this.head=head;
              this.size=removeIfHelper(head,numLeft,firstVal);
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
          for(int numSurvivors=1;--numLeft!=0;++numSurvivors){
            Node prev;
            if((head=(prev=head).next).val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+removeIfHelper(prev,head,numLeft,firstVal);
                return true;
              }
              break;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
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
        int size;
        out.writeInt(size=this.size);
        if(size!=0){
          var curr=this.head;
          for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
            if(--size==0){
              out.writeByte(word);
              return;
            }else if((marker<<=1)==(1<<8)){
              out.writeByte(word);
              word=0;
              marker=1;
            }
            if((curr=curr.next).val){
              word|=marker;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node head,newTail;
        final var newHead=newTail=new Node((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    private boolean isEqualTo(int size,OmniList.OfBoolean list){
      if(list instanceof BooleanDblLnkSeq){
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
          final Node thatHead,thisHead;
          return size==subList.size && ((thatHead=subList.head)==(thisHead=this.head) || UncheckedList.isEqualToHelper(thisHead,thatHead,this.tail));
        }else{
          return size==dls.size && UncheckedList.isEqualToHelper(this.head,dls.head,this.tail);
        }
      }else if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractArrSeq;
        if((abstractArrSeq=(AbstractBooleanArrSeq)list).size!=size){
          return false;
        }
        if(abstractArrSeq instanceof PackedBooleanArrSeq){
          return SequenceEqualityUtil.isEqualTo(0,((PackedBooleanArrSeq.UncheckedList)abstractArrSeq).words,size,this.head);
        }else{
          //must be BooleanArrSeq.UncheckedList
          return SequenceEqualityUtil.isEqualTo(((BooleanArrSeq.UncheckedList)abstractArrSeq).arr,0,size,this.head);
        }
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,thatRoot.words,thatOffset+size,this.head);
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final int thatRootOffset;
        final PackedBooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(thatRootOffset=subList.rootOffset,subList.root.words,thatRootOffset+size,this.head);
      }else if(list instanceof BooleanArrSeq.CheckedSubList){
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }else{
        //must be BooleanArrSeq.UncheckedSubList
        final int thatOffset;
        final BooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head); 
      }
    }
    private boolean isEqualTo(Node thisHead,int size,OmniList.OfBoolean list){
      if(list instanceof BooleanDblLnkSeq){
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        final Node thatHead;
        return size==dls.size && ((thatHead=dls.head)==thisHead||UncheckedList.isEqualToHelper(thatHead,thisHead,dls.tail));
      }else if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractArrSeq;
        if(size!=(abstractArrSeq=(AbstractBooleanArrSeq)list).size){
          return false;
        }
        if(abstractArrSeq instanceof PackedBooleanArrSeq.UncheckedList){
          return SequenceEqualityUtil.isEqualTo(0,((PackedBooleanArrSeq.UncheckedList)abstractArrSeq).words,size,thisHead);
        }else{
          //must be BooleanArrSeq.UncheckedList
          return SequenceEqualityUtil.isEqualTo(((BooleanArrSeq.UncheckedList)abstractArrSeq).arr,0,size,thisHead);
        }
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,thatRoot.words,thatOffset+size,thisHead);
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final PackedBooleanArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,subList.root.words,thatOffset+size,thisHead);
      }else if(list instanceof BooleanArrSeq.CheckedSubList){
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }else{
        //must be BooleanArrSeq.UncheckedSubList
        final BooleanArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfBoolean){
            return this.isEqualTo(size,(OmniList.OfBoolean)list);
          }else if(list instanceof OmniList.OfRef){
            return super.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
          }
        }else{
          final int modCount=this.modCount;
          try{
            return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,super.getItrNode(index,size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final Node subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList(this,fromIndex);
    } 
    boolean uncheckedremoveLastOccurrence(Node tail
    ,boolean val
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
        for(Node next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(Node head
    ,boolean val
    ){
      {
        if(val==(head.val)){
          ++this.modCount;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            ++this.modCount;
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
    @Override public boolean pollBoolean(){
      Node head;
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
      return false;
    }
    @Override public boolean pollLastBoolean(){
      Node tail;
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
      return false;
    }
    @Override public Boolean poll(){
      Node head;
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
    @Override public Boolean pollLast(){
      Node tail;
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
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToDouble(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToDouble(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToFloat(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToFloat(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToLong(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToLong(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(int)TypeUtil.castToByte(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(int)TypeUtil.castToByte(tail.val);
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
    @Override public short pollShort(){
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(short)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToChar(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToChar(tail.val);
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
    private static class DescendingItr
      extends AbstractBooleanItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient Node curr;
      transient Node lastRet;
      transient int currIndex;
      private DescendingItr(DescendingItr itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,Node curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new DescendingItr(this);
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        Node lastRet;
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
              Node.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,BooleanConsumer action){
        final int modCount=this.modCount;
        final CheckedList parent;
        try{
          Node.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount,currIndex,this.currIndex);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfBoolean{
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
      }
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,Node curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean previousBoolean(){
        final CheckedList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          Node curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
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
      @Override public void set(boolean val){
        final Node lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(boolean val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        Node newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new Node(val);
          }else{
            (newNode=parent.tail).next=newNode=new Node(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new Node(val,newNode);
            parent.head=newNode;
          }else{
            final Node tmp;
            (newNode=curr).prev=newNode=new Node(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        Node lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          Node curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))!=0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int size,numLeft;
        final CheckedList parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))!=0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
  }
  public static class UncheckedList extends BooleanDblLnkSeq implements OmniDeque.OfBoolean,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends Boolean> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedList(){
    }
    UncheckedList(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public boolean removeLastBoolean(){
      Node tail;
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
    @Override public boolean popBoolean(){
      Node head;
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
    @Override public boolean removeBooleanAt(int index){
      final boolean ret;
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
          Node before;
          ret=(before=(tail=Node.iterateDescending(tail,size-1)).prev).val;
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
          Node after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,boolean val){
      int size;
      if((size=++this.size-index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new Node(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          Node before;
          (before=(tail=Node.iterateDescending(tail,size-2)).prev).next=before=new Node(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        Node head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new Node(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new Node(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          Node after;
          (after=(head=Node.iterateAscending(head,index-1)).next).prev=after=new Node(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(boolean val){
      Node tail;
      if((tail=this.tail)==null){
        this.head=tail=new Node(val);
      }else{
        tail.next=tail=new Node(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(boolean val){
      Node head;
      if((head=this.head)==null){
        tail=head=new Node(val);
      }else{
        head.prev=head=new Node(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
          if((curr=curr.next)==null){
            out.writeByte(word);
            return;
          }else if((marker<<=1)==(1<<8)){
            out.writeByte(word);
            word=0;
            marker=1;
          }
          if(curr.val){
            word|=marker;
          }
        }
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node curr;
        int word,marker;
        for(this.head=curr=new Node(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new Node(curr,(word&marker)!=0)){
          if((marker<<=1)==(1<<8)){
            word=in.readUnsignedByte();
            marker=1;
          }
        }
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node head,newTail;
        final var newHead=newTail=new Node((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    private static boolean isEqualToHelper(Node thisHead,Node thatHead,Node thisTail){
      for(;thisHead.val==thatHead.val;thisHead=thisHead.next,thatHead=thatHead.next){
        if(thisHead==thisTail){
          return true;
        }
      }
      return false;
    }
    private boolean isEqualTo(int size,OmniList.OfBoolean list){
      if(list instanceof BooleanDblLnkSeq){
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        final Node thatHead,thisHead;
        return dls.size==size && ((thatHead=dls.head)==(thisHead=this.head)||isEqualToHelper(thisHead,thatHead,this.tail));
      }else if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractArrSeq;
        if((abstractArrSeq=(AbstractBooleanArrSeq)list).size!=size){
          return false;
        }
        if(abstractArrSeq instanceof PackedBooleanArrSeq){
          return SequenceEqualityUtil.isEqualTo(0,((PackedBooleanArrSeq.UncheckedList)abstractArrSeq).words,size,this.head);
        }else{
          //must be BooleanArrSeq.UncheckedList
          return SequenceEqualityUtil.isEqualTo(((BooleanArrSeq.UncheckedList)abstractArrSeq).arr,0,size,this.head);
        }
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final int thatRootOffset;
        final PackedBooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(thatRootOffset=subList.rootOffset,subList.root.words,thatRootOffset+size,this.head);
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,thatRoot.words,thatOffset+size,this.head);
      }else if(list instanceof BooleanArrSeq.UncheckedSubList){
        final int thatOffset;
        final BooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }else{
        //must be BooleanArrSeq.CheckedSubList
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }
    }
    private boolean isEqualTo(Node thisHead,int size,OmniList.OfBoolean list){
      if(list instanceof BooleanDblLnkSeq){
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
        }
        final Node thatHead;
        return dls.size==size && ((thatHead=dls.head)==thisHead || isEqualToHelper(thatHead,thisHead,dls.tail));
      }else if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractArrSeq;
        if(size!=(abstractArrSeq=(AbstractBooleanArrSeq)list).size){
          return false;
        }
        if(abstractArrSeq instanceof PackedBooleanArrSeq.UncheckedList){
          return SequenceEqualityUtil.isEqualTo(0,((PackedBooleanArrSeq.UncheckedList)abstractArrSeq).words,size,thisHead);
        }else{
          //must be BooleanArrSeq.UncheckedList
          return SequenceEqualityUtil.isEqualTo(((BooleanArrSeq.UncheckedList)abstractArrSeq).arr,0,size,thisHead);
        }
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final PackedBooleanArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,subList.root.words,thatOffset+size,thisHead);
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && SequenceEqualityUtil.isEqualTo(thatOffset=subList.rootOffset,thatRoot.words,thatOffset+size,thisHead);
      }else if(list instanceof BooleanArrSeq.UncheckedSubList){
        final BooleanArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }else{
        //must be BooleanArrSeq.CheckedSubList
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }
    }
    boolean isEqualTo(Node thisHead,int size,OmniList.OfRef<?> list){
      //TODO
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final int thatOffset;
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }
    }
    private static boolean isEqualTo(ListIterator<?> itr,Node head,Node tail){ 
      while(itr.hasNext() && TypeUtil.refEquals(itr.next(),head.val)){
        if(head==tail){
          return !itr.hasNext();
        }
        head=head.next;
      }
      return false;
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfBoolean){
            return this.isEqualTo(size,(OmniList.OfBoolean)list);
          }else if(list instanceof OmniList.OfRef){
            return this.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
          }
        }else{
          return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
        }
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(Node head
    ,boolean val
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
        for(Node prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      return new BidirectionalItr(this,super.getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        final int tailDist;
        final Node subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList(this,fromIndex);
    }
    @Override public boolean getLastBoolean(){
      return tail.val;
    }
    @Override public boolean offerFirst(boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void addFirst(boolean val){
      push((boolean)val);
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public boolean booleanElement(){
      return head.val;
    }
    @Override public boolean offer(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return Node.uncheckedsearch(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(Node tail
    ,boolean val
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
        for(Node next;(tail=(next=tail).prev)!=null;){
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
    @Override public Boolean peekFirst(){
      return peek();
    }
    @Override public Boolean pollFirst(){
      return poll();
    }
    @Override public Boolean pop(){
      return popBoolean();
    }
    @Override public Boolean remove(){
      return popBoolean();
    }
    @Override public boolean offer(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public Boolean element(){
      return booleanElement();
    }
    @Override public Boolean removeFirst(){
      return popBoolean();
    }
    @Override public Boolean removeLast(){
      return removeLastBoolean();
    }
    @Override public boolean offerFirst(Boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void push(Boolean val){
      push((boolean)val);
    }
    @Override public void addFirst(Boolean val){
      push((boolean)val);
    }
    @Override public void addLast(Boolean val){
      addLast((boolean)val);
    }
    @Override public Boolean getFirst(){
      return booleanElement();
    }
    @Override public Boolean getLast(){
      return getLastBoolean();
    }
    @Override public boolean pollBoolean(){
      Node head;
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
      return false;
    }
    @Override public boolean pollLastBoolean(){
      Node tail;
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
      return false;
    }
    @Override public Boolean poll(){
      Node head;
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
    @Override public Boolean pollLast(){
      Node tail;
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
      Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToDouble(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToFloat(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToLong(tail.val);
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
      Node head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=(int)TypeUtil.castToByte(tail.val);
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
    @Override public short pollShort(){
      Node head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=(short)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
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
      Node tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToChar(tail.val);
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
    @Override public boolean peekBoolean(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return false;
    }
    @Override public boolean peekLastBoolean(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return false;
    }
    @Override public Boolean peek(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Boolean peekLast(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToDouble(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final Node tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToDouble(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToFloat(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final Node tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToFloat(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToLong(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final Node tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToLong(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final Node head;
      if((head=this.head)!=null){
        return (int)TypeUtil.castToByte(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (int)TypeUtil.castToByte(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final Node head;
      if((head=this.head)!=null){
        return (short)TypeUtil.castToByte(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short peekLastShort(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (short)TypeUtil.castToByte(tail.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToByte(head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte peekLastByte(){
      final Node tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToByte(tail.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToChar(head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char peekLastChar(){
      final Node tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToChar(tail.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(Node prev,Node tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(Node next;prev!=tail;++numSurvivors,prev=next){
        if((next=prev.next).val^retainThis){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(Node prev,Node curr,Node tail,boolean retainThis){
      int numSurvivors=0;
      while(curr!=tail) {
        if((curr=curr.next).val==retainThis){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while((curr=(prev=curr).next).val==retainThis);
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        for(var tail=this.tail;head!=tail;){
          if((head=head.next).val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            head.prev=null;
            this.head=head;
            this.size=removeIfHelper(head,tail,firstVal);
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
          final Node prev;
          if((head=(prev=head).next).val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
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
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          Node curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            Node lastRet;
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
      @Override public boolean nextBoolean(){
        final Node curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(Node curr,BooleanConsumer action){
        Node.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractBooleanItr
    {
      transient final UncheckedList parent;
      transient Node curr;
      private AscendingItr(AscendingItr itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedList parent,Node curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr(this);
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
          Node curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            Node lastRet;
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
      @Override public boolean nextBoolean(){
        final Node curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(Node curr,BooleanConsumer action){
        Node.uncheckedForEachAscending(curr,action);
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final Node curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final Node curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfBoolean{
      private transient int currIndex;
      private transient Node lastRet;
      private BidirectionalItr(BidirectionalItr itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,Node curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr(this);
      }
      @Override public boolean nextBoolean(){
        final Node curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public boolean previousBoolean(){
        Node curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(boolean val){
        final UncheckedList parent;
        Node newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new Node(val);
          }else{
            (newNode=parent.tail).next=newNode=new Node(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new Node(val,newNode);
            parent.head=newNode;
          }else{
            final Node tmp;
            (newNode=curr).prev=newNode=new Node(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(boolean val){
        lastRet.val=val;
      }
      @Override public void remove(){
        Node lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
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
            parent.head=curr;
            curr.prev=null;
          }else{
            curr.prev=lastRet=lastRet.prev;
            lastRet.next=curr;
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(Node curr,BooleanConsumer action){
        Node.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
