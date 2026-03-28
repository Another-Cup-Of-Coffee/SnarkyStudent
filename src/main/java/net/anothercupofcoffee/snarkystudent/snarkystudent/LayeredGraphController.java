package net.anothercupofcoffee.snarkystudent.snarkystudent;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LayeredGraphController {

    @GetMapping(value = "/graph", produces = "image/svg+xml")
    public String getGraph() {

        Map<String, List<String>> prereqs = Map.of(
            "CS 201", List.of("CS 101", "Math 101"),
            "Math 201", List.of("Math 102"),
            "Math 102", List.of("Math 101")
        );

        return LayeredGraphCreator.buildAndRenderGraph(prereqs);
    }

}
