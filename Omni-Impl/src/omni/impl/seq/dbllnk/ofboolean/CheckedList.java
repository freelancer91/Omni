package omni.impl.seq.dbllnk.ofboolean;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.api.OmniList;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
public class CheckedList extends AbstractSeq.Checked{
    CheckedList(){
        super();
    }
    CheckedList(Node onlyNode){
        super(onlyNode);
    }
    CheckedList(Node head,int size,Node tail){
        super(head,size,tail);
    }
    @Override
    public void clear(){
        if(size!=0){
            ++modCount;
            super.clear();
        }
    }
    @Override
    public Object clone(){
        Node newHead,newTail;
        int size;
        if((size=this.size)!=0){
            Node oldHead,oldTail;
            for(newHead=new Node((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;newTail=new Node(
                    newTail,(oldHead=oldHead.next).val)){}
        }else{
            newHead=null;
            newTail=null;
        }
        return new CheckedList(newHead,size,newTail);
    }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedForEachForward(head,tail,action::accept);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
    }
    @Override
    public void forEach(BooleanConsumer action){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedForEachForward(head,tail,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
    }
    @Override
    public boolean getBoolean(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public void put(int index,boolean val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public void replaceAll(BooleanPredicate operator){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedReplaceAll(head,tail,operator);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedReplaceAll(head,tail,operator::apply);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail);
            ++modCount;
        }
    }
    @Override
    public boolean set(int index,boolean val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        Node node;
        final var oldVal=(node=staticGetNode(this,index,size-index)).val;
        node.val=val;
        return oldVal;
    }
    @Override
    public void sort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail);
            ++modCount;
        }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,sorter::compare);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void sort(BooleanComparator sorter){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,sorter);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return super.toArray(size->{
            final int modCount=this.modCount;
            try{
                return arrConstructor.apply(size);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        });
    }
    @Override
    public Boolean poll(){
        return super.pollFirst();
    }
    @Override
    public double pollDouble(){
        return super.pollFirstDouble();
    }
    @Override
    public float pollFloat(){
        return super.pollFirstFloat();
    }
    @Override
    public long pollLong(){
        return super.pollFirstLong();
    }
    @Override
    public int pollInt(){
        return super.pollFirstInt();
    }
    @Override
    public short pollShort(){
        return super.pollFirstShort();
    }
    @Override
    public char pollChar(){
        return super.pollFirstChar();
    }
    @Override
    public byte pollByte(){
        return super.pollFirstByte();
    }
    @Override
    public boolean pollBoolean(){
        return super.pollFirstBoolean();
    }
    @Override
    public Boolean pollLast(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return null;
    }
    @Override
    public double pollLastDouble(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToDouble(tail.val);
        }
        return Double.NaN;
    }
    @Override
    public float pollLastFloat(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToFloat(tail.val);
        }
        return Float.NaN;
    }
    @Override
    public long pollLastLong(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToLong(tail.val);
        }
        return Long.MIN_VALUE;
    }
    @Override
    public int pollLastInt(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToByte(tail.val);
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public short pollLastShort(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToByte(tail.val);
        }
        return Short.MIN_VALUE;
    }
    @Override
    public char pollLastChar(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToChar(tail.val);
        }
        return Character.MIN_VALUE;
    }
    @Override
    public byte pollLastByte(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return TypeUtil.castToByte(tail.val);
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public boolean pollLastBoolean(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return false;
    }
    @Override
    public void addFirst(Boolean val){
        super.addFirst((boolean)val);
    }
    @Override
    public void addLast(Boolean val){
        super.addLast((boolean)val);
    }
    @Override
    public omni.api.OmniIterator.OfBoolean descendingIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean getFirst(){
        return super.getFirstBoolean();
    }
    @Override
    public Boolean getLast(){
        return super.getLastBoolean();
    }
    @Override
    public boolean offerFirst(boolean val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerFirst(Boolean val){
        super.addFirst((boolean)val);
        return true;
    }
    @Override
    public boolean offerLast(boolean val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerLast(Boolean val){
        super.addLast((boolean)val);
        return true;
    }
    @Override
    public Boolean removeFirst(){
        return super.removeFirstBoolean();
    }
    @Override
    public Boolean removeLast(){
        return super.removeLastBoolean();
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node tail;
        if((tail=this.tail)!=null){
            boolean v;
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
                v=false;
            }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
            }else{
                return false;
            }
            return uncheckedRemoveLastMatch(tail,v);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node tail;
        if((tail=this.tail)!=null){
            boolean v;
            switch(Float.floatToRawIntBits(val)){
            default:
                return false;
            case 0:
            case Integer.MIN_VALUE:
                v=false;
                break;
            case TypeUtil.FLT_TRUE_BITS:
                v=true;
            }
            return uncheckedRemoveLastMatch(tail,v);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node tail;
        if((tail=this.tail)!=null){
            boolean v;
            switch(val){
            default:
                return false;
            case 0:
                v=false;
                break;
            case 1:
                v=true;
            }
            return uncheckedRemoveLastMatch(tail,v);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node tail;
        if((tail=this.tail)!=null){
            boolean v;
            if(val==0){
                v=false;
            }else if(val==1){
                v=true;
            }else{
                return false;
            }
            return uncheckedRemoveLastMatch(tail,v);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        final Node tail;
        return (tail=this.tail)!=null&&val instanceof Boolean&&uncheckedRemoveLastMatch(tail,(boolean)val);
    }
    private boolean uncheckedRemoveLastMatch(Node curr,boolean val){
        final var head=this.head;
        if(curr.val==val){
            if(curr==head){
                staticInit(this,null);
            }else{
                staticEraseTail(this,curr);
            }
        }else{
            Node next;
            do{
                if(curr==head){
                    return false;
                }
            }while((curr=(next=curr).prev).val^val);
            if(curr==head){
                staticSetHead(this,next);
            }else{
                joinNodes(curr.prev,next);
            }
        }
        ++modCount;
        --size;
        return true;
    }
    @Override
    public boolean booleanElement(){
        return super.getFirstBoolean();
    }
    @Override
    public Boolean element(){
        return super.getFirstBoolean();
    }
    @Override
    public boolean offer(boolean val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offer(Boolean val){
        super.addLast((boolean)val);
        return true;
    }
    @Override
    public Boolean remove(){
        return super.removeFirstBoolean();
    }
    @Override
    public boolean removeBoolean(){
        return super.removeFirstBoolean();
    }
    @Override
    public boolean add(boolean val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean add(Boolean val){
        super.addLast((boolean)val);
        return true;
    }
    @Override
    public omni.api.OmniIterator.OfBoolean iterator(){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean pop(){
        return super.removeFirstBoolean();
    }
    @Override
    public boolean popBoolean(){
        return super.removeFirstBoolean();
    }
    @Override
    public void push(boolean val){
        super.addFirst(val);
    }
    @Override
    public void push(Boolean val){
        super.addFirst((boolean)val);
    }
    @Override
    public omni.api.OmniListIterator.OfBoolean listIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfBoolean listIterator(int index){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node curr,boolean val){
        final var tail=this.tail;
        if(val==curr.val){
            if(curr==tail){
                staticInit(this,null);
            }else{
                staticEraseHead(this,curr);
            }
        }else{
            Node prev;
            do{
                if(curr==tail){
                    return false;
                }
            }while((curr=(prev=curr).next).val^val);
            if(curr==tail){
                staticSetTail(this,prev);
            }else{
                joinNodes(prev,curr.next);
            }
        }
        ++modCount;
        --size;
        return true;
    }
    @Override
    public boolean removeBooleanAt(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        ++modCount;
        Node node;
        if(--size!=0){
            if(index==0){
                staticEraseHead(this,node=head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    staticEraseTail(this,node=tail);
                }else{
                    node=staticExtractNode(this,index,tailDist);
                }
            }
        }else{
            node=head;
            staticInit(this,null);
        }
        this.size=size;
        return node.val;
    }
    @Override
    public void add(int index,boolean val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        ++modCount;
        if(size!=0){
            if(index==0){
                head=new Node(val,head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    tail=new Node(tail,val);
                }else{
                    staticInsertNode(this,index,val,tailDist);
                }
            }
        }else{
            staticInit(this,new Node(val));
        }
        this.size=size+1;
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
}
