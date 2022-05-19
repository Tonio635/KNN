package mining;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import data.Data;
import example.Example;
import utility.Keyboard;

/**
 * Modella il miner
 */
public class KNN implements Serializable{
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
        int k = 0;
        do {
            System.out.print("Inserisci valore k>=1:");
            k = Keyboard.readInt();
        }while (k < 1);

        return data.avgClosest(e, k);
    }

    /**
     * Salva l'oggetto di classe KNN in un file binario <nomeFile>.dat
     * 
     * @param nomeFile in cui salvare il file (comprende l'estensione)
     * @throws IOException
     */
    public void salva(String nomeFile) throws IOException {
        FileOutputStream outFile = new FileOutputStream(nomeFile);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this);
        outStream.close();
    }

    /**
     * Carica da file un oggetto KNN e lo restituisce
     * 
     * @param nomeFile
     * @return oggetto KNN caricato da file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static KNN carica(String nomeFile) throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
        KNN obj = (KNN) in.readObject();
        in.close();

        return obj;
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
