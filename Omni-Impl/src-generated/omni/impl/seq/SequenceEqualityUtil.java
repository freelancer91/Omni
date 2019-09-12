package omni.impl.seq;
import omni.util.TypeUtil;
import java.util.Objects;
interface SequenceEqualityUtil{
  static boolean isEqualTo(int arrSeqRootOffset,int arrSeqRootBound,boolean[] arr,int pbasRootOffset,long[] pbasWords){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];arr[arrSeqRootOffset]==(((pbasWord>>>pbasRootOffset)&1L)!=0L);){
      if(++arrSeqRootOffset==arrSeqRootBound){
        return true;
      }
      if(((++pbasRootOffset)&63)==0){
        pbasWord=pbasWords[pbasRootOffset>>6];
      }
    }
    return false;
  }
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
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,BooleanDblLnkSeq.Node dlsHead){
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
  static boolean isEqualTo(int arrSeqRootOffset,int arrSeqRootBound,Object[] arr,int pbasRootOffset,long[] pbasWords){
    for(long pbasWord=pbasWords[pbasRootOffset>>6];TypeUtil.refEquals(arr[arrSeqRootOffset],(((pbasWord>>>pbasRootOffset)&1L)!=0L));){
      if(++arrSeqRootOffset==arrSeqRootBound){
        return true;
      }
      if(((++pbasRootOffset)&63)==0){
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
  static boolean isEqualTo(int pbasRootOffset,long[] pbasWords,int pbasRootBound,RefDblLnkSeq.Node<?> dlsHead){
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
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])   
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])   
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])   
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])   
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])   
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,Object[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(rhsArr[rhsRootOffset],lhsArr[lhsRootOffset]) 
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,boolean[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,byte[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,char[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,short[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,int[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,long[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,float[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,double[] rhsArr,int rhsRootOffset){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsArr[rhsRootOffset])
    ;++rhsRootOffset){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(boolean[] lhsArr,int lhsRootOffset,int lhsRootBound,BooleanDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(boolean[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,ByteDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)  
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(byte[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,CharDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val) 
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(char[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,ShortDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(short[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,IntDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(int[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,LongDblLnkSeq.Node rhsHead){
    for(;
    (lhsArr[lhsRootOffset])==(rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(long[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])   
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,FloatDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.floatEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(float[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,DoubleDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.doubleEquals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(double[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    TypeUtil.refEquals(rhsHead.val,lhsArr[lhsRootOffset]) 
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,RefDblLnkSeq.Node<?> rhsHead){
    for(;
    Objects.equals(rhsHead.val,lhsArr[lhsRootOffset])
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,BooleanDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,ByteDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,CharDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,ShortDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,IntDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,LongDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,FloatDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(Object[] lhsArr,int lhsRootOffset,int lhsRootBound,DoubleDblLnkSeq.Node rhsHead){
    for(;
    TypeUtil.refEquals(lhsArr[lhsRootOffset],rhsHead.val)
    ;rhsHead=rhsHead.next){
      if(++lhsRootOffset==lhsRootBound){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,BooleanDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(BooleanDblLnkSeq.Node lhsHead,BooleanDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,ByteDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(ByteDblLnkSeq.Node lhsHead,ByteDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,CharDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(CharDblLnkSeq.Node lhsHead,CharDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,ShortDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(ShortDblLnkSeq.Node lhsHead,ShortDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,IntDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(IntDblLnkSeq.Node lhsHead,IntDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,LongDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(LongDblLnkSeq.Node lhsHead,LongDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,FloatDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(FloatDblLnkSeq.Node lhsHead,FloatDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(RefDblLnkSeq.Node<?> lhsHead,RefDblLnkSeq.Node<?> lhsTail,DoubleDblLnkSeq.Node rhsHead){
    for(;TypeUtil.refEquals(lhsHead.val,rhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
  static boolean isEqualTo(DoubleDblLnkSeq.Node lhsHead,DoubleDblLnkSeq.Node lhsTail,RefDblLnkSeq.Node<?> rhsHead){
    for(;TypeUtil.refEquals(rhsHead.val,lhsHead.val);lhsHead=lhsHead.next,rhsHead=rhsHead.next){
      if(lhsHead==lhsTail){
        return true;
      }
    }
    return false;
  }
}
