package knn.knn.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe server che gestisce le richieste del client
 */
@Controller
public class ServerController {

    @Autowired
    @Qualifier("mainServerService")

    private IServerService ss;

    public ServerController() {
        ss = new ServerService();
    }

    /**
     * Restituisce la pagina index.html
     * 
     * @return pagina index.html
     */
    @RequestMapping("/")
    public String index() {
        return ss.index();
    }
    
}

