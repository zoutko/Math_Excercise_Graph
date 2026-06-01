package co.edu.uptc.structures;

public class MyStack <T> {
    private SimpleList<T> list;
    

    public MyStack(){
        list = new SimpleList<>();
    }

    public void push(T data){
        list.add(data);
    }

    public T pop(){
        T data = list.get(list.size()-1);
        list.remove(list.size()-1);
        return data;

    }

    public T peek(){
        return list.get(list.size()-1);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

}
