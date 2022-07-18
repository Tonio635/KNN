package knn.knn.mining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import knn.knn.data.Data;
import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.DbAccess;
import knn.knn.database.InsufficientColumnNumberException;
import knn.knn.server.Server;
import knn.knn.example.Example;

/**
 * Classe che serve per mettersi in contatto con i vari Controller.
 */
@Service("mainKNNService")
public class KNNService implements IKNNService {

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
            throws TrainingDataException, ClassNotFoundException,
            IOException, InsufficientColumnNumberException, DatabaseConnectionException {

        KNN k = null;

        switch (formato) {
            /** File testuale */
            case 1:
                k = new KNN(new Data(nome));
                k.salva(nome + ".dmp");
                break;

            /** File binario */
            case 2:
                try {
                    k = KNN.carica(nome);
                } catch (IOException | ClassNotFoundException e) {
                    throw new TrainingDataException("File non trovato.");
                }

                break;

            /** Database */
            case 3:
                k = new KNN(new Data(new DbAccess(), nome));
                break;
        }

        Server.addKNN(Long.valueOf(id), k);

        return k.getJSONString();
    }

    /**
     * Restituisce i valori della predizione del miner in JSON
     * 
     * @param id indica l'identificativo del client
     * @param e indica l'esempio sulla quale eseguire la predizione
     * @param k intero che indica di eseguire la media sui k esempi più vicini
     * @return stringa contentente i valori del miner con la predizione in JSON
     * @throws Exception eccezione controllata generica.
     */
    public String getPredizione(Long id, String e, Integer k) throws Exception {

        KNN modello = Server.getKNN(id);

        if (modello == null) {
            throw new Exception("Modello KNN non trovato, si prega di reinserire il modello nuovamente!");
        }

        ObjectMapper mapper = new ObjectMapper();
        HashMap<Object, Object> obj = mapper.readValue(e, HashMap.class);

        ArrayList<Object> l = (ArrayList<Object>) obj.values().toArray()[0];
        Example ex = new Example(l.size());

        int i = 0;
        for (Object o : l) {
            if (o instanceof String)
                ex.set(o, i++);
            else
                ex.set(Double.valueOf((Integer) o), i++);
        }

        return modello.getJSONPredizione(ex, k);
    }

}
