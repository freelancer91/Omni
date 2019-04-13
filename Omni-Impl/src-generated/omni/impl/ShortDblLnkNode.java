package omni.impl;
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
  public int compareTo(ShortDblLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
