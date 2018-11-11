package omni.impl.seq.dbllnk.ofint;
import java.util.function.IntPredicate;
import omni.api.OmniDeque;
import omni.api.OmniList;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfInt{
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
  @Override public Integer element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int intElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offer(int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Integer val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Integer remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int removeInt(){
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
  @Override public boolean add(Integer val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfInt iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Integer poll(){
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
  @Override public Integer pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int popInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(int val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Integer val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,int val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Integer val){
    // TODO Auto-generated method stub
  }
  @Override public Integer get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfInt listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfInt listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Integer remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int removeIntAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Integer set(int index,Integer val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Integer val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Integer val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfInt descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Integer getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int getFirstInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Integer getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int getLastInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Integer val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(int val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Integer val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Integer pollFirst(){
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
  @Override public Integer pollLast(){
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
  @Override public Integer removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int removeFirstInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Integer removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int removeLastInt(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,IntPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,IntPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,IntPredicate filter){
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
