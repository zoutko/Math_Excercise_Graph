package co.edu.uptc.structures;

public class MstEdge<T> {
    private T from;
    private T to;
    private int weight;

    public MstEdge(T from, T to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}