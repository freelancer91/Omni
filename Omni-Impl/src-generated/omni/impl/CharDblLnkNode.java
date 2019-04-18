package omni.impl;
import omni.function.CharConsumer;
import omni.function.CharUnaryOperator;
public class CharDblLnkNode implements Comparable<CharDblLnkNode>
{
  public transient CharDblLnkNode prev;
  public transient char val;
  public transient CharDblLnkNode next;
  public CharDblLnkNode(char val)
  {
    this.val=val;
  }
  public CharDblLnkNode(CharDblLnkNode prev,char val)
  {
    this.prev=prev;
    this.val=val;
  }
  public CharDblLnkNode(char val,CharDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public CharDblLnkNode(CharDblLnkNode prev,char val,CharDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  public static  void uncheckedForEachAscending(CharDblLnkNode node,int length,CharConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(CharDblLnkNode node,int length,CharConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(CharDblLnkNode node,int length,CharUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsChar(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void eraseNode(CharDblLnkNode node){
    CharDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  CharDblLnkNode uncheckedIterateAscending(CharDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static  CharDblLnkNode uncheckedIterateDescending(CharDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (CharDblLnkNode head,CharDblLnkNode tail
  ,int val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (CharDblLnkNode head,CharDblLnkNode tail
  ,int val
  ){
    int index=1;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (CharDblLnkNode head,CharDblLnkNode tail
  ,int val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,CharDblLnkNode tail
  ,int val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(char[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Character[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(float)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(long[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(long)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(int[] dst,CharDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(int)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @Override
  public int compareTo(CharDblLnkNode that){
    if(this!=that){
      return this.val-that.val;
    }
    return 0;
  }
}
