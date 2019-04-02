package omni.impl;

import omni.api.OmniCollection;
import omni.util.TypeConversionUtil;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ModCheckTestObject{
  int callCount;
  public boolean equals(Object val) {
    ++callCount;
    throw new IndexOutOfBoundsException();
  }
  public int hashCode(Object val) {
    ++callCount;
    throw new IndexOutOfBoundsException();
  }
  public String toString() {
    ++callCount;
    throw new IndexOutOfBoundsException();
  }
  static class Modding extends ModCheckTestObject{
    OmniCollection.OfRef col;
    Modding(OmniCollection.OfRef<?> col){
      this.col=col;
    }
    public boolean equals(Object val) {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      return super.equals(val);
    }
    public int hashCode(Object val) {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      return super.hashCode();
    }
    public String toString() {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      return super.toString();
    }
  }
  static class ModdingAndThrowing extends Modding{
    ModdingAndThrowing(OmniCollection.OfRef<?> col){
      super(col);
    }
    public boolean equals(Object val) {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      throw new IndexOutOfBoundsException();
    }
    public int hashCode(Object val) {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      throw new IndexOutOfBoundsException();
    }
    public String toString() {
      ++callCount;
      col.add(TypeConversionUtil.convertToObject(0));
      throw new IndexOutOfBoundsException();
    }
  }
}
