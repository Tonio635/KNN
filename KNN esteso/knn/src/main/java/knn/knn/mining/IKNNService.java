package knn.knn.mining;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.InsufficientColumnNumberException;
import knn.knn.example.Example;

public interface IKNNService {

    public String getModello(Long id, Integer formato,String nome) throws JsonProcessingException,TrainingDataException,ClassNotFoundException, IOException,InsufficientColumnNumberException, DatabaseConnectionException;
    public String getModello(Integer id, Integer formato,String nome) throws JsonProcessingException,TrainingDataException,ClassNotFoundException, IOException,InsufficientColumnNumberException, DatabaseConnectionException;
    //public String getPredizione(Example e, Integer k, Integer id) throws JsonProcessingException;

}
