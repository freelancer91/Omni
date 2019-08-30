package omni.impl.set;

import java.util.Iterator;
import java.util.Set;
import omni.impl.CheckedCollection;
interface SetEqualityUtil{
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
                   switch((v=(byte)val)>>6) {
                   case -2:
                       if((word0>>>v&1L)==0) {
                           break goToReturnFalse;
                       }
                       break;
                   case -1:
                       if((word1>>>v&1L)==0) {
                           break goToReturnFalse;
                       }
                       break;
                   case 0:
                       if((word2>>>v&1L)==0) {
                           break goToReturnFalse;
                       }
                       break;
                   default:
                       if((word3>>>v&1L)==0) {
                           break goToReturnFalse;
                       }
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
    static boolean isEqualTo(Set<?> thisSet,ByteSetImpl thatSet) {
        final long word0,word1,word2,word3;
        if(ByteSetImpl.size(word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3)==thisSet.size()) {
          for(final var itr=thisSet.iterator();;) {
              if(!itr.hasNext()) {
                  return true;
              }
              final Object val;
              if((val=itr.next()) instanceof Byte) {
                  final byte v;
                  switch((v=(byte)val)>>6) {
                  case -2:
                      if((word0>>>v&1L)!=0) {
                          continue;
                      }
                      break;
                  case -1:
                      if((word1>>>v&1L)!=0) {
                          continue;
                      }
                      break;
                  case 0:
                      if((word2>>>v&1L)!=0) {
                          continue;
                      }
                      break;
                  default:
                      if((word3>>>v&1L)!=0) {
                          continue;
                      }
                  }
              }
              break;
          }
        }
        return false;
    }
    static boolean isEqualTo(Set<?> thisSet,CharOpenAddressHashSet thatSet) {
        int size;
        if((size=thatSet.size)==0) {
            return thisSet.isEmpty();
        }
        return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,CharOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,ShortOpenAddressHashSet thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,ShortOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,IntOpenAddressHashSet thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,IntOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,LongOpenAddressHashSet thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,LongOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,FloatOpenAddressHashSet thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,FloatOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,DoubleOpenAddressHashSet thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,DoubleOpenAddressHashSet.Checked thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      int modCount=thatSet.modCount;
      try {
        return isEqualToHelper(thisSet,thatSet,size);
      }finally {
        CheckedCollection.checkModCount(modCount,thatSet.modCount);
      }
    }
    static boolean isEqualTo(Set<?> thisSet,RefOpenAddressHashSet<?> thatSet) {
      int size;
      if((size=thatSet.size)==0) {
          return thisSet.isEmpty();
      }
      return isEqualToHelper(thisSet,thatSet,size);
    }
    static boolean isEqualTo(Set<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet) {
      int size;
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
        return thisSet.size==1 && refTableContains(table=thisSet.table,table.length-1,Boolean.FALSE,1237);
      case 0b10:
        return thisSet.size==1 && refTableContains(table=thisSet.table,table.length-1,Boolean.TRUE,1231);
      default:
        final int tableLength;
        return thisSet.size==2 && refTableContains(table=thisSet.table,tableLength=table.length-1,Boolean.FALSE,1237)&&refTableContains(table,tableLength,Boolean.TRUE,1231);
      }
      
    }
    
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ByteSetImpl thatSet) {
      int size;
      if((size=thisSet.size)==0) {
        return thatSet.isEmpty();
      }
      final long word0,word1,word2,word3;
      if(size==ByteSetImpl.size(word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3)) {
        goToReturnFalse:for(final var val:thisSet.table) {
          if(val==null || val==RefOpenAddressHashSet.DELETED) {
            continue;
          }
          if(!(val instanceof Byte)) {
            break goToReturnFalse;
          }
          final byte v;
          switch((v=(byte)val)>>6) {
          case -2:
            if((word0>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case -1:
            if((word1>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case 0:
            if((word2>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          default:
            if((word3>>>v&1L)==0) {
              break goToReturnFalse;
            }
          }
          if(--size==0) {
            return true;
          }
        }
      }
      return false;
    }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ByteSetImpl.Checked thatSet) {
      int size;
      if((size=thisSet.size)==0) {
        return thatSet.isEmpty();
      }
      if(size==thatSet.size) {
        final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
        goToReturnFalse:for(final var val:thisSet.table) {
          if(val==null || val==RefOpenAddressHashSet.DELETED) {
            continue;
          }
          if(!(val instanceof Byte)) {
            break goToReturnFalse;
          }
          final byte v;
          switch((v=(byte)val)>>6) {
          case -2:
            if((word0>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case -1:
            if((word1>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case 0:
            if((word2>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          default:
            if((word3>>>v&1L)==0) {
              break goToReturnFalse;
            }
          }
          if(--size==0) {
            return true;
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
        goToReturnFalse:for(final var val:thisSet.table) {
          if(val==null || val==RefOpenAddressHashSet.DELETED) {
            continue;
          }
          if(!(val instanceof Character)) {
            break goToReturnFalse;
          }
          final char v;
          switch((v=(char)val)>>6) {
          case 0:
            if((word0>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case 1:
            if((word1>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case 2:
            if((word2>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          case 3:
            if((word3>>>v&1L)==0) {
              break goToReturnFalse;
            }
            break;
          default:
            if(!charTableContains(thatTable,thatTableLength,v)) {
              break goToReturnFalse;
            }
          }
          if(--size==0) {
            return true;
          }
        }
        
      }
      return false;
    }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,ShortOpenAddressHashSet thatSet) {
        if(thisSet.size==thatSet.size) {
            //TODO compare tables
              throw omni.util.NotYetImplementedException.getNYI();
          }
          return false;
      }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,IntOpenAddressHashSet thatSet) {
        if(thisSet.size==thatSet.size) {
            //TODO compare tables
              throw omni.util.NotYetImplementedException.getNYI();
          }
          return false;
      }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,LongOpenAddressHashSet thatSet) {
        if(thisSet.size==thatSet.size) {
            //TODO compare tables
              throw omni.util.NotYetImplementedException.getNYI();
          }
          return false;
      }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,FloatOpenAddressHashSet thatSet) {
        if(thisSet.size==thatSet.size) {
            //TODO compare tables
              throw omni.util.NotYetImplementedException.getNYI();
          }
          return false;
      }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,DoubleOpenAddressHashSet thatSet) {
        if(thisSet.size==thatSet.size) {
            //TODO compare tables
              throw omni.util.NotYetImplementedException.getNYI();
          }
          return false;
      }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,RefOpenAddressHashSet<?> thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size!=0) {
                final Object[] thisTable,thatTable;
                final int thisTableLength,thatTableLength;
                if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                   return refTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                }else {
                   return refTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                }
            }
            return true;
        }
        return false;
    }
    static boolean isEqualTo(RefOpenAddressHashSet<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size!=0) {
               
                final int thatSetModCount=thatSet.modCount;
                try {
                    final Object[] thisTable,thatTable;
                    final int thisTableLength,thatTableLength;
                    if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                       return refTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                    }else {
                       return refTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                    }
                }finally {
                    CheckedCollection.checkModCount(thatSetModCount,thatSet.modCount);
                }
                
            }
            return true;
        }
        return false;
    }
    static boolean isEqualTo(RefOpenAddressHashSet.Checked<?> thisSet,RefOpenAddressHashSet<?> thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size!=0) {
                final int thisSetModCount=thisSet.modCount;
                try {
                    final Object[] thisTable,thatTable;
                    final int thisTableLength,thatTableLength;
                    
                    if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                       return refTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                    }else {
                       return refTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                    }
                }finally {
                    CheckedCollection.checkModCount(thisSetModCount,thisSet.modCount);
                }
            }
            return true;
        }
        return false;
    }
    static boolean isEqualTo(RefOpenAddressHashSet.Checked<?> thisSet,RefOpenAddressHashSet.Checked<?> thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size!=0) {
                final int thisSetModCount=thisSet.modCount;
                final int thatSetModCount=thatSet.modCount;
                try {
                    final Object[] thisTable,thatTable;
                    final int thisTableLength,thatTableLength;
                    if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                       return refTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                    }else {
                       return refTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                    }
                }finally {
                    CheckedCollection.checkModCount(thisSetModCount,thisSet.modCount);
                    CheckedCollection.checkModCount(thatSetModCount,thatSet.modCount);
                }
            }
            return true;
        }
        return false;
    }
    static boolean isEqualTo(BooleanSetImpl thisSet,BooleanSetImpl thatSet) {
        return thisSet.state==thatSet.state;
    }
    static boolean isEqualTo(ByteSetImpl thisSet,ByteSetImpl thatSet) {
        return thisSet.word0==thatSet.word0 &&
                thisSet.word1==thatSet.word1 && 
                thisSet.word2==thatSet.word2 && 
                thisSet.word3==thatSet.word3; 
    }
    static boolean isEqualTo(CharOpenAddressHashSet thisSet,CharOpenAddressHashSet thatSet) {
        int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            if(
                    thisSet.word0==thatSet.word0 &&
                    thisSet.word1==thatSet.word1 && 
                    thisSet.word2==thatSet.word2 && 
                    thisSet.word3==thatSet.word3) {
                if((size=thisSet.tableSize)==0) {
                    return true;
                }
                final char[] thisTable,thatTable;
                final int thisTableLength,thatTableLength;
                if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                    return charTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                 }else {
                    return charTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                 }
            }
        }
        return false;
    }
    static boolean isEqualTo(ShortOpenAddressHashSet thisSet,ShortOpenAddressHashSet thatSet) {
        int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            if(
                    thisSet.word0==thatSet.word0 &&
                    thisSet.word1==thatSet.word1 && 
                    thisSet.word2==thatSet.word2 && 
                    thisSet.word3==thatSet.word3) {
                if((size=thisSet.tableSize)==0) {
                    return true;
                }
                final short[] thisTable,thatTable;
                final int thisTableLength,thatTableLength;
                if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                    return shortTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                 }else {
                    return shortTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                 }
            }
        }
        return false;
    }
    static boolean isEqualTo(IntOpenAddressHashSet thisSet,IntOpenAddressHashSet thatSet) {
        int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            if(
                    thisSet.word0==thatSet.word0 &&
                    thisSet.word1==thatSet.word1 && 
                    thisSet.word2==thatSet.word2 && 
                    thisSet.word3==thatSet.word3) {
                if((size=thisSet.tableSize)==0) {
                    return true;
                }
                final int[] thisTable,thatTable;
                final int thisTableLength,thatTableLength;
                if((thisTableLength=(thisTable=thisSet.table).length)>(thatTableLength=(thatTable=thatSet.table).length)) {
                    return intTableContainsAll(thisTable,thatTable,thatTableLength-1,size);
                 }else {
                    return intTableContainsAll(thatTable,thisTable,thisTableLength-1,size);
                 }
            }
        }
        return false;
    }
    static boolean isEqualTo(LongOpenAddressHashSet thisSet,LongOpenAddressHashSet thatSet) {
        int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            if(
                    thisSet.word0==thatSet.word0 &&
                    thisSet.word1==thatSet.word1 && 
                    thisSet.word2==thatSet.word2 && 
                    thisSet.word3==thatSet.word3) {
                if((size=thisSet.tableSize)==0) {
                    return true;
                }
                final long[] thisTable,thatTable;
                final int thisTableLength,thatTableLength;
                if((thisTableLength=(thisTable=thisSet.table).length-1)>(thatTableLength=(thatTable=thatSet.table).length-1)) {
                   return longTableContainsAll(thisTable,thatTable,thatTableLength,size);
                }else {
                   return longTableContainsAll(thatTable,thisTable,thisTableLength,size);
                }
            }
        }
        return false;
    }
    static boolean isEqualTo(FloatOpenAddressHashSet thisSet,FloatOpenAddressHashSet thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            final int[] thisTable,thatTable;
            final int thisTableLength,thatTableLength;
            if((thisTableLength=(thisTable=thisSet.table).length-1)>(thatTableLength=(thatTable=thatSet.table).length-1)) {
               return floatTableContainsAll(thisTable,thatTable,thatTableLength,size);
            }else {
               return floatTableContainsAll(thatTable,thisTable,thisTableLength,size);
            }
        }
        return false;
    }
    static boolean isEqualTo(DoubleOpenAddressHashSet thisSet,DoubleOpenAddressHashSet thatSet) {
        final int size;
        if((size=thisSet.size)==thatSet.size) {
            if(size==0) {
                return true;
            }
            final long[] thisTable,thatTable;
            final int thisTableLength,thatTableLength;
            if((thisTableLength=(thisTable=thisSet.table).length-1)>(thatTableLength=(thatTable=thatSet.table).length-1)) {
               return doubleTableContainsAll(thisTable,thatTable,thatTableLength,size);
            }else {
               return doubleTableContainsAll(thatTable,thisTable,thisTableLength,size);
            }
        }
        return false;
    }
    private static boolean charTableContainsAll(char[] smallTable,char[] bigTable,int bigTableLength,int numInTable) {
      for(int i=0;;++i) {
          final char smallTableVal;
          if(((smallTableVal=smallTable[i])&-2)!=0) {
              if(!charTableContains(bigTable,bigTableLength,smallTableVal)) {
                  return false;
              }
              if(--numInTable==0) {
                  return true;
              }
          }
      }
  }
    private static boolean shortTableContainsAll(short[] smallTable,short[] bigTable,int bigTableLength,int numInTable) {
        for(int i=0;;++i) {
            final short smallTableVal;
            if(((smallTableVal=smallTable[i])&-2)!=0) {
                if(!shortTableContains(bigTable,bigTableLength,smallTableVal)) {
                    return false;
                }
                if(--numInTable==0) {
                    return true;
                }
            }
        }
    }
    private static boolean intTableContainsAll(int[] smallTable,int[] bigTable,int bigTableLength,int numInTable) {
        for(int i=0;;++i) {
            final int smallTableVal;
            if(((smallTableVal=smallTable[i])&-2)!=0) {
                if(!intTableContains(bigTable,bigTableLength,smallTableVal)) {
                    return false;
                }
                if(--numInTable==0) {
                    return true;
                }
            }
        }
    }
    private static boolean longTableContainsAll(long[] smallTable,long[] bigTable,int bigTableLength,int numInTable) {
        for(int i=0;;++i) {
            final long smallTableVal;
            if(((smallTableVal=smallTable[i])&-2)!=0) {
                if(!longTableContains(bigTable,bigTableLength,smallTableVal)) {
                    return false;
                }
                if(--numInTable==0) {
                    return true;
                }
            }
        }
    }
    private static boolean floatTableContainsAll(int[] smallTable,int[] bigTable,int bigTableLength,int numInTable) {
        for(int i=0;;++i) {
            final int smallTableVal;
            switch(smallTableVal=smallTable[i]) {
            case 0:
            case 0x7fe00000:
                continue;
            default:
              if(!floatTableContains(bigTable,bigTableLength,smallTableVal,FloatOpenAddressHashSet.tableHash(smallTableVal))) {
                return false;
              }
              if(--numInTable==0) {
                return true;
              }
            }
        }
    }
    private static boolean doubleTableContainsAll(long[] smallTable,long[] bigTable,int bigTableLength,int numInTable) {
      for(int i=0;;++i) {
        final long smallTableVal;
        if((smallTableVal=smallTable[i])!=0 && smallTableVal!=0x7ffc000000000000L) {
          if(!doubleTableContains(bigTable,bigTableLength,smallTableVal,DoubleOpenAddressHashSet.tableHash(smallTableVal))) {
            return false;
          }
          if(--numInTable==0) {
            return true;
          }
        }
      }
    }
    private static boolean refTableContainsAll(Object[] smallTable,Object[] bigTable,int bigTableLength,int numInTable) {
        for(int i=0;;++i) {
            final Object smallTableVal;
            if((smallTableVal=smallTable[i])!=null && smallTableVal!=RefOpenAddressHashSet.DELETED) {         
                if(!refTableContains(bigTable,bigTableLength,smallTableVal,RefOpenAddressHashSet.tableHash(smallTableVal))) {
                  return false;
                }
                if(--numInTable==0) {
                  return true;
                }
            }
        }
    }
    private static boolean isEqualToHelper(Set<?> thisSet,CharOpenAddressHashSet thatSet,int size) {
        final Iterator<?> itr;
        Object val;
        if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Character) {
            final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
            final char[] table;
            final int tableLength=(table=thatSet.table).length-1;
            goToReturnFalse:do {
                final char v;
                switch((v=(char)val)>>6) {
                case 0:
                  if((word0>>>v&1L)==0) {
                    break goToReturnFalse;
                  }
                  break;
                case 1:
                  if((word1>>>v&1L)==0) {
                    break goToReturnFalse;
                  }
                  break;
                case 2:
                  if((word2>>>v&1L)==0) {
                    break goToReturnFalse;
                  }
                  break;
                case 3:
                  if((word3>>>v&1L)==0) {
                    break goToReturnFalse;
                  }
                  break;
                default:
                  if(!charTableContains(table,tableLength,v)) {
                    break goToReturnFalse;
                  }
                }
                if(!itr.hasNext()) {
                  return true;
                }
            }while((val=itr.next()) instanceof Character);
        }
        return false;
    }
    private static boolean isEqualToHelper(Set<?> thisSet,ShortOpenAddressHashSet thatSet,int size) {
      final Iterator<?> itr;
      Object val;
      if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Short) {
          final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
          final short[] table;
          final int tableLength=(table=thatSet.table).length-1;
          goToReturnFalse:do {
              final short v;
              switch((v=(short)val)>>6) {
              case -2:
                if((word0>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case -1:
                if((word1>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case 0:
                if((word2>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case 1:
                if((word3>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              default:
                if(!shortTableContains(table,tableLength,v)) {
                  break goToReturnFalse;
                }
              }
              if(!itr.hasNext()) {
                return true;
              }
          }while((val=itr.next()) instanceof Short);
      }
      return false;
    } 
    private static boolean isEqualToHelper(Set<?> thisSet,IntOpenAddressHashSet thatSet,int size) {
      final Iterator<?> itr;
      Object val;
      if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Integer) {
          final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
          final int[] table;
          final int tableLength=(table=thatSet.table).length-1;
          goToReturnFalse:do {
              final int v;
              switch((v=(int)val)>>6) {
              case -2:
                if((word0>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case -1:
                if((word1>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case 0:
                if((word2>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              case 1:
                if((word3>>>v&1L)==0) {
                  break goToReturnFalse;
                }
                break;
              default:
                if(!intTableContains(table,tableLength,v)) {
                  break goToReturnFalse;
                }
              }
              if(!itr.hasNext()) {
                return true;
              }
          }while((val=itr.next()) instanceof Integer);
      }
      return false;
    } 
    private static boolean isEqualToHelper(Set<?> thisSet,LongOpenAddressHashSet thatSet,int size) {
      final Iterator<?> itr;
      Object val;
      if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Long) {
          final long word0=thatSet.word0,word1=thatSet.word1,word2=thatSet.word2,word3=thatSet.word3;
          final long[] table;
          final int tableLength=(table=thatSet.table).length-1;
          goToReturnFalse:do {
              goToHasNext:for(;;) {
                final long v;
                final int i;
                if((v=(long)val)==(i=(int)v)) {
                  switch(i>>6) {
                  case -2:
                    if((word0>>>i&1L)==0) {
                      break goToReturnFalse;
                    }
                    break goToHasNext;
                  case -1:
                    if((word1>>>i&1L)==0) {
                      break goToReturnFalse;
                    }
                    break goToHasNext;
                  case 0:
                    if((word2>>>i&1L)==0) {
                      break goToReturnFalse;
                    }
                    break goToHasNext;
                  case 1:
                    if((word3>>>i&1L)==0) {
                      break goToReturnFalse;
                    }
                    break goToHasNext;
                  default:
                  }
                }
                if(!longTableContains(table,tableLength,v)) {
                  break goToReturnFalse;
                }
                break goToHasNext;
              }
              if(!itr.hasNext()) {
                return true;
              }
          }while((val=itr.next()) instanceof Long);
      }
      return false;
    } 
    private static boolean isEqualToHelper(Set<?> thisSet,FloatOpenAddressHashSet thatSet,int size) {
      final Iterator<?> itr;
      Object val;
      if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Float) {
          final int[] table;
          final int tableLength=(table=thatSet.table).length-1;
          do {
            int bits;
            final float v;
            final int hash;
            if((v=(float)val)==v) {
              if((bits=Float.floatToRawIntBits(v))==0) {
                bits=0xffe00000;
                hash=0xffe00000^ 0xffe00000 >>> 16;
              }else {
                hash=FloatOpenAddressHashSet.tableHash(bits);
              }
            }else {
              bits=0x7fc00000;
              hash=0x7fc00000 ^ 0x7fc00000 >>> 16;
            }
            if(!floatTableContains(table,tableLength,bits,hash)) {
              break;
            }
            if(--size==0) {
              return true;
            }
          }while((val=itr.next()) instanceof Float);
      }
      return false;
    } 
    private static boolean isEqualToHelper(Set<?> thisSet,DoubleOpenAddressHashSet thatSet,int size) {
      final Iterator<?> itr;
      Object val;
      if(size==thisSet.size() &&(val=(itr=thisSet.iterator()).next()) instanceof Double) {
          final long[] table;
          final int tableLength=(table=thatSet.table).length-1;
          do {
            long bits;
            final int hash;
            final double v;
            if((v=(double)val)==v) {
              if((bits=Double.doubleToRawLongBits(v))==0) {
                bits=0xfffc000000000000L;
                hash=(int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) ^ (int)(0xfffc000000000000L ^ 0xfffc000000000000L >>> 32) >>> 16;
              }else {
                hash=DoubleOpenAddressHashSet.tableHash(bits);
              }
            }else {
              bits=0x7ff8000000000000L;
              hash=(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16;
            }
            if(!doubleTableContains(table,tableLength,bits,hash)) {
              break;
            }
            if(--size==0) {
              return true;
            }
          }while((val=itr.next()) instanceof Double);
      }
      return false;
    }
    private static boolean isEqualToHelper(Set<?> thisSet,RefOpenAddressHashSet<?> thatSet,int size) {
      if(size==thisSet.size()) {
        final Object[] table;
        final int tableLength=(table=thatSet.table).length-1;
        for(final var itr=thisSet.iterator();;) {
          Object val;
          final int hash;
          if((val=itr.next())==null) {
            val=RefOpenAddressHashSet.NULL;
            hash=RefOpenAddressHashSet.NULLHASH;
          }else {
            hash=RefOpenAddressHashSet.tableHash(val);
          }
          if(!refTableContains(table,tableLength,val,hash)) {
            break;
          }
          if(!itr.hasNext()) {
            return true;
          }
        }
      }
      return false;
    } 
    private static boolean charTableContains(char[] table,int tableLength,int val) {
      char tableVal;
      int hash;
      if((tableVal=table[hash=val&tableLength])!=0) {
          final int initialHash=hash;
          do {
              if(val==tableVal) {
                  return true;
              }
          }while((hash=hash+1&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
      }
      return false;
    }
    private static boolean shortTableContains(short[] table,int tableLength,int val) {
        short tableVal;
        int hash;
        if((tableVal=table[hash=val&tableLength])!=0) {
            final int initialHash=hash;
            do {
                if(val==tableVal) {
                    return true;
                }
            }while((hash=hash+1&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
        }
        return false;
    }
    private static boolean intTableContains(int[] table,int tableLength,int val) {
        int tableVal;
        int hash;
        if((tableVal=table[hash=val&tableLength])!=0) {
            final int initialHash=hash;
            do {
                if(val==tableVal) {
                    return true;
                }
            }while((hash=hash+1&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
        }
        return false;
    }
    private static boolean longTableContains(long[] table,int tableLength,long val) {
        long tableVal;
        int hash;
        if((tableVal=table[hash=Long.hashCode(val)&tableLength])!=0) {
            final int initialHash=hash;
            do {
                if(val==tableVal) {
                    return true;
                }
            }while((hash=hash+1&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
        }
        return false;
    }
    private static boolean floatTableContains(int[] table,int tableLength,int val,int hash) {
      int tableVal;
      if((tableVal=table[hash&=tableLength])!=0){
        final int initialHash=hash;
        do{
          if(val==tableVal){
            return true;
          }
        }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
      }
      return false;
    }
    private static boolean doubleTableContains(long[] table,int tableLength,long val,int hash) {
      long tableVal;
      if((tableVal=table[hash&=tableLength])!=0){
        final int initialHash=hash;
        do{
          if(val==tableVal){
            return true;
          }
        }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=0);
      }
      return false;
    }
    private static boolean refTableContains(Object[] table,int tableLength,Object val,int hash) {
      Object tableVal;
      if((tableVal=table[hash&=tableLength])!=null) {
          final int initialHash=hash;
          do {
              if(val.equals(tableVal)){
                  return true;
              }
          }while((hash=hash+1&tableLength)!=initialHash&&(tableVal=table[hash])!=null);
      }
      return false;
    }
}
