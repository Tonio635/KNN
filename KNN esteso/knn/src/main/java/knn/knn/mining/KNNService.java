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
public class KNNService implements IKNNService{
    
    public String getModello(Integer formato,String nome) throws TrainingDataException, ClassNotFoundException, IOException, InsufficientColumnNumberException, DatabaseConnectionException{
        LinkedList<LinkedList<LinkedList<Object>>> result = new LinkedList<LinkedList<LinkedList<Object>>>();
        KNN k;
        Data trainingSet = new Data(nome);
        result = trainingSet.concatenaElementi();

        if (formato == 1){
            k = new KNN(new Data(nome));
            k.salva(nome);
            
        }else if (formato == 2){
            k = KNN.carica(nome);
            
        }else if (formato == 3){
            k = new KNN(new Data(new DbAccess(),nome));
           
        }

        ObjectMapper mapper = new ObjectMapper(); 
        String jsonResult = mapper.writeValueAsString(result);
        
        return jsonResult;
    }

}
