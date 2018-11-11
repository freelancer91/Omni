package omni.impl.seq.dbllnk.ofchar;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.CharPredicate;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfChar{
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
  @Override public char charElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Character element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(char val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Character val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Character remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char removeChar(){
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
  @Override public boolean add(Character val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfChar iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Character poll(){
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
  @Override public char pollChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Character pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char popChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(char val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Character val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,char val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Character val){
    // TODO Auto-generated method stub
  }
  @Override public Character get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfChar listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfChar listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Character remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char removeCharAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Character set(int index,Character val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Character val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Character val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfChar descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Character getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char getFirstChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Character getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char getLastChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(char val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Character val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(char val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Character val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Character pollFirst(){
    // TODO Auto-generated method stub
    return null;
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
  @Override public Character pollLast(){
    // TODO Auto-generated method stub
    return null;
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
  @Override public Character removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char removeFirstChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Character removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public char removeLastChar(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,CharPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,CharPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,CharPredicate filter){
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
