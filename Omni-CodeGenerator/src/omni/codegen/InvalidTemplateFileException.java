package omni.codegen;

public class InvalidTemplateFileException extends Exception{
    InvalidTemplateFileException(){
        super();
    }
    InvalidTemplateFileException(String message,Throwable cause){
        super(message,cause);
    }
    InvalidTemplateFileException(String message){
        super(message);
    }
    InvalidTemplateFileException(Throwable cause){
        super(cause);
    }
    /**
     *
     */
    private static final long serialVersionUID=-8029473940345003590L;
}
