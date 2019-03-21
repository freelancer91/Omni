package omni.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeUtil;
public class QueryTestUtil{
    // TODO testContainsFloat
    // TODO testContainsDouble
    // TODO testContainsRef
    // TODO abstraction to allow for other kinds of queries

    public static void testContainsLong(OmniCollection.OfDouble collection,double containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((long)1));
            Assertions.assertFalse(collection.contains(TypeUtil.MAX_SAFE_LONG + 1));
            Assertions.assertFalse(collection.contains(TypeUtil.MIN_SAFE_LONG - 1));
            Assertions.assertTrue(collection.contains((long)containsVal));
            Assertions.assertFalse(collection.contains(Long.valueOf(1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertTrue(collection.contains(Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((long)containsVal));
            Assertions.assertFalse(collection.contains((long)0));
            Assertions.assertTrue(collection.contains(Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains(Long.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
            Assertions.assertFalse(collection.contains(TypeUtil.MAX_SAFE_LONG + 1));
            Assertions.assertFalse(collection.contains(TypeUtil.MIN_SAFE_LONG - 1));
            Assertions.assertFalse(collection.contains(Long.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
        }
    }
    public static void testContainsLong(OmniCollection.OfFloat collection,float containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((long)1));
            Assertions.assertFalse(collection.contains((long)TypeUtil.MAX_SAFE_INT + 1));
            Assertions.assertFalse(collection.contains((long)TypeUtil.MIN_SAFE_INT - 1));
            Assertions.assertTrue(collection.contains((long)containsVal));
            Assertions.assertFalse(collection.contains(Long.valueOf(1)));
            Assertions.assertFalse(collection.contains(Long.valueOf((long)TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Long.valueOf((long)TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertTrue(collection.contains(Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((long)containsVal));
            Assertions.assertFalse(collection.contains((long)0));
            Assertions.assertTrue(collection.contains(Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains(Long.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
            Assertions.assertFalse(collection.contains((long)TypeUtil.MAX_SAFE_INT + 1));
            Assertions.assertFalse(collection.contains((long)TypeUtil.MIN_SAFE_INT - 1));
            Assertions.assertFalse(collection.contains(Long.valueOf((long)TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Long.valueOf((long)TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT - 1)));
        }
    }
    public static void testContainsLong(OmniCollection.OfLong collection){
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertTrue(collection.contains((Object)Long.valueOf(0)));
    }
    public static void testContainsLong(OmniCollection.OfInt collection){
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
        Assertions.assertFalse(collection.contains((long)Integer.MAX_VALUE + 1));
        Assertions.assertFalse(collection.contains((long)Integer.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Integer.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Integer.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Integer.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Integer.MIN_VALUE - 1)));
    }
    public static void testContainsLong(OmniCollection.OfShort collection){
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
        Assertions.assertFalse(collection.contains((long)Short.MAX_VALUE + 1));
        Assertions.assertFalse(collection.contains((long)Short.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Short.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Short.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Short.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Short.MIN_VALUE - 1)));
    }
    public static void testContainsLong(OmniCollection.OfByte collection){
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
        Assertions.assertFalse(collection.contains((long)Byte.MAX_VALUE + 1));
        Assertions.assertFalse(collection.contains((long)Byte.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Byte.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Byte.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Byte.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Byte.MIN_VALUE - 1)));
    }
    public static void testContainsLong(OmniCollection.OfChar collection){
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
        Assertions.assertFalse(collection.contains((long)Character.MAX_VALUE + 1));
        Assertions.assertFalse(collection.contains((long)Character.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Character.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Long.valueOf((long)Character.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Character.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf((long)Character.MIN_VALUE - 1)));
    }
    public static void testContainsLong(OmniCollection.OfBoolean collection,boolean containsVal){
        if(containsVal){
            Assertions.assertFalse(collection.contains((long)2));
            Assertions.assertTrue(collection.contains((long)1));
            Assertions.assertFalse(collection.contains((long)0));
            Assertions.assertFalse(collection.contains(Long.valueOf(2)));
            Assertions.assertTrue(collection.contains(Long.valueOf(1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(2)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        }else{
            Assertions.assertFalse(collection.contains((long)2));
            Assertions.assertFalse(collection.contains((long)1));
            Assertions.assertTrue(collection.contains((long)0));
            Assertions.assertFalse(collection.contains(Long.valueOf(2)));
            Assertions.assertFalse(collection.contains(Long.valueOf(1)));
            Assertions.assertTrue(collection.contains(Long.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(2)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Long.valueOf(0)));
        }
    }
    public static <E> void testContainsLong(OmniCollection.OfRef<E> collection){
        Assertions.assertFalse(collection.contains(Long.valueOf(1)));
        Assertions.assertTrue(collection.contains(Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Long.valueOf(1)));
        Assertions.assertTrue(collection.contains((Object)Long.valueOf(0)));
        Assertions.assertFalse(collection.contains((long)1));
        Assertions.assertTrue(collection.contains((long)0));
    }


    public static void testContainsInt(OmniCollection.OfDouble collection,double containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains(1));
            Assertions.assertTrue(collection.contains((int)containsVal));
            Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
            Assertions.assertTrue(collection.contains(Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf((int)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((int)containsVal));
            Assertions.assertFalse(collection.contains(0));
            Assertions.assertTrue(collection.contains(Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
        }
    }
    public static void testContainsInt(OmniCollection.OfFloat collection,float containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains(1));
            Assertions.assertFalse(collection.contains(TypeUtil.MAX_SAFE_INT + 1));
            Assertions.assertFalse(collection.contains(TypeUtil.MIN_SAFE_INT - 1));
            Assertions.assertTrue(collection.contains((int)containsVal));
            Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertTrue(collection.contains(Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf((int)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((int)containsVal));
            Assertions.assertFalse(collection.contains(0));
            Assertions.assertTrue(collection.contains(Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf((int)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains(TypeUtil.MAX_SAFE_INT + 1));
            Assertions.assertFalse(collection.contains(TypeUtil.MIN_SAFE_INT - 1));
            Assertions.assertFalse(collection.contains(Integer.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
        }
    }
    public static void testContainsInt(OmniCollection.OfLong collection){
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertTrue(collection.contains(0));
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
    }
    public static void testContainsInt(OmniCollection.OfInt collection){
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertTrue(collection.contains(0));
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains((Object)Integer.valueOf(0)));
    }
    public static void testContainsInt(OmniCollection.OfShort collection){
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertFalse(collection.contains(Short.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Short.MAX_VALUE + 1));
        Assertions.assertTrue(collection.contains(0));
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains(Integer.valueOf(Short.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains(Integer.valueOf(Short.MAX_VALUE + 1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(Short.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(Short.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
    }
    public static void testContainsInt(OmniCollection.OfChar collection){
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertTrue(collection.contains(0));
        Assertions.assertFalse(collection.contains(-1));
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains(Integer.valueOf(-1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(-1)));
    }
    public static void testContainsInt(OmniCollection.OfByte collection){
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertFalse(collection.contains(Byte.MIN_VALUE - 1));
        Assertions.assertFalse(collection.contains(Byte.MAX_VALUE + 1));
        Assertions.assertTrue(collection.contains(0));
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains(Integer.valueOf(Byte.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains(Integer.valueOf(Byte.MAX_VALUE + 1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(Byte.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(Byte.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
    }
    public static void testContainsInt(OmniCollection.OfBoolean collection,boolean containsVal){
        if(containsVal){
            Assertions.assertFalse(collection.contains(2));
            Assertions.assertTrue(collection.contains(1));
            Assertions.assertFalse(collection.contains(0));
            Assertions.assertFalse(collection.contains(Integer.valueOf(2)));
            Assertions.assertTrue(collection.contains(Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(2)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
        }else{
            Assertions.assertFalse(collection.contains(2));
            Assertions.assertFalse(collection.contains(1));
            Assertions.assertTrue(collection.contains(0));
            Assertions.assertFalse(collection.contains(Integer.valueOf(2)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
            Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(2)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains((Object)Integer.valueOf(0)));
        }
    }
    public static <E> void testContainsInt(OmniCollection.OfRef<E> collection){
        Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains(Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains((Object)Integer.valueOf(1)));
        Assertions.assertTrue(collection.contains((Object)Integer.valueOf(0)));
        Assertions.assertFalse(collection.contains(1));
        Assertions.assertTrue(collection.contains(0));
    }
    public static void testContainsShort(OmniCollection.OfDouble collection,double containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((short)1));
            Assertions.assertTrue(collection.contains((short)containsVal));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((short)containsVal));
            Assertions.assertFalse(collection.contains((short)0));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)0)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
        }
    }
    public static void testContainsShort(OmniCollection.OfFloat collection,float containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((short)1));
            Assertions.assertTrue(collection.contains((short)containsVal));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((short)containsVal));
            Assertions.assertFalse(collection.contains((short)0));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)0)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
        }
    }
    public static void testContainsShort(OmniCollection.OfLong collection){
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertTrue(collection.contains((short)0));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
    }
    public static void testContainsShort(OmniCollection.OfInt collection){
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertTrue(collection.contains((short)0));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
    }
    public static void testContainsShort(OmniCollection.OfChar collection){
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertTrue(collection.contains((short)0));
        Assertions.assertFalse(collection.contains((short)-1));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)-1)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)-1)));
    }
    public static void testContainsShort(OmniCollection.OfShort collection){
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertTrue(collection.contains((short)0));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains((Object)Short.valueOf((short)0)));
    }
    public static void testContainsShort(OmniCollection.OfByte collection){
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertFalse(collection.contains((short)(Byte.MIN_VALUE - 1)));
        Assertions.assertFalse(collection.contains((short)(Byte.MAX_VALUE + 1)));
        Assertions.assertTrue(collection.contains((short)0));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)(Byte.MIN_VALUE - 1))));
        Assertions.assertFalse(collection.contains(Short.valueOf((short)(Byte.MAX_VALUE + 1))));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)(Byte.MIN_VALUE - 1))));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)(Byte.MAX_VALUE + 1))));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
    }
    public static void testContainsShort(OmniCollection.OfBoolean collection,boolean containsVal){
        if(containsVal){
            Assertions.assertFalse(collection.contains((short)2));
            Assertions.assertTrue(collection.contains((short)1));
            Assertions.assertFalse(collection.contains((short)0));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)2)));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)0)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)2)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
        }else{
            Assertions.assertFalse(collection.contains((short)2));
            Assertions.assertFalse(collection.contains((short)1));
            Assertions.assertTrue(collection.contains((short)0));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)2)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
            Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)2)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)0)));
        }
    }
    public static <E> void testContainsShort(OmniCollection.OfRef<E> collection){
        Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains(Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((Object)Short.valueOf((short)1)));
        Assertions.assertTrue(collection.contains((Object)Short.valueOf((short)0)));
        Assertions.assertFalse(collection.contains((short)1));
        Assertions.assertTrue(collection.contains((short)0));
    }
    public static void testContainsChar(OmniCollection.OfDouble collection,double containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((char)1));
            Assertions.assertTrue(collection.contains((char)containsVal));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((char)containsVal));
            Assertions.assertFalse(collection.contains((char)0));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)0)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        }
    }
    public static void testContainsChar(OmniCollection.OfFloat collection,float containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((char)1));
            Assertions.assertTrue(collection.contains((char)containsVal));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((char)containsVal));
            Assertions.assertFalse(collection.contains((char)0));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)0)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        }
    }
    public static void testContainsChar(OmniCollection.OfLong collection){
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
    }
    public static void testContainsChar(OmniCollection.OfInt collection){
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
    }
    public static void testContainsChar(OmniCollection.OfChar collection){
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains((Object)Character.valueOf((char)0)));
    }
    public static void testContainsChar(OmniCollection.OfShort collection){
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
        Assertions.assertFalse(collection.contains((char)(Short.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)(Short.MAX_VALUE + 1))));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)(Short.MAX_VALUE + 1))));
    }
    public static void testContainsChar(OmniCollection.OfByte collection){
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
        Assertions.assertFalse(collection.contains((char)(Byte.MAX_VALUE + 1)));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains(Character.valueOf((char)(Byte.MAX_VALUE + 1))));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)(Byte.MAX_VALUE + 1))));
    }
    public static void testContainsChar(OmniCollection.OfBoolean collection,boolean containsVal){
        if(containsVal){
            Assertions.assertFalse(collection.contains((char)2));
            Assertions.assertTrue(collection.contains((char)1));
            Assertions.assertFalse(collection.contains((char)0));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)2)));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)0)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)2)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        }else{
            Assertions.assertFalse(collection.contains((char)2));
            Assertions.assertFalse(collection.contains((char)1));
            Assertions.assertTrue(collection.contains((char)0));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)2)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
            Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)2)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)0)));
        }
    }
    public static <E> void testContainsChar(OmniCollection.OfRef<E> collection){
        Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains(Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((Object)Character.valueOf((char)1)));
        Assertions.assertTrue(collection.contains((Object)Character.valueOf((char)0)));
        Assertions.assertFalse(collection.contains((char)1));
        Assertions.assertTrue(collection.contains((char)0));
    }
    public static void testContainsByte(OmniCollection.OfDouble collection,double containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((byte)1));
            Assertions.assertTrue(collection.contains((byte)containsVal));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((byte)containsVal));
            Assertions.assertFalse(collection.contains((byte)0));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)0)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
        }
    }
    public static void testContainsByte(OmniCollection.OfFloat collection,float containsVal){
        if(containsVal == 0){
            Assertions.assertFalse(collection.contains((byte)1));
            Assertions.assertTrue(collection.contains((byte)containsVal));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)containsVal)));
        }else{
            Assertions.assertTrue(collection.contains((byte)containsVal));
            Assertions.assertFalse(collection.contains((byte)0));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)0)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)containsVal)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
        }
    }
    public static void testContainsByte(OmniCollection.OfLong collection){
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
    }
    public static void testContainsByte(OmniCollection.OfInt collection){
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
    }
    public static void testContainsByte(OmniCollection.OfShort collection){
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
    }
    public static void testContainsByte(OmniCollection.OfChar collection){
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
        Assertions.assertFalse(collection.contains((byte)-1));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)-1)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)-1)));
    }
    public static void testContainsByte(OmniCollection.OfByte collection){
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains((Object)Byte.valueOf((byte)0)));
    }
    public static <E> void testContainsByte(OmniCollection.OfRef<E> collection){
        Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertTrue(collection.contains((Object)Byte.valueOf((byte)0)));
        Assertions.assertFalse(collection.contains((byte)1));
        Assertions.assertTrue(collection.contains((byte)0));
    }
    public static void testQueryByte(Supplier<OmniCollection.OfBoolean> collectionSupplier,String methodName,
            boolean containsVal){
        OmniCollection col;
        Method queryPrimitive=lookUpMethod(col=collectionSupplier.get(),methodName,byte.class);
        Method queryBoxed=lookUpMethod(col,methodName,Byte.class);
        Method queryObject=lookUpMethod(col,methodName,Object.class);
        assertNegativeReturn(col,queryPrimitive,(byte)2);
        assertNegativeReturn(col=collectionSupplier.get(),queryBoxed,(byte)2);
        assertNegativeReturn(col=collectionSupplier.get(),queryObject,(byte)2);
        // TODO
    }
    public static void testContainsByte(OmniCollection.OfBoolean collection,boolean containsVal){
        if(containsVal){
            Assertions.assertFalse(collection.contains((byte)2));
            Assertions.assertTrue(collection.contains((byte)1));
            Assertions.assertFalse(collection.contains((byte)0));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)2)));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)0)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)2)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
        }else{
            Assertions.assertFalse(collection.contains((byte)2));
            Assertions.assertFalse(collection.contains((byte)1));
            Assertions.assertTrue(collection.contains((byte)0));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)2)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
            Assertions.assertTrue(collection.contains(Byte.valueOf((byte)0)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)2)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains((Object)Byte.valueOf((byte)0)));
        }
    }
    public static void testQueryBoolean(Supplier<OmniCollection> collectionSupplier,String methodName,
            boolean containsVal){
        OmniCollection col;
        Method queryPrimitive=lookUpMethod(col=collectionSupplier.get(),methodName,boolean.class);
        Method queryBoxed=lookUpMethod(col,methodName,Boolean.class);
        Method queryObject=lookUpMethod(col,methodName,Object.class);
        assertNegativeReturn(col,queryPrimitive,!containsVal);
        assertNegativeReturn(col=collectionSupplier.get(),queryBoxed,!containsVal);
        assertNegativeReturn(col=collectionSupplier.get(),queryObject,!containsVal);
        assertPositiveReturn(col=collectionSupplier.get(),queryPrimitive,containsVal);
        assertPositiveReturn(col=collectionSupplier.get(),queryBoxed,containsVal);
        if((col=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || col instanceof OmniCollection.OfRef){
            assertPositiveReturn(col,queryObject,containsVal);
        }else{
            assertNegativeReturn(col,queryObject,containsVal);
        }
    }
    public static void testContainsBoolean(OmniCollection collection,boolean containsVal){
        testQueryBoolean(()->collection,"contains",containsVal);
//        Assertions.assertFalse(collection.contains(!containsVal));
        //        Assertions.assertFalse(collection.contains(Boolean.valueOf(!containsVal)));
        //        Assertions.assertFalse(collection.contains((Object)Boolean.valueOf(!containsVal)));
        //        Assertions.assertTrue(collection.contains(containsVal));
        //        Assertions.assertTrue(collection.contains(Boolean.valueOf(containsVal)));
        //        if(collection instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef){
        //            Assertions.assertTrue(collection.contains((Object)Boolean.valueOf(containsVal)));
        //        }else{
        //            Assertions.assertFalse(collection.contains((Object)Boolean.valueOf(containsVal)));
        //        }
    }
    public static <E> void testContainsNullReturnTrue(OmniCollection.OfRef<E> collection){
        testQueryNullReturnPositive(()->collection,"contains");
        //        Assertions.assertTrue(collection.contains((Boolean)null));
        //        Assertions.assertTrue(collection.contains((Byte)null));
        //        Assertions.assertTrue(collection.contains((Character)null));
        //        Assertions.assertTrue(collection.contains((Short)null));
        //        Assertions.assertTrue(collection.contains((Integer)null));
        //        Assertions.assertTrue(collection.contains((Long)null));
        //        Assertions.assertTrue(collection.contains((Float)null));
        //        Assertions.assertTrue(collection.contains((Double)null));
        //        Assertions.assertTrue(collection.contains((Object)null));
    }
    public static void testContainsNullReturnFalse(OmniCollection collection){
        testQueryNullReturnNegative(()->collection,"contains");
//        Assertions.assertFalse(collection.contains((Boolean)null));
        //        Assertions.assertFalse(collection.contains((Byte)null));
        //        Assertions.assertFalse(collection.contains((Character)null));
        //        Assertions.assertFalse(collection.contains((Short)null));
        //        Assertions.assertFalse(collection.contains((Integer)null));
        //        Assertions.assertFalse(collection.contains((Long)null));
        //        Assertions.assertFalse(collection.contains((Float)null));
        //        Assertions.assertFalse(collection.contains((Double)null));
        //        Assertions.assertFalse(collection.contains((Object)null));
    }
    private static final Class<?>[] BOXED_PARAMETER_TYPES=new Class<?>[]{Boolean.class,Byte.class,Character.class,
            Short.class,Integer.class,Long.class,Float.class,Double.class,Object.class};
    public static <E> void testQueryNullReturnPositive(Supplier<OmniCollection.OfRef<E>> collectionSupplier,
            String methodName){
        Object[] args=new Object[]{null};
            for(Class<?> parameterType:BOXED_PARAMETER_TYPES){
                OmniCollection collection=collectionSupplier.get();
                Method method=lookUpMethod(collection,methodName,parameterType);
                assertPositiveReturn(collection,method,args);
            }
        }
    private static void assertPositiveReturn(Object obj,Method method,Object...args){
            Class<?> returnType=method.getReturnType();
            Object expectedReturn=null;
            if(boolean.class.equals(returnType)){
                expectedReturn=Boolean.TRUE;
            }else{
                Assertions.fail("unknown return type " + returnType + " was found for method " + method);
            }
            Object actualReturn=null;
            try{
                actualReturn=method.invoke(obj,args);
            }catch(InvocationTargetException | IllegalAccessException e){
                Assertions.fail(e);
            }
            Assertions.assertEquals(expectedReturn,actualReturn);
        }
    private static void assertNegativeReturn(Object obj,Method method,Object...args){
            Class<?> returnType=method.getReturnType();
            Object expectedReturn=null;
            if(boolean.class.equals(returnType)){
                expectedReturn=Boolean.FALSE;
            }else{
                Assertions.fail("unknown return type " + returnType + " was found for method " + method);
            }
            Object actualReturn=null;
            try{
                actualReturn=method.invoke(obj,args);
            }catch(InvocationTargetException | IllegalAccessException e){
                Assertions.fail(e);
            }
            Assertions.assertEquals(expectedReturn,actualReturn);
        }
        private static Method lookUpMethod(Object obj,String methodName,Class<?>...parameterTypes){
            try{
                return obj.getClass().getMethod(methodName,parameterTypes);
            }catch(NoSuchMethodException e){
                Assertions.fail(e);
                return null;
            }
        }
    public static void testQueryNullReturnNegative(Supplier<OmniCollection> collectionSupplier,String methodName){
        Object[] args=new Object[]{null};
            for(Class<?> parameterType:BOXED_PARAMETER_TYPES){
                OmniCollection collection=collectionSupplier.get();
                Method method=lookUpMethod(collection,methodName,parameterType);
                assertNegativeReturn(collection,method,args);
            }
        }
        public static void testEmptyContains(OmniCollection collection){
            Assertions.assertFalse(collection.contains((Boolean)null));
            Assertions.assertFalse(collection.contains((Byte)null));
            Assertions.assertFalse(collection.contains((Character)null));
            Assertions.assertFalse(collection.contains((Short)null));
            Assertions.assertFalse(collection.contains((Integer)null));
            Assertions.assertFalse(collection.contains((Long)null));
            Assertions.assertFalse(collection.contains((Float)null));
            Assertions.assertFalse(collection.contains((Double)null));
            Assertions.assertFalse(collection.contains((Object)null));
            Assertions.assertFalse(collection.contains(Boolean.TRUE));
            Assertions.assertFalse(collection.contains(Boolean.FALSE));
            Assertions.assertFalse(collection.contains(Byte.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)-2)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)-1)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)0)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)1)));
            Assertions.assertFalse(collection.contains(Byte.valueOf((byte)2)));
            Assertions.assertFalse(collection.contains(Byte.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)0)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)1)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)2)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Character.valueOf((char)Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Character.valueOf(Character.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Short.valueOf(Short.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Short.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)-2)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)-1)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)0)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)1)));
            Assertions.assertFalse(collection.contains(Short.valueOf((short)2)));
            Assertions.assertFalse(collection.contains(Short.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Short.valueOf(Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Integer.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Short.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(-2)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(-1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(0)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(1)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(2)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Character.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Integer.valueOf(Integer.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Long.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Integer.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Short.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(-2)));
            Assertions.assertFalse(collection.contains(Long.valueOf(-1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(0)));
            Assertions.assertFalse(collection.contains(Long.valueOf(1)));
            Assertions.assertFalse(collection.contains(Long.valueOf(2)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Character.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Integer.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Long.valueOf(Long.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Float.NEGATIVE_INFINITY)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Long.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Integer.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Short.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(-2)));
            Assertions.assertFalse(collection.contains(Float.valueOf(-1)));
            Assertions.assertFalse(collection.contains(Float.valueOf((float)0.0)));
            Assertions.assertFalse(collection.contains(Float.valueOf((float)-0.0)));
            Assertions.assertFalse(collection.contains(Float.valueOf(1)));
            Assertions.assertFalse(collection.contains(Float.valueOf(2)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Character.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Integer.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Long.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
            Assertions.assertFalse(collection.contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Float.POSITIVE_INFINITY)));
            Assertions.assertFalse(collection.contains(Float.valueOf(Float.NaN)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Double.NEGATIVE_INFINITY)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Long.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Integer.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Short.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Byte.MIN_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(-2)));
            Assertions.assertFalse(collection.contains(Double.valueOf(-1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(0.0)));
            Assertions.assertFalse(collection.contains(Double.valueOf(-0.0)));
            Assertions.assertFalse(collection.contains(Double.valueOf(1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(2)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Byte.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Short.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Character.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Integer.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Long.MAX_VALUE)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
            Assertions.assertFalse(collection.contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Double.POSITIVE_INFINITY)));
            Assertions.assertFalse(collection.contains(Double.valueOf(Double.NaN)));
            Assertions.assertFalse(collection.contains(true));
            Assertions.assertFalse(collection.contains(false));
            Assertions.assertFalse(collection.contains(Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains((byte)-2));
            Assertions.assertFalse(collection.contains((byte)-1));
            Assertions.assertFalse(collection.contains((byte)0));
            Assertions.assertFalse(collection.contains((byte)1));
            Assertions.assertFalse(collection.contains((byte)2));
            Assertions.assertFalse(collection.contains(Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((char)0));
            Assertions.assertFalse(collection.contains((char)1));
            Assertions.assertFalse(collection.contains((char)2));
            Assertions.assertFalse(collection.contains((char)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((char)Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Character.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Short.MIN_VALUE));
            Assertions.assertFalse(collection.contains((short)Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains((short)-2));
            Assertions.assertFalse(collection.contains((short)-1));
            Assertions.assertFalse(collection.contains((short)0));
            Assertions.assertFalse(collection.contains((short)1));
            Assertions.assertFalse(collection.contains((short)2));
            Assertions.assertFalse(collection.contains((short)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Integer.MIN_VALUE));
            Assertions.assertFalse(collection.contains((int)Short.MIN_VALUE));
            Assertions.assertFalse(collection.contains((int)Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains(-2));
            Assertions.assertFalse(collection.contains(-1));
            Assertions.assertFalse(collection.contains(0));
            Assertions.assertFalse(collection.contains(1));
            Assertions.assertFalse(collection.contains(2));
            Assertions.assertFalse(collection.contains((int)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((int)Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains((int)Character.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Integer.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Long.MIN_VALUE));
            Assertions.assertFalse(collection.contains((long)Integer.MIN_VALUE));
            Assertions.assertFalse(collection.contains((long)Short.MIN_VALUE));
            Assertions.assertFalse(collection.contains((long)Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains((long)-2));
            Assertions.assertFalse(collection.contains((long)-1));
            Assertions.assertFalse(collection.contains((long)0));
            Assertions.assertFalse(collection.contains((long)1));
            Assertions.assertFalse(collection.contains((long)2));
            Assertions.assertFalse(collection.contains((long)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((long)Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains((long)Character.MAX_VALUE));
            Assertions.assertFalse(collection.contains((long)Integer.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Long.MAX_VALUE));
            Assertions.assertFalse(collection.contains(Float.NEGATIVE_INFINITY));
            Assertions.assertFalse(collection.contains((float)(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains((float)TypeUtil.MIN_SAFE_LONG));
            Assertions.assertFalse(collection.contains((float)(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((float)TypeUtil.MIN_SAFE_INT));
            Assertions.assertFalse(collection.contains((float)Long.MIN_VALUE));
            Assertions.assertFalse(collection.contains((float)Integer.MIN_VALUE));
            Assertions.assertFalse(collection.contains((float)Short.MIN_VALUE));
            Assertions.assertFalse(collection.contains((float)Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains((float)-2));
            Assertions.assertFalse(collection.contains((float)-1));
            Assertions.assertFalse(collection.contains((float)0.0));
            Assertions.assertFalse(collection.contains((float)-0.0));
            Assertions.assertFalse(collection.contains((float)1));
            Assertions.assertFalse(collection.contains((float)2));
            Assertions.assertFalse(collection.contains((float)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((float)Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains((float)Character.MAX_VALUE));
            Assertions.assertFalse(collection.contains((float)Integer.MAX_VALUE));
            Assertions.assertFalse(collection.contains((float)Long.MAX_VALUE));
            Assertions.assertFalse(collection.contains((float)TypeUtil.MAX_SAFE_INT));
            Assertions.assertFalse(collection.contains((float)(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((float)TypeUtil.MAX_SAFE_LONG));
            Assertions.assertFalse(collection.contains((float)(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Float.POSITIVE_INFINITY));
            Assertions.assertFalse(collection.contains(Float.NaN));
            Assertions.assertFalse(collection.contains(Double.NEGATIVE_INFINITY));
            Assertions.assertFalse(collection.contains((double)(TypeUtil.MIN_SAFE_LONG - 1)));
            Assertions.assertFalse(collection.contains((double)TypeUtil.MIN_SAFE_LONG));
            Assertions.assertFalse(collection.contains((double)(TypeUtil.MIN_SAFE_INT - 1)));
            Assertions.assertFalse(collection.contains((double)TypeUtil.MIN_SAFE_INT));
            Assertions.assertFalse(collection.contains((double)Long.MIN_VALUE));
            Assertions.assertFalse(collection.contains((double)Integer.MIN_VALUE));
            Assertions.assertFalse(collection.contains((double)Short.MIN_VALUE));
            Assertions.assertFalse(collection.contains((double)Byte.MIN_VALUE));
            Assertions.assertFalse(collection.contains((double)-2));
            Assertions.assertFalse(collection.contains((double)-1));
            Assertions.assertFalse(collection.contains(0.0));
            Assertions.assertFalse(collection.contains(-0.0));
            Assertions.assertFalse(collection.contains((double)1));
            Assertions.assertFalse(collection.contains((double)2));
            Assertions.assertFalse(collection.contains((double)Byte.MAX_VALUE));
            Assertions.assertFalse(collection.contains((double)Short.MAX_VALUE));
            Assertions.assertFalse(collection.contains((double)Character.MAX_VALUE));
            Assertions.assertFalse(collection.contains((double)Integer.MAX_VALUE));
            Assertions.assertFalse(collection.contains((double)Long.MAX_VALUE));
            Assertions.assertFalse(collection.contains((double)TypeUtil.MAX_SAFE_INT));
            Assertions.assertFalse(collection.contains((double)(TypeUtil.MAX_SAFE_INT + 1)));
            Assertions.assertFalse(collection.contains((double)TypeUtil.MAX_SAFE_LONG));
            Assertions.assertFalse(collection.contains((double)(TypeUtil.MAX_SAFE_LONG + 1)));
            Assertions.assertFalse(collection.contains(Double.POSITIVE_INFINITY));
            Assertions.assertFalse(collection.contains(Double.NaN));
        }

}
