package data;
/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 */
class ContinuousAttribute extends Attribute{
    
    /**
     * Costruttore della classe
     * Invoca il costruttore della super-classe
     * 
     * @param name indica il nome simbolico dell'attributo con cui avvalorare il valore del membro della super-classe name
     * @param index indica l'identificativo numerico dell'attributo con cui avvalorare il valore del membro della super-classe index
     */
    ContinuousAttribute(String name, int index){
        super(name, index);
    }
}
