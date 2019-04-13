package omni.impl;
public class ByteDblLnkNode implements Comparable<ByteDblLnkNode>
{
  public transient ByteDblLnkNode prev;
  public transient byte val;
  public transient ByteDblLnkNode next;
  public ByteDblLnkNode(byte val)
  {
    this.val=val;
  }
  public ByteDblLnkNode(ByteDblLnkNode prev,byte val)
  {
    this.prev=prev;
    this.val=val;
  }
  public ByteDblLnkNode(byte val,ByteDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public ByteDblLnkNode(ByteDblLnkNode prev,byte val,ByteDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  @Override
  public int compareTo(ByteDblLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
