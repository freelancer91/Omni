package omni.util;
import java.util.Objects;
public class RefSnglLnkNode<E> implements Comparable<RefSnglLnkNode<E>>
{
  public transient E val;
  public transient RefSnglLnkNode<E> next;
  public RefSnglLnkNode(E val)
  {
    this.val=val;
  }
  public RefSnglLnkNode(E val,RefSnglLnkNode<E> next)
  {
    this.val=val;
    this.next=next;
  }
  @Override
  public String toString()
  {
    return Objects.toString(val);
  }
  @Override
  public boolean equals(Object val)
  {
    if(val==this){return true;}
    if(val instanceof RefSnglLnkNode)
    {
      return Objects.equals(this.val,((RefSnglLnkNode<?>)val).val);
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.val);
  }
  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(RefSnglLnkNode<E> that)
  {
    if(this!=that)
    {
      return ((Comparable<E>)this.val).compareTo(that.val);
    }
    return 0;
  }
}
