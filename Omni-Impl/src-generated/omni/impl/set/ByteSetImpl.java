package omni.impl.set;
import omni.api.OmniSet;
import java.io.Externalizable;
import java.io.Serializable;
import omni.impl.CheckedCollection;
import omni.function.BytePredicate;
import java.util.function.Predicate;
import omni.function.ByteConsumer;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
public abstract class ByteSetImpl implements OmniSet.OfByte,Externalizable,Cloneable{
 //TODO make not abstract
  //TODO equals methods
  //TODO toString methods
  //TODO clone methods
  //TODO hashCode methods
  //TODO read/write methods
  //TODO constructors
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  @Override public boolean add(Byte val){
    return this.add((byte)val);
  }
  public abstract static class Checked extends ByteSetImpl{
    transient int modCountAndSize;
        //TODO make not abstract
    public abstract static class Descending extends Checked{
        //TODO make not abstract
    }
  }
  public abstract static class Descending extends ByteSetImpl{
      //TODO make not abstract
  }
  private abstract static class UncheckedFullView implements OmniSet.OfByte,Cloneable,Serializable{
    transient final ByteSetImpl root;
        //TODO make not abstract
    private UncheckedFullView(ByteSetImpl root){
      this.root=root;
    }
    @Override public boolean add(Byte val){
      return this.add((byte)val);
    }
    private abstract static class Descending extends UncheckedFullView{
        //TODO make not abstract
      private Descending(ByteSetImpl root){
        super(root);
      }
    }
  }
  private abstract static class AbstractUncheckedSubSet implements OmniSet.OfByte{
    transient final ByteSetImpl root;
    transient final AbstractUncheckedSubSet parent;
    transient final int boundInfo;
    transient int size;
    private AbstractUncheckedSubSet(ByteSetImpl root,int boundInfo,int size){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    private AbstractUncheckedSubSet(AbstractUncheckedSubSet parent,int boundInfo,int size){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    @Override public boolean add(Byte val){
      return this.add((byte)val);
    }
  }
  private abstract static class UncheckedHeadView extends AbstractUncheckedSubSet{
      //TODO make not abstract
    private UncheckedHeadView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedHeadView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    private abstract static class Descending extends UncheckedHeadView{
        //TODO make not abstract
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
    }
  }
  private abstract static class UncheckedTailView extends AbstractUncheckedSubSet{
      //TODO make not abstract
    private UncheckedTailView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedTailView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    private abstract static class Descending extends UncheckedTailView{
        //TODO make not abstract
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
    }
  }
  private abstract static class UncheckedBodyView extends AbstractUncheckedSubSet{
      //TODO make not abstract
    private UncheckedBodyView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedBodyView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    private abstract static class Descending extends UncheckedBodyView{
        //TODO make not abstract
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
    }
  }
  private abstract static class CheckedFullView implements OmniSet.OfByte,Cloneable,Serializable{
      //TODO make not abstract
    transient final ByteSetImpl.Checked root;
    private CheckedFullView(final ByteSetImpl.Checked root){
      this.root=root;
    }
    @Override public boolean add(Byte val){
      return this.add((byte)val);
    }
    private abstract static class Descending extends CheckedFullView{
        //TODO make not abstract
      private Descending(final ByteSetImpl.Checked root){
        super(root);
      }
    }
  }
  private abstract static class AbstractCheckedSubSet implements OmniSet.OfByte{
    transient final ByteSetImpl.Checked root;
    transient final AbstractCheckedSubSet parent;
    transient final int boundInfo;
    transient int modCountAndSize;
    private AbstractCheckedSubSet(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    private AbstractCheckedSubSet(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    @Override public boolean add(Byte val){
      return this.add((byte)val);
    }
  }
  private abstract static class CheckedHeadView extends AbstractCheckedSubSet{
      //TODO make not abstract
    private CheckedHeadView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedHeadView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    private static abstract class Descending extends CheckedHeadView{
      //TODO make not abstract
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
    }
  }
  private abstract static class CheckedTailView extends AbstractCheckedSubSet{
      //TODO make not abstract
    private CheckedTailView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedTailView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    private static abstract class Descending extends CheckedTailView{
      //TODO make not abstract
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
    }
  }
  private static class CheckedBodyView extends AbstractCheckedSubSet{
      //TODO make not abstract
    private CheckedBodyView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedBodyView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(BytePredicate filter){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(Consumer<? super Byte> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedBodyView{
      //TODO make not abstract
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public void forEach(ByteConsumer action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(Consumer<? super Byte> action){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
}
