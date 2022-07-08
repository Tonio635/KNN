package knn.knn.server;

import org.springframework.stereotype.Service;

@Service("mainServerService")
public class ServerService implements IServerService{
    
    public String index() {
        return "index";
    }
    
}
