package omni.util;
public interface ArrCopy
{
  static <SRC extends DST,DST> void uncheckedReverseCopy(SRC[] src,int srcOffset,DST[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(Object[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(Object[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static <SRC extends DST,DST> void uncheckedCopy(SRC[] src,int srcOffset,DST[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(boolean[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(boolean[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(byte[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(byte[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(char[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(char[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(char[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(short[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(short[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(int[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(int[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(int[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(long[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(long[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(float[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(float[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(src[--length])){}
  }
  static void semicheckedSelfCopy(double[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedSelfCopy(src,srcOffset,dstOffset,length);
    }
  }
  static void uncheckedSelfCopy(double[] src,int srcOffset,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    //assert length>5 || dstOffset<=srcOffset || srcOffset+length<=dstOffset;
    switch(length)
    {
    case 5:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      src[dstOffset+4]=src[srcOffset+4];
      return;
    case 4:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      src[dstOffset+3]=src[srcOffset+3];
      return;
    case 3:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      src[dstOffset+2]=src[srcOffset+2];
      return;
    case 2:
      src[dstOffset]=src[srcOffset];
      src[dstOffset+1]=src[srcOffset+1];
      return;
    case 1:
      src[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,src,dstOffset,length);
    }
  }
  static void uncheckedCopy(double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert src!=dst || (length>5||dstOffset+length<=srcOffset||srcOffset<=dstOffset);
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    switch(length)
    {
    case 5:
      dst[dstOffset+4]=src[srcOffset+4];
    case 4:
      dst[dstOffset+3]=src[srcOffset+3];
    case 3:
      dst[dstOffset+2]=src[srcOffset+2];
    case 2:
      dst[dstOffset+1]=src[srcOffset+1];
    case 1:
      dst[dstOffset]=src[srcOffset];
      return;
    default:
      System.arraycopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static <SRC extends DST,DST> void semicheckedReverseCopy(SRC[] src,int srcOffset,DST[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static <SRC extends DST,DST> void semicheckedCopy(SRC[] src,int srcOffset,DST[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Character[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Character[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Integer[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Integer[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(Double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(Double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,Boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,Boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,Byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,Byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,Character[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,Character[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,Short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,Short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,Integer[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,Integer[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(long[] src,int srcOffset,Long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(long[] src,int srcOffset,Long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(float[] src,int srcOffset,Float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(float[] src,int srcOffset,Float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(double[] src,int srcOffset,Double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(double[] src,int srcOffset,Double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(long[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(long[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(float[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(float[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(double[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(double[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(boolean[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(boolean[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(byte[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(byte[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(char[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(char[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(short[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(short[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(int[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(int[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(long[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(long[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(long[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(long[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedReverseCopy(float[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedReverseCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static  void semicheckedCopy(float[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>=0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    if(length!=0)
    {
      uncheckedCopy(src,srcOffset,dst,dstOffset,length);
    }
  }
  static void uncheckedReverseCopy(Boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(boolean)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(boolean)(src[--length])){}
  }
  static void uncheckedCopy(Boolean[] src,int srcOffset,boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(boolean)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(boolean)(src[--length])){}
  }
  static void uncheckedReverseCopy(Byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(byte)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(byte)(src[--length])){}
  }
  static void uncheckedCopy(Byte[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(byte)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(byte)(src[--length])){}
  }
  static void uncheckedReverseCopy(Character[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(char)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(char)(src[--length])){}
  }
  static void uncheckedCopy(Character[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(char)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(char)(src[--length])){}
  }
  static void uncheckedReverseCopy(Short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(short)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(short)(src[--length])){}
  }
  static void uncheckedCopy(Short[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(short)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(short)(src[--length])){}
  }
  static void uncheckedReverseCopy(Integer[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(int)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedCopy(Integer[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(int)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedReverseCopy(Long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedCopy(Long[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedReverseCopy(Float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(Float[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(Double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(Double[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,Boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Boolean)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Boolean)(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,Boolean[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Boolean)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Boolean)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,Byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Byte)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Byte)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,Byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Byte)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Byte)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,Character[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Character)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Character)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,Character[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Character)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Character)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,Short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Short)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Short)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,Short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Short)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Short)(src[--length])){}
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,Integer[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Integer)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Integer)(src[--length])){}
  }
  static void uncheckedCopy(int[] src,int srcOffset,Integer[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Integer)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Integer)(src[--length])){}
  }
  static void uncheckedReverseCopy(long[] src,int srcOffset,Long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Long)(src[--length])){}
  }
  static void uncheckedCopy(long[] src,int srcOffset,Long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Long)(src[--length])){}
  }
  static void uncheckedReverseCopy(float[] src,int srcOffset,Float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Float)(src[--length])){}
  }
  static void uncheckedCopy(float[] src,int srcOffset,Float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Float)(src[--length])){}
  }
  static void uncheckedReverseCopy(double[] src,int srcOffset,Double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Double)(src[--length])){}
  }
  static void uncheckedCopy(double[] src,int srcOffset,Double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Double)(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(int[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(long[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(long[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(float[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(float[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(double[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(Object)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedCopy(double[] src,int srcOffset,Object[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(Object)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(Object)(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(byte)TypeUtil.castToByte(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(byte)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,byte[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(byte)TypeUtil.castToByte(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(byte)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(char)TypeUtil.castToChar(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(char)TypeUtil.castToChar(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,char[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(char)TypeUtil.castToChar(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(char)TypeUtil.castToChar(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(short)TypeUtil.castToByte(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(short)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(short)TypeUtil.castToByte(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(short)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(int)TypeUtil.castToByte(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(int)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(int)TypeUtil.castToByte(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(int)TypeUtil.castToByte(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)TypeUtil.castToLong(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)TypeUtil.castToLong(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)TypeUtil.castToLong(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)TypeUtil.castToLong(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)TypeUtil.castToFloat(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)TypeUtil.castToFloat(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)TypeUtil.castToFloat(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)TypeUtil.castToFloat(src[--length])){}
  }
  static void uncheckedReverseCopy(boolean[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)TypeUtil.castToDouble(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)TypeUtil.castToDouble(src[--length])){}
  }
  static void uncheckedCopy(boolean[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)TypeUtil.castToDouble(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)TypeUtil.castToDouble(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(short)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(short)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,short[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(short)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(short)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(int)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(int)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(byte[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(byte[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(int)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(int)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(char[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(char[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(int)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,int[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(int)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(int)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(short[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(short[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(long)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedCopy(int[] src,int srcOffset,long[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(long)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(long)(src[--length])){}
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(int[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(int[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(int[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(long[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(float)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedCopy(long[] src,int srcOffset,float[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(float)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(float)(src[--length])){}
  }
  static void uncheckedReverseCopy(long[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(long[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedReverseCopy(float[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset]=(double)(src[length+=(srcOffset-1)]);length!=srcOffset;dst[++dstOffset]=(double)(src[--length])){}
  }
  static void uncheckedCopy(float[] src,int srcOffset,double[] dst,int dstOffset,int length)
  {
    //assert srcOffset>=0;
    //assert dstOffset>=0;
    //assert length>0;
    //assert srcOffset+length<=src.length;
    //assert dstOffset+length<=dst.length;
    for(dst[dstOffset+=--length]=(double)(src[length+=srcOffset]);length!=srcOffset;dst[--dstOffset]=(double)(src[--length])){}
  }
}
