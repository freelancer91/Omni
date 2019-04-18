package omni.impl.seq;
import omni.api.OmniList;
import java.io.Serializable;
import omni.impl.CharDblLnkNode;
import java.util.function.Consumer;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import java.util.function.UnaryOperator;
import omni.function.CharUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.CharComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
public abstract class CharDblLnkSeq implements
   CharSubListDefault
  ,Cloneable,Serializable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient CharDblLnkNode head;
  transient CharDblLnkNode tail;
  private  CharDblLnkSeq(){
  }
  private CharDblLnkSeq(CharDblLnkNode head,int size,CharDblLnkNode tail){
    this.head=head;
    this.size=size;
    this.tail=tail;
  }
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(char val){
    CharDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new CharDblLnkNode(val);
    }else{
      tail.next=tail=new CharDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(char val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,char val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new CharDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        CharDblLnkNode before;
        (before=tail.prev).next=before=new CharDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      CharDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new CharDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new CharDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        CharDblLnkNode after;
        (after=head.next).prev=after=new CharDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private CharDblLnkNode getNode(int index,int size)
  {
    int tailDist;
    if((tailDist=size-index)<index){
      for(var tail=this.tail;;tail=tail.prev){
        if(--tailDist==0){
          return tail;
        }
      }
    }else{
      for(var head=this.head;;--index,head=head.next){
        if(index==0){
          return head;
        }
      }
    }
  }
  @Override public char set(int index,char val){
    CharDblLnkNode tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,char val){
    getNode(index,size).val=val;
  }
  @Override public char getChar(int index){
    return getNode(index,size).val;
  }
  @Override public char removeCharAt(int index){
    final char ret;
    int tailDist;
    if((tailDist=--this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==0){
        ret=tail.val;
        if(index==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
      }else{
        ret=(tail=CharDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        CharDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=CharDblLnkNode.uncheckedIterateAscending(head,index)).val;
        CharDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(CharConsumer action)
  {
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedForEachAscending(head,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action)
  {
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedForEachAscending(head,size,action::accept);
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
  @Override public boolean contains(boolean val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if((v=(char)val)==val)
          {
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedcontains(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    if(val==(char)val)
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if((v=(char)val)==val)
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return uncheckedremoveVal(head,tail,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
            return uncheckedremoveVal(head,tail,i);
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    if(val>=0)
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          final char v;
          if((v=(char)val)==val)
          {
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int indexOf(byte val){
    if(val>=0)
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
        {
          return CharDblLnkNode.uncheckedindexOf(head,tail,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
        final CharDblLnkNode tail;
        if((tail=this.tail)!=null)
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
          if((v=(char)val)==val)
          {
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
          //TODO a pattern-matching switch statement would be great here
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
  @Override public int lastIndexOf(byte val){
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
  boolean uncheckedremoveVal(CharDblLnkNode head,CharDblLnkNode tail
  ,int val
  ){
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
    while(head!=tail){
      if(val==((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          CharDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public abstract Object clone();
  @Override public void replaceAll(CharUnaryOperator operator){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Character> operator){
    final CharDblLnkNode head;
    if((head=this.head)!=null){
      CharDblLnkNode.uncheckedReplaceAll(head,size,operator::apply);
    }
  }
  @Override public boolean removeIf(CharPredicate filter)
  {
    final CharDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Character> filter)
  {
    final CharDblLnkNode head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter::test);
  }
  boolean uncheckedRemoveIf(CharDblLnkNode head,int size,CharPredicate filter)
  {
    //TODO
    return false;
  }
  @Override public void sort(CharComparator sorter){
    //TODO
  }
  @Override public void sort(Comparator<? super Character> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(CharComparator sorter){
    //TODO
  }
  @Override public String toString(){
    //TODO
    return null;
  }
  @Override public int hashCode(){
    //TODO
    return 0;
  }
  //TODO serialization methods
  public static class UncheckedList extends CharDblLnkSeq implements OmniDeque.OfChar{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(CharDblLnkNode head,int size,CharDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0)
      {
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
    @Override public OmniIterator.OfChar descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfChar iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfChar listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      //TODO
      return null;
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
    @Override public void addFirst(char val){
      push((char)val);
    }
    @Override public char removeFirstChar(){
      return popChar();
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
    @Override public char removeLastChar(){
      CharDblLnkNode tail;
      final var ret=(tail=this.tail).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (tail=tail.prev).next=null;
        this.tail=tail;
      }
      return ret;
    }
    @Override public char popChar(){
      CharDblLnkNode head;
      final var ret=(head=this.head).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (head=head.next).prev=null;
        this.head=head;
      }
      return ret;
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
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,tail,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(char)val)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if((v=(char)val)==val)
            {
              return CharDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final char v;
            if(val==(v=(char)val))
            {
              return CharDblLnkNode.uncheckedsearch(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
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
              return CharDblLnkNode.uncheckedsearch(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      if(val>=0)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      if(val>=0)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return CharDblLnkNode.uncheckedsearch(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,TypeUtil.castToChar(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            if((v=(char)val)==val)
            {
              return uncheckedremoveLastOccurrence(head,tail,v);
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
              return uncheckedremoveLastOccurrence(head,tail,v);
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
              return uncheckedremoveLastOccurrence(head,tail,v);
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
            //TODO a pattern-matching switch statement would be great here
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
              return uncheckedremoveLastOccurrence(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      if(val>=0)
      {
        {
          final CharDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
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
            return uncheckedremoveLastOccurrence(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(CharDblLnkNode head,CharDblLnkNode tail
    ,int val
    ){
      if(val==(tail.val)){
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
        return true;
      }
      while(head!=tail){
        if(val==((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            CharDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
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
  }
}
