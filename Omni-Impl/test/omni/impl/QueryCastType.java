package omni.impl;

public enum QueryCastType{
    ToPrimitive{
        @Override
        public Class<?> getParamClass(DataType inputType){
            return inputType.primitiveClass;
        }
    },
    ToBoxed{
        @Override
        public Class<?> getParamClass(DataType inputType){
            return inputType.boxedClass;
        }
    },
    ToObject{
        @Override
        public Class<?> getParamClass(DataType inputType){
            return Object.class;
        }
        @Override
        public String getRemoveValMethodName(){
            return "remove";
        }
    };
    public String getRemoveValMethodName(){
        return "removeVal";
    }
    public abstract Class<?> getParamClass(DataType inputType);
}
