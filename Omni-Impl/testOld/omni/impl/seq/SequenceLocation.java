package omni.impl.seq;

public enum SequenceLocation{
    BEGINNING(null,true),NEARBEGINNING(null,false),MIDDLE(null,false),NEAREND(null,false),END(null,false),
    IOBLO(IndexOutOfBoundsException.class,true),IOBHI(IndexOutOfBoundsException.class,true);
    public final Class<? extends Throwable> expectedException;
    public final boolean validForEmpty;
    SequenceLocation(Class<? extends Throwable> expectedException,boolean validForEmpty){
        this.expectedException=expectedException;
        this.validForEmpty=validForEmpty;
    }
}
