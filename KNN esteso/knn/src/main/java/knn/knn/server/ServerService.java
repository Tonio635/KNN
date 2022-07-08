package knn.knn.server;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service("mainServerService")
public class ServerService implements IServerService{
    
    public String index(Model model) {

        model.addAttribute("id", Server.getNewID());
        
        return "index";
    }
    
}
