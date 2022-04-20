/**
 * Modella il miner
 */
public class KNN {
    // Modella il training set
    private Data data;

    /**
     * Costruttore della classe
     * Avvalora il training set
     * 
     * @param trainingSet indica il training set con cui avvalorare l'attributo
     */
    public KNN(Data trainingSet){
        data = trainingSet;
    }

    /**
     * Predice il valore target dell’esempio passato come parametro
     * 
     * @param e indica l'esempio di cui predire il valore target
     * @param k indica la vicinanza massima degli esempi da considerare
     * @return predizione del valore target dell'esempio passato come parametro
     */
    public double predict(Example e, int k){
        double predict;
        predict = data.avgClosest(e, k);
        return predict;
    }

    /**
     * Restituisce i valori del miner
     * 
     * @return stringa contentente i valori del miner
     */
    public String toString(){
        return data.toString();
    }
}
