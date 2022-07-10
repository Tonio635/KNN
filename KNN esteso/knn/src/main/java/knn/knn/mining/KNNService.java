package knn.knn.mining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

@Service("mainKNNService")
public class KNNService implements IKNNService {

    public String getModello(Long id, Integer formato, String nome)
            throws TrainingDataException, ClassNotFoundException,
            IOException, InsufficientColumnNumberException, DatabaseConnectionException {

        KNN k = null;

        switch (formato) {
            // File testuale
            case 1:
                k = new KNN(new Data(nome));
                k.salva(nome + ".dmp");
                break;

            // File binario
            case 2:
                k = KNN.carica(nome);
                break;

            // Database
            case 3:
                k = new KNN(new Data(new DbAccess(), nome));
                break;
        }

        Server.addKNN(new Long(id), k);

        return k.getJSONString();
    }

    /**
     * Restituisce i valori della predizione del miner in JSON
     * 
	 * @param e indica l'esempio sulla quale eseguire la predizione
	 * @param k intero che indica di eseguire la media sui k esempi più vicini
     * @return stringa contentente i valori del miner con la predizione in JSON
	 * @throws JsonProcessingException eccezione controllata nel caso in cui la
	 *                                 conversione in JSON dovesse dare problemi
     */
    public String getPredizione(Long id, String e, Integer k) throws Exception {

        KNN modello = Server.getKNN(id);

        if (modello == null) {
            throw new Exception("Modello KNN non trovato, si prega di reinserire il modello nuovamente!");
        }

        ObjectMapper mapper = new ObjectMapper();
        HashMap<Object, Object> obj = mapper.readValue(e, HashMap.class);

        ArrayList<Object> l = (ArrayList<Object>)obj.values().toArray()[0];
        Example ex = new Example(l.size());

        int i = 0;
        for(Object o: l) {
            if (o instanceof String)
                ex.set(o, i++);
            else
                ex.set(new Double((Integer)o), i++);
        }

        return modello.getJSONPredizione(ex, k);
    }

}
