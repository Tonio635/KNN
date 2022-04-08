public class KNN {
    private Data data;

    public KNN(Data trainingSet){
        data = trainingSet;
    }

    public double predict(Example e, int k){
        // TODO
        double predict;
        predict=data.avgClosest(e,k);
        return predict;
    }
}
