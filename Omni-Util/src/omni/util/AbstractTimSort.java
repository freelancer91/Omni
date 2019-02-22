package omni.util;

abstract class AbstractTimSort{
    private transient int stackSize;
    private transient final int runLenAndBase[];
    AbstractTimSort(int nRemaining){
        this.runLenAndBase=new int[nRemaining<120?10:nRemaining<1542?20:nRemaining<119151?48:98];
    }
    /**
     * Returns the minimum acceptable run length for an array of the specified
     * length. Natural runs shorter than this will be extended with a binary sort
     * 
     * Roughly speaking the computation is:
     * 
     * If n < 32, then return n (it's too small to bother with fancy stuff) Else if
     * n is an exact power of 2, return 16 Else return an int k, 16 <= k <= 32 such
     * that n/k is close to, but not strictly less than an exact power of 2
     * 
     * @param n The length of the array to be sorted
     * @return the length of the minimum run to be merged
     */
    static int minRunLength(int n){
        // assert n >= 0;
        int r=0; // Becomes 1 if any 1 bits are shifted off
        while(n>=32){
            r|=n&1;
            n>>=1;
        }
        return n+r;
    }
    /**
     * Push the specified run to the pending run stack, Then examine the stack of
     * runs waiting to be merged and merge adjacent runs until ths stack invariants
     * are restablished: 1. runLenAndBase[i-6] > runLenAndBase[i-4] +
     * runLenAndBase[i-2] 2. runLenAndBase[i-4] > runLenAndBase[i-2]
     */
    final void mergeCollapse(int runBase,int runLength){
        int stackSize;
        int[] runLenAndBase;
        (runLenAndBase=this.runLenAndBase)[stackSize=this.stackSize]=runLength;
        runLenAndBase[++stackSize]=runBase;
        ++stackSize;
        int n;
        gotoReturn:do{
            gotoMergeAt:for(;;){
                switch((n=stackSize-4)>>1){
                default:
                    // stackSize >= 8
                    if(runLenAndBase[n-4]<=runLenAndBase[n]+runLenAndBase[n-2]){
                        break;
                    }
                case 1:
                    if(runLenAndBase[n-2]<=runLenAndBase[n]+runLenAndBase[n+2]){
                        break;
                    }
                case 0:
                    if(runLenAndBase[n]<=runLenAndBase[n+2]){
                        break gotoMergeAt;
                    }
                case -1:
                    // The invariants are established
                    break gotoReturn;
                }
                if(runLenAndBase[n-2]<runLenAndBase[n+2]){
                    n-=2;
                }
                break gotoMergeAt;
            }
            // gotoMergeAt goes here
        }while((stackSize=mergeAt(n,stackSize,runLenAndBase))>2);
        // gotoReturn goes here
        this.stackSize=stackSize;
    }
    /**
     * Merges all runs on the stack unless only one remains. This method is called
     * once to complete the sort.
     */
    final void mergeForceCollapse(){
        int stackSize;
        if((stackSize=this.stackSize)>2){
            for(final var runLenAndBase=this.runLenAndBase;(stackSize=mergeAt(stackSize-4,stackSize,
                    runLenAndBase))>2;){}
            // I'm fairly certain this this is dead code
            // int n;
            // do
            // {
            // if((n=stackSize-4)>0&&runLenAndBase[n-2]<runLenAndBase[n+2])
            // {
            // n-=2;
            // }
            // }
            // while((stackSize=mergeAt(n,stackSize,runLenAndBase))>2);
        }
        // no need to set the stack size field since we are done
    }
    /**
     * Merges the two runs at the stack indices n and n+2. Run n must be the
     * penultimate or antepenultimate run on the stack. In other words, n must be
     * equal to stackSize-4 or stackSize-6;
     * 
     * @param The stack index of the first of two runs to merge
     * @return the new stack size
     */
    abstract int mergeAt(int n,int stackSize,int[] runLenAndBase);
}
