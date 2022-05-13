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
     * Predice il valore target tramite la distanza minima di Hamming e l'esempio inseriti da tastiera
     * 
     * @return predizione del valore target dell'esempio passato come parametro
     */
    public double predict ()
    {
        Example e = data.readExample();
        int k=0;
        do {
            System.out.print("Inserisci valore k>=1:");
            k=Keyboard.readInt();
        }while (k<1);
        return data.avgClosest(e, k);
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
