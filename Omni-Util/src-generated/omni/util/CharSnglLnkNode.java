package omni.util;
public class CharSnglLnkNode implements Comparable<CharSnglLnkNode>
{
  public transient char val;
  public transient CharSnglLnkNode next;
  public CharSnglLnkNode(char val)
  {
    this.val=val;
  }
  public CharSnglLnkNode(char val,CharSnglLnkNode next)
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
    if(val instanceof CharSnglLnkNode)
    {
      return this.val==((CharSnglLnkNode)val).val;
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return val;
  }
  @Override
  public int compareTo(CharSnglLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
