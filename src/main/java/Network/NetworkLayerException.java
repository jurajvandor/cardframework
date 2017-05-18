package Network;

/**
 * Created by Juraj on 18.05.2017.
 */
public class NetworkLayerException extends RuntimeException{
    NetworkLayerException(){
        super();
    }
    NetworkLayerException(Throwable t){
        super(t);
    }
    NetworkLayerException(String s){
        super(s);
    }
    NetworkLayerException(String s, Throwable t){
        super(s, t);
    }
}
