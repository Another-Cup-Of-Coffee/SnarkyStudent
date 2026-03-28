package net.anothercupofcoffee.snarkystudent.snarkystudent;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;

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

        MutableGraph graph = Factory.mutGraph("courses")
                .setDirected(true)
                .graphAttrs()
                .add("rankdir", "LR");

        for (String course : prereqs.keySet()) {
            for (String prereq : prereqs.get(course)) {
                graph.add(Factory.mutNode(prereq).addLink(Factory.mutNode(course)));
            }
        }

        return Graphviz.fromGraph(graph)
                .render(Format.SVG)
                .toString();
    }

}
