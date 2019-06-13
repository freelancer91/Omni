package omni.impl;

import java.lang.reflect.Method;
public enum QueryMethod{
    Contains{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "contains";
        }
    },
    RemoveVal{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return queryCastType.getRemoveValMethodName();
        }
    },
    IndexOf{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "indexOf";
        }
    },
    LastIndexOf{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "lastIndexOf";
        }
    },
    RemoveFirstOccurrence{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "removeFirstOccurrence";
        }
    },
    RemoveLastOccurrence{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "removeLastOccurrence";
        }
    },
    Search{
        @Override
        String getMethodName(QueryCastType queryCastType){
            return "search";
        }
    };
    abstract String getMethodName(QueryCastType queryCastType);
    public Method getQueryMethod(Class<?> collectionClass,QueryCastType queryCastType,DataType inputType){
        try{
            return collectionClass.getMethod(getMethodName(queryCastType),queryCastType.getParamClass(inputType));
        }catch(NoSuchMethodException | SecurityException e){
            throw new Error(e);
        }
    }
}
