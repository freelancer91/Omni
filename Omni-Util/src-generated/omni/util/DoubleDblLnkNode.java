package omni.util;
public class DoubleDblLnkNode implements Comparable<DoubleDblLnkNode>
{
  public transient DoubleDblLnkNode prev;
  public transient double val;
  public transient DoubleDblLnkNode next;
  public DoubleDblLnkNode(double val)
  {
    this.val=val;
  }
  public DoubleDblLnkNode(DoubleDblLnkNode prev,double val)
  {
    this.prev=prev;
    this.val=val;
  }
  public DoubleDblLnkNode(double val,DoubleDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public DoubleDblLnkNode(DoubleDblLnkNode prev,double val,DoubleDblLnkNode next)
  {
    this.prev=prev;
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
    if(val instanceof DoubleDblLnkNode)
    {
      return TypeUtil.doubleEquals(this.val,((DoubleDblLnkNode)val).val);
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return HashUtil.hashDouble(this.val);
  }
  @Override
  public int compareTo(DoubleDblLnkNode that)
  {
    if(this!=that)
    {
      return Double.compare(this.val,that.val);
    }
    return 0;
  }
}
