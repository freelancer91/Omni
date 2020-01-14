package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class BooleanArrDeq extends BooleanUntetheredArrSeq implements OmniDeque.OfBoolean
{
  BooleanArrDeq(int head,boolean[] arr,int tail){
    super(head,arr,tail);
  }
  BooleanArrDeq(){
    super();
  }
  @Override public boolean add(boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(boolean val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Boolean val){
    super.addLast((boolean)val);
    return true;
  }
  @Override public void addFirst(Boolean val){
    super.push((boolean)val);
  }
  @Override public void addLast(Boolean val){
    super.addLast((boolean)val);
  }
  @Override public boolean offer(Boolean val){
    super.addLast((boolean)val);
    return true;
  }
  @Override public boolean offerFirst(Boolean val){
    super.push((boolean)val);
    return true;
  }
  @Override public boolean offerLast(Boolean val){
    super.addLast((boolean)val);
    return true;
  }
  @Override public void push(Boolean val){
    super.push((boolean)val);
  }
  @Override public boolean booleanElement(){
    return (boolean)arr[head];
  }
  @Override public boolean getLastBoolean(){
    return (boolean)arr[tail];
  }
  @Override public Boolean element(){
    return (boolean)arr[head];
  }
  @Override public Boolean getFirst(){
    return (boolean)arr[head];
  }
  @Override public Boolean getLast(){
    return (boolean)arr[tail];
  }
  @Override public boolean peekBoolean(){
    if(this.tail!=-1){
      return (boolean)arr[head];
    }
    return false;
  }
  @Override public boolean peekLastBoolean(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (boolean)arr[tail];
    }
    return false;
  }
  @Override public Boolean peek(){
    if(this.tail!=-1){
      return (Boolean)(arr[head]);
    }
    return null;
  }
  @Override public Boolean peekFirst(){
    if(this.tail!=-1){
      return (Boolean)(arr[head]);
    }
    return null;
  }
  @Override public Boolean peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Boolean)(arr[tail]);
    }
    return null;
  }
  @Override public double peekDouble(){
    if(this.tail!=-1){
      return TypeUtil.castToDouble(arr[head]);
    }
    return Double.NaN;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToDouble(arr[tail]);
    }
    return Double.NaN;
  }
  @Override public float peekFloat(){
    if(this.tail!=-1){
      return TypeUtil.castToFloat(arr[head]);
    }
    return Float.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToFloat(arr[tail]);
    }
    return Float.NaN;
  }
  @Override public long peekLong(){
    if(this.tail!=-1){
      return TypeUtil.castToLong(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToLong(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override public int peekInt(){
    if(this.tail!=-1){
      return (int)TypeUtil.castToByte(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)TypeUtil.castToByte(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public short peekShort(){
    if(this.tail!=-1){
      return (short)TypeUtil.castToByte(arr[head]);
    }
    return Short.MIN_VALUE;
  }
  @Override public short peekLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)TypeUtil.castToByte(arr[tail]);
    }
    return Short.MIN_VALUE;
  }
  @Override public char peekChar(){
    if(this.tail!=-1){
      return TypeUtil.castToChar(arr[head]);
    }
    return Character.MIN_VALUE;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToChar(arr[tail]);
    }
    return Character.MIN_VALUE;
  }
  @Override public byte peekByte(){
    if(this.tail!=-1){
      return TypeUtil.castToByte(arr[head]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte peekLastByte(){
    final int tail;
    if((tail=this.tail)!=-1){
      return TypeUtil.castToByte(arr[tail]);
    }
    return Byte.MIN_VALUE;
  }
  @Override public boolean popBoolean(){
    return (boolean)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public boolean removeLastBoolean(){
    return (boolean)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Boolean pop(){
    return (Boolean)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Boolean remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Boolean removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Boolean removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;
  }
  @Override public int search(boolean val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,val);
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return -1;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return -1;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return -1;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return -1;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return -1;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeFirstOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(val){
        default:
          return false;
        case 0:
          queryParam=false;
          break;
        case 1:
          queryParam=true;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val==0L){
        queryParam=false;
      }else if(val==1L){  
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          queryParam=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          queryParam=true;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
        queryParam=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        queryParam=true;
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final boolean queryParam;
      if(val instanceof Boolean){
        queryParam=(boolean)val;
      }else if(val instanceof Integer || val instanceof Short || val instanceof Byte){
        switch(((Number)val).intValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)==0){
          queryParam=false;
        }else if(l==1){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Float){
        switch(Float.floatToRawIntBits((float)val)){
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            queryParam=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            queryParam=true;
        }
      }else if(val instanceof Double){
        long bits;
        if((bits=Double.doubleToRawLongBits((double)val))==0 || bits==Long.MIN_VALUE){
          queryParam=false;
        }else if(bits==TypeUtil.DBL_TRUE_BITS){
          queryParam=true;
        }else{
          return false;
        }
      }else if(val instanceof Character){
        switch(((Character)val).charValue()){
          default:
            return false;
          case 0:
            queryParam=false;
            break;
          case 1:
            queryParam=true;
        }
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      boolean[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new boolean[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final boolean[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new boolean[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new BooleanArrDeq(head,copy,tail);
    }
    return new BooleanArrDeq();
  }
}
