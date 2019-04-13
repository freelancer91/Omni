package omni.impl;
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
