package omni.impl.seq;
import java.util.Collection;
import omni.api.OmniCollection;
import java.util.ListIterator;
import java.util.List;
import omni.util.FloatSortUtil;
import omni.api.OmniList;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import java.util.function.UnaryOperator;
import omni.function.FloatUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractFloatItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.FloatComparator;
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
import omni.util.HashUtil;
public abstract class FloatDblLnkSeq extends 
AbstractOmniCollection<Float>
 implements
   FloatSubListDefault
{
  static final class Node{
    transient Node prev;
    transient float val;
    transient Node next;
    Node(float val){
      this.val=val;
    }
    Node(Node prev,float val){
      this.prev=prev;
      this.val=val;
    }
    Node(float val,Node next){
      this.val=val;
      this.next=next;
    }
    Node(Node prev,float val,Node next){
      this.prev=prev;
      this.val=val;
      this.next=next;
    }
    static  void uncheckedCopyFrom(float[] src,int length,Node dst){
      for(;;dst=dst.prev)
      {
        dst.val=(float)src[--length];
        if(length==0)
        {
          return;
        }
      }
    }
    static  int uncheckedToString(Node curr,Node tail,byte[] buffer){
      int bufferOffset=1;
      for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
        if(curr==tail){
          return bufferOffset;
        }
      }
    }
    static  void uncheckedToString(Node curr,Node tail,ToStringUtil.OmniStringBuilderByte builder){
      for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendFloat(curr.val);
        if(curr==tail){
          return;
        }
      }
    }
    static  int uncheckedHashCode(Node curr,Node tail){
      int hash=31+HashUtil.hashFloat(curr.val);
      while(curr!=tail){
        hash=(hash*31)+HashUtil.hashFloat((curr=curr.next).val);
      }
      return hash;
    }
    static  void uncheckedForEachAscending(Node node,int size,FloatConsumer action){
      for(;;node=node.next){
        action.accept(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static  void uncheckedReplaceAll(Node node,int size,FloatUnaryOperator operator){
      for(;;node=node.next){
        node.val=operator.applyAsFloat(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static  void uncheckedForEachAscending(Node node,FloatConsumer action){
      for(;;){
        action.accept(node.val);
        if((node=node.next)==null){
          return;
        }
      }
    }
    static  void uncheckedForEachAscending(Node node,Node tail,FloatConsumer action){
      for(;;node=node.next){
        action.accept(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static  void uncheckedReplaceAll(Node node,Node tail,FloatUnaryOperator operator){
      for(;;node=node.next){
        node.val=operator.applyAsFloat(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static  void uncheckedForEachDescending(Node node,FloatConsumer action){
      for(;;){
        action.accept(node.val);
        if((node=node.prev)==null){
          return;
        }
      }
    }
    static  void uncheckedForEachDescending(Node node,int size,FloatConsumer action){
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
    static  boolean uncheckedcontainsBits(Node head,Node tail
    ,int bits
    ){
      for(;bits!=Float.floatToRawIntBits(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static  int uncheckedsearchBits(Node head
    ,int bits
    ){
      int index=1;
      for(;bits!=Float.floatToRawIntBits(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static  int uncheckedindexOfBits(Node head,Node tail
    ,int bits
    ){
      int index=0;
      for(;bits!=Float.floatToRawIntBits(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static  int uncheckedlastIndexOfBits(int length,Node tail
    ,int bits
    ){
      for(;bits!=Float.floatToRawIntBits(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static  boolean uncheckedcontains0(Node head,Node tail
    ){
      for(;0!=(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static  int uncheckedsearch0(Node head
    ){
      int index=1;
      for(;0!=(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static  int uncheckedindexOf0(Node head,Node tail
    ){
      int index=0;
      for(;0!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static  int uncheckedlastIndexOf0(int length,Node tail
    ){
      for(;0!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static  boolean uncheckedcontainsNaN(Node head,Node tail
    ){
      for(;!Float.isNaN(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static  int uncheckedsearchNaN(Node head
    ){
      int index=1;
      for(;!Float.isNaN(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static  int uncheckedindexOfNaN(Node head,Node tail
    ){
      int index=0;
      for(;!Float.isNaN(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static  int uncheckedlastIndexOfNaN(int length,Node tail
    ){
      for(;!Float.isNaN(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static  void uncheckedCopyInto(float[] dst,Node curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(curr.val);
        if(length==0){
          return;
        }
      }
    }
    static  void uncheckedCopyInto(Float[] dst,Node curr,int length){
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
        dst[--length]=(double)(curr.val);
        if(length==0){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node head;
  transient Node tail;
  private FloatDblLnkSeq(Collection<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfRef<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.FloatOutput<?> that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfShort that){
    super();
    //TODO optimize
    this.addAll(that);
  }  
  private FloatDblLnkSeq(OmniCollection.OfInt that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfLong that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfFloat that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatDblLnkSeq(OmniCollection.OfChar that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private  FloatDblLnkSeq(){
  }
  private FloatDblLnkSeq(Node head,int size,Node tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(float val);
  @Override public boolean add(Float val){
    addLast((float)val);
    return true;
  }  
  @Override public boolean add(float val){
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
  private static  int markSurvivors(Node curr,int numLeft,FloatPredicate filter,long[] survivorSet){
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
  private static  long markSurvivors(Node curr,int numLeft,FloatPredicate filter){
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
  @Override public float set(int index,float val){
    Node tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,float val){
    getNode(index,size).val=val;
  }
  @Override public float getFloat(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(FloatConsumer action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
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
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      Node.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Float[] dst;
      Node.uncheckedCopyInto(dst=new Float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  @Override public void replaceAll(FloatUnaryOperator operator){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Float> operator){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(FloatComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final float[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      if(sorter==null){
        FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Float> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final float[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      if(sorter==null){
        FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final float[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final float[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(FloatComparator sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final float[] tmp;
      final Node tail;
      Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      if(sorter==null){
        FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
      }else{
        FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
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
          if(val){
            return Node.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return Node.uncheckedcontains0(head,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedcontains0(head,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedcontains0(head,tail);
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
          if(val==val){
            return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedcontainsNaN(head,tail);
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
          final float v;
          if(val==(v=(float)val)){
            return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return Node.uncheckedcontainsNaN(head,tail);
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
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
              }
              return Node.uncheckedcontainsNaN(head,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return Node.uncheckedcontainsNaN(head,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedcontains0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(l));
              }
              return Node.uncheckedcontains0(head,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedcontains0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedcontains0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return Node.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return Node.uncheckedcontains0(head,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          if(val!=0){
            return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedcontains0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          if(val!=0){
            return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedcontains0(head,tail);
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
          if(val){
            return Node.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return Node.uncheckedindexOf0(head,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedindexOf0(head,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedindexOf0(head,tail);
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
          if(val==val){
            return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedindexOfNaN(head,tail);
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
          final float v;
          if(val==(v=(float)val)){
            return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return Node.uncheckedindexOfNaN(head,tail);
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
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
              }
              return Node.uncheckedindexOfNaN(head,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return Node.uncheckedindexOfNaN(head,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedindexOf0(head,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(l));
              }
              return Node.uncheckedindexOf0(head,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedindexOf0(head,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedindexOf0(head,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return Node.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return Node.uncheckedindexOf0(head,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          if(val!=0){
            return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedindexOf0(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final Node head;
        if((head=this.head)!=null)
        {
          if(val!=0){
            return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedindexOf0(head,tail);
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
          if(val){
            return Node.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
          }
          return Node.uncheckedlastIndexOf0(size,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val))
            {
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedlastIndexOf0(size,tail);
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
          if(val!=0){
            if(TypeUtil.checkCastToFloat(val)){
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
          }else{
            return Node.uncheckedlastIndexOf0(size,tail);
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
          if(val==val){
            return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedlastIndexOfNaN(size,tail);
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
          final float v;
          if(val==(v=(float)val)){
            return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return Node.uncheckedlastIndexOfNaN(size,tail);
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
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
              }
              return Node.uncheckedlastIndexOfNaN(size,tail);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return Node.uncheckedlastIndexOfNaN(size,tail);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(l));
              }
              return Node.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
              }
              return Node.uncheckedlastIndexOf0(size,tail);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return Node.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
              }
              return Node.uncheckedlastIndexOf0(size,tail);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          if(val!=0){
            return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
          }
          return Node.uncheckedlastIndexOf0(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static  int collapseBodyHelper(Node newHead,Node newTail,FloatPredicate filter){
    int numRemoved=0;
    outer:for(Node prev;(newHead=(prev=newHead).next)!=newTail;){
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
  static class UncheckedSubList extends FloatDblLnkSeq{
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
    @Override public void add(int index,float val){
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
    @Override void addLast(float val){
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
          if(list instanceof OmniList.OfFloat){
            return root.isEqualTo(this.head,size,(OmniList.OfFloat)list);
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
    @Override public float removeFloatAt(int index){
      final float ret;
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
    @Override public boolean removeIf(FloatPredicate filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private void collapsehead(Node oldhead,Node tail,FloatPredicate filter
    ){
      int numRemoved=1;
      Node newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){ 
        if(newhead==tail){
          break;
        }
        if(!filter.test(newhead.val)){
          Node prev,curr;
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
      Node tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(Node oldtail,Node head,FloatPredicate filter
    ){
      int numRemoved=1;
      Node newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.prev){ 
        if(newtail==head){
          break;
        }
        if(!filter.test(newtail.val)){
          Node next,curr;
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
      Node tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
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
    private boolean uncheckedRemoveIf(Node head,FloatPredicate filter){
      Node tail;
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
    private void collapseHeadAndTail(Node head,Node tail,FloatPredicate filter
    ){
      Node newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            Node prev;
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
    private boolean collapseBody(Node head,Node tail,FloatPredicate filter
    ){
      for(Node prev;(head=(prev=head).next)!=tail;){
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
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
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
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
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
      @Override public void forEachRemaining(FloatConsumer action){
        final Node curr;
        if((curr=this.curr)!=null){
          Node.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final Node curr;
        if((curr=this.curr)!=null){
          Node.uncheckedForEachAscending(curr,parent.tail,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
        final Node curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public float previousFloat(){
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
      @Override public void set(float val){
        lastRet.val=val;
      }
      @Override public void add(float val){
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
      @Override public void forEachRemaining(FloatConsumer action){
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
      @Override public void forEachRemaining(Consumer<? super Float> action){
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
    @Override public OmniIterator.OfFloat iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      return new BidirectionalItr(this,super.getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val==val){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveValNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveValNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValBits(Node head
    ,int bits
    ){
      if(bits==Float.floatToRawIntBits(head.val)){
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
          if(bits==Float.floatToRawIntBits((head=(prev=head).next).val)){
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
    boolean uncheckedremoveVal0(Node head
    ){
      if(0==(head.val)){
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
          if(0==((head=(prev=head).next).val)){
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
    boolean uncheckedremoveValNaN(Node head
    ){
      if(Float.isNaN(head.val)){
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
          if(Float.isNaN((head=(prev=head).next).val)){
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
  static class CheckedSubList extends FloatDblLnkSeq{
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
    boolean uncheckedremoveValBits(Node head
    ,int bits
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(bits==Float.floatToRawIntBits(head.val)){
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
          if(bits==Float.floatToRawIntBits((head=(prev=head).next).val)){
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
    boolean uncheckedremoveVal0(Node head
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(0==(head.val)){
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
          if(0==((head=(prev=head).next).val)){
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
    boolean uncheckedremoveValNaN(Node head
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(Float.isNaN(head.val)){
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
          if(Float.isNaN((head=(prev=head).next).val)){
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
    @Override public OmniIterator.OfFloat iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,super.getItrNode(index,size),index);
    }
    private static class BidirectionalItr
      extends AbstractFloatItr
      implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
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
      @Override public float previousFloat(){
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
      @Override public void set(float val){
        final Node lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
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
      @Override public void forEachRemaining(Consumer<? super Float> action){
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
      @Override public void add(float val){
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
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val==val){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveValNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveValNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
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
            if(val){
              return Node.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedindexOf0(head,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedindexOf0(head,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedindexOf0(head,tail);
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
            if(val==val){
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedindexOfNaN(head,tail);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedindexOfNaN(head,tail);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedindexOfNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedindexOfNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedindexOf0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedindexOf0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedindexOf0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedindexOf0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedindexOf0(head,tail);
              }else{
                break returnFalse;
              }
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedindexOf0(head,tail);
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
            if(val){
              return Node.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedlastIndexOf0(size,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedlastIndexOf0(size,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedlastIndexOf0(size,tail);
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
            if(val==val){
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedlastIndexOfNaN(size,tail);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedlastIndexOfNaN(size,tail);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedlastIndexOfNaN(size,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedlastIndexOfNaN(size,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedlastIndexOf0(size,tail);
              }else{
                break returnFalse;
              }
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
          final Node tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return Node.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedlastIndexOf0(size,tail);
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
            if(val){
              return Node.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedcontains0(head,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedcontains0(head,tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedcontains0(head,tail);
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
            if(val==val){
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontainsNaN(head,tail);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedcontainsNaN(head,tail);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedcontainsNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedcontainsNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedcontains0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedcontains0(head,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontains0(head,tail);
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
          for(this.head=curr=new Node((float)ois.readFloat());--size!=0;curr=curr.next=new Node(curr,(float)ois.readFloat())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            for(;;curr=curr.next){
              oos.writeFloat(curr.val);
              if(--size==0){
                break;
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
    private static  Node pullSurvivorsDown(Node prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
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
          return curr;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static  Node pullSurvivorsDown(Node prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return curr;
        }
        prev=curr;
      }
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
    @Override public float removeFloatAt(int index){
      final float ret;
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
    @Override public boolean removeIf(FloatPredicate filter){
      final Node head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      final Node head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter::test);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(Node oldhead,Node tail,FloatPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      Node newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){ 
        if(numLeft==0){
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
      this.head=newhead;
      Node tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
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
    private void collapsetail(Node oldtail,Node head,FloatPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList root=this.root;
      Node newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.prev){ 
        if(numLeft==0){
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
      this.tail=newtail;
      Node tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
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
    private boolean uncheckedRemoveIf(Node head,FloatPredicate filter){
      Node tail;
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
    private static  int collapseBodyHelper(Node newHead,Node newTail,int numLeft,FloatPredicate filter,CheckedList.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft))!=null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft))!=null){
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
    private void collapseHeadAndTail(Node head,Node tail,FloatPredicate filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        for(var newHead=head.next;;newHead=newHead.next){
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList root=this.root;
            for(--size;;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,newTail,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
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
          if(++numRemoved==size){
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
    private boolean collapseBody(Node head,Node tail,FloatPredicate filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        Node prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            head=head.next;
            if(--numLeft==0){
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
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
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
    @Override public void add(int index,float val){
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
    @Override void addLast(float val){
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
    @Override public float set(int index,float val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Node node;
      final var ret=(node=super.getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,float val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.getNode(index,size).val=val;
    }
    @Override public float getFloat(int index){
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
    @Override public void replaceAll(FloatUnaryOperator operator){
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
    @Override public void forEach(FloatConsumer action){
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
    @Override public void sort(FloatComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final float[] tmp;
        final Node tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          try{
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
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
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
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
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
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
    @Override public void forEach(Consumer<? super Float> action){
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
    @Override public void sort(Comparator<? super Float> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final float[] tmp;
        final Node tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          try{
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
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
    @Override public void unstableSort(FloatComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList root;
        final float[] tmp;
        final Node tail;
        if(sorter==null){
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }else{
          Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
          try{
            FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
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
    @Override public float[] toFloatArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toFloatArray();
    }
    @Override public Float[] toArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray();
    }
    @Override public double[] toDoubleArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toDoubleArray();
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
            if(list instanceof OmniList.OfFloat){
              return root.isEqualTo(this.head,size,(OmniList.OfFloat)list);
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
    public CheckedList(Collection<? extends Float> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedList(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedList(OmniCollection.OfLong that){
      super(that);
    }
    public CheckedList(OmniCollection.OfFloat that){
      super(that);
    }
    public CheckedList(OmniCollection.OfChar that){
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
    @Override public float removeLastFloat(){
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
    @Override public float popFloat(){
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
    @Override public float removeFloatAt(int index){
      final float ret;
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
    @Override public void add(int index,float val){
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
    @Override public void addLast(float val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public float set(int index,float val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      Node tmp;
      final var ret=(tmp=super.getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,float val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.getNode(index,size).val=val;
    }
    @Override public float getFloat(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return super.getNode(index,size).val;
    }
    @Override public float getLastFloat(){
      final Node tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public float floatElement(){
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
    @Override public void forEach(FloatConsumer action){
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
    @Override public void replaceAll(FloatUnaryOperator operator){
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
    @Override public void sort(FloatComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
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
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
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
    @Override public void replaceAll(UnaryOperator<Float> operator){
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
    @Override public void sort(Comparator<? super Float> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(FloatComparator sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final float[] tmp;
        final Node tail;
        Node.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          ++this.modCount;
        }else{
          int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          this.modCount=modCount+1;
        }
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(Node prev,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(Node prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(Node prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(Node head,FloatPredicate filter){
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
        int size;
        out.writeInt(size=this.size);
        if(size!=0){
          var curr=this.head;
          for(;;curr=curr.next){
            out.writeFloat(curr.val);
            if(--size==0){
              break;
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
    private boolean isEqualTo(int size,OmniList.OfFloat list){
      if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
          final Node thatHead,thisHead;
          return size==subList.size && ((thatHead=subList.head)==(thisHead=this.head) || UncheckedList.isEqualToHelper(thisHead,thatHead,this.tail));
        }else{
          return size==dls.size && UncheckedList.isEqualToHelper(this.head,dls.head,this.tail);
        }
      }else if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,this.head);
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }else{
        //must be FloatArrSeq.UncheckedSubList
        final int thatOffset;
        final FloatArrSeq.UncheckedSubList subList;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head); 
      }
    }
    private boolean isEqualTo(Node thisHead,int size,OmniList.OfFloat list){
      if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        final Node thatHead;
        return size==dls.size && ((thatHead=dls.head)==thisHead||UncheckedList.isEqualToHelper(thatHead,thisHead,dls.tail));
      }else if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }else{
        //must be FloatArrSeq.UncheckedSubList
        final FloatArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
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
          if(list instanceof OmniList.OfFloat){
            return this.isEqualTo(size,(OmniList.OfFloat)list);
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
    @Override public OmniIterator.OfFloat descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,super.getItrNode(index,size),index);
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
    boolean uncheckedremoveLastOccurrenceBits(Node tail
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(tail.val)){
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
          if(bits==Float.floatToRawIntBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(Node tail
    ){
      {
        if(0==(tail.val)){
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
          if(0==(tail.val)){
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
    boolean uncheckedremoveLastOccurrenceNaN(Node tail
    ){
      {
        if(Float.isNaN(tail.val)){
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
          if(Float.isNaN(tail.val)){
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
    boolean uncheckedremoveValBits(Node head
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
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
          if(bits==Float.floatToRawIntBits(head.val)){
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
    boolean uncheckedremoveVal0(Node head
    ){
      {
        if(0==(head.val)){
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
          if(0==(head.val)){
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
    boolean uncheckedremoveValNaN(Node head
    ){
      {
        if(Float.isNaN(head.val)){
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
          if(Float.isNaN(head.val)){
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
    @Override public float pollFloat(){
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
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
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
      return Float.NaN;
    }
    @Override public Float poll(){
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
    @Override public Float pollLast(){
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
      Node tail;
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
    private static class DescendingItr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
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
      private void uncheckedForEachRemaining(int currIndex,FloatConsumer action){
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
      @Override public void forEachRemaining(FloatConsumer action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action::accept);
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
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
      @Override public float previousFloat(){
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
      @Override public void set(float val){
        final Node lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(float val){
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
      @Override public void forEachRemaining(FloatConsumer action){
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
      @Override public void forEachRemaining(Consumer<? super Float> action){
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
  public static class UncheckedList extends FloatDblLnkSeq implements OmniDeque.OfFloat,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends Float> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedList(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfLong that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfFloat that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfChar that){
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
    @Override public float removeLastFloat(){
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
    @Override public float popFloat(){
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
    @Override public float removeFloatAt(int index){
      final float ret;
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
    @Override public void add(int index,float val){
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
    @Override public void addLast(float val){
      Node tail;
      if((tail=this.tail)==null){
        this.head=tail=new Node(val);
      }else{
        tail.next=tail=new Node(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(float val){
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
        do{
          out.writeFloat(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node curr;
        for(this.head=curr=new Node((float)in.readFloat());--size!=0;curr=curr.next=new Node(curr,(float)in.readFloat())){}
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
      for(;TypeUtil.floatEquals(thisHead.val,thatHead.val);thisHead=thisHead.next,thatHead=thatHead.next){
        if(thisHead==thisTail){
          return true;
        }
      }
      return false;
    }
    private boolean isEqualTo(int size,OmniList.OfFloat list){
      if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        final Node thatHead,thisHead;
        return dls.size==size && ((thatHead=dls.head)==(thisHead=this.head)||isEqualToHelper(thisHead,thatHead,this.tail));
      }else if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,this.head);
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final int thatOffset;
        final FloatArrSeq.UncheckedSubList subList;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }else{
        //must be FloatArrSeq.CheckedSubList
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,thatOffset=subList.rootOffset,thatOffset+size,this.head);
      }
    }
    private boolean isEqualTo(Node thisHead,int size,OmniList.OfFloat list){
      if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
        }
        final Node thatHead;
        return dls.size==size && ((thatHead=dls.head)==thisHead || isEqualToHelper(thatHead,thisHead,dls.tail));
      }else if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        final int thatOffset;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,thatOffset=subList.rootOffset,thatOffset+size,thisHead);
      }else{
        //must be FloatArrSeq.CheckedSubList
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
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
          if(list instanceof OmniList.OfFloat){
            return this.isEqualTo(size,(OmniList.OfFloat)list);
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
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
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
            if(val==val){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveValNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveValNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValBits(Node head
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
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
          if(bits==Float.floatToRawIntBits(head.val)){
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
    boolean uncheckedremoveVal0(Node head
    ){
      {
        if(0==(head.val)){
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
          if(0==(head.val)){
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
    boolean uncheckedremoveValNaN(Node head
    ){
      {
        if(Float.isNaN(head.val)){
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
          if(Float.isNaN(head.val)){
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
    @Override public OmniIterator.OfFloat descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      return new BidirectionalItr(this,super.getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
    @Override public float getLastFloat(){
      return tail.val;
    }
    @Override public boolean offerFirst(float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(float val){
      addLast((float)val);
      return true;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public float floatElement(){
      return head.val;
    }
    @Override public boolean offer(float val){
      addLast((float)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val){
              return Node.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedsearch0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedsearch0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedsearch0(head);
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
            if(val==val){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearchNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedsearchNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedsearchNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedsearchNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedsearch0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearch0(head);
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
            if(val){
              return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveLastOccurrence0(tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveLastOccurrence0(tail);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveLastOccurrence0(tail);
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
            if(val==val){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
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
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveLastOccurrenceNaN(tail);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveLastOccurrenceNaN(tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveLastOccurrenceNaN(tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final Node tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceBits(Node tail
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(tail.val)){
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
          if(bits==Float.floatToRawIntBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(Node tail
    ){
      {
        if(0==(tail.val)){
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
          if(0==(tail.val)){
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
    boolean uncheckedremoveLastOccurrenceNaN(Node tail
    ){
      {
        if(Float.isNaN(tail.val)){
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
          if(Float.isNaN(tail.val)){
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
    @Override public Float peekFirst(){
      return peek();
    }
    @Override public Float pollFirst(){
      return poll();
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public Float remove(){
      return popFloat();
    }
    @Override public boolean offer(Float val){
      addLast((float)val);
      return true;
    }
    @Override public Float element(){
      return floatElement();
    }
    @Override public Float removeFirst(){
      return popFloat();
    }
    @Override public Float removeLast(){
      return removeLastFloat();
    }
    @Override public boolean offerFirst(Float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(Float val){
      addLast((float)val);
      return true;
    }
    @Override public void push(Float val){
      push((float)val);
    }
    @Override public void addFirst(Float val){
      push((float)val);
    }
    @Override public void addLast(Float val){
      addLast((float)val);
    }
    @Override public Float getFirst(){
      return floatElement();
    }
    @Override public Float getLast(){
      return getLastFloat();
    }
    @Override public float pollFloat(){
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
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
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
      return Float.NaN;
    }
    @Override public Float poll(){
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
    @Override public Float pollLast(){
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
      Node tail;
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
    @Override public float peekFloat(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Float.NaN;
    }
    @Override public Float peek(){
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Float peekLast(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final Node head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final Node tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public boolean removeIf(FloatPredicate filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      final Node head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(Node prev,Node tail,FloatPredicate filter){
      int numSurvivors=1;
      outer:for(Node next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(Node prev,Node curr,Node tail,FloatPredicate filter){
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
    boolean uncheckedRemoveIf(Node head,FloatPredicate filter){
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
          final Node prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
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
      @Override public float nextFloat(){
        final Node curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(Node curr,FloatConsumer action){
        Node.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        final Node curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(Node curr,FloatConsumer action){
        Node.uncheckedForEachAscending(curr,action);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final Node curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final Node curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
        final Node curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public float previousFloat(){
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
      @Override public void add(float val){
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
      @Override public void set(float val){
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
      @Override void uncheckedForEachRemaining(Node curr,FloatConsumer action){
        Node.uncheckedForEachAscending(curr,action);
        final UncheckedList parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
