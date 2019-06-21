package omni.impl.set;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.io.Externalizable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
final class FieldAndMethodAccessor{
  private FieldAndMethodAccessor(){
    super();
  }
  private static final Field MODIFIERS_FIELD;
  static{
    try{
      MODIFIERS_FIELD=Field.class.getDeclaredField("modifiers");
      MODIFIERS_FIELD.setAccessible(true);
    }catch(NoSuchFieldException | SecurityException e){
        throw new ExceptionInInitializerError(e);
    }
  }
  static Field prepareFieldForObj(Object obj,String fieldName){
    return prepareFieldForClass(obj.getClass(),fieldName);
  }
  static Method prepareMethodForObj(Object obj,String methodName,Class<?>...params){
    return prepareMethodForClass(obj.getClass(),methodName,params);
  }
  static Field prepareFieldForClassName(String className,String fieldName){
    try{
      return prepareFieldForClass(Class.forName(className),fieldName);
    }catch(ClassNotFoundException e){
      throw new ExceptionInInitializerError(e);
    }
  }
  static Method prepareMethodForClassName(String className,String methodName,Class<?>...params){
    try{
      return prepareMethodForClass(Class.forName(className),methodName,params);
    }catch(ClassNotFoundException e){
      throw new ExceptionInInitializerError(e);
    }
  }
  static Object getValue(Field field,Object obj){
    try{
      return field.get(obj);
    }catch(IllegalArgumentException | IllegalAccessException e){
      throw new RuntimeException(e);
    }
  }
  static int getIntValue(Field field,Object obj){
    try{
      return field.getInt(obj);
    }catch(IllegalArgumentException | IllegalAccessException e){
      throw new RuntimeException(e);
    }
  }
  static Field prepareFieldForClass(Class<?> clazz,String fieldName){
    try{
      Field field=clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      MODIFIERS_FIELD.setInt(field,field.getModifiers() & ~Modifier.FINAL);
      return field;
    }catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
      for(var field:clazz.getDeclaredFields()){
        System.err.println(field);
      }
      throw new ExceptionInInitializerError(e);
    }
  } 
  static Method prepareMethodForClass(Class<?> clazz,String methodName,Class<?>...params){
    try{
      Method method=clazz.getDeclaredMethod(methodName,params);
      method.setAccessible(true);
      return method;
    }catch(NoSuchMethodException e) {
      throw new ExceptionInInitializerError(e);
    }
  }
  /*
  private static Object readObjectHelper(Object obj,ObjectInputStream ois,Method readResolveMethod) throws IOException,ClassNotFoundException{
    Object replacement=ois.readObject();
    try{
      return readResolveMethod.invoke(replacement);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }else if(cause instanceof ClassNotFoundException){
        throw (ClassNotFoundException)cause;
      }
      throw new Error(e);
    }
  }
  */
  private static void writeObjectHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod) throws IOException{
    Object replacement=null;
    try{
      replacement=writeReplaceMethod.invoke(obj);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
    ((Externalizable)replacement).writeExternal(oos);
  }
  private static void writeObjectHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod,Method writeObjectMethod) throws IOException{
    Object replacement=null;
    try{
      replacement=writeReplaceMethod.invoke(obj);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
    try{
      writeObjectMethod.invoke(replacement,oos);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
  }
  private static final char DOLLARSIGN=(char)36;
  static interface BooleanSetImpl{
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
    public static int state(Object obj){
      return ((omni.impl.set.BooleanSetImpl)obj).state;
    }
    public interface Itr{
      static final Field itrStateField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"Itr","itrState");
      static final Field rootField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      public static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    public interface Checked extends BooleanSetImpl{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int state(Object obj){
        return ((omni.impl.set.BooleanSetImpl.Checked)obj).state;
      }
      public interface Itr{
        static final Field itrStateField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","itrState");
        static final Field rootField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.BooleanSetImpl.Checked root(Object obj){
          return (omni.impl.set.BooleanSetImpl.Checked)getValue(rootField,obj);
        }
        public static int itrState(Object obj){
          return getIntValue(itrStateField,obj);
        }
      }
    }
  }
}
