package omni.impl.seq;
import omni.impl.BooleanDblLnkNode;
import omni.impl.ByteDblLnkNode;
import omni.impl.CharDblLnkNode;
import omni.impl.ShortDblLnkNode;
import omni.impl.IntDblLnkNode;
import omni.impl.LongDblLnkNode;
import omni.impl.FloatDblLnkNode;
import omni.impl.DoubleDblLnkNode;
import omni.impl.RefDblLnkNode;
import omni.util.TypeUtil;
import java.util.Objects;
interface SequenceEqualityUtil{
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,boolean[] arr,int arrSeqRootOffset){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];arr[arrSeqRootOffset]^(((pbasWord>>>pbasRootOffset)&1L)==0L);++arrSeqRootOffset){
      if(++pbasRootOffset==pbasRootBound){
        return true;
      }
      if((pbasRootOffset&63)==0){
        pbasWord=pbasWords[pbasRootOffset>>6];
      }
    }
    return false;
  }
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,BooleanDblLnkNode dlsHead){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];dlsHead.val^(((pbasWord>>>pbasRootOffset)&1L)==0L);dlsHead=dlsHead.next){
      if(++pbasRootOffset==pbasRootBound){
        return true;
      }
      if((pbasRootOffset&63)==0){
        pbasWord=pbasWords[pbasRootOffset>>6];
      }
    }
    return false;
  }
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,Object[] arr,int arrSeqRootOffset){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];TypeUtil.refEquals(arr[arrSeqRootOffset],(((pbasWord>>>pbasRootOffset)&1L)!=0L));++arrSeqRootOffset){
      if(++pbasRootOffset==pbasRootBound){
        return true;
      }
      if((pbasRootOffset&63)==0){
        pbasWord=pbasWords[pbasRootOffset>>6];
      }
    }
    return false;
  }
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,RefDblLnkNode<?> dlsHead){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];TypeUtil.refEquals(dlsHead.val,(((pbasWord>>>pbasRootOffset)&1L)!=0L));dlsHead=dlsHead.next){
      if(++pbasRootOffset==pbasRootBound){
        return true;
      }
      if((pbasRootOffset&63)==0){
        pbasWord=pbasWords[pbasRootOffset>>6];
      }
    }
    return false;
  }
  static boolean isEqualTo(boolean[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]);++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(boolean[] lhsArr,int lhsRootOffset,int lhsRootBound,BooleanDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(boolean[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,ByteDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)  
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,CharDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val) 
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,ShortDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,IntDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,LongDblLnkNode rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,FloatDblLnkNode rhsHead){
    for(;
    TypeUtil.floatEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,DoubleDblLnkNode rhsHead){
    for(;
    TypeUtil.doubleEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset]) 
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkNode<?> rhsHead){
    for(;
    Objects.equals(rhsHead.val,lhsArr[lhsRootOffset])  
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(BooleanDblLnkNode lhsHead,BooleanDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(ByteDblLnkNode lhsHead,ByteDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(CharDblLnkNode lhsHead,CharDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(ShortDblLnkNode lhsHead,ShortDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(IntDblLnkNode lhsHead,IntDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(LongDblLnkNode lhsHead,LongDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(FloatDblLnkNode lhsHead,FloatDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(DoubleDblLnkNode lhsHead,DoubleDblLnkNode lhsTail,RefDblLnkNode<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
}
