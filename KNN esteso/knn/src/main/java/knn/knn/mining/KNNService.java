package knn.knn.mining;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import knn.knn.data.Data;
import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.DbAccess;
import knn.knn.database.InsufficientColumnNumberException;

@Service("mainKNNService")
public class KNNService implements IKNNService {

    public String getModello(Integer formato, String nome) throws TrainingDataException, ClassNotFoundException,
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

        return k.getJSONString();
    }

}