package data;

import utility.Keyboard;
/**
 * Modella i valori degli attributi indipendenti di un esempio
 */
public class Example {
    // Array di Object che contiene un valore per ciascun attributo indipendente
    private Object[] example;       

    /**
     * Costruttore della classe
     * Costruisce l’array example come array di dimensione size
     * 
     * @param size indica la dimensione dell'array
     */
    public Example(int size){       
        example = new Object[size];
    }

    /**
     * Memorizza un nuovo valore nella posizione index di example
     * 
     * @param o oggetto da inserire all'interno dell'array
     * @param index indice in cui inserire l'oggetto
     */
    public void set(Object o, int index){
        example[index] = o;
    }

    /**
     * Restituisce il valore memorizzato nella posizione index di example
     * 
     * @param index indice su cui prelevare il valore
     * @return valore in indice index
     */
    public Object get(int index){
        return example[index];
    }

    /**
     * Scambia i valori contenuti nel campo example dell’oggetto corrente con i valori contenuti 
     * nel campo example del parametro e
     * 
     * @param e esempio su cui scambiare i valori
     */
    void swap(Example e){
        if (example.length != e.example.length) 
            throw new ExampleSizeException("I due esempi non hanno lo stesso numero di variabili indipendenti!");

        Object temp;
        for(int i = 0; i < example.length; i++){
            temp = example[i];
            example[i] = e.example[i];
            e.example[i] = temp;
        }
    }

    /**
     * Calcola e restituisce la distanza di Hamming calcolata tra l’istanza di Example 
     * passata come parametro e quella corrente
     * 
     * @param e esempio su cui calcolare la distanza di Hamming
     * @return distanza di hamming
     */
    double distance(Example e){
        if (example.length != e.example.length) 
            throw new ExampleSizeException("I due esempi non hanno lo stesso numero di variabili indipendenti!");

        double distanza = 0;

        for(int i = 0; i < example.length; i++){
            if(example[i] instanceof String){
                if (!example[i].equals(e.example[i])) {
                    distanza++;
                }
            }
        }

        return distanza;
    }

    /**
     * Restituisce i valori in example 
     * 
     * @return risultato
     */
    public String toString(){
        String risultato = "";

        for (int i = 0; i < example.length; i++){
            risultato += example[i] + " ";  
        }

        risultato += "\n";

        return risultato;
    }
}
