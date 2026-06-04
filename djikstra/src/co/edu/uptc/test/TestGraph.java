package co.edu.uptc.test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import co.edu.uptc.model.GraphModel;
import co.edu.uptc.structures.Graph;
import co.edu.uptc.structures.Road;
import co.edu.uptc.structures.SimpleList;

public class TestGraph {

    private static class PathResult {
        List<String> path = new ArrayList<>();
        int weight = Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Ingrese el nodo de inicio (ej A): ");
        String start = console.nextLine();
        System.out.println("Ingrese el nodo de destino (ej G): ");
        String end = console.nextLine();
        if (args.length >= 2) {
            start = args[0];
            end = args[1];
        }

        Graph<String> g = GraphModel.createGraph();

        SimpleList<String> recorrido = g.showValues(start);
        System.out.print("Camino recorrido (DFS) desde " + start + ": ");
        printSimpleList(recorrido);

        System.out.println("\nRuta más corta (Dijkstra) de " + start + " a " + end + ":");
        System.out.println(g.dijkstra(start, end));

        PathResult best = new PathResult();
        List<String> current = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        visited.add(start);
        current.add(start);
        dfsLongest(g, start, end, visited, current, 0, best);

        if (best.weight == Integer.MIN_VALUE) {
            System.out.println("\nNo existe camino simple de " + start + " a " + end + ".");
        } else {
            System.out.println("\nRuta más larga simple de " + start + " a " + end + ":");
            System.out.println("Camino: " + String.join(" -> ", best.path));
            System.out.println("Costo Total: " + best.weight);
        }
    }

    private static void dfsLongest(Graph<String> g, String currentNode, String target, Set<String> visited,
            List<String> currentPath, int accumulatedWeight, PathResult best) {
        if (currentNode.equals(target)) {
            if (accumulatedWeight > best.weight) {
                best.weight = accumulatedWeight;
                best.path = new ArrayList<>(currentPath);
            }
            return;
        }

        SimpleList<Road<String>> adj = g.getAdjacentsOf(currentNode);
        for (Road<String> road : adj) {
            String neigh = road.getAdjacent();
            if (!visited.contains(neigh)) {
                visited.add(neigh);
                currentPath.add(neigh);
                dfsLongest(g, neigh, target, visited, currentPath, accumulatedWeight + road.getWeight(), best);
                currentPath.remove(currentPath.size() - 1);
                visited.remove(neigh);
            }
        }
    }

    private static void printSimpleList(SimpleList<String> list) {
        List<String> vals = new ArrayList<>();
        for (String s : list) {
            vals.add(s);
        }
        System.out.println(String.join(" -> ", vals));
    }
}
