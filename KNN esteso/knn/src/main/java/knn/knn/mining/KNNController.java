package knn.knn.mining;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Qualifier("mainKNNService")
public class KNNController {

    @Autowired
    private IKNNService ks;

    public KNNController() {
        ks = new KNNService();
    }

    @PostMapping(path = "/getModello")
    public ResponseEntity<String> getModello(@RequestBody Map<String, Object> post) throws JsonProcessingException {

        String jsonResult = "";

        try {
            jsonResult = ks.getModello((Long)post.get("id"), (Integer) post.get("formato"), (String) post.get("nome"));
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<String>(mapper.writeValueAsString(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
    }

    @PostMapping(path = "/getPredizione")
    public ResponseEntity<String> getPredizione(@RequestBody Map<String, Object> post) throws JsonProcessingException {

        String jsonResult = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonResult = ks.getPredizione((Long)post.get("id"), (String)post.get("example"), (Integer) post.get("k"));
        } catch (Exception e) {
            return new ResponseEntity<String>(mapper.writeValueAsString(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
    }

}
