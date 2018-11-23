package omni.impl.seq;
import java.util.Objects;
import java.util.function.Predicate;
import omni.util.OmniPred;
public abstract class AbstractRefList<E> extends AbstractSeq
{
  protected AbstractRefList()
  {
    super();
  }
  protected AbstractRefList(int size)
  {
    super(size);
  }
  public int lastIndexOf(byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  protected abstract int uncheckedlastIndexOf(int size,Predicate<Object> pred);
  protected abstract int uncheckedlastIndexOfNonNull(int size,Object nonNull);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return uncheckedlastIndexOfNonNull(size,val);
      }
      return uncheckedlastIndexOf(size,Objects::isNull);
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
     return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
	    return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
	  }
    }
    return -1;
  }
  public int lastIndexOf(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
}
