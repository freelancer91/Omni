package omni.util;
public class ShortDblLnkNode implements Comparable<ShortDblLnkNode>
{
  public transient ShortDblLnkNode prev;
  public transient short val;
  public transient ShortDblLnkNode next;
  public ShortDblLnkNode(short val)
  {
    this.val=val;
  }
  public ShortDblLnkNode(ShortDblLnkNode prev,short val)
  {
    this.prev=prev;
    this.val=val;
  }
  public ShortDblLnkNode(short val,ShortDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public ShortDblLnkNode(ShortDblLnkNode prev,short val,ShortDblLnkNode next)
  {
    this.prev=prev;
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
    if(val instanceof ShortDblLnkNode)
    {
      return this.val==((ShortDblLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val;
  }
  @Override
  public int compareTo(ShortDblLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
