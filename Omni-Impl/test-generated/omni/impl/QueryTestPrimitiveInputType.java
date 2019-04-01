package omni.impl;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.TypeUtil;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
public enum QueryTestPrimitiveInputType{
  Booleanfalse(boolean.class,Boolean.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((boolean)(false));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((boolean)(false));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((boolean)(false));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((boolean)(false));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((boolean)(false));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Boolean)(boolean)(false));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Boolean)(boolean)(false));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Boolean)(boolean)(false));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Boolean)(boolean)(false));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Boolean)(boolean)(false));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(boolean)(false));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(boolean)(false));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(boolean)(false));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(boolean)(false));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(boolean)(false));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)(false));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)(false));
    }
  },
  Booleantrue(boolean.class,Boolean.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((boolean)(true));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((boolean)(true));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((boolean)(true));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((boolean)(true));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((boolean)(true));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Boolean)(boolean)(true));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Boolean)(boolean)(true));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Boolean)(boolean)(true));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Boolean)(boolean)(true));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Boolean)(boolean)(true));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(boolean)(true));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(boolean)(true));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(boolean)(true));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(boolean)(true));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(boolean)(true));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)(true));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)(true));
    }
  },
  Byte0(byte.class,Byte.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((byte)(0));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((byte)(0));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((byte)(0));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((byte)(0));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((byte)(0));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Byte)(byte)(0));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Byte)(byte)(0));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Byte)(byte)(0));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Byte)(byte)(0));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Byte)(byte)(0));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(byte)(0));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(byte)(0));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(byte)(0));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(byte)(0));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(byte)(0));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(byte)(0));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0)==1));
    }
  },
  Bytepos1(byte.class,Byte.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((byte)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((byte)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((byte)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((byte)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((byte)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Byte)(byte)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Byte)(byte)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Byte)(byte)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Byte)(byte)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Byte)(byte)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(byte)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(byte)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(byte)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(byte)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(byte)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(byte)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Bytepos2(byte.class,Byte.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((byte)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((byte)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((byte)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((byte)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((byte)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Byte)(byte)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Byte)(byte)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Byte)(byte)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Byte)(byte)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Byte)(byte)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(byte)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(byte)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(byte)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(byte)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(byte)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(byte)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Byteneg1(byte.class,Byte.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((byte)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((byte)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((byte)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((byte)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((byte)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Byte)(byte)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Byte)(byte)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Byte)(byte)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Byte)(byte)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Byte)(byte)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(byte)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(byte)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(byte)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(byte)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(byte)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(byte)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  Character0(char.class,Character.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((char)(0));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((char)(0));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((char)(0));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((char)(0));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((char)(0));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Character)(char)(0));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Character)(char)(0));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Character)(char)(0));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Character)(char)(0));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Character)(char)(0));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(char)(0));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(char)(0));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(char)(0));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(char)(0));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(char)(0));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(char)(0));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0)==1));
    }
  },
  Characterpos1(char.class,Character.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((char)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((char)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((char)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((char)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((char)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Character)(char)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Character)(char)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Character)(char)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Character)(char)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Character)(char)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(char)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(char)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(char)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(char)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(char)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(char)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Characterpos2(char.class,Character.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((char)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((char)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((char)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((char)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((char)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Character)(char)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Character)(char)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Character)(char)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Character)(char)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Character)(char)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(char)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(char)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(char)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(char)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(char)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(char)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  CharacterMAX_BYTE_PLUS1(char.class,Character.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((char)(((char)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((char)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((char)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((char)Byte.MAX_VALUE)+1)==1));
    }
  },
  CharacterMAX_SHORT_PLUS1(char.class,Character.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((char)(((char)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(char)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((char)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((char)Short.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((char)Short.MAX_VALUE)+1)==1));
    }
  },
  Short0(short.class,Short.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(0));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(0));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(0));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(0));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(0));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(0));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(0));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(0));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(0));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(0));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(0));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(0));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(0));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(0));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(0));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(0));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0)==1));
    }
  },
  Shortpos1(short.class,Short.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Shortpos2(short.class,Short.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Shortneg1(short.class,Short.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  ShortMAX_BYTE_PLUS1(short.class,Short.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(((short)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((short)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((short)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((short)Byte.MAX_VALUE)+1)==1));
    }
  },
  ShortMIN_BYTE_MINUS1(short.class,Short.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((short)(((short)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((short)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((short)Byte.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((short)Byte.MIN_VALUE)-1)==1));
    }
  },
  Integer0(int.class,Integer.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(0));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(0));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(0));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(0));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(0));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(0));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(0));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(0));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(0));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(0));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(0));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(0));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(0));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(0));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(0));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(0));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0)==1));
    }
  },
  Integerpos1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Integerpos2(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Integerneg1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  IntegerMAX_BYTE_PLUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(((int)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((int)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((int)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((int)Byte.MAX_VALUE)+1)==1));
    }
  },
  IntegerMIN_BYTE_MINUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(((int)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((int)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((int)Byte.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((int)Byte.MIN_VALUE)-1)==1));
    }
  },
  IntegerMAX_SHORT_PLUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(((int)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((int)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((int)Short.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((int)Short.MAX_VALUE)+1)==1));
    }
  },
  IntegerMIN_SHORT_MINUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(((int)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((int)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((int)Short.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((int)Short.MIN_VALUE)-1)==1));
    }
  },
  IntegerMAX_CHAR_PLUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(((int)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((int)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((int)Character.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((int)Character.MAX_VALUE)+1)==1));
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(TypeUtil.MAX_SAFE_INT+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(TypeUtil.MAX_SAFE_INT+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((TypeUtil.MAX_SAFE_INT+1)==1));
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(int.class,Integer.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(TypeUtil.MIN_SAFE_INT-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(TypeUtil.MIN_SAFE_INT-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((TypeUtil.MIN_SAFE_INT-1)==1));
    }
  },
  Long0(long.class,Long.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(0));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(0));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(0));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(0));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(0));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(0));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(0));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(0));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(0));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(0));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(0));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(0));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(0));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(0));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(0));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(0));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0)==1));
    }
  },
  Longpos1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Longpos2(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Longneg1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  LongMAX_BYTE_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Byte.MAX_VALUE)+1)==1));
    }
  },
  LongMIN_BYTE_MINUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Byte.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Byte.MIN_VALUE)-1)==1));
    }
  },
  LongMAX_SHORT_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Short.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Short.MAX_VALUE)+1)==1));
    }
  },
  LongMIN_SHORT_MINUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Short.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Short.MIN_VALUE)-1)==1));
    }
  },
  LongMAX_CHAR_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Character.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Character.MAX_VALUE)+1)==1));
    }
  },
  LongMAX_SAFE_INT_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)TypeUtil.MAX_SAFE_INT)+1)==1));
    }
  },
  LongMIN_SAFE_INT_MINUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)TypeUtil.MIN_SAFE_INT)-1)==1));
    }
  },
  LongMAX_INT_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Integer.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Integer.MAX_VALUE)+1)==1));
    }
  },
  LongMIN_INT_MINUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)Integer.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)Integer.MIN_VALUE)-1)==1));
    }
  },
  LongMAX_SAFE_LONG_PLUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)TypeUtil.MAX_SAFE_LONG)+1)==1));
    }
  },
  LongMIN_SAFE_LONG_MINUS1(long.class,Long.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((long)TypeUtil.MIN_SAFE_LONG)-1)==1));
    }
  },
  Floatpos0(float.class,Float.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(0.0F));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(0.0F));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(0.0F));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(0.0F));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(0.0F));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(0.0F));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(0.0F));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(0.0F));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(0.0F));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(0.0F));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(0.0F));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(0.0F));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(0.0F));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(0.0F));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(0.0F));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(0.0F));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0.0F));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0.0F));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0.0F));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0.0F));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0.0F));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0.0F));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0.0F));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0.0F)==1));
    }
  },
  Floatneg0(float.class,Float.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(-0.0F));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(-0.0F));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(-0.0F));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(-0.0F));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(-0.0F));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(-0.0F));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(-0.0F));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(-0.0F));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(-0.0F));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(-0.0F));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(-0.0F));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(-0.0F));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(-0.0F));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(-0.0F));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(-0.0F));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(-0.0F));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-0.0F));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-0.0F));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-0.0F));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-0.0F));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-0.0F));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-0.0F));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-0.0F));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-0.0F)==1));
    }
  },
  Floatpos1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Floatpos2(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Floatneg1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  FloatMAX_BYTE_PLUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Byte.MAX_VALUE)+1)==1));
    }
  },
  FloatMIN_BYTE_MINUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Byte.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Byte.MIN_VALUE)-1)==1));
    }
  },
  FloatMAX_SHORT_PLUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Short.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Short.MAX_VALUE)+1)==1));
    }
  },
  FloatMIN_SHORT_MINUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Short.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Short.MIN_VALUE)-1)==1));
    }
  },
  FloatMAX_CHAR_PLUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Character.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Character.MAX_VALUE)+1)==1));
    }
  },
  FloatMAX_INT_PLUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Integer.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Integer.MAX_VALUE)+1)==1));
    }
  },
  FloatMIN_INT_MINUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Integer.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Integer.MIN_VALUE)-1)==1));
    }
  },
  FloatMAX_LONG_PLUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Long.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Long.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Long.MAX_VALUE)+1)==1));
    }
  },
  FloatMIN_LONG_MINUS1(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(((float)Long.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((float)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((float)Long.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((float)Long.MIN_VALUE)-1)==1));
    }
  },
  FloatMIN_FLOAT_VALUE(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(Float.MIN_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(Float.MIN_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(Float.MIN_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(Float.MIN_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(Float.MIN_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(Float.MIN_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(Float.MIN_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(Float.MIN_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(Float.MIN_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(Float.MIN_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(Float.MIN_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(Float.MIN_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(Float.MIN_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Float.MIN_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Float.MIN_VALUE)==1));
    }
  },
  FloatMAX_FLOAT_VALUE(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(Float.MAX_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(Float.MAX_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(Float.MAX_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(Float.MAX_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(Float.MAX_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(Float.MAX_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(Float.MAX_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(Float.MAX_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(Float.MAX_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(Float.MAX_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(Float.MAX_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(Float.MAX_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(Float.MAX_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Float.MAX_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Float.MAX_VALUE)==1));
    }
  },
  FloatNaN(float.class,Float.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((float)(Float.NaN));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((float)(Float.NaN));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((float)(Float.NaN));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((float)(Float.NaN));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((float)(Float.NaN));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Float)(float)(Float.NaN));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Float)(float)(Float.NaN));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Float)(float)(Float.NaN));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Float)(float)(Float.NaN));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Float)(float)(Float.NaN));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(float)(Float.NaN));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(float)(Float.NaN));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(float)(Float.NaN));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(float)(Float.NaN));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(float)(Float.NaN));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(float)(Float.NaN));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Float.NaN));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Float.NaN));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Float.NaN));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Float.NaN));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Float.NaN));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Float.NaN));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Float.NaN));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Float.NaN)==1));
    }
  },
  Doublepos0(double.class,Double.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(0.0D));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(0.0D));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(0.0D));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(0.0D));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(0.0D));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(0.0D));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(0.0D));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(0.0D));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(0.0D));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(0.0D));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(0.0D));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(0.0D));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(0.0D));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(0.0D));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(0.0D));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(0.0D));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(0.0D));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(0.0D));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(0.0D));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(0.0D));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(0.0D));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(0.0D));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(0.0D));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((0.0D)==1));
    }
  },
  Doubleneg0(double.class,Double.class,true){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(-0.0D));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(-0.0D));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(-0.0D));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(-0.0D));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(-0.0D));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(-0.0D));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(-0.0D));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(-0.0D));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(-0.0D));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(-0.0D));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(-0.0D));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(-0.0D));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(-0.0D));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(-0.0D));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(-0.0D));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)true);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(-0.0D));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-0.0D));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-0.0D));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-0.0D));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-0.0D));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-0.0D));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-0.0D));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-0.0D));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-0.0D)==1));
    }
  },
  Doublepos1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((1)==1));
    }
  },
  Doublepos2(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(2));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(2));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(2));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(2));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(2));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(2));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(2));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(2));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(2));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(2));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(2));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(2));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(2));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(2));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(2));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(2));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(2));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(2));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(2));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(2));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(2));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(2));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(2));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((2)==1));
    }
  },
  Doubleneg1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((-1)==1));
    }
  },
  DoubleMAX_BYTE_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Byte.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Byte.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Byte.MAX_VALUE)+1)==1));
    }
  },
  DoubleMIN_BYTE_MINUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Byte.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Byte.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Byte.MIN_VALUE)-1)==1));
    }
  },
  DoubleMAX_SHORT_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Short.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Short.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Short.MAX_VALUE)+1)==1));
    }
  },
  DoubleMIN_SHORT_MINUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Short.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Short.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Short.MIN_VALUE)-1)==1));
    }
  },
  DoubleMAX_CHAR_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Character.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Character.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Character.MAX_VALUE)+1)==1));
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)TypeUtil.MAX_SAFE_INT)+1)==1));
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)TypeUtil.MIN_SAFE_INT)-1)==1));
    }
  },
  DoubleMAX_INT_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Integer.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Integer.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Integer.MAX_VALUE)+1)==1));
    }
  },
  DoubleMIN_INT_MINUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Integer.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Integer.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Integer.MIN_VALUE)-1)==1));
    }
  },
  DoubleMAX_LONG_PLUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Long.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Long.MAX_VALUE)+1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Long.MAX_VALUE)+1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Long.MAX_VALUE)+1)==1));
    }
  },
  DoubleMIN_LONG_MINUS1(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(((double)Long.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(((double)Long.MIN_VALUE)-1));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(((double)Long.MIN_VALUE)-1));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((((double)Long.MIN_VALUE)-1)==1));
    }
  },
  DoubleMIN_FLOAT_VALUE(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(Float.MIN_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(Float.MIN_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(Float.MIN_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(Float.MIN_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(Float.MIN_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(Float.MIN_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(Float.MIN_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(Float.MIN_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(Float.MIN_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(Float.MIN_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(Float.MIN_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(Float.MIN_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(Float.MIN_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Float.MIN_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Float.MIN_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Float.MIN_VALUE)==1));
    }
  },
  DoubleMAX_FLOAT_VALUE(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(Float.MAX_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(Float.MAX_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(Float.MAX_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(Float.MAX_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(Float.MAX_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(Float.MAX_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(Float.MAX_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(Float.MAX_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(Float.MAX_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(Float.MAX_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(Float.MAX_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(Float.MAX_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(Float.MAX_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Float.MAX_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Float.MAX_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Float.MAX_VALUE)==1));
    }
  },
  DoubleMIN_DOUBLE_VALUE(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(Double.MIN_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(Double.MIN_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(Double.MIN_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(Double.MIN_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(Double.MIN_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(Double.MIN_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(Double.MIN_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(Double.MIN_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(Double.MIN_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(Double.MIN_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(Double.MIN_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(Double.MIN_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(Double.MIN_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Double.MIN_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Double.MIN_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Double.MIN_VALUE)==1));
    }
  },
  DoubleMAX_DOUBLE_VALUE(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(Double.MAX_VALUE));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(Double.MAX_VALUE));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(Double.MAX_VALUE));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(Double.MAX_VALUE));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(Double.MAX_VALUE));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(Double.MAX_VALUE));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(Double.MAX_VALUE));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(Double.MAX_VALUE));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(Double.MAX_VALUE));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(Double.MAX_VALUE));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(Double.MAX_VALUE));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(Double.MAX_VALUE));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(Double.MAX_VALUE));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Double.MAX_VALUE));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Double.MAX_VALUE));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Double.MAX_VALUE)==1));
    }
  },
  DoubleNaN(double.class,Double.class,false){
    @Override public boolean invokecontains(OmniCollection col){return col.contains((double)(Double.NaN));}
    @Override public boolean invokeremoveVal(OmniCollection col){return col.removeVal((double)(Double.NaN));}
    @Override public int invokeindexOf(OmniCollection col){return ((OmniList)col).indexOf((double)(Double.NaN));}
    @Override public int invokelastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((double)(Double.NaN));}
    @Override public int invokesearch(OmniCollection col){return ((OmniStack)col).search((double)(Double.NaN));}
    @Override public boolean invokeBoxedcontains(OmniCollection col){return col.contains((Double)(double)(Double.NaN));}
    @Override public boolean invokeBoxedremoveVal(OmniCollection col){return col.removeVal((Double)(double)(Double.NaN));}
    @Override public int invokeBoxedindexOf(OmniCollection col){return ((OmniList)col).indexOf((Double)(double)(Double.NaN));}
    @Override public int invokeBoxedlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Double)(double)(Double.NaN));}
    @Override public int invokeBoxedsearch(OmniCollection col){return ((OmniStack)col).search((Double)(double)(Double.NaN));}
    @Override public boolean invokeObjectcontains(OmniCollection col){return col.contains((Object)(double)(Double.NaN));}
    @Override public boolean invokeObjectremoveVal(OmniCollection col){return col.remove((Object)(double)(Double.NaN));}
    @Override public int invokeObjectindexOf(OmniCollection col){return ((OmniList)col).indexOf((Object)(double)(Double.NaN));}
    @Override public int invokeObjectlastIndexOf(OmniCollection col){return ((OmniList)col).lastIndexOf((Object)(double)(Double.NaN));}
    @Override public int invokeObjectsearch(OmniCollection col){return ((OmniStack)col).search((Object)(double)(Double.NaN));}
    public boolean addNotEqualsVal(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(boolean)false);}
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public boolean attemptAdd(OmniCollection col){
      if(col instanceof OmniCollection.OfRef){return ((OmniCollection.OfRef)col).add((Object)(double)(Double.NaN));}
      if(col instanceof OmniCollection.OfDouble){return ((OmniCollection.OfDouble)col).add((double)(Double.NaN));}
      if(col instanceof OmniCollection.OfFloat){return ((OmniCollection.OfFloat)col).add((float)(Double.NaN));}
      if(col instanceof OmniCollection.OfLong){return ((OmniCollection.OfLong)col).add((long)(Double.NaN));}
      if(col instanceof OmniCollection.OfInt){return ((OmniCollection.OfInt)col).add((int)(Double.NaN));}
      if(col instanceof OmniCollection.OfShort){return ((OmniCollection.OfShort)col).add((short)(Double.NaN));}
      if(col instanceof OmniCollection.OfChar){return ((OmniCollection.OfChar)col).add((char)(Double.NaN));}
      if(col instanceof OmniCollection.OfByte){return ((OmniCollection.OfByte)col).add((byte)(Double.NaN));}
      return ((OmniCollection.OfPrimitive)col).add((boolean)((Double.NaN)==1));
    }
  },
  ;
  public final Class<?> primitiveClass;
  public final Class<?> boxedClass;
  private boolean notEqualsVal;
  private QueryTestPrimitiveInputType(Class<?> primitiveClass,Class<?> boxedClass,boolean notEqualsVal){
    this.primitiveClass=primitiveClass;
    this.boxedClass=boxedClass;
    this.notEqualsVal=notEqualsVal;
  }
  public abstract boolean invokecontains(OmniCollection col);
  public abstract boolean invokeremoveVal(OmniCollection col);
  public abstract int invokeindexOf(OmniCollection col);
  public abstract int invokelastIndexOf(OmniCollection col);
  public abstract int invokesearch(OmniCollection col);
  public abstract boolean invokeBoxedcontains(OmniCollection col);
  public abstract boolean invokeBoxedremoveVal(OmniCollection col);
  public abstract int invokeBoxedindexOf(OmniCollection col);
  public abstract int invokeBoxedlastIndexOf(OmniCollection col);
  public abstract int invokeBoxedsearch(OmniCollection col);
  public abstract boolean invokeObjectcontains(OmniCollection col);
  public abstract boolean invokeObjectremoveVal(OmniCollection col);
  public abstract int invokeObjectindexOf(OmniCollection col);
  public abstract int invokeObjectlastIndexOf(OmniCollection col);
  public abstract int invokeObjectsearch(OmniCollection col);
  public abstract boolean attemptAdd(OmniCollection col);
  public abstract boolean addNotEqualsVal(OmniCollection col);
}
