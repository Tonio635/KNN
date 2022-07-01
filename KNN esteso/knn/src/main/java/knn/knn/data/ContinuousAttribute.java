package knn.knn.data;

/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 */
final class ContinuousAttribute extends Attribute {
    private double min;
    private double max;

    /**
     * Costruttore della classe
     * Invoca il costruttore della super-classe
     * 
     * @param name  indica il nome simbolico dell'attributo con cui avvalorare il
     *              valore del membro della super-classe name
     * @param index indica l'identificativo numerico dell'attributo con cui
     *              avvalorare il valore del membro della super-classe index
     */
    ContinuousAttribute(String name, int index) {
        super(name, index);
        min = Double.MAX_VALUE;                    // indica il valore minimo dell'attributo
        max = Double.MIN_VALUE;                    // indica il valore massimo dell'attributo
    }

    /**
     * Imposta il minimo dell'attributo
     * 
     * @param v valore minimo da impostare
     */
    void setMin(Double v) {
        if (v < min)
            min = v;
    }

    /**
     * Imposta il massimo dell'attributo
     * 
     * @param v valore massimo da impostare
     */
    void setMax(Double v) {
        if (v > max)
            max = v;
    }

    /**
     * Scala il valore passato in input
     * 
     * @param value da scalare
     * @return valore scalato
     */
    double scale(Double value) {
        return (value - min) / (max - min);
    }

    /**
     * Restituisce i valori dell'attributo
     * 
     * @return stringa contentente i valori dell'attributo
     */
    public String toString() {
        return super.toString() + "Max: " + max + " - Min: " + min + "\n";
    }
}
