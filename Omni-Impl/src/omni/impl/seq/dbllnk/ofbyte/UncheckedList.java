package omni.impl.seq.dbllnk.ofbyte;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.BytePredicate;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfByte{
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
  @Override public boolean removeLastOccurrence(byte val){
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
  @Override public byte byteElement(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Byte element(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offer(Byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Byte remove(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte removeByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean add(Byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public omni.api.OmniIterator.OfByte iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Byte poll(){
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
  @Override public byte pollByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Byte pop(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte popByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void push(byte val){
    // TODO Auto-generated method stub
  }
  @Override public void push(Byte val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,byte val){
    // TODO Auto-generated method stub
  }
  @Override public void add(int index,Byte val){
    // TODO Auto-generated method stub
  }
  @Override public Byte get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfByte listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfByte listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Byte remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte removeByteAt(int index){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Byte set(int index,Byte val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void addFirst(Byte val){
    // TODO Auto-generated method stub
  }
  @Override public void addLast(Byte val){
    // TODO Auto-generated method stub
  }
  @Override public omni.api.OmniIterator.OfByte descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Byte getFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte getFirstByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Byte getLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte getLastByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public boolean offerFirst(byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerFirst(Byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean offerLast(Byte val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Byte pollFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte pollFirstByte(){
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
  @Override public Byte pollLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte pollLastByte(){
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
  @Override public Byte removeFirst(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte removeFirstByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Byte removeLast(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public byte removeLastByte(){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override boolean collapseBody(Node head,Node tail,BytePredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override void collapseTail(Node head,Node tail,BytePredicate filter){
    // TODO Auto-generated method stub
  }
  @Override void findNewHead(Node head,BytePredicate filter){
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
