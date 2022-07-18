package knn.knn.mining;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.InsufficientColumnNumberException;

/**
 * Interfaccia che contiene i metodi che verranno utilizzati dai vari Service del Knn.
 */
public interface IKNNService {

    /**
     * Restituisce il modello del training set.
     * 
     * @id identificativo del client.
     * @formato indica il formato del training set da aprire.
     * @nome indica il nome del file.
     * @return Stringa che indica il training convertito dal formato JSON.
     */
    public String getModello(Long id, Integer formato,String nome) throws JsonProcessingException,TrainingDataException,ClassNotFoundException, IOException,InsufficientColumnNumberException, DatabaseConnectionException;
    
    /**
     * /**
     * Restituisce i valori della predizione del miner in JSON
     * 
     * @param id indica l'identificativo del client
     * @param e indica l'esempio sulla quale eseguire la predizione
     * @param k intero che indica di eseguire la media sui k esempi più vicini
     * @return stringa contentente i valori del miner con la predizione in JSON.
     */
    public String getPredizione(Long id, String e, Integer k) throws Exception;

}
