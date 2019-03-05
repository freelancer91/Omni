package omni.impl.seq;

public class InvalidReadIndexException extends IndexOutOfBoundsException{
    private static final long serialVersionUID=2396437187531402905L;
    public InvalidReadIndexException(){
        super();
    }
    public InvalidReadIndexException(int index){
        super(index);
    }
    public InvalidReadIndexException(int index,int size){
        super("index=" + index + "; size=" + size);
    }
    public InvalidReadIndexException(String s){
        super(s);
    }

}
