package omni.util;
public class ShortSnglLnkNode implements Comparable<ShortSnglLnkNode>
{
  public transient short val;
  public transient ShortSnglLnkNode next;
  public ShortSnglLnkNode(short val)
  {
    this.val=val;
  }
  public ShortSnglLnkNode(short val,ShortSnglLnkNode next)
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
    if(val instanceof ShortSnglLnkNode)
    {
      return this.val==((ShortSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val;
  }
  @Override
  public int compareTo(ShortSnglLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
