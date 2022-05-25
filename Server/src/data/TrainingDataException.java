package data;

/**
 * Eccezione che controlla che il TrainingSet sia stato acquisito in maniera corretta
 */
public class TrainingDataException extends Exception{
    
    public TrainingDataException(){

    }

    public TrainingDataException(String message){
        super(message);
    }
}
