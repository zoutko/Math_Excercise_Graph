package co.edu.uptc.test;

import co.edu.uptc.model.GraphModel;
import co.edu.uptc.structures.Graph;
import co.edu.uptc.structures.Road;
import co.edu.uptc.structures.SimpleList;
import co.edu.uptc.structures.MstEdge;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class TestGraph {

    private static class PathResult {
        List<String> path = new ArrayList<>();
        int weight = Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Graph<String> g = GraphModel.createGraph();

        while (true) {
            System.out.println("\n=== MENU DE GRAFOS ===");
            System.out.println("1. Dijkstra");
            System.out.println("2. Kruskal minimo");
            System.out.println("3. Kruskal maximo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            int option = Integer.parseInt(console.nextLine());

            switch (option) {
                case 1: {
                    System.out.print("Ingrese el nodo de inicio (ej A): ");
                    String start = console.nextLine();
                    System.out.print("Ingrese el nodo de destino (ej G): ");
                    String end = console.nextLine();

                    System.out.println("\nRuta mas corta (Dijkstra) de " + start + " a " + end + ":");
                    System.out.println(g.dijkstra(start, end));
                    break;
                }
                case 2: {
                    System.out.println("\nÁrbol de expansión mínima (Kruskal):");
                    printKruskalTree(g.kruskalMin());
                    break;
                }
                case 3: {
                    System.out.println("\nÁrbol de expansión máxima (Kruskal):");
                    printKruskalTree(g.kruskalMax());
                    break;
                }
                case 4:
                    System.out.println("Saliendo...");
                    console.close();
                    return;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void printKruskalTree(SimpleList<MstEdge<String>> tree) {
        int total = 0;
        for (MstEdge<String> edge : tree) {
            System.out.println(edge.getFrom() + " - " + edge.getTo() + " (" + edge.getWeight() + ")");
            total += edge.getWeight();
        }
        System.out.println("Peso total: " + total);
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
