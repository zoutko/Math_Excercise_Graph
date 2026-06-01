package co.edu.uptc.structures;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SimpleList<T> implements List<T> {
    private Node<T> head;

    public SimpleList() {
        head = null;
    }

    @Override
    public int size() {
        int counter = 0;
        Node<T> aux = head;
        while (aux != null) {
            counter++;
            aux = aux.getNext();
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean add(T e) {
        boolean added = false;
        if (head == null) {
            head = new Node<>(e);
            added = true;
        } else {
            Node<T> aux = head;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(new Node<>(e));
        }
        return added;
    }

    @Override
    public T remove(int index) {
        T removedData;
        if (index < 0 || index >= size()) {
            removedData = null;
        }

        if (index == 0) {
            removedData = head.getData();
            head = head.getNext();
        } else {
            Node<T> prev = getNode(index - 1);
            Node<T> toRemove = prev.getNext();
            removedData = toRemove.getData();
            prev.setNext(toRemove.getNext());
        }

        return removedData;
    }

    @Override
    public boolean contains(Object o) {
        boolean isContained = false;
        Node<T> aux = head;
        while (aux != null && !isContained) {
            isContained = aux.getData().equals(o);
            aux = aux.getNext();
        }
        return isContained;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            Node<T> aux = head;

            @Override
            public boolean hasNext() {
                return aux != null;
            }

            @Override
            public T next() {
                T data = aux.getData();
                aux = aux.getNext();
                return data;
            }
        };
        return iterator;
    }

    @Override
    public Object[] toArray() {
        int size = size();
        Object[] array = new Object[size];
        Node<T> aux = head;
        int i = 0;
        while (aux != null) {
            array[i++] = aux.getData();
            aux = aux.getNext();
        }
        return array;
    }

    @SuppressWarnings("hiding")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            a = Arrays.copyOf(a, size());
        }

        @SuppressWarnings("unchecked")
        Node<T> aux = (Node<T>) head;
        for (int i = 0; i < size(); i++) {
            a[i] = aux.getData();
            aux = aux.getNext();
        }

        if (a.length > size()) {
            for (int i = size(); i < a.length; i++) {
                a[i] = null;
            }
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        boolean isRemoved = false;
        if (head == null) {
            isRemoved = false;
        } else {
            if (head.getData().equals(o)) {
                head = head.getNext();
                isRemoved = true;
            }
            Node<T> aux = head;
            while (aux.getNext() != null) {
                if (aux.getNext().getData().equals(o)) {
                    aux.setNext(aux.getNext().getNext());
                    isRemoved = true;
                }
                aux = aux.getNext();
            }
        }

        return isRemoved;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean added = false;
        if (index > size() || index < 0) {
            added = false;
        } else {
            Iterator<? extends T> iterator = c.iterator();
            Node<T> newHead = new Node<>(iterator.next());
            Node<T> aux = newHead;
            while (iterator.hasNext()) {
                aux.setNext(new Node<>(iterator.next()));
                aux = aux.getNext();
            }

            if (index == 0) {
                aux.setNext(head);
                head = newHead;
                added = true;
            } else {
                Node<T> prev = getNode(index - 1);
                aux.setNext(prev.getNext());
                prev.setNext(newHead);
            }
        }
        return added;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;
        Node<T> aux = head;
        Node<T> prevNode = null;

        while (aux != null) {
            if (c.contains(aux.getData())) {
                if (prevNode == null) {
                    head = aux.getNext();
                } else {
                    prevNode.setNext(aux.getNext());
                }
                isModified = true;
            } else {
                prevNode = aux;
            }
            aux = aux.getNext();
        }

        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isModified = false;
        Node<T> aux = head;
        Node<T> prevNode = null;

        while (aux != null) {
            if (!c.contains(aux.getData())) {
                if (prevNode == null) {
                    head = aux.getNext();
                } else {
                    prevNode.setNext(aux.getNext());
                }
                isModified = true;
            } else {
                prevNode = aux;
            }
            aux = aux.getNext();
        }

        return isModified;
    }

    @Override
    public void clear() {
        head = null;
    }

    public Node<T> getNode(int index) {
        if (index < 0)
            return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null)
                return null;
            current = current.getNext();
        }
        return current;
    }

    @Override
    public T get(int index) {
        if (index < 0)
            return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            if (current == null)
                return null;
            current = current.getNext();
        }
        return (T) current.getData();
    }

    @Override
    public T set(int index, T element) {
        Node<T> current = getNode(index);
        current.setData(element);
        return (T) current.getData();
    }

    @Override
    public void add(int index, T data) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(data);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index; i++) {
                prev = prev.getNext();
            }
            newNode.setNext(prev.getNext());
            prev.setNext(newNode);
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        SimpleList<T> subList = new SimpleList<>();
        Node<T> aux = head;
        for(int i = 0; i <fromIndex; i++){
            if(aux == null) return null;
            aux = aux.getNext();
        }
        for (int j = fromIndex; j <= toIndex; j++) {
            subList.add(aux.getData());
            aux = aux.getNext();
        }
        return subList;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size(); i++) {
            if (index == -1 && get(i).equals(o)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastFoundIndex = -1;
        for (int i = 0; i < size(); i++)
            if (get(i).equals(o)) {
                lastFoundIndex = i;
            }
        return lastFoundIndex;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean allContained = true;
        for (Object element : c) {
            if (!contains(element)) {
                allContained = true;
            }
        }
        return allContained;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

}
