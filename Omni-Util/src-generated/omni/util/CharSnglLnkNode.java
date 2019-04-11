package omni.util;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
public class CharSnglLnkNode implements Comparable<CharSnglLnkNode>
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
  //public static  int retainSurvivors(CharSnglLnkNode prev, final CharPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  //public static  int retainTrailingSurvivors(CharSnglLnkNode prev,CharSnglLnkNode curr,final CharPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  static  CharSnglLnkNode uncheckedSkip(CharSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  CharSnglLnkNode skip(CharSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(CharSnglLnkNode prev, final CharPredicate filter){
    int numSurvivors=1;
    outer:for(CharSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
      if(filter.test(next.val)){
        do{
          if((next=next.next)==null){
            prev.next=null;
            break outer;
          }
        }while(filter.test(next.val));
        prev.next=next;
      }
    }
    return numSurvivors;
  }
  public static  int retainTrailingSurvivors(CharSnglLnkNode prev,CharSnglLnkNode curr,final CharPredicate filter){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }
      if(!filter.test(curr.val)){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }
        while(!filter.test(curr.val));
      }
    }
    return numSurvivors;
  }
  public static  int uncheckedHashCode(CharSnglLnkNode curr){
    int hash=31+(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+(curr.val)){}
    return hash;
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
  @Override
  public int compareTo(CharSnglLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
