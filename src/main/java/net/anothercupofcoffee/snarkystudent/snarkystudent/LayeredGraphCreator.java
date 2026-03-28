package net.anothercupofcoffee.snarkystudent.snarkystudent;

import java.util.List;
import java.util.Map;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;

public class LayeredGraphCreator {

    static Graph buildGraph(Map<String, List<String>> prereqs) {
        MutableGraph graph = Factory.mutGraph("courses")
                .setDirected(true)
                .graphAttrs()
                .add("rankdir", "LR");
        
        for (String course : prereqs.keySet()) {
            for (String prereq : prereqs.get(course)) {
                graph.add(
                        Factory.mutNode(prereq).addLink(Factory.mutNode(course))
                );
            }
        }

        return graph.toImmutable();
    }

    static String renderGraph(Graph graph) {
        return Graphviz.fromGraph(graph)
                .render(Format.SVG)
                .toString();
    }

    public static String buildAndRenderGraph(Map<String, List<String>> prereqs) {
        return renderGraph(buildGraph(prereqs));
    }

}
