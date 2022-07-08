package knn.knn.mining;

import com.fasterxml.jackson.core.JsonProcessingException;

import knn.knn.data.TrainingDataException;

public interface IKNNService {

    public String getModello(Integer formato,String nome) throws JsonProcessingException,TrainingDataException;

}
