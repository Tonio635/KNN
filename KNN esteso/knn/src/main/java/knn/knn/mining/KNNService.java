package knn.knn.mining;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.Data;
import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.DbAccess;
import knn.knn.database.InsufficientColumnNumberException;
import knn.knn.server.Server;
import knn.knn.example.Example;

@Service("mainKNNService")
public class KNNService implements IKNNService {

    public String getModello(Long id, Integer formato, String nome) throws TrainingDataException, ClassNotFoundException,
            IOException, InsufficientColumnNumberException, DatabaseConnectionException {
        
        KNN k = null;

        switch(formato)
        {
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
    
    
    /*public String getPredizione(Example e, Integer k, Integer id) throws JsonProcessingException{
        /*
         * Preleva il modello knn in base all'id
         * Esegui la predizione sul knn, passando l'esempio e l'intero k
         * Restituisco il formato , stesso formato di sopra (senza il 2 array),aggiungo anche l'esempio
         * [example,x(quelle di prima),y(sono sempre quelle)]
         * Ordina l'array(quello di sopra) in base alla distance dei due example passato ma i valori di x che restituisce devono essere calcolati
         * al vecchio example "0" e 0.0

        

        
        

        return k.getJSONString();
    }*/

}
