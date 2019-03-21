package omni.api;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeUtil;
import java.util.function.Supplier;
public class QueryTestUtil
{
  public static void testcontainsLong(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)0)));
    }
  }
  public static void testcontainsLong(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)0)));
    }
  }
  public static void testcontainsLong(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.contains((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,col.contains((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((long)Long.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfLong)
    {
      Assertions.assertEquals(true,col.contains((Object)(long)0));
    }
    else
    {
      Assertions.assertEquals(false,col.contains((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((long)0));
  }
  public static void testcontainsLong(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testremoveValLong(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveValLong(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveValLong(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.remove((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,col.remove((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Long.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfLong)
    {
      Assertions.assertEquals(true,col.remove((Object)(long)0));
    }
    else
    {
      Assertions.assertEquals(false,col.remove((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)0));
  }
  public static void testremoveValLong(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testcontainsInt(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testcontainsInt(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testcontainsInt(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,col.contains((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.contains((Object)(int)0));
    }
    else
    {
      Assertions.assertEquals(false,col.contains((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((int)0));
  }
  public static void testcontainsInt(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValInt(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveValInt(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveValInt(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,col.remove((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.remove((Object)(int)0));
    }
    else
    {
      Assertions.assertEquals(false,col.remove((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)0));
  }
  public static void testremoveValInt(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsShort(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    }
  }
  public static void testcontainsShort(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    }
  }
  public static void testcontainsShort(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.contains((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,col.contains((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.contains((Object)(short)0));
    }
    else
    {
      Assertions.assertEquals(false,col.contains((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((short)0));
  }
  public static void testcontainsShort(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValShort(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveValShort(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveValShort(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.remove((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,col.remove((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.remove((Object)(short)0));
    }
    else
    {
      Assertions.assertEquals(false,col.remove((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)0));
  }
  public static void testremoveValShort(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsChar(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    }
  }
  public static void testcontainsChar(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    }
  }
  public static void testcontainsChar(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.contains((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.contains((Object)(char)0));
    }
    else
    {
      Assertions.assertEquals(false,col.contains((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)0));
  }
  public static void testcontainsChar(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testremoveValChar(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveValChar(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveValChar(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.remove((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.remove((Object)(char)0));
    }
    else
    {
      Assertions.assertEquals(false,col.remove((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)0));
  }
  public static void testremoveValChar(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testcontainsByte(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testcontainsByte(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testcontainsByte(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.contains((Object)(byte)0));
    }
    else
    {
      Assertions.assertEquals(false,col.contains((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((byte)0));
  }
  public static void testcontainsByte(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValByte(Supplier<OmniCollection.OfDouble> collectionSupplier,double containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveValByte(Supplier<OmniCollection.OfFloat> collectionSupplier,float containsVal)
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveValByte(Supplier<OmniCollection> collectionSupplier)
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.remove((Object)(byte)0));
    }
    else
    {
      Assertions.assertEquals(false,col.remove((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)0));
  }
  public static void testremoveValByte(Supplier<OmniCollection.OfBoolean> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsBoolean(Supplier<OmniCollection> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)!containsVal));
    OmniCollection collection;
    if((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef)
    {
      Assertions.assertEquals(true,collection.contains((Object)containsVal));
    }
    else
    {
      Assertions.assertEquals(false,collection.contains((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)!containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().contains(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Boolean)containsVal));
  }
  public static void testremoveValBoolean(Supplier<OmniCollection> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)!containsVal));
    OmniCollection collection;
    if((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef)
    {
      Assertions.assertEquals(true,collection.remove((Object)containsVal));
    }
    else
    {
      Assertions.assertEquals(false,collection.remove((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)!containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Boolean)containsVal));
  }
  public static <E> void testcontainsNullReturnPositive(Supplier<OmniCollection.OfRef<E>> collectionSupplier)
  {
    Assertions.assertEquals(true,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Double)null));
  }
  public static <E> void testremoveValNullReturnPositive(Supplier<OmniCollection.OfRef<E>> collectionSupplier)
  {
    Assertions.assertEquals(true,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)null));
  }
  public static void testcontainsNullReturnNegative(Supplier<OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)null));
  }
  public static void testremoveValNullReturnNegative(Supplier<OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)null));
  }
  public static void testEmptycontains(Supplier<OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(true));
    Assertions.assertEquals(false,collectionSupplier.get().contains(false));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(0));
    Assertions.assertEquals(false,collectionSupplier.get().contains(1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.NaN));
  }
  public static void testEmptyremoveVal(Supplier<OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(true));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(false));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.NaN));
  }
}
