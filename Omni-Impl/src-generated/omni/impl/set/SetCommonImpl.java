package omni.impl.set;
interface SetCommonImpl{
  //TODO move quickInsert to here
  static int tableHash(int val){
    return val^(val>>>16);
  }
  static int tableHash(long val){
    return tableHash(Long.hashCode(val));
  }
  static int tableHash(Object val){
    return tableHash(val.hashCode());
  }
  static int size(long word0,long word1,long word2,long word3){
    return Long.bitCount(word0)+Long.bitCount(word1)+Long.bitCount(word2)+Long.bitCount(word3);
  }
  static void quickInsert(char val,char[] table,int tableLength,int hash){
    while(table[hash]!=0){
      hash=(hash+1)&tableLength;
    }
    table[hash]=val;
  }
  static void quickInsert(short val,short[] table,int tableLength,int hash){
    while(table[hash]!=0){
      hash=(hash+1)&tableLength;
    }
    table[hash]=val;
  }
  static void quickInsert(int val,int[] table,int tableLength,int hash){
    while(table[hash]!=0){
      hash=(hash+1)&tableLength;
    }
    table[hash]=val;
  }
  static void quickInsert(long val,long[] table,int tableLength,int hash){
    while(table[hash]!=0L){
      hash=(hash+1)&tableLength;
    }
    table[hash]=val;
  }
  static void quickInsert(Object val,Object[] table,int tableLength,int hash){
    while(table[hash]!=null){
      hash=(hash+1)&tableLength;
    }
    table[hash]=val;
  }
/*
  static boolean isEqualTo(int booleanState,RefOpenAddressHashSet<?> thatSet){
     switch(booleanState){
       case 0b00:
         return thatSet.size==0;
       case 0b01:
         final Object[] table;
         final int tableLength;
         return thatSet.size==1
           && tableContains(Boolean.FALSE,table=thatSet.table,tableLength=table.length-1,tableLength&1237);
       case 0b10:
         return thatSet.size==1
           && tableContains(Boolean.TRUE,table=thatSet.table,tableLength=table.length-1,tableLength&1231);
       default:
         return thatSet.size==2
           && tableContains(Boolean.FALSE,table=thatSet.table,tableLength=table.length-1,tableLength&1237)
           && tableContains(Boolean.TRUE,table,tableLength,tableLength&1231);
     }
  }
  static boolean isEqualTo(int booleanState,Set<?> thatSet){
    for(final var val:thatSet){
      if(!(val instanceof Boolean)){
        return false;
      }
      if((boolean)val){
        if(booleanState==(booleanState&=0b01)){
          return false;
        }
      }else{
        if(booleanState==(booleanState&=0b10)){
          return false;
        }
      }
    }
    return booleanState==0b00;
  }
  static boolean isEqualTo(ByteSetImpl byteSet,RefOpenAddressHashSet<?> refSet){
    final long word0,word1,word2,word3;
    int size;
    if(refSet.size==(size=size(word0=byteSet.word0,word1=byteSet.word1,word2=byteSet.word2,word3=byteSet.word3))) {
      if(size==0){
        return true;
      }
      final var refTable=refSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=refTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Byte)) {
            break;
          }
          final byte v;
          goToHasNext:switch((v=(byte)val)>>6) {
          case -2:
               if((word0>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case -1:
               if((word1>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 0:
               if((word2>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          default:
               if((word3>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int byteSetSize,ByteSetImpl.Checked byteSet,Object[] refTable){
    final long word0=byteSet.word0,word1=byteSet.word1,word2=byteSet.word2,word3=byteSet.word3;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=refTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Byte)) {
          break;
        }
        final byte v;
        goToHasNext:switch((v=(byte)val)>>6) {
        case -2:
             if((word0>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case -1:
             if((word1>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 0:
             if((word2>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        default:
             if((word3>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        }
        if(--byteSetSize==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(ByteSetImpl byteSet,Set<?> thatSet){
    final long word0,word1,word2,word3;
    int size;
    if(thatSet.size()==(size=size(word0=byteSet.word0,word1=byteSet.word1,word2=byteSet.word2,word3=byteSet.word3))) {
      goToReturnFalse:for(;;){
        for(final var val:thatSet){
          if(!(val instanceof Byte)){
            return false;
          }
          final byte v;
          goToHasNext:switch((v=(byte)val)>>6){
          case -2:
               if((word0>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case -1:
               if((word1>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 0:
               if((word2>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          default:
               if((word3>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          }
        }
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,ByteSetImpl.Checked byteSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      goToReturnFalse:for(;;){
        Object val;
        if(size==thatSet.size() && (val=itr.next()) instanceof Byte){
          final long word0=byteSet.word0,word1=byteSet.word1,word2=byteSet.word2,word3=byteSet.word3;
          do{
            final byte v;
            goToHasNext:switch((v=(byte)val)>>6){
            case -2:
                 if((word0>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            case -1:
                 if((word1>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            case 0:
                 if((word2>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            default:
                 if((word3>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            }
            if(!itr.hasNext()){
              return true;
            }
          }while((val=itr.next()) instanceof Byte);
        }
        break;
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,CharOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
    final char[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Character)){
          break;
        }
        final char v;
        goToHasNext:switch((v=(char)val)>>6) {
        case 0:
             if((word0>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 1:
             if((word1>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 2:
             if((word2>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 3:
             if((word3>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        default:
          if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
            break;
          }
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,CharOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Character){
        final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
        final char[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          final char v;
          goToHasNext:switch((v=(char)val)>>6) {
          case 0:
               if((word0>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 1:
               if((word1>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 2:
               if((word2>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 3:
               if((word3>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          default:
            if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Character);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,ShortOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
    final short[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Short)){
          break;
        }
        final short v;
        goToHasNext:switch((v=(short)val)>>6) {
        case -2:
             if((word0>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case -1:
             if((word1>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 0:
             if((word2>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 1:
             if((word3>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        default:
          if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
            break;
          }
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,ShortOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Short){
        final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
        final short[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          final short v;
          goToHasNext:switch((v=(short)val)>>6) {
          case -2:
               if((word0>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case -1:
               if((word1>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 0:
               if((word2>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 1:
               if((word3>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          default:
            if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Short);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,IntOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
    final int[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Integer)){
          break;
        }
        final int v;
        goToHasNext:switch((v=(int)val)>>6) {
        case -2:
             if((word0>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case -1:
             if((word1>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 0:
             if((word2>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        case 1:
             if((word3>>>v&1L)==0) {
                 break goToReturnFalse;
             }
             break goToHasNext;
        default:
          if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
            break;
          }
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,IntOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Integer){
        final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
        final int[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          final int v;
          goToHasNext:switch((v=(int)val)>>6) {
          case -2:
               if((word0>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case -1:
               if((word1>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 0:
               if((word2>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          case 1:
               if((word3>>>v&1L)==0) {
                   break goToReturnFalse;
               }
               break goToHasNext;
          default:
            if(tableContains(v,thisTable,thisTableLength,v&thisTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Integer);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,LongOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
    final long[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Long)){
          break;
        }
        goToHasNext:for(;;){
          final int v;
          final long l;
          if((l=(long)val)==(v=(int)l)){
            switch(v>>6){
            case -2:
                 if((word0>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            case -1:
                 if((word1>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            case 0:
                 if((word2>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            case 1:
                 if((word3>>>v&1L)==0) {
                     break goToReturnFalse;
                 }
                 break goToHasNext;
            default:
            }
          }
          if(tableContains(l,thisTable,thisTableLength,Long.hashCode(l)&thisTableLength)) {
            break;
          }
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,LongOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Long){
        final long word0=thisSet.word0,word1=thisSet.word1,word2=thisSet.word2,word3=thisSet.word3;
        final long[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          goToHasNext:for(;;){
            final int v;
            final long l;
            if((l=(long)val)==(v=(int)l)){
              switch(v>>6){
              case -2:
                   if((word0>>>v&1L)==0) {
                       break goToReturnFalse;
                   }
                   break goToHasNext;
              case -1:
                   if((word1>>>v&1L)==0) {
                       break goToReturnFalse;
                   }
                   break goToHasNext;
              case 0:
                   if((word2>>>v&1L)==0) {
                       break goToReturnFalse;
                   }
                   break goToHasNext;
              case 1:
                   if((word3>>>v&1L)==0) {
                       break goToReturnFalse;
                   }
                   break goToHasNext;
              default:
              }
            }
            if(tableContains(l,thisTable,thisTableLength,Long.hashCode(l)&thisTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Long);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,FloatOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final int[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Float)){
          break;
        }
        final int hash;
        int bits;
        final float f;
        if((f=(float)val)==f){
          if((bits=Float.floatToRawIntBits(f))==0){
            bits=0xffe00000;
            hash=0xffe00000^(0xffe00000 >>> 16);
          }else{
            hash=tableHash(bits);
          }
        }else{
          bits=0x7fc00000;
          hash=0x7fc00000 ^ (0x7fc00000 >>> 16);
        }
        if(!tableContains(bits,thisTable,thisTableLength,hash&thisTableLength)){
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,FloatOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Float){
        final int[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          final int hash;
          int bits;
          final float f;
          if((f=(float)val)==f){
            if((bits=Float.floatToRawIntBits(f))==0){
              bits=0xffe00000;
              hash=0xffe00000^(0xffe00000 >>> 16);
            }else{
              hash=tableHash(bits);
            }
          }else{
            bits=0x7fc00000;
            hash=0x7fc00000 ^ (0x7fc00000 >>> 16);
          }
          if(!tableContains(bits,thisTable,thisTableLength,hash&thisTableLength)){
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Float);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,DoubleOpenAddressHashSet thisSet,RefOpenAddressHashSet<?> thatSet){
    final long[] thisTable;
    final int thisTableLength=(thisTable=thisSet.table).length-1;
    final Object[] thatTable=thatSet.table;
    goToReturnFalse:for(int tableIndex=0;;++tableIndex){
      final Object val;
      if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
        if(!(val instanceof Double)){
          break;
        }
        final int hash;
        long bits;
        final double d;
        if((d=(double)val)==d){
          if((bits=Double.doubleToRawLongBits(d))==0L){
            bits=0xfffc000000000000L;
            hash=(int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) ^ (int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) >>> 16;
          }else{
            hash=tableHash(bits);
          }
        }else{
          bits=0x7ff8000000000000L;
          hash=(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16;
        }
        if(!tableContains(bits,thisTable,thisTableLength,hash&thisTableLength)){
          break goToReturnFalse;
        }
        if(--size==0) {
          return true;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(int size,DoubleOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      Object val;
      if(size==thatSet.size() && (val=itr.next()) instanceof Double){
        final long[] thisTable;
        final int thisTableLength=(thisTable=thisSet.table).length-1;
        goToReturnFalse:do{
          final int hash;
          long bits;
          final double d;
          if((d=(double)val)==d){
            if((bits=Double.doubleToRawLongBits(d))==0L){
              bits=0xfffc000000000000L;
              hash=(int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) ^ (int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) >>> 16;
            }else{
              hash=tableHash(bits);
            }
          }else{
            bits=0x7ff8000000000000L;
            hash=(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16;
          }
          if(!tableContains(bits,thisTable,thisTableLength,hash&thisTableLength)){
            break goToReturnFalse;
          }
          if(!itr.hasNext()){
            return true;
          }
        }while((val=itr.next()) instanceof Double);
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int size,RefOpenAddressHashSet thisSet,Set<?> thatSet){
    final Iterator<?> itr;
    if((itr=thatSet.iterator()).hasNext()){
      if(size==thatSet.size()){
        final Object[] table;
        for(final int tableLength=(table=thisSet.table).length-1;;){
          Object val;
          final int hash;
          if((val=itr.next())==null){
            hash=RefOpenAddressHashSet.NULLHASH;
            val=RefOpenAddressHashSet.NULL;
          }else{
            hash=tableHash(val);
          }
          if(!tableContains(val,table,tableLength,hash&tableLength)){
            break;
          }
          if(!itr.hasNext()){
            return true;
          }
        }
      }
      return false;
    }
    return size==0;
  }
  static boolean isEqualTo(int refSize,Object[] refTable,int booleanState){
    switch(booleanState){
    case 0b01:
      final int tableLength;
      return refSize==1 && SetCommonImpl.tableContains(Boolean.FALSE,refTable,tableLength=refTable.length-1,tableLength&1237);
    case 0b10:
      return refSize==1 && SetCommonImpl.tableContains(Boolean.TRUE,refTable,tableLength=refTable.length-1,tableLength&1231);
    default:
      return refSize==2 && SetCommonImpl.tableContains(Boolean.FALSE,refTable,tableLength=refTable.length-1,tableLength&1237)&&SetCommonImpl.tableContains(Boolean.TRUE,refTable,tableLength,tableLength&1231);
    }
  }
  */
  static boolean tableContains(Object val,Object[] table,int tableLength,int hash){
    Object tableVal;
    if((tableVal=table[hash])!=null){
      final int initialHash=hash;
      do{
        if(val.equals(tableVal)){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=null);
    }
    return false;
  }
  static boolean tableContains(long val,long[] table,int tableLength,int hash){
    long tableVal;
    if((tableVal=table[hash])!=0L){
      final int initialHash=hash;
      do{
        if(val==tableVal){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0L);
    }
    return false;
  }
  static boolean tableContains(int val,int[] table,int tableLength,int hash){
    int tableVal;
    if((tableVal=table[hash])!=0){
      final int initialHash=hash;
      do{
        if(val==tableVal){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
    }
    return false;
  }
  static boolean tableContains(short val,short[] table,int tableLength,int hash){
    short tableVal;
    if((tableVal=table[hash])!=0){
      final int initialHash=hash;
      do{
        if(val==tableVal){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
    }
    return false;
  }
  static boolean tableContains(char val,char[] table,int tableLength,int hash){
    char tableVal;
    if((tableVal=table[hash])!=0){
      final int initialHash=hash;
      do{
        if(val==tableVal){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
    }
    return false;
  }
    /*
  private static int setContainsAllByteWord(long word,Set<?> thatSet,int offset,int bound,int numLeft){
      do{
        if(((word>>>offset)&1L)!=0){
          if(!thatSet.contains((byte)offset)){
            return -1;
          }
          if(--numLeft==0){
            break;
          }
        }
      }while(++offset!=bound);
      return numLeft;
    }
  private static int setContainsAllCharWord(long word,Set<?> thatSet,int offset,int bound,int numLeft){
      do{
        if(((word>>>offset)&1L)!=0){
          if(!thatSet.contains((char)offset)){
            return -1;
          }
          if(--numLeft==0){
            break;
          }
        }
      }while(++offset!=bound);
      return numLeft;
    }
  private static int setContainsAllShortWord(long word,Set<?> thatSet,int offset,int bound,int numLeft){
      do{
        if(((word>>>offset)&1L)!=0){
          if(!thatSet.contains((short)offset)){
            return -1;
          }
          if(--numLeft==0){
            break;
          }
        }
      }while(++offset!=bound);
      return numLeft;
    }
  private static int setContainsAllIntWord(long word,Set<?> thatSet,int offset,int bound,int numLeft){
      do{
        if(((word>>>offset)&1L)!=0){
          if(!thatSet.contains((int)offset)){
            return -1;
          }
          if(--numLeft==0){
            break;
          }
        }
      }while(++offset!=bound);
      return numLeft;
    }
  private static int setContainsAllLongWord(long word,Set<?> thatSet,long offset,long bound,int numLeft){
      do{
        if(((word>>>offset)&1L)!=0){
          if(!thatSet.contains((long)offset)){
            return -1;
          }
          if(--numLeft==0){
            break;
          }
        }
      }while(++offset!=bound);
      return numLeft;
    }
*/
}
