package omni.util;

import java.util.TreeMap;

public class NotYetImplementedException extends UnsupportedOperationException{

  private static final long serialVersionUID=5443194191324596591L;

  public static final TreeMap<Integer,Integer> COUNT_MAP=new TreeMap<>();
  
  public NotYetImplementedException(int locationMarker){
    super();
    synchronized(COUNT_MAP) {
        COUNT_MAP.compute(locationMarker,(loc,count)->{
            if(count==null) {
                return Integer.valueOf(1);
            }
            return count+1;
         });
    }
    
  }
  
  public static void reportCounts() {
      int totalCount=0;
      for(var val:COUNT_MAP.values()) {
          totalCount+=val;
      }
      final double finalCount=totalCount;
      System.out.println("total count = "+totalCount);
      COUNT_MAP.forEach((key,count)->{
          double percent=100.0*((double)count/finalCount);
          System.out.println("location = "+key+" count = "+count+" percent = "+percent+"%");
      });
      
  }

  
}
