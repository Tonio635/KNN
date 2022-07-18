package knn.knn.server;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
/**
 * Classe che serve per mettersi in contatto con i vari Controller.
 */
@Service("mainServerService")
public class ServerService implements IServerService{

    /**
     * Metodo che fornisce l'implementazione del metodo index presente nell'interfaccia
     * IServerService visualizzando la pagina index.html.
     * 
     * @param model modello da passare
     * @return "index", stringa che indica la pagina da restituire.
     */
    public String index(Model model) {

        model.addAttribute("id", Server.getNewID());
        
        return "index";
    }
    
}
