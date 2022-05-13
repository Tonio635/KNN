package data;

import javax.lang.model.util.ElementScanner14;

/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 */
class ContinuousAttribute extends Attribute{
    private double min;
    private double max;
    /**
     * Costruttore della classe
     * Invoca il costruttore della super-classe
     * 
     * @param name indica il nome simbolico dell'attributo con cui avvalorare il valore del membro della super-classe name
     * @param index indica l'identificativo numerico dell'attributo con cui avvalorare il valore del membro della super-classe index
     */
    ContinuousAttribute(String name, int index){
        super(name, index);
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
    }

    void setMin (Double v){
        if (v<min)
            min=v;
    }

    void setMax (Double v){
        if (v>max)
            max=v;
    }

    double scale (Double value){
        return (value-min)/max;
    }


}
