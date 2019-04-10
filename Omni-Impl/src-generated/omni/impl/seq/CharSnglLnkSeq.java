package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import java.util.function.Consumer;
import omni.function.CharConsumer;
import omni.util.TypeUtil;
public abstract class CharSnglLnkSeq implements OmniCollection.OfChar,Cloneable{
  static class Node{
    transient char val;
    transient Node next;
    Node(char val){
      this.val=val;
    }
    Node(char val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+(curr.val);
      }
      return hash;
    }
    private void uncheckedToString(char[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',++bufferOffset){
        buffer[bufferOffset]=curr.val;
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(CharConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(char[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Object[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Character[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(double)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(float)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(long[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(long)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(int[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(int)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  transient int size;
  transient Node head;
  private CharSnglLnkSeq(){
  }
  private CharSnglLnkSeq(int size,Node head){
    this.size=size;
    this.head=head;
  }
  @Override public abstract Object clone();
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final Node head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final char[] buffer;
      head.uncheckedToString(buffer=new char[size=this.size*3]);
      buffer[0]='[';
      buffer[size-1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  @Override public void forEach(CharConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  public void push(char val){
    this.head=new Node(val,this.head);
    ++this.size;
  }
  @Override public boolean add(char val){
    push(val);
    return true;
  }
  @Override
  public boolean add(Character val)
  {
    push((char)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((char)TypeUtil.castToChar(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public char[] toCharArray(){
    final Node head;
    if((head=this.head)!=null){
      final char[] dst;
      head.uncheckedCopyInto(dst=new char[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public Character[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Character[] dst;
      head.uncheckedCopyInto(dst=new Character[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      head.uncheckedCopyInto(dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      head.uncheckedCopyInto(dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      head.uncheckedCopyInto(dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
