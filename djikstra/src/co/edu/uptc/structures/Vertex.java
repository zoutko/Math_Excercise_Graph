package co.edu.uptc.structures;

public class Vertex<T> {
    private T value;
    private SimpleList<Road<T>> list;

    public Vertex(T value) {
        this.value = value;
        list = new SimpleList<>();
    }

    public T getValue() {
        return value;
    }

    public void addAdjacent(T destination) {
        Road<T> newRoad = new Road<>(destination);
        boolean exists = false;
        for (Road<T> road : list) {
            if (road.getAdjacent().equals(destination)) {
                exists = true;
            }
        }
        if (!exists) {
            list.add(newRoad);
        }
    }

    public void addAdjacent(T destination, int weight) {
        Road<T> newRoad = new Road<>(destination, weight);
        boolean exists = false;
        for (Road<T> road : list) {
            if (road.getAdjacent().equals(destination)) {
                exists = true;
            }
        }
        if (!exists) {
            list.add(newRoad);
        }
    }

    public void removeVertex(T destination) {
        int index = -1;
        int currentIndex = 0;
        for (Road<T> road : list) {
            if (road.getAdjacent().equals(destination)) {
                index = currentIndex;
            }
            currentIndex++;
        }
        if (index != -1) {
            list.remove(index);
        }
    }

    public boolean existsArc(T destination) {
        boolean found = false;
        for (Road<T> road : list) {
            if (road.getAdjacent().equals(destination)) {
                found = true;
            }
        }
        return found;
    }

    public SimpleList<Road<T>> getAdjacents() {
        return list;
    }

    @Override
    public String toString() {
        return "Vertex [value=" + value + "]";
    }
}
