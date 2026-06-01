package co.edu.uptc.structures;

public class Graph<T> {
    private SimpleList<Vertex<T>> vertices;

    public Graph() {
        vertices = new SimpleList<>();
    }

    public void addVertex(T data) {
        boolean exists = false;
        for (Vertex<T> vertex : vertices) {
            if (vertex.getValue().equals(data)) {
                exists = true;
            }
        }
        if (!exists) {
            vertices.add(new Vertex<>(data));
        }
    }

    public void addArc(T data1, T data2) {
        Vertex<T> v1 = searchVertex(data1);
        Vertex<T> v2 = searchVertex(data2);
        if (v1 != null && v2 != null) {
            v1.addAdjacent(data2);
            v2.addAdjacent(data1);
        }
    }

    public void addArc(T data1, T data2, int weight) {
        Vertex<T> v1 = searchVertex(data1);
        Vertex<T> v2 = searchVertex(data2);
        if (v1 != null && v2 != null) {
            v1.addAdjacent(data2, weight);
            v2.addAdjacent(data1, weight);
        }
    }

    public void removeVertex(T data) {
        Vertex<T> toRemove = searchVertex(data);
        boolean found = toRemove != null;
        if (found) {
            vertices.remove(toRemove);
            for (Vertex<T> vertex : vertices) {
                vertex.removeVertex(data);
            }
        }
    }

    public void removeArc(T data1, T data2) {
        Vertex<T> v1 = searchVertex(data1);
        boolean exists = v1 != null;
        if (exists) {
            v1.removeVertex(data2);
        }
    }

    public boolean adjacent(T data1, T data2) {
        Vertex<T> v1 = searchVertex(data1);
        boolean connected = false;
        if (v1 != null) {
            connected = v1.existsArc(data2);
        }
        return connected;
    }

    private Vertex<T> searchVertex(T data) {
        Vertex<T> found = null;
        for (Vertex<T> vertex : vertices) {
            if (found == null && vertex.getValue().equals(data)) {
                found = vertex;
            }
        }
        return found;
    }

    public SimpleList<T> showValues(T start) {
        SimpleList<T> visitedList = new SimpleList<>();
        MyStack<Vertex<T>> stack = new MyStack<>();

        Vertex<T> startVertex = searchVertex(start);
        if (startVertex == null) {
            return visitedList;
        }

        SimpleList<T> marked = new SimpleList<>();
        marked.add(startVertex.getValue());
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Vertex<T> current = stack.pop();
            visitedList.add(current.getValue());

            for (int i = current.getAdjacents().size() - 1; i >= 0; i--) {
                T adjValue = current.getAdjacents().get(i).getAdjacent();
                if (!marked.contains(adjValue)) {
                    Vertex<T> adjVertex = searchVertex(adjValue);
                    if (adjVertex != null) {
                        stack.push(adjVertex);
                        marked.add(adjValue);
                    }
                }
            }
        }

        return visitedList;
    }

    public String dijkstra(T initial, T destination) {
        SimpleList<Vertex<T>> visited = new SimpleList<>();
        SimpleList<Vertex<T>> allVertices = getAllVertices();
        SimpleList<Integer> distances = initializeDistances(allVertices, initial);
        SimpleList<Vertex<T>> predecessors = initializePredecessors(allVertices);

        while (visited.size() < allVertices.size()) {
            Vertex<T> current = getMinimumDistanceVertex(allVertices, distances, visited);
            if (current != null) {
                visited.add(current);
                updateDistances(current, allVertices, distances, predecessors);
            }
        }

        return buildPathString(initial, destination, predecessors, distances, allVertices);
    }

    private SimpleList<Vertex<T>> getAllVertices() {
        SimpleList<Vertex<T>> all = new SimpleList<>();
        for (Vertex<T> v : vertices) {
            all.add(v);
        }
        return all;
    }

    private SimpleList<Integer> initializeDistances(SimpleList<Vertex<T>> vertexList, T initial) {
        SimpleList<Integer> distances = new SimpleList<>();
        for (Vertex<T> vertex : vertexList) {
            if (vertex.getValue().equals(initial)) {
                distances.add(0);
            } else {
                distances.add(Integer.MAX_VALUE);
            }
        }
        return distances;
    }

    private SimpleList<Vertex<T>> initializePredecessors(SimpleList<Vertex<T>> vertexList) {
        SimpleList<Vertex<T>> predecessors = new SimpleList<>();
        for (int i = 0; i < vertexList.size(); i++) {
            predecessors.add(null);
        }
        return predecessors;
    }

    private Vertex<T> getMinimumDistanceVertex(SimpleList<Vertex<T>> vertexList, SimpleList<Integer> distances,
            SimpleList<Vertex<T>> visited) {
        int minDistance = Integer.MAX_VALUE;
        Vertex<T> minVertex = null;
        for (int i = 0; i < vertexList.size(); i++) {
            Vertex<T> vertex = vertexList.get(i);
            if (!visited.contains(vertex) && distances.get(i) < minDistance) {
                minDistance = distances.get(i);
                minVertex = vertex;
            }
        }
        return minVertex;
    }

    private void updateDistances(Vertex<T> current, SimpleList<Vertex<T>> vertexList, SimpleList<Integer> distances,
            SimpleList<Vertex<T>> predecessors) {
        int currentIndex = getVertexIndex(vertexList, current);
        int currentDistance = distances.get(currentIndex);

        for (Road<T> road : current.getAdjacents()) {
            Vertex<T> neighbor = searchVertex(road.getAdjacent());
            int neighborIndex = getVertexIndex(vertexList, neighbor);
            int newDistance = currentDistance + road.getWeight();

            if (newDistance < distances.get(neighborIndex)) {
                distances.set(neighborIndex, newDistance);
                predecessors.set(neighborIndex, current);
            }
        }
    }

    private int getVertexIndex(SimpleList<Vertex<T>> list, Vertex<T> vertex) {
        int index = -1;
        int i = 0;
        while (i < list.size() && index == -1) {
            if (list.get(i).getValue().equals(vertex.getValue())) {
                index = i;
            }
            i++;
        }
        return index;
    }

    private String buildPathString(T initial, T destination, SimpleList<Vertex<T>> predecessors,
            SimpleList<Integer> distances, SimpleList<Vertex<T>> vertexList) {
        MyStack<T> path = new MyStack<>();
        Vertex<T> current = searchVertex(destination);
        int currentIndex = getVertexIndex(vertexList, current);

        while (current != null && !current.getValue().equals(initial)) {
            path.push(current.getValue());
            current = predecessors.get(currentIndex);
            currentIndex = getVertexIndex(vertexList, current);
        }

        if (current != null) {
            path.push(initial);
        }

        StringBuilder result = new StringBuilder("Camino: ");
        while (!path.isEmpty()) {
            result.append(path.pop());
            if (!path.isEmpty()) {
                result.append(" -> ");
            }
        }

        int finalIndex = getVertexIndex(vertexList, searchVertex(destination));
        result.append("\nCosto Total: ").append(distances.get(finalIndex));

        return result.toString();
    }

    // Nuevo método público para obtener la lista de adyacencias de un vértice
    public SimpleList<Road<T>> getAdjacentsOf(T data) {
        Vertex<T> v = searchVertex(data);
        if (v != null) {
            return v.getAdjacents();
        }
        return new SimpleList<>();
    }

}
