package omni.impl.seq.dbllnk.offloat;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.FloatPredicate;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfFloat{
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
  @Override public Float element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float floatElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offer(float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Float remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float removeFloat(){
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
  @Override public boolean add(float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Float val){
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
  @Override public omni.api.OmniIterator.OfFloat iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Float poll(){
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
  @Override public Float pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float popFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(float val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Float val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,float val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Float val){
    // TODO Auto-generated method stub
  }
  @Override public Float get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfFloat listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfFloat listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Float remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float removeFloatAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Float set(int index,Float val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Float val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Float val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfFloat descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Float getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float getFirstFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Float getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float getLastFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Float val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Float pollFirst(){
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
  @Override public Float pollLast(){
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
  @Override public Float removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float removeFirstFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Float removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public float removeLastFloat(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,FloatPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,FloatPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,FloatPredicate filter){
    // TODO Auto-generated method stub
  }
  @Override boolean uncheckedRemoveFirstFlt0(Node head){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveFirstFltBits(Node head,int fltBits){
    // TODO Auto-generated method stub
    return false;
  }
  @Override boolean uncheckedRemoveFirstFltNaN(Node head){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
}
