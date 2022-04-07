public abstract class Attribute {
    private String name;    //Nome simbolico dell'attributo
    private int index;      //Identificativo numerico dell'attributo

    public Attribute(String name, int index){       //È il costruttore di classe. Inizializza i valori dei membri name, index
        this.name = name;
        this.index = index;
    }

    String getName(){       //Restituisce il valore nel membro name;
        return name;
    }

    int getIndex(){         //Restituisce il valore nel membro index;
        return index;
    }
}
