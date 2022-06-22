package example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Modella i valori degli attributi indipendenti di un esempio
 */
public class Example implements Serializable, Cloneable {
    // Array di Object che contiene un valore per ciascun attributo indipendente
    private ArrayList<Object> example;

    /**
     * Costruttore della classe
     * Costruisce l’array example come array di dimensione size
     * 
     * @param size indica la dimensione dell'array
     */
    public Example(int size) {
        example = new ArrayList<Object>(size);
    }

    /**
     * Memorizza un nuovo valore nella posizione index di example
     * 
     * @param o     oggetto da inserire all'interno dell'array
     * @param index indice in cui inserire l'oggetto
     */
    public void set(Object o, int index) {
        while (index >= example.size())
            example.add(null);

        example.set(index, o);
    }

    /**
     * Restituisce il valore memorizzato nella posizione index di example
     * 
     * @param index indice su cui prelevare il valore
     * @return valore in indice index
     */
    public Object get(int index) {
        return example.get(index);
    }

    /**
     * Calcola e restituisce la distanza calcolata tra l’istanza di Example
     * passata come parametro e quella corrente
     * 
     * @param e esempio su cui calcolare la distanza
     * @return distanza tra i due esempi
     */
    public double distance(Example e) {
        if (example.size() != e.example.size())
            throw new ExampleSizeException("I due esempi non hanno lo stesso numero di variabili indipendenti!");

        double distanza = 0;

        ListIterator<Object> it = example.listIterator();
        ListIterator<Object> it2 = e.example.listIterator();

        while (it.hasNext() && it2.hasNext()) {
            Object el1 = it.next();
            Object el2 = it2.next();

            if ((el1 instanceof String) && (el2 instanceof String)) {
                if (!el1.equals(el2))
                    distanza++;
            } else if ((el1 instanceof Double) && (el2 instanceof Double))
                distanza += Math.abs(((Double) el1).doubleValue() - ((Double) el2).doubleValue());
        }

        return distanza;
    }

    /**
     * Clona l'oggetto di tipo example
     * 
     * @return example clonato
     */
    public Object clone() {
        try {
            Example e = (Example) super.clone();
            e.example = new ArrayList<Object>(this.example);

            return e;

        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();

            return null;
        }
    }

    /**
     * Restituisce i valori in example
     * 
     * @return risultato
     */
    public String toString() {
        String risultato = "";

        for (Object o : example) {
            risultato += o + " ";
        }

        risultato += "\n";

        return risultato;
    }

}
