package omni.impl;
public class IntDblLnkNode implements Comparable<IntDblLnkNode>
{
  public transient IntDblLnkNode prev;
  public transient int val;
  public transient IntDblLnkNode next;
  public IntDblLnkNode(int val)
  {
    this.val=val;
  }
  public IntDblLnkNode(IntDblLnkNode prev,int val)
  {
    this.prev=prev;
    this.val=val;
  }
  public IntDblLnkNode(int val,IntDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public IntDblLnkNode(IntDblLnkNode prev,int val,IntDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  @Override
  public int compareTo(IntDblLnkNode that)
  {
    if(this!=that)
    {
      int thisVal,thatVal;
      if((thisVal=this.val)<(thatVal=that.val))
      {
        return -1;
      }
      if(thisVal>thatVal)
      {
        return 1;
      }
    }
    return 0;
  }
}
