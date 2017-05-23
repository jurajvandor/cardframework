package cz.muni.fi.cardframework.Network;

/**
 * Created by Juraj on 18.05.2017.
 */

/**
 * Exception thrown by this package
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
