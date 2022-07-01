package knn.knn.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServerController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
