package knn.knn.server;

import org.springframework.ui.Model;
/**
 * Interfaccia che contiene i metodi che verranno utilizzati dai vari Service del Server.
 */
public interface IServerService {

    /**
     * Metodo che serve per restituire la pagina index.html
     * 
     * @param model modello da visualizzare
     * @return index
     */
    public String index(Model model);

}
