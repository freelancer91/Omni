package omni.impl.seq.arr;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.CharUnaryOperator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.FloatUnaryOperator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.function.ShortUnaryOperator;
import omni.util.ArrCopy;
import omni.util.HashUtils;
public interface ArrSeqUtil{
    // TODO create a template and use macros to reduce the amount of hand-written
    // code
    public static void eraseIndexHelper(boolean[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(byte[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(char[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(double[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(float[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(int[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(long[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static void eraseIndexHelper(Object[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
        arr[newSize]=null;
    }
    public static void eraseIndexHelper(short[] arr,int index,int newSize){
        ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    }
    public static int forwardHashCode(boolean[] arr,int offset,int bound){
        int hash=31+Boolean.hashCode(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+Boolean.hashCode(arr[offset]);
        }
        return hash;
    }
    public static int forwardHashCode(byte[] arr,int offset,int bound){
        int hash=31+arr[offset];
        while(++offset!=bound){
            hash=hash*31+arr[offset];
        }
        return hash;
    }
    public static int forwardHashCode(char[] arr,int offset,int bound){
        int hash=31+arr[offset];
        while(++offset!=bound){
            hash=hash*31+arr[offset];
        }
        return hash;
    }
    public static int forwardHashCode(double[] arr,int offset,int bound){
        int hash=31+HashUtils.hashDouble(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+HashUtils.hashDouble(arr[offset]);
        }
        return hash;
    }
    public static int forwardHashCode(float[] arr,int offset,int bound){
        int hash=31+HashUtils.hashFloat(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+HashUtils.hashFloat(arr[offset]);
        }
        return hash;
    }
    public static int forwardHashCode(int[] arr,int offset,int bound){
        int hash=31+arr[offset];
        while(++offset!=bound){
            hash=hash*31+arr[offset];
        }
        return hash;
    }
    public static int forwardHashCode(long[] arr,int offset,int bound){
        int hash=31+Long.hashCode(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+Long.hashCode(arr[offset]);
        }
        return hash;
    }
    public static int forwardHashCode(Object[] arr,int offset,int bound){
        int hash=31+Objects.hashCode(arr[offset]);
        while(++offset!=bound){
            hash=hash*31+Objects.hashCode(arr[offset]);
        }
        return hash;
    }
    public static int forwardHashCode(short[] arr,int offset,int bound){
        int hash=31+arr[offset];
        while(++offset!=bound){
            hash=hash*31+arr[offset];
        }
        return hash;
    }
    public static void forwardToString(boolean[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(byte[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(char[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(double[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(float[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(int[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(long[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(Object[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static void forwardToString(short[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
    }
    public static int reverseHashCode(boolean[] arr,int offset,int bound){
        int hash=31+Boolean.hashCode(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+Boolean.hashCode(arr[--bound]);
        }
        return hash;
    }
    public static int reverseHashCode(byte[] arr,int offset,int bound){
        int hash=31+arr[--bound];
        while(bound!=offset){
            hash=hash*31+arr[--bound];
        }
        return hash;
    }
    public static int reverseHashCode(char[] arr,int offset,int bound){
        int hash=31+arr[--bound];
        while(bound!=offset){
            hash=hash*31+arr[--bound];
        }
        return hash;
    }
    public static int reverseHashCode(double[] arr,int offset,int bound){
        int hash=31+HashUtils.hashDouble(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+HashUtils.hashDouble(arr[--bound]);
        }
        return hash;
    }
    public static int reverseHashCode(float[] arr,int offset,int bound){
        int hash=31+HashUtils.hashFloat(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+HashUtils.hashFloat(arr[--bound]);
        }
        return hash;
    }
    public static int reverseHashCode(int[] arr,int offset,int bound){
        int hash=31+arr[--bound];
        while(bound!=offset){
            hash=hash*31+arr[--bound];
        }
        return hash;
    }
    public static int reverseHashCode(long[] arr,int offset,int bound){
        int hash=31+Long.hashCode(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+Long.hashCode(arr[--bound]);
        }
        return hash;
    }
    public static int reverseHashCode(Object[] arr,int offset,int bound){
        int hash=31+Objects.hashCode(arr[--bound]);
        while(bound!=offset){
            hash=hash*31+Objects.hashCode(arr[--bound]);
        }
        return hash;
    }
    public static int reverseHashCode(short[] arr,int offset,int bound){
        int hash=31+arr[--bound];
        while(bound!=offset){
            hash=hash*31+arr[--bound];
        }
        return hash;
    }
    public static void reverseToString(boolean[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(byte[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(char[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(double[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(float[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(int[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(long[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(Object[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static void reverseToString(short[] arr,int offset,int bound,StringBuilder builder){
        for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
    }
    public static boolean uncheckedcontains(boolean[] arr,int offset,int bound,boolean val){
        while(arr[offset]^val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(byte[] arr,int offset,int bound,int val){
        while(arr[offset]!=val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(char[] arr,int offset,int bound,int val){
        while(arr[offset]!=val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(double[] arr,int offset,int bound,double val){
        if(val==val){
            return uncheckedcontainsBits(arr,offset,bound,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontainsNaN(arr,offset,bound);
    }
    public static boolean uncheckedcontains(float[] arr,int offset,int bound,float val){
        if(val==val){
            return uncheckedcontainsBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedcontainsNaN(arr,offset,bound);
    }
    public static boolean uncheckedcontains(int[] arr,int offset,int bound,int val){
        while(arr[offset]!=val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(long[] arr,int offset,int bound,long val){
        while(arr[offset]!=val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(Object[] arr,int offset,int bound,Predicate<Object> pred){
        while(!pred.test(arr[offset])){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains(short[] arr,int offset,int bound,int val){
        while(arr[offset]!=val){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains0(double[] arr,int offset,int bound){
        while(arr[offset]!=0){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontains0(float[] arr,int offset,int bound){
        while(arr[offset]!=0){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontainsBits(double[] arr,int offset,int bound,long bits){
        while(Double.doubleToRawLongBits(arr[offset])!=bits){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontainsBits(float[] arr,int offset,int bound,int bits){
        while(Float.floatToRawIntBits(arr[offset])!=bits){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontainsNaN(double[] arr,int offset,int bound){
        while(!Double.isNaN(arr[offset])){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontainsNaN(float[] arr,int offset,int bound){
        while(!Float.isNaN(arr[offset])){
            if(++offset==bound){
                return false;
            }
        }
        return true;
    }
    public static boolean uncheckedcontainsRawInt(float[] arr,int offset,int bound,int val){
        if(val!=0){
            return uncheckedcontainsBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedcontains0(arr,offset,bound);
    }
    public static void uncheckedForwardForEach(boolean[] arr,int offset,int bound,BooleanConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(byte[] arr,int offset,int bound,ByteConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(char[] arr,int offset,int bound,CharConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(double[] arr,int offset,int bound,DoubleConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(float[] arr,int offset,int bound,FloatConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(int[] arr,int offset,int bound,IntConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(long[] arr,int offset,int bound,LongConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    @SuppressWarnings("unchecked")
    public static <E> void uncheckedForwardForEach(Object[] arr,int offset,int bound,Consumer<? super E> action){
        do{
            action.accept((E)arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedForwardForEach(short[] arr,int offset,int bound,ShortConsumer action){
        do{
            action.accept(arr[offset]);
        }while(++offset!=bound);
    }
    public static int uncheckedindexOf0(double[] arr,int offset,int bound){
        int index=offset;
        while(arr[index]!=0){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf0(float[] arr,int offset,int bound){
        int index=offset;
        while(arr[index]!=0){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOfBits(double[] arr,int offset,int bound,long bits){
        int index=offset;
        while(Double.doubleToRawLongBits(arr[index])!=bits){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOfBits(float[] arr,int offset,int bound,int bits){
        int index=offset;
        while(Float.floatToRawIntBits(arr[index])!=bits){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(boolean[] arr,int offset,int bound,boolean val){
        int index=offset;
        while(arr[index]^val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(byte[] arr,int offset,int bound,int val){
        int index=offset;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(char[] arr,int offset,int bound,int val){
        int index=offset;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(double[] arr,int offset,int bound,double val){
        if(val==val){
            return uncheckedindexOfBits(arr,offset,bound,Double.doubleToRawLongBits(val));
        }
        return uncheckedindexOfNaN(arr,offset,bound);
    }
    public static int uncheckedindexOf(float[] arr,int offset,int bound,float val){
        if(val==val){
            return uncheckedindexOfBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOfNaN(arr,offset,bound);
    }
    public static int uncheckedindexOf(int[] arr,int offset,int bound,int val){
        int index=offset;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(long[] arr,int offset,int bound,long val){
        int index=offset;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(Object[] arr,int offset,int bound,Predicate<Object> pred){
        int index=offset;
        while(!pred.test(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOf(short[] arr,int offset,int bound,int val){
        int index=offset;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOfNaN(double[] arr,int offset,int bound){
        int index=offset;
        while(!Double.isNaN(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOfNaN(float[] arr,int offset,int bound){
        int index=offset;
        while(!Float.isNaN(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index-offset;
    }
    public static int uncheckedindexOfRawInt(float[] arr,int offset,int bound,int val){
        if(val!=0){
            return uncheckedindexOfBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOf0(arr,offset,bound);
    }
    public static int uncheckedindexOf0(double[] arr,int bound){
        int index=0;
        while(arr[index]!=0){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf0(float[] arr,int bound){
        int index=0;
        while(arr[index]!=0){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOfBits(double[] arr,int bound,long bits){
        int index=0;
        while(Double.doubleToRawLongBits(arr[index])!=bits){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOfBits(float[] arr,int bound,int bits){
        int index=0;
        while(Float.floatToRawIntBits(arr[index])!=bits){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(boolean[] arr,int bound,boolean val){
        int index=0;
        while(arr[index]^val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(byte[] arr,int bound,int val){
        int index=0;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(char[] arr,int bound,int val){
        int index=0;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(double[] arr,int bound,double val){
        if(val==val){
            return uncheckedindexOfBits(arr,bound,Double.doubleToRawLongBits(val));
        }
        return uncheckedindexOfNaN(arr,bound);
    }
    public static int uncheckedindexOf(float[] arr,int bound,float val){
        if(val==val){
            return uncheckedindexOfBits(arr,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOfNaN(arr,bound);
    }
    public static int uncheckedindexOf(int[] arr,int bound,int val){
        int index=0;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(long[] arr,int bound,long val){
        int index=0;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(Object[] arr,int bound,Predicate<Object> pred){
        int index=0;
        while(!pred.test(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOf(short[] arr,int bound,int val){
        int index=0;
        while(arr[index]!=val){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOfNaN(double[] arr,int bound){
        int index=0;
        while(!Double.isNaN(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOfNaN(float[] arr,int bound){
        int index=0;
        while(!Float.isNaN(arr[index])){
            if(++index==bound){
                return -1;
            }
        }
        return index;
    }
    public static int uncheckedindexOfRawInt(float[] arr,int bound,int val){
        if(val!=0){
            return uncheckedindexOfBits(arr,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOf0(arr,bound);
    }
    public static int uncheckedlastIndexOf0(double[] arr,int offset,int bound){
        while(arr[--bound]!=0){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf0(float[] arr,int offset,int bound){
        while(arr[--bound]!=0){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOfBits(double[] arr,int offset,int bound,long bits){
        while(Double.doubleToRawLongBits(arr[--bound])!=bits){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOfBits(float[] arr,int offset,int bound,int bits){
        while(Float.floatToRawIntBits(arr[--bound])!=bits){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(boolean[] arr,int offset,int bound,boolean val){
        while(arr[--bound]^val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(byte[] arr,int offset,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(char[] arr,int offset,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(double[] arr,int offset,int bound,double val){
        if(val==val){
            return uncheckedlastIndexOfBits(arr,offset,bound,Double.doubleToRawLongBits(val));
        }
        return uncheckedlastIndexOfNaN(arr,offset,bound);
    }
    public static int uncheckedlastIndexOf(float[] arr,int offset,int bound,float val){
        if(val==val){
            return uncheckedlastIndexOfBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOfNaN(arr,offset,bound);
    }
    public static int uncheckedlastIndexOf(float[] arr,int offset,int bound,int val){
        if(val!=0){
            return uncheckedlastIndexOfBits(arr,offset,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOf0(arr,offset,bound);
    }
    public static int uncheckedlastIndexOf(int[] arr,int offset,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(long[] arr,int offset,int bound,long val){
        while(arr[--bound]!=val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(Object[] arr,int offset,int bound,Predicate<Object> pred){
        while(!pred.test(arr[--bound])){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf(short[] arr,int offset,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOfNaN(double[] arr,int offset,int bound){
        while(!Double.isNaN(arr[--bound])){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOfNaN(float[] arr,int offset,int bound){
        while(!Float.isNaN(arr[--bound])){
            if(bound==offset){
                return -1;
            }
        }
        return bound-offset;
    }
    public static int uncheckedlastIndexOf0(double[] arr,int bound){
        while(arr[--bound]!=0){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf0(float[] arr,int bound){
        while(arr[--bound]!=0){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOfBits(double[] arr,int bound,long bits){
        while(Double.doubleToRawLongBits(arr[--bound])!=bits){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOfBits(float[] arr,int bound,int bits){
        while(Float.floatToRawIntBits(arr[--bound])!=bits){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(boolean[] arr,int bound,boolean val){
        while(arr[--bound]^val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(byte[] arr,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(char[] arr,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(double[] arr,int bound,double val){
        if(val==val){
            return uncheckedlastIndexOfBits(arr,bound,Double.doubleToRawLongBits(val));
        }
        return uncheckedlastIndexOfNaN(arr,bound);
    }
    public static int uncheckedlastIndexOf(float[] arr,int bound,float val){
        if(val==val){
            return uncheckedlastIndexOfBits(arr,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOfNaN(arr,bound);
    }
    public static int uncheckedlastIndexOf(float[] arr,int bound,int val){
        if(val!=0){
            return uncheckedlastIndexOfBits(arr,bound,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOf0(arr,bound);
    }
    public static int uncheckedlastIndexOf(int[] arr,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(long[] arr,int bound,long val){
        while(arr[--bound]!=val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(Object[] arr,int bound,Predicate<Object> pred){
        while(!pred.test(arr[--bound])){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOf(short[] arr,int bound,int val){
        while(arr[--bound]!=val){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOfNaN(double[] arr,int bound){
        while(!Double.isNaN(arr[--bound])){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static int uncheckedlastIndexOfNaN(float[] arr,int bound){
        while(!Float.isNaN(arr[--bound])){
            if(bound==0){
                return -1;
            }
        }
        return bound;
    }
    public static void uncheckedReplaceAll(boolean[] arr,int offset,int bound,BooleanPredicate operator){
        do{
            arr[offset]=operator.test(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(byte[] arr,int offset,int bound,ByteUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsByte(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(char[] arr,int offset,int bound,CharUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsChar(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(double[] arr,int offset,int bound,DoubleUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsDouble(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(float[] arr,int offset,int bound,FloatUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsFloat(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(int[] arr,int offset,int bound,IntUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsInt(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(long[] arr,int offset,int bound,LongUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsLong(arr[offset]);
        }while(++offset!=bound);
    }
    @SuppressWarnings("unchecked")
    public static <E> void uncheckedReplaceAll(Object[] arr,int offset,int bound,
            Function<? super E,? extends E> operator){
        do{
            arr[offset]=operator.apply((E)arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReplaceAll(short[] arr,int offset,int bound,ShortUnaryOperator operator){
        do{
            arr[offset]=operator.applyAsShort(arr[offset]);
        }while(++offset!=bound);
    }
    public static void uncheckedReverseForEach(boolean[] arr,int offset,int bound,BooleanConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(byte[] arr,int offset,int bound,ByteConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(char[] arr,int offset,int bound,CharConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(double[] arr,int offset,int bound,DoubleConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(float[] arr,int offset,int bound,FloatConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(int[] arr,int offset,int bound,IntConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(long[] arr,int offset,int bound,LongConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    @SuppressWarnings("unchecked")
    public static <E> void uncheckedReverseForEach(Object[] arr,int offset,int bound,Consumer<? super E> action){
        do{
            action.accept((E)arr[--bound]);
        }while(bound!=offset);
    }
    public static void uncheckedReverseForEach(short[] arr,int offset,int bound,ShortConsumer action){
        do{
            action.accept(arr[--bound]);
        }while(bound!=offset);
    }
    public static int uncheckedsearch(boolean[] arr,int size,boolean val){
        int index=size-1;
        while(arr[index]^val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(byte[] arr,int size,int val){
        int index=size-1;
        while(arr[index]!=val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(char[] arr,int size,int val){
        int index=size-1;
        while(arr[index]!=val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(double[] arr,int size,double val){
        if(val==val){
            return uncheckedsearchBits(arr,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedsearchNaN(arr,size);
    }
    public static int uncheckedsearch(float[] arr,int size,float val){
        if(val==val){
            return uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
        return uncheckedsearchNaN(arr,size);
    }
    public static int uncheckedsearch(int[] arr,int size,int val){
        int index=size-1;
        while(arr[index]!=val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(long[] arr,int size,long val){
        int index=size-1;
        while(arr[index]!=val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(Object[] arr,int size,Predicate<Object> pred){
        int index=size-1;
        while(!pred.test(arr[index])){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch(short[] arr,int size,int val){
        int index=size-1;
        while(arr[index]!=val){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch0(double[] arr,int size){
        int index=size-1;
        while(arr[index]!=0){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearch0(float[] arr,int size){
        int index=size-1;
        while(arr[index]!=0){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearchBits(double[] arr,int size,long bits){
        int index=size-1;
        while(Double.doubleToRawLongBits(arr[index])!=bits){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearchBits(float[] arr,int size,int bits){
        int index=size-1;
        while(Float.floatToRawIntBits(arr[index])!=bits){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearchNaN(double[] arr,int size){
        int index=size-1;
        while(!Double.isNaN(arr[index])){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearchNaN(float[] arr,int size){
        int index=size-1;
        while(!Float.isNaN(arr[index])){
            if(index==0){
                return -1;
            }
            --index;
        }
        return size-index;
    }
    public static int uncheckedsearchRawInt(float[] arr,int size,int val){
        if(val!=0){
            return uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
        return uncheckedsearch0(arr,size);
    }
    public @SuppressWarnings("unchecked") static <E> int markSurvivors(Object[] arr,long[] survivorSet,int offset,
            int bound,Predicate<? super E> filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test((E)arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(Object[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(byte[] arr,long[] survivorSet,int offset,int bound,BytePredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(byte[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(char[] arr,long[] survivorSet,int offset,int bound,CharPredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(char[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(short[] arr,long[] survivorSet,int offset,int bound,ShortPredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(short[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(int[] arr,long[] survivorSet,int offset,int bound,IntPredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(int[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(long[] arr,long[] survivorSet,int offset,int bound,LongPredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(long[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(float[] arr,long[] survivorSet,int offset,int bound,FloatPredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(float[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int markSurvivors(double[] arr,long[] survivorSet,int offset,int bound,DoublePredicate filter){
        for(int numSurvivors=0,wordOffset=0;;){
            long word=0L,marker=1L;
            do{
                if(!filter.test(arr[offset])){
                    ++numSurvivors;
                    word|=marker;
                }
                if(++offset==bound){
                    survivorSet[wordOffset]=word;
                    return numSurvivors;
                }
            }while((marker<<=1)!=0);
            survivorSet[wordOffset++]=word;
        }
    }
    public static int pullSurvivorsDown(double[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
        for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
            long survivorWord;
            int runLength;
            if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
                int wordSrcOffset=srcOffset;
                do{
                    ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                            runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
                    dstOffset+=runLength;
                    if((numSurvivors-=runLength)==0){
                        return dstOffset;
                    }
                    wordSrcOffset+=runLength;
                }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
            }
        }
    }
    public static int pullSurvivorsDown(boolean[] arr,int srcOffset,int srcBound,boolean v){
        for(int dstOffset=srcOffset;;arr[dstOffset++]=v){
            do{
                if(++srcOffset==srcBound){
                    return dstOffset;
                }
            }while(arr[srcOffset]^v);
        }
    }
    public static int pullSurvivorsDown(boolean[] arr,int dstOffset,int srcOffset,int srcBound,boolean v){
        for(;;){
            arr[dstOffset++]=v;
            do{
                if(++srcOffset==srcBound){
                    return dstOffset;
                }
            }while(arr[srcOffset]^v);
        }
    }
    public static int pullSurvivorsDown(byte[] arr,int srcBegin,int srcEnd,BytePredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final byte v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(char[] arr,int srcBegin,int srcEnd,CharPredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final char v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(short[] arr,int srcBegin,int srcEnd,ShortPredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final short v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(int[] arr,int srcBegin,int srcEnd,IntPredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final int v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(long[] arr,int srcBegin,int srcEnd,LongPredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final long v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(float[] arr,int srcBegin,int srcEnd,FloatPredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final float v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    public static int pullSurvivorsDown(double[] arr,int srcBegin,int srcEnd,DoublePredicate filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final double v;
            if(!filter.test(v=arr[++srcBegin])){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
    @SuppressWarnings("unchecked")
    public static <E> int pullSurvivorsDown(Object[] arr,int srcBegin,int srcEnd,Predicate<? super E> filter){
        int dstOffset=srcBegin;
        while(srcBegin!=srcEnd){
            final Object v;
            if(!filter.test((E)(v=arr[++srcBegin]))){
                arr[dstOffset++]=v;
            }
        }
        return dstOffset;
    }
}
