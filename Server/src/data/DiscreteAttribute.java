package data;

/**
 * Estende la classe Attribute e rappresenta un attributo discreto.
 */
final class DiscreteAttribute extends Attribute {

    /**
     * Costruttore della classe.
     * Invoca il costruttore della super-classe.
     * 
     * @param name  Indica il nome simbolico dell'attributo con cui avvalorare il
     *              valore del membro della super-classe name.
     * @param index Indica l'identificativo numerico dell'attributo con cui
     *              avvalorare il valore del membro della super-classe index.
     */
    DiscreteAttribute(String name, int index) {
        super(name, index);
    }
}
