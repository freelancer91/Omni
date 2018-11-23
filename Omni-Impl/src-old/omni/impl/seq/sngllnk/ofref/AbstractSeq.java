package omni.impl.seq.sngllnk.ofref;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.util.OmniArray;
import omni.util.OmniPred;
abstract class AbstractSeq<E>extends omni.impl.seq.AbstractSeq{
    public void push(E val){
        ++this.size;
        this.head=new Node<>(val,this.head);
    }
    public E peek(){
        Node<E> head;
        if((head=this.head)!=null){
            return head.val;
        }
        return null;
    }
    public boolean remove(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            if(val!=null){
                return uncheckedRemoveFirstNonNull(head,val);
            }
            return uncheckedRemoveFirstMatch(head,Objects::isNull);
        }
        return false;
    }
    static <E> boolean uncheckedAnyMatches(Node<E> curr,Predicate<Object> pred){
        do{
            if(pred.test(curr.val)){
                return true;
            }
        }while((curr=curr.next)!=null);
        return false;
    }
    static <SRC extends DST,DST> void uncheckedCopyIntoArray(Node<SRC> src,DST[] dst,int dstOffset){
        for(;;++dstOffset){
            dst[dstOffset]=src.val;
            if((src=src.next)==null){
                return;
            }
        }
    }
    static <E> void uncheckedForEach(Node<E> curr,Consumer<? super E> action){
        do{
            action.accept(curr.val);
        }while((curr=curr.next)!=null);
    }
    static int uncheckedHashCode(Node<?> curr){
        int hash=31+Objects.hashCode(curr.val);
        while((curr=curr.next)!=null){
            hash=hash*31+Objects.hashCode(curr.val);
        }
        return hash;
    }
    static <E> int uncheckedSearch(Node<E> curr,Predicate<Object> pred){
        int index=1;
        while(!pred.test(curr.val)){
            if((curr=curr.next)==null){
                return -1;
            }
            ++index;
        }
        return index;
    }
    static void uncheckedToString(Node<?> curr,StringBuilder builder){
        for(builder.append(curr.val);(curr=curr.next)!=null;builder.append(',').append(' ').append(curr.val)){}
    }
    transient Node<E> head;
    AbstractSeq(){
    }
    AbstractSeq(int size,Node<E> head){
        super(size);
    }
    public int search(boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(char val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Character val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(int val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Integer val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public boolean contains(boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(char val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Character val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(int val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Integer val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean contains(Short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public void forEach(Consumer<? super E> action){
        Node<E> head;
        if((head=this.head)!=null){
            uncheckedForEach(head,action);
        }
    }

    public boolean removeIf(Predicate<? super E> filter){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    public boolean removeVal(boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(char val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Character val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(int val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Integer val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public boolean removeVal(Short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    public Object[] toArray(){
        int size;
        if((size=this.size)!=0){
            Object[] dst;
            uncheckedCopyIntoArray(head,dst=new Object[size],0);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
    }
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final T[] dst=arrConstructor.apply(size=this.size);
        if(size!=0){
            uncheckedCopyIntoArray(head,dst,0);
        }
        return dst;
    }
    public <T> T[] toArray(T[] dst){
        int size;
        if((size=this.size)!=0){
            uncheckedCopyIntoArray(head,dst=OmniArray.uncheckedArrResize(size,dst),0);
        }else if(dst.length!=0){
            dst[0]=null;
        }
        return dst;
    }
    abstract boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred);
    abstract boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull);
    abstract boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter);
}
