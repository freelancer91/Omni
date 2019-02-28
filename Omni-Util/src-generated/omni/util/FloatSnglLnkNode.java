package omni.util;
public class FloatSnglLnkNode implements Comparable<FloatSnglLnkNode>
{
  public transient float val;
  public transient FloatSnglLnkNode next;
  public FloatSnglLnkNode(float val)
  {
    this.val=val;
  }
  public FloatSnglLnkNode(float val,FloatSnglLnkNode next)
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
    if(val instanceof FloatSnglLnkNode)
    {
      return TypeUtil.floatEquals(this.val,((FloatSnglLnkNode)val).val);
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return HashUtil.hashFloat(this.val);
  }
  @Override
  public int compareTo(FloatSnglLnkNode that)
  {
    if(this!=that)
    {
      return Float.compare(this.val,that.val);
    }
    return 0;
  }
}
