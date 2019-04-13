package omni.impl;
public class FloatDblLnkNode implements Comparable<FloatDblLnkNode>
{
  public transient FloatDblLnkNode prev;
  public transient float val;
  public transient FloatDblLnkNode next;
  public FloatDblLnkNode(float val)
  {
    this.val=val;
  }
  public FloatDblLnkNode(FloatDblLnkNode prev,float val)
  {
    this.prev=prev;
    this.val=val;
  }
  public FloatDblLnkNode(float val,FloatDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public FloatDblLnkNode(FloatDblLnkNode prev,float val,FloatDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  @Override
  public int compareTo(FloatDblLnkNode that)
  {
    if(this!=that)
    {
      return Float.compare(this.val,that.val);
    }
    return 0;
  }
}
