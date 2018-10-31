package omni.impl2;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import omni.util.ArrCopy;
import omni.util.OmniArray;
interface ArrSeq{
    static int uncheckedAccumulate(int begin,int end,int seed,IntBinaryOperator operator){
        for(;;++begin){
            seed=operator.applyAsInt(begin,seed);
            if(begin==end){
                return seed;
            }
        }
    }
    static void uncheckedForEachForward(int begin,int end,IntConsumer action){
        for(;;++begin){
            action.accept(begin);
            if(begin==end){
                return;
            }
        }
    }
    static void uncheckedForEachReverse(int begin,int end,IntConsumer action){
        for(;;--end){
            action.accept(end);
            if(begin==end){
                return;
            }
        }
    }
    static boolean uncheckedAnyMatches(int begin,int end,IntPredicate predicate){
        for(;!predicate.test(begin);++begin){
            if(begin==end){
                return false;
            }
        }
        return true;
    }
    static int uncheckedFirstIndex(int begin,int end,IntPredicate predicate){
        int index;
        for(index=begin;!predicate.test(index);++index){
            if(index==end){
                return -1;
            }
        }
        return index-begin;
    }
    static int uncheckedLastIndex(int begin,int end,IntPredicate predicate){
        for(;!predicate.test(end);--end){
            if(begin==end){
                return -1;
            }
        }
        return end-begin;
    }
    static int uncheckedFirstIndex(int end,IntPredicate predicate){
        int index;
        for(index=0;!predicate.test(index);++index){
            if(index==end){
                return -1;
            }
        }
        return index;
    }
    static int uncheckedLastIndex(int end,IntPredicate predicate){
        for(;!predicate.test(end);--end){
            if(0==end){
                return -1;
            }
        }
        return end;
    }
    static int uncheckedSearch(int size,IntPredicate predicate){
        int index;
        for(index=size-1;!predicate.test(index);--index){
            if(index==0){
                return -1;
            }
        }
        return size-index;
    }
    static class OfRef<E>{
        static <E> void uncheckedSort(Object[] arr,int begin,int end,Comparator<? super E> sorter){
            // TODO
        }
        static void uncheckedSort(Object[] arr,int begin,int end){
            // TODO
        }
        static void uncheckedReverseSort(Object[] arr,int begin,int end){
            // TODO
        }
        @SuppressWarnings("unchecked")
        static <E> void uncheckedReplaceAll(Object[] arr,int offset,int bound,Function<? super E,? extends E> operator){
            do{
                arr[offset]=operator.apply((E)arr[offset]);
            }while(++offset!=bound);
        }

        static void eraseIndex(Object[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
            arr[newSize]=null;
        }

        transient int size;
        transient Object[] arr;
        OfRef(int size,Object[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfRef(){
            this.arr=OmniArray.OfRef.DEFAULT_ARR;
        }
        OfRef(int capacity){
            switch(capacity){
            default:
                this.arr=new Object[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfRef.DEFAULT_ARR;
            case 0:
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
    }
    static class OfFloat{
        public void push(float val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(float[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(float val){
            float[] arr;
            if((arr=this.arr)==OmniArray.OfFloat.DEFAULT_ARR){
                this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new float[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(float val,int size){
            float[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient float[] arr;
        OfFloat(int size,float[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfFloat(){
            this.arr=OmniArray.OfFloat.DEFAULT_ARR;
        }
        OfFloat(int capacity){
            switch(capacity){
            default:
                this.arr=new float[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfFloat.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfLong{
        public void push(long val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(long[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(long val){
            long[] arr;
            if((arr=this.arr)==OmniArray.OfLong.DEFAULT_ARR){
                this.arr=arr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new long[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(long val,int size){
            long[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new long[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient long[] arr;
        OfLong(int size,long[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfLong(){
            this.arr=OmniArray.OfLong.DEFAULT_ARR;
        }
        OfLong(int capacity){
            switch(capacity){
            default:
                this.arr=new long[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfLong.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfInt{
        public void push(int val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(int[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(int val){
            int[] arr;
            if((arr=this.arr)==OmniArray.OfInt.DEFAULT_ARR){
                this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new int[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(int val,int size){
            int[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient int[] arr;
        OfInt(int size,int[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfInt(){
            this.arr=OmniArray.OfInt.DEFAULT_ARR;
        }
        OfInt(int capacity){
            switch(capacity){
            default:
                this.arr=new int[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfInt.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfShort{
        public void push(short val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(short[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(short val){
            short[] arr;
            if((arr=this.arr)==OmniArray.OfShort.DEFAULT_ARR){
                this.arr=arr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new short[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(short val,int size){
            short[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient short[] arr;
        OfShort(int size,short[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfShort(){
            this.arr=OmniArray.OfShort.DEFAULT_ARR;
        }
        OfShort(int capacity){
            switch(capacity){
            default:
                this.arr=new short[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfShort.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfChar{
        public void push(char val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(char[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(char val){
            char[] arr;
            if((arr=this.arr)==OmniArray.OfChar.DEFAULT_ARR){
                this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new char[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(char val,int size){
            char[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient char[] arr;
        OfChar(int size,char[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfChar(){
            this.arr=OmniArray.OfChar.DEFAULT_ARR;
        }
        OfChar(int capacity){
            switch(capacity){
            default:
                this.arr=new char[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfChar.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfByte{
        public void push(byte val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(byte[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(byte val){
            byte[] arr;
            if((arr=this.arr)==OmniArray.OfByte.DEFAULT_ARR){
                this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new byte[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(byte val,int size){
            byte[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient byte[] arr;
        OfByte(int size,byte[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfByte(){
            this.arr=OmniArray.OfByte.DEFAULT_ARR;
        }
        OfByte(int capacity){
            switch(capacity){
            default:
                this.arr=new byte[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfByte.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfDouble{
        public void push(double val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(double[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(double val){
            double[] arr;
            if((arr=this.arr)==OmniArray.OfDouble.DEFAULT_ARR){
                this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new double[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(double val,int size){
            double[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient double[] arr;
        OfDouble(int size,double[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfDouble(){
            this.arr=OmniArray.OfDouble.DEFAULT_ARR;
        }
        OfDouble(int capacity){
            switch(capacity){
            default:
                this.arr=new double[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfDouble.DEFAULT_ARR;
            case 0:
            }
        }
    }
    static class OfBoolean{
        public void push(boolean val){
            int size;
            if((size=this.size)!=0){
                uncheckedAppend(val,size);
            }else{
                uncheckedInit(val);
            }
        }
        public int size(){
            return size;
        }
        public boolean isEmpty(){
            return size==0;
        }
        static void eraseIndex(boolean[] arr,int index,int newSize){
            ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        }
        void uncheckedInit(boolean val){
            boolean[] arr;
            if((arr=this.arr)==OmniArray.OfBoolean.DEFAULT_ARR){
                this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
            }else if(arr==null){
                this.arr=new boolean[1];
            }
            arr[0]=val;
            this.size=1;
        }
        void uncheckedAppend(boolean val,int size){
            boolean[] arr;
            if((arr=this.arr).length==size){
                ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,size);
                this.arr=arr;
            }
            arr[size]=val;
            this.size=size+1;
        }
        transient int size;
        transient boolean[] arr;
        OfBoolean(int size,boolean[] arr){
            this.size=size;
            this.arr=arr;
        }
        OfBoolean(){
            this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
        }
        OfBoolean(int capacity){
            switch(capacity){
            default:
                this.arr=new boolean[capacity];
                return;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
                this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
            case 0:
            }
        }
    }
}
