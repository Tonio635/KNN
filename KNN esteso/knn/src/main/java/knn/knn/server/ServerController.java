package knn.knn.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe server che gestisce le richieste del client
 */
@Controller
public class ServerController {

    @Autowired
    @Qualifier("mainServerService")

    private IServerService ss;
    
    /**
     * Costruttore della classe
     */
    public ServerController() {
        ss = new ServerService();
    }

    /**
     * Restituisce la pagina index.html
     * 
     * @return pagina index.html
     */
    @RequestMapping("/")
    public String index(Model model) {
        return ss.index(model);
    }
    
}

