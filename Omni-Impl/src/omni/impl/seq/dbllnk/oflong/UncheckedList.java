package omni.impl.seq.dbllnk.oflong;
import java.util.function.LongPredicate;
import omni.api.OmniDeque;
import omni.api.OmniList;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfLong{
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
  @Override public Long element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long longElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offer(long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Long remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long removeLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfLong iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Long poll(){
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
  @Override public Long pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long popLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(long val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Long val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,long val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Long val){
    // TODO Auto-generated method stub
  }
  @Override public Long get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfLong listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfLong listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Long remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long removeLongAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Long set(int index,Long val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Long val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Long val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfLong descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Long getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long getFirstLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Long getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long getLastLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Long pollFirst(){
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
  @Override public long pollFirstLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Long pollLast(){
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
  @Override public long pollLastLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Long removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long removeFirstLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Long removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public long removeLastLong(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,LongPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,LongPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,LongPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override boolean uncheckedRemoveFirstMatch(Node head,long val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
}
