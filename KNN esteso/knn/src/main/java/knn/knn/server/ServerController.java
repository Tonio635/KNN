package knn.knn.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @RequestMapping(value = "/domenico", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> checkUsername(@RequestBody String str) {
        return new ResponseEntity<String>(str, HttpStatus.OK);
    }
}

