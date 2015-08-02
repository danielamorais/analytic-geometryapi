package geometric.analysis.api.API;

import java.util.concurrent.atomic.AtomicLong;

import geometric.analysis.api.Entity.Line;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineAPI {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/line")
    public Line line() {
        return new Line();
    }
}
