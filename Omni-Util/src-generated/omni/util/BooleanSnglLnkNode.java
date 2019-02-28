package omni.util;
public class BooleanSnglLnkNode implements Comparable<BooleanSnglLnkNode>
{
  public transient boolean val;
  public transient BooleanSnglLnkNode next;
  public BooleanSnglLnkNode(boolean val)
  {
    this.val=val;
  }
  public BooleanSnglLnkNode(boolean val,BooleanSnglLnkNode next)
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
    if(val instanceof BooleanSnglLnkNode)
    {
      return this.val==((BooleanSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val?1231:1237;
  }
  @Override
  public int compareTo(BooleanSnglLnkNode that)
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
