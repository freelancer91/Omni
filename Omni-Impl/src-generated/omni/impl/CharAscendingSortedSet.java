package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.function.CharComparator;
import java.util.function.IntUnaryOperator;
import omni.util.OmniArray;
public class CharAscendingSortedSet extends CharUntetheredArrSeqSet implements Cloneable
{
  public CharAscendingSortedSet(){
    super();
  }
  public CharAscendingSortedSet(int head,char[] arr,int tail){
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
      return new CharAscendingSortedSet(0,dst,cloneTail);
    }
    return new CharAscendingSortedSet();
  }
  @Override public CharComparator comparator(){
    return Character::compare;
  }
  @Override boolean uncheckedAdd0(int tail){
    final char[] arr;
    final int head;
    if(((arr=this.arr)[head=this.head])!=0){
      super.insertAtHead(arr,(char)0,head,tail);
      return true;
    }
    return false;
  }
  @Override boolean uncheckedAdd1(int tail){
    int head;
    char[] arr;
    final char bottomVal;
    if((bottomVal=(arr=this.arr)[head=this.head])!=1){
      if(bottomVal==0){
        //add just after head
        int newHead;
        switch(Integer.signum(tail-(newHead=head-1))){
          case 0:
            //fragmented must grow
            final char[] tmp;
            int arrLength;
            ArrCopy.uncheckedCopy(arr,0,tmp=new char[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
            ArrCopy.semicheckedCopy(arr,head,tmp,head=tail-(arrLength-=(head+1)),arrLength);
            --head;
            newHead=head-1;
            this.arr=arr=tmp;
            break;
          default:
            //nonfragmented
            if(newHead==-1){
              if(tail==(newHead=(tail=arr.length)-1)){
                //must grow
                this.tail=(head=OmniArray.growBy50Pct(tail))-1;
                ArrCopy.semicheckedCopy(arr,0,arr=new char[head],head-=newHead,newHead);
                --head;
                this.arr=arr;
              }
              newHead=head-1;
            }
          case -1:
            //fragmented
        }
        arr[head]=1;
        arr[newHead]=0;
        this.head=newHead;
      }else{
        super.insertAtHead(arr,(char)1,head,tail);
      }
      return true;
    }
    return false;
  }
  @Override boolean uncheckedcontains(int tail,boolean key){
    final var arr=this.arr;
    int head=this.head;
    if(key){
      switch(arr[head]){
        case 0:
          return head!=tail && arr[(++head==arr.length)?0:head]==1;
        case 1:
          return true;
        default:
      }
    }
    return arr[head]==0;
  }
  @Override boolean uncheckedremoveVal(int tail,boolean key){
    final var arr=this.arr;
    int head=this.head;
    if(key){
      switch(arr[head]){
        case 0:
          if(head!=tail && arr[(++head==arr.length)?(head=0):head]==1){
            arr[head]=0;
            this.head=head;
            return true;
          }
        default:
          return false;
        case 1:
      }
    }else if(arr[head]!=0){
      return false;
    }
    if(head==tail){
      this.tail=-1;
    }else{
      if(++head==arr.length){
        head=0;
      }
      this.head=head;
    }
    return true;
  }
  private static IntUnaryOperator getQueryComparator(int key){
    return (k)->Integer.signum(key-k);
  }
  @Override boolean uncheckedcontains(int tail,int key){
    switch(key){
      default:
        return super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
      case 1:
        {
          final char[] arr;
          int head;
          switch((arr=this.arr)[head=this.head]){
            case 0:
              return head!=tail && arr[(++head==arr.length)?0:head]==1;
            case 1:
              return true;
            default:
          }
          return false;
        }
      case 0:
        return arr[head]==0;
    }
  }
  @Override boolean uncheckedremoveVal(int tail,int key){
    final char[] arr;
    int head;
    switch(key){
      default:
        return super.uncheckedRemoveMatch(tail,getQueryComparator(key));
      case 1:
        switch((arr=this.arr)[head=this.head]){
          case 0:
            if(head!=tail && arr[(++head==arr.length)?(head=0):head]==1){
              arr[head]=0;
              this.head=head;
              return true;
            }
          default:
            return false;
          case 1:
        }
        break;
      case 0:
        if((arr=this.arr)[head=this.head]!=0){
          return false;
        }
    }
    if(head==tail){
      this.tail=-1;
    }else{
      if(++head==arr.length){
        head=0;
      }
      this.head=head;
    }
    return true;
  }
  @Override int insertionCompare(char key1,char key2){
    return Integer.signum(key1-key2);
  }
}
