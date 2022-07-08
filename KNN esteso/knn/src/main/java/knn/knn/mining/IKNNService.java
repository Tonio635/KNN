package knn.knn.mining;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.TrainingDataException;
import knn.knn.database.DatabaseConnectionException;
import knn.knn.database.InsufficientColumnNumberException;

public interface IKNNService {

    public String getModello(Integer formato,String nome) throws JsonProcessingException,TrainingDataException,ClassNotFoundException, IOException,InsufficientColumnNumberException, DatabaseConnectionException;

}
