package knn.knn.mining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@Qualifier("mainKNNService")
public class KNNController {
    
    @Autowired
    private IKNNService ks;

    public KNNController(){
        ks = new KNNService();
    }

    @RequestMapping(value = "/domenico", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<String> gestisciFile(Integer formato,String nome){
        String jsonResult;
        try{
            jsonResult = ks.getModello(formato, nome);
        }catch(Exception e){
            return new ResponseEntity<String>("404",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
    }
}
