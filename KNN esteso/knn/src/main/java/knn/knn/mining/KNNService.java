package knn.knn.mining;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import knn.knn.data.Data;
import knn.knn.data.TrainingDataException;
import knn.knn.database.DbAccess;


@Service("mainKNNService")
public class KNNService implements IKNNService{
    
    public String getModello(Integer formato,String nome) throws JsonProcessingException, TrainingDataException{
        LinkedList<LinkedList<LinkedList<Double>>> result = new LinkedList<LinkedList<LinkedList<Double>>>();
        KNN k;
        ObjectMapper mapper = new ObjectMapper(); 
        String jsonResult = mapper.writeValueAsString(result);
        System.out.println(jsonResult);
        
        if (formato == 1){
            k = new KNN(new Data(nome));
            result.add(k.toString());
        }else if (formato == 2){
            k = KNN.carica(nome);
            result.add();
        }else if (formato == 3){
            k = new KNN(new Data(new DbAccess(),nome));
            result.add();
        }

    }

}
