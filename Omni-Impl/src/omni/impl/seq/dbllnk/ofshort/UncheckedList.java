package omni.impl.seq.dbllnk.ofshort;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.ShortPredicate;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfShort{
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
  @Override public boolean removeLastOccurrence(char val){
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
  @Override public boolean removeLastOccurrence(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Short element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Short remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short removeShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short shortElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfShort iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Short poll(){
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
  @Override public Short pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short popShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(short val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Short val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,short val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Short val){
    // TODO Auto-generated method stub
  }
  @Override public Short get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfShort listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfShort listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Short remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short removeShortAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Short set(int index,Short val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Short val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Short val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfShort descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Short getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short getFirstShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Short getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short getLastShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Short pollFirst(){
    // TODO Auto-generated method stub
    return null;
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
  @Override public Short pollLast(){
    // TODO Auto-generated method stub
    return null;
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
  @Override public Short removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short removeFirstShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Short removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public short removeLastShort(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,ShortPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,ShortPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,ShortPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override boolean uncheckedRemoveFirstMatch(Node head,int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
}
