package omni.impl.seq.dbllnk.ofdouble;
import java.util.function.DoublePredicate;
import omni.api.OmniDeque;
import omni.api.OmniList;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfDouble{
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
  @Override public double doubleElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Double remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double removeDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(char val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(float val){
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
  @Override public boolean add(short val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfDouble iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Double poll(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double pollDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double popDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(double val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Double val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,double val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Double val){
    // TODO Auto-generated method stub
  }
  @Override public Double get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfDouble listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfDouble listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Double remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double removeDoubleAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double set(int index,Double val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Double val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Double val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfDouble descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Double getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double getFirstDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double getLastDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Double val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Double pollFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double pollFirstDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double pollLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double pollLastDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double removeFirstDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Double removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double removeLastDouble(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,DoublePredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,DoublePredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,DoublePredicate filter){
    // TODO Auto-generated method stub
  }
  @Override boolean uncheckedRemoveFirstDbl0(Node head){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveFirstDblBits(Node head,long dblBits){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveFirstDblNaN(Node head){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
}
