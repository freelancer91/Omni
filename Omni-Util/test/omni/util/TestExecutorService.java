package omni.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestExecutorService {
    private static final Object monitor=new Object();


    private static final LinkedBlockingQueue<Runnable> testQueue;
    private static final AtomicInteger totalNumTasks;
    private static final AtomicInteger totalCompletedTasks;
    private static int numWorkers=Runtime.getRuntime().availableProcessors();
    private static Thread[] workers;
    public static void setNumWorkers(int numWorkers) {
        if(numWorkers<1) {
            numWorkers=1;
        }else {
            final int maxNumThreads;
            if(numWorkers>(maxNumThreads=Runtime.getRuntime().availableProcessors())) {
                numWorkers=maxNumThreads;
            }
        }
        final int oldNumWorkers;
        if(numWorkers!=(oldNumWorkers=TestExecutorService.numWorkers)) {
            reset();
            for(int i=0;i<oldNumWorkers;++i) {
                try {
                    workers[i].interrupt();
                }catch(Throwable t) {
                    //swallow
                }
            }
            TestExecutorService.numWorkers=numWorkers;
            workers=new Thread[numWorkers];
            for(int i=0;i<numWorkers;++i) {
                Thread worker;
                workers[i]=worker=new Thread(TestExecutorService::worker);
                worker.start();
            }
        }
    }
    public static int getNumWorkers() {
       return numWorkers; 
    }

    static {
        testQueue=new LinkedBlockingQueue<>();
        totalNumTasks=new AtomicInteger(0);
        totalCompletedTasks=new AtomicInteger(0);
        int nw;
        if((nw=numWorkers)>1) {
            workers=new Thread[nw];
            for(int i=0;i<nw;++i) {
                Thread worker;
                workers[i]=worker=new Thread(TestExecutorService::worker);
                worker.start();
            }
        }

    }
    

    private static void worker() {
        try {
            var tct=totalCompletedTasks;
            var m=monitor;
            for(var tq=testQueue;;)
            {
                tq.take().run();
                tct.incrementAndGet();
                synchronized(m) {
                    m.notify();
                }
            }
        }catch(InterruptedException e) {
            //swallow
        }catch(Error e) {
            if(e instanceof OutOfMemoryError) {
                throw e;
            }
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void submitTest(Runnable test) {
        totalNumTasks.incrementAndGet();
        if(!testQueue.offer(test)) {
            test.run();
            totalCompletedTasks.incrementAndGet();
        }
    }
    public static void suspend() {
        System.out.println("here");
    }


    public static void completeAllTests() {
        try {
            final int tnt=totalNumTasks.get();
            final var tct=totalCompletedTasks;
            Object m=monitor;
            for(var tq=testQueue;;){
                final var myTask=tq.poll();
                if(tct.get()==tnt) {
                    return;
                }
                if(myTask!=null) {
                    myTask.run();
                    tct.incrementAndGet();
                }else {
                    //wait for the other threads to finish
                    for(;;) {
                        if(tct.get()==tnt) {
                            return;
                        }
                        synchronized(m) {
                            m.wait(1000);
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
    private static void finishWritingProgressBar(int nextPercent){
        while(nextPercent <= 100){
            nextPercent=incrementProgressBar(nextPercent);
        }
        System.out.println();
    }
    private static int incrementProgressBar(int nextPercent){
        if(nextPercent % 10 == 0){
            System.out.print(nextPercent + "%");
        }else{
            System.out.print('.');
        }
        return nextPercent + 1;
    }
    public static void completeAllTests(String testName) {
        final int tnt;
        System.out.println("Running "+(tnt=totalNumTasks.get())+" "+testName+" tests");
        System.out.print("0%");
        try {
            int nextPercent;
            int threshold=(int)Math.ceil((nextPercent=1)/100.0*tnt);
            final var tct=totalCompletedTasks;
            Object m=monitor;
            for(var tq=testQueue;;){
                final var myTask=tq.poll();
                int currCompleted;
                if((currCompleted=tct.get())==tnt) {
                    finishWritingProgressBar(nextPercent);
                    return;
                }
                while(currCompleted>=threshold) {
                    threshold=(int)Math.ceil((nextPercent=incrementProgressBar(nextPercent)) / 100.0 * tnt);
                }
                if(myTask!=null) {
                    myTask.run();
                    tct.incrementAndGet();
                }else {
                    //wait for the other threads to finish
                    for(;;) {
                        if((currCompleted=tct.get())==tnt) {
                            finishWritingProgressBar(nextPercent);
                            return;
                        }else{
                            while(currCompleted >= threshold){
                                threshold=(int)Math.ceil((nextPercent=incrementProgressBar(nextPercent)) / 100.0 * tnt);
                            }
                        }
                        synchronized(m) {
                            m.wait(1000);
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
    public static int getNumRemainingTasks() {
        return totalNumTasks.get()-totalCompletedTasks.get();
    }
    public static void reset() {
        totalNumTasks.set(0);
        totalCompletedTasks.set(0);
        testQueue.clear();
    }
}
