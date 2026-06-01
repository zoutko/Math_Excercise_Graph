package co.edu.uptc.structures;

public class Road<T> {
    private T adjacent;
    private int weight;

    public Road(T adjacentValue, int weight){
        this.adjacent = adjacentValue;
        this.weight = weight;
    }

    public Road(T adjacentValue){
        this.adjacent = adjacentValue;
        this.weight = 1;
    }
    public T getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(T adjacent) {
        this.adjacent = adjacent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    

}
