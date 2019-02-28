package omni.util;
public class BooleanDblLnkNode implements Comparable<BooleanDblLnkNode>
{
  public transient BooleanDblLnkNode prev;
  public transient boolean val;
  public transient BooleanDblLnkNode next;
  public BooleanDblLnkNode(boolean val)
  {
    this.val=val;
  }
  public BooleanDblLnkNode(BooleanDblLnkNode prev,boolean val)
  {
    this.prev=prev;
    this.val=val;
  }
  public BooleanDblLnkNode(boolean val,BooleanDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public BooleanDblLnkNode(BooleanDblLnkNode prev,boolean val,BooleanDblLnkNode next)
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
    if(val instanceof BooleanDblLnkNode)
    {
      return this.val==((BooleanDblLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val?1231:1237;
  }
  @Override
  public int compareTo(BooleanDblLnkNode that)
  {
    if(this!=that)
    {
      if(this.val)
      {
        if(!that.val)
        {
          return 1;
        }
      }else if(that.val)
      {
        return -1;
      }
    }
    return 0;
  }
}
