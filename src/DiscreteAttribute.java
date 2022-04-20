/**
 * Estende la classe Attribute e rappresenta un attributo discreto
 */
public class DiscreteAttribute extends Attribute{

    /**
     * Costruttore della classe
     * Invoca il costruttore della super-classe
     * 
     * @param name indica il nome simbolico dell'attributo con cui avvalorare il valore del membro della super-classe name
     * @param index indica l'identificativo numerico dell'attributo con cui avvalorare il valore del membro della super-classe index
     */
    public DiscreteAttribute(String name, int index){
        super(name, index);
    }
}
