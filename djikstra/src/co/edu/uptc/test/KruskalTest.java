package co.edu.uptc.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KruskalTest {    
    static class Edge {
        String u, v;
        int w;

        Edge(String u, String v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return u + " - " + v + " : " + w;
        }
    }

    static class UnionFind {
        private Map<String, String> parent = new HashMap<>();
        private Map<String, Integer> rank = new HashMap<>();

        UnionFind(Collection<String> vertices) {
            for (String v : vertices) {
                parent.put(v, v);
                rank.put(v, 0);
            }
        }

        String find(String x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        boolean union(String a, String b) {
            String ra = find(a);
            String rb = find(b);

            if (ra.equals(rb)) return false;

            int rankA = rank.get(ra);
            int rankB = rank.get(rb);

            if (rankA < rankB) {
                parent.put(ra, rb);
            } else if (rankA > rankB) {
                parent.put(rb, ra);
            } else {
                parent.put(rb, ra);
                rank.put(ra, rankA + 1);
            }
            return true;
        }
    }

    public static List<Edge> kruskal(Collection<String> vertices, List<Edge> edges, boolean minimum) {
        List<Edge> sorted = new ArrayList<>(edges);

        if (minimum) {
            sorted.sort(Comparator.comparingInt(e -> e.w));
        } else {
            sorted.sort((e1, e2) -> Integer.compare(e2.w, e1.w));
        }

        UnionFind uf = new UnionFind(vertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : sorted) {
            if (uf.union(e.u, e.v)) {
                mst.add(e);
                if (mst.size() == vertices.size() - 1) break;
            }
        }

        return mst;
    }

    public static void printTree(List<Edge> tree) {
        int total = 0;
        for (Edge e : tree) {
            System.out.println(e);
            total += e.w;
        }
        System.out.println("Peso total = " + total);
    }
}
