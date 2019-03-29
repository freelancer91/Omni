package omni.impl;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.TypeUtil;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
public enum QueryTestPrimitiveInputType{
  Booleanfalse(boolean.class,Boolean.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,false); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,false); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Booleantrue(boolean.class,Boolean.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,true); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,true); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Byte0(byte.class,Byte.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Bytepos1(byte.class,Byte.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Bytepos2(byte.class,Byte.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Byteneg1(byte.class,Byte.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Character0(char.class,Character.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Characterpos1(char.class,Character.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Characterpos2(char.class,Character.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  CharacterMAX_BYTE_PLUS1(char.class,Character.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((char)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((char)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  CharacterMAX_SHORT_PLUS1(char.class,Character.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((char)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((char)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Short0(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Shortpos1(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Shortpos2(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Shortneg1(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  ShortMAX_BYTE_PLUS1(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((short)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((short)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  ShortMIN_BYTE_MINUS1(short.class,Short.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((short)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((short)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Integer0(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Integerpos1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Integerpos2(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Integerneg1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMAX_BYTE_PLUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((int)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((int)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMIN_BYTE_MINUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((int)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((int)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMAX_SHORT_PLUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((int)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((int)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMIN_SHORT_MINUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((int)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((int)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMAX_CHAR_PLUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((int)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((int)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,TypeUtil.MAX_SAFE_INT+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,TypeUtil.MAX_SAFE_INT+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(int.class,Integer.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,TypeUtil.MIN_SAFE_INT-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,TypeUtil.MIN_SAFE_INT-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Long0(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Longpos1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Longpos2(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Longneg1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_BYTE_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMIN_BYTE_MINUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_SHORT_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMIN_SHORT_MINUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_CHAR_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_SAFE_INT_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)TypeUtil.MAX_SAFE_INT)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)TypeUtil.MAX_SAFE_INT)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMIN_SAFE_INT_MINUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)TypeUtil.MIN_SAFE_INT)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)TypeUtil.MIN_SAFE_INT)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_INT_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMIN_INT_MINUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMAX_SAFE_LONG_PLUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)TypeUtil.MAX_SAFE_LONG)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)TypeUtil.MAX_SAFE_LONG)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  LongMIN_SAFE_LONG_MINUS1(long.class,Long.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((long)TypeUtil.MIN_SAFE_LONG)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((long)TypeUtil.MIN_SAFE_LONG)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Floatpos0(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0.0F); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0.0F); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Floatneg0(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-0.0F); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-0.0F); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Floatpos1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Floatpos2(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Floatneg1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_BYTE_PLUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMIN_BYTE_MINUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_SHORT_PLUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMIN_SHORT_MINUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_CHAR_PLUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_INT_PLUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMIN_INT_MINUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_LONG_PLUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Long.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Long.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMIN_LONG_MINUS1(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((float)Long.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((float)Long.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMIN_FLOAT_VALUE(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Float.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Float.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatMAX_FLOAT_VALUE(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Float.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Float.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  FloatNaN(float.class,Float.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Float.NaN); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Float.NaN); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Doublepos0(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,0.0D); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,0.0D); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Doubleneg0(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)true);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)true);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-0.0D); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-0.0D); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Doublepos1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Doublepos2(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,2); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  Doubleneg1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_BYTE_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Byte.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_BYTE_MINUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Byte.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_SHORT_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Short.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_SHORT_MINUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Short.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_CHAR_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Character.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)TypeUtil.MAX_SAFE_INT)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)TypeUtil.MAX_SAFE_INT)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)TypeUtil.MIN_SAFE_INT)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)TypeUtil.MIN_SAFE_INT)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_INT_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Integer.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_INT_MINUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Integer.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_LONG_PLUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Long.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Long.MAX_VALUE)+1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_LONG_MINUS1(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,((double)Long.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,((double)Long.MIN_VALUE)-1); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_FLOAT_VALUE(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Float.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Float.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_FLOAT_VALUE(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Float.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Float.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMIN_DOUBLE_VALUE(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Double.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Double.MIN_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleMAX_DOUBLE_VALUE(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Double.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Double.MAX_VALUE); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  DoubleNaN(double.class,Double.class){
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
    public boolean addNotEqualsVal(OmniCollection col)
    {
      return ((OmniCollection.OfPrimitive)col).add((boolean)false);
    }
    public void insertNotEqualsValInList(int index,OmniCollection col)
    {
      ((OmniList.OfPrimitive)col).add(index,(boolean)false);
    }
    public boolean attemptAdd(OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      Class<?> clazz=col.getClass();
      Method method;
      try
      {
        //attempt to call with the primitive type
        method=clazz.getDeclaredMethod("add",primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          //if that fails, call with the boxed type
          method=clazz.getDeclaredMethod("add",boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            //if all else fails, call with the object class
            method=clazz.getDeclaredMethod("add",Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        return (boolean)method.invoke(col,Double.NaN); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
    }
    public boolean attemptInsertionInList(int index,OmniCollection col)
    {
      //TODO switch pattern matching would be very handy here
      OmniList list=(OmniList)col; //throw if not correct type
      Class<?> clazz=list.getClass();
      Method method;
      try
      {
        method=clazz.getDeclaredMethod("add",int.class,primitiveClass);
      }
      catch(NoSuchMethodException e)
      {
        try
        {
          method=clazz.getDeclaredMethod("add",int.class,boxedClass);
        }
        catch(NoSuchMethodException e1)
        {
          try
          {
            method=clazz.getDeclaredMethod("add",int.class,Object.class);
          }
          catch(NoSuchMethodException e2)
          {
            return false;
          }
        }
      }
      try
      {
        method.invoke(list,index,Double.NaN); 
      }
      catch(IllegalAccessException|InvocationTargetException e)
      {
        throw new RuntimeException(e);
      }
      return true;
    }
  },
  ;
  public final Class<?> primitiveClass;
  public final Class<?> boxedClass;
  private QueryTestPrimitiveInputType(Class<?> primitiveClass,Class<?> boxedClass){
    this.primitiveClass=primitiveClass;
    this.boxedClass=boxedClass;
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
  public abstract boolean attemptInsertionInList(int index,OmniCollection col);
  public abstract boolean addNotEqualsVal(OmniCollection col);
  public abstract void insertNotEqualsValInList(int index,OmniCollection col);
}
