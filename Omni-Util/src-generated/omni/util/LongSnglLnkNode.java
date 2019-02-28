package omni.util;
public class LongSnglLnkNode implements Comparable<LongSnglLnkNode>
{
  public transient long val;
  public transient LongSnglLnkNode next;
  public LongSnglLnkNode(long val)
  {
    this.val=val;
  }
  public LongSnglLnkNode(long val,LongSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  @Override
  public String toString()
  {
    return ToStringUtil.getString(val);
  }
  @Override
  public boolean equals(Object val)
  {
    if(val==this){return true;}
    if(val instanceof LongSnglLnkNode)
    {
      return this.val==((LongSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return Long.hashCode(this.val);
  }
  @Override
  public int compareTo(LongSnglLnkNode that)
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
