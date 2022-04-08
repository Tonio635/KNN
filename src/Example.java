/**
 * Modella i valori degli attributi indipendenti di un esempio
 */
public class Example {
    // Array di Object che continue un valore per ciascun attributo indipendente
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
    public void swap(Example e){
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
    public double distance(Example e){
        double distanza = 0;

        for(int i = 0; i < example.length; i++){
            if(example[i] instanceof String){
                if (example[i] != e.example[i]) {
                    distanza++;
                }
            }
        }

        return distanza;
    }

}
