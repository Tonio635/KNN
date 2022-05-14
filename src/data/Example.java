package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Modella i valori degli attributi indipendenti di un esempio
 */
public class Example implements Serializable, Cloneable{
    // Array di Object che contiene un valore per ciascun attributo indipendente
    private ArrayList<Object> example;       

    /**
     * Costruttore della classe
     * Costruisce l’array example come array di dimensione size
     * 
     * @param size indica la dimensione dell'array
     */
    public Example(int size){       
        example = new ArrayList<Object>(size);
    }

    /**
     * Memorizza un nuovo valore nella posizione index di example
     * 
     * @param o oggetto da inserire all'interno dell'array
     * @param index indice in cui inserire l'oggetto
     */
    public void set(Object o, int index){
        while(index >= example.size())
            example.add(null);

        example.set(index, o);
    }

    /**
     * Restituisce il valore memorizzato nella posizione index di example
     * 
     * @param index indice su cui prelevare il valore
     * @return valore in indice index
     */
    public Object get(int index){
        return example.get(index);
    }

    /**
     * Scambia i valori contenuti nel campo example dell’oggetto corrente con i valori contenuti 
     * nel campo example del parametro e
     * 
     * @param e esempio su cui scambiare i valori
     */
    void swap(Example e){
        if (example.size() != e.example.size()) 
            throw new ExampleSizeException("I due esempi non hanno lo stesso numero di variabili indipendenti!");


        ListIterator<Object> it = example.listIterator();
        ListIterator<Object> it2 = e.example.listIterator();
 
        while (it.hasNext() && it2.hasNext())
        {
            Object el1 = it.next();
            Object el2 = it2.next();

            it.set(el2);
            it2.set(el1);
        }
    }

    /**
     * Calcola e restituisce la distanza calcolata tra l’istanza di Example 
     * passata come parametro e quella corrente
     * 
     * @param e esempio su cui calcolare la distanza
     * @return distanza tra i due esempi
     */
    double distance(Example e){
        if (example.size() != e.example.size()) 
            throw new ExampleSizeException("I due esempi non hanno lo stesso numero di variabili indipendenti!");

        double distanza = 0;

        ListIterator<Object> it = example.listIterator();
        ListIterator<Object> it2 = e.example.listIterator();
 
        while (it.hasNext() && it2.hasNext()){
            Object el1 = it.next();
            Object el2 = it2.next();

            if((el1 instanceof String) && (el2 instanceof String)){
                if (!el1.equals(el2)) 
                    distanza++;
            } else if ((el1 instanceof Double) && (el2 instanceof Double))
                distanza += Math.abs(((Double) el1).doubleValue() - ((Double) el2).doubleValue());
        }

        return distanza;
    }

    public Example clone(){
        int size = example.size();
        Example e = new Example(size);

        for(int i = 0; i < size; i++){
            e.set(example.get(i), i);
        }

        return e;
    }

    /**
     * Restituisce i valori in example 
     * 
     * @return risultato
     */
    public String toString(){
        String risultato = "";

        for (Object o : example) {
            risultato += o + " ";  
        }

        risultato += "\n";

        return risultato;
    }
}
