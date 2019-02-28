package omni.util;
import java.util.Objects;
public class RefDblLnkNode<E> implements Comparable<RefDblLnkNode<E>>
{
  public transient RefDblLnkNode<E> prev;
  public transient E val;
  public transient RefDblLnkNode<E> next;
  public RefDblLnkNode(E val)
  {
    this.val=val;
  }
  public RefDblLnkNode(RefDblLnkNode<E> prev,E val)
  {
    this.prev=prev;
    this.val=val;
  }
  public RefDblLnkNode(E val,RefDblLnkNode<E> next)
  {
    this.val=val;
    this.next=next;
  }
  public RefDblLnkNode(RefDblLnkNode<E> prev,E val,RefDblLnkNode<E> next)
  {
    this.prev=prev;
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
    if(val instanceof RefDblLnkNode)
    {
      return Objects.equals(this.val,((RefDblLnkNode<?>)val).val);
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
  public int compareTo(RefDblLnkNode<E> that)
  {
    if(this!=that)
    {
      return ((Comparable<E>)this.val).compareTo(that.val);
    }
    return 0;
  }
}
