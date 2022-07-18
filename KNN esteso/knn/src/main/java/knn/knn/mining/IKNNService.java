package knn.knn.mining;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.InsufficientColumnNumberException;

/**
 * Interfaccia che contiene i metodi che verranno utilizzati dai vari Service
 * del Knn.
 */
public interface IKNNService {

    /**
     * Restituisce il modello del training set.
     * 
     * @param id      identificativo del client.
     * @param formato indica il formato del training set da aprire.
     * @param nome    indica il nome del file.
     * @return Stringa che indica il training convertito dal formato JSON.
     * @throws JsonProcessingException           Eccezione controllata sollevata nel
     *                                           caso in cui dovessero presentarsi
     *                                           problemi
     *                                           durante l'elaborazione di un
     *                                           contenuto JSON.
     * @throws TrainingDataException             Eccezione controllata sollevata nel
     *                                           caso in cui il training set non
     *                                           dovesse essere acquisito in maniera
     *                                           corretta.
     * @throws ClassNotFoundException            Eccezione usata per controllare il
     *                                           caso in cui
     *                                           si dovesse provare a risalire una
     *                                           classe
     *                                           tramite stringa ma questa classe
     *                                           non dovesse
     *                                           essere trovata.
     * @throws IOException                       Eccezione per il controllo dei
     *                                           flussi Input/Output.
     * @throws InsufficientColumnNumberException Eccezione controllata sollevata nel
     *                                           caso in cui non si riesca a
     *                                           caricare la colonna dei target dal
     *                                           database o nel caso in cui la
     *                                           tabella del database sia senza
     *                                           colonne.
     * @throws DatabaseConnectionException       Eccezione controllata sollevata nel
     *                                           caso
     *                                           in cui non dovesse riuscire la
     *                                           connessione al database.
     */
    public String getModello(Long id, Integer formato, String nome)
            throws JsonProcessingException, TrainingDataException, ClassNotFoundException, IOException,
            InsufficientColumnNumberException, DatabaseConnectionException;

    /**
     * Restituisce i valori della predizione del miner in JSON
     * 
     * @param id indica l'identificativo del client
     * @param e  indica l'esempio sulla quale eseguire la predizione
     * @param k  intero che indica di eseguire la media sui k esempi più vicini
     * @return stringa contentente i valori del miner con la predizione in JSON
     * @throws Exception eccezione controllata generica.
     */
    public String getPredizione(Long id, String e, Integer k) throws Exception;

}
