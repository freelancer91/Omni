package omni.util;
public class IntSnglLnkNode implements Comparable<IntSnglLnkNode>
{
  public transient int val;
  public transient IntSnglLnkNode next;
  public IntSnglLnkNode(int val)
  {
    this.val=val;
  }
  public IntSnglLnkNode(int val,IntSnglLnkNode next)
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
    if(val instanceof IntSnglLnkNode)
    {
      return this.val==((IntSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val;
  }
  @Override
  public int compareTo(IntSnglLnkNode that)
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
