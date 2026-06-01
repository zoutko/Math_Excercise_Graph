package co.edu.uptc.model;

import co.edu.uptc.structures.Graph;

public class GraphModel {

    public static Graph<String> createGraph() {
        Graph<String> g = new Graph<>();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("E");
        g.addVertex("F");
        g.addVertex("G");
        g.addVertex("H");

        g.addArc("A", "C", 5);
        g.addArc("A", "B", 3);
        g.addArc("A", "H", 10);
        g.addArc("A", "D", 2);

        g.addArc("B", "C", 5);
        g.addArc("B", "D", 8);
        g.addArc("B", "G", 6);
        g.addArc("B", "E", 4);
        g.addArc("B", "H", 6);

        g.addArc("C", "F", 7);
        g.addArc("C", "G", 9);
        g.addArc("C", "E", 1);

        g.addArc("D", "H", 14);
        g.addArc("D", "E", 8);

        g.addArc("E", "G", 15);

        return g;
    }

}
