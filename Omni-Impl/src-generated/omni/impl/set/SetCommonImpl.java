package omni.impl.set;
import java.util.Iterator;
import java.util.Set;
import omni.impl.CheckedCollection;
interface SetCommonImpl{
  //TODO move quickInsert to here
  static int tableHash(int val){
    return val&(val>>>16);
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
  static boolean isEqualTo(Set<?> thisSet,BooleanSetImpl thatSet) {
      switch(thatSet.state) {
      case 0b00:
          return thisSet.isEmpty();
      case 0b01:
          return thisSet.size()==1 && thisSet.contains(Boolean.FALSE);
      case 0b10:
          if(thisSet.size()!=1){
              return false;
          }
          break;
      default:
          if( thisSet.size()!=2 || !thisSet.contains(Boolean.FALSE)) {
              return false;
          }
      }
      return thisSet.contains(Boolean.TRUE);
  }
  static boolean isEqualTo(Set<?> thisSet,ByteSetImpl thatSet){
    final long word0,word1,word2,word3;
    if(size(word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3)==thisSet.size()) {
      goToReturnFalse:for(;;){
        goToHasNext:for(final var itr=thisSet.iterator();;) {
            if(!itr.hasNext()) {
              return true;
            }
            final Object val;
            if((val=itr.next()) instanceof Byte) {
                final byte v;
                switch((v=(byte)val)>>6) {
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
            break goToReturnFalse;
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(Set<?> thisSet,ByteSetImpl.Checked thatSet) {
    int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
         final Iterator<?> itr;
         Object val;
         if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Byte) {
             final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
             goToReturnFalse:do {
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
                 if(!itr.hasNext()) {
                   return true;
                 }
             }while((val=itr.next()) instanceof Byte);
         }
    }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
    return false;
  }
  static boolean isEqualTo(Set<?> thisSet,CharOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,CharOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,ShortOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,ShortOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,IntOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,IntOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,LongOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,LongOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,FloatOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,FloatOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,DoubleOpenAddressHashSet  thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,DoubleOpenAddressHashSet.Checked  thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(Set<?> thisSet,RefOpenAddressHashSet<?> thatSet) {
      final int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
  }
  static boolean isEqualTo(Set<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet) {
    final int size;
    if((size=thatSet.size)==0) {
        return thisSet.isEmpty();
    }
    final int modCount=thatSet.modCount;
    try {
      return isEqualToHelper(thisSet,thatSet,size);
    }finally {
      CheckedCollection.checkModCount(modCount,thatSet.modCount);
    }
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,BooleanSetImpl thatSet) {
    switch(thatSet.state) {
    case 0b00:
      return thisSet.size==0;
    case 0b01:
      final Object[] table;
      final int tableLength;
      return thisSet.size==1 && tableContains(Boolean.FALSE,table=thisSet.table,tableLength=table.length-1,tableLength&1237);
    case 0b10:
      return thisSet.size==1 && tableContains(Boolean.TRUE,table=thisSet.table,tableLength=table.length-1,tableLength&1231);
    default:
      return thisSet.size==2 && tableContains(Boolean.FALSE,table=thisSet.table,tableLength=table.length-1,tableLength&1237)&&tableContains(Boolean.TRUE,table,tableLength,tableLength&1231);
    }
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ByteSetImpl thatSet) {
    int size;
    if((size=thisSet.size)==0) {
      return thatSet.isEmpty();
    }
    final long word0,word1,word2,word3;
    if(size==size(word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3)) {
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
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
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ByteSetImpl.Checked thatSet) {
    int size;
    if((size=thisSet.size)==0) {
      return thatSet.size==0;
    }
    if(size==thatSet.size) {
      final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
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
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,CharOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
      final char[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Character)) {
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
            if(tableContains(v,thatTable,thatTableLength,v&thatTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ShortOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
      final short[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Short)) {
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
            if(tableContains(v,thatTable,thatTableLength,v&thatTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,IntOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
      final int[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Integer)) {
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
            if(tableContains(v,thatTable,thatTableLength,v&thatTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,LongOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
      final long[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      goToReturnFalse:for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Long)) {
            break;
          }
          goToHasNext:for(;;){
            final long l;
            final int v;
            if((l=(long)val)==(v=(int)l)) {
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
            if(tableContains(l,thatTable,thatTableLength,Long.hashCode(l)&thatTableLength)) {
              break;
            }
            break goToReturnFalse;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,FloatOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final int[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Float)) {
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
          if(!tableContains(bits,thatTable,thatTableLength,hash&thatTableLength)){
            break;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,DoubleOpenAddressHashSet thatSet) {
    int size;
    if((size=thisSet.size)==thatSet.size) {
      if(size==0) {
        return true;
      }
      final long[] thatTable;
      final int thatTableLength=(thatTable=thatSet.table).length-1;
      final var thisTable=thisSet.table;
      for(int tableIndex=0;;++tableIndex){
        final Object val;
        if((val=thisTable[tableIndex])!=null && val!=RefOpenAddressHashSet.DELETED) {
          if(!(val instanceof Double)) {
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
          if(!tableContains(bits,thatTable,thatTableLength,hash&thatTableLength)){
            break;
          }
          if(--size==0) {
            return true;
          }
        }
      }
    }
    return false;
  }
  /*
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,RefOpenAddressHashSet<?> thatSet){
    final int size;
    if((size=thisSet.size)==thatSet.size){
      if(size==0){
        return true;
      }
      final Object[] thisTable,thatTable;
      final int thisTableLength,thatTableLength;
      if((thisTableLength=(thisTable=thisSet.table).length)<=(thatTableLength=(thatTable=thatSet.table).length)){
        return tableContainsAllRefRef(thisTable,thatTable,thatTableLength-1,size);
      }else{
        return tableContainsAllRefRef(thatTable,thisTable,thisTableLength-1,size);
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet){
    final int size;
    if((size=thisSet.size)==thatSet.size){
      if(size==0){
        return true;
      }
      final int thatModCount=thatSet.modCount;
      try{
        final Object[] thisTable,thatTable;
        final int thisTableLength,thatTableLength;
        if((thisTableLength=(thisTable=thisSet.table).length)<=(thatTableLength=(thatTable=thatSet.table).length)){
          return tableContainsAllRefRef(thisTable,thatTable,thatTableLength-1,size);
        }else{
          return tableContainsAllRefRef(thatTable,thisTable,thisTableLength-1,size);
        }
      }finally{
        CheckedCollection.checkModCount(thatModCount,thatSet.modCount);
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet.Checked<?> thisSet,RefOpenAddressHashSet<?> thatSet){
    final int size;
    if((size=thisSet.size)==thatSet.size){
      if(size==0){
        return true;
      }
      final int thisModCount=thisSet.modCount;
      try{
        final Object[] thisTable,thatTable;
        final int thisTableLength,thatTableLength;
        if((thisTableLength=(thisTable=thisSet.table).length)<=(thatTableLength=(thatTable=thatSet.table).length)){
          return tableContainsAllRefRef(thisTable,thatTable,thatTableLength-1,size);
        }else{
          return tableContainsAllRefRef(thatTable,thisTable,thisTableLength-1,size);
        }
      }finally{
        CheckedCollection.checkModCount(thisModCount,thisSet.modCount);
      }
    }
    return false;
  }
  static boolean isEqualTo(RefOpenAddressHashSet.Checked<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet){
    final int size;
    if((size=thisSet.size)==thatSet.size){
      if(size==0){
        return true;
      }
      final int thisModCount=thisSet.modCount;
      final int thatModCount=thatSet.modCount;
      try{
        final Object[] thisTable,thatTable;
        final int thisTableLength,thatTableLength;
        if((thisTableLength=(thisTable=thisSet.table).length)<=(thatTableLength=(thatTable=thatSet.table).length)){
          return tableContainsAllRefRef(thisTable,thatTable,thatTableLength-1,size);
        }else{
          return tableContainsAllRefRef(thatTable,thisTable,thisTableLength-1,size);
        }
      }finally{
        CheckedCollection.checkModCount(thisModCount,thisSet.modCount);
        CheckedCollection.checkModCount(thatModCount,thatSet.modCount);
      }
    }
    return false;
  }
  private static boolean tableContainsAllRefRef(Object[] smallTable,Object[] bigTable,int bigTableLength,int numInTable){
    for(int tableIndex=0;;++tableIndex){
      Object smallTableVal;
      if((smallTableVal=smallTable[tableIndex])!=null && smallTableVal!=RefOpenAddressHashSet.DELETED){
        final int hash;
        if(smallTableVal==RefOpenAddressHashSet.NULL){
          hash=RefOpenAddressHashSet.NULLHASH;
        }else{
          hash=tableHash(smallTableVal.hashCode());
        }
        if(!tableContains(smallTableVal,bigTable,bigTableLength,hash&bigTableLength)){
          return false;
        }
        if(--numInTable==0){
          return true;
        }
      }
    }
  }
    */
  private static boolean isEqualToHelper(Set<?> thisSet,CharOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Character){
        final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
        final char[] table;
        final int tableLength=(table=thatSet.table).length-1;
        goToReturnFalse:do{
          final char v;
          goToHasNext:switch((v=(char)val)>>6){
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
            if(tableContains(v,table,tableLength,v&tableLength)){
              break goToHasNext;
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
  private static boolean isEqualToHelper(Set<?> thisSet,ShortOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Short){
        final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
        final short[] table;
        final int tableLength=(table=thatSet.table).length-1;
        goToReturnFalse:do{
          final short v;
          goToHasNext:switch((v=(short)val)>>6){
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
            if(tableContains(v,table,tableLength,v&tableLength)){
              break goToHasNext;
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
  private static boolean isEqualToHelper(Set<?> thisSet,IntOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Integer){
        final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
        final int[] table;
        final int tableLength=(table=thatSet.table).length-1;
        goToReturnFalse:do{
          final int v;
          goToHasNext:switch((v=(int)val)>>6){
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
            if(tableContains(v,table,tableLength,v&tableLength)){
              break goToHasNext;
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
  private static boolean isEqualToHelper(Set<?> thisSet,LongOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Long){
        final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
        final long[] table;
        final int tableLength=(table=thatSet.table).length-1;
        goToReturnFalse:do{
          goToHasNext:for(;;){
            final long l;
            final int v;
            if((l=(long)val)==(v=(int)l)) {
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
            if(tableContains(l,table,tableLength,Long.hashCode(l)&tableLength)) {
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
  private static boolean isEqualToHelper(Set<?> thisSet,FloatOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Float){
      final int[] table;
      final int tableLength=(table=thatSet.table).length-1;
      do{
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
        if(!tableContains(bits,table,tableLength,hash&tableLength)){
          break;
        }
        if(!itr.hasNext()){
          return true;
        }
      }while((val=itr.next()) instanceof Float);
    }
    return false;
  }
  private static boolean isEqualToHelper(Set<?> thisSet,DoubleOpenAddressHashSet thatSet,int size){
    final Iterator<?> itr;
    Object val;
    if(size==thisSet.size() && (val=(itr=thisSet.iterator()).next()) instanceof Double){
      final long[] table;
      final int tableLength=(table=thatSet.table).length-1;
      do{
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
        if(!tableContains(bits,table,tableLength,hash&tableLength)){
          break;
        }
        if(!itr.hasNext()){
          return true;
        }
      }while((val=itr.next()) instanceof Double);
    }
    return false;
  }
  private static boolean isEqualToHelper(Set<?> thisSet,RefOpenAddressHashSet<?> thatSet,int size){
    if(size==thisSet.size()){
      final Object[] table;
      final int tableLength=(table=thatSet.table).length-1;
      for(final var itr=thisSet.iterator();;){
        final int hash;
        Object val;
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
}
