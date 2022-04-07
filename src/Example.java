public class Example {
    private Object[] example;       //Array di Object che continue un valore per ciascun attributo indipendente

    public Example(int size){       //Costruisce l’array example come array di dimensione size
        example = new Object[size];
    }

    public void set(Object o, int index){       //Memorizza o nella posizione index di example
        example[index] = o;
    }

    public Object get(int index){       //Restituisce in valore memorizzato nella posizione index di example
        return example[index];
    }

    //Da testare
    public void swap(Example e){        //Scambia i valori contenuti nel campo example dell’oggetto corrente con i valori contenuti nel campo example del parametro e
        Example scambia = new Example(example.length);

        for(int i = 0; i < example.length; i++){
            scambia.example[i] = example[i];
            example[i] = e.example[i];
            e.example[i] = scambia.example[i];
        }
    }

    //Da testare
    public double distance(Example e){      //Calcola e restituisce la distanza di Hamming calcolata tra l’istanza di Example passata come parametro e quella corrente
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
