package omni.impl;
public class CharDblLnkNode implements Comparable<CharDblLnkNode>
{
  public transient CharDblLnkNode prev;
  public transient char val;
  public transient CharDblLnkNode next;
  public CharDblLnkNode(char val)
  {
    this.val=val;
  }
  public CharDblLnkNode(CharDblLnkNode prev,char val)
  {
    this.prev=prev;
    this.val=val;
  }
  public CharDblLnkNode(char val,CharDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public CharDblLnkNode(CharDblLnkNode prev,char val,CharDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  @Override
  public int compareTo(CharDblLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
