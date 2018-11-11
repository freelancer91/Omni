package omni.impl.seq.dbllnk.ofboolean;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.BooleanPredicate;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfBoolean{
  UncheckedList(){
    super();
  }
  UncheckedList(Node onlyNode){
    super(onlyNode);
  }
  UncheckedList(Node head,int size,Node tail){
    super(head,size,tail);
  }
  @Override public boolean removeLastOccurrence(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean removeLastOccurrence(float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean removeLastOccurrence(int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean booleanElement(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean removeBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfBoolean iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean poll(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double pollDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float pollFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long pollLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int pollInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short pollShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char pollChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public byte pollByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean pollBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean popBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void push(boolean val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Boolean val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,boolean val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Boolean val){
    // TODO Auto-generated method stub
  }
  @Override public Boolean get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfBoolean listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfBoolean listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean removeBooleanAt(int index){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean set(int index,Boolean val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Boolean val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Boolean val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfBoolean descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean getFirstBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean getLastBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean pollFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean pollFirstBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public byte pollFirstByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char pollFirstChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double pollFirstDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float pollFirstFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int pollFirstInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long pollFirstLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short pollFirstShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Boolean pollLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean pollLastBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public byte pollLastByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char pollLastChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double pollLastDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float pollLastFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int pollLastInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long pollLastLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short pollLastShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Boolean removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean removeFirstBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean removeLastBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveFirstMatch(Node head,boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
}
