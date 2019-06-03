package omni.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestExecutorService {
  private final LinkedBlockingQueue<Runnable> testQueue;
  private final AtomicInteger totalNumTasks;
  private final AtomicInteger totalCompletedTasks;
  public final int numWorkers;
  
  public TestExecutorService() {
    this(Runtime.getRuntime().availableProcessors());
  }
  
  public TestExecutorService(int numWorkers){
    super();
    if(numWorkers<=0) {
      numWorkers=Runtime.getRuntime().availableProcessors();
    }
    this.numWorkers=numWorkers;
    this.testQueue=new LinkedBlockingQueue<>();
    this.totalNumTasks=new AtomicInteger(0);
    this.totalCompletedTasks=new AtomicInteger(0);
    if(numWorkers>1) {
      Thread[] workers;
      
      workers=new Thread[numWorkers];
      for(int i=0;i<numWorkers;++i) {
        Thread worker;
        workers[i]=worker=new Thread(this::worker);
        worker.start();
      }
    }
    
    
  }
  private void worker() {
    try {
      for(var testQueue=this.testQueue;;)
      {
        testQueue.take().run();
        totalCompletedTasks.incrementAndGet();
        synchronized(this) {
          notify();
        }
      }
    }catch(InterruptedException t) {
      throw new Error(t);
    }
  }
  
  public void submitTest(Runnable test) {
    totalNumTasks.incrementAndGet();
    if(!testQueue.offer(test)) {
      test.run();
      totalCompletedTasks.incrementAndGet();
    }
  }

  public void completeAllTests() {
    try {
      final int totalNumTasks=this.totalNumTasks.get();
      final var totalCompletedTasks=this.totalCompletedTasks;
      for(var testQueue=this.testQueue;;){
        final var myTask=testQueue.poll();
        if((totalCompletedTasks.get())==totalNumTasks) {
          return;
        }
        if(myTask!=null) {
          myTask.run();
          totalCompletedTasks.incrementAndGet();
        }else {
          //wait for the other threads to finish
          for(;;) {
            if((totalCompletedTasks.get())==totalNumTasks) {
              return;
            }
            synchronized(this) {
              wait(1000);
            }
          }
        }
      }
    }catch(InterruptedException e){
      throw new Error(e);
    }finally {
      reset();
    }
  }
  public void completeAllTests(String testName) {
    System.out.print("Running test "+testName+" 0%");
    try {
      final int totalNumTasks;
      int nextPercent;
      int threshold=(int)Math.ceil(((double)(nextPercent=1)/100.0)*(double)(totalNumTasks=this.totalNumTasks.get()));
      final var totalCompletedTasks=this.totalCompletedTasks;
      for(var testQueue=this.testQueue;;){
        final var myTask=testQueue.poll();
        int currCompleted;
        if((currCompleted=totalCompletedTasks.get())==totalNumTasks) {
          System.out.println("100% Finished "+totalNumTasks+" tests!");
          return;
        }
        while(currCompleted>=threshold) {
          if(nextPercent%10==0) {
            System.out.print(nextPercent+"%");
          }else {
            System.out.print('.');
          }
          threshold=(int)Math.ceil(((double)(++nextPercent)/100.0)*(double)totalNumTasks);
        }
        if(myTask!=null) {
          myTask.run();
          totalCompletedTasks.incrementAndGet();
        }else {
          //wait for the other threads to finish
          for(;;) {
            if((currCompleted=totalCompletedTasks.get())==totalNumTasks) {
              System.out.println("100% Finished "+totalNumTasks+" tests!");
              return;
            }else if(currCompleted>=threshold) {
              if(nextPercent%10==0) {
                System.out.print(nextPercent+"%");
              }else {
                System.out.print('.');
              }
              threshold=(int)Math.ceil(((double)(++nextPercent)/100.0)*(double)totalNumTasks);
            }
            synchronized(this) {
              wait(1000);
            }
          }
        }
      }
    }catch(InterruptedException e){
      throw new Error(e);
    }finally {
      reset();
    }
  }
  public int getNumRemainingTasks() {
    return totalNumTasks.get()-totalCompletedTasks.get();
  }
  public void reset() {
    this.totalNumTasks.set(0);
    this.totalCompletedTasks.set(0);
    this.testQueue.clear();
  }
}
