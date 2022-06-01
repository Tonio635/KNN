package data;

import java.io.Serializable;

/**
 * Classe astratta che rappresenta un attributo generico
 */
abstract class Attribute implements Serializable {
    // Nome simbolico dell'attributo
    private String name;
    // Identificativo numerico dell'attributo
    private int index;

    /**
     * Costruttore della classe
     * 
     * @param name  nome simbolico dell'attributo
     * @param index identificativo numerico dell'attributo'
     */
    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'attributo
     * 
     * @return nome dell'attributo
     */
    String getName() {
        return name;
    }

    /**
     * Restituisce l'identificativo numerico dell'attributo
     * 
     * @return identificativo numerico dell'attributo
     */
    int getIndex() {
        return index;
    }

    /**
     * Restituisce i valori dell'attributo
     * 
     * @return stringa contentente i valori dell'attributo
     */
    public String toString() {
        return "Nome: " + name + " - N° attributo: " + index + "\n";
    }
}
