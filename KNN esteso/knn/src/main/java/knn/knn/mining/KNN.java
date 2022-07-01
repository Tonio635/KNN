package knn.knn.mining;

import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import knn.knn.data.Data;
import knn.knn.example.Example;

/**
 * Classe che modella il miner
 */
public class KNN implements Serializable {

    /** Attributo di classe Data, contiene l'intero training set. */
    private Data data;

    /**
     * Costruttore della classe KNN
     * Avvalora il training set
     * 
     * @param trainingSet indica il training set con cui avvalorare l'attributo
     */
    public KNN(Data trainingSet) {
        data = trainingSet;
    }

    /**
     * Predice il valore target utilizzando l'algoritmo del KNN, ovvero:
     * – Si applica la distanza di Hamming alle variabili discrete
     * – Si applica il minmax scaler alle variabile continue
     * 
     * @param out Oggetto di output stream utilizzato per mandare dati al client.
     * @param in  Oggetto di input stream utilizzato per ricevere dati dal client.
     * @return Predizione del valore target dell'esempio passato come parametro.
     * @throws IOException            Eccezione per il controllo dei flussi di
     *                                Input/Output.
     * @throws ClassNotFoundException Eccezione usata per controllare il caso in cui
     *                                si dovesse provare a risalire una classe
     *                                tramite stringa ma questa classe non dovesse
     *                                essere trovata.
     * @throws ClassCastException     Eccezione usata per controllare il caso in cui
     *                                si dovesse usare
     *                                il cast tra classi in cui non vale il
     *                                principio di sostituibilità.
     */
    public double predict(ObjectOutputStream out, ObjectInputStream in)
            throws IOException, ClassNotFoundException, ClassCastException {

        Example e = data.readExample(out, in);
        out.writeObject("Inserisci il valore k >= 1: ");
        int k = (Integer) (in.readObject());

        return data.avgClosest(e, k);
    }

    /**
     * Salva l'oggetto di classe KNN in un file binario {@literal <nomeFile>}.dat
     * 
     * @param nomeFile Nome del file in cui salvare il la classe (comprende
     *                 l'estensione).
     * @throws IOException Eccezione per il controllo dei flussi di Input/Output.
     */
    public void salva(String nomeFile) throws IOException {
        ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(nomeFile));
        outStream.writeObject(this);
        outStream.close();
    }

    /**
     * Carica da file un oggetto KNN e lo restituisce
     * 
     * @param nomeFile Nome del file da cui caricare la classe (comprende
     *                 l'estensione).
     * @return Oggetto KNN caricato da file.
     * @throws IOException            Eccezione per il controllo dei flussi di
     *                                Input/Output.
     * @throws ClassNotFoundException Eccezione chiamata quando si cerca di caricare
     *                                una classe attraverso il suo nome ma non
     *                                vengono
     *                                trovate definizioni di classe con quel nome.
     */
    public static KNN carica(String nomeFile) throws IOException, ClassNotFoundException {
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
    public String toString() {
        return data.toString();
    }
}
