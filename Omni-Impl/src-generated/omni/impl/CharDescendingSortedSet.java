package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.function.CharComparator;
import java.util.function.IntUnaryOperator;
import omni.util.OmniArray;
public class CharDescendingSortedSet extends CharUntetheredArrSeqSet implements Cloneable
{
  public CharDescendingSortedSet(){
    super();
  }
  public CharDescendingSortedSet(int head,char[] arr,int tail){
    super(head,arr,tail);
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
      return new CharDescendingSortedSet(0,dst,cloneTail);
    }
    return new CharDescendingSortedSet();
  }
  @Override public CharComparator comparator(){
    return CharComparator::descendingCompare;
  }
  @Override int insertionCompare(char key1,char key2){
    return Integer.signum(key2-key1);
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
}
