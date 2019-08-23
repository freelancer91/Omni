package omni.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.TreeMap;

public class NotYetImplementedException extends UnsupportedOperationException{

  private static final long serialVersionUID=5443194191324596591L;

  public static final HashMap<String,HashMap<String,HashMap<Integer,Integer>>> COUNT_MAP=new HashMap<>();
  
  public static NotYetImplementedException getNYI() {
      final NotYetImplementedException exception;
      final StackTraceElement s;
      final var fileName=(s=(exception=new NotYetImplementedException()).getStackTrace()[1]).getFileName();
      final HashMap<String,HashMap<Integer,Integer>> mapForFile;
      synchronized(COUNT_MAP) {
          mapForFile=COUNT_MAP.computeIfAbsent(fileName,fn->new HashMap<>());
      }
      final var methodName=s.getMethodName();
      final HashMap<Integer,Integer> mapForMethod;
      synchronized(mapForFile) {
          mapForMethod=mapForFile.computeIfAbsent(methodName,mn->new HashMap<>());
      }
      final var lineNumber=s.getLineNumber();
      synchronized(mapForMethod) {
          mapForMethod.compute(lineNumber,(ln,count)->{
            if(count==null) {
                return Integer.valueOf(1);
            }
            return count+1;
         });
      }
      return exception;
  }
  
  private NotYetImplementedException() {
      super();
  }
  
  private static class ReportData<K,V> extends TreeMap<K,V>{
      long total;
      
      
  }
  
  public static void reportCounts() {
      if(COUNT_MAP.isEmpty()) {
          System.out.println("No counts found");
          return;
      }
      final var sortedCountMap=new ReportData<String,ReportData<String,ReportData<Integer,Integer>>>();
      long grandTotal=0L;
      for(var countMapEntry:COUNT_MAP.entrySet()) {
          final var fileName=countMapEntry.getKey();
          final var fileNameMap=countMapEntry.getValue();
          final var sortedMapForFileName=new ReportData<String,ReportData<Integer,Integer>>();
          long totalForFile=0;
          for(var fileNameMapEntry:fileNameMap.entrySet()) {
              final var methodName=fileNameMapEntry.getKey();
              final var mapForMethodName=fileNameMapEntry.getValue();
              long totalForMethod=0;
              final var sortedMapForMethod=new ReportData<Integer,Integer>();
              for(var mapForMethodNameEntry:mapForMethodName.entrySet()) {
                  final var lineNumberEncounters=mapForMethodNameEntry.getValue();
                  long asLong=lineNumberEncounters.longValue();
                  totalForMethod+=asLong;
                  totalForFile+=asLong;
                  grandTotal+=asLong;
                  sortedMapForMethod.put(mapForMethodNameEntry.getKey(),lineNumberEncounters);
              }
              sortedMapForMethod.total=totalForMethod;
              
              sortedMapForFileName.put(methodName,sortedMapForMethod);
          }
          sortedMapForFileName.total=totalForFile;
          sortedCountMap.put(fileName,sortedMapForFileName);
      }
      System.out.println("Grand total = "+grandTotal);
      double grandTotalDouble=grandTotal;
      DecimalFormat df = new DecimalFormat("##0.000");
     sortedCountMap.forEach((fileName,fileData)->{
         long fileTotal=fileData.total;
         double fileTotalDouble=fileTotal;
         double filePercent=fileTotalDouble/grandTotalDouble*100.0;
         System.out.printf("  File %s total = %d; %7s%%%n",fileName,fileTotal,df.format(filePercent));
         
         fileData.forEach((methodName,methodData)->{
             long methodTotal=methodData.total;
             double methodTotalDouble=methodTotal;
             double methodPercentOfFile=methodTotalDouble/fileTotalDouble*100.0;
             double methodPercent=methodTotalDouble/grandTotalDouble*100.0;
             System.out.printf("    Method %s total = %d; %7s%% (of file); %7s%% (of total)%n",methodName,methodTotal,df.format(methodPercentOfFile),df.format(methodPercent));
             methodData.forEach((lineNumber,lineNumberCount)->{
                 int lineNumberCountInt=lineNumberCount.intValue();
                 double lineNumberCountDouble=lineNumberCountInt;
                 double lineNumberPercentOfMethod=lineNumberCountDouble/methodTotalDouble*100.0;
                 double lineNumberPercentOfFile=lineNumberCountDouble/fileTotalDouble*100.0;
                 double lineNumberPercent=lineNumberCountDouble/grandTotalDouble*100.0;
                 System.out.printf("      Line number %5d total = %11d; %7s%% (of method); %7s%% (of file); %7s%% (of total)%n",lineNumber,lineNumberCount,df.format(lineNumberPercentOfMethod),df.format(lineNumberPercentOfFile),df.format(lineNumberPercent));

             });
         });
     });
      
      
      
      
      
      
      
      
      
  }
  
  
  
  
  
  //public static final TreeMap<Integer,Integer> COUNT_MAP=new TreeMap<>();
  
  
  
//  public NotYetImplementedException(int locationMarker){
//    super();
//    synchronized(COUNT_MAP) {
//        COUNT_MAP.compute(locationMarker,(loc,count)->{
//            if(count==null) {
//                return Integer.valueOf(1);
//            }
//            return count+1;
//         });
//    }
//    
//  }
//  
//  public static void reportCounts() {
//      int totalCount=0;
//      for(var val:COUNT_MAP.values()) {
//          totalCount+=val;
//      }
//      final double finalCount=totalCount;
//      System.out.println("total count = "+totalCount);
//      COUNT_MAP.forEach((key,count)->{
//          double percent=100.0*((double)count/finalCount);
//          System.out.println("location = "+key+" count = "+count+" percent = "+percent+"%");
//      });
//      
//  }

  
}
