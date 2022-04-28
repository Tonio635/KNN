package mining;
import data.Data;
import data.Example;
import utility.Keyboard;
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
     * Predice il valore target tramite la distanza minima di Hamming e l'esempio inseriti da tastiera
     * 
     * @return predizione del valore target dell'esempio passato come parametro
     */
    public double predict(){
        int k;
        Example e = new Example(data.getNumberOfExplanatoryAttributes());

        for (int i=0; i < data.getNumberOfExplanatoryAttributes(); i++){
            System.out.println("Inserisci il parametro n°" + (i+1) + " dell'esempio: ");
		    e.set(Keyboard.readString(), i);
        }
        
        System.out.println("Inserisci la distanza minima di Hamming: ");
        k = Keyboard.readInt();

        return predict(e, k);
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
