package knn.knn.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe server che gestisce le richieste del client
 */
@Controller
public class ServerController {

    /**
     * Restituisce la pagina index.html
     * 
     * @return pagina index.html
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
