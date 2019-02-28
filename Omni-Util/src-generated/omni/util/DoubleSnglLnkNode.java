package omni.util;
public class DoubleSnglLnkNode implements Comparable<DoubleSnglLnkNode>
{
  public transient double val;
  public transient DoubleSnglLnkNode next;
  public DoubleSnglLnkNode(double val)
  {
    this.val=val;
  }
  public DoubleSnglLnkNode(double val,DoubleSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  @Override
  public String toString()
  {
    return Double.toString(val);
  }
  @Override
  public boolean equals(Object val)
  {
    if(val==this){return true;}
    if(val instanceof DoubleSnglLnkNode)
    {
      return TypeUtil.doubleEquals(this.val,((DoubleSnglLnkNode)val).val);
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return HashUtil.hashDouble(this.val);
  }
  @Override
  public int compareTo(DoubleSnglLnkNode that)
  {
    if(this!=that)
    {
      return Double.compare(this.val,that.val);
    }
    return 0;
  }
}
