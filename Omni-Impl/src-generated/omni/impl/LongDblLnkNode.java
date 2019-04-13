package omni.impl;
public class LongDblLnkNode implements Comparable<LongDblLnkNode>
{
  public transient LongDblLnkNode prev;
  public transient long val;
  public transient LongDblLnkNode next;
  public LongDblLnkNode(long val)
  {
    this.val=val;
  }
  public LongDblLnkNode(LongDblLnkNode prev,long val)
  {
    this.prev=prev;
    this.val=val;
  }
  public LongDblLnkNode(long val,LongDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public LongDblLnkNode(LongDblLnkNode prev,long val,LongDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  @Override
  public int compareTo(LongDblLnkNode that)
  {
    if(this!=that)
    {
      long thisVal,thatVal;
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
