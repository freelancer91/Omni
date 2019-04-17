package omni.impl;
import omni.function.CharConsumer;
public class CharSnglLnkNode
{
  public transient char val;
  public transient CharSnglLnkNode next;
  public CharSnglLnkNode(char val)
  {
    this.val=val;
  }
  public CharSnglLnkNode(char val,CharSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  void uncheckedToString(CharSnglLnkNode curr,char[] buffer){
    int bufferOffset=1;
    for(;;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',++bufferOffset){
      buffer[bufferOffset]=curr.val;
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(CharSnglLnkNode curr){
    int hash=31+(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(CharSnglLnkNode curr,CharSnglLnkNode tail,CharConsumer action){
    for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
  }
  public static  void uncheckedForEach(CharSnglLnkNode curr,CharConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontains (CharSnglLnkNode curr
  ,int val
  ){
    for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch (CharSnglLnkNode curr
  ,int val
  ){
    int index=1;
    for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,char[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,Character[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(double)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(float)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(long)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(CharSnglLnkNode curr,int[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(int)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
}
