package omni.util;

public class NotYetImplementedException extends UnsupportedOperationException{

  private static final long serialVersionUID=5443194191324596591L;

  public NotYetImplementedException(){
    super();
  }

  public NotYetImplementedException(String message,Throwable cause){
    super(message,cause);
  }

  public NotYetImplementedException(String message){
    super(message);
  }

  public NotYetImplementedException(Throwable cause){
    super(cause);
  }
  
}
