package geometric.analysis.api.API;

import com.google.gson.Gson;
import geometric.analysis.api.Entity.Line;
import geometric.analysis.api.Service.RelativePositions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineAPI {

    @Autowired
    RelativePositions relativePositions;

    @RequestMapping("/line")
    public Line line() {
        return new Line();
    }

    @RequestMapping(value = "/line/relativepositions", method = RequestMethod.POST)
    public Line getRelativePositions(@RequestBody Line lineOne) {
        try {
            System.out.println(new Gson().toJson(lineOne));
            return new Line();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
