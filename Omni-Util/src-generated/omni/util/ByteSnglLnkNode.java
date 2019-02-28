package omni.util;
public class ByteSnglLnkNode implements Comparable<ByteSnglLnkNode>
{
  public transient byte val;
  public transient ByteSnglLnkNode next;
  public ByteSnglLnkNode(byte val)
  {
    this.val=val;
  }
  public ByteSnglLnkNode(byte val,ByteSnglLnkNode next)
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
    if(val instanceof ByteSnglLnkNode)
    {
      return this.val==((ByteSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val;
  }
  @Override
  public int compareTo(ByteSnglLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
