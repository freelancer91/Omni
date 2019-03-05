package omni.impl.seq;

public class InvalidWriteIndexException extends IndexOutOfBoundsException{
    private static final long serialVersionUID=2396437187531402905L;
    public InvalidWriteIndexException(){
        super();
    }
    public InvalidWriteIndexException(int index){
        super(index);
    }
    public InvalidWriteIndexException(int index,int size){
        super("index=" + index + "; size=" + size);
    }
    public InvalidWriteIndexException(String s){
        super(s);
    }
}
